package com.disney.studios.petapp.service;

import com.disney.studios.petapp.domain.external.DogPicture;
import com.disney.studios.petapp.domain.external.Vote;

import java.util.List;
import java.util.Map;


public interface PetAppService {

    Map<String,List<DogPicture>> getDogPicturesGroupedByBreed();

    List<DogPicture> getDogPictures(String breed);

    DogPicture getDogPicture(long id);

    void castVote(Vote vote);
}