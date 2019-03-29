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

			$("select#productNumber").change(function() {
				var post_url = '/asset/rest/product/skus';
				var productNumber = $("select#productNumber").val();
			    $.ajax({
			        url : post_url,
			        type: 'POST',
			        data : productNumber,
					dataType: 'json',
					contentType: 'application/json; charset=utf-8',
					cache: false,
					processData:false
			    }).done(function(response){ 
			    	var options = '<option value="">None</option>';
			    	$.each(response.data.productSKUList, function (key, value) {
	                    options += '<option value="'+ value +'">' + value + '</option>';
	                });
	                $('select#sku').empty().append(options);
	                $("input#productDescription").val('');
			    }).fail(function(jqXHR, textStatus, errorThrown){
			    	console.log("textStatus: "+textStatus+" : "+errorThrown);
			    });
			});
			$("select#sku").change(function() {
				var post_url = '/asset/rest/product/description';
				var productNumber = $("select#productNumber").val();
				var productSKU = $("select#sku").val();
				
				var json_data = {};
				json_data.productNumber = productNumber;
				json_data.sku = productSKU;
				
			    $.ajax({
			        url : post_url,
			        type: 'POST',
			        data : JSON.stringify(json_data),
					dataType: 'json',
					contentType: 'application/json; charset=utf-8',
					cache: false,
					processData:false
			    }).done(function(response){ 
			    	$("input#productDescription").val(response.data.productDescription);
			    }).fail(function(jqXHR, textStatus, errorThrown){
			    	console.log("textStatus: "+textStatus+" : "+errorThrown);
			    });
			});
			
			$("select#customerName").change(function() {
				var post_url = '/asset/rest/customer/address';
				var customerName = $("select#customerName").val();
			    $.ajax({
			        url : post_url,
			        type: 'POST',
			        data : customerName,
					dataType: 'json',
					contentType: 'application/json; charset=utf-8',
					cache: false,
					processData:false
			    }).done(function(response){ 
			    	var options = '<option value="">None</option>';
			    	$.each(response.data.customerAddress, function (key, value) {
	                    options += '<option value="'+ value +'">' + value + '</option>';
	                });
	                $('select#customerAddress1').empty().append(options);
			    	$("input#customerAddress2").val('');
			    	$("input#city").val('');
			    	$("input#state").val('');
			    	$("input#zipCode").val('');
			    	$("input#country").val('');
			    }).fail(function(jqXHR, textStatus, errorThrown){
			    	console.log("textStatus: "+textStatus+" : "+errorThrown);
			    });
			});
			$("select#customerAddress1").change(function() {
				var post_url = '/asset/rest/customer/info';
				var customerName = $("select#customerName").val();
				var customerAddress1 = $("select#customerAddress1").val();
				
				var json_data = {};
				json_data.customerName = customerName;
				json_data.address1 = customerAddress1;
				
			    $.ajax({
			        url : post_url,
			        type: 'POST',
			        data : JSON.stringify(json_data),
					dataType: 'json',
					contentType: 'application/json; charset=utf-8',
					cache: false,
					processData:false
			    }).done(function(response){ 
			    	$("input#customerAddress2").val(response.data.customerInfo.address2);
			    	$("input#city").val(response.data.customerInfo.city);
			    	$("input#state").val(response.data.customerInfo.state);
			    	$("input#zipCode").val(response.data.customerInfo.zipCode);
			    	$("input#country").val(response.data.customerInfo.country);
			    }).fail(function(jqXHR, textStatus, errorThrown){
			    	console.log("textStatus: "+textStatus+" : "+errorThrown);
			    });
			});
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
                        <select class="form-control form-control-sm" id="trackName" name="trackName">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                            <c:forEach items="${trackInfoList}" var="trackInfo">
								<option value="${trackInfo.trackName}"><c:out value="${trackInfo.trackName}" /></option>
							</c:forEach>
                        </select>
					</div>
				    <div class="text-left form-group required  col-md-3">
	                    <label class="pl-1 control-label" for="oemName"><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></label>
                        <select class="form-control form-control-sm" id="oemName" name="oemName">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                            <c:forEach items="${oemInfoList}" var="oemInfo">
								<option value="${oemInfo.oemName}"><c:out value="${oemInfo.oemName}" /></option>
							</c:forEach>
                        </select>
					</div>
				    <div class="text-left form-group col-md-6">
					</div>
				</div>
		        <div class="form-row">
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="productNumber"><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></label>
                        <select class="form-control form-control-sm" id="productNumber" name="productNumber">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                            <c:forEach items="${productNumbers}" var="productNumber">
								<option value="${productNumber}"><c:out value="${productNumber}" /></option>
							</c:forEach>
                        </select>
					</div>
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="sku"><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></label>
                        <select class="form-control form-control-sm" id="sku" name="sku">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                        </select>
                    </div>
				    <div class="text-left form-group col-md-4">
						<label class="pl-1" for="productDescription"><fmt:message key="label.asset.product.description" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="productDescription" name="productDescription">		                    
					</div>	                
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="customerName"><fmt:message key="label.asset.detail.deployedLocation" bundle="${resourceBundle}"/></label>
                        <select class="form-control form-control-sm" id="customerName" name="customerName">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                            <c:forEach items="${customerNames}" var="customerName">
								<option value="${customerName}"><c:out value="${customerName}" /></option>
							</c:forEach>
                        </select>
					</div>
				    <div class="text-left form-group required col-md-4">
                        <label class="pl-1 control-label" for="customerAddress1"><fmt:message key="label.asset.detail.deployedAddress1" bundle="${resourceBundle}"/></label>
                        <select class="form-control form-control-sm" id="customerAddress1" name="customerAddress1">
							<option value=""><fmt:message key="label.asset.detail.none" bundle="${resourceBundle}"/></option>
                        </select>
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="customerAddress2"><fmt:message key="label.asset.detail.deployedAddress2" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="customerAddress2" name="customerAddress2">
                    </div>
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="city"><fmt:message key="label.asset.detail.deployedCity" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="city" name="city">		                    
					</div>
				    <div class="text-left form-group col-md-3">
                        <label class="pl-1" for="state"><fmt:message key="label.asset.detail.deployedState" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="state" name="state">
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="zipCode"><fmt:message key="label.asset.detail.deployedZipCode" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="zipCode" name="zipCode">		                    
					</div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="country"><fmt:message key="label.asset.detail.deployedCountry" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="country" name="country">		                    
					</div>
				</div>	                
		        <div class="form-row">
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="contractNumber"><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="contractNumber" name="contractNumber">
                    </div>
				    <div class="text-left form-group col-md-1">
                        <label class="pl-1" for="quantity"><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="quantity" name="quantity">		                    
					</div>
				    <div class="text-left form-group required col-md-3">
                        <label class="pl-1 control-label" for="serialNumber"><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></label>
						<input type="text" class="form-control form-control-sm" id="serialNumber" name="serialNumber">		                    
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
				    <div class="text-left form-group required col-md-2">
                        <label class="pl-1 control-label" for="startDate"><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></label>
						<div class="input-group date" id="datepicker1">
							<input type="text" class="form-control form-control-sm" id="startDate" name="startDate">
							<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						</div>
                    </div>
				    <div class="text-left form-group col-md-2">
                        <label class="pl-1" for="endDate"><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></label>
                        <div class="input-group date" id="datepicker2">
                            <input type="text" class="form-control form-control-sm" id="endDate" name="endDate"/>
                            <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                        </div>
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
