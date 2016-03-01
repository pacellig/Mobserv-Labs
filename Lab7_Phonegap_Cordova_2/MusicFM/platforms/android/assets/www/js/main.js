
// The Audio player
var my_media = null;
var mediaTimer = null;
// duration of media (song)
var dur = -1;
// need to know when paused or not
var is_paused = false;
var src = '';
var directoryReader = null;
var recur = 0;
var sdgrid = null;
var parentDirectoryPath = null;
var directoryPath = null;
var rootPath = null;
var filesystem = null;

//function init() {
 
//    document.addEventListener("deviceready", onDR, false);
//}
//Set audio position on page
function setAudioPosition(position) {
    $("#audio_position").html(position + " sec");
}

//On device ready
function onDR(){
    //console.log("Ready");
    window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, onRequestFileSystem, fail);
}

function fail(){
    console.log("file system failure");
}

function initialize() {
    document.addEventListener("deviceready", onDR, false);
}

//onSuccess Callback
function onSuccess() {
    setAudioPosition(dur);
    clearInterval(mediaTimer);
    mediaTimer = null;
    my_media = null;
    is_paused = false;
    dur = -1;
}

//onError Callback 
function onError(error) {
    alert('code: '    + error.code    + '\n' + 
            'message: ' + error.message + '\n');
    clearInterval(mediaTimer);
    mediaTimer = null;
    my_media = null;
    is_paused = false;
    setAudioPosition("0");
}

function playAudio(src) {
    console.log("Play Audio " + src);
   
    if (my_media) {
    my_media.stop();
    } 
   
    // Create Media object from src, and set the onSuccess and onError methods
    my_media = new Media(src, onSuccess, onError);
     // Play audio src
    my_media.play();
    // Update my_media position every second
    // Update media position every second
    var mediaTimer = setInterval(function () {
        // get media position
        if (my_media != null ) {
            my_media.getCurrentPosition(
                // success callback
                function (position) {
                    if (position > -1) {
                        console.log((position) + " sec");
                        document.getElementById('audio_position').innerHTML = (position) + " sec";
                    }
                },
                // error callback
                function (e) {
                    console.log("Error getting pos=" + e);
                }
            );
        }
    }, 1000);
    // get duration
    var counter = 0;
    var timerDur = setInterval(function() {
        counter = counter + 100;
        if (counter > 2000) {
            clearInterval(timerDur);
        }
        var dur = my_media.getDuration();
        if (dur > 0) {
            clearInterval(timerDur);
            document.getElementById('media_dur').innerHTML = (dur) + " sec";
        }
    }, 100);
   // if (dur > 0) , update the interface using 
   // docuement.getElementById('media_dur').innerHTML=(dur) + "sec";    
    // OR 
   
}

//Pause audio
function pauseAudio() {
    console.log('pressed pause audio');
     if (is_paused) {
        if (my_media) {
            is_paused = false;
            my_media.play();
            $("#pauseaudio").text("Pause");
        }
    } else {
        if (my_media) {
            is_paused = true;
            my_media.pause();
            $("#pauseaudio").text("Play");
        }
    }
}

//Stop audio
function stopAudio() {
    if (my_media) {
        // A successful .stop() will call .release()
        my_media.stop();
        my_media = null;
    }
    if (mediaTimer) {
        clearInterval(mediaTimer);
        mediaTimer = null;
    }
    document.getElementById('media_dur').innerHTML = "0 sec";
    is_paused = false;
    dur = 0;
}

/* Concert  */
 
function searchConcerts(nameArtist, nameCity) {
//	var url = http://api.setlist.fm/rest/0.1/search/setlist.json?artistName="nameArtist"&cityName="nameCity";
	var serviceURL = "http://api.setlist.fm/rest/0.1/search/setlists.json?"; 
	var parameter = new String();
	parameter = parameter.concat("artistName=", nameArtist, "&cityName=", nameCity);
	var url = serviceURL + "" + parameter;
	console.log(url);
   
     $.ajax({
        url : serviceURL + "" + parameter,
        type : "GET",
	 dataType : "json",
        success : parseConcert,
        error : showError
        });
}
function parseConcert(dataJson){
    console.log("response " + dataJson);
    
    var setlists = dataJson.setlists.setlist;
    
    var theHtml = new String("<h4>concerts</h4>");    
    
    for (i=0; i<setlists.length; i++){
        var date = (setlists[i])["@eventDate"];
        var tour = (setlists[i])["@tour"];
        var venue = (setlists[i].venue)["@name"];
        var city = (setlists[i].venue.city)["@name"];
        var venueUrl = setlists[i].venue.url;
        var url = setlists[i].url;
        
        theHtml = theHtml.concat("<p style=\"border-bottom-width:1px; border-bottom-style:solid; padding-bottom-4px;\"><b>Tour:</b> ",tour,
                                "<br><b>Date:</b> ", date,
                                "<br><b>City:</b> ", city,
                                 "<br><b><a href=\"",venueUrl,"\">",venue,"</a></b>",
                                 "<br><b>link</b>: <a href=\"",url,"\">",url,"</a></p>");
    }
    
    $("#resultConcert").html(theHtml);
    
}
/* Tour */ 


function searchTour(nameArtist,yearTour,nameCity){
    $("#resultTours").html("");
    var serviceURL = "http://api.setlist.fm/rest/0.1/search/setlists.json?"; 
    var parameter = new String();
    // SEE searchConcert
    /*
            artistName=$('#artistNameTour').val();   
        year=$('#yearTour').val();
        cityName=$('#cityNameTour').val();
        searchTour(artistName, year, cityName);
        */
    parameter = parameter.concat("artistName=", nameArtist, "&city=", nameCity, "&year=", yearTour);
    var url = serviceURL + "" + parameter;
    console.log(url);

    $.ajax({
        url : serviceURL + "" + parameter,
        type : "GET",
        //        dataType : "json",
        success : parseTour,
        error : showError
        });

}

/*
function searchTour(nameArtist,nameTour){
    $("#resultTours").html("");
    var serviceURL = "http://api.setlist.fm/rest/0.1/search/setlists.json?"; 
    var parameter = new String();
    parameter = parameter.concat("artistName=", nameArtist, "&tour=", nameTour);
    var url = serviceURL + "" + parameter;
    console.log(url);

    $.ajax({
        url : serviceURL + "" + parameter,
        type : "GET",
        dataType : "json",
        success : parseTour,
        error : showError
        });
}
*/

function parseTour(dataJson){
    console.log("response " + dataJson);
    
    var setlists = dataJson.setlists.setlist;
    
    var theHtml = new String("<h4>concerts</h4>");    
    
    for (i=0; i<setlists.length; i++){
        var date = (setlists[i])["@eventDate"];
        var tour = (setlists[i])["@tour"];
        var venue = (setlists[i].venue)["@name"];
        var venueUrl = setlists[i].venue.url;
        var city = (setlists[i].venue.city)["@name"];
        var url = setlists[i].url;
        
        theHtml = theHtml.concat("<p style=\"border-bottom-width:1px; border-bottom-style:solid; padding-bottom-4px;\"><b>Tour:</b> ",tour,
                                "<br><b>Date:</b> ", date,
                                "<br><b>City:</b> ", city,
                                 "<br><b><a href=\"",venueUrl,"\">",venue,"</a></b>",
                                 "<br><b>link</b>: <a href=\"",url,"\">",url,"</a></p>");
    }
    
    $("#resultTours").html(theHtml);
    
}

/* sd */

function fileButton(file) {
    return $("<p>").attr({'data-role': 'button', 'uri': file.toURL()}).addClass('music-file').append($("<small>").html(file.name));
}
function directoryButton(file) {
    return $("<p>").attr({'data-role': 'button', 'data-icon': 'arrow-r', 'data-iconpos': 'right', 'uri': file.toURL().substring(7)}).addClass('directory').append($("<small>").html(file.name));
}
function parentButton(path) {
    return $("<p>").attr({'data-role': 'button', 'data-icon': 'back', 'uri': path}).addClass('directory').append($("<small>").html('..'));
}

function onRequestFileSystem(fileSystem) {
    directoryReader = fileSystem.root.createReader();
    filesystem = fileSystem;
    directoryPath = fileSystem.root.toURL().substring(7)
    rootPath = fileSystem.root.toURL().substring(7)
}

function onReadEntries(entries) {
    var i;
    $musicFiles = $sdgrid.find("#music-files");
    $directories = $sdgrid.find("#directories");
    if(parentDirectoryPath != null) {
        $sdgrid.find("#parent-directory").append(parentButton(parentDirectoryPath));
    }
    
    var ext = new RegExp('\\.(mp3|wav|m4a)$');
    for (i = 0; i < entries.length; i++) {
        if (entries[i].isFile == true) {
            if(ext.test(entries[i].name)) {
                $musicFiles.append(fileButton(entries[i]));
            }
        }
        else if (entries[i].isDirectory == true) {
            $directories.append(directoryButton(entries[i]));
        }
    }
    $sdgrid.find("#parent-directory").children().button();
    $musicFiles.children().sort(uriCompare).appendTo($musicFiles).button();
    $directories.children().sort(uriCompare).appendTo($directories).button();
}

function uriCompare(a,b) {
    aa=a.getAttribute('uri');
    ba=b.getAttribute('uri');
    return aa>ba ? 1 : (aa<ba ? -1 : 0);
}

function parseEntries(dirReader){
    $sdgrid.html("");
    $sdgrid.append($('<span>').attr('id', 'parent-directory'));
    $sdgrid.append($('<span>').attr('id', 'music-files'));
    $sdgrid.append($('<span>').attr('id', 'directories'));
    $("#directory-path").empty().append($("<h5>").html(directoryPath));
    if(dirReader){
        dirReader.readEntries(onReadEntries, fail);
    }
}

function gotoDir(dirEntry) {
    var patt = new RegExp("^(.*/)[^/]+/$");
    var res = patt.exec(path);
    parentDirectoryPath = res[1];
    console.log("parent directory path: "+res);
    directoryPath = path;
    console.log("directory path: "+directoryPath);
    console.log("");
    parseEntries(dirEntry.createReader())
}

/**
 * Callback ajax error
 * @param request
 * @param error
 * @param obj
 */
function showError(request, error, obj) {
        console.log("Error received" + error + " " + request);
        alert("Error contacting remote Server.");
}

$( function() {
		
	$('#playaudio').click(function() {
        // Note: two ways to access media file: web and local file        
        //src = 'http://audio.ibeat.org/content/p1rj1s/p1rj1s_-_rockGuitar.mp3';
        
        // local (on device): copy file to project's /assets folder:
        //var src = '/android_asset/spittinggames.m4a';
        
        playAudio(src);
    });

    // Start with Manual selected and Flip Mode hidden
    $('#nav-manual').focus();
    $('#content-list1').hide();
    $('#content-list2').hide();
    $('#content-list3').hide();
    $('#content-list4').hide();
   
 $('#nav-manual').live('tap', function() {
   	console.log("nav-manual tapped");
    $('#nav-manual').focus();
    $('#content-list1').hide();
  	$('#content-list2').hide();
	$('#content-list3').hide();
	$('#content-list4').hide();
 
    $('#content-manual').show();
    stopAudio();
    });
   
 $('#nav-list1').live('tap', function() {
    $('#nav-list1').focus();
    $('#content-manual').hide();	
	$('#content-list2').hide();
	$('#content-list3').hide();
	$('#content-list4').hide();

    $('#content-list1').show();
    });

 $('#nav-list2').live('tap', function() {
    $('#nav-list2').focus();
    $('#content-manual').hide();	
    $('#content-list1').hide();
	$('#content-list3').hide();
	$('#content-list4').hide();
    
    $('#content-list2').show();
    });
	
 $('#nav-list3').live('tap', function() {
    console.log("nav-list3 tapped");
    $('#nav-list3').focus();
    $('#content-manual').hide();	
    $('#content-list1').hide();
    $('#content-list2').hide();
	$('#content-list4').hide();
    /*SD card*/
    $('#content-list3').show();
    parseEntries(directoryReader);
    });
	
 $('#nav-list4').live('tap', function() {
    $('#nav-list4').focus();
    $('#content-manual').hide();	
	$('#content-list1').hide();
    $('#content-list2').hide();
    $('#content-list3').hide();
    
    $('#content-list4').show();
    });
		

    $("#pauseaudio").live('click', function() {
        pauseAudio();
    });
    
    $("#stopaudio").live('tap', function() {
        stopAudio();
    });
    
    $('#music1').click(function() {
    	src = 'http://www.universal-soundbank.com/mp3/sounds/10157.mp3';
    	playAudio(src);
    });
    
    $('#music2').click(function() {
    	src = 'http://www.universal-soundbank.com/mp3/sounds/10183.mp3';
    	playAudio(src);
    });
    //button hymne 
    $('#music3').click(function() {
    	src = 'http://s1download-universal-soundbank.com/mp3/sounds/7730.MP3';
    	playAudio(src);
    });
    // radio
    $('#music4').click(function() {
    	src = 'http://stream8.addictradio.net/addictrock.mp3?';
    	playAudio(src);
    });
    
    $("#searchButtonConcert").click(function() {
	    // get the vars : artistName and cityName
	    artistName=$('#artistNameConcert').val();	
	    nameCity=$('#cityName').val();
	    // call the function to make an ajax request
        searchConcerts(artistName, nameCity);
    });
    
    $("#searchButtonTour").click(function() {
        
        artistName=$('#artistNameTour').val();   
        year=$('#yearTour').val();
        cityName=$('#cityNameTour').val();
	    searchTour(artistName, year, cityName);
    });
    
    // implement a function to read a file from the sdcard
    $(document).on('click', '.music-file', function(){
        console.log($(this).attr("uri"));
        var uri = $(this).attr("uri");
        playAudio(uri);
    });

    $(document).on('click', '.directory', function(){
        path = $(this).attr("uri");
        path_togo = path.substring(rootPath.length)
        console.log("rp: "+rootPath+" going to "+ path_togo);
        filesystem.root.getDirectory(
            path_togo,  
            {create: false}, 
            gotoDir, 
            function (error) {
                console.log("Unable to go to directory - error code: " + error.code);
            }
        )
    });

    $sdgrid=$("#sd-grid");
    
});


