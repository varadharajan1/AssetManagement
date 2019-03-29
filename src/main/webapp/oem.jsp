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
			$("#insert").click(function() {
				$("#action").val("insert");
				$("#oem_form").submit();
			});
			$("#update").click(function() {
				$("#action").val("update");
				$("#rowSelected").val(rowSelected);
				if(rowSelected.length == 1){
					var oem = unescape(rowSelected[0]).split("~");
					$("#editOEMName").val(oem[0]);
					$("#editOEMDescription").val(oem[1]);
					updateFlag = true;
				}else if(rowSelected.length > 1){
					$("#editOEMName").val(multipleValue);
					$("#editOEMDescription").val(multipleValue);
					updateFlag = true;
				}else if(rowSelected.length <= 0){
		    		updateFlag = false;
		    	}
				if(updateFlag == true){
					$('#editModalInputs').modal('show');
				}
			});
			$("#save-oem").click(function() {
				$("#action").val("update");
				$("#rowSelected").val(rowSelected);
				$("#oem_form").submit();
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
		<form id="oem_form" name="oem_form" action="/asset/oem" method="POST">
			<input type="hidden" id="action" name="action">
			<input type="hidden" id="rowSelected" name="rowSelected">
			<div class="main-content">

			    <div class="text-right form-row form-group required">
			    	<label class="pl-1 control-label col-md-2" for="oemName"><fmt:message key="label.asset.oem.name" bundle="${resourceBundle}"/></label>
			    	<input type="text" class="form-control form-control-sm col-md-3" id="oemName" name="oemName">
				</div>
			    <div class="text-right form-row form-group">
					<label class="pl-1 col-md-2" for="oemDescription"><fmt:message key="label.asset.oem.description" bundle="${resourceBundle}"/></label>
					<input type="text" class="form-control form-control-sm col-md-6" id="oemDescription" name="oemDescription">		                    
				</div>	                
		        <div class="form-group pt-3">
					<input type="reset" class="btn btn-secondary mr-5" id="clear" name="clear" value="<fmt:message key="btn.asset.clear" bundle="${resourceBundle}"/>">		                    
					<input type="button" class="btn btn-primary ml-5" id="insert" name="insert" value="<fmt:message key="btn.asset.submit" bundle="${resourceBundle}"/>">		                    
                </div>
                
			</div>
	        <div class="data-table-section">
	        	<a id="data-table-section"></a>
		        <table id="res-data-table" class="table table-sm table-striped table-bordered">
		            <thead>
		                <tr>
		                    <th>&nbsp;</th>
		                    <th><fmt:message key="label.asset.oem.name" bundle="${resourceBundle}"/></th>
		                    <th><fmt:message key="label.asset.oem.description" bundle="${resourceBundle}"/></th>
		                </tr>
		            </thead>
		            <tbody>
						<c:forEach items="${oemList}" var="oem" varStatus="loop">
			                <tr>
			                    <td>
									<div class="form-check">
										<input type="checkbox" class="form-check-input" value="${oem.oemName}~${oem.oemDescription}" id="checkName${loop.index}" name="checkName${loop.index}">
									</div>
	  		                    </td>
			                    <td>${oem.oemName}</td>
			                    <td>${oem.oemDescription}</td>
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
								<li class="page-item"><a class="page-link" href="/asset/oem?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}#data-table-section">Previous</a></li>
							</c:if>
							
							<c:forEach begin="1" end="${noOfPages}" var="i">
								<c:choose>
									<c:when test="${currentPage eq i}">
										<li class="page-item active"><a class="page-link"> ${i} <span class="sr-only">(current)</span></a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="/asset/oem?recordsPerPage=${recordsPerPage}&currentPage=${i}#data-table-section">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:if test="${currentPage lt noOfPages}">
								<li class="page-item"><a class="page-link" href="/asset/oem?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}#data-table-section">Next</a></li>
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
			  <div class="modal-dialog modal-dialog-centered" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="editOEMModalTitle"><fmt:message key="modal.title.asset.oem" bundle="${resourceBundle}"/></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
					<div class="row">
					    <div class="col-md-12 col-12">
			                <div class="row p-sm-2">
			                    <div class="col-sm-3 text-right">
			                        <strong><fmt:message key="label.asset.oem.name" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-7">
									<input type="text" class="form-control form-control-sm" id="editOEMName" name="editOEMName" readonly="readonly">		                    
			                    </div>
			                </div>
			                <div class="row p-sm-2">
			                    <div class="col-sm-3 text-right">
			                        <strong><fmt:message key="label.asset.oem.description" bundle="${resourceBundle}"/></strong>
			                    </div>
			                    <div class="col-sm-9">
									<input type="text" class="form-control form-control-sm" id="editOEMDescription" name="editOEMDescription" readonly="readonly">
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
