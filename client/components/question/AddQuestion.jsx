import React, { useEffect, useState } from "react";
import {
  createQuestion,
  getAllDifficulty,
  getAllSubjects,
} from "../../utils/QuizService";
import { Link } from "react-router-dom";

const AddQuestion = () => {
  const [question, setQuestion] = useState("");
  const [questionType, setQuestionType] = useState("single");
  const [choices, setChoices] = useState([""]);
  const [correctAnswers, setCorrectAnswers] = useState([""]);
  const [subject, setSubject] = useState("");
  const [difficulty, setDifficulty] = useState("");
  const [newdifficulty, setNewDifficulty] = useState("");
  const [newSubject, setNewSubject] = useState("");
  const [subjectOptions, setSubjectOptions] = useState([""]);
  const [difficultyOptions, setDifficultyOptions] = useState([""]);

  useEffect(() => {
    fetchSubjects();
    fetchDifficulty();
  }, []);

  const fetchSubjects = async () => {
    try {
      const subjectData = await getAllSubjects();
      setSubjectOptions(subjectData);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchDifficulty = async () => {
    try {
      const difficultyData = await getAllDifficulty();
      setDifficultyOptions(difficultyData);
    } catch (error) {
      console.error(error);
    }
  };

  const handleAddChoice = () => {
    const lastChoice = choices[choices.length - 1];
    const lastChoiceLetter = lastChoice ? lastChoice.charAt(0) : "A";
    const newChoiceLetter = String.fromCharCode(lastChoiceLetter.charCodeAt(0) + 1);
    const newChoice = `${newChoiceLetter}.`;
    setChoices([...choices, newChoice]);
  };

  const handleRemoveChoice = (index) => {
    setChoices(choices.filter((choice, i) => i !== index));
  };

  const handleChoiceChange = (index, value) => {
    setChoices(choices.map((choice, i) => (i === index ? value : choice)));
  };

  const handleCorrectAnswerChange = (index, value) => {
    setCorrectAnswers(correctAnswers.map((answer, i) => (i === index ? value : answer)));
  };

  const handleAddCorrectAnswer = () => {
    setCorrectAnswers([...correctAnswers, ""]);
  };

  const handleRemoveCorrectAnswer = (index) => {
    setCorrectAnswers(correctAnswers.filter((answer, i) => i !== index));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const result = {
        question,
        questionType,
        choices,
        correctAnswers: correctAnswers.map((answer) => {
          const choiceLetter = answer.charAt(0).toUpperCase();
          const choiceIndex = choiceLetter.charCodeAt(0) - 65;
          return choiceIndex >= 0 && choiceIndex < choices.length
            ? choiceLetter
            : null;
        }),
        subject,
        difficulty,
      };
      await createQuestion(result);
      setQuestion("");
      setQuestionType("single");
      setChoices([""]);
      setCorrectAnswers([""]);
      setSubject("");
      setDifficulty("");
    } catch (error) {
      console.error(error);
    }
  };

  const handleAddSubject = () => {
    if (!subject.trim() !== "") {
      setSubject(newSubject.trim());
      setSubjectOptions([...subjectOptions, newSubject.trim()]);
      setNewSubject("");
    }
  };

  const handleAddDifficulty = () => {
    if (!difficulty.trim() !== "") {
      setDifficulty(newdifficulty.trim());
      setDifficultyOptions([...difficultyOptions, newdifficulty.trim()]);
      setNewDifficulty("");
    }
  };

  return (
    <div className="container" style={{ maxWidth: "900px"}}>
      <div className="row justify-content-center">
        <div className="col-lg-8 col-md-10">
          <div className="card mt-5">
            <div className="card-header bg-primary text-white">
              <h5 className="card-title text-center">Add New Question</h5>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-4">
                  <label htmlFor="subject" className="form-label">
                    Select Subject
                  </label>
                  <select
                    id="subject"
                    value={subject}
                    onChange={(e) => setSubject(e.target.value)}
                    className="form-select"
                  >
                    <option value="">Select subject</option>
                    <option value={"New"}>Add New</option>
                    {subjectOptions.map((option) => (
                      <option key={option} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
                </div>

                {subject === "New" && (
                  <div className="mb-4">
                    <label htmlFor="new-subject" className="form-label">
                      Add New Subject
                    </label>
                    <div className="input-group">
                      <input
                        type="text"
                        id="new-subject"
                        value={newSubject}
                        onChange={(e) => setNewSubject(e.target.value)}
                        className="form-control"
                      />
                      <button
                        type="button"
                        onClick={handleAddSubject}
                        className="btn btn-outline-secondary"
                      >
                        Add Subject
                      </button>
                    </div>
                  </div>
                )}

                <div className="mb-4">
                  <label htmlFor="difficulty" className="form-label">
                    Select Difficulty
                  </label>
                  <select
                    id="difficulty"
                    value={difficulty}
                    onChange={(e) => setDifficulty(e.target.value)}
                    className="form-select"
                  >
                    <option value="">Select Difficulty</option>
                    <option value={"New"}>Add New</option>
                    {difficultyOptions.map((option) => (
                      <option key={option} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
                </div>

                {difficulty === "New" && (
                  <div className="mb-4">
                    <label htmlFor="new-difficulty" className="form-label">
                      Add New Difficulty
                    </label>
                    <div className="input-group">
                      <input
                        type="text"
                        id="new-difficulty"
                        value={newdifficulty}
                        onChange={(e) => setNewDifficulty(e.target.value)}
                        className="form-control"
                      />
                      <button
                        type="button"
                        onClick={handleAddDifficulty}
                        className="btn btn-outline-secondary"
                      >
                        Add Difficulty
                      </button>
                    </div>
                  </div>
                )}

                <div className="mb-4">
                  <label htmlFor="question-text" className="form-label">
                    Question
                  </label>
                  <textarea
                    className="form-control"
                    rows={4}
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Enter your question here"
                  ></textarea>
                </div>

                <div className="mb-4">
                  <label htmlFor="question-type" className="form-label">
                    Question Type
                  </label>
                  <select
                    id="question-type"
                    value={questionType}
                    onChange={(e) => setQuestionType(e.target.value)}
                    className="form-select"
                  >
                    <option value="single">Single Answer</option>
                    <option value="multiple">Multiple Answers</option>
                  </select>
                </div>

                <div className="mb-4">
                  <label htmlFor="choices" className="form-label">
                    Choices
                  </label>
                  {choices.map((choice, index) => (
                    <div key={index} className="input-group mb-2">
                      <input
                        type="text"
                        value={choice}
                        onChange={(e) => handleChoiceChange(index, e.target.value)}
                        className="form-control"
                      />
                      <button
                        type="button"
                        onClick={() => handleRemoveChoice(index)}
                        className="btn btn-outline-danger"
                      >
                        Remove
                      </button>
                    </div>
                  ))}
                  <button
                    type="button"
                    onClick={handleAddChoice}
                    className="btn btn-outline-primary"
                  >
                    Add Choice
                  </button>
                </div>

                {questionType === "single" && (
                  <div className="mb-4">
                    <label htmlFor="answer" className="form-label">
                      Correct Answer
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      id="answer"
                      value={correctAnswers[0]}
                      onChange={(e) => handleCorrectAnswerChange(0, e.target.value)}
                    />
                  </div>
                )}

                {questionType === "multiple" && (
                  <div className="mb-4">
                    <label htmlFor="answer" className="form-label">
                      Correct Answer(s)
                    </label>
                    {correctAnswers.map((answer, index) => (
                      <div key={index} className="d-flex mb-2">
                        <input
                          type="text"
                          className="form-control me-2"
                          value={answer}
                          onChange={(e) => handleCorrectAnswerChange(index, e.target.value)}
                        />
                        {index > 0 && (
                          <button
                            type="button"
                            className="btn btn-danger"
                            onClick={() => handleRemoveCorrectAnswer(index)}
                          >
                            Remove
                          </button>
                        )}
                      </div>
                    ))}
                    <button
                      type="button"
                      className="btn btn-outline-info"
                      onClick={handleAddCorrectAnswer}
                    >
                      Add Correct Answer
                    </button>
                  </div>
                )}

                <div className="text-center">
                  <button type="submit" className="btn btn-success me-3">
                    Save Question
                  </button>
                  <Link to={"/all-quizzes"} className="btn btn-primary">
                    Back to existing questions
                  </Link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddQuestion;

