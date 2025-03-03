import Carousel from '../components/Carousel';
import MovieList from '../components/MovieList';
import Footer from '../components/Footer';
import CinemaList from '../components/CinemaList';


const Home = () => {
  
  return (
    <div className="min-h-screen flex flex-col">
      
      <Carousel />
      <div className="container mx-auto p-4 flex-grow">
        <h1 className="text-3xl mb-4">Movies</h1>
        <MovieList />
        <h1 className="text-3xl mb-4">Cinemas</h1>
        <CinemaList/>
      </div>
      
    </div>
  );
};

export default Home;
