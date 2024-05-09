package com.wipro.lms.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.wipro.lms.entity.Member;
import com.wipro.lms.service.LibraryService;
import com.wipro.lms.service.impl.LibraryServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/findMember")
public class FindMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link LibraryService} for the library service implementation
	 */
	private static LibraryService libraryService;

	/**
	 * In this method only once the {@link LibraryServiceImpl} instance get created
	 */
	public void init() throws ServletException {
		super.init();
		libraryService = new LibraryServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		try {
			Member member = libraryService.findMemberById(memberId);
			// Set the member object as an attribute of the request
			String userNotFound = "user Not found with giver Member Id :: ";
			if (member != null) {
				request.setAttribute("member", member);
			} else {
				/**
				 * If No member found than we send this attribute
				 */
				request.setAttribute("notFound", userNotFound+ memberId);
			}
			/**
			 * after finding the member details we are sending the to the findmember.jsp
			 * page
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("findMembers.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
