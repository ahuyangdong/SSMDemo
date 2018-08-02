package com.dommy.controller;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * 交互层基类
 */
public class BaseController {
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";

	@Resource
	private HttpServletRequest request;

	/**
	 * 获取模型视图构造器
	 * @return MVBuilder 模型视图构造器
	 */
	protected MVBuilder getMVBuilder() {
		return new MVBuilder();
	}

	/**
	 * 跳转到指定页面
	 * @param viewName 视图名称
	 * @param mvb 视图构造器
	 * @return ModelAndView 模型视图对象
	 */
	protected ModelAndView forward(String viewName, MVBuilder mvb) {
		mvb.getMV().setViewName(viewName);
		return mvb.getMV();
	}

	/**
	 * 输出文本内容到页面上
	 * @param text 文本内容
	 * @param contentType 返回类型
	 */
	private void render(HttpServletResponse response, String text, String contentType) {
		if (response != null) {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType(contentType);
			PrintWriter pWriter = null;
			try {
				pWriter = response.getWriter();
				pWriter.write(text);
				pWriter.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pWriter != null) {
					pWriter.close();
				}
			}
		}
	}

	/**
	 * 输出普通文本到页面
	 * @param text 普通文本
	 */
	protected void renderText(HttpServletResponse response, String text) {
		this.render(response, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 输出Html格式的文本到页面
	 * @param text Html文本内容
	 */
	protected void renderHtml(HttpServletResponse response, String html) {
		this.render(response, html, "text/html;charset=UTF-8");
	}

	/**
	 * 输出Json格式的文本到页面
	 * @param json Json文本内容
	 */
	protected void renderJson(HttpServletResponse response, String json) {
		this.render(response, json, "text/json;charset=UTF-8");
	}

	/**
	 * 输出Json格式的文本到页面
	 * @param obj 待转换对象
	 */
	protected void renderJson(HttpServletResponse response, Object obj) {
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		this.renderJson(response, json);
	}

	/**
	 * 输出XML格式的文本到页面
	 * @param xml XML文本内容
	 */
	protected void renderXML(HttpServletResponse response, String xml) {
		this.render(response, xml, "text/xml;charset=UTF-8");
	}

	/**
	 * 输出结果“success”到页面
	 */
	protected void renderSuccess(HttpServletResponse response) {
		this.renderText(response, SUCCESS);
	}

	/**
	 * 输出结果“error”到页面
	 */
	protected void renderError(HttpServletResponse response) {
		this.renderText(response, ERROR);
	}

	/**
	 * 获取访问者IP
	 * @return String IP地址
	 */
	protected String getIp() {
		String ip = getRequest().getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				ip = addr.getHostAddress().toString();// 获得本机IP
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return ip;
	}

	/**
	 * 获取request对象
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

}
