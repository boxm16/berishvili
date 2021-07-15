<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        Selected teams:
        <br/>
        <c:forEach var="routeNumber" items="${tripPeriodsFilter.routeNumbers}">
            ${routeNumber}
            <br/>
        </c:forEach>
        <hr>
        <c:forEach var="dateStamp" items="${tripPeriodsFilter.dateStamps}">
            ${dateStamp}
            <br/>
        </c:forEach>
        <hr>
        <c:forEach var="busNumber" items="${tripPeriodsFilter.busNumbers}">
            ${busNumber}
            <br/>
        </c:forEach> 
        <hr>
        <c:forEach var="exodusNumber" items="${tripPeriodsFilter.exodusNumbers}">
            ${exodusNumber}
            <br/>
        </c:forEach> 
    </body>
</html>
