<%-- 
    Document   : techMan
    Created on : May 23, 2021, 11:38:35 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tech Man Page</title>
    </head>
    <body>
        <a hreflang="index.htm" >GO TO INDEX</a>

        <jsp:text>
            Uploads Directory Exists At Path  ${directoryPath}:  ${uploadsDirectoryExists} :
        </jsp:text>
        &nbsp; &nbsp;
        <a href="createUploadDirecotry.htm">Create Upload Directory</a>
        <hr>
        ${uploadedFilesList}
        <hr>
        <jsp:text>
            Host Name : ${hostName}
        </jsp:text>
        <hr>
        <form  action="runTest.htm" method="POST">
            <input type="number" name="routesQuantity">
            <input type="submit">
        </form>

    </body>
</html>
