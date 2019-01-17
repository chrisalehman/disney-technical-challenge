package com.disney.studios.petapp.domain.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
public class Vote implements Serializable {

    @NotNull
    private String clientName;

    @NotNull
    private Long dogPictureId;

    @NotNull
    private Boolean voteUp;
}
