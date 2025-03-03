package com.wipro.emovieapp.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.emovieapp.entity.Booking;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.service.BookingService;
import com.wipro.emovieapp.service.CinemaService;
import com.wipro.emovieapp.service.MovieService;

@RestController
@RequestMapping("/api/v1/moviesapp")
@CrossOrigin("*")
public class MoviesController {

    @Autowired
    private MovieService moviesService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired 
    private BookingService bkService;

    
    //Movie Controller 
    
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = moviesService.allMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        
        return ResponseEntity.ok(moviesService.addMovie(movie));
    }
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getOneMovies(@PathVariable Long id) {
        Movie movie = moviesService.getOneMovie(id);
        return ResponseEntity.ok(movie);
    }

  
    @GetMapping("/movies/{movieId}/cinemas")
    public Set<Cinema> getCinemasByMovieId(@PathVariable Long movieId) {
        return moviesService.getCinemasByMovieId(movieId);
    }

   //Cinema Controller
    
    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.allCinemas();
        return ResponseEntity.ok(cinemas);
    }

    @PostMapping("/cinemas")
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) {
        
        return ResponseEntity.ok(cinemaService.addCinema(cinema));
    }
    
    @GetMapping("/cinemas/{cinemaId}/movies")
    public Set<Movie> getMoviesByCinemaId(@PathVariable Long cinemaId) {
        return cinemaService.getMoviesByCinemaId(cinemaId);
    }
    @PutMapping("/cinema/{id}")
    public ResponseEntity<String> blockCinema(@PathVariable Long id ,@RequestParam Boolean blocked){
    	return new ResponseEntity<>(cinemaService.blockCinema(id,blocked),HttpStatus.OK);
    }

    //Booking Controller
    
    @PostMapping("/booking")
    public ResponseEntity<Booking> addBooking(@RequestBody Booking book){
    	return new ResponseEntity(bkService.createBooking(book),HttpStatus.OK);
    }
    
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getByEmail(@RequestParam String email){
    	
    	return new ResponseEntity(bkService.retrieveByEmail(email),HttpStatus.OK);
    }
    
    @GetMapping("/bookingsId")
    public ResponseEntity<Booking> getByBkId(@RequestParam Long bkId){
    	
    	return new ResponseEntity(bkService.retrieveByBkId(bkId),HttpStatus.OK);
    }
    
    @PutMapping("/booking/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId, @RequestParam String cancel){
    	System.out.println(bookingId);
    	return new ResponseEntity(bkService.cancelBk(bookingId,cancel),HttpStatus.OK);
    }
    
    

}






