<%@page import="java.time.LocalTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>-------</title>
        <style>
            input[type="number"] {
                width:45px;
            }


            table, tr, td, th {

                border:1px solid black;
            }
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

        <a href="timeTable2.htm" target="_blank">SHOW TIMETABLE</a>
        <hr>
  <div id="tooltip" display="none" style="position: absolute; display: none;"></div>
        <!-- end of template -->

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
            </c:forEach>

        </c:forEach>


        </svg>
        <hr>

        <script>
            function   showNewIntervals() {
                let form = document.getElementById("form");
                form.action = "newInterval.htm";

            }
            function   showTimeTable() {
                let form = document.getElementById("form");
                form.action = "showTimeTable.htm";

            }
        </script>
    </body>
</html>
