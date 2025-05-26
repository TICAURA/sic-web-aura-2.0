'use strict';
angular
		.module('theme.core.templates')
		.controller(
				'resumenSaldosController',
				function($scope, $location, $timeout, $http, CONFIG, $bootbox,
						$log, $window, pinesNotifications, NgTableParams) {

					$scope.data = {};

					$scope.saldoTotal = 0.00;
					$scope.saldoTotalF = 0.00;
					$scope.saldoTotalOI = 0.00;
					$scope.fechaSaldo = "";
					$scope.totalSaldoF = 0.00;
					$scope.totalSaldoIBF = 0.00;
					$scope.totalSaldoIF = 0.00;
					$scope.totalSaldoCS = 0.00;
					$scope.totalSaldoSCF = 0.00;
					$scope.totalSaldoCMF = 0.00;
					
					$scope.totalSaldoOI = 0.00;
					$scope.totalSaldoIBOI = 0.00;
					$scope.totalSaldoIFOI = 0.00;
					$scope.totalSaldoCSOI = 0.00;
					$scope.totalSaldoSCOI = 0.00;
					
					$scope.fechaSaldo = new Date();
					
					$scope.validaSaldo =false;

					$scope.tableSaldoParams = {}

					$scope.init = function() {
						$scope.data.catCelula = {};
						$scope.tipoProvision = "6";
				

					};
					
					$("#descarga").css('display','none');

					$scope.resumenSaldo = function() {
						
						
						$http.post(CONFIG.APIURL+ "/ppp/finanzas/resumenSaldos.json").then(function(resp) {
									$scope.fechaSaldo = resp.data.fechaSaldo;
									$scope.resumen = resp.data.saldos;
									
									if (resp.data.error) {
										
										pinesNotifications
												.notify({
													title : 'Error al obtener saldos ',
													text : 'EL servidor de STP, no se encuentra disponible, favor de intentarlo más tarde.',
													type : 'warning'
												});
									} else {
										$scope.totalSaldoF = resp.data.saldos[0].montoFondo;
										$scope.totalSaldoIBF = resp.data.saldos[1].montoFondo;
										$scope.totalSaldoSCF = resp.data.saldos[4].montoFondo;
										$scope.totalSaldoCMF = resp.data.saldos[5].montoFondo;
										$scope.totalSaldoOI = resp.data.saldos[0].montoOtroIngreso;
										$scope.totalSaldoIBOI = resp.data.saldos[1].montoOtroIngreso;
										$scope.totalSaldoCSOI = resp.data.saldos[2].montoOtroIngreso;
										$scope.totalSaldoIFOI = resp.data.saldos[3].montoOtroIngreso;
										$scope.totalSaldoSCOI = resp.data.saldos[4].montoOtroIngreso;
										$scope.saldoTotalF = resp.data.saldos[6].montoFondo;
										$scope.saldoTotalOI = resp.data.saldos[6].montoOtroIngreso;
										$scope.saldoTotal = resp.data.saldoTotal;
										$scope.fechaSaldo =resp.data.fechaSaldo ;
										
										if ($scope.saldoTotal >0) {
											$("#descarga").show();
										}
										
										$scope.tableSaldoParams = new NgTableParams({page: 1, count: 20}, {data: resp.data.saldos});
									    
									}
								}, function(data) {
									console.log("error --> " + data);
								});
					}

					$scope.init();

				});
