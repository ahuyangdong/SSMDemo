package com.dommy.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 字符编码过滤器
 */
public class CharacterEncodingFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null; // 过滤器配置参数
	private String encoding = null; // 字符编码

	public CharacterEncodingFilter() {
		super();
	}

	public void destroy() {
		this.filterConfig = null;
		this.encoding = null;
	}

	/**
	 * 过滤方法
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String methodName = request.getMethod();
		if ("post".equalsIgnoreCase(methodName)) {
			// post请求编码设置
			request.setCharacterEncoding(this.encoding);
		} else if ("get".equalsIgnoreCase(methodName)) {
			// get请求编码设置，需要逐个参数解析
			request = new MyRequestWrapper(request);
			HashMap map = new HashMap(request.getParameterMap());
			if (map != null && map.size() > 0) {
				Set<Entry> entrySet = map.entrySet();
				for (Map.Entry entry : entrySet) {
					Object key = entry.getKey();
					Object value = entry.getValue();
					if (value instanceof String[]) {
						String[] valueArray = (String[]) value;
						for (int i = 0; i < valueArray.length; ++i) {
							// 改编码
							valueArray[i] = doEncoding(valueArray[i]);
						}
						map.put(key, valueArray);
					}
				}
			}

			ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request, map);
			request = wrapRequest;
		}

		// 设置输出的字符编码
		res.setContentType("text/html;charset=" + encoding);
		// 继续执行后面的程序
		fc.doFilter(request, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;

		/* 获取字符编码 */
		this.encoding = filterConfig.getInitParameter("encoding");
		if (encoding == null || encoding.length() == 0) {
			encoding = "UTF-8";
		}
	}

	/**
	 * 内部类，继承自 HttpServletRequestWrapper 类
	 */
	public class MyRequestWrapper extends HttpServletRequestWrapper {
		HttpServletRequest request = (HttpServletRequest) super.getRequest();

		public MyRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			String value = this.encode(request.getParameter(name));
			return value;
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = request.getParameterValues(name);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					values[i] = this.encode(values[i]);
				}
			}
			return values;
		}

		/**
		 * 字符编码处理
		 * @param value 编码前的字符串
		 * @return 编码后的字符串
		 */
		public String encode(String value) {
			if (value != null) {
				try {
					value = new String(value.getBytes("ISO-8859-1"), CharacterEncodingFilter.this.encoding);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return value;
		}
	}

	/**
	 * 修改编码
	 * @param value 编码前的字符串
	 * @return String 编码后的字符串
	 */
	private String doEncoding(String value) {
		if (value != null && value.length() > 0) {
			try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
}