/**
 * Created by vakhtanggelashvili on 5/7/17.
 */
$(document).load(function () {
    var city = getParameterByName('city');
    var clas = getParameterByName('class');

    $("citySearchField").val(city);
    $("categorySearchField").val(clas);



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