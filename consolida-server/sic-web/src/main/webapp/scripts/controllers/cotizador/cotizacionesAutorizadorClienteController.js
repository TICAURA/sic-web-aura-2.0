'use strict';
angular.module('theme.core.templates')
        .controller('cotizacionesAutorizadorClienteController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications) {
            $scope.cliente;
            $scope.idClienteTemp;
            $scope.idCotizacion;
            $scope.cotizacion;
            $scope.modalBitNuevo = false;
            $scope.cotizacionRealiada = false;
            $scope.catalogosCotizadorDTO = {};
            $scope.observacionAutorizador = {};
            $scope.idCotizacion;
            
            
            $scope.init = function () {
            	/*$http.post(CONFIG.APIURL + "/seguimiento/busquedaContadorXEstatus.json", 4).then(function (response) {
                    console.log("response --> " + response);
                    $scope.gridClientes = response.data;
                }, function (response) {
                    console.log("error --> " + response);
                });*/
            	
                $http.post(CONFIG.APIURL + "/cliente/cargaInicialAutorizador.json").then(
                        function (data) {
                        		$scope.cliente = data.data.listClienteTempDto;
                    },
                        function (data) {
                            console.log("error --> " + data);
                    });
            }
            

            $scope.actualizaNomFis = function () {
                $scope.cotizacion.porcentajeNomFis = 100 - $scope.cotizacion.porcentajePpp;
            }
            
            $scope.cerrarModalAutorizador = function () {
                $('#noAutorizar').modal('hide');
            }
            
            
            $scope.guardarObservacionAutorizador = function (cotizacion){
            	$scope.observacionAutorizador.observacionAutorizador = cotizacion.observacionAutorizador;
            	$scope.observacionAutorizador.idCotizacion = $scope.idCotizacion;
                $http.post(CONFIG.APIURL + "/cliente/guardarObservacionAutorizador.json", $scope.observacionAutorizador).then(
                        function (response) {
//                        	location.href = '/sic-web#/cotizador/solicitudAutorizaciones'
                        		$location.path('/cotizador/solicitudAutorizaciones');
                        },
                        function (data) {
                            alert("Ocurrio un error al guardar la observacion, favor de verificar. ")
                        }
                );
            }
            
            
            $scope.verRentabilidad = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacionVerAutorizador.json", row.idClienteTemp).then(
                        function (data) {
                        	if(data.data.mensaje==="Exito"){
                        		location.href = "#/cotizador/autorizador/rentabilidad";
                        	}else{
                        		pinesNotifications.notify({
                  			        title: 'Error',
                  			        text: 'Error al obtener la cotizaci&oacute;n',
                  			        type: 'error'
                  			      });
                        	}
                        },
                        function (data) {
                        	pinesNotifications.notify({
              			        title: 'Error',
              			        text: 'Error al obtener la cotizaci&oacute;n',
              			        type: 'error'
              			      });
                        }
                );
            }
            /*$scope.verCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacionVerAutorizador.json", row.idClienteTemp).then(
                        function (data) {
                        	if(data.data.mensaje==="Exito"){
                        		location.href = "#/cotizador/autorizador/cotizacionAutorizador";
                        	}else{
                        		pinesNotifications.notify({
                  			        title: 'Error',
                  			        text: 'Error al obtener la cotizaci&oacute;n',
                  			        type: 'error'
                  			      });
                        	}
                        },
                        function (data) {
                        	pinesNotifications.notify({
              			        title: 'Error',
              			        text: 'Error al obtener la cotizaci&oacute;n',
              			        type: 'error'
              			      });
                        }
                );
            }*/
            
            $scope.verCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", row.cotizacionDto.idCotizacion).then(
                        function (data) {
//                        	if(data.data.mensaje==="Exito"){
                        		location.href = "#/cotizador/autorizador/cotizacionAutorizador";
//                        	}else{
//                        		pinesNotifications.notify({
//                  			        title: 'Error',
//                  			        text: 'Error al obtener la cotizaci&oacute;n',
//                  			        type: 'error'
//                  			      });
//                        	}
                        },
                        function (data) {
                        	pinesNotifications.notify({
              			        title: 'Error',
              			        text: 'Error al obtener la cotizaci&oacute;n',
              			        type: 'error'
              			      });
                        }
                );
            }
           
            
            
            $scope.autorizarCotizacion = function (){
            	bootbox
        		.confirm({
        			title : "Confirmar acción",
        			message : "¿Est\u00e1s seguro que deseas autorizar la cotizaci\u00f3n?",
        			buttons : {
        				confirm : {
        					label : 'S\u00ed',
        					className : 'btn-primary'
        				},
        				cancel : {
        					label : 'No',
        					className : 'btn-danger'
        				}
        			},
        			callback : function(result) {
        				if (result) {
        					$http.post(CONFIG.APIURL + "/cliente/autorizarCotizacion.json", $scope.cotizacion.idCotizacion).then(
                        function (response) {
//                        	location.href = '/sic-web#/cotizador/solicitudAutorizaciones'
                        		$location.path('/cotizador/solicitudAutorizaciones');
                        },
                        function (data) {
                            alert("Ocurrio un error al solicitar la cotización, favor de verificar. ")
                        }
                );
        				}	
            }
        		});
            }
        				
            
            $scope.init();
        });
