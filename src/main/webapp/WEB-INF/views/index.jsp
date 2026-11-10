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

<style>
    /* 메인페이지 게시글 썸네일 */

    .board-preview img {
        width: 100%;
        max-width: 300px;
        height: 200px;
        object-fit: cover;
        border-radius: 8px;
        margin-bottom: 15px;
    }
</style>

<div class="container">

<c:forEach var ="board" items = "${boards.content}">

    <!-- m-2 : 마진을 2 늘림 -->
    <div class="card m-2">
        <div class="card-body">

            <%-- 게시글 제목 --%>
            <h4 class="card-title">
                <c:out value="${board.title}" />
            </h4>

            <%--
                게시글 내용에서 첫 번째 이미지만 출력할 영역
                실제 이미지는 아래 JavaScript에서 추출하여 추가
            --%>
            <div class="board-preview"></div>

            <%--
                게시글 원본 내용 (HTML 포함)
                Base64 이미지가 포함된 HTML 문자열을 보관
                화면에는 직접 출력하지 않음
                c:out으로 HTML을 이스케이프 처리
            --%>
            <div class="board-content" style="display:none;">
                <c:out value="${board.content}" />
            </div>


            <%-- 상세보기를 위해 href = "#"를 "/board/${board.id}" --%>
            <a href="/board/${board.id}" class="btn btn-primary">
                상세보기
            </a>

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

<script>
    // 페이지의 HTML 요소가 로딩되면 실행
    $(document).ready(function() {

        // .card 클래스를 가진 게시글을 하나씩 반복
        $(".card").each(function() {

            // 현재 게시글의 원본 HTML 문자열 가져오기
            let content = $(this).find(".board-content").text();

            // HTML 문자열을 DOM으로 변환
            // 실제 화면에 게시글 전체 내용을 출력하지 않음
            let html = $("<div>").html(content);

            // 게시글에 포함된 첫 번째 img 태그만 추출
            let image = html.find("img").first();

            // 첫 번째 이미지가 존재하는 경우
            if (image.length > 0) {

                // 이미지 태그를 복제하여 썸네일 영역에 추가
                $(this).find(".board-preview").append(image.clone());

            }

        });

    });
</script>

<%@ include file =  "layout/footer.jsp"%>