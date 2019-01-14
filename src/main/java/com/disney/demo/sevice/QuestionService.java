package com.disney.demo.sevice;

import com.disney.demo.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;


public interface QuestionService {

    Page<Question> get(Pageable pageable);

    Optional<Question> getOne(long id);

    Question create(String title, String text);

    Question update(long id, String title, String text);

    Question partialUpdate(long id, String title, String text);

    void delete(long id);
}