<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="omniwear.model.ProdottoBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>
		<%	ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
			if(prodotto != null) { %>
			<%= prodotto.getNomeProdotto() %>
		<%	} %>
	</title>
	<link rel="stylesheet" type="text/css" href="styles/style.css">
	<link rel="icon" type="image/x-icon" href="images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<%@ include file="footer.jsp" %>
</body>
</html>