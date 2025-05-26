'use strict';
angular.module('theme.core.templates')
  .controller('autorizaOperacionController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, autorizaOperacionService, nominaService, pinesNotifications) {
	  
	  $scope.nominaComplementoDto={};
	  $scope.nominaProcesoSeleccionada = {};
	  
	  // documento excel
      $scope.data = {};
      $scope.listaColaboradores={};
      $scope.dataExcel = {};
      $scope.dataExcel.contentRows = {};
	  
	  $scope.cargaInicial = function(){
		 
		  $scope.isVisibleSeccionesNomina = false;
		  
		  //Carga Inicial de catalogos que utiliza la pagina
		  autorizaOperacionService.cargaInicialNominaParaAutorizarFinanciamiento(function(response) {
			  
			  $scope.gridNominasParaAutorizarFinanciamiento = response.data.gridNominasParaAutorizarFinanciamiento;

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
	  
	  $scope.cargaInicial();
	  
	  
	    $scope.getDatosNominaByIdNomina = function (nominaItem) {
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
	    
	      $scope.cargaInicialColaboradores = function (idNominaCliente) {
	    	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", idNominaCliente).then(function(response) {
	  			  
	    	  		 $scope.dataExcel.contentRows= response.data;
	    	  		 $scope.totalNominaPPPColaboradores =  response.data[0].totalMontoPPP;
	    	  		 $scope.totalPPPColaboradores =  response.data[0].totalColaboradores;
	    	  		 
	    	  		
	  			}, function(data) {
	  				console.log("error modalCrearNomina--> " + data);
	  				pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'Ocurrio un error al cargar los datos para la creacion de una nomina, favor de intentarlo nuevamente.',
	  			        type: 'error'
	  			      });
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
		    			 $scope.conceptos = response.data.conceptos;
		    			 
		    			 $scope.calcularTotales();
	      		  
		    			
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
		    
		    
			  
			  $scope.modalAutorizarFinanciamiento = function (idPppNomina) {
				  $scope.motivoRechazo = "";
				  $scope.idPppNomina = idPppNomina;
				  $scope.titulo = "Autorizar financiamiento";
				  $scope.esVisibleBotonRechazar = false;
				  $scope.esVisibleBotonAutorizar = true;
				  $('#modalRechazarFinanciamiento').modal('show');
	          }
		    
		 	  $scope.autorizarFinanciamiento = function(idPppNomina) {

		 		 $scope.nominaDto = {
					        idNominaPPP : idPppNomina,
							motivoRechazo : $scope.motivoRechazo
			              }
		 		  
					autorizaOperacionService.autorizarFinanciamientoOperaciones($scope.nominaDto,function(response) {
				 		if(response.data.mensajeError != undefined || response.data.mensajeError != null){
				 			$scope.idPppNomina = null;
                            $log.debug('Error al  autorizar financiamiento');
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
								$scope.cargaInicial();
							}
						});	
                        }else{
                          $scope.idPppNomina = null;
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
								
									$scope.cargaInicial();

									
								}
							});		
					  }
                    }, function(response) {

                        $log.error(response.status + ' - ' + response.statusText);
					});
				
			  };

		    		    
			  $scope.modalRechazarFinanciamiento = function (idPppNomina) {
				  $scope.motivoRechazo = "";
				  $scope.idPppNomina = idPppNomina;
				  $scope.titulo = "Rechazar financiamiento";
				  $scope.esVisibleBotonRechazar = true;
				  $scope.esVisibleBotonAutorizar = false;
				  $('#modalRechazarFinanciamiento').modal('show');
	          }

		       $scope.rechazarFinanciamiento = function (idPppNomina){

					$scope.nominaDto = {
							        idNominaPPP : idPppNomina,
									motivoRechazo : $scope.motivoRechazo
					              }
					
					autorizaOperacionService.rechazarFinanciamientoOperaciones($scope.nominaDto,function(response) {
								 		if(response.data.mensajeError != undefined || response.data.mensajeError != null){
								 			$scope.idNominaPPP = null;
								 			$('#modalRechazarFinanciamiento').modal('hide');
		                                    $log.debug('Error al  rechazar financiamiento');
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
		                                  $scope.idPppNomina = null;
								 		  $('#modalRechazarFinanciamiento').modal('hide');
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
		      									
		      										$scope.cargaInicial();

		      										
		      									}
		      								});		
		      						  }
		                            }, function(response) {

		                                $log.error(response.status + ' - ' + response.statusText);
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

  });