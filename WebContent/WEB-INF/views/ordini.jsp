<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="omniwear.model.OrdineBean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Omniwear - I miei ordini</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<%@ include file="errorHandler.jsp" %>
	
	<section class="glass" id="cart_container">
	
		<h1>I tuoi ordini</h1>
		<hr><br>
		
		<% 
			List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini"); 
			String[] statoOrd = {"Annullato", "Ricevuto", "Spedito", "Consegnato"};	
			if(ordini == null || ordini.isEmpty()){
		%>
				<div class="glass" id="msg">
					Non hai ancora effettuato alcun ordine!
					<hr><br>
					<a href="${pageContext.request.contextPath}/index">Torna alla home</a>
				</div>
		<% 
			} else { 
				for(OrdineBean o : ordini){
		%>			<section class="glass scheda_ordine" >
						<ul>
							<li>ID Ordine: <%= o.getIdOrdine() %></li>
							<hr>
							<li>Stato: <%= statoOrd[o.getStatoOrdine()] %></li>
							
							<% if(o.getStatoOrdine() != 0) { %>
								<li>Totale: <%= o.getTotale() %>€</li>
							<% } %>
							
							<% if(o.getStatoOrdine() != 0 && o.getStatoOrdine() != 3) { %>
								<li>
									<form action="${pageContext.request.contextPath}/user_ordini" method="POST">
										<input type="hidden" name="action" value="annulla">
										<input type="hidden" name="id_ordine" value="<%= o.getIdOrdine() %>">
										<input type="submit" value="Annulla Ordine">
									</form>
								<br>
								<a href="${pageContext.request.contextPath}/dettaglio_ordini?id_ordine=<%= o.getIdOrdine() %>">Dettagli</a>
							<% } %>
						</ul>
						<br>
					</section>
		<% 		}
			}
		%>
		
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>