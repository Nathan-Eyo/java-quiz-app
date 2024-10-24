import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';  // For redirection

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();  // For navigating to the login page

    const handleRegister = async (e) => {
        e.preventDefault();

        try {
            const payload = {
                username: username,
                password: password
            };

            // Make POST request to the registration API
            const response = await axios.post('http://localhost:8080/api/auth/register', payload);

            // Redirect to login page on successful registration
            if (response.status === 200) {
                navigate('/login');
            }
        } catch (err) {
            console.error('Registration failed', err);
            setError('Registration failed! Please try again.');
        }
    };

    return (
        <div className="container d-flex align-items-center justify-content-center" style={{ height: '100vh' }}>
            <div className="card p-4" style={{ width: '400px' }}>
                <h3 className="card-title text-center">Register</h3>
                <form onSubmit={handleRegister}>
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
                    <button type="submit" className="btn btn-primary w-100">Register</button>
                </form>
                <Link to={"/login"} className="">
                    Login Here
                  </Link>
            </div>
        </div>
    );
};

export default Register;
