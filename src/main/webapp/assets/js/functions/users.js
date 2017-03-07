/**
 * Created by kakha on 11/12/2015.
 */
var cities = {};
function loadUsersData(index, search) {
    $.getJSON("getusers?index=" + index + "&search=" + search, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < userColumns.length; i++) {
            var currentElement = userColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")

        }
        console.log(result);
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < dataArray.length; i++) {
            var currentElement = dataArray[i];

            $("#dataGridBody").append(
                "<tr value='" + i + "' class='gridRow'><td>" + currentElement["name"] + "</td><td>"
                + currentElement["surname"] + "</td><td>"
                + currentElement["username"] + "</td><td>"
                + currentElement["personalNumber"] + "</td>" +
                "<td>" + currentElement["mobile"] + "</td></tr>"
            );

        }
        for (i = 0; i < totalPages; i++) {
            $("#paginationUl").append('<li value="' + i + '" class="paginate_button ' + (index == i ? 'active"' : '') + '"><a href="#">' + (i + 1) + '</a></li>');
        }
        $(".paginate_button").click(function () {
            //console.log($(this).val())
            loadUsersData($(this).val(), "")
        });

        var gridRow = $('.gridRow');
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {
            var currentElement = dataArray[$(this).attr("value")];
            console.log(currentElement);
            showModalWithTableInside(function (head, body, modal, rand) {
                body.html(clientProfileTemplate);
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


                payments.append('<div class="row">' +
                    '<div class="col-md-3">' +
                    '<label>' +
                    '<input id="checkBoxPayments1" type="checkbox" data-checkbox="icheckbox_square-blue">გამოყენებული' +
                    '</label>' +
                    '</div><div class="col-md-3">' +
                    '<label>' +
                    '<input id="checkBoxPayments2" type="checkbox" data-checkbox="icheckbox_square-blue">გადარიცხული' +
                    '</label></div><div class="col-md-4"><div class="input-group">            ' +
                    '<div class="icheck-list"><label>' +
                    '<input id="checkBoxPayments3" type="checkbox" data-checkbox="icheckbox_square-blue">გაუქმებული' +
                    '</label></div></div></div>' +
                    '</div>');
                payments.append(PaymentsTemplate);
                openDocuments(DOMElements, documents, currentElement);


                permissions.append('<div style="display:inline-flex;width: 100%">' +
                    '    <div style="width: 45%">' +
                    '        <table class="table">' +
                    '            <thead>' +
                    '            <tr>' +
                    '                <th class="text-left">მომხმარებლის უფლებები</th>' +
                    '            </tr>' +
                    '            </thead>' +
                    '            <tbody id="userpermissions">' +
                    '            </tbody>' +
                    '        </table>' +
                    '    </div>' +
                    '    <div style="width:10%">' +
                    '        <button style="width: 100%" id="removePermission">-></button>' +
                    '        <br>' +
                    '        <button style="width: 100%" id="addPermissions"><-</button>' +
                    '    </div>' +
                    '    <div style="width: 45%">' +
                    '        <table class="table">' +
                    '            <thead>' +
                    '            <tr>' +
                    '                <th class="text-left">დასამატებელი უფლებები</th>' +
                    '            </tr>' +
                    '            </thead>' +
                    '            <tbody id="notuserspermissions">' +
                    '            </tbody>' +
                    '        </table>' +
                    '    </div>' +
                    '</div>');

                drawPermsForAdding(currentElement.id);

                var addPerms = $("#addPermissions");
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
                        url: "/giveuserpermission",
                        data: {
                            id: currentElement["id"],
                            ids: productIds.toString()
                        }
                    }).done(function (result) {
                        drawPermsForAdding(currentElement["id"]);
                    })
                });

                var removePerms = $("#removePermission");
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
                        url: "/removeuserpermission",
                        data: {
                            id: currentElement["id"],
                            ids: productIds.toString()
                        }
                    }).done(function (result) {
                        drawPermsForAdding(currentElement["id"]);
                    })
                })
            }, {
                "დამატებითი ღილაკი": function () {
                }
            }, 1024);

        });


        $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark">ახალი მომხმარებლის დამატება </button>');
        $("#addNewButton").click(function () {
            showModalWithTableInside(function (head, body, modal) {
                dynamicCreateForm(body, "/createuser", {
                    name: {
                        name: "სახელი",
                        type: "text"
                    },
                    surname: {
                        name: "გვარი",
                        type: "text"
                    },
                    city: {
                        name: "ქალაქი",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/cities"
                    },
                    username: {
                        name: "მომხმარებელი",
                        type: "text"
                    },
                    email: {
                        name: "email",
                        type: "text"
                    },
                    password: {
                        name: "პაროლი",
                        type: "text"
                    },
                    personalNumber: {
                        name: "პირადი ნომერი",
                        type: "text"
                    },
                    mobile: {
                        name: "მობილური",
                        type: "text"
                    },
                    address: {
                        name: "მისამართი",
                        type: "text"
                    }
                }, function () {
                    modal.modal("hide")
                })
            })
        })
    });
    function drawPermsForAdding(id) {
        $.getJSON("/getuserpermissions/" + id, function (result) {
            var userPermTable = $("#userpermissions");
            userPermTable.html("");
            for (var key in result) {
                userPermTable.append("<tr><td><input class='checkboxUserPerm' value='" + result[key].id + "' type='checkbox'> " + result[key].name + "</td></tr>")
            }
        });
        $.getJSON("/getnotuserpermissions/" + id, function (result) {
            var notUserPermTable = $("#notuserspermissions");
            notUserPermTable.html("");
            for (var key in result) {
                notUserPermTable.append("<tr><td><input class='checkboxPerm' value='" + result[key].id + "' type='checkbox'> " + result[key].name + "</td></tr>")
            }
        });
    }

    function openDocuments(DOMElements, documents, currentElement) {
        documents.append('<div class="row">' +
            '<section class="dropbox" id="dropbox">' +
            '<h4>Drop files here to upload</h4>' +
            '</section>' +
            '</div>' +
            '<div class="row">' +
            '<div class="col-md-3">' +
            '<label>' +
            '<input id="checkBoxDocuments1" type="checkbox" data-checkbox="icheckbox_square-blue">დიპლომები' +
            '</label>' +
            '</div><div class="col-md-3">' +
            '<label>' +
            '<input id="checkBoxDocuments2" type="checkbox" data-checkbox="icheckbox_square-blue">პირადობის' +
            '</label></div><div class="col-md-4"><div class="input-group">            ' +
            '<div class="icheck-list"><label>' +
            '<input id="checkBoxDocuments3" type="checkbox" data-checkbox="icheckbox_square-blue">შიდა' +
            '</label></div></div></div>' +
            '</div>');
        documents.append(DocumentsTemplate);

        DOMElements.DocumentsDataTableBody = $("#DocumentsDataTableBody");


        dropBoxFunc('promptModal' + DOMElements.rand,'upload/' + currentElement.id,function(){
            loadDocumentsForUser(DOMElements, currentElement.id, 0)
        });
        loadDocumentsForUser(DOMElements, currentElement.id, 0);
    }

    function loadDocumentsForUser(DOMElements, id, page) {
        $("#checkBoxDocuments1").unbind();
        $("#checkBoxDocuments1").on('ifChanged', function () {
            loadDocumentsForUser(DOMElements, id, 0);
        });
        $("#checkBoxDocuments2").unbind();
        $("#checkBoxDocuments2").on('ifChanged', function () {
            loadDocumentsForUser(DOMElements, id, 0);
        });
        $("#checkBoxDocuments3").unbind();
        $("#checkBoxDocuments3").on('ifChanged', function () {
            loadDocumentsForUser(DOMElements, id, 0);
        });
        $.getJSON("listdocs/" + id + "?page=" + page + "&closed=" +
            ($("#checkBoxDocuments1").is(":checked") ? "true" : "false") +
            "&opened=" +
            ($("#checkBoxDocuments2").is(":checked") ? "true" : "false") +
            "&late=" +
            ($("#checkBoxDocuments3").is(":checked") ? "true" : "false"), function (result) {
            DOMElements.DocumentsDataTableBody.html("");
            var dataArray = result["content"];
            var totalPages = result["totalPages"];
            var totalElements = result["totalElements"];
            for (var i = 0; i < dataArray.length; i++) {
                var currentElement = dataArray[i];
                var itemLogos = "";

                DOMElements.DocumentsDataTableBody.append("<tr>" +
                    "<td style='font-family: font1;' value='" + i + "' class='gridRowDoc'>" + itemLogos + "</td>" +
                    "<td style='font-family: font1;' value='" + i + "' class='gridRowDoc'>" + currentElement["name"] + "</td>" +
                    "<td style='font-family: font1;' value='" + i + "' class='gridRowDoc'>" +

                    moment(new Date(currentElement["date"])).locale("ka").format("L") + "</td>" +
                    "<td><a href='doc/" + currentElement.id + "'><i class='fa fa-bars' aria-hidden='true'></i></a></td>" +
                    "</tr>");
            }
            var gridRow = $('.gridRowDoc');
            gridRow.css('cursor', 'pointer');
            gridRow.unbind();
            gridRow.click(function () {
                var currentElement = dataArray[$(this).attr("value")];
                console.log($(this).attr("value"));

                //openLoanGlobal(currentElement);
                //DOMElements.modal.modal("hide");
            });

        })
    }
}
