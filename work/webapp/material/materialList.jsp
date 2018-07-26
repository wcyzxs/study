<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<title>视听资料证据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/material/material/">视听资料证据列表</a></li>
		<shiro:hasPermission name="material:material:edit"><li><a href="${ctx}/material/material/form">视听资料证据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="material" action="${ctx}/material/material/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>收集方式：</label>
				<form:input path="materialType" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>收集地点：</label>
				<form:input path="getLocation" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li>
			<label>收集时间：</label>&nbsp;<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${paramMap.beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			至
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${paramMap.endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>收集方式</th>
				<th>收集地点</th>
				<th>收集时间</th>
				<shiro:hasPermission name="material:material:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="material">
			<tr>
				
				<td>
					${material.materialType}
				</td>
				<td>
					${material.getLocation}
				</td>
				<td><a href="${ctx}/material/material/form?id=${material.id}">
					<fmt:formatDate value="${material.getDate}" pattern="yyyy-MM-dd "/>
				</a></td>
				<shiro:hasPermission name="material:material:edit"><td>
    				<a href="${ctx}/material/material/form?id=${material.id}">修改</a>
					<a href="${ctx}/material/material/delete?id=${material.id}" onclick="return confirmx('确认要删除该视听资料证据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>