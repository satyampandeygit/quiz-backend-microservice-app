package com.psatyam360.question_service.service;

import com.psatyam360.question_service.model.Question;
import com.psatyam360.question_service.model.QuestionWrapper;
import com.psatyam360.question_service.model.Response;
import com.psatyam360.question_service.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    ResponseEntity<String> createQuestion(Question question);

    ResponseEntity<List<Question>> getQuestions();

    ResponseEntity<Question> getQuestionById(Integer id);

    ResponseEntity<List<Question>> getQuestionByCategory(String category);

    ResponseEntity<String> updateQuestion(Question question);

    ResponseEntity<String> deleteQuestion(Integer id);

    ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ);

    ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds);

    ResponseEntity<Integer> getScoreForResponses(List<Response> responses);
}
