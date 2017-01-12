<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="script/member.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 관리</title>
</head>
<body>
<%--아이디 중복 체크를 위한 JSP 페이지 --%>

	<h2>아이디 중복 확인</h2>
	<form action="idCheck.do" method="get" name="frm">
		아이디 <input type=text name="userid">
			<input type=submit value="중복체크">
		<br>
			<c:if test="${result == 1}">
				<script type="text/javascript">
					opener.document.frm.userid.value="";
				</script>
				${userid}는 이미 사용 중인 아이디 입니다.
			</c:if>
			<c:if test="${result == -1}">
				${userid}는 사용 가능한 아이디 입니다. 
				<input type="button" value="사용" class="cancle" onclick="idok()">
			</c:if>
	</form>
</body>
</html>