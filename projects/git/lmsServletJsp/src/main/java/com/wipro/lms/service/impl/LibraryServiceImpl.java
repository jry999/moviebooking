package com.wipro.lms.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wipro.lms.entity.Book;
import com.wipro.lms.entity.Member;
import com.wipro.lms.service.DatabaseConnection;
import com.wipro.lms.service.LibraryService;

public class LibraryServiceImpl implements LibraryService {

	private static Connection connection;

	static {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * @Parameter Member Id and return the member object
	 * 
	 */
	@Override
	public Member findMemberById(String id) throws SQLException {
		// Corrected the table name and used PreparedStatement to prevent SQL injection
		String selectQuery = "SELECT * FROM Members WHERE memberId = ?";
		PreparedStatement preparedStatement = null;
		Member member = null;

		if (connection != null) {
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, id); // Set the memberId parameter in the query
		}
		if (preparedStatement != null) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) { // Assuming memberId is unique, we use if instead of while
				member = new Member(); // Assuming Member class has a no-arg constructor
				member.setMemberId(resultSet.getString("memberId"));
				member.setFirstName(resultSet.getString("firstName"));
				member.setLastName(resultSet.getString("lastName"));
				member.setAddress(resultSet.getString("address"));
				member.setPhoneNumber(resultSet.getString("phoneNumber"));
				member.setEmail(resultSet.getString("email"));
				member.setMembershipType(resultSet.getString("membershipType"));
				member.setMembershipStartDate(resultSet.getDate("membershipStartDate"));
				member.setMembershipExpiryDate(resultSet.getDate("membershipExpiryDate"));
				member.setFines(resultSet.getDouble("fines"));

			}
		}
		return member; // Returns the member object if found, or null if not
	}

	@Override
	public Book findBookById(String bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book findBookByName(String bookName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnBook(String memberId, String bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getBookList() {
		String book_fetch_query = "Select * from books";
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;
		Statement statement = null;
		if (connection != null) {
			try {
				statement = connection.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				ResultSet resultSet = statement.executeQuery(book_fetch_query);
				while (resultSet.next()) {
					book = new Book();
					book.setBook_id(resultSet.getString("bookId"));
					book.setBook_name(resultSet.getString("bookName"));
					book.setBook_publisher(resultSet.getString("bookPublisher"));
					book.setBook_available(resultSet.getString("bookAvailable"));
					book.setBook_publication(resultSet.getString("publicationYear"));
					book.setBook_category(resultSet.getString("bookCategory"));
					book.setBook_language(resultSet.getString("bookLanguage"));
					book.setBook_status(resultSet.getString("bookStatus"));
					book.setBook_authors(resultSet.getString("author"));
					book.setNoOfCopies(resultSet.getInt("NoOfCopies"));
					bookList.add(book);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookList;
	}

}
