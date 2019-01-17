package com.disney.studios.petapp.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VoteConstraintException extends RuntimeException {
    public VoteConstraintException(String message, Throwable e) {
        super(message, e);
    }
}