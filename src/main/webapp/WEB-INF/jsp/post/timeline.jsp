<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>타임라인</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<link rel="stylesheet" href="/static/css/style.css" type="text/css">
<body>

	<div class="container">
		<c:import url="/WEB-INF/jsp/include/header.jsp" />
		<section class="contents d-flex justify-content-center">
			<!--  타임 라인  -->
			<div class="timeline">
				<!-- 입력 박스 -->
				<div class="input-box border rounded">
					<textarea rows="4" class="form-control border-0" id="contentInput"></textarea>
					<div class="d-flex justify-content-between mx-2 mb-2">
						<i id="imageIcon" class="bi bi-card-image image-icon-size"></i>
						<input type="file" id="fileInput" class="d-none">
						<button type="button" class="btn btn-info btn-sm" id="uploadBtn" >업로드</button>
					</div>
				</div>
				<!-- / 입력박스 -->
				
				<!-- 게시글 카드 리스트 -->
				<div class="card-list mt-4">
					<c:forEach var="post" items="${postList }" >
					<!--  게시글 카드  -->
					<div class="card mt-3">
						<div class="d-flex justify-content-between p-2">
							<div>${post.loginId }</div>
							<div><i class="bi bi-three-dots"></i></div>
						</div>
						<div>
							<img width="100%" src="${post.imagePath }">
						</div>
						<div class="p-2">
							<i class="bi bi-heart like-icon" data-post-id="${post.id }"></i> 
							좋아요 ${post.likeCount }개
						</div>
						<div class="p-2">
							<b>${post.loginId }</b> ${post.content }
						</div>
						
						<!--  댓글 박스 -->
						<div class="small">
							<div class="p-2">댓글</div>
							
							<div class="px-2"><b>hagulu</b> 진짜 예쁘네요</div>
							<div class="px-2"><b>bada</b> 저도 가보고 싶어요</div>
							
							<div class="d-flex mt-2">
								<input type="text" class="form-control">
								<button type="button" class="btn btn-info">게시</button>
							</div>
						</div>
						<!-- / 댓글 박스 -->
					</div>
					<!--  / 게시글 카드 -->
					
					</c:forEach>
					
				</div>
				<!-- / 게시글 카드 리스트 -->
			
			</div>
			<!-- / 타임 라인  -->
		</section>
		<c:import url="/WEB-INF/jsp/include/footer.jsp" />
	
	
	</div>
	
	<script>
		$(document).ready(function() {
			
			$(".like-icon").on("click", function() {
				
				let postId = $(this).data("post-id");
				
				
				$.ajax({
					type:"get"
					, url:"/post/like"
					, data:{"postId":postId}
					, success:function(data) {
						
						if(data.result == "success") {
							location.reload();
						} else {
							alert("좋아요 실패");
						}
					}
					, error:function() {
						alert("좋아요 에러");
					}
				});
				
				
			});
			
			$("#imageIcon").on("click", function() {
				// file input을 클릭한 동작을 수행한다. 
				$("#fileInput").click();
			});
			
			$("#uploadBtn").on("click", function() {
				let content = $("#contentInput").val();
				let file = $("#fileInput")[0];
				
				if(content == "") {
					alert("내용을 입력하세요");
					return;
				}
				
				// 파일이 선택되지 않았을 경우의 유효성 검사
				if(file.files.length == 0) {
					alert("파일을 선택하세요");
					return ;
				}
				
				var formData = new FormData();
				formData.append("content", content);
				formData.append("file", file.files[0]);
				
				$.ajax({
					type:"post"
					, url:"/post/create"
					, data:formData
					, enctype:"multipart/form-data"
					, processData:false
					, contentType:false
					, success:function(data) {
						if(data.result == "success") {
							location.reload();
						} else {
							alert("업로드 실패");
						}
						
					}
					, error:function() {
						alert("업로드 에러")
					}
				});
				
				
				
			});
		});
	
	</script>
	
</body>
</html>