<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%@ include file="../include/static-head.jsp" %>

</head>
<body>

<h1>게시글 등록</h1>

<form action="/board/write" method="post">
	<p>
		# 작성자: <input class="form-control" type="text" name="writer"><br>
		# 제목: <input type="text" name="title"><br>
		# 내용: <br>
		 <textarea rows="5" cols="30" name="content"></textarea>
		<br>
		<input class="btn btn-outline-danger" type="submit" value="등록">
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#abc">
			모달 테스트
		  </button>
	</p>
</form>

<a href="/board/list">글 목록보기</a>


<!-- Modal -->
<div class="modal fade" id="abc" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
	  <div class="modal-content">
		<div class="modal-header">
		  <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
		  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		</div>
		<div class="modal-body">
		  ...
		</div>
		<div class="modal-footer">
		  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		  <button type="button" class="btn btn-primary">Save changes</button>
		</div>
	  </div>
	</div>
  </div>

</body>
</html>





