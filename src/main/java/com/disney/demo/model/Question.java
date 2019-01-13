package com.disney.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "question")
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public class Question extends Auditable {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
        name = "question_generator",
        sequenceName = "question_sequence",
        initialValue = 1000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String description;
}