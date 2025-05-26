'use strict';  
angular.module('theme.core.templates')
  .controller('clienteCotizadorController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, $window, nuevoClienteService, pinesNotifications) {
	  
	  
	  $scope.motivoRechazoProspecto = "";
	  $scope.motivoDeclinadoProspecto = "";
	  $scope.idClienteTemporal;
	  $scope.gridClientes = {};
	  $scope.cliente = {};
	  $scope.rfc = {};
	  $scope.mostrarBtnCotizacion=false;
	  $scope.mostrarBtnPreCotizacion=false;
	  $scope.mostrarBtnAutorizarProspecto=false;
	  $scope.mostrarBtnAgregarProspecto = false;
	  $scope.mostrarBtnEliminarProspecto = false;
	  $scope.mostrarBtnEditarProspecto = false;
	  $scope.mostrarBtnDeclinarProspecto = false;
	  $scope.mostrarBtnAutorizarProspecto = false;
	  $scope.mostrarBtnRechazarProspecto = false;
	  
	  $scope.validaRol = function(){
		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
	            $scope.usuarioAplicativo = data.data;
	            var auxDirComer = $scope.usuarioAplicativo.usuarioRols.filter (us => us.rol.nombre=="director_comercial").length>0;
				var auxCoordinadorCoti = $scope.usuarioAplicativo.usuarioRols.filter (us => us.rol.nombre=="coordinador_cotizaciones").length>0;
				var auxEjecutivoCoti = $scope.usuarioAplicativo.usuarioRols.filter (us => us.rol.nombre=="ejecutivo_cotizaciones").length>0;
				var auxEjecComercial = $scope.usuarioAplicativo.usuarioRols.filter (us => us.rol.nombre=="ejecutivo_comercial").length>0;
			    var auxPromotor = $scope.usuarioAplicativo.usuarioRols.filter(us => us.rol.nombre=="promotor_ventas").length>0;
				var auxOficina = $scope.usuarioAplicativo.usuarioRols.filter(us => us.rol.nombre=="promotor_ventas").length>0;
			
	            $scope.mostrarBtnAutorizarProspecto = auxDirComer;

				if($scope.usuarioAplicativo.canalVentaDto!=null){
	            	$scope.mostrarBtnPreCotizacion = $scope.usuarioAplicativo.canalVentaDto.generaCotizacion == 1 ? false : true;
	            }
				if(auxDirComer ||  auxEjecutivoCoti){
					$scope.mostrarBtnCotizacion = false;
				}else{
					$scope.mostrarBtnCotizacion = true;
				}
				if(auxDirComer || auxEjecComercial || auxPromotor || auxOficina || auxCoordinadorCoti){
					$scope.mostrarBtnAgregarProspecto = true;
					$scope.mostrarBtnEditarProspecto = true;
				}
				if(auxDirComer || auxEjecComercial){
					$scope.mostrarBtnAgregarProspecto = true;
					$scope.mostrarBtnEliminarProspecto = true;
					$scope.mostrarBtnDeclinarProspecto = true;
			  		$scope.mostrarBtnAutorizarProspecto = true;
			  		$scope.mostrarBtnRechazarProspecto = true;
				}
	  			

          },function(data){
            console.log("error --> " + data);
          });
	  };
	  $scope.validaRol();
	  
	  $scope.init = function(){
		  $http.get(CONFIG.APIURL + "/cliente/cargaInicial.json").then(function(data){
			            $scope.gridClientes = data.data;
			          },function(data){
			            console.log("error --> " + data);
			        });
	  };
	  $scope.init();
	  $scope.verCotizaciones = function(idCliente) {
		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",idCliente);
//		  location.href=CONFIG.APIURL+"#/cotizador/cotizacionesCliente";
		  $location.path('/cotizador/cotizacionesCliente');
	  };
	  
	  
	  $scope.nuevaCotizacion = function(idCliente) {
		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",idCliente);
//          location.href = CONFIG.APIURL + "#/cotizador/cotizacion";
          $location.path('/cotizador/cotizacion');
	  };
	  $scope.nuevaPreCotizacion = function(idCliente) {
		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",idCliente);
//		  location.href = CONFIG.APIURL + "#/cotizador/preCotizacion";
		  $location.path('/cotizador/preCotizacion');
	  };
	  
	  $scope.buscar = function(){
		  var rfc = $scope.gridClientes.rfc;
		  
		  $http.post(CONFIG.APIURL + "/cliente/buscar.json", rfc).then(
                  function (data) {
                	  
              if(data.data.length ===0){
            	  $scope.gridClientes = {};
            	  var idClienteTemp = null
            	  $scope.gridClientes = [idClienteTemp];
            	  $scope.gridClientes.rfc= rfc;
            		  
              }else{
                	  $scope.gridClientes = data.data;
                  }
                	
                  },
                  function (data) {
                      console.log("error --> " + data);
                  }
          );
	  };
	  
	 $scope.editarProspectos = function(idClienteTemp){
		 $http.post(CONFIG.APIURL + "/cliente/verEditarProspecto.json",idClienteTemp);
//		  location.href=CONFIG.APIURL+"#/cotizador/nuevoCliente";
		 $location.path('/cotizador/nuevoCliente');
	 }
	 
	 $scope.limpiarSession = function(){
		 $http.post(CONFIG.APIURL + "/cliente/nuevoProspecto.json");
//		  location.href=CONFIG.APIURL+"#/cotizador/nuevoCliente";
		 $location.path('/cotizador/nuevoCliente');
	 }
	 
	 
//	 $scope.declinarProspecto = function(idClienteTemp){
//		 bootbox
//			.confirm({
//				title : "Confirmar acci&oacute;n",
//				message : "¿Est\u00e1s seguro que deseas declinar al prospecto?",
//				buttons : {
//					confirm : {
//						label : 'S\u00ed',
//						className : 'btn-primary'
//					},
//					cancel : {
//						label : 'No',
//						className : 'btn-danger'
//					}
//				},
//				callback : function(result) {
//					if (result) {
//						
//						$http.post(CONFIG.APIURL + "/cliente/declinarProspecto.json",idClienteTemp);
//						 $scope.init();
//		  
//					}
//				}
//			});
//	 }
	 
	 
	 
	 $scope.eliminarProspecto = function(cliente) {

			bootbox
			.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas eliminar la informaci\u00f3n del prospecto?",
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
						
					var data = {
		                'clienteTempDto' : cliente
		            };
						nuevoClienteService
								.eliminarProspecto(data.clienteTempDto,function(response) {
									if(response.data.mensajeError != undefined){
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: response.data.mensajeError,
									        type: 'error'
									      });
									}else{
											$log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });
											
											$scope.init();
//											location.href=APIURL+"#/cotizador/cotizador";
											$location.path('/cotizador/cotizador');
									}
										},
										function(response) {
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: 'Ocurrio un error al eliminar la informaci\u00f3n del prospecto, favor de verificar.',
										        type: 'error'
										      });

										});
						
						$scope.init();

					}
				}
			});
		};
		
		
		  $scope.autorizarProspecto = function(idClienteTemp) {
	          
			  bootbox.confirm({
	              title : "Mensaje confirmaci&oacute;n",
	              message : "\u00bfEst&aacute;s seguro que deseas autorizar el prospecto?",
	              buttons : {
	                 cancel : {
	                    label : '<i class="fa fa-times"></i> Cancelar',
	                    className : 'btn-danger'
	                 },
	                 confirm : {
	                    label : '<i class="fa fa-check"></i> Aceptar',
	                    className : 'btn-primary'
	                 }
	              },
				callback : function(result) {
					
				  if(result){
					  nuevoClienteService.autorizarProspecto(idClienteTemp, function(response) {
						  
						  if(response.data.mensajeError != undefined || response.data.mensajeError != null){
								
							  
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
										$scope.init();
									}
								});	
							  
						  }else{
							  
							  bootbox.alert({
									title : "Mensaje",
									message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
									buttons : {
										ok : {
											label : 'Aceptar',
											className : 'center-block btn-primary'
										}
									},
									callback : function() {
									
										$scope.init();

										
									}
								});		
						  }
						}, function(response) {
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
									$scope.init();
								}
							});
						});
					} 
				  }	
	      	});
		  };
		  
		  $scope.motivoRechazoCliente = function (idClienteTemp) {
			  $scope.motivoRechazoProspecto = "";
			  $scope.idClienteTemporal = idClienteTemp;
              $('#rechazadaProspecto').modal('show');
          }
		  

		  
          $scope.rechazarProspecto = function (idClienteTemp){

			$scope.cliente = {
							idClienteTemp : idClienteTemp,
							motivoRechazo : $scope.motivoRechazoProspecto
			              }
			
			nuevoClienteService.rechazarProspecto($scope.cliente,function(response) {
						 		if(response.data.mensajeError != undefined || response.data.mensajeError != null){
						 			$scope.idClienteTemporal = null;
						 			$('#rechazadaProspecto').modal('hide');
                                    $log.debug('Solicitud Rechazada');
      							  	bootbox.alert({
      							  		title : "Error",
      							  		message : response.data.mensajeError,
      							  		buttons : {
      							  			 ok : {
      							  				 	label : 'Aceptar',
      							  				 	className : 'center-block btn-primary'
      							  			 }
      							  		},
  									callback : function() {
  										$scope.init();
  									}
  								});	
                                }else{
                                  $scope.idClienteTemporal = null;
						 		  $('#rechazadaProspecto').modal('hide');
      							  bootbox.alert({
      									title : "Mensaje",
      									message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
      									buttons : {
      										ok : {
      											label : 'Aceptar',
      											className : 'center-block btn-primary'
      										}
      									},
      									callback : function() {
      									
      										$scope.init();

      										
      									}
      								});		
      						  }
                            }, function(response) {
//			                                    $ngBootbox.customDialog({
//			                                        title : "Mensaje de error",
//			                                        message : "Ocurrió un error al intentar rechazar la solicitud.",
//			                                        buttons : {
//			                                            main : {
//			                                                label : 'Aceptar',
//			                                                className : 'center-block btn-primary',
//			                                                callback : function() {
//
//			                                                }
//			                                            }
//			                                        }
//			                                    });
                                $log.error(response.status + ' - ' + response.statusText);
							});
          }
          
          
		  $scope.motivoDeclinaCliente = function (idClienteTemp) {
			  $scope.motivoDeclinadoProspecto = "";
			  $scope.idClienteTemporal = idClienteTemp;
              $('#declinadaProspecto').modal('show');
          }
          
     	 $scope.declinarProspecto = function(idClienteTemp){
     		 
     		$scope.cliente = {
					idClienteTemp : idClienteTemp,
					motivoRechazo : $scope.motivoDeclinadoProspecto
	              }
     		 
     		
			nuevoClienteService.declinarProspecto($scope.cliente,function(response) {
		 			$scope.idClienteTemporal = null;
		 			$('#declinadaProspecto').modal('hide');
		 			
		 			if(response.status == '200'){
                        $scope.idClienteTemporal = null;
						  bootbox.alert({
								title : "Mensaje",
								message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
								buttons : {
									ok : {
										label : 'Aceptar',
										className : 'center-block btn-primary'
									}
								},
								callback : function() {
								
									$scope.init();

									
								}
							});
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
									$scope.init();
								}
							});	
		 			}
		 			
                    $log.debug('Solicitud declinada');
					  	bootbox.alert({
					  		title : "Error",
					  		message : response.data.mensajeError,
					  		buttons : {
					  			 ok : {
					  				 	label : 'Aceptar',
					  				 	className : 'center-block btn-primary'
					  			 }
					  		},
						callback : function() {
							$scope.init();
						}
					});	
            }, function(response) {
                $log.error(response.status + ' - ' + response.statusText);
			});
    	 }
		
  });
