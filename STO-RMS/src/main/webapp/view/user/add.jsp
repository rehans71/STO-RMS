<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>add user</title>
</head>
<body>
	<h1>add user</h1>
	<form action="user" method="post">
		<input type="hidden" name="add">
		<ul>
			<li>User ID: <input type="text" name="userID"></li>
			<li>First Name: <input type="text" name="firstName"></li>
			<li>Last Name: <input type="text" name="lastName"></li>
			<li>Password: <input type="text" name="password"></li>
			<li>Email: <input type="text" name="email"></li>
			<li>DOB: <input type="text" name="dob"></li>
			<li>Roles: <input type="text" name="roles"></li>
			<li>Status: <input type="text" name="status"></li>
		</ul>
		<input type="submit" value="add">
	</form>
	<a href="users">back to user list</a>

</body>
</html>