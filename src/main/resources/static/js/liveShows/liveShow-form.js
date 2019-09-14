

$(document).ready(function () {
    $(function () {
        $("#datepicker").datepicker();
    });

    $("#submit").click(
        function () {

            var event = {
                artist: document.getElementById("artists").value,
                address: document.getElementById("address").value,
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
                    // window.location.href = "http://localhost:8080/liveshows/lists"
                  }
                })
        });

});

