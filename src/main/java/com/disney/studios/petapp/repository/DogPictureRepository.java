package com.disney.studios.petapp.repository;

import com.disney.studios.petapp.domain.internal.DogPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DogPictureRepository extends JpaRepository<DogPicture, Long> {
}