<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "../layout/header.jsp"%>


<div class="container">

    <button class = "btn btn-secondary"e onclick = "history.back()"> 돌아가기 </button>

    <%-- 수정과 삭제를 할 때 id값을 이용 -> id="btn-OOOOO"
    또한, board를 들고 올 때 board에서 .EAGER에 의해 user, reply 정보도 같이
    조인해서 들고 오기 때문에(board.java 확인) board.user.username이 가능
    작성한 글 id와 유저id가 같을때만 삭제(수정) 버튼이 보이게
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
         <div class="board-url-copy">
                 <button class="btn btn-outline-secondary btn-sm"
                         onclick="copyUrl()">
                     🔗 URL 복사
                 </button>
         </div>
         <div> ${board.content}</div>

      </div>
      <hr />


      <%-- 1. card : div 태그 안에 있는 속성들이 카드로 묶임
           2. btn btn-primary -> 파란색 버튼
      --%>
      <div class =  "card">
        <form>
        <%-- 어떤 게시물에 댓글이 달리는지 알아야됨. hidden 값을 통해 알아냄
         board.js => replySave 확인 --%>
        <input type = "hidden" id = "userId" value = "${principal.user.id}" />
        <input type = "hidden" id = "boardId" value = "${board.id}" />
            <div>
                <div class= "card-body">
                    <textarea id="reply-content" class="form-control" rows="1"></textarea>
                </div>
                <div class=  "card-footer">
                    <button type = "button" id = "btn-reply-save" class = "btn btn-primary">등록</button></div>
                </div>
        </form>

        <div class = "card">
            <div class=  "card-header"> 댓글 목록 </div>
          <%-- display:block으로 돼있음 -> display를 d-flex 로 변경하면
                      각 문단이 위아래로 나뉘어져 있던게 나란히 그리고 comment-- 처럼 "--" 가 2개인건 내가 만든
                      클래스 내가 만든 id라 생각하면 편함
                      또한, reply는 board에서 mappedBy = "board"에 의해 board 데이터와 함께 join되어 가져와짐
                      따라서 board 데이터를 가져올 때 활용가능--%>

            <ul id = "reply-box" class = "list-group">
                <c:forEach var = "reply" items = "${board.replys}">
                    <li id="reply-${reply.id}" class = "list-group-item d-flex justify-content-between">
                        <div> ${reply.content}  </div>
                        <div class = "d-flex">
                            <div class="font-italic"> 작성자 : ${reply.user.username} &nbsp; </div>
                            <!-- onclic으로 하고 replyDelete함수를 만들어서 board와 reply id를 전달
                            board.js에서 replyDelete함수(여기서 board.id와 user.id를 매개변수로 받음) 만들것-->
                            <button onclick ="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제 </button>
                       </div>
                    </li>
                </c:forEach>

            </ul>

        </div>


      </div>
</div>
<script>
function copyUrl() {
    navigator.clipboard.writeText(window.location.href)
        .then(() => alert("URL이 복사되었습니다."));
}
</script>

<script src = "/js/board.js"> </script>
<%@ include file =  "../layout/footer.jsp"%>