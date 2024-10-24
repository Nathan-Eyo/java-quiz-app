import React from 'react';
import { Navigate } from 'react-router-dom';

// Mock authentication function to check if the user is logged in
const isAuthenticated = () => {
    return localStorage.getItem('token') ? true : false;
};

const PrivateRoute = ({ children }) => {
    return isAuthenticated() ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
