# Application Context 정리 
#### '2023/09/22/금 과제 - ApplicationContext로 SiteVo 상태 관리 하기'와 연관

### [Task]
- admin page에서 수정한 title을 main, guestbook, board page에서 동적으로 반영되도록 할 것이다
- title 외에 welcome, profile, description도 반영해주어 main page에서 바로 반영되도록 한다
- 이를 위해, SiteInterceptor를 만들어 preHandle 메소드에서 SiteVo 객체를 빈으로 등록하고, 이를 request 속성에 저장할 것이다
- 이렇게 저장된 siteVo는 jsp에서 불러다 쓸 수 있다
- AdminController에서 SiteVo 객체를 update 한 후에, 해당 정보를 통해 등록한 빈 또한 변경해준다 

### [ApplicationContext란?]
- 빈들의 생성과 의존성 주입 등의 역할을 하며, 해당 애플리케이션에 대한 구성정보를 제공하는 인터페이스
- 애플리케이션에 대한 구성정보 - 빈을 등록하고, 빈 정보를 가져오고, 등록된 빈 정보를 수정할 수 있다
- 
