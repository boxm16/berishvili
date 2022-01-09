
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ფაილის ატვირთვა</title>
    </head>
    <body>

    <center>
        <a href="index.htm">საწყის გვერდზე გადასვლა</a>
        <hr>
        <h1>  ფაილის ატვირთვა</h1>
        <h2 style="color:red">${uploadStatus}<br>${errorMessage}</h2>
        <form action="savefile.htm" method="post" enctype="multipart/form-data">  
            <h2>შეარჩიეთ ასატვირთი ფაილი: </h2>
            <input type="file" name="file"/>  
            <input type="submit" value="ატვირთვა"/>  
        </form>  
    </center>
</body>
</html>
