<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<script
	src="${pageContext.request.contextPath }/js/uploadfile/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	function doAdd(){
		$('#addRegionWindow').window("open");
	}
	
	function doView(){
		$("#queryRegionWindow").window("open");
	}
	
	function doDelete(){
		alert("删除...");
	}
	
	function doExport(){
		location.href="${pageContext.request.contextPath}/regionAction_export";
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
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
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}, {
		id : 'button-import',
		text : '导出',
		iconCls : 'icon-redo',
		handler : doExport
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [10,20,30],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/regionAction_pagination",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		$("#button-import").upload({
			name: 'upload',  // <input name="file" />  
		    action: '${pageContext.request.contextPath}/regionAction_onclickimport',  // 提交请求action路径  
		    enctype: 'multipart/form-data', // 编码格式  
		    //autoSubmit: true,  
		    onSelect:function(){
		    	this.autoSubmit=false;
		    	//var reg=/^(.+\.xls|.+\xlsx)$/;
		    	var reg = /^(.+\.xls|.+\.xlsx)$/;
		    	if(reg.test(this.filename())) {
		    		this.submit();
		    	}else {
		    		$.messager.alert("警告","上传必须是一个Excel文件","warning");
		    	}
		    },
		    onComplete: function(data) {
		        if(data=="true"){  
		            $.messager.alert("恭喜你","文件导入成功","info");
		        }else {
		        	 $.messager.alert("出错了","文件导入失败","error");
		        }
		    }  
		});
		
		$("#save").click(function(){
			var flag=$("#addRegionForm").form("validate");
			if(flag) {
				$("#addRegionForm").submit();
				$('#addRegionWindow').window("close");
			}
		});
		
		$("#search").click(function(){
			var jsondata={"province":$("#qprovince").val(),"city":$("#qcity").val(),"district":$("#qdistrict").val()};
			$("#grid").datagrid("load",jsondata);
			$("#queryRegionWindow").window("close");
		})
		
		$("#edit").click(function(){
			var flag=$("#editRegionForm").form("validate");
			if(flag) {
				$("#editRegionForm").submit();
				$('#editRegionWindow').window("close");
			}
		})
	});
	

	function doDblClickRow(rowIndex,rowData){
		$('#editRegionWindow').window("open");
		//回显数据
		$("#editRegionForm").form("load",rowData);
	}
	//区域编号唯一性校验
	$.extend($.fn.validatebox.defaults.rules, { 

		validateid: { 

		validator: function(value, param){
			var flag;
			$.ajax({
				method:"post",
				url:"${pageContext.request.contextPath}/regionAction_validateId",
				timeout:60000,
				async:false,
				data:{"id":value},
				success:function(data) {
					if(data) {
						flag=true;
					}else {
						flag=false;
					}
				}
				
			});
			return flag;
		}, 

		message: '区域编码已存在' 

		}

		}); 
	
	$.extend($.fn.validatebox.defaults.rules, { 

		validatepostcode: { 

		validator: function(value, param){
			var flag;
			$.ajax({
				method:"post",
				url:"${pageContext.request.contextPath}/regionAction_validatePostcode",
				timeout:60000,
				async:false,
				data:{"postcode":value},
				success:function(data) {
					if(data) {
						flag=true;
					}else {
						flag=false;
					}
				}
				
			});
			return flag;
		}, 

		message: '邮编已存在' 

		}

		});
	
	//窗口关闭前清楚表单数据
	function clearformdata(){
		$("#queryRegionForm").form("clear");
		return true;
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addRegionForm" method="post" action="${pagecontext.request.contextPath }/regionAction_save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编码</td>
						<td><input type="text" name="id" class="easyui-validatebox" data-options="required:true,validType:'validateid'"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" data-options="required:true,validType:'validatepostcode'"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 修改窗口 -->
	<div class="easyui-window" title="条件查询" id="editRegionWindow"  
	closed="true" modal="true" collapsible="false" minimizable="false" 
	maximizable="false" style="top:20px;left:200px;width:500px;height:350px"">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRegionForm" method="post" action="${pagecontext.request.contextPath }/regionAction_save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td>
						<input type="hidden" name="id"/>
						<input type="text" name="province" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>更改邮编</td>
						<td><input type="text" name="newpostcode" class="easyui-validatebox" data-options="validType:'validatepostcode'"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 条件查询窗口 -->
	<div class="easyui-window" title="条件查询" id="queryRegionWindow"  
	closed="true" modal="true" collapsible="false" minimizable="false" 
	maximizable="false" style="top:20px;left:200px;width:500px;height:300px"
	data-options="onBeforeClose:clearformdata">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="queryRegionForm" method="post" action="${pagecontext.request.contextPath }/regionAction_pagination">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省份:</td>
						<td><input type="text" name="province" id="qprovince"/></td>
					</tr>
					<tr>
						<td>城市:</td>
						<td><input type="text" name="city" id="qcity"/></td>
					</tr>
					<tr>
						<td>区(县):</td>
						<td><input type="text" name="district" id="qdistrict"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>