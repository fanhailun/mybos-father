<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面布局1</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
	<script type="text/javascript">
		var setting = {	};
					
		var zNodes =[
			{ name:"传智播客集团1", open:true,
				children: [
			{ name:"上海校区",
				children: [
					{ name:"航头镇奋斗者"},
					{ name:"陆家嘴中心"}
				]},
			{ name:"北京校区",
				children: [
					{ name:"中关村"},
					{ name:"中关园"}
						]},
			{ name:"合肥校区",isParent:true}
							]},
			{ name:"传智播客集团2", isParent:true}
					
					];
					
					$(function(){
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					
					});
						
				</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div  class="easyui-accordion" data-options="region:'west',split:false,title:'菜单导航'" style="width:180px;">
		<!-- 折叠菜单 -->
			<div title="基本功能">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div title="系统管理">系统菜单</div>
			<div title="联系我们">联系我们</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div class="easyui-tabs" data-options="region:'center',title:'Center',iconCls:'icon-add'">
		<div title="Tab1" style="padding:20px;">啦啦 </div> 

		<div title="Tab2" data-options="closable:true" style="overflow:auto;padding:20px;">哈哈</div> 

		<div title="Tab3" data-options="closable:true" style="padding:20px;">tab3 </div> 
		
	</div>
</body>

</html>