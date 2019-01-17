package com.disney.studios.petapp.repository;

import com.disney.studios.petapp.domain.internal.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT * FROM Vote v WHERE v.client_id = ?1 AND v.dog_picture_id = ?2", nativeQuery = true)
    Optional<Vote> findByClientIdAndDogPictureId(Long clientId, Long dogPictureId);
}