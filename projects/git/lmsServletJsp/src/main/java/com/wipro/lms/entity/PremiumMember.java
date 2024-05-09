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
public class PremiumMember extends Member {
	private int maxBooksAllowed;
	private double annualFee;
	private boolean accessToExclusiveMaterials;
}
