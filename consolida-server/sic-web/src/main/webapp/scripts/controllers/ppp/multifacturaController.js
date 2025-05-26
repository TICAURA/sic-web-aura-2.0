'use strict';
angular
		.module('theme.core.templates')
		.controller(
				'multifacturaController',
				function($route, $window, $scope, $rootScope, $templateCache,
						$location, $timeout, $http, CONFIG, $bootbox, $log,
						multifacturaService, nominaService, pinesNotifications,
						NgTableParams) {
					
					$scope.clienteSeleccionado ={};
					$scope.facturaSeleccionada ={};

					$scope.idNominaCliente = null;
					$scope.nominaClienteDto = {};
					//crear factura
					$scope.isBotonDetalleFactura=false;
					$scope.habilitaPrecioUnitario=false;
					$scope.habilitaCampos = false;					
					$scope.isVisibleSeccionFactura = false;
					$scope.isVisibleNominasFactura =false;
					$scope.factura = {};
					$scope.generarFacturaPaso5={};
					$scope.generarFacturaPaso5.documentoNuevo = {};
					 $scope.pacTimbrado={};
					$scope.concepto = {};
					$scope.conceptoDigitado = {};
					$scope.totales = {};
					$scope.conceptos = [];
					$scope.conceptosPlus = [];
					$scope.montosFactura = {};
					$scope.formulaFactura = {};
					$scope.catMoneda = {};
					$scope.catMoneda.idCatGeneral = 2804;
					$scope.catMoneda.clave = 'MXN';
					$scope.catTipoComprobante = {};
					$scope.catTipoComprobante.idCatGeneral = 329;
					$scope.catTipoComprobante.clave = 'I';
					$scope.catRegimenFiscal = {};
					$scope.catRegimenFiscal.idCatGeneral = 334;
					$scope.catRegimenFiscal.clave = '601';
					$scope.catUsoCdfi = {};
					$scope.catUsoCdfi.idCatGeneral = 309;
					$scope.catUsoCdfi.clave = 'G03';
					$scope.catMetodoPago = {};
					$scope.catMetodoPago.idCatGeneral = 294;
					$scope.catMetodoPago.clave = 'PUE';
					
					$scope.catFormaPago = {};
					$scope.catFormaPago.idCatGeneral = 361;
					$scope.catFormaPago.clave = '03';
					
					$scope.unidadDefault = {}
					$scope.unidadDefault.idCatGeneral = 1058;
					$scope.unidadDefault.clave = 'E48';
					$scope.codigoSatDefault = 84131801;
					$scope.descripcionSatDefault = "Fondos de pensiones autodirigidos o patrocinados por el empleador";
					$scope.descripcionConceptoDefault = "Prestación de servicios de administración de fondo privado de pensiones con el Plan ID CONSAR"
					$scope.isfacturas = false;
					$scope.isFacturacionNomina=false;
					$scope.isVincularFacturasNominas=false;
					$scope.habilitaPaso4Factura = false;
					$scope.habilitaCatMetodoPago= true;
					$scope.mostrarAccionesPaso4=false;
					$scope.habilitaTotalNominas=false;
					 $scope.nominasAfacturar =[];
					 $scope.total={};
					 $scope.total.totalNomina=0;
					 $scope.total.montoTotal=0;
					 $scope.totalMontoFactura=0;
					 $scope.idConsar=null;
					 $scope.botonGenFactura=false;
					 $scope.isVisibleSeleccionNomina=false;
					 $scope.botonVincularFacturaNominas=false;
					 $scope.botonAgregarConcepto=false;
					 $scope.catNominasCliente=false;
					 $scope.dataExcel = {};
					 $scope.dataExcel.contentRows= {};
						$scope.gridNominasProceso=null;
						$scope.gridFacturaDisponible=null;
						$scope.gridFacturaGeneradas=null;
				
					  $scope.isVisibleClientes=false;
							
					$scope.cargaInicialMultifactura = function() {
						$scope.gridNominasProceso=null;
						$scope.gridFacturaDisponible=null;
						$scope.gridFacturaGeneradas=null;
				
						// Carga inicial de los clientes asignados a nominista
						multifacturaService.cargaInicialMultifactura(function(response) {
											//$scope.gridNominaCliente = response.data.gridNominaCliente;
											 $scope.rol = response.data.rol;
											 $scope.comboPac = response.data.comboPac;
											//$scope.tableParams = new NgTableParams({page : 1,count : 25	},{data : $scope.gridNominaCliente});
											  $scope.catCelula = response.data.catCelula;
											  $scope.facturaDTO =response.data.facturaDTO;
											  $scope.isVisibleClientes=false;
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error, favor de intentarlo mas tarde.',
														type : 'error'
													});
										});
						
			

						// Carga Inicial de catalogos que utiliza la pagina
						nominaService.cargaInicialNominaCatalogos(
										function(response) {
											$scope.comboTipoComprobante = response.data.comboTipoComprobante;
											$scope.comboMetodoPago = response.data.comboMetodoPago;
											$scope.comboFormaPago = response.data.comboFormaPago;
											$scope.comboMoneda = response.data.comboMoneda;
											$scope.comboRegimenFiscal = response.data.comboRegimenFiscal;
											$scope.comboSerie = response.data.comboSerie;
											$scope.comboUnidadFactura = response.data.comboUnidadFactura;
											$scope.comboUsoCFDI = response.data.comboUsoCFDI;
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error, favor de intentarlo mas tarde.',
														type : 'error'
													});
										});
						
						
					};
					
					$scope.muestraDatos = function() {
					      alert($scope.facturaDTO.celula.idCelula);
					    }
					$scope.cargaInicialClientes = function() {
						// Carga inicial de los clientes asignados a nominista
						  $http.post(CONFIG.APIURL + "/ppp/multifactura/cargaInicialMultifacturaClientes.json", $scope.facturaDTO.celula.idCelula).then(function(response) {			      			  
				    			
						//multifacturaService.cargaInicialMultifacturaClientes(function(response) {
											$scope.gridNominaCliente = response.data.gridNominaCliente;
											// $scope.rol = response.data.rol;
											// $scope.comboPac = response.data.comboPac;
											$scope.tableParams = new NgTableParams({page : 1,count : 25	},{data : $scope.gridNominaCliente});
											//  $scope.catCelula = response.data.catCelula;
											  $scope.isVisibleClientes=true;
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error, favor de intentarlo mas tarde.',
														type : 'error'
													});
										});
					};

					$scope.cargaInicialMultifactura();

					
				/*	 $scope.detalleNomina = function (nominaCliente) {
				    	  if(nominaCliente!=undefined && nominaCliente!=null){
				    		  $http.post(CONFIG.APIURL + "/ppp/nominas/detalleNominasClienteByIdCliente.json", nominaCliente.clienteDto.idCliente).then(function(response) {
				    			      			  
				    			  
				    			  $scope.nombreCliente =  nominaCliente.clienteDto.nombreComercial +" / "+ nominaCliente.clienteDto.nombreCompleto + nominaCliente.clienteDto.razonSocial;

				    			  $scope.gridDetalleNominaCliente = response.data;
				    			  $('#modalDetalleNominas').modal('show');
								}, function(data) {
									console.log("error modalDetalleNomina--> " + data);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar al mostrar el detalle de la nomina, favor de intentarlo nuevamente.',
								        type: 'error'
								      });
								}); 
				    	  }
				      }*/

					  
		  			//1.1 se genera la factura sin nominas
					$scope.crearFactura = function(nominaCliente){
						$scope.nominaClienteDto=nominaCliente;
						$scope.gridNominasProceso=null;
						$scope.gridFacturaDisponible=null;
						$scope.gridFacturaGeneradas=null;
						  $scope.factura = {};
						   $scope.concepto = {};
						   $scope.conceptoDigitado = {};
			               $scope.conceptos = []; 
			           	    $scope.totales = {};
			               $scope.nominasAfacturar=[];
							$scope.total.totalNomina=0;
							$scope.total.montoTotal=0;
							$scope.isVisibleSeccionesNomina = false;		
							$scope.habilitaCampos=false;
						    $scope.catNominasCliente=false;
						    $scope.conceptosPlus = [];
							$scope.montosFactura = {};
							$scope.formulaFactura = {};
							$scope.habilitaCampos=false;
							$scope.botonGenFactura=false;
							$scope.botonVincularFacturaNominas=false;
							$scope.generarFacturaPaso5={};
						
						 if(nominaCliente!=undefined && nominaCliente!=null){
				    		  $http.post(CONFIG.APIURL + "/ppp/multifactura/detalleNominasClienteByIdClient.json", nominaCliente.clienteDto.idCliente).then(function(response) {			      			  
				    			  $scope.catNominaCliente = response.data;
				    			  $scope.idPrestadoraServicio=response.data[0].prestadoraServicio.idPrestadoraServicio;
				    			  $scope.consar=response.data[0].prestadoraServicio.idConsar;
				    			  $scope.isVisibleSeccionFactura = true;
									$scope.habilitaPrecioUnitario = true;
									$scope.isBotonDetalleFactura = true;
				    			
								}, function(data) {
									console.log("error detalle nominas por cliente--> " + data);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar al mostrar el detalle de la nomina, favor de intentarlo nuevamente.',
								        type: 'error'
								      });
								}); 
				    	  }
					
						
		
						}
	
						
		
						 
					
					//1.2 detalle genera factura
					$scope.crearFacturaDetalle=function (nominaClienteDto){	
					$scope.isBotonDetalleFactura=false;
					$scope.habilitaPrecioUnitario=true;
					$scope.isfacturas = true;
					 $scope.mostrarAccionesPaso4=true;
					  $scope.botonAgregarConcepto=false;
					$scope.getFactura(nominaClienteDto);
					
					}
					
					 //de varias nominas se genera una factura
					$scope.generarNominasFactura=function(){
						$scope.nominasAfacturar;
						$scope.nominaClienteDto;
						$scope.isfacturas = false;
						$scope.isFacturacionNomina=true;
						$scope.isVincularFacturasNominas=false;
						$scope.mostrarAccionesPaso4 = true;
						$scope.botonAgregarConcepto=false;
						$scope.getFactura($scope.nominaClienteDto);
						
					}
					
					// boton Vincular Factura con Nominas
					$scope.vincularNominasFactura=function(){					
                         //$scope.nominasAfacturar;
						//$scope.nominaClienteDto;
					//	$scope.facturaSeleccionada.idNominaCliente=$scope.nominaClienteDto.clienteDto.idNominaCliente;
						$scope.facturaSeleccionada.idNominaCliente=$scope.nominaClienteDto.idNominaCliente;
						$scope.facturaSeleccionada.listNominasVincular=$scope.nominasAfacturar;
						$scope.isfacturas = false;
						$scope.isFacturacionNomina=false;

				          var facturaDto = {};
							facturaDto = angular.copy($scope.facturaSeleccionada);
							//facturaDto.listNominasVincular=angular.copy($scope.nominasAfacturar);
					
							$http.post(CONFIG.APIURL+ "/ppp/nominas/vinculaFacturaNomina.json",facturaDto).then(
											function(response) {
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
												
												$scope.vincularFacturasNomina($scope.nominaClienteDto);
											//	$scope.vincularFacturas($scope.facturaSeleccionada)
											
						
						},
						function(response) {
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al guardar las nominas a la factura, favor de intentarlo más tarde.',
						        type: 'error'
						      });
						});
											
				
					}
					
					
			
					
					//
					$scope.vincularFacturas=function (facturas){
						$scope.facturaSeleccionada ={};
						$scope.facturaSeleccionada =facturas;
						$scope.isVisibleNominasFactura =false;
						$scope.habilitaPrecioUnitario=false;
						$scope.isVincularFacturasNominas=true;
						$scope.total.totalNomina=0;
						$scope.total.montoTotal=0;
						$scope.isfacturas = false;
						$scope.botonGenFactura=false;
					//	$scope.botonVincularFacturaNominas=true;
						$scope.isVisibleSeleccionNomina=true;
					//	$scope.nominaClienteDto.idNominaCliente  =facturas.idNominaCliente;
						$scope.listaNominasPorFacturar($scope.nominaClienteDto);
					//	$scope.getFactura(nominaCliente);
					
					}
					
					$scope.detalleClienteNomina = function(nominaCliente) {
						   $scope.factura = {};
						   $scope.concepto = {};
			               $scope.conceptos = []; ;
			               $scope.nominasAfacturar=[];
			               $scope.gridNominasProceso=null;
			               $scope.gridFacturaDisponible=null;
			               $scope.catNominaCliente=null;
							$scope.total.totalNomina=0;
							$scope.total.montoTotal=0;
							$scope.habilitaTotalNominas=false;
							$scope.isVisibleSeccionesNomina = false;
							$scope.habilitaPrecioUnitario=false;
							$scope.habilitaCampos=false;
							$scope.isVisibleSeccionFactura = false;
							$scope.generarFacturaPaso5={};
						 if(nominaCliente!=undefined && nominaCliente!=null){
				    		  $http.post(CONFIG.APIURL + "/ppp/multifactura/detalleNominasClienteByIdClient.json", nominaCliente.clienteDto.idCliente).then(function(response) {			      			  
				    			  $scope.catNominaCliente = response.data;
				    			  $scope.catNominasCliente=true;
								}, function(data) {
									console.log("error detalle nominas por cliente--> " + data);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar al mostrar el detalle de la nomina, favor de intentarlo nuevamente.',
								        type: 'error'
								      });
								}); 
				    	  }
					
					}
				
					/*2 .- varias Nominas a facturar*/
					$scope.listaNominasPorFacturar = function(nominaCliente) {	
						$scope.isVisibleNominasFactura = true;					
						$scope.isVisibleSeleccionNomina= true;	
						$scope.titulo = "Nóminas a facturar";
						
					//	$scope.botonGenFactura=true;
						//$scope.limpiarFactura();
						if($scope.botonVincularFacturaNominas==true){
							$scope.botonGenFactura=false;
						}else {
							$scope.botonGenFactura=true;
							$scope.gridFacturaDisponible=null;
						}
						
						$scope.clienteSeleccionado = nominaCliente.clienteDto;
						$scope.nominaClienteDto=nominaCliente;
					//nominaCliente	$scope.idNominaCliente =nominaCliente.clienteDto.idNominaCliente;
						multifacturaService.listaNominasFacturar(nominaCliente, function(response) {
											if (response.data.gridNominasProceso != undefined
													&& response.data.gridNominasProceso != null) {
												    

												if (nominaCliente.clienteDto.nombreCompleto != null) {
													$scope.descripcionNomina = nominaCliente.clienteDto.nombreCompleto;
												} else if (nominaCliente.clienteDto.razonSocial != null) {
													$scope.descripcionNomina = nominaCliente.clienteDto.razonSocial;
												}

												$scope.gridNominasProceso = response.data.gridNominasProceso;

												if ($scope.gridNominasProceso.length >= 1) {
													$scope.rfcCliente = nominaCliente.clienteDto.rfc;
													 $scope.habilitaTotalNominas=true;
													pinesNotifications
															.notify({
																title : 'Nomina completa',
																text : 'Se cargarón las nominas a facturar exitosamente ',
																type : 'success'
															});
												} else {

													pinesNotifications
															.notify({
																title : 'Error',
																text : 'No existen nominas para facturar',
																type : 'error'
															});
												}

											} else {
												$log.error(response.status
														+ ' - '
														+ response.statusText);
											}
										},
										function(response) {
											$log.error(response.status + ' - '
													+ response.statusText);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
														type : 'error'
													});

										});

					}
					
					 $scope.guardarNominaAFacturar = function (nomina){
					
						 if(nomina.aFacturar==true){
							 $scope.total.totalNomina+=1;
							 $scope.total.montoTotal+=nomina.nominaClienteDto.montoTotalColaboradores;
							if( $scope.isVincularFacturasNominas){
								//$scope.montoTotalNomina=$scope.factura.totales.saldoDisponible;
								$scope.montoTotalNomina=$scope.facturaSeleccionada.totales.saldoDisponible;
								$scope.total.montoTotal=	$scope.total.montoTotal-1
							 if($scope.montoTotalNomina>= ($scope.total.montoTotal) ){
								 $scope.nominasAfacturar.push(angular.copy(nomina));
							 }else{
								 $scope.botonVincularFacturaNominas=false;
									pinesNotifications
									.notify({
										title : 'Error',
										text : 'El monto de la Factura rebasa el total del monto de las Nóminas',
										type : 'error'
									});
									 $scope.total.montoTotal-=nomina.nominaClienteDto.montoTotalColaboradores;
									 $scope.total.totalNomina-=1;
								 
							 }
							 }else  {
								 $scope.nominasAfacturar.push(angular.copy(nomina));
								 }
						
						 }else if (nomina.aFacturar==0) {
							 $scope.total.montoTotal-=nomina.nominaClienteDto.montoTotalColaboradores;
							 $scope.total.totalNomina-=1;
							 $scope.nominasAfacturar.splice(angular.copy(nomina));
						 } 
					 }
					 
					 
					
					$scope.vincularFacturasNomina = function(nominaCliente){
						$scope.gridFacturaDisponible=null;
						$scope.gridFacturaGeneradas=null;
						$scope.nominasAfacturar=[];
						$scope.gridNominasProceso=null;
						$scope.botonVincularFacturaNominas=true;
						$scope.botonGenFactura=false;
						$scope.isVisibleSeccionFactura=false;
						$scope.isVisibleSeccionesNomina = false;
						$scope.isVisibleNominasFactura =false;
						$scope.habilitaPrecioUnitario=false;
						$scope.habilitaCampos=false;
						$scope.habilitaTotalNominas=false;
						$scope.isVincularFacturasNominas=true;
						$scope.clienteSeleccionado = nominaCliente.clienteDto;
						$scope.nominaClienteDto=nominaCliente;
						$scope.nominaClienteDto.idNominaCliente=nominaCliente.clienteDto.idNominaCliente;
						$scope.idNominaCliente =nominaCliente.clienteDto.idNominaCliente;
						$scope.catNominasCliente=false;
						multifacturaService.listaFacturasDisponibles(nominaCliente, function(response) {
											if (response.data.gridFacturaDisponible != undefined
													&& response.data.gridFacturaDisponible != null) {
                               		$scope.gridFacturaDisponible = response.data.gridFacturaDisponible;

												if ($scope.gridFacturaDisponible.length >= 1) {
													pinesNotifications
															.notify({
																title : 'Facturas disponibles',
																text : 'Se cargarón las facturas exitosamente ',
																type : 'success'
															});
												
												
												} else {

													pinesNotifications
															.notify({
																title : 'Error',
																text : 'No existen facturas ',
																type : 'error'
															});
												}

											} else {
												$log.error(response.status
														+ ' - '
														+ response.statusText);
											}
										},
										function(response) {
											$log.error(response.status + ' - '
													+ response.statusText);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error al cargar las facturas, favor de intentarlo más tarde.',
														type : 'error'
													});

										});
					}
					
					$scope.facturasGeneradas = function(nominaCliente){
						$scope.gridFacturaDisponible=null;
						$scope.gridFacturaGeneradas=null;
						$scope.nominasAfacturar=[];
						$scope.gridNominasProceso=null;
						$scope.botonVincularFacturaNominas=true;
						$scope.botonGenFactura=false;
						$scope.isVisibleSeccionFactura=false;
						$scope.isVisibleSeccionesNomina = false;
						$scope.isVisibleNominasFactura =false;
						$scope.habilitaPrecioUnitario=false;
						$scope.habilitaCampos=false;
						$scope.habilitaTotalNominas=false;
						$scope.isVincularFacturasNominas=true;
						$scope.clienteSeleccionado = nominaCliente.clienteDto;
						$scope.nominaClienteDto=nominaCliente;
						$scope.idNominaCliente =nominaCliente.clienteDto.idNominaCliente;
						$scope.catNominasCliente=false;
						multifacturaService.listaFacturasGeneradas(nominaCliente.clienteDto.idCliente,function(response) {
											if (response.data.gridFacturaGeneradas != undefined
													&& response.data.gridFacturaGeneradas != null) {
                               		$scope.gridFacturaGeneradas = response.data.gridFacturaGeneradas;

												if ($scope.gridFacturaGeneradas.length >= 1) {
													pinesNotifications
															.notify({
																title : 'Facturas Generadas',
																text : 'Se cargarón las facturas exitosamente ',
																type : 'success'
															});
												
												
												} else {

													pinesNotifications
															.notify({
																title : 'Error',
																text : 'No existen facturas ',
																type : 'error'
															});
												}

											} else {
												$log.error(response.status
														+ ' - '
														+ response.statusText);
											}
										},
										function(response) {
											$log.error(response.status + ' - '
													+ response.statusText);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error al cargar las facturas, favor de intentarlo más tarde.',
														type : 'error'
													});

										});
					}
					
				    $scope.modalNominas = function (factura) {
	    		    	
				    	$http.post(CONFIG.APIURL + "/ppp/multifactura/cargaNominasVinculadas.json", factura.idFactura).then(function(response) {
							  
				  	  		 $scope.dataExcel.contentRows= response.data;
				  	  	
				  	  		$('#modalNominas').modal('show');
				  	  		 	  	  		
							}, function(data) {
								console.log("error modalNominaVinculadasFactura--> " + data);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al cargar las nominas vinculadas a la factura, favor de intentarlo nuevamente.',
							        type: 'error'
							      });
							});

				      }
					
					$scope.descargarFactura=function(factura) {
						alert("entra a descargar factura");
						
					}
					
					
				
						/*multi obtiene valorrs por default**********************/
					$scope.getFactura = function(nominaCliente) {
					//	$scope.idNominaCliente = nominaCliente.idNominaCliente;
					//	$scope.nominaClienteDto = angular.copy(nominaCliente);
						$scope.idCliente = nominaCliente.clienteDto.idCliente;
						$scope.botonAgregarConcepto=false;
						$scope.isVisibleSeccionFactura = true;
						$scope.factura = {};
						$scope.factura.conceptos = null;
						$scope.factura.conceptosPlus = null;
						$scope.habilitaCampos = true;	
					/*	if(nominaCliente.prestadoraServicio!=null){
							$scope.idConsar =nominaCliente.prestadoraServicio.idConsar;
							$scope.factura.idPrestadoraServicio=nominaCliente.prestadoraServicio.idPrestadoraServicio;
							}else{
								$scope.idConsar=" ";
							}*/

							// valores por defaul ya que son PPP
							$scope.factura.regimenFiscal = $scope.catRegimenFiscal;
							$scope.factura.usoCFDI = $scope.catUsoCdfi;							
							$scope.factura.moneda = $scope.catMoneda;
							$scope.factura.tipoComprobante = $scope.catTipoComprobante;

							$scope.isVisibleSeccionCargaDocComplementoPago = false;
							$scope.isVisibleSeccionDescargaDocComplementoPago = false;
							$scope.totalesFlujoAlterno = {};
							
							$scope.formulaFactura = {};
							$scope.totales = {};
							$scope.calculaConceptoYFactura();
						/*	nominaService.getFormulaFactura($scope.idNominaCliente,
											function(response) {
												$scope.formulaFactura = response.data;
												$scope.totales = {};
												$scope.calculaConceptoYFactura();

											},
											function(response) {
												$log.error(response.status
														+ ' - '
														+ response.statusText);
												pinesNotifications
														.notify({
															title : 'Error',
															text : 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
															type : 'error'
														});
											});
						
            */
				

					}

					$scope.calculaConceptoYFactura = function() {
						$scope.concepto.cantidad = 1;
						$scope.concepto.unidad = $scope.unidadDefault;
						$scope.concepto.codigoSat = $scope.codigoSatDefault;
						$scope.concepto.descripcionSat = $scope.descripcionSatDefault;
						$scope.concepto.descripcionConcepto = $scope.descripcionConceptoDefault + " " + $scope.consar;
		               var importeUnitario= parseFloat($scope.conceptoDigitado.precioUnitario/116*100).toFixed(2);
		               
						if ($scope.isfacturas) {
						$scope.totalMontoFactura = importeUnitario;
						//	$scope.totalMontoFactura = parseFloat(($scope.conceptoDigitado.precioUnitario/100)*116).toFixed(2);
						}

						else if ($scope.isFacturacionNomina || $scope.isVincularFacturasNominas) { 
							$scope.totalMontoFactura= $scope.total.montoTotal;
						}
						//Valores por default
			
							
						$scope.concepto.precioUnitario = parseFloat(importeUnitario);
						$scope.concepto.importe = parseFloat(importeUnitario);

						$scope.concepto.ivaTrasladado16 = "SI";
						$scope.concepto.ivaTrasladado16Monto = parseFloat(parseFloat($scope.concepto.precioUnitario* parseFloat(0.16)).toFixed(2));
						//	$scope.concepto.ivaTrasladado16Monto = parseFloat($scope.conceptoDigitado.precioUnitario-$scope.totalMontoFactura.toFixed(2));
						
							// Honorario DISPERSIÓN PPP * HONORARIO PACTADO
					    /* 	$scope.montosFactura.honorario = parseFloat($scope.totalMontoFactura)* parseFloat($scope.formulaFactura.honorarioPPP)/ 100;
						   // Formula IVA
							if ($scope.formulaFactura.formulaTipoIva.clave === 'H2') { // IVA *MONTO DISPERSIÓN + HONORARIO PACTADO
								$scope.montosFactura.iva = (parseFloat($scope.totalMontoFactura) + $scope.montosFactura.honorario)
										* parseFloat((parseFloat($scope.formulaFactura.ivaComercial) / 100)
												.toFixed(2));
							} else if ($scope.formulaFactura.formulaTipoIva.clave === 'H3') {// IVA// */
							/*	$scope.montosFactura.iva = $scope.montosFactura.honorario * parseFloat((parseFloat($scope.formulaFactura.ivaComercial) / 100).toFixed(2));
							} else if ($scope.formulaFactura.formulaTipoIva.clave === 'H4') {// NO// APLICA
								$scope.montosFactura.iva = parseFloat(0);
							}

							// Formula Factura
							if ($scope.formulaFactura.formulaFactura.clave === 'H5') { // DISPERSIÓN// PPP// +// HONORARIO// PACTADO// +// IVA
								$scope.montosFactura.montoFactura = (parseFloat($scope.totalMontoFactura)
										+ $scope.montosFactura.honorario + $scope.montosFactura.iva);
							} else if ($scope.formulaFactura.formulaFactura.clave === 'H6') {// HONORARIo PPP// +// IVA
								$scope.montosFactura.montoFactura = ($scope.montosFactura.honorario + $scope.montosFactura.iva);
							}

							 if ($scope.formulaFactura.formulaTipoIva.clave === 'H4'){
								 $scope.montosFactura.subtotal = parseFloat(parseFloat($scope.montosFactura.montoFactura).toFixed(2));
									$scope.concepto.precioUnitario = parseFloat(parseFloat($scope.montosFactura.subtotal).toFixed(2));
									$scope.concepto.importe = $scope.concepto.cantidad* $scope.concepto.precioUnitario;

									$scope.concepto.ivaTrasladado16 = "NO";
									$scope.concepto.ivaTrasladado16Monto = parseFloat(0);
									 
							 }else{
							$scope.montosFactura.subtotal = parseFloat(parseFloat($scope.montosFactura.montoFactura / parseFloat(1.16)).toFixed(2));
							$scope.concepto.precioUnitario = parseFloat(parseFloat($scope.montosFactura.subtotal).toFixed(2));
							$scope.concepto.importe = $scope.concepto.cantidad* $scope.concepto.precioUnitario;

							$scope.concepto.ivaTrasladado16 = "SI";
							$scope.concepto.ivaTrasladado16Monto = parseFloat(parseFloat($scope.concepto.precioUnitario* parseFloat(0.16)).toFixed(2));
							 }*/
					}//fin funcion calculaConceptoYFactura


					
					$scope.calcularImporteM = function() {
						if (angular.isNumber($scope.conceptoDigitado.precioUnitario)) {
							$scope.conceptoDigitado.importe = $scope.conceptoDigitado.precioUnitario;

						}

					};
					
					$scope.calcularImporte = function() {
						if (angular.isNumber($scope.concepto.cantidad) && angular.isNumber($scope.concepto.precioUnitario)) {
							$scope.concepto.importe = $scope.concepto.cantidad* $scope.concepto.precioUnitario;

							$scope.cambiarIvaTrasladado16();
							$scope.cambiarIvaRetenido6();

						}

					};

					$scope.cambiarIvaTrasladado16 = function() {

						if ($scope.concepto.ivaTrasladado16) {
							$scope.concepto.ivaTrasladado16Monto = $scope.concepto.importe* parseFloat(0.16).toFixed(2);
						} else {
							$scope.concepto.ivaTrasladado16Monto = 0;
						}
					}

					$scope.cambiarIvaRetenido6 = function() {

						if ($scope.concepto.ivaRetenido6) {
							$scope.concepto.ivaRetenido6Monto = $scope.concepto.importe * parseFloat(0.06).toFixed(2);
						} else {
							$scope.concepto.ivaRetenido6Monto = 0;
						}

					}

					$scope.cancelarUpdateConcepto = function(formularioConcepto) {
						$scope.concepto = {};
						if (formularioConcepto) {
							formularioConcepto.$setPristine();
							formularioConcepto.$setUntouched();
							$scope.mostrarAccionesPaso4 = false;
						}

					}

					$scope.vistaPreviaFactura = function() {
						if ($scope.totales.total != undefined && $scope.totales.total > 0) {

							$http.post(CONFIG.APIURL + "/ppp/nominas/preFacturaMul.json", $scope.facturaSeleccionada).then(
											function(response) {
												$log.debug('ok');
												pinesNotifications
														.notify({
															title : 'Mensaje',
															text : 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
															type : 'success'
														});

												var modal = document.getElementById("pdfPrefactura");
												modal.innerHTML = '<object type="text/html" style="width: 100%; height: 500px;" data=' + response.data.mimeType + response.data.documentoBase64 + '></object>';
												$('#verDocumentoFactura').modal('show');

											},
											function(data) {
												$log.error(data.status + ' - '+ data.statusText);
												pinesNotifications
														.notify({
															title : 'Error',
															text : 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
															type : 'error'
														});
												$scope.getDatoIdFactura ($scope.factura);
												$scope.cargaInicialDocumentosPppFactura($scope.factura.idFactura);
												// $scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
												// $scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);

											});
						} else {
							pinesNotifications
									.notify({
										title : 'Error',
										text : 'Es necesario registrar un concepto de facturación',
										type : 'error'
									});
						}
					}

					$scope.agregarConcepto = function(formularioConcepto) {
						$scope.idCelula=$scope.facturaDTO.celula.idCelula;
						
						 $scope.mostrarAccionesPaso4=false;
						if (formularioConcepto.$invalid) {
							pinesNotifications.notify({
								title : 'Concepto',
								text : 'El formulario tiene un error',
								type : 'error'
							});
						} else {
							var facturaDto = {};
							facturaDto = angular.copy($scope.factura);
							facturaDto.concepto = angular.copy($scope.concepto);
						//	facturaDto.idNominaCliente = angular.copy($scope.nominaClienteDto.idNominaCliente);
							facturaDto.idCliente = angular.copy($scope.nominaClienteDto.clienteDto.idCliente);
							facturaDto.listNominasVincular= angular.copy($scope.nominasAfacturar);
							facturaDto.celula=	angular.copy($scope.facturaDTO.celula);
							facturaDto.idPrestadoraServicio= $scope.idPrestadoraServicio;
							facturaDto.consar== $scope.consar;
							$scope.botonAgregarConcepto=false;
							// facturaDto.nomina=
							// angular.copy($scope.nominaProcesoSeleccionada);

							$scope.totales = {};
							$scope.totales.sumaImpuestos = 0;

							$scope.totales.subtotal = 0;
							$scope.totales.total = 0;
							var conceptosTemporal = angular.copy($scope.conceptos);
							if (conceptosTemporal != undefined && conceptosTemporal.length >= 1 && $scope.concepto.idConcepto >= 1) {
								angular.forEach(conceptosTemporal,
												function(value, key) {
													if (value.idConcepto === $scope.concepto.idConcepto) {
														conceptosTemporal[key] = $scope.concepto;
													}
												});

							} else {
								// Se agrega ya que es el primer concepto
								conceptosTemporal.push(angular.copy($scope.concepto));
							}
							angular.forEach(conceptosTemporal,function(value, key) {
												if (value.ivaTrasladado16 === 'SI') {
							$scope.totales.sumaImpuestos = parseFloat(parseFloat(parseFloat($scope.totales.sumaImpuestos)+ parseFloat(value.ivaTrasladado16Monto)).toFixed(2));
												}
                       // 	$scope.totales.subtotal = parseFloat(parseFloat(parseFloat($scope.totales.subtotal)	+ parseFloat(value.importe)).toFixed(2));
						  $scope.totales.subtotal = parseFloat(parseFloat(parseFloat($scope.totales.subtotal)	+ parseFloat(value.importe)));
							});
							

					         $scope.totales.total = parseFloat(parseFloat(parseFloat($scope.totales.subtotal)+ parseFloat($scope.totales.sumaImpuestos)).toFixed(2));
							// Se agregan los totales para la generacion de los
							// reportes de manera correcta
							$scope.totales.ivaComercial = angular.copy($scope.formulaFactura.ivaComercial);
							$scope.totales.honorario = angular.copy($scope.formulaFactura.honorarioPPP);
							$scope.totales.montoTotalHonorario = angular.copy($scope.montosFactura.honorario);
							// $scope.totales.montoTotalColaboradoresPPP = angular.copy($scope.totalNominaPPPColaboradores);

							facturaDto.totales = angular.copy($scope.totales);

							$http.post(CONFIG.APIURL+ "/ppp/nominas/guardarConceptoMulti.json",facturaDto).then(
											function(response) {
												if (response.data != undefined && response.data != null) {
													$scope.facturaSeleccionada = response.data;
												//$scope.getDatoFactura($scope.idNominaCliente);
											//	$scope.getDatoIdFactura($scope.nominaClienteDto);
													$scope.getDatoIdFactura($scope.facturaSeleccionada);
												}
												pinesNotifications
														.notify({
															title : 'Concepto',
															text : 'Se ha cargado el concepto exitosamente',
															type : 'success'
														});

												$scope.limpiarConcepto(formularioConcepto);
												$scope.mostrarAccionesPaso4 = false;
											},
											function(data) {
												console
														.log("error guardar el concepto--> "
																+ data);
												pinesNotifications
														.notify({
															title : 'Error',
															text : 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
															type : 'error'
														});
											});

						}}
						
						$scope.agregarPlusConceptos= function(concepto){
							var facturaDto = {};
							facturaDto = angular.copy($scope.facturaSeleccionada);
							facturaDto.conceptoPlus=angular.copy(concepto);
						//if(facturaDto.totales.total<=  (concepto.total+facturaDto.totales.subtotal)){
				 		if (facturaDto.conceptoPlus != undefined && facturaDto.conceptoPlus != null) {
								
								$http.post(CONFIG.APIURL+ "/ppp/multifactura/agregarConceptoPlus.json",facturaDto).then(
										function(response) {
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
													$scope.getDatoIdFactura($scope.facturaSeleccionada);
											}
											
						
					
					},
					function(response) {
						$log.error(response.status+ ' - '+ response.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al guardar el concepto, favor de intentarlo más tarde.',
					        type: 'error'
					      });
					});
								
							}
				 	/*	}
						else{
								pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al guardar el concepto, favor de intentarlo más tarde.',
						        type: 'error'
						      });
								}*/
							
					}
						
						
					
						
						$scope.vincularFacturaNom= function(factura){
							var facturaDto = {};
							facturaDto = angular.copy(factura);
							facturaDto.listNominasVincular=angular.copy($scope.nominasAfacturar);
						
							$http.post(CONFIG.APIURL+ "/ppp/nominas/vinculaFacturaNomina.json",facturaDto).then(
											function(response) {
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
						        text: 'Ocurrio un error al guardar las nominas a la factura, favor de intentarlo más tarde.',
						        type: 'error'
						      });
						});
											
						}
						
						$scope.limpiarFactura = function() {
							$scope.gridNominasProceso=null;
							  $scope.factura = {};
			                   $scope.concepto = {};
			                   $scope.conceptoDigitado = {};
								$scope.totales = {};
								$scope.conceptos = [];
								$scope.conceptosPlus = [];
								$scope.montosFactura = {};
								$scope.formulaFactura = {};
								$scope.habilitaCampos=false;
								$scope.botonGenFactura=false;
								$scope.botonVincularFacturaNominas=false;
							
						}

						
						$scope.getDatoFactura = function(idNominaCliente) {
							$scope.factura = {};
							$scope.factura.conceptos = null;

							nominaService.getFormulaFactura(idNominaCliente,function(response) {
												$scope.formulaFactura = response.data;
									multifacturaService.getFacturacionByNominaCliente(idNominaCliente,
													function(response) {

																	$scope.mostrarAccionesPaso4 = true;
																	$scope.habilitaGuardar=true;

																	if (response.data != undefined
																			&& response.data != null
																			&& response.data != ""
																			&& response.data != undefined) {

																		$scope.factura = response.data;
																		$scope.conceptos = response.data.conceptos;
																		$scope.totales = response.data.totales;
																		$scope.montoComprobantePagoPaso4 = $scope.factura.montoComprobantePago;

															
																		if ($scope.factura.conceptos.length >= 1) {
																			$scope.mostrarAccionesPaso4 = false;
																		}

																		if (($scope.factura.documentoNuevo != undefined && $scope.factura.documentoNuevo != null)
																				&& ($scope.factura.documentoNuevo.nombreArchivo != undefined && $scope.factura.documentoNuevo.nombreArchivo != null)) {

																			$scope.generarFacturaPaso5.documentoNuevo = $scope.factura.documentoNuevo;
																		}

																		$scope.calculaConceptoYFactura();

																		// recupera datos para el paso 5(fluj cuando SI requierenfactura)
																		if (($scope.factura.metodoPago != undefined
																				&& $scope.factura.metodoPago != null && $scope.factura.metodoPago.idCatGeneral >= 1)
																				&& ($scope.factura.formaPago != undefined
																						&& $scope.factura.formaPago != null && $scope.factura.formaPago.idCatGeneral >= 1)) {

																			$scope.generarFacturaPaso5.metodoPago = $scope.factura.metodoPago;
																			$scope.generarFacturaPaso5.formaPago = $scope.factura.formaPago;

																			if ($scope.factura.formaPago != undefined
																					&& $scope.factura.formaPago != null) {
																				$scope.generarFacturaPaso5.montoComprobantePago = $scope.factura.montoComprobantePago;
																			}

																			$scope.nombreArchivo = $scope.factura.documentoNuevo.nombreArchivo;
																			$scope.isVisibleSeccionCargaDocComplementoPago = false;
																			$scope.isVisibleSeccionDescargaDocComplementoPago = true;

																		} else {
																			$scope.isVisibleSeccionCargaDocComplementoPago = true;
																			$scope.isVisibleSeccionDescargaDocComplementoPago = false;
																			$scope.generarFacturaPaso5.formaPago = $scope.catFormaPago;

																		
																		}

													
																		  $scope.validaCamposPaso4_5(); 
																		 
																	} else {

																		$log.error(response.status+ ' - '	+ response.statusText);
																		// valores  por defaul ya que son PPP
																		$scope.factura.regimenFiscal = $scope.catRegimenFiscal;
																		$scope.factura.usoCFDI = $scope.catUsoCdfi;
																		$scope.factura.moneda = $scope.catMoneda;
																		$scope.factura.tipoComprobante = $scope.catTipoComprobante;

																		$scope.isVisibleSeccionCargaDocComplementoPago = false;
																		$scope.isVisibleSeccionDescargaDocComplementoPago = false;
																		$scope.totalesFlujoAlterno = {};

																		$scope.totales = {};

																		$scope.calculaConceptoYFactura();
																	}

																	// $scope.calcularTotales();
																},
																function(
																		response) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
																				type : 'error'
																			});

																});

											},
											function(response) {
												$log.error(response.status
														+ ' - '
														+ response.statusText);
												pinesNotifications
														.notify({
															title : 'Error',
															text : 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
															type : 'error'
														});
											});

						

					}

					$scope.cargaInicialDocumentosPppFactura = function(idPppFactura) {
						
				//	 $http.post(CONFIG.APIURL + "/ppp/nominas/obtenerDocumentosPppNomina.json", idPppFactura).then(
					$http.post(CONFIG.APIURL+ "/ppp/nominas/obtenerDocumentosPppFactura.json",idPppFactura).then(
										function(data) {
									$scope.documentosPppNomina = data.data;
								}, function(data) {
									console.log("error --> " + data);
								});
					}

					$scope.limpiarConcepto = function(formularioConcepto) {
						$scope.concepto = {};
						if (formularioConcepto) {
							formularioConcepto.$setPristine();
							formularioConcepto.$setUntouched();
						}

					}
					
				    $scope.fileChangedDocComplementoPagoPaso5 = function (documento) {
						  
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
				              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
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
				                  
				                  $scope.generarFacturaPaso5.documentoNuevo = documento;
				              }
				              
				              reader.readAsDataURL(lstArchivos[0]);
				              
				          }

				      };
				      
					  

				      $scope.guardarPasoCinco = function (generarFacturaForm){
							 
							 var generarFacturaDto = angular.copy($scope.factura);
									      // Se agregan los totales para la generacion de los reportes de manera correcta
				          //   $scope.totales.ivaComercial = angular.copy($scope.formulaFactura.ivaComercial);
				        //      $scope.totales.honorario = angular.copy($scope.formulaFactura.honorarioPPP);
				          //   $scope.totales.montoTotalHonorario = angular.copy($scope.montosFactura.honorario);
				  		//	  $scope.totales.montoTotalColaboradoresPPP = angular.copy($scope.totalNominaPPPColaboradores);
							
				           //   generarFacturaDto.totales = angular.copy($scope.totales);
									 
									 if(generarFacturaForm.$invalid){
							    		  
							        	  pinesNotifications.notify({
							    			        title: 'Generar factura',
							    			        text: 'El formulario tiene un error, favor de ingresar y/o seleccionar los campos solicitados',
							    			        type: 'error'
							    			      });
							        	  
							    	  }else{
							    		  generarFacturaDto.formaPago = $scope.generarFacturaPaso5.formaPago;
								    	generarFacturaDto.metodoPago = $scope.generarFacturaPaso5.metodoPago;
							    		  
										 if(((generarFacturaDto.formaPago !=null && generarFacturaDto.formaPago.idCatGeneral >=1) 
												 && (generarFacturaDto.metodoPago !=null && generarFacturaDto.metodoPago.idCatGeneral >=1))){
												// && ($scope.factura.idCmsPdfFactura >= 1 && $scope.factura.idCmsXmlFactura >= 1))){
											 
											 if($scope.generarFacturaPaso5.montoComprobantePago != null && $scope.generarFacturaPaso5.montoComprobantePago != undefined){
													   generarFacturaDto.montoComprobantePago = $scope.generarFacturaPaso5.montoComprobantePago;
													    		 }
																 
											if($scope.generarFacturaPaso5.documentoNuevo != null && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo != undefined && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo !=null){
													    			 generarFacturaDto.documentoNuevo = $scope.generarFacturaPaso5.documentoNuevo;
													    		 }

					    	  		
													    		/* if(generarFacturaDto.montoComprobantePago < $scope.totales.total){
													    			 
													    			 pinesNotifications.notify({
													    			        title: 'Error',
													    			        text: 'El "monto del comprobante de pago" no puede ser menor al "monto total de la factura". Favor de revisar los montos',
													    			        type: 'error'
													    			      });
													    			 
													    		 }else{*/
												      $http.post(CONFIG.APIURL + "/ppp/nominas/guardarConceptoMulti.json", generarFacturaDto).then(function(response) {
					

														            	  pinesNotifications.notify({
														        			        title: 'Concepto',
														        			        text: 'Se ha cargado el concepto exitosamente',
														        			        type: 'success'
														        			      });
														            	  
														            	  $scope.generarFacturaPaso5.documentoNuevo = null;
														            	  $scope.limpiarFormularioPaso5(generarFacturaForm);
														            	  $scope.getDatoIdFactura ($scope.factura);
																			$scope.cargaInicialDocumentosPppFactura($scope.factura.idFactura);
														            	  
														    			}, function(data) {
														    				console.log("error al crear la factura--> " + data);
														    				pinesNotifications.notify({
														    			        title: 'Error',
														    			        text: 'Ocurrio un error al cargar los datos para la creacion de la factura, favor de intentarlo nuevamente.',
														    			        type: 'error'
														    			      });
														    			});
													    		 } 
															}
														}
								 
								    $scope.limpiarFormularioPaso5 = function(generarFacturaForm){
							    	  if(generarFacturaForm){
							    		  generarFacturaForm.$setPristine();
							    		  generarFacturaForm.$setUntouched();
							        	  
							    	  }
							    	  
							      }
								    
							 		$scope.confirmarGeneracionFactura = function() {
										  bootbox.confirm({
											  title : "Confirmar acci&oacute;n",
												message : '<div class="text-center">Una vez generada la factura, los pasos anteriores quedaran inhabilitados para su edición.<br>¿Desea continuar?</div>',
												buttons : {
													confirm : {
														label : 'ACEPTAR',
														className : 'btn-green'
													},
													cancel : {
														label : 'CANCELAR',
														className : 'btn-danger'
													}
												},
												callback : function(result) {
													if (result) {
														
							                        $scope.facturaSeleccionada;
														$('#modalPacTimbrado').modal('show');
													}
												}				  
										  });
									}
							 		
							 		
									

							 		$scope.generarFactura = function(facturaSeleccionada, pacForm) {
							 												
							 												$('#modalPacTimbrado').modal('hide');

							 												facturaSeleccionada.pacTimbrado = $scope.pacTimbrado.catPacTimbrado;
							 												
							 									$http.post(CONFIG.APIURL + "/ppp/multifactura/generarFacturaMulti.json", facturaSeleccionada).then(
							 										                  function (response) {
							 										                	  
							 										                	  if(response.data.responseCode ==="200"){
							 										                		  
							 										                		  multifacturaService.getFactura(facturaSeleccionada.idFactura, function(response) {

							 										        		    		if(response.data!=undefined && response.data !=null && response.data !="" && response.data !=undefined){
							 										        		    				
							 										        		    			$scope.factura = response.data;
							 										        		    			
							 										        		    			if($scope.factura !=null 
							 												                				  && $scope.factura.idCmsXmlFactura >= 1 
							 												                				  && $scope.factura.idCmsPdfFactura >= 1
							 												                				  && $scope.factura.idCMS >= 1 
							 			                
							 												                				  && ($scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral >= 1)
							 												                				  && ($scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral >=1)){
							 										
							 																					
							 									         $scope.getDatoIdFactura ($scope.factura);
							 												$scope.cargaInicialDocumentosPppFactura(facturaSeleccionada.idFactura);				
							 																							var fileElement = angular.element('#file_doc_comp_pago');
							 																						    angular.element(fileElement).val(null);
							 																						    $scope.limpiaCampoPac(pacForm);																
							 																	                           		  
							 															                		  
							 															                }else{
							 															                		  $log.debug('ok');
							 																						pinesNotifications.notify({
							 																					        title: 'Mensaje',
							 																					        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							 																					        type: 'success'
							 																					      });
							 																						facturaSeleccionada;
							 																						$scope.getDatoIdFactura ($scope.factura);
							 																						$scope.cargaInicialDocumentosPppFactura($scope.factura.idFactura);
							 											
							 																					  var fileElement = angular.element('#file_doc_comp_pago');
							 																				      angular.element(fileElement).val(null);
							 																				      $scope.limpiaCampoPac(pacForm);								                		  
							 															                }	 
							 										        		    		}
							 										        				},
							 										        				function(response) {
							 										        					$log.error(response.data.responseCode+ ' - '+ response.data.responseMessage);
							 										        					pinesNotifications.notify({
							 										        				        title: 'Error',
							 										        				        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
							 										        				        type: 'error'
							 										        				      });
							 										        					
							 										        					$scope.limpiaCampoPac(pacForm);
							 										        				});
							 										                	  }
							 															  else{
							 										                		  $log.error(response.data.responseCode+ ' - '+ response.data.responseMessage);
							 										                		  bootbox.alert({
							 																		title : "Mensaje de error",
							 																		message : response.data.responseMessage
							 										                		  });
							 																			
							 										                		  
							 										                	  }
							 										                	  
							 										                  },
							 										                  function (data) {
							 									                	  $log.error(data.status+ ' - '+ data.statusText);
							 															pinesNotifications.notify({
							 														        title: 'Error',
							 														        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
							 														        type: 'error'
							 														      });
							 														
							 															$scope.getDatoIdFactura ($scope.facturaSeleccionada);
							 															$scope.cargaInicialDocumentosPppFactura($scope.facturaSeleccionada.idFactura);
							 															var fileElement = angular.element('#file_doc_comp_pago');
							 													        angular.element(fileElement).val(null);
							 													        $scope.limpiaCampoPac(pacForm);
							 															
							 										                  });

							 											}
				
				
				
								
								$scope.limpiaCampoPac = function(pacForm){
									
									$scope.pacTimbrado={};
									
									if(pacForm){
										pacForm.$setPristine();
										pacForm.$setUntouched();
										
							    	}
								}
								
								//si 
								$scope.getDatoIdFactura = function(fac) {
									$scope.factura = {};
									$scope.factura.conceptos = null;
									$scope.isVisibleSeccionFactura=true;

									/*nominaService.getFormulaFactura(fac.idNominaCliente,function(response) {
											$scope.formulaFactura = response.data;*/
									
									multifacturaService.getFactura(fac.idFactura,function(response) {

																		//	$scope.mostrarAccionesPaso4 = true;
																			$scope.habilitaGuardar=true;

																			if (response.data != undefined
																					&& response.data != null
																					&& response.data != ""
																					&& response.data != undefined) {

																				$scope.factura = response.data;
																				$scope.conceptos = response.data.conceptos;
																				$scope.totales = response.data.totales;
																				$scope.montoComprobantePagoPaso4 = $scope.factura.montoComprobantePago;
																				$scope.conceptosPlus=response.data.conceptosPlus;
																				

																	
																				if ($scope.factura.conceptos.length >= 1) {
																					$scope.mostrarAccionesPaso4 = false;
																				}

																				if (($scope.factura.documentoNuevo != undefined && $scope.factura.documentoNuevo != null)
																						&& ($scope.factura.documentoNuevo.nombreArchivo != undefined && $scope.factura.documentoNuevo.nombreArchivo != null)) {

																					$scope.generarFacturaPaso5.documentoNuevo = $scope.factura.documentoNuevo;
																				}

																				$scope.calculaConceptoYFactura();

																				// recupera datos para el paso 5(fluj cuando SI requierenfactura)
																				if (($scope.factura.metodoPago != undefined
																						&& $scope.factura.metodoPago != null && $scope.factura.metodoPago.idCatGeneral >= 1)
																						&& ($scope.factura.formaPago != undefined
																								&& $scope.factura.formaPago != null && $scope.factura.formaPago.idCatGeneral >= 1)) {

																					$scope.generarFacturaPaso5.metodoPago = $scope.factura.metodoPago;
																					$scope.generarFacturaPaso5.formaPago = $scope.factura.formaPago;

																					if ($scope.factura.formaPago != undefined
																							&& $scope.factura.formaPago != null) {
																						$scope.generarFacturaPaso5.montoComprobantePago = $scope.factura.montoComprobantePago;
																					}

																					$scope.nombreArchivo = $scope.factura.documentoNuevo.nombreArchivo;
																					$scope.isVisibleSeccionCargaDocComplementoPago = false;
																					$scope.isVisibleSeccionDescargaDocComplementoPago = true;

																				} else {
																					$scope.isVisibleSeccionCargaDocComplementoPago = true;
																					$scope.isVisibleSeccionDescargaDocComplementoPago = false;
																					$scope.generarFacturaPaso5.formaPago = $scope.catFormaPago;

																				
																				}

															
																				  $scope.validaCamposPaso4_5(); 
																				 
																			} else {

																				$log.error(response.status+ ' - '	+ response.statusText);
																				// valores  por defaul ya que son PPP
																				$scope.factura.regimenFiscal = $scope.catRegimenFiscal;
																				$scope.factura.usoCFDI = $scope.catUsoCdfi;
																				$scope.factura.moneda = $scope.catMoneda;
																				$scope.factura.tipoComprobante = $scope.catTipoComprobante;

																				$scope.isVisibleSeccionCargaDocComplementoPago = false;
																				$scope.isVisibleSeccionDescargaDocComplementoPago = false;
																				$scope.totalesFlujoAlterno = {};

																				$scope.totales = {};

																				$scope.calculaConceptoYFactura();
																			}

																			// $scope.calcularTotales();
																		},
																		function(
																				response) {
																			$log
																					.error(response.status
																							+ ' - '
																							+ response.statusText);
																			pinesNotifications
																					.notify({
																						title : 'Error',
																						text : 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
																						type : 'error'
																					});

																		});

													/*},
													function(response) {
														$log.error(response.status
																+ ' - '
																+ response.statusText);
														pinesNotifications
																.notify({
																	title : 'Error',
																	text : 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
																	type : 'error'
																});
													});*/

								}


								

								  $scope.cargaInicialDocumentosIdFactura = function (idPppFactura){
							  
							  $http.post(CONFIG.APIURL + "/ppp/nominas/obtenerDocumentosPppFactura.json", idPppFactura).then(
										function(data) {
											$scope.documentosPppNomina = data.data;
										}, function(data) {
											console.log("error --> " + data);
										});
						  }
								  
								  $scope.validaCamposPaso4_5 = function () {
								    	
								    	$scope.habilitaCampos = true;
								    	$scope.actulizaComprobante= true;
								    	$scope.habilitaCatMetodoPago = true;
								    	$scope.habilitaGuardar = true;
								    	$scope.habilitaGenerarFactura = true;
								    	$scope.isVisibleSeccionCargaDocComplementoPago = true;
								    	$scope.isVisibleSeccionDescargaDocComplementoPago = false;
						    		
							
								    	if($scope.factura.idCmsXmlFactura >= 1 && $scope.factura.idCmsPdfFactura >= 1){
								    		
								    		$scope.habilitaCampos = false;
								    		$scope.actulizaComprobante= true;
								    		$scope.habilitaGenerarFactura = false;
								    		
								    		if($scope.factura.montoComprobantePago != null){
								    			$scope.isVisibleSeccionCargaDocComplementoPago = false;
								    		}
								    		
								 
								    		
								    	}else if($scope.factura.montoComprobantePago == null 
								    			&& ($scope.factura.metodoPago !=undefined && $scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral >= 1) 
						    					 && ($scope.factura.formaPago !=undefined && $scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral >= 1)) {
								    		
								    		
								    		$scope.habilitaGenerarFactura = true;
								    		
								    		
								    		
								    	}else if(($scope.factura.metodoPago !=undefined && $scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral == 0) 
							    					 && ($scope.factura.formaPago !=undefined && $scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral == 0)) {
								    		
								    		$scope.habilitaGenerarFactura = false;
								    //		$scope.habilitaGuardar = false;
								    	//	$scope.habilitaCatMetodoPago = false;
								    		
								    	}else if($scope.factura.idCMS >= 1){
								    		
								    		$scope.isVisibleSeccionCargaDocComplementoPago = false;
									    	$scope.isVisibleSeccionDescargaDocComplementoPago = true;
								    		
								    	}
								    }
								      
								  
								  
								  
								  
									$scope.agregarConceptoFactura = function() {
										$scope.conceptoFac=null;
										  bootbox.confirm({
											  title : "Confirmar acci&oacute;n",
												message : '<div class="text-center">Requiere agregar más conceptos a la factura.<br>¿Desea continuar?</div>',
												buttons : {
													confirm : {
														label : 'ACEPTAR',
														className : 'btn-green'
													},
													cancel : {
														label : 'CANCELAR',
														className : 'btn-danger'
													}
												},
												callback : function(result) {
													if (result) {
														$scope.facturaSeleccionada;
														$scope.conceptoFac={};
														$('#modalAgregarConcepto').modal('show');
													}
												}				  
										  });
									}
									
								
									$scope.calcularIvaConcepto= function(conceptoFac) {
										if(conceptoFac.ivaTrasladado16==true){
										conceptoFac.ivaTrasladado16Monto = parseFloat(parseFloat(conceptoFac.subtotal* parseFloat(0.16)).toFixed(2));	
										conceptoFac.total = parseFloat(parseFloat(conceptoFac.subtotal*parseFloat(1.16)).toFixed(2));
																
										}else if (conceptoFac.ivaTrasladado16==false)
										{
											conceptoFac.total = conceptoFac.total - conceptoFac.ivaTrasladado16Monto;
											conceptoFac.ivaTrasladado16Monto = 0;	
											
											
										}
									 
									}
									
									
									$scope.generarConceptosPlus= function(conceptoFac) {
											$scope.conceptosPlus.push(angular.copy(conceptoFac));	
											if ($scope.totales.total != undefined
													&& $scope.totales.total > 0) {
											if(conceptoFac.ivaTrasladado16=true){
										$scope.totales.sumaImpuestos = parseFloat(parseFloat(parseFloat($scope.totales.sumaImpuestos)+ parseFloat(conceptoFac.ivaTrasladado16Monto)).toFixed(2));
												
			                        	$scope.totales.subtotal = parseFloat(parseFloat(parseFloat($scope.totales.subtotal)	+ parseFloat(conceptoFac.total)).toFixed(2));
											}
											}
										
											
									}
									
									   $scope.modalColaboradores = function (nomina) {
						    		    	
									    	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", nomina.idNomina).then(function(response) {
												  
									    		$scope.claveNomina = nomina.claveNomina;
									  	  		 $scope.dataExcel.contentRows= response.data;
									  	  		 
									  	  		
									  	  	
									  	  		$('#modalColaboradores').modal('show');
									  	  		 	  	  		
												}, function(data) {
													console.log("error modalColaboradores--> " + data);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
												        type: 'error'
												      });
												});

									      }
									   
									   
									    
									    $scope.actualizarDocComprobantePago = function(){					
											$scope.isVisibleSeccionCargaDocComplementoPago = true;
											$scope.isVisibleSeccionDescargaDocComplementoPago = false;
											$scope.actualizaDocComplementoPago = true;
											$scope.generarFacturaPaso5.documentoNuevo = null;
											
											var fileElement = angular.element('#file_doc_comp_pago');
									        angular.element(fileElement).val(null);
								      	}
									    
									    $scope.cancelarDocComprobantePago = function(){
									    	$scope.isVisibleSeccionCargaDocComplementoPago = false;
											$scope.isVisibleSeccionDescargaDocComplementoPago = true;
											$scope.actualizaDocComplementoPago = false;
											
											var fileElement = angular.element('#file_doc_comp_pago');
									        angular.element(fileElement).val(null);
									        
									        $scope.generarFacturaPaso5.documentoNuevo = $scope.factura.documentoNuevo;
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
									
									
								    
								    
				      
					
					
					

				});