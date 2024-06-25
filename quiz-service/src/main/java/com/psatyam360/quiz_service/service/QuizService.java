package com.psatyam360.quiz_service.service;

import com.psatyam360.quiz_service.model.QuestionWrapper;
import com.psatyam360.quiz_service.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    ResponseEntity<String> createQuiz(String category, Integer noOfQuestions, String quizTitle);

    ResponseEntity<List<QuestionWrapper>> getQuizById(Integer quizId);

    ResponseEntity<Integer> processResponse(Integer quizId, List<Response> responses);
}
