<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<h1><a style="text-decoration: none; color: #79B30B" href="<%=request.getContextPath() %>/">MySite</h1>
	<ul>
		<li><a href="<%=request.getContextPath() %>/user?a=loginform">로그인</a><li>
		<li><a href="<%=request.getContextPath() %>/user?a=joinform">회원가입</a><li>
		<li><a href="<%=request.getContextPath() %>/user?a=updateform">회원정보수정</a><li>
		<li><a href="<%=request.getContextPath() %>/user?a=logout">로그아웃</a><li>
		<li>님 안녕하세요 ^^;</li>
	</ul>
</div>