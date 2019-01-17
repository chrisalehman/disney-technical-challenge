package com.disney.studios.petapp.repository;

import com.disney.studios.petapp.domain.internal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {}