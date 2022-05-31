<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>გამოთვლების მენიუ</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <style>
            tr, td, th{
                border:solid black 1px;
                font-size: 15px;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <center>
                        <h1>გამოთვლების მენიუ</h1>
                        <h3>გამოთვლისთვის გამოიყენება მხოლოდ A-B და B-A წირები, რომლების გეგმიური და ფაქტიური დროს შორის აცდენა უდრის ან ნაკლებია( =< ) მითუთებულ ფროცენტს(ზე) </h3>

                    </center>
                    <hr>


                    <center>
                        <table >
                            <tbody>
                                <tr>
                                    <td>
                            <center>  <h2>  ჩაწერე სასურველი პროცენტი </h2></center>
                            </td>
                            </tr>
                            <tr>
                                <td> <form id="form" action="" method="POST" >
                                        <h2> 
                                            <input name="percents" class="form-control" type="number" style="font-size: 30px" value="${percents}" >  
                                        </h2>
                                    </form>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <button class="btn btn-primary "  style="dispaly:block;  width:100%;" onclick="requestRouter('tripPeriodsCalculatePercentage.htm')"><h2>გამოთვალე</h2></button> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h3>  <button class="btn btn-success "  style="dispaly:block;  width:100%;" onclick="requestRouter('tripPeriodsCalculatePercentagePerDriver.htm')"><h2>გამოთვალე მძღოლების მიხედვით</h2></button> </h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h3>  <button class="btn btn-info" style="dispaly:block;  width:100%;" onclick="requestRouter('tripPeriodsFilteredByPercentageRequest.htm')"><h2>გამოყენებული ბრუნების გამოსახვა</h2></button> </h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h3> <button class="btn btn-secondary" style="dispaly:block;  width:100%;" onclick="requestRouter('tripPeriodsInitialRequest.htm')"><h2>ყველა ბრუნების გამოსახვა</h2></button> </h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <h3> <button class="btn btn-warning" style="dispaly:block;  width:100%;" onclick="requestRouter('tripPeriodsExcelExportDashboard.htm')"><h2>ექსელში ექსპორტი</h2></button> </h3>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </center>



                </div>
            </div> 
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script>
                                        function requestRouter(requestTarget) {
                                            form.target = "_blank";
                                            form.action = requestTarget;
                                            form.submit();
                                        }
        </script>
    </body>
</html>
