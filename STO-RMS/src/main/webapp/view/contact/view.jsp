<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${contact.name }</title>
</head>
<body>
	<h1>${contact.name }</h1>
	<ul>
		<c:set var="address" value="${contact.address}" />
		<li>${address.street }</li>
		<li>${address.city }, ${address.state }, ${address.zip }</li>
	</ul>
	<a href="contact?edit&id=${contact.id}">edit contact </a> |
	<a href="contacts">back to contact list</a>

</body>
</html>