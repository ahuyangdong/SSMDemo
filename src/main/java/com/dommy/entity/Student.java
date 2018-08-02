package com.dommy.entity;

/**
 * 学生实体
 */
public class Student {
	private int id; // 主键
	private String name; // 姓名
	private int age; // 年龄
	private int sex; // 性别 1-男 2-女

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}
