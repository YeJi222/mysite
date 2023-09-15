## Assignment03 - MySite(JSP & Servlet)
### [파일 위치]
- controller 파일 : mysite/mysite02/src/main/java/com/poscodx/mysite/controller
- dao 파일 : mysite/mysite02/src/main/java/com/poscodx/mysite/dao
- vo 파일 : mysite/mysite02/src/main/java/com/poscodx/mysite/vo
- jsp 파일 : mysite/mysite02/src/main/webapp/WEB-INF/views

### [MVC 적용]
1. 적절한 Model 사용(DAO, VO)
   - DAO : BoardDao, GuestBookDao, UserDao
   - VO : BoardVo, GuestBookVo, PageVo, UserVo   
     <img width="161" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/c785afae-bf11-42ee-9595-82703914c059">
2. Action 클래스 구현 여부   
   <img width="248" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/0641e6f1-77bf-4e30-9624-56ac4fbd058b">

### [구현 결과]
#### (회원정보 관련 - user)
1. 회원가입
   - 회원정보 등록 
   <img width="695" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/d491916c-091b-4016-a187-b6c658bc444b">
   
   - 회원가입 완료   
   <img width="698" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/c6c4a44b-c4a2-485b-aca0-c492c3c63b3b">
2. 로그인
   - 회원정보 등록 
   <img width="699" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/6ec30ab2-8e97-4238-b251-a64a40731ecb">

   - 패스워드 틀린 경우 
   <img width="696" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/aef0fc9d-de35-4d09-ba31-f01e27f63a3c">
   
3. 인증 유무에 따른 Navigation 및 Top 상태 변화
   - 로그인 전 Top   
   <img width="697" alt="스크린샷 2023-09-16 오전 2 09 43" src="https://github.com/YeJi222/mysite/assets/70511859/3e19dc92-a1d0-49de-9ce3-c008ed21ef90">

   - 로그인 후 
   <img width="696" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/e167ff31-8720-4899-b2fd-8115372ceeef">
4. 회원정보수정 - 수정 완료 후, 회원정보수정 페이지에 그대로 위치    
   <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/74a0f462-1d10-44b0-b18a-025106b08748">

#### (게시물 관련 - board)
1. 게시물 입력
   - 글쓰기 폼 
   <img width="698" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/35bf48de-cc35-4768-825a-7d7e1c135324">

   - 등록 버튼 클릭시, 게시글 등록 후 이전 게시판 페이지로 이동
   - 취소 버튼 클릭시, 이전 게시판 페이지로 이동 
   <img width="569" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/005683fb-ca52-473f-a6a0-fab3e3ea532f">
1-1. 답글 입력
   - 게시글 들어간 후, 답글 버튼 눌러 글쓰기 페이지로 이동
     <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/6ff34dcb-83c6-43e4-b6b7-c3daadef0942">
   - 취소 버튼 누르면, 이전 게시글 보기 페이지로 이동 
     <img width="696" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/dfdc682e-50c9-4191-8e58-b5adf086ee32">
   - 답글 작성 후, 등록 버튼 누르면 이전 게시글 페이지로 이동 후, 추가된 답글 확인 가능 
     <img width="578" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/2be1d81b-e999-43c9-a1d7-3906995d7f3e">

   - 답글 계층형으로 나타냄   
     <img width="569" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/0fa67d97-aa38-4b48-acd2-ec1970f3ece8">

2. 게시물 리스트
   - 로그인 전, 글쓰기 버튼 안보이게   
   <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/9e37df7d-acc9-4bff-a81e-79705e828a73">

   - 로그인 하면, 글쓰기 가능하도록   
     <img width="695" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/3be05e8e-7152-4f50-a3ac-fe23e2e4511f">

2-1. 페이징 처리 
   - 한 페이지 당, 8개 게시물 열람 가능
     <img width="572" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/db28e07f-f13c-4259-86b2-5fcd8dcbca0d">
   - 보여지는 페이지 개수는 5개씩, 이전 & 다음 페이지 버튼 눌러 이동 가능
   - 1페이지로 시작하는 경우, 이전 버튼 숨기기   
     <img width="162" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/650bd6e3-0ae9-41c6-9be4-e82deb50fc53">

   - 현재 페이지는 빨간색 표시, 보여줄 게시글이 없는 페이지는 연한 회색 표시
   - 페이지가 끝난 경우, 다음 버튼 숨기기     
     <img width="166" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/807f1710-80f4-444a-b30d-3a35fcc28b5d">


     





3. 게시물 삭제
   - 작성자 게시글에만 삭제 버튼 생성   
   <img width="563" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/0de9ca77-f294-4d41-a3a5-3b5e63f23e3c">

   - 삭제 버튼 누르면, 패스워드 확인 페이지로 이동
   - 패스워드가 일치하면 삭제 후, 이전 게시판 페이지로 이동
   - 패스워드가 틀리면, 현재 패스워드 확인 페이지 그대로
   - 게시판 리스트 버튼 누르면, 이전 게시판 페이지로 이동 
   <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/79da694d-8be4-423f-9f55-04b349d00bb1">

4. 게시물 보기
   - 게시물 제목 클릭하면, 게시물 보기 페이지로 이동
   - 자신의 게시글이 아닌 경우, 답글과 글목록 버튼만 보이게(수정 버튼 안보이게)
   - 글목록 버튼 누르면, 이전 게시판 페이지로 이동 
     <img width="695" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/0987c69a-b65c-4bac-9ed4-abfcf6b5f1d6">
  
   - 작성자의 게시글인 경우, 글수정 버튼도 보이게
     <img width="696" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/e058c522-3b05-48e7-b5d8-b4a6de591d47">

   - 게시글 보기 페이지 들어갈 때마다 조회수 + 1    
     <img width="76" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/f31204c1-5d6e-4fbd-9ac5-bba5ac0d3387">

5. 게시물 수정
   - 글수정 버튼을 누르면, 제목과 내용을 수정할 수 있다
     <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/edddac13-b8a4-4129-b653-a083b1c04e9d">

   - 수정 버튼을 누르면 게시물 보기 페이지로 이동 후 변경 사항 확인 가능 
     <img width="697" alt="image" src="https://github.com/YeJi222/mysite/assets/70511859/7e726a2b-2898-4218-be80-0d0c07186077">











#### (방명록 관련 - guestbook)
