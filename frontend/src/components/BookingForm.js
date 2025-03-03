import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';

const BookingForm = ({ movie }) => {
  const navigate = useNavigate();
  const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')));
  const [selectedCinemaName, setSelectedCinemaName] = useState('');

  // Validation schema for Formik
  const validationSchema = Yup.object({
    name: Yup.string().required('Name is required'),
    email: Yup.string().email('Invalid email format').required('Email is required'),
    seats: Yup.number()
      .min(1, `You must book at least 1 seat`)
      .max(100, `You can book up to 100 seats`)
      .required('Seats are required'),
    date: Yup.date().required('Date is required'),
    cinemaId: Yup.number().required('Cinema selection is required'),
  });

  const handleSubmit = async (values, { setSubmitting, setStatus }) => {
    console.log("entered");
    if (!JSON.parse(localStorage.getItem('user'))?.email) {
      console.log("entered2");
      alert("Kindly login to book");
      return;
    }
    if (JSON.parse(localStorage.getItem('user'))?.role === "admin") {
      console.log("entered2");
      alert("Login as a user to book");
      return;
    }
    setSubmitting(true);
    setStatus('');

    const bookingData = { ...values, movieId: movie.id };
    console.log("bk data", bookingData);

    try {
      // const response = await fetch('http://3.84.189.227:8083/api/v1/moviesapp/booking', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify(bookingData),
      // });
      const response = await fetch('http://localhost:8083/api/v1/moviesapp/booking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookingData),
      });

      if (response.ok) {
        setStatus('Booking successful!');
        navigate('/booking-history', { state: { user } });
      } else {
        setStatus('Booking failed. Please try again.');
      }
    } catch (error) {
      console.error('Error booking show:', error);
      setStatus('An error occurred. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  const handleCinemaChange = (event) => {
    const selectedCinemaId = event.target.value;
    const selectedCinema = movie.cinemas.find(cinema => cinema.id === parseInt(selectedCinemaId));
    if (selectedCinema) {
      setSelectedCinemaName(selectedCinema.name);
      localStorage.setItem('selectedCinemaName', selectedCinema.name);
      console.log('Selected Cinema:', selectedCinema.name);
    }
  };

  return (
    <>
      {console.log("user", user)}

      <Formik
        initialValues={{
          email: user?.email,
          name: user?.name,
          seats: 1,
          date: '',
          cinemaId: -1, // Initialize cinemaId as an empty string
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting, status, setFieldValue }) => (
          <Form className="space-y-4">
            <div>
              <label className="block text-gray-700">Name:</label>
              <Field
                type="text"
                name="name"
                placeholder="Enter your name"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="name" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Email:</label>
              <Field
                type="email"
                name="email"
                placeholder="Enter your email"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="email" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Seats:</label>
              <Field
                type="number"
                name="seats"
                min="1"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="seats" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Date:</label>
              <Field
                type="date"
                name="date"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="date" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Cinema:</label>
              <Field
                as="select"
                name="cinemaId"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
                onChange={(e) => {
                  setFieldValue("cinemaId", e.target.value);
                  handleCinemaChange(e);
                }} // Use onChange handler to update form value and local storage
              >
                <option value="" label="Select a cinema" />
                {movie.cinemas.map((cinema) => (
                  <option key={cinema.id} value={cinema.id} label={cinema.name} />
                ))}
              </Field>
              <ErrorMessage name="cinemaId" component="div" className="text-red-500 text-sm" />
            </div>
            <button
              type="submit"
              disabled={isSubmitting}
              className={`w-full px-4 py-2 text-white font-semibold rounded-md ${isSubmitting ? 'bg-gray-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'}`}
            >
              {isSubmitting ? 'Booking...' : 'Book Now'}
            </button>
            {status && <div className="text-center text-red-500 mt-4">{status}</div>}
          </Form>
        )}
      </Formik>
    </>
  );
};

export default BookingForm;
