<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("EUC-KR");

	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	String name = request.getParameter("name");
	%>
	
		<!-- JSTL �� DataBase Connection ���� -->
	<sql:setDataSource var = "dataSource"
					url = "jdbc:oracle:thin:@localhost:1521:XE"
					driver = "oracle.jdbc.driver.OracleDriver"
					user = "HR2" password = "1234" />
					
	<sql:update dataSource = "${dataSource }" var = "resultSet" >
		insert into member(id, pass, name) values(?,?,?)
		<sql:param value = "<%= id %>" />
		<sql:param value = "<%= passwd %>" />		
		<sql:param value = "<%= name %>" />
	</sql:update>
	
	<c:import var = "url" url ="sql01.jsp" />
	
	${url }
	
	
	
</body>
</html>