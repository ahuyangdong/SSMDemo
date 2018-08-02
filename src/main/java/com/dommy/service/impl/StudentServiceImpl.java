package com.dommy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dommy.dao.StudentDao;
import com.dommy.entity.Student;
import com.dommy.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Resource
	StudentDao dao;

	@Override
	public List<Student> getList(String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		return dao.getList(param);
	}

	@Override
	public int getCount(String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		return dao.getCount(param);
	}

}
