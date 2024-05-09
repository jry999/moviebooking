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
public class FictionBook extends Book {
	private String genre; // Fiction books often belong to specific genres like Fantasy, Sci-Fi, etc.

}
