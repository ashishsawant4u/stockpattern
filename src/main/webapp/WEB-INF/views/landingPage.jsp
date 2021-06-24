<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>      
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<td><fmt:formatDate value="${prices.marketDate}" pattern="dd-MMM-yyyy"/></td>
		<td><b>O-</b>${prices.openPrice}</td>
		<td><b>H-</b>${prices.highPrice}</td>
		<td><b>L-</b>${prices.lowPrice}</td>
		<td><b>C-</b>${prices.closePrice}</td>
		<td style="color: red;">${prices.movingAverage}</td>
		<c:if test="${prices.hasSupport eq true}">
			<td style="background-color: green;color: white;">SUPPORT</td>
		</c:if> 
		<c:if test="${prices.entry eq true}">
			<td style="background-color: blue;color: white;">ENTRY ${prices.orderDetails}</td>
		</c:if>
		<c:if test="${not empty prices.tradeResult}">
			<td style="background-color: orange;color: white;">${prices.tradeResult}</td>
		</c:if>
		<%-- <c:if test="${prices.movingAverage < prices.lowPrice && (prices.lowPrice-prices.movingAverage)<=2}">
			<td style="background-color: blue;">SUPPORT</td>
		</c:if> --%>
	</tr>
		
</c:forEach>
</table>



</body>
</html>