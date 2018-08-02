package com.dommy.controller.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dommy.controller.BaseController;
import com.dommy.controller.MVBuilder;
import com.dommy.entity.Student;
import com.dommy.service.StudentService;

/**
 * 学生信息交互层
 */
@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

	@Resource
	StudentService studentService; // 注入student业务类接口

	/**
	 * 查询学生信息列表
	 * <p>可按学生信息模糊查询</p>
	 * @param name 学生姓名
	 * @return ModelAndView 界面视图
	 */
	@RequestMapping("/list")
	public ModelAndView list(String name) {
		MVBuilder mvb = getMVBuilder();
		try {
			List<Student> students = studentService.getList(name);
			mvb.addAttribute("students", students);
			mvb.addAttribute("name", name); // 查询参数返回
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward("student", mvb);
	}

	/**
	 * 查询学生信息总数
	 * <p>这是一个接收异步请求并输出结果的示例</p>
	 * @param response
	 * @param name 学生姓名
	 */
	@RequestMapping("/getCount")
	public void getCount(HttpServletResponse response, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = ERROR;
		try {
			int count = studentService.getCount(name);
			map.put("count", count);
			result = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("result", result);
		this.renderJson(response, map);
	}
}
