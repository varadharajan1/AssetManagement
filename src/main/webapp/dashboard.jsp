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

	<c:set var="intervals" scope="request" value="<%=AssetConstants.INTERVALS%>"/>

	<script>
	$(function () {
		var colors = ['#00C0F1','#00A757','#F69D01','#F8684E',"#8E5EA2","#3CBA9f","#E8C3B9","#5BB95B", "#347AB8", "#EFAD4D", "#D9544F","#F27B53", "#959801", "#FFB400", "#74B648"];
		var colors1 = ["#F27B53", "#959801", "#FFB400", "#74B648"];
		var colors2 = ["#5BB95B", "#347AB8", "#EFAD4D", "#D9544F"];
		var labels = Array();
		var data = Array();
		
		<c:forEach items="${oemNameCounts}" var="oemNameCount">
			labels.push("${oemNameCount.key}");
			data.push("${oemNameCount.value}");
		</c:forEach>

		var chBar = $("#chBar");
		var barChart;
		if (chBar) {
		  barChart = new Chart(chBar, {
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
			layout: {
	            padding: {
	                left: 0,
	                right: 0,
	                top: 0,
	                bottom: 0
	            }
	        },
		    legend: {
		      display: false
		    },
		    title: {
		        display: true,
		        text: 'Assets, based on OEM'
		    },
		    responsive: true,
		    scales: {
		      xAxes: [{
		        barPercentage: 0.9,
	            gridLines: {
	                display:true
	            },
                scaleLabel: {
					display: true,
					labelString: 'OEM'
				}
		      }],
		      yAxes: [{
                ticks: {
                    beginAtZero:true
                },
                gridLines: {
                    display:true
                },
                scaleLabel: {
					display: true,
					labelString: '# of assets'
				}
	          }]
		    },
		    onClick : function (evt, item) {
		    	console.log('legd item', item);
				if (item[0]) {
					var chartData = item[0]['_chart'].config.data;
					var idx = item[0]['_index'];
					//var label = chartData.labels[idx];
					//var value = chartData.datasets[0].data[idx];
					window.location.replace("/asset/filter?filterType=OEM&filterValue="+chartData.labels[idx]);
				}
		    }
		  }
		  });
		}
	});
	</script>

	<script>
		$(function () {
			//var colors = ['#dc3545','#28a745','#adff2f','#007bff'];
			var colors = ['#dc3545'];
			var data = Array();
			var labels = Array();
			labels.push("3 months");
			data.push("${renewalCounts['3 months']}");

			var pieChart = $("#doughnutChart0");
			if (pieChart) {
				new Chart(pieChart,{
				    type: 'doughnut',
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
					    onClick : function (evt, item) {
					    	console.log('legd item', item);
							if (item[0]) {
								var chartData = item[0]['_chart'].config.data;
								var idx = item[0]['_index'];
								//var label = chartData.labels[idx];
								//var value = chartData.datasets[0].data[idx];
								window.location.replace("/asset/filter?filterType=RENEWAL&filterValue="+chartData.labels[idx]);
							}
					    },
						cutoutPercentage: 70,
						layout: {
				            padding: {
				                left: 0,
				                right: 0,
				                top: 0,
				                bottom: 0
				            }
				        },
						legend: {
							display: false,
							position: "bottom"
						},
						title: {
						    display: false,
						    position: "top",
						    text: '3 months'
						},
						responsive: true
					}
				});
			}
		});
	</script>
	<script>
		$(function () {
			var colors1 = ['#28a745'];
			var data1 = Array();
			var labels1 = Array();
			labels1.push("6 months");
			data1.push("${renewalCounts['6 months']}");

			var pieChart1 = $("#doughnutChart1");
			if (pieChart1) {
				new Chart(pieChart1,{
				    type: 'doughnut',
					data: {
					   labels: labels1,
					   datasets: [{
					     data: data1,
					     backgroundColor: colors1,
					     borderColor: colors1,
					     borderWidth: 1
					   }]
					},
					options: {
					    onClick : function (evt, item) {
					    	console.log('legd item', item);
							if (item[0]) {
								var chartData = item[0]['_chart'].config.data;
								var idx = item[0]['_index'];
								//var label = chartData.labels[idx];
								//var value = chartData.datasets[0].data[idx];
								window.location.replace("/asset/filter?filterType=RENEWAL&filterValue="+chartData.labels[idx]);
							}
					    },
						cutoutPercentage: 70,
						layout: {
				            padding: {
				                left: 0,
				                right: 0,
				                top: 0,
				                bottom: 0
				            }
				        },
						legend: {
							display: false,
							position: "bottom"
						},
						title: {
						    display: false,
						    position: "top",
						    text: '6 months'
						},
						responsive: true
					}
				});
			}
		});
	</script>
	<script>
		$(function () {
			var colors2 = ['#adff2f'];
			var data2 = Array();
			var labels2 = Array();
			labels2.push("9 months");
			data2.push("${renewalCounts['9 months']}");

			var pieChart2 = $("#doughnutChart2");
			if (pieChart2) {
				new Chart(pieChart2,{
				    type: 'doughnut',
					data: {
					   labels: labels2,
					   datasets: [{
					     data: data2,
					     backgroundColor: colors2,
					     borderColor: colors2,
					     borderWidth: 1
					   }]
					},
					options: {
					    onClick : function (evt, item) {
					    	console.log('legd item', item);
							if (item[0]) {
								var chartData = item[0]['_chart'].config.data;
								var idx = item[0]['_index'];
								//var label = chartData.labels[idx];
								//var value = chartData.datasets[0].data[idx];
								window.location.replace("/asset/filter?filterType=RENEWAL&filterValue="+chartData.labels[idx]);
							}
					    },
						cutoutPercentage: 70,
						layout: {
				            padding: {
				                left: 0,
				                right: 0,
				                top: 0,
				                bottom: 0
				            }
				        },
						legend: {
							display: false,
							position: "bottom"
						},
						title: {
						    display: false,
						    position: "top",
						    text: '9 months'
						},
						responsive: true
					}
				});
			}
		});
	</script>
	<script>
		$(function () {
			var colors3 = ['#007bff'];

			var data3 = Array();
			var labels3 = Array();
			labels3.push("12 months");
			data3.push("${renewalCounts['12 months']}");

			var pieChart3 = $("#doughnutChart3");
			if (pieChart3) {
				new Chart(pieChart3,{
				    type: 'doughnut',
					data: {
					   labels: labels3,
					   datasets: [{
					     data: data3,
					     backgroundColor: colors3,
					     borderColor: colors3,
					     borderWidth: 1
					   }]
					},
					options: {
					    onClick : function (evt, item) {
					    	console.log('legd item', item);
							if (item[0]) {
								var chartData = item[0]['_chart'].config.data;
								var idx = item[0]['_index'];
								//var label = chartData.labels[idx];
								//var value = chartData.datasets[0].data[idx];
								window.location.replace("/asset/filter?filterType=RENEWAL&filterValue="+chartData.labels[idx]);
							}
					    },
						cutoutPercentage: 70,
						layout: {
				            padding: {
				                left: 0,
				                right: 0,
				                top: 0,
				                bottom: 0
				            }
				        },
						legend: {
							display: false,
							position: "bottom"
						},
						title: {
						    display: false,
						    position: "top",
						    text: '12 months'
						},
						responsive: true
					}
				});
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
		<form id="home_form" name="home_form" action="/asset/home" method="POST">
			<input type="hidden" id="filterValue" name="filterValue">
			<div class="main-content">
				<div class="row pb-4">
					<div class="col-md-12">
						<div class="card-deck">
							<div class="card card-first card-spacing">
								<div class="card-body align-items-center d-flex">
									<div class="float-left"><i class="fa fa-4x fa-th-list" aria-hidden="true"></i></div>
									<div class="pl-3"><span class="box-text-size">${totalNoOfAssets} Assets</span></div>
								</div>
								<div class="card-footer"><a href="/asset/detail">view detail</a></div>
							</div>
							<div class="card card-second card-spacing">
								<div class="card-body align-items-center d-flex">
									<div class="float-left"><i class="fa fa-4x fa-tags" aria-hidden="true"></i></div>
									<div class="pl-3"><span class="box-text-size">${totalNoOfTracks} Tracks</span></div>
								</div>
								<div class="card-footer"><a href="/asset/detail">view detail</a></div>
							</div>
							<div class="card card-third card-spacing">
								<div class="card-body align-items-center d-flex">
									<div class="float-left"><i class="fa fa-4x fa fa-building" aria-hidden="true"></i></div>
									<div class="pl-3"><span class="box-text-size">${totalNoOfOpcos} OPCOs</span></div>
								</div>
								<div class="card-footer"><a href="/asset/detail">view detail</a></div>
							</div>
							<div class="card card-fourth card-spacing">
								<div class="card-body align-items-center d-flex">
									<div class="float-left"><i class="fa fa-4x fa-cog" aria-hidden="true"></i></div>
									<div class="pl-3"><span class="box-text-size">${totalNoOfOEM} OEMs</span></div>
								</div>
								<div class="card-footer"><a href="/asset/detail">view detail</a></div>
							</div>
		                </div>
	                </div>
                </div>
 				<div class="row">
					<div class="col-md-6"><p class="label-text">Assets expiration</p></div>
                </div>
 				<div class="row">
				    <div class="col-md-6">
		 				<div class="row">
							<c:forEach items="<%=AssetConstants.INTERVALS%>" var="interval" varStatus="loop">
						    <div class="col-md-6">
								<div class="card card-spacing ">
									<div class="card-body card-${loop.index} align-items-center d-flex justify-content-center">
										<canvas id="doughnutChart${loop.index}"></canvas>
										<div class="absolute-center text-center">
											<p class="label-text"><c:out value="${interval}"/></p>
											<p class="label-text"><c:out value="${renewalCounts[interval]}"/></p>
										</div>
									</div>
								</div>
				            </div>
							</c:forEach>
		                </div>
	                </div>
				    <div class="col-md-6 align-self-md-center">
			            <div class="card card-spacing">
			                <div class="card-body pt-0">
			                    <canvas id="chBar"></canvas>
			                </div>
			            </div>
	                </div>
                </div> <!-- main content - row -->
			</div> <!-- main content -->
        </form>
	</main>
	<footer class="footer">
		<%@ include file = "footer.jspf" %>
	</footer>    
</body>
</html>
