// src/components/CreateCinemaForm.js
import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const CreateCinemaForm = () => {
  // Validation schema for Formik
  const validationSchema = Yup.object({
    name: Yup.string().required('Name is required'),
    address: Yup.string().required('Address is required'),
    totalScreens: Yup.number()
      .min(1, 'There must be at least 1 screen')
      .required('Total screens is required'),
    totalSeats: Yup.number()
      .min(1, 'There must be at least 1 seat')
      .required('Total seats is required'),
    facilities: Yup.string().required('Facilities are required'),
    blocked: Yup.boolean(),
  });

  const handleSubmit = async (values, { setSubmitting, resetForm, setStatus }) => {
    try {
      const response = await fetch('http://localhost:8083/api/v1/moviesapp/cinemas',{
      //const response = await fetch('http://3.84.189.227:8083/api/v1/moviesapp/cinemas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });

      if (response.ok) {
        setStatus('Cinema created successfully!');
        resetForm();
      } else {
        setStatus('Failed to create cinema. Please try again.');
      }
    } catch (error) {
      console.error('Error creating cinema:', error);
      setStatus('An error occurred. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-4 bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-bold mb-4">Create Cinema</h2>
      <Formik
        initialValues={{
          name: '',
          address: '',
          totalScreens: '',
          totalSeats: '',
          facilities: '',
          blocked: false,
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting, status }) => (
          <Form className="space-y-4">
            <div>
              <label className="block text-gray-700">Name:</label>
              <Field
                type="text"
                name="name"
                placeholder="Enter cinema name"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="name" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Address:</label>
              <Field
                type="text"
                name="address"
                placeholder="Enter cinema address"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="address" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Total Screens:</label>
              <Field
                type="number"
                name="totalScreens"
                placeholder="Enter total number of screens"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="totalScreens" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Total Seats:</label>
              <Field
                type="number"
                name="totalSeats"
                placeholder="Enter total number of seats"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="totalSeats" component="div" className="text-red-500 text-sm" />
            </div>
            <div>
              <label className="block text-gray-700">Facilities:</label>
              <Field
                type="text"
                name="facilities"
                placeholder="Enter facilities (e.g., IMAX, 3D)"
                className="mt-1 block w-full px-4 py-2 border rounded-md"
              />
              <ErrorMessage name="facilities" component="div" className="text-red-500 text-sm" />
            </div>
            <div className="flex items-center">
              <Field
                type="checkbox"
                name="blocked"
                className="mr-2"
              />
              <label className="text-gray-700">Blocked</label>
            </div>
            <button
              type="submit"
              disabled={isSubmitting}
              className={`w-full px-4 py-2 text-white font-semibold rounded-md ${isSubmitting ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-500 hover:bg-blue-600'}`}
            >
              {isSubmitting ? 'Creating...' : 'Create Cinema'}
            </button>
            {status && <div className="text-center text-green-500 mt-4">{status}</div>}
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default CreateCinemaForm;
