<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contacts</title>
</head>
<body>

	<h1> Welcome ${currentUser}</h1>
	<h2>users</h2>
	<ul>
		<c:forEach var="user" items="${users}">
			<li><a href="${user.url}">${user.firstName} ${user.lastName}</a></li>
		</c:forEach>
	</ul>
<!--  	<a href="person?add">add a new person</a> | <a href="company?add">add a new company</a> -->
	
	<li> 
		<a href="user?add">add a new user</a>
	</li>
	
		<ul class="nav pull-right">
		<c:url var="logoutUrl" value="/j_spring_security_logout"/>
		<li>
			<a href="${logoutUrl}">Log out</a>
		</li>
	</ul>
	
</body>
</html>