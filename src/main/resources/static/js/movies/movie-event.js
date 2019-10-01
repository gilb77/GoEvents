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
                async: false,
                url: "http://localhost:8080/event/new/",
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(event),
                success: function (data) {
                    if (data.trim() === "No events")
                        $("#note").text("Please fill up all the fields").show();
                    else if (data.trim() === "Events exists")
                        $("#note").text("There is event on theater at the same time.").show();
                    else
                        window.location.replace("/event/movies/table");
                    $.ajaxStop();
                }

            });

         });
    $("#cinemas").change(fillUpTheaters);

    function fillUpTheaters() {
        $.ajax({
            url: "http://localhost:8080/event/cinema/" +
            document.getElementById("cinemas").value,
            success: function (data) {
                $("#theaterSelectHolder").html(data);
                $.ajaxStop();
            }
        })
    }
});