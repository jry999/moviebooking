package com.wipro.emovieapp.service;

import java.util.List;
import java.util.Set;

import com.wipro.emovieapp.entity.*;


public interface MovieService {
    List<Movie> allMovies();
    Movie addMovie(Movie movie);
	Set<Cinema> getCinemasByMovieId(Long movieId);
	Movie getOneMovie(Long id);
}
