package com.assignment.quizzy.repository;

import com.assignment.quizzy.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubject();

    @Query("SELECT DISTINCT q.difficulty FROM Question q")
    List<String> findDistinctDifficulty();

    Page<Question> findBySubject(String subject, Pageable pageable);

    Page<Question> findBySubjectAndDifficulty(String subject, String difficulty, Pageable pageable);
}
