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

    <c:if test="${loginUser == null}">
        <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=${appKey}&redirect_uri=http://localhost:8181${redirectUri}&response_type=code">
            <img src="//mud-kage.kakao.com/14/dn/btqbjxsO6vP/KPiGpdnsubSq3a0PHEGUK1/o.jpg" alt="카카오 로그인">
        </a>
    </c:if>

    <c:if test="${loginUser != null}">
        <h1>${loginUser.name}님 카카오 로그인 성공!</h1>
        <img src="${profile_path}" alt="프로필사진">
        <br>
        <p>
            # 이메일: ${loginUser.email}
        </p>

        <a href="/kakao/logout">카카오 로그아웃</a>
    </c:if>

</body>

</html>