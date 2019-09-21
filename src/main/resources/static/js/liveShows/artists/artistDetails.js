$(document).ready(function () {

    var str = window.location.pathname.split('/');
    var artist = str[2];//the id of the artist page
    var pathUrl = "http://localhost:8080/invite/liveshow";
    var loaded = false;

    $("#invite").click(function () {
        if (loaded)
            return;
        loaded = true;
        $.ajax({
            async: false,
            url: pathUrl + "/location/" + artist,
            success: function (data) {
                if (data.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#locationSelectHolder").html(data);
                $("#invite").hide();
            }
        });
        loaded = false;
    });

    $("#location").change(fillUpDate);

    function fillUpDate() {
        if (loaded)
            return;
        $("#location option[value='option1']").remove();
        $("#dateSelect").remove();
        $("#timeSelect").remove();
        $("#seatSelect").remove();
        loaded = true;
        var event = {
            artist: artist,
            location: document.getElementById("location").value
        };
        $.ajax({
            async: false,
            url: "http://localhost:8080/invite/liveshow/date/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#dateSelectHolder").html(response);
            }
        });
        loaded = false;
    }


    $("#date").change(fillUpTime);

    function fillUpTime() {
        if (loaded)
            return;
        $("#date option[value='option1']").remove();
        $("#timeSelect").remove();
        $("#seatSelect").remove();
        loaded = true;
        var event = {
            artist: artist,
            location: document.getElementById("location").value,
            date: document.getElementById("date").innerText
        };
        $.ajax({
            async: false,
            url: "http://localhost:8080/invite/liveshow/time/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#timeSelectHolder").html(response);
                $("#myRadio").show();
                $("#classInvite").show();
            }
        })
        loaded = false;
    }

    $("#seating").click(fillUpSeats);

    function fillUpSeats() {
        if (loaded)
            return;
        $("#time option[value='option1']").remove();
        $("#seatSelect").remove();
        loaded = true;
        $("#seatSelectHolder").show();
        var event = {
            artist: artist,
            location: document.getElementById("location").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText
        };

        $.ajax({
            async: false,
            url: "http://localhost:8080/invite/liveshow/seats/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#seatSelectHolder").html(response);
            }
        });
        loaded = false;
    }

    $("#standing").click(function () {
        $("#seatSelectHolder").hide()
    });

    $("#submitInvite").click(function () {
        if (loaded)
            return;
        loaded = true;
        var iAmStand = 1, seatNum = -1;
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
            async: false,
            url: "http://localhost:8080/invite/liveshow/new/",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event)

        });
        loaded=false
    });

});