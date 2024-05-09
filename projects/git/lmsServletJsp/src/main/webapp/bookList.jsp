<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.wipro.lms.entity.Book"%>
<%@ include file="header.jsp"%>
<h2>List of Books</h2>
<form action="findMember" class="form-group row my-4">
	<div class="col-sm-8">
		<input type="text" name="memberId"
			placeholder="Enter book Id or book Name B001" class="form-control">
	</div>
	<div class="col-sm-4">
		<input type="submit" class="btn btn-primary" value="Find Book">
	</div>
</form>
<div class="row">
	<%
	List<Book> bookList = (List<Book>) request.getAttribute("books");
	for (Book book : bookList) {
	%>
	<div class="col-lg-4 col-md-6 col-sm-12 mb-4">
		<div class="card shadow-sm">
			<div class="card-body">
				<h5 class="card-title"><%=book.getBook_name()%></h5>
				<h6 class="card-subtitle mb-1 text-muted">
					Book ID:
					<%=book.getBook_id()%></h6>
				<p class="card-text">
					Author:
					<%=book.getBook_authors()%></p>
				<p class="card-text">
					Publisher:
					<%=book.getBook_publisher()%></p>
				<p class="card-text">
					Available:
					<%=book.getBook_available()%></p>
				<p class="card-text">
					Publish Year:
					<%=book.getBook_publication()%></p>
				<p class="card-text">
					Category:
					<%=book.getBook_category()%></p>
				<p class="card-text">
					Language:
					<%=book.getBook_language()%></p>
				<p class="card-text">
					Status:
					<%=book.getBook_status()%></p>
				<p class="card-text">
					NoOfCopies:
					<%= String.valueOf(book.getNoOfCopies()) %></p>
			</div>
		</div>
	</div>
	<%
	}
	%>
</div>
<%@ include file="footer.jsp"%>
