<%@page import="omniwear.model.ImmagineBean"%>
<%@page import="java.util.Collection"%>
<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Homepage</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<%@ include file="errorHandler.jsp" %>
	
	<div id="box_immagine_home">
		<div class="slideshow-container">
			<div class="mySlides fade">
    			<img src="${pageContext.request.contextPath}/images/carosello1.jpg">
    			<div class="text">Stile casual</div>
    		</div>
    		<div class="mySlides fade">
    			<img src="${pageContext.request.contextPath}/images/carosello2.jpg">
    			<div class="text">Capi di alta qualità</div>
    		</div>
    		<div class="mySlides fade">
    			<img src="${pageContext.request.contextPath}/images/carosello3.jpg">
 					<div class="text">Sfoggia chi sei</div>
    		</div>
		</div>
		<br>

		<div style="text-align:center">
			<%	for(int i = 0; i < 3; i++) { %>
				<span class="dot"></span>
			<%	} %>
		</div>
	</div>
	<section class="glass" id = "catalogo">
		<h3>Ultimi arrivi:</h3><br>
		<%  List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
			int i = 0;
	        if(prodotti != null) {
		        for(; i<prodotti.size() && i<5; i++) { 
		        	ProdottoBean p = prodotti.get(i);%>
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
			<% }
		     } %>
	</section>
	<%@ include file="footer.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/scripts/utils.js"></script>
</html>