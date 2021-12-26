<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            tr td  {
                border:solid black 1px;
            }
            th {

                border:solid black 1px;
            }
        </style>
    </head>
    <body>
        <h1>Halt Misconducts HERE </h1>
        <table>
            <thead>
            <th>
                routeNumber
            </th>
            <th>
                date Stamp
            </th>
            <th>
                startTime
            </th>
        </thead>
        <c:forEach var="haltMisconudct" items="${haltMisconductsList}" varStatus="loop">
            <c:forEach var="halt" items="${haltMisconudct.participantHalts}" varStatus="loop">
                <tr >
                    <td>${haltMisconudct.routeNumber}</td>
                    <td>${haltMisconudct.dateStamp}</td>
                    <td>${halt.getStartTimeString()}</td>
                    <td>${halt.getEndTimeString()}</td>
                </tr>

            </c:forEach>
            <tr><td>--</td><td>--</td><td>--</td><td>--</td></tr>
        </c:forEach>
    </table>
</body>
</html>
