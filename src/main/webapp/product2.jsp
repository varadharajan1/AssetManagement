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

	<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css" type="text/css" />
	<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.7/css/select.dataTables.min.css" type="text/css" />

	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>
	<script src="https://cdn.datatables.net/select/1.2.7/js/dataTables.select.min.js"></script>
	
	<script>
		var dataSet = JSON.stringify(<%=request.getAttribute("dataSet")%>);
		var dataObject = JSON.parse(dataSet);
		var editor;
		
		$(function () {
			$("#submit").click(function() {
				$("action").val("insert");
				$("#product-form").submit();
			});
			
			var table = $('#res-data-table').DataTable( {
		    	"responsive": true,
		        "searching": false,
		        "data": dataObject,
		        "columns": [
		            { "visible":true, "orderable": false, className: "select-checkbox" },
		            { "data": "productNumber", "visible":true, "orderable": true },
		            { "data": "productDescription", "visible":true, "orderable": false },
		            { "data": "SKU", "visible":true, "orderable": false, className: "editable-field" }
		        ],
		        select: { style: 'multi', selector: 'td:first-child' },
		        "order": [[1, 'asc']],
		    });
			
			$('#res-data-table tbody').on('click', 'td:first-child', function () {
			    var data = table.row( {selected:  true} ).data();
				$.each(data, function( index, value ) {
					console.log( index + ": " + value );
				});
			});
		});
	</script>
</head>
<body>
	<header>
		  <nav class="navbar navbar-expand-md bg-red">
			<a class="navbar-brand" href="#">
				<img class="d-none d-md-block" src="${pageContext.servletContext.contextPath}/images/logo-desktop.png"><img class="d-block d-md-none" src="${pageContext.servletContext.contextPath}/images/logo-mobile.png">
			</a>
			<div class="text-white col">
				<h2><fmt:message key="page.title.asset.management" bundle="${resourceBundle}"/></h2>
			</div>
			<!--
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
			  <span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarCollapse">
			  <ul class="navbar-nav mr-auto">
				<li class="nav-item active">
				  <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item">
				  <a class="nav-link" href="#">Link</a>
				</li>
				<li class="nav-item">
				  <a class="nav-link disabled" href="#">Disabled</a>
				</li>
			  </ul>
			  <form class="form-inline mt-2 mt-md-0">
				<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			  </form>
			</div>
			-->
		  </nav>
	</header>
	<main role="main" class="container">
		<div>
		    <c:if test="${! empty( message ) }">
				<div class="alert alert-success" id="success-alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong><c:out value="${message}" /></strong>
				</div>
		    </c:if>
		</div>
		<div class="main-content">
			<form id="product-form" name="product-form" action="/asset/product" method="POST">
				<input type="hidden" class="form-control" id="action" name="action">
		        <div class="row px-lg-4 pt-lg-4 pt-3">
		            <div class="col-12">
		                <div class="row p-sm-2">
		                    <div class="col-sm-2 pb-2">
		                        <strong><fmt:message key="label.asset.product.number" bundle="${resourceBundle}"/></strong>
		                    </div>
		                    <div class="col-sm-4">
								<input type="text" class="form-control" id="productNumber" name="productNumber">		                    
		                    </div>
		                </div>
		                <div class="row p-sm-2">
		                    <div class="col-sm-2 pb-2">
		                        <strong><fmt:message key="label.asset.product.description" bundle="${resourceBundle}"/></strong>
		                    </div>
		                    <div class="col-sm-8">
								<input type="text" class="form-control" id="productDescription" name="productDescription">
		                    </div>
		                </div>
		                <div class="row p-sm-2">
		                    <div class="col-sm-2 pb-2">
		                        <strong><fmt:message key="label.asset.product.sku" bundle="${resourceBundle}"/></strong>
		                    </div>
		                    <div class="col-sm-4">
								<input type="text" class="form-control" id="sku" name="sku">		                    
		                    </div>
		                </div>
		                <div class="row p-4">
		                    <div class="col-sm-2">
		                    </div>
		                    <div class="col-sm-8">
								<input type="button" class="btn btn-secondary mr-5" id="cancel" name="cancel" value="<fmt:message key="btn.asset.cancel" bundle="${resourceBundle}"/>">		                    
								<input type="button" class="btn btn-primary ml-5" id="submit" name="submit" value="<fmt:message key="btn.asset.submit" bundle="${resourceBundle}"/>">		                    
		                    </div>
		                </div>
		            </div>
		        </div>
	        </form>
		</div>
        <div>
	        <table id="res-data-table" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	            <thead>
	                <tr>
	                    <th></th>
	                    <th><fmt:message key="label.asset.product.number" bundle="${resourceBundle}"/></th>
	                    <th><fmt:message key="label.asset.product.description" bundle="${resourceBundle}"/></th>
	                    <th><fmt:message key="label.asset.product.sku" bundle="${resourceBundle}"/></th>
	                </tr>
	            </thead>
	       </table> 
        </div>
	</main>
	<footer class="footer">
		<div class="container text-center">
			<span class="text-muted"><fmt:message key="page.footer.asset.management" bundle="${resourceBundle}"/></span>
		</div>
	</footer>    
</body>
</html>
