<%-- 
    Document   : guarantyTripsUploadPage
    Created on : Jun 7, 2021, 11:22:22 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>საგარანტიო ბრუნები: ფაილის ატვირთვა</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <a href="index.htm">საწყის გვერდზე გადასვლა</a>
                    <hr>
                    <center>  <h1>საგარანტიო ბრუნები: ფაილის ატვირთვა</h1></center>
                    <form action="saveGuarantyExcelFile.htm" method="post" enctype="multipart/form-data">

                        <center>
                            <table >
                                <tr>
                                    <td style="color:red">
                                <center><h2>${status}</h2></center>
                                </td>
                                </tr>
                                <tr>
                                    <td style="color:red">
                                <center><h2>${errorMessage}</h2></center>
                                </td>
                                </tr>
                                <tr>
                                    <td>
                                <center><h1>აირჩიე ასატვირთი ფაილი</h1></center>
                                </td>
                                </tr>
                                <tr><td style="height: 20px"></td></tr>
                                <tr>
                                    <td>

                                <center> <input   type="file" name="file"></center>
                                </td>
                                </tr>
                                <tr><td style="height: 20px"></td></tr>
                                <tr>
                                    <td>
                                <center><input class="btn btn-lg btn-primary" type="submit" value="ატვირთვა" name="submit"></center>  
                                </td>
                                </tr>

                                <tr><td style="height: 150px"></td></tr>
                            </table>
                        </center>
                    </form>
                    <center>
                        <c:if test = "${uploadedFileExists}">
                            <a class="btn btn btn-outline-success" href="gotGuarantyDashboard.htm" role="button">წინად ატვირთული ფაილის გამოყენება</a>
                        </c:if>
                    </center>
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


    </body>
</html>
