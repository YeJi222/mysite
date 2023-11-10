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
var messageBox = function(title, message, callback){
	$("#dialog").attr('title', title);
	$("#dialog p").text(message);
	
	$("#dialog").dialog({
		width: 340,
		modal: true,
		buttons: {
			"확인": function(){
				// $(this) : dialog element 
				$(this).dialog("close");
			}
		},
		close: callback
	});
}

var render = function(vo, mode, idx) {
	// console.log("render idx : " + idx);
	var html = 		
		"<li data-no='" + idx + "'>" +
		"<strong>" + vo.name + "</strong>" +
		"<p>" + vo.contents + "</p>" +
		"<strong></strong>" +
		"<a href='#' data-no='" + vo.no + "'>삭제</a>" +
	    "</li>"
		    
	$("#list-guestbook")[mode ? 'prepend' : 'append'](html);
}
 
// var endVal = false;
var ajaxResult = null;
var sno = 0;
var fetch = function(sno) {
	console.log("$$$$$ fetch sno:" + sno);
	// if(endVal) return;
	
	if(ajaxResult != null) ajaxResult.abort();
	
	ajaxResult = $.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook?sno=" + sno,
		type: "get",
		dataType: "json",
		success: function(response) {
			// if sno가 guestbook list length 이상이면 
			/* if(sno >= 14) endVal = true;
			if(response.data.length < 3) endVal = true; */
			
			sno = response.sno;
			console.log("ajax-sno : " + response.sno);
			
			if(response.result === 'fail') {
				console.error(response.message);
				return;
			}
			
			response.data.forEach(function(vo, idx){
				// console.log(vo); 
				/* 
				console.log(response.data);
				
				*/
				console.log(idx);
				render(vo, false, idx+1);
			})
			
			var startNo = $("#list-guestbook li").last().data("no") + 1;
			console.log("last + 1", startNo);
			
			// 그 숫자가 sno가 된다 
		 	sno = startNo;
		}
	})
	
	
}

$(function(){
	// 스크롤이 페이지 최하단에 위치했는지를 확인
	// 이벤트를 통해 추가되는 콘텐츠가 비동기식(ajax)으로 하단에 추가
	// 스크롤이 계속 늘어나도록 

	var n = 5;
	$(function(){
		$(window).scroll(function(){ // 스크롤 변경시 이벤트 발생 
			// 조건(스크롤바가 바닥에 도착)이 되면 fetch() 호출
			var $window = $(this);
			var $document = $(document);
			
			var wh = $window.height(); // 현재 화면 높이 
			var dh = $document.height(); // 전체 문서의 높이 
			var st = $window.scrollTop(); // 현재 스크롤 위치 
			
			if(dh < wh + st + 10) {
				
				if(n < ${listLen}){
					console.log("fetch!!!");
					console.log("sno", sno);
					fetch(sno);
					/* fetch(n);
					n += 5; */
				}
			}
		})
		
		// 최초 리스트 가져오기
		// fetch();
	});
	
	$('#add-form').submit(function(event) {
		event.preventDefault();
		
		// 1. 이름 
		if($("#input-name").val() === ''){
			messageBox('방명록 추가', '이름은 필수 항목 입니다.', function(){
				$("#input-name").focus();
			});
			return;
		}
		
		// 2. 비밀번호  
		if($("#input-password").val() === ''){
			messageBox('방명록 추가', '비밀번호는 필수 항목 입니다.', function(){
				$("#input-password").focus();
			});
			return;
		}
		
		// 3. 방명록 내용 
		if($("#tx-content").val() === ''){
			messageBox('방명록 추가', '방명록 내용은 필수 항목 입니다.', function(){
				$("#tx-content").focus();
			});
			return;
		}
		
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
				
				$('#input-name').val("");		
				$('#input-password').val("");		
				$('#tx-content').val("");		
			}
		})
	});
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		width: 320,
		autoOpen: false,
		model: true,
		buttons: {
			"삭제": function() {
				var no = $('#hidden-no').val();
				var password = $('#password-delete').val();
				
				console.log("ajax 삭제~~", no, password);
				
				if(password == '') { // 패스워드를 입력하지 않았을 때 
					$('#password-delete').focus();
					return;
				}
				
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
							messageBox('방명록 삭제', response.message);
							
							return;
						} else{ // delete 성공 
							// 1. response.data(no) 가지고 있는 <li data+no='{no}' > 찾아서 삭제
							$("li[data-no='" + no + "']").remove();
						}
					}
				})
				
				// 2. dialogDelete.dialog('close');
				$(this).dialog('close');
				
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
	fetch(0);
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
	<div id="dialog" title="빈칸 체크" style="display:none;">
  		<p style="line-height: 60px;">빈칸이 있으면 방명록이 추가되지 않습니다.</p>
	</div>
</body>
</html>