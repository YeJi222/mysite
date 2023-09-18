<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // fn:replace 안에 '\' 사용불가 
    pageContext.setAttribute("newline", "\r\n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>		
							<div class="view-content">
								${fn:replace(vo.contents, newline, "<br>")}
							</div>
						</td>    
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board/${pageNum}">글목록</a>
					<c:if test="${authUser.name == vo.writer }">
						<a href="${pageContext.request.contextPath }/board/modifyform/${vo.no}/${pageNum}">글수정</a>
					</c:if>
					<c:if test="${not empty authUser }">
						<a href="${pageContext.request.contextPath }/board/writeform/${vo.no}/${pageNum}">답글</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>