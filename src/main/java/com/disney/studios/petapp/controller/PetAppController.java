package com.disney.studios.petapp.controller;

import com.disney.studios.petapp.domain.external.DogPicture;
import com.disney.studios.petapp.domain.external.DogPictures;
import com.disney.studios.petapp.domain.external.Vote;
import com.disney.studios.petapp.service.PetAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class PetAppController {

    @Autowired
    private PetAppService petAppService;


    @GetMapping("/pictures")
    public DogPictures getDogPictures(@RequestParam(name = "breed", required = false) String breed) {

        DogPictures result = new DogPictures();

        if (breed != null && !breed.trim().isEmpty())
            result.setPictures(petAppService.getDogPictures(breed.trim()));
        else
            result.setPicturesByBreed(petAppService.getDogPicturesGroupedByBreed());

        return result;
    }

    @GetMapping("/pictures/{id}")
    public DogPicture getDogPicture(@PathVariable Long id) {
        return petAppService.getDogPicture(id);
    }

    @PostMapping("/votes")
    public ResponseEntity<?> castVote(@Valid @RequestBody Vote vote) {
        petAppService.castVote(vote);
        return ResponseEntity.ok().build();
    }
}