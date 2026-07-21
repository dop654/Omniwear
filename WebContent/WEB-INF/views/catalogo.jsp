<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Catalogo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/ajax.js"></script>
</head>
<body onload="aggiornaCatalogo(null, null)">
	<%@ include file="header.jsp" %>
	<section id = "catalogo">
		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>