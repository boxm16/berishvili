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
        <style> /* -----------------
           pagination
            
            --------------------*/
            .pagination {
                display: inline-block;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
            }

            .pagination a.active {
                width:100px;
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {background-color: #ddd;}

            /*--------------tooltip---------------*/
            #tooltip {
                background: cornsilk;
                border: 1px solid black;
                border-radius: 5px;
                padding: 5px;
            }
        </style>
        <script>
            function showTooltip(evt, text) {
                let tooltip = document.getElementById("tooltip");
                tooltip.innerHTML = text;
                tooltip.style.display = "block";
                tooltip.style.left = evt.pageX + 10 + 'px';
                tooltip.style.top = evt.pageY + 10 + 'px';
            }

            function hideTooltip() {
                var tooltip = document.getElementById("tooltip");
                tooltip.style.display = "none";
            }
        </script>
    </head>
    <body>
        <h4>ვერსიების მთლიანი რაოდენობა:${breaksPager.getVersionsCount()}</h4>
        <h1 style="background-color:red">${message}</h1>
    <center>  ${breaksPager.display}</center>

    <div id="tooltip" display="none" style="position: absolute; display: none;"></div>

    <c:forEach var="route" items="${breaksPager.getCurrentVersions()}" varStatus="loop">
        <label>${breaksPager.getBlockStartingVersionNumber()+loop.index+1}</label>
        <svg width='1500' height='${route.height}'>
        <rect x='5' width='1530' height='20' style='fill:rgb(0,0,0);' />
        <rect x='5' width='20' height='${route.height}' style='fill:rgb(0,0,0);' />
        <% int x = 0;
            LocalTime hh = LocalTime.parse("05:00:00");
        %>
        <c:forEach var = "i" begin = "30" end = "1260" step="60">
            <% x++;
                String time = hh.format(DateTimeFormatter.ofPattern("HH:mm"));
            %>

            <line x1='${i}' y1='20' x2='${i}' y2='${route.height}'style='stroke:rgb(0,0,0);stroke-width:1' />
            <text x='${i-18}' y='15' fill='white'> <% out.print(time);%></text>

            <%  hh = hh.plusHours(1);%>

        </c:forEach>
        <c:forEach var="exodus" items="${route.exoduses}" varStatus="exodusesCount">
            <c:forEach var="tripPeriod" items="${exodus.tripPeriods}" >
                <rect x='${tripPeriod.getStartPoint()}' y='${(exodusesCount.index*30)+30}' width='${tripPeriod.length}' height='20'  rx='7' style='fill:${tripPeriod.color}' onmousemove="showTooltip(evt, '${tripPeriod.getDescription()}')" onmouseout="hideTooltip()"/>
                <text x="${tripPeriod.getStartPoint()+3}" y="${(exodusesCount.index*30)+43}" font-size="7" font-weight="bold" fill="black" >${tripPeriod.getStartTimeString()}</text>

            </c:forEach>

        </c:forEach>


        </svg>
        <hr>

    </c:forEach>
    <hr>
    <center>  ${breaksPager.display}</center>
    <hr>
</body>
</html>
