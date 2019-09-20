$(document).ready(function () {

    var str = window.location.pathname.split('/');
    var movie = str[2];//the id of the movie


     $("#invite").click( function () {
        $.ajax({
            url: "http://localhost:8080/movie/invite/" + movie,
            success: function (data) {
                $("#citySelectHolder").html(data);
                $("#invite").hide();                                                                                            
                fillUpCinemas();
            }
        })

    });
    $("#cinema").change(fillUpTime);
    function fillUpCinemas() {
        $.ajax({
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").innerText,
            success: function (data) {
                $("#cinemaSelectHolder").html(data);
                fillUpDates();
            }
        })
    }
    $("#date").change(fillUpTime);
    function fillUpDates() {
        $.ajax({
            url: "http://localhost:8080/movie/invite/" +
            movie + "/" +
            document.getElementById("city").innerText + "/" +
            document.getElementById("cinema").value,
            success: function (data) {
                $("#dateSelectHolder").html(data);
                fillUpTime();
            }
        })
    }
    $("#time").change(fillUpTime);
    function fillUpTime() {
        var event = {
            movie: movie,
            city: document.getElementById("city").innerText,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText
         };

        $.ajax({
            url: "http://localhost:8080/movie/invite/fillUpTime",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                $("#timeSelectHolder").html(response);
                fillUpSeats();
            }
        })
    }

    function fillUpSeats() {
        var event = {
            movie: movie,
            city: document.getElementById("city").innerText,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText
        };

        $.ajax({
            url: "http://localhost:8080/movie/invite/fillUpSeats",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function (response) {
                $("#seatSelectHolder").html(response);
            }
        })
    }

     $( "#submitInvite" ).click(sendNewInvite);
    function sendNewInvite() {
        var inviteJson = {
            movie: movie,
            city: document.getElementById("city").innerText,
            cinema: document.getElementById("cinema").value,
            date: document.getElementById("date").innerText,
            time: document.getElementById("time").innerText,
            seat: document.getElementById("seat").value
        };
        $.ajax({
            url: "http://localhost:8080/movie/invite/new",
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(inviteJson),
            success: function (response) {
                window.location = "/";
            }
        })
    }

});