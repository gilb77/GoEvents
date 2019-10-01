$(document).ready(function () {
    $(function () {
        $("#datepicker").datepicker();
    });

    $("#submit").click(
        function () {

            var event = {
                artist: document.getElementById("artists").value,
                location: document.getElementById("locations").value,
                places: document.getElementById("places").value,
                costStanding: document.getElementById("costStanding").value,
                numSeating: document.getElementById("numSeating").value,
                costSeating: document.getElementById("costSeating").value,
                time: document.getElementById("time").value,
                date: document.getElementById("datepicker").value
            };

            $.ajax({
                url: "http://localhost:8080/liveshows/new/liveShow",
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(event),
                success: function (data) {
                    if (data.trim() === "success")
                        window.location.replace("/liveshows/lists");
                    else
                        $("#note").text(data.trim()).show();

                }
            });
            $.ajaxStop();
        });

});

