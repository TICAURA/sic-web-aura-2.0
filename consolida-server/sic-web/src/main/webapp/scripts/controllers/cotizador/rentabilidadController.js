'use strict';
angular.module('theme')
        .controller('rentabilidadController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {

            $scope.idCotizacion;
            $scope.cotizacion = {};
            $scope.totalColaboradores;
            $scope.cotizacionTotales;
            $scope.rentabilidad = {};
            
            $scope.inicializarporcentajesRentavilidad = function(){
            	if($scope.rentabilidad.porcentajeClIMPLANT == undefined){
	           		 $scope.rentabilidad.porcentajeClIMPLANT = 0;
	           	 }
            	if($scope.rentabilidad.porcentajePrIMPLANT == undefined){
	           		 $scope.rentabilidad.porcentajePrIMPLANT = 0;
	           	 }
            	if($scope.rentabilidad.porcentajeCoIMPLANT == undefined){
	           		 $scope.rentabilidad.porcentajeCoIMPLANT = 0;
	           	 }
            	
            	
            	if($scope.rentabilidad.porcentajeClSGMM == undefined){
	           		 $scope.rentabilidad.porcentajeClSGMM = 0;
	           	 }
            	if($scope.rentabilidad.porcentajePrSGMM == undefined){
	           		 $scope.rentabilidad.porcentajePrSGMM = 0;
	           	 }
            	if($scope.rentabilidad.porcentajeCoSGMM == undefined){
	           		 $scope.rentabilidad.porcentajeCoSGMM = 0;
	           	 }
            }
            $scope.sumarIMPLANT = function(){
            	return parseInt($scope.rentabilidad.porcentajeClIMPLANT)
            	+ parseInt($scope.rentabilidad.porcentajePrIMPLANT)
            	+ parseInt($scope.rentabilidad.porcentajeCoIMPLANT);
            }
            $scope.sumarSGMM = function(){
            	return parseInt($scope.rentabilidad.porcentajeClSGMM)
            	+ parseInt($scope.rentabilidad.porcentajePrSGMM)
            	+ parseInt($scope.rentabilidad.porcentajeCoSGMM);
            }
            
            $scope.cargaCotizacion = function () {
                $http.post(CONFIG.APIURL + "/cotizador/cargaInicial.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.cotizacion = response.data;
                    $scope.idCotizacion = response.data.idCotizacion; 
                    if($scope.idCotizacion!=null){
                    	$scope.inicializarporcentajesRentavilidad();
                    	$http.post(CONFIG.APIURL + "/cotizador/cargarColaboradores.json").then(
                        	function (response1) {
                        		$scope.totalColaboradores = response1.data.contentRows.length;
                                    $http.post(CONFIG.APIURL + "/cotizador/cargartotales.json").then(
                                        	function (response2) {
                                            	$scope.cotizacionTotales=response2.data.resultado;
                                        	},function (error2) {
                                        		pinesNotifications.notify({
                                 			        title: 'Error',
                                 			        text: 'Errror al obtener la cotizaci&oacute;n',
                                 			        type: 'error'
                                 			      });
                                        	}
                                        );
                             }, function (error1) {
                                 $scope.empleadoDTO = null;
                                 $scope.dataExcel = undefined;
                                 pinesNotifications.notify({
                  			        title: 'Error',
                  			        text: 'Error al obtener la cotizaci&oacute;n',
                  			        type: 'error'
                  			      });
                             }
                        );
                    }else{
                    	$location.path('/cotizador/autorizador/solicitudAutorizaciones');
                    	pinesNotifications.notify({
          			        title: 'Error',
          			        text: 'Error al obtener la cotizaci&oacute;n',
          			        type: 'error'
          			      });
                    }
                }, function (error) {
                	pinesNotifications.notify({
     			        title: 'Error',
     			        text: 'Error al obtener la configuraci&oacute;n',
     			        type: 'error'
     			      });
                });
            }
            $scope.cancelar = function () {
            	$location.path('/cotizador/solicitudAutorizaciones')
            }  

            $scope.ejecutarCotizacion = function() {
            	if ($scope.dataExcel != undefined) {
            		$http.post(CONFIG.APIURL + "/cotizador/ejecutarCotizacion.json",$scope.dataExcel.contentRows).then(function(response){
            			$scope.total = response.data;
                        $scope.varCot=response.data.resultado;
            			$scope.cotizacionRealiada = true;
            			pinesNotifications.notify({
         			        title: 'Cotizaci&oacute;n',
         			        text: 'La cotizaci&oacute;n se genero exitosamente',
         			        type: 'success'
         			      });
            			$('html,body').animate({
                    	    scrollTop: $("#idPanelCotizacion").offset().top
                    	}, 2000);
            		},function(response){
            			pinesNotifications.notify({
         			        title: 'Error',
         			        text: 'Error al realizar la cotizaci&oacute;n',
         			        type: 'error'
         			      });
            		});
            	}
            };
            
            
            $scope.cargaCotizacion();
        });
