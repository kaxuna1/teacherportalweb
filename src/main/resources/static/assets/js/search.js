/**
 * Created by vakhtanggelashvili on 5/7/17.
 */
var sortMenu = false;
var priceMenu = false;


$(document).ready(function () {
    var city = getParameterByName('city');
    var clas = getParameterByName('class');
    console.log(city, clas);
    $("#upper").val(199);
    $("#lower").val(0);
    if (city && clas) {
        loadSearch(city, clas, 0,$("#lower").val(),$("#upper").val())
    }


    $("#lower").change(function () {

        if ($(this).val() > $("#upper").val()) {
            $("#upper").val($(this).val())
        }
        loadSearch(city, clas, 0,$("#lower").val(),$("#upper").val())
        drawPriceRange($("#lower").val(),$("#upper").val())
    });
    $("#upper").change(function () {
        if ($(this).val() < $("#lower").val()) {
            $("#lower").val($(this).val())
        }
        loadSearch(city, clas, 0,$("#lower").val(),$("#upper").val())
        drawPriceRange($("#lower").val(),$("#upper").val())
    });



    function drawPriceRange(lower, upper) {
        $(".priceRangeClass").html(lower+"₾ - "+upper+"₾")
    }



    $(".citySearchField").val(city).change(function () {
        loadSearch($(".citySearchField").val(), $(".categorySearchField").val(), 0,$("#lower").val(),$("#upper").val())
    });
    $(".categorySearchField").val(clas).change(function () {
        loadSearch($(".citySearchField").val(), $(".categorySearchField").val(), 0,$("#lower").val(),$("#upper").val())
    });
    $(".sortCheck").change(function () {
        loadSearch($(".citySearchField").val(), $(".categorySearchField").val(), 0,$("#lower").val(),$("#upper").val())
    });

    $(".dropMenu").click(function () {
        $(".dropMenu").removeClass("activeItem");

        $(this).addClass("activeItem");
        $(".results").addClass("hr");
        var item = this;
        $(document).mouseup(function (e) {
            var container = $(item);

            // if the target of the click isn't the container nor a descendant of the container
            if (!container.is(e.target) && container.has(e.target).length === 0) {
                $(".results").removeClass("hr")
                container.removeClass("activeItem");
            }
        });
    });


    var lowerSlider = document.querySelector('#lower'),
        upperSlider = document.querySelector('#upper'),
        lowerVal = parseInt(lowerSlider.value);
    upperVal = parseInt(upperSlider.value);

    upperSlider.oninput = function () {
        lowerVal = parseInt(lowerSlider.value);
        upperVal = parseInt(upperSlider.value);

        if (upperVal < lowerVal + 4) {
            lowerSlider.value = upperVal - 4;

            if (lowerVal == lowerSlider.min) {
                upperSlider.value = 4;
            }
        }
    };


    lowerSlider.oninput = function () {
        lowerVal = parseInt(lowerSlider.value);
        upperVal = parseInt(upperSlider.value);

        if (lowerVal > upperVal - 4) {
            upperSlider.value = lowerVal + 4;

            if (upperVal == upperSlider.max) {
                lowerSlider.value = parseInt(upperSlider.max) - 4;
            }

        }
    };


});
function loadSearch(city, clas, page,lower,upper,loadmore) {
    $.getJSON("/searchapi?city=" + city
        + "&category=" + clas
        + "&page=" + page
        + "&lower=" + lower
        + "&sort="+$(".sortCheck").val()
        + "&upper=" + upper, function (result) {
        var data = result["content"]

        if(!loadmore)
        $(".searchResultDiv").html("");

        for (key in data) {
            var item = data[key];
            var itemString = '<article class="searchResultItem">' +
                '    <div class="searchResultItemTop">' +
                '        <div class="searchResultItemTopImgDiv">' +
                '            <img class="img-circle searchResultItemTopImg" src="profilePic/' + item.user.id + '">' +
                '        </div>' +
                '        <div class="searchResultItemTopTextDiv">' +
                '            <div class="searchResultItemTopTextDivChild">' +
                '                <p class="nameLabel">' +
                '                    ' + item.user.name +
                '                </p>' +
                '                <div class="degreeAndClass"><p class="classLabel">' +
                '                   ' + item["categoryName"] +
                '                </p>' +
                '                <p class="degreeLabel">' +
                '                   Bachelor Degree' +
                '                </p></div>' +
                '            </div>' +
                '        </div>' +
                '        <div class="searchResultItemTopPriceDiv">' +
                '                            <span class="priceLabelClass"><span class="priceTag">' + item.price + '₾</span> <span' +
                '                                    class="delimiter">/</span>class</span>' +
                '        </div>' +
                '    </div>' +
                '    <div class="searchResultItemBottom" style="">' +
                '        <div class="starRatingDiv">' +
                '                             <span class="starRating">' +
                '                                 <img class="staricon" src="png/search/v.png">' +
                '                                 <img class="staricon" src="png/search/v.png">' +
                '                                 <img class="staricon" src="png/search/v.png">' +
                '                                 <img class="staricon" src="png/search/v.png">' +
                '                                 <img class="staricon" src="png/search/v.png">' +
                '                             </span>' +
                '            <span class="reviewsLabel">' +
                '                                '+item.ratingNum+' reviews' +
                '                            </span>' +
                '            <span class="viewsLabel">' +
                '                                <img src="png/search/4.png">'+item.views +
                '                            </span>' +
                '            <a href="class?id=' + item.id + '"><button class="btn readMoreBtn">' +
                '                Read More' +
                '            </button></a>' +
                '        </div>' +
                '' +
                '    </div>' +
                '</article>';
            $(".searchResultDiv").append(itemString);
            console.log(item);

        }
        $(".searchResultDiv").append("<a id='loadMore' style='cursor: pointer;margin: auto;font-family: brixLight;font-size: 18px'>Load more</a>")
        $("#loadMore").unbind().click(function () {
            page++;
            $(this).remove();
            loadSearch(city, clas, page,lower,upper,true);
        })
    })
}
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}