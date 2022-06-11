<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            tr, td, th{
                border:solid black 1px;
                font-size: 15px;
            }
        </style>
    </head>
    <body>
    <center> 
        <h1>TripPeriodsAnaliticCalculatations</h1>
        <h2>${errorMessage}</h2>
    </center> 
    <table>
        <thead>
            <tr>
                <th>Route #</th>
                <th>AB+ Average</th>
                <th>AB+ Driver Averages</th>
                <th style='background-color:black'>--</th>
                <th>AB- Average</th>
                <th>AB- Driver Averages</th>
                <th style='background-color:black'>--</th>
                <th>BA+ Average</th>
                <th>BA+ Driver Averages</th>
                <th style='background-color:black'>--</th>
                <th>BA- Average</th>
                <th>BA- Driver Averages</th>
            </tr>
        </thead>
        <c:forEach var="routeAverage" items="${routesAverages}" varStatus="loop">
            <tr>
                <td> ${routeAverage.value.routeNumber}</td>
                <td>
                    ${routeAverage.value.getAbLowAverageString()}
                </td>
                <td>
                    <table width='300px'>
                        <thead>
                            <tr>
                                <th style="width:10%">Driver #</th>
                                <th style="width:50%">Driver Name</th>
                                <th style="width:20%">Driver Average</th>
                                <th style="width:20%">S</th>
                            </tr>
                        </thead>
                    </table>
                    <c:forEach var="driverAverage" items="${routeAverage.value.driverAverages}" varStatus="loop">
                        <table width='300px'>
                            <tr>
                                <td style="width:10%">  ${driverAverage.value.driverNumber} </td>
                                <td style="width:50%">  ${driverAverage.value.driverName} </td>
                                <td style="width:20%">  ${driverAverage.value.getAbLowAverageString()} </td>
                                <td style="width:20%">  ${driverAverage.value.abLowTripPeriods.size()} </td>
                            </tr>
                        </table>
                    </c:forEach>
                </td>
                <td style='background-color:black'>--</td>
                <td>
                    ${routeAverage.value.getAbHighAverageString()}
                </td>
                <td>
                    <table width='300px'>
                        <thead>
                            <tr>
                                <th style="width:10%">Driver #</th>
                                <th style="width:50%">Driver Name</th>
                                <th style="width:20%">Driver Average</th>
                                <th style="width:20%">S</th>
                            </tr>
                        </thead>
                    </table>
                    <c:forEach var="driverAverage" items="${routeAverage.value.driverAverages}" varStatus="loop">
                        <table width='300px'>
                            <tr>
                                <td style="width:10%">  ${driverAverage.value.driverNumber} </td>
                                <td style="width:50%">  ${driverAverage.value.driverName} </td>
                                <td style="width:20%">  ${driverAverage.value.getAbHighAverageString()} </td>
                                <td style="width:20%">  ${driverAverage.value.abHighTripPeriods.size()} </td>

                            </tr>
                        </table>
                    </c:forEach>
                </td>
                <td style='background-color:black'>--</td>
                <td>
                    ${routeAverage.value.getBaLowAverageString()}
                </td>
                <td>
                    <table width='300px'>
                        <thead>
                            <tr>
                                <th style="width:10%">Driver #</th>
                                <th style="width:50%">Driver Name</th>
                                <th style="width:20%">Driver Average</th>
                                <th style="width:20%">S</th>
                            </tr>
                        </thead>
                    </table>
                    <c:forEach var="driverAverage" items="${routeAverage.value.driverAverages}" varStatus="loop">
                        <table width='300px'>
                            <tr>
                                <td style="width:10%">  ${driverAverage.value.driverNumber} </td>
                                <td style="width:50%">  ${driverAverage.value.driverName} </td>
                                <td style="width:20%">  ${driverAverage.value.getBaLowAverageString()} </td>
                                <td style="width:20%">  ${driverAverage.value.baLowTripPeriods.size()} </td>

                            </tr>
                        </table>
                    </c:forEach>
                </td>
                <td style='background-color:black'>--</td>
                <td>
                    ${routeAverage.value.getBaHighAverageString()}
                </td>
                <td>
                    <table>
                        <thead>
                            <tr>
                                <th style="width:10%">Driver #</th>
                                <th style="width:50%">Driver Name</th>
                                <th style="width:20%">Driver Average</th>
                                <th style="width:20%">S</th>
                            </tr>
                        </thead>
                    </table>
                    <c:forEach var="driverAverage" items="${routeAverage.value.driverAverages}" varStatus="loop">
                        <table width='300px'>
                            <tr>
                                <td style="width:10%">  ${driverAverage.value.driverNumber} </td>
                                <td style="width:50%">  ${driverAverage.value.driverName} </td>
                                <td style="width:20%">  ${driverAverage.value.getBaHighAverageString()} </td>
                                <td style="width:20%">  ${driverAverage.value.baHighTripPeriods.size()} </td>

                            </tr>
                        </table>
                    </c:forEach>
                </td>

            </tr>

        </c:forEach> 
    </table>
</body>
</html>
