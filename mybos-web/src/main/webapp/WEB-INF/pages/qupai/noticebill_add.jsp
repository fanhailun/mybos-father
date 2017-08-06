<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加业务受理单</title>
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
		$("body").css({visibility:"visible"});
		
		// 对save按钮条件 点击事件
		$("#save").click(function(){
			if($("#noticebillForm").form("validate")){
				var nprovince=document.getElementById("province").selectedOptions[0].text;
	      		var ncity= document.getElementById("city").selectedOptions[0].text
	      		var ndistrict= document.getElementById("district").selectedOptions[0].text;
				$("#nprovince").val(nprovince);
				$("#ncity").val(ncity);
				$("#ndistrict").val(ndistrict);
				$("#noticebillForm").submit();
			}
		})
		
		load(0,province);
		
		$("#telephone").blur(function(){
			if(this.value==""){
				$.messager.alert("警告","手机号不能为空","warning")
				return;
			}
			$.post("${pageContext.request.contextPath}/noticebillAction_findCustomerByTelephone",{"telephone":this.value},function(data){
				if(data==null){
					//新客户
					$("#tel_sp").html("<font color='green'>新客户</font>");
					$("#customerId").val("");
					$("#customerId").hide();//隐藏客户id
					$("#customerName").val("");
				}else{
					$("#tel_sp").html("<font color='red'>老客户</font>");
					$("#customerId").val(data.id);
					$("#customerId").show();//显示客户id
					$("#customerId").attr("readonly",true);
					$("#customerName").val(data.name);
				}
			})
		})
		
		
	});
	 function load(value,target){
		target.length=1;
		district.length=1;
		if(value=="none"){
			return;
		}
		$.post("${pageContext.request.contextPath}/cityAction_load",{"pid":value},function(data){
			$(data).each(function(){
				$(target).append("<option value='"+this.id+"'>"+this.name+"</option>");
			})
		})
	}
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
				plain="true">新单</a>
			<a id="edit" icon="icon-edit" href="${pageContext.request.contextPath }/page_qupai_noticebill.action" class="easyui-linkbutton"
				plain="true">工单操作</a>	
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="noticebillForm" action="${pageContext.request.contextPath }/noticebillAction_save" method="post">
			<table class="table-edit" width="95%" align="center">
				<tr class="title">
					<td colspan="4">客户信息</td>
				</tr>
				<tr>
					<td>来电号码:</td>
					<td><input type="text" class="easyui-validatebox" name="telephone" id="telephone"
						required="true" />
						<span id="tel_sp"></span>
					</td>
					<td>客户编号:</td>
					<td><input type="text" name="customerId" id="customerId"/></td>
				</tr>
				<tr>
					<td>客户姓名:</td>
					<td><input type="text" class="easyui-validatebox" name="customerName" id="customerName"
						required="true" /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>品名:</td>
					<td><input type="text" class="easyui-validatebox" name="product"
						required="true" /></td>
					<td>件数:</td>
					<td><input type="text" class="easyui-numberbox" name="num"
						required="true" /></td>
				</tr>
				<tr>
					<td>重量:</td>
					<td><input type="text" class="easyui-numberbox" name="weight"
						required="true" /></td>
					<td>体积:</td>
					<td><input type="text" class="easyui-validatebox" name="volume"
						required="true" /></td>
				</tr>
				<tr>
					<td>取件地址</td>
					<!-- 表单提交将省市区中文信息发送给后台 -->
					 <input type="hidden" name="nprovince"  id="nprovince">
					 <input type="hidden" name="ncity"  	id="ncity">
					 <input type="hidden" name="ndistrict"  id="ndistrict">
					<td colspan="3">
					 省&nbsp;
					 <select name="province" id="province" onchange="load(value,city);">
					  <option value="none">--请选择--</option>
					 </select>&nbsp;
					 市&nbsp;
					 <select name="city" id="city" onchange="load(value,district);">
					   <option value="none">--请选择--</option>
					 </select>&nbsp;
					 区&nbsp;
					 <select name="district" id="district">
					   <option value="none">--请选择--</option>
					 </select>&nbsp;详细地址
					<input type="text" class="easyui-validatebox" name="pickaddress" required="true" size="50"/>
				  </td>
				</tr>
				<tr>
					<td>到达城市:</td>
					<td><input type="text" class="easyui-validatebox" name="arrivecity"
						required="true" /></td>
					<td>预约取件时间:</td>
					<td><input type="text" class="easyui-datebox" name="pickdate"
						data-options="required:true, editable:false" /></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="remark"
						required="true" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>