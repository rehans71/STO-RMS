<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${person.name }</title>
</head>
<body>
	<h1>${person.name }</h1>
	<ul>
		<c:set var="address" value="${person.address}" />
		<li>${address.street }</li>
		<li>${address.city }, ${address.state }, ${address.zip }</li>
		<li>manager: ${person.manager.name}</li>
		<li>employer: ${person.employer.name}</li>
	</ul>
	<a href="${person.url}&edit">edit person </a> |
	<a href="contacts">back to contact list</a>

	<ul class="nav pull-right">
		<c:url var="logoutUrl" value="/j_spring_security_logout"/>
		<li>
			<a href="${logoutUrl}">Log out</a>
		</li>
	</ul>
	
</body>
</html>