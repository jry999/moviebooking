import React, { useState } from 'react';
import axios from 'axios';

const AuthModal = ({ type, closeModal, onSuccess }) => {
  const [activeTab, setActiveTab] = useState(type);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [role, setRole] = useState('user'); // State for role
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const endpoint = activeTab === 'login'
      ? 'http://localhost:8083/api/v1/user/login'
      : 'http://localhost:8083/api/v1/user/register';
    // const endpoint = activeTab === 'login'
    //   ? 'http://3.84.189.227:8083/api/v1/user/login'
    //   : 'http://3.84.189.227:8083/api/v1/user/register';

    const payload = activeTab === 'login'
      ? { email, password }
      : { name, email, password, role }; // Include role in the signup payload

    try {
      const response = await axios.post(endpoint, payload);

      if (response.status === 200) {
        const userData = response.data; // Assuming the backend returns user data
        console.log('Success:', userData);
        localStorage.setItem('user', JSON.stringify(userData)); // Store user data in localStorage
        onSuccess(userData); // Pass the user data back to the parent
        closeModal(); // Close the modal
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        // User not found
        setError('User not found. Please check your email and password.');
      } else {
        setError(error.response ? error.response.data.message : 'An error occurred');
      }
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white p-8 rounded shadow-lg z-50 w-full max-w-md">
        <div className="flex justify-between mb-4">
          <button
            onClick={() => setActiveTab('login')}
            className={`w-1/2 py-2 ${activeTab === 'login' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
          >
            Sign In
          </button>
          <button
            onClick={() => setActiveTab('signup')}
            className={`w-1/2 py-2 ${activeTab === 'signup' ? 'bg-green-500 text-white' : 'bg-gray-200'}`}
          >
            Sign Up
          </button>
        </div>
        <h2 className="text-2xl mb-4">{activeTab === 'login' ? 'Sign In' : 'Sign Up'}</h2>
        {error && <div className="mb-4 text-red-500">{error}</div>}
        <form onSubmit={handleSubmit}>
          {activeTab === 'signup' && (
            <>
              <div className="mb-4">
                <label className="block mb-2 text-sm font-medium text-gray-700">Name</label>
                <input 
                  type="text" 
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="w-full p-2 border rounded text-black"
                />
              </div>
              <div className="mb-4">
                <label className="block mb-2 text-sm font-medium text-gray-700">Role</label>
                <select 
                  value={role}
                  onChange={(e) => setRole(e.target.value)}
                  className="w-full p-2 border rounded text-black"
                >
                  <option value="user">User</option>
                  <option value="admin">Admin</option>
                </select>
              </div>
            </>
          )}
          <div className="mb-4">
            <label className="block mb-2 text-sm font-medium text-gray-700">Email</label>
            <input 
              type="email" 
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-2 border rounded text-black"
            />
          </div>
          <div className="mb-4">
            <label className="block mb-2 text-sm font-medium text-gray-700">Password</label>
            <input 
              type="password" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-2 border rounded text-black"
            />
          </div>
          <div className="flex justify-between items-center">
            <button 
              type="button" 
              onClick={closeModal}
              className="bg-red-500 text-white px-4 py-2 rounded"
            >
              Cancel
            </button>
            <button 
              type="submit" 
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              {activeTab === 'login' ? 'Sign In' : 'Sign Up'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AuthModal;
