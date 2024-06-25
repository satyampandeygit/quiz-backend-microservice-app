package com.psatyam360.quiz_service.service;

import com.psatyam360.quiz_service.feign.QuizInterface;
import com.psatyam360.quiz_service.model.*;
import com.psatyam360.quiz_service.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final QuizInterface quizInterface;

    public QuizServiceImpl(QuizRepository quizRepository, QuizInterface quizInterface){
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    @Override
    public ResponseEntity<String> createQuiz(String category, Integer noOfQuestions, String quizTitle) {
        try {
            List<Integer> questions = quizInterface.getQuestionsForQuiz(category, noOfQuestions).getBody();

            Quiz quiz = new Quiz();
            quiz.setTitle(quizTitle);
            quiz.setQuestionIds(questions);
            quizRepository.save(quiz);

            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer quizId) {
        try {
            Quiz quiz = quizRepository.findById(quizId).orElse(null);

            if(quiz == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
            }

            List<Integer> questionsIdsForQuiz = quiz.getQuestionIds();

            ResponseEntity<List<QuestionWrapper>> questionWrapperList = quizInterface.getQuestionsForQuiz(questionsIdsForQuiz);

            return questionWrapperList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Integer> processResponse(Integer quizId, List<Response> responses) {
        try{
            Quiz quiz = quizRepository.findById(quizId).orElse(null);

            if(quiz == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
            }

            Integer score = quizInterface.getScoreForResponses(responses).getBody();

            return new ResponseEntity<>(score, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
