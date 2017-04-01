/**
 * Created by kakha on 3/30/2017.
 */
$(document).ready(function () {
    $("#logisignbtn").click(function () {
        showModalWithTableInside(function (head, body, modal, random, footer) {
            //$(".modal-header").remove();
            body.append(' <form action="loginapi" method="post" class="form-signin">' +
                ' <h2 class="form-signin-heading">Please login</h2>' +
                '<input style="margin-top: 5px;" type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus="" />' +
                '<input style="margin-top: 5px;" type="password" class="form-control" name="password" placeholder="Password" required=""/>' +
                '<button style="margin-top: 5px;background-color: #ee9cac;color: white;" id="signInBtn" class="btn btn-block btn-social"> <span class="fa fa-sign-in"></span> Sign in </button>' +
                '<a id="signInFB" onclick="loginWithFace()" class="btn btn-block btn-social btn-facebook"> <span class="fa fa-facebook"></span> Sign in with Facebook </a>' +
                '<a id="signInGoogle" class="btn btn-block btn-social btn-google"> <span class="fa fa-google"></span> Sign in with Google </a>' +
                '</form>');
            $("#signInBtn").click(function () {

            })

        }, {
            "sign up": function () {
                showModalWithTableInside(function (head, body, modal, random, footer) {
                    //graph.facebook.com/10202582199151436/picture?type=normal


                    body.append(' <form id="regForm" class="form-signin">' +
                        ' <h2 class="form-signin-heading">Sign Up</h2>' +
                        '<div id="imagePlace" style="text-align: center;padding-bottom: 10px" class="row"></div>' +
                        '<input type="text" id="nameReg" class="form-control reg-control " placeholder="Name" required="" autofocus="" />' +
                        '<input type="text" id="surnameReg" class="form-control reg-control"  placeholder="Surname" required="" autofocus="" />' +
                        '<input type="text" id="emailReg" class="form-control reg-control"  placeholder="Email Address" required="" autofocus="" />' +
                        '<input type="password" id="passwordReg" class="form-control reg-control"  placeholder="Password" required=""/>' +
                        '<input type="hidden" id="fbToken">' +
                        '<input type="hidden" id="googleToken">' +
                        '<a id="registerBtn" style="background-color: #ee9cac;color: white;" class="btn btn-block btn-social"> <span class="fa fa-sign-in"></span> Sign up </a>' +
                        '<a id="connectFB" class="btn btn-block btn-social btn-facebook"> <span class="fa fa-facebook"></span> Connect Facebook </a>' +
                        '<a id="connectGoogle" class="btn btn-block btn-social btn-google"> <span class="fa fa-google"></span> Connect Google </a>' +
                        '<div id="errorMessage"></div>' +
                        '</form>');

                    $("#emailReg").change(function () {
                        $.getJSON("emailexists?email=" + $(this).val(), function (result) {
                            $("#emailExistsError").remove();
                            if (result) {
                                $("#emailReg").addClass("reg-invalid");
                                $("#errorMessage").append("<p id='emailExistsError'>Email exists</p>")
                            } else {
                                $("#emailReg").removeClass("reg-invalid");
                            }
                        })
                    });
                    $("#connectFB").click(function () {
                        registerWithFacebook();
                    });
                    $("#registerBtn").click(function () {
                        $('.reg-control').removeClass("reg-invalid");
                        $("#emailExistsError").remove();
                        var regData = {};
                        var valid = true;
                        regData["name"] = $("#nameReg").val();
                        regData["surname"] = $("#surnameReg").val();
                        regData["email"] = $("#emailReg").val();
                        regData["password"] = $("#passwordReg").val();
                        for (key in regData) {
                            if (!regData[key]) {
                                $("#" + key + "Reg").addClass("reg-invalid");
                                valid = false;
                            }
                        }
                        if ($("#connectFB").attr("value")) {
                            regData["fbId"] = $("#connectFB").attr("value");
                        }
                        if (valid) {
                            $.ajax({
                                url: "registerapi",
                                data: regData
                            }).done(function (result) {
                                createCookie("projectSessionId", result["id"], 365);
                                location.reload();
                            })
                        }

                    })
                }, {}, 400, true)
            }
        }, 400, true)
    })
});
function checkRegValue(name, val) {
}

function createCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}
window.fbAsyncInit = function () {
    FB.init({
        appId: '917362565073485',
        xfbml: true,
        version: 'v2.8'
    });
    FB.AppEvents.logPageView();

};
function registerWithFacebook() {
    $("#emailExistsError").remove();
    FB.login(function (response) {
        //https://graph.facebook.com/me?access_token=
        //10202582199151436
        console.log(response);
        if (response.status = "connected") {
            FB.api("/me?fields=id,name,email", function (result) {
                console.log(result);
                if (result) {
                    var token = response.authResponse.accessToken;
                    $.getJSON("/loginapifb/" + token, function (session) {
                        console.log(session)
                        if (session.id) {
                            createCookie("projectSessionId", session["id"], 365);
                            location.reload();
                        } else {
                            $("#imagePlace").html("<img style='height: 150px' src='http://graph.facebook.com/" + result.id + "/picture?type=large'>");
                            var name = result.name.split(" ")[0];
                            var surname = result.name.split(" ")[1];
                            $("#nameReg").val(name);
                            $("#surnameReg").val(surname);
                            $("#emailReg").val(result.email);
                            $("#emailReg").change();
                            $("#connectGoogle").hide();
                            $("#connectFB").unbind().html('<span class="fa fa-facebook"></span>Facebook Connected');
                            $("#connectFB").attr("value", result.id);
                        }
                    })


                }
            });
            var token = response.authResponse.accessToken;
            /*$.getJSON("/loginapifb/" + token, function (result) {
             if (result) {
             createCookie("projectSessionId",result["id"],365);
             createCookie("userId",result["user"]["id"],365);
             location.reload();
             } else {
             alert("no such user")
             }
             })*/
        }
    }, {scope: 'email'});
}
function loginWithFace() {
    FB.login(function (response) {
        //https://graph.facebook.com/me?access_token=
        //10202582199151436
        if (response.status = "connected") {

            var token = response.authResponse.accessToken;
            $.getJSON("/loginapifb/" + token, function (result) {
                if (result) {
                    createCookie("projectSessionId", result["id"], 365);
                    location.reload();
                } else {
                    alert("no such user")
                }
            })
        }
    });
}
/*
 function onSignIn(googleUser) {
 var profile = googleUser.getBasicProfile();
 console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
 console.log('Name: ' + profile.getName());
 console.log('Image URL: ' + profile.getImageUrl());
 console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.


 var id_token = googleUser.getAuthResponse().id_token;
 $.getJSON("/loginapigoogle?token=" + id_token, function (result) {
 if (result) {
 createCookie("projectSessionId",result["id"],365);
 createCookie("userId",result["user"]["id"],365);
 location.reload();
 } else {
 alert("no such user")
 }
 })

 }
 */
$(".settingsBtn").click(function () {
    $.getJSON("/mydata", function (result) {
        console.log(result);
        showModalWithTableInside(function (head, body, modal, rand) {
            body.html("<ul class='settingsList'>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Name</h3>" +
                "<span class='settingsItemValue'>" + result.nameSurname + "</span>" +
                "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Email</h3>" +
                "<span class='settingsItemValue'>" + result.email + "</span>" +
                "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Password</h3>" +
                "<span class='settingsItemValue'>******</span>" +
                "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Language</h3>" +
                "<span class='settingsItemValue'>" + result.langName + "</span>" +
                "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Facebook</h3>" +
                "<span class='settingsItemValue'>" + getFacebookFieldForSettings(result) + "</span>" +
                "<span><span class='settingsItemEditButton'></span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Google</h3>" +
                "<span class='settingsItemValue'>" + getGoogleFieldForSettings(result) + "</span>" +
                "<span><span class='settingsItemEditButton'></span></span></a> </li>" +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Calendar</h3>" +
                "<span class='settingsItemValue'>" + getCalendarFieldForSettings(result) + "</span>" +
                "<span><span class='settingsItemEditButton'></span></span></a> </li>" +
                getCalendarChooserField(result) +
                "<li class='settingsItem'>" +
                "<a class='settingsItemA'>" +
                "<h3 class='settingsItemH'>Language</h3>" +
                "<span class='settingsItemValue'>" + result.langName + "</span>" +
                "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>" +
                "</ul>");
            $(".disconnectFbButton").click(function () {
                $.getJSON("/disconnect/1", function (result) {
                    if (result) {
                        modal.modal("hide");
                        $(".settingsBtn").click();
                    }
                })
            });
            $(".disconnectGoogleButton").click(function () {
                $.getJSON("/disconnect/2", function (result) {
                    if (result) {
                        modal.modal("hide");
                        $(".settingsBtn").click();
                    }
                })
            });
            $(".connectGoogleButton").click(function () {
                var auth2 = gapi.auth2.getAuthInstance();
                //console.log(auth2);
                auth2.signIn().then(function (response) {//request to sign in
                    console.log(response);
                    console.log(response.getId());
                    var id = response.getId();
                    $.ajax({
                        url: "connectSocial/2",
                        data: {
                            value: id
                        }
                    }).done(function (result) {
                        if (result) {
                            modal.modal("hide");
                            $(".settingsBtn").click();
                        } else {
                            alert("Cant connect account")
                        }
                    })
                });
            });
            $(".connectFbButton").click(function () {
                FB.login(function (response) {
                    console.log(response);
                    var id = response.authResponse.userID;
                    $.ajax({
                        url: "connectSocial/1",
                        data: {
                            value: id
                        }
                    }).done(function (result) {
                        if (result) {
                            modal.modal("hide");
                            $(".settingsBtn").click();
                        } else {
                            alert("Cant connect account")
                        }
                    })
                })
            });
            $(".connectCalendarButton").click(function () {
                window.open("https://accounts.google.com/o/oauth2/v2/auth?" +
                    "scope=https://www.googleapis.com/auth/calendar&" +
                    "access_type=offline&" +
                    "prompt=consent&" +
                    "include_granted_scopes=true&" +
                    "state=state_parameter_passthrough_value&" +
                    "redirect_uri=http://localhost:8081/oauthcall&" +
                    "response_type=code&" +
                    "client_id=55995473742-00obqav5bir1au4qdn4l1jgdvf7kbmv2.apps.googleusercontent.com", "_self");
            });
            $(".disconnectCalendarButton").click(function () {
                $.getJSON("/disconnect/3", function (result) {
                    if (result) {
                        modal.modal("hide");
                        $(".settingsBtn").click();
                    }
                })
            })

        }, {}, 500, true);


    })

});
function getFacebookFieldForSettings(user) {
    if (user.facebookConnected) {
        return "connected<button class='disconnectFbButton' style='margin-left: 5px'>Disconnect</button>";
    } else {
        return "<button class='connectFbButton'>connect</button>"
    }
}
function getGoogleFieldForSettings(user) {
    if (user.googleConnected) {
        return "connected<button class='disconnectGoogleButton' style='margin-left: 5px'>Disconnect</button>";
    } else {
        return "<button class='connectGoogleButton'>connect</button>";
    }
}
function getCalendarFieldForSettings(user) {
    if (user.calendarConnected) {
        return "connected<button class='disconnectCalendarButton' style='margin-left: 5px'>Disconnect</button>";
    } else {
        return "<button class='connectCalendarButton'>connect</button>";
    }
}
function getCalendarChooserField(user) {
    if (user.calendarConnected) {
        return "<li class='settingsItem'>" +
            "<a class='settingsItemA'>" +
            "<h3 class='settingsItemH'>Calendar To Use</h3>" +
            "<span class='settingsItemValue'>" + (user.calendarId ? user.calendarId : "not selected") + "</span>" +
            "<span><span class='settingsItemEditButton'>edit</span></span></a> </li>";
    }
    else {
        return "";
    }

}
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
}

$(".logoutBtn").click(function () {
    $.getJSON("/logout", function (result) {
        if (result) {
            eraseCookie("projectSessionId");
            window.location.href = "/";
        }
    })
});
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));