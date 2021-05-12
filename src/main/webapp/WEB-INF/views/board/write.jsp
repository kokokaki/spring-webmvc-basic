<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>게시글 등록</h1>

<form action="/board/write" method="post">
	<p>
		# 작성자: <input type="text" name="writer"><br>
		# 제목: <input type="text" name="title"><br>
		# 내용: <br>
		 <textarea rows="5" cols="30" name="content"></textarea>
		<br>
		<input type="submit" value="등록">
	</p>
</form>

<a href="/board/list">글 목록보기</a>
</body>
</html>





