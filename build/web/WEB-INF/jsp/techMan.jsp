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
        <a href="index.htm" >GO TO INDEX</a>
        <hr>
        <jsp:text>
            Uploads Directory Exists At Path  ${directoryPath}:  ${uploadsDirectoryExists} :
        </jsp:text>
        <a href="createUploadDirecotry.htm">Create Upload Directory</a>
        <hr>
        <jsp:text>
            Downloads Directory Exists At Path  ${directoryPath}:  ${downloadsDirectoryExists} :
        </jsp:text>
        <a href="createDownloadDirecotry.htm">Create Download Directory</a>
        &nbsp; &nbsp;

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
        <hr><hr>
        <h1>Database Manipulations</h1>
        <hr>
        <a href="createSchema.htm" >Create Database Schema </a>
        <br> ${schemaCreationStatus}
        <hr>
        <a href="createTables.htm" >Create Tables </a>
        <br>${routeTableCreationStatus}
        <h3>Upload Excel File With Routes Data </h3>
        <h4>Careful, routes that exists in database but are not included in uploaded files will be deleted</h4>
        <form action="uploadRoutesDataFile.htm" method="post" enctype="multipart/form-data">  
            Select File: <input type="file" name="file"/>  
            <input type="submit" value="Upload File"/>  
        </form> 
        ${uploadStatus}
        <br>
        ${dataBaseInsertionStatus}
    </body>
</html>
