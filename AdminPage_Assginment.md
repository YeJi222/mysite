## ApplicationContext를 활용한 SiteVo 상태 관리 과제 - 코드로 이해하기

### [SiteInterceptor]
- 우선, 브라우저로 api 요청을 할 때, RequestMapping이 이루어지므로, Controller가 실행되기 전, SiteVo 오브젝트를 빈으로 등록해야 한다
- 등록한 빈을 가져와 request 속성에 저장하는 등의 역할을 SiteInterceptor에서 preHandle 메소드를 통해 Controller가 동작하기 전, 처리를 한다

#### (전체 코드) 
```java
public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	ApplicationContext applicationContext; // applicationContext 주입받기 
	
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("site interceptor preHandle...");
		
		SiteVo siteVo = siteService.getSite();
		
		ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) applicationContext;
		
		if (!applicationContext.containsBean("siteVo")) { // 빈 등록 한 번만 실행 
			appContext.getBeanFactory().registerSingleton("siteVo", siteVo);
		}
		
		// 빈이 정상적으로 등록되었는지 확인
    SiteVo registeredSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
    System.out.println("Site Interceptor registeredSiteVo : " + registeredSiteVo);
		
		// 가져온 SiteVo를 request 속성에 저장 
    request.setAttribute("siteVo", registeredSiteVo);

    return true;
	}
}
```
- SiteInterceptor를 사용하려면, spring-servlet.xml 파일 mvc:interceptors></mvc:interceptors> 태그 사이에 추가해주어야 한다
```xml
<mvc:interceptor>
  <mvc:mapping path="/**"/>
  <mvc:exclude-mapping path="/assets/**"/>
  <bean class="com.poscodx.mysite.security.SiteInterceptor" />
</mvc:interceptor>
```
- spring-servlet.xml파일에 추가한 후, 톰캣 실행시켜 System.out.println("site interceptor preHandle...");를 테스트해보니, 잘 나온 것을 확인할 수 있었다
- SiteService를 통해 SiteVo 객체의 정보를 가져오고 이 것을 빈으로 등록을 해주려고 한다
- 빈 등록을 할 때, getBeanFactory().registerSingleton() 메소드가 필요하고, getBeanFactory를 사용하려면 ConfigurableApplicationContext가 필요하다
- <b>(에러 주의)</b> 또한, 웹 애플리케이션에서 매 요청마다 preHandle 메서드가 실행되기 때문에 매 요청마다 registerSingleton이 호출되고 빈이 중복으로 등록되는 문제가 발생하므로, 빈 등록을 한 번만 실행하도록, Bean에 siteVo 빈이 없는 경우에만 registerSingleton 메소드가 호출되도록 한다 
```java
ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) applicationContext;
		
if (!applicationContext.containsBean("siteVo")) { // 빈 등록 한 번만 실행 
  appContext.getBeanFactory().registerSingleton("siteVo", siteVo);
}
```
- 빈이 잘 등록되었는지 확인해보고, 등록된 siteVo 빈을 request 속성에 set한다.
- 이것은 Controller에서 jsp들을 return해주는데, 리턴된 jsp에서 ${siteVo.title} 형태로 사용된다
- return true를 반환해야, controller로 요청을 한다 
```java
// 빈이 정상적으로 등록되었는지 확인
SiteVo registeredSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
System.out.println("Site Interceptor registeredSiteVo : " + registeredSiteVo);

// 가져온 SiteVo를 request 속성에 저장 
request.setAttribute("siteVo", registeredSiteVo);

return true; // 반환이 false이면, controller로 요청하지 않음 
```

### [Controller]
- 원래 코드를 보면, SiteService를 통해 SiteVo 객체를 불러오고, 이것을 model 속성에 추가한 후, jsp를 리턴해준다
```java
// MainController

@Controller
public class MainController {
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("")
	public String index(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		
		return "main/index";
	}
}
```
- ApplicationContext를 통한 빈 등록을 사용하면, 코드가 아래와 같이 매우 간결해진다
```java
// MainController

@Controller
public class MainController {
	@RequestMapping("")
	public String index() {
		return "main/index";
	}
}
```
- GuestbookController와 BoardController도 마찬가지이다
```java
// GuestbookController

@RequestMapping("")
public String main(Model model) {
	List<GuestbookVo> list = guestbookService.getContentsList();
	model.addAttribute("list", list);
	
return "guestbook/main";
}
```
```java
// BoardController

@RequestMapping("")
public String index(Model model,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
	
	Map<String, Object> map = boardService.getContentsList(page, keyword);
	
	model.addAttribute("map", map);
	model.addAttribute("keyword", keyword);
	
	return "board/index";
}
```

### [AdminController]
- AdminController에서 admin page 수정한 경우, SiteVo가 변경되고, 이것을 동적으로 반영해주어야 하므로, 등록한 빈을 불러와서 update 해주는 과정이 필요하다
- 먼저 ApplicationContext를 주입받고, 이를 사용하여 getBean 메소드를 사용하면 등록한 빈을 가져올 수 있다
```java
@Autowired
private ApplicationContext applicationContext;

...

SiteVo existingSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
```
- admin page에서 변경 버튼을 누르면, siteService의 updateSite 메소드를 통해 SiteVo 객체를 변경해준다
```java
siteService.updateSite(vo);
```
- 변경된 SiteVo 객체를 통해 불러온 빈 'existingSiteVo'의 title, welcome, profile, description을 Set 해준다
- 등록한 빈을 update 해주었으므로, Main page, Guestbook page, Board page 모두 변경한 Title로 적용된 것을 확인할 수 있다 
```java
existingSiteVo.setTitle(vo.getTitle());
existingSiteVo.setWelcome(vo.getWelcome());
existingSiteVo.setProfile(vo.getProfile());
existingSiteVo.setDescription(vo.getDescription());
```

#### (전체 코드) 
```java
@Autowired
private ApplicationContext applicationContext;

...

@RequestMapping("/main/update")
public String update(SiteVo vo, @RequestParam("file") MultipartFile file) {
	// 빈 불러오기 
	SiteVo existingSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
	
	/* 이미지 파일 업로드 처리 */
	String url = fileUploadService.restore(file);

	// siteVo profile 셋 해주기 
	if(url == null) { // before url로 세팅 
		url = vo.getProfile();
	}
	vo.setProfile(url);
	System.out.println("admin controller vo" + vo);
	
	siteService.updateSite(vo);
	existingSiteVo.setTitle(vo.getTitle());
	existingSiteVo.setWelcome(vo.getWelcome());
	existingSiteVo.setProfile(vo.getProfile());
	existingSiteVo.setDescription(vo.getDescription());
	
	return "redirect:/admin";
}
```

### [header.jsp]
- jsp에서 ${siteVo.title}로 불러올 수 있다 
```jsp
<h1>
	<a style="text-decoration: none; color: #79B30B" href="${pageContext.request.contextPath }/">
		${siteVo.title}
	</a>
</h1>
```
