<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>შეჯგუფებები</title>
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
    <center> <h1>შეჯგუფებები</h1></center>
    <table>
        <thead>
        <th>
            მარშრუტის<br>ნომერი
        </th>
        <th>
            პუნკტი
        </th>
        <th>
            თარიღი
        </th>
        <th>
            დგომის<br> დაწყების<br> დრო
        </th>
        <th>
            დგომის <br> დასარულების <br>დრო
        </th>
        <th>
            დგომის <br> ხანგრძლივობა
        </th>
        <th>
            ლინკი
        </th>
    </thead>
    <c:forEach var="haltMisconudct" items="${haltMisconductsList}" varStatus="loop">
        <c:forEach var="halt" items="${haltMisconudct.participantHalts}" varStatus="loop">
            <tr >
                <td>${haltMisconudct.routeNumber}</td>
                <td>${haltMisconudct.point}</td>
                <td>${haltMisconudct.dateStamp}</td>
                <td>${halt.getStartTimeString()}</td>
                <td>${halt.getEndTimeString()}</td>
                <td>${halt.getDurationString()}</td>
                <td><a href="exodus.htm?routeNumber=${haltMisconudct.routeNumber}&dateStamp=${haltMisconudct.dateStamp}&exodusNumber=${halt.exodusNumber}&startTimeScheduled=${halt.tripPeriodStartTimeScheduled}"  target="_blank">link</a></td>
            </tr>

        </c:forEach>
        <tr><td>--</td><td>--</td><td>--</td><td>--</td><td>--</td><td>--</td><td>--</td></tr>
    </c:forEach>
</table>
</body>
</html>
