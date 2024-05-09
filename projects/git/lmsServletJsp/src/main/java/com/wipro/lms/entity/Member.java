package com.wipro.lms.entity;

import java.util.Date;
import java.util.List;

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
public class Member {
    private String memberId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String membershipType;
    private Date membershipStartDate;
    private Date membershipExpiryDate;
    private List<Book> booksCurrentlyBorrowed;
    private List<Book> booksBorrowedHistory;
    private double fines;
    
    

}

