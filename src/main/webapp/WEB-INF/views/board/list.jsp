<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		.pagination {
			width: 60%;
			margin-top: 10px;
			list-style: none;
			display: flex;
		}

		.pagination>li {
			justify-content: flex-end;
			margin-right: 5px;
		}

		.pagination>li>a {
			text-decoration: none;
			color: black;
		}

		.pagination>li>a:hover {
			color: yellowgreen;
		}

		.pagination>li.active>a {
			font-weight: bold;
			color: orangered;
			font-size: 1.1em;
		}

		.amount {
			width: 30%;
			display: flex;
			justify-content: flex-end;
			margin-bottom: 10px;
		}

		.amount a {
			display: block;
			color: #fff;
			background: #f00;
			width: 50px;
			height: 20px;
			border-radius: 5px;
			margin-right: 5px;
			text-align: center;
			font-weight: 700;
			text-decoration: none;
		}
	</style>
	<link rel="stylesheet" href="/css/main.css">

	<%@ include file="../include/static-head.jsp" %>

</head>

<body>

	<%@ include file="../include/header.jsp" %>

	<c:if test="${articles.size() <= 0}">
		<p>게시물이 존재하지 않습니다.</p>
	</c:if>

	<c:if test="${articles.size() > 0}">

		<h1>게시글 목록</h1>

		<div class="amount">
			<a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 10)}">10</a>
			<a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 20)}">20</a>
			<a href="/board/list${pageMaker.makeParam(pageMaker.criteria.page, 30)}">30</a>
		</div>

		<table border="1">
			<tr>
				<td>번호</td>
				<td>작성자</td>
				<td>제목</td>
				<td>조회수</td>
				<td>비고</td>
			</tr>

			<%-- 컨트롤러가 가져온 게시글 데이터를 반복하여 출력하세요. --%>
			<%-- 게시물 개수가 0개일 경우 목록대신 "게시물이 존재하지 않습니다." 출력 --%>

			<c:forEach var="article" items="${articles}">
				<tr>
					<td>${article.boardNo}</td>
					<td>${article.writer}</td>
					<td>
						<a
							href="/board/content${pageMaker.makeParam(pageMaker.criteria.page)}&boardNo=${article.boardNo}&vf=true">${article.title}</a>

						<c:if test="${article.newArticle}">
							<span class="badge rounded-pill bg-danger">new</span>
						</c:if>
					</td>
					<td>${article.viewCnt}</td>
					<td>
						<c:if test="${article.writer == loginUser.account || loginUser.auth == 'ADMIN'}">
							<a href="/board/delete?boardNo=${article.boardNo}">[삭제]</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>

		</table>

		<!-- 페이지 영역 -->
		<ul class="pagination">

			<c:if test="${pageMaker.prev}">
				<li>
					<a href="/board/list${pageMaker.makeParam(pageMaker.beginPage-1)}">[prev]</a>
				</li>
			</c:if>

			<!-- li*5>a{[$]} -->
			<c:forEach var="i" begin="${pageMaker.beginPage}" end="${pageMaker.endPage}" step="1">
				<li data-page="${i}"><a href="/board/list${pageMaker.makeParam(i)}">[${i}]</a></li>
			</c:forEach>

			<c:if test="${pageMaker.next}">
				<li>
					<a href="/board/list${pageMaker.makeParam(pageMaker.endPage+1)}">[next]</a>
				</li>
			</c:if>
		</ul>

	</c:if>

	<!-- 검색창 영역 -->
	<div class="search">
		<form action="/board/list" id="search-form">

			<input type="hidden" name="amount" value="${pageMaker.criteria.amount}">

			<select name="type">
				<option value="title" ${pageMaker.criteria.type=='title' ? 'selected' : '' }>제목</option>
				<option value="content" ${pageMaker.criteria.type=='content' ? 'selected' : '' }>내용</option>
				<option value="writer" ${pageMaker.criteria.type=='writer' ? 'selected' : '' }>작성자</option>
				<option value="titleContent" ${pageMaker.criteria.type=='titleContent' ? 'selected' : '' }>제목+내용
				</option>
			</select>

			<input type="text" name="keyword" placeholder="검색어를 입력!" value="${pageMaker.criteria.keyword}">

			<button type="submit">검색</button>

		</form>
	</div>

	<p>
		<a href="/board/write">게시글 작성하기</a>
	</p>


	<%@ include file="../include/footer.jsp" %>

	<script>
		//현재 위치한 페이지넘버에 클래스 active를 부여하는 함수 정의
		function appendPageActive(curPageNum) {
			const $ul = document.querySelector('.pagination');
			for (let $li of [...$ul.children]) {
				if ($li.dataset.page === curPageNum) {
					$li.classList.add('active');
				}
			}
		}

		(function () {
			appendPageActive('${pageMaker.criteria.page}');
		}());
	</script>

</body>

</html>