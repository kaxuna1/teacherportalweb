/**
 * Created by vakhtanggelashvili on 5/21/17.
 */

$(document).ready(function () {
    var id = getParameterByName("id");
    var times = getParameterByName("times")
    var times = times.split(",")
    console.log(times);
    var timesObject = {};

    var firstLastDates = {
        a: "",
        b: ""
    }

    var numberOfTimes = 0;
    for (var key in times) {

        var item = times[key];

        if (numberOfTimes === 0) {
            firstLastDates.a = moment(parseInt(item)).locale("us").format("LL");
        }
        if (numberOfTimes === times.length-1) {
            firstLastDates.b = moment(parseInt(item)).locale("us").format("LL");
        }


        numberOfTimes++;
        timesObject[item] = {
            date: moment(parseInt(item)).locale("us").format("LL"),
            startTime: moment(parseInt(item)).locale("us").format("HH:mm"),
            endTime: moment(parseInt(item)).add(parseInt($("#duration").html()), "minutes").format("HH:mm")
        }
    }
    console.log(timesObject);


    $(".numberOfClasses").html("<div class='numberOfClassesName'>" + numberOfTimes + " Classes</div>");
    $(".numberOfClasses").append("<div class='numberOfClassesAB'>" + firstLastDates.a + "-" + firstLastDates.b + "</div>")
    $(".numberOfClasses").append("<div><a style='cursor:pointer;float: right;' class='expandDates'>See Detaials</a>" +
        "<table style='padding-top: 10px'><tbody class='timesExpanded'></tbody></table></div>")

    for(var key in timesObject ){
        var item = timesObject[key];

        $(".timesExpanded").append("<tr>" +
            "<td>" +
                item.date+"  -  "+item.startTime+" - "+item.endTime+
            "</td>" +
            "</tr>")


    }


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