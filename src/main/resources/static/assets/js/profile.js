/**
 * Created by vakhtanggelashvili on 5/21/17.
 */

$(document).ready(function () {
    $(".profilePicProfilePage").click(function () {
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
            window.location.reload()
        };

        //Open an AJAX post request
        xhr.open('post', "uploadProfilePic/");
        xhr.send(formData);
    })
    $(".profileEditButton").click(function () {


        var title = "";
        var text = "";
        var sendUrl = "/editme?";
        var inputType = "textarea";

        var inputOptions = {};

        var type = $(this).attr("value");

        if (type === "about") {
            title = "Enter";
            text = "Information about you";
        }
        if (type === "academic") {
            title = "Academic Credentials";
        }
        if (type === "current") {
            title = "Current Employment";
        }
        if (type === "succeed") {
            title = "Succeed";
        }
        if (type === "skills") {
            title = "Skills";
        }
        if(type === "city"){
            inputType = 'select';
            title = "Choose";
            text = "Your city!";
            inputOptions = {
                '1': 'Tbilisi'
            }
        }
        if(type === "address"){
            title = "Enter";
            text = "Your address!";
            inputType = "text";
        }
        if(type === "phone"){
            title = "Enter";
            text = "Your phone number!";
            inputType = "text";
        }
        swal({
            title: title,
            text: text,
            inputOptions: inputOptions,
            input: inputType,
            imageWidth: 400,
            imageHeight: 200,
            animation: false
        }).then(function (val) {
            if (val === "") {

                swal(
                    'Error',
                    'You need to write something',
                    'error'
                )
                return false
            }
            $.getJSON(sendUrl + type + "=" + val, function (result) {
                var text2= "Changes saved!";
                if(text){
                    text2 = "You changed: " + text
                }
                if (result) {
                    swal({
                        title: "Nice!",
                        text: text2,
                        type: "success"
                    }).then(
                        function () {
                            location.reload()
                        }
                    );

                }
            })
        })



    })
});