<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ა/ბ დარღვევები</title>
        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">

        <!-- Font Awesome JS -->
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
        <style>

            /*
        DEMO STYLE
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
                          CONTENT STYLE
                      ----------------------------------------------------- */

            #content {
                width: calc(100% - 150px);
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
                background-color: yellow;
                color: black;
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
            <!-- Page Content  -->
            <div id="content">
                <center> <h1>ა/ბ დარღვევები </h1></center>
                <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
                    <div class="container-fluid">

                        <button type="button" class="btn btn-outline-success">
                            <span> <a href="misconductsRedirect.htm">ხარვეზების დარღვევების<br>დათვალიერება</a> </span>
                        </button>
                        &nbsp;
                        <button type="button" class="btn btn-outline-success">
                            <span> <a href="firstTripMisconductMinusVersionRedirect.htm">ა/ბ დარღვევების დათვალიერება<br>(ნაადრევად გასვლების ჩათვლით)</a> </span>
                        </button>
                        &nbsp;
                        <form action="firstTripMisconduct.htm" method="POST" class="form-inline my-2 my-lg-0">
                            <input name="misconductTimeBound" class="form-control mr-sm-2" type="number" value="${misconductTimeBound}" >
                            <button class="btn btn-primary my-2 my-sm-0" type="submit">წუთიანი დაგვიანებების<br>გამოსახვა</button>
                        </form>

                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            &nbsp  &nbsp  &nbsp 
                            <button type="button" class="btn btn-warning">
                                <span> <a href="misconductsExcelExportDashboard.htm">ექსელში ექსპორტი</a> </span>
                            </button>
                        </div>
                    </div>
                </nav>

                <table>
                    <thead>
                        <tr>
                            <th>თარიღი</th>
                            <th>ავტობაზა</th>

                            <th>მარშრუ-<br>ტის #</th>
                            <th>გასვლის<br>#</th>

                            <th>ავტობუსის #</th>

                            <th>გეგმიუირი<br>გასვლის<br>დრო</th>
                            <th>ფაქტიური<br>გასვლის<br>დრო</th>
                            <th>სხვაობა</th>

                            <th>ბაზიდან<br>გეგმიუირი<br>გასვლის<br>დრო</th>
                            <th>ბაზიდან<br>ფაქტიური<br>გასვლის<br>დრო</th>
                            <th>სხვაობა</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tripPeriod" items="${misconductedFirstTripPeriods}">
                            <tr style="background-color:${tripPeriod.getColor()} ">
                                <td align="center">${tripPeriod.dateStamp} </td>
                                <td align="center"> ${tripPeriod.baseNumber}</td>
                                <td align="center">${tripPeriod.routeNumber} </td>
                                <td align="center"><a href="exodus.htm?routeNumber=${tripPeriod.routeNumber}&dateStamp=${tripPeriod.dateStamp}&exodusNumber=${tripPeriod.getExodusNumber()}&startTimeScheduled=${tripPeriod.startTimeScheduled}"  target="_blank">${tripPeriod.exodusNumber}</a> </td>
                                <td>${tripPeriod.busNumber}</td>
                                <td> ${tripPeriod.getStartTimeScheduledString()}</td>
                                <td> ${tripPeriod.getStartTimeActualString()}</td>
                                <td> ${tripPeriod.getStartTimeDifference()}</td>
                                <td> ${tripPeriod.getBaseTripStartTimeScheduledString()}</td>
                                <td> ${tripPeriod.getBaseTripStartTimeActualString()}</td>
                                <td> ${tripPeriod.getBaseTripStartTimeDifference()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <hr>
                <center>

                    ${tripPeriodsPager.display}

                </center>
                <hr>

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

        <script type="text/javascript">
            $(document).ready(function () {
                $("#sidebar").mCustomScrollbar({
                    theme: "minimal"
                });

                $('#sidebarCollapse').on('click', function () {
                    $('#sidebar, #content').toggleClass('active');
                    $('.collapse.in').toggleClass('in');
                    $('a[aria-expanded=true]').attr('aria-expanded', 'false');
                });
            });



        </script>
    </body>
</html>
