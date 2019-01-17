package com.disney.studios.petapp.service;

import com.disney.studios.petapp.domain.ResourceNotFoundException;
import com.disney.studios.petapp.domain.VoteConstraintException;
import com.disney.studios.petapp.domain.external.DogPicture;
import com.disney.studios.petapp.domain.external.Vote;
import com.disney.studios.petapp.repository.DogPictureVoteAggregateRepository;
import com.disney.studios.petapp.repository.VoteRepository;
import com.disney.studios.petapp.repository.ClientRepository;
import com.disney.studios.petapp.repository.DogPictureRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.slf4j.LoggerFactory.getLogger;


@Service
@Transactional
public class PetAppServiceImpl implements PetAppService {

    private static final Logger LOGGER = getLogger(PetAppServiceImpl.class);

    @Autowired private DogPictureRepository dogPictureRepo;
    @Autowired private DogPictureVoteAggregateRepository aggregateRepo;
    @Autowired private VoteRepository voteRepo;
    @Autowired private ClientRepository clientRepo;


    @Override
    public Map<String, List<DogPicture>> getDogPicturesGroupedByBreed() {
        return aggregateRepo.findAllAggregates()
            .stream()
            .map(e -> new DogPicture(e.getId(), e.getUrl(), e.getBreed(), e.getVotes()))
            .collect(Collectors.groupingBy(DogPicture::getBreed, Collectors.toList()));
    }

    @Override
    public List<DogPicture> getDogPictures(String breed) {
        return aggregateRepo.findByBreed(breed)
            .stream()
            .map(e -> new DogPicture(e.getId(), e.getUrl(), e.getBreed(), e.getVotes()))
            .collect(Collectors.toList());
    }

    @Override
    public DogPicture getDogPicture(long id) {
        return aggregateRepo.findOneAggregate(id)
            .map(e -> new DogPicture(e.getId(), e.getUrl(), e.getBreed(), e.getVotes()))
            .orElseThrow(() -> new ResourceNotFoundException("Dog picture not found with id " + id));
    }

    @Override
    public void castVote(Vote v) {

        com.disney.studios.petapp.domain.internal.Client client = clientRepo
            .findByClientName(v.getClientName())
            .orElseThrow(() -> new ResourceNotFoundException("Client not found with client name " + v.getClientName()));

        com.disney.studios.petapp.domain.internal.DogPicture dogPicture = dogPictureRepo
            .findById(v.getDogPictureId())
            .orElseThrow(() -> new ResourceNotFoundException("Dog picture not found with id " + v.getDogPictureId()));

        com.disney.studios.petapp.domain.internal.Vote vote =
            new com.disney.studios.petapp.domain.internal.Vote(dogPicture, client);

        // vote up
        if (v.getVoteUp()) {
            try {
                voteRepo.save(vote);
            } catch (Exception e) {
                // indicates a unique constraint was tripped - only one up vote allowed per client per picture
                if (e.getMessage().contains("idx_vote_aggregate_key"))
                    throw new VoteConstraintException("Only one vote allowed per client and picture", e);
                else
                    throw e;
            }
        }

        // vote down
        if (!v.getVoteUp()) {

            Optional<com.disney.studios.petapp.domain.internal.Vote> vote2 =
                voteRepo.findByClientIdAndDogPictureId(client.getId(), dogPicture.getId());

            // should never happen - log warning
            if (!vote2.isPresent()) {
                LOGGER.warn("Unable to load vote by client ID {} and dog picture ID {}", client.getId(), dogPicture.getId());
                return;
            }

            // delete
            voteRepo.delete(vote2.get());
        }
    }
}