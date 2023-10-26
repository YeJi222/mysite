<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<link href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css">
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
$(function(){
	$('#join-form').submit(function(event){
		event.preventDefault();
		
		// 1. 이름 
		if($("#name").val() === ''){
			messageBox('회원가입', '이름은 필수 항목 입니다.', function(){
				$("#name").focus();
			});
			return;
		}
		
		// 2. 이메일  
		if($("#email").val() === ''){
			messageBox('회원가입', '이메일은 필수 항목 입니다.', function(){
				$("#email").focus();
			});
			return;
		}
		
		// 3. 이메일 중복 체크 
		if(!$("#img-check-email").is(':visible')){
			messageBox('회원가입', '이메일 중복 확인을 해주세요.');
			return;
		}
		
		// 4. 비밀번호  
		if($("#password").val() === ''){
			messageBox('회원가입', '비밀번호는 필수 항목 입니다.', function(){
				$("#password").focus();
			});
			return;
		}
		
		// 5. ok
		this.submit();
	})
	
	$("#email").change(function(){
		console.log("change");
		
		$('#img-check-email').hide();
		$('#btn-check-email').show();
	})
	
	$('#btn-check-email').click(function() {
		var email = $('#email').val(); // 이메일 입력하는 값 
		if(email === '') {
			return;
		}

		$.ajax({
			url: '${pageContext.request.contextPath }/api/user?email=' + email,
			type: 'get',
			dataType: 'json',
			success: function(response) {
				if(response.result !== 'success') {
					console.error(response.message);
					return;
				}
				
				// response.data
				// if 이메일 중복이 아니면 response.data는 null
				// 이메일 중복이면 객체로 받아옴 
				// console.log(response.data);
				
				if(response.data) {
					$("#dialog").dialog({
						width: 340,
						modal: true,
						buttons: {
							"확인": function(){
								// $(this) : dialog element 
								$(this).dialog("close");
								
							}
						},
						close: function(){
							$("#email").val('').focus();
						}
					});
					
					return;
				}
				
				$('#img-check-email').show();
				$('#btn-check-email').hide();
				
				// dialog 띄워진 상태에서 중복되지 않은 이메일을 확인했을 때
				// 띄워져있는 dialog 안보이게 
				$('.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.ui-draggable.ui-resizable').hide();

			},
			error: function(xhr, status, e) {
				console.error(status, e);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form
					modelAttribute="userVo" 
					id="join-form"
					name="joinForm"
					method="post"
					action="${pageContext.request.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<p style="padding:3px 0 5px 0; text-align: left; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<!--
								${errors.getFieldError("name").defaultMessage }
								-->
								<spring:message code='${errors.getFieldError("name").codes[0]}' />
							</c:if> 
						</spring:hasBindErrors>
					</p>
					
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />

					<input id='btn-check-email' type="button" value="중복체크">
					<img id='img-check-email' src='${pageContext.request.contextPath }/assets/images/check.png' style='width: 16px; vertical-align: middle; display:none'>
					
					<p style="padding:3px 0 5px 0; text-align: left; color: #f00">
						<form:errors path="email" />
					</p>
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="padding:3px 0 5px 0; text-align: left; color: #f00">
						<form:errors path="password" />
					</p>
					
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여" checked="checked" />
						<form:radiobutton path="gender" value="male" label="남" checked="checked" />
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	<div id="dialog" title="이메일 중복 체크" style="display:none;">
  		<p style="line-height: 60px;">사용중인 이메일입니다. 다른 이메일을 사용해 주세요.</p>
	</div>
</body>
</html>