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
				$("#asset_form").submit();
			});
			
			$("#rowSelected").val('${rowSelected}');
			$("#filterType").val('${filterType}');
			$("#filterValue").val('${filterValue}');
			$("#renewal").val('${renewal}');
			$("#fromDate").val('${fromDate}');
			$("#toDate").val('${toDate}');

			var rowSelectedObject = JSON.parse('['+'${rowSelected}'+']');
			console.log("filterType: "+$("#filterType").val());
			console.log("filterValue: "+$("#filterValue").val());
			console.log("rowSelectedObject.length: "+rowSelectedObject.length);
			
			if(rowSelectedObject.length > 0){
				$("#action").val("update");
				$("#insert").val('<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>');
			}else{
				$("#action").val("insert");
			}
			if(rowSelectedObject.length == 1){
				var asset = rowSelectedObject[0];
				console.log(rowSelectedObject[0]);
                $("#trackName").val(asset.trackName);
				$("#businessSegment").val(asset.businessSegment);
				$("#opcoName").val(asset.opcoName);
				$("#deviceName").val(asset.deviceName);
				$("#oemName").val(asset.oemName);
				$("#productNumber").val(asset.productNumber);
				$("#productDescription").val(asset.productDescription);
				$("#sku").val(asset.sku);
				$("#contractNumber").val(asset.contractNumber);
				$("#contractedThrough").val(asset.contractedThrough);
				$("#quantity").val(asset.quantity);
				$("#serialNumber").val(asset.serialNumber);
				$("#serviceLevel").val(asset.serviceLevel);
				$("#serviceLevelDescription").val(asset.serviceLevelDescription);
				$("#supportStartDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
				$("#supportEndDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
				$('#supportEndDate').datepicker("setDate", asset.supportEndDate);
				$('#supportStartDate').datepicker("setDate", asset.supportStartDate);
				$("#eolDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
				$('#eolDate').datepicker("setDate", asset.eolDate);
				$("#purchasedDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
				$('#purchasedDate').datepicker("setDate", asset.purchasedDate);
				$("#installedDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
				$('#installedDate').datepicker("setDate", asset.installedDate);
				$("#purchasedVendor").val(asset.purchasedVendor);
				$("#purchasedCost").val(asset.purchasedCost);
				$("#deployedLocation").val(asset.deployedLocation);
				$("#deployedAddress1").val(asset.address1);
				$("#deployedAddress2").val(asset.address2);
				$("#deployedCity").val(asset.city);
				$("#deployedState").val(asset.state);
				$("#deployedCountry").val(asset.country);
				$("#deployedZipCode").val(asset.zipCode);
			}
			$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#supportStartDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#supportEndDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#datepicker3").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#eolDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#datepicker4").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#installedDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#datepicker5").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
			$("#purchasedDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
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
			<input type="hidden" id="filterType" name="filterType">
			<input type="hidden" id="filterValue" name="filterValue">
			<input type="hidden" id="renewal" name="renewal">
			<input type="hidden" id="fromDate" name="fromDate">
			<input type="hidden" id="toDate" name="toDate">
			<input type="hidden" id="rowSelected" name="rowSelected">
			<div class="main-content">
		        <div class="pb-3 form-row">
				    <div class="text-left form-group col-md-2">
				    	<label class="pl-1" for="trackName"><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="trackName" name="trackName">
					</div>
				    <div class="text-left form-group col-md-2">
	                    <label class="pl-1" for="businessSegment"><fmt:message key="label.asset.detail.businessSegment" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="businessSegment" name="businessSegment">
					</div>
				    <div class="text-left form-group col-md-2">
	                    <label class="pl-1" for="oemName"><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="oemName" name="oemName">
					</div>
				    <div class="text-left form-group col-md-2">
	                    <label class="pl-1" for="opcoName"><fmt:message key="label.asset.detail.opcoName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="opcoName" name="opcoName">
					</div>
				    <div class="text-left form-group col-md-3">
	                    <label class="pl-1" for="deviceName"><fmt:message key="label.asset.detail.deviceName" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="deviceName" name="deviceName">
					</div>
				</div>
		        <div class="pb-3 form-row">
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="contractedThrough"><fmt:message key="label.asset.detail.contractedThrough" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="contractedThrough" name="contractedThrough">		                    
					</div>
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="productNumber"><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="productNumber" name="productNumber">
					</div>
				    <div class="text-left form-group col-md-4">
						<label class="pl-1" for="productDescription"><fmt:message key="label.asset.detail.description" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="productDescription" name="productDescription">		                    
					</div>	                
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="quantity"><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="quantity" name="quantity">		                    
					</div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="serialNumber"><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="serialNumber" name="serialNumber">		                    
					</div>
				</div>	                
		        <div class="pb-3 form-row">
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="sku"><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></label>
				    	<input type="text" class="form-control form-control-sm" id="sku" name="sku">
                    </div>
				    <div class="text-left form-group col-md-2">
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
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="purchasedVendor"><fmt:message key="label.asset.detail.purchasedVendor" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="purchasedVendor" name="purchasedVendor">
                    </div>
				</div>
		        <div class="pb-3 form-row">
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="supportStartDate"><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></label>
						<div class="input-group date" id="datepicker1">
							<input type="text" class="form-control form-control-sm" id="supportStartDate" name="supportStartDate">
							<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						</div>
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="supportEndDate"><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></label>
                        <div class="input-group date" id="datepicker2">
                            <input type="text" class="form-control form-control-sm" id="supportEndDate" name="supportEndDate"/>
                            <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                        </div>
					</div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="eolDate"><fmt:message key="label.asset.detail.eolDate" bundle="${resourceBundle}"/></label>
						<div class="input-group date" id="datepicker3">
							<input type="text" class="form-control form-control-sm" id="eolDate" name="eolDate">
							<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						</div>
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="installedDate"><fmt:message key="label.asset.detail.installedDate" bundle="${resourceBundle}"/></label>
                        <div class="input-group date" id="datepicker4">
                            <input type="text" class="form-control form-control-sm" id="installedDate" name="installedDate"/>
                            <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                        </div>
					</div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="purchasedDate"><fmt:message key="label.asset.detail.purchasedDate" bundle="${resourceBundle}"/></label>
						<div class="input-group date" id="datepicker5">
							<input type="text" class="form-control form-control-sm" id="purchasedDate" name="purchasedDate">
							<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						</div>
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="purchasedCost"><fmt:message key="label.asset.detail.purchasedCost" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="purchasedCost" name="purchasedCost">
                    </div>
				</div>
		        <div class="pb-3 form-row">
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
