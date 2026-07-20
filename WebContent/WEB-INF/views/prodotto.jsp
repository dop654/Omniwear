<%@page import="omniwear.model.ImmagineBean"%>
<%@page import="java.util.Collection"%>
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
	<script src="scripts/utils.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<section class="glass" id="cart_container">
	
		<%	Collection<ImmagineBean> img = (Collection<ImmagineBean>) request.getAttribute("immagini"); 
			int count = 0;%>
		<div>
			<div class="slideshow-container">
			<%	for(ImmagineBean i : img) { %>
				<div class="mySlides fade">
	    			<img src="${ i.getPath }" style="width:100%">
	    		</div>
	    	<%	} %>

			  <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
			  <a class="next" onclick="plusSlides(1)">&#10095;</a>
			</div>
			<br>

			<div style="text-align:center">
				<%	for(int i = 0; i < count; i++) { %>
					<span class="dot" onclick="currentSlide(<%= i+1 %>)"></span>
				<%	} %>
			</div>
		</div>
		
		<aside id="riepilogo">
			<h3><%= prodotto.getNomeProdotto() %></h3><br>
			<hr>
			<h4><%= prodotto.getPrezzo() %> €</h4>
		</aside>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>