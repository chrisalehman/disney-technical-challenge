package com.disney.studios.petapp.domain.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class DogPictures implements Serializable {
    Map<String, List<DogPicture>> picturesByBreed;
    List<DogPicture> pictures;
}