<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>გამოთვლები</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        გამოთვლისთვის გამოიყენება მხოლოდ A-B და B-A წირები, რომლების გეგმიური და ფაქტიური დროს შორის აცდენა უდრის ან ნაკლებია( =< ) მითუთებულ ფროცენტს(ზე)

        <nav class="navbar navbar-expand-lg navbar-light bg-light">

            <button type="button" class="btn btn-outline-success">
                <span> <a href="index.htm">საწყისი გვერდი</a> </span>
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-outline-success">
                <span> <a href="tripPeriodsRequest.htm?requestedPage=1">ბრუნების დროების დათვალიერება</a> </span>
            </button>

            &nbsp;&nbsp;&nbsp;&nbsp;

            <form action="tripPeriodsCalculatePercentage.htm" method="POST" class="form-inline my-2 my-lg-0">
                <input name="percents" class="form-control mr-sm-2" type="number" value="20" >
                <button class="btn btn-primary my-2 my-sm-0" type="submit">გამოთვლა</button>
            </form>

            &nbsp  &nbsp  &nbsp 
            <button type="button" class="btn btn-outline-success">
                <span> <a href="tripPeriodsExcelExportDashboard.htm">ექსელში ექსპორტი</a> </span>
            </button>

        </nav>
        <div class="container">

            <div class="row">

                <div class="col-lg">
                    <hr>
                    <table style="text-align:center; font-size:25px">
                        <tbody>
                            <c:forEach var="entry" items="${routesAverages}">
                                <tr>
                                    <td rowspan="2">
                                        ${entry.value.routeNumber}
                                    </td>
                                    <td>
                                        ${entry.value.abLowCount}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                    ${entry.value.baLowCount} 
                                    </td>
                                </tr>
                            </c:forEach> 
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
