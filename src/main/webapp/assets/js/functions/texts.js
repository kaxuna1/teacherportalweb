/**
 * Created by vakhtanggelashvili on 3/26/17.
 */
var openTextGlobal;
function loadTexts(index, search) {

    $("#addNewDiv").html(
        '');
    createButtonWithHandlerr($("#addNewDiv"),"დამატება",function () {
       showModalWithTableInside(function (head, body, modal, rand) {
           dynamicCreateForm(body,"/createstring",{
               name: {
                   name: "სახელი",
                   type: "text"
               },
               value: {
                   name: "მნიშვნელობა",
                   type: "text"
               }
           },function () {
               dataLoading();
               modal.modal("hide");
           })
       },{},500)
    });
    dataLoading()
    function dataLoading() {
        $.getJSON("/strings/"+index, function (result) {
            $("#dataGridHeader").html("");
            $("#dataGridBody").html("");
            $("#paginationUl").html("");
            for (i = 0; i < stringsColumns.length; i++) {
                var currentElement = stringsColumns[i];
                $("#dataGridHeader").append('<th style="font-family: font1;">' + currentElement + "</th>")
            }
            currentData = result;
            var dataArray = result["content"];
            var totalPages = result["totalPages"];
            var totalElements = result["totalElements"];
            for (i = 0; i < dataArray.length; i++) {
                var currentElement = dataArray[i];
                var name = "";

                var type = ""


                $("#dataGridBody").append("<tr>" +
                    "<td><input type='checkbox'></td>" +
                    "<td style='font-family: font1;' value='" + i + "' class='gridRowClientUz2'>" + currentElement["name"] + "</td>" +
                    "<td style='font-family: font1;' value='" + i + "' class='gridRowClientUz2'>" + currentElement["value"] + "</td>" +
                    "</tr>");


            }
            var gridRow = $('.gridRowClientUz2');
            gridRow.css('cursor', 'pointer');
            gridRow.unbind();
            gridRow.click(function () {
                var currentItem=dataArray[$(this).attr("value")];
                openTextGlobal(currentItem.id);
            });
            for (i = 0; i < totalPages; i++) {
                if (i > index - 3 && i < index + 3 || i === 0 || i === (totalPages - 1))
                    $("#paginationUl").append('<li value="' + i + '" class="paginate_button ' + (index == i ? 'active"' : '') + '"><a href="#">' + (i + 1) + '</a></li>');

            }
            $(".paginate_button").click(function () {
                //console.log($(this).val())
                currentPage = $(this).val();
                index = currentPage;
                dataLoading();
            });

        })
    }
    openTextGlobal=function (id,DOMElements) {
        showModalWithTableInside(function (head, body, modal, rand) {
        }, {}, 500);
    }
}