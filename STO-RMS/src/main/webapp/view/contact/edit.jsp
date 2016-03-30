<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>edit contact</title>
</head>
<body>
	<h1>edit contact</h1>
	<form action="contact" method="post">
		<input type="hidden" name="edit"> <input type="hidden"
			name="id" value="${contact.id}">
		<ul>
			<c:set var="address" value="${contact.address}" />
			<li>name: <input type="text" name="name" value="${contact.name}"></li>
			<li>street: <input type="text" name="street"
				value="${address.street}"></li>
			<li>city: <input type="text" name="city" value="${address.city}"></li>
			<li>state: <input type="text" name="state"
				value="${address.state}"></li>
			<li>zip: <input type="text" name="zip" value="${address.zip}"></li>
		</ul>
		<input type="submit" value="edit">
	</form>
	<form action="contact" method="post">
		<input type="hidden" name="delete"> <input type="hidden"
			name="id" value="${contact.id}"> <input type="submit"
			value="delete">
	</form>
	<a href="contacts">back to contact list</a>

</body>
</html>