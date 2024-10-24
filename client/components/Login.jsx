import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';  // Axios for handling the API request
import {  Link, useNavigate } from 'react-router-dom';

const Login = () => {
    // State for storing username and password
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); 

    // Function to handle form submission
    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            // Define the login payload
            const payload = {
                username: username,
                password: password
            };

            // Make POST request to login endpoint
            const response = await axios.post('http://localhost:8080/api/auth/authenticate', payload);

            // Process response (assuming JWT token is returned)
            console.log('Login successful!', response.data);
            // For example, you can store the token in localStorage:
            localStorage.setItem('token', response.data.token);
            if (response.status === 200) {
                navigate('/');
            }

            // Redirect or update the UI on successful login
            alert('Login successful!');

        } catch (err) {
            console.error('Login failed', err);
            setError('Login failed! Please check your credentials.');
        }
    };

    return (
        <div className="container d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
            <div className="card p-4" style={{ width: '400px' }}>
                <h3 className="card-title text-center">Login</h3>
                <form onSubmit={handleLogin}>
                    <div className="form-group mb-3">
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            className="form-control"
                            id="username"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && <div className="alert alert-danger" role="alert">{error}</div>}
                    <button type="submit" className="btn btn-primary w-100">Login</button>
                </form>
                <Link to={"/register"} className="">
                    Not Registered? Register Here
                  </Link>
            </div>
        </div>
    );
};

export default Login;
