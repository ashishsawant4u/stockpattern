<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

STOCK PATTERN

<table>
<c:forEach var="prices" items="${stockPriceList}">
	
	<tr>
		<td>${prices.marketDate}</td>
		<td>${prices.openPrice}</td>
		<td>${prices.highPrice}</td>
		<td>${prices.lowPrice}</td>
		<td>${prices.closePrice}</td>
	</tr>
		
</c:forEach>
</table>



</body>
</html>