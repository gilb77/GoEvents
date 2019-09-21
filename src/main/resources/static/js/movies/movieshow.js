$(document).ready(function () {

    var str = window.location.pathname.split('/');
    var movie = str[2];//the id of the movie
    $("#city").change(fillUpCinemas);
     $("#invite").click( function () {
        $.ajax({
            url: "http://localhost:8080/movie/invite/" + movie,
            async: false,
            success: function (data) {
                if(data.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#citySelectHolder").html(data);
                $("#invite").hide();                                                                                            
                fillUpCinemas();
            }
        })

    });

    function fillUpCinemas() {
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            success: function (data) {
                if(data.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#cinemaSelectHolder").html(data);
                fillUpDates();
            }
        })
    }
    // $("#date").click(fillUpTime);
    function fillUpDates() {
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").options[document.getElementById("city").selectedIndex].text + "/" +
            document.getElementById("cinema").value,
            success: function (data) {
                if(data.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#dateSelectHolder").html(data);
                fillUpTime();
            }
        })
    }
    // $("#time").change(fillUpTime);
    function fillUpTime() {
        var event = {
            async: false,
            movie: movie,
            city: document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText
         };

        $.ajax({
            url: "http://localhost:8080/movie/invite/fillUpTime",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if(response.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#timeSelectHolder").html(response);
                fillUpSeats();
            }
        })
    }

    function fillUpSeats() {
        var event = {
            movie: movie,
            city: document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText
        };

        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/fillUpSeats",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if(response.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#seatSelectHolder").html(response);
            }
        })
    }

     $( "#submitInvite" ).click(sendNewInvite);
    function sendNewInvite() {
        var inviteJson = {
            movie: movie,
            city: document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText,
            seat: document.getElementById("seat").value
        };
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/new",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(inviteJson),
            success: function (response) {
                if(response.trim()==="No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                window.location = "/";
            }
        })
    }

});