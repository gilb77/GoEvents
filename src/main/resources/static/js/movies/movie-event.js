$(document).ready(function () {
    $(function () {
        $("#datepicker").datepicker();
    });

    fillUpTheaters();
    $("#submit").click(
        function () {
            var event = {
                movie: document.getElementById("movies").value,
                theater: document.getElementById("theater").value,
                price: document.getElementById("price").value,
                date: document.getElementById("datepicker").value,
                time: document.getElementById("timepicker").value
            };
            $.ajax({
                url: "http://localhost:8080/event/new/",
                dataType: 'json',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(event),
                success: function (data) {
                    if (data.trim() === "No events") {
                        $("#note").text("Please fill up all the fields").show();
                        $.ajaxStop();
                    }
                 else   if (data.trim() === "Events exists") {
                        $("#note").text("There is event on theater theater on the same time.").show();
                        $.ajaxStop();
                    }
                }
            });
            $.ajaxStop();
        });
    $("#cinemas").change(fillUpTheaters);

    function fillUpTheaters() {
        $.ajax({
            url: "http://localhost:8080/event/cinema/" +
            document.getElementById("cinemas").value,
            success: function (data) {
                $("#theaterSelectHolder").html(data);
                $("#theaterSelect").modal("show");
            }
        })
    }
});