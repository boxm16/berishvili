<%-- 
    Document   : readSuccess
    Created on : May 30, 2021, 3:08:41 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Excel file has been read successfully</h1>
        <jsp:text>
            List Size : ${data.size()}
        </jsp:text>
        <hr>
        <jsp:text>
            List Size : ${data["A35"]}
            List Size : ${data["B35"]}
            List Size : ${data["c35"]}
            List Size : ${data["D35"]}
            List Size : ${data["E35"]}
        </jsp:text>
    </body>
</html>
