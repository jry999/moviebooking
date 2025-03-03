package com.wipro.emovieapp.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wipro.emovieapp.dao.BookingRepository;
import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Booking;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;
import com.wipro.emovieapp.service.BookingService;
import com.wipro.emovieapp.service.impl.BookingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private MovieRepository moviesRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private Cinema cinema;
    private Movie movie;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cinema = new Cinema(1L, "Cinema1", "Address1", 3, 100, "Facilities", false, new HashSet<>());
        movie = new Movie(1L, "Movie1", "Director1", "image1.jpg", "4.5", "Action", "2 hours", LocalDate.now(), "English", new HashSet<>(Collections.singletonList(cinema)));
        booking = new Booking(1L, "John Doe", "john@example.com", 1L, 1L, 2, LocalDate.now(), "PENDING");
    }

    @Test
    public void testCreateBookingSuccess() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(moviesRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(booking);

        assertNotNull(createdBooking);
        assertEquals("booked", createdBooking.getBookingStatus());
        verify(cinemaRepository, times(1)).save(cinema);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    public void testCreateBookingFailure_CinemaOrMovieNotFound() {
        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());
        when(moviesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> bookingService.createBooking(booking));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    public void testRetrieveByEmail() {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findByEmail("john@example.com")).thenReturn(bookings);

        List<Booking> retrievedBookings = bookingService.retrieveByEmail("john@example.com");

        assertNotNull(retrievedBookings);
        assertEquals(1, retrievedBookings.size());
        verify(bookingRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    public void testRetrieveByBkIdSuccess() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking retrievedBooking = bookingService.retrieveByBkId(1L);

        assertNotNull(retrievedBooking);
        assertEquals(1L, retrievedBooking.getId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveByBkIdFailure() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> bookingService.retrieveByBkId(1L));
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testCancelBookingSuccess() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking canceledBooking = bookingService.cancelBk(1L, "cancelled");

        assertNotNull(canceledBooking);
        assertEquals("cancelled", canceledBooking.getBookingStatus());
        verify(cinemaRepository, times(1)).save(cinema);
        verify(bookingRepository, times(1)).save(booking);
    }

    public Booking cancelBk(Long bookingId, String cancel) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new MovieNotFoundException("Booking not found"));
        Cinema cinema = cinemaRepository.findById(booking.getCinemaId())
                .orElseThrow(() -> new MovieNotFoundException("Cinema not found"));

        booking.setBookingStatus("cancelled");
        cinema.setTotalSeats(cinema.getTotalSeats() + booking.getSeats());
        cinemaRepository.save(cinema);
        return bookingRepository.save(booking);
    }

}
