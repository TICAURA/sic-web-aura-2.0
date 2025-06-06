'use strict';
angular.module('theme.core.templates')
  .controller('nominaController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, nominaService, pinesNotifications,NgTableParams) {
	  
	  $scope.nominaDto={};
	  $scope.nominaClienteDto={};
	  $scope.nominaComplementoDto={};
	  $scope.nombreCliente;
	  $scope.clienteSeleccionado ={};
	  $scope.montosFactura = {};
	  
	  $scope.isVisibleNominasEnProceso = false;
	  $scope.isVisibleSeccionesNomina = false;
	  $scope.isVisibleSeccionCargaDocumentoSustento = true;
	  $scope.isVisibleSeccionDescargaDocumentoSustento = false;
	  $scope.habilitaPaso4Documentos = false;
	  $scope.habilitaPaso4Factura = false;
	  $scope.idNomina;
	  $scope.isEditarColaborador=false;
	   $scope.idNominaComplementaria;
	  
	  $scope.nominaProcesoSeleccionada = {};

	  $scope.concepto ={};
	  $scope.totales = {};
	  $scope.conceptos =[];
	  
	  $scope.formulaFactura = {};
	  $scope.catMoneda={};
	  $scope.catMoneda.idCatGeneral= 2804;
	  $scope.catMoneda.clave = 'MXN';
	  
	  $scope.catTipoComprobante={};
	  $scope.catTipoComprobante.idCatGeneral= 329;
	  $scope.catTipoComprobante.clave = 'I';
	  
	  $scope.catRegimenFiscal={};
	  $scope.catRegimenFiscal.idCatGeneral= 334;
	  $scope.catRegimenFiscal.clave = '601';
	  
	  $scope.catUsoCdfi={};
	  $scope.catUsoCdfi.idCatGeneral= 309;
	  $scope.catUsoCdfi.clave = 'G03';
	  
	  //metod de pago aplicaria cuando la nomina requeria 
	  // financiamiento y fe rechazada por operaciones o finanzas
	  $scope.catMetodoPago={};
	  $scope.catMetodoPago.idCatGeneral= 294;
	  $scope.catMetodoPago.clave = 'PUE';
	  
	  $scope.catFormaPago={};
	  $scope.catFormaPago.idCatGeneral= 361;
	  $scope.catFormaPago.clave = '03';
	  
	  $scope.unidadDefault ={}
	  $scope.unidadDefault.idCatGeneral= 1058;
	  $scope.unidadDefault.clave = 'E48';
	  
	  $scope.codigoSatDefault = 84131801;
	  $scope.descripcionSatDefault = "Fondos de pensiones autodirigidos o patrocinados por el empleador";
	  $scope.descripcionConceptoDefault ="Prestación de servicios de administración de fondo privado de pensiones con el Plan ID CONSAR" 
	    
	   $scope.codigoSatDefaultIRLAB = 85122200;
	  $scope.descripcionSatDefaultIRLAB = "Aportación derivada al plan de Indemnización";
	
	  
	  $scope.descripcionConceptoDefaultIRLAB="Aportación derivada al Plan de Indemnización";
	  $scope.factura={};
	  $scope.generarFacturaPaso5={};
	  $scope.colaborador = {};
	  $scope.generarFacturaPaso5.documentoNuevo = {};
	  $scope.isVisibleSeccionCargaDocComplementoPago = true;
	  $scope.isVisibleSeccionDescargaDocComplementoPago = false;
	  $scope.actualizaDocComplementoPago = false;
	  $scope.habilitaCampos = true;
	  $scope.actulizaComprobante= true;
	  $scope.mostrarAccionesPaso4 = true;
	  $scope.datosVisiblePaso6 =false;
	  
	  $scope.pacTimbrado={};
	  
	  $scope.existeRfcColaborador = false;
	  $scope.existeCurpColaborador = false;
	  $scope.existeNssColaborador = false;
	  $scope.existeClabeColaborador = false;
	  $scope.mostrarBotonGuardarColaboradores =false;
	  
	  // totalesFlujoAlterno solo aplica en el paso 4
	  // cuando no requieran factura
	  $scope.totalesFlujoAlterno = {};
	  $scope.totalesFlujoAlterno.totales = {};
	  
	  // documento excel
      $scope.data = {};
      $scope.listaColaboradores={};
      $scope.dataExcel = {};
      $scope.dataExcel.contentRows = {};
      $scope.notaGuardarColaboradores = false;
      $scope.indexColaboradorEditar = undefined;
      
       $scope.subtotalPPP;
	   $scope.totalAcomplementar=0;
	   $scope.validaMontoPPP = false;
	   $scope.isComplementariaNomina = false;
	   $scope.colaboradoresCargados=false;
	   $scope.fechaFacturacionComplemento=null;
	   $scope.isVisibleComplementaria=false;
	   $scope.titulo="Nóminas en proceso";
	   $scope.titulo_timbrado="Timbrado nómina-";
	   $scope.tableColaboradoresParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataExcel});
	   
	  $scope.cargaInicialNomina = function(){
		  
		  //Carga inicial de los clientes asignados a nominista
		  nominaService.cargaInicialNomina(function(response) {
			  $scope.gridNominaCliente = response.data.gridNominaCliente;
			  $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridNominaCliente});
			  $scope.deshabilitaMesSiguiente();
			  $scope.rol = response.data.rol;
			 
			},
			function(response) {
				$log.error("error --> " + response);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error, favor de intentarlo mas tarde.',
			        type: 'error'
			      });
			});

		  
		  $scope.deshabilitaMesSiguiente = function(){
			  var date = new Date();
			  var ultimoDiaMes =  new Date(date.getFullYear(), date.getMonth() + 1, 0);
			  var mes = ultimoDiaMes.getMonth() + 1;    
			  var dia = ultimoDiaMes.getDate();
			  var anio = ultimoDiaMes.getFullYear();
			  
			  if(mes < 10)
				  mes = '0' + mes.toString();
			  if(dia < 0)
				  dia = '0' + dia.toString();
			  
			  var fechaMaxima = anio + '-' + mes + '-' + dia;			  
			  $('#fechaInicioNomina').attr('max', fechaMaxima);
			  $('#fechaFinNomina').attr('max', fechaMaxima);
			  
		  }
		 
		  //Carga Inicial de catalogos que utiliza la pagina
		  nominaService.cargaInicialNominaCatalogos(function(response) {
			  
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
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error, favor de intentarlo mas tarde.',
			        type: 'error'
			      });
			});
		  
	  };
		 

	  $scope.cargaInicialNomina();
	  
	  
	    $scope.crearNomina = function () {
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
						
						nominaService.guardarNominaPpp($scope.nominaDto, function(response) {

							if(response.data.mensajeError != undefined){
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: response.data.mensajeError,
							        type: 'error'
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

										 $('#modalCrearNominas').modal('hide');
										 
										 $scope.cargaInicialNomina();
										 
										if($scope.nominaDto.nominaClienteDto.clienteDto != null){
											 $scope.listaNominaEnProceso($scope.nominaDto.nominaClienteDto);
									  	}else{
											$scope.getDatosNominaByIdNomina($scope.nominaDto);
										}

										
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
	      }
	  	    
	    $scope.listaNominaEnProceso = function (nominaCliente) {
	    	
	    	$scope.isVisibleSeccionesNomina = false;
	    	 $scope.isComplementariaNomina = false;
	    	 $scope.isVisibleComplementaria=false;
	    	 $scope.titulo="Nóminas en proceso";
	    	
	    	$scope.clienteSeleccionado = nominaCliente.clienteDto;
	    	
	    	nominaService.listaNominaEnProceso(nominaCliente.clienteDto.idCliente, function(response) {

	    		if(response.data.gridNominasProceso!=undefined && response.data.gridNominasProceso !=null){
	    			
	    			if(nominaCliente.clienteDto.nombreCompleto!=null){
	    				$scope.descripcionNomina = nominaCliente.clienteDto.nombreCompleto;
	    			}else if(nominaCliente.clienteDto.razonSocial!=null){
	    				$scope.descripcionNomina = nominaCliente.clienteDto.razonSocial;
	    			}

					$scope.gridNominasProceso = response.data.gridNominasProceso;
					
					if($scope.gridNominasProceso.length >= 1){
						$scope.rfcCliente = nominaCliente.clienteDto.rfc;
						
						  pinesNotifications.notify({
		    			        title: 'Nomina completa',
		    			        text: 'Se cargarón las nominas en proceso exitosamente ',
		    			        type: 'success'
		    			      });
					}else{
						
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'No existen nominas en proceso',
					        type: 'error'
					      });
					}
					
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
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
	      
	       $scope.listaNominaAcomplementar = function (nominaCliente) {
	    	$scope.isVisibleSeccionesNomina = false;
	    	$scope.isVisibleComplementaria = true;	  
	    	$scope.titulo="Nóminas para Complementar";  	
	    	$scope.clienteSeleccionado = nominaCliente.clienteDto;	
	    	nominaService.listaNominaAcomplementar(nominaCliente.clienteDto.idCliente, function(response) {

	    		if(response.data.gridNominasProceso!=undefined && response.data.gridNominasProceso !=null){
	    			
	    			if(nominaCliente.clienteDto.nombreCompleto!=null){
	    				$scope.descripcionNomina = nominaCliente.clienteDto.nombreCompleto;
	    			}else if(nominaCliente.clienteDto.razonSocial!=null){
	    				$scope.descripcionNomina = nominaCliente.clienteDto.razonSocial;
	    			}

					$scope.gridNominasProceso = response.data.gridNominasProceso;
					
					if($scope.gridNominasProceso.length >= 1){
						$scope.rfcCliente = nominaCliente.clienteDto.rfc;
						
						  pinesNotifications.notify({
		    			        title: 'Nomina completa',
		    			        text: 'Se cargarón las nominas a complementar exitosamente ',
		    			        type: 'success'
		    			      });
					}else{
						
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'No existen nominas para complementar',
					        type: 'error'
					      });
					}
					
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
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
	    	    
	    $scope.getHistoricoByIdPppNomina = function (nomina) {
	    		    	
	    	
	    	nominaService.getHistoricoByIdPppNomina(nomina.idNomina, function(response) {
	    		$scope.claveNomina = nomina.claveNomina;
	    		$scope.gridHistorico = response.data;
	    		$('#modalHistoricoMovimiento').modal('show');
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
	    
	    
	    $scope.modalColaboradores = function (nomina) {
	    		    	
	    	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", nomina.idNomina).then(function(response) {
				  
	    		$scope.claveNomina = nomina.claveNomina;
	  	  		 $scope.dataExcel.contentRows= response.data;
	  	  		 if(response.data.length >= 1){	
	  	  			 $scope.colaboradoresCargados=true;
	  	  		 }else{
	  	  			$scope.colaboradoresCargados=false;
	  	  		 }
	  	  		
	  	  	
	  	  		$('#modalColaboradores').modal('show');
	  	  		 	  	  		
				}, function(data) {
					console.log("error modalCrearNomina--> " + data);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
				        type: 'error'
				      });
				});

	      }
		  
		  $scope.reenviarCorreo = function(colaborador){
		  	
		  	var nomina ={}
		  	
		  	nomina.colaboradores=[colaborador];
		  	nomina.nominaPPP = $scope.nominaProcesoSeleccionada;
		  	nomina.nominaComplemento = angular.copy($scope.nominaComplementoDto);
		  	nomina.idPPPNominaFactura = angular.copy($scope.factura.idPPPNominaFactura);
		  	  
		  	$http.post(CONFIG.APIURL + "/ppp/seguimientoNomina/enviarCorreoColaborador.json", nomina).then(function(response) {
		  		  
		  		pinesNotifications.notify({
		  	        title: 'Env&iacute;o de correo',
		  	        text: 'El correo se env&iacute;o correctamente',
		  	        type: 'success'
		  	      });
		    	
		    		 	  	  		
		  		}, function(data) {
		  			console.log("error al enviar correo al colaborador --> " + data);
		  			pinesNotifications.notify({
		  		        title: 'Error',
		  		        text: 'Ocurri&oacute; un error al enviar el correo, favor de intentarlo nuevamente.',
		  		        type: 'error'
		  		      });
		  		});
		  	} 
	    
	    // carga documento de sustento en  en memoria
	    $scope.fileChangedDoc = function (documento) {
			  
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
	                  
	                  $scope.nominaComplementoDto.documentoNuevo = documento;
	              }
	              
	              reader.readAsDataURL(lstArchivos[0]);
	              
	          }

	      };
	      
		    // carga documento complemento de pago  en memoria
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
	    
	    $scope.guardarNominaComplemento = function (complementoNominaForm) {
	    
	    	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", $scope.nominaProcesoSeleccionada.idNomina).then(function(response) {
			
			 if(response.data.length >= 1){	
	    
    	  if(complementoNominaForm.$invalid){
	    		  
	    		  if(complementoNominaForm.fechaDispersion_.$invalid && complementoNominaForm.fechaFacturacion_.$invalid){
	    			  pinesNotifications.notify({
	    			        title: 'Datos Adicionales',
	    			        text: 'El formulario tiene un error. Favor de ingresar "Fechas de dispersión y facturación"',
	    			        type: 'error'
	    			      });
	    			  
	    		  }else if(complementoNominaForm.fechaDispersion_.$invalid){
	    			  
	    			  pinesNotifications.notify({
	    			        title: 'Datos Adicionales',
	    			        text: 'El formulario tiene un error. Favor de ingresar "Fecha de dispersión"',
	    			        type: 'error'
	    			      });
	    			  
	    		  }else if(complementoNominaForm.fechaFacturacion_.$invalid){
	    			  
	    			  pinesNotifications.notify({
	    			        title: 'Datos Adicionales',
	    			        text: 'El formulario tiene un error. Favor de ingresar "Fecha de facturación"',
	    			        type: 'error'
	    			      });
	    			  
	    		  }
	    		  
	    		  // requiere financiamiento
	    		  if($scope.nominaComplementoDto.requiereFianciamiento){
	    			  
	    			  var fileName = document.getElementById("file_documento_complemento").value;
	    			  
	    			  if(complementoNominaForm.montoFinanciamiento.$invalid && complementoNominaForm.montoFinanciamiento.$invalid){
		    			  pinesNotifications.notify({
		    			        title: 'Datos Adicionales',
		    			        text: 'El formulario tiene un error. Favor de ingresar "Monto de financiamiento"',
		    			        type: 'error'
		    			      });
		    			  
		    		  }else if(complementoNominaForm.motivoFinanciamiento.$invalid && complementoNominaForm.motivoFinanciamiento.$invalid){
		    			  pinesNotifications.notify({
		    			        title: 'Datos Adicionales',
		    			        text: 'El formulario tiene un error. Favor de ingresar "Motivo de financiamiento"',
		    			        type: 'error'
		    			      });
		    			  
		    		  }else if(fileName==undefined || fileName==null || fileName==""){
		    			  pinesNotifications.notify({
		    			        title: 'Datos Adicionales',
		    			        text: 'El formulario tiene un error. Favor de ingresar "Documento de sustento"',
		    			        type: 'error'
		    			      });
		    			  
		    			  $("#file_documento_complemento").val('');
		    		  }
	    			  
	    		  }
	    		  
	    		  
	    		  
	        	 
	    	  }else{
	    		  
	    		// requiere financiamiento
	    		  if($scope.nominaComplementoDto.requiereFianciamiento){
	    			  var fileName = document.getElementById("file_documento_complemento").value;
	    			  
	    			  if($scope.nominaComplementoDto.montoFinanciamiento > $scope.totalNominaPPPColaboradores){
	    				  pinesNotifications.notify({
		    			        title: 'Datos Adicionales',
		    			        text: 'El formulario tiene un error. <br>El monto de financiamiento NO PUEDE SER MAYOR al monto total ppp',
		    			        type: 'error'
		    			      });
	    				  
	    			  }else if(fileName==undefined || fileName==null || fileName==""){
		    			  pinesNotifications.notify({
		    			        title: 'Datos Adicionales',
		    			        text: 'El formulario tiene un error. Favor de ingresar "Documento de sustento"',
		    			        type: 'error'
		    			      });
		    			  
		    			  $("#file_documento_complemento").val('');
		    			  
		    		  }else{
		    			  
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
										
										$scope.guardarNominaComplementoSubmit();
										
									}
								}
							});
		    		  }
	    			  
	    		  }else{
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
									
									$scope.guardarNominaComplementoSubmit();
									
								}
							}
						});
	    		  }  
	    	  }
	    	  }//if de validar colaborador
	    	  else {
	    	  pinesNotifications.notify({
	 										        title: 'Error',
	 										        text: 'Favor de agregar al menos un colaborador a la Nómina.',
	 										        type: 'error'
	 										      });
	    	  }
	    	  }
	    	  ,function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, al consultar los colaboradores.',
			        type: 'error'
			      });
	    	});
	     }
	    
	    
	    $scope.guardarNominaComplementoSubmit = function(){
  		  
	     var fechaInicioNomina = null;
		 var fechaDispersion = null;
		 var fechaFacturacion = null;	
	    	
	     if(($scope.nominaProcesoSeleccionada.fechaInicioNomina == undefined || $scope.nominaProcesoSeleccionada.fechaInicioNomina == null)
	    		 || ($scope.nominaComplementoDto.fechaDispersion == undefined || $scope.nominaComplementoDto.fechaDispersion == null)
	    		 || ($scope.nominaComplementoDto.fechaFacturacion == undefined || $scope.nominaComplementoDto.fechaFacturacion == null)){
	    	 
	    	 pinesNotifications.notify({
			        title: 'Datos Adicionales',
			        text: 'Ocurrio un error al recuperar las fechas. Fvaor de intentarlo mas tarde.',
			        type: 'error'
			      });
	    	 
	     }else{
		      fechaInicioNomina= new Date($scope.nominaProcesoSeleccionada.fechaInicioNomina);
		      fechaDispersion = new Date($scope.nominaComplementoDto.fechaDispersion);
		      fechaFacturacion = new Date($scope.nominaComplementoDto.fechaFacturacion);
		      
		      if(fechaDispersion < fechaInicioNomina){
		    	  
		    	  pinesNotifications.notify({
				        title: 'Datos Adicionales',
				        text: 'La FECHA DE DISPERSIÓN no puede ser menor a la FECHA INICIO DE NÓMINA',
				        type: 'error'
				      });
		    	  
		      }else if(fechaFacturacion > fechaDispersion){
		    	  pinesNotifications.notify({
				        title: 'Datos Adicionales',
				        text: 'La FECHA DE FACTURACIÓN no puede ser mayor a la FECHA DE DISPERSIÓN',
				        type: 'error'
				      });
		    	  
		      }else{
		    	  
		  		  if($scope.nominaProcesoSeleccionada!=null && $scope.nominaProcesoSeleccionada !=undefined
		  				  && $scope.totalNominaPPPColaboradores!=null){
		  				  //$scope.nominaProcesoSeleccionada.nominaClienteDto.montoTotalColaboradores){
		  			// $scope.nominaComplementoDto.montoTotalPpp = $scope.nominaProcesoSeleccionada.nominaClienteDto.montoTotalColaboradores;
		  			$scope.nominaProcesoSeleccionada.nominaClienteDto.montoTotalColaboradores;
		  			$scope.nominaComplementoDto.montoTotalPpp = $scope.totalNominaPPPColaboradores;
		  		  }
		  		 
		  		  var complementoNomina = {};
		  		  
		  		  complementoNomina = angular.copy($scope.nominaComplementoDto);
		  		 if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 23||$scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 19){
		  		   complementoNomina.nominaDto= angular.copy($scope.nominaDto);
		  		   		 
		  		  }else{
		  		  complementoNomina.nominaDto= angular.copy($scope.nominaProcesoSeleccionada);
				  }
				  nominaService.guardaNominaComplemento(complementoNomina, function(response) {
		
						if(response.data.mensajeError != undefined){
							$scope.getNominaComplemento(complementoNomina.nominaDto.claveNomina);
							
							
							  $scope.validaCamposPaso4_5();
							  
							
							
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: response.data.mensajeError,
						        type: 'error'
						      });
						}else{
							
						$log.info("exito al guardar el complemento de la nomina");
			        	  
			        	nominaService.cargarDatosDespuesDeGuardarComplemento(complementoNomina.nominaDto.claveNomina, function(response) {
			        		
			        		 $scope.getDatosNominaByIdNomina(response.data);
			        		 $scope.nominaCliente = response.data.nominaClienteDto;
//			        		 $scope.listaNominaEnProceso($scope.nominaCliente);				
			        		 $scope.validaCamposPaso4_5();
			        		 
			        							 
						  	 if( $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===25 && $scope.isComplementariaNomina === true){
						  	 		 $scope.listaNominaEnProceso($scope.nominaCliente);		
						  	 		 pinesNotifications.notify({
						  	 			 title: 'Datos adicionales',
						  	 			 text: 'La operación se realizó exitosamente de complemento de Nomina ',
						  	 			 type: 'success'
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
	    }
	    
	        
	    $scope.getDatosNominaByIdNomina = function (nominaItem) { 
	    	$scope.totalAcomplementar=0;
	    	$scope.dataExcel={};
		    $scope.tableColaboradoresParams = new NgTableParams({page: 1, count: 10}, {data: $scope.dataExcel.contentRows});
		    $scope.data = {};
	    	$scope.nominaProcesoSeleccionada = nominaItem;
	    	$scope.conceptos =[];
	    	if( $scope.nominaDto.esNominaComplementaria){
		    	$scope.getDatosNominaEnProceso($scope.idNomina);
		    	$scope.cargaInicialColaboradores($scope.idNomina);	 
		    	//$scope.getNominaComplemento($scope.nominaDto.claveNomina);   
		    	$scope.nominaComplementoDto.fechaFacturacion = new Date($scope.nominaDto.nominaComplementoDto.fechaFacturacion);
		    	if($scope.nominaDto.nominaComplementoDto.fechaDispersion!=null){
		    	$scope.nominaComplementoDto.fechaDispersion= 	new Date($scope.nominaDto.nominaComplementoDto.fechaDispersion);}
		    	$scope.isComplementariaNomina = true;
		    	$scope.existenColaboradodaresTimbrados($scope.idNomina);
	    	}
	    	
	    	else if($scope.nominaProcesoSeleccionada.esNominaComplementaria ){
		    	$scope.getDatosNominaEnProceso(nominaItem.idNomina);
		    	$scope.cargaInicialColaboradores(nominaItem.idNomina);
		    	$scope.existenColaboradodaresTimbrados($scope.nominaProcesoSeleccionada.idNomina);
		    	//	$scope.getDatosNominaComplementaria(nominaItem); 
		    	//$scope.getDatosFactura(nominaItem.idNomina);
		    	
	    	}
	  
	    	else{
		    	$scope.getDatosNominaEnProceso(nominaItem.idNomina);
		    	$scope.cargaInicialColaboradores(nominaItem.idNomina);	
		    	$scope.getNominaComplemento(nominaItem.claveNomina);
		    	$scope.getDatoFactura(nominaItem.idNomina , $scope.nominaProcesoSeleccionada.nominaClienteDto.idNominaCliente);
		    	$scope.existenColaboradodaresTimbrados(nominaItem.idNomina);
		    	$scope.generarFacturaPaso5 = {};
	    	}
	    	    		
	    }
	    
	     //para el inicio de la nomina complementaria
	    $scope.getDatosInicioNominaComplementaria = function (nominaItem) {
	    
	    	  $scope.dataExcel={};
		      $scope.tableColaboradoresParams = new NgTableParams({page: 1, count: 10}, {data: $scope.dataExcel.contentRows});
		      $scope.data = {};
		      $scope.nominaDto={};
		      $scope.idNomina=null;
		      $scope.nominaComplementoDto={};
		      $scope.dataExcel = {};
		      $scope.totalNominaPPPColaboradores = null;
		      $scope.nominaProcesoSeleccionada = nominaItem;
		      $scope.conceptos =[]; 	  
		      $scope.getDatosNominaComplementaria(nominaItem); 
		      $scope.getDatosFactura(nominaItem.idNomina);
		      $scope.isComplementariaNomina = true;
	   }
	    
	    //para la nomina complementaria
	      $scope.getDatosNominaByIdNominaComplementaria = function (nominaItem) {    	
	    	$scope.nominaProcesoSeleccionada = nominaItem;
	    	$scope.conceptos =[];	
	    	$scope.getDatosNominaEnProceso($scope.idNomina);
	    	$scope.cargaInicialColaboradores($scope.idNomina);	  	
	    }
	    
	        $scope.getDatosNominaComplementaria = function(nominaItem){    	
	    	nominaService.getDatosNominaByIdNominaComplementaria(nominaItem, function(response) {
	    		if(response.data!=undefined && response.data !=null){	
	    			$scope.isVisibleSeccionesNomina = true;
	    			$scope.nominaDto = response.data.nominaDto;
	    			$scope.nominaDto.fechaInicioNomina = new Date(response.data.nominaDto.fechaInicioNomina);
	    			$scope.nominaDto.fechaFinNomina = new Date(response.data.nominaDto.fechaFinNomina);    			
	    			$scope.nominaDto.esNominaComplementaria=response.data.nominaDto.esNominaComplementaria;    		
	    			$scope.nominaDto.nominaComplementoDto=response.data.nominaDto.nominaComplementoDto;
	    			$scope.listaColaboradoresPadre = response.data.ListColaboradoresPadre;		
	    			$scope.subtotalPPP = response.data.nominaDto.montoTotalPpp;
	                $scope.totalAcomplementar = response.data.nominaDto.montoAComplementarPpp;
	                $scope.nominaComplementoDto.fechaFacturacion = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);
	    		    $scope.fechaFacturacionComplemento = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);    		
	    		if(response.data.nominaDto.idNomina!=undefined && response.data.nominaDto.idNomina!=null){
	    		$scope.idNomina = response.data.nominaDto.idNomina;
	    		$scope.cargaInicialColaboradores($scope.idNomina);	  	
	    		}
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
	    		}
			},
			function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });

			});
	    }
	    
	    
	     $scope.guardaDatosNominaComplementaria = function(nominaDto){    	
	    	nominaService.guardaNominaByIdNominaComplementaria(nominaDto, function(response) {
	    		if(response.data!=undefined && response.data !=null){	
	    			$scope.isVisibleSeccionesNomina = true;
	    			
	    			$scope.nominaDto = response.data.nominaDto;
	    			$scope.nominaDto.fechaInicioNomina = new Date(response.data.nominaDto.fechaInicioNomina);
	    			$scope.nominaDto.fechaFinNomina = new Date(response.data.nominaDto.fechaFinNomina);
	    			$scope.nominaDto.catEstatusNomina.idCatGeneral=response.data.nominaDto.catEstatusNomina.idCatGeneral;
	    			$scope.nominaDto.esNominaComplementaria=response.data.nominaDto.esNominaComplementaria;
	    			//$scope.listaColaboradoresPadre = response.data.ListColaboradoresPadre;	    			
	    			$scope.idNomina = response.data.nominaDto.idNomina;
	    		
	               $scope.nominaComplementoDto.fechaFacturacion = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);
	    		             $scope.fechaFacturacionComplemento = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);    		
                // $scope.nominaComplementoDto.fechaFacturacion =$scope.fechaFacturacionComplemento;
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
	    		}
			},
			function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });

			});
	    }
	    
	    //Para Obtener totales
	$scope.getDatosFactura = function(idNomina){ 
        nominaService.getDatosFacturaByIdNomina(idNomina, function(response) {
	     if(response.data!=undefined && response.data !=null && response.data !="" && response.data !=undefined){
		 $scope.totales = response.data.totales;
    	    		
      }

    },
			function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });

			});
 }
	    
	  
	     
	    $scope.validaPasoPaso4 = function () {
	    	// valida seccion paso 4
    		if($scope.nominaComplementoDto!=null &&  $scope.nominaComplementoDto.idNominaComplemento !=null 
    				&& $scope.nominaComplementoDto.idNominaComplemento != undefined &&  $scope.nominaComplementoDto.idNominaComplemento >= 1){

    			
    			// estatus de la nomina cuando pide financiamiento
    			if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 3 
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 4
//    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
//    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15
    					){
    				
        			$scope.habilitaPaso4Documentos = false;
    	    		$scope.habilitaPaso4Factura = false;
    	    	
    	    	// estatus de la nomina cuando pide financiamiento
    			}else if(($scope.nominaComplementoDto.requiereFianciamiento && !$scope.nominaProcesoSeleccionada.necesitaFactura)
    					&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 5
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 11
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 17
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 18
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 19)){
    				
    				$scope.habilitaPaso4Documentos = true;
		    		$scope.habilitaPaso4Factura = false;
		    		
		    	// estatus de la nomina cuando pide financiamiento	
    			}else if (($scope.nominaComplementoDto.requiereFianciamiento && $scope.nominaProcesoSeleccionada.necesitaFactura) 
    					&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 5
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 7
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 11
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 18
    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 19)){
    				
    				$scope.habilitaPaso4Documentos = false;
    			if($scope.nominaDto.esNominaComplementaria){
    				$scope.habilitaPaso4Factura = false;
    			}
		    	// estatus de la nomina cuando  NO  pide financiamiento	
    			}else     if($scope.nominaProcesoSeleccionada.necesitaFactura && !$scope.nominaComplementoDto.requiereFianciamiento){
    				
		    		$scope.habilitaPaso4Documentos = false;
					$scope.habilitaPaso4Factura = true;
    				if($scope.nominaDto.esNominaComplementaria ){
    				$scope.habilitaPaso4Factura = false;
    				}
		    	
		    		
		    		
		    	// estatus de la nomina cuando  NO  pide financiamiento	
		    	}else if (!$scope.nominaProcesoSeleccionada.necesitaFactura && !$scope.nominaComplementoDto.requiereFianciamiento){
		    		
		    		$scope.habilitaPaso4Documentos = true;
					$scope.habilitaPaso4Factura = false;
    			if($scope.nominaDto.esNominaComplementaria===true){
    				$scope.habilitaPaso4Factura = true;
    			}else{
		    		$scope.habilitaPaso4Factura = false;
		    		}
		    		
		    	}
    		}else{
    			
    			$scope.habilitaPaso4Documentos = false;
	    		$scope.habilitaPaso4Factura = false;
	    		
    		}
	    }
	    

	    
	    
	    $scope.getDatosNominaEnProceso = function(idNomina){
	    	
	    	nominaService.getDatosNominaByIdNomina(idNomina, function(response) {

	    		if(response.data!=undefined && response.data !=null){
	    			
	    			$scope.isVisibleSeccionesNomina = true;
	    		
	    			$scope.nominaDto = response.data.nominaDto;
	    //			$scope.nominaComplementoDto.nominaDto = response.data.nominaDto;
	    			$scope.nominaDto.fechaInicioNomina = new Date(response.data.nominaDto.fechaInicioNomina);
	    			$scope.nominaDto.fechaFinNomina = new Date(response.data.nominaDto.fechaFinNomina);
	    			$scope.nominaDto.idNominaPPPPadre=response.data.nominaDto.idNominaPPPPadre;
	    			$scope.comboConceptoFacturaCrm = response.data.comboConceptoFacturaCrm;
	    			$scope.comboPac = response.data.comboPac;
	    			if( $scope.nominaProcesoSeleccionada.esNominaComplementaria){
                        $scope.fechaFacturacionComplemento = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);    		
                     	$scope.nominaComplementoDto.fechaFacturacion = new Date(response.data.nominaDto.nominaComplementoDto.fechaFacturacion);
                        $scope.subtotalPPP = response.data.nominaDto.montoTotalPpp;
                        $scope.totalAcomplementar = response.data.nominaDto.montoAComplementarPpp;
                        $scope.nominaDto.esNominaComplementaria=response.data.nominaDto.esNominaComplementaria; 
                        $scope.listaColaboradoresPadre=response.data.nominaDto.listColaboradores;
                 }
                 if(response.data.nominaDto.necesitaTimbre == false){
                 $scope.titulo_timbrado="Cierre de nómina-";
                 }
	    			
	    			$scope.idNomina = response.data.nominaDto.idNomina;
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
	    		}
			},
			function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });

			});
	    }
	    
	    
	    $scope.getDatoFactura = function(idNomina, idNominaCliente){    	
	    	$scope.factura = {};
	    	$scope.factura.conceptos = null;
	    	
	    	nominaService.getFormulaFactura(idNominaCliente, function(response) {
	    		
	    		$scope.formulaFactura = response.data;
	    		
	    		
	    		nominaService.getDatosFacturaByIdNomina(idNomina, function(response) {

	    			$scope.mostrarAccionesPaso4 = true;
	    			
		    		if(response.data!=undefined && response.data !=null && response.data !="" && response.data !=undefined){
		    				
		    			 $scope.factura = response.data;
		    			 $scope.conceptos = response.data.conceptos;
		    			 $scope.totales = response.data.totales;
		    			 $scope.montoComprobantePagoPaso4 = $scope.factura.montoComprobantePago;
		    			 
		    			 
		    			 
		    			// se necesita para validacion del documeno en paso 4 (flujo cuando NO requieren factura)
		    			 if($scope.factura !=undefined && $scope.factura !=null && $scope.factura.idPPPNominaFactura !=null){
		    		    		$scope.cargaInicialDocumentosPppNomina($scope.factura.idPPPNominaFactura);
		    		    		
		    		    		$scope.totalesFlujoAlterno.totales = $scope.factura.totales;
		    		    		
		    		    		if($scope.factura.montoComprobantePago != undefined && $scope.factura.montoComprobantePago != null){
		    		    			$scope.totalesFlujoAlterno.montoComprobantePagoPaso4 = $scope.factura.montoComprobantePago;
		    		    		}
		    		    		
		    		    	}
		    			 
		    			// se necesita para validacion de acciones del paso 4 (flujo cuando SI requieren factura)
		    			 if($scope.factura.conceptos.length >= 1){
		    				 $scope.mostrarAccionesPaso4 = false;
		    				 
		    				 if($scope.factura.montoDeposito==null ||$scope.factura.montoDeposito==0 ){
		    					 $scope.obtenerListDepositos(idNomina);
		    				 }
		    			 }
		    			 
		    			 // se necesita para validacion del documeno en paso 5 (flujo cuando SI requieren factura)
		    			 if(($scope.factura.documentoNuevo !=undefined && $scope.factura.documentoNuevo !=null)
		    					 && ($scope.factura.documentoNuevo.nombreArchivo !=undefined && $scope.factura.documentoNuevo.nombreArchivo !=null)){
		    				 
		    				 $scope.generarFacturaPaso5.documentoNuevo = $scope.factura.documentoNuevo;
		    			 }
		    			 

		    			
		    			 
//		    			 if($scope.conceptos == undefined || $scope.conceptos == null || $scope.conceptos == [] || $scope.conceptos.length === 0){
		    				 
		    				 $scope.calculaConceptoYFactura();
//		    			 }
		    			 
		    			// recupera datos para el paso 5 (flujo cuando SI requieren factura)
		    			 if(($scope.factura.metodoPago !=undefined && $scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral >= 1) 
		    					 && ($scope.factura.formaPago !=undefined && $scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral >= 1) ){
				    			
		    				 $scope.generarFacturaPaso5.metodoPago = $scope.factura.metodoPago;
		    				 $scope.generarFacturaPaso5.formaPago = $scope.factura.formaPago;

		    				 if($scope.factura.formaPago!= undefined && $scope.factura.formaPago != null){
		    					 $scope.generarFacturaPaso5.montoComprobantePago = $scope.factura.montoComprobantePago; 
		    				 }
		    				 
		    				 // si la nomina necesito financiamiento y fue rechazado por operaciones o finanzas, el combo de metodo de pago sera por defaul "PUE"
		    				 if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
		    	    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15){
		    					 
		    					 $scope.generarFacturaPaso5.metodoPago = $scope.catMetodoPago;
		    				 }
		    				 
		    				 $scope.nombreArchivo = $scope.factura.documentoNuevo.nombreArchivo;
		    				 $scope.isVisibleSeccionCargaDocComplementoPago = false;
		    				 $scope.isVisibleSeccionDescargaDocComplementoPago = true;

		    			 }else{
		    				  $scope.isVisibleSeccionCargaDocComplementoPago = true;
		    				  $scope.isVisibleSeccionDescargaDocComplementoPago = false;
		    				  $scope.generarFacturaPaso5.formaPago = $scope.catFormaPago;
		    				  
		    				// si la nomina necesito financiamiento y fue rechazado por operaciones o finanzas, el combo de metodo de pago sera por defaul "PUE"
			    				 if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
			    	    					|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15){ 
			    					 $scope.generarFacturaPaso5.metodoPago = $scope.catMetodoPago;
			    				 }		    				  
		    			 }

		    			 // valida los campos de las secciones 4 y 5
		    			 
		    			  if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral !== 24){
							  	$scope.validaCamposPaso4_5();
							  }
		    		}else{
		    			
		    			$log.error(response.status+ ' - '+ response.statusText);
//		    			$scope.factura.regimenFiscal = $scope.nominaProcesoSeleccionada.clienteDto.catRegimenFiscal;
		    			// valores por defaul ya que son PPP
		    			$scope.factura.regimenFiscal = $scope.catRegimenFiscal;
		    			$scope.factura.usoCFDI = $scope.catUsoCdfi;
		    			$scope.factura.moneda = $scope.catMoneda;
		    			$scope.factura.tipoComprobante = $scope.catTipoComprobante;

		    			$scope.isVisibleSeccionCargaDocComplementoPago = false;
	    				$scope.isVisibleSeccionDescargaDocComplementoPago = false;
	    				$scope.totalesFlujoAlterno = {};
		    			
	    				$scope.totales ={};
		    			
		    			$scope.calculaConceptoYFactura();
		    		}
		    		
//		    		$scope.calcularTotales();
				},
				function(response) {
					$log.error(response.status+ ' - '+ response.statusText);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
				        type: 'error'
				      });

				});
	    		
	    		
	    		
	    	},function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });
	    	});
	    	
	    	
	    	
	    }
	    
	    $scope.guardaValorDeposito = function(deposito){
	    	$scope.idDepositoSeleccionado = deposito.idDeposito;
	    }
	    
	    $scope.calculaConceptoYFactura  = function(){
	    	
	    	$scope.concepto.cantidad=1;
			$scope.concepto.unidad = $scope.unidadDefault;
			$scope.concepto.codigoSat = $scope.codigoSatDefault;
			$scope.concepto.descripcionSat = $scope.descripcionSatDefault;
			
			if(($scope.nominaProcesoSeleccionada.prestadoraServicioDto.idPrestadoraServicio !=undefined && $scope.nominaProcesoSeleccionada.nominaClienteDto.catProductoNomina.idCatGeneral!=9958)
					&& ($scope.nominaProcesoSeleccionada.prestadoraServicioDto.idConsar !=undefined && $scope.nominaProcesoSeleccionada.prestadoraServicioDto.idConsar !=null)){
				$scope.concepto.descripcionConcepto =  $scope.descripcionConceptoDefault + " " + $scope.nominaProcesoSeleccionada.prestadoraServicioDto.idConsar;
			}else if($scope.nominaProcesoSeleccionada.nominaClienteDto.catProductoNomina.idCatGeneral==9958){
					$scope.concepto.codigoSat = $scope.codigoSatDefaultIRLAB;
			$scope.concepto.descripcionSat = $scope.descripcionSatDefaultIRLAB;
			
				$scope.concepto.descripcionConcepto =  $scope.descripcionConceptoDefaultIRLAB;
		
			}
			
			
			//calculo de la factura
			//Honorario DISPERSIÓN PPP * HONORARIO PACTADO
			
			if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral==5 && ($scope.totalNominaPPPColaboradores==undefined || $scope.totalNominaPPPColaboradores==null)){
			$scope.totalNominaPPPColaboradores =$scope.nominaProcesoSeleccionada.nominaClienteDto.montoTotalColaboradores;
			}
			 
		 $scope.montosFactura.honorario =  parseFloat($scope.totalNominaPPPColaboradores) * parseFloat($scope.formulaFactura.honorarioPPP) / 100;
		 
			 
			 //Formula IVA				    			 
			 if($scope.formulaFactura.formulaTipoIva.clave === 'H2'){ //IVA * MONTO DISPERSIÓN + HONORARIO PACTADO
				 $scope.montosFactura.iva = (parseFloat($scope.totalNominaPPPColaboradores) + $scope.montosFactura.honorario) * parseFloat((parseFloat($scope.formulaFactura.ivaComercial) / 100).toFixed(2));
			 }else if($scope.formulaFactura.formulaTipoIva.clave === 'H3'){//IVA * HONORARIO PACTADO
				 $scope.montosFactura.iva = $scope.montosFactura.honorario  * parseFloat((parseFloat($scope.formulaFactura.ivaComercial) / 100).toFixed(2));
			 }else if($scope.formulaFactura.formulaTipoIva.clave === 'H4'){// NO APLICA
				 $scope.montosFactura.iva = parseFloat(0);
			 }
			 
			 //Formula Factura
			 if($scope.formulaFactura.formulaFactura.clave === 'H5'){ //DISPERSIÓN PPP + HONORARIO PACTADO + IVA	
				 $scope.montosFactura.montoFactura = (parseFloat($scope.totalNominaPPPColaboradores)+ $scope.montosFactura.honorario  + $scope.montosFactura.iva );
			 }else if($scope.formulaFactura.formulaFactura.clave === 'H6'){//HONORARIO PPP + IVA
				 $scope.montosFactura.montoFactura = ( $scope.montosFactura.honorario +   $scope.montosFactura.iva );
			 }
			 
			 
			 if($scope.nominaProcesoSeleccionada.nominaClienteDto.catProductoNomina.idCatGeneral==9958){
			  $scope.montosFactura.subtotal= parseFloat($scope.montosFactura.montoFactura);
			 $scope.concepto.precioUnitario = parseFloat($scope.montosFactura.subtotal);
			 $scope.concepto.importe =  $scope.concepto.precioUnitario;
			 
			 
			 $scope.concepto.ivaTrasladado16 = "SI";
			 $scope.concepto.ivaTrasladado16Monto = parseFloat(0);
			 
			 }else{
			 $scope.montosFactura.subtotal= parseFloat(parseFloat($scope.montosFactura.montoFactura / parseFloat(1.16)).toFixed(2));
			 $scope.concepto.precioUnitario = parseFloat(parseFloat($scope.montosFactura.subtotal).toFixed(2));
			 $scope.concepto.importe =  $scope.concepto.cantidad *  $scope.concepto.precioUnitario;
			 
			 
			 $scope.concepto.ivaTrasladado16 = "SI";
			 $scope.concepto.ivaTrasladado16Monto = parseFloat(parseFloat($scope.concepto.precioUnitario * parseFloat(0.16)).toFixed(2));
			 }
	    }
	    
	    
	    $scope.getNominaComplemento = function(nominaItem){
	    	nominaService.getDatosNominaComplemento(nominaItem, function(response) {

	    		if(response.data!=undefined && response.data !=null && response.data !=''){
	    			if((response.data.idCMS!=null && response.data.idCMS!=undefined) && (response.data.nombreArchivo!=null && response.data.nombreArchivo!=undefined)){
	    				$scope.isVisibleSeccionCargaDocumentoSustento = false;
	    				$scope.isVisibleSeccionDescargaDocumentoSustento = true;
	    				$scope.nombreArchivo = response.data.nombreArchivo;
	    			}else{
	    				$scope.isVisibleSeccionCargaDocumentoSustento = true;
	    				$scope.isVisibleSeccionDescargaDocumentoSustento = false;
	    			}
	    			
	    			$scope.isVisibleSeccionesNomina = true;
	    			$scope.nominaComplementoDto = response.data;
	    			
	    			if(response.data.fechaDispersion != null && response.data.fechaDispersion != undefined && response.data.fechaDispersion != ""){
	    				$scope.nominaComplementoDto.fechaDispersion =  new Date(response.data.fechaDispersion);	
	    			}
	    			
	    			if(response.data.fechaTimbrado != null && response.data.fechaTimbrado != undefined && response.data.fechaTimbrado != ""){
	    				$scope.nominaComplementoDto.fechaTimbrado  =  new Date(response.data.fechaTimbrado);
	    			}
	    			
	    			 if( $scope.nominaDto.esNominaComplementaria===true){
	    				$scope.nominaComplementoDto.fechaFacturacion =  $scope.fechaFacturacionComplemento;
    	  	          }
	    			if(response.data.fechaDispersion != null && response.data.fechaDispersion != undefined && response.data.fechaDispersion != "" && $scope.nominaDto.esNominaComplementaria===false){
	    				$scope.nominaComplementoDto.fechaFacturacion =  new Date(response.data.fechaFacturacion);	
	    			}
	    			
	    			
	    			$scope.validaPasoPaso4();
	    			
	    		}else{
	    			$log.error(response.status+ ' - '+ response.statusText);
	    		}
			},
			function(response) {
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error en el sistema, favor de intentarlo más tarde.',
			        type: 'error'
			      });

			});	
	    }

	    
	    $scope.actualizarDocComplemento = function(){
	    	$scope.isVisibleSeccionCargaDocumentoSustento = true;
			$scope.isVisibleSeccionDescargaDocumentoSustento = false;
			
			var fileElement = angular.element('#file_documento');
	        angular.element(fileElement).val(null);
      	}
	    
	    $scope.cancelarDocComplemento = function(){
	    	$scope.isVisibleSeccionCargaDocumentoSustento = false;
			$scope.isVisibleSeccionDescargaDocumentoSustento = true;
			
			var fileElement = angular.element('#file_documento');
	        angular.element(fileElement).val(null);
      	}
	    
      $scope.modalDetalleNomina = function (nominaCliente) {
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
      }
      
      $scope.modalCrearNomina = function (nominaCliente) {
    	     
    	  $scope.isVisibleNominasEnProceso = false;
    	  
    	  $http.post(CONFIG.APIURL + "/ppp/nominas/detalleNominasClienteByIdCliente.json", nominaCliente.clienteDto.idCliente).then(function(response) {
  			  $scope.isComplementariaNomina = false;
    		  $scope.isVisibleSeccionesNomina = false;
    		  $scope.nominaDto.nominaClienteDto = null;
    		  $scope.nominaDto.fechaInicioNomina = null;
    		  $scope.nominaDto.fechaFinNomina = null;
    		   $scope.nominaDto.esNominaComplementaria =false;
    		  $scope.catNominaCliente = response.data;
    		  
    		  $scope.nominaDto = {};
			  $scope.idNomina = null;
			  $scope.comboConceptoFacturaCrm = null;
			  $scope.nominaProcesoSeleccionada = {};
			  $scope.dataExcel = {};
			  $scope.dataExcel.contentRows= {};
			  $scope.totalNominaPPPColaboradores = null;
			  $scope.totalPPPColaboradores = null;
			  $scope.nombreArchivo = null;
			  $scope.nominaComplementoDto = {};
			  $scope.formulaFactura = null;
			  $scope.errorSecciones = {}
    		  $scope.listaColaboradoresPadre=null;
    		  
			  var errorCliente = document.getElementById("errorNominaCliente");
			  errorCliente.innerHTML = null;
			  var errorPrestadora = document.getElementById("errorNominaPrestadora");
			  errorPrestadora.innerHTML = null;
    		  $('#modalCrearNominas').modal('show');
			  
			}, function(data) {
				console.log("error modalCrearNomina--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
      }
      
      $scope.validaCreacionNomina = function (catNominaCliente) {
    	  
    	  $http.post(CONFIG.APIURL + "/ppp/nominas/validaCreacionNomina.json", catNominaCliente).then(function(response) {

    		  if(response.data != undefined && response.data != null){
    			  
    			  $scope.errorSecciones = response.data;
    			  
    			  if (response.data.esCliente == true){
	  					
  					var errorCliente = document.getElementById("errorNominaCliente");
  					errorCliente.innerHTML = '<div class="alert alert-danger"><H5><strong>CLIENTE (CRM)</strong></H5>'+ $scope.errorSecciones.moduloClienteError+'</div>';
  					
    			  }
    			  
    			  if (response.data.esPrestadoraServicio == true){
	  					    					
    					var errorPrestadora = document.getElementById("errorNominaPrestadora");
    					errorPrestadora.innerHTML = '<div class="alert alert-danger"><H5><strong>PRESTADORA FONDO (CRM)</strong></H5>'+ $scope.errorSecciones.moduloPresatdoraError+'</div>';
    					
      			  }
    		  }else{
    			  
  				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al validar las secciones para crear la nomina, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
    		  }
    		  
    		  

			}, function(data) {
				console.log("error modalCrearNomina--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al validar las secciones para crear la nomina, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
      }
      
      $scope.verificarCheckboxFinanciamiento = function(){
    	     
    	    if($scope.nominaComplementoDto.idNominaComplemento != undefined){
    	    	
    	    	if($scope.nominaComplementoDto.requiereFianciamiento === false){
    	    		
        	    	bootbox.confirm({
        				title : "Confirmar acci&oacute;n",
        				message : "¿Est\u00e1s seguro que deseas quitar el financiamiento de la n\u00f3mina ?",
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
        					if(result){
        						//Inicializa las variables de financiamiento ya que marco que no quiere financiamiento
        						$scope.nominaComplementoDto.montoFinanciamiento=null;
        		    	    	$scope.nominaComplementoDto.observaciones=null;
        		    	    	
        						$scope.guardarNominaComplementoSubmit();
        						
        						 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
        						
        					}else{
        						$scope.nominaComplementoDto.requiereFianciamiento=true;
        						$scope.$apply();
        					}
        					
        				}
        	    	});	
    	    	} else if($scope.nominaComplementoDto.requiereFianciamiento === true){
    	    		
    	    		var idNominaPPP = $scope.nominaComplementoDto.nominaDto.idNomina;
    	    		
    	      	  	$http.post(CONFIG.APIURL + "/ppp/nominas/existeInfoPasoCinco.json", idNominaPPP).then(function(response) {
    	      	  		
    	      	  		if(response.data){
    	        	    	bootbox.confirm({
    	        				title : "Confirmar acci&oacute;n",
    	        				message : '<div class="text-center">¿Est\u00e1s seguro que deseas requerir financiamiento?<br>Si lo hace, la información registrada en el paso 5 se eliminara.<br>¿Desea continuar? </div>',
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
    	        					if(result){
    	        						
    	        						$scope.updateDatosPasoCinco(idNominaPPP);	
    	        					}
    	        				}
    	        	    	});	
    	      	  		}

    	   			}, function(data) {
    	   				pinesNotifications.notify({
    	   			        title: 'Error',
    	   			        text: 'Ocurrio un error borrar la informacion del paso 5, favor de intentarlo nuevamente.',
    	   			        type: 'error'
    	   			      });
    	   			});
    	    		
    	    	}
    	    		
    	    }
    	    
    	}
      
      $scope.updateDatosPasoCinco = function(idNominaPPP){
    	  
    	  	$http.post(CONFIG.APIURL + "/ppp/nominas/updateDatosPasoCinco.json", idNominaPPP).then(function(response) {
      	  		
      	  		if(response.data){
	      	  		pinesNotifications.notify({
				        title: 'Datos adicionales',
				        text: 'La operación se realizó exitosamente ',
				        type: 'success'
				      });
	      	  		
	      	  	$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
	      	  	$scope.nominaComplementoDto.requiereFianciamiento = true;
	      	  		
      	  		}else{
	      	  		pinesNotifications.notify({
				        title: 'Datos adicionales',
				        text: 'Ocurrio un error al eliminar los datos del paso 5. ',
				        type: 'error'
				      });
      	  		}

   			}, function(data) {
   				pinesNotifications.notify({
   			        title: 'Datos adicionales',
   			        text: 'Ocurrio un error borrar la informacion del paso 5.',
   			        type: 'error'
   			      });
   			});
    	  
      }
      
      $scope.obtenerListDepositos = function(idNomina){   	  
  	  	$http.post(CONFIG.APIURL + "/ppp/nominas/listDepositos.json", idNomina).then(function(response) {
  	  	 $scope.gridListDepositos = response.data.gridListDepositos;
		  $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridListDepositos});
 			}, function(data) {
 				pinesNotifications.notify({
 			        title: 'Lista Depositos',
 			        text: 'Ocurrio un error al obtener los depósitos',
 			        type: 'error'
 			      });
 			});
  	  
    }
    
/////////////////////// Metodos para cargar documento de excel////////////////////////////////////////////////
      $scope.fileChanged = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp(".(xls|xlsx)$");

          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.xls y/o .xlsx</b>";
              alert($scope.mensaje);

          } else if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
              return;
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
                  $log.debug("Archivo cargado memoria");
                  $scope.data.archivo = reader.result;
                  $scope.data.nombreArchivo = lstArchivos[0].name;
                  $scope.data.tamanioArchivo = lstArchivos[0].size;
                  $scope.registrarDocumento();
                  $scope.mostrarBotonGuardarColaboradores =true;
              }
              reader.readAsDataURL(lstArchivos[0]);
          }

      };

      $scope.registrarDocumento = function () {
    	  $scope.data.esSindicato=$scope.nominaProcesoSeleccionada.clienteDto.estimbreSindicato;
       if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===24 && ($scope.totalNominaPPPColaboradores !==null || $scope.totalNominaPPPColaboradores !==0)){
      $scope.saldoAnterior= $scope.totalNominaPPPColaboradores;
        $scope.totalAcomplementar=$scope.totalAcomplementar+ $scope.saldoAnterior;
       }
          $http.post(CONFIG.APIURL + "/archivoNomina/createFile.json", $scope.data).then(function (response) {

          	if(response.data.errorCargalayout!=null){
          		$scope.cerrar();
          		$scope.dataExcel = {}
                $scope.headerTable = {};
                $scope.totalPPPColaboradores = 0;
            	$scope.totalNominaPPPColaboradores = 0;
            	$scope.mostrarBotonGuardarColaboradores = false;
            	$scope.notaGuardarColaboradores = false;
          		var mensajeError = response.data.errorCargalayout
          		return pinesNotifications.notify({
   			        title: 'Error',
   			        text: mensajeError,
   			        type: 'error'
   			      });

          		
          	}else{
                $scope.dataExcel = response.data;
                
                $scope.tableColaboradoresParams = new NgTableParams({page: 1, count: 10}, {data: $scope.dataExcel.contentRows});
              

               $scope.dataExcel.contentRows[0].totalColaboradores = response.data.contentRows.length;
                $scope.dataExcel.contentRows[0].totalMontoPPP =response.data.totalMonto;
//                	response.data.contentRows[response.data.contentRows.length-1].totalMontoPPP;

//                $scope.headerTable = $scope.dataExcel[0];
                
               	$scope.totalPPPColaboradores = response.data.contentRows.length;
            	  $scope.totalNominaPPPColaboradores = response.data.totalMonto;
                
            
            //valida monto acompletar
            	  if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===19 || $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===23 || $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===24){
                 var arregloExcel = response.data.contentRows;
                $scope.existeColaborador = false;
                var arreglo = $scope.listaColaboradoresPadre;
            for(var i = 0; i < arregloExcel.length; i++)
			     {
                 	for(var j=0;j<arreglo.length;j++)
				  {
                    if(response.data.contentRows[i].curp == arreglo[j].curp){
    				$scope.cerrar();
          			$scope.dataExcel = {}
               		$scope.headerTable = {};
                    $scope.totalPPPColaboradores = 0;
                     $scope.totalNominaPPPColaboradores = 0;
            	    $scope.mostrarBotonGuardarColaboradores = false;
            	    $scope.notaGuardarColaboradores = false;
            	
                    pinesNotifications.notify({
    	   			        title: 'Error',
    	   			        text: 'El colaborador con curp ' + response.data.contentRows[i].curp +' ya existe en la nómina que desea complementar',
    	   			        type: 'error'
    	   			      });
    	  		  }
            }
            }
            
      
              
                if($scope.totalNominaPPPColaboradores >  $scope.totalAcomplementar){
                $scope.cerrar();
          		$scope.dataExcel = {}
                $scope.headerTable = {};
                $scope.totalPPPColaboradores = 0;
            	$scope.totalNominaPPPColaboradores = 0;
            	$scope.mostrarBotonGuardarColaboradores = false;
            	$scope.notaGuardarColaboradores = false;
                pinesNotifications.notify({
    	   			        title: 'Error',
    	   			        text: 'El monto total del layout es mayor al saldo disponible de la nómina.',
    	   			        type: 'error'
    	   			      });
                }else{
				var resta=$scope.totalAcomplementar-$scope.totalNominaPPPColaboradores;
				$scope.totalAcomplementar=resta;
				}
                }else{
                                if(response.status = 200){
//                	$scope.cerrar();
                	pinesNotifications.notify({
     			        title: 'Archivo',
     			        text: 'La carga del layout se realiz&oacute; exitosamente',
     			        type: 'success'
     			      });
                	 $scope.notaGuardarColaboradores = true;
                }
                }
                


                ///Boton cerrar de modal de carga de documentos
                $scope.cerrar();
          	}
          	

          }, function (data) {
              $scope.dataExcel = undefined;
              console.log("error --> " + data);
          });
      }
      
      
      $scope.mostrarDialogo = function () {
          $('#cargarDocumento').modal('show');
      }

      $scope.cerrar = function () {
          $('#cargarDocumento').modal('hide');
      }
      
      $scope.mostrarDialogoAgregarColaborador = function () {
    	  $scope.colaborador={};
    	  $scope.indexColaboradorEditar = undefined;
    	  
    	  $scope.existeClabeColaborador = false;
    	  $scope.existeRfcColaborador = false;
    	  $scope.existeNssColaborador = false;
    	  $scope.existeCurpColaborador = false;
    	  
          $('#modalAgregarColaborador').modal('show');
      }

      $scope.cerrarDialogoAgregarColaborador = function () {
          $('#modalAgregarColaborador').modal('hide');
      }
      
      $scope.cargaInicialColaboradores = function (idNominaCliente) {
  	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", idNominaCliente).then(function(response) {
			  
  	  		 $scope.dataExcel.contentRows= response.data;
  	  	  $scope.tableColaboradoresParams = new NgTableParams({page: 1, count: 10}, {data: $scope.dataExcel.contentRows});
          
  	  		 if(response.data.length >= 1){
  	  		 $scope.colaboradoresCargados=true;
  	  		 $scope.totalNominaPPPColaboradores =  response.data[0].totalMontoPPP;
  	  		 $scope.totalPPPColaboradores =  response.data[0].totalColaboradores;
  	  		 }else{
  	  			$scope.totalNominaPPPColaboradores = 0;
  	  			$scope.totalPPPColaboradores = 0;
  	  		 $scope.colaboradoresCargados=false;
  	  		 }
  	  		$scope.notaGuardarColaboradores = false;
			}, function(data) {
				console.log("error modalCrearNomina--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 	
			
    }
    
  
    
    $scope.cargaInicialColaboradoresComplementos = function (idNominaCliente) {
  	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradoresComplemento.json", idNominaCliente).then(function(response) {
			  
  	  		 $scope.dataExcel.contentRows= response.data;
  	  		 if(response.data.length >= 1){
  	  		 $scope.totalNominaPPPColaboradores =  response.data[0].totalMontoPPP;
  	  		 $scope.totalPPPColaboradores =  response.data[0].totalColaboradores;
  	  		 }else{
  	  			$scope.totalNominaPPPColaboradores = 0;
  	  			$scope.totalPPPColaboradores = 0;
  	  		 }
  	  		$scope.notaGuardarColaboradores = false;
			}, function(data) {
			
			});
			}
    
    $scope.guardarColaboradores = function(listaColaboradores){
  	  for (var i = 0; i < listaColaboradores.length; i++) {
  		 if(listaColaboradores[i].estatus === 0){
  			 pinesNotifications.notify({
				        title: 'Error',
				        text: 'Existen colaboradores con estatus incorrecto, favor de verificar',
				        type: 'error'
				      });
  			 $scope.notaGuardarColaboradores = true;
  			 return;
  		 }
  	  }
  	    if($scope.idNomina==null){	
  	  		pinesNotifications.notify({
	 		title: 'Error',
	 		text: 'Guarde primero los datos generales de la nómina',
             type: 'error'
	 									      });
  	  
  	  }else{
  	  
  	  for (var i = 0; i < listaColaboradores.length; i++) {
  	
  		  listaColaboradores[i].idNomina = $scope.idNomina;
  	  }
  	
  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", $scope.idNomina).then(function(response) {
		  
	  		 if(response.data.length > 1){
	  			 bootbox
	 			.confirm({
	 				title : "Confirmar acci&oacute;n",
	 				message : "¿Est\u00e1s seguro que deseas actualizar los colaboradores guardados con anterioridad?",
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
	 					nominaService
	 								.guardarColaboradores(listaColaboradores ,function(response) {
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
	 									$scope.notaGuardarColaboradores = false;
	 									for (var i = 0; i < $scope.gridNominasProceso.length; i++) {
	 										if($scope.idNomina == $scope.gridNominasProceso[i].idNomina){
	 								  			$scope.gridNominasProceso[i].nominaClienteDto.totalColaboradores = listaColaboradores.length;
	 								  			$scope.gridNominasProceso[i].nominaClienteDto.montoTotalColaboradores = listaColaboradores[listaColaboradores.length-1].totalMontoPPP;
	 								  		  }
	 								  	  }
	 									$scope.mostrarBotonGuardarColaboradores = false;
	 									$scope.cargaInicialColaboradores($scope.idNomina);
	 										},
	 										function(response) {
	 											$log.error(response.status+ ' - '+ response.statusText);
	 											pinesNotifications.notify({
	 										        title: 'Error',
	 										        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
	 										        type: 'error'
	 										      });
	 											$scope.mostrarBotonGuardarColaboradores = true;
	 										});

	 					}
	 				}
	 			});
	  		 }else{
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
	 					nominaService.guardarColaboradores(listaColaboradores ,function(response) {
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
	 											
	 											$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
	 											
	 											}
	 									$scope.notaGuardarColaboradores = false;
	 									for (var i = 0; i < $scope.gridNominasProceso.length; i++) {
	 										if($scope.idNomina == $scope.gridNominasProceso[i].idNomina){
	 								  			$scope.gridNominasProceso[i].nominaClienteDto.totalColaboradores = listaColaboradores.length;
	 								  			$scope.gridNominasProceso[i].nominaClienteDto.montoTotalColaboradores = listaColaboradores[listaColaboradores.length-1].totalMontoPPP;
	 								  		  }
	 								  	  }
	 									$scope.mostrarBotonGuardarColaboradores = false;
	 									$scope.cargaInicialColaboradores($scope.idNomina);
	 										},
	 										function(response) {
	 											$log.error(response.status+ ' - '+ response.statusText);
	 											pinesNotifications.notify({
	 										        title: 'Error',
	 										        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
	 										        type: 'error'
	 										      });
	 											$scope.mostrarBotonGuardarColaboradores = true;
	 										});

	 					}
	 				}
	 			});
	  		 }
	  		
		}, function(data) {
			pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al guardar, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			$scope.mostrarBotonGuardarColaboradores = true;
		});
		} 
  	  
}
    
    $scope.mostrarDialogoColaboradorEstatus = function (colaborador) {
    	var colaboradorAux = colaborador;
        $http.post(CONFIG.APIURL + "/ppp/nominas/cargaEstatusColaborador.json", colaborador).then(function(response) {
			  var consecutivo = 0;
 	  		if(response.data.length >= 1){
			  $scope.estatusColaborador = response.data;
 	  		 
 	  		 for (var i = 0; i < $scope.estatusColaborador.length; i++) {
 	  			consecutivo = consecutivo + 1;
 	  			$scope.estatusColaborador[i].consecutivo = consecutivo;
 	    	  }
 	  		}
			}, function(data) {
				console.log("error modalCrearNomina--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
        
        $('#modalEstatus').modal('show');
    }
    
    $scope.agregarColaborador = function (colaborador) {
    	colaborador.isEditarColaborador = false;
   
    	if(colaborador.idPppColaborador != null || colaborador.idPppColaborador != undefined){
    		const arreglo = $scope.dataExcel.contentRows;
    		for (var i = 0; i < arreglo.length; i++) {
    			if(arreglo[i].idPppColaborador === colaborador.idPppColaborador){
    				var montoColaboradorGuardado = parseFloat(arreglo[i].montoPPP);
    				$scope.dataExcel.contentRows[i].nombre =  colaborador.nombre;
    				$scope.dataExcel.contentRows[i].apellidoPaterno =  colaborador.apellidoPaterno;
    				$scope.dataExcel.contentRows[i].apellidoMaterno =  colaborador.apellidoMaterno;
    		    	$scope.dataExcel.contentRows[i].rfc = colaborador.rfc;
    		    	$scope.dataExcel.contentRows[i].montoPPP = colaborador.montoPPP;
    		    	$scope.dataExcel.contentRows[i].clabe = colaborador.clabe;
    		    	$scope.dataExcel.contentRows[i].curp = colaborador.curp;
    		    	$scope.dataExcel.contentRows[i].nss = colaborador.nss;
    		    	$scope.dataExcel.contentRows[i].institucionContraparte = colaborador.institucionContraparte;
    		    	$scope.dataExcel.contentRows[i].correoElectronico = colaborador.correoElectronico;
    		    	$scope.dataExcel.contentRows[i].puesto = colaborador.puesto;
    		    	var montoColaborador = parseFloat(colaborador.montoPPP);
    		    	if(montoColaboradorGuardado != montoColaborador){
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP - montoColaboradorGuardado;
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP + montoColaborador;
    		    		$scope.totalPPPColaboradores = $scope.dataExcel.contentRows[0].totalMontoPPP
    		    	}
    			}
    		}
    		$scope.notaGuardarColaboradores = true;
        	$scope.colaborador={};
        	$('#modalAgregarColaborador').modal('hide');
    	}else{
    	
    	colaborador.estatus=1;
    	colaborador.idNomina = $scope.idNomina;
    	colaborador.descEstatus = 'Cargado layout';
    	if(($scope.totalNominaPPPColaboradores != null || $scope.totalNominaPPPColaboradores != undefined) && $scope.totalNominaPPPColaboradores > 0){
    	var totalNomina = $scope.totalNominaPPPColaboradores;
    	var montoColaborador = parseFloat(colaborador.montoPPP);
    	var suma = totalNomina + montoColaborador;
    	var totalColaboradores = $scope.totalPPPColaboradores;
    	$scope.totalPPPColaboradores = totalColaboradores + 1;
    	$scope.totalNominaPPPColaboradores = suma;
    	colaborador.totalMontoPPP = suma;
    	$scope.dataExcel.contentRows.push(colaborador);
    	$scope.dataExcel.contentRows[0].totalColaboradores = $scope.totalPPPColaboradores;
    	$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.totalNominaPPPColaboradores;
    	
    	
    	
    	}else{
    		$scope.totalPPPColaboradores = 1;
    		$scope.totalNominaPPPColaboradores = parseFloat(colaborador.montoPPP);
    		$scope.dataExcel.contentRows.push(colaborador);
    		
    		$scope.dataExcel.contentRows[0].totalColaboradores = 1;
        	$scope.dataExcel.contentRows[0].totalMontoPPP = parseFloat(colaborador.montoPPP);
    	}
    	$scope.notaGuardarColaboradores = true;
    	$scope.colaborador={};
    	$('#modalAgregarColaborador').modal('hide');
    	}
    }
    
    
    $scope.guardarColaborador = function (colaborador) {
    	if(colaborador.idPppColaborador != null || colaborador.idPppColaborador != undefined){
    		const arreglo = $scope.dataExcel.contentRows;
    		for (var i = 0; i < arreglo.length; i++) {
    			if(arreglo[i].idPppColaborador === colaborador.idPppColaborador){
    				var montoColaboradorGuardado = parseFloat(arreglo[i].montoPPP);
    				$scope.dataExcel.contentRows[i].nombre =  colaborador.nombre;
    				$scope.dataExcel.contentRows[i].apellidoPaterno =  colaborador.apellidoPaterno;
    				$scope.dataExcel.contentRows[i].apellidoMaterno =  colaborador.apellidoMaterno;
    		    	$scope.dataExcel.contentRows[i].rfc = colaborador.rfc;
    		    	$scope.dataExcel.contentRows[i].montoPPP = colaborador.montoPPP;
    		    	$scope.dataExcel.contentRows[i].codigoPostal= colaborador.codigoPostal;
    		    	$scope.dataExcel.contentRows[i].clabe = colaborador.clabe;
    		    	$scope.dataExcel.contentRows[i].curp = colaborador.curp;
    		    	$scope.dataExcel.contentRows[i].nss = colaborador.nss;
    		    	$scope.dataExcel.contentRows[i].institucionContraparte = colaborador.institucionContraparte;
    		    	$scope.dataExcel.contentRows[i].correoElectronico = colaborador.correoElectronico;
    		    	$scope.dataExcel.contentRows[i].idNomina = $scope.idNomina;
    		    	$scope.dataExcel.contentRows[i].isEditarColaborador = true;
    		    	var montoColaborador = parseFloat(colaborador.montoPPP);
    		    	if(montoColaboradorGuardado != montoColaborador){
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP - montoColaboradorGuardado;
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP + montoColaborador;
    		    		$scope.totalPPPColaboradores = $scope.dataExcel.contentRows[0].totalMontoPPP
    		    	}
    			}
    		}
    		$scope.notaGuardarColaboradores = false;
        	
        	$('#modalAgregarColaborador').modal('hide');
    	
    	
        	$http.post(CONFIG.APIURL + "/ppp/nominas/guardarColaborador.json", colaborador).then(function(response) {

    		 $scope.colaborador={};
    		 pinesNotifications.notify({
			        title: 'Mensaje',
			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
			        type: 'success'
			      });
    		 $scope.isEditarColaborador = false;
    		 $scope.cargaInicialColaboradores($scope.idNomina);
			}, function(data) {
				console.log("error al guardar colaboradores de editar--> " + data);
			}); 
    	$scope.colaborador={};
    	$('#modalAgregarColaborador').modal('hide');
    	}else{
    		colaborador.idNomina = $scope.idNomina;
    		colaborador.isEditarColaborador = false;
    		$scope.notaGuardarColaboradores = false;
        	
        	$http.post(CONFIG.APIURL + "/ppp/nominas/guardarColaboradorDirecto.json", colaborador).then(function(response) {

    		 $scope.colaborador={};
    		 pinesNotifications.notify({
			        title: 'Mensaje',
			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
			        type: 'success'
			      });
    		 var montoColaborador = parseFloat(colaborador.montoPPP);
    		 var totalColaboradores = $scope.totalPPPColaboradores;
    	    	$scope.totalPPPColaboradores = totalColaboradores + 1;
    		 
    		
	    	 
    		 $scope.cargaInicialColaboradores($scope.idNomina);
    		 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
       		 $scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
//       		 $scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP + montoColaborador;
//	    	 $scope.dataExcel.contentRows[0].totalColaboradores = $scope.totalPPPColaboradores;
//	    	 $scope.totalPPPColaboradores = $scope.dataExcel.contentRows[0].totalMontoPPP;
			}, function(data) {
				console.log("error al guardar colaborador directo--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al guardar los datos del colaborador, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
			
    	$scope.colaborador={};
    	$('#modalAgregarColaborador').modal('hide');
    	}
    }
    
    /**/
     
    $scope.guardarColaboradorComplementaria = function (colaborador) {
     $scope.validaMonto=false;
    		if($scope.idNomina==null){	
  	  		pinesNotifications.notify({
	 		title: 'Error',
	 		text: 'Guarde primero los datos generales de la nómina',
             type: 'error'
	 									      });
		}else{
    	if(colaborador.idPppColaborador != null || colaborador.idPppColaborador != undefined){
    		const arreglo = $scope.dataExcel.contentRows;
    		for (var i = 0; i < arreglo.length; i++) {
    			if(arreglo[i].idPppColaborador === colaborador.idPppColaborador){
    				var montoColaboradorGuardado = parseFloat(arreglo[i].montoPPP);
    				$scope.dataExcel.contentRows[i].nombre =  colaborador.nombre;
    				$scope.dataExcel.contentRows[i].apellidoPaterno =  colaborador.apellidoPaterno;
    				$scope.dataExcel.contentRows[i].apellidoMaterno =  colaborador.apellidoMaterno;
    		    	$scope.dataExcel.contentRows[i].rfc = colaborador.rfc;
    		    	$scope.dataExcel.contentRows[i].montoPPP = colaborador.montoPPP;
    		    	$scope.dataExcel.contentRows[i].codigoPostal= colaborador.codigoPostal;
    		    	$scope.dataExcel.contentRows[i].clabe = colaborador.clabe;
    		    	$scope.dataExcel.contentRows[i].curp = colaborador.curp;
    		    	$scope.dataExcel.contentRows[i].nss = colaborador.nss;
    		    	$scope.dataExcel.contentRows[i].institucionContraparte = colaborador.institucionContraparte;
    		    	$scope.dataExcel.contentRows[i].correoElectronico = colaborador.correoElectronico;
    		    	$scope.dataExcel.contentRows[i].idNomina = $scope.idNomina;
    		    	$scope.dataExcel.contentRows[i].isEditarColaborador = true;
    		    	var montoColaborador = parseFloat(colaborador.montoPPP);
    		    	if(montoColaboradorGuardado != montoColaborador){
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP - montoColaboradorGuardado;
    		    		$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP + montoColaborador;
    		    		$scope.totalPPPColaboradores = $scope.dataExcel.contentRows[0].totalMontoPPP
    		    	    
    		    	    $scope.validaMonto=true;
    		    	    $scope.totalAcomplementar=$scope.totalAcomplementar+montoColaboradorGuardado;
    		    	    $scope.totalAcomplementar=$scope.totalAcomplementar-montoColaborador;
    		    	}
    			}
    		}
    		$scope.notaGuardarColaboradores = false;
    		
    		if($scope.validaMonto==false){
    		 var totalNominaP =$scope.totalPPPColaboradores;
    	     var totalRestaAcompletar = $scope.totalAcomplementar;
    	     var restaCompletar = totalRestaAcompletar-totalNominaP ;
    	    $scope.totalAcomplementar = restaCompletar;
        	}
        	$scope.validaMonto=false;
        	
        	$('#modalAgregarColaborador').modal('hide');
        	
    	    if($scope.totalAcomplementar<0){
    	    	pinesNotifications.notify({
			        title: 'Error',
			        text: 'El monto disponible es menor al monto de los colaboradores que desea agregar.',
			        type: 'error'
			      });
    	    }  
    	    else{
    	
        	$http.post(CONFIG.APIURL + "/ppp/nominas/guardarColaborador.json", colaborador).then(function(response) {

    		 $scope.colaborador={};
    		 pinesNotifications.notify({
			        title: 'Mensaje',
			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
			        type: 'success'
			      });
    		 $scope.isEditarColaborador = false;
    		 $scope.cargaInicialColaboradores($scope.idNomina);
			}, function(data) {
				console.log("error al guardar colaboradores de editar--> " + data);
			}); 
    	$scope.colaborador={};
    	$('#modalAgregarColaborador').modal('hide');
    	}
    	}else{
    		colaborador.idNomina = $scope.idNomina;
    		//colaborador.idNomina = $scope.idNominaComplementaria;
    		colaborador.isEditarColaborador = false;
    		$scope.notaGuardarColaboradores = false;
    		

    		$http.post(CONFIG.APIURL + "/ppp/nominas/guardarColaboradorDirecto.json", colaborador).then(function(response) {

    		 $scope.colaborador={};
    		 pinesNotifications.notify({
			        title: 'Mensaje',
			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
			        type: 'success'
			      });
    		 var montoColaborador = parseFloat(colaborador.montoPPP);
    		 var totalColaboradores = $scope.totalPPPColaboradores;
    	    	$scope.totalPPPColaboradores = totalColaboradores + 1;
    		   	 
    		 $scope.cargaInicialColaboradores($scope.idNomina);
    	//	 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
       	//	 $scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
       		 
       		
	    //     $scope.cargaInicialColaboradoresComplementos($scope.idNomina);
	          $scope.getDatosNominaByIdNominaComplementaria($scope.nominaProcesoSeleccionada);
             $scope.listaNominaAcomplementar($scope.nominaProcesoSeleccionada.nominaClienteDto);
			
       		 
       		 
//       		 $scope.dataExcel.contentRows[0].totalMontoPPP = $scope.dataExcel.contentRows[0].totalMontoPPP + montoColaborador;
//	    	 $scope.dataExcel.contentRows[0].totalColaboradores = $scope.totalPPPColaboradores;
//	    	 $scope.totalPPPColaboradores = $scope.dataExcel.contentRows[0].totalMontoPPP;
			// var totalNominaP = $scope.totalNominaPPPColaboradores;
			 var totalNominaP =montoColaborador;
    	     var totalRestaAcompletar = $scope.totalAcomplementar;
    	     var restaCompletar = totalRestaAcompletar-totalNominaP ;
    	    $scope.totalAcomplementar = restaCompletar;
			
			}, function(data) {
				console.log("error al guardar colaborador directo--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al guardar los datos del colaborador, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
			}
    	$scope.colaborador={};
    	$('#modalAgregarColaborador').modal('hide');
    	}
    }
    
    $scope.validaMonto = function (monto) {
    if($scope.totalAcomplementar<monto){
    $scope.validaMontoPPP = true;
     $scope.mensaje = "El monto es mayor al total a Acomplementar</b>";            
     pinesNotifications.notify({
				        title: 'Error',
				        text: $scope.mensaje,
				        type: 'error'
				      });
    		
    		}else{
    		$scope.validaMontoPPP = false;
    		
    		}
    }
    
    $scope.eliminarColaborador = function (colaborador) {
    	const arreglo = $scope.dataExcel.contentRows;
    	for (var i = 0; i < arreglo.length; i++) {
    		if(colaborador.curp == arreglo[i].curp){
				arreglo.splice(i, 1);
	  		  }
	  	  }
    	
    	$scope.dataExcel.contentRows = arreglo;
    	
    	var totalNomina = $scope.totalNominaPPPColaboradores;
    	var montoColaborador = parseFloat(colaborador.montoPPP);
    	var resta = totalNomina - montoColaborador;
    	var totalColaboradores = $scope.totalPPPColaboradores;
    	$scope.totalPPPColaboradores = totalColaboradores - 1;
    	$scope.totalNominaPPPColaboradores = resta;
    	//para nominas complementarias
    	var montoCompletar=$scope.totalAcomplementar + montoColaborador;	
			$scope.totalAcomplementar=montoCompletar;
    	$scope.notaGuardarColaboradores = true;
    	if($scope.totalPPPColaboradores == 0){
    		colaborador.idNomina = $scope.idNomina;
    		$http.post(CONFIG.APIURL + "/ppp/nominas/eliminarColaboradores.json", colaborador).then(function(response) {
       		 console.log("eliminar colaboradores ok--> " + response);
       		 $scope.colaborador={};
       		 pinesNotifications.notify({
   			        title: 'Mensaje',
   			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
   			        type: 'success'
   			      });
   			      
   			      	 $scope.notaGuardarColaboradores = false;
       		         $scope.cargaInicialColaboradores($scope.idNomina);
   			      if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 23 ){
              $scope.getDatosNominaByIdNominaComplementaria($scope.nominaProcesoSeleccionada);
             $scope.listaNominaAcomplementar($scope.nominaProcesoSeleccionada.nominaClienteDto);			
   			      }else{  	
       		 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
       		 $scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
       		 }
   			}, function(response) {
   				console.log("error al eliminar colaboradores--> " + response);
   			}); 
    		
    	}else{
    		colaborador.idNomina = $scope.idNomina;
    		$http.post(CONFIG.APIURL + "/ppp/nominas/eliminarColaborador.json", colaborador).then(function(response) {
       		 console.log("eliminar colaboradores ok--> " + response);
       		 $scope.colaborador={};
       		 pinesNotifications.notify({
   			        title: 'Mensaje',
   			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
   			        type: 'success'
   			      });
       		 $scope.notaGuardarColaboradores = false;
       		 $scope.cargaInicialColaboradores($scope.idNomina);
       		
       		if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 23){
              $scope.getDatosNominaByIdNominaComplementaria($scope.nominaProcesoSeleccionada);
             $scope.listaNominaAcomplementar($scope.nominaProcesoSeleccionada.nominaClienteDto);			
   			      }else{  	
       		 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
       		 $scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
       		 }
       	
    		}, function(response) {
   				console.log("error al eliminar colaboradores--> " + response);
   			}); 
    		$scope.dataExcel.contentRows[0].totalColaboradores = $scope.totalPPPColaboradores;
        	$scope.dataExcel.contentRows[0].totalMontoPPP = $scope.totalNominaPPPColaboradores;
    	}
    }
    
    $scope.editarColaborador = function (colaborador , indice) {
    	$scope.indexColaboradorEditar = indice;
    	
    	$scope.colaborador.nombre = colaborador.nombre;
    	$scope.colaborador.apellidoPaterno = colaborador.apellidoPaterno;
    	$scope.colaborador.apellidoMaterno = colaborador.apellidoMaterno;
    	$scope.colaborador.rfc = colaborador.rfc;
    	$scope.colaborador.codigoPostal= colaborador.codigoPostal;
    	$scope.colaborador.montoPPP = colaborador.montoPPP;
    	$scope.colaborador.clabe = colaborador.clabe==null?colaborador.numeroCuenta:colaborador.clabe;
    	$scope.colaborador.idPppColaborador = colaborador.idPppColaborador;
    	$scope.colaborador.curp = colaborador.curp;
    	$scope.colaborador.nss = colaborador.nss;
    	$scope.colaborador.institucionContraparte = colaborador.institucionContraparte;
    	$scope.colaborador.correoElectronico = colaborador.correoElectronico;
    	$scope.colaborador.idPppColaboradorClabeInterbancaria = colaborador.idPppColaboradorClabeInterbancaria;
    	$scope.colaborador.idPpppColaboradorStp = colaborador.idPpppColaboradorStp;
    	$scope.colaborador.idEstatus = colaborador.idEstatus;
    	if(colaborador.idEstatus == 1 || colaborador.idEstatus == 5
    			|| colaborador.idEstatus == 6){
    		$scope.isEditarColaborador = false;
    	}else{
    	$scope.isEditarColaborador = true;
    	}
    	
    	$scope.existeClabeColaborador = false;
  	  	$scope.existeRfcColaborador = false;
  	  	$scope.existeNssColaborador = false;
  	  	$scope.existeCurpColaborador = false
  	  	
    	$('#modalAgregarColaborador').modal('show');
    }
    
    $scope.bloquearColaborador = function (colaborador) {
    	bootbox
			.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas bloquear al colaborador?",
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
					nominaService
								.bloquearColaborador(colaborador ,function(response) {
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
									
									$scope.cargaInicialColaboradores($scope.idNomina);
									
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
    
    $scope.desbloquearColaborador = function (colaborador) {
    	bootbox
			.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas desbloquear al colaborador?",
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
					nominaService
								.desbloquearColaborador(colaborador ,function(response) {
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
									
									$scope.cargaInicialColaboradores($scope.idNomina);
									
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
    
    $scope.verificarDatosColaboradorRfc = function (colaborador) {
    	$scope.existeRfcColaborador = false;
    	const arreglo = $scope.dataExcel.contentRows;
    	for (var i = 0; i < arreglo.length; i++) {
    		
    		if($scope.indexColaboradorEditar=== undefined){
    			if(colaborador.rfc == arreglo[i].rfc){
    				$scope.existeRfcColaborador = true;
    	  		  }
    		}else{
    			//Actualiza colaborador Se valida que no sea el mismo que se esta consultado a si mismo
    			if($scope.indexColaboradorEditar !== i && colaborador.rfc == arreglo[i].rfc){
    				$scope.existeRfcColaborador = true;
    	  		}
    		}
    		
	  	  }
    }
    
    $scope.verificarDatosColaboradorCurp = function (colaborador) {
    	$scope.existeCurpColaborador = false;
    	const arreglo = $scope.dataExcel.contentRows;
    	for (var i = 0; i < arreglo.length; i++) {
    		
    		if($scope.indexColaboradorEditar=== undefined){
    			if(colaborador.curp == arreglo[i].curp){
    				$scope.existeCurpColaborador = true;
    	  		  }
    		}else{
    			//Actualiza colaborador Se valida que no sea el mismo que se esta consultado a si mismo
    			if($scope.indexColaboradorEditar !== i && colaborador.curp == arreglo[i].curp){
    				$scope.existeCurpColaborador = true;
    	  		  }
    		}
			
	  	  }
	  	  
	  	  	if($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===23 || $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral ===24){
	  	  	$scope.verificarColaboradorCurpEnNominaPadre(colaborador);
	  	  	}
	  	  
    }
    
    
    	 $scope.verificarColaboradorCurpEnNominaPadre = function (colaborador) {
 				$scope.existeCurpColaborador = false;
 				const arreglo = $scope.listaColaboradoresPadre;
					 for (var i = 0; i < arreglo.length; i++) {
 						if(colaborador.curp == arreglo[i].curp ){
    							$scope.existeCurpColaborador = true;
    	  		 	 }
 					}
 
 						}
    
    $scope.verificarDatosColaboradorNss = function (colaborador) {
    	$scope.existeNssColaborador = false;
    	const arreglo = $scope.dataExcel.contentRows;
    	for (var i = 0; i < arreglo.length; i++) {
    		if($scope.indexColaboradorEditar=== undefined){
    			if(colaborador.nss == arreglo[i].nss){
    				$scope.existeNssColaborador = true;
    	  		  }
    		}else{
    			//Actualiza colaborador Se valida que no sea el mismo que se esta consultado a si mismo
    			if($scope.indexColaboradorEditar !== i && colaborador.nss == arreglo[i].nss){
    				$scope.existeNssColaborador = true;
    	  		  }	
    		}
			
	  	  }
    }
    
    $scope.verificarDatosColaboradorClabe = function (colaborador) {
    	$scope.existeClabeColaborador = false;
    	const arreglo = $scope.dataExcel.contentRows;
    	for (var i = 0; i < arreglo.length; i++) {
    		//Valor nuevo
    		if($scope.indexColaboradorEditar=== undefined){
    			if(colaborador.clabe == arreglo[i].clabe){
    				$scope.existeClabeColaborador = true;
    	  		  }
    		}else{
    			//Actualiza colaborador Se valida que no sea el mismo que se esta consultado a si mismo
    			if($scope.indexColaboradorEditar !== i && colaborador.clabe == arreglo[i].clabe){
    				$scope.existeClabeColaborador = true;
    	  		  }
    		}
    		
			
	  	  }
    }
        
      $scope.calcularImporte = function(){
    	  
    	  //parseFloat(number).toFixed(2);
          if( angular.isNumber($scope.concepto.cantidad)
            && angular.isNumber($scope.concepto.precioUnitario)) {

            $scope.concepto.importe =
              $scope.concepto.cantidad * $scope.concepto.precioUnitario;
            
            $scope.cambiarIvaTrasladado16();
            $scope.cambiarIvaRetenido6();
            
          }

        };
      
      $scope.agregarConcepto = function(formularioConcepto){
    	  
    	  if($scope.dataExcel == null || $scope.dataExcel ==undefined || $scope.dataExcel.contentRows[0] == undefined || 
    			  $scope.dataExcel.contentRows[0].totalMontoPPP === undefined || $scope.dataExcel.contentRows[0].totalMontoPPP <=0 ){

    		 return pinesNotifications.notify({
			        title: 'Error',
			        text: 'Para guardar el concepto de facturación, es necesario registrar colaboradores en el paso 2',
			        type: 'error'
			      });
    	  }
    	  
    	  if($scope.notaGuardarColaboradores){
     		 return pinesNotifications.notify({
			        title: 'Error',
			        text: 'Es muy importante que presione el botón Guardar colaboradores, para conservar los cambios realizados.',
			        type: 'error'
			      });
    	  }
    	  
    	  if(formularioConcepto.$invalid){
    		  
        	  pinesNotifications.notify({
    			        title: 'Concepto',
    			        text: 'El formulario tiene un error',
    			        type: 'error'
    			      });
    	  }else{
    		  
    		  var facturaDto = {};
    		  
    		  facturaDto = angular.copy($scope.factura);
    		  facturaDto.concepto = angular.copy($scope.concepto);
    		  facturaDto.nomina= angular.copy($scope.nominaProcesoSeleccionada);
    		  
    		  $scope.totales = {};
        	  $scope.totales.sumaImpuestos = 0;
        	  
        	  $scope.totales.subtotal = 0;
        	  $scope.totales.total = 0;
        	  var conceptosTemporal = angular.copy($scope.conceptos);
        	  
        	  if(conceptosTemporal != undefined && conceptosTemporal.length >= 1 && $scope.concepto.idConcepto >=1){
        		  
        		  angular.forEach(conceptosTemporal,function (value,key) {
                	  if(value.idConcepto === $scope.concepto.idConcepto){
                		  conceptosTemporal[key] = $scope.concepto;
                	  } 	  
                  });
        		  
        	  }else{
        		  //Se agrega ya que es el primer concepto
        		  conceptosTemporal.push(angular.copy($scope.concepto));
        	  }
              
    		  angular.forEach(conceptosTemporal,function (value,key) {
    			  
        		  if(value.ivaTrasladado16 === 'SI'){	 
            		  $scope.totales.sumaImpuestos = parseFloat(parseFloat( parseFloat( $scope.totales.sumaImpuestos)  + parseFloat(value.ivaTrasladado16Monto)).toFixed(2));
            	  }
            	              	  
            	  $scope.totales.subtotal = parseFloat(parseFloat( parseFloat( $scope.totales.subtotal)  + parseFloat(value.importe)).toFixed(2));	            	  
              });
    		  

              $scope.totales.total = parseFloat(parseFloat( parseFloat( $scope.totales.subtotal)  + parseFloat($scope.totales.sumaImpuestos) ).toFixed(2));
              // Se agregan los totales para la generacion de los reportes de manera correcta
              $scope.totales.ivaComercial = angular.copy($scope.formulaFactura.ivaComercial);
              $scope.totales.honorario = angular.copy($scope.formulaFactura.honorarioPPP);
              $scope.totales.montoTotalHonorario = angular.copy($scope.montosFactura.honorario);
  			  $scope.totales.montoTotalColaboradoresPPP = angular.copy($scope.totalNominaPPPColaboradores);
			
              facturaDto.totales = angular.copy($scope.totales);
                
    		  $http.post(CONFIG.APIURL + "/ppp/nominas/guardarConcepto.json", facturaDto).then(function(response) {
    			  
    		 //   $scope.obtenerListDepositos($scope.nominaProcesoSeleccionada.idNomina);
    			  $scope.getDatoFactura($scope.nominaProcesoSeleccionada.idNomina , $scope.nominaProcesoSeleccionada.nominaClienteDto.idNominaCliente);
    			  
            	  pinesNotifications.notify({
        			        title: 'Concepto',
        			        text: 'Se ha cargado el concepto exitosamente',
        			        type: 'success'
        			      });
            	  
            	  $scope.limpiarConcepto(formularioConcepto);
            	  
            	  $scope.mostrarAccionesPaso4 = false;
            	  
    			}, function(data) {
    				console.log("error modalCrearNomina--> " + data);
    				pinesNotifications.notify({
    			        title: 'Error',
    			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
    			        type: 'error'
    			      });
    			}); 
    		  
    		  
    		  
    	  }
    	  
    	  
    	  
      }
      
      
      $scope.limpiarConcepto = function(formularioConcepto){
    	  $scope.concepto = {};
    	  if(formularioConcepto){
    		  formularioConcepto.$setPristine();
        	  formularioConcepto.$setUntouched();
//        	  formularioConcepto.$submitted = false;
//        	  $scope.$apply();
    	  }
    	  
      }
      
      $scope.cancelarUpdateConcepto = function(formularioConcepto){
    	  $scope.concepto = {};
    	  if(formularioConcepto){
    		  formularioConcepto.$setPristine();
        	  formularioConcepto.$setUntouched();
        	  $scope.mostrarAccionesPaso4=false;
    	  }
    	  
      }
      
      $scope.calcularTotales = function(){
    	  $scope.totales = {};
    	  $scope.totales.retenciones = 0;
    	  $scope.totales.trasladados = 0;
    	  
    	  $scope.totales.subTotal = 0;
    	  $scope.totales.total = 0;
    	  
          angular.forEach($scope.conceptos,function (value,key) {
        	  
        	  if(value.ivaTrasladado16 === 'SI'){	 
        		  $scope.trasladados = parseFloat( parseFloat( $scope.totales.trasladados)  + parseFloat(value.ivaTrasladado16Monto)).toFixed(2);
        	  }
        	  
        	  if(value.ivaRetenido6 === 'SI'){
        		  $scope.totales.retenciones = parseFloat( parseFloat( $scope.totales.retenciones)  + parseFloat(value.ivaRetenido6Monto)).toFixed(2);
        	  }
        	  
        	  $scope.totales.subTotal = parseFloat( parseFloat( $scope.totales.subTotal)  + parseFloat(value.importe)).toFixed(2);
        	          	  
          });
          
          $scope.totales.total = parseFloat( parseFloat( $scope.totales.subTotal)  + parseFloat($scope.totales.trasladados) - parseFloat($scope.totales.retenciones)).toFixed(2);
          
          
      }
      
      $scope.calcularSubYTotal = function(){
    	  
          $scope.concepto.subtotal = $scope.concepto.importe + $scope.concepto.sumaImpuestos;

          $scope.concepto.total = $scope.concepto.subtotal - $scope.concepto.sumaRetenciones;

        };
        
        
        $scope.cambiarIvaTrasladado16 = function(){
        	
        	if($scope.concepto.ivaTrasladado16){
        		$scope.concepto.ivaTrasladado16Monto = $scope.concepto.importe * parseFloat(0.16).toFixed(2);	
        	}else{
        		$scope.concepto.ivaTrasladado16Monto = 0;
        	}	
        }
        
		$scope.cambiarIvaRetenido6 = function(){

			if($scope.concepto.ivaRetenido6){
				$scope.concepto.ivaRetenido6Monto = $scope.concepto.importe * parseFloat(0.06).toFixed(2);	
			}else{
				$scope.concepto.ivaRetenido6Monto = 0;
			}
			
		}
		
		$scope.editarConcepto = function(itemConcepto){
			$scope.concepto = angular.copy(itemConcepto);
			
			$scope.mostrarAccionesPaso4 = true;
			
			pinesNotifications.notify({
		        title: 'Concepto',
		        text: 'Se ha cargado el concepto exitosamente para su edici&oacute;n',
		        type: 'success'
		      });
		}
		
		
		$scope.eliminarConcepto = function(itemConcepto){
			var conceptoEliminar = angular.copy(itemConcepto);
			
			bootbox.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas eliminar el concepto de facturaci\u00f3n?",
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
						
			    		  var conceptoFacturaDto = {};
			    		  conceptoFacturaDto.idConceptoFactura = conceptoEliminar.idConcepto;
			    		  conceptoFacturaDto.idPPPNominaFactura= $scope.factura.idPPPNominaFactura;
			        	  
			        	  
						
		    		  $http.post(CONFIG.APIURL + "/ppp/nominas/eliminarConcepto.json", conceptoFacturaDto).then(function(response) {
		    			  
		    			  if(response){
		    				  
		    				  
		    				  $scope.getDatoFactura($scope.nominaProcesoSeleccionada.idNomina , $scope.nominaProcesoSeleccionada.nominaClienteDto.idNominaCliente);
		    				  $scope.conceptos =[];
		    				  $scope.totales = {};
			    			  
			    			  pinesNotifications.notify({
							        title: 'Concepto',
							        text: 'Se ha eliminado el concepto exitosamente',
							        type: 'success'
							      });
		    			  }

		    			  
		            	  
		            	  
		    			}, function(data) {
		    				console.log("error modalCrearNomina--> " + data);
		    				pinesNotifications.notify({
		    			        title: 'Error',
		    			        text: 'Ocurrio un error al eliminar el concepto de facturaci&oacute;n, favor de intentarlo nuevamente.',
		    			        type: 'error'
		    			      });
		    			}); 

					}
				}});
		}
		
      /// Termina Metodos para cargar documento de excel
		
		  $scope.cargaInicialDocumentosPppNomina = function (idPppNomina){
			  
			  $http.post(CONFIG.APIURL + "/ppp/nominas/obtenerDocumentosPppNomina.json", idPppNomina).then(
						function(data) {
							$scope.documentosPppNomina = data.data;
						}, function(data) {
							console.log("error --> " + data);
						});
		  }
		  
		  $scope.mostrarModalDocumentoPppNominas = function(itemDefinicionDocumento) {

			  	$scope.archivoPrestadora = {};
			  	$scope.tipoDoc = angular.lowercase(itemDefinicionDocumento.definicion.tiposDocumentos);
//			  	itemDefinicionDocumento.idClienteRepresentanteLegal = $scope.representanteLegalDto.idGenericoRepresentanteLegal;
			  							
			  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
			  	
		        $('#agregarDocumentoNominaPpp').modal('show');
		        
		        var fileElement = angular.element('#file_nominappp');
		        angular.element(fileElement).val(null);

		    };
		    
	      	$scope.guardarDocumentoNominaPpp = function(){
		    	  
		    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined || 
		    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){
		    		  
		    		  pinesNotifications.notify({
					        title: 'Error',
					        text: 'Es necesario adjuntar el documento',
					        type: 'error'
					      });
		    		  
		    		  return;
		    	  }
		    	  
		    	  $scope.itemDefinicionDocumento.idPppNomina = $scope.factura.idPPPNomina;
		    	  $http.post(CONFIG.APIURL + "/ppp/nominas/guardarDocumentosPppNomina.json", $scope.itemDefinicionDocumento).then(
		                  function (response) {
		                	  $log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });
								
								$('#agregarDocumentoNominaPpp').modal('hide');
								var fileElement = angular.element('#file_nominappp');
								angular.element(fileElement).val(null);
					  		       
								$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
								$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
								
														
		                  },
		                  function (data) {
		                	  $log.error(data.status+ ' - '+ data.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
							        type: 'error'
							      });
								
								var fileElement = angular.element('#file_nominappp');
				  		        angular.element(fileElement).val(null);
				  		       $('#agregarDocumentoNominaPpp').modal('hide');
		                  });
		      	}
	      	
			  $scope.fileChangedDocNominaPpp = function (documento) {
				  
		          var lstArchivos = documento.files;
		          var val = lstArchivos[0].name.toLowerCase();
		          
		          
		        
		          var regex = new RegExp("(.*?)\.(pdf|xml)$");

		          if (!(regex.test(val))) {
		              $(this).val('');
		              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.pdf, .docx, .png, y/o .jpg </b>";
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
		                  
		                  $scope.itemDefinicionDocumento.documentoNuevo = documento;
		              }
		              
		              reader.readAsDataURL(lstArchivos[0]);
		              
		          }

		      };
		      
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
		      	
				$scope.eliminarDocumentoNominaPpp = function(documento) {
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
									
							    	  $http.post(CONFIG.APIURL + "/ppp/nominas/eliminarDocumentosPppNomina.json", documento).then(
							                  function (response) {
							                	  $log.debug('ok');
													pinesNotifications.notify({
												        title: 'Mensaje',
												        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
												        type: 'success'
												      });
													
													
													$('#agregarDocumentoNominaPpp').modal('hide');
													var fileElement = angular.element('#file_nominappp');
													angular.element(fileElement).val(null);
										  		       
													$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
													$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
													
							                  },
							                  function (data) {
							                	  $log.error(data.status+ ' - '+ data.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al eliminar el documento, favor de intentarlo más tarde.',
												        type: 'error'
												      });
													
													$('#agregarDocumentoNominaPpp').modal('hide');
													var fileElement = angular.element('#file_nominappp');
													angular.element(fileElement).val(null);
										  		       
													$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
													$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
													
							                  });
								}
							}				  
					  });
				}
				
				
				
				$scope.cargarDatosDespuesDeGuardarComplemento = function(claveNomina) {
		        	nominaService.cargarDatosDespuesDeGuardarComplemento(claveNomina, function(response) {
		        		
		          		 $scope.getDatosNominaByIdNomina(response.data);
		          		 $scope.nominaCliente = response.data.nominaClienteDto;
//		          		 $scope.listaNominaEnProceso($scope.nominaCliente);
		    			},
		    			function(response) {
		    				$log.error(response.status+ ' - '+ response.statusText);
		    				pinesNotifications.notify({
		    			        title: 'Error',
		    			        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
		    			        type: 'error'
		    			      });
		    				
		    				var fileElement = angular.element('#file_nominappp');
		    		        angular.element(fileElement).val(null);
		    		       $('#agregarDocumentoNominaPpp').modal('hide');

		    			});
				}
				
				$scope.enviarNominaFacturaCargada = function() {
					  bootbox.confirm({
						  title : "Confirmar acci&oacute;n",
							message : "¿Est\u00e1s seguro de enviar a tesoreria?",
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
									
							    	  $http.post(CONFIG.APIURL + "/ppp/nominas/cambiaEstatusNomina.json", $scope.nominaProcesoSeleccionada.idNomina).then(
							                  function (response) {
							                	  $log.debug('ok');
													pinesNotifications.notify({
												        title: 'Mensaje',
												        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
												        type: 'success'
												      });
										  		       
													$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
													$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
													
							                  },
							                  function (data) {
							                	  $log.error(data.status+ ' - '+ data.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
												        type: 'error'
												      });
													

										  		       
													$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
													$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
													
							                  });
								}
							}				  
					  });
				}
				
				
				$scope.vistaPreviaFactura = function() {	
					if($scope.totales.total != undefined && $scope.totales.total > 0){
						
						$http.post(CONFIG.APIURL + "/ppp/nominas/preFactura.json", $scope.nominaProcesoSeleccionada).then(
				                  function (response) {
				                	  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
							  		    
										var modal = document.getElementById("pdfPrefactura");
										modal.innerHTML = '<object type="text/html" style="width: 100%; height: 500px;" data=' + response.data.mimeType + response.data.documentoBase64 + '></object>';
										$('#verDocumentoFactura').modal('show');
																			
				                  },
				                  function (data) {
				                	  $log.error(data.status+ ' - '+ data.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
									        type: 'error'
									      });
	  
										$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
										$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
										
				                  });
					}else{
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Es necesario registrar un concepto de facturación',
					        type: 'error'
					      });
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
									$scope.nominaProcesoSeleccionada;
									$('#modalPacTimbrado').modal('show');
								}
							}				  
					  });
				}
				
				$scope.generarFactura = function(nominaProcesoSeleccionada, pacForm) {
					
					$('#modalPacTimbrado').modal('hide');

					nominaProcesoSeleccionada.catPacTimbrado = $scope.pacTimbrado.catPacTimbrado;
					
					$http.post(CONFIG.APIURL + "/ppp/nominas/generarFactura.json", nominaProcesoSeleccionada).then(
			                  function (response) {
			                	  
			                	  if(response.data.responseCode ==="200"){
			                		  
			          	    		nominaService.getDatosFacturaByIdNomina(nominaProcesoSeleccionada.idNomina, function(response) {

			        		    		if(response.data!=undefined && response.data !=null && response.data !="" && response.data !=undefined){
			        		    				
			        		    			$scope.factura = response.data;
			        		    			
			        		    			if($scope.factura !=null 
					                				  && $scope.factura.idCmsXmlFactura >= 1 
					                				  && $scope.factura.idCmsPdfFactura >= 1
					                				//  && $scope.factura.idCMS >= 1 
					                				 // && $scope.factura.montoComprobantePago >= 1
					                				  && ($scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral >= 1)
					                				  && ($scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral >=1)){
			        		    				if($scope.factura.idDeposito>=1){
			        		    				nominaProcesoSeleccionada.idDeposito=$scope.factura.idDeposito;  
			        		    				}else{
			        		    				nominaProcesoSeleccionada.idDeposito=$scope.idDepositoSeleccionado;
			        		    			}
			        		    				nominaProcesoSeleccionada.idFactura=$scope.factura.idPPPNominaFactura;  
			        		    				// nominaProcesoSeleccionada.idNomina
			        		    				  $http.post(CONFIG.APIURL + "/ppp/nominas/cambiaEstatusNominaGenerada.json", nominaProcesoSeleccionada).then(
										                  function (response) {		  
										                	  $log.debug('ok');
																pinesNotifications.notify({
															        title: 'Mensaje',
															        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
															        type: 'success'
															      });
																$scope.cargarDatosDespuesDeGuardarComplemento(nominaProcesoSeleccionada.claveNomina);
																$scope.cargaInicialDocumentosPppNomina(nominaProcesoSeleccionada.idNomina);
																var fileElement = angular.element('#file_doc_comp_pago');
															    angular.element(fileElement).val(null);
															    $scope.limpiaCampoPac(pacForm);
																$scope.listaNominaEnProceso($scope.nominaCliente)	;	
																 $scope.cargaInicialNomina();
										                  },
										                  function (data) {
										                	  $log.error(data.status+ ' - '+ data.statusText);
																pinesNotifications.notify({
															        title: 'Error',
															        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
															        type: 'error'
															      });
																
																$scope.limpiaCampoPac(pacForm);
										                  });
								                		  
								                		  
								                }else{
								                		  $log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });
								                		  
								                		  $scope.cargarDatosDespuesDeGuardarComplemento(nominaProcesoSeleccionada.claveNomina);
														  $scope.cargaInicialDocumentosPppNomina(nominaProcesoSeleccionada.idNomina);
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
			                	  }else{
			                		  $log.error(response.data.responseCode+ ' - '+ response.data.responseMessage);
			                		  bootbox.alert({
											title : "Mensaje de error",
											message : response.data.responseMessage
			                		  });
												
			                		  
//									  pinesNotifications.notify({
//									        title: 'Error',
//									        text: response.data.responseMessage,
//									        type: 'error'
//									      });
									
							  		       //No se carga nada ya que ocurrio 
//										$scope.cargarDatosDespuesDeGuardarComplemento(nominaProcesoSeleccionada.claveNomina);
//										$scope.cargaInicialDocumentosPppNomina(nominaProcesoSeleccionada.idNomina);
//										var fileElement = angular.element('#file_doc_comp_pago');
//								        angular.element(fileElement).val(null);
//								        $scope.limpiaCampoPac(pacForm);

			                	  }
			                	  
			                  },
			                  function (data) {
		                	  $log.error(data.status+ ' - '+ data.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
							        type: 'error'
							      });
					  		       
								$scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
								$scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
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
				
				
				$scope.generaRegistroSTP = function(list){
				 	  $http.post(CONFIG.APIURL + "/ppp/finanzas/generaPagosSTP.json", list).then(function(response) {
		    		  if(response.data!=undefined && response.data !=null){
		    	
		    	  			pinesNotifications.notify({
		    	  				title: 'Archivo',
		       			        text: 'La carga del layout se realiz&oacute; exitosamente',
		       			        type: 'success'
		    			      });
		    		  }else{
		    			  pinesNotifications.notify({
		    			        title: 'Error',
		    			        text: 'Para generar la clave de orden de pago de los colaboradores, debe agregar el prefijo STP',
		    			        type: 'error'
		    			      });
		    		  }	
		    	  		
		  			}, function(data) {
		  				console.log("error modalCrearNomina--> " + data);
		  				pinesNotifications.notify({
		  			        title: 'Error',
		  			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
		  			        type: 'error'
		  			      });
		  			}); 
		      }
			
				 $scope.generaRegistroAlquimia = function(list){
					 	  $http.post(CONFIG.APIURL + "/ppp/finanzas/dispersaAlquimia.json", list).then(function(response) {
			    		  if(response.data!=undefined && response.data !=null){
			    	
			    	  			pinesNotifications.notify({
			    	  				title: 'Archivo',
			       			        text: 'La carga del layout se realiz&oacute; exitosamente',
			       			        type: 'success'
			    			      });
			    		  }else{
			    			  pinesNotifications.notify({
			    			        title: 'Error',
			    			        text: 'Para generar la clave de orden de pago de los colaboradores, debe agregar el prefijo de dispersión',
			    			        type: 'error'
			    			      });
			    		  }	
			    	  		
			  			}, function(data) {
			  				console.log("error modalCrearNomina--> " + data);
			  				pinesNotifications.notify({
			  			        title: 'Error',
			  			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
			  			        type: 'error'
			  			      });
			  			}); 
			      }
				 
				 $scope.guardarPasoCinco = function (generarFacturaForm){
					 
					 var generarFacturaDto = angular.copy($scope.factura);
					      // Se agregan los totales para la generacion de los reportes de manera correcta
              $scope.totales.ivaComercial = angular.copy($scope.formulaFactura.ivaComercial);
              $scope.totales.honorario = angular.copy($scope.formulaFactura.honorarioPPP);
              $scope.totales.montoTotalHonorario = angular.copy($scope.montosFactura.honorario);
  			  $scope.totales.montoTotalColaboradoresPPP = angular.copy($scope.totalNominaPPPColaboradores);
  			
              generarFacturaDto.totales = angular.copy($scope.totales);
					 
					 if(generarFacturaForm.$invalid){
			    		  
			        	  pinesNotifications.notify({
			    			        title: 'Generar factura',
			    			        text: 'El formulario tiene un error, favor de ingresar y/o seleccionar los campos solicitados',
			    			        type: 'error'
			    			      });
			        	  
			    	  }else{
			    		  
						 if(((generarFacturaDto.formaPago !=null && generarFacturaDto.formaPago.idCatGeneral >=1) 
								 && (generarFacturaDto.metodoPago !=null && generarFacturaDto.metodoPago.idCatGeneral >=1)
								 && ($scope.factura.idCmsPdfFactura >= 1 && $scope.factura.idCmsXmlFactura >= 1))){
							 
							 if($scope.generarFacturaPaso5.documentoNuevo == null || $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo == undefined || $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo ==null
									 || $scope.generarFacturaPaso5.montoComprobantePago == null){
								 
				    		  pinesNotifications.notify({
			    			        title: 'Generar factura',
			    			        text: 'El formulario tiene un error, favor de ingresar el monto del comprobante de pago y/o su comprobante',
			    			        type: 'error'
			    			      });
				    		  
							 }else{

								  bootbox.confirm({
									  title : "Confirmar acci&oacute;n",
										message : "Una vez registrado el comprobante de pago ya no podrá realizar acción alguna. ¿Desea continuar?",
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
								    	  		
								    	  		 if($scope.generarFacturaPaso5.montoComprobantePago != null && $scope.generarFacturaPaso5.montoComprobantePago != undefined){
									    			 generarFacturaDto.montoComprobantePago = $scope.generarFacturaPaso5.montoComprobantePago;
									    		 }
									    		 
									    		 if($scope.generarFacturaPaso5.documentoNuevo != null && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo != undefined && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo !=null){
									    			 generarFacturaDto.documentoNuevo = $scope.generarFacturaPaso5.documentoNuevo;
									    		 }
								    	  		
//									    		 if(generarFacturaDto.montoComprobantePago < $scope.totales.total){
									    	//	 if(generarFacturaDto.montoDeposito >=$scope.totales.totalMenos10 && generarFacturaDto.montoDeposito <=$scope.totales.totalMas10  ){
									    	/*	 if(generarFacturaDto.montoDepositoMenosDiez > $scope.totales.total ){
									    			 pinesNotifications.notify({
									    			        title: 'Mensaje',
									    			      //  text: 'El "monto del comprobante de pago" no puede ser menor al "monto total de la factura". Favor de revisar los montos',
									    			          text: 'No hay "Depósito" con el rango permitido, por lo tanto la Factura la realizara Finanzas',	       
									    			        type: 'error'
									    			      });
									    			 
									    		 }else{*/
									    			 $http.post(CONFIG.APIURL + "/ppp/nominas/guardarConcepto.json", generarFacturaDto).then(function(response) {

										            	  pinesNotifications.notify({
										        			        title: 'Concepto',
										        			        text: 'Se ha cargado el concepto exitosamente',
										        			        type: 'success'
										        			      });
										            	  
										            	  $scope.generarFacturaPaso5.documentoNuevo = null;
										            	  $scope.limpiarFormularioPaso5(generarFacturaForm);
										            	  $scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
														  $scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
										            	  
										    			}, function(data) {
										    				console.log("error modalCrearNomina--> " + data);
										    				pinesNotifications.notify({
										    			        title: 'Error',
										    			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
										    			        type: 'error'
										    			      });
										    			});
									    		 //} 
											}
										}				  
								  });
				    	  	}
 
							 
						 }else{
							
							 generarFacturaDto.formaPago = $scope.generarFacturaPaso5.formaPago;
				    		 generarFacturaDto.metodoPago = $scope.generarFacturaPaso5.metodoPago;
				    		 
				    		 if(($scope.generarFacturaPaso5.montoComprobantePago != null && $scope.generarFacturaPaso5.montoComprobantePago != undefined)
				    				 && ($scope.generarFacturaPaso5.documentoNuevo == null || $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo == undefined || $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo ==null)){
				    			 
				    			 pinesNotifications.notify({
				    			        title: 'Generar factura',
				    			        text: 'El formulario tiene un error, favor de ingresar el comprobante de pago',
				    			        type: 'error'
				    			      });
				    			 
				    		 }
				    		 /*else if(($scope.generarFacturaPaso5.montoComprobantePago == null || $scope.generarFacturaPaso5.montoComprobantePago == undefined)
				    				 && ($scope.generarFacturaPaso5.documentoNuevo != null && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo != undefined && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo !=null)){
				    			 
				    			 pinesNotifications.notify({
				    			        title: 'Generar factura',
				    			        text: 'El formulario tiene un error, favor de ingresar el monto del comprobante de pago',
				    			        type: 'error'
				    			      });
				    			 
				    		 }*/
				    		 else{
				    			 
				    			 if($scope.generarFacturaPaso5.montoComprobantePago != null && $scope.generarFacturaPaso5.montoComprobantePago != undefined){
					    			 generarFacturaDto.montoComprobantePago = $scope.generarFacturaPaso5.montoComprobantePago;
					    		 }
					    		 
					    		 if($scope.generarFacturaPaso5.documentoNuevo != null && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo != undefined && $scope.generarFacturaPaso5.documentoNuevo.nombreArchivo !=null){
					    			 generarFacturaDto.documentoNuevo = $scope.generarFacturaPaso5.documentoNuevo;
					    		 }
					    		
					    		/* if(generarFacturaDto.montoComprobantePago!=null && (generarFacturaDto.montoComprobantePago < $scope.totales.total)){
					    			 
					    			 pinesNotifications.notify({
					    			        title: 'Error',
					    			        text: 'El "monto del comprobante de pago" no puede ser menor al "monto total de la factura". Favor de revisar los montos',
					    			         //  text: 'El "monto del comprobante de pago" no puede ser menor al "monto total de la factura". Favor de revisar los montos',
					    			     
					    			        type: 'error'
					    			      });*/
					    	
					    		 generarFacturaDto.montoComprobantePago = $scope.totales.montoTotalColaboradoresPPP;
					    		// if(generarFacturaDto.montoDeposito >= $scope.totales.totalMenos10 && generarFacturaDto.montoDeposito <=$scope.totales.totalMas10  ){		
					    		// if(generarFacturaDto.montoDepositoMenosDiez <=generarFacturaDto.montoDeposito ){
					    			 $http.post(CONFIG.APIURL + "/ppp/nominas/guardarConcepto.json", generarFacturaDto).then(function(response) {

						            	  pinesNotifications.notify({
						        			        title: 'Concepto',
						        			        text: 'Se ha cargado el concepto exitosamente',
						        			        type: 'success'
						        			      });
						            	  
						            	  $scope.generarFacturaPaso5.documentoNuevo = null;
						            	  generarFacturaDto.documentoNuevo = null;
						            	  $scope.limpiarFormularioPaso5(generarFacturaForm);
						            	  $scope.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina);
										  $scope.cargaInicialDocumentosPppNomina($scope.nominaProcesoSeleccionada.idNomina);
						            	  
						            	  
						    			}, function(data) {
						    				console.log("error modalCrearNomina--> " + data);
						    				pinesNotifications.notify({
						    			        title: 'Error',
						    			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
						    			        type: 'error'
						    			      });
						    			});
					    			 
					    		
					    			 
					    			/* } else{
					    				 
						    			 pinesNotifications.notify({
						    			        title: 'Error',
						    			         text: 'No hay "Depósito" con el rango permitido, por lo tanto la Factura la realizara Finanzas',       
						    			        type: 'error'
						    			      });
					    			 
					    			
					    		 }*/
				    		 } 
						 }
			    	  }
				 }
				 
			    $scope.guardarNominaFacturaFlujoAlterno = function (complementoNominaFacturaForm) {
			    	
			    	if(complementoNominaFacturaForm.$invalid){
			    		  
			        	  pinesNotifications.notify({
			    			        title: 'Facturaci&oacute;n n&oacute;mina',
			    			        text: 'El formulario tiene un error, favor de ingresar los campos solicitados',
			    			        type: 'error'
			    			      });
			    	}else{

			    		if($scope.factura.totales != undefined && $scope.factura.totales !=null){
			    			
			    			if($scope.totalesFlujoAlterno.montoComprobantePagoPaso4 == undefined || $scope.totalesFlujoAlterno.montoComprobantePagoPaso4 == null ){
			    				
			    				pinesNotifications.notify({
			    			        title: 'Facturaci&oacute;n n&oacute;mina',
			    			        text: 'El formulario tiene un error, favor de ingresar el monto de comprobate de pago',
			    			        type: 'error'
			    			      });
			    				
			    			}else if($scope.totalesFlujoAlterno.montoComprobantePagoPaso4 < $scope.totales.total){
				    			 
				    			 pinesNotifications.notify({
				    			        title: 'Error',
				    			        text: 'El "monto del comprobante de pago" no puede ser menor al "monto total de la factura". Favor de revisar los montos',
				    			        type: 'error'
				    			      });
			    			
			    			
			    			}else{
			    				
						    	$scope.facturaDto = {
						    			"totales" : $scope.totalesFlujoAlterno.totales,
						    			"idPPPNomina" : $scope.nominaProcesoSeleccionada.idNomina,
						    			"montoComprobantePago" : $scope.totalesFlujoAlterno.montoComprobantePagoPaso4
						    	};
						    	
						    	if($scope.factura != undefined && $scope.factura != null && $scope.factura.idPPPNominaFactura != null ){
						    		$scope.facturaDto.idPPPNominaFactura = $scope.factura.idPPPNominaFactura;
						    	}

						    	$scope.guardarNominaFacturaFlujoAlternoImplementacion($scope.facturaDto);
			    				
			    			}
			    		}else{
			    			
			    			$scope.facturaDto = {
					    			"totales" : $scope.totalesFlujoAlterno.totales,
					    			"idPPPNomina" : $scope.nominaProcesoSeleccionada.idNomina
					    	};
					    	
					    	if($scope.factura != undefined && $scope.factura != null && $scope.factura.idPPPNominaFactura != null ){
					    		$scope.facturaDto.idPPPNominaFactura = $scope.factura.idPPPNominaFactura;
					    	}

					    	$scope.guardarNominaFacturaFlujoAlternoImplementacion($scope.facturaDto);
			    			
			    		}
			    	}
			    }
			   
			    $scope.guardarNominaFacturaFlujoAlternoImplementacion = function (facturaDto) {
			    	
					nominaService.guardarNominaFacturaFlujoAlterno(facturaDto, function(response) {

						if(response.data.mensajeError != undefined){
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: response.data.mensajeError,
						        type: 'error'
						      });
						}else{

							bootbox.alert({
								title : "Mensaje",
								message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
								buttons : {
									ok : {
										label : 'ACEPTAR',
										className : 'center-block btn-primary'
									}
								},
								callback : function() {
									
									$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);

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
						
						$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
					})
			    }
			    			    
			    $scope.validaCamposPaso4_5 = function () {
			    	
			    	$scope.habilitaCampos = true;
			    	$scope.actulizaComprobante= true;
			    	$scope.habilitaCatMetodoPago = true;
			    	$scope.habilitaGuardar = true;
			    	$scope.habilitaGenerarFactura = true;
			    	$scope.isVisibleSeccionCargaDocComplementoPago = true;
			    	$scope.isVisibleSeccionDescargaDocComplementoPago = false;
			    	
			    	if ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 7
			    	   || $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 25
			    		|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 18
			    		|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 19
			    		|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 20
			    		|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 23
			    		|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 13){
			    		
			    		$scope.habilitaCampos = false;
			    		$scope.actulizaComprobante= true;
			    		$scope.habilitaCatMetodoPago = false;
			    		$scope.habilitaGuardar = true;
		
				    	$scope.habilitaGenerarFactura = false;
				    	$scope.isVisibleSeccionCargaDocComplementoPago = false;
				    	$scope.isVisibleSeccionDescargaDocComplementoPago = true;
			    		
			    	}else if($scope.factura.idCmsXmlFactura >= 1 && $scope.factura.idCmsPdfFactura >= 1){
			    		
			    		$scope.habilitaCampos = false;
			    		$scope.actulizaComprobante= true;
			    		$scope.habilitaGenerarFactura = false;
			    		
			    		if($scope.factura.montoComprobantePago != null){
			    		//yo	$scope.isVisibleSeccionCargaDocComplementoPago = false;
			    		}
			    		
			    		if($scope.nominaComplementoDto.requiereFianciamiento
			    				&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
			    						|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15)){ 
				    		$scope.habilitaCatMetodoPago = false;
			    		}
			    		
			    	}else if($scope.factura.montoComprobantePago == null 
			    			&& ($scope.factura.metodoPago !=undefined && $scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral >= 1) 
	    					 && ($scope.factura.formaPago !=undefined && $scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral >= 1) 
			    		     && $scope.pagoVinculado==1){
			    		
			    		$scope.habilitaGenerarFactura = true;
			    		
			    		if($scope.nominaComplementoDto.requiereFianciamiento
			    				&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
			    						|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15)){ 
				    		$scope.habilitaCatMetodoPago = false;
			    		}
			    		
			    	}else if(($scope.factura.metodoPago !=undefined && $scope.factura.metodoPago !=null && $scope.factura.metodoPago.idCatGeneral == 0) 
		    					 && ($scope.factura.formaPago !=undefined && $scope.factura.formaPago !=null && $scope.factura.formaPago.idCatGeneral == 0)) {
			    		
			    		$scope.habilitaGenerarFactura = false;
			    		
			    		if($scope.nominaComplementoDto.requiereFianciamiento
			    				&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
			    						|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15)){ 
				    		$scope.habilitaCatMetodoPago = false;
			    		}
			    		
			    	}else if($scope.factura.idCMS >= 1){
			    		
			    		$scope.isVisibleSeccionCargaDocComplementoPago = false;
				    	$scope.isVisibleSeccionDescargaDocComplementoPago = true;
				    	
				    	if($scope.nominaComplementoDto.requiereFianciamiento
			    				&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
			    						|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15)){ 
				    		$scope.habilitaCatMetodoPago = false;
			    		}
			    		
			    	} else if($scope.nominaComplementoDto.requiereFianciamiento
		    				&& ($scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 14
		    						|| $scope.nominaProcesoSeleccionada.catEstatusNomina.idCatGeneral === 15)){ 
			    		$scope.habilitaCatMetodoPago = false;
			    	}
					
					if ($scope.factura.idCMS){
						$scope.habilitaGuardar = false;
					}
			    }
			    

			    
			    $scope.actualizarDocComprobantePago = function(){					
					$scope.isVisibleSeccionCargaDocComplementoPago = true;
					$scope.isVisibleSeccionDescargaDocComplementoPago = false;
					$scope.actualizaDocComplementoPago = true;
					$scope.generarFacturaPaso5.documentoNuevo = null;
					
					var fileElement = angular.element('#file_doc_comp_pago');
			        angular.element(fileElement).val(null);
					$scope.habilitaGuardar = true;
		      	}
			    
			    $scope.cancelarDocComprobantePago = function(){
			    	$scope.isVisibleSeccionCargaDocComplementoPago = false;
					$scope.isVisibleSeccionDescargaDocComplementoPago = true;
					$scope.actualizaDocComplementoPago = false;
					
					var fileElement = angular.element('#file_doc_comp_pago');
			        angular.element(fileElement).val(null);
			        
			        $scope.generarFacturaPaso5.documentoNuevo = $scope.factura.documentoNuevo;
		      	}
			    
			      $scope.limpiarFormularioPaso5 = function(generarFacturaForm){
			    	  if(generarFacturaForm){
			    		  generarFacturaForm.$setPristine();
			    		  generarFacturaForm.$setUntouched();
			        	  
			    	  }
			    	  
			      }
			      
			      $scope.confirmarTimbradoColaboradores = function() {
			    	  var colaboradoresValidos = [];			    	  
			    	  angular.forEach($scope.dataExcel.contentRows,function (value,key) {
			    		  
			    		    if(value.idEstatus === 12 || value.idEstatus ===16){		
                                colaboradoresValidos.push(value);	    		    	
			    		    }

			    	  });
			    	  
			    	  if(colaboradoresValidos.length >=1){
			    		  
			    		  $('#modalPacTimbradoColaboradores').modal('show');
			    		  
			    	  }else{
  						  bootbox.alert({
								title : "Mensaje",
								message : "No existen colaboradores por timbrar.",
								buttons : {
									ok : {
										label : 'ACEPTAR',
										className : 'center-block btn-primary'
									}
								},
								callback : function() {
									
								}
							});	      					  
                      } 
					}
			      
			      $scope.timbrarColaboradores = function(){
			    	  
			    	  var colaboradorTimbradoSend = {};
                      colaboradorTimbradoSend.catPacTimbrado={
                          "idCatGeneral": 9945,
                          "clave": "SW",
                          "descripcion": "SW",
                          "idMasdescripcion": null,
                          "indEstatus": 1,
                          "usuarioAlta": null,
                          "usuarioModificacion": null,
                          "fechaAlta": null,
                          "fechaModificacion": null
                      };
                  //  colaboradorTimbradoSend.colaboradores=[colaborador];
                    colaboradorTimbradoSend.nominaPPP = $scope.nominaProcesoSeleccionada;
                    colaboradorTimbradoSend.nominaComplemento = angular.copy($scope.nominaComplementoDto);
                    colaboradorTimbradoSend.idPPPNominaFactura = angular.copy($scope.factura.idPPPNominaFactura);
                        
                                              $http.post(CONFIG.APIURL + "/ppp/nomina/timbrado.json",colaboradorTimbradoSend).then(function(response) {

                           $scope.data= response;
                          if (response.data.responseCode==='200'){
                                  pinesNotifications.notify({
                                      title: 'Timbrado',
                                      text: 'El timbrado se realizó exitosamente',
                                      type: 'success'
                                    });
                              
                                    $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                                    $scope.existenColaboradodaresTimbrados($scope.nominaProcesoSeleccionada.idNomina);
                          }else{
                              
                              $scope.validarEstatusNomina();
                          }
                            
                            // Se ejecuta la carga inicial de los colaboadores
                           $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                           $scope.existenColaboradodaresTimbrados($scope.nominaProcesoSeleccionada.idNomina);
                        },function(response){
                               $scope.data= response;
                               $scope.validarEstatusNomina();
                            });
            }
            
            
            $scope.validarEstatusNomina = function(){
                $http.post(CONFIG.APIURL + "/ppp/nominas/getEstatusNominaByIdNomina.json",$scope.nominaProcesoSeleccionada.idNomina).then(function(response) {
                      if (response.data.nominaEstatus != null && response.data.nominaEstatus==='CIERRE_NOM'){
                          pinesNotifications.notify({
                              title: 'Timbrado',
                              text: 'El timbrado se realizó exitosamente',
                              type: 'success'
                            });
                          
                           $scope.datosVisiblePaso6 =true;
                          $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                          $scope.existenColaboradodaresTimbrados($scope.nominaProcesoSeleccionada.idNomina);
                      }else if (response.data.nominaEstatus != null && (response.data.nominaEstatus==='NOM_STP_EXITO' || response.data.nominaEstatus==='NOM_STP_ERROR' )){
                          pinesNotifications.notify({
                              title: 'Timbrado',
                              text: 'Uno o más de los colaboradores contiene algún error.',
                              type: 'warning'
                            });
                           $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                           $scope.datosVisiblePaso6 =true;
                           $scope.existenColaboradodaresTimbrados($scope.nominaProcesoSeleccionada.idNomina);                      }else{
                          $scope.validarEstatusNominaAux();
                      }
                    
                    
                },function(response){
                           $scope.data= response;
                           $scope.validarEstatusNominaAux();
                        });
            }
            
            
            $scope.validarEstatusNominaAux = function(){
                $http.post(CONFIG.APIURL + "/ppp/nominas/getEstatusNominaByIdNomina.json",$scope.nominaProcesoSeleccionada.idNomina).then(function(response) {
                      if (response.data.nominaEstatus != null && response.data.nominaEstatus==='CIERRE_NOM'){
                          pinesNotifications.notify({
                              title: 'Timbrado',
                              text: 'El timbrado se realizó exitosamente.',
                              type: 'success'
                            });
                          
                        
                          $scope.datosVisiblePaso6 =true;
                           $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                      }else if (response.data.nominaEstatus != null &&  (response.data.nominaEstatus==='NOM_STP_EXITO' || response.data.nominaEstatus==='NOM_STP_ERROR' )) {
                          pinesNotifications.notify({
                              title: 'Timbrado',
                              text: 'Uno o más de los colaboradores no fueron timbrados.',
                              type: 'warning'
                            });
                          
                           $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                           $scope.datosVisiblePaso6 =true;
                      }else{
                          $scope.validarEstatusNomina();
                      }
                    
                    
                },function(response){
                           $scope.data= response;
                           
                           $scope.validarEstatusNomina();
                        });
            }
            
            
            $scope.timbrarColaboradoresAux = function(){	  
		
			    	  var colaboradorTimbrado = {};
			    	  var colaboradoresValidos = [];
			    	  colaboradorTimbrado.nominaPPP = $scope.nominaProcesoSeleccionada;
			    	  
			    	  angular.forEach($scope.dataExcel.contentRows,function (value,key) {
			    		    if(value.idEstatus === 12){		
                                colaboradoresValidos.push(value);	    		    	
			    		    }

			    	  });
                        
                        if(colaboradoresValidos.length >=1){
                    
                            $scope.totalPaginacion = 1;
                    		if(colaboradoresValidos.length > 20 && colaboradoresValidos.length < 40) {
                    			$scope.totalPaginacion = 5;
                    		}else if(colaboradoresValidos.length > 40 && colaboradoresValidos.length < 80) {
                    			$scope.totalPaginacion = 10;
                    		}else if(colaboradoresValidos.length > 80 && colaboradoresValidos.length < 100) {
                    			$scope.totalPaginacion = 20;
                    		}else if(colaboradoresValidos.length > 100){
                    			$scope.totalPaginacion = 25;
                    		}
                    		
                			var primerElemento = 0;
                			var ultimo = 0;
                			var monitorTimbrado=true;
                			/* cuantas veces se tiene que hacer el ciclo con decimales*/
                			$scope.totalCiclos = colaboradoresValidos.length / $scope.totalPaginacion; 
                			/*se redondea el valor para que quede en entero*/
                			$scope.totalCiclos = Math.ceil($scope.totalCiclos);
                			$scope.totalRespuestasBackend= 0;
                			$log.debug("Numero de colaboradores "+ colaboradoresValidos.length);
                			$log.debug("Numero de ciclos "+ $scope.totalCiclos);
                			
                			for (var i = 0 ; i < $scope.totalCiclos; i++) {
                				
                				ultimo = ultimo + $scope.totalPaginacion;
                				var sublista = colaboradoresValidos.slice(primerElemento,ultimo);
                				
                				
                				var colaboradorTimbradoSend = {};
                				colaboradorTimbradoSend.colaboradores = sublista;
                				colaboradorTimbradoSend.nominaPPP = $scope.nominaProcesoSeleccionada;
                				colaboradorTimbradoSend.nominaComplemento = angular.copy($scope.nominaComplementoDto);
                				colaboradorTimbradoSend.idPPPNominaFactura = angular.copy($scope.factura.idPPPNominaFactura);
                				
                				// se le aigna el pac por el cual timbrara la factura y el xml
                				colaboradorTimbradoSend.catPacTimbrado = $scope.pacTimbrado.catPacTimbrado;
          			    	  
                				$http.post(CONFIG.APIURL + "/ppp/nomina/timbrado.json",colaboradorTimbradoSend).then(function(response) {
                					$scope.totalRespuestasBackend = $scope.totalRespuestasBackend +1;
                        			$log.debug("totalRespuestasBackend "+ $scope.totalRespuestasBackend);
                        			
                        			 if (response.data.responseCode != '200'){
                                         monitorTimbrado=false;
                                       }
                        			
                        			 if($scope.totalRespuestasBackend ===  $scope.totalCiclos && monitorTimbrado ) {
                        				pinesNotifications.notify({
                        					title: 'Timbrado',
                        					text: 'El timbrado se realizó exitosamente',
            	         			        type: 'success'
            	         			      });
                        				
                        				// si todo es ok y si el total de colaboradores registrados para la nomina es igual al total de colaboradores timbrados, cambia el estatus a la nomina a timbrado
                        	      	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cambiarEstatusNominaCierre.json", $scope.factura.idPPPNomina).then(function(response) {
                        	      	  		
                        	      	  		if(response.data){

                        	      	  		pinesNotifications.notify({
                        	   			        title: 'Éxito',
                        	   			        text: 'La n\u00f3mina se ha timbrado con \u00e9xito..',
                        	   			        type: 'success'
                        	   			      });
                        	      	  		}

                        	   			}, function(data) {
                        	   				pinesNotifications.notify({
                        	   			        title: 'Error',
                        	   			        text: 'Ocurrio un error al cambiar a Timbrado la n\u00f3mina.',
                        	   			        type: 'error'
                        	   			      });
                        	   			});
                        				
                        				//Se ejecuta la carga inicial de los colaboadores
                            			$scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                        				
                        			}
                        			 
                        			 if($scope.totalRespuestasBackend ===  $scope.totalCiclos && !monitorTimbrado ) {
                                         $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
                                      }
                					
                        			
                				},function(response) {
                        			$log.debug("Error al realizar el timbrado "+ response);
            						$scope.totalRespuestasBackend = $scope.totalRespuestasBackend +1;
            						$scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
            						
            						if($scope.totalRespuestasBackend ===  $scope.totalCiclos ) {
        								pinesNotifications.notify({
        		         			        title: 'Error',
        		         			        text: 'Error al realizar el timbrado',
        		         			        type: 'error'
        		         			      });
        							}
                				});
                				
                				
                    			primerElemento = ultimo;
                    			
                			}
                			
                			
                			
                        }else{
  						  bootbox.alert({
								title : "Mensaje",
								message : "No existen colaboradores por timbrar.",
								buttons : {
									ok : {
										label : 'ACEPTAR',
										className : 'center-block btn-primary'
									}
								},
								callback : function() {
									
								}
							});	      					  
                        }  
			      }
			      
			      
			      $scope.timbrarColaborador =  function(colaborador){
	                  
		                var colaboradorTimbradoSend = {};
		                colaboradorTimbradoSend.catPacTimbrado={
		                    "idCatGeneral": 9945,
		                    "clave": "SW",
		                    "descripcion": "SW",
		                    "idMasdescripcion": null,
		                    "indEstatus": 1,
		                    "usuarioAlta": null,
		                    "usuarioModificacion": null,
		                    "fechaAlta": null,
		                    "fechaModificacion": null
		                };
		              colaboradorTimbradoSend.colaboradores=[colaborador];
		              colaboradorTimbradoSend.nominaPPP = $scope.nominaProcesoSeleccionada;
		              colaboradorTimbradoSend.nominaComplemento = angular.copy($scope.nominaComplementoDto);
		              colaboradorTimbradoSend.idPPPNominaFactura = angular.copy($scope.factura.idPPPNominaFactura);
		                  
		                  $http.post(CONFIG.APIURL + "/ppp/nomina/timbrado.json",colaboradorTimbradoSend).then(function(response) {
		                
		                     $scope.data= response;
		                     
		                     if(response.data.responseCode=='200'){
	                               pinesNotifications.notify({
	                                  title: 'Timbrado',
	                                  text: 'El colaborador se se ha timbrado con \u00e9xito..',
	                                  type: 'success'
	                                });
	                              }else{
	                               pinesNotifications.notify({
	                                  title: 'Timbrado',
	                                  text: 'El colaborador no se pudo timbrar, por favor verifique el registro...',
	                                  type: 'error'
	                                });
	                              }
		                    
		                      
		                      // Se ejecuta la carga inicial de los colaboadores
		                        $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
		                  },function(response){
		                     $scope.data= response;
		                     pinesNotifications.notify({
                                 title: 'Timbrado',
                                 text: 'Se agoto el tiempo de espera, por favor actualice, revise el registro y si es necesario vuelva ha intentarlo más tarde. ',
                                 type: 'warning'
                               });
                          
                          $scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
		                  });

		                  
		                }
			  
		       $scope.modalCancelarNomina = function (nomina){
		    	   
					  bootbox.confirm({
						  title : "Confirmar acci&oacute;n",
							message : "¿Esta seguro de cancelar la nómina?",
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
									$scope.motivoCancelacion = "";
									$scope.clienteSeleccionado = nomina;
									$('#modalCancelarNomina').modal('show');
								}
							}
					  	});
		          
		       }
			  
			  
				  
	       $scope.cancelarNomina = function (clienteSeleccionado){
	    	   
	    	   $scope.nominaDto = {
			        idNominaPPP : clienteSeleccionado.idNomina,
					motivoRechazo : $scope.motivoCancelacion
	              }
	
	    	   nominaService.cancelarNomina($scope.nominaDto,function(response) {
				 		if(response.data.mensajeError != undefined || response.data.mensajeError != null){
					 			$scope.idNominaPPP = null;
					 			$('#modalCancelarNomina').modal('hide');
  							  	bootbox.alert({
  							  		title : "Error",
  							  		message : response.data.mensajeError,
  							  		buttons : {
  							  			 ok : {
  							  				 	label : 'ACEPTAR',
  							  				 	className : 'center-block btn-primary'
  							  			 }
  							  		},
								callback : function() {
									$scope.listaNominaEnProceso(clienteSeleccionado);
								}
							});	
                        }else{
                              $scope.idPppNomina = null;
					 		  $('#modalCancelarNomina').modal('hide');
  							  bootbox.alert({
  									title : "Mensaje",
  									message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
  									buttons : {
  										ok : {
  											label : 'ACEPTAR',
  											className : 'center-block btn-primary'
  										}
  									},
  									callback : function() {
  									
  										$scope.listaNominaEnProceso(clienteSeleccionado);

  									}
  								});		
					  }
                    }, function(response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        $scope.listaNominaEnProceso(clienteSeleccionado);
					});
	          }
		
	       $scope.guardarCuentaOrdenante = function(cuenta){
		    	  
		    	  bootbox
					.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas guardar el n&uacute;mero de clabe ordenante?",
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
								$http.post(CONFIG.APIURL + "/ppp/finanzas/guardarCuentaOrdenante.json", cuenta).then(function(response) {
						    		  if(response.data.mensajeError!=undefined && response.data.mensajeError !=null){
						    			  pinesNotifications.notify({
						    			        title: 'Error',
						    			        text: 'Existio un error al guardar la clabe ordenante, favor de intentarlo nuevamente.',
						    			        type: 'error'
						    			      });
						    		  }else{
						    			  pinesNotifications.notify({
						    	  				title: 'Mensaje',
						       			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
						       			        type: 'success'
						    			      });
						    		  }	
						    		  
						  			}, function(data) {
						  				console.log("error generaRegistroSTP--> " + data);
						  				pinesNotifications.notify({
						  			        title: 'Error',
						  			        text: 'Existio un error al guardar la clabe ordenante, favor de intentarlo nuevamente.',
						  			        type: 'error'
						  			      });
						  			}); 

							}
						}
					});
		      }
	       
	       
	       $scope.guardarCuentaOrdenante = function(cuenta){
		    	  
		    	  bootbox
					.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas guardar el n&uacute;mero de clabe ordenante?",
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
								$http.post(CONFIG.APIURL + "/ppp/finanzas/guardarCuentaOrdenante.json", cuenta).then(function(response) {
						    		  if(response.data.mensajeError!=undefined && response.data.mensajeError !=null){
						    			  pinesNotifications.notify({
						    			        title: 'Error',
						    			        text: 'Existio un error al guardar la clabe ordenante, favor de intentarlo nuevamente.',
						    			        type: 'error'
						    			      });
						    		  }else{
						    			  pinesNotifications.notify({
						    	  				title: 'Mensaje',
						       			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
						       			        type: 'success'
						    			      });
						    		  }	
						    		  
						  			}, function(data) {
						  				console.log("error generaRegistroSTP--> " + data);
						  				pinesNotifications.notify({
						  			        title: 'Error',
						  			        text: 'Existio un error al guardar la clabe ordenante, favor de intentarlo nuevamente.',
						  			        type: 'error'
						  			      });
						  			}); 

							}
						}
					});
		      }
	       
	       $scope.existenColaboradodaresTimbrados = function(idPppNomina){
				$http.post(CONFIG.APIURL + "/ppp/nominas/existenColaboradodaresPorTimbrar.json", idPppNomina).then(function(response) {
					
					  $scope.habilitaTimbradoNomina = false;
					
		    		  if(response.data){
		    			  $scope.habilitaTimbradoNomina = true;
		    		  }
		    		  
		    		
		    		  
		  			}, function(data) {
		  				console.log("error existenColaboradodaresTimbrados--> " + data);
		  				pinesNotifications.notify({
		  			        title: 'Error',
		  			        text: 'Existio un error en el sistema.',
		  			        type: 'error'
		  			      });
		  			}); 
	       }
	       
	       $scope.existenColaboradoresSinTimbrar = function(idPppNomina){
				$http.post(CONFIG.APIURL + "/ppp/nominas/existenColaboradoresSinTimbrar.json", idPppNomina).then(function(response) {
					
					  
					  $scope.habilitaTimbradoColaborador = false;
					
		    		 
		    		  
		  			}, function(data) {
		  				console.log("error existenColaboradodaresTimbrados--> " + data);
		  				pinesNotifications.notify({
		  			        title: 'Error',
		  			        text: 'Existio un error en el sistema.',
		  			        type: 'error'
		  			      });
		  			}); 
	       }

	       $scope.timbrarNomina= function(){
				$http.post(CONFIG.APIURL + "/ppp/nominas/existenColaboradoresPorTimbrar.json", $scope.nominaProcesoSeleccionada.idNomina).then(function(response) {
					
					if(response.data){
				    	  bootbox
							.confirm({
								title : "Confirmar acci&oacute;n",
    	        				message : '<div class="text-center"><strong>¡¡¡ EXISTEN COLABORADORES SIN TIMBRAR !!!</strong><br>¿Est\u00e1s seguro que deseas cerrar la nómina?<br>Si lo hace, todos los pasos quedaran inhabilitados para su edición y acción alguna.<br>¿Desea continuar? </div>',
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
										
										$scope.cambiarEstatusNominaTimbrado($scope.nominaProcesoSeleccionada.idNomina);
									}
								}
							});  
		    		 }else{
		    			 bootbox
							.confirm({
								title : "Confirmar acci&oacute;n",
 	        				message : '<div class="text-center">¿Est\u00e1s seguro que deseas timbrar la nómina?<br>Si lo hace, todos los pasos quedaran inhabilitados para su edición y acción alguna.<br>¿Desea continuar? </div>',
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
										$scope.cambiarEstatusNominaTimbrado($scope.nominaProcesoSeleccionada.idNomina);
									}
								}
							});
		    		 }
					
					
				}, function(data) {
	  				console.log("error timbrarNomina--> " + data);
	  				pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'Existio un error en el sistema.',
	  			        type: 'error'
	  			      });
	  			});
	       }
					
		$scope.cambiarEstatusNominaTimbrado= function(idNominaPpp){
			$http.post(CONFIG.APIURL + "/ppp/nominas/cambiarEstatusNominaTimbrado.json", idNominaPpp).then(function(response) {
				
				if(response.data){
					
					pinesNotifications.notify({
    	  				title: 'Mensaje',
       			        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
       			        type: 'success'
    			      });
					
					nominaService.cargarDatosDespuesDeGuardarComplemento($scope.nominaProcesoSeleccionada.claveNomina, function(response) {
						$scope.datosVisiblePaso6=false;
						 $scope.cargaInicialNomina();
		        		 $scope.getDatosNominaByIdNomina(response.data);
		        		 $scope.nominaCliente = response.data.nominaClienteDto;
		        		 $scope.listaNominaEnProceso($scope.nominaCliente);
		  			},
		  			function(response) {
		  				$log.error(response.status+ ' - '+ response.statusText);
		  				pinesNotifications.notify({
		  			        title: 'Error',
		  			        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
		  			        type: 'error'
		  			      });
		  				
		  				$scope.cargaInicialNomina();
						$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
						$scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
	
		  			});
					
				}else{
					
					pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'Existio un error en el sistema al timbrar la nómina.',
	  			        type: 'error'
	  			      });
					
					
					$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
					$scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
				}
	    		  
	  			}, function(data) {
	  				console.log("error cambiarEstatusNominaTimbrado--> " + data);
	  				pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'Existio un error en el sistema al cambiar el estatus de la nomina a timbrado.',
	  			        type: 'error'
	  			      });
	  				
					$scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
					$scope.listaNominaEnProceso($scope.nominaProcesoSeleccionada.nominaClienteDto);
	  			});
		}
		
		$scope.showModalDecodificador = function() {
			 $scope.textoDecodificar = "";
		      $scope.textoDecodificado = "";
		      
		      $('#modalDecodificador').modal('show');
		}
		
		$scope.decodificar = function(textoDecodificar){
			
			$http.post(CONFIG.APIURL + "/ppp/decrypt.json", textoDecodificar).then(function(response) {
										
				if (Array.isArray(response.data)){
					const datos = response.data;
				datos.forEach(function(datos, index) {
					if(`${datos}`===":"){
						$scope.textoDecodificado+="|";
					}else{
						$scope.textoDecodificado+=`${datos}`;
					}
				});
				}else {
					$scope.textoDecodificado =JSON.stringify(response.data);
				}
		
	  			}, function(data) {
	  				console.log("error texto a decodificar--> " + data);
	  				pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'La cadea no tiene el formato correcto',
	  			        type: 'error'
	  			      });
	  				
	  				$scope.cargaInicialColaboradores($scope.idNomina);	
	  			}); 

			
		}
  });