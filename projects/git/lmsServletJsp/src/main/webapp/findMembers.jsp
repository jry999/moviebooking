<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>


<h3>Find the Library Member</h3>
<form action="findMember" class="form-group row my-4">
	<div class="col-sm-8">
		<input type="text" name="memberId"
			placeholder="Enter member Id linke M001" class="form-control">
	</div>
	<div class="col-sm-4">
		<input type="submit" class="btn btn-primary" value="Find Member">
	</div>
</form>
<p>${notFound }</p>
<c:if test="${not empty member}">
	<div class="card member-card">
		<div class="card-header">
			<i class="fas fa-user"></i> Member Details
		</div>
		<div class="card-body">
			<h5 class="card-title">
				<i>${member.firstName} ${member.lastName}</i>
			</h5>
			<p class="card-text">
				<i class="fas fa-id-card"></i><b> Member ID:</b> ${member.memberId}
			</p>
			<p class="card-text">
				<i class="fas fa-home"></i> <b>Address:</b> ${member.address}
			</p>
			<p class="card-text">
				<i class="fas fa-phone"></i> <b>Phone Number:</b>
				${member.phoneNumber}
			</p>
			<p class="card-text">
				<i class="fas fa-envelope"></i> <b>Email: </b>${member.email}</p>
			<p class="card-text">
				<i class="fas fa-users"></i> <b>Membership Type:</b>
				${member.membershipType}
			</p>
			<p class="card-text">
				<i class="fas fa-calendar-alt"></i> <b>Membership Start Date:</b>
				${member.membershipStartDate}
			</p>
			<p class="card-text">
				<i class="fas fa-calendar-times"></i> <b>Membership Expiry Date:</b>
				${member.membershipExpiryDate}
			</p>
			<p class="card-text">
				<i class="fas fa-dollar-sign"></i> <b>Fines:</b> ${member.fines}
			</p>
		</div>
	</div>
</c:if>

<%@ include file="footer.jsp"%>
