<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ include file="header.jsp"%>
<h3>Welcome to the Library Management System</h3>

<div class="card">
	<div class="card-body">
		<h5 class="card-title">Manage Books</h5>
		<p class="card-text">Add, update, or remove books from the library
			catalog.</p>
		<a href="/lms/bookList" class="btn btn-primary">Manage Books</a>
	</div>
</div>

<div class="card">
	<div class="card-body">
		<h5 class="card-title">Find Members</h5>
		<p class="card-text">Browse library members and their borrowing
			history.</p>
		<a href="findMembers.jsp" class="btn btn-primary">Find Members</a>
	</div>
</div>

<%@ include file="footer.jsp"%>
