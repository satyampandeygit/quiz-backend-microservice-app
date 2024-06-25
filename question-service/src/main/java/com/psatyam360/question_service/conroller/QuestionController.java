package com.psatyam360.question_service.conroller;

import com.psatyam360.question_service.model.Question;
import com.psatyam360.question_service.model.QuestionWrapper;
import com.psatyam360.question_service.model.Response;
import com.psatyam360.question_service.service.QuestionService;
import org.springframework.cloud.bootstrap.encrypt.EnvironmentDecryptApplicationInitializer;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    Environment environment;

    public QuestionController(QuestionService questionService, Environment environment) {
        this.questionService = questionService;
        this.environment = environment;
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getQuestions(){
        return questionService.getQuestions();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer question_id){
        return questionService.getQuestionById(question_id);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/delete/{question_id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer question_id) {
        return questionService.deleteQuestion(question_id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("./local.server.port"));
        return questionService.getQuestions(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScoreForResponses(@RequestBody List<Response> responses) {
        System.out.println(environment.getProperty("./local.server.port"));
        return questionService.getScoreForResponses(responses);
    }
}
