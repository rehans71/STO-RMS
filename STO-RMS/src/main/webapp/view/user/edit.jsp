<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>edit user</title>
</head>
<body>
	<h1>edit user</h1>
	<form action="user" method="post">
		<input type="hidden" name="edit"> <input type="hidden"
			name="id" value="${user.id}">
		<ul>
			<li>UserID: <input type="text" name="userID" value="${user.userID}"></li>
  			<li>First Name: <input type="text" name="firstName" value="${user.firstName}"></li>
			<li>Last Name: <input type="text" name="lastName" value="${user.lastName}"></li>
			<li>Password: <input type="text" name="password" value="${user.password}"></li>
			<li>Email: <input type="text" name="email" value="${user.email}"></li>
			<li>DOB: <input type="text" name="dob" value="${user.dob}"></li>
			<li>Roles: <input type="text" name="roles" value="${user.roles}"></li>
		</ul>
		<input type="submit" value="edit">
	</form>
	<form action="user" method="post">
		<input type="hidden" name="delete"> <input type="hidden"
			name="id" value="${user.id}"> <input type="submit"
			value="delete">
	</form>
	<a href="users">back to user list</a>

</body>
</html>