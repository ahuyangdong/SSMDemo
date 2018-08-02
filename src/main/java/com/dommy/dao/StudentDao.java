package com.dommy.dao;

import java.util.List;
import java.util.Map;

import com.dommy.entity.Student;

/**
 * 学生信息dao层接口
 */
public interface StudentDao {

	/**
	 * 读取学生列表
	 * @param param 查询参数
	 * @return List<Student> 列表
	 */
	List<Student> getList(Map<String, Object> param);

	/**
	 * 读取学生数量
	 * @param param 查询参数
	 * @return int 数量
	 */
	int getCount(Map<String, Object> param);
}
