package com.assignment.quizzy.controller;

import com.assignment.quizzy.model.Question;
import com.assignment.quizzy.service.IQuestionService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {

  private final IQuestionService questionService;

    @PostMapping("/create-new-question")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Question question) {
        try {
            // Attempt to create the question using the service
            Question createdQuestion = questionService.createQuestion(question);
            // Return a successful response if no issues arise
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } catch (DataIntegrityViolationException e) {
            // This handles database-related exceptions (e.g., constraint violations)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            // This handles validation-related issues
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            // Generic catch-all for unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the question: " + e.getMessage());
        }
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            return ResponseEntity.ok(questions);
        } catch (Exception ex) {
            // Return the error message and exception type
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    };

    @GetMapping("/question/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        try {
            Optional<Question> question = questionService.getQuestionById(id);
            if (question.isPresent()) {
                return ResponseEntity.ok(question.get());
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }
        } catch (ChangeSetPersister.NotFoundException ex) {
            // Handle NotFoundException specifically
            String errorMessage = String.format("Error: %s. Exception Type: %s", "Question not found", ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception ex) {
            // Catch any other exceptions
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PutMapping("/question/{id}/update")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, question);
            return ResponseEntity.ok(updatedQuestion);
        } catch (ChangeSetPersister.NotFoundException ex) {
            // Handle not found exception
            String errorMessage = String.format("Question with id %d not found. Exception Type: %s", id, ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception ex) {
            // Handle any other exception
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            // Handle any other exception
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<String> subjects = questionService.getAllSubjects();
            return ResponseEntity.ok(subjects);
        } catch (Exception ex) {
            // Handle any exception and return error message with exception type
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/difficulty")
    public ResponseEntity<?> getDifficulty() {
        try {
            List<String> difficulties = questionService.getAllDifficulties();
            return ResponseEntity.ok(difficulties);
        } catch (Exception ex) {
            // Handle any exception and return error message with exception type
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<?> getQuestionsForUser(@RequestParam Integer numOfQuestion, @RequestParam String subject, @RequestParam String difficulty) {
        try {
            List<Question> questionsForUser = questionService.getQuestionsForUser(numOfQuestion, subject, difficulty);
            List<Question> mutableQuestions = new ArrayList<>(questionsForUser);
            Collections.shuffle(mutableQuestions);

            int availableQuestions = Math.min(numOfQuestion, mutableQuestions.size());
            List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);

            return ResponseEntity.ok(randomQuestions);
        } catch (Exception ex) {
            // Handle any exception and return error message with exception type
            String errorMessage = String.format("Error occurred: %s. Exception Type: %s", ex.getMessage(), ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
