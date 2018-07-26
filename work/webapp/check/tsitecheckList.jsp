<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<title>现场踏勘信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/check/Tsitecheck/list");
			$("#searchForm").submit();
        	return false;
        }
		<!--
		$('#beginDate').datepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true//选中之后自动隐藏日期选择框
		}).on('changeDate',function(){
		    $("#beginTime-error").hide();
		    var beginDate= $("#beginDate").val();
		    $("#endDate").datepicker('setStartDate',beginDate);
		    $("#beginDate").datepicker('hide');
		});

		$('#endDate').datepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true//选中之后自动隐藏日期选择框
		}).on('changeDate',function(){
		    var beginDate = $("beginDate").val();
		    var endDate = $("endDate").val();
		    $("#beginDate").datepicker('setEndDate',endDate);
		    $("#endDate").datepicker('hide');
		    -->
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/check/tsitecheck/list">现场踏勘信息列表</a></li>
	<shiro:hasPermission name="check:tsitecheck:edit"><li><a href="${ctx}/check/tsitecheck/form">现场踏勘信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsitecheck" action="${ctx}/check/tsitecheck" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
		<ul class="ul-form">
			<li><label>建设单位：</label>
				<form:input path="developOrg" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>施工单位：</label>
				<form:input path="constructionOrg" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>工程名称：</label>
				<form:input path="projectName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			
			<li>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			</ul>
			
			<ul class="ul-form">
			<li><label>工程地址：</label>
				<form:input path="projectAddress" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			
			<li><label>勘查人：</label>
				<form:input path="checker" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li>
			<label>踏勘时间：</label>&nbsp;<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${paramMap.beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			至
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${paramMap.endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="clearfix"></li>
			</ul>
	</form:form>
			
				
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工程名称</th>
				<th>建设单位</th>
				<th>建设单位负责人及电话</th>
				<th>施工单位</th>
				<th>施工单位负责人及电话</th>
				<th>工程地址</th>
				<th>勘查人</th>
				<th>踏勘时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tsitecheck">
			<tr>
				<td>
					${tsitecheck.projectName}
				</td>
				<td>
					${tsitecheck.developOrg}
				</td>
				<td>
					${tsitecheck.developContact}&nbsp; &nbsp; 
					${tsitecheck.developPhone}
				</td>
				<td>
					${tsitecheck.constructionOrg}
				</td>
				<td>
					${tsitecheck.constructionContact}&nbsp; &nbsp; 
					${tsitecheck.constructionPhone}
				</td>
				<td>
					${tsitecheck.projectAddress}
				</td>
				<td>
					${tsitecheck.checker}
				</td>
				<td>
					<fmt:formatDate value="${tsitecheck.checkDate}" pattern="yyyy-MM-dd "/>
				</td>
				<shiro:hasPermission name="check:tsitecheck:edit"><td>
    				<a href="${ctx}/check/tsitecheck/form?id=${tsitecheck.id}">修改</a>
					<a href="${ctx}/check/tsitecheck/delete?id=${tsitecheck.id}" onclick="return confirmx('确认要删除该现场踏勘信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>