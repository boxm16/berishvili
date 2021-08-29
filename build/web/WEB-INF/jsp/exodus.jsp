<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>გასვლის დეტალები</title>
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
                width: 100%;
                padding: 5px;
                min-height: 100vh;
                transition: all 0.3s;
                position: absolute;
                top: 0;
            }



            /* ---------------------------------------------------
                MEDIAQUERIES
            ----------------------------------------------------- */

            @media (max-width: 768px) {

                #sidebar.active {
                    margin-left: 0;
                }
                #content {
                    width: 100%;
                }
                #content.active {
                    width: calc(100% - 150px);
                }

            }
            /* ---------------------------------------------------
                          
            ----------------------------------------------------- */

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
                background-color: blue;
                color: #fff;
                border:solid black 1px;
            }

        </style>
    </head>
    <body>




        <!-- Page Content  -->
        <div id="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">

                <div style="position: relative;  left: 250px;"><h2>${exodusHeader}</h2></div>

            </nav>
            <div >
                <table style="text-align: center;">
                    <thead>
                        <tr>
                            <th>გეგმიუირი<br>გასვლის<br>დრო</th>
                            <th>ფაქტიური<br>გასვლის<br>დრო</th>
                            <th>სხვაობა</th>
                            <th>მიმართულება</th>
                            <th>გეგმიუირი<br>მისვლის<br>დრო</th>
                            <th>ფაქტიური<br>მისვლის<br>დრო</th>
                            <th>სხვაობა</th>
                            <th>link</th>
                            <th>წირის<br>გეგმიური<br>დრო</th>
                            <th>წირის<br>ფაქტიური<br>დრო</th>
                            <th>სხვაობა</th>
                            <th>დგომის<br>გეგმიური<br>დრო</th>   
                            <th>დგომის<br>ფაქტიური<br>დრო</th>
                            <th>'დაკარგული<br>დრო'</th>
                            <th>GPS<br>ინტერვალი</th>
                            <th>ინტერვალების<br>ლინკი</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan='16' align="center">
                                მარშრუტა #: ${detailedRoute.number}
                            </td>
                        </tr>
                        <c:forEach var="day" items="${detailedRoute.days}">
                            <tr>
                                <td colspan='16' align="center">
                                    თარიღი: ${day.value.dateStamp}
                                </td>
                            </tr>
                            <c:forEach var="exodus" items="${day.value.exoduses}">
                                <tr>
                                    <td colspan='16' align="center">
                                        გასვლა #: ${exodus.value.number}
                                    </td>
                                </tr>
                                <c:forEach var="tripVoucher" items="${exodus.value.tripVouchers}">

                                    <tr>
                                        <td colspan='16' align="center">
                                            მარშრუტა #: ${detailedRoute.number}
                                            თარიღი: ${day.value.dateStamp}
                                            გასვლა #: ${exodus.value.number}
                                            შენიშვნები: ${tripVoucher.value.notes}
                                        </td>
                                    </tr>
                                    <c:forEach var="tripPeriod" items="${tripVoucher.value.tripPeriods}">
                                        <tr style="background-color:${tripPeriod.getStartTimeScheduled()==anchor ? "lightgreen" : "inherited" };">


                                            <td align="center">${tripPeriod.getStartTimeScheduledString()} </td>
                                            <td align="center">${tripPeriod.getStartTimeActualString()} </td>
                                            <td align="center" style='background-color: ${tripPeriod.getStartTimeDifferenceColor()}'>${tripPeriod.getStartTimeDifference()}</td>
                                            <td align="center">${tripPeriod.typeG} </td>
                                            <td align="center">${tripPeriod.getArrivalTimeScheduledString()} </td>
                                            <td align="center">${tripPeriod.getArrivalTimeActualString()} </td>
                                            <td align="center" style='background-color: ${tripPeriod.getArrivalTimeDifferenceColor()}'>${tripPeriod.getArrivalTimeDifference()}</td>

                                            <td align="center">link</td>
                                            <td align="center">${tripPeriod.getTripPeriodTimeScheduledString()} </td>
                                            <td align="center">${tripPeriod.getTripPeriodTimeActualString()} </td>
                                            <td align="center" style="background-color: ${tripPeriod.getTripPeriodTimeDifferenceColor()}">${tripPeriod.getTripPeriodTimeDifferenceString()} </td>
                                            <td align="center">${tripPeriod.getHaltTimeScheduledString()}</td>
                                            <td align="center">${tripPeriod.getHaltTimeActualString()}</td>
                                            <td align="center" style="background-color:${tripPeriod.getLostTimeColor()} ">${tripPeriod.lostTimeString}</td>
                                            <td align="center">${tripPeriod.getGpsIntervalString()}</td>
                                            <td align="center">link</td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </c:forEach>
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
