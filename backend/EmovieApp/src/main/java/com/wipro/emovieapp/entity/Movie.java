package com.wipro.emovieapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String director;
	private String image;
	private String ratings;
	private String genre;
	private String length;
	private LocalDate releasedDate;
	private String language;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movie_cinema", joinColumns = {
			@JoinColumn(name = "movie_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cinema_Id", referencedColumnName = "id") })
	private Set<Cinema> cinemas;

}