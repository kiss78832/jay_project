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
 * ��@�Ȥ��Υ[value={"user"}�A�����guser�Y�i�A�q�{�ȬOvalue�A�[�o�h�~��QsessionScope���C
 * types -> �i�H���������C
 * 
 */
//@SessionAttributes(value={"user"},types={String.class})
@RequestMapping("/springmvc02")
@Controller
public class ModelAndView_Test {
	
	private static final String PAGE = "modelAndView_page";
	
	/*********************--ModelAndView�d��(�B�z�ҫ��ƾڲĤ@�ؤ覡)--***********************/
	/*
	 *	�ؼФ�k����^�ȥi�H�OModelAndView�����C
	 * 	�䤤�i�H�]�t���ϩM�ҫ��T��
	 * 	SpringMVC �|��ModelAndView ��model���ƾک��Request���H���C
	 */
	@RequestMapping("/modelAndView")
	public ModelAndView modelAndView() {
		String viewName = PAGE;
		ModelAndView M_V_U = new ModelAndView(viewName);
		
		//	�K�[�ҫ��ƾ�ModelAndView���Atime�O�n�������ϥΡAex:${requestScope.time}
		M_V_U.addObject("time",new Date());
		return M_V_U;
	}
	
	/*********************--Map�d��(�B�z�ҫ��ƾڲĤG�ؤ覡)--***********************/
	/*
	 *	�ؼФ�k�i�H�K�[Map����(��ڤW�]�i�H�OModel�����άOModelMap����)���Ѽ�
	 *	Object->AbstractMap->HashMap->LinkedHashMap->ModelMap->ExtendedModelMap->BindingAwareModelMap
	 *	�F�ѧY�i�A�D�n�N�O�ǤJMap�����AMap�񪺼ƾڤ]�|�নModelAndView
	 */
	@RequestMapping("/mapParams")
	public String mapParams(Map<String,Object> map) {
		System.out.println("��ڶǤJ����k: "+map.getClass().getName());
		map.put("name", Arrays.asList("Tom","Jerry","Mike"));
		return PAGE;
	}
	
	/*********************--SessionAttributes�d��--***********************/
	/*
	 *	@SeesionAttributes 
	 *		a.�i�H�q�L�ݩʦW��(user)���w�ݭn�Ȫ���b������(��ڤW��Value�ݩʭ�)�C
	 *		b.�]�i�H�q�L�ҫ��ݩʪ�����������b������(��ڤW��Types�ݩʭ�)
	 *
	 *	�d��Number�N������A�]��@SeesionAttribute��types�]�m�OString����
	 *	�`�N:��annotation�u���b���O�W���C
	 */
	@RequestMapping("/sessionAttributes")
	public String sessionAttributes(Map<String,Object> map) {
		User user = new User("Jay","jay's@gmail.com",29);
		map.put("user", user);
		map.put("school", "SpringMVC");
		map.put("number", 123);
		return PAGE;
	}
	
	/*********************--@ModelAttribute�d��--***********************/
	/**
	 * 	SpringMVC�T�{�ؼФ�kPOJO�����J�ѹL�{:
	 * 		1.�T�w�@��key[user]:
	 * 			a.�Y�ؼФ�k��POJO����[User����]�ѼƦ��ϥ�@ModelAttribute�@���׹��A�hkey��POJO���O�Ĥ@�Ӧr�����p�g�C
	 * 			b.�Y�ϥ�@ModelAttribute�ӭ׹��A�hkey��@ModelAttribute���Ѫ�value�ݩʭ�[value="user"]�C
	 * 
	 * 		2.�bimplicitModel���d��key��������H�A�Y�s�b�A�h���ǤJ�ѼơC
	 * 			a.�Y�b@ModelAttribute�аO����k���bMap�O�s�L�A�Bkey�M������key�ۦP�A�h�|�����C
	 * 
	 * 		3.�YimplicitModel[Map�N�OBindingAwareModelMap;implicitModel�N�OBindingAwareModelMap]���s�bkey��H���ܡA
	 * 			�h�ˬd��eHandler�O�_�ϥ�@SessionAttributes�ӭ׹��A�Y�ϥθӵ��ѡA�B@SessionAttributes���Ѥ���value�]�t�Fkey�A
	 * 				�h�|�qHttpSession�����key�ҹ�����value�A�Y�s�b�h�����ǤJ�ؼФ�k�����ѼơA�Y���s�b�h�|�o�Ϳ��~�C
	 * 					[�Y@SessionAttributes��value�]�O"user"���ܡA�|�j��h��@SessionAttributes����user������types={String.class}�A
	 * 						�]�����OUser�����N���Ϳ��~�C�B�z��k(1).�s�W@ModelAttribute�b��k�W�A�άO�b�Ѽƫe[�O�ovalue��@SessionAttributes���P�Y�i]ex:(@ModelAttribute("test") User user1) ]
	 * 		
	 * 		4.�YHandler�S���Х�@SessionAttributes���ѩ�@SessionAttributes���Ѫ�value�Ȥ����]�tkey�A�h�|�q�L�Ϯg�ӳЫ�POJO�����ѼơA�ǤJ�ؼФ�k�ѼơC
	 * 		5.SpringMVC�|��key�MPOJO����������O�s�bimplicitModel���A�i�ӷ|�O�s�brequest���C
	 * 
	 * */
	/*
	 *	��@ModelAttribute �аO��k�A�|�b�C�ӥؼФ�k����e�QSpringMVC�ե� �C
	 *	�]�N�O�Ҧ���k����ɳ��|�q�L�Q@ModelAttribute�аO����k�C
	 */
	@RequestMapping("/modelAttribute")
//	public String modelAttribute(@ModelAttribute("test") User user1) {
	public String modelAttribute( User user1) {
		System.out.println("�ק�: "+user1);
		return PAGE;
	}
	
	/*
	 * �D��:�t�m�ק�򥻸�ƮɡA�N��S�ק�K�X�]�|�����(�|�O����Ʈw�ɪ����)�C
	 * 	���ѮĪG:�|�o�{�bpassword�Onull�A�]�N�O�@�ӳзs������A�u�קﭶ����즳����ơC
	 * 	�����ѮĪG:�|�o�{password�O���q��Ʈw��������ơA�ήɨS��s�]�|����ơA�]���O����Ʈw��ƦA�ק�C
	 * 
	 * 	�B�@�y�{:
	 * 		1.����@ModelAttribute���ѭ׹�����k�A�q��Ʈw�����Xuser����A���H��J��FMap���A���:user
	 * 		2.SpringMVC�qMap�����XUser����A�ç��檺�ШD�Ѽƽᤩ��User���󪺹����ȡC
	 * 		3.SpringMVC��W�z��H�ǤJ�ؼФ�k���ѼơC
	 * 
	 * 	�`��:�����ǰeinput������ƨ��� -> ��ݱ����ѼơA������Q@ModelAttribute�аO����k ->�P�_����name=username���ȬO�_����
	 *	 		->�����Ũ��o������Ʈw��� -> �A��user��ƶ�imap���X�� ��Ȭ�user->�A���歶�����|����kmodelAttribute(User user)
	 * 				->��modelAttribute(Map������Key.value�n�����n)��k�⭶���Ѽƭק��Map����User����(SpringMVC��@��)->
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="username",required=false)String username,
					Map<String,Object> map) {
		System.out.println("�i��@ModelAttribute�аO����k");
		if(username!=null) {
			//	������Ʈw
			User user = new User("Jay","1234567890","jay@gmail.com",28);
			System.out.println("�q��Ʈw�������: "+user);
			
			//key.value�ȭn����modelAttribute(User[key�������O�ç�p�g�Avalue�@�w�n�OUser���O] user[�o�䤣�v�T])
//			map.put("user",user);
			
			
			//	�Y�w�n����ȧ�test�A�n�p����ۭq��ȩO?
			//	Ans:�b@RequestMapping��modelAttribute(@ModelAttribute("test") User user1) ->�[�W@ModelAttribute
			map.put("test",user);
		}
	}
	
	
	/*********************--InternalResourceViewResolver���Ͻd��(�w�t�mxml)--***********************/
	
	@RequestMapping("/internalResourceViewResolver")
	public String internalResourceViewResolver() {
		System.out.println("internalResourceViewResolver�e��ݶǻ��L�{����");
		return PAGE;
	}
	
	/*********************--BeanNameViewResolver���Ͻd��(�w�t�mxml)--***********************/
	
	@RequestMapping("/BeanNameViewResolver")
	public String testBeanNameViewResolver() {
		System.out.println("BeanNameViewResolver�d��");
		return "helloView";
	}
	
	/*********************--Redirect����d��--***********************/
	@RequestMapping("/redirect")
	public String redirect() {
		System.out.println("Redirect ���୶��");
		return "redirect:/index.jsp";
	}
	
	/*********************--forward����d��--***********************/
	@RequestMapping("/forward")
	public String forward() {
		System.out.println("Forward ������o����");
		return "forward:/index.jsp";
	}
}
