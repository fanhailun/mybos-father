<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,5,10],
			pagination : true,
			toolbar : [
				{
					id : 'add',
					text : '添加菜单',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_menu_add.action';
					}
				}           
			],
			url : '${pageContext.request.contextPath}/menuAction_pagination',
			columns : [[
			  {
				  field : 'id',
				  title : '菜单编号',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			  },
			  {
				  field : 'name',
				  title : '菜单名称',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			  },  
			  {
				  field : 'description',
				  title : '菜单描述',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					},
				  formatter:function(value,row,index){
					  if(value=="1"){
						  return "是";
					  }else{
						  return "否";
					  }
				  }
			  },  
			  {
				  field : 'menu.name',
				  title : '父菜单名称',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					},
				  formatter:function(value,row,index){
					  if(!row.parentName){
						  return "没有父菜单";
					  }else{
						  return row.parentName;
					  }
				  }
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			  },  
			  {
				  field : 'page',
				  title : '路径',
				  width : 200,
				  editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			  }
			]]
		});
	});
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
	<table id="grid"></table>
</div>
</body>
</html>