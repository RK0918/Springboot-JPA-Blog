<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file =  "layout/header.jsp"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!--"${boards}"  boardController.java 참고
forEach문을 통해 items(boards)에 데이터를 받아 board 변수에 하나씩 담아서 처리
또한 ${board.title} 에서 board class에서 @Data를 통해 getter, setter를 생성한 것이므로
실제론 board.gettitle()이 호출이 되는 것
-->

<div class="container">

<c:forEach var ="board" items = "${boards.content}">

<!-- m-2 : 마진을 2 늘림 -->
    <div class="card m-2">
  <div class="card-body">
    <h4 class="card-title">${board.title}</h4>

    <%-- 상세보기를 위해 href = "#"를 "/board/${board.id}" --%>
    <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
    </div>
    </div>
</c:forEach>
<ul class="pagination justify-content-center">
<%--
    [Pagination - Previous/Next 버튼]
    boards는 Page<Board> 타입 (Pageable로 조회한 결과)
    - boards.first : 현재 페이지가 첫 페이지인지 (boolean) → isFirst()
    - boards.last  : 현재 페이지가 마지막 페이지인지 (boolean) → isLast()
    - boards.number: 현재 페이지 번호, 0부터 시작 (0-based) → getNumber()

    첫 페이지에서는 Previous를 누를 수 없어야 하고,
    마지막 페이지에서는 Next를 누를 수 없어야 하므로
    c:choose로 분기해서 각각 다른 class(disabled 여부)를 부여한다.
--%>

<%-- Previous 버튼 --%>
<c:choose>
    <%-- 현재 페이지가 첫 페이지라면 → Previous 비활성화 (page-item disabled) --%>
    <c:when test="${boards.first}">
        <li class="page-item disabled">
            <%-- number-1이 음수(-1)가 될 수 있지만, 어차피 disabled 상태라 클릭 방지 필요 --%>
            <a class="page-link" href="?page=${boards.number-1}">Previous</a>
        </li>
    </c:when>
    <%-- 첫 페이지가 아니라면 → 이전 페이지(number-1)로 이동 가능 --%>
    <c:otherwise>
        <li class="page-item">
            <a class="page-link" href="?page=${boards.number-1}">Previous</a>
        </li>
    </c:otherwise>
</c:choose>

<%-- Next 버튼 --%>
<c:choose>
    <%-- 현재 페이지가 마지막 페이지라면 → Next 비활성화 (page-item disabled) --%>
    <c:when test="${boards.last}">
        <li class="page-item disabled">
            <a class="page-link" href="?page=${boards.number+1}">Next</a>
        </li>
    </c:when>
    <%-- 마지막 페이지가 아니라면 → 다음 페이지(number+1)로 이동 가능 --%>
    <c:otherwise>
        <li class="page-item">
            <a class="page-link" href="?page=${boards.number+1}">Next</a>
        </li>
    </c:otherwise>
</c:choose>

</ul>

</div>

<%@ include file =  "layout/footer.jsp"%>