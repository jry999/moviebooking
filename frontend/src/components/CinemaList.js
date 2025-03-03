import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CinemaList = () => {
  const [cinemas, setCinemas] = useState([]);
  const [error, setError] = useState(null);
  const [hoveredCinemaId, setHoveredCinemaId] = useState(null);
  const [movies, setMovies] = useState([]);
  const [blockStatus, setBlockStatus] = useState({}); // Store the blocked status of cinemas
  const [userRole, setUserRole] = useState(null); // Store the user's role

  useEffect(() => {
    // Retrieve user role from localStorage
    const storedUser = JSON.parse(localStorage.getItem('user'));
    if (storedUser && storedUser.role) {
      setUserRole(storedUser.role);
    }

    // Fetch cinemas from the backend
    const fetchCinemas = async () => {
      try {
        const response = await axios.get('http://localhost:8083/api/v1/moviesapp/cinemas');
        //const response = await axios.get('http://3.84.189.227:8083/api/v1/moviesapp/cinemas');
        setCinemas(response.data); // Assuming the backend returns an array of cinemas

        // Initialize blockStatus based on fetched data
        const statusMap = {};
        response.data.forEach(cinema => {
          statusMap[cinema.id] = cinema.blocked;
        });
        setBlockStatus(statusMap);
      } catch (err) {
        setError('Failed to fetch cinemas.');
        console.error(err);
      }
    };

    fetchCinemas();
  }, []);

  useEffect(() => {
    // Fetch movies for the hovered cinema
    const fetchMovies = async (cinemaId) => {
      try {
        const response = await axios.get(`http://localhost:8083/api/v1/moviesapp/cinemas/${cinemaId}/movies`);
        setMovies(response.data);
      } catch (err) {
        console.error('Failed to fetch movies.', err);
      }
    };

    if (hoveredCinemaId) {
      fetchMovies(hoveredCinemaId);
    } else {
      setMovies([]); // Clear movies when not hovering
    }
  }, [hoveredCinemaId]);

  const handleBlockCinema = async (cinemaId) => {
    if (userRole !== 'admin') {
      alert('Only an admin can block or unblock a cinema.');
      return;
    }

    const currentStatus = blockStatus[cinemaId];
    try {
      const response = await axios.put(`http://localhost:8083/api/v1/moviesapp/cinema/${cinemaId}?blocked=${!currentStatus}`);
      //const response = await axios.put(`http://3.84.189.227:8083/api/v1/moviesapp/cinema/${cinemaId}?blocked=${!currentStatus}`);
      if (response.status === 200) {
        // Update block status in the UI
        setBlockStatus(prevStatus => ({
          ...prevStatus,
          [cinemaId]: !currentStatus,
        }));
        alert(`Cinema ${cinemaId} has been ${!currentStatus ? 'blocked' : 'unblocked'} successfully.`);
      }
    } catch (err) {
      console.error('Failed to update block status.', err);
      alert('Failed to update cinema block status. Please try again.');
    }
  };

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div id="cinemas-section" className="grid grid-cols-4 gap-4">
      {cinemas.map(cinema => (
        <div 
          key={cinema.id} 
          className="border p-4 bg-white shadow-lg rounded hover:bg-slate-200 transition-all duration-700 transform hover:scale-105 relative"
          onMouseEnter={() => setHoveredCinemaId(cinema.id)}
          onMouseLeave={() => setHoveredCinemaId(null)}
        >
          <h2 className="text-xl font-bold mb-2">Name: {cinema.name}</h2>
          <p className="text-gray-700">Address: {cinema.address}</p>
          <p className="text-gray-700">Screens: {cinema.totalScreens}</p>
          <p className="text-gray-700">Capacity: {cinema.totalSeats}</p>
          <p className="text-gray-700">
            Status: <span className={blockStatus[cinema.id] ? 'text-red-500' : 'text-green-500'}>{blockStatus[cinema.id] ? 'Blocked' : 'Active'}</span>
          </p>

          {hoveredCinemaId === cinema.id  && (//&& movies.length > 0 {hoveredCinemaId === cinema.id && movies.length > 0 && (
            <div className="absolute top-0 left-0 w-full h-full bg-white bg-opacity-90 p-4 rounded shadow-lg">
              <h3 className="text-lg font-bold mb-2">Movies Playing:</h3>
              <ul className="text-gray-700 mb-4">   
                {movies.map(movie => (
                  <li key={movie.id}>{movie.title}</li>
                ))}
              </ul>
              {userRole === 'admin' && (
                <button
                  onClick={() => handleBlockCinema(cinema.id)}
                  className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600"
                >
                  {blockStatus[cinema.id] ? 'Unblock' : 'Block'} Cinema
                </button>
              )}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default CinemaList;
