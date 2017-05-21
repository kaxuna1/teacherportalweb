/**
 * Created by vakhtanggelashvili on 5/21/17.
 */

$(document).ready(function () {
    $.getJSON("/mystudentschedule/60", function (result) {
        var callData = [];
        for (var key in result) {
            var item = result[key];
            callData.push({
                start: moment(item.startDate),
                end: moment(item.endDate).toDate(),
                title: item.categoryName
            })
        }
        $('#callAllLessons').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek,agendaDay,listMonth'
            },
            defaultView: "agendaWeek",
            height: 500,
            minTime: "08:00:00",
            editable: false,
            eventLimit: true, // allow "more" link when too many events
            events: callData
        });
    });

});