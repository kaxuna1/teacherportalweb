/**
 * Created by kakha on 11/12/2015.
 */
var currentPage = 0;
var currentData;
var projectColumns = ["#", "სახელი", "მისამართი", "საკადასტრო"];
var loanColumns = ["#", "ნივთები", "ნომერი", "კლიენტი", "რაოდენობა", "გაცემის თარიღი"];
var confiscatedColumns = ["#", "სახელი", "ნივთის #", "სესხის #", "შეფასება", "დარიცხული %", "გადახდილი", "სტატუსი"];
var orderColumns = ["#","კატეგორია","მასწავლებელი","მოსწავლე","თარიღი","ფასი"];
var stringsColumns = ["#","name","value"];
var userColumns = ["სახელი", "გვარი", "მომხმარებლის სახელი", "პირადი ნომერი", "მობილური"];
var loanConditionsColumns = ["#", "სახელი", "პროცენტი", "პირველი დღის %", "პერიოდი", "პერიოდის ტიპი"];
var periodTypes = {
    "1": "დღე",
    "2": "კვირა",
    "3": "თვე"
};
var paymentTypes = {
    1: "ძირის დაფარვა",
    2: "სრული გადახდა",
    3: "% გადახდა"
}
var loanStatuses = {
    1: "დატვირთული",
    2: "დაკავებული",
    3: "გაყიდული",
    4: "გათავისუფლებული",
    5: "გატანილი",
    6: "გასაყიდად გაგზავნილი"
};

var clientColumns = ["სახელი", "გვარი", "პირადი ნომერი", "საკონტაქტო ნომერი", "სესხების რაოდენობა"];
var categoryColumns = ["სახელი", 'მოქმედებები'];
var elementColumns = ["კოდი", 'სახელი'];
var regionColumns = ["სახელი"];
var projectStageTypeColumns = ["სახელი"];
var formatColumns = ["სახელი", "ფასი"];
var productRequestsColumns = ["#", "ფილიალი", "თარიღი", "მოთხოვნილი პროდუქტების რაოდენობა"];
var mosataniColumns = ["#", "პროდუქტი", "რაოდენობა", "მოიტანს"];
var tenderColumns = ["#", "სახელი", "შექმნის დრო", "დაწყების დრო", "დამთავრების დრო"];
var zoneColumns = ["სახელი", "რეგიონი"];
var tenderWonColumns = ["#", "ტენდერი", "პროდუქტი", "რაოდენობა", "ფასი"];
var requestFromTenderColumns = ["#", "ტენდერი", "პროდუქტი", "რაოდენობა", "ფილიალი", "#"];

var userTypes = {
    "1": "sa",
    "2": "admin",
    "3": "პრარაბი"
};
var canCreateProject = false;
var canCreateUsers = false;
var topPanelButtons = $("#topPanelButtons");
var addZoneToSelectedVisible = false;
var quantTypes = ['', 'კგ.', "ცალი"];
function dropBoxFunc(id, url, callback) {
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
    function uploadFiles(files) {
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
        xhr.open('post', uploadDest);
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

        //this.className = 'dropbox';
        console.log(e.dataTransfer.files);

        uploadFiles(e.dataTransfer.files);
    }
}