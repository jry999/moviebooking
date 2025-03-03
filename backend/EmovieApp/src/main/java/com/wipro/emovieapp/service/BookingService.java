package com.wipro.emovieapp.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.util.MultiValueMap;

import com.wipro.emovieapp.entity.Booking;

public interface BookingService {

	List<Booking> retrieveByEmail(String email);

	Booking retrieveByBkId(Long bkId);

	Booking cancelBk(Long bookingId,String cancel);

	Booking createBooking(Booking book);

}
