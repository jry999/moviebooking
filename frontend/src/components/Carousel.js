import React, { useState, useEffect } from 'react';
import ff7 from "../img/ff7.jpeg";
import aven from "../img/avengers.jpeg";
import batman from "../img/batman.jpg";

const Carousel = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const slides = [
    { id: 1, image: 'https://cdn.marvel.com/content/1x/avengersendgame_lob_mas_mob_01.jpg', title: 'Avengers EndGame' },
    { id: 2, image: 'https://www.pluggedin.com/wp-content/uploads/2019/12/ice-age-1024x576.jpg', title: 'Ice Age' },
    { id: 3, image: 'https://i.pinimg.com/736x/cc/16/a8/cc16a8abaa587004453323fc624a1264.jpg', title: 'Batman' },
  ];

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % slides.length);
    }, 5000);

    return () => clearInterval(interval);
  }, [slides.length]);

  const handleNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % slides.length);
  };

  const handlePrevious = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + slides.length) % slides.length);
  };

  const goToSlide = (index) => {
    setCurrentIndex(index);
  };

  return (
    <div className="relative w-full">
      <div className="carousel">
        <div className="carousel-item">
          <img src={slides[currentIndex].image} alt={slides[currentIndex].title} className="w-full" />
          <div className="carousel-caption">
            <h3 className="text-2xl">{slides[currentIndex].title}</h3>
          </div>
        </div>
      </div>
      <button onClick={handlePrevious} className="absolute left-4 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-2 rounded-full">Prev</button>
      <button onClick={handleNext} className="absolute right-4 top-1/2 transform -translate-y-1/2 bg-gray-800 text-white p-2 rounded-full">Next</button>
      <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
        {slides.map((_, index) => (
          <button
            key={index}
            onClick={() => goToSlide(index)}
            className={`w-4 h-4 rounded-full ${index === currentIndex ? 'bg-gray-800' : 'bg-gray-300'}`}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
