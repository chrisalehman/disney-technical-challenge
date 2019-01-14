package com.disney.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
public class Question extends Auditable {

    public Question(String title, String text) {
        super();
        this.title = title;
        this.text = text;
    }

    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_id_seq")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 128)
    private String title;

    @NotBlank
    @Size(min = 3, max = 2048)
    private String text;
}