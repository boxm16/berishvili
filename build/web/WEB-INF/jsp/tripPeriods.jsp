<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                width: calc(100% - 250px);
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
                background-color: #D170F7;
                color: #fff;
                border:solid black 1px;
            }

            /* ---------------------------------------------------
                          tool tip styling          
            ----------------------------------------------------- */
        </style>
    </head>

    <body>
        <div class="wrapper">
            <!-- Sidebar  -->
            <nav id="sidebar">
                <div class="sidebar-header" style="background-color: blue">
                    <h3> <a href="index.htm">საწყისი გვერდი</a> </h3>
                </div>
                <div class="sidebar-header">
                    <h4>მარშრუტების ბლოკები</h4>
                </div>

                <ul class="list-unstyled components">
                    <c:forEach var="routesBlock" items="${selectedRoutesBlocks}" varStatus="loop">
                        <li >
                            <a href="tripPeriods.htm?blockIndex=${loop.index}">${routesBlock.name}</a>
                        </li>
                    </c:forEach> 

                    <li >
                        <a href="#" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">საგარანტიოები</a>
                        <ul class="collapse list-unstyled" id="homeSubmenu">
                            <li>
                                <a href="#">Home 1</a>
                            </li>
                            <li>
                                <a href="#">Home 2</a>
                            </li>
                            <li>
                                <a href="guarantyTripsUploadPage.htm"">ავტობუსების მარშრუტების ანალიზი</a>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Pages</a>
                        <ul class="collapse list-unstyled" id="pageSubmenu">
                            <li>
                                <a href="#"></a>
                            </li>
                            <li>
                                <a href="#">Page 2</a>
                            </li>
                            <li>
                                <a href="#">Page 3</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                </ul>

                <ul class="list-unstyled CTAs">
                    <li>
                        <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download source</a>
                    </li>
                    <li>
                        <a href="https://bootstrapious.com/p/bootstrap-sidebar" class="article">Back to article</a>
                    </li>
                </ul>
            </nav>

            <!-- Page Content  -->
            <div id="content">

                <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
                    <div class="container-fluid">
                        <button type="button" id="sidebarCollapse" class="btn btn-info">
                            <i class="fas fa-align-left"></i>
                            <span>Toggle Sidebar</span>
                        </button>
                        <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fas fa-align-justify"></i>
                        </button>

                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="nav navbar-nav ml-auto">
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Page</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Page</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Page</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <table>
                    <thead>
                        <tr>
                            <th>მარშრუტის #</th>
                            <th>თარიღი</th>
                            <th>ავტობუსის #</th>
                            <th>გასვლის #</th>
                            <th>მძღოლი</th>
                            <th>მიმართულება</th>
                            <th>გასვლის<br>გეგმიური<br>დრო</th>
                            <th>გასვლის<br>ფაქტიური<br>დრო</th>
                            <th>მისვლის<br>გეგმიური<br>დრო</th>
                            <th>მისვლის<br>ფაქტიური<br>დრო</th>
                            <th>წირის<br>გეგმიური<br>დრო</th>
                            <th>წირის<br>ფაქტიური<br>დრო</th>
                            <th>სხვაობა</th>
                            <!--
                <th title="მარშრუტის #" >მრშრ.#</th>
                <th title="თარიღი">თარიღი</th>
                <th title="ავტობუსის #">ავტობუსის #</th>
                <th title="გასვლის #">გას.#</th>
                <th title="მძღოლი">მძღოლი</th>
                <th title="მიმართულება">მიმართულება</th>
                <th title="გასვლის გეგმიური დრო">გ.გ.დ.</th>
                <th title="გასვლის ფაქტიური დრო">გ.ფ.დ.</th>
                <th title="მისვლის გეგმიური დრო">მ.გ.დ.</th>
                <th title="მისვლის ფაქტიური დრო">მ.ფ.დ.</th>
                <th title="წირის გეგმიური დრო">წ.გ.დ</th>
                <th title="წირის ფაქტიური დრო">წ.ფ.დ.</th>
                <th title="სხვაობ">სხვაობა</th>
                            -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="routeEntrySet" items="${routes}">


                            <c:forEach var="dayEntrySet" items="${routeEntrySet.value.days}">

                                <c:forEach var="exodusEntrySet" items="${dayEntrySet.value.exoduses}">




                                    <c:forEach var="tripVouchersEntry" items="${exodusEntrySet.value.tripVouchers}">
                                        <c:forEach var="tripPeriod" items="${tripVouchersEntry.value.tripPeriods}">
                                            <tr>
                                                <td>${routeEntrySet.value.number} </td>
                                                <td>${dayEntrySet.value.dateStamp}</td>
                                                <td>${tripVouchersEntry.value.number}</td>
                                                <td>${exodusEntrySet.value.number}</td>                                        
                                                <td>${tripVouchersEntry.value.driverName}</td>
                                                <td>${tripPeriod.getTypeG()}</td>
                                                <td>${tripPeriod.getStartTimeScheduledString()}</td>
                                                <td>${tripPeriod.getStartTimeActualString()}</td>
                                                <td>${tripPeriod.getArrivalTimeScheduledString()}</td>
                                                <td>${tripPeriod.getArrivalTimeActualString()}</td>
                                                <td>${tripPeriod.getTripPeriodTimeScheduledString()}</td>
                                                <td>${tripPeriod.getTripPeriodTimeActualString()}</td>
                                                <td style="background-color: ${tripPeriod.getTripPeriodTimeDifferenceColor()}">${tripPeriod.getTripPeriodTimeDifferenceString()}</td>


                                            </tr>

                                        </c:forEach>

                                    </c:forEach>


                                </c:forEach>


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
