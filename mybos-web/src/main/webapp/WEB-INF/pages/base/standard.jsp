<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
		<!-- 导入jquery核心类库 -->
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
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 收派标准信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [3,6,9],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/standardAction_pagination",
					idField : 'id',
					columns : columns,
					onDblClickRow:onDblClickRow
				});
				/* // 添加取派员窗口
				$('#addStandardWindow').window({
			        title: '取派员操作',
			        width: 600,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 400,
			        resizable:false,
			        onBeforeClose:function(){
			        	//   清除form 表单数据 尤其  隐藏id 一定要清除  reset   jquery --->Dom
			        	  $("#addStaffForm")[0].reset();//  text  
			        	 $("#tel").removeClass('validatebox-invalid');  
			             $("#id").val("");  //  一定将隐藏id 值清除 // hidden
			        }
			    }); */
			    
			    $("#save").click(function(){
			    	var flag=$("#addStandardForm").form("validate");//表单校验,非法返回false
			    	if(flag){
			    		$("#addStandardForm").submit();
			    	}
			    })
			   
			});	
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
					$('#addStandardWindow').window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : edit
			},{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : deltag
			},{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : function(){
					alert('还原');
				}
			}];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '标准名称',
				width : 120,
				align : 'center'
			}, {
				field : 'minweight',
				title : '最小重量',
				width : 120,
				align : 'center'
			}, {
				field : 'maxweight',
				title : '最大重量',
				width : 120,
				align : 'center'
			}, {
				field : 'minlength',
				title : '最小长度',
				width : 120,
				align : 'center'
			}, {
				field : 'maxlength',
				title : '最大长度',
				width : 120,
				align : 'center'
			}, {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operationtime',
				title : '操作时间',
				width : 160,
				align : 'center'
			}, {
				field : 'operatorcompany',
				title : '操作单位',
				width : 120,
				align : 'center'
			} , {
				field : 'deltag',
				title : '是否作废',
				width : 120,
				align : 'center',
				formatter: function(value,row,index){
			         if (value==1){
			            return "已启用";
			         } else {
			            return "已作废";
			         }  			
			     }  		
			}] ];
			
			function deltag(){
				//获取所有选定行的id的数组
				 var arr=$('#grid').datagrid('getSelections');
				if(arr!=null && arr!="") {
					//数组有两个方法,push数组添加,join数组转换成字符串
					var ids=new Array();
					for(var i=0;i<arr.length;i++) {
						ids.push(arr[i].id);
					}
					var idsString=ids.join(",");
					$.post("${pageContext.request.contextPath}/standardAction_deltag",{'ids':idsString},function(data){
						if(data) {
							$.messager.alert("恭喜","作废成功","info");
							//清除所有选中的行
							$("#grid").datagrid("clearChecked");
							//重新加载,更新页面
							$("#grid").datagrid("reload");
						}else {
							$.messager.alert("温馨提示","系统正忙,请稍后再试","info");
						}
					})
				}else{
					$.messager.alert("警告","请至少选中一行","warning");
				}
			};
			
			function onDblClickRow(rowIndex,rowData){
				$('#addStandardWindow').window("open");
				//回显数据
				$("#addStandardForm").form("load",rowData);
			}
			
			//窗口关闭前清楚表单数据
			function clearformdata(){
				$("#addStandardForm").form("clear");
				return true;
			}
			
			function edit(){
				 var standard=$('#grid').datagrid('getSelected');
				 if(standard==null) {
					 $.messager.alert("警告","请选中一行","warning");
				 }else {
					 $('#addStandardWindow').window("open");
					 $("#addStandardForm").form("load",standard);
				 }
			}
			
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		<!-- 添加取派员窗体  -->
	<div class="easyui-window" title="收派标准添加或修改" id="addStandardWindow"  
	closed="true" modal="true" collapsible="false" minimizable="false" 
	maximizable="false" style="top:20px;left:200px;width:600px;height:400px" 
	data-options="onBeforeClose:clearformdata">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStandardForm" method="post" action="${pageContext.request.contextPath }/standardAction_save">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息</td>
					</tr>
					<tr>
						<td>收派名称</td>
						<td>
						<input type="hidden" name="id" id="id"/>
						<input type="hidden" name="deltag" id="deltag"/>
						<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td><input id="tel" type="text" name="minweight" class="easyui-numberbox" 
						   data-options="min:0,suffix:'KG',required:true"/>
						   </td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input type="text" name="maxweight" class="easyui-numberbox" data-options="max:1000,suffix:'KG',required:true"/></td>
					</tr>
					<tr>
						<td>最小长度</td>
						<td>
							<input type="text" name= "minlength" class="easyui-numberbox" data-options="min:10,suffix:'CM',required:true"/>  
						</td>
					</tr>
					<tr>
						<td>最大长度</td>
						<td>
							<input type="text" name="maxlength" class="easyui-numberbox" data-options="max:1000,suffix:'CM',required:true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	</body>

</html>