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
			<h1>Results List for ${city}</h1>
			<div class="hotel-intro">
				<p>
					<%-- Rooms for ${city} --%>
				</p>
			</div>
			<form role="form" action="consumer-controller" id="consumerSearchForm">
				<table class="table table-hover">
					<thead>
						<tr>
							
						</tr>
					</thead>
				</table>
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