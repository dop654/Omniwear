<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="omniwear.model.ProdottoBean" %>
<%@ page import="omniwear.model.ImmagineBean" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Catalogo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<script src="${pageContext.request.contextPath}/scripts/ajax.js"></script>

<body>
	<%@ include file="header.jsp" %>
	<%@ include file="errorHandler.jsp" %>
	
	<div id="catalogo_layout">
	        <aside class="glass" id="filtri_laterali">
	    	    <form>
		            <input type="text" id="cerca" name="cerca" placeholder="Cerca prodotto...">
		            
		            <h3>Seleziona categoria</h3>
		            <label><input type="checkbox" name="categorie" value="Felpe"> Felpe</label><br>
		            <label><input type="checkbox" name="categorie" value="T-Shirt"> T-Shirt</label><br>
		            <label><input type="checkbox" name="categorie" value="Pantaloni"> Pantaloni</label><br>
		            <label><input type="checkbox" name="categorie" value="Accessori"> Accessori</label><br><br>
		            
		            <input type="button" value="Filtra" onclick="filtraCatalogo();">
	        </form>
	  	  </aside>

	<section class="glass" id="prodotti_main">
		<%  
	    	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
	        if(prodotti != null) {
	        for(ProdottoBean p : prodotti) { 
		%>
	    <div class="glass anteprima_prodotto">
	    	<% 	List<ImmagineBean> immagini = (List<ImmagineBean>) p.getImmagini();
				if(immagini != null && !immagini.isEmpty()) { 
			%>
				<img class="immagine_prodotto" src="<%= immagini.get(0).getPath() %>" alt="<%= p.getNomeProdotto() %>">
			<% } %>
		                
		    <h3><%= p.getNomeProdotto() %></h3><br>
		    <label class="prezzo"><%= p.getPrezzo() %> €</label><br>
		    <a href="${pageContext.request.contextPath}/SchedaProdottoServlet?id_prodotto=<%= p.getIdProdotto() %>">Dettagli</a>
	    </div>
		<%  
	    	}
	     } 
		%>
	</section>
	</div>

	<%@ include file="footer.jsp" %>
</body>
</html>