<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var render = function(vo, mode) {
	var html = 		
		"<li data-no='" + vo.no + "'>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.contents + "</p>" +
		"<strong></strong>" +
		"<a href='#' data-no='" + vo.no + "'>삭제</a>" +
	    "</li>"
		    
	$("#list-guestbook")[mode ? 'prepend' : 'append'](html);
}

var fetch = function() {
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook",
		type: "get",
		dataType: "json",
		success: function(response) {
			if(response.result === 'fail') {
				console.error(response.message);
				return;
			}
			
			response.data.forEach(function(vo){
				render(vo, false);
			})
		}
	})	
}

/* $(function(){
	$(window).scroll(function(){
		// 조건(스크롤바가 바닥에 도착)이 되면 fetch() 호출
		var $window = $(this);
		var $document = $(document);
		
		var wh = $window.height();
		var dh = $document.height();
		var st = $window.scrollTop();
		
		if(dh < wh + st + 10) {
			console.log("fetch!!!");
			fetch();
		}
	})
	
	// 최초 리스트 가져오기
	fetch();
}); */

$(function(){
	$('#add-form').submit(function(event) {
		event.preventDefault();
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.contents = $("#tx-content").val().replace(/\n/g, '<br>');
		
		$.ajax({
			url: '${pageContext.request.contextPath}/api/guestbook',
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response) {
				if(response.result === 'fail') {
					console.error(response.message);
					return;
				}
				
				render(response.data, true);
			}
		})
	});
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		model: true,
		buttons: {
			"삭제": function() {
				var no = $('#hidden-no').val();
				var password = $('#password-delete').val();
				
				console.log("ajax 삭제~~", no, password);
				
				// 후처리
				$.ajax({
					url: '${pageContext.request.contextPath}/api/guestbook/' + no,
					type: 'delete',
					dataType: 'json',
					contentType: 'application/x-www-form-urlencoded',
					data: 'password=' + password,
					success: function(response) {
						if(response.result === 'fail') {
							console.error(response.message);
							return;
						} else{
							// 1. response.data(no) 가지고 있는 <li data+no='{no}' > 찾아서 삭제
							$("li[data-no='" + no + "']").remove();
							
							// 2. dialogDelete.dialog('close');
							$(this).dialog('close');
						}
					}
				})
				
				// 폼의 input value reset;
				$('#password-delete').val("");			
			},
			"취소": function() {
				$(this).dialog('close');
				$('#password-delete').val("");		
			}
		},
		close: function() {
			console.log("다이알로그 close!!!!!");
		}
	
	});
	
	// 메세지 삭제 버튼 click 이벤트 처리(Live Event)
	$(document).on('click', '#list-guestbook li a', function(event) {
		event.preventDefault();

		var no = $(this).data('no');
		$('#hidden-no').val(no);
		dialogDelete.dialog('open');
	})
	
	// 최초 리스트 가져오기
	fetch();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
							
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>