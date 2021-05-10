<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
</head>
<body>  
    <p>${user}</p>

    <h1>${user.userName}님 가입정보</h1>
    <div># 아이디: ${user.userId}</div>
    <div># 패스워드: ${user.userPw}</div>
    <div># 취미: ${user.hobby}</div>

</body>
</html>