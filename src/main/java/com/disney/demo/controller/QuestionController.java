package com.disney.demo.controller;

import com.disney.demo.model.Question;
import com.disney.demo.sevice.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping("/questions")
    public Page<Question> getQuestions(Pageable pageable) {
        return service.get(pageable);
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return service.create(
            question.getTitle(),
            question.getText());
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable Long id,
                                   @Valid @RequestBody Question question) {
        return service.update(
            id,
            question.getTitle(),
            question.getText());
    }

    @PatchMapping("/questions/{id}")
    public Question patchQuestion(@PathVariable Long id,
                                  @RequestBody Question question) {
        return service.partialUpdate(
                id,
                question.getTitle(),
                question.getText());
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}