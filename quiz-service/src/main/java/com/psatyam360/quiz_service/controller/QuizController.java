package com.psatyam360.quiz_service.controller;

import com.psatyam360.quiz_service.model.QuestionWrapper;
import com.psatyam360.quiz_service.model.QuizDto;
import com.psatyam360.quiz_service.model.Response;
import com.psatyam360.quiz_service.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer numQ, @RequestParam String title) {
//        return quizService.createQuiz(category, numQ, title);
//    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQ(), quizDto.getTitle());
    }

    @GetMapping("/get/{quiz_id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer quiz_id) {
        return quizService.getQuizById(quiz_id);
    }

    @PostMapping("/submit/{quiz_id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quiz_id, @RequestBody List<Response> responses) {
        return quizService.processResponse(quiz_id, responses);
    }
}
