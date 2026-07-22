<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="omniwear.model.UtenteBean" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Checkout</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/validate.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	
	
	<%@ include file="errorHandler.jsp" %>
	
	<% 
		float totale = (request.getAttribute("totale") == null) ? 0.0f : (Float) request.getAttribute("totale");
		
		UtenteBean utente = (UtenteBean) session.getAttribute("utente"); 
	%>
	
	<section class="glass" id="checkout_container" style="max-width: 600px; margin: 0 auto; padding: 20px;">
		<h2>Procedi al Pagamento</h2>
		<p style="font-size: 1.2em;">Totale da pagare: <strong><%= totale %> €</strong></p>
		<hr><br>
		
		<form action="${pageContext.request.contextPath}/checkout" method="POST">
			
			<span class="row">
				<label for="indirizzo">Indirizzo di Spedizione:</label><br>
				<input type="text" id="indirizzo" name="indirizzo" required pattern="\w[\w\d,. ]+" placeholder="Es. Via Roma X, CAP Salerno" onchange="validateField(this, document.getElementById('errore_indirizzo'), msg_addr)">
				<span class="err_validazione" id="errore_indirizzo"></span>
			</span>
			
			<h3>Dati della Carta di Credito</h3>
			
			<span class="row">
				<label for="titolare">Titolare:</label><br>
				<input type="text" id="titolare" name="titolare" required placeholder="Nome e cognome del titolare" pattern="^[A-z]+(\s[A-z]+)*$" onchange="validateField(this, document.getElementById('errore_titolare'), msg_solo_lettere)"><br>
				<span class="err_validazione" id="errore_titolare"></span>
			</span>
			
			<span class="row">
				<label for="numero_carta">Numero della Carta:</label><br>
				<input type="text" id="numero_carta" name="numero_carta" pattern="\d{16}" required placeholder="1234567891234567" maxlength="16" onchange="validateField(this, document.getElementById('errore_carta'), msg_carta)"><br>
				<span class="err_validazione" id="errore_carta"></span>
			</span>
			
			<span class="row">
				<label for="cvc">CVC:</label><br>
				<input type="text" id="cvc" name="cvc" pattern="\d{3}" required maxlength="3" placeholder="123" onchange="validateField(this, document.getElementById('errore_cvc'), msg_cvc)"><br>
				<span class="err_validazione" id="errore_cvc"></span>
			</span>
			
			<input type="submit" value="Procedi con l'ordine">
		</form>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>