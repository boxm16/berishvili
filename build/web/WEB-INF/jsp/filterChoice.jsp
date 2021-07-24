

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            button {
                font-size: 25px;
            }
        </style>
    </head>
    <body>
        <h1>Filter Choice here</h1>
        <hr>
        <table>
            <tr>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="filter.htm?target=routeNumber">Route Numbers Filter</a> </span>
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger">
                        <span> <a href="filter.htm?target=dateStamp">Date Stamp Filter</a> </span>
                    </button>
                </td>
            </tr>
        </table>
    </body>
</html>
