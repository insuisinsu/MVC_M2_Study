<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
	<script type="text/javascript">
		function validateForm (form){
			if (form.name.value=="" || form.name.value.length==0){
				alert ("작성자를 입력하세요");
				form.name.focus();
				return false;
			}
			if(form.title.value == "" || form.title.value.length==){
				alert("제목을 입력하새요");
				form.title.focus();
				return false;
			}
			if(form.content.value == "" || form.content.value.length==){
				alert("내용 입력하새요");
				form.content.focus();
				return false;
			}
			if(form.pass.value == "" || form.pass.value.length==){
				alert("제목을 입력하새요");
				form.pass.focus();
				return false;
			}
		}
	</script>
</head>
<body>
<h2>파일 첨부형 게시판 - 글쓰기 (Write)</h2>

	<!-- Form 태그 내에서 input type = "file" 이 존재하면 반드시
		method = "post"			<< <== 업로드 용량 제한이 없다. >>
		enctype = "multipart/form-date"		<== 라이브러리를 통해서 업로드 지원
		
		<주의> : 서버에서 request.getParameter ("name") : request 객체를 사용하면 안됨.
			  : 라이브러리에서 지원해주는 객체의 메소드로 Form의 변수값을 받아야 한다.
			  		참고 : 라이브러리 마다 메소드 이름이 다를 수 있다.
		
	 -->
<form name = "writeFrm" method = "post" enctype = "multipart/form-data"
	action = "../mvcboard/write.do" onsubmit = "return validateForm(this)">
	<table border = "1" width = "90%">
		<tr>
			<td> 작성자 : </td>
			<td> <input type = "text" name = "name" style = "width:150px;"></td>
		</tr>
		<tr>
			<td> 제목 : </td>
			<td> <input type = "text" name = "title" style = "width:90%;"></td>
		</tr>
		<tr>
			<td> 내용 : </td>
			<td> <textarea name = "content" style = "width:90%; height : 100px"></textarea></td>
		</tr>
		<tr>
			<td> 첨부파일 : </td>
			<td> <input type="file" name = "ofile" /></td>
		</tr>
		<tr>
			<td> 비밀번호 : </td>
			<td> <input type="password" name = "pass" style = "width:100px;" /></td>
		</tr>
		<tr>
			<td colspan = "2" align = "center">
				<button type = "submit"> 작성 완료 </button>
				<button type = "reset"> RESET </button>
				<button type = "button" onclick = "location.href = '../mvcboard/list.do';"> 
					목록 바로가기
			    </button>
			</td>
		</tr>
	</table>
<form>

</body>
</html>