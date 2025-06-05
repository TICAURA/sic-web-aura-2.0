'use strict';
angular.module('theme.core.templates').controller('consultaClienteFinalController', function($window, $scope, $rootScope, $location, $timeout, $http, CONFIG,consultaClienteFinalService, $bootbox, $log, NgTableParams) {

			$scope.gridClientes = {};
			$scope.cliente;
			 $scope.esAgregarCliente = 'false';
			 $scope.inHabilitaEscritura = true;
			 $scope.IsVisibleSeccionNuevoCliente;
			 $scope.IsVisibleSeccionListaProspectos;
			 $scope.busqueda={};
			 $scope.gridProspectosAut= {};
			 $scope.seleccionEditarBorrar=['SUPERVISOR_CELULA'];
			 
			  $scope.cargaInicialRol = function(){
				  
				  $scope.inHabilitaEscritura = true;
				  
				  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
			            $scope.rolSeleccionado = data.data.usuarioRols[0].rol.nombre.toUpperCase();
			            
						  if($scope.seleccionEditarBorrar.includes($scope.rolSeleccionado)){
							  $scope.inHabilitaEscritura = false;
						  }

			            
				  },function(data){
			            console.log("error --> " + data);
			          });
			  }
			  
			  $scope.cargaInicialRol();

			 $scope.cargaInicial = function(){

				 consultaClienteFinalService.initProspectosAutMesaControl(function(data) {
					 $scope.gridProspectosAut = data.data;
					 $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridProspectosAut});
					 
						},
							function(data) {
								$log.error("error --> " + data);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error, favor de verificar.',
							        type: 'error'
							      });

							});
				 
				 
				 
				 var datos = angular.copy($scope.gridProspectosAut);
				 
				  $scope.search = function (datos) {
		                if ($scope.searchText == undefined) {
		                    return true;
		                }
		                else {
		                    if (
//		                    	item.rfc.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
//		                    	item.rfc.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
								item.razonSocial.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
		                        item.catGrupo.descripcionRazonSocial.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
		                        item.nombre.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
		                        item.apellidoPaterno.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ||
		                        item.apellidoMaterno.toLowerCase().indexOf($scope.searchText.toLowerCase()) != -1 ) {
		                        return true;
		                    }
		                }

		                return false;
		            };
				 
			  };
			
			  
			$scope.cargaInicial();
 
			$scope.actualizaDatosGeneralesCliente = function(cliente) {
				
				consultaClienteFinalService.obtieneClienteDtoParaEditar(cliente, function(data) {
						$scope.esAgregarCliente = 'true';
						  consultaClienteFinalService.esAgregarCliente($scope.esAgregarCliente, function(data) {
							  $location.path("/crm/cliente");
							},
							function(data) {
								 console.log("error --> " + data);
							});
					
				},
				function(data) {
					console.log("error --> " + data);
				});
			};
			
			  
			  $scope.agregarCliente = function() {
				  $scope.esAgregarCliente = 'true';
				  consultaClienteFinalService.esAgregarCliente($scope.esAgregarCliente, function(data) {
					  	$scope.limpiaCamposDatosGeneralesCliente();
					  	$scope.clienteDto = {};
					  	$location.path("/crm/cliente");
					},
					function(data) {
						 console.log("error --> " + data);
					});
			  };
			  
			  $scope.limpiaCamposDatosGeneralesCliente = function() {
				  	$scope.cliente = {}
					consultaClienteFinalService.obtieneClienteDtoParaEditar($scope.cliente, function(data) {},
					function(response) {});
				};
			  
			  
			  $scope.autorizarProspecto = function() {
				  $scope.esAgregarCliente = 'false';
				  consultaClienteFinalService.esAgregarCliente($scope.esAgregarCliente,function(data) {
						$location.path("/crm/cliente");
					},
					function(data) {
						 console.log("error --> " + data);
					});
			  };
			  
			  $scope.buscarCliente = function(){
				  var rfc = $scope.gridProspectosAut.rfc;
				  $http.post(CONFIG.APIURL + "/clienteCrm/buscarCliente.json", rfc).then(
		                  function (data) {
		                	  $scope.gridProspectosAut = data.data;
		                	  $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridProspectosAut});
		                  },
		                  function (data) {
		                      console.log("error --> " + data);
		                  }
		          );
			  };
			  
			  $scope.enviarSeccionesCliente = function(cliente) {
				  
				  consultaClienteFinalService.enviarClienteSeccciones(cliente, function(data) {
					  $location.path("/crm/actualiza-cliente");
					},
					function(data) {
						console.log("error --> " + data);
					});
			  };
			  
			  
			  $scope.eliminarCliente = function(idCliente) {
		          
				  bootbox.confirm({
		              title : "Mensaje confirmaci&oacute;n",
		              message : "\u00bfEst&aacute; seguro que desea eliminar al cliente?",
		              buttons : {
		                 cancel : {
		                    label : '<i class="fa fa-times"></i> CANCELAR',
		                    className : 'btn-danger'
		                 },
		                 confirm : {
		                    label : '<i class="fa fa-check"></i> ACEPTAR',
		                    className : 'btn-primary'
		                 }
		              },
					callback : function(result) {
						
					  if(result){
						  consultaClienteFinalService.eliminarCliente(idCliente, function(response) {
							  
							  if(response.data){
								  bootbox.dialog({
		                              message : "La operaci\u00f3n se completo con \u00e9xito",
		                              title : "Mensaje",
		                              buttons : {
		                                  success : {
		                                      label : "Aceptar",
		                                      className : "btn-primary",
		                                  }
		                              }
		                          });
								  
								  $scope.cargaInicial(); 
								  
							  }else{
									bootbox.alert({
										title : "Error",
										message : "Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde",
										buttons : {
											ok : {
												label : 'Aceptar',
												className : 'center-block btn-primary'
											}
										},
										callback : function() {
											$scope.cargaInicial();
										}
									});	
							  }
							});
						} 
					  }	
		      	});
			  };
			  			  
			  	     
//				$scope.verCotizaciones = function(idCliente) {
//					$http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",
//							idCliente);
//					location.href = CONFIG.APIURL
//							+ "#/cotizador/cotizacionesClientes";
//				};
//
//				$scope.detalles = function(idClienteTemp) {
//					location.href = CONFIG.APIURL + "#/crm/C/"
//							+ idClienteTemp;
//
//				};
//
//				$scope.actualizaCliente = function(idClienteTemp) {
//					location.href = CONFIG.APIURL + "#/crm/actualiza-cliente/"
//							+ idClienteTemp;
//
//				};


			  
		});
