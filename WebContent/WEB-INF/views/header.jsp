<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<header class="glass">
		<div id="logo">
			<img src="images/logo_b.svg" alt="logo">
			<h1>OMNIWEAR</h1>
		</div>
		<nav>
			<ul>
				<li><a href="${request.getContextPath}HomeServlet">Home</a></li>
				<li><a href="#">Sconti</a></li>
				<li>
					<c:choose>
						<c:when test="${session.getAttribute('id_utente') != null}">
							<a href="${request.getContextPath}UserServlet">Ciao, <c:out>${session.getAttribute("nome_utente")}</c:out></a>
						</c:when>
						<c:otherwise>
							<a href="${request.getContextPath}LoginServlet">Accedi</a>
						</c:otherwise>
					</c:choose>
				</li>
				<li><a href="#">Carrello</a></li>
				<li>
					<c:if test="${session.getAttribute('id_utente') != null}">
							<a href="${request.getContextPath}LogoutServlet">Esci</a>
					</c:if>
				</li>
			</ul>
		</nav>
	</header>
</body>
</html>