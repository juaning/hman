<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="scripts/css/ui-lightness/jquery-ui-1.10.4.custom.min.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="scripts/css/bootstrap.min.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="scripts/css/bootstrap-theme.min.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="scripts/css/slider.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="scripts/css/main.css" media="screen" />
		<title>Hotel Manager System</title>
	</head>
	<body>
		<div class="container-fluid">
			<h1>Welcome to the hotel booking system</h1>
			<div class="hotel-intro">
				<p>
					Please, select a Check-in and Check-out date, the city, the number of rooms and the maximum price per night you are willing to pay.
				</p>
			</div>
			<div id="displayAlerts" class="bg-danger"><ul>
				<c:if test="${!empty errMsg}">
					${errMsg}
				</c:if>
				</ul></div>
			<form role="form" action="consumer-controller" id="consumerSearchForm">
				<div class="form-group">
			    	<label for="checkin">Check-in date</label>
			    	<input type="date" class="form-control" id="checkin" name="checkin" placeholder="dd/mm/yyyy">
			  	</div>
			  	<div class="form-group">
			    	<label for="checkout">Check-out date</label>
			    	<input type="date" class="form-control" id="checkout" name="checkout" placeholder="dd/mm/yyyy">
			  	</div>
			  	<div class="form-group">
			    	<label for="city">City</label>
			    	<select id="city" name="city" class="form-control custom">
			    		<option value="0">Select City</option>
					  	<option value="1">Sydney</option>
					  	<option value="2">Brisbane</option>
					  	<option value="3">Melbourne</option>
					  	<option value="4">Adelaide</option>
					  	<option value="5">Hobart</option>
					</select>
			  	</div>
			  	<div class="form-group">
			    	<label for="rooms">Rooms</label>
			    	<select id="rooms" name="rooms" class="form-control custom">
			    		<option value="0">Number of rooms</option>
					  	<option value="1">1</option>
					  	<option value="2">2</option>
					  	<option value="3">3</option>
					  	<option value="4">4</option>
					  	<option value="5">5</option>
					</select>
			  	</div>
			  	<div class="form-group">
			  		<label for="maxPrice">Maximum price you are willing to pay per room per night:</label>
			    	<b> $50 </b><input id="maxPrice" name="maxPrice" class="form-control" data-slider-id='maxPrice' type="text" data-slider-min="50" data-slider-max="420" data-slider-step="5" data-slider-value="70"/><b> $420</b>
			    	<input id="selMaxPrice" name="selMaxPrice" type="hidden" value="70" />
			  	</div>
			  	<button id="consumerSearchSubmit" type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
		<!-- JS files -->
		<script type="text/javascript" src="scripts/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="scripts/js/jquery-ui-1.10.4.custom.min.js"></script>
		<script type="text/javascript" src="scripts/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="scripts/js/bootstrap-slider.js"></script>
		<script type="text/javascript" src="scripts/js/main.js"></script>
	</body>
</html>