package com.assignment.quizzy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.assignment.quizzy.model.Question;
import com.assignment.quizzy.repository.QuestionRepository;
import com.assignment.quizzy.service.IQuestionService;
import com.assignment.quizzy.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {QuestionController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class QuestionControllerDiffblueTest {
    @MockBean
    private IQuestionService iQuestionService;

    @Autowired
    private QuestionController questionController;

    /**
     * Test {@link QuestionController#createQuestion(Question)}.
     * <ul>
     *   <li>Given {@link Question} (default constructor) Difficulty is
     * {@code Difficulty}.</li>
     *   <li>Then status {@link StatusResultMatchers#isCreated()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#createQuestion(Question)}
     */
    @Test
    @DisplayName("Test createQuestion(Question); given Question (default constructor) Difficulty is 'Difficulty'; then status isCreated()")
    void testCreateQuestion_givenQuestionDifficultyIsDifficulty_thenStatusIsCreated() throws Exception {
        // Arrange
        Question question = new Question();
        question.setDifficulty("Difficulty");
        question.setId(1L);
        question.setQuestion("Question");
        question.setQuestionType("Question Type");
        question.setSubject("Hello from the Dreaming Spires");
        when(iQuestionService.createQuestion(Mockito.<Question>any())).thenReturn(question);

        Question question2 = new Question();
        question2.setDifficulty("Difficulty");
        question2.setId(1L);
        question2.setQuestion("Question");
        question2.setQuestionType("Question Type");
        question2.setSubject("Hello from the Dreaming Spires");
        String content = (new ObjectMapper()).writeValueAsString(question2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quizzes/create-new-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(questionController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"question\":\"Question\",\"subject\":\"Hello from the Dreaming Spires\",\"questionType\":\"Question"
                                        + " Type\",\"difficulty\":\"Difficulty\",\"choices\":null,\"correctAnswers\":null}"));
    }

    /**
     * Test {@link QuestionController#createQuestion(Question)}.
     * <ul>
     *   <li>Then content string {@code Invalid data: Msg}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#createQuestion(Question)}
     */
    @Test
    @DisplayName("Test createQuestion(Question); then content string 'Invalid data: Msg'")
    void testCreateQuestion_thenContentStringInvalidDataMsg() throws Exception {
        // Arrange
        when(iQuestionService.createQuestion(Mockito.<Question>any()))
                .thenThrow(new DataIntegrityViolationException("Msg"));

        Question question = new Question();
        question.setDifficulty("Difficulty");
        question.setId(1L);
        question.setQuestion("Question");
        question.setQuestionType("Question Type");
        question.setSubject("Hello from the Dreaming Spires");
        String content = (new ObjectMapper()).writeValueAsString(question);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quizzes/create-new-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(questionController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Invalid data: Msg"));
    }

    /**
     * Test {@link QuestionController#createQuestion(Question)}.
     * <ul>
     *   <li>Then content string {@code Validation error: null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#createQuestion(Question)}
     */
    @Test
    @DisplayName("Test createQuestion(Question); then content string 'Validation error: null'")
    void testCreateQuestion_thenContentStringValidationErrorNull() throws Exception {
        // Arrange
        when(iQuestionService.createQuestion(Mockito.<Question>any())).thenThrow(new ConstraintViolationException(null));

        Question question = new Question();
        question.setDifficulty("Difficulty");
        question.setId(1L);
        question.setQuestion("Question");
        question.setQuestionType("Question Type");
        question.setSubject("Hello from the Dreaming Spires");
        String content = (new ObjectMapper()).writeValueAsString(question);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/quizzes/create-new-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(questionController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Validation error: null"));
    }

    /**
     * Test {@link QuestionController#getAllQuestions()}.
     * <ul>
     *   <li>Then return Body is a string.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getAllQuestions()}
     */
    @Test
    @DisplayName("Test getAllQuestions(); then return Body is a string")
    void testGetAllQuestions_thenReturnBodyIsAString() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ResponseEntity<?> actualAllQuestions = (new QuestionController(null)).getAllQuestions();

        // Assert
        HttpStatusCode statusCode = actualAllQuestions.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"com.assignment.quizzy.service.IQuestionService.getAllQuestions()\" because"
                        + " \"this.questionService\" is null. Exception Type: NullPointerException",
                actualAllQuestions.getBody());
        assertEquals(500, actualAllQuestions.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualAllQuestions.hasBody());
    }

    /**
     * Test {@link QuestionController#getAllQuestions()}.
     * <ul>
     *   <li>Then return Body is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getAllQuestions()}
     */
    @Test
    @DisplayName("Test getAllQuestions(); then return Body is 'null'")
    void testGetAllQuestions_thenReturnBodyIsNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        when(questionRepository.findAll()).thenReturn(null);

        // Act
        ResponseEntity<?> actualAllQuestions = (new QuestionController(new QuestionService(questionRepository)))
                .getAllQuestions();

        // Assert
        verify(questionRepository).findAll();
        HttpStatusCode statusCode = actualAllQuestions.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualAllQuestions.getBody());
        assertEquals(200, actualAllQuestions.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertFalse(actualAllQuestions.hasBody());
    }

    /**
     * Test {@link QuestionController#getQuestionById(Long)}.
     * <p>
     * Method under test: {@link QuestionController#getQuestionById(Long)}
     */
    @Test
    @DisplayName("Test getQuestionById(Long)")
    void testGetQuestionById() throws Exception {
        // Arrange
        Question question = new Question();
        question.setDifficulty("Difficulty");
        question.setId(1L);
        question.setQuestion("Question");
        question.setQuestionType("Question Type");
        question.setSubject("Hello from the Dreaming Spires");
        Optional<Question> ofResult = Optional.of(question);
        when(iQuestionService.getQuestionById(Mockito.<Long>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/quizzes/question/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(questionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"question\":\"Question\",\"subject\":\"Hello from the Dreaming Spires\",\"questionType\":\"Question"
                                        + " Type\",\"difficulty\":\"Difficulty\",\"choices\":null,\"correctAnswers\":null}"));
    }

    /**
     * Test {@link QuestionController#updateQuestion(Long, Question)}.
     * <p>
     * Method under test: {@link QuestionController#updateQuestion(Long, Question)}
     */
    @Test
    @DisplayName("Test updateQuestion(Long, Question)")
    void testUpdateQuestion() throws Exception {
        // Arrange
        Question question = new Question();
        question.setDifficulty("Difficulty");
        question.setId(1L);
        question.setQuestion("Question");
        question.setQuestionType("Question Type");
        question.setSubject("Hello from the Dreaming Spires");
        when(iQuestionService.updateQuestion(Mockito.<Long>any(), Mockito.<Question>any())).thenReturn(question);

        Question question2 = new Question();
        question2.setDifficulty("Difficulty");
        question2.setId(1L);
        question2.setQuestion("Question");
        question2.setQuestionType("Question Type");
        question2.setSubject("Hello from the Dreaming Spires");
        String content = (new ObjectMapper()).writeValueAsString(question2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/quizzes/question/{id}/update", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(questionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"question\":\"Question\",\"subject\":\"Hello from the Dreaming Spires\",\"questionType\":\"Question"
                                        + " Type\",\"difficulty\":\"Difficulty\",\"choices\":null,\"correctAnswers\":null}"));
    }

    /**
     * Test {@link QuestionController#deleteQuestion(Long)}.
     * <ul>
     *   <li>Then return Body is a string.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#deleteQuestion(Long)}
     */
    @Test
    @DisplayName("Test deleteQuestion(Long); then return Body is a string")
    void testDeleteQuestion_thenReturnBodyIsAString() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ResponseEntity<?> actualDeleteQuestionResult = (new QuestionController(null)).deleteQuestion(1L);

        // Assert
        HttpStatusCode statusCode = actualDeleteQuestionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"com.assignment.quizzy.service.IQuestionService.deleteQuestion(java.lang.Long)\""
                        + " because \"this.questionService\" is null. Exception Type: NullPointerException",
                actualDeleteQuestionResult.getBody());
        assertEquals(500, actualDeleteQuestionResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualDeleteQuestionResult.hasBody());
    }

    /**
     * Test {@link QuestionController#deleteQuestion(Long)}.
     * <ul>
     *   <li>Then return Body is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#deleteQuestion(Long)}
     */
    @Test
    @DisplayName("Test deleteQuestion(Long); then return Body is 'null'")
    void testDeleteQuestion_thenReturnBodyIsNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        doNothing().when(questionRepository).deleteById(Mockito.<Long>any());

        // Act
        ResponseEntity<?> actualDeleteQuestionResult = (new QuestionController(new QuestionService(questionRepository)))
                .deleteQuestion(1L);

        // Assert
        verify(questionRepository).deleteById(eq(1L));
        HttpStatusCode statusCode = actualDeleteQuestionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualDeleteQuestionResult.getBody());
        assertEquals(204, actualDeleteQuestionResult.getStatusCodeValue());
        assertEquals(HttpStatus.NO_CONTENT, statusCode);
        assertFalse(actualDeleteQuestionResult.hasBody());
    }

    /**
     * Test {@link QuestionController#getAllSubjects()}.
     * <ul>
     *   <li>Then return Body is a string.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getAllSubjects()}
     */
    @Test
    @DisplayName("Test getAllSubjects(); then return Body is a string")
    void testGetAllSubjects_thenReturnBodyIsAString() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ResponseEntity<?> actualAllSubjects = (new QuestionController(null)).getAllSubjects();

        // Assert
        HttpStatusCode statusCode = actualAllSubjects.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"com.assignment.quizzy.service.IQuestionService.getAllSubjects()\" because"
                        + " \"this.questionService\" is null. Exception Type: NullPointerException",
                actualAllSubjects.getBody());
        assertEquals(500, actualAllSubjects.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualAllSubjects.hasBody());
    }

    /**
     * Test {@link QuestionController#getAllSubjects()}.
     * <ul>
     *   <li>Then return Body is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getAllSubjects()}
     */
    @Test
    @DisplayName("Test getAllSubjects(); then return Body is 'null'")
    void testGetAllSubjects_thenReturnBodyIsNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        when(questionRepository.findDistinctSubject()).thenReturn(null);

        // Act
        ResponseEntity<?> actualAllSubjects = (new QuestionController(new QuestionService(questionRepository)))
                .getAllSubjects();

        // Assert
        verify(questionRepository).findDistinctSubject();
        HttpStatusCode statusCode = actualAllSubjects.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualAllSubjects.getBody());
        assertEquals(200, actualAllSubjects.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertFalse(actualAllSubjects.hasBody());
    }

    /**
     * Test {@link QuestionController#getDifficulty()}.
     * <ul>
     *   <li>Then return Body is a string.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getDifficulty()}
     */
    @Test
    @DisplayName("Test getDifficulty(); then return Body is a string")
    void testGetDifficulty_thenReturnBodyIsAString() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ResponseEntity<?> actualDifficulty = (new QuestionController(null)).getDifficulty();

        // Assert
        HttpStatusCode statusCode = actualDifficulty.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"com.assignment.quizzy.service.IQuestionService.getAllDifficulties()\""
                        + " because \"this.questionService\" is null. Exception Type: NullPointerException",
                actualDifficulty.getBody());
        assertEquals(500, actualDifficulty.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualDifficulty.hasBody());
    }

    /**
     * Test {@link QuestionController#getDifficulty()}.
     * <ul>
     *   <li>Then return Body is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link QuestionController#getDifficulty()}
     */
    @Test
    @DisplayName("Test getDifficulty(); then return Body is 'null'")
    void testGetDifficulty_thenReturnBodyIsNull() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        when(questionRepository.findDistinctDifficulty()).thenReturn(null);

        // Act
        ResponseEntity<?> actualDifficulty = (new QuestionController(new QuestionService(questionRepository)))
                .getDifficulty();

        // Assert
        verify(questionRepository).findDistinctDifficulty();
        HttpStatusCode statusCode = actualDifficulty.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualDifficulty.getBody());
        assertEquals(200, actualDifficulty.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertFalse(actualDifficulty.hasBody());
    }

    /**
     * Test {@link QuestionController#getQuestionsForUser(Integer, String, String)}.
     * <ul>
     *   <li>Then calls
     * {@link QuestionRepository#findBySubjectAndDifficulty(String, String, Pageable)}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link QuestionController#getQuestionsForUser(Integer, String, String)}
     */
    @Test
    @DisplayName("Test getQuestionsForUser(Integer, String, String); then calls findBySubjectAndDifficulty(String, String, Pageable)")
    void testGetQuestionsForUser_thenCallsFindBySubjectAndDifficulty() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        when(questionRepository.findBySubjectAndDifficulty(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(null);

        // Act
        ResponseEntity<?> actualQuestionsForUser = (new QuestionController(new QuestionService(questionRepository)))
                .getQuestionsForUser(10, "Hello from the Dreaming Spires", "Difficulty");

        // Assert
        verify(questionRepository).findBySubjectAndDifficulty(eq("Hello from the Dreaming Spires"), eq("Difficulty"),
                isA(Pageable.class));
        HttpStatusCode statusCode = actualQuestionsForUser.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"org.springframework.data.domain.Page.getContent()\" because the return"
                        + " value of \"com.assignment.quizzy.repository.QuestionRepository.findBySubjectAndDifficulty(String,"
                        + " String, org.springframework.data.domain.Pageable)\" is null. Exception Type: NullPointerException",
                actualQuestionsForUser.getBody());
        assertEquals(500, actualQuestionsForUser.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualQuestionsForUser.hasBody());
        assertTrue(actualQuestionsForUser.getHeaders().isEmpty());
    }

    /**
     * Test {@link QuestionController#getQuestionsForUser(Integer, String, String)}.
     * <ul>
     *   <li>Then calls
     * {@link QuestionService#getQuestionsForUser(Integer, String, String)}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link QuestionController#getQuestionsForUser(Integer, String, String)}
     */
    @Test
    @DisplayName("Test getQuestionsForUser(Integer, String, String); then calls getQuestionsForUser(Integer, String, String)")
    void testGetQuestionsForUser_thenCallsGetQuestionsForUser() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        QuestionService questionService = mock(QuestionService.class);
        when(questionService.getQuestionsForUser(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(null);

        // Act
        ResponseEntity<?> actualQuestionsForUser = (new QuestionController(questionService)).getQuestionsForUser(10,
                "Hello from the Dreaming Spires", "Difficulty");

        // Assert
        verify(questionService).getQuestionsForUser(eq(10), eq("Hello from the Dreaming Spires"), eq("Difficulty"));
        HttpStatusCode statusCode = actualQuestionsForUser.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(
                "Error occurred: Cannot invoke \"java.util.Collection.toArray()\" because \"c\" is null. Exception Type:"
                        + " NullPointerException",
                actualQuestionsForUser.getBody());
        assertEquals(500, actualQuestionsForUser.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualQuestionsForUser.hasBody());
        assertTrue(actualQuestionsForUser.getHeaders().isEmpty());
    }
}
