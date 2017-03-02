/**
 * Created by KGelashvili on 10/27/2015.
 */
var userRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="surnameField">გვარი</label> <input type="text" name="surname" class="form-control" id="surnameField" placeholder="გვარი"></div><div class="form-group"><label for="usernameField">მომხმარებლის სახელი</label> <input type="text" name="username"class="form-control"id="usernameField"placeholder="მომხმარებლის სახელი"></div><div class="form-group"><label for="passwordField">პაროლი</label> <input type="text" name="password" class="form-control" id="passwordField" placeholder="პაროლი"></div><div class="form-group"><label for="emailField">ელ ფოსტა</label> <input type="text" name="email"class="form-control" id="emailField"placeholder="ელ ფოსტა"></div><div class="form-group"><label for="addressField">მისამართი</label> <input type="text" name="address" class="form-control" id="addressField" placeholder="მისამართი"></div><div class="form-group"><label for="mobileField">საკონტაქტო ნომერი</label> <input type="text" name="mobile" class="form-control" id="mobileField" placeholder="საკონტაქტო ნომერი"></div><div class="form-group"><label for="personalNumberField">პირადი ნომერი</label> <input type="text"name="personalNumber"class="form-control"id="personalNumberField"placeholder="პირადი ნომერი"></div><div class="form-group"><label for="filialIdField">ფილიალი</label> <select name="filialIdFieldId" id="filialIdField" class="form-control"> </select></div><div class="form-group"><label for="typeField">მომხმარებლის ტიპი</label> <select name="type" id="typeField" class="form-control"> </select></div></form>';
var filialsRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="addressField">მისამართი</label> <input type="text" name="address"class="form-control" id="addressField"placeholder="მისამართი"></div></form>';
var regionRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label><input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div></form>';
var formatRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label><input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="priceField">ფასი</label><input type="text" name="price" class="form-control" id="priceField" placeholder="ფასი"></div></form>';
var serviceTypeRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label><input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="priceField">ფასზე ნამატი</label><input type="text" name="price" class="form-control" id="priceField" placeholder="ფასზე ნამატი"></div></form>';
var elementRegistrationFormTemplate = '<form> <div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="nameField" class="form-control" id="nameField" placeholder="სახელი"></div> <div class="form-group"><label for="barcodeField">კოდი</label> <input type="text" name="barcodeField"class="form-control" id="barcodeField"placeholder="კოდი"></div> </form>';
var zoneRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label><input type="text" name="reciever" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="regionIdField">რეგიონი</label><select name="regionId" id="regionIdField" class="form-control"></select></div></form>';
var tenderRegistrationFormTemplate = '<form><div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="dateStartField">დაწყების დრო</label> <input type="datetime-local" name="surname"class="form-control" id="dateStartField"></div><div class="form-group"><label for="dateEndField">დასრულების დრო</label> <input type="datetime-local" name="username" class="form-control"id="dateEndField" ></div></form>';
var etapebiPageTemplate='<div class="page-content page-app mailbox"> <section class="app"> <aside class="aside-md emails-list" style="display: table-cell;"> <section style="padding: 0px"> <div class="mailbox-page clearfix"> <h1 class="pull-left">ეტაპები</h1> <div class="append-icon"> <input type="text" class="form-control form-white pull-right" id="email-search"placeholder="ძებნა..."> <i class="icon-magnifier"></i> </div> </div> <ul class="nav nav-tabs text-right"> <li class="emails-action"> <i class="icon-rounded-arrow-curve-left"></i> <i class=" icon-rounded-heart"></i> <div class="pos-rel dis-inline"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"data-close-others="true" data-delay="300"> <i class=" icon-rounded-arrow-down-thin"></i> </a> <ul class="dropdown-menu"> <li><a href="#" class="reorder-menu">Select All</a></li> <li><a href="#" class="remove-menu">Not Read</a></li> <li><a href="#" class="hide-top-sidebar">Read</a></li> </ul> </div> </li> <li class="f-right"><a href="#recent" data-toggle="tab">tab 1</a></li> <li class="active f-right"><a href="#alphabetically" data-toggle="tab">tab 2</a></li> </ul> <div class="tab-content"> <div class="tab-pane fade in active" id="alphabetically"> <div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180"data-height="window"> <div class="mCustomScrollBox mCS-light" id="mCSB_18"style="position:relative; height:100%; overflow:hidden; max-width:100%;"> <div id="etapebiContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div> <div class="mCSB_scrollTools" style="position: absolute; display: none;"> <div class="mCSB_draggerContainer"> <div class="mCSB_dragger" style="position: absolute; top: 0px;"oncontextmenu="return false;"> <div class="mCSB_dragger_bar" style="position:relative;"></div> </div> <div class="mCSB_draggerRail"></div> </div> </div> </div> </div> </div> <div class="tab-pane fade " id="recent"> <div id="tab2Content"></div> </div> </div> </section> </aside> </section> </div>';
var actionsPageTemplate='<div class="page-content page-app mailbox"> <section class="app"> <aside class="aside-md emails-list" style="display: table-cell;"> <section style="padding: 0px"> <div class="mailbox-page clearfix"> <h1 class="pull-left">მოქმედებები</h1> <div class="append-icon"> <input type="text" class="form-control form-white pull-right" id="email-search"placeholder="ძებნა..."> <i class="icon-magnifier"></i> </div> </div> <ul class="nav nav-tabs text-right"> <li class="emails-action"> <i class="icon-rounded-arrow-curve-left"></i> <i class=" icon-rounded-heart"></i> <div class="pos-rel dis-inline"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"data-close-others="true" data-delay="300"> <i class=" icon-rounded-arrow-down-thin"></i> </a> <ul class="dropdown-menu"> <li><a href="#" class="reorder-menu">Select All</a></li> <li><a href="#" class="remove-menu">Not Read</a></li> <li><a href="#" class="hide-top-sidebar">Read</a></li> </ul> </div> </li> <li class="f-right"><a href="#recent" data-toggle="tab">tab 1</a></li> <li class="active f-right"><a href="#alphabetically" data-toggle="tab">tab 2</a></li> </ul> <div class="tab-content"> <div class="tab-pane fade in active" id="alphabetically"> <div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180"data-height="window"> <div class="mCustomScrollBox mCS-light" id="mCSB_18"style="position:relative; height:100%; overflow:hidden; max-width:100%;"> <div id="actionsContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div> <div class="mCSB_scrollTools" style="position: absolute; display: none;"> <div class="mCSB_draggerContainer"> <div class="mCSB_dragger" style="position: absolute; top: 0px;"oncontextmenu="return false;"> <div class="mCSB_dragger_bar" style="position:relative;"></div> </div> <div class="mCSB_draggerRail"></div> </div> </div> </div> </div> </div> <div class="tab-pane fade " id="recent"> <div id="tab2ContentActions"></div> </div> </div> </section> </aside> </section> </div>';
var actionItemsListTemplate='<div class="col-md-4 col-sm-6 portlets ui-sortable"> <div class="panel"> <div class="panel-header"><h3><i class="icon-list"></i> <strong>ხარჯების</strong> სია</h3></div> <div class="panel-content"> <ul id="expansesUl" class="todo-list ui-sortable"></ul> <div class="clearfix m-t-10"> <div class="pull-left"> <button type="button" class="btn btn-sm btn-default check-all-tasks">მონიშნე ყველა</button> </div> <div class="pull-right"> <button type="button" id="addExpanse" class="btn btn-sm btn-dark add-task"> დამატება</button> </div> </div> </div> </div> </div> <div class="col-md-8 col-sm-6 portlets ui-sortable"> <div class="panel"> <div class="panel-header"><h3><i class="icon-data"></i> <strong>ინფორმაცია</strong> მოქმედებაზე</h3></div> <div class="panel-content"> <div id="actionDataDiv"></div> <div class="clearfix m-t-10"> <div class="pull-left"> <button type="button" class="btn btn-sm btn-default">ღილაკი 1</button> </div> <div class="pull-right"> <button type="button"class="btn btn-sm btn-dark"> ღილაკი 2</button> </div> </div> </div> </div> </div> ';
var projectActiveActionsTemplate='<div class="page-content page-app mailbox"> <section class="app"> <aside class="aside-md emails-list" style="display: table-cell;"> <section style="padding-top: 0px;padding-left: 0px;padding-right: 0px;"> <div class="mailbox-page clearfix"><div class="append-icon"></div> </div> <ul class="nav nav-tabs text-right"> <li class="emails-action"> <div class="pos-rel dis-inline"><a href="#" style="padding-top: 0px;padding-right: 0px;padding-left: 0px;padding-bottom: 0px;background-color: transparent;" class="dropdown-toggle" data-toggle="dropdown"data-hover="dropdown" data-close-others="true"data-delay="300"> <i class=" icon-rounded-arrow-down-thin"></i> </a> <ul class="dropdown-menu"> <li><a href="#" id="lastActionsTableAction1" >მოქმედება 1</a></li> <li><a href="#" id="lastActionsTableAction2">მოქმედება 2</a></li> <li><a href="#" id="lastActionsTableAction3">მოქმედება 3</a></li> </ul> </div> </li> <li class="f-right"><a href="#recent" data-toggle="tab">tab 1</a></li> <li class="active f-right"><a href="#alphabetically" data-toggle="tab">tab 2</a></li> </ul> <div class="tab-content"> <div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180"data-height="window"> <div class="mCustomScrollBox mCS-light" id="mCSB_18"style="position:relative; height:100%; overflow:hidden; max-width:100%;"> <div id="lastActionsContainerDiv" class="mCSB_container mCS_no_scrollbar"style="position:relative; top:0;"></div> <div class="mCSB_scrollTools" style="position: absolute; display: none;"> <div class="mCSB_draggerContainer"> <div class="mCSB_dragger" style="position: absolute; top: 0px;"oncontextmenu="return false;"> <div class="mCSB_dragger_bar" style="position:relative;"></div> </div> <div class="mCSB_draggerRail"></div> </div> </div> </div> </div> </div> <div class="tab-pane fade " id="recent"> <div id="tab2Content"></div> </div> </div> </section> </aside> </section> </div>';
var projectStageTypeRegistrationFormTemplate = '<form> <div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="nameField" class="form-control" id="nameField" placeholder="სახელი"></div></form>';
var projectRegistrationTemplate='<form><div class="form-group"><label for="nameField">პროექტის სახელი</label> <input type="text" name="nameField"class="form-control" id="nameField"placeholder="პროექტის სახელი"></div><div class="form-group"><label for="addressField">მისამართი</label> <input type="text" name="barcodeField" class="form-control" id="addressField" placeholder="მისამართი"></div><div class="form-group"><label for="sakField">საკადასტრო კოდი</label> <input type="text" name="sakField"class="form-control"id="sakField" placeholder="საკადასტრო კოდი"></div><div class="form-group"><label for="xField">კოორდინატი X</label> <input type="text" name="xField" class="form-control" id="xField"placeholder="კოორდინატი X"></div><div class="form-group"><label for="yField">კოორდინატი Y</label> <input type="text" name="yField" class="form-control" id="yField"placeholder="კოორდინატი Y"></div></form>';
var projectPrarabLastActionsTemplate='';
var clientRegistrationFormTemplate='<form><div class="form-group"><label for="nameField">სახელი</label> <input type="text" name="name" class="form-control" id="nameField" placeholder="სახელი"></div><div class="form-group"><label for="surnameField">გვარი</label> <input type="text" name="surname" class="form-control" id="surnameField" placeholder="გვარი"></div><div class="form-group"><label for="mobileField">საკონტაქტო ნომერი</label> <input type="text" name="mobile" class="form-control" id="mobileField" placeholder="საკონტაქტო ნომერი"></div><div class="form-group"><label for="personalNumberField">პირადი ნომერი</label> <input type="text"name="personalNumber"class="form-control"id="personalNumberField"placeholder="პირადი ნომერი"></div></form>';
var clientChooserTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="mailbox-page clearfix"><button class="btn" id="createClientButton">კლიენტის დამატება</button><div class="append-icon"><input type="text" class="form-control form-white pull-right"id="clientSearch" placeholder="ძებნა..."> <iclass="icon-magnifier"></i></div></div><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="clientsContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var uzrunvelyofaGridTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="uzrunvelyofaContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var uzrunvelyofaGridTemplateConfiscate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="uzrunvelyofaConfiscateContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var loansForClientGridTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="clientLoansContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var loanMovementsGridTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="loanMovementsContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var loanInterestGridTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="loanInterestsContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var loanPaymentGridTemplate='<div class="page-content page-app mailbox"><section class="app"><aside class="aside-md emails-list" style="display: table-cell;"><section style="padding: 0px"><div class="tab-content"><div class="tab-pane fade in active" id="alphabetically"><div class="messages-list withScroll show-scroll mCustomScrollbar _mCS_18" data-padding="180" data-height="window"><div class="mCustomScrollBox mCS-light" id="mCSB_18" style="position:relative; height:100%; overflow:hidden; max-width:100%;"><div id="loanPaymentsContainerDiv" class="mCSB_container mCS_no_scrollbar" style="position:relative; top:0;"></div><div class="mCSB_scrollTools" style="position: absolute; display: none;"><div class="mCSB_draggerContainer"><div class="mCSB_dragger" style="position: absolute; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="position:relative;"></div></div><div class="mCSB_draggerRail"></div></div></div></div></div></div><div class="tab-pane fade " id="recent"><div id="tab2Content"></div></div></div></section></aside></section></div>';
var clientProfileTemplate='<div class="row"><div class="col-md-8"><div claass="panel"><div class="panel-header panel-controls"><h3><strong id="profile-header-1"></strong></h3></div><div class="panel-content"><div class="nav-tabs2"><ul class="nav nav-tabs nav-red"><li class="active"><a href="#tab5_1" data-toggle="tab"><i class="fa fa-balance-scale"></i>დოკუმენტები</a></li><li><a href="#tab5_2" data-toggle="tab"><i class="icon-user"></i> უფლებები</a></li><li><a href="#tab5_3" data-toggle="tab"><i class="icon-cloud-download"></i> გაკვეთილები</a></li><li><a href="#tab5_4" data-toggle="tab"><i class="icon-cloud-download"></i> გადახდები</a></li></ul><div class="tab-content"><div class="tab-pane active" id="tab5_1"><p></p></div><div class="tab-pane" id="tab5_2"></div><div class="tab-pane" id="tab5_3"></div><div class="tab-pane" id="tab5_4"></div></div></div></div></div></div><div class="col-md-4"><div class="panel"><div class="panel-header panel-controls"><h3><strong id="profile-header-2"></strong></h3></div><div class="panel-content"><div class="nav-tabs2"><ul class="nav nav-tabs nav-red"><li><a href="#tab6_1" data-toggle="tab"><i class="icon-home"></i> მოქმედებები</a></li><li class="active"><a href="#tab6_2" data-toggle="tab"><i class="icon-user"></i> ინფო.</a></li></ul><div class="tab-content"><div class="tab-pane " style="font-weight: bold" id="tab6_1"></div><div class="tab-pane active" style="font-weight: bold" id="tab6_2"></div></div></div></div></div></div></div>';
var clPaginationTemplate='<div class="row"><div class="col-md-6"><div class="dataTables_paginate paging_simple_numbers"><ul class="pagination" id="cLPagination"></ul></div></div></div>';
var clientProfileUzrunvelyofasTemplate='<table id="clientUzrunvelyofasDataTable" class="table table-hover table-dynamic"><thead><tr><th>სახელი</th><th>ნომერი</th><th>გაცემული</th><th>ძირი</th></tr></thead><tbody id="clientUzrunvelyofaDataTableBody"></tbody></table>';
var loanAddingUzrunvelyofasTemplate='<table id="loanAddingUzrunvelyofasDataTable" class="table table-hover table-dynamic"><thead><tr><th>სახელი</th><th>ნომერი</th><th>შეფასება</th><th>სტატუსი</th></tr></thead><tbody id="loanAddingUzrunvelyofasDataTableBody"></tbody></table>';
var clientProfileUzrunvelyofasPaginationTemplate='<div class="row"><div class="col-md-6"><div class="dataTables_paginate paging_simple_numbers"><ul class="pagination" id="cLUzPagination"></ul></div></div></div>';
var clientProfilePaymentsTemplate='<table id="clientPaymentDataTable" class="table table-hover table-dynamic"><thead><tr><th>თარიღი</th><th>თანხა</th> <th>სესხი</th> <th>ტიპი</th> <th>გამოყენებული</th> </tr></thead><tbody id="clientPaymentDataTableBody"></tbody></table>';
var clientProfilePaymentsPaginationTemplate='<div class="row"><div class="col-md-6"><div class="dataTables_paginate paging_simple_numbers"><ul class="pagination" id="cLPzPagination"></ul></div></div></div>';
var loanInterestsTableTemplate='<table id="loanInterestDataTable" class="table table-hover table-dynamic">    <thead>    <tr>        <th>#</th>        <th>დარიცხვის თ.</th>        <th>გადახდის თ.</th>        <th>სტატუსი</th>    </tr>    </thead>    <tbody id="loanInterestDataTableBody"></tbody></table>';
var loanPaymentsTableTemplate='<table id="loanPaymentDataTable" class="table table-hover table-dynamic">    <thead>    <tr>        <th>#</th>            <th>გადახდის თ.</th>        <th>სტატუსი</th>    </tr>    </thead>    <tbody id="loanPaymentDataTableBody"></tbody></table>';
var loanCreatingUzrunvelyofasTemplate='<table id="loanCreatingUzrunvelyofasDataTable" class="table table-hover table-dynamic"><thead><tr><th>სახელი</th><th>შეფასება</th></tr></thead><tbody id="loanCreatingUzrunvelyofasDataTableBody"></tbody></table>';
var loanCloseUzrunvelyofasTemplate='<table id="loanCloseUzrunvelyofasDataTable" class="table table-hover table-dynamic"><thead><tr><th>სახელი</th><th>ნომერი</th><th>ძირი</th><th>%</th></tr></thead><tbody id="loanCloseUzrunvelyofaDataTableBody"></tbody></table>';
var UzrunvelyofasTemplate='<table id="UzrunvelyofasDataTable" class="table table-hover table-dynamic"><thead><tr><th>სახელი</th><th>ნომერი</th><th>გაცემული</th><th>ძირი</th><th>%</th><th>განაკვეთი</th><th>სტატუსი</th></tr></thead><tbody id="UzrunvelyofaDataTableBody"></tbody></table>';




var PaymentsTemplate='<table id="PaymentsDataTable" class="table table-hover table-dynamic"><thead><tr><th>დანიშნულება</th><th>რაოდენობა</th><th>სტატუსი</th><th>ინფო.</th></tr></thead><tbody id="PaymentsDataTableBody"></tbody></table>';
var DocumentsTemplate='<table id="DocumentsDataTable" class="table table-hover table-dynamic"><thead><tr><th>#</th><th>სახელი</th><th>ატვირთვის თარიღი</th><th>დათვალიერება</th></tr></thead><tbody id="DocumentsDataTableBody"></tbody></table>';
