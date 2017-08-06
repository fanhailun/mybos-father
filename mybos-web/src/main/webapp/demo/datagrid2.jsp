<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<script type="text/javascript">
     $(function(){
    	 $('#grid').datagrid({ 
    		 
    		 pagination:true,
    		 pageList:[5,8,10],
    		 url:'data.json', 
             fit:true,
             toolbar: [{  		
            	  iconCls: 'icon-edit', 
            	  text:'编辑',
            	  handler: function(){
            		  $.messager.show({  	
            			  title:'中奖啦!',  	
            			  msg:'恭喜你,喜中18888元大奖!',  	
            			  timeout:5000,  	
            			  showType:'fade'  
            			}); 
            		  }  	
            	  },'-',{  		
            	  iconCls: 'icon-help',  	
            	  text:'帮助',
            	  handler: function(){
            		  $.messager.prompt('请输入', '请输入你的名字:', function(r){if (r){ alert('你的名字是:娃娃' ); }}); 
            	  }  	
            	  }]  ,
    		 columns:[[ 

    		 {field:'code',title:'用户编号',width:200,align:'center'}, 

    		 {field:'name',title:'用户姓名',width:200,align:'center'}, 

    		 {field:'sex',title:'性别',width:200,align:'center'} 

    		 ]] 

    		 }); 

     })
   
   </script>
<body>
		<table id="grid"></table>
</body>
</html>