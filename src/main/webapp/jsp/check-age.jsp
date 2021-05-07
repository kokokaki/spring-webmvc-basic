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

    <%
        int age = Integer.parseInt(request.getParameter("age"));

        String txt = "";
        if (age > 19) {
            txt = "성인";
        } else {
            txt = "미성년자";
        }
    %>

    <h1>check-age.jsp파일입니다.</h1>
    
    <h2>당신은 <%= txt %>입니다.</h2>
</body>
</html>