$(document).ready(function () {

    var str = window.location.pathname.split('/');
    var movie = str[2];//the id of the

    var loaded = false;


    $("#invite").click(function () {
        if (loaded)
            return;
        loaded = true;
        $.ajax({
            url: "http://localhost:8080/movie/invite/" + movie,
            async: false,
            success: function (data) {
                if (data.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#citySelectHolder").html(data);
                $("#invite").hide();
                $(".mediaGroup").hide();
            }
        });
        loaded = false;
        $.ajaxStop();
    });

    $("#city").change(fillUpCinemas);

    function fillUpCinemas() {
        if (loaded)
            return;
        $("#city option[value='option1']").remove();
        $("#dateSelect").remove();
        $("#timeSelect").remove();
        $("#seatSelect").remove();
        loaded = true;
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            success: function (data) {
                if (data.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#cinemaSelectHolder").html(data);

            }
        });
        loaded = false;
        $.ajaxStop();
    }

    $("#cinema").change(fillUpDates);

    function fillUpDates() {
        if (loaded)
            return;
        loaded = true;
        $("#dateSelect").remove();
        $("#timeSelect").remove();
        $("#seatSelect").remove();
        $("#cinema option[value='option1']").remove();
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").options[document.getElementById("city").selectedIndex].text + "/" +
            document.getElementById("cinema").value,
            success: function (data) {
                if (data.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#dateSelectHolder").html(data);

            }
        });
        loaded = false;
        $.ajaxStop();
    }

    $("#date").change(fillUpTime);

    function fillUpTime() {
        if (loaded)
            return;
        loaded = true;
        $("#timeSelect").remove();
        $("#seatSelect").remove();
        $("#date option[value='option1']").remove();
        var event = {
            movie: movie,
            city: document.getElementById("city").options[document.getElementById("city").selectedIndex].text,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText
        };
        $.ajax({
            async: false,
            url: "http://localhost:8080/movie/invite/fillUpTime",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#timeSelectHolder").html(response);
            }
        });
        loaded = false;
        $.ajaxStop();
    }

    $("#time").change(fillUpSeats);

    function fillUpSeats() {
        if (loaded)
            return;
        loaded = true;
        $("#seatSelect").remove();
        $("#time option[value='option1']").remove();
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
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();
                    $.ajaxStop();
                }
                $("#seatSelectHolder").html(response);
            }
        });
        loaded = false;
        $.ajaxStop();
    }

    $("#submitInvite").click(sendNewInvite);


    function sendNewInvite() {
        if (loaded)
            return;
        loaded = true;
        $("#seat option[value='option1']").remove();
        var inviteJson = {
            movie: movie,
            city: document.getElementById("city").options[document.getElementById("city").selectedIndex].text - 1,
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
                if (response.trim() === "No events") {
                    $("#note").text("No events :(").show();

                }
            }
        });
        window.location.replace("/shoppingCart");
        loaded = false;
        $.ajaxStop();
    }


});