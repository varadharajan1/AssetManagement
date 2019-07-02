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
		            { "data": "opcoName", "visible":true, "orderable": false },
		            { "data": "deviceName", "visible":true, "orderable": false },
		            { "data": "serialNumber", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "supportEndDate", "visible":true, "orderable": true },
		            { "data": "businessSegment", "visible":true, "orderable": false },
		            { "data": "oemName", "visible":true, "orderable": false },
		            { "data": "supportStartDate", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "contractedThrough", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "serviceLevel", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "productNumber", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "productDescription", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "quantity", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "contractNumber", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "serviceLevelDescription", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "sku", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "eolDate", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "purchasedDate", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "purchasedVendor", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "installedDate", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "purchasedCost", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "deployedLocation", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "address1", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "address2", "visible":true, "orderable": false, defaultContent: '' },
		            { "data": "city", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "state", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "zipCode", "visible":true, "orderable": false, defaultContent: ''  },
		            { "data": "country", "visible":true, "orderable": false, defaultContent: ''  }
		        ],
		        select: { style: 'multi', selector: 'td.select-checkbox' },
		        "order": [[9, 'asc']],
		    });

			var updateFlag = true;
			var multipleValue = "Multiple Rows Selected";
			var rowSelected = new Array();
			$("#update").click(function() {
			    var data = table.rows( {selected:  true} ).data();
			    rowSelected = new Array();
				$.each(data, function( key, value ) {
					rowSelected.push(JSON.stringify(value));
				});
				if(rowSelected.length <= 0){
		    		updateFlag = false;
		    	}
				if(updateFlag == true){
					$("#asset_form").attr("action", "/asset/entry");
					$("#action").val("");
					$("#rowSelected").val(rowSelected);
					$("#asset_form").submit();
				}
			});

			var deleteFlag = true;
			$("#delete").click(function() {
			    var data = table.rows( {selected:  true} ).data();
			    rowSelected = new Array();
				$.each(data, function( key, value ) {
					rowSelected.push(JSON.stringify(value));
				});
				if(rowSelected.length <= 0){
					deleteFlag = false;
		    	}
				if(deleteFlag == true){
					$("#action").val("delete");
					$("#rowSelected").val(rowSelected);
					$("#asset_form").submit();
		    	}
			});

			$("select#currentPage").val("${pagination.currentPage}");
			$("select#currentPage").change(function() {
				$("#action").val("");
				$("#recordsPerPage").val("${pagination.recordsPerPage}");
				$("#asset_form").submit();
			});
		});
	</script>
	<script>
	$(function () {
		$('#export').click(function () {
			var post_url = '/asset/exportData';
			var action = '';

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
			json_data.filterValue = $("#filterValue").val();
		
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
		<form id="asset_form" name="asset_form" action="/asset/detail" method="POST">
			<input type="hidden" id="action" name="action">
			<input type="hidden" id="filterType" name="filterType">
			<input type="hidden" id="filterValue" name="filterValue">
			<input type="hidden" id="rowSelected" name="rowSelected">
	        <div class="data-table-section">
	        	<a id="data-table-section"></a>
		        <table id="res-data-table" class="table table-sm table-striped table-bordered dt-responsive nowrap">
		            <thead>
		                <tr>
		                    <th>&nbsp;</th>
		                    <th>&nbsp;</th>
		                    <th><fmt:message key="label.asset.detail.trackName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.opcoName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.deviceName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serialNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.endDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.businessSegment" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.oemName" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.startDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.contractedThrough" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevel" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.productNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.description" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.quantity" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.contractNumber" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.serviceLevelDescription" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.sku" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.eolDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.purchasedDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.purchasedVendor" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.installedDate" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.detail.purchasedCost" bundle="${resourceBundle}"/></th>
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
							<li class="page-item <c:if test='${pagination.currentPage == 1}'>disabled</c:if>"><a class="page-link" href="/asset/detail?recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage-1}#data-table-section">Previous</a></li>
							<li class="page-item">
								<select class="form-control form-control-sm" id="currentPage" name="currentPage">
									<c:forEach begin="1" end="${pagination.noOfPages}" var="i">
										<option value="${i}">${i}</option>
									</c:forEach>
								</select>
							</li>
							<li class="page-item <c:if test='${pagination.currentPage ge pagination.noOfPages}'>disabled</c:if>"><a class="page-link" href="/asset/detail?recordsPerPage=${pagination.recordsPerPage}&currentPage=${pagination.currentPage+1}#data-table-section">Next</a></li>
						</ul>
					</div>
				</div> <!-- pagination -->
				<div class="justify-content-start">
					<input type="button" class="btn" id="delete" name="delete" value="<fmt:message key="btn.asset.delete" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn ml-5" id="update" name="update" value="<fmt:message key="btn.asset.update" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn ml-5" id="export" name="export" value="<fmt:message key="btn.asset.export" bundle="${resourceBundle}"/>">
		        </div>
	        </div>

        </form>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
