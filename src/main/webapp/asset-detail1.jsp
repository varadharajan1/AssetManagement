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
			var multipleValue = "Multiple Rows Selected";
			var rowSelected = new Array();
			var updateFlag = false;
			/*$("#insert").click(function() {
				$("#action").val("insert");
				$("#asset_form").submit();
			});*/
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
					$("#editServiceLevel").val(asset[9]);
					$("#editServiceLevelDescription").val(asset[10]);
					$("#editStartDate").val(asset[11]);
					$("#editEndDate").val(asset[12]);
					//$('#editStartDate').datepicker("setDate", asset[11]);
					//$('#editEndDate').datepicker("setDate", asset[12]);
					$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: asset[11], todayHighlight: true, autoclose: true });
					$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: asset[12], todayHighlight: true, autoclose: true });
					$("#editStartDate").datepicker({ format: 'yyyy-mm-dd', startDate: asset[11], todayHighlight: true, autoclose: true });
					$("#editEndDate").datepicker({ format: 'yyyy-mm-dd', startDate: asset[12], todayHighlight: true, autoclose: true });
					$("#editComments").val(asset[13]);
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
					$("#editServiceLevel").val(multipleValue);
					$("#editServiceLevelDescription").val(multipleValue);
					$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					//$("#editStartDate").val(multipleValue);
					//$("#editEndDate").val(multipleValue);
					$("#editComments").val(multipleValue);
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
				$("#asset_form").submit();
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
		<form id="asset_form" name="asset_form" action="/asset/detail" method="POST">
			<input type="hidden" id="action" name="action">
			<input type="hidden" id="rowSelected" name="rowSelected">
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
		                    <th><fmt:message key="label.asset.customer.address2" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.customer.city" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.customer.state" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.customer.zip" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.customer.country" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevel" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevelDescription" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.comments" bundle="${resourceBundle}"/></th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach items="${assetDetailList}" var="assetDetail" varStatus="loop">
			                <tr>
			                    <td>
									<div class="form-check">
										<input type="checkbox" class="form-check-input" value="${assetDetail.trackName}~${assetDetail.oemName}~${assetDetail.productNumber}~${assetDetail.SKU}~${assetDetail.customerName}~${assetDetail.address1}~${assetDetail.contractNumber}~${assetDetail.quantity}~${assetDetail.serialNumber}~${assetDetail.serviceLevel}~${assetDetail.serviceLevelDescription}~${assetDetail.startDate}~${assetDetail.endDate}~${assetDetail.comments}" id="checkName${loop.index}" name="checkName${loop.index}">
									</div>
	  		                    </td>
			                    <td>${assetDetail.trackName}</td>
			                    <td>${assetDetail.oemName}</td>
			                    <td>${assetDetail.productNumber}</td>
			                    <td>${assetDetail.SKU}</td>
			                    <td>${assetDetail.customerName}</td>
			                    <td>${assetDetail.address1}</td>
			                    <td>${assetDetail.address2}</td>
			                    <td>${assetDetail.city}</td>
			                    <td>${assetDetail.state}</td>
			                    <td>${assetDetail.zipCode}</td>
			                    <td>${assetDetail.country}</td>
			                    <td>${assetDetail.contractNumber}</td>
			                    <td>${assetDetail.quantity}</td>
			                    <td>${assetDetail.serialNumber}</td>
			                    <td>${assetDetail.serviceLevel}</td>
			                    <td>${assetDetail.serviceLevelDescription}</td>
			                    <td>${assetDetail.startDate}</td>
			                    <td>${assetDetail.endDate}</td>
			                    <td>${assetDetail.comments}</td>
			                </tr>
						</c:forEach>
		            </tbody>
		       </table>
				<div class="d-flex">
					<div class="d-none d-md-flex flex-md-grow-1 bd-highlight">
						<c:choose>
							<c:when test="${(currentPage * recordsPerPage) lt totalRecords}">
								<span>Showing ${((currentPage * recordsPerPage) - (recordsPerPage-1))} to ${(currentPage * recordsPerPage)} of ${totalRecords} entries</span>
							</c:when>
							<c:when test="${totalRecords gt 0}">
								<span>Showing ${((currentPage * recordsPerPage) - (recordsPerPage-1))} to ${totalRecords} of ${totalRecords} entries</span>
							</c:when>
							<c:otherwise>
								<span>&nbsp;</span>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="d-flex bd-highlight">
						<ul class="pagination pagination-sm">
							<c:if test="${currentPage != 1}">
								<li class="page-item"><a class="page-link" href="/asset/detail?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}#data-table-section">Previous</a></li>
							</c:if>
							
							<c:forEach begin="1" end="${noOfPages}" var="i">
								<c:choose>
									<c:when test="${currentPage eq i}">
										<li class="page-item active"><a class="page-link"> ${i} <span class="sr-only">(current)</span></a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="/asset/detail?recordsPerPage=${recordsPerPage}&currentPage=${i}#data-table-section">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:if test="${currentPage lt noOfPages}">
								<li class="page-item"><a class="page-link" href="/asset/detail?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}#data-table-section">Next</a></li>
							</c:if>              
						</ul>
					</div>
				</div>
				<div class="justify-content-start">
					<input type="button" class="btn mr-5" id="delete" name="delete" value="<fmt:message key="btn.asset.delete" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn ml-5" id="update" name="update" value="<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>">		                    
		        </div>
	        </div>

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
					    <div class="col-md-12 col-12">
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
			                        <strong><fmt:message key="label.asset.detail.serviceLevel" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editServiceLevel" name="editServiceLevel" readonly="readonly">
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.serviceLevelDescription" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editServiceLevelDescription" name="editServiceLevelDescription" readonly="readonly">
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
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.comments" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editComments" name="editComments" readonly="readonly">
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
