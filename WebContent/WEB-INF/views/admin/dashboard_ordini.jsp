	<%@page import="omniwear.model.OrdineBean"%>
	<%@page import="java.util.List"%>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="UTF-8">
		<title>Dashboard Admin</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
		<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	</head>
	<body>
		<%@ include file="../header.jsp" %>
		<section class="glass" id="ordini">
			<%	List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("listaOrdini");
				String[] statoOrd = {"Annullato", "Ricevuto", "Spedito", "Consegnato"};
				if(ordini == null || ordini.isEmpty()) { %>
					<div class="glass" id="msg">
						Non ci sono ordini
						<hr><br>
						<a href="${pageContext.request.contextPath}/admin/home">Torna alla Dashboard</a>
					</div>
			<%	} else {
					for(OrdineBean o : ordini) { %>
						<section class="glass scheda_ordine" >
							<ul>
								<li>ID Ordine: <%= o.getIdOrdine() %></li>
								<li>Totale: <%= o.getTotale() %>€</li>
								
								<li>
									<div id="form">
										<form action="${pageContext.request.contextPath}/admin/ordini" method="POST">
											<input type="hidden" name="id_ordine" value="<%= o.getIdOrdine() %>">
											<label for="stato">Stato:</label>
											<select name="stato" id="stato">
												<% for(int i = 0; i<statoOrd.length; i++) { %>
													<option value="<%= i %>" <%= (o.getStatoOrdine() == i)?"selected='selected'" : ""%>><%= statoOrd[i] %></option>
												<% } %>
											</select>
											<input type="submit" value="Aggiorna stato">
										</form>
									</div>
								</li>
							</ul>
						</section>
			<%		} 
				} %>
		</section>
		<%@ include file="../footer.jsp" %>
	</body>
	</html>