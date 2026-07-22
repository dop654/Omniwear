<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="omniwear.model.ProdottoBean" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Catalogo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	<%@ include file="errorHandler.jsp" %>
		
	<section class="glass" id = "prodotti">
		<%	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
			if(prodotti != null) {
				for(ProdottoBean p : prodotti) { 
		%>
					<form class="glass" id="schedaP" method="POST" action="${pageContext.request.contextPath}/CartServlet">
						<input type="hidden" name="action" value="aggiungi">
						<input type="hidden" name="id_prodotto" value="<%= p.getIdProdotto() %>">
						<input type="hidden" name="quantita" value="1">
						<label class="nomeProdotto"><%= p.getNomeProdotto() %></label><br>
						<label class="prezzo"><%= p.getPrezzo() %> €</label><br>
						
						<a href="${pageContext.request.contextPath}/SchedaProdottoServlet?id_prodotto=<%= p.getIdProdotto() %>">Dettagli</a>
						
						<input type="submit" value="Aggiungi al carrello">
					</form>
		<%	
				}
			} 
		%>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>