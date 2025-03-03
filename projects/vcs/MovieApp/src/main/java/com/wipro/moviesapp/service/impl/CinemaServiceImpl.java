package com.wipro.moviesapp.service.impl;

import com.wipro.moviesapp.dao.MovieRepository;
import com.wipro.moviesapp.model.Movies;
import com.wipro.moviesapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepo;
    public List<Movies> allMovies() {
        return null;
    }


    public Movies addMovie(Movies movie) {
        return movieRepo.save(movie);
    }
}
