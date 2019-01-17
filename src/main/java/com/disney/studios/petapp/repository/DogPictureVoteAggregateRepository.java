package com.disney.studios.petapp.repository;

import com.disney.studios.petapp.domain.internal.DogPictureVoteAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface DogPictureVoteAggregateRepository extends JpaRepository<DogPictureVoteAggregate, Long> {

    @Query(value =
        "SELECT DISTINCT p.id, p.url, d.breed, count(v.id) as votes" +
        "   FROM dog_picture p" +
        "   JOIN dog d ON d.id = p.dog_id" +
        "   LEFT JOIN vote v ON p.id = v.dog_picture_id" +
        "   WHERE d.breed = ?1" +
        "   GROUP BY p.id, d.breed" +
        "   ORDER BY count(v.id) desc, breed asc, id asc", nativeQuery = true)
    List<DogPictureVoteAggregate> findByBreed(String breed);

    @Query(value =
        "SELECT DISTINCT p.id, p.url, d.breed, count(v.id) as votes" +
        "   FROM dog_picture p" +
        "   JOIN dog d ON d.id = p.dog_id" +
        "   LEFT JOIN vote v ON p.id = v.dog_picture_id" +
        "   GROUP BY p.id, d.breed" +
        "   ORDER BY count(v.id) desc, breed asc, id asc", nativeQuery = true)
    List<DogPictureVoteAggregate> findAllAggregates();

    @Query(value =
        "SELECT DISTINCT p.id , p.url, d.breed, count(v.id) as votes" +
        "   FROM dog_picture p" +
        "   JOIN dog d ON d.id = p.dog_id" +
        "   LEFT JOIN vote v ON p.id = v.dog_picture_id" +
        "   WHERE p.id = ?1" +
        "   GROUP BY p.id, d.breed", nativeQuery = true)
    Optional<DogPictureVoteAggregate> findOneAggregate(Long id);
}