package com.wipro.emovieapp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.service.CinemaService;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository moviesRepository;

    public List<Cinema> allCinemas() {
        return cinemaRepository.findAll();
    }

    public Set<Movie> getMoviesByCinemaId(Long cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new MovieNotFoundException("Cinema not found"));
        return cinema.getMovies();
    }

    public Cinema addCinema(Cinema cinema) {
//    	Set<Movie> movieId=cinema.getMovies().stream().collect(Collectors.toSet());
//    	cinema.setMovies(movieId);
        return cinemaRepository.save(cinema);
    }

	@Override
	public String blockCinema(Long cinemaID,Boolean blocked) {
		Cinema cine = cinemaRepository.findById(cinemaID).orElseThrow(() -> new MovieNotFoundException("Cinema not found"));
		String msg="";
		if(cine.isBlocked()) {
			cine.setBlocked(false);	
			msg="Cinema is unblocked now";
		}
		else {
			cine.setBlocked(true);
			msg="Cinema is blocked now";
		}
		cinemaRepository.save(cine);
		return msg;
		
		
	}
}
