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
    
    <h1>basic.jsp입니다~~</h1>

    <% 
        //스크립트릿: 이 태그안에서는 자바코드를 사용할 수 있음.
        for (int i = 1; i <= 9; i++) {
            //out객체는 PrintWriter타입의 객체이며 html을 그릴 수 있음.
            out.println("2 x " + i + " = " + (2*i) + "<br>");
        }
    %>

</body>
</html>



