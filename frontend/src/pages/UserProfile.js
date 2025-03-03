import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const UserProfile = () => {
  const location = useLocation();
  const [user, setUser] = useState(location.state?.user || null);
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState(user);

  useEffect(() => {
    if (!user) {
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        setUser(JSON.parse(storedUser));
        setEditedUser(JSON.parse(storedUser));
      }
    }
  }, [user]);

  if (!user) {
    return (
      <div className="container mx-auto p-6 text-center">
        <p className="text-red-500 font-semibold">No user data available.</p>
      </div>
    );
  }

  const handleEditToggle = () => {
    setIsEditing(!isEditing);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedUser({
      ...editedUser,
      [name]: value,
    });
  };

  const handleSaveChanges = () => {
    setUser(editedUser);
    localStorage.setItem('user', JSON.stringify(editedUser));
    setIsEditing(false);
  };

  return (
    <div className="container mx-auto p-6 bg-gray-100 rounded-lg shadow-md max-w-lg mt-8">
      <h2 className="text-3xl font-semibold text-gray-800 mb-6 text-center">User Profile</h2>
      
      <div className="flex flex-col gap-4">
        <div>
          <label className="font-medium text-gray-700">Name:</label>
          {isEditing ? (
            <input
              type="text"
              name="name"
              value={editedUser.name}
              onChange={handleInputChange}
              className="w-full p-2 mt-1 border rounded focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          ) : (
            <p className="text-lg text-gray-800">{user.name}</p>
          )}
        </div>

        <div>
          <label className="font-medium text-gray-700">Email:</label>
          {isEditing ? (
            <input
              type="email"
              name="email"
              value={editedUser.email}
              onChange={handleInputChange}
              className="w-full p-2 mt-1 border rounded focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          ) : (
            <p className="text-lg text-gray-800">{user.email}</p>
          )}
        </div>

        <div>
          <label className="font-medium text-gray-700">Role:</label>
          <p className="text-lg text-gray-800 capitalize">{user.role}</p>
        </div>
      </div>

      <div className="flex justify-center mt-6">
        {isEditing ? (
          <button
            onClick={handleSaveChanges}
            className="bg-green-500 text-white font-semibold py-2 px-4 rounded hover:bg-green-600 transition"
          >
            Save Changes
          </button>
        ) : (
          <button
            onClick={handleEditToggle}
            className="bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600 transition"
          >
            Edit Profile
          </button>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
