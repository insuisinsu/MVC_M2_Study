<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�� ���� ����</title>
</head>
<body>

<h2> ���� ÷���� �Խ��� - �󼼺��� (View)</h2>
<table border ="1" width = "90%">
	<colgroup>
		<col width = "15%" /> <col width = "35%" />
		<col width = "15%" /> <col width = "*" />
	</colgroup>
<!-- �Խñ� ���� ��� -->
	<tr>
		<td>��ȣ</td>		<td>${dto.idx }</td>
		<td>�ۼ���</td>		<td>${dto.name }</td>
	</tr>
	<tr>
		<td>�ۼ���</td>		<td>${dto.postdate }</td>
		<td>��ȸ��</td>		<td>${dto.visitcount }</td>
	</tr>
	<tr>
		<td>����</td>	
		<td colspan ="3" height ="100">${dto.content }</td>
	</tr>
<!-- ÷������ -->
	<tr>
		<td>÷������</td>
		<td>
			<c:if test = "${not empty dto.ofile }">
			${dto.ofile }
			<a href = "../mvcboard/download.do?ofile=${dto.ofile }&sfile=${dto.sfile}&idx=${dto.idx}">
				[�ٿ�ε�]
			</a>
			</c:if>
		</td>
		<td>�ٿ�ε� ��</td>
		<td>${dto.downcount }</td>
	</tr>
<!-- �ϴ� �޴� ��ư -->
	<tr>
		<td colspan ="4" align ="center">
			<button type ="button" onclick ="location.href= '../mvcboard/pass.do?mode=edit&idx=${param.idx}';">�����ϱ�</button>
			<button type ="button" onclick ="location.href= '../mvcboard/pass.do?mode=delete&idx=${param.idx}';">�����ϱ�</button>
			<button type ="button" onclick ="location.href= '../mvcboard/list.do';">���</button>
		</td>
	</tr>
	
	
	
</table>



</body>
</html>