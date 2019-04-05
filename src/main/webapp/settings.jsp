<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.pfg.asset.util.AssetConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="com.pfg.asset.nls.asset_management" var="resourceBundle"/>

<!DOCTYPE html>
<html>
<head>
	<title><fmt:message key="page.title.asset.management" bundle="${resourceBundle}"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" type="text/css" />
	
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/theme/style.css" type="text/css" />
	
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

	<script>
		$(function () {
			$("#update").click(function() {
				$("#action").val("update");
				$("#settings_form").submit();
			});
			$("#import").click(function() {
				$("#action").val("import");
				var value = $("#importFileName").val();
				if (typeof value != 'undefined' && value) {
					$("#importFileName").removeClass("validation-error");
					$("#settings_form").submit();
				}else{
					$("#importFileName").addClass("validation-error");
				}
			});

			$('#tableName').css('text-transform','uppercase');
			$('[data-toggle="popover"]').popover({"trigger": 'hover'});
			$("select#cronExpression").val("${assetConfig.cronExpression}");
		});
		</script>
</head>
<body>
	<header>
		<%@ include file = "header.jspf" %>
	</header>
	<main role="main" class="container">
		<div class="page-header text-center pb-1">
			<h3><fmt:message key="page.title.asset.management" bundle="${resourceBundle}"/></h3>
		</div>
	    <c:if test="${! empty( message ) }">
			<div>
				<div class="alert alert-success" id="success-alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong><c:out value="${message}" /></strong>
				</div>
			</div>
	    </c:if>
		<div class="main-content">
			<form id="settings_form" name="settings_form" action="/asset/settings" method="POST" enctype="multipart/form-data">
				<input type="hidden" id="action" name="action">
				<div class="row">
				    <div class="col-md-6">
						<div class="panel panel-primary">
							<div class="panel-body">
								<div class="first text-on-pannel text-primary"><span><fmt:message key="label.asset.settings.smtp" bundle="${resourceBundle}"/></span></div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpHostName"><fmt:message key="label.asset.settings.smtp.host" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpHostName" name="smtpHostName" value="${assetConfig.smtpHost}">		                    
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpPort"><fmt:message key="label.asset.settings.smtp.port" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpPort" name="smtpPort" value="${assetConfig.smtpPort}">
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpUserName"><fmt:message key="label.asset.settings.smtp.username" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpUserName" name="smtpUserName" value="${assetConfig.smtpUsername}">  
								</div>	                
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpPassword"><fmt:message key="label.asset.settings.smtp.password" bundle="${resourceBundle}"/></label>
									<input type="password" class="form-control form-control-sm col-md-6" id="smtpPassword" name="smtpPassword" value="${assetConfig.smtpPassword}">
								</div>
							</div>
						</div>
						<div class="panel panel-primary">
							<div class="panel-body">
								<div class="next text-on-pannel text-primary"><span><fmt:message key="label.asset.settings.other" bundle="${resourceBundle}"/></span></div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="recordsPerPage"><fmt:message key="label.asset.settings.recordsPerPage" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-1" id="recordsPerPage" name="recordsPerPage" value="${assetConfig.recordsPerPage}" readonly="readonly">		                    
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="cronExpression"><fmt:message key="label.asset.settings.cronExpression" bundle="${resourceBundle}"/></label>
									<select class="form-control form-control-sm col-md-6" id="cronExpression" name="cronExpression">
			                            <c:forEach items="${cronExpressions}" var="cronExpression">
											<option value="${cronExpression.key}"><c:out value="${cronExpression.value}" /></option>
										</c:forEach>
			                        </select>
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="filterType"><fmt:message key="label.asset.settings.default.filterType" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="filterType" name="filterType" value="${assetConfig.filterType}" readonly="readonly">
									<%-- <select class="form-control form-control-sm col-md-4" id="filterType" name="filterType">
			                            <c:forEach items="${tableNameList}" var="tableName">
											<option value="${tableName}"><c:out value="${tableName}" /></option>
										</c:forEach>
			                        </select> --%>
								</div>   
							    <div class="text-right row form-group">
									<label class="col-md-4" for="filterValue"><fmt:message key="label.asset.settings.default.filterValue" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="filterValue" name="filterValue" value="${assetConfig.filterValue}" readonly="readonly">
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="renewalPeriodOption"><fmt:message key="label.asset.settings.renewalPeriodOption" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="renewalPeriodOption" name="renewalPeriodOption" value="${assetConfig.renewalPeriod}" readonly="readonly">
								</div>
							</div>
						</div>
	                </div><!-- main content - row - col-md-6 -->
				    <div class="col-md-6">
						<div class="panel panel-primary">
							<div class="panel-body">
								<div class="first text-on-pannel text-primary"><span><fmt:message key="label.asset.settings.email" bundle="${resourceBundle}"/></span></div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpSender"><fmt:message key="label.asset.settings.smtp.sender" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpSender" name="smtpSender" value="${assetConfig.smtpSender}">		                    
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpRecipient"><fmt:message key="label.asset.settings.smtp.recipient" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpRecipient" name="smtpRecipient" value="${assetConfig.smtpRecipient}">
								</div>
							    <div class="text-right row form-group">
									<label class="col-md-4" for="smtpReplyTo"><fmt:message key="label.asset.settings.smtp.replyto" bundle="${resourceBundle}"/></label>
									<input type="text" class="form-control form-control-sm col-md-6" id="smtpReplyTo" name="smtpReplyTo" value="${assetConfig.smtpReplyTo}">
								</div>	                
							</div>
						</div>
	                </div>
                </div> <!-- main content - row -->
		        <div class="form-group pt-3">
					<input type="reset" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">
					<input type="button" class="btn btn-primary ml-5" id="update" name="update" value="<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>">
                </div>
				<div class="panel panel-primary">
					<div class="panel-body">
						<div class="previous text-on-pannel text-primary"><span><fmt:message key="label.asset.import.file" bundle="${resourceBundle}"/></span></div>
					    <div class="text-right row form-group required">
							<label class="control-label col-md-2" for="smtpHostName"><fmt:message key="label.asset.import.filename" bundle="${resourceBundle}"/></label>
							<input type="file" class="form-control form-control-sm col-md-5" id="importFileName" name="importFileName">
						</div>
					    <div class="text-right row form-group required">
							<label class="control-label col-md-2" for="smtpPort"><fmt:message key="label.asset.import.tablename" bundle="${resourceBundle}"/></label>
							<select class="form-control form-control-sm col-md-3" id="tableName" name="tableName">
	                            <c:forEach items="${tableNameList}" var="tableName">
									<option value="${tableName}"><c:out value="${tableName}"/></option>
								</c:forEach>
	                        </select>
	                        <a class="help align-items-center d-flex" data-toggle="popover" data-content="Select the table, where the file going to be imported.">&nbsp;<i class="fa fa-question-circle"></i>&nbsp;</a>
	                   </div>
					</div>
				</div>
		        <div class="form-group pt-3">
					<input type="reset" class="btn btn-secondary mr-5" id="clear1" name="clear1" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn btn-primary ml-5" id="import" name="import" value="<fmt:message key="btn.asset.import" bundle="${resourceBundle}"/>">		                    
                </div>
			</form>
		</div>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
