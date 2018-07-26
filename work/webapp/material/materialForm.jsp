<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视听资料证据管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function exportPdf(){
			$("#inputForm").attr("action","${ctx}/material/material/exportPDF");
			$("#inputForm").submit();
	    }
	</script>
	
</head>
<body>
	<h3>视听资料证据</h3>
    <div style="text-align:right" >
    <input id="btnExportPdf" class="btn btn-primary" type="button" value="导出PDF " onclick="exportPdf()"/>
    </div>
	
	<h4>基本信息</h4>
	<sys:message content="${message}"/>
	
	<form:form id="inputForm" modelAttribute="material" action="${ctx}/material/material/saveInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		 <div class="control-group container-fluid nopadding"> 
			<div class="row-fluid">
				<div class="span3">		
					<label class="control-label control-tight">收集时间：</label>
				<div class="controls controls-tight">
					<input name="getDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${material.getDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				</div>
			     </div>
			       
				<div class="span3">		
					<label class="control-label control-tight">收集方式：</label>
				<div class="controls controls-tight">
					<form:input path="materialType" htmlEscape="false" maxlength="32" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			    </div>
				
				<div class="span6">		
					<label class="control-label control-tight">收集地点：</label>
				<div class="controls controls-tight">
					<form:input path="getLocation" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			    </div>
			</div>
		</div>
		
		
		 <div class="control-group container-fluid nopadding"> 
			<div class="control-group">
				<label class="control-label control-tight">资料路径：</label>&nbsp;&nbsp;&nbsp;
				<div class="controls controls-tight">
					<form:hidden id="nameImage" path="materialPath" htmlEscape="false" maxlength="255" class="input-xxlarge required"/>
					<sys:ckfinder input="nameImage" type="images" uploadPath="/test/test" selectMultiple="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		 </div>
		 
		 <div class="control-group container-fluid nopadding"> 
		<div class="control-group">
			<label class="control-label control-tight">资料说明：</label>&nbsp;&nbsp;&nbsp;
			<div class="controls controls-tight">
				<form:textarea  path="materialComment" htmlEscape="false"   rows="2" maxlength="255" style="width:800px;height:80px;" class=" required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="material:material:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">	
					
					<div class="span6">		
			<label class="control-label">当事人签名：</label>
			<div class="controls controls-tight">
			    <les:signatureLoader sig="${material.partySig}" path="partySig.id"></les:signatureLoader>
			</div>
		        </div>
					
				<div class="span6">		
			<label class="control-label">巡查人签名：</label>
			<div class="controls controls-tight">
				<les:signatureLoader sig="${material.getterSig}" path="getterSig.id"></les:signatureLoader>
				<les:signatureModal></les:signatureModal>
			</div>
		        </div> 
		        
		    </div>
		</div>		
	</form:form>
</body>
</html>