<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${user.firstName } ${user.lastName }</title>
</head>
<body>
	<h1>${user.firstName } ${user.lastName }</h1>
	<ul>
		<li>User ID: ${user.userID}</li>
		<li>First Name: ${user.firstName}</li>
		<li>Last Name: ${user.lastName}</li>
		<li>Email Address: ${user.email}</li>	 
		<li>Password: ${user.password}</li>	 
		<li>DOB: ${user.dob}</li> 
		<li>
				<c:forEach var="userRolls" items="${users.roles}">
				</c:forEach>
		</li>
	</ul>
	<a href="${user.url}&edit">edit user </a> |
	<a href="users">back to user list</a>

	<ul class="nav pull-right">
		<c:url var="logoutUrl" value="/j_spring_security_logout"/>
		<li>
			<a href="${logoutUrl}">Log out</a>
		</li>
	</ul>
	
</body>
</html>