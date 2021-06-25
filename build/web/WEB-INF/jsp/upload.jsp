
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Page</title>
    </head>
    <body>
        ${uploadStatus}<br>${errorMessage}
        <form action="savefile.htm" method="post" enctype="multipart/form-data">  
            Select File: <input type="file" name="file"/>  
            <input type="submit" value="Upload File"/>  
        </form>  

    </body>
</html>
