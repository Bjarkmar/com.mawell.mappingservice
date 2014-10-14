// ## ## GLOBAL VARIABLES ## ## //

var activeView = "import";
var activeViewExtended = false;
var idInput;
var idTypeInput;
var hsaidInput;

var dataHasBeenSent = false;

var idCheck = false;
var idTypeCheck = false;
var hsaidCheck = false;

//
//Defines the input fields that are to be displayed.
//Each field needs an id and a label. The tooltip is optional.
//
var importData = [
  {
	  label: "Namn",
	  id : "name",
	  disabled : true,
	  visible : true,
	  preset : true,
	  placeHolder : ""
  },
  {
	  label: "HSA-id",
	  id : "hsaid",
	  tooltip : "HSA-id hjälptext kan läggas här",
	  disabled : false,
	  visible : true,
	  preset : true,
	  placeHolder : "HSA-id"
  },
  {
	  label: "Remitterande enhets id i lokalt RIS",
	  id : "id",
	  tooltip : "HSA-id hjälptext kan läggas här",
	  disabled : false,
	  visible : false,
	  preset : true,
	  placeHolder : ""
  },
  {
	  label: "Kod för sändande RIS",
	  id : "id_type",
	  tooltip : "Id-typ hjälptext kan läggas här",
	  disabled : false,
	  visible : false,
	  preset : true,
	  placeHolder : ""
  },

  
]

//## ## END OF GLOBAL VARIABLES ## ## //


$(document).ready(function(){
	buildTabs();
	buildImportLayout();
	buildExportLayout();
	
	setTooltips();
	
	//Validate input-value if value is set initially
	for(var i = 0; i < importData.length; i++){
		if($("#"+ importData[i].id + "_input").val() != ""){
			validateImportForm($("#"+ importData[i].id + "_input")[0]);
		}
	}
	
	$("#id_input, #id_type_input, #hsaid_input").keyup(function(){
		validateImportForm(this);
	});
	
});

function buildTabs(){
	var importTab = "<div onclick='switchView(this)' id='importTab' value='import' class='tabItem activeTab'>Lägg till&nbsp;&nbsp;&nbsp;&nbsp;</div>";
	var exportTab = "<div onclick='switchView(this)' id='exportTab' value='export' class='tabItem'>Exportera</div>";
	
	
	$("#mainContainer").prepend(importTab + exportTab);
}


function buildImportLayout(){
	$("#mainContainer_import").append(createSectionHeader("Lägg till Mappning"));
	
	$("#mainContainer_import").append(createImportForm());
	$("#mainContainer_import").append(createShowAlterToggle());
	$("#mainContainer_import").append(createButton("Lägg till mappning", "right"));
}

function buildExportLayout(){
	$("#mainContainer_export").append(createSectionHeader("Exportera Mappning"));
	
	var exportButton = "<p class='exportLabel'>Exportera mappningsdata till tabell (.csv)</p>" +
			"<div id='exportButton' onclick='exportData();' class='button borderRadius exportButtonClass'><span>Exportera mappningar som tabell</span></div>";
	
	$("#mainContainer_export").append(exportButton);
}


function createSectionHeader(label){
	return "<div class='sectionHeader'>"+ label +"</div>";
}

function createImportForm(){
	var inputFields = "";
	var maxlength;
	var mandatory = false; 
	
	for(var i = 0; i < importData.length; i++){
		if(i > 0){
			mandatory = true;
			maxlength = 40;
			
			if(i == 1){
				maxlength = 64;
			}
		}
		else{
			mandatory = false;
			maxlength = 40;
		}
		inputFields += createInputField(importData[i].label, importData[i].id, mandatory, maxlength, importData[i].disabled, importData[i].visible, importData[i].preset, importData[i].placeHolder);
	}
	
	return inputFields;
}


function createInputField(label, id, mandatory, maxkeys, disabled, visible, preset, placeHolder){
	var mandatorySign = "";
	
	if(mandatory){
		mandatorySign = "*"; 
	}
	
	return "<div "+
	(!visible ? "style='display: none;'" : "") +
	"id='"+ id +"_inputContainer'>"+
	"<span id='" + id + "_label' class='inputLabel'>"+ label + " <span class='mandatoryInput'>" + mandatorySign +"</span></span>"+
	"<input class='inputClass borderRadius boxShadow'"+
	(placeHolder != "" ? "placeholder='"+ placeHolder +"'" : '') +
	(preset ? "value='"+ getURLParameter(id) +"'" : '') +
	(disabled ? "disabled='disabled' style='background-color: #ddd;'" : '') +
	"type='text' id='"+ id +"_input' maxlength='"+ maxkeys +"'/>"+
	"<div class='clearFix'></div>"+
	"</div>";
	
}

function createShowAlterToggle(){
	var html = "";
	
	html += "<div id='showAlterView' onclick='toggleAlterView();'>" +
	"Visa utökad vy" +
	"<img src='images/showMore.png' />" +
	"</div>";
	
	return html;
}

function toggleAlterView(){
	if(!activeViewExtended){
		setTimeout(function(){
			$("#id_inputContainer, #id_type_inputContainer").slideDown(250);
			$("#showAlterView").html(
				"Visa förenklad vy" +
				"<img src='images/showLess.png' />");
			activeViewExtended = true;
			
			for(var i = 0; i < importData.length; i++){
				validateImportForm(importData[i]);
			}
			
		}, 75);
	}
	else{
		setTimeout(function(){
			$("#id_inputContainer, #id_type_inputContainer").slideUp(250);
			$("#showAlterView").html(
					"Visa utökad vy" +
					"<img src='images/showMore.png' />");
			activeViewExtended = false;
			for(var i = 0; i < importData.length; i++){
				validateImportForm(importData[i]);
			}
		}, 75);
	}
	
}

function createButton(label, position){
	return "<div id='import_submitButton' class='button uploadButtonUpload borderRadius float_"+ position +" button_disabled'>"+ label +"</div>";
}

function setTooltips(){
	//If a tooltip is defined in the importData-object (see global variables at top of document) then it is built here
	
	for(var i = 0; i < importData.length; i++){
		
		if(importData[i].tooltip != undefined){
			
			$("#" + importData[i].id + "_input").after(
					"<div style='position: relative;'>"+
						"<span id='"+ importData[i].id +"_input_tooltip' class='tooltip'>"+
							importData[i].tooltip +
						"</span>" +
						"<div id='"+ importData[i].id +"_input_tooltipArrow' class='tooltipArrow'>"+
						"</div>"+
					"</div>");
			
			$("#" + importData[i].id + "_input").on('mouseover focus', function(){
					showTooltip(this.id, 350);
			});
			
			$("#" + importData[i].id + "_input").on('mouseout blur', function(){
					hideTooltip(this.id);
			});
		}
	}
}

function alterATooltip(id, label, type){
	toggleATooltip(id, "hides");
	
	if(type == "error"){
		$("#" + importData[id].id + "_input_tooltip").addClass('tooltipErrorType');
	}
	else{
		$("#" + importData[id].id + "_input_tooltip").removeClass('tooltipErrorType');
	}
	
	$("#" + importData[id].id + "_input_tooltip").html(label);
	toggleATooltip(id, "show");
}

function toggleATooltip(id, action){
	if(action == "hide"){
		$("#" + importData[id].id + "_input_tooltipArrow, #" + importData[id].id + "_input_tooltip" ).hide();	
	}
	else{
		$("#" + importData[id].id + "_input_tooltipArrow, #" + importData[id].id + "_input_tooltip" ).show();
	}
}

var timer = null;
function showTooltip(id, timeout){
	timer = setTimeout(function(){
		$("#" + id + "_tooltip, #"+ id  +"_tooltipArrow").fadeIn(150);
	}, timeout);
}

function hideTooltip(id){
	clearTimeout(timer);
	
	setTimeout(function(){
		$("#" + id + "_tooltip, #"+ id  +"_tooltipArrow").fadeOut(150);
	}, 100);
}

function switchView(obj){
	view = $(obj).attr("value");
	
	if(view != activeView){
		$("#divError").toggle();
		$(".activeTab").removeClass('activeTab');
		$(obj).addClass('activeTab');
		
		$("#mainContainer_" + activeView).hide();
		$("#mainContainer_" + view).show();
		
		//change the current active view to the selected
		activeView = view;
	}
}
// Here is validation of fields controlled.
function validateImportForm(obj){
	var hsaid_re= /^\SE\d{10}(\-)([a-zA-Z0-9]{1,51})$/;
	
	if(obj.id == "hsaid_input" && hsaid_re.test(obj.value) && obj.value.length >! 64){
		hsaidCheck = true;
		hsaidInput = $("#hsaid_input").val();
		alterATooltip(1, importData[1].tooltip, "normal")
		toggleATooltip(1, "hide");
		addSuccessClass(obj.id);
		
	}
	else if(obj.value != "" && obj.id != "hsaid_input"){
		//addSuccessClass(obj.id);
		if(obj.id == "id_input"){
			idCheck = true;
			idInput = $("#id_input").val();
			addSuccessClass(obj.id);
		}
		else if(obj.id == "id_type_input"){
			idTypeCheck = true;
			idTypeInput = $("#id_type_input").val();
			addSuccessClass(obj.id);
		}
	}
	else{
		//addFailClass(obj.id);
		if(obj.id == "id_input"){
			idCheck = false;
			idInput = "";
			removeSuccessClass(obj.id);
		}
		else if(obj.id == "id_type_input"){
			idTypeCheck = false;
			idTypeInput = "";
			removeSuccessClass(obj.id);
			
		}
		else if(obj.id == "hsaid_input"){
			hsaidCheck = false;
			hsaidInput = "";
			alterATooltip(1, "HSA-id är felformaterat", "error")
			removeSuccessClass(obj.id);
		}
	}
	
	
//	if(hsaidCheck && (!activeViewExtended || (idCheck && idTypeCheck))){
	if(hsaidCheck && idCheck && idTypeCheck){
		$("#import_submitButton").removeClass('button_disabled');
		
		$("#import_submitButton").click(function(){
			if(!dataHasBeenSent){
				closeErrorMsg();
				sendData();
				dataHasBeenSent = true;
			}
		});
	}
	else{
		$("#import_submitButton").addClass('button_disabled');

	}
}

function addSuccessClass(id){
	$("#"+ id).addClass('inputSuccess');
}

function removeSuccessClass(id){
	$("#"+ id).removeClass('inputSuccess');
}

//Configuration based on server.
function sendData(){
	toggleSendButton("Loading", "Skickar");
	
	var urlParams = "id=" + idInput + "&idType=" + idTypeInput + "&hsaid=" + hsaidInput;
	
	if($("#name_input").val() != ""){
		urlParams += "&name=" + $("#name_input").val();
	}
	
setTimeout(function(){
	console.log(urlParams);
	
	
    $.ajax({
    	url: "http://172.16.40.29:8080/com.mawell.mappingservice/InsertEntry/?" + urlParams, //mapper1.bfr.vgregion.se
        async: false,
        cache: true,
       type: "GET",
       contentType: "application/json; charset=UTF-8",
       success:
           function (data) {
    	   	   toggleSendButton("Success", "Skickat");
               alert(data);
            },
        error: function (jqXHR, textStatus, err) {
        	toggleSendButton("Fail", "Misslyckades");
            $('#divError').addClass("error");
            $('#closeErrorBox').addClass("closeErrorShow");
            $('#divError').html('Error: ' + err);
            $('#divError').slideDown(100);
        }
    });
}, 2000);
}

function toggleSendButton(param, label){
	var button = $("#import_submitButton");
	$(button).removeClass("uploadButtonUpload uploadButtonFail uploadButtonTryagain uploadButtonSuccess uploadButtonLoading");
	
	if(label == "Skickat" || label == "Skickar"){
		$(button).animate({
			width: '52px'
		}, 150);
	}
	else if (label == "Misslyckades"){
		$(button).animate({
			width: "98px"
		}, 200)
	}
	
	setTimeout(function(){
			$(button).addClass("uploadButton" + param);
			$(button).html(label);
			
			$(button).animate({
				color: "#fff"
			}, 50);
	});
				
//	if(label == "Misslyckades" && (hsaidCheck && (!activeViewExtended || (idCheck && idTypeCheck)))){
	if(label == "Misslyckades" && hsaidCheck && idCheck && idTypeCheck){
		setTimeout(function(){
			setRetryButton(button);
		}, 5000);
	}
}	

function setRetryButton(button){
	$(button).animate({
		width: "80px"
	}, 200, function(){
	
		$(button).removeClass('uploadButtonFail');
		$(button).html("Försök igen");
		$(button).addClass("uploadButtonTryagain");
		
		dataHasBeenSent = false;
	});
}
	
function resetLoadingButton(button){
	$("#divError").empty();
	$("#divError").removeClass('error');
	$(button).removeClass("uploadButtonTryagain");
	$(button).addClass("uploadButtonLoading");
	
	$(button).animate({
		width: "52px",
		backgroundColor: "#53a93f"
	}, 200)
	
	$(button).html("Skickar");

	sendData();
}

//DOWNLOAD SCRIPT BEGINS HERE
window.downloadFile = function (sUrl) {

    //iOS devices do not support downloading. We have to inform user about this.
    if (/(iP)/g.test(navigator.userAgent)) {
        alert('Your device does not support files downloading. Please try again in desktop browser.');
        return false;
    }

    //If in Chrome or Safari - download via virtual link click
    if (window.downloadFile.isChrome || window.downloadFile.isSafari) {
        //Creating new link node.
        var link = document.createElement('a');
        link.href = sUrl;

        if (link.download !== undefined) {
            //Set HTML5 download attribute. This will prevent file from opening if supported.
            var fileName = sUrl.substring(sUrl.lastIndexOf('/') + 1, sUrl.length);
            link.download = fileName;
        }

        //Dispatching click event.
        if (document.createEvent) {
            var e = document.createEvent('MouseEvents');
            e.initEvent('click', true, true);
            link.dispatchEvent(e);
            return true;
        }
    }

    // Force file download (whether supported by server).
    if (sUrl.indexOf('?') === -1) {
        sUrl += '?download';
    }

    window.open(sUrl, '_self');
    return true;
}

window.downloadFile.isChrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
window.downloadFile.isSafari = navigator.userAgent.toLowerCase().indexOf('safari') > -1;

//ENDS HERE



function exportData(){

    $.ajax({
    	url: "http://172.16.40.29:8080/com.mawell.mappingservice/Exporter/",//TODO mapper1.bfr.vgregion.se
        cache: true,
       type: "POST",
       contentType: "application/json; charset=UTF-8",
       success:
           function (data) {
	  			//NAVIGATE TO DATA
    	   		window.downloadFile(data);
    	   		//alert('länk: ' + data);
       		},
        error: function (jqXHR, textStatus, err) {
            $('#divError').addClass("error");
            $('#closeErrorBox').addClass("closeErrorShow");
            $('#divError').html('Error: ' + err);
            $('#divError').slideDown(100);
        }
    });
}


function getURLParameter(name) {
    var paramValue = decodeURI(
        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
    );
    
    if(paramValue == "null"){
    	paramValue = "";
    }
    
    console.log(paramValue);
    
    return paramValue;
}

function closeErrorMsg(referer){
	if(referer == "click"){
		$('#divError').slideUp(100);
	}
	
	$('#divError').removeClass("error");
	$("#divError").html("");
	$("#closeErrorBox").removeClass("closeErrorShow");
}
