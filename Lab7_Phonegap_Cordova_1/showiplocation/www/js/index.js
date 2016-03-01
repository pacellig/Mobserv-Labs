var ipLoactionBaseURI = new String("http://api.ipinfodb.com/v3/ip-city/?");
var key = new String(
		"key=24dcedc0edacb640057e309a5ba987cf9ae521492bdef0df1e6ef48cb666da91");

/**
 * Bind event "deviceready".
 */
function init() {
    console.log("init called");
    document.addEventListener("deviceready", bindEvents, false);
}

/**
 * Once DOM document is loaded, add some logic.
 */
var bindEvents = function() {
    console.log("DomContentLoaded OK");
    document.getElementById("ipLocationRaw").addEventListener("click",
							      function() {
								  makeRestRequest("raw")
								      }, false);
    /*
      add the func call for xml and json 
    */
	document.getElementById("ipLocationXML").addEventListener("click",
							      function() {
								  makeRestRequest("xml")
								      }, false);
	document.getElementById("ipLocationJSon").addEventListener("click",
							      function() {
								  makeRestRequest("json")
								      }, false);
    document.getElementById("soapDictService").addEventListener("click",
								function() {
								    soapDictService()
									}, false);
}
    
/**
 * Check the connectivity, show an alert if no wifi/3G network available.
 * 
 * @returns true if ok, false otherwise.
 */
    function checkNetwork() {
	console.log("Check the connection");
	
	if (navigator.network.connection.type === Connection.NONE) {
	    console.log("No Internet connection");
	    alert("Please check your connection");
	    return false;
	} else {
	    return true;
	}
    }

/**
 * Send the request to the ip location webservice, initialize the callback.
 * 
 * @param format: response type accepted.
 */
function makeRestRequest(format) {
    if (checkNetwork() != true) {
	console.log("No connection. No Request sent");
	return;
    }
    
    var parameter = format === "xml" ? "&format=xml"
	: format === "json" ? "&format=json" : "";
    
    var ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function() {
	responseCallBack(ajax, format);
    }
    
    var uri = ipLoactionBaseURI.concat(key, parameter);
    ajax.open("GET", uri);
    ajax.send();
    
    console.log("Sent Request " + uri);
}

/**
 * XMLHTTPRequest callback, if sate = 4 and response 200 call function injecting
 * html code to the element id = main.
 * 
 * @parm ajax: the XMLHttpRequest.
 * @param format: format of the response.
 */
function responseCallBack(ajax, format) {
    console.log("Callback called");
    if (ajax.readyState == 4 && (ajax.status == 200)) {
	console.log("Response for " +format+" --> " + ajax.responseText);
	/*
	  add switch case for 
	  formatText(ajax.responseText);
	  formatXML(ajax.responseXML);
	  formatJSON(JSON.parse(new String(ajax.responseText)));
		 */
	switch(format){
		case "raw":
			formatText(ajax.responseText)
			break;
		case "xml":
			formatXML(ajax.responseXML);
			break;
		case "json":
			formatJSON(JSON.parse(new String(ajax.responseText)));
			break;
		default:
			break;	
	}
    }
}

/**
 * Inject HTML code from a text response.
 * 
 * @param text
 *            response.
 */
function formatText(text) {
    var sliced = text.split(";");
    var theHtml = new String("<h4> raw response</h4>");
    theHtml = theHtml.concat("<h2> IP: ", sliced[2], "</h2>");
    theHtml = theHtml.concat("<h2> Country: ", sliced[4], "(", sliced[3], ")",
			     "</h2>");
    theHtml = theHtml.concat("<h2>", sliced[6], " - ", sliced[5], "</h2>");
    theHtml = theHtml.concat("<h2>", sliced[8], " ", sliced[9], "</h2>");
    theHtml = theHtml.concat("<h2> GMT ", sliced[10], "</h2>");
    
    document.getElementById("main").innerHTML = theHtml;
}

/**
 * Inject HTML code from a XML response.
 * 
 * @param DOM
 *            response.
 */
function formatXML(response) {
    var nodes = response.documentElement.childNodes;
     var theHtml = new String("<h4> xml response </h4>")
	 
    // format the xml into html 
    // use a for loop from 0 to nodes.length 
    // use method x.innerHTML to add theHtml to the main
    for(var i = 0; i < nodes.length; i++){
    	if ( nodes.item(i) && nodes.item(i).nodeType == Node.ELEMENT_NODE){
    		theHtml = theHtml.concat("<h4>", nodes.item(i).tagName, ": ", nodes.item(i).innerHTML, "</h4>");
    	}
    }
	document.getElementById("main").innerHTML = theHtml;
}



/**
 * Inject HTML code from a JSON object.
 * 
 * @param json object.
 */
function formatJSON(jsonT) {
    var keys = Object.keys(jsonT);
    // format the json into html
    // use a for loop from 0 to nodes.length key.length
	var theHtml = new String("<h4> json response </h4>");
	
	// jsonT[keys[i]]
	for(var i = 0; i < keys.length; i++){
        	var value = jsonT[keys[i]];
		theHtml = theHtml.concat("<h4>", keys[i], ": ",  value, "</h4>");
	}
    	// use method x.innerHTML to add theHtml to the main
	document.getElementById("main").innerHTML = theHtml;
}

/**
 * Sample of a SOAP webservice.
 * note that in soap, the envolpe is standard, while the soap body is proprietary    
 */
function soapDictService() {
	var wordToSearch = document.getElementById("wordSearch").value;

	console.log("soapDictService called worldToSearch " + wordToSearch);
	if (wordToSearch.length === 0) {
		var theHtml = new String("<h4> No definition found. </h4>");
		document.getElementById("main").innerHTML = theHtml;
		return;
	}

	var ajax = new XMLHttpRequest();
	var url = "http://services.aonaware.com/DictService/DictService.asmx";

	var soapBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " 
			+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " 
			+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "<soap:Body>"
			+ "<Define xmlns=\"http://services.aonaware.com/webservices/\">"
			+ "<word>" + wordToSearch + "</word>" + "</Define>"
			+ "</soap:Body>" + "</soap:Envelope>";

	ajax.timeout = 6000;

	ajax.onreadystatechange = function() {
		if (ajax.readyState == 4) {
			if (ajax.status == 200) {
				var documentResponse = ajax.responseXML;
				var definitions = documentResponse
						.getElementsByTagName("Definition");
				var theHtml = definitions.length === 0 ? "<h4>No definitions found</h4>"
						: soapformatDefinitions(definitions);
				document.getElementById("main").innerHTML = theHtml;
			} else {
				console
						.log("Dict service received response code"
								+ ajax.status)
				alert("Unexpected error connecting service");
			}
		}
	}

	ajax.open("POST", url);
	ajax.setRequestHeader("SOAPAction",
			"\"http://services.aonaware.com/webservices/Define\"");
	ajax.setRequestHeader("Content-Type", "text/xml");
	ajax.send(soapBody);

};

/**
 * Format the soap response.
 * 
 * @param nodelist
 * @returns {String} html code.  
 */
function soapformatDefinitions(nodelist) {
	/*console.log("definitions length " + nodelist.length);
	// here, you receive a table that includes all the answers.
	// take the first answer
	// format the answer in theHtml var	
*/
	 var theHtml = new String("<h4>Definitions</h4>");    
    
    for (i=0; i<nodelist.length; i++) {
        theHtml = theHtml.concat("<p>",new String(nodelist[i].childNodes[2].textContent).trim(),"</p>");
    }
    
    document.getElementById("main").innerHTML = theHtml;

	return theHtml;       
};
