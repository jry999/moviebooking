package com.wipro.moviesapp.dao;

import com.wipro.moviesapp.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movies,String> {
}
