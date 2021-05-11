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
        li {
            list-style: none;
            margin: 0;
            padding: 0;
            font-size: 1.4em;
        }
        section {
            width: 30%;
            margin: 0 auto;
            padding: 20px;
            border: 2px solid orange;
            border-radius: 10px;
            box-shadow: 2px 2px 5px orangered;
        }
        a {
            display: block;
            width: fit-content;
            text-decoration: none;
            background: rgb(83, 189, 83);
            color: white;
            box-shadow: 1px 1px 2px rgb(146, 228, 146);
            border-radius: 5px;
            border: 1px solid white;
            padding: 5px;
        }
    </style>

</head>

<body>

    <section>
        <h1>${score.name}님 성적 정보</h1>
        <ul>
            <li># 국어: ${score.kor}점</li>
            <li># 영어: ${score.eng}점</li>
            <li># 수학: ${score.math}점</li>
            <li># 총점: ${score.total}점</li>
            <li># 평균: ${score.average}점</li>
        </ul>
        <div class="btn-group">
            <a href="/score/list">목록</a>
        </div>
    </section>
</body>

</html>