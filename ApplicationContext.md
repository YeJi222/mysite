# Application Context 정리 
#### '2023/09/22/금 과제 - ApplicationContext로 SiteVo 상태 관리 하기'와 연관
### 🖥️ [과제 코드로 이해하기](#[과제 코드로 이해하기])

### [Task]
- admin page에서 수정한 title을 main, guestbook, board page에서 동적으로 반영되도록 할 것이다
- title 외에 welcome, profile, description도 반영해주어 main page에서 바로 반영되도록 한다
- 이를 위해, SiteInterceptor를 만들어 preHandle 메소드에서 SiteVo 객체를 빈으로 등록하고, 이를 request 속성에 저장할 것이다
- 이렇게 저장된 siteVo는 jsp에서 불러다 쓸 수 있다
- AdminController에서 SiteVo 객체를 update 한 후에, 해당 정보를 통해 등록한 빈 또한 변경해준다 

### [ApplicationContext란?]
- 빈들의 생성과 의존성 주입 등의 역할을 하며, 해당 애플리케이션에 대한 구성정보를 제공하는 인터페이스
- 애플리케이션에 대한 구성정보 - 빈을 등록하고, 빈 정보를 가져오고, 등록된 빈 정보를 수정할 수 있다
- ApplicationContext 상위에 빈들을 생성하는 BeanFactory 인터페이스를 부모로 상속받고 있다 -> 빈 등록과 관련

### [Interceptor]
- Interceptor를 추가하려면, spring-servlet.xml에서 설정할 수 있다
- Interceptor 메소드의 프로세스 과정
```text
1. preHandler 메소드 실행 
2. 요청 처리 
3. postHandler 메소드 실행 
4. View 렌더링 
5. afterCompletion 메소드 실행 
```
- preHandle 메소드 : Controller로 보내기 전에 처리하는 인터셉터, 반환이 false라면 controller로 요청을 하지 않는다
- postHandle 메소드 : Contrller의 handler가 끝나면 처리가 되고, view 렌더링 전에 처리되는 메소드이다
- afterCompletion : view 렌더링까지 처리가 끝난 후에 실행되는 메소드이다 

### [빈 동적 등록]
- 빈 등록을 위해 applicationContext를 주입받는다
```java
@Autowired
ApplicationContext applicationContext;
```
- beanFactory의 registerSingleton 메소드를 통해 Object를 등록할 수 있다
- 그런데, applicationContext의 getBeanFactory() 메소드를 이용하려면 ConfigurableApplicationContext의 Casting이 필요하다
- 다음과 같이 registerSingleton 메소드를 사용하여 "siteVo"라는 빈 이름으로, SiteVo의 객체를 등록할 수 있다
```java
ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) applicationContext;
appContext.getBeanFactory().registerSingleton("siteVo", siteVo);
```
- 주의 : 빈 중복 등록이 되면 에러가 발생

### [cf. - 빈 삭제]
- getBeanFactory().destroySingleton(등록된 빈 이름); 메소드로 빈 삭제 가능 

