package com.wipro.lms.controller;

import java.io.IOException;

import com.wipro.lms.service.LibraryService;
import com.wipro.lms.service.impl.LibraryServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookListServlet
 */
@WebServlet(urlPatterns = "/bookList")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static LibraryService libraryService;

	public void init() throws ServletException {
		super.init();
		libraryService = new LibraryServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("books", libraryService.getBookList());
		System.out.println(libraryService.getBookList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("bookList.jsp");
		dispatcher.forward(request, response);
	}

}
