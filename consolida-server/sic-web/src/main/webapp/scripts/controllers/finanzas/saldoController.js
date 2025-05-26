'use strict';
angular.module('theme.core.templates')
  .controller('saldoController', 
function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications,NgTableParams) {
	   
	$scope.data = {};
	
	$scope.saldoTotal= 0.00;
	$scope.fechaSaldo= "";
	
	$scope.tableSaldoParams ={}
	
	   
	  $scope.init = function(){
		  $scope.data.catCelula={};
		  $scope.tipoProvision="1";
		  $http.get(CONFIG.APIURL+ "/ppp/finanzas/obtenerSaldos.json").then(function(resp) {
			//$http.get(CONFIG.APIURL+ "/conciliacion/sicofi/cargaInicialSaldos.json")	.then(function(response) {
				
			  if (resp.data.error) {
					
					pinesNotifications
							.notify({
								title : 'Error al obtener saldos ',
								text : 'EL servidor de STP, no se encuentra disponible, favor de intentarlo más tarde.',
								type : 'warning'
							});
				} else {
			
				$scope.tableSaldoParams = new NgTableParams({page: 1, count: 20}, {data: resp.data.prestadoras});
		    	$scope.saldoTotal= resp.data.saldoTotal;
		    	$scope.fechaSaldo= resp.data.fechaSaldo;
		    	$scope.provision = resp.data.prestadoras;
				}
			}, function(data) {
				console.log("error --> " + data);
			});
				
			
		  
      	  };
      	  
      	  
      	

      
	
 $scope.init();	
					
  });







