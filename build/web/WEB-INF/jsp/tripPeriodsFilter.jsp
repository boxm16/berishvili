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
            .filter_btn{
                background-color: blue;
                color:white;
                padding: 2px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 20px;
                margin:0px 0px;
                cursor: pointer;
                width:100%; 
                height:100%;

            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <a href="index.htm">საწყისი გვერდი</a>
            <a href="tripPeriods.htm?blockIndex=${currentBlockIndex}">უკან დაბრუნება</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>

        <br><br>     
    <center> <h1>ბრუნების დროების ფილტრები</h1></center>

    <form action="tripPeriodsFilter.htm" method="POST">
        <input type="hidden" name="triggerFilter" id="triggerFilter" value="0">
        <input type="hidden" name="blockIndex"  value="${blockIndex}">
        <table>
            <thead >
                <tr>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('routeNumbers')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('dateStamps')"/>

                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('busNumbers')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('exodusNumbers')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('driverNames')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('tripPeriodTypes')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('startTimesScheduled')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('startTimesActual')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('arrivalTimesScheduled')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('arrivalTimesActual')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('tripPeriodTimesScheduled')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('tripPeriodTimesActual')"/>
                    </td>
                    <td>  
                        <input type="submit" value="ფ" class="filter_btn" onclick="DoSubmit('tripPeriodTimeDifferences')"/>
                    </td>
                </tr>
                <tr>
                    <th>მარშრ-<br>უტის<br>#</th>
                    <th>თარიღი</th>
                    <th>ავტობ-<br>უსის #</th>
                    <th>გასვ-<br>ლის<br> #</th>
                    <th>მძღოლი</th>
                    <th>მიმართ-<br>ულება</th>
                    <th>გასვლის<br>გეგმიური<br>დრო</th>
                    <th>გასვლის<br>ფაქტიური<br>დრო</th>
                    <th>მისვლის<br>გეგმიური<br>დრო</th>
                    <th>მისვლის<br>ფაქტიური<br>დრო</th>
                    <th>წირის<br>გეგმიური<br>დრო</th>
                    <th>წირის<br>ფაქტიური<br>დრო</th>
                    <th>სხვაობა</th>
                </tr>
            </thead>
            <tbody> 

                <tr> 
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> </td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>
                    <td><input type="checkbox" onclick="check(event, 0)" checked="true"> ყველა</td>

                </tr> 


                <tr>
                    <td>
                        <table>
                            <tbody style="height:500px; overflow-y:scroll; display:block;">
                                <c:forEach var="item" items="${tripPeriodsFilter.routeNumbers}">
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
                    </td>
                    <td>
                        <table>
                            <tbody style="height:500px; overflow-y:scroll; display:block;">

                                <c:forEach var="item" items="${tripPeriodsFilter.dateStamps}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="dateStamps" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="busNumbers" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="exodusNumbers" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="driverNames" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="tripPeriodTypes" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="startTimesScheduled" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="startTimesActual" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="arrivalTimesScheduled" class="dates"  ${item.value} value="${item.key}"> 
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
                                            <input type="checkbox" name="arrivalTimesActual" class="dates"  ${item.value} value="${item.key}"> 
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
                                <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimesScheduled}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="tripPeriodTimesScheduled" class="dates"  ${item.value} value="${item.key}"> 
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
                                <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimesActual}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="tripPeriodsTimeActual" class="dates"  ${item.value} value="${item.key}"> 
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
                                <c:forEach var="item" items="${tripPeriodsFilter.tripPeriodTimeDifferences}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="tripPeriodTimesDifference" class="dates"  ${item.value} value="${item.key}"> 
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
    </form>
    <script>
        function DoSubmit(triggerFilter) {
            document.getElementById("triggerFilter").value = triggerFilter;
            return true;
        }
    </script>

</body>
</html>
