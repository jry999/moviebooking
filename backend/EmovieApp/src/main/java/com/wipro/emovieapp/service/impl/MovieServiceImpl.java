package com.wipro.emovieapp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;
import com.wipro.emovieapp.service.MovieService;

import jakarta.transaction.Transactional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository moviesRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Movie> allMovies() {
        return moviesRepository.findAll();
    }

    @Transactional
    public Set<Cinema> getCinemasByMovieId(Long movieId) {
        Movie movie = moviesRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        //System.out.println(movie.getCinemas());
        return  movie.getCinemas() ;
    }

    @Transactional
	public Movie addMovie(Movie movie) {
		// Validate and manage cinemas
		Set<Cinema> cinemas = movie.getCinemas().stream().map(cinema -> {
			Cinema cine = cinemaRepository.findById(cinema.getId())
					.orElseThrow(() -> new MovieNotFoundException("Cinema not found"));

			if (cine.isBlocked()) {
				throw new MovieNotFoundException("Cinema is blocked and cannot be added with a movie");
			}

			return cine;
		}).collect(Collectors.toSet());

		// Set the managed cinemas back to the movie
		movie.setCinemas(cinemas);

		// Save the movie with the associated cinemas
		return moviesRepository.save(movie);
	}

	@Override
	public Movie getOneMovie(Long id) {
		Movie movie=moviesRepository.findById(id).orElseThrow(()->new MovieNotFoundException("No movies found"));
		return movie;
	}
    
}


