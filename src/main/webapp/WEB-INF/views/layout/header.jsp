<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access = "isAuthenticated()">
    <sec:authentication property = "principal" var = "principal"/>
</sec:authorize>


<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>


<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <a class="navbar-brand" href="/blog">나의 Home</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">

 <!-- session이 null이거나 비어있다면 아래 c:when 발동
 session이 있다면 아래 c:otherwise 발동 -->
  <c:choose>
    <c:when test = "${empty principal}">
    <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="/blog/user/loginForm">로그인</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/blog/user/joinForm">회원가입</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">마이페이지</a>
          </li>
        </ul>
    </c:when>

    <c:otherwise>

        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="/blog/board/writeForm">글쓰기</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/blog/user/userForm">회원정보</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/blog/user/logout">로그아웃</a>
          </li>
        </ul>
    </c:otherwise>
  </c:choose>

  </div>
</nav>
<br/>