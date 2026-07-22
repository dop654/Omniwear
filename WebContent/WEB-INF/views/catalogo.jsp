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
	
<section class="glass" id="ricerca">
		<div id="form">
			<form action="${pageContext.request.contextPath}/catalogo" method="GET">
				<input type="text" name="cerca" placeholder="Cerca prodotto...">
			
				<aside class="glass"> 
					<p>Seleziona la categoria del prodotto</p>
					
					<label>
						<input type="checkbox" name="categorie" value="Felpe"> Felpe
					</label>
				
					<label>
						<input type="checkbox" name="categorie" value="T-Shirt"> T-Shirt
					</label>
					
					<label>
						<input type="checkbox" name="categorie" value="Pantaloni"> Pantaloni
					</label>
					
					<label>
						<input type="checkbox" name="categorie" value="Accessori"> Accessori
					</label>
					
					<br><br>
					<input type="submit" value="Filtra">
					<a href="${pageContext.request.contextPath}/catalogo">Resetta filtri</a>
				</aside>
			</form>
		</div>
	</section>
	
	<section class="glass" id = "prodotti">
		<%	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
			if(prodotti != null) {
				for(ProdottoBean p : prodotti) { 
		%>
					<div class="glass anteprima_prodotto">
						<input type="hidden" name="action" value="aggiungi">
						<input type="hidden" name="id_prodotto" value="<%= p.getIdProdotto() %>">
						<input type="hidden" name="quantita" value="1">
						<h3><%= p.getNomeProdotto() %></h3><br>
						<label class="prezzo"><%= p.getPrezzo() %> €</label><br>
						<a href="${pageContext.request.contextPath}/SchedaProdottoServlet?id_prodotto=<%= p.getIdProdotto() %>">Dettagli</a>
					</div>
		<%	
				}
			} 
		%>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>