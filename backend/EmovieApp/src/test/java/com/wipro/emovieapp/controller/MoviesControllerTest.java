package com.wipro.emovieapp.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.wipro.emovieapp.entity.Booking;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.service.BookingService;
import com.wipro.emovieapp.service.CinemaService;
import com.wipro.emovieapp.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MoviesControllerTest {

    @Mock
    private MovieService moviesService;

    @Mock
    private CinemaService cinemaService;

    @Mock
    private BookingService bkService;

    @InjectMocks
    private MoviesController moviesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for Movie-related endpoints

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = Arrays.asList(new Movie(1L, "Movie1", "Director1", "image1.jpg", "4.5", "Action", "2 hours", LocalDate.now(), "English", null));
        when(moviesService.allMovies()).thenReturn(movies);

        ResponseEntity<List<Movie>> response = moviesController.getAllMovies();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(moviesService, times(1)).allMovies();
    }

    @Test
    public void testCreateMovie() {
        Movie movie = new Movie(1L, "Movie1", "Director1", "image1.jpg", "4.5", "Action", "2 hours", LocalDate.now(), "English", null);
        when(moviesService.addMovie(movie)).thenReturn(movie);

        ResponseEntity<Movie> response = moviesController.createMovie(movie);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
        verify(moviesService, times(1)).addMovie(movie);
    }

    @Test
    public void testGetOneMovie() {
        Movie movie = new Movie(1L, "Movie1", "Director1", "image1.jpg", "4.5", "Action", "2 hours", LocalDate.now(), "English", null);
        when(moviesService.getOneMovie(1L)).thenReturn(movie);

        ResponseEntity<Movie> response = moviesController.getOneMovies(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
        verify(moviesService, times(1)).getOneMovie(1L);
    }

    @Test
    public void testGetCinemasByMovieId() {
        Set<Cinema> cinemas = new HashSet<>(Arrays.asList(new Cinema(1L, "Cinema1", "Address1", 3, 100, "Facilities", false, null)));
        when(moviesService.getCinemasByMovieId(1L)).thenReturn(cinemas);

        Set<Cinema> response = moviesController.getCinemasByMovieId(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(moviesService, times(1)).getCinemasByMovieId(1L);
    }

    // Test cases for Cinema-related endpoints

    @Test
    public void testGetAllCinemas() {
        List<Cinema> cinemas = Arrays.asList(new Cinema(1L, "Cinema1", "Address1", 3, 100, "Facilities", false, null));
        when(cinemaService.allCinemas()).thenReturn(cinemas);

        ResponseEntity<List<Cinema>> response = moviesController.getAllCinemas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(cinemaService, times(1)).allCinemas();
    }

    @Test
    public void testCreateCinema() {
        Cinema cinema = new Cinema(1L, "Cinema1", "Address1", 3, 100, "Facilities", false, null);
        when(cinemaService.addCinema(cinema)).thenReturn(cinema);

        ResponseEntity<Cinema> response = moviesController.createCinema(cinema);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cinema, response.getBody());
        verify(cinemaService, times(1)).addCinema(cinema);
    }

    @Test
    public void testGetMoviesByCinemaId() {
        Set<Movie> movies = new HashSet<>(Arrays.asList(new Movie(1L, "Movie1", "Director1", "image1.jpg", "4.5", "Action", "2 hours", LocalDate.now(), "English", null)));
        when(cinemaService.getMoviesByCinemaId(1L)).thenReturn(movies);

        Set<Movie> response = moviesController.getMoviesByCinemaId(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(cinemaService, times(1)).getMoviesByCinemaId(1L);
    }

    @Test
    public void testBlockCinema() {
        when(cinemaService.blockCinema(1L, true)).thenReturn("Cinema blocked successfully");

        ResponseEntity<String> response = moviesController.blockCinema(1L, true);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cinema blocked successfully", response.getBody());
        verify(cinemaService, times(1)).blockCinema(1L, true);
    }

    // Test cases for Booking-related endpoints

    @Test
    public void testAddBooking() {
        Booking booking = new Booking(1L, "John Doe", "john@example.com", 1L, 1L, 2, LocalDate.now(), "CONFIRMED");
        when(bkService.createBooking(booking)).thenReturn(booking);

        ResponseEntity<Booking> response = moviesController.addBooking(booking);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bkService, times(1)).createBooking(booking);
    }

    @Test
    public void testGetByEmail() {
        List<Booking> bookings = Arrays.asList(new Booking(1L, "John Doe", "john@example.com", 1L, 1L, 2, LocalDate.now(), "CONFIRMED"));
        when(bkService.retrieveByEmail("john@example.com")).thenReturn(bookings);

        ResponseEntity<List<Booking>> response = moviesController.getByEmail("john@example.com");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(bkService, times(1)).retrieveByEmail("john@example.com");
    }

    @Test
    public void testGetByBkId() {
        Booking booking = new Booking(1L, "John Doe", "john@example.com", 1L, 1L, 2, LocalDate.now(), "CONFIRMED");
        when(bkService.retrieveByBkId(1L)).thenReturn(booking);

        ResponseEntity<Booking> response = moviesController.getByBkId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bkService, times(1)).retrieveByBkId(1L);
    }

    @Test
    public void testCancelBooking() {
        Booking booking = new Booking(1L, "John Doe", "john@example.com", 1L, 1L, 2, LocalDate.now(), "CANCELLED");
        when(bkService.cancelBk(1L, "CANCELLED")).thenReturn(booking);

        ResponseEntity<Booking> response = moviesController.cancelBooking(1L, "CANCELLED");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CANCELLED", response.getBody().getBookingStatus());
        verify(bkService, times(1)).cancelBk(1L, "CANCELLED");
    }
}
