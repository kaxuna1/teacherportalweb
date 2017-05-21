/**
 * Created by vakhtanggelashvili on 5/21/17.
 */

$(document).ready(function () {
    $.getJSON("/mystudentschedule/60", function (result) {
        console.log(result);
    })
});