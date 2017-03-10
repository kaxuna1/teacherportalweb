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
                var categories = $("#tab5_4")
                var actions = $("#tab6_1");
                var infoDiv = $("#tab6_2");
                var DOMElements = {
                    documents: documents,
                    permissions: permissions,
                    lessons: lessons,
                    categories: categories,
                    actions: actions,
                    infoDiv: infoDiv,
                    modal: modal,
                    rand: rand,
                    currentElement: currentElement
                };
                drawInfoForUser(DOMElements);

                drawCategories(DOMElements, currentElement);

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
                    modal.modal("hide");
                    loadUsersData(index, search);
                })
            })
        })
    });
    function drawCategories(DOMElements, currentElement) {

        DOMElements.categories.append('<div id="categoryPageActions" class="row">' +
            '</div>' +
            '<div class="row">' +
            '<div class="col-md-3">' +
            '<label>' +
            '<input id="checkBoxPayments1" type="checkbox" data-checkbox="icheckbox_square-blue">ფილტრი 1' +
            '</label>' +
            '</div><div class="col-md-3">' +
            '<label>' +
            '<input id="checkBoxPayments2" type="checkbox" data-checkbox="icheckbox_square-blue">ფილტრი 2' +
            '</label></div><div class="col-md-4"><div class="input-group">            ' +
            '<div class="icheck-list"><label>' +
            '<input id="checkBoxPayments3" type="checkbox" data-checkbox="icheckbox_square-blue">ფილტრი 3' +
            '</label></div></div></div>' +
            '</div>');
        DOMElements.categories.append(UserCategories);
        DOMElements.CategoriesDataTableBody = $("#CategoriesDataTableBody");
        loadCategiries(DOMElements, currentElement);
        createButtonWithHandlerr($("#categoryPageActions"), "კატეგორიის დამატება", function () {
            showModalWithTableInside(function (head, body, modal, rand) {
                dynamicCreateForm(body, "/addcategorytouser", {
                    category: {
                        name: "კატეგორია",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/getcategoriesforuseradding/" + currentElement.id
                    },
                    user: {
                        type: "hidden",
                        value: "" + currentElement.id
                    }
                }, function () {
                    loadCategiries(DOMElements, currentElement);
                    modal.modal("hide")
                })
            }, {}, 600)
        })
    }

    function loadCategiries(DOMElements, currentElement) {
        DOMElements.CategoriesDataTableBody.html("");
        $.getJSON("usercategories/" + currentElement.id, function (result) {
            for (var key in result) {
                var item = result[key];
                DOMElements.CategoriesDataTableBody.append("<tr value='" + key + "' class='categoryItem " + (item.accepted ? "" : "danger") + "'>" +
                    "<td>" +
                    item.category.name +
                    "</td>" +
                    "<td>" +
                    "</td>" +
                    "<td>" +
                    "</td>" +
                    "<td>" +
                    "</td>" +
                    "</tr>")
            }
            var categoryItem = $('.categoryItem');
            categoryItem.css('cursor', 'pointer');
            categoryItem.unbind();
            categoryItem.click(function () {
                var currentCategory = result[$(this).attr("value")];
                showModalWithTableInside(function (head, body, modal, rand) {
                    body.html(clientCategoryPageTemplate);
                    DOMElements.categoryPageDom = {
                        docs: $("#tab10_1"),
                        lessons: $("#tab10_2"),
                        actions: $("#tab10_3"),
                        currentCategory: currentCategory
                    };
                    loadCategoryDocs(DOMElements);


                }, {}, 800)
            })
        })
    }

    function loadCategoryDocs(DOMElements) {
        createTable(DOMElements.categoryPageDom.docs,
            {
                name: {
                    name: "სახელი"
                },
                date: {
                    name: "თარიღი"
                },
                actions: {
                    name: "#"
                }
            }, function (tableBody) {

            });


        $.getJSON("getusercatdocs/" + DOMElements.categoryPageDom.currentCategory.id, function (result) {

            for (var key in result) {

            }
        });
    }

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
        documents.append(
            '<input type="file" id="docFile" style="display:none">');
        documents.append(filemanager);

        function maximizeWindow() {
            var newWid = $(document).width();
            var newHit = $(document).height();
            $('.doc-browser').css('margin-top', '0px');
            $('.doc-browser').animate({
                width: newWid,
                height: newHit,
                top: 0,
                left: 0
            });
            $('.sidebar-folder-list').animate({
                height: (newHit - 35) + 'px'
            })
            $('.main-content-view').animate({
                width: newWid - $('.sidebar-folder-list').width(),
                height: (newHit - 25) + 'px'
            })
        }

        function minimizeWindow() {
            var newWid = 900;
            var newHit = 900;
            var center = $(document).width() / 2 - newWid / 2;
            $('.doc-browser').animate({
                width: 900,
                height: 900,
                top: '10%',
                left: center
            });
            $('.sidebar-folder-list').animate({
                height: newHit - 35
            });
            $('.main-content-view').animate({
                width: newWid - $('.sidebar-folder-list').width(),
                height: newHit - 25
            });
        }

        function centerElementAbsolute(elem) {
            var center = $(document).width() / 2 - $(elem).width() / 2;
            $(elem).css('left', center);
        }


        $(document).ready(function () {

            centerElementAbsolute('.doc-browser')

            // Color variables for sidebar list items
            var listNorm = '#CACDD1';
            var listSel = '#ADB0B2';

            // Give the "Documents" item an initial selected color
            $('#docs').css({'background-color': listSel});

            /* List Item Click
             *
             * Change all list items back to the normal color, then
             * give the clicked list item the selected color.
             *
             * Hide the currently shown content, then show
             * the content for the selected item
             */
            $('.folder-list-item').click(function () {

                // Iterate items, give all normal color
                $('.folder-list-item').each(function () {
                    $(this).css({'background-color': listNorm});
                });
                // Then give the selected item the selected color
                $(this).css({'background-color': listSel});

                // Iterate all content and hide each
                $('.main-content-view').each(function () {
                    $(this).hide();
                });

                // Figure out which content to display and display it
                if ($(this).attr('id') == 'docs') {
                    $('#docs-content').fadeIn(500);
                } else if ($(this).attr('id') == 'projects') {
                    $('#projects-content').fadeIn(500);
                } else if ($(this).attr('id') == 'samples') {
                    $('#samples-content').fadeIn(500);
                } else if ($(this).attr('id') == 'about-me') {
                    $('#about-content').fadeIn(500);
                }
            });


        });

        DOMElements.fileManager = {};
        DOMElements.fileManager.docs = $("#docs-content");
        DOMElements.fileManager.categories = $("#projects-content");
        DOMElements.fileManager.galery = $("#about-content");


        $("#uploadDoc").click(function () {
            $("#docFile").click();
        });
        $("#docFile").change(function () {
            var obj = this;
            var sendData = [];
            showModalWithTableInside(function (head, body, modal, rand) {
                dynamicCreateToArray(body, sendData, {

                    category: {
                        name: "კატეგორია",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/usercategoriescats/" + currentElement.id
                    },
                    docType: {
                        name: "კატეგორია",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/doctypes/"
                    },
                }, function () {
                    console.log(sendData);
                    var formData = new FormData();
                    var xhr = new XMLHttpRequest();

                    for (var i = 0; i < obj.files.length; i++) {
                        //TODO Append in php files array
                        formData.append('file', obj.files[i]);
                        console.log('Looping trough passed data', obj.files[i]);
                    }

                    //On successful upload response, parse JSON data
                    //TODO handle response from php server script
                    xhr.onload = function () {
                        var data = JSON.parse(this.responseText);
                        loadDocumentsForUser(DOMElements, currentElement.id, 0)
                    };

                    //Open an AJAX post request
                    xhr.open('post', 'upload/' + currentElement.id + "?category=" + sendData[0].category + "&docType=" + sendData[0].docType);
                    xhr.send(formData);


                    modal.modal("hide");
                }, function () {

                }, function () {

                });

            }, {}, 400)
        });
        dropBoxFuncUserDocs('promptModal' + DOMElements.rand, 'upload/' + currentElement.id, function () {
            loadDocumentsForUser(DOMElements, currentElement.id, 0)
        }, currentElement.id);


        loadDocumentsForUser(DOMElements, currentElement.id, 0);
    }

    function loadDocumentsForUser(DOMElements, id, page) {

        $.getJSON("listdocs/" + id + "?page=" + page, function (result) {
            DOMElements.fileManager.docs.html("");
            var dataArray = result["content"];
            var totalPages = result["totalPages"];
            var totalElements = result["totalElements"];
            for (var i = 0; i < dataArray.length; i++) {
                var currentElement = dataArray[i];
                var itemLogos = "";
                var showName = currentElement.name;
                if (currentElement.name.length > 11) {
                    showName = currentElement.name.substring(0, 11);
                }
                var logo = 'fa-file';
                switch (currentElement.extension) {
                    case 'image/jpeg':
                        logo = 'fa-file-image-o';
                        break;
                    case 'application/pdf':
                        logo = 'fa-file-pdf-o';
                        break;
                    case 'application/vnd.ms-excel':
                    case 'application/vnd.openxmlformats-officedocument.presentationml.presentation':
                        logo = 'fa-file-excel-o';
                        break;
                    case 'application/zip':
                    case 'application/x-zip-compressed':
                        logo = 'fa-file-archive-o';
                        break;
                    case 'text/plain':
                        logo = 'fa-file-text-o';
                        break;
                    case 'application/vnd.openxmlformats-officedocument.wordprocessingml.document':
                    case 'application/msword':
                        logo = 'fa-file-word-o';
                        break;
                    case 'application/vnd.ms-powerpoint':
                        logo = 'fa-file-text-o';
                        break;
                    case 'video/quicktime':
                    case 'video/mpeg':
                    case 'video/x-ms-wmv':
                    case 'video/mp4':
                        logo = 'fa-file-video-o';
                        break;

                }


                DOMElements.fileManager.docs.append('<div value="'+currentElement.id+'" class="content-item">' +
                    '<div class="content-icon">' +
                    '   <i class="fa ' + logo + ' fa-3x"></i>' +
                    '   </div>' +
                    '   <div title="' + currentElement.name + '" class="content-description">' +
                    showName +
                    '   </div>' +
                    '   </div>');
            }
            $('.content-item').dblclick(function () {
                var ifrm = document.getElementById("frame1");
                ifrm.src = "doc/"+$(this).attr("value");
            });
            $('.content-item').draggable({
                handle: '.content-icon',
                opacity: 0.9,
                revert: true, helper: "clone",
                containment: 'document'
            });


        })
    }

    function dropBoxFuncUserDocs(id, url, callback, userId) {
        // Global variables
        var dropbox = document.getElementById(id);
        var uploadDest = url;
        var maxFiles = 2;
        //var allowedFiles = /(.*?)\.(jpeg|jpg|gif|png|pdf)$/;
        //TODO Create maxFiles var

        //TODO Limit file extensions

        //TODO create a function that displays uploded files in our html!!!
        function displayFiles() {

        }

        // AJAX function for file uploads
        function uploadFiles(files, data) {
            //FormData supports IE 10+ TODO falback
            var formData = new FormData();
            var xhr = new XMLHttpRequest();

            for (var i = 0; i < files.length; i++) {
                //TODO Append in php files array
                formData.append('file', files[i]);
                console.log('Looping trough passed data', files[i]);
            }

            //On successful upload response, parse JSON data
            //TODO handle response from php server script
            xhr.onload = function () {
                var data = JSON.parse(this.responseText);
                callback();
            };

            //Open an AJAX post request
            xhr.open('post', uploadDest + "?category=" + data.category + "&docType=" + data.docType);
            xhr.send(formData);
        }

        //Style dropbox on this event
        dropbox.ondragover = function () {
            //this.className = 'dropbox dragover';
            return false;
        }
        //Style dropbox on this event
        dropbox.ondragleave = function () {
            //this.className = 'dropbox';
            return false;
        }

        // Call uploadFiles function with arguments
        dropbox.ondrop = function (e) {
            //Prevent default browser behaviour
            e.preventDefault();
            var g = e.dataTransfer.files;
            //this.className = 'dropbox';
            console.log(e.dataTransfer.files);
            var sendData = [];
            showModalWithTableInside(function (head, body, modal, rand) {
                dynamicCreateToArray(body, sendData, {

                    category: {
                        name: "კატეგორია",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/usercategoriescats/" + userId
                    },
                    docType: {
                        name: "კატეგორია",
                        type: "comboBox",
                        valueField: "id",
                        nameField: "name",
                        url: "/doctypes/"
                    },
                }, function () {
                    console.log(sendData);
                    console.log(g);
                    uploadFiles(g, sendData[0]);
                    modal.modal("hide");
                }, function () {

                }, function () {

                });

            }, {}, 400)
        }
    }

    function drawInfoForUser(DOMElements) {
        DOMElements.infoDiv.html("");
        DOMElements.infoDiv.append(
            "<div id='categoryLogoDiv' class='row'>" +
            "<div class='col-md-2'></div>" +
            "<div class='col-md-2'>" +
            "<img id='profilePicBtn' style='width: 150px;cursor: pointer' src='profilePic/" + DOMElements.currentElement.id + "?" + new Date().getTime() + "'/>" +
            '<input type="file" id="profilePick" style="display:none">' +
            "</div>" +
            "</div>");
        $("#profilePicBtn").click(function () {
            $("#profilePick").click();
        });
        $("#profilePick").change(function (e) {
            console.log(this.files);
            var formData = new FormData();
            var xhr = new XMLHttpRequest();

            for (var i = 0; i < this.files.length; i++) {
                //TODO Append in php files array
                formData.append('file', this.files[i]);
                console.log('Looping trough passed data', this.files[i]);
            }

            //On successful upload response, parse JSON data
            //TODO handle response from php server script
            xhr.onload = function () {
                var data = JSON.parse(this.responseText);
                drawInfoForUser(DOMElements);
            };

            //Open an AJAX post request
            xhr.open('post', "uploadProfilePic/" + DOMElements.currentElement.id);
            xhr.send(formData);
        })
        DOMElements.infoDiv.append(
            "<div class='row'>" +
            "<div>" +
            "</div>"
        )
    }
}
