package com.wipro.lms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.wipro.lms.service.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/libraryServlet")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
    
    public void init() throws ServletException {
    	super.init();
    	
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			 connection = DatabaseConnection.getConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(connection != null) {
			
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
