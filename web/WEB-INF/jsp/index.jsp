<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>საწყისი გვერდი</title>

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
                padding: 15px 10px;
                background: #fff;
                border: none;
                border-radius: 0;
                margin-bottom: 40px;
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
                width: 250px;
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
                margin-left: -250px;
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
                background: #59c1d4;
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
                width: calc(100% - 250px);
                padding: 40px;
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
                    margin-left: -250px;
                }
                #sidebar.active {
                    margin-left: 0;
                }
                #content {
                    width: 100%;
                }
                #content.active {
                    width: calc(100% - 250px);
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

            tr  {
                border:solid black 1px;
            }
        </style>
    </head>

    <body>

        <div class="wrapper">
            <!-- Sidebar  -->


            <!-- Page Content  -->
            <div id="content">

                <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
                    <div class="container-fluid">

                        <a class="btn btn-primary" href="upload.htm" style="font-size: 20px">ახალი ფაილის ატვირთვა</a>


                        <div style="margin-left: 5%;"> 
                            <table>
                                <thead>
                                <th>
                                    <input type="checkbox" id="mainCheckBox" style="width:28px;height:28px"  checked="true" onclick="selectRouteAll(event)">
                                </th>
                                <th style="width:200px">
                                    ყველა
                                </th>
                                <th>
                                    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#<?php echo $routeNumber; ?>" aria-expanded="false" aria-controls="<?php echo $routeNumber; ?>">
                                        +
                                    </button>
                                </th>
                                </thead>
                            </table>
                        </div>
                        &nbsp; &nbsp; &nbsp;
                        <div>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                                თარიღის ერთიანად მონიშვნა
                            </button>
                        </div>

                        <!-- this part is juts to keep space -->

                        <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fas fa-align-justify"></i>
                        </button>


                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="nav navbar-nav ml-auto">

                                <li class="nav-item">
                                    <a class="nav-link"></a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" ></a>
                                </li>
                            </ul>
                        </div>
                        <!-- end of the  part that  is just to keep space -->
                    </div>
                </nav>
                <div style="margin-left: 20%;">

                    <h3>აირჩიე მარშრუტი და რიცხვები</h3>
                    <br>
                    <c:forEach var="entry" items="${routes}">

                        <table style="text-align:center; font-size:25px">
                            <thead>
                                <tr>
                                    <th>
                                        <input type="checkbox" class="routes" id="routeCheckBox:${entry.value.number}"  onclick="selectRouteAllDates(event, '${entry.value.number}')" style="width:28px;height:28px"  checked="true">
                                    </th>
                                    <th style="width:400px">
                                        მარშრუტი #${entry.value.number} 
                                    </th>
                                    <th>
                                        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#${entry.value.number} " aria-expanded="false" aria-controls="${entry.value.number}">
                                            +
                                        </button>
                                    </th>
                                </tr>
                            </thead>
                        </table>

                        <div class="collapse" id="${entry.value.number}">

                            <table style="text-align:center; font-size:20px" id="daysOfRoute:${entry.value.number}">
                                <tbody>

                                    <c:forEach var="dayEntry" items="${entry.value.days}">
                                        <tr>
                                            <td>
                                                &nbsp&nbsp&nbsp
                                            </td>
                                            <td>
                                                <input type="checkbox" class="route_date" id=" ${dayEntry.value.getDateStampWeekFormat()}" checked="true" value="${entry.value.number}:${dayEntry.value.getDateStamp()}" onclick="checkDayCheckBoxes(event)"> 
                                            </td>
                                            <td colspan="2">
                                                &nbsp&nbsp ${dayEntry.value.getDateStampWeekFormat()}
                                            </td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>


                        </div>

                    </c:forEach> 
                </div>
            </div>
            <nav id="sidebar">
                <div class="sidebar-header" style="background-color: coral">
                    <h3> <a href="http://berishvili.eu5.org">ძველ სერვერზე გადასვლა</a> </h3>
                </div>
                <div class="sidebar-header">
                    <!--- <h3>ფუნქციები</h3> ---->
                </div>

                <ul class="list-unstyled components">

                    <li>
                        <a href="#detailedRoutesSubmenue" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>ბრუნები დეტალურად</h3></a>
                        <ul class="collapse list-unstyled" id="detailedRoutesSubmenue">
                            <li>
                                <a href="#" onclick="requestRouter('detailedRoutesInitialRequest.htm')"><h4>დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('detailedRoutesExcelExportDashboardInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#intervalsSubmenue" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>ინტერვალები</h3></a>
                        <ul class="collapse list-unstyled" id="intervalsSubmenue">
                            <li>
                                <a href="#" onclick="requestRouter('intervalsInitialRequest.htm')"><h4>დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('intervalsExcelExportDashboardInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#routeTimesSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>ბრუნების დროები</h3></a>
                        <ul class="collapse list-unstyled" id="routeTimesSubmenu">
                            <li>
                                <a href="#" onclick="requestRouter('tripPeriodsInitialRequest.htm')"><h4>დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('tripPeriodsCalculationsInitialRequest.htm')"><h4>გამოთვლები</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('tripPeriodsCalculationsPerDriver.htm')"><h4>გამოთვლები მძღოლების მიხედვით</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('tripPeriodsExcelExportDashboardInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#guarantySubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>საგარანტიოები</h3></a>
                        <ul class="collapse list-unstyled" id="guarantySubmenu">
                            <li>
                                <a href="#" onclick="requestRouter('guarantyTripsInitialRequest.htm')"><h4>დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('guarantyTripsExcelExportDashboardInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#baseReturnsSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>შესვლების ანალიზი</h3></a>
                        <ul class="collapse list-unstyled" id="baseReturnsSubmenu">
                            <li>
                                <a href="#" onclick="requestRouter('baseReturnsInitialRequest.htm')"><h4>დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('baseReturnsExcelExportDashboardInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#misconductsSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><h3>დარღვევები</h3></a>
                        <ul class="collapse list-unstyled" id="misconductsSubmenu">
                            <li>
                                <a href="#" onclick="requestRouter('misconducts.htm')"><h4>ხაზზე დარღვევების დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('firstTripMisconductInitialRequest.htm')"><h4>ა/ბ დარღვევების დათვალიერება</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('firstTripMisconductMinusVersionInitialRequest.htm')"><h4>ა/ბ დარღვევების დათვალიერება (ნაადრევად გასვლების ჩათვლით)</h4></a>
                            </li>
                            <li>
                                <a href="#" onclick="requestRouter('misconductsExcelExportInitialRequest.htm')"><h4>ექსელში ექსპორტი</h4></a>
                            </li>

                        </ul>
                    </li>
                    <hr>
                    <li>
                        <a href="#" onclick="requestRouter('haltMisconducts.htm')"><h4>პუნკტში ერთად დგომის შემთხვევები</h4></a>
                    </li>
                    <hr>
                    <li>
                        <a href="guarantyTripsUploadPage.htm""><h4>ავტობუსების მარშრუტების ანალიზი</h4></a>
                    </li>


                </ul>



                <ul class="list-unstyled CTAs">
                    <li>
                        <a href="routes.htm"><h3>მარშრუტები</h3></a>
                    </li>
                </ul>
                <ul class="list-unstyled CTAs">
                    <li>
                        <a href="graphicalRouteInitialRequest.htm" target="_blank"><h3>გრაფიკული გამოსახვა</h3></a>
                    </li>
                </ul>
            </nav>
        </div>
        <form id="form" action="#" method="POST">
            <input hidden type="text" id="routes_dates" name="routes:dates">
        </form>

        <!-- Modal -->
        <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">აირჩიე  თარიღი ყველა მარშრუთისვის</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <table style="text-align:center; font-size:20px">
                            <tbody>

                                <c:forEach var="dateEntry" items="${dates.dates}">
                                    <tr>
                                        <td>
                                            &nbsp&nbsp&nbsp
                                        </td>
                                        <td>
                                            <input type="checkbox" class="dates" checked="true" value="${dateEntry.value.dateStampExcelFormat}" onclick="checkDate(event)"> 
                                        </td>
                                        <td style="color: ${dateEntry.value.dayColor}">
                                            ${dateEntry.value.dateStampExcelFormat}
                                        </td>
                                        <td style="color: ${dateEntry.value.dayColor}">
                                            ${dateEntry.value.dayOfWeek}
                                        </td>
                                    </tr>
                                </c:forEach> 
                            </tbody>
                        </table>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
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


                                                function selectRouteAll(event) {

                                                    var bigCheckBox = event.target;
                                                    var allCheckBoxes = document.querySelectorAll("input[type=\"checkbox\"]");
                                                    for (x = 0; x < allCheckBoxes.length; x++) {
                                                        allCheckBoxes[x].checked = bigCheckBox.checked;
                                                    }
                                                }

                                                function selectRouteAllDates(event, routeNumber) {
                                                    console.log(routeNumber);
                                                    var checkbox = event.target;
                                                    var table = document.getElementById("daysOfRoute:" + routeNumber);
                                                    var targetCheckBoxes = table.querySelectorAll("input[type=\"checkbox\"]");

                                                    for (x = 0; x < targetCheckBoxes.length; x++) {
                                                        targetCheckBoxes[x].checked = checkbox.checked;
                                                    }
                                                    checkRouteCheckBoxes();

                                                }

                                                function checkRouteCheckBoxes() {
                                                    var targetCheckBoxes = document.querySelectorAll(".routes");
                                                    for (x = 0; x < targetCheckBoxes.length; x++) {
                                                        if (targetCheckBoxes[x].checked) {
                                                            //do nothing
                                                        } else {
                                                            mainCheckBox.checked = false;
                                                            return;
                                                        }
                                                    }
                                                    mainCheckBox.checked = true;
                                                }

                                                function checkDateCheckBoxes(event) {

                                                    var targetTable = event.target.parentNode.parentNode.parentNode.parentNode;

                                                    var targetTableFullId = targetTable.id;
                                                    var targetTableIdArray = targetTableFullId.split(":");
                                                    var routeNumber = targetTableIdArray[1];

                                                    var routeCheckBox = document.getElementById("routeCheckBox:" + routeNumber);

                                                    var routeDatesCheckBoxes = targetTable.querySelectorAll(".route_date");

                                                    for (x = 0; x < routeDatesCheckBoxes.length; x++) {
                                                        if (routeDatesCheckBoxes[x].checked) {
                                                            //do nothing
                                                        } else {
                                                            routeCheckBox.checked = false;
                                                            checkRouteCheckBoxes();
                                                            return;
                                                        }
                                                    }
                                                    routeCheckBox.checked = true;
                                                    checkRouteCheckBoxes();

                                                }

                                                function checkDate(event) {
                                                    var triggerCheckbox = event.target;
                                                    var targetCheckBoxes = document.querySelectorAll(".route_date");
                                                    console.log(triggerCheckbox.value);
                                                    for (x = 0; x < targetCheckBoxes.length; x++) {
                                                        let triggerCheckboxValue = " " + triggerCheckbox.value;//i dont have idea why triggerCHeckboxValue adds empty string in front
                                                        let targetCheckBoxeValue = targetCheckBoxes[x].getAttribute("id");
                                                        if (triggerCheckboxValue == targetCheckBoxeValue) {

                                                            targetCheckBoxes[x].checked = triggerCheckbox.checked;
                                                        }
                                                    }

                                                }
                                                ////--------------------
                                                function requestRouter(requestTarget) {
                                                    form.target = "_blank";
                                                    form.action = requestTarget;
                                                    routes_dates.value = collectSellectedCheckBoxes();
                                                    console.log(form.action);
                                                    form.submit();
                                                }
                                                //this function collects all checked checkbox values, concatinates them in one string and returns that string to send it after by POST method to server
                                                function collectSellectedCheckBoxes() {
                                                    var returnValue = "";
                                                    var targetCheckBoxes = document.querySelectorAll(".route_date");
                                                    for (x = 0; x < targetCheckBoxes.length; x++) {
                                                        if (targetCheckBoxes[x].checked)
                                                            returnValue += targetCheckBoxes[x].value + ",";
                                                    }
                                                    return returnValue;
                                                }
        </script>
    </body>

</html>