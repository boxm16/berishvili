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
        <table>
            <thead>
                <tr>   
                    <th>თარიღი</th>
                    <th>ა/ბაზა</th>
                    <th>მარშ. #</th>

                    <th>"A" პუნქტი</th>
                    <th>"Β" პუნქტი</th>
                    <th>მიმართულება</th>
                    <th>საგარანტიო<br>გასვლის<br>ტიპი</th>

                    <th>გეგმიური<br>გასვლის<br>დრო</th>
                    <th>ფაქტიური<br>გასვლის<br>დრო</th>
                    <th>სხვაობა</th>

                    <th>გეგმიური<br>გასვლის<br>ნომერი</th>
                    <th>ფაქტიური<br>გასვლის<br>ნომერი</th>

                    <th>ფაქტიური<br>გასვლის<br>მძღოლი</th>
                    <th>ფაქტიური<br>გასვლის<br>სახ. ნომერი</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="dayArray" items="${guarantyData}" varStatus="loop">
                    <c:forEach var="guarantyTripPeriod" items="${dayArray}">
                        <tr style="background-color: ${loop.index%2==0?"inherited":"lightgrey"}">
                            <td  style="width: 80px;">${guarantyTripPeriod.dateStamp}</td>
                            <td>${guarantyTripPeriod.baseNumber}</td>
                            <td>${guarantyTripPeriod.routeNumber}</td>
                            <td>${guarantyTripPeriod.aPoint}</td>
                            <td>${guarantyTripPeriod.bPoint}</td>


                            <td>${guarantyTripPeriod.getTypeG()}</td>
                            <td>${guarantyTripPeriod.guarantyType}</td>


                            <td>${guarantyTripPeriod.getGuarantyStartTimeScheduledString()}</td>
                            <td style="background-color:${guarantyTripPeriod.isSpacialCase()?"red":"inherited"} ">${guarantyTripPeriod.getGuarantyStartTimeActualString()}</td>
                            <td style="background-color:${guarantyTripPeriod.getGuarantyDifferenceColor()} ">${guarantyTripPeriod.getGuarantyStartTimeDifferenceString()}</td>

                            <td align="center"><a href="interval.htm?routeNumber=${guarantyTripPeriod.routeNumber}&dateStamp=${guarantyTripPeriod.dateStamp}&tripPeriodType=${guarantyTripPeriod.getType()}&startTimeScheduled=${guarantyTripPeriod.getGuarantyStartTimeScheduled()}" target="_blank">${guarantyTripPeriod.exodusScheduled}</a></td>
                            <td align="center" style="background-color:${guarantyTripPeriod.exodusScheduled==guarantyTripPeriod.exodusActual?"inherited":"red"} "><a href="interval.htm?routeNumber=${guarantyTripPeriod.routeNumber}&dateStamp=${guarantyTripPeriod.dateStamp}&tripPeriodType=${guarantyTripPeriod.getType()}&startTimeScheduled=${guarantyTripPeriod.getExodusActualStartTimeScheduled()}" target="_blank">${guarantyTripPeriod.exodusActual==0? "":guarantyTripPeriod.exodusActual}</a></td>

                            <td>${guarantyTripPeriod.driverName}</td>
                            <td>${guarantyTripPeriod.busNumber}</td>

                        </tr>
                    </c:forEach>

                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
