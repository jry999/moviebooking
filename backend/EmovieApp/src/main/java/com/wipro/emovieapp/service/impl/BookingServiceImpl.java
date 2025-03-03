package com.wipro.emovieapp.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.emovieapp.dao.BookingRepository;
import com.wipro.emovieapp.dao.CinemaRepository;
import com.wipro.emovieapp.dao.MovieRepository;
import com.wipro.emovieapp.entity.Booking;
import com.wipro.emovieapp.entity.Cinema;
import com.wipro.emovieapp.entity.Movie;
import com.wipro.emovieapp.exception.MovieNotFoundException;
import com.wipro.emovieapp.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired 
	private BookingRepository bookingRepository;
	
	@Autowired 
	private CinemaRepository cinemaRepository;
	
	@Autowired 
	private MovieRepository moviesRepository;
	
	public Booking createBooking(Booking booking) {
		
		
		Optional<Cinema> cinemaOptional = cinemaRepository.findById(booking.getCinemaId());
        Optional<Movie> movieOptional = moviesRepository.findById(booking.getMovieId());

        if (cinemaOptional.isPresent() && movieOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            Movie movie = movieOptional.get();

            // Check if seats are available
            if(movie.getCinemas().contains(cinema) && cinema.getTotalSeats()>=booking.getSeats() && booking.getSeats()<=100 && cinema.isBlocked()==false) {
            	cinema.setTotalSeats(cinema.getTotalSeats()-booking.getSeats()); 
            	cinemaRepository.save(cinema);
            	booking.setBookingStatus("booked");
            	return bookingRepository.save(booking);
            }           
        }             
        throw new MovieNotFoundException("Cinema or Movie not found.");
        
    	}

	@Override
	public List<Booking> retrieveByEmail(String email) {
		List<Booking> bkEmail= bookingRepository.findByEmail(email);
		return bkEmail;
	}

	@Override
	public Booking retrieveByBkId(Long bkId) {
		Booking book= bookingRepository.findById(bkId).orElseThrow(()->new MovieNotFoundException("No booking Id found"));
		return book;
	}

	public Booking cancelBk(Long bookingId,String cancel) {
        Booking booking = bookingRepository.findById(bookingId).get();
        Cinema cinema = cinemaRepository.findById(booking.getCinemaId()).get();
        if (Objects.nonNull(booking) && Objects.nonNull(cinema)) {
            
            booking.setBookingStatus("cancelled");
            cinema.setTotalSeats(cinema.getTotalSeats()+booking.getSeats());
            cinemaRepository.save(cinema);
           return bookingRepository.save(booking);
        }
        
        throw new MovieNotFoundException("Movie not found");
		
    }
}
