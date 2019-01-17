package com.disney.studios.petapp.domain.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DogPicture implements Comparable<DogPicture>, Serializable {
    private Long id;
    private String url;
    private String breed;
    private Integer votes;

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object x) {
        return (x instanceof DogPicture) && url.equals(((DogPicture) x).url);
    }

    @Override
    public int compareTo(DogPicture o) {
        return (o == null) ? 1 : votes - o.votes;
    }
}