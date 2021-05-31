<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>

	<%@ include file="../include/static-head.jsp" %>

</head>

<body>
	<div class="container">
		<div class="row">
			<div class="offset-md-1 col-md-10">
				<h1>${article.boardNo}번 게시물 내용</h1>

				<p>
					# 글번호: ${article.boardNo}<br>
					# 작성자: ${article.writer}<br>
					# 제목: ${article.title}<br>
					# 내용: <br>
					<textarea rows="5" cols="30" disabled>${article.content}</textarea>
				</p>

				<a href="/board/list?page=${cri.page}&type=${cri.type}&keyword=${cri.keyword}&amount=${cri.amount}">글
					목록보기</a>&nbsp;
				<a href="/board/modify?boardNo=${article.boardNo}&vf=false">글 수정하기</a>
			</div>
		</div>

		<!-- 댓글 영역 -->

		<div id="replies" class="row">
			<div class="offset-md-1 col-md-10">
				<!-- 댓글 쓰기 영역 -->
				<div class="card">
					<div class="card-body">
						<div class="row">
							<div class="col-md-9">
								<div class="form-group">
									<label for="newReplyText" hidden>댓글 내용</label>
									<textarea rows="3" id="newReplyText" name="replyText" class="form-control"
										placeholder="댓글을 입력해주세요."></textarea>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label for="newReplyWriter" hidden>댓글 작성자</label>
									<input id="newReplyWriter" name="replyWriter" type="text" class="form-control"
										placeholder="작성자 이름" style="margin-bottom: 6px;">
									<button id="replyAddBtn" type="button" class="btn btn-dark form-control">등록</button>
								</div>
							</div>
						</div>
					</div>
				</div> <!-- end reply write -->

				<!--댓글 내용 영역-->
				<div class="card">
					<!-- 댓글 내용 헤더 -->
					<div class="card-header text-white m-0" style="background: #343A40;">
						<div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
					</div>

					<!-- 댓글 내용 바디 -->
					<div id="replyCollapse" class="card">
						<div id="replyData">
							<!-- 
								< JS로 댓글 정보 DIV삽입 > 
							-->
						</div>

						<!-- 댓글 페이징 영역 -->
						<ul class="pagination justify-content-center">
							<!-- 
								< JS로 댓글 페이징 DIV삽입 > 
							-->
						</ul>
					</div>
				</div> <!-- end reply content -->
			</div>
		</div> <!-- end replies row -->
	</div> <!-- end content container -->

	<!-- 댓글 수정 모달 -->
	<div class="modal fade bd-example-modal-lg" id="replyModifyModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header" style="background: #343A40; color: white;">
					<h4 class="modal-title">댓글 수정하기</h4>
					<button type="button" class="close text-white" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<div class="form-group">
						<input id="modReplyId" type="hidden">
						<label for="modReplyText" hidden>댓글내용</label>
						<textarea id="modReplyText" class="form-control" placeholder="수정할 댓글 내용을 입력하세요."
							rows="3"></textarea>
					</div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button id="replyModBtn" type="button" class="btn btn-dark">수정</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
				</div>


			</div>
		</div>
	</div>

	<!-- end replyModifyModal -->


	<%@ include file="../include/footer.jsp" %>

	<script>
		// 댓글 처리 JS
		$(function () {
			//원본글 번호
			const boardNo = '${article.boardNo}';

			//날짜 포맷 변환 함수
			function formatDate(datetime) {
				//문자열 날짜 데이터를 날짜객체로 변환
				const dateObj = new Date(datetime);
				// console.log(dateObj);
				//날짜객체를 통해 각 날짜 정보 얻기
				let year = dateObj.getFullYear();
				let month = dateObj.getMonth() + 1;
				let day = dateObj.getDate();
				let hour = dateObj.getHours();
				let minute = dateObj.getMinutes();

				//오전, 오후 시간체크
				let ampm = '';
				if (hour < 12 && hour >= 6) {
					ampm = '오전';
				} else if (hour >= 12 && hour < 21) {
					ampm = '오후';
					if (hour !== 12) {
						hour -= 12;
					}
				} else if (hour >= 21 && hour <= 24) {
					ampm = '밤';
					hour -= 12;
				} else {
					ampm = '새벽';
				}

				//숫자가 1자리일 경우 2자리로 변환
				(month < 10) ? month = '0' + month: month;
				(day < 10) ? day = '0' + day: day;
				(hour < 10) ? hour = '0' + hour: hour;
				(minute < 10) ? minute = '0' + minute: minute;

				return year + "-" + month + "-" + day + " " + ampm + " " + hour + ":" + minute;

			}

			//댓글 페이지 태그 생성 배치함수
			function makePageInfo(pageInfo) {
				let tag = "";

				const begin = pageInfo.beginPage;
				const end = pageInfo.endPage;

				//이전 버튼 만들기
				if (pageInfo.prev) {
					tag += "<li class='page-item'><a class='page-link page-active' href='" + (begin - 1) +
						"'>이전</a></li>";
				}

				//페이지 번호 리스트 만들기
				for (let i = begin; i <= end; i++) {
					const active = (pageInfo.criteria.page === i) ? 'page-active' : '';
					tag += "<li class='page-item'><a class='page-link page-custom " + active + "' href='" + i + "'>" +
						i + "</a></li>";
				}

				//다음 버튼 만들기
				if (pageInfo.next) {
					tag += "<li class='page-item'><a class='page-link page-active' href='" + (end + 1) +
						"'>다음</a></li>";
				}

				//태그 삽입하기
				$(".pagination").html(tag);
			}

			//댓글 태그 생성, 배치 함수
			function makeReplyListDOM(replyMap) {
				let tag = '';

				for (let reply of replyMap.replyList) {
					tag += "<div id='replyContent' class='card-body' data-replyId='" + reply.replyNo + "'>" +
						"    <div class='row user-block'>" +
						"       <span class='col-md-3'>" +
						"         <b>" + reply.replyWriter + "</b>" +
						"       </span>" +
						"       <span class='offset-md-6 col-md-3 text-right'><b>" + formatDate(reply.replyDate) +
						"</b></span>" +
						"    </div><br>" +
						"    <div class='row'>" +
						"       <div class='col-md-6'>" + reply.replyText + "</div>" +
						"       <div class='offset-md-2 col-md-4 text-right'>" +
						"         <a id='replyModBtn' class='btn btn-sm btn-outline-dark' href='#replyModifyModal' data-toggle='modal'>수정</a>&nbsp;" +
						"         <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>" +
						"       </div>" +
						"    </div>" +
						" </div>";
				}

				//만든 태그를 댓글목록 안에 배치
				$('#replyData').html(tag);
				//댓글 수 배치
				$('#replyCnt').text(replyMap.count);

				//페이지 태그 배치
				makePageInfo(replyMap.pageInfo);

			}

			//댓글 목록 비동기 요청처리 함수
			function getReplyList(page) {
				fetch('/api/v1/reply/' + boardNo + "/" + page)
					.then(res => res.json())
					.then(replyMap => {
						console.log(replyMap);
						makeReplyListDOM(replyMap);
					});
			}

			//페이지 첫 진입시 비동기로 댓글목록 불러오기
			getReplyList(1);

			//페이지 버튼 클릭 이벤트
			$('.pagination').on('click', 'li a',  e => {
				e.preventDefault();
				getReplyList(e.target.getAttribute('href'));				
			});
		});
	</script>


</body>

</html>