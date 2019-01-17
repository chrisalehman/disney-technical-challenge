package com.disney.studios.petapp.repository;

import com.disney.studios.petapp.domain.internal.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM Client c WHERE c.client_name = ?1", nativeQuery = true)
    Optional<Client> findByClientName(String clientName);
}