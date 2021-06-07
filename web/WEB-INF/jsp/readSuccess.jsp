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
        <table border="1" cellpadding="5">
            <c:forEach items="${routes.values()}" var="route">
                <tr><td colspan="3"><c:out value="Route Number: ${route.number}"></c:out></td></tr>
                    <c:forEach items="${route.getDays().values()}" var="day">
                    <tr><td colspan="3"><c:out value="${day.dateStamp}"></c:out></td></tr>
                        <c:forEach items="${day.getExoduses().values()}" var="exodus">
                        <tr><td colspan="3"><c:out value="Exodus Number: ${exodus.number}"></c:out></td></tr>
                            <c:forEach items="${exodus.getTripVouchers().values()}" var="tripVoucher">
                            <tr><td colspan="3"><c:out value="Trip Voucher Number: ${tripVoucher.number}"></c:out></td></tr>
                                <c:forEach items="${tripVoucher.getTripPeriods()}" var="tripPeriod">
                                <tr>
                                    <td><c:out value="${tripPeriod.getStartTimeScheduledString()}"></c:out></td>
                                    <td><c:out value="${tripPeriod.getStartTimeActualString()}"></c:out></td>
                                    <td><c:out value="${tripPeriod.getStartTimeDifference()}"></c:out></td>    
                                    </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
            </c:forEach>
        </table>
    </body>
</html>
