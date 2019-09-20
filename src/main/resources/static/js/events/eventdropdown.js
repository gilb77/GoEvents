$(document).ready(function () {
    // $("#threads").hide();
    $( "#cinemas" ).change(function() {
        var varid = document.getElementById("cinemas").value    ;
         var JSONObject= { 'id': varid };
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/event/cinema",
            data: varid,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
            }
        });

});
});