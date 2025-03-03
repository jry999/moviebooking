import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';




const PrivateRoute = ({ element: Element, requiredRole, ...rest }) => {
    
    const location = useLocation();

    const userData=JSON.parse(localStorage.getItem('user'));
    let userRole = '';

    if (userData) {
        try {
            
            userRole = userData?.role || '';
            console.log(userRole);
        } catch (e) {
            console.error("Failed to get user details:", e);
        }
    }

    if (requiredRole && userRole !== requiredRole) {
        return <Navigate to="/not-authorized" state={{ from: location }} replace />;
    }

    return <Element {...rest} />;
};

export default PrivateRoute;
