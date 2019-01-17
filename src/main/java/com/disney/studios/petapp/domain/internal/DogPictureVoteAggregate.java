package com.disney.studios.petapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dog_picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DogPictureVoteAggregate {
    @Id
    private Long id;
    private String url;
    private String breed;
    private Integer votes;
}