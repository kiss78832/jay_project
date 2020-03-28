package com.jay.springmvc.handlers;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jay.springmvc.pojo.User;

/*
 * 單一值不用加value={"user"}，直接寫user即可，默認值是value，加這層才能被sessionScope抓到。
 * types -> 可以限制類型。
 * 
 */
//@SessionAttributes(value={"user"},types={String.class})
@RequestMapping("/springmvc02")
@Controller
public class ModelAndView_Test {
	
	private static final String PAGE = "modelAndView_page";
	
	/*********************--ModelAndView範例(處理模型數據第一種方式)--***********************/
	/*
	 *	目標方法的返回值可以是ModelAndView類型。
	 * 	其中可以包含視圖和模型訊息
	 * 	SpringMVC 會把ModelAndView 的model中數據放到Request域對象中。
	 */
	@RequestMapping("/modelAndView")
	public ModelAndView modelAndView() {
		String viewName = PAGE;
		ModelAndView M_V_U = new ModelAndView(viewName);
		
		//	添加模型數據ModelAndView中，time是要給頁面使用，ex:${requestScope.time}
		M_V_U.addObject("time",new Date());
		return M_V_U;
	}
	
	/*********************--Map範例(處理模型數據第二種方式)--***********************/
	/*
	 *	目標方法可以添加Map類型(實際上也可以是Model類型或是ModelMap類型)的參數
	 *	Object->AbstractMap->HashMap->LinkedHashMap->ModelMap->ExtendedModelMap->BindingAwareModelMap
	 *	了解即可，主要就是傳入Map類型，Map放的數據也會轉成ModelAndView
	 */
	@RequestMapping("/mapParams")
	public String mapParams(Map<String,Object> map) {
		System.out.println("實際傳入的方法: "+map.getClass().getName());
		map.put("name", Arrays.asList("Tom","Jerry","Mike"));
		return PAGE;
	}
	
	/*********************--SessionAttributes範例--***********************/
	/*
	 *	@SeesionAttributes 
	 *		a.可以通過屬性名稱(user)指定需要值的放在頁面中(實際上指Value屬性值)。
	 *		b.也可以通過模型屬性的物件類型放在頁面中(實際上指Types屬性值)
	 *
	 *	範例Number就取不到，因為@SeesionAttribute的types設置是String類型
	 *	注意:該annotation只能放在類別上面。
	 */
	@RequestMapping("/sessionAttributes")
	public String sessionAttributes(Map<String,Object> map) {
		User user = new User("Jay","jay's@gmail.com",29);
		map.put("user", user);
		map.put("school", "SpringMVC");
		map.put("number", 123);
		return PAGE;
	}
	
	/*********************--@ModelAttribute範例--***********************/
	/**
	 * 	SpringMVC確認目標方法POJO類型入參過程:
	 * 		1.確定一個key[user]:
	 * 			a.若目標方法的POJO類型[User類型]參數有使用@ModelAttribute作為修飾，則key為POJO類別第一個字母的小寫。
	 * 			b.若使用@ModelAttribute來修飾，則key為@ModelAttribute註解的value屬性值[value="user"]。
	 * 
	 * 		2.在implicitModel中查找key對應的對象，若存在，則為傳入參數。
	 * 			a.若在@ModelAttribute標記的方法中在Map保存過，且key和對應的key相同，則會獲取到。
	 * 
	 * 		3.若implicitModel[Map就是BindingAwareModelMap;implicitModel就是BindingAwareModelMap]不存在key對象的話，
	 * 			則檢查當前Handler是否使用@SessionAttributes來修飾，若使用該註解，且@SessionAttributes註解中的value包含了key，
	 * 				則會從HttpSession中獲取key所對應的value，若存在則直接傳入目標方法中的參數，若不存在則會發生錯誤。
	 * 					[若@SessionAttributes的value也是"user"的話，會強制去取@SessionAttributes那個user對應的types={String.class}，
	 * 						因為不是User類型就產生錯誤。處理辦法(1).新增@ModelAttribute在方法上，或是在參數前[記得value跟@SessionAttributes不同即可]ex:(@ModelAttribute("test") User user1) ]
	 * 		
	 * 		4.若Handler沒有標示@SessionAttributes註解或@SessionAttributes註解的value值中部包含key，則會通過反射來創建POJO類型參數，傳入目標方法參數。
	 * 		5.SpringMVC會把key和POJO類型的物件保存在implicitModel中，進而會保存在request中。
	 * 
	 * */
	/*
	 *	有@ModelAttribute 標記方法，會在每個目標方法執行前被SpringMVC調用 。
	 *	也就是所有方法執行時都會通過被@ModelAttribute標記的方法。
	 */
	@RequestMapping("/modelAttribute")
//	public String modelAttribute(@ModelAttribute("test") User user1) {
	public String modelAttribute( User user1) {
		System.out.println("修改: "+user1);
		return PAGE;
	}
	
	/*
	 * 題目:演練修改基本資料時，就算沒修改密碼也會有資料(會是取資料庫時的資料)。
	 * 	註解效果:會發現在password是null，也就是一個創新的物件，只修改頁面欄位有的資料。
	 * 	未註解效果:會發現password是當初從資料庫撈取的資料，及時沒更新也會有資料，因為是取資料庫資料再修改。
	 * 
	 * 	運作流程:
	 * 		1.執行@ModelAttribute註解修飾的方法，從資料庫中取出user物件，把對象放入到了Map中，鍵值:user
	 * 		2.SpringMVC從Map中取出User物件，並把表單的請求參數賦予該User物件的對應值。
	 * 		3.SpringMVC把上述對象傳入目標方法的參數。
	 * 
	 * 	總結:頁面傳送input內的資料到後端 -> 後端接收參數，先執行被@ModelAttribute標記的方法 ->判斷頁面name=username的值是否為空
	 *	 		->不為空取得模擬資料庫資料 -> 再把user資料塞進map集合裡 鍵值為user->再執行頁面路徑的方法modelAttribute(User user)
	 * 				->到modelAttribute(Map對應的Key.value要對應好)方法把頁面參數修改到Map拿到User物件(SpringMVC實作的)->
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="username",required=false)String username,
					Map<String,Object> map) {
		System.out.println("進來@ModelAttribute標記的方法");
		if(username!=null) {
			//	模擬資料庫
			User user = new User("Jay","1234567890","jay@gmail.com",28);
			System.out.println("從資料庫獲取物件: "+user);
			
			//key.value值要對應modelAttribute(User[key對應類別並改小寫，value一定要是User類別] user[這邊不影響])
//			map.put("user",user);
			
			
			//	若硬要把鍵值改test，要如何找到自訂鍵值呢?
			//	Ans:在@RequestMapping的modelAttribute(@ModelAttribute("test") User user1) ->加上@ModelAttribute
			map.put("test",user);
		}
	}
	
	
	/*********************--InternalResourceViewResolver視圖範例(已配置xml)--***********************/
	
	@RequestMapping("/internalResourceViewResolver")
	public String internalResourceViewResolver() {
		System.out.println("internalResourceViewResolver前後端傳遞過程講解");
		return PAGE;
	}
	
	/*********************--BeanNameViewResolver視圖範例(已配置xml)--***********************/
	
	@RequestMapping("/BeanNameViewResolver")
	public String testBeanNameViewResolver() {
		System.out.println("BeanNameViewResolver範例");
		return "helloView";
	}
	
	/*********************--Redirect跳轉範例--***********************/
	@RequestMapping("/redirect")
	public String redirect() {
		System.out.println("Redirect 跳轉頁面");
		return "redirect:/index.jsp";
	}
	
	/*********************--forward跳轉範例--***********************/
	@RequestMapping("/forward")
	public String forward() {
		System.out.println("Forward 內部轉發頁面");
		return "forward:/index.jsp";
	}
}
