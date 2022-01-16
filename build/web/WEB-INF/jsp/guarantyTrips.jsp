<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>საგარანტიო გასვლები</title>
        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

        <style>

            /*
       !!!!!!!!! this is copied from tripPeriods.jsp and then changed, dont have this for reference !!!!!!!!!!!!
            */

            @import "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700";
            body {
                font-family: 'Poppins', sans-serif;
                background: #fafafa;
            }

            p {
                font-family: 'Poppins', sans-serif;
                font-size: 1.1em;
                font-weight: 300;
                line-height: 1.7em;
                color: #999;
            }

            a,
            a:hover,
            a:focus {
                color: inherit;
                text-decoration: none;
                transition: all 0.3s;
            }

            .navbar {
                padding: 0px 0px;
                background: #fff;
                border: none;
                border-radius: 0;
                margin-bottom: 0px;
                box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
            }

            .navbar-btn {
                box-shadow: none;
                outline: none !important;
                border: none;
            }

            .line {
                width: 100%;
                height: 1px;
                border-bottom: 1px dashed #ddd;
                margin: 40px 0;
            }

            /* ---------------------------------------------------
                SIDEBAR STYLE
            ----------------------------------------------------- */

            .wrapper {
                display: flex;
                width: 100%;
            }

            #sidebar {
                width: 20px;
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                z-index: 999;
                background: #7386D5;
                color: #fff;
                transition: all 0.3s;
            }

            #sidebar.active {
                margin-left: -20px;
            }

            #sidebar .sidebar-header {
                padding: 20px;
                background: #6d7fcc;
            }

            #sidebar ul.components {
                padding: 20px 0;
                border-bottom: 1px solid #47748b;
            }

            #sidebar ul p {
                color: #fff;
                padding: 10px;
            }

            #sidebar ul li a {
                padding: 10px;
                font-size: 1.1em;
                display: block;
            }

            #sidebar ul li a:hover {
                color: #7386D5;
                background: #fff;
            }

            #sidebar ul li.active>a,
            a[aria-expanded="true"] {
                color: #fff;
                background: #6d7fcc;
            }

            a[data-toggle="collapse"] {
                position: relative;
            }

            .dropdown-toggle::after {
                display: block;
                position: absolute;
                top: 50%;
                right: 20px;
                transform: translateY(-50%);
            }

            ul ul a {
                font-size: 0.9em !important;
                padding-left: 30px !important;
                background: #6d7fcc;
            }

            ul.CTAs {
                padding: 20px;
            }

            ul.CTAs a {
                text-align: center;
                font-size: 0.9em !important;
                display: block;
                border-radius: 5px;
                margin-bottom: 5px;
            }

            a.download {
                background: #fff;
                color: #7386D5;
            }

            a.article,
            a.article:hover {
                background: #6d7fcc !important;
                color: #fff !important;
            }

            /* ---------------------------------------------------
                CONTENT STYLE
            ----------------------------------------------------- */

            #content {
                width: calc(100% - 20px);
                padding: 5px;
                min-height: 100vh;
                transition: all 0.3s;
                position: absolute;
                top: 0;
                right: 0;
            }

            #content.active {
                width: 100%;
            }

            /* ---------------------------------------------------
                MEDIAQUERIES
            ----------------------------------------------------- */

            @media (max-width: 768px) {
                #sidebar {
                    margin-left: -150px;
                }
                #sidebar.active {
                    margin-left: 0;
                }
                #content {
                    width: 100%;
                }
                #content.active {
                    width: calc(100% - 150px);
                }
                #sidebarCollapse span {
                    display: none;
                }
            }
            /* ---------------------------------------------------
                          
            ----------------------------------------------------- */
            input[type="checkbox"]{
                width: 20px; /*Desired width*/
                height: 20px; /*Desired height*/
            }

            tr td {
                border:solid black 1px;
            }
            /* ---------------------------------------------------
            for sticky header for table
            -----------------------------------------------------*/
            /* Fixed Headers */

            th {
                position: -webkit-sticky;
                position: sticky;
                top: 40px;
                z-index: 2;

            }

            th[scope=row] {
                position: -webkit-sticky;
                position: sticky;
                left: 0;
                z-index: 1;
            }

            th[scope=row] {
                vertical-align: top;
                color: inherit;
                background-color: inherit;
                background: linear-gradient(90deg, transparent 0%, transparent calc(100% - .05em), #d6d6d6 calc(100% - .05em), #d6d6d6 100%);
            }
            th {
                vertical-align: bottom;
                background-color: #4AE537;
                color: #fff;
                border:solid black 1px;
            }

            /* -----------------
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

        </style>
    </head>
    <body>
        <div class="wrapper">
            <!-- hre goes sidebar, if i ever need it -->
            <div id="content">
                <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
                    <div class="container-fluid">
                        <div>
                            <span><!-- this div is just to maintain centered the next text --> </span>
                        </div>
                        <center><h2>საგარანტიო გასვლები</h2></center>
                        <button type="button" class="btn btn-warning">
                            <span> <a href="guarantyTripsExcelExportDashboard.htm">ექსელში ექსპორტი</a> </span>
                        </button>
                    </div>
                </nav>

                <!-- <a href="guarantyExplanation.htm" target="_blank">განმარტება</a> -->

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
                                    <td  style="width: 100px;" align="center">${guarantyTripPeriod.dateStamp}</td>
                                    <td align="center">${guarantyTripPeriod.baseNumber}</td>
                                    <td align="center">${guarantyTripPeriod.routeNumber}</td>
                                    <td align="center">${guarantyTripPeriod.aPoint}</td>
                                    <td align="center">${guarantyTripPeriod.bPoint}</td>


                                    <td align="center">${guarantyTripPeriod.getTypeG()}</td>
                                    <td align="center">${guarantyTripPeriod.guarantyType}</td>


                                    <td align="center">${guarantyTripPeriod.getGuarantyStartTimeScheduledString()}</td>
                                    <td style="background-color:${guarantyTripPeriod.isSpacialCase()?"red":"inherited"} " align="center">${guarantyTripPeriod.getGuarantyStartTimeActualString()}</td>
                                    <td style="background-color:${guarantyTripPeriod.getGuarantyDifferenceColor()} " align="center">${guarantyTripPeriod.getGuarantyStartTimeDifferenceString()}</td>

                                    <td align="center"><a href="interval.htm?routeNumber=${guarantyTripPeriod.routeNumber}&dateStamp=${guarantyTripPeriod.dateStamp}&tripPeriodType=${guarantyTripPeriod.getType()}&startTimeScheduled=${guarantyTripPeriod.getGuarantyStartTimeScheduled()}" target="_blank">${guarantyTripPeriod.exodusScheduled}</a></td>
                                    <td align="center" style="background-color:${guarantyTripPeriod.exodusScheduled==guarantyTripPeriod.exodusActual?"inherited":"red"} "><a href="interval.htm?routeNumber=${guarantyTripPeriod.routeNumber}&dateStamp=${guarantyTripPeriod.dateStamp}&tripPeriodType=${guarantyTripPeriod.getType()}&startTimeScheduled=${guarantyTripPeriod.getExodusActualStartTimeScheduled()}" target="_blank">${guarantyTripPeriod.exodusActual==0? "":guarantyTripPeriod.exodusActual}</a></td>

                                    <td align="center">${guarantyTripPeriod.driverName}</td>
                                    <td align="center">${guarantyTripPeriod.busNumber}</td>

                                </tr>
                            </c:forEach>

                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- jQuery CDN - Slim version (=without AJAX) -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <!-- Popper.JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        <!-- jQuery Custom Scroller CDN -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>

    </body>
</html>

