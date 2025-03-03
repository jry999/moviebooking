import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthModal from './AuthModal';
import { FaUserCircle } from 'react-icons/fa';

const Header = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalType, setModalType] = useState('login');
  const [user, setUser] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  const openModal = (type) => {
    setModalType(type);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleLoginSuccess = (userData) => {
    setUser(userData);
    closeModal();
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem('user'); // Clear user data from localStorage
    setUser(null); // Clear user data from state
    setIsDropdownOpen(false);
    navigate('/'); // Redirect to home after logout
  };

  const goToProfile = () => {
    setIsDropdownOpen(false);
    navigate('/profile', { state: { user } }); // Pass user data via state using navigate
  };
  const goToCinemaForm = () => {
    setIsDropdownOpen(false);
    navigate('/create-cinema'); 
  };
  const goToMovieForm = () => {
    setIsDropdownOpen(false);
    navigate('/create-movie'); 
  };
  const goToBookings = () => {
    setIsDropdownOpen(false);
    navigate('/booking-history', { state: { user } }); 
  };

  const scrollToMovies = (e) => {
    e.preventDefault();
    const moviesSection = document.getElementById('movies-section');
    if (moviesSection) {
      moviesSection.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <header className="bg-gray-900 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <div className="flex items-center">
          <img
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1P3ju7_u7ywWfF53LzWwH3y-zcAZL0A6KcQ&s"
            alt="Logo"
            className="h-10 w-10 rounded-full mr-4"
          />
          <Link to="/" className="text-2xl font-bold">Jerry Movies</Link>
        </div>
        <nav>
          <Link to="/" className="mr-4">Home</Link>
          <a href="#movies-section" onClick={scrollToMovies} className="mr-4">Movies</a>
          <a href="#cinemas-section" onClick={scrollToMovies} className="mr-4">Cinemas</a>
          {user ? (
            <div className="relative">
              <FaUserCircle 
                size={30} 
                className="cursor-pointer" 
                onClick={toggleDropdown} 
              />
              {isDropdownOpen && (
                <div className="absolute right-0 mt-2 py-2 w-48 bg-white rounded-lg shadow-xl z-20">
                  <button 
                    onClick={goToProfile} 
                    className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-200"
                  >
                    Profile
                  </button>
                  {user?.role==='admin'?(<><button 
                    onClick={goToCinemaForm} 
                    className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-200"
                  >
                    Create Cinema
                  </button>
                  <button 
                    onClick={goToMovieForm} 
                    className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-200"
                  >
                    Create Movie
                  </button>
                  </>):(<button 
                    onClick={goToBookings} 
                    className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-200"
                  >
                    My Bookings
                  </button>)
                  
                }
                  
                  <button 
                    onClick={handleLogout} 
                    className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-200"
                  >
                    Logout
                  </button>
                </div>
              )}
            </div>
          ) : (
            <>
              <button 
                onClick={() => openModal('login')} 
                className="mr-4 bg-blue-500 px-4 py-2 rounded"
              >
                Login
              </button>
              <button 
                onClick={() => openModal('signup')} 
                className="bg-green-500 px-4 py-2 rounded"
              >
                Signup
              </button>
            </>
          )}
        </nav>
      </div>
      {isModalOpen && (
        <AuthModal type={modalType} closeModal={closeModal} onSuccess={handleLoginSuccess} />
      )}
    </header>
  );
};

export default Header;
