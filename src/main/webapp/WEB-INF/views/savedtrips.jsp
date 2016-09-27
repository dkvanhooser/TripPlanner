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
        		tripID: tripid,
        		typeOfEvent: "place"
        },
        success: function() {
            window.location.reload(true);
            alert("event removed")
        }
       
	});
};</script>


<script src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${gKey}"/>&libraries=places&callback=initMap" async defer></script>

</head>
<body background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<h1 align = "center">Saved Trip</h1>
<table><tr><td></td><td>

<c:if test="${cookie.username.value != null}">
	logged in as: ${cookie.username.value} </br>
	<a href="<c:url value="userProfile" />" align ="right" >Profile</a></br>
	<a href="<c:url value="logout" />" align ="right" >Logout</a>
</c:if>
<c:if test="${cookie.username.value == null}">
<a href="<c:url value="login" />" align ="right" >Login</a><br/>
<a href="<c:url value="createaccount" />" align ="right" >Register</a></td>
</c:if>

</td></tr>
</form>	
<tr><td>

<table>


		<c:forEach var="place" items="${listevents.places}">
		<tr><td><c:out value = " ${place.name} " />	</td>
		<td><c:out value = " ${place.address} " />	</td>
		</tr>
		</c:forEach>
		
		<c:forEach var="event" items="${listevents.events}">
		
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><a href="<c:out value ="${event.url}" />">Click Here to View it on Ticketmaster!</a>	</td>
			<td><c:out value ="${event.dateTime}" /></td>
<<<<<<< HEAD
			<td><c:out value ="${event.info}" /></td></br>
			<td><button onclick = "deleteEvent( '${event.id}', '${tripsearch.tripID}')">Delete Event</button></td>
			
=======
			<td><c:out value ="${event.info}" /></td>
>>>>>>> 3ed106d4d313e8563dc3e09c1c62cf73a8c64b6c
			<input type ="hidden" name ="eventId" value = "${event.id}"></input>
			</td>
		</tr>
		</form>
		</c:forEach>


</table>
<form action="userProfile" method="GET">
    <input type="submit" value="Return to profile page!" 
         name="Submit"/>         
</form>

</table>

<div id="map"></div>
</body>
<footer>
</footer>
</html>