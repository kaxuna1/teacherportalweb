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
});