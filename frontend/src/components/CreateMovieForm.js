import React, { useState, useEffect } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';

const CreateMovieForm = () => {
  const [cinemaOptions, setCinemaOptions] = useState([]);

  useEffect(() => {
    // Fetch cinema options from the backend
    const fetchCinemas = async () => {
      try {
        const response = await axios.get('http://localhost:8083/api/v1/moviesapp/cinemas');
        //const response = await axios.get('http://3.84.189.227:8083/api/v1/moviesapp/cinemas');
        setCinemaOptions(response.data);
      } catch (error) {
        console.error('Error fetching cinemas:', error);
      }
    };

    fetchCinemas();
  }, []);

  // Validation schema for Formik
  const validationSchema = Yup.object({
    title: Yup.string().required('Title is required'),
    director: Yup.string().required('Director is required'),
    image: Yup.string().required('Image URL is required'),
    ratings: Yup.string().required('Ratings are required'),
    genre: Yup.string().required('Genre is required'),
    length: Yup.number().required('Length is required'),
    releasedDate: Yup.date().required('Release date is required'),
    language: Yup.string().required('Language is required'),
    cinemas: Yup.array().min(1, 'At least one cinema must be selected').required('Cinemas are required'),
  });

  const handleSubmit = async (values, { setSubmitting, setStatus, resetForm }) => {
    setSubmitting(true);
    setStatus('');

    // Transform cinema IDs to objects
    const formattedValues = {
      ...values,
      cinemas: values.cinemas.map(id => ({ id: parseInt(id, 10) })), // Convert string IDs to integers
    };

    try {
      const response = await fetch('http://localhost:8083/api/v1/moviesapp/movies', {
      //const response = await fetch('http://3.84.189.227:8083/api/v1/moviesapp/movies', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formattedValues),
      });

      if (response.ok) {
        setStatus('Movie created successfully!');
        resetForm();
      } else {
        setStatus('Failed to create the movie.');
      }
    } catch (error) {
      console.error('Error creating movie:', error);
      setStatus('An error occurred. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="p-6 max-w-lg mx-auto bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-bold mb-4">Create Movie</h2>
      <Formik
        initialValues={{
          title: '',
          director: '',
          image: '',
          ratings: '',
          genre: '',
          length: '',
          releasedDate: '',
          language: '',
          cinemas: [], // Initialize cinemas as an empty array
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting, status }) => (
          <Form className="space-y-4">
            <div>
              <label className="block text-gray-700">Title:</label>
              <Field
                type="text"
                name="title"
                placeholder="Enter movie title"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="title" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Director:</label>
              <Field
                type="text"
                name="director"
                placeholder="Enter director's name"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="director" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Image URL:</label>
              <Field
                type="text"
                name="image"
                placeholder="Enter image URL"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="image" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Ratings:</label>
              <Field
                type="text"
                name="ratings"
                placeholder="Enter movie ratings"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="ratings" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Genre:</label>
              <Field
                type="text"
                name="genre"
                placeholder="Enter movie genre"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="genre" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Length (minutes):</label>
              <Field
                type="text"
                name="length"
                placeholder="Enter movie length"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="length" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Release Date:</label>
              <Field
                type="date"
                name="releasedDate"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="releasedDate" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Language:</label>
              <Field
                type="text"
                name="language"
                placeholder="Enter language"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="language" component="div" className="text-red-500 text-sm" />
            </div>

            <div>
              <label className="block text-gray-700">Cinemas:</label>
              <div className="grid grid-cols-2 gap-2 mt-1">
                {cinemaOptions.map(cinema => (
                  <label key={cinema.id} className="flex items-center">
                    <Field
                      type="checkbox"
                      name="cinemas"
                      value={cinema.id.toString()} // Use string value for the checkbox
                      className="mr-2"
                    />
                    {cinema.name}
                  </label>
                ))}
              </div>
              <ErrorMessage name="cinemas" component="div" className="text-red-500 text-sm" />
            </div>

            <button
              type="submit"
              disabled={isSubmitting}
              className={`w-full px-4 py-2 text-white font-semibold rounded-md ${isSubmitting ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-600 hover:bg-blue-700'}`}
            >
              {isSubmitting ? 'Submitting...' : 'Create Movie'}
            </button>
            {status && <div className="text-center text-red-500 mt-4">{status}</div>}
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default CreateMovieForm;
