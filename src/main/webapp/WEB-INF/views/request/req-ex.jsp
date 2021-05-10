<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
    <h1>RequestController를 이용한 요청 처리 작업</h1>
    <p>
        컨트롤러 테스트중입니다.
    </p>

    <form action="/request/basic" method="get">
        <button type="submit">GET요청하기!</button>
    </form>

    <form action="/request/basic" method="post">
        <button type="submit">POST요청하기!</button>
    </form>

</body>
</html>