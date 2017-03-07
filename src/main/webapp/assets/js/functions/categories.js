/**
 * Created by kakha on 11/12/2015.
 */

function loadCategoriesData(index, search) {
    $.getJSON("/categories/"+index, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < categoryColumns.length; i++) {
            var currentElement = categoryColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < dataArray.length; i++) {
            var currentElement = dataArray[i];

            $("#dataGridBody").append("<tr value='" + i + "'   class='gridRow' ><td>" + currentElement["name"] + "</td>" +
                "<td><a value='" + currentElement['id'] + "' class='deleteCat' href='#'><i class='fa fa-times'></i></a></td>" +
                "</tr>");

        }
        for (i = 0; i < totalPages; i++) {
            $("#paginationUl").append('<li value="' + i + '" class="paginate_button ' + (index == i ? 'active"' : '') + '"><a href="#">' + (i + 1) + '</a></li>');
        }
        $(".paginate_button").click(function () {
            //console.log($(this).val())
            loadCategoriesData($(this).val(), search)
        });
        $(".deleteCat").click(function () {
            /*)*/
            var deleteValue = $(this).attr("value");
            showBootstrapPrompt("გსურთ წაშალოთ ჩანაწერი", {
                "კი": function () {
                    $.ajax({
                        url: "/deleteCategory",
                        data: {
                            id: deleteValue
                        }
                    }).done(function (result) {
                        loadCategoriesData(index, search);
                    });
                }
            });

        });
        $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark">ახალი კატეგორიის დამატება </button>');
        $("#addNewButton").click(function () {
            showModalWithTableInside(function (head, body, modal) {
                dynamicCreateForm(body, "/createcategory", {
                    name: {
                        name: "სახელი",
                        type: "text"
                    }
                }, function () {
                    modal.modal("hide");
                    loadCategoriesData(0, search);
                })
            })
        });
        var gridRow=$('.gridRow');
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {
            var currentElement = dataArray[$(this).attr("value")];
            console.log(currentElement);
            showModalWithTableInside(function (head, body, modal, rand) {
                body.html(categoryPageTemplate);
                var documents = $("#tab5_1");
                var permissions = $("#tab5_2");
                var lessons = $("#tab5_3");
                var payments = $("#tab5_4")
                var actions = $("#tab6_1");
                var infoDiv = $("#tab6_2");
                var DOMElements = {
                    documents: documents,
                    permissions: permissions,
                    lessons: lessons,
                    payments: payments,
                    actions: actions,
                    infoDiv: infoDiv,
                    modal: modal,
                    rand: rand,
                    currentElement:currentElement
                };

                documents.append('<div style="display:inline-flex;width: 100%">' +
                    '    <div style="width: 45%">' +
                    '        <table class="table">' +
                    '            <thead>' +
                    '            <tr>' +
                    '                <th class="text-left">საჭირო დოკუმენტის ტიპები</th>' +
                    '            </tr>' +
                    '            </thead>' +
                    '            <tbody id="categoryDocTypes">' +
                    '            </tbody>' +
                    '        </table>' +
                    '    </div>' +
                    '    <div style="width:10%">' +
                    '        <button style="width: 100%" id="removeDocType">-></button>' +
                    '        <br>' +
                    '        <button style="width: 100%" id="addDocType"><-</button>' +
                    '    </div>' +
                    '    <div style="width: 45%">' +
                    '        <table class="table">' +
                    '            <thead>' +
                    '            <tr>' +
                    '                <th class="text-left">დასამატებელი ტიპები</th>' +
                    '            </tr>' +
                    '            </thead>' +
                    '            <tbody id="notCategoryDocTypes">' +
                    '            </tbody>' +
                    '        </table>' +
                    '    </div>' +
                    '</div>');

                drawDocTypesForAdding(currentElement.id);

                var addPerms = $("#addDocType");
                addPerms.unbind();
                addPerms.click(function () {
                    var checkboxPerm = $(".checkboxPerm");
                    var productIds = [];
                    checkboxPerm.each(function () {
                        if (this.checked) {
                            productIds.push(this.value);
                            this.checked = false;
                        }
                    });
                    $.ajax({
                        url: "/givecategorydoctype",
                        data: {
                            id: currentElement["id"],
                            ids: productIds.toString()
                        }
                    }).done(function (result) {
                        drawDocTypesForAdding(currentElement["id"]);
                    })
                });
                var removePerms = $("#removeDocType");
                removePerms.unbind();
                removePerms.click(function () {
                    var checkboxPerm = $(".checkboxUserPerm");
                    var productIds = [];
                    checkboxPerm.each(function () {
                        if (this.checked) {
                            productIds.push(this.value);
                            this.checked = false;
                        }
                    });
                    $.ajax({
                        url: "/removecategorydoctype",
                        data: {
                            id: currentElement["id"],
                            ids: productIds.toString()
                        }
                    }).done(function (result) {
                        drawDocTypesForAdding(currentElement["id"]);
                    })
                })



                drawInfoPage(DOMElements);






            }, {
                "დამატებითი ღილაკი": function () {
                }
            }, 1024);
        })
        if(permissions["docTypes"]){
            createButtonWithHandlerr($("#addNewDiv"),"დოკუმენტის ტიპები", function () {
                showModalWithTableInside(function (head, body, modal, rand) {
                    head.html("<h2>დოკუმენტის ტიპები</h2>");
                    body.append("<div id='buttonsDivForDocTypes'></div>" +
                        "<div id='addFormDiv'></div>" +
                        "<div>" +
                        "<table class='table-bordered table'>" +
                        "<thead>" +
                        "<tr>" +
                        "<th>სახელი</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody id='docTypesTableBody'>" +
                        "</tbody>" +
                        "</table>" +
                        "</div>");
                    createButtonWithHandlerr($("#buttonsDivForDocTypes"),"ახალი დოკ ტიპი", function () {
                        dynamicCreateForm($("#addFormDiv"), "/createdoctype", {
                            name: {
                                name: "სახელი",
                                type: "text"
                            }
                        }, function () {
                            $("#addFormDiv").html("");
                            loadDocTypesInTable($("#docTypesTableBody"))
                        })

                    });
                    loadDocTypesInTable($("#docTypesTableBody"))
                },{},1024);
            })
        }

    });
    function drawDocTypesForAdding(id) {
        $.getJSON("/getcategorydocs/" + id, function (result) {
            var categoryDocsTable = $("#categoryDocTypes");
            categoryDocsTable.html("");
            for (var key in result) {
                categoryDocsTable.append("<tr><td><input class='checkboxUserPerm' value='" + result[key].id + "' type='checkbox'> " + result[key].name + "</td></tr>")
            }
        });
        $.getJSON("/getnotcategorydocs/" + id, function (result) {
            var notCategoryDocsTable = $("#notCategoryDocTypes");
            notCategoryDocsTable.html("");
            for (var key in result) {
                notCategoryDocsTable.append("<tr><td><input class='checkboxPerm' value='" + result[key].id + "' type='checkbox'> " + result[key].name + "</td></tr>")
            }
        });
    }
    function loadDocTypesInTable(tBody){
        tBody.html("");
        $.getJSON("/doctypes", function (result) {
            for(var key in result){
                var item=result[key];
                tBody.append("<tr><td>"+item.name+"</td></tr>")
            }
        })
    }
    function drawInfoPage(DOMElements){
        DOMElements.infoDiv.append(
            "<div id='categoryLogoDiv' class='row'>" +
            "<img style='width: 150px' src='categorylogo/"+DOMElements.currentElement.id+"'/>" +
            "</div>");
        dropBoxFunc('promptModal' + DOMElements.rand,"uploadcategorylogo/"+DOMElements.currentElement.id, function () {
            drawInfoPage(DOMElements);
        });
    }

}