<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            table{
                border-collapse: collapse;
            }
            tr td {
                border:solid black 2px;

            }
        </style>
    </head>
    <body>
        <!-- template from my php program  -->
    <center>
        <table>
            <tr>
                <td>
                    <table>
                        <c:forEach var="intervalTripPeriodEntrySet" items="${abTimetable}" varStatus="loop">

                            <tr>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getExodusNumber()}
                                </td>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getStartTimeScheduledString()}
                                </td>

                                <td>
                                    ${intervalTripPeriodEntrySet.value.getArrivalTimeScheduledString()}
                                </td>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getScheduledIntervalString()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td>---</td>
                <td>
                    <table>
                        <c:forEach var="intervalTripPeriodEntrySet" items="${baTimetable}" varStatus="loop">

                            <tr>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getExodusNumber()}
                                </td>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getStartTimeScheduledString()}
                                </td>

                                <td>
                                    ${intervalTripPeriodEntrySet.value.getArrivalTimeScheduledString()}
                                </td>
                                <td>
                                    ${intervalTripPeriodEntrySet.value.getScheduledIntervalString()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </center>
</body>
</html>
