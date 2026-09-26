<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "../layout/header.jsp"%>


<div class="container">

    <button class = "btn btn-secondary"e onclick = "history.back()"> 돌아가기 </button>
    <%-- 수정과 삭제를 할 때 id값을 이용 -> id="btn-OOOOO"
    또한, board를 들고 올 때 board에서 .EAGER에 의해 user, reply 정보도 같이
    조인해서 들고 오기 때문에(board.java 확인) board.user.username이 가능
    --%>
    <%-- 작성한 글 id와 유저id가 같을때만 삭제(수정) 버튼이 보이게
    수정의 경우, 별도의 페이지가 필요하므로 id="btn-update" 가 아닌 하이퍼링크
    /board/${board.id}/updateForm 따라서, 해당 페이지도 따로 제작해야됨--%>

    <c:if test ="${board.user.id == principal.user.id}">
        <a href = "/board/${board.id}/updateForm" class = "btn btn-warning"> 수정 </a>
        <button id="btn-delete" class = "btn btn-danger"> 삭제 </button>
    </c:if>
    <br/><br/>
    <div>
    글 번호 : <span id = "id">${board.id} </i></span>
    작성자 : <span>${board.user.username} </i></span>
    </div>
    <br/><br/>
      <div>
        <%-- 이 부분을 saveForm과 다르게 아래 코드로 작성  --%>
        <h3> ${board.title}</h3>
      </div>

      <hr />
      <div>
         <%-- 이 부분을 saveForm과 다르게 아래 코드로 작성  --%>
        <div> ${board.content}</div>
      </div>
      <hr />
</div>

<script src = "/js/board.js"> </script>
<%@ include file =  "../layout/footer.jsp"%>