package com.disney.demo.sevice;

import com.disney.demo.model.Question;
import com.disney.demo.model.exception.ResourceNotFoundException;
import com.disney.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;


    public Page<Question> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Question> getOne(long id) {
        return repository.findById(id);
    }

    public Question create(String title, String text) {
        Question q = new Question(title, text);
        return repository.save(q);
    }

    public Question update(long id, String title, String text) {
        return repository.findById(id)
            .map(q -> {
                q.setTitle(title);
                q.setText(text);
                return q;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
    }

    public Question partialUpdate(long id, String title, String text) {
        return repository.findById(id)
            .map(q -> {
                if (Optional.ofNullable(title).isPresent())
                    q.setTitle(title);
                if (Optional.ofNullable(text).isPresent())
                    q.setText(text);
                return q;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
    }

    public void delete(long id) {
        repository.findById(id)
            .map(q -> {
                repository.delete(q);
                return q;
            })
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
    }
}
