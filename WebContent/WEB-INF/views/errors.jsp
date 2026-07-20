<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Errori</title>
	<link rel="stylesheet" type="text/css" href="styles/style.css">
	<link rel="icon" type="image/x-icon" href="images/favicons/favicon.ico">
	<script src="scripts/utils.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<section class="glass" id="cart_container" style="text-align: center;">
	
	<h1>Oops! Qualcosa è andato storto</h1>
	<div id="error">
	<% List<String> errors = (List<String>) request.getAttribute("errors");
	   if(errors != null && !errors.isEmpty()) {
		   
		   for(String e : errors) { 
	%>
			<p><%= e %></p>
	<% }
	}else { 
		%>
		<p>Si è verificato un errore inaspettato o la pagina non esiste.</p>
	<% }%> 
	</div>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>