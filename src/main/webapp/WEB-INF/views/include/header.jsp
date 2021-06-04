<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <h1>
       <a href="/board/list">Spring Web Basic Board</a>
    </h1>
    <div class="btn-box">
        <c:if test="${loginUser == null}">
            <a href="/member/sign-in" class="btn btn-primary">로그인</a>
            <a href="/member/sign-up" class="btn btn-danger">회원가입</a>
        </c:if>

        <c:if test="${loginUser != null}">
            <span style="margin-right: 20px;">${loginUser.name}님 하이~!</span>
            <a href="/member/logout" class="btn btn-danger">로그아웃</a>
        </c:if>
    </div>
</header>

<c:if test="${loginUser != null}">
    <div>현재 로그인한 유저: ${loginUser.name}</div>
</c:if>