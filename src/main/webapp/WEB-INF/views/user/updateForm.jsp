<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "../layout/header.jsp"%>

<!--model에서 회원가입할 때 뭐가 필요한지 확인 -->

<div class="container">

<form>
  <%--user.js에서 보내지 않았지만 어떤 유저인지는 알아야 하므로
  hidden을 통해 id값을 찾아 어떤 user인지 알게함(username은 readonly때문에 보내지 않지만
  user가 누구인지를 파악하기 위해 id는 필요하므로
  --%>
  <input type = "hidden" id = "id" value = "${principal.user.id}" />
  <div class="form-group">
    <label for="username">Username</label>
    <%-- readonly를 이용해 username은 수정못하게 --%>
    <input type="text" value = "${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
  </div>

  <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" placeholder="Enter password" id="password">
    </div>

  <div class="form-group">
      <label for="Email">Email address:</label>
      <input type="email" value = "${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
    </div>


    </form>
      <button id="btn-update" class="btn btn-primary">회원수정 완료 </button>

</div>
<script src = "/js/user.js"> </script>
<%@ include file =  "../layout/footer.jsp"%>