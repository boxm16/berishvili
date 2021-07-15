<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
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
            ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }
            label {
                font-size: 20px;
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
                padding: 14px 16px;
                text-decoration: none;
                font-size: 17px;
            }

            .navbar a:hover {
                background: #ddd;
                color: black;
            }
            .btn{
                color: lightblue;
                padding: 10px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <form:form method="POST" commandName="tripPeriodsFilter" action="res.htm">

            <div class="navbar">
                <a href="#home">Home</a>
                <center>
                    <div>
                        <input class="btn" type="submit" value="გაფილტვრა"/>
                    </div>
                </center>
            </div>

            <br><br>     
        <center> <h1>ბრუნების დროების ფილტრები</h1></center>


        <table>
            <thead>
                <tr>
                    <th>ROuteNumber</th>
                    <th>DateStamp</th>
                    <th>BusNumber</th>
                    <th>ExodusNumber</th>
                    <th>DriverName</th>
                    <th>type</th>
                    <th>StartTimeScheduled</th>
                    <th>StartTimeActual</th>
                    <th>ArrivalTimeScheduled</th>
                    <th>ArrivalTimeActual</th>
                </tr>
            </thead>
            <tr>
                <td style="vertical-align: top;">
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>" element="li" path="routeNumbers" items="${tripPeriodsFilter.routeNumbers}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="dateStamps" items="${tripPeriodsFilter.dateStamps}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>" element="li" path="busNumbers" items="${tripPeriodsFilter.busNumbers}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="exodusNumbers" items="${tripPeriodsFilter.exodusNumbers}"/>
                    </ul>
                </td>

                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="driverNames" items="${tripPeriodsFilter.driverNames}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="tripPeriodTypes" items="${tripPeriodsFilter.tripPeriodTypes}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="startTimesScheduled" items="${tripPeriodsFilter.startTimesScheduled}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="startTimesActual" items="${tripPeriodsFilter.startTimesActual}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>" element="li" path="arrivalTimesScheduled" items="${tripPeriodsFilter.arrivalTimesScheduled}"/>
                    </ul>
                </td>
                <td>
                    <ul  style="list-style-type:none;">
                        <form:checkboxes delimiter="<hr>"  element="li" path="arrivalTimesActual" items="${tripPeriodsFilter.arrivalTimesActual}"/>
                    </ul>
                </td>

        </table>
    </form:form>


</body>
</html>
