<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="resources/css/main.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body {
      background-attachment: fixed, scroll;
       background-image:url("http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/08/amazing-planet-hd-wallpapers.jpg");
        height: 100%;
        margin: 0;
        padding: 0;
        background-attachment: fixed;
      }
      #map {
        height: 50%;
        width:50%;
      }

.alert-box {
	
	padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;  
    z-index:98;
}	
.success {
	margin-top:-270px;
	top:50%;
    left:50%;
	position:fixed;
    color: #3c763d;
    background-color: #dff0d8;
    border-color: #d6e9c6;
    display: none;
    text-align:center;
    z-index:99;
}
.failure {
	margin-top:-270px;
	top:50%;
    left:50%;
	position:fixed;
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1;
    display: none;
    text-align:center;
    z-index:99;
}


.col-sm-3{
	padding: 15px;
	border: 1px solid;
	margin-left: 10px;
	margin-right: 15px;
	margin-top: 5px;
	margin-bottom: 5px;
	width: 25%;
	display: table-cell;
	background:navy;
	opacity: .8;

}
.col-sm-3:hover {
    opacity: 1.0;
    box-shadow:7px 7px 5px #888888;
}

.genreCheck{
	padding: 5px;
	border: 1px double;
	border-color: royalblue;
	background:navy;
}
.col-sm-3 .tables{
	opacity: 1.0;
}
#button{
	color:black;
}

    </style>
    
    <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
function hideStuff(genre){
	
	if(document.getElementById(genre).checked) {
		$('div.'+genre).toggle( 400 );
	} else {
		$('div.'+genre).toggle( 400 );
	}
	
}


var tripid;
var dateP;
function setDate(){
	dateP =document.getElementById("date").value;
}
function resetDate(){
	dateP = null;
}
function setTrip(){
	tripid = $('select[name=tripID]').val();
}
function SubForm(eventid, dateE) {
	if(tripid == null){
		$( "div.failure" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );
		return;
	}
	event.preventDefault();
	$.ajax({
		type: "POST",
        url: "addEvent",
        data : {
        	eventID: eventid,
    		tripID: tripid,
    		typeOfEvent: "event",
    		date: dateE
        },

        success : function() {
        	  $( "div.success" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );
        }
	});
};
function PlacesSubForm(eventid) {
	if(tripid == null){
		$( "div.failure" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );
		return;
	}
	event.preventDefault();
	$.ajax({
		type: "POST",
        url: "addEvent",
        data : {eventID: eventid,
        		tripID: tripid,
        		typeOfEvent: "place",
        		date: dateP
        },
        success : function() {
        	  $( "div.success" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );}
	});
};


</script>

    <script>

      var map;
      var infowindow;

      function initMap() {
        var city = {<c:out value="${events.latAndLng}"/>};
        map = new google.maps.Map(document.getElementById('map'), {
          center: city,
          zoom: 15
        });

        infowindow = new google.maps.InfoWindow();
        var service = new google.maps.places.PlacesService(map);
        service.nearbySearch({
          location: city,
          radius: 5000,
          types: ['art_gallery','zoo','museum','aquarium']
        }, callback);
      }

      function callback(results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
          for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
          }
        }
      }
      function createMarker(place) {
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
          map: map,
          position: place.geometry.location
        });
		
        var infoContent = place.name
		+ "<br/>When will you want to visit?<input type=\"date\" id =\"date\" size=\"30\" onchange ='setDate()'/>"+"<br/><button onclick = 'PlacesSubForm(\""
		+place.place_id + "\")' >Add to Trip</button>" ;
        
        google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(infoContent);
          
          infowindow.open(map, this);
        });
      }
    </script>
    
    
<meta charset="utf-8">
	<title>PlanIT</title>
</head>
<body>
<div class="container-fluid">
<div class="row">


<div class="alert-box success">Event Added!</div>
<div class="alert-box failure">Please select a trip to add to!</div>


<table border="0" width="100%">
<tr>
<td><h1 align = "center">
	Search
</h1></td>
<c:if test="${cookie.username.value != null}">
	<td>logged in as: ${cookie.username.value} </br>
	<a href="userProfile" align ="right"><input type="button" value="Profile"/></a></br>
	<a href="logout" align ="right"><input type="button" value="Logout"/></a></td>
</c:if>
<c:if test="${cookie.username.value == null}">
<td><a href="login" align ="right"><input type="button" value="Login"/></a><br/>
<a href="createaccount" align ="right"><input type="button" value="Register"/></a></td>
</form>
</c:if>
</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form action = "<c:url value="search" />">
<td><input type="text" path="search" name = "search" size="30" placeholder="Search by City"/></td>
<td><input path="dateFrom" type="date" name = "dateFrom" size="30"/></td>
<td><input path="dateTo" type="date" name ="dateTo" size="30"/></td>
<td><input type = "submit" value = "Search"></td>
</form>
</tr>
</table>

<div class="dropdown">
<select name="tripID" onchange = "setTrip();">
<option>Please select a trip to add to</option>
<c:forEach var="trip" items="${events.trips}">
			<option id = "${trip.tripID}" value="${trip.tripID}">${trip.tripName}</option>
			</c:forEach>
			</select>
		</div>
<table class="checkboxes">
	<tr>

		
		<c:forEach var="genre" items="${events.genres}">
		<td class = "genreCheck">${genre}<br/><input type="checkbox" id="${genre}" onchange ='hideStuff("${genre}")'checked/></td>
		</c:forEach></tr>
</table>


		<c:forEach var="event" items="${eventList}">

	<div class="col-sm-3 ${event.genre}">
		<div class ="tables">
		<table class="eventTable">
		<tr><th style = "word-break:break-word;"><c:out value ="${event.name}" /></td></tr>
			<tr><td style = "word-break:break-word;"><a href="<c:out value ="${event.url}" />">Click here for more details!</a></td></tr>
			<tr><td style = "word-break:break-word;"><c:out value ="${event.dateTime}" />	</td></tr>
			<tr><td style = "word-break:break-word;"><c:out value ="${event.info}" /></td></tr>
			<tr><td style = "word-break:break-word;"><button id = "sub" onclick = '<c:out value ="SubForm('${event.id}', '${event.dateTime}');" />' >Add to Trip</button></td></tr>
			
		</table>
		</div>
		</div>

		</c:forEach>
	
	</div>

	</div>
    <div id="map"></div>
    
    
    
            <script src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${events.gKey}"/>&libraries=places&callback=initMap" async defer></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 
</body>
</html>