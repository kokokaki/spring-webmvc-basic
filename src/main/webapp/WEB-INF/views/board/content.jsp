<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>

	<%@ include file="../include/static-head.jsp" %>
	<style>
		.attach-file-list {
			display: flex;
			width: 80%;
			border: 1px dashed gray;
			margin: 20px auto;
			padding: 20px 10px;
		}

		.attach-file-list a {
			display: flex;
			flex-direction: column;
		}

		.attach-file-list a img {
			width: 100px;
			height: 100px;
			display: block;
		}

		.attach-file-list .thumbnail-box {
			display: flex;
		}
	</style>
</head>

<body>

	<%@ include file="../include/header.jsp" %>
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

				<c:if test="${article.writer == loginUser.account || loginUser.auth == 'ADMIN'}">
					<a href="/board/modify?boardNo=${article.boardNo}&vf=false">글 수정하기</a>
				</c:if>

			</div>
		</div>

		<!-- 첨부파일 영역 -->
		<div class="row">
			<div class="attach-file-list"></div>
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
					<button type="button" class="close text-white" data-dismiss="modal">X</button>
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


	<!-- 첨부파일 관련 스크립트 -->
	<script>
		$(function () {
			const boardNo = '${article.boardNo}';
			const $attachDiv = $('.attach-file-list');

			//첨부파일 경로 목록 요청
			fetch('/board/file/' + boardNo)
				.then(res => res.json())
				.then(filePathList => {
					showFileData(filePathList)
				});


			//드롭한 파일의 형식에 따라 태그를 만들어주는 함수
			function showFileData(pathList) {
				//경로: \2021\06\08\dfjskfdjskf_dfjskfdj_dog.gif
				for (let path of pathList) {
					//이미지인지 아닌지에 따라 구분하여 처리
					checkExtType(path);
				}
			}

			//확장자 판별 후 태그 생성 처리 함수
			function checkExtType(path) {
				//원본 파일명 추출
				let originFileName = path.substring(path.indexOf("_") + 1);

				const $div = document.createElement('div');
				$div.classList.add('thumbnail-box');


				//이미지인지 확장자 체크
				if (isImageFile(originFileName)) {
					//이미지인 경우
					originFileName = originFileName.substring(originFileName.indexOf("_") + 1);

					const $img = document.createElement('img');
					$img.setAttribute('src', '/loadFile?fileName=' + path);
					$img.setAttribute('alt', originFileName);

					$div.appendChild($img);


				} else {
					//이미지가 아닌 경우: 다운로드 링크 생성
					const $link = document.createElement('a');
					$link.setAttribute('href', '/loadFile?fileName=' + path);

					$link.innerHTML = '<img src="/img/file_icon.jpg" alt="파일아이콘"> <span class="file-name">' +
						originFileName + '</span>';

					$div.appendChild($link);
				}
				$attachDiv.append($div);
			}

			//정규표현식으로 이미지파일 여부 확인하는 함수
			function isImageFile(originFileName) {
				const pattern = /jpg$|gif$|png$/i;
				return originFileName.match(pattern);
			}

		}); //end jquery
	</script>


	<!-- 댓글 관련 스크립트 -->
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
			$('.pagination').on('click', 'li a', e => {
				e.preventDefault();
				getReplyList(e.target.getAttribute('href'));
			});

			//댓글 등록 버튼 클릭 이벤트
			$('#replyAddBtn').on('click', e => {

				//서버로 댓글 내용을 전송해서 DB에 저장
				const reqInfo = {
					method: 'POST', //요청 방식
					headers: { //요청 헤더 내용
						'content-type': 'application/json'
					},
					//서버로 전송할 데이터 (JSON)
					body: JSON.stringify({
						boardNo: boardNo,
						replyText: $('#newReplyText').val(),
						replyWriter: $('#newReplyWriter').val()
					})
				};
				fetch('/api/v1/reply', reqInfo)
					.then(res => res.text())
					.then(msg => {
						if (msg === 'insertSuccess') {
							getReplyList(1);
							$('#newReplyText').val('');
							$('#newReplyWriter').val('');
						} else {
							alert('댓글 등록에 실패했습니다.');
						}
					})
			});

			//댓글 수정버튼 클릭 이벤트
			const $modal = $('#replyModifyModal');
			$('#replyData').on('click', '#replyModBtn', e => {
				//console.log("수정버튼 클릭!");
				//모달 띄우기
				$modal.modal('show');

				//기존 댓글내용 가져오기
				const originText = e.target.parentNode.previousElementSibling.textContent;
				// console.log(originText);

				$('#modReplyText').val(originText);

				//모달이 열릴때 모달안에 댓글번호 넣어놓기
				const replyId = e.target.parentNode.parentNode.parentNode.dataset.replyid;
				// console.log(replyId);

				$('#modReplyId').val(replyId);
			});

			//모달창 닫기 이벤트
			$('.modal-header button, .modal-footer button:last-child').on('click', e => {
				$modal.modal('hide');
			});

			//댓글 수정 요청 이벤트
			$('#replyModBtn').on('click', e => {
				//댓글 번호
				const replyId = $('#modReplyId').val();
				//수정된 댓글 내용
				const replyText = $('#modReplyText').val();
				//console.log("댓글번호:", replyId);
				//console.log("댓글내용:", replyText);

				const reqInfo = {
					method: 'PUT',
					headers: {
						'content-type': 'application/json'
					},
					body: JSON.stringify({
						replyNo: replyId,
						replyText: replyText
					})
				};
				fetch('/api/v1/reply/' + replyId, reqInfo)
					.then(res => res.text())
					.then(msg => {
						if (msg === 'modSuccess') {
							$modal.modal('hide');
							getReplyList(1);
						} else {
							alert("댓글 수정에 실패했습니다.");
						}
					})
			});

			//댓글 삭제 비동기 요청 이벤트
			$("#replyData").on("click", "#replyDelBtn", e => {
				const replyId = e.target.parentNode.parentNode.parentNode.dataset.replyid;
				//console.log("삭제 버튼 클릭! : " + replyId);
				if (!confirm("진짜로 삭제할거니??")) {
					return;
				}
				const reqInfo = {
					method: 'DELETE'
				};
				fetch('/api/v1/reply/' + replyId, reqInfo)
					.then(res => res.text())
					.then(msg => {
						if (msg === 'delSuccess') {
							getReplyList(1);
						} else {
							alert("댓글 삭제에 실패했습니다.");
						}
					})
			});
		});
	</script>


</body>

</html>