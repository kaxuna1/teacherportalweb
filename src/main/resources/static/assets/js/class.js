/**
 * Created by vakhtanggelashvili on 5/12/17.
 */
$(document).ready(function () {
    var id=getParameterByName("id")
    $.getJSON("schedulefordays/"+id+"/45",function (result) {
        calendarInit(result);


        //$('#calendar').datepicker();



        $(".bookButton").click(function () {
            if(readCookie("projectSessionId")){

            }else{
                $("#logisignbtn").click();
            }
        })
    });
    $(document).ready(function () {
        function checkScroll() {
            var startY = $('.navbar').height() * 2; //The point where the navbar changes in px

            if ($(window).scrollTop() > startY) {
                $('.navbar').addClass("scrolled");
                $(".navlabel").addClass("navlabelScroled")
            } else {
                $('.navbar').removeClass("scrolled");
                $(".navlabel").removeClass("navlabelScroled")
            }
        }

        $(window).on("scroll load resize", function () {
            checkScroll();
        });
    })

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