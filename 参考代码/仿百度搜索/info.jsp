<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table>
<c:forEach items="${list }" var="W"> 

<tr>
<td>${W.word }</td>
</tr>

</c:forEach>
</table>