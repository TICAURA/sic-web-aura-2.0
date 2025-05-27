'use strict';
angular.module('theme.core.templates')
  .controller('agregarPrestadoraServicioController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, 
		  agregarPrestadoraServicioService, pinesNotifications) {
	  
	  
	  $scope.escrituraDatosGenerales=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraRegistroPatronal=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraObjetoSocial=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraProductos=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraDomicilio=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraRepresentaApoderado=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraAccionistas=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraCuentas=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'DIRECTOR_FINANZAS','TESORERIA'];

	  $scope.escrituraDatosSTP=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL','TESORERIA'];

	  $scope.escrituraFielCSD=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];

	  $scope.escrituraDocumentos=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL'];
	  
	  $scope.tipoDoc = "";
	  $scope.tabSeleccionado = "generales";
	  $scope.prestadoraServicioDomicilioDto={};
	  $scope.prestadoraServicioDomicilioDto.domicilio={};
	  $scope.prestadoraServicioDomicilioDto.domicilio.catMunicipios= {};
	  $scope.prestadoraServicioCuentaBancaria={};
	  $scope.itemDefinicionDocumento ={};
	  
	  $scope.prestadoraServicioDto = {};
	  $scope.prestadoraServicioDto.prestadoraServicioDomicilioDto = {};
	  $scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio = {};
	  $scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio.catMunicipios = {};
	  
	  $scope.prestadoraServicioCuentaBancaria= {};
	  $scope.prestadoraServicioCuentaBancaria.catBanco={};
	  $scope.prestadoraServicioCuentaBancaria.catTipoCuenta = {};
	  
	  $scope.data = {};
	  $scope.prestadoraServicioDocumento={};
	  $scope.data.archivoFielCer={};
	  $scope.data.archivoFielKey={};
	  $scope.data.archivoCsdCer={};
	  $scope.data.archivoCsdKey={};
	  $scope.data.archivoLogotipo={};
	  
	  $scope.archivoPrestadora ={};
	  // DATOS GENERALES
	  $scope.IsVisibleLogotipo = true;
	  $scope.IsVisibleNombreLogotipo = false;
	  $scope.IsVisibleNombreBotonCancelarCarga = false;
	  
	  
	  /// Producto
	  $scope.prestadoraServicioProductoDto = {};
	  $scope.catDispersor = {};
	  $scope.edicionDispersor = {};
	  $scope.agregarCatSat = false;
	  $scope.prestadoraServicioProductoCatSatDto= {};
	  $scope.prestadoraServicioProductoCatSatDto.catSat = {};
	  $scope.panelAgregarProducto = false;
	  $scope.panelAgregarDispersor = false;

	  
	  // REGISTRO PATRONAL, CLASE Y FRACCION, IMSS
//	  $scope.clienteDto = {};
	  $scope.prestadoraRegistroPatronal = {}
	  $scope.IsVisibleBotonGuardarRegistroPatronal = true;
	  $scope.IsVisibleBotonActualizarRegistroPatronal = false;
	  
	  $scope.presatdoraClase = {};
	  $scope.presatdoraImms = {};
	  
	  // OBJETO SOCIAL
	  $scope.prestadoraObjetoSocial = {};
	  $scope.IsVisibleBotonGuardarObjetoSocial = true;
	  $scope.IsVisibleBotonActualizarObjetoSocial = false;
	  
	  // REPRESENTANTE Y APODERADOS LEGALES
	  $scope.representanteLegalDto = {};
	  $scope.IsVisibleFormularioRepresentante = false;
	  $scope.IsVisibleDocumentosRepresentante = false;
	  $scope.tituloDinamicoLegal = '';
	  
	  $scope.apoderadoLegalDto = {};
	  $scope.IsVisibleFormularioApoderado = false;
	  $scope.IsVisibleDocumentosApoderado = false;
	  
	  $scope.IsVisibleRepresentanteArchivos = false;
	  
	// ACCIONISTAS
	  $scope.accionistaDto = {};
	  $scope.IsVisibleFormularioAccionista = false;
	  $scope.IsVisibleDocumentosAccionista = false;
	  $scope.entidadFederativa ={};
	  $scope.accionistaDto.municipios ={};
	  $scope.accionistaDto.domicilioDto ={};
	  $scope.accionistaDto.domicilioDto.catMunicipios ={};
	  $scope.accionistaDto.domicilioDto.idEntidadFederativa ={};
	  $scope.porcentajeAccionistaSelect = 0;
	  
	  /// Cuentas
	  $scope.isPanelAgregarCuentaBancaria = false;
	  
	  // condicion rol / escritura
	  $scope.rolSeleccionado = null;
	  $scope.inHabilitaEscritura = true;
	  
	  
	  
	  $scope.cargaInicialRol = function(){
		  
		  $scope.inHabilitaEscritura = true;
		  
		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
	            $scope.rolSeleccionado = data.data.usuarioRols[0].rol.nombre.toUpperCase();
	            
				  if($scope.escrituraDatosGenerales.includes($scope.rolSeleccionado)){
					  $scope.inHabilitaEscritura = false;
				  }
	            
		  },function(data){
	            console.log("error --> " + data);
	          });
	  }
	  
	  $scope.cargaInicialRol();
	  
	  $scope.init = function() {
			$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaCatalogos.json").then(
					function(data) {
						
						$scope.prestadoraServicioDto = data.data;
						$scope.verLogotipo($scope.prestadoraServicioDto);
						
						if(data.data!= undefined && data.data!=null && data.data.nombreArchivoLogotipo!=null){
							 $scope.IsVisibleLogotipo = false;
							 $scope.IsVisibleNombreLogotipo = true;
						}

					}, function(data) {
						console.log("error --> " + data);
					});
			
			$http.post(CONFIG.APIURL + "/prestadoraServicio/actividad/cargaInicialActividad.json").then(
					function(response) {
						$scope.clienteActividad = response.data;
						$scope.clienteActividad.idGiroComercial = -1;
						$scope.clienteActividad.idSubGiroComercial = -1;
					}, function(data) {
						console.log("error cargaInicialActividad--> " + data);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la carga inicial de actividad, favor de intentarlo nuevamente.',
					        type: 'error'
					      });
					});
			
		};
		$scope.init();
		
		$scope.actualizarLogotipo = function(){
			$scope.IsVisibleLogotipo = true;
			$scope.IsVisibleNombreLogotipo = false;
			$scope.IsVisibleNombreBotonCancelarCarga = true;
		}
		
		$scope.cancelarUpload = function(){
			$scope.IsVisibleLogotipo = false;
			$scope.IsVisibleNombreLogotipo = true;
			$scope.IsVisibleNombreBotonCancelarCarga = false;
		}
	  
	  $scope.seleccionarTab = function(tab){
		  $scope.tabSeleccionado = tab;
		  $scope.inHabilitaEscritura = true;
		  
		  if($scope.tabSeleccionado == "generales"){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaCatalogos.json").then(
						function(data) {
							$scope.prestadoraServicioDto = data.data;
							$scope.verLogotipo($scope.prestadoraServicioDto);
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  $scope.cargaInicialActividad();
			  
			  if($scope.escrituraDatosGenerales.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
			  
		  }
		  
		 
		  
		  if($scope.tabSeleccionado == "registroPatronal"){
			  $scope.cargaInicialRegistroPatronal();
			  $scope.cargaInicialClseFraccionPrima();
			  $scope.cargaInicialPrestadoraImss();
			  
			  if($scope.escrituraRegistroPatronal.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
			  
		  }	
		  
		  if($scope.tabSeleccionado == "objetoSocial"){
			  $scope.cargaInicialObjetoSocial();
			  
			  if($scope.escrituraObjetoSocial.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	
		  
		  
		  if($scope.tabSeleccionado == "productos"){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialProductos.json").then(
						function(data) {
							$scope.panelAgregarProducto = false;
							$scope.prestadoraServicioDto = data.data;
							$scope.verLogotipo($scope.prestadoraServicioDto);
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  $scope.mostrarModalCatSat();
			  
			  if($scope.escrituraProductos.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	 
		  
		  if($scope.tabSeleccionado == "fiel"){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialFiel.json").then(
						function(data) {
							$scope.prestadoraServicioDto = data.data;
							$scope.verLogotipo($scope.prestadoraServicioDto);
							if(data.data.prestadoraServicioDocumento != undefined){
								$scope.prestadoraServicioDocumento = data.data.prestadoraServicioDocumento;
							}
							$scope.cargaInicialDocumentosPrestadoraFielCsd();
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  if($scope.escrituraFielCSD.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	 
		  
		  if($scope.tabSeleccionado === "domicilioFiscal"){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialDomicilio.json").then(
						function(data) {
							$scope.prestadoraServicioDto = data.data;
							$scope.verLogotipo($scope.prestadoraServicioDto);
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  if($scope.escrituraDomicilio.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	
		  
		  if($scope.tabSeleccionado === "cuentasBancarias"){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialCuentas.json").then(
						function(data) {
							$scope.prestadoraServicioDto = data.data;
							$scope.verLogotipo($scope.prestadoraServicioDto);
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  if($scope.escrituraCuentas.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }
		  
		  if($scope.tabSeleccionado === "documentosPrestServ"){
			  $scope.cargaInicialDocumentosPrestadora();
			  
			  if($scope.escrituraDocumentos.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	
		  
		  if($scope.tabSeleccionado === "representanteLegal"){
			  $scope.cargaInicialRepresentanteLegal();
			  $scope.cargaInicialApoderadoLegal();
//			  $scope.cargaInicialDocumentosPrestadoraRepresentanteLegal();
//			  $scope.cargaInicialDocumentosPrestadoraApoderadoLegal();
			  
			  if($scope.escrituraRepresentaApoderado.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }
		  
		  if($scope.tabSeleccionado === "accionista"){
			  $scope.cargaInicialAccionista();
//			  $scope.cargaInicialDocumentosPrestadoraAccionista();
			  
			  if($scope.escrituraAccionistas.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	
		  
		  if($scope.tabSeleccionado == "stpPrestServ"){
			  $scope.panelAgregarDispersor = false;
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialDispersores.json").then(
						function(data) {
							$scope.prestadoraServicioDto.listprestadoraServicioStpDto = data.data.listprestadoraServicioStpDto;
							$scope.catDispersor= data.data.catTipoDispersor;
							
						}, function(data) {
							console.log("error --> " + data);
						});
			  
			  if($scope.escrituraDatosSTP.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }	 

	  }
	  
	  
	  $scope.cargaInicialDocumentosPrestadora = function (){
		  $http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerDocumentosPrestadora.json").then(
					function(data) {
						$scope.documentosPrestadora = data.data;
					}, function(data) {
						console.log("error --> " + data);
					});
	  }
	  
	  $scope.cargaInicialDocumentosPrestadoraRepresentanteLegal = function (id){
		  $http.post(CONFIG.APIURL + "/prestadoraServicio/representanteLegal/obtenerDocumentosRepresentante.json", id).then(
					function(data) {
						$scope.documentosPrestadoraRepresentante = data.data;
					}, function(data) {
						console.log("error --> " + data);
					});
	  }
	  
	  $scope.cargaInicialDocumentosPrestadoraApoderadoLegal = function (id){
		  $http.post(CONFIG.APIURL + "/prestadoraServicio/apoderadoLegal/obtenerDocumentosApoderado.json", id).then(
					function(data) {
						$scope.documentosPrestadoraApoderado = data.data;
					}, function(data) {
						console.log("error --> " + data);
					});
	  }
	  
	  $scope.cargaInicialDocumentosPrestadoraAccionista = function (id){
		  $http.post(CONFIG.APIURL + "/prestadoraServicio/accionista/obtenerDocumentosAccionista.json",id).then(
					function(data) {
						$scope.documentosPrestadoraAccionista = data.data;
					}, function(data) {
						console.log("error --> " + data);
					});
	  }
	  
	  
	  $scope.obtenerCatMunicipiosByEntidad = function(seleccion) {
		  agregarPrestadoraServicioService.cargaCatMunicipiosXEntidadFed(seleccion, function(response) {
	    		$scope.prestadoraServicioDto.municipios = response.data;
	    	},function(response){
				
			});
	    }
	  
	  $scope.obtenerEntidadFederativaXCP = function(codigoPostal) {
		  agregarPrestadoraServicioService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
			  if(response.data.municipios != null || response.data.municipios != undefined){
		    		$scope.prestadoraServicioDto.municipios = response.data.municipios;
		    		$scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio.idEntidadFederativa = response.data.prestadoraServicioDomicilioDto.domicilio.idEntidadFederativa;
		    		$scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio.catMunicipios = response.data.prestadoraServicioDomicilioDto.domicilio.catMunicipios;
				  }else{
					  $scope.prestadoraServicioDto.municipios = '';
			    		$scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio.idEntidadFederativa = '';
			    		$scope.prestadoraServicioDto.prestadoraServicioDomicilioDto.domicilio.catMunicipios = '';
				  }
	    	},function(response){
				
			});
	    }
	  
	  $scope.guardarPrestadoraServicio = function(prestadora) {
		  if($scope.data != undefined && $scope.data.archivoLogotipo != undefined){
				  prestadora.archivoLogotipo = $scope.data.archivoLogotipo;
		  }
		 
	    	bootbox
			.confirm({
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
						
					
					agregarPrestadoraServicioService
								.guardarPrestadoraServicio(prestadora ,function(response) {
									if(response.data.mensajeDTO != null ||response.data.mensajeDTO != undefined && response.data.mensajeDTO.mensajeError != undefined){
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: response.data.mensajeDTO.mensajeError,
									        type: 'error'
									      });
									}else{
											$log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });
											
											$http.post(CONFIG.APIURL + "/prestadoraServicio/editarPrestadoraServicio.json",response.data.idPrestadoraServicio);
											 location.href=CONFIG.APIURL+"#/crm/prestadoraServicio/agregarPrestadoraServicio";
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
	    }
	  
	  
	  $scope.guardarProducto = function (productoForm){
		  $scope.prestadoraServicioProductoDto.prestadoraServicioDto = $scope.prestadoraServicioDto;
		   
	    	bootbox
			.confirm({
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
						
						agregarPrestadoraServicioService.guardarProducto($scope.prestadoraServicioProductoDto  ,function(response) {
									if(response.data.mensajeDTO != null ||response.data.mensajeDTO != undefined && response.data.mensajeDTO.mensajeError != undefined){
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: response.data.mensajeDTO.mensajeError,
									        type: 'error'
									      });
									}else{
											$log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });
											
											if(productoForm){
												productoForm.$setPristine();
												productoForm.$setUntouched();
											}
											$scope.prestadoraServicioProductoDto = {};
											 $scope.panelAgregarProducto = false;
											$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialProductos.json").then(
													function(data) {
														$scope.prestadoraServicioDto = data.data;
														$scope.verLogotipo($scope.prestadoraServicioDto);
													}, function(data) {
														console.log("error --> " + data);
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
}
	  
	  $scope.guardarDomicilio = function(prestadora) {

	    	bootbox
			.confirm({
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
						
					var data = {
	                    'prestadoraServicioDto' : prestadora
	                };
					agregarPrestadoraServicioService
								.guardarPrestadoraServicio(prestadora ,function(response) {
									if(response.data.mensajeDTO != null ||response.data.mensajeDTO != undefined && response.data.mensajeDTO.mensajeError != undefined){
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: response.data.mensajeDTO.mensajeError,
									        type: 'error'
									      });
									}else{
											$log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });
											
											$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialDomicilio.json").then(
													function(data) {
														$scope.prestadoraServicioDto = data.data;
														$scope.verLogotipo($scope.prestadoraServicioDto);
													}, function(data) {
														console.log("error --> " + data);
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
	    }
	  
	  $scope.guardarCuentaPrestadoraServicio = function(cuenta) {

	    	bootbox
			.confirm({
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
					cuenta.idPrestadoraServicio = $scope.prestadoraServicioDto.idPrestadoraServicio;
					agregarPrestadoraServicioService
								.guardarCuentaPrestadoraServicio(cuenta ,function(response) {
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
											$scope.limpiarCamposCuenta();
											$scope.isPanelAgregarCuentaBancaria = false;
											$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialCuentas.json").then(
													function(data) {
														$scope.prestadoraServicioDto = data.data;
														$scope.verLogotipo($scope.prestadoraServicioDto);
													}, function(data) {
														console.log("error --> " + data);
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
	    }
	  
	  $scope.limpiarCamposCuenta = function(){
		  $scope.prestadoraServicioCuentaBancaria.numeroCuenta = '';
		  $scope.prestadoraServicioCuentaBancaria.esPrincipal = false;
		  $scope.prestadoraServicioCuentaBancaria.clabeInterbancaria = '';
		  $scope.prestadoraServicioCuentaBancaria.numeroReferencia = '';
		  $scope.prestadoraServicioCuentaBancaria.catBanco.idCatGeneral = 'null';
		  $scope.prestadoraServicioCuentaBancaria.catTipoCuenta.idCatGeneral = 'null';
		  $scope.prestadoraServicioCuentaBancaria.idPrestadoraServicioCuentaBancaria = null;
	  }
	  
	  $scope.editarCuentaBancaria = function(cuenta) {
		  $scope.isPanelAgregarCuentaBancaria = true;
		  $scope.prestadoraServicioCuentaBancaria.numeroCuenta = cuenta.numeroCuenta;
		  $scope.prestadoraServicioCuentaBancaria.esPrincipal = cuenta.esPrincipal;
		  $scope.prestadoraServicioCuentaBancaria.clabeInterbancaria = cuenta.clabeInterbancaria;
		  $scope.prestadoraServicioCuentaBancaria.numeroReferencia = cuenta.numeroReferencia;
		  $scope.prestadoraServicioCuentaBancaria.catBanco.idCatGeneral = cuenta.catBanco.idCatGeneral;
		  $scope.prestadoraServicioCuentaBancaria.catTipoCuenta.idCatGeneral = cuenta.catTipoCuenta.idCatGeneral
		  $scope.prestadoraServicioCuentaBancaria.idPrestadoraServicioCuentaBancaria = cuenta.idPrestadoraServicioCuentaBancaria;
		  
	  }
	  
	  $scope.cancelarAgregarCuenta = function() {
		  $scope.isPanelAgregarCuentaBancaria = false;
		  $scope.limpiarCamposCuenta();		  
	  }
	  
	  $scope.eliminarCuentaBancaria = function(cuenta) {

	    	bootbox
			.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas eliminar la cuenta bancaria?",
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
					agregarPrestadoraServicioService
								.eliminarCuentaPrestadoraServicio(cuenta ,function(response) {
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
											
											$scope.limpiarCamposCuenta();
											$scope.isPanelAgregarCuentaBancaria = false;
											$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialCuentas.json").then(
													function(data) {
														$scope.prestadoraServicioDto = data.data;
														$scope.verLogotipo($scope.prestadoraServicioDto);
													}, function(data) {
														console.log("error --> " + data);
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
	    }
	  
	  
	  $scope.nuevaCuenta = function() {
		  $scope.isPanelAgregarCuentaBancaria = true;
		  $scope.limpiarCamposCuenta();
			};
	  
	  
	  //Carga de archivos
	  $scope.fileChangedFielCer = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp("(.*?)\.(cer)$");
          
          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.cer</b>";
              pinesNotifications.notify({
			        title: 'Error',
			        text: $scope.mensaje,
			        type: 'error'
			      });
          }else if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
                  $log.debug("Archivo cargado memoria");
                  $scope.data.archivoFielCer.archivoFielCer = reader.result;
                  $scope.data.archivoFielCer.nombreArchivoFielCer = lstArchivos[0].name;
                  $scope.data.archivoFielCer.tamanioArchivoFielCer = lstArchivos[0].size;
                  $scope.prestadoraServicioDocumento.nombreArchivoFielCer = lstArchivos[0].name;
              }
              reader.readAsDataURL(lstArchivos[0]);
          }

      };
      
      $scope.fileChangedFielCsd = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp("(.*?)\.(key|cer)$");

          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.key y/o .cer</b>";
              pinesNotifications.notify({
			        title: 'Error',
			        text: $scope.mensaje,
			        type: 'error'
			      });
          }else if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
            	  var documento = {};
            	  
            	  documento.mimeType = reader.result.substr(0,reader.result.indexOf(',')+1);
                  documento.archivo = reader.result.substr(reader.result.indexOf(',') + 1);
                  documento.nombreArchivo = lstArchivos[0].name;
                  documento.tamanioArchivo = lstArchivos[0].size;
                  
                  $scope.itemDefinicionDocumento.documentoNuevo = documento;
              }
              reader.readAsDataURL(lstArchivos[0]);
          }
      }
      
      $scope.fileChangedCsdCer = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp("(.*?)\.(cer)$");

          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.cer</b>";
              pinesNotifications.notify({
			        title: 'Error',
			        text: $scope.mensaje,
			        type: 'error'
			      });

          }else if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
                  $log.debug("Archivo cargado memoria");
                  $scope.data.archivoCsdCer.archivoCsdCer = reader.result;
                  $scope.data.archivoCsdCer.nombreArchivoCsdCer = lstArchivos[0].name;
                  $scope.data.archivoCsdCer.tamanioArchivoCsdCer = lstArchivos[0].size;
                  $scope.prestadoraServicioDocumento.nombreArchivoCsdCer = lstArchivos[0].name;
              }
              reader.readAsDataURL(lstArchivos[0]);
          }

      };
      
      
      $scope.fileChangedCsdKey = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp("(.*?)\.(key)$");
          
          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.key</b>";
              pinesNotifications.notify({
			        title: 'Error',
			        text: $scope.mensaje,
			        type: 'error'
			      });

          }else  if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
                  $log.debug("Archivo cargado memoria");
                  $scope.data.archivoCsdKey.archivoCsdKey = reader.result;
                  $scope.data.archivoCsdKey.nombreArchivoCsdKey = lstArchivos[0].name;
                  $scope.data.archivoCsdKey.tamanioArchivoCsdKey = lstArchivos[0].size;
                  $scope.prestadoraServicioDocumento.nombreArchivoCsdKey = lstArchivos[0].name;
              }
              reader.readAsDataURL(lstArchivos[0]);
          }

      };
      
      $scope.guardarArchivosFiel = function (){
        	if($scope.data != undefined){
//        		$scope.prestadoraServicioDocumento.archivoFielCer = $scope.data.archivoFielCer;
//        		$scope.prestadoraServicioDocumento.archivoFielKey = $scope.data.archivoFielKey;
//        		$scope.prestadoraServicioDocumento.archivoCsdCer = $scope.data.archivoCsdCer;
//        		$scope.prestadoraServicioDocumento.archivoCsdKey = $scope.data.archivoCsdKey;
        		$scope.prestadoraServicioDocumento.idPrestadoraServicio = $scope.prestadoraServicioDto.idPrestadoraServicio;
        	}
        	
        	if($scope.prestadoraServicioDocumento.passwordFiel == null || "" === $scope.prestadoraServicioDocumento.passwordFiel.trim()){
        		pinesNotifications.notify({
				        title: 'Error',
				        text: 'Debe ingresar el password del archivo FIEL.',
				        type: 'error'
				      });
        	}else {
                $http.post(CONFIG.APIURL + "/prestadoraServicio/guardarArchivosFiel.json", $scope.prestadoraServicioDocumento).then(
                        function (response) {
                      	  $log.debug('ok');
      						pinesNotifications.notify({
      					        title: 'Mensaje',
      					        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
      					        type: 'success'
      					      });
                      	  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialFiel.json").then(
            						function(data) {
            							$scope.prestadoraServicioDto = data.data;
            							$scope.verLogotipo($scope.prestadoraServicioDto);
            							if(data.data.prestadoraServicioDocumento != undefined){
            								$scope.prestadoraServicioDocumento = data.data.prestadoraServicioDocumento;
            							}
            						}, function(data) {
            							console.log("error --> " + data);
            						});
                        },
                        function (data) {
                      	  $log.error(response.status+ ' - '+ response.statusText);
      						pinesNotifications.notify({
      					        title: 'Error',
      					        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
      					        type: 'error'
      					      });
                        });
        	}
        }
      
      $scope.guardarArchivosCsd = function (){
      	if($scope.data != undefined){

      		$scope.prestadoraServicioDocumento.idPrestadoraServicio = $scope.prestadoraServicioDto.idPrestadoraServicio;
      	}
      	
      	if($scope.prestadoraServicioDocumento.passwordCsd == null || "" === $scope.prestadoraServicioDocumento.passwordCsd.trim()){
      		pinesNotifications.notify({
				        title: 'Error',
				        text: 'Debe ingresar el password del archivo CSD.',
				        type: 'error'
				      });
      	}else {
              $http.post(CONFIG.APIURL + "/prestadoraServicio/guardarArchivosFiel.json", $scope.prestadoraServicioDocumento).then(
                      function (response) {
                    	  $log.debug('ok');
    						pinesNotifications.notify({
    					        title: 'Mensaje',
    					        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
    					        type: 'success'
    					      });
                    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialFiel.json").then(
          						function(data) {
          							$scope.prestadoraServicioDto = data.data;
          							$scope.verLogotipo($scope.prestadoraServicioDto);
          							if(data.data.prestadoraServicioDocumento != undefined){
          								$scope.prestadoraServicioDocumento = data.data.prestadoraServicioDocumento;
          							}
          						}, function(data) {
          							console.log("error --> " + data);
          						});
                      },
                      function (data) {
                    	  $log.error(response.status+ ' - '+ response.statusText);
    						pinesNotifications.notify({
    					        title: 'Error',
    					        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
    					        type: 'error'
    					      });
                      });
      	}
      }
         
      
      $scope.verificarFondo = function(prestadora){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/verificarFondo.json", prestadora).then(
						function(data) {
							if(data.data.correcto == false){
							$scope.prestadoraServicioDto.esFondo = false;
							pinesNotifications.notify({
						        title: 'Error',
						        text: data.data.mensaje,
						        type: 'error'
						      });
							}
						}, function(data) {
							console.log("error --> " + data);
						});
		  
      }
      
      $scope.cargaInicialActividad = function() {
			$http.post(CONFIG.APIURL + "/prestadoraServicio/actividad/cargaInicialActividad.json").then(
					function(response) {
						$scope.clienteActividad = response.data;
						$scope.clienteActividad.idGiroComercial = -1;
						$scope.clienteActividad.idSubGiroComercial = -1;
					}, function(data) {
						console.log("error cargaInicialActividad--> " + data);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la carga inicial de actividad, favor de intentarlo nuevamente.',
					        type: 'error'
					      });
					});
		};
		
		 $scope.obtenerSubgiro = function(idGiro) {
			 agregarPrestadoraServicioService.obtenerSubgiro(idGiro, function(response) {
		    		$scope.clienteActividad.catSubGiroComercial = response.data.catSubGiroComercial;
		    		$scope.clienteActividad.idSubGiroComercial = null;
		    	},function(response){
					
				});
		    }
		 
		 
		 $scope.guardarActividad = function(actividad) {

		    	bootbox
				.confirm({
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
						actividad.cliente = $scope.clienteDto;
						agregarPrestadoraServicioService
									.guardarActividad(actividad ,function(response) {
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
											
												$scope.reset();
												$scope.cargaInicialActividad();
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
		    }
		 
		 
		 $scope.eliminarActividad = function(actividad) {

		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas eliminar la actividad econ\u00f3mica?",
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
						actividad.cliente = $scope.clienteDto;
						 $http.post(CONFIG.APIURL + "/prestadoraServicio/actividad/eliminarActividad.json", actividad).then(
				                  function (response) {
				                  	$log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										
										$scope.cargaInicialActividad();
				                  },
				                  function (response) {
				                	  $log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentar nuevamente.',
									        type: 'error'
									      });
				                	  console.log("error --> " + data);
				                  });

						}
					}
				});
		    }
		 
		 
////////////// REGISTRO PATRONAL ///////////////////////////////////////////
		  
		  
		  $scope.cargaInicialRegistroPatronal = function() {
			  agregarPrestadoraServicioService.cargaInicialRegistroPatronal(function(response) {
				  $scope.gridRegistroPatronal = response.data;
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
			};
		  
		  $scope.guardaRegistroPatronal = function() {
			  			  
			  agregarPrestadoraServicioService.guardaRegistroPatronal($scope.prestadoraRegistroPatronal,function(response) {
				  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
					  $log.error(response.status+ ' - '+ response.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la el resguardo del concepto. Favor de intentarlo mas tarde',
					        type: 'error'
					      });
								$scope.prestadoraRegistroPatronal.registroPatronal = null;
								$scope.cargaInicialRegistroPatronal();
								$scope.IsVisibleBotonGuardarRegistroPatronal = true;
								$scope.IsVisibleBotonActualizarRegistroPatronal = false;
				  }else{
					  $scope.prestadoraRegistroPatronal.registroPatronal = null;
					  $scope.IsVisibleBotonGuardarRegistroPatronal = true;
					  $scope.IsVisibleBotonActualizarRegistroPatronal = false;
					  $scope.cargaInicialRegistroPatronal();

					  
				  }
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
			};
			
			// servicio que realiza la accion de actualizar
			$scope.actualizarRegistroPatronal = function() {				
				agregarPrestadoraServicioService.guardaRegistroPatronal($scope.prestadoraRegistroPatronal,function(response) {
				  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
					  bootbox.alert({
							title : "Error",
							message : "Ocurrio un error al realizar la el resguardo del registro. Favor de intentarlo mas tarde",
							buttons : {
								ok : {
									label : 'ACEPTAR',
									className : 'center-block btn-primary'
								}
							},
							callback : function() {								
								$scope.prestadoraRegistroPatronal.registroPatronal = null;
								$scope.cargaInicialRegistroPatronal();
								$scope.IsVisibleBotonGuardarRegistroPatronal = true;
								$scope.IsVisibleBotonActualizarRegistroPatronal = false;
								$scope.prestadoraRegistroPatronal = {};
								
							}
						});
				  }else{					  
					  $scope.prestadoraRegistroPatronal.registroPatronal = null;
					  $scope.cargaInicialRegistroPatronal();
					  $scope.IsVisibleBotonGuardarRegistroPatronal = true;
					  $scope.IsVisibleBotonActualizarRegistroPatronal = false;
					  $scope.prestadoraRegistroPatronal = {};
					 
					  
				  }
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
				
			}
			
			  $scope.eliminarRegistroPatronal = function(registro) {
				  bootbox.confirm({
					  title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas eliminar el registro patronal?",
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
								agregarPrestadoraServicioService.eliminarRegistroPatronal(registro, function(response) {

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
																							
											  $scope.prestadoraRegistroPatronal.registroPatronal = null;
											  $scope.IsVisibleBotonGuardarRegistroPatronal = true;
											  $scope.IsVisibleBotonActualizarRegistroPatronal = false;
											  $scope.cargaInicialRegistroPatronal();

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
			
			$scope.cancelarActualizacionRegisPatron = function() {

			  $scope.IsVisibleBotonGuardarRegistroPatronal = true;
			  $scope.IsVisibleBotonActualizarRegistroPatronal = false;
			  $scope.prestadoraRegistroPatronal = {};
			  $scope.cargaInicialRegistroPatronal();
			
			}
			
			// opcion del grid
			$scope.actualizarConceptoFacturacion = function(registro) {				
				$scope.IsVisibleBotonGuardarRegistroPatronal = false;
				$scope.IsVisibleBotonActualizarRegistroPatronal = true;
				
				$scope.prestadoraRegistroPatronal = angular.copy(registro);
			}
			

//////////////CLASE, FRACCION, PRIMA ///////////////////////////////////////////

			  $scope.cargaInicialClseFraccionPrima = function() {
				  agregarPrestadoraServicioService.cargaInicialClasePrimaFraccion(function(response) {
					  
					  $scope.presatdoraClase = {};
					  
					  if(response.data.presatdoraClase!=undefined && response.data.presatdoraClase!=null){
						  $scope.presatdoraClase = response.data.presatdoraClase;
					  }

					  $scope.listaCatClase = response.data.listaCatClase;
//					  $scope.listaCatRiesgo = response.data.listaCatRiesgo;

	  
				  },function(response) {
							$log.error("error --> " + reponse);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error, favor de verificar.',
						        type: 'error'
						      });

						});
			  }
			  
			  $scope.guardarClaseFraccionPrima = function() {
				  agregarPrestadoraServicioService.guardaClasePrimaFraccion($scope.presatdoraClase,function(response) {
					  
					  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
						  
						  $scope.cargaInicialClseFraccionPrima();
						  
					  }
				  },function(response) {
						$log.error("error --> " + response);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
					        type: 'error'
					      });

					});
			  }
			
			
//////////////          IMSS     ///////////////////////////////////////////

			  $scope.cargaInicialPrestadoraImss = function() {
				  agregarPrestadoraServicioService.cargaInicialPrestadoraImss(function(response) {
					  
					  $scope.presatdoraImms = {};
					  
					  if(response.data.presatdoraImms!=undefined && response.data.presatdoraImms!=null){
						  $scope.presatdoraImms = response.data.presatdoraImms;
					  }
	  
				  },function(response) {
							$log.error("error --> " + reponse);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error, favor de verificar.',
						        type: 'error'
						      });

						});
			  }
			  
			  $scope.guardarPresatdoraImss = function() {
				  agregarPrestadoraServicioService.guardaPrestadoraImss($scope.presatdoraImms,function(response) {
					  
					  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
						  
						  $scope.cargaInicialPrestadoraImss();
						  
					  }
				  },function(response) {
						$log.error("error --> " + response);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
					        type: 'error'
					      });

					});
			  }
		 
//////////////        OBJETO SOCIAL      ///////////////////////////////////////////     
			  
			  $scope.cargaInicialObjetoSocial = function() {
				  agregarPrestadoraServicioService.cargaInicialObjetoSocial(function(response) {
					  
					  $scope.prestadoraObjetoSocial = {};
					  					  
					  $scope.gridObjetoSocial = response.data.gridObjetoSocial;
					  
				  },function(response) {
							$log.error("error --> " + reponse);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error, favor de verificar.',
						        type: 'error'
						      });

						});
			  }			  
			  

				  $scope.guardaObjetoSocial = function() {
		  			  
					  agregarPrestadoraServicioService.guardaObjetoSocial($scope.prestadoraObjetoSocial,function(response) {
						  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
							  $log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al realizar la el resguardo del objeto social. Favor de intentarlo mas tarde',
							        type: 'error'
							      });
										$scope.prestadoraObjetoSocial.descripcion = "";
										$scope.cargaInicialObjetoSocial();
										$scope.IsVisibleBotonGuardarObjetoSocial = true;
										$scope.IsVisibleBotonActualizarObjetoSocial = false;
						  }else{

							  $scope.prestadoraObjetoSocial.descripcion = "";
								$scope.cargaInicialObjetoSocial();
								$scope.IsVisibleBotonGuardarObjetoSocial = true;
								$scope.IsVisibleBotonActualizarObjetoSocial = false;

						  }
					  },function(response) {
							$log.error("error --> " + response);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
						        type: 'error'
						      });

						});
					};
					
					
					$scope.actualizarObjeto = function() {				
						agregarPrestadoraServicioService.guardaObjetoSocial($scope.prestadoraObjetoSocial,function(response) {
						  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
							  bootbox.alert({
									title : "Error",
									message : "Ocurrio un error al realizar la el resguardo del objeto social. Favor de intentarlo mas tarde",
									buttons : {
										ok : {
											label : 'ACEPTAR',
											className : 'center-block btn-primary'
										}
									},
									callback : function() {	
										
										
										$scope.prestadoraObjetoSocial = {};
										$scope.prestadoraObjetoSocial.descripcion = "";
										$scope.IsVisibleBotonGuardarObjetoSocial = true;
										$scope.IsVisibleBotonActualizarObjetoSocial = false;
										$scope.cargaInicialObjetoSocial();

									}
								});
						  }else{					  
							  
							  
							  $scope.IsVisibleBotonGuardarObjetoSocial = true;
							  $scope.IsVisibleBotonActualizarObjetoSocial = false;
							  $scope.prestadoraObjetoSocial = {};
							  $scope.prestadoraObjetoSocial.descripcion = "";
							  $scope.cargaInicialObjetoSocial();
							  
						  }
					  },function(response) {
							$log.error("error --> " + response);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
						        type: 'error'
						      });

						});
						
					}
					
					  $scope.eliminarObjetoSocial = function(registro) {
						  bootbox.confirm({
							  title : "Confirmar acci&oacute;n",
								message : "¿Est\u00e1s seguro que deseas eliminar el objeto social?",
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
										agregarPrestadoraServicioService.eliminarObjetoSocial(registro, function(response) {

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

													  
												  $scope.prestadoraObjetoSocial.descripcion = "";
												  $scope.IsVisibleBotonGuardarObjetoSocial = true;
												  $scope.IsVisibleBotonActualizarObjetoSocial = false;
												  $scope.cargaInicialObjetoSocial();

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
					
					
					$scope.cancelarActualizacionObjetoSocial = function() {

						  $scope.IsVisibleBotonGuardarObjetoSocial = true;
						  $scope.IsVisibleBotonActualizarObjetoSocial = false;
						  $scope.prestadoraObjetoSocial = {};
						  $scope.cargaInicialObjetoSocial();
						
					}
					
					// opcion del grid
					$scope.actualizarObjetoSocial = function(registro) {				
						$scope.IsVisibleBotonGuardarObjetoSocial = false;
						$scope.IsVisibleBotonActualizarObjetoSocial = true;
						
						$scope.prestadoraObjetoSocial = angular.copy(registro);
					}
			  
			  
		    $scope.reset = function() {
		    	$scope.clienteActividad = {};
		      }
		    	
		    	
		    
 /////// Logotipo
		    $scope.fileChangedLogotipo = function (documento) {
		          var lstArchivos = documento.files;

		          var val = lstArchivos[0].name.toLowerCase();
		          var regex = new RegExp("(.*?)\.(jpg|JPG|png|PNG)$");

		          if (!(regex.test(val))) {
		              $(this).val('');
		              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.jpg y/o .png</b>";
		              pinesNotifications.notify({
					        title: 'Error',
					        text: $scope.mensaje,
					        type: 'error'
					      });

		          }else if (lstArchivos[0].size > 2097152) {
		              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
		              $scope.$apply();
		              alert($scope.mensaje);
		          } else {
		              var reader = new FileReader();
		              reader.onloadend = function () {
		                  $log.debug("Archivo cargado memoria");
		                  $scope.data.archivoLogotipo.archivo = reader.result;
		                  $scope.logotipo = reader.result;
		                  $scope.data.archivoLogotipo.nombreArchivoLogotipo= lstArchivos[0].name;
		                  $scope.data.archivoLogotipo.tamanioArchivoLogotipo = lstArchivos[0].size;
		              }
		              reader.readAsDataURL(lstArchivos[0]);
		          }

		      };
		      
		      
		      $scope.descargarLogotipo= function(prestadoraServicioDto){
		    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/descargarArchivo.json", prestadoraServicioDto).then(
	                         function (response) {
	                      	   var link = document.createElement("a");
	                      	   link.href = response.data.documento;
	                      	   link.style = "visibility:hidden";
	                      	   link.download = $scope.prestadoraServicioDto.nombreArchivoLogotipo;
	                      	   document.body.appendChild(link);
	                      	   link.click();
	                      	   document.body.removeChild(link);
	              },function(data) {
	                  console.log("fallo al descargar el archivo en el metodo descargarArchivo");
	              });
		      }
		      $scope.logotipo = "";
		      $scope.verLogotipo= function(prestadoraServicioDto){
		    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/descargarArchivo.json", prestadoraServicioDto).then(
	                  function (response) {
	                      $scope.logotipo = response.data.documento;
		              },function(data) {
		                  console.log("fallo al descargar el archivo en el metodo descargarArchivo");
		              });
		      }
		      
		      
		      $scope.obtenerCatProductoSat = function(codigoSat){
				  $http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerCatProductoSat.json", codigoSat).then(
							function(response) {
								if(response.data.clave != null || response.data.clave != undefined){
								$scope.prestadoraServicioProductoDto.idCatGeneral = response.data.idCatGeneral;
								$scope.prestadoraServicioProductoDto.clave = response.data.clave;
//								$scope.prestadoraServicioProductoDto.descripcion = response.data.descripcion;
//								}else{
//									$scope.prestadoraServicioProductoDto.descripcion = '';
								}
							}, function(data) {
								console.log("error --> " + data);
							});
		  }
		      
		      
		      $scope.setDispersor = function(dispersor){
				var prueba = dispersor;
		  }
		      
		  $scope.limpiarFormProducto = function(){
			  $scope.panelAgregarProducto = false;
			
					$scope.prestadoraServicioProductoDto.clave='';
					$scope.prestadoraServicioProductoDto.descripcionProductoConsolida='';
					$scope.prestadoraServicioProductoDto.idPrestadoraServicioProducto = null;
		}
		  
		  $scope.limpiarFormDispersor = function(){
			  $scope.panelAgregarDispersor = false;
			 
		}
		  

		      
		  $scope.editarProducto = function(producto){
			  $scope.panelAgregarProducto = true;
			 
			$scope.prestadoraServicioProductoDto.idCatGeneral = producto.idCatGeneral;
			$scope.prestadoraServicioProductoDto.clave = producto.clave;
			$scope.prestadoraServicioProductoDto.descripcionProductoConsolida = producto.descripcionProductoConsolida;
			$scope.prestadoraServicioProductoDto.idPrestadoraServicioProducto = producto.idPrestadoraServicioProducto;
								
		  }
		  
		  $scope.editarDispersor = function(dispersor){
			  $scope.panelAgregarDispersor =  true;
			  $scope.edicionDispersor=dispersor;
								
		  }
		  
		
		  
		  $scope.mostrarPanelAgregarProducto= function(){
			  $scope.panelAgregarProducto = true;
			  setTimeout("$('html, body').animate({scrollTop: ($('#panelAgregarProducto').offset().top)-65}, 1500);",1000);
			  $scope.prestadoraServicioProductoDto.clave='';
				$scope.prestadoraServicioProductoDto.descripcionProductoConsolida = '';
				$scope.prestadoraServicioProductoDto.idPrestadoraServicioProducto = null;
		  }
		  
		  $scope.mostrarPanelAgregarDispersor= function(){
			  $scope.edicionDispersor={};
			  $scope.edicionDispersor.activo=false;
			  $scope.panelAgregarDispersor = true;
			  setTimeout("$('html, body').animate({scrollTop: ($('#panelAgregarDispersor').offset().top)-65}, 1500);",1000);
			
		  }
		      
		  
		  $scope.eliminarProducto = function(producto){
			$http.post(CONFIG.APIURL + "/prestadoraServicio/eliminarProducto.json", producto).then(
				function(response) {
					$scope.limpiarFormProducto();
					$log.debug('ok');
					pinesNotifications.notify({
				        title: 'Mensaje',
				        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
				        type: 'success'
				      });
					 $scope.limpiarFormProducto();
					$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicialProductos.json").then(
							function(data) {
								$scope.prestadoraServicioDto = data.data;
								$scope.verLogotipo($scope.prestadoraServicioDto);
							}, function(data) {
								console.log("error --> " + data);
							});
							}, function(data) {
								console.log("error --> " + data);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al eliminar el producto, favor de intentarlo nuevamente.',
							        type: 'error'
							      });
							});
		  }
		  
		  
		  $scope.mostrarModalCatSat = function() {
//		        $('#agregarCodigoSat').modal('show');
		        $http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerCatProductoSatCompleto.json").then(
						function(response) {
							 $scope.prestadoraServicioProductoCatSatDto.catSat = response.data;
						}, function(data) {
							console.log("error --> " + data);
						});
		    };
		    
		    $scope.cerrarModalCatSat = function() {
		        $('#agregarCodigoSat').modal('hide');
		    };
		    
		    
		    $scope.mostrarAgregarCatSat = function() {
		    	$scope.agregarCatSat = true;
		    }
		    
		    $scope.cancelarAgregarCodigoSat = function() {
		    	$scope.agregarCatSat = false;
		    	$scope.productoSat = {};
		    	 $http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerCatProductoSatCompleto.json").then(
							function(response) {
								$scope.prestadoraServicioProductoCatSatDto.catSat = response.data;
								
							}, function(data) {
								console.log("error --> " + data);
							});
		    }
		    
		    $scope.guardarProductoSat = function(registro) {
				  bootbox.confirm({
					  title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas guardar?",
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
								agregarPrestadoraServicioService.guardarProductoSat(registro, function(response) {

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
										 $http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerCatProductoSat.json", registro.clave).then(
													function(response) {
														
														$scope.prestadoraServicioProductoDto.idCatGeneral = response.data.idCatGeneral;
														$scope.prestadoraServicioProductoDto.clave = response.data.clave;
														$scope.prestadoraServicioProductoDto.descripcion = response.data.descripcion;
														
													}, function(data) {
														console.log("error --> " + data);
													});
										$scope.cancelarAgregarCodigoSat();
										$scope.cerrarModalCatSat();
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
//////////////////////////////////////////////////////  REPRESENTANTES LEGALES //////////////////////////////////////////
				
				  $scope.cargaInicialRepresentanteLegal = function() {
					  agregarPrestadoraServicioService.cargaInicialRepresentanteLegal(function(response) {
						  $scope.gridRepresentantesLegales = response.data.gridRepresentantesLegales;
						  $scope.IsVisibleRepresentanteArchivos = false;
						  $scope.IsVisibleFormularioRepresentante = false;
						  $scope.IsVisibleDocumentosRepresentante = false;
						  $scope.representanteLegalDto = {};
					  },function(response) {
							$log.error("error --> " + response);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
						        type: 'error'
						      });

						});
					};
						
			    $scope.guardarRepresentanteLegal = function(representanteLegalForm) {
			    	agregarPrestadoraServicioService.guardarRepresentanteLegal($scope.representanteLegalDto,function(response) {
						  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
								
								if(representanteLegalForm){
									representanteLegalForm.$setPristine();
									representanteLegalForm.$setUntouched();
								}
								
								$scope.IsVisibleFormularioRepresentante = false;
								$scope.IsVisibleDocumentosRepresentante = false;
								$scope.cargaInicialRepresentanteLegal();

						  }
					  },function(response) {
							$log.error("error --> " + response);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
						        type: 'error'
						      });
							
							$scope.IsVisibleFormularioRepresentante = false;
							$scope.IsVisibleDocumentosRepresentante = false;
							$scope.cargaInicialRepresentanteLegal();

						});
					};
					
				    $scope.eliminarRepresentanteLegal = function(representante) {
						  bootbox.confirm({
							  title : "Confirmar acci&oacute;n",
								message : "¿Est\u00e1s seguro de eliminar al  representante legal?",
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
										agregarPrestadoraServicioService.eliminarRepresentanteLegal(representante, function(response) {

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

												$scope.IsVisibleFormularioRepresentante = false;
												$scope.IsVisibleDocumentosRepresentante = false;
												$scope.cargaInicialRepresentanteLegal();
											}
										},
										function(response) {
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
										        type: 'error'
										      });
											
											$scope.IsVisibleFormularioRepresentante = false;
											$scope.IsVisibleDocumentosRepresentante = false;
											$scope.cargaInicialRepresentanteLegal();
										});
									}
								}				  
						  });
						};
					
					
					// accion que carga los campos para su edicion
					$scope.actualizarRepresentanteLegal = function(representante) {	
						
						$scope.tituloDinamicoLegal = 'ACTUALIZAR INFORMACIÓN REPRESENTANTE LEGAL';
						
						$scope.IsVisibleDocumentosRepresentante = false;
						
						$scope.representanteLegalDto = {};
						$scope.IsVisibleFormularioRepresentante = true;
						$scope.representanteLegalDto = angular.copy(representante);
						
						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;
						$scope.IsVisibleRepresentanteArchivos = false;
						
					}
					
					$scope.nuevoRepresentanteLegal = function() {
						
						$scope.tituloDinamicoLegal = 'REGISTRO DE REPRESENTANTE LEGAL';
						
						$scope.representanteLegalDto = {};
						$scope.IsVisibleFormularioRepresentante = true;
						$scope.IsVisibleDocumentosRepresentante = false;
						
						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;
						$scope.IsVisibleRepresentanteArchivos = false;
					}
					
					$scope.showDocumentosRepresentante = function(representante) {

						$scope.representanteLegalDto = angular.copy(representante);
						
						$scope.cargaInicialDocumentosPrestadoraRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);
						
						$scope.IsVisibleFormularioRepresentante = false;
						$scope.IsVisibleDocumentosRepresentante = true;
						
						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;
						$scope.IsVisibleRepresentanteArchivos = false;
						
					}
					
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////APODERADOS LEGALES //////////////////////////////////////////
					
					  $scope.cargaInicialApoderadoLegal = function() {
						  agregarPrestadoraServicioService.cargaInicialApoderadoLegal(function(response) {
							  $scope.gridApoderadosLegales = response.data.gridApoderadosLegales;
							  $scope.catFacultades = response.data.catFacultades;
							  $scope.IsVisibleFormularioApoderado = false;
							  $scope.IsVisibleDocumentosApoderado = false;
							  $scope.apoderadoLegalDto = {};
						  },function(response) {
								$log.error("error --> " + response);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
							        type: 'error'
							      });

							});
						};
						
						
					    $scope.guardarApoderadoLegal = function(apoderadoLegalForm) {
					    	agregarPrestadoraServicioService.guardarApoderadoLegal($scope.apoderadoLegalDto,function(response) {
								  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
										
										
										if(apoderadoLegalForm){
											apoderadoLegalForm.$setPristine();
											apoderadoLegalForm.$setUntouched();
										}
										
										$scope.IsVisibleFormularioApoderado = false;
										$scope.IsVisibleDocumentosApoderado = false;
										$scope.cargaInicialApoderadoLegal();

								  }
							  },function(response) {
									$log.error("error --> " + response);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
								        type: 'error'
								      });
									
									$scope.IsVisibleFormularioApoderado = false;
									$scope.IsVisibleDocumentosApoderado = false;
									$scope.cargaInicialApoderadoLegal();

								});
							};
							
						    $scope.eliminarApoderadoLegal = function(apoderado) {
								  bootbox.confirm({
									  title : "Confirmar acci&oacute;n",
										message : "¿Est\u00e1s seguro de eliminar al  apoderado legal ?",
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
												agregarPrestadoraServicioService.eliminarApoderadoLegal(apoderado, function(response) {

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

														$scope.IsVisibleFormularioApoderado = false;
														$scope.IsVisibleDocumentosApoderado = false;
														$scope.cargaInicialApoderadoLegal();
													}
												},
												function(response) {
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
												        type: 'error'
												      });
													
													$scope.IsVisibleFormularioApoderado = false;
													$scope.IsVisibleDocumentosApoderado = false;
													$scope.cargaInicialApoderadoLegal();
												});
											}
										}				  
								  });
								};
								
								// accion que carga los campos para su edicion
								$scope.actualizarApoderadoLegal = function(apoderado) {
									
									$scope.tituloDinamicoLegal = 'ACTUALIZAR INFORMACIÓN APODERADO LEGAL';
									
									$scope.IsVisibleDocumentosApoderado = false;
									
									$scope.apoderadoLegalDto = {};
									$scope.IsVisibleFormularioApoderado = true;
									$scope.apoderadoLegalDto = angular.copy(apoderado);
									
									// se cierran formularios de representante
									$scope.IsVisibleFormularioRepresentante = false;
									$scope.IsVisibleDocumentosRepresentante = false;
									
								}
								
								$scope.nuevoApoderadoLegal = function() {
									
									$scope.tituloDinamicoLegal = 'REGISTRO DE APODERADO LEGAL';
									
									$scope.apoderadoLegalDto = {};
									$scope.IsVisibleFormularioApoderado = true;
									$scope.IsVisibleDocumentosApoderado = false;
									
									// se cierran formularios de representante
									$scope.IsVisibleFormularioRepresentante = false;
									$scope.IsVisibleDocumentosRepresentante = false;
									
								}
								
								$scope.showDocumentoApoderado = function(apoderado) {
									
									$scope.apoderadoLegalDto = angular.copy(apoderado);
									
									$scope.cargaInicialDocumentosPrestadoraApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);
									
									$scope.IsVisibleDocumentosApoderado = true;
									$scope.IsVisibleFormularioApoderado = false;
									
									// se cierran formularios de representante
									$scope.IsVisibleFormularioRepresentante = false;
									$scope.IsVisibleDocumentosRepresentante = false;
									
								}
								
								
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////// ACCIONISTAS //////////////////////////////////////////
								
								$scope.cargaInicialAccionista = function() {
									  agregarPrestadoraServicioService.cargaInicialAccionista(function(response) {
										  $scope.gridAccionistas = response.data.gridAccionistas;
										  $scope.entidadFederativa = response.data.entidadFederativa;
										  $scope.IsVisibleFormularioAccionista = false;
										  $scope.IsVisibleDocumentosAccionista = false;
										  $scope.accionistaDto = {};
									  },function(response) {
											$log.error("error --> " + response);
											pinesNotifications.notify({
										        title: 'Error',
										        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
										        type: 'error'
										      });

										});
									};
									$scope.sumatoriaPorcentajeAccionista = function(){
										var total = 0;
										if($scope.gridAccionistas != undefined && $scope.gridAccionistas.length != undefined){
										    for(var i = 0; i < $scope.gridAccionistas.length; i++) {
										        var aux = $scope.gridAccionistas[i];
										        total += aux.porcentajeAccion;
										    }
										}
										return total;
									}
									$scope.validaPorcentajeIngresado = function(){
										var valorPorcentaje = parseFloat($scope.accionistaDto.porcentajeAccion);
										var total = 0;
										if($scope.gridAccionistas != undefined && $scope.gridAccionistas.length != undefined){
										    for(var i = 0; i < $scope.gridAccionistas.length; i++) {
										        var aux = $scope.gridAccionistas[i];
										        total += aux.porcentajeAccion;
										    }
										}
										return (total + valorPorcentaje - $scope.porcentajeAccionistaSelect);
									}
									
									
								    $scope.guardarAccionista = function(accionistaForm) {
								    	agregarPrestadoraServicioService.guardarAccionista($scope.accionistaDto,function(response) {
											  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
													
													if(accionistaForm){
														accionistaForm.$setPristine();
														accionistaForm.$setUntouched();
													}
													
													$scope.IsVisibleFormularioAccionista = false;
													$scope.cargaInicialAccionista();

											  }
										  },function(response) {
												$log.error("error --> " + response);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
											        type: 'error'
											      });
												
												$scope.IsVisibleFormularioAccionista = true;
//												$scope.cargaInicialAccionista();

											});
										};
										
										$scope.eliminarAccionista = function(accionista) {
											  bootbox.confirm({
												  title : "Confirmar acci&oacute;n",
													message : "¿Est\u00e1s seguro de eliminar al  accionista ?",
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
															agregarPrestadoraServicioService.eliminarAccionista(accionista, function(response) {

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

																	
																	$scope.IsVisibleFormularioAccionista = false;
																	$scope.IsVisibleDocumentosAccionista = false;
																	$scope.cargaInicialAccionista();
																}
															},
															function(response) {
																$log.error(response.status+ ' - '+ response.statusText);
																pinesNotifications.notify({
															        title: 'Error',
															        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
															        type: 'error'
															      });
																
																$scope.IsVisibleFormularioAccionista = false;
																$scope.IsVisibleDocumentosAccionista = false;
																$scope.cargaInicialAccionista();
															});
														}
													}				  
											  });
											};
											
											// accion que carga los campos para su edicion
											$scope.actualizarAccionista = function(accionista) {
												$scope.porcentajeAccionistaSelect=0;
												$scope.porcentajeAccionistaSelect=accionista.porcentajeAccion;
												$scope.accionistaDto = {};
												$scope.IsVisibleDocumentosAccionista = false;
												$scope.IsVisibleFormularioAccionista = true;
												accionista.fechaNacimiento =  new Date(accionista.fechaNacimiento);  
												  
												$scope.accionistaDto = angular.copy(accionista);
												 $scope.obtenerEntidadFederativaXCPAccionistas($scope.accionistaDto.domicilioDto.codigoPostal);
												 setTimeout("$('html, body').animate({scrollTop: ($('#pnlRegistroAccionista').offset().top)-65}, 1500);",1000);
											}
											
											$scope.nuevoAccionista = function() {
												$scope.accionistaDto = {};
												$scope.IsVisibleFormularioAccionista = true;
												$scope.IsVisibleDocumentosAccionista = false;
												setTimeout("$('html, body').animate({scrollTop: ($('#pnlRegistroAccionista').offset().top)-65}, 1500);",1000);
											}
											
											$scope.docmuentosAccionista = function(accionista) {
												
												$scope.accionistaDto = angular.copy(accionista);
												
												$scope.cargaInicialDocumentosPrestadoraAccionista($scope.accionistaDto.idGenericoAccionista);
												
												$scope.IsVisibleDocumentosAccionista = true;
												$scope.IsVisibleFormularioAccionista = false;
												setTimeout("$('html, body').animate({scrollTop: ($('#pnlDocumentosAccionista').offset().top)-65}, 1500);",1000);
											}

											
				  $scope.mostrarModalDocumentoPrestadora = function(itemDefinicionDocumento) {
					  	$scope.archivoPrestadora = {};
					  	
					  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
				        $('#agregarDocumentoPrestadora').modal('show');
				        
				        var fileElement = angular.element('#file_prestadora');
				        angular.element(fileElement).val(null);
				         

				    };
				    
					  $scope.mostrarModalDocumentoApoderado = function(itemDefinicionDocumento) {

						  	$scope.archivoPrestadora = {};
						  	itemDefinicionDocumento.idPrestadoraServicioApoderadoLegal = $scope.apoderadoLegalDto.idGenericoApoderadoLegal;
						  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
						  	
					        $('#agregarDocumentoApoderado').modal('show');
					        
					        var fileElement = angular.element('#file_apoderado');
					        angular.element(fileElement).val(null);

					    };
					    
						  $scope.mostrarModalDocumentoRepresentante = function(itemDefinicionDocumento) {

							  	$scope.archivoPrestadora = {};
							  	itemDefinicionDocumento.idPrestadoraServicioRepresentanteLegal = $scope.representanteLegalDto.idGenericoRepresentanteLegal;
							  							
							  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
							  	
						        $('#agregarDocumentoRepresentante').modal('show');
						        
						        var fileElement = angular.element('#file_representante');
						        angular.element(fileElement).val(null);

						    };
						    
						    $scope.mostrarModalDocumentoAccionista = function(itemDefinicionDocumento) {

							  	$scope.archivoPrestadora = {};
							  	itemDefinicionDocumento.idPrestadoraServicioAccionista = $scope.accionistaDto.idGenericoAccionista;
							  							
							  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
							  	
						        $('#agregarDocumentoAccionista').modal('show');
						        
						        var fileElement = angular.element('#file_accionista');
						        angular.element(fileElement).val(null);

						    };

				    
					  $scope.fileChangedDocPrestadora = function (documento) {
						  
				          var lstArchivos = documento.files;
				          var val = lstArchivos[0].name.toLowerCase();
				          
				          
				        
				          var regex = new RegExp("(.*?)\.(pdf|docx|png|jpg)$");

				          if (!(regex.test(val))) {
				              $(this).val('');
				              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.pdf, .docx, .png, y/o .jpg</b>";
				              pinesNotifications.notify({
							        title: 'Error',
							        text: $scope.mensaje,
							        type: 'error'
							      });
				          }else if (lstArchivos[0].size > 2097152) {
				              $scope.mensaje = "El archivo excede el límite  de " + (20971502 / 1024 / 1024) + "MB";
				              $scope.$apply();
				              alert($scope.mensaje);
				          } else {
				              var reader = new FileReader();
				              
				              reader.onloadend = function () {
				                  $log.debug("Archivo cargado memoria");
				                  
				                  var documento = {};
				                  documento.mimeType = reader.result.substr(0,reader.result.indexOf(',')+1);
				                  documento.archivo = reader.result.substr(reader.result.indexOf(',') + 1);
				                  documento.nombreArchivo = lstArchivos[0].name;
				                  documento.tamanioArchivo = lstArchivos[0].size;
				                  
				                  $scope.itemDefinicionDocumento.documentoNuevo = documento;
				              }
				              
				              reader.readAsDataURL(lstArchivos[0]);
				              
				          }

				      };
				     				      
				      $scope.guardarDocumentoPrestadora = function(){
				    	  
				    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
				    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
				    		  
				    		  pinesNotifications.notify({
							        title: 'Error',
							        text: 'Es necesario adjuntar el documento',
							        type: 'error'
							      });
				    		  
				    		  return;
				    	  }
				    	  
				    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/guardarDocumentosPrestadora.json", $scope.itemDefinicionDocumento).then(
				                  function (response) {
				                	  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										
										$scope.cargaInicialDocumentosPrestadora();

										$('#agregarDocumentoPrestadora').modal('hide');
				                  },
				                  function (data) {
				                	  $log.error(data.status+ ' - '+ data.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
									        type: 'error'
									      });
				                  });
				    	  
				    	  
				      }
				      
				      $scope.guardarDocumentoApoderado = function(){
				    	  
				    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
				    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
				    		  
				    		  pinesNotifications.notify({
							        title: 'Error',
							        text: 'Es necesario adjuntar el documento',
							        type: 'error'
							      });
				    		  
				    		  return;
				    	  }
				    	  
				    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/apoderadoLegal/guardarDocumentosApoderado.json", $scope.itemDefinicionDocumento).then(
				                  function (response) {
				                	  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										
										$scope.cargaInicialDocumentosPrestadoraApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);
										$('#agregarDocumentoApoderado').modal('hide');
				                  },
				                  function (data) {
				                	  $log.error(data.status+ ' - '+ data.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
									        type: 'error'
									      });
				                  });
				    	  
				    	  
				      }
				      
				      	$scope.guardarDocumentoRepresentante = function(){
				    	  
				    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
				    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
				    		  
				    		  pinesNotifications.notify({
							        title: 'Error',
							        text: 'Es necesario adjuntar el documento',
							        type: 'error'
							      });
				    		  
				    		  return;
				    	  }
				    	  
				    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/representanteLegal/guardarDocumentosPrestadoraRepresentante.json", $scope.itemDefinicionDocumento).then(
				                  function (response) {
				                	  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										
										
										$scope.cargaInicialDocumentosPrestadoraRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);
										$('#agregarDocumentoRepresentante').modal('hide');
				                  },
				                  function (data) {
				                	  $log.error(data.status+ ' - '+ data.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
									        type: 'error'
									      });
				                  });
				      	}
				      	
				      	$scope.guardarDocumentoAccionista = function(){
					    	  
					    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
					    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
					    		  
					    		  pinesNotifications.notify({
								        title: 'Error',
								        text: 'Es necesario adjuntar el documento',
								        type: 'error'
								      });
					    		  
					    		  return;
					    	  }
					    	  
					    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/accionista/guardarDocumentosAccionista.json", $scope.itemDefinicionDocumento).then(
					                  function (response) {
					                	  $log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });
											
											$scope.cargaInicialDocumentosPrestadoraAccionista($scope.accionistaDto.idGenericoAccionista);
											$('#agregarDocumentoAccionista').modal('hide');
					                  },
					                  function (data) {
					                	  $log.error(data.status+ ' - '+ data.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
										        type: 'error'
										      });
					                  });
					      	}
				      
				      	$scope.eliminarDocumentoApoderado = function(itemDefinicionDocumento){
				    	  
				      		bootbox.confirm({
								  title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
											
											 $http.post(CONFIG.APIURL + "/prestadoraServicio/apoderadoLegal/eliminarDocumentosApoderado.json",itemDefinicionDocumento).then(
									                  function (response) {
									                	  $log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });
															
															$scope.cargaInicialDocumentosPrestadoraApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);
															
									                  },
									                  function (data) {
									                	  $log.error(data.status+ ' - '+ data.statusText);
															pinesNotifications.notify({
														        title: 'Error',
														        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
														        type: 'error'
														      });
									                  });
											
										}
									}				  
							  });
				      }
				      	
				      	$scope.eliminarDocumentoRepresentante = function(itemDefinicionDocumento){
					    	  
				      		bootbox.confirm({
								  title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
											
											 $http.post(CONFIG.APIURL + "/prestadoraServicio/representanteLegal/eliminarDocumentosRepresentante.json",itemDefinicionDocumento).then(
									                  function (response) {
									                	  $log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });
															
															$scope.cargaInicialDocumentosPrestadoraRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);
															
									                  },
									                  function (data) {
									                	  $log.error(data.status+ ' - '+ data.statusText);
															pinesNotifications.notify({
														        title: 'Error',
														        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
														        type: 'error'
														      });
									                  });
											
										}
									}				  
							  });
				      }
				      	
				      	$scope.eliminarDocumentoAccionista = function(itemDefinicionDocumento){
					    	  
				      		bootbox.confirm({
								  title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
											
											 $http.post(CONFIG.APIURL + "/prestadoraServicio/accionista/eliminarDocumentosAccionista.json",itemDefinicionDocumento).then(
									                  function (response) {
									                	  $log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });
															
															$scope.cargaInicialDocumentosPrestadoraAccionista($scope.accionistaDto.idGenericoAccionista);
															
									                  },
									                  function (data) {
									                	  $log.error(data.status+ ' - '+ data.statusText);
															pinesNotifications.notify({
														        title: 'Error',
														        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
														        type: 'error'
														      });
									                  });
											
										}
									}				  
							  });
				      }
				      	


											 $scope.obtenerEntidadFederativaXCPAccionistas = function(codigoPostal) {
												  agregarPrestadoraServicioService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
													  $scope.accionistaDto.municipios = response.data.municipios;
													  if(response.data.prestadoraServicioDomicilioDto != null || response.data.prestadoraServicioDomicilioDto != undefined){
													  $scope.accionistaDto.domicilioDto.idEntidadFederativa = response.data.prestadoraServicioDomicilioDto.domicilio.idEntidadFederativa;
													  $scope.accionistaDto.domicilioDto.catMunicipios = response.data.prestadoraServicioDomicilioDto.domicilio.catMunicipios;
													  }else{
														  
														  if(($scope.accionistaDto.domicilioDto !=undefined && $scope.accionistaDto.domicilioDto !=null)
																  && ($scope.accionistaDto.domicilioDto.idEntidadFederativa != undefined && $scope.accionistaDto.domicilioDto.idEntidadFederativa !=null)){
															  $scope.accionistaDto.domicilioDto.idEntidadFederativa = '';
															  $scope.accionistaDto.domicilioDto.catMunicipios = '';
														  }
														  
														  
													  }
											    	},function(response){
														
													});
											    }	
				
		$scope.cargaInicialDocumentosPrestadoraFielCsd = function (){
				var listaDefinicionDocto= '11,12,13,14';
			$http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerDoctosPrestadoraXDefinicionDoctoXIdPrestadora.json", listaDefinicionDocto).then(
			         function(data) {
			   $scope.documentosPrestadoraGenerico = data.data;
				   }, function(data) {
			   console.log("error --> " + data);
			});
			}
													
		$scope.mostrarModalDocumentoPrestadoraFielCsd = function(itemDefinicionDocumento) {
			$scope.tipoDoc = angular.lowercase(itemDefinicionDocumento.definicion.nombreDocumento);
			$scope.archivoPrestadora = {};
			$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
			$('#agregarDocumentoPrestadoraFielCsd').modal('show');
		 };
													
		$scope.guardarDocumentoPrestadoraFielCsd = function(){
		  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
				$scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
												    		  
												    		  pinesNotifications.notify({
															        title: 'Error',
															        text: 'Es necesario adjuntar el documento',
															        type: 'error'
															      });
												    		  
												    		  return;
												    	  }
												    	  
												    	  $http.post(CONFIG.APIURL + "/prestadoraServicio/guardarDocumentosPrestadoraFielCsd.json", $scope.itemDefinicionDocumento).then(
												                  function (response) {
												                	  $log.debug('ok');
																		pinesNotifications.notify({
																	        title: 'Mensaje',
																	        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
																	        type: 'success'
																	      });
																		
																		$scope.cargaInicialDocumentosPrestadoraFielCsd();
																		var fileElement = angular.element('#fielKeyCsd');
																        angular.element(fileElement).val(null);
																		$('#agregarDocumentoPrestadoraFielCsd').modal('hide');
												                  },
												                  function (data) {
												                	  $log.error(data.status+ ' - '+ data.statusText);
																		pinesNotifications.notify({
																	        title: 'Error',
																	        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																	        type: 'error'
																	      });
												                  });
								}
										
										
										$scope.eliminarDocumentoFielCsd = function(documento) {
												  bootbox.confirm({
													  title : "Confirmar acci&oacute;n",
														message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
																agregarPrestadoraServicioService.eliminarDocumentoFielCsd(documento, function(response) {

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

																		$scope.cargaInicialDocumentosPrestadoraFielCsd();
																		var fileElement = angular.element('#fielKeyCsd');
																        angular.element(fileElement).val(null);
																	}
																},
																function(response) {
																	$log.error(response.status+ ' - '+ response.statusText);
																	pinesNotifications.notify({
																        title: 'Error',
																        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
																        type: 'error'
																      });
																});
															}
														}				  
												  });
											}
										$scope.showDocumentosCerKeyRepresentante = function(archivo){
											$scope.representanteLegalDto = angular.copy(archivo);
											$scope.IsVisibleRepresentanteArchivos = true;
											$scope.IsVisibleFormularioApoderado = false;
											$scope.IsVisibleDocumentosApoderado = false;
											$scope.IsVisibleDocumentosRepresentante = false;
											$scope.IsVisibleFormularioRepresentante = false;
											$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
										}
										
										$scope.cargaInicialDocumentosCerKeyRepresentante = function (id){
										$http.post(CONFIG.APIURL + "/prestadoraServicio/obtenerDoctosPrestadoraDocumentoRepresentanteCerKey.json", id).then(
										         function(data) {
										   $scope.documentosPrestadoraRepCerKey = data.data;
											   }, function(data) {
										   console.log("error --> " + data);
										});
										}
										
										$scope.mostrarModalDocumentoRepresentanteCerKey = function(itemDefinicionDocumento) {
											$scope.tipoDoc = angular.lowercase(itemDefinicionDocumento.definicion.nombreDocumento);
											$scope.archivoPrestadora = {};
											itemDefinicionDocumento.idPrestadoraServicioRepresentanteLegal = $scope.representanteLegalDto.idGenericoRepresentanteLegal;
												$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
												$('#agregarDocumentoRepresentanteCerKey').modal('show');
											 };
											 
											
									$scope.guardarDocumentoRepresentanteCerKey = function(){
												  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
														$scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
																						    		  
													  pinesNotifications.notify({
													  title: 'Error',
													  text: 'Es necesario adjuntar el documento',
													  type: 'error'
													  });
													 return;
													 }
																						    	  
										$http.post(CONFIG.APIURL + "/prestadoraServicio/representanteLegal/guardarDocumentosPrestadoraRepresentanteCerKey.json", $scope.itemDefinicionDocumento).then(
										  function (response) {
										    $log.debug('ok');
											pinesNotifications.notify({
											title: 'Mensaje',
											text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
											type: 'success'
											});
																												
											$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
											var fileElement = angular.element('#fileRepresentanteCerKey');
											angular.element(fileElement).val(null);
											$('#agregarDocumentoRepresentanteCerKey').modal('hide');
											},
												function (data) {
													 $log.error(data.status+ ' - '+ data.statusText);
														pinesNotifications.notify({
														title: 'Error',
														text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
														type: 'error'
														});
										});
									}
									
									
									$scope.eliminarDocumentoRepresentanteCerKey = function(documento) {
										  bootbox.confirm({
											  title : "Confirmar acci&oacute;n",
												message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
														agregarPrestadoraServicioService.eliminarDocumentoRepresentanteCerKey(documento, function(response) {

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

																$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
																var fileElement = angular.element('#fileRepresentanteCerKey');
														        angular.element(fileElement).val(null);
															}
														},
														function(response) {
															$log.error(response.status+ ' - '+ response.statusText);
															pinesNotifications.notify({
														        title: 'Error',
														        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
														        type: 'error'
														      });
														});
													}
												}				  
										  });
									}
									
									$scope.guardarRepresentanteLegalContrasenia = function() {
								    	agregarPrestadoraServicioService.guardarRepresentanteLegal($scope.representanteLegalDto,function(response) {
											  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
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
													agregarPrestadoraServicioService.cargaInicialRepresentanteLegal(function(response) {
														  $scope.gridRepresentantesLegales = response.data.gridRepresentantesLegales;
														  $scope.IsVisibleFormularioRepresentante = false;
														  $scope.IsVisibleDocumentosRepresentante = false;
													  },function(response) {
															$log.error("error --> " + response);
															pinesNotifications.notify({
														        title: 'Error',
														        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
														        type: 'error'
														      });

														});
											  }
										  },function(response) {
												$log.error("error --> " + response);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
											        type: 'error'
											      });
											});
										}
											 
				
						 
					      	$scope.descargarDocumentoByIdCMS = function(idCMS){
						    	  
								
				      			$http.get(CONFIG.APIURL + "/documento/documentoByIdCMS/"+ idCMS +".json").then(function (response) {
				      				
				      				var link = document.createElement("a");
				      				   link.href =  response.data.mimeType + response.data.documentoBase64;
			                      	   link.style = "visibility:hidden";
			                      	   link.download = response.data.archivo;
			                      	   document.body.appendChild(link);
			                      	   link.click();
			                      	   document.body.removeChild(link);
										
				                  },
				                  function (data) {
				                	  $log.error(data.status+ ' - '+ data.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
									        type: 'error'
									      });
				                  });

					      	}
					      	
					      	
					      	$scope.eliminarDocumentoPrestadoraServicio = function(documento) {
								  bootbox.confirm({
									  title : "Confirmar acci&oacute;n",
										message : "¿Est\u00e1s seguro de eliminar el documento ?",
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
												agregarPrestadoraServicioService.eliminarDocumentoPrestadoraServicio(documento, function(response) {

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

														$scope.cargaInicialDocumentosPrestadora();
//														var fileElement = angular.element('#fielKeyCsd');
//												        angular.element(fileElement).val(null);
													}
												},
												function(response) {
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
												        type: 'error'
												      });
												});
											}
										}				  
								  });
							}
					      	
					      	 $scope.guardarCuentaOrdenante = function(cuenta){
					      		bootbox.confirm({
									  title : "Confirmar acci&oacute;n",
										message : "¿Est\u00e1s seguro que deseas guardar los datos de STP?",
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
												
												agregarPrestadoraServicioService.guardarDatosStp(cuenta, function(response) {
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
													}
												},
												function(response) {
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al guardar los datos Stp, favor de volver a intentar.',
												        type: 'error'
												      });
												});
											}
										}				  
								  });
						      }
					      	 
					      	  $scope.activarDispersor = function(activar) {
					          	
					          		edicionDispersor.activo=!activar;
					          		
					          	
					          };
					      	
				      			
  });