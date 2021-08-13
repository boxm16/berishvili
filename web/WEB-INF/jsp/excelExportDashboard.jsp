

<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>ექსელში ექსპორტი</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <a href="index.htm">საწყის გვერდზე გადასვლა</a>
                    <hr><center><h2>ჩაწერე ფაილის დასახელება და დააჭირე 'ექსელში ექსპორტი'</h2><br>

                    </center>
                    <hr>
                    <form  action="${excelExportLink}" method="POST">
                        <center><input type="text" name="fileName" size="70"></center>
                        <hr>
                        <center><button type="submit" class="btn btn-warning ">ექსელში ექსპორტი</button></center>
                    </form>

                    ${error} 
                    <a href="downloadFile.htm?fileName=${fileName}.xlsx">${fileName}</a> <br>
                    <hr>

                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    </body>
</html>
