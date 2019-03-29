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

    <script src="${pageContext.servletContext.contextPath}/custom/moment.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/custom/Chart.min.js"></script>

	<script>
	$(function () {
		var colors = ['#dc3545','#28a745','#adff2f','#007bff'];
		var labels = Array();
		var data = Array();
		
		<c:forEach items="<%=AssetConstants.INTERVALS%>" var="interval">
			labels.push("${interval}");
			data.push("${renewalCounts[interval]}");
		</c:forEach>

		var chBar = $("#chBar");
		if (chBar) {
		  new Chart(chBar, {
		  type: 'bar',
		  data: {
		    labels: labels,
		    datasets: [{
		      data: data,
		      backgroundColor: colors,
		      borderColor: colors,
		      borderWidth: 1
		    }]
		  },
		  options: {
		    legend: {
		      display: false
		    },
		    title: {
		        display: true,
		        text: 'Assets based on renewal period'
		    },
		    responsive: true,
		    scales: {
		      xAxes: [{
		        barPercentage: 0.7,
		        categoryPercentage: 0.3,
                scaleLabel: {
					display: true,
					labelString: 'Renewal periods'
				}
		      }],
		      yAxes: [{
                ticks: {
                    beginAtZero:true
                },
                scaleLabel: {
					display: true,
					labelString: '# of assets'
				}
	          }]
		    }
		  }
		  });
		}
	});
	</script>

	<script>
		$(function () {
			var multipleValue = "Multiple Rows Selected";
			var rowSelected = new Array();
			var updateFlag = false;
			$("#update").click(function() {
				$("#action").val("update");
				$("#rowSelected").val(rowSelected);
				if(rowSelected.length == 1){
					var asset = unescape(rowSelected[0]).split("~");
                    $("#editTrackName").val(asset[0]);
					$("#editOemName").val(asset[1]);
					$("#editProductNumber").val(asset[2]);
					$("#editSku").val(asset[3]);
					$("#editCustomerName").val(asset[4]);
					$("#editCustomerAddress1").val(asset[5]);
					$("#editContractNumber").val(asset[6]);
                    $("#editQuantity").val(asset[7]);
					$("#editSerialNumber").val(asset[8]);
					$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#editStartDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#editEndDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$('#editStartDate').datepicker("setDate", asset[9]);
					$('#editEndDate').datepicker("setDate", asset[10]);
					updateFlag = true;
				}else if(rowSelected.length > 1){
                    $("#editTrackName").val(multipleValue);
					$("#editOemName").val(multipleValue);
					$("#editProductNumber").val(multipleValue);
					$("#editSku").val(multipleValue);
					$("#editCustomerName").val(multipleValue);
					$("#editCustomerAddress1").val(multipleValue);
					$("#editContractNumber").val(multipleValue);
                    $("#editQuantity").val(multipleValue);
					$("#editSerialNumber").val(multipleValue);
					$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#editStartDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#editEndDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					//$("#editStartDate").val(multipleValue);
					//$("#editEndDate").val(multipleValue);
					updateFlag = true;
				}else if(rowSelected.length <= 0){
		    		updateFlag = false;
		    	}
				if(updateFlag == true){
					$('#editModalInputs').modal('show');
				}
			});
			$("#save-asset").click(function() {
				$("#action").val("update");
				$("#rowSelected").val(rowSelected);
				$("#filter_form").submit();
			});
			$('input[type="checkbox"]').change(function() { 
			    if ($(this).is(':checked')) {
			    	rowSelected.push(escape($(this).val()));
			    } else {
			    	var removeItem = $.inArray(escape($(this).val()), rowSelected);
			    	if(removeItem >= 0){
				    	rowSelected.splice(removeItem, 1);
			    	}
			    }
			});
		});
	</script>

	<script>
		$(function () {
			$("#clear").click(function() {
				$("#product").addClass("d-none");
				$("#renewal").removeClass("d-none");
				$("#customer").addClass("d-none");
				$("#address").addClass("d-none");
				$("#serial").addClass("d-none");
				$("select#filterType").val("RENEWAL");
			});
			$("select#filterType").change(function() {
				var filterType = $("select#filterType").val();
				if(filterType == 'PRODUCT'){
					$("#product").removeClass("d-none");
					$("#renewal").addClass("d-none");
					$("#customer").addClass("d-none");
					$("#address").addClass("d-none");
					$("#serial").addClass("d-none");
				}else if(filterType == 'SERIAL'){
					$("#product").addClass("d-none");
					$("#renewal").addClass("d-none");
					$("#customer").addClass("d-none");
					$("#address").addClass("d-none");
					$("#serial").removeClass("d-none");
				}else if(filterType == 'CUSTOMER'){
					$("#product").addClass("d-none");
					$("#renewal").addClass("d-none");
					$("#customer").removeClass("d-none");
					$("#address").addClass("d-none");
					$("#serial").addClass("d-none");
				}else if(filterType == 'ADDRESS'){
					$("#product").addClass("d-none");
					$("#renewal").addClass("d-none");
					$("#customer").addClass("d-none");
					$("#address").removeClass("d-none");
					$("#serial").addClass("d-none");
				}else{
					$("#product").addClass("d-none");
					$("#renewal").removeClass("d-none");
					$("#customer").addClass("d-none");
					$("#address").addClass("d-none");
					$("#serial").addClass("d-none");
				}
			});
			$("#filter").click(function() {
				var filterType = $("select#filterType").val();
				if(filterType == 'PRODUCT'){
					$("#filterValue").val($("#product").val());
				}else if(filterType == 'SERIAL'){
					$("#filterValue").val($("#serial").val());
				}else if(filterType == 'CUSTOMER'){
					$("#filterValue").val($("#customer").val());
				}else if(filterType == 'ADDRESS'){
					$("#filterValue").val($("#address").val());
				}else{
					$("#filterValue").val($("#renewal").val());
				}
				$("#action").val("filter");
				$("#filter_form").submit();
			});
			var filterType = "${filterParam.filterType}";
			$("select#filterType").val(filterType);
			$("#filterValue").val("${filterParam.filterValue}");
			if(filterType == 'PRODUCT'){
				$("#product").removeClass("d-none");
				$("#product").val($("#filterValue").val());
				$("#renewal").addClass("d-none");
				$("#customer").addClass("d-none");
				$("#address").addClass("d-none");
				$("#serial").addClass("d-none");
			}else if(filterType == 'SERIAL'){
				$("#product").addClass("d-none");
				$("#renewal").addClass("d-none");
				$("#customer").addClass("d-none");
				$("#address").addClass("d-none");
				$("#serial").removeClass("d-none");
				$("#serial").val($("#filterValue").val());
			}else if(filterType == 'CUSTOMER'){
				$("#product").addClass("d-none");
				$("#renewal").addClass("d-none");
				$("#customer").removeClass("d-none");
				$("#customer").val($("#filterValue").val());
				$("#address").addClass("d-none");
				$("#serial").addClass("d-none");
			}else if(filterType == 'ADDRESS'){
				$("#product").addClass("d-none");
				$("#renewal").addClass("d-none");
				$("#customer").addClass("d-none");
				$("#address").removeClass("d-none");
				$("#address").val($("#filterValue").val());
				$("#serial").addClass("d-none");
			}else{
				$("#product").addClass("d-none");
				$("#renewal").removeClass("d-none");
				$("#renewal").val($("#filterValue").val());
				$("#customer").addClass("d-none");
				$("#address").addClass("d-none");
				$("#serial").addClass("d-none");
			}
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
		<form id="filter_form" name="filter_form" action="/asset/filter" method="POST">
			<input type="hidden" id="action" name="action">
			<input type="hidden" id="filterValue" name="filterValue" value="${filterParam.filterValue}">
			<input type="hidden" id="rowSelected" name="rowSelected">
			<div class="main-content">
				<div class="row">
				    <div class="col-md-8">
					    <div class="form-row">
					    	<div class="label text-left pb-2 col-md-12"><fmt:message key="label.asset.filter.filterby" bundle="${resourceBundle}"/></div>
						</div>
					    <div class="form-row form-group">
							<select class="form-control form-control-sm col-md-4" id="filterType" name="filterType">
								<option value='<fmt:message key="label.asset.filter.renewal" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.renewal.value" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.filter.product" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.product.value" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.filter.serial" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.serial.value" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.filter.customer" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.customer.value" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.filter.address" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.address.value" bundle="${resourceBundle}"/></option>
							</select>
					    	<div class="pt-1 col-md-1"> of </div>
							<select class="form-control form-control-sm col-md-6" id="renewal" name="renewal">
								<option value='<fmt:message key="label.asset.renewal.value1" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.renewal.value1" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.renewal.value2" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.renewal.value2" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.renewal.value3" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.renewal.value3" bundle="${resourceBundle}"/></option>
								<option value='<fmt:message key="label.asset.renewal.value4" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.renewal.value4" bundle="${resourceBundle}"/></option>
							</select>
	                        <select class="form-control form-control-sm col-md-6 d-none" id="product" name="product">
	                            <c:forEach items="${productNumbers}" var="productNumber">
									<option value="${productNumber}"><c:out value="${productNumber}" /></option>
								</c:forEach>
	                        </select>
	                        <select class="form-control form-control-sm col-md-6 d-none" id="customer" name="customer">
	                            <c:forEach items="${customerNames}" var="customerName">
									<option value="${customerName}"><c:out value="${customerName}" /></option>
								</c:forEach>
	                        </select>
	                        <select class="form-control form-control-sm col-md-6 d-none" id="address" name="address">
	                            <c:forEach items="${customerAddressList}" var="customerAddress">
									<option value="${customerAddress}"><c:out value="${customerAddress}" /></option>
								</c:forEach>
	                        </select>
	                        <select class="form-control form-control-sm col-md-6 d-none" id="serial" name="serial">
	                            <c:forEach items="${serialNumbers}" var="serialNumber">
									<option value="${serialNumber}"><c:out value="${serialNumber}" /></option>
								</c:forEach>
	                        </select>
						</div>
				        <div class="text-left form-row form-group pt-3">
				        	<div class="col-md-2"></div>
							<input type="button" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
							<input type="button" class="btn btn-primary ml-5" id="filter" name="filter" value="<fmt:message key="btn.asset.search" bundle="${resourceBundle}"/>">		                    
		                </div>
	                </div><!-- main content - row - col-md-8 -->
				    <div class="col-md-4">
			            <div class="card">
			                <div class="card-body p-0">
			                    <canvas id="chBar"></canvas>
			                </div>
			            </div>
	                </div>
                </div> <!-- main content - row -->
			</div> <!-- main content -->
	        <div class="data-table-section">
	        	<a id="data-table-section"></a>
		        <table id="res-data-table" class="table table-sm table-striped table-bordered">
		            <thead>
		                <tr>
		                    <th>&nbsp;</th>
		                    <th><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.customerName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.address1" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach items="${filteredList}" var="assetDetail" varStatus="loop">
			                <tr>
			                    <td>
									<div class="form-check">
										<input type="checkbox" class="form-check-input" value="${assetDetail.trackName}~${assetDetail.oemName}~${assetDetail.productNumber}~${assetDetail.SKU}~${assetDetail.customerName}~${assetDetail.address1}~${assetDetail.contractNumber}~${assetDetail.quantity}~${assetDetail.serialNumber}~${assetDetail.startDate}~${assetDetail.endDate}" id="checkName${loop.index}" name="checkName${loop.index}">
									</div>
	  		                    </td>
			                    <td>${assetDetail.trackName}</td>
			                    <td>${assetDetail.oemName}</td>
			                    <td>${assetDetail.productNumber}</td>
			                    <td>${assetDetail.SKU}</td>
			                    <td>${assetDetail.customerName}</td>
			                    <td>${assetDetail.address1}</td>
			                    <td>${assetDetail.contractNumber}</td>
			                    <td>${assetDetail.quantity}</td>
			                    <td>${assetDetail.serialNumber}</td>
			                    <td>${assetDetail.startDate}</td>
			                    <td>${assetDetail.endDate}</td>
			                </tr>
						</c:forEach>
		            </tbody>
		       </table>
				<div class="d-flex">
					<div class="d-none d-md-flex flex-md-grow-1 bd-highlight">
						<c:choose>
							<c:when test="${(pagination.currentPage * pagination.recordsPerPage) lt pagination.totalRecords}">
								<span>Showing ${((pagination.currentPage * pagination.recordsPerPage) - (pagination.recordsPerPage-1))} to ${(pagination.currentPage * pagination.recordsPerPage)} of ${pagination.totalRecords} entries</span>
							</c:when>
							<c:when test="${pagination.totalRecords gt 0}">
								<span>Showing ${((pagination.currentPage * pagination.recordsPerPage) - (pagination.recordsPerPage-1))} to ${pagination.totalRecords} of ${pagination.totalRecords} entries</span>
							</c:when>
							<c:otherwise>
								<span>&nbsp;</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="d-flex bd-highlight">
						<ul class="pagination pagination-sm">
							<c:if test="${pagination.currentPage != 1}">
								<li class="page-item"><a class="page-link" href="/asset/filter?filterType=${filterParam.filterType}&filterValue=${filterParam.filterValue}&recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage-1}#data-table-section">Previous</a></li>
							</c:if>
							
							<c:forEach begin="1" end="${pagination.noOfPages}" var="i">
								<c:choose>
									<c:when test="${pagination.currentPage eq i}">
										<li class="page-item active"><a class="page-link"> ${i} <span class="sr-only">(current)</span></a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="/asset/filter?filterType=${filterParam.filterType}&filterValue=${filterParam.filterValue}&recordsPerPage=${pagination.recordsPerPage}&currentPage=${i}#data-table-section">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:if test="${pagination.currentPage lt pagination.noOfPages}">
								<li class="page-item"><a class="page-link" href="/asset/filter?filterType=${filterParam.filterType}&filterValue=${filterParam.filterValue}&recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage+1}#data-table-section">Next</a></li>
							</c:if>              
						</ul>
					</div>
				</div> <!-- pagination -->
				<div class="justify-content-start">
					<input type="button" class="btn" id="update" name="update" value="<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>">		                    
		        </div>
	        </div><!-- data-table-section -->

			<!-- Modal starts-->
			<div class="modal fade" id="editModalInputs" tabindex="-1" role="dialog" aria-hidden="true">
			  <div class="modal-dialog modal-dialog-centered modal" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="editAssetModalTitle"><fmt:message key="modal.title.asset.detail" bundle="${resourceBundle}"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					<div class="row">
					    <div class="col-md-12">
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editTrackName" name="editTrackName" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editOemName" name="editOemName" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editProductNumber" name="editProductNumber" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editSku" name="editSku" readonly="readonly">
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editContractNumber" name="editContractNumber" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-5">
									<input type="text" class="form-control form-control-sm" id="editQuantity" name="editQuantity" readonly="readonly">
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editSerialNumber" name="editSerialNumber" readonly="readonly">
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.customerName" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editCustomerName" name="editCustomerName" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.address1" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editCustomerAddress1" name="editCustomerAddress1" readonly="readonly">
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-5">
		                            <div class="input-group date" id="datepicker1">
										<input type="text" class="form-control form-control-sm" id="editStartDate" name="editStartDate">
		                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
		                            </div>
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-5">
		                            <div class="input-group date" id="datepicker2">
		                                <input type="text" class="form-control form-control-sm" id="editEndDate" name="editEndDate"/>
		                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
		                            </div>
			                    </div>
			                </div>
					    </div>
					</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="btn.asset.close" bundle="${resourceBundle}"/></button>
			        <button id="save-asset" type="button" class="btn btn-primary"><fmt:message key="btn.asset.save" bundle="${resourceBundle}"/></button>
			      </div>
			    </div>
			  </div>
			</div>
			<!-- Modal ends-->
        </form>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
