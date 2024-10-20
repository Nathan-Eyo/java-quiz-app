package com.assignment.quizzy.controller;

import com.assignment.quizzy.model.Question;
import com.assignment.quizzy.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {

  private final IQuestionService questionService;

//    private  QuestionController(IQuestionService questionService) {
//        this.questionService = questionService;
//    }

    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    };

    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    };

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> question = questionService.getQuestionById(id);
        if(question.isPresent()){
            return ResponseEntity.ok(question.get());
        } else{
            throw new ChangeSetPersister.NotFoundException();
        }
    };

    @PutMapping("/{id}/update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
    Question updatedQuestion = questionService.updateQuestion(id, question);
    return ResponseEntity.ok(updatedQuestion);
    };

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    };

    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    };

    @GetMapping("/difficulty")
    public ResponseEntity<List<String>> getDifficulty(){
        List<String> difficulties = questionService.getAllDifficulties();
        return ResponseEntity.ok(difficulties);
    };

    @GetMapping("/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer numOfQuestion,@RequestParam String subject){
        List<Question> questionsForUser = questionService.getQuestionsForUser(numOfQuestion, subject);
        List<Question> mutableQuestions = new ArrayList<>(questionsForUser);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(numOfQuestion, mutableQuestions.size());

        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);

        return ResponseEntity.ok(randomQuestions);
    }
}
