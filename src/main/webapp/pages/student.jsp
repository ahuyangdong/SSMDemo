<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
  	<head>
    	<base href="<%=basePath%>">
    	<title>学生信息</title>
    	<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="resources/js/jquery-1.9.1.min.js"></script>
  	</head>
<body>
  	<div>
  		<div>
			<span>学生管理</span>
		</div>
  		<div>
			<form id="frm" method="post">
				<span>姓名：</span>
				<input type="text" id="name" name="name" value="${name }"/>
				&ensp;
				<button type="button" onclick="query();">查询</button>
			</form>
 			<br/>
	  	</div>
		<div>
			<table>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>年龄</th>
					<th>性别</th>
				</tr>
				<c:forEach var="student" items="${students }" varStatus="i">
					<tr>
					 	<td>${i.count+(page.pageIndex-1)*page.pageSize }</td>
						<td>${student.name }</td>
						<td>${student.age}</td>
						<td>
							<c:if test="${student.sex==1 }">男</c:if>
							<c:if test="${student.sex==2 }">女</c:if>
						</td>
					</tr>
				 </c:forEach>
			</table>
			<br/>
			总数：<span id="count"></span>
		</div>
	</div>
</body>
<script>
$(function() {
	// 页面加载完成后异步请求count值
	getCount();
});

// 查询
function query() {
	$("#frm").attr("action", "<%=basePath%>student/list");
	$("#frm").submit();
}

// ajax异步请求
function getCount() {
	$.ajax({
		type: "post",
		url: "<%=basePath %>student/getCount",
		data: {
			"name": $("#name").val()
		},
		dataType: "json",
		success: function(data) {
			if (data.result == "success") {
				$("#count").html(data.count);
			} else if (data == "error") {
				$("#count").html("");
				alert("ajax查询失败！");
			} else {
				$("#count").html("");
			}
		}
	});
}
</script>
</html>
