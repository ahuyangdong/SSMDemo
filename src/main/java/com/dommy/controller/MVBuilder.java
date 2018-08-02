package com.dommy.controller;

import org.springframework.web.servlet.ModelAndView;

/**
 * 视图模型构造器
 */
public class MVBuilder {
	private ModelAndView mv;

	public MVBuilder() {
		mv = new ModelAndView();
	}

	/**
	 * 添加界面参数
	 * @param attributeName 参数名称
	 * @param attributeValue 参数值
	 */
	public void addAttribute(String attributeName, Object attributeValue) {
		mv.addObject(attributeName, attributeValue);
	}

	/**
	 * 获取模型视图
	 * @return ModelAndView
	 */
	public ModelAndView getMV() {
		return mv;
	}
}
