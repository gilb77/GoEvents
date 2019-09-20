
$(document).ready(function () {
    var str = window.location.pathname.split('/');
    var id = str[2];

    $.loadTheaters = function() {
        $.ajax({
            url: "http://localhost:8080/theaters" +
            window.location.pathname,
            success: function (data) {
                $("#theaterSelectHolder").html(data);
                $("#theaterTable").modal("show");
            }
        });
    };

    $.loadTheaters();

    $("#add_row").on("click", function () {
        var seats = prompt("Please enter the number of seats:", "");
        var event = {
            seats: seats,
            cinema: id
        };
        $.ajax({
            url: "http://localhost:8080/theater/new/",
            dataType: 'json',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(event),
            complete: $.loadTheaters()
        })

    });

});

