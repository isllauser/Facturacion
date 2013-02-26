<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function formSubmit() {
		document.getElementById("lg").submit();
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema de Facturacion Electronica</title>
<link rel="stylesheet" type="text/css" href="css/styleshome.css" />

</head>
<body>
<% response.sendRedirect("http://localhost:8080/FactElect/admin/index.html"); %>

</body> 

</html>