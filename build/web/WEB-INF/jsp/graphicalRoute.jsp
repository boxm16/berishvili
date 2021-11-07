<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
$routeController = new RouteController();
$allVersions = $routeController->getRouteVersionsWithoutBreaks($starterTrip, $firstTripStartTime, $lastTripStartTime, $abTripTimeMinutes, $abTripTimeSeconds, $baTripTimeMinutes, $baTripTimeSeconds, $abBusCount, $baBusCount, $intervalTime);
-->
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
                margin: 0;
            }

            .navbar {
                overflow: hidden;
                background-color: lightyellow; 
            }


            .subnavbtn {
                font-size: 16px;  
                border: none;
                outline: none;
                color: black;
                padding: 8px 40%;
                background-color: inherit;
                font-family: inherit;
                margin: 0;
            }

            .navbar a:hover, .subnav:hover .subnavbtn {
                background-color: lightyellow;
            }

            .subnav-content {
                display: none;
                position: absolute;
                left: 0;
                background-color: lightyellow;
                width: 100%;
                z-index: 10;
            }


            input[type="number"] {
                width:45px;
            }


            table, tr, td, th {

                border:1px solid black;
            }
        </style>
    </head>
    <body>

        <div class="navbar">

            <div class="subnav">
                <button class="subnavbtn" onclick="showParamatersForm()">პარამეტრების შეყვანა <i class="fa fa-caret-down" ></i></button>
                <div id="subnav-content" class="subnav-content">
                    <form action="graphicalRouteVersions.htm" method="POST">
                        <table>
                            <tr style="height:200px;">
                                <td>
                                    <div >
                                        <table id="calculationTable" style="width:200px; padding-left: 10px" >

                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <input id="roundCheckBox" name="roundCheckBox" type="checkbox" ${route.roundCheckBoxChecked} onclick="checkCheckBoxes(event)">
                                                    </td>
                                                    <td>
                                                        ბრუნის დრო
                                                    </td>


                                                    <td>
                                                        <table>
                                                            <tr id="roundTr">
                                                                <td >

                                                                    <input id="roundInputHour" name="roundInputHour" class="input" type="number" min="-1" disabled="true" value="${route.roundInputHourValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                                <td >

                                                                    <input id="roundInputMinute" name="roundInputMinute" class="input" type="number" disabled="true" value="${route.roundInputMinuteValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                                <td>

                                                                    <input id="roundInputSecond" name="roundInputSecond" class="input" type="number" disabled="true" value="${route.roundInputSecondValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                            </tr>
                                                            <tr id="roundMinutesTr">
                                                                <td colspan="2" style="padding-top:5px; padding-left:40px">
                                                                    <input id="roundInputMinutes"  name="roundInputMinutes" class="input" type="number" disabled="true" value="${route.roundInputMinutesValue}" style="width:50px;" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                                <td style="padding-top:5px">

                                                                    <input id="roundInputSeconds" name="roundInputSeconds" class="input" type="number" disabled="true" value="${route.roundInputSecondsValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td> 
                                                            </tr>
                                                        </table>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>
                                                        <input id="busCheckBox" name="busCheckBox" type="checkbox" ${route.busCheckBoxChecked} onclick="checkCheckBoxes(event)" >
                                                    </td>
                                                    <td>
                                                        ავტ. რაოდ.
                                                    </td>
                                                    <td colspan="3">
                                                        <input id="busInput" name="busInput" class="input" type="number" disabled="true" style="width:135px;" max="200" min="1" value="${route.busInputValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                    </td>


                                                </tr>

                                                <tr >
                                                    <td>
                                                        <input id="intervalCheckBox" name="intervalCheckBox" type="checkbox" ${route.intervalCheckBoxChecked} onclick="checkCheckBoxes(event)">
                                                    </td>

                                                    <td>
                                                        ინტერვალი
                                                    </td>
                                                    <td>
                                                        <table>
                                                            <tr id="intervalTr">
                                                                <td >
                                                                    <input id="intervalInputHour" name="intervalInputHour" class="input" type="number" min="-1" disabled="true" value="${route.intervalInputHourValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                                <td >
                                                                    <input id="intervalInputMinute"  name="intervalInputMinute" class="input" type="number" disabled="true" value="${route.intervalInputMinuteValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                                <td>
                                                                    <input id="intervalInputSecond" name="intervalInputSecond"  class="input" type="number" disabled="true" value="${route.intervalInputSecondValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)" onfocus="this.select()">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td colspan="3" style="padding:0px">
                                                        <table style="width:100%">
                                                            <tr>

                                                                <td style="width:40%">
                                                                    <label>+/-</label>
                                                                    <input id="plusMinusInput" name="plusMinusInput" type="number" min="0" value="${route.plusMinusInputValue}" oninput="adjastTimeInputs(event)" onkeyup="incoming(event)">
                                                                </td>
                                                                <td style="widht:60%">
                                                                    <button type="button"  style="width:100%; background-color:blue; color:white" onclick="checkAndCalculate()"><b>გამოთვლა</b></button>
                                                                </td>
                                                                <td style="width:0%">

                                                                </td>
                                                            </tr>

                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="5" style=" padding:0px; padding-top:2px;"> 
                                                        <table style="width:100%">
                                                            <tr>
                                                                <td style="width:20%">
                                                                    <button  type="button" id="backButton"  disabled="true" onclick="goBack()"><<<<</button>
                                                                </td>
                                                                <td style="width:60%">  <label id="notes"> შენიშვბები</label></td>
                                                                <td style="width:20%">      <button  type="button" id="forwardButton"  disabled="true" onclick="goForward()">>>>></button>
                                                                </td>
                                                            </tr>
                                                        </table>

                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                                <td >
                                    <div id="resultTables" class="col-sm-7" style="height: 200px; overflow:auto"> 
                                        <div >

                                            <table style="width:100%">
                                                <tr>
                                                    <th>#</th><th><h6><b>ბრუნის დრო</b></h6></th><th><h6><b>ბრუნის დრო წუთებში</b></h6></th><th><h6><b>ავტ.</b></h6></th><th><h6><b>ინტერვალი</b></h6></th><th><h6><b>არჩევა</b></h6></th>
                                                </tr>
                                                <tbody id="zeroTableBody">
                                                </tbody>
                                            </table>
                                            <hr>
                                            <div>
                                                <label>ყველა შედეგები</label>
                                                <table style="width:100%">
                                                    <tr>
                                                        <th>#</th><th><h6><b>ბრუნის დრო</b></h6></th><th><h6><b>ბრუნის დრო წუთებში</b></h6></th><th><h6><b>ავტ.</b></h6></th><th><h6><b>ინტერვალი</b></h6></th><th><h6><b>არჩევა</b></h6></th>
                                                    </tr>
                                                    <tbody id="allTableBody">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div>

                                        <table>

                                            <tr>
                                                <td>პერიოდი</td>
                                                <td>      
                                                    <input name="firstTripStartTimeInFormInput" type="time" step="1" value="${route.firstTripStartTime}">
                                                </td>
                                                <td>
                                                    <input name="lastTripStartTimeInFormInput" type="time" step="1" value="${route.lastTripStartTime}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    ათვლის დამწყები მარშრუტი
                                                </td>
                                                <td>
                                                    A_B &nbsp;<input type="radio" name="starterTripInFormInput" value="ab" ${route.starterTrip.equals("ab")? "checked":""}>
                                                </td>
                                                <td> B_A &nbsp;<input type="radio" name="starterTripInFormInput" value="ba" ${route.starterTrip.equals("ba")? "checked":""}>
                                            </tr>
                                            <tr>

                                                <td rowspan="2">
                                                    ბრუნის დრო* <br><label id="roundTimeInFormLabel">${route.roundTripTime}</label>
                                                </td>
                                                <td>A-B წირის დრო</td>
                                                <td>B-A წირის დრო</td>


                                            </tr>
                                            <tr>
                                                <td>
                                                    <input name="abTripTimeMinutesInFormInput"  id="abTripTimeMinutesInFormInput" type="number" value="${route.abTripTimeMinutes}" step="any" oninput="adjastHalfRoundTimeInputsInForm(event)">

                                                    <input name="abTripTimeSecondsInFormInput" id="abTripTimeSecondsInFormInput" type="number" value="${route.abTripTimeSeconds}" step="any" oninput="adjastHalfRoundTimeInputsInForm(event)">*
                                                </td>
                                                <td>
                                                    <input name="baTripTimeMinutesInFormInput" id="baTripTimeMinutesInFormInput" type="number" value="${route.baTripTimeMinutes}" step="any" oninput="adjastHalfRoundTimeInputsInForm(event)">

                                                    <input name="baTripTimeSecondsInFormInput" id="baTripTimeSecondsInFormInput" type="number" value="${route.baTripTimeSeconds}" step="any" oninput="adjastHalfRoundTimeInputsInForm(event)">*
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">
                                                    ავტობუსების რაოდენობა <br> <label id="busCountInFormLabel"> ${route.busCount}</label>
                                                </td>
                                                <td>
                                                    A-B ავტობუსები
                                                </td>
                                                <td>
                                                    B-A ავტობუსები
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>
                                                    <input name="abBusCountInFormInput" id="abBusCountInFormInput" type="number" value="${route.abBusCount}" oninput="adjastBusCountInputsInForm(event)">
                                                </td>
                                                <td>
                                                    <input name="baBusCountInFormInput" id="baBusCountInFormInput" type="number" value="${route.baBusCount}" oninput="adjastBusCountInputsInForm(event)">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    ინტერვალის დრო
                                                </td>
                                                <td><input name="intervalTimeInFormInput" id="intervalTimeInFormInput" type="text" value="${route.intervalTime}" readonly="true" style="width:80px"></td>
                                                <td>
                                                    <input type="submit" value="გამოსახვა" style="background-color:green; color:white; width:100%" >
                                                </td>
                                            </tr>

                                        </table>

                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <h6>*ბრუნის დრო შეადგენს წირების და დგომების ჯამს ანუ ბრუნის სრული დრო= A_B წირის დრო + 5წ.დგომა + B_A წირის დრო +5წ.დგომა</h6>
                    <button onclick="hideParamatersForm()">პარამეტრების ფანჯრის აკეცვა</button>

                    <br><br>
                    <br><br><br><br>

                </div>

            </div> 


        </div>

        <hr>

        <!--
                <?php
                $routeVersionNumber = 0;
        
                foreach ($allVersions as $route) {
                $exoduses = $route->getExodusesWithoutBreak();
                include 'Model/RouteTemplate.php';
        
                $routeVersionNumber++;
                }
                ?>
        -->





        <script>
            var h = [];
            var hNumber = 0;
            //---------
            var checkBoxes = document.querySelectorAll('input[type=checkbox]:checked');

            for (i = 0; i < checkBoxes.length; i++) {
                var trElement = checkBoxes[i].parentNode.parentNode;
                var trInputs = trElement.querySelectorAll(".input");
                for (x = 0; x < trInputs.length; x++) {
                    trInputs[x].disabled = false;
                }
                checkAndCalculate();
            }


            //------------






            function incoming(event) {
                zeroTableBody.innerHTML = "";
                allTableBody.innerHTML = "";
                if (event.keyCode === 13) {
                    checkAndCalculate();
                }
            }

            function checkCheckBoxes(event) {
                var target = event.target;

                zeroTableBody.innerHTML = "";
                allTableBody.innerHTML = "";
                var trElements = event.target.parentElement.parentElement;
                var trInputs = trElements.querySelectorAll(".input");
                if (selectedParametres() == 3) {

                    target.checked = false;
                    notes.style.color = "red";
                    notes.innerHTML = "სამივე პარამეტრის არჩევა დაუშვებელია";

                    //  redNotes.innerHTML = "სამივე პარამეტრის არჩევა დაუშვებელია";
                    for (i = 0; i < trInputs.length; i++) {
                        trInputs[i].disabled = true;
                    }
                } else {
                    notes.innerHTML = "";
                    if (target.checked == false) {
                        for (i = 0; i < trInputs.length; i++) {
                            trInputs[i].disabled = true;
                        }
                    } else {
                        for (i = 0; i < trInputs.length; i++) {
                            trInputs[i].disabled = false;
                        }
                    }
                }



            }

            function selectedParametres() {
                return document.querySelectorAll('input[type=checkbox]:checked').length;
            }



            function checkAndCalculate() {

                zeroTableBody.innerHTML = "";
                allTableBody.innerHTML = "";


                if (inputsValid()) {
                    saveInputs();
                    checkHistory();
                    calculate();
                }

            }

            function checkHistory() {
                if (h.length > 0 && hNumber > 0) {
                    backButton.disabled = false;
                }
                if (h.length > 0 && hNumber < h.length) {
                    forwardButton.disabled = false;
                }
                if (hNumber == 1) {
                    backButton.disabled = true;
                }
                if (hNumber == h.length) {
                    forwardButton.disabled = true;
                }

            }

            function saveInputs() {
                var inputs = [];
                inputs.push(roundCheckBox.checked);
                inputs.push(busCheckBox.checked);
                inputs.push(intervalCheckBox.checked);

                inputs.push(roundInputHour.value);
                inputs.push(roundInputMinute.value);
                inputs.push(roundInputSecond.value);

                inputs.push(busInput.value);

                inputs.push(intervalInputHour.value);
                inputs.push(intervalInputMinute.value);
                inputs.push(intervalInputSecond.value);

                inputs.push(plusMinusInput.value);

                h.push(inputs);
                hNumber = h.length;
            }

            function getRoundSeconds() {
                return  (roundInputHour.value * 60 * 60) + (roundInputMinute.value * 60) + parseInt(roundInputSecond.value);

            }

            function getIntervalSeconds() {
                return (intervalInputHour.value * 60 * 60) + (intervalInputMinute.value * 60) + intervalInputSecond.value * 1;
            }

            function calculate() {
                var seconds = getRoundSeconds();
                if (roundCheckBox.checked === true & busCheckBox.checked === true) {

                    calculateInterval(seconds);
                } else if (roundCheckBox.checked === true & intervalCheckBox.checked === true) {
                    calculateBus(seconds);
                } else if (busCheckBox.checked === true & intervalCheckBox.checked === true) {
                    calculateRound();
                }
                copyHoursToMinutes();
                calculateTable();

            }
            function calculateRound() {
                var seconds = getIntervalSeconds();
                ;
                var result = seconds * busInput.value;

                var date = new Date(0);
                date.setSeconds(result);
                var resultString = date.toISOString().substr(11, 8);
                var splittedResult = resultString.split(":");
                roundInputHour.value = splittedResult[0];
                roundInputMinute.value = splittedResult[1];
                roundInputSecond.value = splittedResult[2];
                notes.style.color = "green";
                notes.innerHTML = "შედეგი ჯერადია.";
            }

            function calculateBus(seconds) {
                var intervalSeconds = getIntervalSeconds();

                var result = seconds / intervalSeconds;
                var nashti = result % 1;
                busInput.value = parseInt(result);
                if (nashti == 0) {
                    notes.style.color = "green";
                    notes.innerHTML = "შედეგი ჯერადია.";
                } else {

                    notes.innerHTML = "<label style='color:red;'>შედეგი არ არის ჯერადი </label>";
                    // + "<br><label>ნაშთი= " + nashti + "</label><br>"
                    //+ "<label>ჯერადი შედეგის მისაღებად ან დააკელი ბრუნის დრო ან გაზარდე ინტერვალის დრო</label>";

                }
            }

            function calculateInterval(seconds) {
                var result = seconds / busInput.value;
                var date = new Date(0);
                date.setSeconds(result);
                var resultString = date.toISOString().substr(11, 8);
                var splittedResult = resultString.split(":");
                intervalInputHour.value = splittedResult[0];
                intervalInputMinute.value = splittedResult[1];
                intervalInputSecond.value = splittedResult[2];
                var nashti = result % 3600 % 60 % 1;
                if (nashti == 0) {
                    notes.style.color = "green";
                    notes.innerHTML = "შედეგი ჯერადია.";
                } else {
                    var recalculatedSeconds = (splittedResult[0] * 3600) + (splittedResult[1] * 60) + parseInt(splittedResult[2]);
                    var recalculatedTime = recalculatedSeconds * busInput.value;
                    var recalculatedDate = new Date(0);
                    recalculatedDate.setSeconds(recalculatedTime);
                    var recalculatedTime = recalculatedDate.toISOString().substr(11, 8);
                    notes.innerHTML = "<label style='color:red;'>შედეგი არ არის ჯერადი</label>";
                    //  + "<br><label>ნაშთი= " + nashti + "</label><br>"
                    // + "<label>მიღებული ინტერვალისგან გადათვლილი ბრუნის დრო უდრის " + recalculatedTime + "</label>";
                }
            }

            function calculateTable() {


                var roundSeconds = (roundInputHour.value * 60 * 60) + (roundInputMinute.value * 60) + parseInt(roundInputSecond.value);
                var plusMinus = plusMinusInput.value * 60;
                var startingTime = roundSeconds + plusMinus;
                var endingTime = roundSeconds - plusMinus;

                var zeroTableRows = "";
                var allTableRows = "";
                var a = 0;
                var busCounts = new Array();
                var busCount = busInput.value;
                if (!busCheckBox.checked && busCount > 1) {
                    busCount--;
                    busCounts.push(busCount);
                    busCount++;
                    busCounts.push(busCount);
                    busCount++;
                    busCounts.push(busCount);

                } else if (!busCheckBox.checked && busCount == 1)
                {
                    busCounts.push(busCount);
                    busCount++;
                    busCounts.push(busCount);
                } else {
                    busCounts.push(busCount);
                }


                for (y = 0; y < busCounts.length; y++) {
                    for (x = startingTime; x > endingTime - 1; x--) {
                        if (x == 0) {
                            break;
                        }

                        var result = x / busCounts[y];
                        var roundTime = new Date(0);
                        roundTime.setSeconds(x);
                        var roundTimeResultString = roundTime.toISOString().substr(11, 8);

                        var intervalTime = new Date(0);
                        intervalTime.setSeconds(result);
                        var intervalTimeResultString = intervalTime.toISOString().substr(11, 8);

                        var roundTimeSplittedResult = roundTimeResultString.split(":");
                        var roundTimeHour = roundTimeSplittedResult[0];
                        var roundTimeMinute = roundTimeSplittedResult[1];
                        var roundTimeMinutes = (roundTimeHour * 60) + parseInt(roundTimeMinute);
                        var roundTimeSeconds = roundTimeSplittedResult[2];
                        var minutesText = roundTimeMinutes + ":" + roundTimeSeconds;
                        var interalSplittedResult = intervalTimeResultString.split(":");
                        var intervalSeconds = interalSplittedResult[2];


                        var nashti = result % 3600 % 60 % 1;
                        if (nashti == 0) {
                            allTableRows = allTableRows + "<tr><td>" + a + "</td><td>" + roundTimeResultString + "</td><td>" + minutesText + "</td><td>" + busCounts[y] + "</td><td>" + intervalTimeResultString + "</td><td><input type='button' value='არჩევა' style='background-color:blue;color:white' onclick='chooseRow(event)'></td></tr>";
                            if (intervalSeconds == 00 || intervalSeconds == 30) {
                                zeroTableRows = zeroTableRows + "<tr><td>" + a + "</td><td>" + roundTimeResultString + "</td><td>" + minutesText + "</td><td>" + busCounts[y] + "</td><td>" + intervalTimeResultString + "</td><td><input type='button' value='არჩევა' style='background-color:blue;color:white;' onclick='chooseRow(event)'></td></tr>";

                            }
                            a++;
                        }

                    }
                }
                zeroTableBody.innerHTML = zeroTableRows;
                allTableBody.innerHTML = allTableRows;
            }

            function inputsValid() {
                if (roundCheckBox.checked & intervalCheckBox.checked) {
                    var seconds = (roundInputHour.value * 60 * 60) + (roundInputMinute.value * 60) + parseInt(roundInputSecond.value);
                    var intervalSeconds = (intervalInputHour.value * 60 * 60) + (intervalInputMinute.value * 60) + parseInt(intervalInputSecond.value);

                    if (intervalSeconds > seconds) {
                        notes.style.color = "red";
                        notes.innerHTML = "ინტერვალის დრო აღემათება ბრუნის დროს, რაც დაუშვებელია";
                        return false;
                        //    redNotes.innerHTML = "ინტერვალის დრო აღემათება ბრუნის დროს, რაც დაუშვებელია";
                    }
                }

                if (selectedParametres() < 2) {
                    notes.style.color = "red";
                    notes.innerHTML = "არჩეულია არასაკარისი პარამეტრები. საჭიროა 2 პარამეტრის არჩევა";
                    // redNotes.innerHTML = "არჩეულია არასაკარისი პარამეტრები. საჭიროა 2 პარამეტრის არჩევა";
                    return false;
                }
                if (intervalCheckBox.checked & intervalInputHour.value == 0 & intervalInputMinute.value == 0 & intervalInputSecond.value == 0) {
                    notes.style.color = "red";
                    notes.innerHTML = "მითითებული ინტერვალი დაუშვებელია (0). ინტერვალი უნდა იყოს არანაკლებ 1 წამი";
                    //  redNotes.innerHTML = "მითითებული ინტერვალი დაუშვებელია (0). ინტერვალი უნდა იყოს არანაკლებ 1 წამი";
                    return false;
                }
                if (busCheckBox.checked & (busInput.value <= 0)) {
                    notes.style.color = "red";
                    notes.innerHTML = " ავტობუსების რაოდენობის ველში მითითებულია დაუშვებელი რიცხვი (" + busInput.value + ")";
                    // redNotes.innerHTML = " ავტობუსების რაოდენობის ველში მითითებულია დაუშვებელი რიცხვი (0)";
                    return false;
                }
                return true;
            }

            function adjastTimeInputs(event) {
                notes.innerHTML = "";
                zeroTableBody.innerHTML = "";
                allTableBody.innerHTML = "";
                var targetTR = event.target.parentNode.parentNode.id;
                var targetInputs;

                if (targetTR == "roundTr") {
                    targetInputs = "round";
                    adjastTimeInputs_1(targetInputs);
                }
                if (targetTR == "roundMinutesTr") {

                    adjastTimeInputs_2();
                }
                if (targetTR == "intervalTr") {
                    targetInputs = "interval";
                    adjastTimeInputs_1(targetInputs);
                }




            }
            function adjastTimeInputs_1(targetInputs) {

                var second = document.getElementById(targetInputs + "InputSecond");
                var minute = document.getElementById(targetInputs + "InputMinute");
                var hour = document.getElementById(targetInputs + "InputHour");

                if (second.value == 60) {
                    second.value = 0;
                    minute.value = parseInt(minute.value) + 1;
                }
                if (second.value == -1) {
                    minute.value = parseInt(minute.value) - 1;
                    if (hour.value == 0 && minute.value == -1) {
                        second.value = 0;
                    } else {
                        second.value = 59;
                    }
                }


                if (minute.value == 60) {
                    minute.value = 0;
                    timeInputHourPlusPlus(targetInputs);
                }
                if (minute.value == -1) {

                    minute.value = 59;
                    timeInputHourMinusMinus(targetInputs);
                }

                if (hour.value == -1) {
                    hour.value = 0;
                    notes.style.color = "red";
                    notes.innerHTML = "საათების 0 ზე ქვემოთ ჩამოსვლა დაუშვებელია";
                    //  redNotes.innerHTML = "საათების 0 ზე ქვემოთ ჩამოსვლა დაუშვებელია";

                }

                copyHoursToMinutes();
            }


            function adjastTimeInputs_2() {
                var second = document.getElementById("roundInputSeconds");
                var minute = document.getElementById("roundInputMinutes");


                if (second.value == 60) {
                    second.value = 0;
                    minute.value = parseInt(minute.value) + 1;
                }
                if (second.value == -1) {
                    minute.value = parseInt(minute.value) - 1;
                    if (minute.value == -1) {
                        second.value = 0;
                    } else {
                        second.value = 59;
                    }
                }


                if (minute.value == -1) {

                    minute.value = 0;
                    notes.style.color = "red";
                    notes.innerHTML = "წუთების 0 ზე ქვემოთ ჩამოსვლა დაუშვებელია";
                }


                copyMinutesToHours();
            }

            function copyMinutesToHours() {
                var minutes = roundInputMinutes.value;
                var hour = parseInt(minutes / 60);
                var minute = minutes % 60;
                if (hour < 10) {
                    roundInputHour.value = "0" + hour;
                } else {
                    roundInputHour.value = hour;
                }
                if (minute < 10) {
                    roundInputMinute.value = "0" + minute;
                } else {
                    roundInputMinute.value = minute;
                }

                roundInputSecond.value = roundInputSeconds.value;


            }
            function copyHoursToMinutes() {
                var hour = roundInputHour.value;
                var minute = roundInputMinute.value;
                var minutes = (hour * 60) + parseInt(minute);
                roundInputMinutes.value = minutes;
                roundInputSeconds.value = roundInputSecond.value;
            }

            function   timeInputHourPlusPlus(targetInputs) {
                var hour = document.getElementById(targetInputs + "InputHour");
                var x = parseInt(hour.value) + 1
                if (x < 10) {
                    hour.value = "0" + x;
                } else {
                    hour.value = x;
                }
            }

            function   timeInputHourMinusMinus(targetInputs) {
                var hour = document.getElementById(targetInputs + "InputHour");
                var minute = document.getElementById(targetInputs + "InputMinute");

                var x = parseInt(hour.value) - 1
                if (x < 0) {
                    x = 0;
                    hour.value = 0;
                    minute.value = 0;
                    notes.style.color = "red";
                    notes.innerHTML = "საათების 0 ზე ქვემოთ ჩამოსვლა დაუშვებელია";
                    //  redNotes.innerHTML = "საათების 0 ზე ქვემოთ ჩამოსვლა დაუშვებელია";
                }
                if (x < 10) {
                    hour.value = "0" + x;
                } else {
                    hour.value = x;
                }
            }

            function goBack() {
                hNumber--;
                var inputs = h[hNumber - 1];
                roundCheckBox.checked = inputs[0];
                busCheckBox.checked = inputs[1];
                intervalCheckBox.checked = inputs[2];

                checkCheckBoxHistory(roundCheckBox);
                checkCheckBoxHistory(busCheckBox);
                checkCheckBoxHistory(intervalCheckBox);

                roundInputHour.value = inputs[3];
                roundInputMinute.value = inputs[4];
                roundInputSecond.value = inputs[5];

                busInput.value = inputs[6];

                intervalInputHour.value = inputs[7];
                intervalInputMinute.value = inputs[8];
                intervalInputSecond.value = inputs[9];

                plusMinusInput.value = inputs[10];

                checkHistory();

                calculate();
            }

            function goForward() {
                hNumber++;
                var inputs = h[hNumber - 1];
                roundCheckBox.checked = inputs[0];
                busCheckBox.checked = inputs[1];
                intervalCheckBox.checked = inputs[2];

                checkCheckBoxHistory(roundCheckBox);
                checkCheckBoxHistory(busCheckBox);
                checkCheckBoxHistory(intervalCheckBox);


                roundInputHour.value = inputs[3];
                roundInputMinute.value = inputs[4];
                roundInputSecond.value = inputs[5];

                busInput.value = inputs[6];

                intervalInputHour.value = inputs[7];
                intervalInputMinute.value = inputs[8];
                intervalInputSecond.value = inputs[9];


                plusMinusInput.value = inputs[10];

                checkHistory();

                calculate();
            }

            function   checkCheckBoxHistory(checkBox) {
                var trElements = checkBox.parentElement.parentElement;
                var trInputs = trElements.querySelectorAll(".input");
                if (checkBox.checked == true) {
                    for (i = 0; i < trInputs.length; i++) {
                        trInputs[i].disabled = false;
                    }
                } else {
                    for (i = 0; i < trInputs.length; i++) {
                        trInputs[i].disabled = true;
                    }
                }
            }

            function chooseRow(event) {


                var trElements = event.target.parentElement.parentElement;

                var trTexts = trElements.querySelectorAll("td");
                var roundTime = trTexts[2].innerHTML;
                var busCounts = trTexts[3].innerHTML;
                var interval = trTexts[4].innerHTML;
                //alert(roundTripTime + "-" + busCounts + "-" + interval);
                roundTimeInFormLabel.innerHTML = roundTime;
                busCountInFormLabel.innerHTML = busCounts;
                intervalTimeInFormInput.value = interval;
                var roundTimeArray = roundTime.split(":");
                var roundTimeInSeconds = (roundTimeArray[0] * 60) + (roundTimeArray[1] * 1) - (10 * 60);

                if (roundTimeInSeconds % 2 == 0) {

                    var halfRoundTimeInSeconds = roundTimeInSeconds / 2;
                    var halfRoundTimeMinutes = parseInt(halfRoundTimeInSeconds / 60);
                    var halfRoundTimeSeconds = halfRoundTimeInSeconds % 60;
                    abTripTimeMinutesInFormInput.value = halfRoundTimeMinutes;
                    abTripTimeSecondsInFormInput.value = halfRoundTimeSeconds;
                    baTripTimeMinutesInFormInput.value = halfRoundTimeMinutes;
                    baTripTimeSecondsInFormInput.value = halfRoundTimeSeconds;
                } else {
                    var halfRoundTimeInSeconds = parseInt(roundTimeInSeconds / 2);
                    var halfRoundTimeMinutes = parseInt(halfRoundTimeInSeconds / 60);
                    var halfRoundTimeSeconds = halfRoundTimeInSeconds % 60;
                    abTripTimeMinutesInFormInput.value = halfRoundTimeMinutes;
                    abTripTimeSecondsInFormInput.value = halfRoundTimeSeconds + 1;
                    baTripTimeMinutesInFormInput.value = halfRoundTimeMinutes;
                    baTripTimeSecondsInFormInput.value = halfRoundTimeSeconds;
                }

                if (busCounts % 2 == 0) {
                    abBusCountInFormInput.value = busCounts / 2;
                    baBusCountInFormInput.value = busCounts / 2;
                } else {
                    abBusCountInFormInput.value = parseInt(busCounts / 2) + 1;
                    baBusCountInFormInput.value = parseInt(busCounts / 2);
                }



            }

            function adjastBusCountInputsInForm(event) {
                var busCountSum = 1 * (busCountInFormLabel.innerHTML);

                var firstValue = 1 * (event.target.value);

                if (firstValue > busCountSum) {
                    firstValue = busCountSum;
                    event.target.value = firstValue;
                }

                if (firstValue < 0) {
                    firstValue = 0;
                    event.target.value = 0;
                }
                var targetInput;
                if (event.target.id == "abBusCountInFormInput") {
                    targetInput = baBusCountInFormInput;
                } else {
                    targetInput = abBusCountInFormInput;
                }
                var secondValue = busCountSum - firstValue;
                targetInput.value = secondValue;
            }

            function adjastHalfRoundTimeInputsInForm(event) {
                var roundTimeString = roundTimeInFormLabel.innerHTML;
                var roundTimeArray = roundTimeString.split(":");
                var roundTimeInSeconds = (roundTimeArray[0] * 60) + (roundTimeArray[1] * 1) - (10 * 60);
                var trElements = event.target.parentElement;
                var trInputs = trElements.querySelectorAll("input");
                var triggerInputMinutes;
                var triggerInputSeconds;
                var targetInputMinutes;
                var targetInputSeconds;
                if (trInputs[0].id == "abTripTimeMinutesInFormInput") {
                    triggerInputMinutes = abTripTimeMinutesInFormInput;
                    triggerInputSeconds = abTripTimeSecondsInFormInput;

                    targetInputMinutes = baTripTimeMinutesInFormInput;
                    targetInputSeconds = baTripTimeSecondsInFormInput;
                } else {
                    triggerInputMinutes = baTripTimeMinutesInFormInput;
                    triggerInputSeconds = baTripTimeSecondsInFormInput;

                    targetInputMinutes = abTripTimeMinutesInFormInput;
                    targetInputSeconds = abTripTimeSecondsInFormInput;
                }

                if (triggerInputSeconds.value < 0) {
                    triggerInputSeconds.value = 59;
                    triggerInputMinutes.value = triggerInputMinutes.value * 1 - 1;
                }
                if (triggerInputSeconds.value > 59) {
                    triggerInputSeconds.value = "00";
                    triggerInputMinutes.value = triggerInputMinutes.value * 1 + 1;
                }

                var halfTimeInSecondsTrigger = trInputs[0].value * 60 + trInputs[1].value * 1;
                if (halfTimeInSecondsTrigger > roundTimeInSeconds) {
                    halfTimeInSecondsTrigger = roundTimeInSeconds;
                    triggerInputMinutes.value = parseInt(roundTimeInSeconds / 60);
                    triggerInputSeconds.value = roundTimeInSeconds % 60;
                    targetInputMinutes.value = 0;
                    targetInputSeconds.value = 0;
                }
                if (halfTimeInSecondsTrigger < 0) {
                    halfTimeInSecondsTrigger = 0;
                    triggerInputMinutes.value = 0;
                    triggerInputSeconds.value = 0;
                    targetInputMinutes.value = parseInt(roundTimeInSeconds / 60);
                    targetInputSeconds.value = roundTimeInSeconds % 60;
                }
                var halfTimeInSecondsTarget = roundTimeInSeconds - halfTimeInSecondsTrigger;
                targetInputMinutes.value = parseInt(halfTimeInSecondsTarget / 60);
                targetInputSeconds.value = halfTimeInSecondsTarget % 60;


            }



            function   showParamatersForm() {
                document.getElementById("subnav-content").style.display = "block";
            }
            function   hideParamatersForm() {
                document.getElementById("subnav-content").style.display = "none";
            }
        </script>
    </body>
</html>
