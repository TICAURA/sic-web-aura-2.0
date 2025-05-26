'use strict';
angular.module('theme.core.templates').controller('agregarClienteCrmController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox,  $log, agregarClienteCrmService, pinesNotifications) {
      
	 var data = {};
	 $scope.clienteDto = {};
	 $scope.clienteDto.catGiroComercialDto={};
	 $scope.clienteDto.prestadoraServicioFondo={};
	 $scope.clienteDto.listaNoministas=[];
	 $scope.arregloNoministas=[];
	 $scope.catGrupo = [];
	 $scope.esAgregarCliente;
	 $scope.IsVisibleSeccionNuevoCliente;
	 
	
	 $scope.cargaInicial = function(){
		 		 
		  agregarClienteCrmService.cargaInicialPospectosAutorizar(function(data) {
			  $scope.gridProspecto = data.data;
				},
					function(response) {
						$log.error("error --> " + data);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error, favor de verificar.',
					        type: 'error'
					      });
	
					});
		  
		  //carga el catalogo de tipo de pago (PUE, PPD Y PA)
//		  agregarClienteCrmService.listaCatTipoPago(function(response) {
//	    		$scope.listaCatTipoPago = response.data;
//	    	},function(response){});
		  
		  //carga el catalogo de grupos
		  agregarClienteCrmService.cargaCatGrupo(function(response) {
	    		$scope.listaGrupos = response.data;
	    	},function(response){});
		  
		  //carga el catalogo de categoria
		  agregarClienteCrmService.cargaCatCategoria(function(response) {
	    		$scope.listaCategorias = response.data;
	    	},function(response){});
		  
		  // llena los campos con el dto de cliente
		  agregarClienteCrmService.editarProspectoAutorizado(function(response) {
			  if(response.data != undefined && response.data !==null && response.data!==""){
				    $scope.IsVisibleSeccionNuevoCliente = true;
		    		$scope.clienteDto = response.data;
		    		$scope.listaNoministas = response.data.listaNoministas;
		    		$scope.listaSubGrupo = response.data.listaSubGiroComercialDto;
		    		if($scope.clienteDto.fechaConstitucionEmpresa!= undefined && $scope.clienteDto.fechaConstitucionEmpresa!=null){
		    			$scope.clienteDto.fechaConstitucionEmpresa = new Date($scope.clienteDto.fechaConstitucionEmpresa);
		    		}
		    		
			  }else{
				  $scope.IsVisibleSeccionNuevoCliente = false;
			  }
	    	},function(response){});
		  
		  
		  //carga el catalogo de celulas registradas
		  agregarClienteCrmService.listaCelulas(function(response) {
	    		$scope.listaCelulas = response.data;	    		
	    	},function(response){});
		  
		  //carga el catalogo de giros comerciales (grupo)
//		  agregarClienteCrmService.listaCatGiroComercial(function(response) {
//	    		$scope.listaCatGiroComercial = response.data;
//
//	    	},function(response){});
		  
		  agregarClienteCrmService.listaCatRegimenFiscal(function(response) {
	  		$scope.listaRegimenes = response.data;
	
	  		},function(response){});

		  // bandera para visualizar la seccion de nuevo cliente o edicion de cliente
		  agregarClienteCrmService.obtenerValorAgregarCliente(function(response) {
			  $scope.IsVisibleSeccionNuevoCliente = response.data;
			    if(response.data){
			    	$scope.esAgregarCliente = false;
			    }else{
			    	$scope.esAgregarCliente = true;
			    }
			    
	    	},function(response){});
	  };
	  
	  $scope.cargaInicial();
	  	  
	  // combo de noministas, se mostraran noministas vinculados a la celula seleccionada    	  
	  $scope.getNoministas = function () {
		  
		  if($scope.clienteDto !== null && $scope.clienteDto.celula.idCelula !==null){
			  $http.get(CONFIG.APIURL + "/clienteCrm/listaNoministas/"+$scope.clienteDto.celula.idCelula+".json").then(function(data){
				  $scope.listaNoministas = data.data.listaNoministas;
				  $scope.clienteDto.noministaDto = null;
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	  });
		  }
	  }
	  
	  // combo de subgrupo comercial    	  
	  $scope.getSubgrupo = function () {
		  
		  if($scope.clienteDto){
			  $http.get(CONFIG.APIURL + "/clienteCrm/listaCatSubGiroComercial/"+$scope.clienteDto.catGiroComercialDto.idCatGeneral+".json").then(function(response){
				  $scope.listaSubGrupo = response.data;
//				  $scope.clienteDto.noministaDto = null;
		  	    },function(response){
		  	        console.log("error --> " + response);
		  	  });
		  }
	  }
	  
	  
	  //combo dependiente de celulas, carga noministas correspondientes a la celula seleccionada
	  agregarClienteCrmService.listaCelulas(function(response) {
    		$scope.listaCelulas = response.data;
    	},function(response){});
	    
	  
	  $scope.autorizarProspecto = function(prospecto) {
          
		  bootbox.confirm({
              title : "Mensaje confirmaci&oacute;n",
              message : "\u00bfEst&aacute;s seguro que deseas autorizar el cliente pre autorizado?",
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
				  agregarClienteCrmService.autorizarProspecto(prospecto, function(response) {
					  
					  if(response.data.idCliente!=null){
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
								
									$scope.esAgregarCliente = 'false';
									agregarClienteCrmService.esAgregarCliente($scope.esAgregarCliente, function(data) {
										$scope.IsVisibleSeccionNuevoCliente = true;
										$scope.esAgregarCliente = false;
										$scope.clienteDto = response.data;
										$scope.getSubgrupo();
										if($scope.clienteDto.fechaConstitucionEmpresa!= undefined && $scope.clienteDto.fechaConstitucionEmpresa!=null){
							    			$scope.clienteDto.fechaConstitucionEmpresa = new Date($scope.clienteDto.fechaConstitucionEmpresa);
							    		}
									},function(response) {});
									
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
									$scope.cargaInicial();
								}
							});	
					  }
					});
				} 
			  }	
      	});
	  };
	  
	  
	  $scope.declinarProspecto = function(prospecto) {
          
		  bootbox.confirm({
              title : "Mensaje confirmaci&oacute;n",
              message : "\u00bfEst&aacute;s seguro que deseas declinar el cliente pre autorizado?",
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
				  agregarClienteCrmService.declinarProspecto(prospecto, function(response) {
					  
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
	  
  	$scope.registrarDatosGeneralesClienteCrm = function() {

    	bootbox.confirm({
			title : "Confirmar acci&oacute;n",
			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
			buttons : {
				confirm : {
					label : 'ACEPTAR',
					className : 'btn-primary'
				},
				cancel : {
					label : 'CANCELAR',
					className : 'btn-danger'
				}
			},
			callback : function(result) {
				if (result) {
				agregarClienteCrmService.guardarGeneralesCliente($scope.clienteDto, function(response) {

						if(response.data.mensajeError != undefined){
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: response.data.mensajeError,
						        type: 'error'
						      });
						}else{

							var idCliente = Number(response.data.mensaje);
							
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
								
//									location.href=CONFIG.APIURL+"#/crm/actualiza-cliente";
									
									$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/datosGenerales/creaDatosGenCliente.json", idCliente).then(function(data){

										$location.path("/crm/actualiza-cliente");
										
								  	    },function(data){
								  	        console.log("error --> " + data);
								  	  });
									
									
//									agregarClienteCrmService.enviarClienteSeccciones(cliente, function(data) {
//										  $location.path("/crm/actualiza-cliente");
//										},
//										function(data) {
//											console.log("error --> " + data);
//										});
									
									
								}
							});	
						}
					},
					function(response) {
						$log.error(response.status+ ' - '+ response.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
					        type: 'error'
					      });

					});
				}
			}
		});
    };
    
	  // combo de prestadoras fondo    	  
	  $scope.getPrestadoras = function (idCelula) {
		  $http.post(CONFIG.APIURL + "/clienteCrm/getPrestadorasByIdCelula.json", idCelula).then(function(response){
			  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
			  $scope.clienteDto.prestadoraServicioFondo = response.data.listaPrestadorasFondo[0];
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
	  }

    
    $scope.limpiarSeleccion = function(seleccion) {

    	if(seleccion ==="22"){
    		$scope.clienteDto.nombre = null;
    		$scope.clienteDto.apellidoPaterno = null;
    		$scope.clienteDto.apellidoMaterno = null;
    	}else{
    		$scope.clienteDto.razonSocial = null;
    	}
    };
        	
    $scope.cancelar = function() {
    	location.href=CONFIG.APIURL+"#/crm/clientes";
    }
});