<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "../layout/header.jsp"%>

<!--model에서 회원가입할 때 뭐가 필요한지 확인 -->

<div class="container">

<form action ="/auth/loginProc" method = "post">
  <div class="form-group">
    <label for="username">Username</label>

    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
  </div>

  <div class="form-group">
    <label for="password">Password:</label>
    <input type="password" name = "password" class="form-control" placeholder="Enter password" id="password">
  </div>

  <button id="btn-login" class="btn btn-primary">로그인</button>
</form>


</div>

<%@ include file =  "../layout/footer.jsp"%>