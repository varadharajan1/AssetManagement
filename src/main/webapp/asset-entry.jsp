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
			$("#insert").click(function() {
				$("#action").val("insert");
				$("#asset_form").submit();
			});
			$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#startDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#endDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
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
		<form id="asset_form" name="asset_form" action="/asset/entry" method="POST">
			<input type="hidden" id="action" name="action">
			<div class="main-content">
		        <div class="form-row">
				    <div class="text-left form-group required col-md-3">
				    	<label class="pl-1 control-label" for="trackName"><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="trackName" name="trackName">
					</div>
				    <div class="text-left form-group required  col-md-3">
	                    <label class="pl-1 control-label" for="oemName"><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="oemName" name="oemName">
					</div>
				    <div class="text-left form-group required  col-md-3">
	                    <label class="pl-1 control-label" for="opcoName"><fmt:message key="label.asset.detail.opcoName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="opcoName" name="opcoName">
					</div>
				    <div class="text-left form-group required  col-md-3">
	                    <label class="pl-1 control-label" for="deviceName"><fmt:message key="label.asset.detail.deviceName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="deviceName" name="deviceName">
					</div>
				</div>
		        <div class="form-row">
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="productNumber"><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="productNumber" name="productNumber">
					</div>
				    <div class="text-left form-group col-md-4">
						<label class="pl-1" for="productDescription"><fmt:message key="label.asset.product.description" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="productDescription" name="productDescription">		                    
					</div>	                
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="quantity"><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="quantity" name="quantity">		                    
					</div>
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="serialNumber"><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="serialNumber" name="serialNumber">		                    
					</div>
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="sku"><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="sku" name="sku">
                    </div>
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="contractNumber"><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="contractNumber" name="contractNumber">
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="serviceLevel"><fmt:message key="label.asset.detail.serviceLevel" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="serviceLevel" name="serviceLevel">
                    </div>
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="serviceLevelDescription"><fmt:message key="label.asset.detail.serviceLevelDescription" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="serviceLevelDescription" name="serviceLevelDescription">		                    
					</div>
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="startDate"><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></label>
						<div class="input-group date" id="datepicker1">
							<input type="text" class="form-control form-control-sm" id="startDate" name="startDate">
							<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						</div>
                    </div>
				    <div class="text-left form-group required col-md-2">
                        <label class="pl-1 control-label" for="endDate"><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></label>
                        <div class="input-group date" id="datepicker2">
                            <input type="text" class="form-control form-control-sm" id="endDate" name="endDate"/>
                            <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                        </div>
					</div>
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="deployedLocation"><fmt:message key="label.asset.detail.deployedLocation" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedLocation" name="deployedLocation">		                    
					</div>
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="deployedAddress1"><fmt:message key="label.asset.detail.deployedAddress1" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedAddress1" name="deployedAddress1">		                    
                    </div>
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="deployedAddress2"><fmt:message key="label.asset.detail.deployedAddress2" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedAddress2" name="deployedAddress2">
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="deployedCity"><fmt:message key="label.asset.detail.deployedCity" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedCity" name="deployedCity">		                    
					</div>
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="state"><fmt:message key="label.asset.detail.deployedState" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedState" name="deployedState">
                    </div>
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="deployedZipCode"><fmt:message key="label.asset.detail.deployedZipCode" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedZipCode" name="deployedZipCode">		                    
					</div>
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="deployedCountry"><fmt:message key="label.asset.detail.deployedCountry" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="deployedCountry" name="deployedCountry">		                    
					</div>
				</div>	                
		        <div class="form-group pt-3">
					<input type="reset" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn btn-primary ml-5" id="insert" name="insert" value="<fmt:message key="btn.asset.submit" bundle="${resourceBundle}"/>">		                    
                </div>
			</div>
        </form>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
