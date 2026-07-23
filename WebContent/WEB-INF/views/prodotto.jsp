<%@page import="omniwear.model.ImmagineBean"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="omniwear.model.MisuraBean"%>
<%@page import="omniwear.model.CategoriaBean"%>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>
		<%	ProdottoBean prodotto = (ProdottoBean) request.getAttribute("prodotto");
			if(prodotto != null) { %>
			<%= prodotto.getNomeProdotto() %>
		<%	} %>
	</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/utils.js"></script></head>
<body>
	<%@ include file="header.jsp" %>
	<section class="glass" id="cart_container">
	
		<%	Collection<ImmagineBean> img = (Collection<ImmagineBean>) request.getAttribute("immagini"); 
			int count = 0;%>
		<div id="box_immagine">
			<div class="slideshow-container">
			<%	for(ImmagineBean i : img) { %>
				<div class="productSlides" <% if(count == 0) out.print("style='display: block;'"); %>>
	    			<img src="<%= i.getPath() %>">
	    		</div>
	    	<%	count++;
	    			} %>
				
			  <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
			  <a class="next" onclick="plusSlides(1)">&#10095;</a>
			</div>
			<br>

			<div>
				<%	for(int i = 0; i < count; i++) { %>
					<span class="dot" onclick="currentSlide(<%= i+1 %>)"></span>
				<%	} %>
			</div>
		</div>
		
		<aside id="riepilogo">
			<h3><%= prodotto.getNomeProdotto() %></h3><br>
			<hr>
			<% Collection<CategoriaBean> categorie = (Collection<CategoriaBean>) request.getAttribute("categorie");
				if(categorie != null && !categorie.isEmpty()) { %>
				<p>Categoria: 
					<% for(CategoriaBean c : categorie) { %>
							<span><%= c.getNomeCat() %></span> 
					<% } %>
				</p>
				
			<%	} %>
			<h4><%= prodotto.getPrezzo() %> €</h4>
		
			<form action="${pageContext.request.contextPath}/CartServlet" method="POST" id="form_carrello">
					<input type="hidden" name="action" value="aggiungi">
					<input type="hidden" name="id_prodotto" value="<%= prodotto.getIdProdotto() %>">
					<div>
						<label for="misura">Taglia:</label><br>
						<select name="misura" id="misura" required>
							<option value="" disabled selected>Scegli una taglia...</option>
							<option value="S">S</option>
							<option value="M">M</option>
							<option value="L">L</option>
							<option value="XL">XL</option>
						</select>
					</div>
					
					<div>
						<label for="quantita">Quantità:</label><br>
						<input type="number" name="quantita" id="quantita" value="1" min="1" max="<%= prodotto.getQt() %>" required>
						<span>(Disponibili: <%= prodotto.getQt() %>)</span>
					</div>
					
					<% if(prodotto.getQt() > 0) { %>
					<div class="bottoni_carrello">
						<input type="submit" value="Aggiungi al Carrello">
					</div>
					<% } else { %>
						<input type="button" value="Prodotto Esaurito" disabled>
					<% } %>
				</form>
		</aside>
		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>