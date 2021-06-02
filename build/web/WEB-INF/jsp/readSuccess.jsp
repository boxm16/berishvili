<%-- 
    Document   : readSuccess
    Created on : May 30, 2021, 3:08:41 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Excel file has been read successfully</h1>
        <jsp:text>
            Routes Size : ${routes.size()}
        </jsp:text>
        <hr>
        <c:forEach items="${routes.values()}" var="route">
            <p>${route.number}</p>
        </c:forEach>
    </body>
</html>
