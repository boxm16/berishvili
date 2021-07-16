<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ბრუნების დროების ფილტრები</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <style>
            th,  tr,  td {
                border:solid black 1px;

            }
            table{
                border-collapse: collapse;
            }
            input[type="checkbox"]{
                width: 20px; /*Desired width*/
                height: 20px; /*Desired height*/
            }
            td {
                vertical-align: top;
            }
            /*-----------------
            navbar styling
            -------------*/
            .navbar {
                overflow: hidden;
                background-color: #D170F7;
                position: fixed;
                top: 0;
                width: 100%;
            }

            .navbar a {
                float: left;
                display: block;
                color: #f2f2f2;
                text-align: center;
                padding: 18px 16px;
                text-decoration: none;
                font-size: 17px;
            }

            .navbar a:hover {
                background: lightgreen;
                color: black;
            }
            .btn{
                background-color: blue;
                color:white;
                padding: 10px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 20px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <a href="index.htm">საწყისი გვერდი</a>
            <a href="tripPeriods.htm?blockIndex=${blockIndex}">უკან დაბრუნება</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


        </div>

        <br><br>     
    <center> <h1>ბრუნების დროების ფილტრები</h1></center>
    <table>
        <thead >
            <tr>
                <th><button onclick="">მარშრუტის ნომრების გაფილტვრა</button></th>
                <th>DateStamp</th>
                <th>BusNumber</th>
                <th>Exodus<br>Number</th>
                <th>DriverName</th>
                <th>type</th>
                <th>StartTime<br>Scheduled</th>
                <th>StartTime<br>Actual</th>
                <th>ArrivalTime<br>Scheduled</th>
                <th>ArrivalTime<br>Actual</th>
                <th>TripPeriodTime<br>Scheduled</th>
                <th>TripPeriodTime<br>Actual</th>
                <th>TripPeriodTime<br>Difference</th>
            </tr>
        </thead>
        <tbody 
            <tr height="150px">
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">
                    <c:forEach var="item" items="${tripPeriodsFilter.routeNumbers}">
                        <tr>
                            <td>
                                <input type="checkbox" name="exodusNumbers" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.dateStamps}">
                        <tr>
                            <td>
                                <input type="checkbox" name="dateStamps" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>

        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.busNumbers}">
                        <tr>
                            <td>
                                <input type="checkbox" name="busNumbers" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.exodusNumbers}">
                        <tr>
                            <td>
                                <input type="checkbox" name="exodusNumbers" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">
                    <c:forEach var="item" items="${tripPeriodsFilter.driverNames}">
                        <tr>
                            <td>
                                <input type="checkbox" name="driverNames" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTypes}">
                        <tr>
                            <td>
                                <input type="checkbox" name="tripPeriodTypes" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.startTimesScheduled}">
                        <tr>
                            <td>
                                <input type="checkbox" name="startTimesScheduled" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.startTimesActual}">
                        <tr>
                            <td>
                                <input type="checkbox" name="startTimesActual" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.arrivalTimesScheduled}">
                        <tr>
                            <td>
                                <input type="checkbox" name="arrivalTimesScheduled" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">

                    <c:forEach var="item" items="${tripPeriodsFilter.arrivalTimesActual}">
                        <tr>
                            <td>
                                <input type="checkbox" name="arrivalTimesActual" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">
                    <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimeScheduled}">
                        <tr>
                            <td>
                                <input type="checkbox" name="tripPeriodTimeScheduled" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>

        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">
                    <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimeActual}">
                        <tr>
                            <td>
                                <input type="checkbox" name="tripPeriodTimeActual" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>

        <td>
            <table>
                <tbody style="height:500px; overflow-y:scroll; display:block;">
                    <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimeDifference}">
                        <tr>
                            <td>
                                <input type="checkbox" name="tripPeriodTimeDifference" class="dates" checked="${item.value}" value="${item.key}"> 
                            </td>
                            <td>
                                ${item.key}  
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>

    </tr>
</tbody>
</table>

</body>
</html>
