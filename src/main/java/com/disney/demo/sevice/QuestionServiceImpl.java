package com.disney.demo.sevice;

import com.disney.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
@Transactional
public class QuestionServiceImpl {

    @Autowired
    private QuestionRepository questionRepository;
}
