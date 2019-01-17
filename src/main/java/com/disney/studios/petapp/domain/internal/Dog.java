package com.disney.studios.petapp.domain.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dog")
@Getter
@Setter
@NoArgsConstructor
public class Dog extends Auditable {

    public Dog(String breed) {
        super();
        this.breed = breed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String breed;
}