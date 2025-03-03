import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import BookingForm from '../components/BookingForm';
import axios from 'axios';

// Function to fetch movie by ID from the backend
const fetchMovieById = async (id) => {
  try {
    const response = await axios.get(`http://localhost:8083/api/v1/moviesapp/movies/${id}`);
    //const response = await axios.get(`http://3.84.189.227:8083/api/v1/moviesapp/movies/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching movie by ID:', error);
    throw error; // rethrow the error so that the calling component can handle it
  }
};

const Movie = () => {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const getMovie = async () => {
      try {
        const data = await fetchMovieById(id);
        setMovie(data);

        // Store only the current movie in local storage
        localStorage.setItem('selectedMovie', JSON.stringify(data));
        console.log('movie details',localStorage.getItem('selectedMovie'));

      } catch (error) {
        setError('Failed to fetch movie details.');
      } finally {
        setLoading(false);
      }
    };

    getMovie();
  }, [id]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="p-4">
      {movie ? <BookingForm movie={movie} /> : 'Movie not found.'}
    </div>
  );
};

export default Movie;
