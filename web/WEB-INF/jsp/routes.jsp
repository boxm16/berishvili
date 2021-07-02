

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>მარშრუტები</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    </head>
    <body>
        <div class="container">
            <div class=""row>
                <div class="col"> 
                    <a href="index.htm">საწყის გვერდზე გადასვლა</a>
                    <hr>
                    <h1>მარშრუტების დასახელებები და სქემები</h1>

                    <table>
                        <c:forEach var="routeEntry" items="${routes}">
                            <tr>
                                <td>
                                    <input name="number" type="text" value="${routeEntry.value.number}" readonly size="3">
                                </td>
                                <td>
                                    <input name="aPoint" type="text" value="${routeEntry.value.aPoint}" size="50">
                                </td>

                                <td>
                                    <input name="bPoint" type="text" value="${routeEntry.value.bPoint}" size="50"> 
                                </td>
                                <td>
                                    <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#schemeWindow" onclick="setSchemeToInput(event)">სქემის ნახვა</button>
                                </td>
                                <td>
                                    <input name="scheme" id="schemeTextArea:${routeEntry.value.number}" hidden value="${routeEntry.value.scheme}" size="250"> 
                                </td>

                                <td>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmationWindow" onclick="collect(event)">შეცვალე</button>
                                </td>
                            </tr>

                        </c:forEach>
                    </table>

                    <!-- Modal -->
                    <form action="saveRoute.htm" method="POST">
                        <div class="modal fade bd-example-modal-lg" id="confirmationWindow" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title" id="exampleModalLongTitle">
                                            დაადასტურე #<input id="routeNumberInSubmitForm" name="routeNumber" type="text" readonly size="1" > მარშრუტიის მონაცემები

                                        </h3>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div> 

                                    <div class="modal-body">
                                        A პუნკტის დასახელება <br><input id="aPoint" name="aPoint"  type="text" readonly size="50" style="font-size:30px">
                                        <br>
                                        B პუნკტის დასახელება <br><input id="bPoint" name ="bPoint"  type="text" readonly size="50" style="font-size:30px">
                                        <br>
                                        სქემა
                                        <textarea class="form-control" id="schemeInput" rows="15" readonly></textarea>

                                        <input id="scheme" name ="scheme"  hidden readonly size="50" style="font-size:30px">
                                        <input name="changeRouteName" hidden>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">გაუქმება</button>
                                        <button type="submit" class="btn btn-primary">დადასტურება</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- end modal -->

                    <!-- Scheme modal -->
                    <div class="modal fade bd-example-modal-lg" id="schemeWindow" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h3 class="modal-title" id="exampleModalLongTitle">
                                        #<input id="routeNumberInSchemeChanger" name="routeNumber" type="text" readonly size="1" > მარშრუტიის სქემა
                                    </h3>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div> 

                                <div class="modal-body">
                                    <textarea class="form-control" id="schemeChanger" rows="15"></textarea>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">გაუქმება</button>
                                    <button class="btn btn-primary" data-dismiss="modal" onclick="returnChangedScheme()">შენახვა</button>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- END of Scheme modal -->
                </div>
            </div>
        </div>



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        <script>
                                        function collect(event) {

                                            let row = event.target.parentNode.parentNode;
                                            let inputs = row.querySelectorAll("input");
                                            for (var x = 0; x < inputs.length; x++) {
                                                let name = inputs[x].name;
                                                if (name == "number") {
                                                    routeNumberInSubmitForm.value = inputs[x].value;
                                                }
                                                if (name == "aPoint") {
                                                    aPoint.value = inputs[x].value;
                                                }
                                                if (name == "bPoint") {
                                                    bPoint.value = inputs[x].value;
                                                }
                                                if (name == "scheme") {
                                                    schemeInput.value = inputs[x].value;
                                                    scheme.value = inputs[x].value;
                                                }
                                            }

                                        }

                                        function setSchemeToInput() {
                                            let row = event.target.parentNode.parentNode;
                                            let inputs = row.querySelectorAll("input");
                                            for (var x = 0; x < inputs.length; x++) {
                                                let name = inputs[x].name;
                                                if (name == "number") {
                                                    routeNumberInSchemeChanger.value = inputs[x].value;
                                                }
                                                if (name == "scheme") {

                                                    schemeChanger.value = inputs[x].value;

                                                }
                                            }
                                        }

                                        function returnChangedScheme() {
                                            let routeNumber = routeNumberInSchemeChanger.value;

                                            let targetTextAreaId = "schemeTextArea:" + routeNumber;
                                            let targetTextArea = document.getElementById(targetTextAreaId);
                                            targetTextArea.value = schemeChanger.value;
                                            console.log(targetTextArea);
                                        }
        </script>
    </body>
</html>
