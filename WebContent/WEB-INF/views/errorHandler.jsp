<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<%	String msg = (String) request.getAttribute("msg");
		if(msg != null && !msg.isEmpty()) { %>
			<div class="glass" id="msg">
				<%= msg %>
			</div>
	<% } %>
	<%	List<String> errors = (List<String>) request.getAttribute("errors");
		if(errors != null && !errors.isEmpty()) { %>
		<div class="glass" id="error">
			<ul>
				<% for(String e : errors) { %>
					<li><%= e %></li>
				<% } %>
			</ul>
		</div>
	<% } %>
</body>
</html>