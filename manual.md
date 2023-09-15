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



7. 상태에 따른 UI 변화

#### (방명록 관련 - guestbook)


#### (게시물 관련 - board)
2. 게시물 입력
3. 게시물 리스트
4. 게시물 삭제
5. 게시물 보기
6. 게시물 수정
