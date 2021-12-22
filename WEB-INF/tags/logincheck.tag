<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:if test="${empty mem}">
		<script>
			alert("retry login!");
			location.href="login.jsp";
		</script>
	</c:if>