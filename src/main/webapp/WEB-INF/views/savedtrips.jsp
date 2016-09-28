<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Saved trips</title>

<style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 50%;
      }
      .alert-box {
	
	padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;  
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
}
      
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    
    	var infowindow;
    	var map;
    	var places;
	    
	    
	    // Initialize the map.
	 function initMap() {
	    $( function() {
	   	  places = ${jsonPlaces};
	      map = new google.maps.Map(document.getElementById('map'), {
	        zoom: 14,
	        center: {lat: places[0].lat, lng: places[0].lng}
	      });
	      infowindow = new google.maps.InfoWindow;
	      
	      google.maps.event.addListener(map, 'click', function() {	 
   		 	infowindow.close();
 		  });
	      
	      for (var i = 0; i < places.length; i++) {
	    	  
	    	  var gMarker = new google.maps.Marker({
	    	    map: map,
	    	    position: new google.maps.LatLng(places[i].lat, places[i].lng),
	    	    title:places[i].name,
	    	    animation: google.maps.Animation.DROP
	    	  });
	    	  
	    	  
	    	  google.maps.event.addListener(gMarker, 'click', function() {
	    		marker = this;
	    		map.panTo(marker.position);
				infowindow.setContent(marker.title);
				infowindow.open(map, marker);
				marker.setAnimation(google.maps.Animation.BOUNCE);
				setTimeout(function(){
					marker.setAnimation(null);
				}, 1500);
	    	  });
	      } 
	    });
	  }
   
  </script>
  <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script>
function deleteEvent(eventid, tripid) {
	event.preventDefault();
	$.ajax({
		type: "POST",
        url: "deleteEvent",
        data : {eventID: eventid,
        		tripID: tripid
        },
        success:
        	function() {
	        	  $( "div.success" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );
			location.reload(true);}
       
	});
};</script>


<script src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${gKey}"/>&libraries=places&callback=initMap" async defer></script>

</head>
<body background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<div class="alert-box success">Event Deleted!</div>
<h1 align = "center">Saved Trip</h1>
<table><tr><td></td><td>

<c:if test="${cookie.username.value != null}">
	logged in as: ${cookie.username.value} </br>
	<a href="userProfile" align ="right"><input type="button" value="Profile"/></a></br>
	<a href="logout" align ="right"><input type="button" value="Logout"/>
</c:if>
<c:if test="${cookie.username.value == null}">
<a href="login" align ="right"><input type="button" value="Login"/></a><br/>
<a href="createaccount" align ="right"><input type="button" value="Register"/></td>
</c:if>

</td></tr>
</form>	
<tr><td>

<table>


		<c:forEach var="place" items="${listevents.places}">
		<tr><td><c:out value = " ${place.name} " />	</td>
		<td><c:out value = " ${place.date} " />	</td>
		<td><c:out value = " ${place.address} " />	</td>
		<td><button onclick = "deleteEvent( '${place.placeID}', '${savedtrip}')">Delete Event</button></td>
		</tr>
		</c:forEach>
		
		<c:forEach var="event" items="${listevents.events}">
		
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><a href="<c:out value ="${event.url}" />">Click Here to View it on Ticketmaster!</a>	</td>
			<td><c:out value ="${event.dateTime}" /></td>

			<td><c:out value ="${event.info}" /></td></br>
			<td><button onclick = "deleteEvent( '${event.id}', '${savedtrip}')">Delete Event</button></td>
			


			</td>
		</tr>
		</form>
		</c:forEach>


</table>

</table>

<div id="map"></div>
</body>
<footer>
</footer>
</html>