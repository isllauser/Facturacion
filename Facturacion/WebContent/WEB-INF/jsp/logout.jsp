<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../css/v2_styles.css" type="text/css" rel="stylesheet" />

</head>
<body>
<% response.sendRedirect("http://localhost:8080/FactEl/j_spring_security_logout"); %>
<% session.invalidate(); %>
<% 
response.setIntHeader("Refresh", 5);

%>
<body> 

</body>
</body>
</html>