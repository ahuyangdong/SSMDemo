package com.dommy.service;

import java.util.List;

import com.dommy.entity.Student;

/**
 * 学生信息业务层接口
 */
public interface StudentService {
	
	/**
	 * 读取学生列表
	 * @param name 姓名
	 * @return List<Student> 列表
	 */
	List<Student> getList(String name);

	/**
	 * 读取学生数量
	 * @param name 姓名
	 * @return int 数量
	 */
	int getCount(String name);
}
