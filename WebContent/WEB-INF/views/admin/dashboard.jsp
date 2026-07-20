<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dashboard Admin</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="../header.jsp" %>
	<section class="glass" id="dash">
		<a href="${pageContext.request.contextPath}/admin/prodotti">Accedi al catalogo</a><br>
		<a href="${pageContext.request.contextPath}/admin/users">Accedi alla lista utenti</a><br>
		<a href="${pageContext.request.contextPath}/admin/ordini">Accedi alla lista ordini</a><br>
	</section>
	<%@ include file="../footer.jsp" %>
</body>
</html>