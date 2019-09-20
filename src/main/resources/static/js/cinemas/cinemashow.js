
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

    $("#add_row").on("click", function (e) {
        var seats = prompt("Please enter the number of seats:", "");
        if (seats === "") {
            alert("You don't enter value, please try again.");
            $.ajaxStop();
        }
        if (seats !== (/^[0-9.,]+$/)){
            alert("You need to enter only numeric values (0,1,2,3,...), please try again.");
            $.ajaxStop();
        }
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

