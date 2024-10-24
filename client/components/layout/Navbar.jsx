import React from "react";
import { NavLink, useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();

    // Logout function to clear token and redirect to login page
    const handleLogout = () => {
        // Clear the token from localStorage or sessionStorage
        localStorage.removeItem('authToken');
        localStorage.removeItem('userDetails'); // If you store user details, clear them too

        // Redirect to the login page
        navigate('/login');
    };
  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary px-5 shadow sticky-top">
      <div className="container-fluid">
        <NavLink className="navbar-brand" to={"/"}>
          Online Quiz App
        </NavLink>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to={"/admin"}>
                Admin
              </NavLink>
            </li>

            <li className="nav-item">
              <NavLink className="nav-link" to={"/quiz-stepper"}>
                Take Quiz
              </NavLink>
            </li>
            <li>
            <button className="btn btn-danger" onClick={handleLogout}>Logout</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
