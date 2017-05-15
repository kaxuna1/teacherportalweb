/**
 * Created by vakhtanggelashvili on 5/7/17.
 */
$(document).ready(function () {
    var city = getParameterByName('city');
    var clas = getParameterByName('class');
    console.log(city,clas);
    if(city&&clas){
        loadSearch(city,clas,0)
    }


    $("citySearchField").val(city);
    $("categorySearchField").val(clas);



});
function loadSearch(city, clas, page) {
    $.getJSON("/searchapi?city="+city+"&category="+clas+"&page="+page,function (result) {
        var data=result["content"]

        for(key in data){
            var item= data[key];
            var itemString='<article class="searchResultItem">'+
                '    <div class="searchResultItemTop">'+
                '        <div class="searchResultItemTopImgDiv">'+
                '            <img class="img-circle searchResultItemTopImg" src="profilePic/'+item.user.id+'">'+
                '        </div>'+
                '        <div class="searchResultItemTopTextDiv">'+
                '            <div class="searchResultItemTopTextDivChild">'+
                '                <p class="nameLabel">'+
                '                    '+item.user.nameSurname+
                '                </p>'+
                '                <div class="degreeAndClass"><p class="classLabel">'+
                '                   '+item["categoryName"]+
                '                </p>'+
                '                <p class="degreeLabel">'+
                '                   Bachelor Degree'+
                '                </p></div>'+
                '            </div>'+
                '        </div>'+
                '        <div class="searchResultItemTopPriceDiv">'+
                '                            <span class="priceLabelClass"><span class="priceTag">'+item.price+'$</span> <span'+
                '                                    class="delimiter">/</span>class</span>'+
                '        </div>'+
                '    </div>'+
                '    <div class="searchResultItemBottom" style="">'+
                '        <div class="starRatingDiv">'+
                '                             <span class="starRating">'+
                '                                 <img class="staricon" src="png/search/v.png">'+
                '                                 <img class="staricon" src="png/search/v.png">'+
                '                                 <img class="staricon" src="png/search/v.png">'+
                '                                 <img class="staricon" src="png/search/v.png">'+
                '                                 <img class="staricon" src="png/search/v.png">'+
                '                             </span>'+
                '            <span class="reviewsLabel">'+
                '                                37 reviews'+
                '                            </span>'+
                '            <span class="viewsLabel">'+
                '                                <img src="png/search/4.png">450'+
                '                            </span>'+
                '            <a href="class?id='+item.id+'"><button class="btn readMoreBtn">'+
                '                Read More'+
                '            </button></a>'+
                '        </div>'+
                ''+
                '    </div>'+
                '</article>';
            $(".searchResultDiv").append(itemString);
            console.log(item);

        }
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