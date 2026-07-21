<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="java.util.List"%>
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
<body>
	<%@ include file="header.jsp" %>
	<section id = "catalogo">
		<%	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
			if(prodotti != null) {
				for(ProdottoBean p : prodotti) { %>
					<form class="glass" id="schedaP" method="POST" action="${pageContext.request.contextPath}/CartServlet">
						<input type="hidden" id="action" name="action" value="aggiungi">
						<input type="hidden" id="id_prodotto" name="id_prodotto" value="<%= p.getIdProdotto() %>">
						<input type="hidden" id="quantita" name="quantita" value="1">
						<label id="nomeProdotto" name="nomeProdotto"><%= p.getNomeProdotto() %></label><br>
						<label id="prezzo" name="prezzo"><%= p.getPrezzo() %></label><br>
						<a href="${pageContext.request.contextPath}/SchedaProdottoServlet">Dettagli</a>
						<input type="submit" value="Aggungi al carrello">
					</form>
			<%	}
			} %>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>