import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../components/Home";
import QuizStepper from "../components/quiz/QuizStepper";
import Quiz from "../components/quiz/Quiz";
import QuizResult from "../components/quiz/QuizResult";
import GetAllQuiz from "../components/quiz/GetAllQuiz";
import AddQuestion from "../components/question/AddQuestion";
import UpdateQuestion from "../components/question/UpdateQuestion";
import Navbar from "../components/layout/Navbar";
import Admin from "../components/Admin";
import Login from "../components/Login";
import PrivateRoute from "../components/auth/PrivateRoute";
import Register from "../components/Register";

function App() {
  return (
    <main className="container mt-5 mb-5">
      <Router>
        <Navbar />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/"
            element={
              <PrivateRoute>
                <Home />
              </PrivateRoute>
            }
          />
          <Route
            path="/quiz-stepper"
            element={
              <PrivateRoute>
                <QuizStepper />
              </PrivateRoute>
            }
          />
          <Route
            path="/take-quiz"
            element={
              <PrivateRoute>
                <Quiz />
              </PrivateRoute>
            }
          />
          <Route
            path="/admin"
            element={
              <PrivateRoute>
                <Admin />
              </PrivateRoute>
            }
          />
          <Route
            path="/create-quiz"
            element={
              <PrivateRoute>
                <AddQuestion />
              </PrivateRoute>
            }
          />
          <Route
            path="/update-quiz/:id"
            element={
              <PrivateRoute>
                <UpdateQuestion />
              </PrivateRoute>
            }
          />
          <Route
            path="/all-quizzes"
            element={
              <PrivateRoute>
                <GetAllQuiz />
              </PrivateRoute>
            }
          />
          <Route
            path="/quiz-result"
            element={
              <PrivateRoute>
                <QuizResult />
              </PrivateRoute>
            }
          />
        </Routes>
      </Router>
    </main>
  );
}

export default App;
