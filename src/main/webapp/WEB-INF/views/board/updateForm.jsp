<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "../layout/header.jsp"%>


<div class="container">
    <form>
      <%-- 글을 수정하기 위해선 board.title과 content로는 부족하기 때문에
       아래 값들이 필요하고 아래 세 값 id, board.title, board.content 값을 board.js에서 가져와야됨(board.js로 이동)--%>
      <input type ="hidden" id = "id" value = "${board.id}" />
      <div class="form-group">
      <%-- model의 데이터를 들고오니깐 value = ""${board.title}"" --%>
        <input value = "${board.title}" type="text" class="form-control" placeholder="Enter title" id="title">
      </div>

   <div class="form-group">
     <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
   </div>
    </form>
    <%-- 이제 아래 btn-update 를 추가하고 해당 버튼을 누르면 어떻게 동작할지
     board.js 수정하면 됨 --%>
    <button id="btn-update" class="btn btn-primary">글수정 완료</button>
</div>
<script>
      $('.summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 300
      });
    </script>

<script src = "/js/board.js"> </script>
<%@ include file =  "../layout/footer.jsp"%>