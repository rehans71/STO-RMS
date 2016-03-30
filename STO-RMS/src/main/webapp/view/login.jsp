<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>STO main page</title>
<script>
	function validate() {
		if(document.forms.loginForm.j_username.value == "")
			{
				alert("You must provide the username DUDE.");
				return false;
			}
		return true;
	}
</script>
</head>
<body onload='document.LoginForm.j_username.focus();'>
	<h3>STO main page</h3>
	<%
		String errorString = (String) request.getAttribute("error");
		if (errorString != null && errorString.trim().equals("true")) {
			out.println("Incorrect login name or password. Please retry using correct login name and password-rehan.");
		}
	%>
	<form id='loginForm' action="<c:url value='j_spring_security_check' />" method='POST' onsubmit="return validate();">
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td><input name="submit" type="submit" value="submit" /></td>
				<td><input name="reset" type="reset" /></td>
			</tr>
		</table>
	</form>
	<sec:authorize access="authenticated">
	<ul>
		<c:url var="logoutUrl" value="/j_spring_security_logout"/>
		<li>
			<a href="${logoutUrl}">Log out</a>
		</li>
	</ul>
	</sec:authorize>
</body>
</html>




