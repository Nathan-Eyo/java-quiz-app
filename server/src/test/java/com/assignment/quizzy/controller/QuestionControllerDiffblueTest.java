package com.assignment.quizzy.controller;

import static org.mockito.Mockito.when;

import com.assignment.quizzy.model.Question;
import com.assignment.quizzy.service.IQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
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
}
