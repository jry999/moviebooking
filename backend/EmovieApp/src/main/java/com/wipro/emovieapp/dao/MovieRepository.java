package com.wipro.emovieapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.emovieapp.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
