package com.wipro.emovieapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.emovieapp.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema,Long> {
}
