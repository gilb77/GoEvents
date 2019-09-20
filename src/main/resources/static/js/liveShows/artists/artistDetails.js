$(document).ready(function () {

    var str = window.location.pathname.split('/');
    var artist = str[2];//the id of the artist page
    var pathUrl = "http://localhost:8080/invite/liveshow";

    $("#invite").click(function () {
        $.ajax({
            url: pathUrl + "/location/" + artist,
            success: function (data) {
                $("#locationSelectHolder").html(data);
                $("#invite").hide();
                fillUpDate();
            }
        })
    });
    $("#location").change(fillUpDate);

    function fillUpDate() {
        var event = {
            artist: artist,
            location: document.getElementById("location").value
        };
        $.ajax({
            url: "http://localhost:8080/invite/liveshow/date/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                $("#dateSelectHolder").html(response);
                fillUpTime();
            }
        })
    }


    $("#date").change(fillUpTime);

    function fillUpTime() {
        var event = {
            artist: artist,
            location: document.getElementById("location").value,
            date: document.getElementById("date").innerText
        };
        $.ajax({
            url: "http://localhost:8080/invite/liveshow/time/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                $("#timeSelectHolder").html(response);
                $("#myRadio").show();
                $("#classInvite").show();
            }
        })
    }

    $("#seating").click(fillUpSeats);

    function fillUpSeats() {
        $("#seatSelectHolder").show();
        var event = {
            artist: artist,
            location: document.getElementById("location").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText
        };

        $.ajax({
            url: "http://localhost:8080/invite/liveshow/seats/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                $("#seatSelectHolder").html(response);
            }
        })
    }

    $("#standing").click(function () {
        $("#seatSelectHolder").hide()
    });

    $("#submitInvite").click(function () {
        var iAmStand = 1, seatNum =-1;
        if ($("#seatSelectHolder").is(":visible")) {
            seatNum = document.getElementById("seat").value;
            iAmStand = 0;
        }
        var event = {
            artist: artist,
            location: document.getElementById("location").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText,
            seat: seatNum,
            iAmStand: iAmStand
        };
        $.ajax({
            url: "http://localhost:8080/invite/liveshow/new/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event)

        })
    });
});