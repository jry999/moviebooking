// // src/services/api.js

// const movies = [
//     { 
//       id: 1, 
//       title: 'Inception', 
//       description: 'A mind-bending thriller', 
//       poster: 'https://via.placeholder.com/150x225', 
//       runtime: '148 min', 
//       genre: 'Action, Sci-Fi', 
//       releaseDate: '2010-07-16',
//       director: 'Christopher Nolan'
//     },
//     { 
//       id: 2, 
//       title: 'Interstellar', 
//       description: 'A journey through space and time', 
//       poster: 'https://via.placeholder.com/150x225', 
//       runtime: '169 min', 
//       genre: 'Adventure, Drama, Sci-Fi', 
//       releaseDate: '2014-11-07',
//       director: 'Christopher Nolan'
//     },
//     // Add more movies here
//   ];
  
//   export const fetchMovies = () => {
//     return new Promise(resolve => {
//       setTimeout(() => resolve(movies), 500);
//     });
//   };
  
//   export const fetchMovieById = (id) => {
//     return new Promise(resolve => {
//       const movie = movies.find(m => m.id === parseInt(id));
//       setTimeout(() => resolve(movie), 500);
//     });
//   };
  
//   export const bookTickets = (id, seats) => {
//     return new Promise(resolve => {
//       setTimeout(() => resolve({ success: true }), 500);
//     });
//   };
  