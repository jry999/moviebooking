package com.wipro.emovieapp.service.impl;

import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private Cinema cinema;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        cinema.setId(1L);
        cinema.setName("Test Cinema");
        cinema.setBlocked(false);

        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setDirector("Test Director");
        movie.setGenre("Action");
        movie.setLanguage("English");
        movie.setReleasedDate(LocalDate.now());
        movie.setCinemas(new HashSet<>(Set.of(cinema)));
    }

    @Test
    void testAllMovies() {
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.allMovies();

        assertEquals(1, movies.size());
        assertEquals("Test Movie", movies.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetCinemasByMovieId() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Set<Cinema> cinemas = movieService.getCinemasByMovieId(1L);

        assertEquals(1, cinemas.size());
        assertTrue(cinemas.stream().anyMatch(c -> c.getName().equals("Test Cinema")));
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCinemasByMovieId_MovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getCinemasByMovieId(1L));
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testAddMovie_Success() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie savedMovie = movieService.addMovie(movie);

        assertEquals("Test Movie", savedMovie.getTitle());
        verify(cinemaRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testAddMovie_CinemaNotFound() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.addMovie(movie));
        verify(cinemaRepository, times(1)).findById(1L);
        verify(movieRepository, never()).save(movie);
    }

    @Test
    void testAddMovie_CinemaBlocked() {
        cinema.setBlocked(true);
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));

        assertThrows(MovieNotFoundException.class, () -> movieService.addMovie(movie));
        verify(cinemaRepository, times(1)).findById(1L);
        verify(movieRepository, never()).save(movie);
    }

    @Test
    void testGetOneMovie_Success() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie foundMovie = movieService.getOneMovie(1L);

        assertEquals("Test Movie", foundMovie.getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOneMovie_MovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getOneMovie(1L));
        verify(movieRepository, times(1)).findById(1L);
    }
}
