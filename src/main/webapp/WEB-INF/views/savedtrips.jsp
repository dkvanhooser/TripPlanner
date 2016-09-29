<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="resources/css/savedTrips.css">

<link href="https://fonts.googleapis.com/css?family=Josefin+Sans" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Saved trips</title>

<style>
       body {
        height: 100%;
        margin: 0;
		color:white;
        
      }
      #map {
        height: 50%;
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
.privacy {
	margin-top:-270px;
	top:50%;
    left:50%;
	position:fixed;
    color: #3c763d;
    background-color: white;
    border-color: grey;
    display: none;
    text-align:center;
    z-index:99;
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
function setPrivacy(privacyP, tripid){
	event.preventDefault();
	$.ajax({
		type: "POST",
        url: "updatePrivacy",
        data : {privacy: privacyP,
        		tripID: tripid
        },
        success : function() {
        	  $( "div.privacy" ).fadeIn( 300 ).delay( 1500 ).fadeOut( 400 );
        	  location.reload(true);}
	});
}
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
};
</script>


<script src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${gKey}"/>&libraries=places&callback=initMap" async defer></script>

</head>
<body id ="thebody" background="http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/08/amazing-planet-hd-wallpapers.jpg">

<div class="alert-box success">Event Deleted!</div>
<div class="alert-box privacy">Privacy Updated!</div>
<h1 align = "center">Saved Trip</h1>


<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		
      </button>
      <a class="navbar-brand" href="home">Home</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    
<c:if test="${cookie.username.value == null}">
          <ul class="nav navbar-nav navbar-right">
        <li><a href="createaccount">Register</a></li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <li><a href="login">Login</a></li>
      </ul>
      </c:if>
      <c:if test="${cookie.username.value != null}">

      <ul class="nav navbar-nav navbar-right">
        <li><a href="userProfile">Profile</a></li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">Logout</a></li>
      </ul>
            <ul class="nav navbar-nav navbar-right">
        <li>	<p class="navbar-text">Logged in as: ${cookie.username.value}</p></li>
      </ul>
      </c:if>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.7";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
<c:if test="${savedtrip.privacy == 1}"><div class="fb-share-button" data-href="http://planit-env.us-west-2.elasticbeanstalk.com/savedtrips?tripID=${savedtrip}" data-layout="button" data-mobile-iframe="true"><a class="fb-xfbml-parse-ignore" target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fplanit-env.us-west-2.elasticbeanstalk.com%2Fsavedtrips%3FtripID%3D%2524%257Bsavedtrip%257D&amp;src=sdkpreparse">Share</a></div></c:if>
<c:if test="${savedtrip.privacy == 0}"><br/>To share this trip on Facebook, make this trip public!<button onclick = 'setPrivacy("1", ${savedtrip.tripID})'>Set this trip to Public</button></c:if>
<c:if test="${savedtrip.privacy == 1}"><button onclick = 'setPrivacy("0", ${savedtrip.tripID})'>Set this trip to Private</button></c:if>


<div class="row">
<div class="col-sm-12 ${event.genre}">
	<table class="savedTrips table table-striped" >
		<c:forEach var="place" items="${listevents.places}">

	
		<tr><td><c:out value = " ${place.name} " />	</td>
		<td><c:out value = " ${place.date} " />	</td>
		<td><c:out value = " ${place.address} " />	</td>
		<td><button onclick = "deleteEvent( '${place.placeID}', '${savedtrip.tripID}')">Delete Event</button></td>
		</tr>
		</c:forEach>
		
		<c:forEach var="event" items="${listevents.events}">
		
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><a href="<c:out value ="${event.url}" />">Click Here to View it on Ticketmaster!</a>	</td>
			<td><c:out value ="${event.dateTime}" /></td>

			<td><c:out value ="${event.info}" /></td>
			<td><button onclick = "deleteEvent( '${event.id}', '${savedtrip.tripID}')">Delete Event</button></td>

		</tr>
		</c:forEach>
<a href="home" align ="center"><input type="button" value="Search for more events!"/></a>

	</table>
</div><!-- end bs row -->
</div><!-- end bs container -->


<div id="map"></div>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 

</body>
</html>