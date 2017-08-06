<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<table class="easyui-datagrid" data-options="pagination:true,pageList:[3,6,9],fit:true,striped:true">
		<thead>
			<tr>
			<th data-options="field:'code',width:'300px'">用户编号</th>
			<th data-options="field:'name',align:'center'">用户姓名</th>
			<th data-options="field:'sex',align:'right'">用户性别</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td><td>马云1</td><td>男</td>
			</tr>
				<tr>
				<td>2</td><td>马云2</td><td>男</td>
			</tr>
				<tr>
				<td>3</td><td>马云3</td><td>男</td>
			</tr>
				<tr>
				<td>4</td><td>马云4</td><td>男</td>
			</tr>
				<tr>
				<td>5</td><td>马云5</td><td>男</td>
			</tr>
			
			<tr>
				<td>6</td><td>马云6</td><td>男</td>
			</tr>
				<tr>
				<td>7</td><td>马云7</td><td>男</td>
			</tr>
				<tr>
				<td>8</td><td>马云8</td><td>男</td>
			</tr>
				<tr>
				<td>9</td><td>马云9</td><td>男</td>
			</tr>
				<tr>
				<td>10</td><td>马云10</td><td>男</td>
			</tr>
			
			<tr>
				<td>11</td><td>马云11</td><td>男</td>
			</tr>
				<tr>
				<td>12</td><td>马云12</td><td>男</td>
			</tr>
				<tr>
				<td>13</td><td>马云13</td><td>男</td>
			</tr>
				<tr>
				<td>14</td><td>马云14</td><td>男</td>
			</tr>
				<tr>
				<td>15</td><td>马云15</td><td>男</td>
			</tr>
		</tbody>
	</table>
</body>
</html>