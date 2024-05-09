package com.wipro.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NonFictionBook extends Book {
	private String field; // Non-fiction books are often categorized by field, e.g., History, Science.

}
