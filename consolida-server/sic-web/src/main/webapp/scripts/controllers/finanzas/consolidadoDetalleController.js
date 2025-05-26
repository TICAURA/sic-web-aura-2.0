'use strict';
angular
		.module('theme.core.templates')
		.controller(
				'consolidadoDetalleController',
				function($scope, $location, $timeout, $http, CONFIG, $bootbox,
						$log, $window, pinesNotifications, NgTableParams) {

					$scope.data = {};
					
					$scope.init = function() {
						$scope.mostrarDetalleDispersion=false;
						// var aniomes=$scope.getAnioMesDia();
						$http.get(CONFIG.APIURL+ "/conciliacion/pagos/cargaInicialDispersion.json")
								.then(function(response) {
									$scope.catCelula = response.data.catCelula;

								}, function(data) {
									console.log("error --> " + data);
								});
					};

					$scope.init();
					
					

					$scope.consultar = function(){
						$scope.orden = {};
						$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
						$scope.orden.fechaFin = new Date($scope.data.finDate);
						//var centroCostos = new Array($scope.data.catCelula);
						//$scope.orden.listaCentroCostos = centroCostos;
						$scope.orden.catCelula=$scope.data.catCelula
						if ($scope.data.inicioDate == undefined || $scope.data.finDate == undefined) {
							pinesNotifications
									.notify({
										title : 'Error C&aacute;lculo ',
										text : 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
										type : 'error'
									});

						} else if ($scope.data.inicioDate > $scope.data.finDate) {
							pinesNotifications
									.notify({
										title : 'Error C&aacute;lculo ',
										text : 'La fecha inicio, no puede ser mayor a la fecha fin',
										type : 'error'
									});
						} else {
							$http.post(CONFIG.APIURL+ "/conciliacion/pagos/consultarParaDispersion.json",$scope.orden).then(function(response) {
							     // console.log("response --> " + response);				
								$scope.mostrarDetalleDispersion=true;
												$scope.gridlistCC = response.data.listCC;
												$scope.tableDetalleCCParams = new NgTableParams(
														{
															page : 1,
															count : 10
														},
														{
															data : $scope.gridlistCC
														});

											},
											function(data) {
											      console.log("error --> " + response);
										             
											});

						}

					};

					$scope.dispersar = function() {	
						$scope.orden = {};
						$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
						$scope.orden.fechaFin = new Date($scope.data.finDate);
						$scope.orden.catCelula=$scope.data.catCelula
						$http.post(CONFIG.APIURL+ "/conciliacion/pagos/consultarParaDispersion.json",$scope.orden).then(function(response) {
						
						//	$http.get(CONFIG.APIURL+ "/conciliacion/pagos/dispersar.json").then(function(response) {
							$scope.mostrarDetalleDispersion=true;
						//}
							$scope.gridlistCC = response.data.listCC;
							$scope.tableDetalleCCParams = new NgTableParams(
									{
										page : 1,
										count : 10
									},
									{
										data : $scope.gridlistCC
									});

						},
						function(data) {
							console
									.log("error --> "
											+ data);
						});
					}

				});