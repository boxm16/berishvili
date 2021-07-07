<%-- 
    Document   : upload-success
    Created on : May 23, 2021, 11:16:20 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <a href="index.htm">საწყის გვერდზე გადასვლა</a> 
        </h1>
        <hr>
        <h2>
            <jsp:text>
                Upload Status : ${unregisteredRoutesMessage}
            </jsp:text>
        </h2>  
    </body>
</html>
