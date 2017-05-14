/**
 * Created by vakhtanggelashvili on 5/12/17.
 */
$(document).ready(function () {
    var id=getParameterByName("id")
    $.getJSON("schedulefordays/"+id+"/30",function (result) {
        calendarInit(result);
    });

});


function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}