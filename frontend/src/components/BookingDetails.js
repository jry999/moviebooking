// import React, { useState, useEffect } from 'react';
// import { useParams, useNavigate } from 'react-router-dom';

// const BookingDetails = () => {
//   const { bookingId } = useParams();
//   const [booking, setBooking] = useState(null);
//   const navigate = useNavigate();

//   useEffect(() => {
//     console.log("booking id",bookingId);
    
//     fetchBookingDetails();
//   }, [bookingId]);

//   const fetchBookingDetails = async () => {
//     try {
//       const response = await fetch(`http://localhost:8080/api/v1/moviesapp/bookings?bookingId=${bookingId}`);
//       if (response.ok) {
//         const data = await response.json();
//         setBooking(data);
//       } else {
//         alert('Failed to fetch booking details.');
//       }
//     } catch (error) {
//       console.error('Error fetching booking details:', error);
//     }
//   };

//   const cancelBooking = async () => {
//     try {
//       const response = await fetch(`http://localhost:8080/api/v1/moviesapp/booking/${bookingId}?cancel=true`, {
//         method: 'PUT',
//       });

//       if (response.ok) {
//         alert('Booking canceled successfully!');
//         navigate('/bookings');
//       } else {
//         alert('Failed to cancel booking.');
//       }
//     } catch (error) {
//       console.error('Error canceling booking:', error);
//     }
//   };

//   if (!booking) return <p>Loading booking details...</p>;

//   return (
//     <div>
//       <h2>Booking Details</h2>
//       <p>Movie: {booking.movieTitle}</p>
//       <p>Cinema: {booking.cinemaName}</p>
//       <p>Seats: {booking.seats}</p>
//       <p>Booking ID: {booking.bookingId}</p>
//       <p>Email: {booking.email}</p>
//       <button onClick={cancelBooking}>Cancel Booking</button>
//     </div>
//   );
// };

// export default BookingDetails;
