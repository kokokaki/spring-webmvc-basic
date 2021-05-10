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
    label {
        display: block;
    }
</style>
</head>
<body>

    <h1>Model객체에 대해 알아보기</h1>
    <a href="/response/test?age=20">테스트 1페이지로~</a>

    <br>

    <form action="/response/test2" method="post">
        <label>
            # 아이디: <input type="text" name="userId">
        </label>
        <label>
            # 패스워드: <input type="password" name="userPw">
        </label>
        <label>
            # 이름: <input type="text" name="userName">
        </label>
        <label>
            # 취미: 
            <input type="checkbox" name="hobby" value="공부"> 공부 
            <input type="checkbox" name="hobby" value="축구"> 축구 
            <input type="checkbox" name="hobby" value="게임"> 게임 
        </label>
        <button type="submit">회원가입</button>
    </form>

</body>
</html>