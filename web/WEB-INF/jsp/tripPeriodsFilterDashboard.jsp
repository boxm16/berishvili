
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            button {
                font-size: 25px;
            }
        </style>
    </head>
    <body>
        <hr>
        &nbsp
        <button type="button" class="btn btn-outline-success">
            <span> <a href="index.htm">საწყისი გვერდი</a> </span>
        </button>
        <h1>Filter Choice here</h1>

        <hr>
        <table>
            <tr>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=routeNumbers">Route Numbers Filter</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=dateStamps">Date Stamp Filter</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=busNumbers">Bus Number</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=exodusNumbers">Exodus Number</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=driverNames">driver name</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=tripPeriodTypes">Trip Period Tpe</a> </span>
                    </button>
                </td>


                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=startTimesScheduled">Start Time Scheduled</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=startTimesActual">startTimesActual</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=arrivalTimesScheduled">arrival Times Scheduled</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="tripPeriodsFilterChoice.htm?target=arrivalTimesActual">arrival Times Actual</a> </span>
                    </button>
                </td>
                startTimesScheduled
            </tr>
        </table>
        <hr><hr>
        <table>
            <tbody style="height:200px; overflow-y:scroll; display:block;">
                <c:forEach var="item" items="${targetFilter}">
                    <tr>
                        <td>
                            <input type="checkbox" name="routeNumbers" class="dates"   ${item.value} value="${item.key}"> 
                        </td>
                        <td>
                            ${item.key}  
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
