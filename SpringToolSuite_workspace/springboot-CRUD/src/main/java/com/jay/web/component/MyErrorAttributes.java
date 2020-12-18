package com.jay.web.component;

import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

/**
 * 給容器中加入自己定義的元件 MyErrorAttributes
 * */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes{

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {

		Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
		/* MyExceptionHandler 攜帶的 Map */
		Map<String, Object> handlerMap = (Map<String, Object>) request.getAttribute("handlerMap", 0);

		map.put("componentMap", "test.test.error");
		map.put("handlerMap", handlerMap);
		return map;
	}
}
