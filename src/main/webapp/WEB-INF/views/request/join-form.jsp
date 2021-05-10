<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        label {
            display: block;
        }
    </style>
</head>
<body>
    
    <form action="/request/param3">
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