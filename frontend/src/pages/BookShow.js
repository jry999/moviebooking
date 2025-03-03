import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const BookShow = ({ movieId, cinemaId }) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [seats, setSeats] = useState(1);
  const [date, setDate] = useState('');
  const [availableSeats, setAvailableSeats] = useState(100);
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAvailableSeats = async () => {
      const availableSeatsFromAPI = 100; // Replace with an actual API call
      setAvailableSeats(availableSeatsFromAPI);
    };

    fetchAvailableSeats();
  }, [cinemaId, movieId]);

  const handleBooking = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    setLoading(true);

    if (seats < 1 || seats > availableSeats) {
      setErrorMessage(`You can only book between 1 and ${availableSeats} seats.`);
      setLoading(false);
      return;
    }

    if (!date) {
      setErrorMessage('Please select a date.');
      setLoading(false);
      return;
    }

    const bookingData = { name, email, seats, date, cinemaId, movieId };

    try {
      const response = await fetch('http://localhost:8080/api/v1/moviesapp/booking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookingData),
      });

      if (response.ok) {
        setShowSuccessModal(true);
        setTimeout(() => {
          navigate('/booking-history', { state: { email } });
        }, 2000);
      } else {
        setErrorMessage('Booking failed. Please try again.');
      }
    } catch (error) {
      console.error('Error booking show:', error);
      setErrorMessage('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-lg shadow-lg">
      <h2 className="text-2xl font-bold mb-4">Book Your Show</h2>
      <p className="mb-4">Available Seats: {availableSeats - seats >= 0 ? availableSeats - seats : 0}</p>

      {errorMessage && (
        <div className="mb-4 text-red-600">
          {errorMessage}
        </div>
      )}

      <form onSubmit={handleBooking} className="space-y-4">
        <div>
          <label className="block text-gray-700">Name:</label>
          <input 
            type="text" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            required 
            placeholder="Enter your name"
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
          />
        </div>
        <div>
          <label className="block text-gray-700">Email:</label>
          <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
            placeholder="Enter your email"
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
          />
        </div>
        <div>
          <label className="block text-gray-700">Seats:</label>
          <input 
            type="number" 
            value={seats} 
            onChange={(e) => setSeats(parseInt(e.target.value) || 1)} 
            min="1" 
            max={availableSeats} 
            required 
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
          />
        </div>
        <div>
          <label className="block text-gray-700">Date:</label>
          <input 
            type="date" 
            value={date} 
            onChange={(e) => setDate(e.target.value)} 
            required 
            className="mt-1 block w-full px-4 py-2 border rounded-md focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
          />
        </div>
        <button 
          type="submit" 
          disabled={loading || seats > availableSeats}
          className={`w-full px-4 py-2 text-white font-semibold rounded-md ${loading || seats > availableSeats ? 'bg-gray-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700 focus:ring focus:ring-indigo-200 focus:ring-opacity-50'}`}
        >
          {loading ? <span className="spinner border-t-transparent border-solid animate-spin inline-block w-4 h-4 border-4 rounded-full"></span> : 'Book Now'}
        </button>
      </form>

      {showSuccessModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg text-center">
            <h3 className="text-xl font-semibold mb-2">Booking Successful!</h3>
            <p>Your seats have been booked.</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default BookShow;
