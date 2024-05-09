package com.wipro.lms.service;

import java.sql.SQLException;
import java.util.List;

import com.wipro.lms.entity.Book;
import com.wipro.lms.entity.Member;

public interface LibraryService {

	public Member findMemberById(String Id) throws SQLException;

	public Book findBookById(String bookId);

	public Book findBookByName(String bookName);

	public void returnBook(String memberId, String bookId);
	
	public List<Book> getBookList();
}
