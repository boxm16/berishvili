<%-- 
    Document   : downloads
    Created on : Jun 6, 2021, 5:40:40 PM
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
        <jsp:text>
            Items For Download: ${downloadFilesList.size()} 
        </jsp:text>
        <hr>
        <c:forEach items="${downloadFilesList}" var="item" varStatus="theCount">
            <a href="downloadFile.htm?fileName=${item}">${item}</a> <br>
        </c:forEach>

    </body>
</html>
