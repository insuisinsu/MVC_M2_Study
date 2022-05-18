<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    <!--  라이브러리에 JSTL 등록되어 있어야함 -->
    <!--  JSTL 선언부 -->
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

	browser 변수 설정
	<c:set var="browser" value = "${header['User-Agent'] }"/>
	<br>
	<c:out value = "${browser}"/>
	
	<p> browser 변수 값 제거 후
	<c:remove var = "browser" />
	<c:out value = "${browser }"/>
	
	

</body>
</html>