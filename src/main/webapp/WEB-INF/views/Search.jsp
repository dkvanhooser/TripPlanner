<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
</head>
<body>
<script>
$.ajax({
	  type:"GET",
	  url:"https://app.ticketmaster.com/discovery/v2/events.json",
	  async:true,
	  dataType: "json",
	 // data: {"radius":"1", "startDateTime":"2016-09-23T00%3A00%3A00Z", "endDateTime":"2016-09-26T00%3A00%3A00Z","city":"Detroit", "apikey":"9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3"},
	 data:{"size":"10","apikey":"9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3","dmaId":"266"},
	  success: function(json) {
	              console.log(json);
	              // Parse the response.
	              // Do other things.
	           },
	  error: function(xhr, status, err) {
		  		   console.log(err);
	              // This time, we do not end up here!
	           }
	});</script>
</body>
</html>