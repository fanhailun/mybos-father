<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
<title>页面布局2</title>
<script type="text/javascript">
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		
		/* callback:{
			onClick:function(event,treeId,treeNode,clickFlag){
				//添加选项卡
				if(treeNode.page!=undefined&&treeNode.page!=""){
		        	  var flag =  $("#tt").tabs("exists",treeNode.name);
			            if(!flag){
			            	//  创建
			             $("#tt").tabs("add",{
						  title:treeNode.name,
						  //  content 内容 添加嵌套页面<iframe>
						  content:'<div style="width:100%;height:100%;overflow:hidden;">'
								+ '<iframe src="'
								+ treeNode.page
								+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>',
						  closable:true
					  });
			            }else{
			            	// 存在选中当前的tabs
			            	 $("#tt").tabs("select",treeNode.name);
			            }
		         }
				
			}
		} */
		callback:{
			onClick:function(event,treeId,treeNode,clickFlag){
				if(treeNode.page!=undefined) {
					var flag=$("#tt").tabs('exists',treeNode.name)
					if(!flag){
						//添加选项卡
						$("#tt").tabs('add',{
							title:treeNode.name,
							 content:'<div style="width:100%;height:100%;overflow:hidden;">'
									+ '<iframe src="'
									+ treeNode.page
									+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>',
							closable:true
						});
					}else {
						//存在就选中当前选项卡
						$("#tt").tabs('select',treeNode.name);
					}
				}
			}
		}
	};

	var zNodes =[
		{ "id":"11", "pId":"0", "name":"基础数据",iconOpen:"${pageContext.request.contextPath}/js/ztree/img/diy/1_open.png", iconClose:"${pageContext.request.contextPath}/js/ztree/img/diy/1_close.png"},
		{ "id":"111", "pId":"11", "name":"收派标准",  "page":"page_base_standard.action"},
		{ "id":"112", "pId":"11", "name":"取派员设置",  "page":"page_base_staff.action"},
		{ "id":"113", "pId":"11", "name":"区域设置","page":"page_base_region.action"},
		{ "id":"114", "pId":"11", "name":"管理分区", "page":"page_base_subarea.action"},
		{ "id":"115", "pId":"11", "name":"管理定区","page":"page_base_decidedzone.action"},
		{ "id":"12", "pId":"0", "name":"受理",icon:"${pageContext.request.contextPath}/js/ztree/img/diy/4.png"},
		{ "id":"121", "pId":"12", "name":"业务受理" ,"page":"page_qupai_noticebill_add.action"},
		{ "id":"122", "pId":"12", "name":"工作单快速录入" ,"page":"page_qupai_quickworkorder.action"},
		{ "id":"124", "pId":"12", "name":"工作单导入" ,"page":"page_qupai_workorderimport.action"},
		{ "id":"13", "pId":"0", "name":"调度",icon:"${pageContext.request.contextPath}/js/ztree/img/diy/3.png"},
		{ "id":"131", "pId":"13", "name":"人工调度","page":"page_qupai_diaodu.action"},
		{ "id":"132", "pId":"13", "name":"测试datagrid1","page":"http://localhost/demo/datagrid.jsp"},
		{ "id":"133", "pId":"13", "name":"测试datagrid2","page":"http://localhost/demo/datagrid2.jsp"},
		{ "id":"134", "pId":"13", "name":"测试datagrid3","page":"http://localhost/demo/datagrid3.jsp"}
	];

		$(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);	
		});
		
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">north region</div>
	<div class="easyui-accordion" data-options="region:'west',split:true,title:'系统菜单'" style="width:180px;padding:0px">
		<div title="用户菜单">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div title="管理员菜单"></div>
		<div title="联系我们"></div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div class="easyui-tabs" data-options="region:'center',title:'Center',iconCls:'icon-add'" id="tt">
		
	</div>
</body>

</html>