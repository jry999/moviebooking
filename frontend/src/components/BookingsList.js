import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import '../ticket.css';

const BookingsList = () => {
  const [email, setEmail] = useState('');
  const [bookings, setBookings] = useState([]);
  const [movieDetails, setMovieDetails] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const location = useLocation();

  const fetchMovieDetails = async (movieId) => {
    try {
      const response = await fetch(`http://localhost:8083/api/v1/moviesapp/movies/${movieId}`);
      //const response = await fetch(`http://3.84.189.227:8083/api/v1/moviesapp/movies/${movieId}`);
      if (response.ok) {
        const movieData = await response.json();
        return movieData;
      } else {
        console.error('Failed to fetch movie details.');
        return null;
      }
    } catch (error) {
      console.error('Error fetching movie details:', error);
      return null;
    }
  };

  const handleFetchBookings = async () => {
    setLoading(true);
    setError('');
    // try {
    //   const response = await fetch(
    //     `http://3.84.189.227:8083/api/v1/moviesapp/bookings?email=${email}`
    //   );
      try {
        const response = await fetch(
          `http://localhost:8083/api/v1/moviesapp/bookings?email=${email}`
        );
      if (response.ok) {
        const data = await response.json();

        // Sort bookings by date (latest first)
        const sortedData = data.sort((a, b) => new Date(b.date) - new Date(a.date));
        setBookings(sortedData);

        // Fetch movie details for each booking
        const movies = {};
        for (const booking of sortedData) {
          if (!movies[booking.movieId]) {
            const movie = await fetchMovieDetails(booking.movieId);
            if (movie) {
              movies[booking.movieId] = movie;
            }
          }
        }
        setMovieDetails(movies);
      } else {
        alert('Failed to fetch bookings.');
      }
    } catch (error) {
      console.error('Error fetching bookings:', error);
      setError('An error occurred while fetching bookings.');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (bookingId) => {
    const confirmCancel = window.confirm('Are you sure you want to cancel this booking?');
    if (!confirmCancel) return;

    try {
      const response = await fetch(
        `http://localhost:8083/api/v1/moviesapp/booking/${bookingId}?cancel=true`,
        { method: 'PUT', mode: 'cors' }
      );
      // const response = await fetch(
      //   `http://3.84.189.227:8083/api/v1/moviesapp/booking/${bookingId}?cancel=true`,
      //   { method: 'PUT', mode: 'cors' }
      // );

      if (response.ok) {
        alert('Booking cancelled successfully.');
        handleFetchBookings(); // Fetch bookings again after cancellation
      } else {
        alert('Failed to cancel booking.');
      }
    } catch (error) {
      console.error('Error cancelling booking:', error);
      alert('An error occurred while cancelling the booking.');
    }
  };

  useEffect(() => {
    if (location?.state?.user?.email) {
      setEmail(location.state.user.email);
      handleFetchBookings();
    }
  }, [location]);

  return (
    <div className="p-4">
      <h2 className="text-2xl font-semibold mb-4">Your Bookings</h2>
      <div className="mb-4">
        <input
          type="text"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Enter your email"
          className="p-2 border border-gray-300 rounded-md w-full mb-2"
        />
        <button
          onClick={handleFetchBookings}
          disabled={loading}
          className={`bg-blue-500 text-white px-4 py-2 rounded-md ${loading ? 'bg-blue-300' : 'hover:bg-blue-600'}`}
        >
          {loading ? 'Finding Bookings...' : 'Find Bookings'}
        </button>
      </div>

      {error && <p className="text-red-500 mb-4">{error}</p>}
      {bookings.length === 0 && !loading && <p className="text-sm text-gray-500">No bookings found.</p>}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {bookings.map((booking) => {
          const movie = movieDetails[booking.movieId] || {};
          const cinema = movie.cinemas?.find(cinema => cinema.id === booking.cinemaId) || {};
          return (
            <div
              key={booking.id}
              className="border border-gray-300 p-4 rounded-lg shadow-lg bg-white relative ticket"
            >
              <div className="ticket-content">
                <div className="flex mb-4">
                  <img
                    src={movie.image || 'default-image-url.jpg'}
                    alt={movie.title || 'Movie Image'}
                    className="w-24 h-36 object-cover mr-4"
                  />
                  <div>
                    <h3 className="text-lg font-semibold">
                      {movie.title || 'Loading...'}
                    </h3>
                    <span className="bg-green-200 text-green-800 text-sm px-2 py-1 rounded-full">
                      {booking.bookingStatus}
                    </span>
                  </div>
                </div>
                <p className="text-sm mb-2">
                  <strong>Cinema:</strong> {cinema.name || 'Loading...'}
                </p>
                <p className="text-sm mb-2">
                  <strong>Date:</strong> {new Date(booking.date).toLocaleDateString()}
                </p>
                <p className="text-sm mb-2">
                  <strong>Seats:</strong> {booking.seats}
                </p>

                {booking.bookingStatus !== 'cancelled' && (
                  <button
                    onClick={() => handleCancel(booking.id)}
                    className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 mt-2 block"
                  >
                    Cancel Booking
                  </button>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default BookingsList;
