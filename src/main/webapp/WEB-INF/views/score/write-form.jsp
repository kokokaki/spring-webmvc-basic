<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>

<style>
    .del-btn {
        width: 10px;
        height: 10px;
        background: red;
        color: white;
        border-radius: 5px;
        margin-left: 5px;
        text-decoration: none;
        font-size: 0.7em;
        padding: 6px;
    }
    .del-btn:hover {
        background: orangered;
    }
    li {
        margin-bottom: 10px;
    }

    .score-list > li:first-child {
        font-size: 1.2em;
        color: blue;
        font-weight: 700;
        border-bottom: 1px solid skyblue;
        margin-bottom: 10px;
    }
</style>
</head>
<body>

    <h1>시험 점수 등록</h1>
    <form action="/score/register" method="post">
        <p>
            # 이름: <input type="text" name="name">
        </p>
        <p>
            # 국어: <input type="text" name="kor">
        </p>
        <p>
            # 영어: <input type="text" name="eng">
        </p>
        <p>
            # 수학: <input type="text" name="math">
        </p>
        <div>
            <button type="submit">확인</button>
        </div>
    </form>

    <hr>


    <ul class="score-list">

        <li>총 학생 수 : ${count}명</li>

        <c:forEach var="score" items="${scoreList}">
            <li># 학번: ${score.stuNum}, 이름: <a href="/score/detail?stuNum=${score.stuNum}">${score.name}</a>, 국어: ${score.kor}점, 영어: ${score.eng}점, 수학: ${score.math}점, 총점: ${score.total}점, 평균: ${score.average}점
                <a class="del-btn" href="/score/delete?stuNum=${score.stuNum}">삭제</a>
            </li>
        </c:forEach>
        
    </ul>

</body>
</html>