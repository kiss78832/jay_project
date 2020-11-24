package com.jay.web.component;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 可以在連結上攜帶區域訊息
 * */
public class MyLocaleResolver implements LocaleResolver{

	@Override
	public Locale resolveLocale(HttpServletRequest request) {

		//取得 ? 後的"l"參數值。ex:l='zh_TW'
		String l = request.getParameter("l");
		//預設頁面取得 Header 內的參數
		Locale locale = Locale.getDefault();
		//進行資料分割取得 'zh_TW' 。
		if(!StringUtils.isEmpty(l)) {
			String[] split = l.split("_");
			locale = new Locale(split[0],split[1]);
		}
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub

	}

}
