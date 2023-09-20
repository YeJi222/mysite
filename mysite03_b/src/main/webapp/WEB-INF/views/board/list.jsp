<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="hidden" name="a" value="add">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>	
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">			
						<tr>
							<td>${count - status.index } </td>
							<td style="padding-left: ${((vo.depth + 1) - 1)*30 }px">
								<c:choose>
									<c:when test='${vo.depth > 0 }'>
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">
									</c:when>
									<c:otherwise>
										<div></div>
									</c:otherwise>
								</c:choose>
								<a href="${pageContext.request.contextPath }/board/viewform/${vo.no}/${pageNum}">${vo.title }</a>
							</td>
							<td>${vo.writer }</td> 
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							
							<c:choose>
								<c:when test="${authUser.name == vo.writer }">
									<td>
										<a href="${pageContext.request.contextPath }/board/deleteform/${vo.no}/${pageInfo.curPageNo}" class="del">삭제</a>
									</td>
								</c:when>
								
								<c:when test="${authUser.name != vo.writer }">
									<td></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager -->
				<div class="pager">
					<ul>
						<c:if test="${pageInfo.prevBtn == true}">
							<li><a href="${pageContext.request.contextPath }/board/${pageInfo.curPageNo - 1}">◀</a></li>
						</c:if>
						
						<c:forEach var="pageNo" begin="${pageInfo.startPageNo }" end="${pageInfo.endPageNo }">
							<c:choose>
								<c:when test="${pageNo > pageInfo.totalPageNo}">
									<li>${pageNo }</li>
								</c:when>
								
								<c:when test="${pageNo != pageInfo.curPageNo}">
									<li><a href="${pageContext.request.contextPath }/board/${pageNo }">${pageNo }</a></li>
								</c:when>
								
								<c:when test="${pageNo == pageInfo.curPageNo}">
									<li class="selected">${pageNo }</li>
								</c:when>
							</c:choose>
						</c:forEach>
						
						<c:if test="${pageInfo.nextBtn == true}">
							<li><a href="${pageContext.request.contextPath }/board/${pageInfo.curPageNo + 1}">▶</a></li>
						</c:if>
					</ul>
				</div>					
				
				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/writeform/0/${pageInfo.curPageNo}" id="new-book">글쓰기</a>
					</div>				
				</c:if>	
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>