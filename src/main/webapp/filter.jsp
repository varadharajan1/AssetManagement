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

	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" type="text/css" />
	<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.7/css/select.dataTables.min.css" type="text/css" />

	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>
	<script src="https://cdn.datatables.net/select/1.2.7/js/dataTables.select.min.js"></script>
	
	<script>
		var dataSet = JSON.stringify(<%=request.getAttribute("dataSet")%>);
		var dataObject = JSON.parse(dataSet);
		var table;
		$(function () {
			table = $('#res-data-table').DataTable( {
		        responsive: {
		            details: {
		                type: 'column'
		            }
		        },		    	
		        "searching": false,
		        "paging": false,
		        "info": false,
		        "data": dataObject,
		        "columns": [
		            { "visible":true, "orderable": false, defaultContent: '', className: "control" },
		            { "visible":true, "orderable": false, defaultContent: '', className: "select-checkbox" },
		            { "data": "trackName", "visible":true, "orderable": false },
		            { "data": "oemName", "visible":true, "orderable": false },
		            { "data": "opcoName", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "deviceName", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "serialNumber", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "startDate", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "endDate", "visible":true, "orderable": true },
		            { "data": "serviceLevel", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "productNumber", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "productDescription", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "quantity", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "contractNumber", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "serviceLevelDescription", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "sku", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedLocation", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedAddress1", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedAddress2", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "deployedCity", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedState", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedZipCode", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "deployedCountry", "visible":true, "orderable": false, defaultContent: ''  }
		        ],
		        select: { style: 'multi', selector: 'td.select-checkbox' },
		        "order": [[8, 'asc']],
		    });

			var updateFlag = false;
			var multipleValue = "Multiple Rows Selected";
			var rowSelected = new Array();
			$("#update").click(function() {
			    var data = table.rows( {selected:  true} ).data();
			    rowSelected = new Array();
				$.each(data, function( key, value ) {
					rowSelected.push(JSON.stringify(value));
				});
				if(rowSelected.length == 1){
					var asset = data[0];
                    $("#editTrackName").val(asset.trackName);
					$("#editOpcoName").val(asset.opcoName);
					$("#editDeviceName").val(asset.deviceName);
					$("#editOemName").val(asset.oemName);
					$("#editSerialNumber").val(asset.serialNumber);
					$("#editServiceLevel").val(asset.serviceLevel);
					$("#editDatepicker1").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#editDatepicker1").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#editStartDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$("#editEndDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
					$('#editStartDate').datepicker("setDate", asset.startDate);
					$('#editEndDate').datepicker("setDate", asset.endDate);
					updateFlag = true;
				}else if(rowSelected.length > 1){
                    $("#editTrackName").val(multipleValue);
					$("#editOpcoName").val(multipleValue);
					$("#editDeviceName").val(multipleValue);
					$("#editOemName").val(multipleValue);
					$("#editSerialNumber").val(multipleValue);
					$("#editServiceLevel").val(multipleValue);
					$("#editDatepicker1").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#editDatepicker2").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#editStartDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
					$("#editEndDate").datepicker({ format: 'yyyy-mm-dd', startDate: "today", todayHighlight: true, autoclose: true });
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
		});
	</script>

	<script>
		$(function () {
			$("#datepicker1").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
			$("#datepicker2").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
			$("#fromDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
			$("#toDate").datepicker({ format: 'yyyy-mm-dd', todayHighlight: true, autoclose: true });
			
			$("#clear").click(function() {
				$("#renewal").removeClass("d-none");
				$("#labelFilterValue").removeClass("d-none");
				$("#filterValue").addClass("d-none");
				$("#expirationDate").addClass("d-none");
				$("select#filterType").val("RENEWAL");
				$("#filter_form").reset();
			});
			
			$("#filterValue").on('input',function() {
				var filterType = $("select#filterType").val();
				var filterValue = $("#filterValue").val();
		    	$(".autocomplete-items").empty();
		    	$(".autocomplete-items").addClass("d-none");
				if(filterValue && filterValue.length > 1){
					var post_url = '/asset/rest/filter/type';
					var json_data = {};
					json_data.filterType = filterType;
					json_data.filterValue = filterValue;
				    $.ajax({
				        url : post_url,
				        type: 'POST',
				        data : JSON.stringify(json_data),
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						cache: false,
						processData:false
				    }).done(function(response){ 
				    	$(".autocomplete-items").removeClass("d-none");
				    	$.each(response.data.filteredColumnList, function( index, value ) {
				    		$(".autocomplete-items").append("<div>" + value + "</div>");
				    	});
				    }).fail(function(jqXHR, textStatus, errorThrown){
				    	console.log("textStatus: "+textStatus+" : "+errorThrown);
				    });
				}
			});
			
			$("select#filterType").change(function() {
				var filterType = $("select#filterType").val();
				if((filterType == 'RENEWAL')){
					$("#renewal").removeClass("d-none");
					$("#labelFilterValue").removeClass("d-none");
					$("#filterValue").val("");
					$("#filterValue").addClass("d-none");
					$("#fromDate").val("");
					$("#toDate").val("");
					$("#expirationDate").addClass("d-none");
				}else if(filterType == 'DATE'){
					$("#expirationDate").removeClass("d-none");
					$("#labelFilterValue").addClass("d-none");
					$("#filterValue").val("");
					$("#filterValue").addClass("d-none");
					$("#renewal").val("");
					$("#renewal").addClass("d-none");
				}else{
					$("#filterValue").removeClass("d-none");
					$("#labelFilterValue").removeClass("d-none");
					$("#renewal").addClass("d-none");
					$("#expirationDate").addClass("d-none");
				}
			});

			$("select#currentPage").change(function() {
				$("#filterType").val("${filterParam.filterType}");
				$("#filterValue").val("${filterParam.filterValue}");
				$("#fromDate").val("${filterParam.startDate}");
				$("#toDate").val("${filterParam.endDate}");
				$("#recordsPerPage").val("${pagination.recordsPerPage}");
				$("#filter_form").submit();
			});
			
			$("#filter").click(function() {
				$("#action").val("filter");
				$("#filter_form").submit();
			});
			
			var filterType = "${filterParam.filterType}";
			$("select#filterType").val(filterType);
			$("select#currentPage").val("${pagination.currentPage}");
			
			if((filterType == 'RENEWAL')){
				$("#renewal").val("${filterParam.filterValue}");
				$("#renewal").removeClass("d-none");
				$("#labelFilterValue").removeClass("d-none");
				$("#filterValue").addClass("d-none");
				$("#expirationDate").addClass("d-none");
			}else if(filterType == 'DATE'){
				$('#fromDate').datepicker("setDate", "${filterParam.startDate}");
				$('#toDate').datepicker("setDate", "${filterParam.endDate}");
				$("#expirationDate").removeClass("d-none");
				$("#labelFilterValue").addClass("d-none");
				$("#filterValue").addClass("d-none");
				$("#renewal").addClass("d-none");
			}else{
				$("#filterValue").val("${filterParam.filterValue}");
				$("#filterValue").removeClass("d-none");
				$("#labelFilterValue").removeClass("d-none");
				$("#renewal").addClass("d-none");
				$("#expirationDate").addClass("d-none");
			}
			
			$(document).on("click", function (e) {
		    	var container = $(".autocomplete");
		    	if (!container.is(e.target) && container.has(e.target).length === 0){
		    		$(".autocomplete-items").addClass("d-none");
		    	}
		    });

			$(".autocomplete-items").on('click', 'div', function() {
		        var value = $(this).text();
		        $('#filterValue').val(value);
		        $(".autocomplete-items").addClass("d-none");
		    });
		    
		});
	</script>

	<script>
	$(function () {
		$('#export').click(function () {
			var post_url = '/asset/exportData';
			var action = '';
		    var filterType = $("#filterType").val();

		    var json_data = {};
		    var data = table.rows( {selected:  true} ).data();
		    rowSelected = new Array();
			$.each(data, function( key, value ) {
				rowSelected.push(JSON.stringify(value));
			});
			if(rowSelected.length > 0){
				action = "export_selected";
				json_data.rowSelected = "["+rowSelected.toString()+"]";
	    	}else{
				action = "export_all";
	    	}
			json_data.action = action;
			json_data.filterType = $("#filterType").val();
			if(filterType == 'RENEWAL'){
				json_data.filterValue = $("#renewal").val();
			}else{
				json_data.filterValue = $("#filterValue").val();
			}
			json_data.fromDate = $("#fromDate").val();
			json_data.toDate = $("#toDate").val();
		
			console.log("json: "+JSON.stringify(json_data));
			
		    $.ajax({
				url : post_url,
				type: 'POST',
				data : JSON.stringify(json_data),
				dataType: 'text',
				contentType: 'application/json; charset=utf-8',
				cache: false,
				processData:false,
		        xhrFields: {
		            responseType: 'text'
		        }
		    }).done(function(response){ 
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(new Blob([response], { type: 'application/csv;charset=utf8' }));
	            a.href = url;
	            a.download = action +'.csv';
	            a.click();
	            window.URL.revokeObjectURL(url);
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
		<form id="filter_form" name="filter_form" action="/asset/filter" method="POST">
			<input type="hidden" id="action" name="action">
			<input type="hidden" id="rowSelected" name="rowSelected">
			<div class="main-content">
		    	<div class="form-row">
				    <div class="form-group col-md-3 pr-3 text-left">
				    	<label for="filterType"><fmt:message key="label.asset.filter.filterby" bundle="${resourceBundle}"/></label>
						<select class="form-control form-control-sm" id="filterType" name="filterType">
							<option value='<fmt:message key="label.asset.filter.renewal" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.renewal.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.oem" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.oem.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.product" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.product.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.serial" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.serial.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.location" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.location.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.address" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.address.value" bundle="${resourceBundle}"/></option>
							<option value='<fmt:message key="label.asset.filter.date" bundle="${resourceBundle}"/>'><fmt:message key="label.asset.filter.date.value" bundle="${resourceBundle}"/></option>
						</select>
					</div>
				    <div class="form-group col-md-3 text-left">
				    	<label id="labelFilterValue" for="filterValue">&nbsp;</label>
						<select class="form-control form-control-sm" id="renewal" name="renewal">
						<c:forEach items="<%=AssetConstants.INTERVALS%>" var="interval">
							<option value='${interval}'>${interval}</option>
						</c:forEach>
						</select>
						<div class="autocomplete">
							<input type="text" class="form-control form-control-sm " id="filterValue" name="filterValue" autocomplete="off">
							<div class="autocomplete-items d-none">
							</div>
						</div>
		                <div id="expirationDate" class="pt-3">
			                <div class="form-row">
			                    <div class="col-sm-2 text-right">
			                        <strong><fmt:message key="label.asset.filter.from" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-7">
		                            <div class="input-group date" id="datepicker1">
										<input type="text" class="form-control form-control-sm" id="fromDate" name="fromDate" autocomplete="off">
		                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
		                            </div>
			                    </div>
		                    </div>
			                <div class="form-row">
			                    <div class="col-sm-2 text-right">
			                        <strong><fmt:message key="label.asset.filter.to" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-7">
		                            <div class="input-group date" id="datepicker2">
		                                <input type="text" class="form-control form-control-sm" id="toDate" name="toDate" autocomplete="off"/>
		                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
		                            </div>
			                    </div>
			                </div>
		                </div>
					</div>
				</div>
		        <div class="text-left form-row form-group pt-3">
		        	<div class="col-md-2"></div>
					<input type="button" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn btn-primary ml-5" id="filter" name="filter" value="<fmt:message key="btn.asset.search" bundle="${resourceBundle}"/>">		                    
                </div>
			</div> <!-- main content -->
	        <div class="data-table-section">
	        	<a id="data-table-section"></a>
		        <table id="res-data-table" class="table table-sm table-striped table-bordered dt-responsive nowrap">
		            <thead>
		                <tr>
		                    <th>&nbsp;</th>
		                    <th>&nbsp;</th>
		                    <th><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.opcoName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deviceName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevel" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.productDescription" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevelDescription" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedLocation" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedAddress1" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedAddress2" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedCity" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedState" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedZipCode" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deployedCountry" bundle="${resourceBundle}"/></th>
		                </tr>
		            </thead>
		       </table>
				<div class="d-flex pt-3">
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
							<li class="page-item <c:if test='${pagination.currentPage == 1}'>disabled</c:if>"><a class="page-link" href="/asset/filter?filterType=${filterParam.filterType}&filterValue=${filterParam.filterValue}&fromDate=${filterParam.startDate}&toDate=${filterParam.endDate}&recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage-1}#data-table-section">Previous</a></li>
							<li class="page-item">
								<select class="form-control form-control-sm" id="currentPage" name="currentPage">
									<c:forEach begin="1" end="${pagination.noOfPages}" var="i">
										<option value="${i}">${i}</option>
									</c:forEach>
								</select>
							</li>
							<li class="page-item <c:if test='${pagination.currentPage ge pagination.noOfPages}'>disabled</c:if>"><a class="page-link" href="/asset/filter?filterType=${filterParam.filterType}&filterValue=${filterParam.filterValue}&fromDate=${filterParam.startDate}&toDate=${filterParam.endDate}&recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage+1}#data-table-section">Next</a></li>
						</ul>
					</div>
				</div> <!-- pagination -->
				<div class="justify-content-start">
					<input type="button" class="btn disabled" id="delete" name="delete" value="<fmt:message key="btn.asset.delete" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn ml-5" id="update" name="update" value="<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn ml-5" id="export" name="export" value="<fmt:message key="btn.asset.export" bundle="${resourceBundle}"/>">
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
			                        <strong><fmt:message key="label.asset.detail.opcoName" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editOpcoName" name="editOpcoName" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.deviceName" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-8">
									<input type="text" class="form-control form-control-sm" id="editDeviceName" name="editDeviceName" readonly="readonly">		                    
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
			                        <strong><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-5">
		                            <div class="input-group date" id="editDatepicker1">
										<input type="text" class="form-control form-control-sm" id="editStartDate" name="editStartDate" autocomplete="off"/>
		                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
		                            </div>
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-4 text-right">
			                        <strong><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-5">
		                            <div class="input-group date" id="editDatepicker2">
		                                <input type="text" class="form-control form-control-sm" id="editEndDate" name="editEndDate" autocomplete="off"/>
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
