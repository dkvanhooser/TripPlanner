<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 50%;
        width:50%;
      }
    </style>
    <script>

      var map;
      var infowindow;

      function initMap() {
        var pyrmont = {<c:out value="${events.latAndLng}"/>};
        map = new google.maps.Map(document.getElementById('map'), {
          center: pyrmont,
          zoom: 15
        });

        infowindow = new google.maps.InfoWindow();
        var service = new google.maps.places.PlacesService(map);
        service.nearbySearch({
          location: pyrmont,
          radius: 5000,
          types: ['art_gallery','zoo','museum','library','aquarium']
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

        google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(place.name);
          infowindow.open(map, this);
        });
      }
    </script>
<meta charset="utf-8">
	<title>PlanIT</title>
</head>
<body = background="http://picview.info/download/20150530/soft-light-color-line-shape-2880x1800.jpg">
<table border="0" width="100%">
<tr>
<td><h1 align = "center">
	Search
</h1></td>
<c:if test="${cookie.username.value != null}">
	<td>logged in as: ${cookie.username.value} </br>
	<a href="<c:url value="userProfile" />" align ="right" >Profile</a></br>
	<a href="<c:url value="logout" />" align ="right" >Logout</a></td>
</c:if>
<c:if test="${cookie.username.value == null}">
<td><a href="<c:url value="login" />" align ="right" >Login</a><br/>
<a href="<c:url value="createaccount" />" align ="right" >Register</a></td>

</form>
</c:if>
</tr>
</table>
<table>
<tr><td></td><td align = "right">Trip</td><td>date</td></tr>
<tr><td>Search</td><td align = "center">Start</td><td align = "center">End</td></tr>
<tr>
<form action = "<c:url value="search" />">
<td><input type="text" path="search" name = "search" size="30"/></td>
<td><input path="dateFrom" type="date" name = "dateFrom" size="30"/></td>
<td><input path="dateTo" type="date" name ="dateTo" size="30"/></td>
<td><input type = "submit" value = "Search"></td>
</form>
</tr>
</table>
<table>
		<c:forEach var="event" items="${events.eventList}">
		<form action = "<c:url value="addEvent" />" method = "get">
		<tr>
			<td><c:out value ="${event.name}" /></td>
			<td><a href="<c:out value ="${event.url}" />">Click Here to View it on Ticketmaster!</a></td>
			<td><c:out value ="${event.dateTime}" />	</td>
			<td><c:out value ="${event.info}" /></td>
			<td><select name="trip">
			<c:forEach var="trip" items="${events.trips}">
			<option value="${trip.tripID}">${trip.tripName}</option>
			</c:forEach>
			</select>
			<input type ="hidden" name ="eventID" value = "${event.id}">
			</td>
			<td><button type="submit">Add to Trip</button></td>
		</tr>
		</form>
		</c:forEach>

</table>


    <div id="map"></div>
    <script src="https://maps.googleapis.com/maps/api/js?key=<c:out value="${events.gKey}"/>&libraries=places&callback=initMap" async defer></script>

</body>
</html>