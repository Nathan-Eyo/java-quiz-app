import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllSubjects, getAllDifficulty } from "../../utils/QuizService";

const QuizStepper = () => {
  const [currentStep, setCurrentStep] = useState(1);
  const [selectedSubject, setSelectedSubject] = useState("");
  const [selectedDifficulty, setSelectedDifficulty] = useState("");
  const [selectedNumQuestions, setSelectedNumQuestions] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [difficulties, setDifficulties] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchSubjectData();
    fetchDiffcultyData();
  }, []);

  const fetchSubjectData = async () => {
    try {
      const subjectsData = await getAllSubjects();
      setSubjects(subjectsData);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchDiffcultyData = async () => {
    try {
      const difficultiesData = await getAllDifficulty();
      setDifficulties(difficultiesData);
    } catch (error) {
      console.error(error);
    }
  };

  const handleNext = () => {
    if (currentStep === 3) {
      if (selectedSubject && selectedNumQuestions && selectedDifficulty) {
        navigate("/take-quiz", {
          state: { selectedNumQuestions, selectedSubject, selectedDifficulty },
        });
      } else {
        alert("Please select a subject, difficulty and number of questions.");
      }
    } else {
      setCurrentStep((prevStep) => prevStep + 1);
    }
  };

  const handlePrevious = () => {
    setCurrentStep((prevStep) => prevStep - 1);
  };

  const handleSubjectChange = (event) => {
    setSelectedSubject(event.target.value);
  };

  const handleDifficultyChange = (event) => {
    setSelectedDifficulty(event.target.value);
  };

  const handleNumQuestionsChange = (event) => {
    setSelectedNumQuestions(event.target.value);
  };

  const renderStepContent = () => {
    switch (currentStep) {
      case 1:
        return (
          <div>
            <h3 className="text-info mb-2">I want to take a quiz on :</h3>
            <select
              className="form-select"
              value={selectedSubject}
              onChange={handleSubjectChange}
            >
              <option value="">Select a subject</option>
              {subjects.map((subject) => (
                <option key={subject} value={subject}>
                  {subject}
                </option>
              ))}
            </select>
          </div>
        );
      case 2:
        return (
          <div>
            <h3 className="text-info mb-2">What difficulty would you like to attempt :</h3>
            <select
              className="form-select"
              value={selectedDifficulty}
              onChange={handleDifficultyChange}
            >
              <option value="">Select a difficulty</option>
              {difficulties.map((difficulty) => (
                <option key={difficulty} value={difficulty}>
                  {difficulty}
                </option>
              ))}
            </select>
          </div>
        );
      case 3:
        return (
          <div>
            <h4 className="text-info mb-2">
              How many questions would you like to attempt ?
            </h4>
            <input
              type="number"
              className="form-control"
              value={selectedNumQuestions}
              onChange={handleNumQuestionsChange}
              placeholder="Enter the number of questions"
            />
          </div>
        );
      case 4:
        return (
          <div>
            <h2>Confirmation</h2>
            <p>Subject: {selectedSubject}</p>
            <p>Difficulty: {selectedDifficulty}</p>
            <p>Number of Questions: {selectedNumQuestions}</p>
          </div>
        );
      default:
        return null;
    }
  };

  const renderProgressBar = () => {
    const progress = currentStep === 3 ? 100 : ((currentStep - 1) / 2) * 100;
    return (
      <div className="progress">
        <div
          className="progress-bar"
          role="progressbar"
          style={{ width: `${progress}%` }}
          aria-valuenow={progress}
        >
          Step {currentStep}
        </div>
      </div>
    );
  };

  return (
    <section className="mt-5">
      <h3 style={{ color: "GrayText" }} className="mb-4">
        Welcome to quiz online
      </h3>
      {renderProgressBar()}
      <div className="card">
        <div className="card-body">
          {renderStepContent()}
          <div className="d-flex justify-content-between mt-4">
            {currentStep > 1 && (
              <button className="btn btn-primary" onClick={handlePrevious}>
                Previous
              </button>
            )}
            {currentStep < 3 && (
              <button
                className="btn btn-primary"
                onClick={handleNext}
                disabled={
                  (currentStep === 1 && !selectedSubject) ||
                  (currentStep === 2 && !selectedDifficulty) ||
                  (currentStep === 3 && !selectedNumQuestions)
                }
              >
                Next
              </button>
            )}
            {currentStep === 3 && (
              <button className="btn btn-success" onClick={handleNext}>
                Start Quiz
              </button>
            )}
          </div>
        </div>
      </div>
    </section>
  );
};

export default QuizStepper;
