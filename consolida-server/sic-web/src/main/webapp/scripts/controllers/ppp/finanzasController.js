'use strict';
angular.module('theme.core.templates')
  .controller('finanzasController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, finanzasService, nominaService, pinesNotifications) {


	  $scope.isVisibleSecciones = false;
	  $scope.isVisibleMensajeSinNominas = false;

	  $scope.nominaComplementoDto={};
	  $scope.nominaProcesoSeleccionada = {};
	  $scope.celula = {}

	  // documento excel
      $scope.data = {};
      $scope.dataExcel = {};
      $scope.dataExcel.contentRows = {};
      $scope.registraOrden = {};
      $scope.ordenPago ={};
      $scope.idCatEstatusSelected = null;


	  $scope.cargaInicial = function(){

		  $scope.isVisibleSecciones = false;

		  finanzasService.cargaInicial(function(response) {

			  $scope.gridNominasParaDispersion = response.data.gridNominasParaDispersion;
			  $scope.catEstatusNomina = response.data.catEstatusNomina;

			  if($scope.usuarioDTO.rol.idRol===15){
			   $scope.idCatEstatusSelected = 25;
			  }else{
			  $scope.idCatEstatusSelected = 7;
			  }

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

	  $scope.cargaInicialXEstatus = function(idEstatus){
		  $scope.isVisibleSecciones = false;

		  $http.post(CONFIG.APIURL + "/ppp/finanzas/cargaInicialFinanzasXEstatus.json", idEstatus).then(function(response) {
			  $scope.gridNominasParaDispersion = response.data.gridNominasParaDispersion;
			  if($scope.gridNominasParaDispersion.length == 0){
				  $scope.idCatEstatusSelected = idEstatus;
			  }
			}, function(data) {
				console.log("error cargaInicialXEstatus--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al cargar los datos, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			});
	  };


	  $scope.cargaInicial();

	    $scope.getDatosNominaByIdNomina = function (nominaItem) {
	    	$scope.isVisibleSecciones = true;
	    	$scope.nominaProcesoSeleccionada = nominaItem;
	    	$scope.getDatosNominaEnProceso(nominaItem.idNomina);
	    	$scope.cargaInicialColaboradores(nominaItem.idNomina);
	    	$scope.getNominaComplemento(nominaItem.claveNomina);
	    	$scope.getDatoFactura(nominaItem.idNomina);

	    }

	    $scope.getDatosNominaEnProceso = function(idNomina){

	    	nominaService.getDatosNominaByIdNomina(idNomina, function(response) {

	    		if(response.data!=undefined && response.data !=null){

	    			$scope.isVisibleSeccionesNomina = true;
	    			$scope.nominaDto = response.data.nominaDto;
	    			$scope.nominaComplementoDto.nominaDto = response.data.nominaDto;
	    			$scope.nominaDto.fechaInicioNomina = new Date(response.data.nominaDto.fechaInicioNomina);
	    			$scope.nominaDto.fechaFinNomina = new Date(response.data.nominaDto.fechaFinNomina);
	    			$scope.comboConceptoFacturaCrm = response.data.comboConceptoFacturaCrm;


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



		$scope.cargarDatosNominaSeleccionada = function(claveNomina) {
        	nominaService.cargarDatosDespuesDeGuardarComplemento(claveNomina, function(response) {
        			$scope.nominaProcesoSeleccionada = response.data;

        			//Se actualiza de la lista el estatus de la nomina tambien
        			angular.forEach($scope.gridNominasParaDispersion,function (value,key) {
                   	  if(value.claveNomina === $scope.nominaProcesoSeleccionada.claveNomina){
                   		  //Se actualiza el estatus de la nomina
                   		$scope.gridNominasParaDispersion[key].catEstatusNomina = $scope.nominaProcesoSeleccionada.catEstatusNomina;
                   	  }
                     });
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
	      $scope.cargaInicialColaboradores = function (idNominaCliente) {
	    	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", idNominaCliente).then(function(response) {

	    	  		 $scope.dataExcel.contentRows= response.data;
	    	  		 $scope.totalNominaPPPColaboradores =  response.data[0].totalMontoPPP;
	    	  		 $scope.totalPPPColaboradores =  response.data[0].totalColaboradores;

	    	  		 if($scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP != null ||
	    					  $scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP != undefined){
	    	  		$scope.generarCveOrdenPagoColaboradores();
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

	      $scope.generarCveOrdenPagoColaboradores = function () {
	    	  if($scope.dataExcel.contentRows != null){
	    		  if($scope.dataExcel.contentRows[0].cveOrdenPago == null || $scope.dataExcel.contentRows[0].cveOrdenPago == undefined){

		  		var prefijoSTP = $scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP;
		  		if(prefijoSTP.includes("_") || prefijoSTP.includes("-")  || prefijoSTP.includes("/") || prefijoSTP.includes(".")
		  		 || prefijoSTP.includes("|")  || prefijoSTP.includes("!")  || prefijoSTP.includes("#")  || prefijoSTP.includes("$")
		  		 || prefijoSTP.includes("%")  || prefijoSTP.includes("&")  || prefijoSTP.includes("(")  || prefijoSTP.includes("*")
		  		 || prefijoSTP.includes(")")  || prefijoSTP.includes("=")  || prefijoSTP.includes("?")  || prefijoSTP.includes("'")
		  		 || prefijoSTP.includes("¿")  || prefijoSTP.includes("¡")  || prefijoSTP.includes("|")  || prefijoSTP.includes("°")){
		  			pinesNotifications.notify({
				        title: 'Error',
				        text: 'Para poder generar la orden de pago de los colaboradores, el campo prefijo STP, no puede contener caracteres especiales, por ejemplo _-/\*#$%&!|?¡¿()[]{}.;',
				        type: 'error'
				      });
		  			return;
		  		}
		  		var claveNominaCompleta = $scope.nominaProcesoSeleccionada.claveNomina;
		  		var claveNominaDividida = claveNominaCompleta.split("-",3);
		  		var claveNominaNumber = Number(claveNominaDividida[2]).toString();
		  		var consecutivo = 1;
		  		var cadenaNumerica = '0000';
		  		var resultado;
		  		for (var i = 0; i < $scope.dataExcel.contentRows.length; i++) {
		  			resultado = cadenaNumerica + consecutivo;
		  			resultado = resultado.substring(resultado.length - cadenaNumerica.length);
		  			var ordenPagoColaborador = prefijoSTP +claveNominaDividida[1]+ claveNominaNumber + resultado;
		  			$scope.dataExcel.contentRows[i].cveOrdenPago = ordenPagoColaborador;
		  			$scope.dataExcel.contentRows[i].idNomina = $scope.nominaProcesoSeleccionada.idNomina;
		  			consecutivo  = consecutivo + 1;
		  		}

		  		finanzasService
					.guardarColaboradores($scope.dataExcel.contentRows ,function(response) {
						if(response.data.mensajeError != undefined){
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: response.data.mensajeError,
						        type: 'error'
						      });
						}else{
								$log.debug('ok');

						}
						$scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
							},
							function(response) {
								$log.error(response.status+ ' - '+ response.statusText);
							});
	  		}else{
	  			// Se valida el estatus de stp con errores
		    	  if($scope.dataExcel.contentRows[0].idEstatus === 11){
		    		  var prefijoSTP = $scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP;
				  		if(prefijoSTP.includes("_") || prefijoSTP.includes("-")  || prefijoSTP.includes("/") || prefijoSTP.includes(".")
				  		 || prefijoSTP.includes("|")  || prefijoSTP.includes("!")  || prefijoSTP.includes("#")  || prefijoSTP.includes("$")
				  		 || prefijoSTP.includes("%")  || prefijoSTP.includes("&")  || prefijoSTP.includes("(")  || prefijoSTP.includes("*")
				  		 || prefijoSTP.includes(")")  || prefijoSTP.includes("=")  || prefijoSTP.includes("?")  || prefijoSTP.includes("'")
				  		 || prefijoSTP.includes("¿")  || prefijoSTP.includes("¡")  || prefijoSTP.includes("|")  || prefijoSTP.includes("°")){
				  			pinesNotifications.notify({
						        title: 'Error',
						        text: 'Para poder generar la orden de pago de los colaboradores, el campo prefijo STP, no puede contener caracteres especiales, por ejemplo _-/\*#$%&!|?¡¿()[]{}.;',
						        type: 'error'
						      });
				  			return;
				  		}
				  		var claveNominaCompleta = $scope.nominaProcesoSeleccionada.claveNomina;
				  		var claveNominaDividida = claveNominaCompleta.split("-",3);
				  		var claveNominaNumber = Number(claveNominaDividida[2]).toString();
				  		var cveOrdenPago = $scope.dataExcel.contentRows[$scope.dataExcel.contentRows.length - 1].cveOrdenPago;

				  		var totalCadena = prefijoSTP.length + claveNominaNumber.length;
				  		var consecutivo = parseInt(cveOrdenPago.substring(totalCadena,cveOrdenPago.length)) + parseInt(1);
				  		var cadenaNumerica = '0000';
				  		var resultado;
				  		for (var i = 0; i < $scope.dataExcel.contentRows.length; i++) {
				  			if($scope.dataExcel.contentRows[i].idEstatus === 11){
				  				resultado = cadenaNumerica + consecutivo;
				  				resultado = resultado.substring(resultado.length - cadenaNumerica.length);
				  			var ordenPagoColaborador = prefijoSTP + claveNominaDividida[1]+claveNominaNumber + resultado;
				  			$scope.dataExcel.contentRows[i].cveOrdenPago = ordenPagoColaborador;
				  			$scope.dataExcel.contentRows[i].idNomina = $scope.nominaProcesoSeleccionada.idNomina;
				  			consecutivo  = consecutivo + 1;
				  			}
				  		}

				  		finanzasService
							.guardarColaboradores($scope.dataExcel.contentRows ,function(response) {
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
									        title: 'Aviso',
									        text: 'Se han generado las nuevas claves de orden de pago, para los colaboradores que tenian el estatus ORDEN DE PAGO RECHAZADA STP',
									        type: 'success'
									      });
								}
								$scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);
									},
									function(response) {
										$log.error(response.status+ ' - '+ response.statusText);
									});
		    	  }
	      }

	    }
   }

	      $scope.generaRegistroSTP = function(list){
	    	  list[0].idNomina = $scope.idNomina;
	    	  list[0].nombreCentroCostos = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.nombreCentroCostos;
	    	  list[0].urlActual = window.location.hostname;
	    	  list[0].clabeCuentaOrdenante = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.clabeCuentaOrdenante;

	    	  bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas generar la orden de pago en el sistema STP?",
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
							$http.post(CONFIG.APIURL + "/ppp/finanzas/generaPagosSTP.json", list).then(function(response) {
					    		  if(response.data!=undefined && response.data !=null){
					    	  			pinesNotifications.notify({
					    	  				title: 'Archivo',
					       			        text: 'La generaci&oacute;n se realiz&oacute; exitosamente',
					       			        type: 'success'
					    			      });
					    		  }else{
					    			  pinesNotifications.notify({
					    			        title: 'Error',
					    			        text: 'Existio un error al generar el registro de pago, en el sistema STP',
					    			        type: 'error'
					    			      });
					    		  }

					    		  $scope.cargaInicialColaboradores($scope.idNomina);
//					    		  $scope.getDatosNominaEnProceso($scope.idNomina);
					    		  $scope.cargarDatosNominaSeleccionada($scope.nominaProcesoSeleccionada.claveNomina);

					  			}, function(data) {
					  				console.log("error generaRegistroSTP--> " + data);
					  				pinesNotifications.notify({
					  			        title: 'Error',
					  			        text: 'Existio un error al generar el registro de pago, en el sistema STP, favor de intentarlo nuevamente.',
					  			        type: 'error'
					  			      });

					  				$scope.cargaInicialColaboradores($scope.idNomina);
					  			});

						}
					}
				});
	      }

		    $scope.getNominaComplemento = function(nominaItem){
		    	nominaService.getDatosNominaComplemento(nominaItem, function(response) {

		    		if(response.data!=undefined && response.data !=null){

		    			if((response.data.idCMS!=null && response.data.idCMS!=undefined) && (response.data.nombreArchivo!=null && response.data.nombreArchivo!=undefined)){
		    				$scope.nombreArchivo = response.data.nombreArchivo;
		    			}

		    			$scope.isVisibleSeccionesNomina = true;
		    			$scope.nominaComplementoDto = response.data;

		    			if(response.data.fechaDispersion != null && response.data.fechaDispersion != undefined && response.data.fechaDispersion != ""){
		    				$scope.nominaComplementoDto.fechaDispersion =  new Date(response.data.fechaDispersion);
		    			}

		    			if(response.data.fechaTimbrado != null && response.data.fechaTimbrado != undefined && response.data.fechaTimbrado != ""){
		    				$scope.nominaComplementoDto.fechaTimbrado  =  new Date(response.data.fechaTimbrado);
		    			}

		    			if(response.data.fechaDispersion != null && response.data.fechaDispersion != undefined && response.data.fechaDispersion != ""){
		    				$scope.nominaComplementoDto.fechaFacturacion =  new Date(response.data.fechaFacturacion);
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

		    $scope.getDatoFactura = function(idNomina){
		    		nominaService.getDatosFacturaByIdNomina(idNomina, function(response) {

			    		if(response.data!=undefined && response.data !=null && response.data !="" && response.data !=undefined){

			    			$scope.factura = response.data;
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


			    $scope.dispersionStpColaborador = function (idNomina) {
			    	bootbox.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que desea realizar la dispersi\u00f3n de los colaboradores?",
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

								finanzasService.dispersionStpColaborador(idNomina, function(response) {

									if(!response.data){
										$log.error("no se pudo dispersar el colaborador");
										pinesNotifications.notify({
									        title: 'Error',
									        text: "Ocurrio un error en el sistema. Favor de intentarlo mas tarde",
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

												 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
												 $scope.cargaInicial();

											}
										});
									}
								},
								function(response) {
									$log.error("no se pudo dispersar el colaborador");
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error en el sistema. Favor de intentarlo mas tarde.',
								        type: 'error'
								      });

								});
							}
						}
					});
			      }


			      	$scope.enviarNominaCuentaConciliada = function() {
					  bootbox.confirm({
						  title : "Confirmar acci&oacute;n",
							message : "¿Est\u00e1s seguro de cambiar el estatus de la nómina a Cuenta conciliada?",
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

							    	  $http.post(CONFIG.APIURL + "/ppp/nominas/cambiaEstatusNominaCuentaConciliada.json", $scope.nominaProcesoSeleccionada.idNomina).then(
							                  function (response) {
							                	  $log.debug('ok');
													pinesNotifications.notify({
												        title: 'Mensaje',
												        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
												        type: 'success'
												      });

														 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
												         $scope.cargaInicial();
							                  },
							                  function (data) {
							                	  $log.error(data.status+ ' - '+ data.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error en el sistema. Favor de intentarlo más tarde.',
												        type: 'error'
												      });



														 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
												         $scope.cargaInicial();
							                  });
								}
							}
					  });
				}

				  $scope.modalRechazarDispersionStp = function (idPppNomina) {

					  bootbox.confirm({
						  title : "Confirmar acci&oacute;n",
							message : "¿Esta seguro de cancelar la dispersión?",
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
									  $scope.motivoRechazo = "";
									  $scope.idPppNomina = idPppNomina;
									  $('#modalRechazarDispersionStp').modal('show');
								}
							}
					  	});
		          }

			       $scope.cancelarDispersionStp = function (idNomina){

			    	   $scope.nominaDto = {
					        idNominaPPP : idNomina,
							motivoRechazo : $scope.motivoCancelacion
			              }

			    	   finanzasService.cambiaEstatusNominaRechazarDispersion($scope.nominaDto,function(response) {
						 		if(response.data.mensajeError != undefined || response.data.mensajeError != null){
							 			$scope.idNominaPPP = null;
							 			$('#modalRechazarDispersionStp').modal('hide');
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
											 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
											 $scope.cargaInicial();
										}
									});
		                        }else{
		                              $scope.idPppNomina = null;
							 		  $('#modalRechazarDispersionStp').modal('hide');
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
												 $scope.cargaInicial();

		  									}
		  								});
							  }
		                    }, function(response) {
		                        $log.error(response.status + ' - ' + response.statusText);
								 $scope.getDatosNominaByIdNomina($scope.nominaProcesoSeleccionada);
								 $scope.cargaInicial();
							});
			          }

			       $scope.rastreoStpColaborador = function(colaborador) {
						colaborador.nombreCentroCostos = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.nombreCentroCostos;
						colaborador.clabeCuentaOrdenante = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.clabeCuentaOrdenante;

						$http.post(CONFIG.APIURL	+ "/ppp/finanzas/restreoMovimieno.json",colaborador).then(function(response) {
										//		$("#actualizaColaboradores").trigger("click");
										$scope.cargaInicialColaboradores($scope.nominaProcesoSeleccionada.idNomina);

										pinesNotifications
										.notify({
											title : 'Actualización de estado dispersión',
											text : 'La operación se actualizó correctamente. ',
											type : 'success'
										});

										},
										function(data) {
											console.log("error rastreo  --> "
													+ data);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Por el momento el servicio de rastreo no se encuetra disponible, por favor intentelo más tarde. ',
														type : 'error'
													});
										});

					}

					$scope.consulta = function(colaborador) {
						colaborador.nombreCentroCostos = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.nombreCentroCostos;
						colaborador.clabeCuentaOrdenante = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.prestadoraServicioStpDto.clabeCuentaOrdenante;

						$http.post(CONFIG.APIURL + "/ppp/finanzas/consualtaOrdenes.json",colaborador).then(function(response) {
											$scope.data = resonse;

											pinesNotifications
													.notify({
														title : 'Actualización de estado dispersión',
														text : 'La operación se actualizó correctamente. ',
														type : 'success'
													});
										},
										function(data) {console.log("error rastreo  --> " + data);
											pinesNotifications.notify({
														title : 'Error',
														text : 'Por el momento el servicio de rastreo no se encuetra disponible, por favor intentelo más tarde. ',
														type : 'error'
													});
										});

					}
  });
