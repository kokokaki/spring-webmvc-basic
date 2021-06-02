<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>

    <%@ include file="../include/static-head.jsp" %>
</head>

<body>
    <%@ include file="../include/header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="offset-md-2 col-md-4">
                <div class="card" style="width:200%;">
                    <div class="card-header text-white" style="background: #343A40;">
                        <h2><span style="color: gray;">MVC</span> 회원 가입</h2>
                    </div>
                    <div class="card-body">


                        <form action="/member/sign-up" name="signup" id="signUpForm" method="post"
                            style="margin-bottom: 0;">

                            <input type="hidden" name="auth" value="COMMON">

                            <table style="cellpadding: 0; cellspacing: 0; margin: 0 auto; width: 100%">
                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                            <span id="idChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="account" id="user_id"
                                            class="form-control tooltipstered" maxlength="14" required="required"
                                            aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="숫자와 영어로 4-14자">
                                    </td>

                                </tr>

                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="password" size="17" maxlength="20" id="password" name="password"
                                            class="form-control tooltipstered" maxlength="20" required="required"
                                            aria-required="true"
                                            style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="영문과 특수문자를 포함한 최소 8자"></td>
                                </tr>
                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>비밀번호를 재확인해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk2"></span>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="password" size="17" maxlength="20" id="password_check"
                                            name="pw_check" class="form-control tooltipstered" maxlength="20"
                                            required="required" aria-required="true"
                                            style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="비밀번호가 일치해야합니다."></td>
                                </tr>

                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>이름을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="nameChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="name" id="user_name" class="form-control tooltipstered"
                                            maxlength="6" required="required" aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="한글로 최대 6자"></td>
                                </tr>


                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>이메일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="emailChk"></span>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="email" name="email" id="user_email"
                                            class="form-control tooltipstered" required="required" aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="ex) abc@mvc.com"></td>
                                </tr>


                                <tr>
                                    <td style="padding-top: 10px; text-align: center">
                                        <p><strong>회원가입하셔서 더 많은 서비스를 사용하세요~~!</strong></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 100%; text-align: center; colspan: 2;">
                                        <input type="button" value="회원가입" class="btn form-control tooltipstered"
                                            id="signup-btn"
                                            style="background: gray; margin-top: 0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">
                                    </td>
                                </tr>

                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>


    <script>
        $(function () {
            //입력값 검증 정규표현식
            const getIdCheck = RegExp(/^[a-zA-Z0-9]{4,14}$/);
            const getPwCheck = RegExp(
                /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);
            const getName = RegExp(/^[가-힣]+$/);
            const getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);

            //========== 회원가입 입력값 검증 =============//
            //ID검증
            const $idInput = $('#user_id');
            $idInput.on('keyup', e => {
                //아이디를 입력하지 않았거나 공백만 쓰고 넘어간 경우
                if ($idInput.val().trim() === '') {
                    $idInput.css('background', 'pink');
                    $('#idChk').html('<b style="color:red; font-size:14px;">[아이디는 필수 정보입니다!]</b>');

                //아이디를 정규표현식 패턴대로 입력하지 않을 경우
                //test메서드는 정규표현식을 검증하여 값이 일치하면 true,
                //일치하지 않으면 false를 리턴
                } else if (!getIdCheck.test($idInput.val())) {
                    $idInput.css('background', 'pink');
                    $('#idChk').html('<b style="color:red; font-size:14px;">[영문, 숫자 4~14자로 작성하세요.]</b>');
                } else {

                    //아이디 중복확인 비동기 요청
                    fetch('/check?type=account&keyword=' + $idInput.val())
                        .then(res => res.text())
                        .then(flag => {
                            console.log(flag);
                            if (flag === 'true') {
                                //중복된!
                                $idInput.css('background', 'pink');
                    $('#idChk').html('<b style="color:red; font-size:14px;">[아이디가 중복되었습니다.]</b>');
                            } else {
                                //중복안됨!
                                $idInput.css('background', 'aqua');
                    $('#idChk').html('<b style="color:green; font-size:14px;">[참 잘했어요 화이팅!]</b>');
                            }
                        });
                    /*setTimeout(() => {
                        fetch('/check?type=account&keyword=' + $idInput.val())
                        .then(res => res.text())
                        .then(flag => {
                            console.log(flag);
                        })
                    }, 3000);*/
                   

                    
                }
            });


            //회원가입 버튼 클릭 이벤트
            $('#signup-btn').on('click', e => {
                //form Node
                $('#signUpForm').submit(); //수동 submit
            });

        }); //JQUERY END
    </script>

</body>

</html>