'use strict';
angular.module('theme.core.templates')
  .controller('seguimientoNominaController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, finanzasService,seguimientoNominasService, nominaService, pinesNotifications,NgTableParams ) {


	  $scope.isVisibleSecciones = false;
	  $scope.isVisibleMensajeSinNominas = false;

	  $scope.nominaComplementoDto={};
	  $scope.nominaProcesoSeleccionada = {};
	  $scope.seguimientoNominaDto = {}
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

		  seguimientoNominasService.cargaInicial(function(response) {

			  $scope.gridNominasParaDispersion = response.data.gridNominasParaDispersion;
			    $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridNominasParaDispersion});
			  $scope.catEstatusNomina = response.data.catEstatusNomina;
			  $scope.idCatEstatusSelected = 7;


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




	  $scope.buscarNominas = function(){
		  $scope.isVisibleSecciones = false;

		  $http.post(CONFIG.APIURL + "/ppp/seguimientoNomina/buscarNominas.json", $scope.seguimientoNominaDto).then(function(response) {
			  $scope.gridNominasParaDispersion = response.data.gridNominasParaDispersion;
			    $scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridNominasParaDispersion});

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

	  $scope.limparCampos = function(){
		  $scope.isVisibleSecciones = false;
		  $scope.seguimientoNominaDto = {}

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

	      $scope.cargaInicialColaboradores = function (idNominaCliente) {
	    	  	$http.post(CONFIG.APIURL + "/ppp/nominas/cargaInicialColaboradores.json", idNominaCliente).then(function(response) {

	    	  		 $scope.dataExcel.contentRows= response.data;
	    	  		 $scope.totalNominaPPPColaboradores =  response.data[0].totalMontoPPP;
	    	  		 $scope.totalPPPColaboradores =  response.data[0].totalColaboradores;

	    	  		 if($scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP != null ||
	    					  $scope.nominaProcesoSeleccionada.nominaClienteDto.clienteDto.prefijoSTP != undefined){
	    	  	//	$scope.generarCveOrdenPagoColaboradores();
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


	      $scope.generaRegistroSTP = function(list){
	    	  list[0].idNomina = $scope.idNomina;
	    	  list[0].nombreCentroCostos = $scope.nominaProcesoSeleccionada.prestadoraServicioDto.nombreCentroCostos;

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

					    		  $scope.cargaInicial();

					  			}, function(data) {
					  				console.log("error generaRegistroSTP--> " + data);
					  				pinesNotifications.notify({
					  			        title: 'Error',
					  			        text: 'Existio un error al generar el registro de pago, en el sistema STP, favor de intentarlo nuevamente.',
					  			        type: 'error'
					  			      });

					  				 $scope.cargaInicial();
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
		    			 $scope.conceptos = response.data.conceptos;

		    			// $scope.calcularTotales();


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



			   $scope.colorNomina = function (listaNomina) {
			         $scope.listaNominaColor=listaNomina;
			        for(var i=0; i<listaNomina.length; i++){
			        if(listaNomina[i].catEstatusNomina.idCatGeneral == 1){
			        listaNominaColor[i].colorNomina= "circuloamarillo.jpg";
			        }else{
			          listaNominaColor[i].colorNomina= "circuloMorado.jpg";
			         }
			         }
			        return listaNominaColor;


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
