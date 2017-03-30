/**
 * Created by kakha on 3/30/2017.
 */
$(document).ready(function () {
    $("#logisignbtn").click(function () {
        showModalWithTableInside(function (head, body, modal, random,footer) {
            //$(".modal-header").remove();
            body.append(' <form class="form-signin">' +
                ' <h2 class="form-signin-heading">Please login</h2>' +
                '<input type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus="" />' +
                '<input type="password" class="form-control" name="password" placeholder="Password" required=""/>' +
                '<a style="background-color: #ee9cac;color: white;" class="btn btn-block btn-social"> <span class="fa fa-sign-in"></span> Sign in </a>' +
                '<a id="signInFB" class="btn btn-block btn-social btn-facebook"> <span class="fa fa-facebook"></span> Sign in with Facebook </a>' +
                '<a id="signInGoogle" class="btn btn-block btn-social btn-google"> <span class="fa fa-google"></span> Sign in with Google </a>' +
                '</form>');

        },{
            "sign up":function () {
                showModalWithTableInside(function (head, body, modal, random,footer) {
                    body.append(' <form class="form-signin">' +
                        ' <h2 class="form-signin-heading">Please login</h2>' +
                        '<input type="text" class="form-control reg-control" name="name" placeholder="Name" required="" autofocus="" />' +
                        '<input type="text" class="form-control reg-control" name="surname" placeholder="Surname" required="" autofocus="" />' +
                        '<div id="birthdayPicker"></div>' +
                        '<input type="text" class="form-control reg-control" name="username" placeholder="Username" required="" autofocus="" />' +
                        '<input type="text" class="form-control reg-control" name="email" placeholder="Email Address" required="" autofocus="" />' +
                        '<input type="password" class="form-control reg-control" name="password" placeholder="Password" required=""/>' +
                        '<a style="background-color: #ee9cac;color: white;" class="btn btn-block btn-social"> <span class="fa fa-sign-in"></span> Sign up </a>' +
                        '<a id="connectFB" class="btn btn-block btn-social btn-facebook"> <span class="fa fa-facebook"></span> Connect Facebook </a>' +
                        '<a id="connectGoogle" class="btn btn-block btn-social btn-google"> <span class="fa fa-google"></span> Connect Google </a>' +
                        '</form>');
                    $("#birthdayPicker").birthdayPicker();
                },{},400,true)
            }
        },400,true)
    })
});
