package com.wipro.emovieapp.service.impl;

import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;
import com.wipro.emovieapp.service.CinemaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceImplTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    private Cinema cinema;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        cinema = new Cinema();
        cinema.setId(1L);
        cinema.setName("Test Cinema");
        cinema.setAddress("123 Main St");
        cinema.setTotalScreens(5);
        cinema.setTotalSeats(100);
        cinema.setFacilities("Parking, Food Court");
        cinema.setBlocked(false);
        cinema.setMovies(new HashSet<>(Collections.singletonList(movie)));
    }

    @Test
    void testAllCinemas() {
        when(cinemaRepository.findAll()).thenReturn(List.of(cinema));

        List<Cinema> cinemas = cinemaService.allCinemas();

        assertEquals(1, cinemas.size());
        assertEquals("Test Cinema", cinemas.get(0).getName());
        verify(cinemaRepository, times(1)).findAll();
    }

    @Test
    void testGetMoviesByCinemaId() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));

        Set<Movie> movies = cinemaService.getMoviesByCinemaId(1L);

        assertEquals(1, movies.size());
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Test Movie")));
        verify(cinemaRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMoviesByCinemaId_CinemaNotFound() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> cinemaService.getMoviesByCinemaId(1L));
        verify(cinemaRepository, times(1)).findById(1L);
    }

    @Test
    void testAddCinema() {
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        Cinema savedCinema = cinemaService.addCinema(cinema);

        assertEquals("Test Cinema", savedCinema.getName());
        verify(cinemaRepository, times(1)).save(cinema);
    }

    @Test
    void testBlockCinema_Unblock() {
        cinema.setBlocked(true);

        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        String response = cinemaService.blockCinema(1L, false);

        assertEquals("Cinema is unblocked now", response);
        assertFalse(cinema.isBlocked());
        verify(cinemaRepository, times(1)).findById(1L);
        verify(cinemaRepository, times(1)).save(cinema);
    }

    @Test
    void testBlockCinema_Block() {
        cinema.setBlocked(false);

        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        String response = cinemaService.blockCinema(1L, true);

        assertEquals("Cinema is blocked now", response);
        assertTrue(cinema.isBlocked());
        verify(cinemaRepository, times(1)).findById(1L);
        verify(cinemaRepository, times(1)).save(cinema);
    }

    @Test
    void testBlockCinema_CinemaNotFound() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> cinemaService.blockCinema(1L, true));
        verify(cinemaRepository, times(1)).findById(1L);
    }
}
