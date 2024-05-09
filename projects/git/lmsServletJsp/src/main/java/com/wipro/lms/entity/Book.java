package com.wipro.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class Book {

	private String book_id;
	private String book_name;
	private String book_authors;
	private String book_publisher;
	private String book_available;
	private String book_publication;
	private String book_category;
	private String book_language;
	private String book_status;
	private int NoOfCopies;
}
