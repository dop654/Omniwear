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
				<li><a href="${pageContext.request.contextPath}/index">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/catalogo">Catalogo</a></li>
				<li>
					<c:choose>
						<c:when test="${sessionScope.utente != null && sessionScope.utente.admin == true}">
							<a href="${pageContext.request.contextPath}/admin/home">Ciao, <c:out value="${sessionScope.utente.nome}"/></a>
						</c:when>
						<c:when test="${sessionScope.utente != null && sessionScope.utente.admin == false}">
							<a href="${pageContext.request.contextPath}/user_page">Ciao, <c:out value="${sessionScope.utente.nome}"/></a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/LoginServlet">Accedi</a>
						</c:otherwise>
					</c:choose>
				</li>
				<li><a href="${pageContext.request.contextPath}/CartServlet">Carrello</a></li>
				<li>
					<c:if test="${sessionScope.utente != null}">
							<a href="${pageContext.request.contextPath}/user_ordini">I miei Ordini</a>
					</c:if>
				</li>
				<li>
					<c:if test="${sessionScope.utente != null}">
							<a href="${pageContext.request.contextPath}/LogoutServlet">Esci</a>
					</c:if>
			</ul>
		</nav>
	</header>
</body>
</html>