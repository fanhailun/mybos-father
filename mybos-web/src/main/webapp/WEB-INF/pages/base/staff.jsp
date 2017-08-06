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
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		$('#querystaffWindow').window("open");
	}
	
	function doDelete(){
		
		//获取所有选定的行的数据(json对象)
		var arr=$("#grid").datagrid("getSelections");
		if(arr!=null && arr!="") {
			var ids=new Array();
			for (var i = 0; i < arr.length; i++) {
				ids.push(arr[i].id);
			}
			var idsString=ids.join(",");
			$.post("${pageContext.request.contextPath}/staffAction_deltagList",{"ids":idsString},function(data){
				if(data) {
					$.messager.alert("恭喜","作废操作完成","info");
					//清除所有选中的行
					$("#grid").datagrid("clearChecked");
					//重新加载页面
					$("#grid").datagrid("reload");
				}else {
					$.messager.alert("温馨提示","系统正忙,请稍后再试","info");
				}
			})
		}else {
			$.messager.alert("警告","请至少选中一行","warning");
		}
	}
	
	function doRestore(){
		alert("将取派员还原...");
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data==1){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,5,10],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/staffAction_pagination",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		//页面加载完成
		$("#save").click(function(){
			if ($("#addStaffForm").form("validate")) {
				$("#addStaffForm").submit();
				$('#addStaffWindow').window("close");
			}
		});
		
		$("#edit").click(function(){
			if($("#editStaffForm").form("validate")) {
				$("#editStaffForm").submit();
				$('#editStaffWindow').window("close");
			}
			
		})
		
		$("#query").click(function(){
			var jsondata={"name":$("#qname").val(),"telephone":$("#qtelephone").val(),"station":$("#qstation").val(),"standard":$("#qstandard").combobox("getValue")};
			$("#grid").datagrid("load",jsondata);
			$("#querystaffWindow").window("close");
		})
		
	});

	function doDblClickRow(rowIndex,rowData){
		$('#editStaffWindow').window("open");
		//回显数据
		$("#editStaffForm").form("load",rowData);
		if(rowData.haspda=="1") {
			$("input[name='haspda']").attr("checked",true);
		};
	}
	//自定义校验器,校验手机号
	$.extend($.fn.validatebox.defaults.rules, { 

		telephone: { 
		
		validator: function(value, param){ 
		var regex=/^1[3|5|7|8]\d{9}$/;
		return regex.test(value); 
		
		}, 
		
		message: '手机号必须为以13,15,17,18开头的11位数字' 
		
		},
		 uniquePhone:{
			 
			validator: function(value, param){ 
				var flag;
				$.ajax({
					method:"post",
					url:"${pageContext.request.contextPath}/staffAction_findByPhoneAjax",
					timeout:60000,
					data:{"telephone":value},
					async: false,
					success:function(data, textStatus, jqXHR) {
						if(data) {
							flag=true;
						}else {
							flag=false;
						}
					}
				})
				return flag;
				
				},  
				
				message: '手机号已存在' 
		} 
	});
	function clearformdata(){
		$("#addStaffForm").form("clear");
		return true;
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<!-- 添加取派员窗体  -->
	<div class="easyui-window" title="对收派员进行添加" id="addStaffWindow"  
	 collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px" 
	data-options="onBeforeClose:clearformdata">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" method="post" action="${pageContext.request.contextPath }/staffAction_save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td>
						<input type="text" name="name" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" data-options="required:true,validType:['telephone','uniquePhone']"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" id="haspda"/>
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-combobox" required="true" 
							data-options="valueField:'name',editable:false,textField:'name',url:'${pageContext.request.contextPath }/standardAction_selectAjax'"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 查询条件窗体 -->
	<div class="easyui-window" title="条件查询" id="querystaffWindow"  
	closed="true" modal="true" collapsible="false" minimizable="false" 
	maximizable="false" style="top:20px;left:200px;width:500px;height:300px" 
	data-options="onBeforeClose:clearformdata">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="query" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="querystaffForm" method="post" action="${pageContext.request.contextPath }/staffAction_pagination">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询取派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td>
						<input type="text" name="name" id="qname"/></td>
					</tr>
					<tr>
						<td>手机号码</td>
						<td><input id="qtelephone" type="text" name="telephone"/>
						   </td>
					</tr>
					<tr>
						<td>所属单位</td>
						<td><input type="text" name="station" id="qstation"/></td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" id="qstandard" name="standard" class="easyui-combobox" 
							data-options="valueField:'name',textField:'name',url:'${pageContext.request.contextPath }/standardAction_selectAjax'"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 修改取派员窗体  -->
<div class="easyui-window" title="取派员信息修改" id="editStaffWindow"  
	closed="true" modal="true" collapsible="false" minimizable="false" 
	maximizable="false" style="top:20px;left:200px;width:600px;height:400px" 
	data-options="onBeforeClose:clearformdata">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" method="post" action="${pageContext.request.contextPath }/staffAction_save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td>
						<input type="hidden" name="id" id="id"/>
						<input type="hidden" name="deltag" id="deltag"/>
						<input type="text" name="name" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-combobox " data-options="editable:false"/></td>
					</tr>
					<tr>
						<td>更改手机号</td>
						<td><input type="text" name="newtelephone" class="easyui-validatebox"  data-options="validType:['telephone','uniquePhone']"></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" id="haspda"/>
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-combobox" required="true" 
							data-options="valueField:'name',editable:false,textField:'name',url:'${pageContext.request.contextPath }/standardAction_selectAjax'"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>	