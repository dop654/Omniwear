<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Carrello</title>
	<link rel="stylesheet" type="text/css" href="styles/style.css">
	<link rel="icon" type="image/x-icon" href="images/favicons/favicon.ico">
	<script src="scripts/ajax.js"></script>
	<script src="scripts/cart.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<section id="container">
		<section class="glass" id="elenco_prodotti">
			<h1>CIAO</h1>
		</section>
		<aside id="riepilogo">
			<h3>Riepilogo ordine:</h3><br>
			<ul id="prezzi_prodotti">
			
			</ul>
			<hr>
			<p id="n_prodotti">Numero prodotti: 0</p>
			<h4 id="totale">Totale: 0,00€</h4>
		</aside>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>