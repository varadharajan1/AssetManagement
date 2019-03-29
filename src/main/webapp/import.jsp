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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet" type="text/css" />
	
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/theme/style.css" type="text/css" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
	<script>
		$(function () {
			$('#tableName').css('text-transform','uppercase');
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
			<form id="import_form" name="import_form" action="/asset/importData" method="POST"  enctype="multipart/form-data">
				<input type="hidden" class="form-control form-control-sm" id="action" name="action">
		        <div class="row px-lg-4 pt-lg-4 pt-3">
		            <div class="col-12">
		                <div class="row p-sm-2">
		                    <div class="col-sm-2 pb-2 text-right">
		                        <strong><fmt:message key="label.asset.import.filename" bundle="${resourceBundle}"/></strong>
		                    </div>
		                    <div class="col-sm-8">
								<input type="file" class="form-control form-control-sm" id="importFileName" name="importFileName">		                    
		                    </div>
		                </div>
		                <div class="row p-sm-2">
		                    <div class="col-sm-2 pb-2 text-right">
		                        <strong><fmt:message key="label.asset.import.tablename" bundle="${resourceBundle}"/></strong>
		                    </div>
		                    <div class="col-sm-4">
								<select class="form-control form-control-sm" id="tableName" name="tableName">
		                            <c:forEach items="${tableNameList}" var="tableName">
										<option value="${tableName}"><c:out value="${tableName}" /></option>
									</c:forEach>
		                        </select>
		                    </div>
		                </div>
		                <div class="row p-4">
		                    <div class="col-sm-2">
		                    </div>
		                    <div class="col-sm-8">
								<input type="reset" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
								<input type="submit" class="btn btn-primary ml-5" id="importData" name="importData" value="<fmt:message key="btn.asset.import" bundle="${resourceBundle}"/>">		                    
		                    </div>
		                </div>
		            </div>
		        </div>
	        </form>
		</div>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
