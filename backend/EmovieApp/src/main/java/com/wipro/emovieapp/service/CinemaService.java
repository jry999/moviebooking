package com.wipro.emovieapp.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.wipro.emovieapp.entity.*;

public interface CinemaService {
    List<Cinema> allCinemas();

    Cinema addCinema(Cinema cinema);

	Set<Movie> getMoviesByCinemaId(Long cinemaId);

	String blockCinema(Long id,Boolean blocked);
}
