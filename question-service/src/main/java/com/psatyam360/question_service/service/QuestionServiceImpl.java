package com.psatyam360.question_service.service;

import com.psatyam360.question_service.model.Question;
import com.psatyam360.question_service.model.QuestionWrapper;
import com.psatyam360.question_service.model.Response;
import com.psatyam360.question_service.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public ResponseEntity<String> createQuestion(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Question> getQuestionById(Integer id) {
        try {
            return new ResponseEntity<>(questionRepository.findById(id).orElse(new Question()), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.NOT_MODIFIED);
    }

    @Override
    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        try {
            List<Integer> questionsId = questionRepository.findRandomQuestionByCategory(category, numQ);

            return new ResponseEntity<>(questionsId, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds) {
        try {
            List<QuestionWrapper> questionWrappersList = new ArrayList<>();

            List<Question> questionList = new ArrayList<>();

            for(Integer question_id : questionIds) {
                Question question = questionRepository.findById(question_id).orElse(null);

                if(question == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
                }

                questionList.add(question);
            }

            for(Question question : questionList) {
                QuestionWrapper questionWrapper = new QuestionWrapper(question.getQuestion_id(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
                questionWrappersList.add(questionWrapper);
            }

            return new ResponseEntity<>(questionWrappersList, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Integer> getScoreForResponses(List<Response> responses) {
        try {
            Integer score = 0;
            for(Response response : responses) {
                Question question  = questionRepository.findById(response.getQuestion_id()).orElse(null);
                if(question == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
                }

                if(question.getRightAnswer().equals(response.getOption_chosen())){
                    score += 1;
                }
            }


            return new ResponseEntity<>(score, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
    }
}
