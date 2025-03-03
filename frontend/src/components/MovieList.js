import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const MovieList = () => {
  const [movies, setMovies] = useState([]);
  const [favorites, setFavorites] = useState([]);
  const [hoveredMovieCinemas, setHoveredMovieCinemas] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch movies from the backend
    const fetchMovies = async () => {
      try {
        const response = await axios.get('http://localhost:8083/api/v1/moviesapp/movies');//3.84.189.227
        //const response = await axios.get('http://3.84.189.227:8083/api/v1/moviesapp/movies');
        setMovies(response.data); // Assuming the backend returns an array of movies
      } catch (err) {
        setError('Failed to fetch movies.');
        console.error(err);
      }
    };

    fetchMovies();
  }, []); // Empty dependency array to run this effect only once on mount

  const fetchCinemasForMovie = async (movieId) => {
    try {
      const response = await axios.get(`http://localhost:8083/api/v1/moviesapp/movies/${movieId}/cinemas`);
      //const response = await axios.get(`http://3.84.189.227:8083/api/v1/moviesapp/movies/${movieId}/cinemas`);
      setHoveredMovieCinemas((prev) => ({
        ...prev,
        [movieId]: response.data, // Assuming the backend returns an array of cinemas
      }));
    } catch (err) {
      console.error(`Failed to fetch cinemas for movie ${movieId}.`, err);
    }
  };

  const toggleFavorite = (id) => {
    setFavorites((prevFavorites) => 
      prevFavorites.includes(id) 
        ? prevFavorites.filter(favId => favId !== id) 
        : [...prevFavorites, id]
    );
  };

  const handleMouseEnter = (movieId) => {
    if (!hoveredMovieCinemas[movieId]) {
      fetchCinemasForMovie(movieId);
    }
  };

  const handleMouseLeave = (movieId) => {
    // Remove cinema information when mouse leaves the movie card
    setHoveredMovieCinemas((prev) => {
      const updated = { ...prev };
      delete updated[movieId];
      return updated;
    });
  };

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div id="movies-section" className="grid grid-cols-4 gap-4">
      {movies.map(movie => (
        <div 
          key={movie.id} 
          className={`border p-4 relative transition transform hover:scale-105 ${
            favorites.includes(movie.id) ? 'bg-yellow-100' : 'bg-white'
          }`}
          onMouseEnter={() => handleMouseEnter(movie.id)}
          onMouseLeave={() => handleMouseLeave(movie.id)}
        >

          <Link to={`/movie/${movie.id}`} className="block">
            <img src={movie.image} alt={movie.title} className="w-full mb-4" />
            <h2 className="text-xl mb-2">{movie.title}</h2>
            <p>Genre: {movie.genre}</p>
            <p>Director: {movie.director}</p>
            <p>ReleasedDate: {movie.releasedDate}</p>
            <p>Language: {movie.language}</p>
            <p>Ratings: {movie.ratings}</p>
            <p>Length: {movie.length}</p>
          </Link>
          <button 
            onClick={() => toggleFavorite(movie.id)} 
            className={`absolute top-2 right-2 p-2 rounded-full ${
              favorites.includes(movie.id) ? 'bg-red-500 text-white' : 'bg-gray-300 text-black'
            }`}
          >
            {favorites.includes(movie.id) ? '★' : '☆'}
          </button>
          {hoveredMovieCinemas[movie.id] && (
            <div className="absolute bottom-0 left-0 w-full bg-white bg-opacity-90 p-2 text-sm">
              <h3 className="font-bold mb-1">Cinemas:</h3>
              <ul>
                {hoveredMovieCinemas[movie.id].map(cinema => (
                  <li key={cinema.id}>{cinema.name} - {cinema.location}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default MovieList;
