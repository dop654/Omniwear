<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<header class="glass">
		<div id="logo">
			<img src="${pageContext.request.contextPath}/images/logo_b.svg" alt="logo">
			<h1>OMNIWEAR</h1>
		</div>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/HomeServlet">Home</a></li>
				<li><a href="#">Sconti</a></li>
				<li>
					<c:choose>
						<c:when test="${sessionScope.role == 'admin'}">
							<a href="${pageContext.request.contextPath}/admin/home">Ciao, <c:out value="${sessionScope.nome_utente}"/></a>
						</c:when>
						<c:when test="${sessionScope.role == 'user'}">
							<a href="${pageContext.request.contextPath}/UserServlet">Ciao, <c:out value="${sessionScope.nome_utente}"/></a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/LoginServlet">Accedi</a>
						</c:otherwise>
					</c:choose>
				</li>
				<li><a href="${pageContext.request.contextPath}/CartServlet">Carrello</a></li>
				<li>
					<c:if test="${sessionScope.id_utente != null}">
							<a href="${pageContext.request.contextPath}/LogoutServlet">Esci</a>
					</c:if>
				</li>
			</ul>
		</nav>
	</header>
</body>
</html>