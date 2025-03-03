// src/App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Movie from './pages/Movie';
import BookShow from './pages/BookShow';
import UserProfile from './pages/UserProfile';
import Header from './components/Header';
import BookingsList from './components/BookingsList';
import PrivateRoute from './components/PrivateRoute';
import CreateCinemaForm from './components/CreateCinemaForm';
import CreateMovieForm from './components/CreateMovieForm';
import Footer from './components/Footer';
const App = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route path="/movie/:id" element={<Movie/>} />
        <Route path="/booking/:id" element={<BookShow/>} />
        <Route path="/profile" element={<UserProfile/>} />
        <Route path="/booking-history" element={<PrivateRoute element={BookingsList} requiredRole="user" />}/>
        <Route path="/create-movie" element={<PrivateRoute element={CreateMovieForm} requiredRole="admin" />}/>
        <Route path="/create-cinema" element={<PrivateRoute element={CreateCinemaForm} requiredRole="admin" />}/>
        
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;


