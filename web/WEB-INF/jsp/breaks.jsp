<%@page import="java.time.LocalTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>


        <c:forEach var="route" items="${routes}">

            <svg width='1500' height='200'>
            <rect x='5' width='1530' height='20' style='fill:rgb(0,0,0);' />
            <rect x='5' width='20' height='300' style='fill:rgb(0,0,0);' />
            <% int x = 0;
                LocalTime hh = LocalTime.parse("05:00:00");
            %>
            <c:forEach var = "i" begin = "30" end = "1260" step="60">
                <% x++;
                    String time = hh.format(DateTimeFormatter.ofPattern("HH:mm"));
                %>

                <line x1='${i}' y1='20' x2='${i}' y2='200'style='stroke:rgb(0,0,0);stroke-width:1' />
                <text x='${i-18}' y='15' fill='white'> <% out.print(time);%></text>

                <%  hh = hh.plusHours(1);%>

            </c:forEach>
            <c:forEach var="exodus" items="${route.exoduses}" varStatus="exodusesCount">
                <c:forEach var="tripPeriod" items="${exodus.tripPeriods}" >
                    <rect x='${tripPeriod.getStartPoint()}' y='${(exodusesCount.index*30)+30}' width='100' height='20'  rx='7' style='fill:red' />

                </c:forEach>

            </c:forEach>


            </svg>
            <hr>
        </c:forEach>
    </body>
</html>
