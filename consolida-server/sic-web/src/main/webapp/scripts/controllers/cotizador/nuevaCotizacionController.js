'use strict';
angular.module('theme')
        .controller('nuevaCotizacionController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {

            var data = {};
            $scope.variable={};
            $scope.isPromotor = false;
            $scope.cotizacionRealiada = false;
            $scope.cotizacionColaboradores = false;
            $scope.banderaErrorImplant = false;
            $scope.banderaErrorSgmm = false;
            $scope.banderaErrorSgmmImplant = false;
            $scope.catalogosCotizadorDTO = {};
            $scope.idCotizacion;
            $scope.cotizacion = {};
            $scope.inicializaVariablescotizacion = function(){
            	$scope.cotizacion.idVacaciones = {};
            	$scope.cotizacion.idTipo = {};
            	$scope.cotizacion.idZona = {};
            	$scope.cotizacion.idPeriodicidad = {};
            	$scope.cotizacion.idDias = {};
            	$scope.cotizacion.idImss = {};
            	$scope.cotizacion.idImss.idCatGeneral = "";
            	$scope.cotizacion.idPrestaciones = {};
            	$scope.cotizacion.idTipoCotizacion={};
            }
            $scope.inicializaVariablescotizacion();
            $scope.observacionAutorizador={};
            $scope.cotizacion.costoAdicional={};
            $scope.cotizacion.costoAdicional.idPorcentajePromotor={};
            $scope.cotizacion.costoAdicional.idCatValorDefault = null

            $scope.headerTable = {};
            var APIURL = '/sic-web'

            	
            	
            $scope.inicializarporcentajesRentavilidad = function(){
            	if($scope.cotizacion.costoAdicional.porcentajeClienteImplant == undefined || $scope.cotizacion.costoAdicional.porcentajeClienteImplant == null){
	           		 $scope.cotizacion.costoAdicional.porcentajeClienteImplant = 0;
	           	 }
            	if($scope.cotizacion.costoAdicional.porcentajePromotorImplant == undefined || $scope.cotizacion.costoAdicional.porcentajePromotorImplant == null){
	           		 $scope.cotizacion.costoAdicional.porcentajePromotorImplant = 0;
	           	 }
            	if($scope.cotizacion.costoAdicional.porcentajeCorporativoImplant == undefined || $scope.cotizacion.costoAdicional.porcentajeCorporativoImplant == null){
	           		 $scope.cotizacion.costoAdicional.porcentajeCorporativoImplant = 0;
	           	 }
            	
            	
            	if($scope.cotizacion.costoAdicional.porcentajeClienteSgmm == undefined || $scope.cotizacion.costoAdicional.porcentajeClienteSgmm == null){
	           		 $scope.cotizacion.costoAdicional.porcentajeClienteSgmm = 0;
	           	 }
            	if($scope.cotizacion.costoAdicional.porcentajePromotorSgmm == undefined || $scope.cotizacion.costoAdicional.porcentajePromotorSgmm == null){
	           		 $scope.cotizacion.costoAdicional.porcentajePromotorSgmm = 0;
	           	 }
            	if($scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm == undefined || $scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm == null){
	           		 $scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm = 0;
	           	 }
            }
            $scope.sumarIMPLANT = function(){
            	return parseInt($scope.cotizacion.costoAdicional.porcentajeClienteImplant ==""?"0":$scope.cotizacion.costoAdicional.porcentajeClienteImplant)
            	+ parseInt($scope.cotizacion.costoAdicional.porcentajePromotorImplant ==""?"0":$scope.cotizacion.costoAdicional.porcentajePromotorImplant)
            	+ parseInt($scope.cotizacion.costoAdicional.porcentajeCorporativoImplant ==""?"0":$scope.cotizacion.costoAdicional.porcentajeCorporativoImplant);
            }
            $scope.sumarSGMM = function(){
            	return parseInt($scope.cotizacion.costoAdicional.porcentajeClienteSgmm ==""?"0":$scope.cotizacion.costoAdicional.porcentajeClienteSgmm)
            	+ parseInt($scope.cotizacion.costoAdicional.porcentajePromotorSgmm ==""?"0":$scope.cotizacion.costoAdicional.porcentajePromotorSgmm)
            	+ parseInt($scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm ==""?"0":$scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm);
            }
            
            $scope.validaMontoSGMM = function() {
            	if($scope.cotizacion.costoAdicional.montoSgmm !=null && $scope.cotizacion.costoAdicional.montoSgmm >0){
            		$scope.validaSGMM = true;
            	} else {
            		$scope.cotizacion.costoAdicional.porcentajeClienteSgmm = 0;
	   	           	$scope.cotizacion.costoAdicional.porcentajePromotorSgmm = 0;
	   	           	$scope.cotizacion.costoAdicional.porcentajeCorporativoSgmm = 0;
            		$scope.validaSGMM = false;
            	}
            }
            $scope.validaMontoImplant = function() {
            	if($scope.cotizacion.costoAdicional.montoImplant !=null && $scope.cotizacion.costoAdicional.montoImplant >0){
            		$scope.validaImplant = true;
            	} else {
            		$scope.cotizacion.costoAdicional.porcentajeClienteImplant = 0;
      	           	$scope.cotizacion.costoAdicional.porcentajePromotorImplant = 0;
      	           	$scope.cotizacion.costoAdicional.porcentajeCorporativoImplant = 0;
            		$scope.validaImplant =  false;
            	}
            }
            
            $scope.validaCostosAdicionales = function() {
            	$scope.banderaErrorImplant = false;
            	$scope.banderaErrorSgmm = false;
            	$scope.banderaErrorSgmmImplant = false;
            	var bandera = false
            	if($scope.cotizacion.costoAdicional.montoImplant >0 ||  $scope.cotizacion.costoAdicional.montoSgmm >0){
            		if($scope.cotizacion.costoAdicional.montoImplant >0){
            			var implant = $scope.sumarIMPLANT();
            			if(implant != 100){
            				$scope.banderaErrorImplant = true;
            				bandera =  true;
            			}
            		}
            		
            		if( $scope.cotizacion.costoAdicional.montoSgmm >0){
            			var sgmm = $scope.sumarSGMM();
	            		if(sgmm != 100){
	            			$scope.banderaErrorSgmm = true;
	            			bandera = true;
	        			}
            		}
            		if(bandera){
            			return bandera;
            		}
            	} else {
            		$scope.banderaErrorSgmmImplant = true;
            		return true;
            	}
            }
                     
            $scope.cargaInicial = function () {
            	$http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosCotizador.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.catalogosCotizadorDTO = response.data;
                }, function (response) {

                    console.log("error --> " + response);
                });
            	
            	$http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosRealizarCotizador.json").then(function (response) {
                    console.log("response --> " + response);
                }, function (response) {

                    console.log("error --> " + response);
                });
            	$http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
      	            $scope.usuarioAplicativo = data.data;
      	            if($scope.usuarioAplicativo.canalVentaDto!=null){
      	            	$scope.isPromotor = true;
      	            }
                },function(data){
                  console.log("error --> " + data);
                });
            	
            }
            
            $scope.cargaCotizacion = function () {
                $http.post(CONFIG.APIURL + "/cotizador/cargaInicial.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.cotizacion = response.data;
                    $scope.idCotizacion = response.data.idCotizacion; 
                    if($scope.cotizacion.tieneCostosAdicionales == 1){
                    	$scope.cotizacion.tieneCostosAdicionales = true;
                    } else{
                    	$scope.cotizacion.tieneCostosAdicionales = false;
                    }
                    $scope.inicializarporcentajesRentavilidad();
                    if($scope.idCotizacion!=null){
                    	//$scope.actualizaTipoCot();
                    	$scope.cotizacionColaboradores = true;
                    	$http.post(CONFIG.APIURL + "/cotizador/cargarColaboradores.json").then(
                        	function (response1) {
                        		if(response1.data === undefined || response1.data.contentRows.length === 0){
                        			$scope.cotizacionRealiada = false;
                        		}else{
                        			$scope.cotizacionRealiada = true;
                              		$scope.dataExcel = response1.data;
                                    $scope.headerTable = $scope.dataExcel[0];
                                    $http.post(CONFIG.APIURL + "/cotizador/cargartotales.json").then(
                                        	function (response2) {
                                        		$scope.total = response2.data;
                                            	$scope.varCot=response2.data.resultado;
                                        	},function (error2) {
                                        		pinesNotifications.notify({
                                 			        title: 'Error',
                                 			        text: 'Errror al obtener la cotizaci&oacute;n',
                                 			        type: 'error'
                                 			      });
                                        	}
                                        );
                        		}
                             }, function (error1) {
                                 $scope.empleadoDTO = null;
                                 $scope.dataExcel = undefined;
                                 pinesNotifications.notify({
                  			        title: 'Error',
                  			        text: 'Error al obtener la cotizaci&oacute;n',
                  			        type: 'error'
                  			      });
                             }
                        );
                    } else {
                    	$scope.inicializaVariablescotizacion();
                        $scope.cotizacion.idZona.idCatGeneral = 4;
                        $scope.cotizacion.idPeriodicidad.idTipoPago = 4;
                        $scope.cotizacion.idDias.idCatGeneral = 8;
                        $scope.cotizacion.tieneProvedor = 1;
                    }
                }, function (error) {
                	pinesNotifications.notify({
     			        title: 'Error',
     			        text: 'Error al obtener la configuraci&oacute;n',
     			        type: 'error'
     			      });
                });
            }
            

            $scope.cargaInicial();
            $scope.cargaCotizacion();

            $scope.actualizaNomFis = function () {
                $scope.cotizacion.porcentajeNomFis = 100 - $scope.cotizacion.porcentajePpp;
                $scope.cotizacion.dgporcCotizacionDeseado = $scope.cotizacion.porcentajePpp;
            }
            $scope.actualizaTipoCot = function (){
            	if($scope.cotizacion.idTipoCotizacion.idCatGeneral == 15){//Monto
            		$scope.cotizacion.porcentajePpp = 0;
            		$scope.cotizacion.porcentajeNomFis = 0;
            		$scope.cotizacion.dgporcCotizacionDeseado = 0;
            		$scope.cotizacion.dgVSM=0;
            	}else if($scope.cotizacion.idTipoCotizacion.idCatGeneral == 16){//VSM
            		$scope.cotizacion.porcentajePpp = 0;
            		$scope.cotizacion.porcentajeNomFis = 0;
            		$scope.cotizacion.dgporcCotizacionDeseado = 0;
            		$scope.cotizacion.dgMontoBrutoMensual = 0;
            	}else{//Porcentaje
            		$scope.cotizacion.dgMontoBrutoMensual = 0;
            		$scope.cotizacion.dgVSM=0;
            		$scope.cotizacion.porcentajePpp = 100;
            		if ($scope.cotizacion.idImss.idCatGeneral == 53){
            			$scope.cotizacion.porcentajePpp = 90;
            		}
                    $scope.actualizaNomFis();
            	}
            	
            }
            $scope.seleccionaTipoProducto = function (){
            	if($scope.cotizacion.idImss.idCatGeneral==9){//PPP
            		$scope.variable.habilitaTipoCot = false;
            		$scope.variable.habilitaRequired= false;
            		$scope.cotizacion.idTipoCotizacion.idCatGeneral = 25;
                	$scope.cotizacion.dgMontoBrutoMensual = 0;
            		$scope.cotizacion.dgVSM=0;
            		$scope.cotizacion.porcentajePpp = 100;
            		$scope.cotizacion.comisionImss = null;
            		$scope.cotizacion.comisionPpp = null;
                    $scope.actualizaNomFis();
            	}else{
            		$scope.variable.habilitaTipoCot = true;
            		if($scope.cotizacion.idImss.idCatGeneral==53){
            			$scope.variable.habilitaRequired= false;
            		}else{
            			$scope.variable.habilitaRequired= true;
            		}
            		$scope.cotizacion.idTipoCotizacion = {};
            		$scope.cotizacion.comisionImss = null;
            		$scope.cotizacion.comisionPpp = null;
            	}
            	
            }
            var passTOJson = function (data) {

                var json = JSON.stringify(data, function (key, value) {
                    if (key === ",") {
                        return undefined;
                    }

                    return value;
                });

                return json;
            };



            $scope.mostrarDialogo = function () {
                $('#cargarDocumento').modal('show');
            }

            $scope.cerrar = function () {
                $('#cargarDocumento').modal('hide');
            }

            $scope.mostrarAgregarColaborador = function () {
            	$scope.empleadoDTO = {};
            	$scope.empleadoDTO.dgPrimaDeRiesgo="I";
            	$scope.variable.tipoSalario = 1;
                $('#agregarColaborador').modal('show');
            }

            $scope.cerrarAgregarColaborador = function () {
                $('#agregarColaborador').modal('hide');
            }
            
            $scope.cerrarModalAutorizador = function () {
                $('#noAutorizar').modal('hide');
            }
            
     
            /// Metodos para cargar documento de excel
            $scope.data = {};
            $scope.dataExcel = undefined;


            $scope.fileChanged = function (documento) {
                var lstArchivos = documento.files;

                var val = lstArchivos[0].name.toLowerCase();
                var regex = new RegExp(".(xls|xlsx)$");

                if (!(regex.test(val))) {
                    $(this).val('');
                    $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión .xlsx";
                    alert($scope.mensaje);
                    return;
                } else if (lstArchivos[0].size > 2097152) {
                    $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
                    $scope.$apply();
                    alert($scope.mensaje);
                } else {
                    var reader = new FileReader();
                    reader.onloadend = function () {
                        $log.debug("Archivo cargado memoria");
                        $scope.data.archivo = reader.result;
                        $scope.data.nombreArchivo = lstArchivos[0].name;
                        $scope.data.tamanioArchivo = lstArchivos[0].size;
                        $scope.registrarDocumento();
                    }
                    reader.readAsDataURL(lstArchivos[0]);
                }

            };

            $scope.registrarDocumento = function () {

                $http.post(CONFIG.APIURL + "/archivo/xlsFile.json", $scope.data).then(function (response) {
                	if(response.data.errorCargalayout!=null){
                		$scope.cerrar();
                		var mensajeError = response.data.errorCargalayout
                		return pinesNotifications.notify({
         			        title: 'Error',
         			        text: mensajeError,
         			        type: 'error'
         			      });
                	}
                	
                	/*********************************************************************************************/
                	$http.post(CONFIG.APIURL + "/cotizadorFormulas/obtenerSalarios.json", response.data.contentRows).then(function (response1) {
                		$scope.dataExcel = response1.data;
                        $scope.headerTable = $scope.dataExcel[0];
                        if(response1.status = 200){
                        	$scope.cerrar();
                        	pinesNotifications.notify({
             			        title: 'Cotizaci&oacute;n',
             			        text: 'La carga del layout se realiz&oacute; exitosamente',
             			        type: 'success'
             			      });
                        }

                        // /Boton cerrar de modal de carga de documentos
                        $scope.cerrar();
                	}, function(response){
                		pinesNotifications.notify({
         			        title: 'Error',
         			        text: 'Error al obtener los datos', 
         			        type: 'error'
         			      });
                	});
                	/*********************************************************************************************/
                }, function (data) {
                    $scope.dataExcel = undefined;
                    console.log("error --> " + data);
                });
            }
            /// Termina Metodos para cargar documento de excel    


            $scope.cancelar = function () {
            	$location.path('/cotizador/cotizador')
//                location.href = APIURL + "#/cotizador/cotizador";
            }  

            $scope.guardarValoresCotizacion = function () {
            	if($scope.cotizacion.tieneCostosAdicionales && $scope.validaCostosAdicionales()){
            		pinesNotifications.notify({
     			        title: 'Error',
     			        text: 'Faltan campos por ingresar, favor de verificar',
     			        type: 'error'
     			      });
            		return ;
            	}
            	
            	$scope.cotizacion.costoAdicional.idValorTimbre = $scope.catalogosCotizadorDTO.catValorTimbrado;
        		$scope.cotizacion.costoAdicional.idValoSpei = $scope.catalogosCotizadorDTO.catValorSpei;
        		$scope.cotizacion.costoAdicional.idPorcentajeCostoEstrategia = $scope.catalogosCotizadorDTO.catPorcEstrategia;
        		$scope.cotizacion.costoAdicional.idPorcentajeCostosIndirectos = $scope.catalogosCotizadorDTO.catPorcIndirectos;
        		$scope.cotizacion.costoAdicional.idPorcentajePromotorImss = $scope.catalogosCotizadorDTO.catPorcPromotorImss;
        		if($scope.cotizacion.costoAdicional.idPorcentajePromotor==null || $scope.cotizacion.costoAdicional.idPorcentajePromotor.idCatValorDefault == null){
        			$scope.cotizacion.costoAdicional.idPorcentajePromotor = $scope.catalogosCotizadorDTO.catPorcPromotor;
        		}
//        		$scope.cotizacion.costoAdicional.idPorcentajePromotor = $scope.catalogosCotizadorDTO.catPorcPromotor;
            	
        		
            	if($scope.cotizacion.tieneCostosAdicionales == true){
            		$scope.cotizacion.tieneCostosAdicionales = 1;
                } else{
                	$scope.cotizacion.tieneCostosAdicionales = 0;
                }
                $http.post(CONFIG.APIURL + "/cotizador/guardarValoresCotizacion.json", $scope.cotizacion).then(
                        function (response) {
                        	$scope.cotizacionColaboradores = true;
                        	$scope.cargaCotizacion();
                        	pinesNotifications.notify({
             			        title: 'Cotizaci&oacute;n',
             			        text: 'La configuraci&oacute;n se guardo exitosamente',
             			        type: 'success'
             			      });
                        	
                        	$('html,body').animate({
                        	    scrollTop: $("#idPanelColaboradores").offset().top
                        	}, 2000);

                        }, function (data) {
                        	pinesNotifications.notify({
             			        title: 'Error',
             			        text: 'Faltan campos por ingresar, favor de verificar',
             			        type: 'error'
             			      });
                }
                );
            }

            $scope.registrarColaborador = function () {
                if ($scope.dataExcel != undefined) {
                    $scope.empleadoDTO.colaboradoresGuardados = $scope.dataExcel.contentRows;
                }
                $http.post(CONFIG.APIURL + "/cotizador/nuevaCotizacion/agregarColaborador.json",$scope.empleadoDTO).then(
	                function (response) {
	                    if ($scope.dataExcel != undefined) {
	                        $scope.dataExcel.contentRows = response.data.contentRows;
	                    } else {
	                        $scope.dataExcel = response.data;
	                        $scope.headerTable = $scope.dataExcel[0];
	                    }
	                    
	                    $scope.cerrarAgregarColaborador();
	                }, function (data) {
	                    $scope.empleadoDTO = null;
	                    console.log("error --> " + data);
	                }
	            );
            };
            
            
            $scope.totales=[];
            $scope.varCot=[];
            $scope.ejecutarCotizacion = function() {
            	if ($scope.dataExcel != undefined) {
            		
            		$http.post(CONFIG.APIURL + "/cotizador/limpiarEjecutarCotizacion.json",$scope.dataExcel.contentRows).then(function(response) {
            		$scope.totalPaginacion = 1;
            		if($scope.dataExcel.contentRows.length > 20 && $scope.dataExcel.contentRows.length < 40) {
            			$scope.totalPaginacion = 5;
            		}else if($scope.dataExcel.contentRows.length > 40 && $scope.dataExcel.contentRows.length < 80) {
            			$scope.totalPaginacion = 10;
            		}else if($scope.dataExcel.contentRows.length > 80 && $scope.dataExcel.contentRows.length < 100) {
            			$scope.totalPaginacion = 20;
            		}else if($scope.dataExcel.contentRows.length > 100){
            			$scope.totalPaginacion = 50;
            		}
        			var primerElemento = 0;
        			var ultimo = 0;
        			/* cuantas veces se tiene que hacer el ciclo con decimales*/
        			$scope.totalCiclos = $scope.dataExcel.contentRows.length / $scope.totalPaginacion; 
        			/*se redondea el valor para que quede en entero*/
        			$scope.totalCiclos = Math.ceil($scope.totalCiclos);
        			$scope.totalRespuestasBackend= 0;
        			$log.debug("Numero de colaboradores "+ $scope.dataExcel.contentRows.length);
        			$log.debug("Numero de ciclos "+ $scope.totalCiclos);
        			for (var i = 0 ; i < $scope.totalCiclos; i++) {
        				ultimo = ultimo + $scope.totalPaginacion;
        				var sublista = $scope.dataExcel.contentRows.slice(primerElemento,ultimo);
        			
            		$http.post(CONFIG.APIURL + "/cotizadorFormulas/ejecutarCotizacion.json",sublista).then(function(response) {
            			$scope.totalRespuestasBackend = $scope.totalRespuestasBackend +1;
            			$log.debug("totalRespuestasBackend "+ $scope.totalRespuestasBackend);
            			if($scope.totalRespuestasBackend ===  $scope.totalCiclos ) {
            				$http.post(CONFIG.APIURL + "/cotizador/obtenerTotalesCotizacion.json").then(function(response) {
    	            			$scope.total = response.data;
    	                        $scope.varCot=response.data.resultado;
    	            			$scope.cotizacionRealiada = true;
    	            			pinesNotifications.notify({
    	         			        title: 'Cotizaci&oacute;n',
    	         			        text: 'La cotizaci&oacute;n se genero exitosamente',
    	         			        type: 'success'
    	         			      });
    	            			$('html,body').animate({
    	                    	    scrollTop: $("#idPanelCotizacion").offset().top
    	                    	}, 2000);
                			},function(response){
                				$log.debug("Error al realizar la sumatoria de los calculos "+ response);
                    			pinesNotifications.notify({
                 			        title: 'Error',
                 			        text: 'Error al realizar la cotizaci&oacute;n',
                 			        type: 'error'
                 			      });
                    		});
						}
            		},function(response) {
            			$log.debug("Error al ejecutar el calculo de la cotizacion "+ response);
						$scope.totalRespuestasBackend = $scope.totalRespuestasBackend +1;
							if($scope.totalRespuestasBackend ===  $scope.totalCiclos ) {
								pinesNotifications.notify({
		         			        title: 'Error',
		         			        text: 'Error al realizar la cotizaci&oacute;n',
		         			        type: 'error'
		         			      });
							}
            		});
            		primerElemento = ultimo;
            		}
            		},function(response){//Limpiar ejecutarCotizacion
            			$log.debug("Error al Borrar datos "+ response);
            			pinesNotifications.notify({
         			        title: 'Error',
         			        text: 'Error al realizar la cotizaci&oacute;n',
         			        type: 'error'
         			      });
            		});
            	}//cierra condicion lista diferente de undefined
            }
            
           $scope.valonesPositivos = function(valor) {
//        	   return valor<0? valor * -1 : valor;
        	   return valor;
           }
           $scope.formatoDecimal = function(valor) {
            	  // si no es un número devuelve el valor, o lo convierte a número con 2 decimales
        	   valor = isNaN(valor) ? valor : parseFloat(valor).toFixed(2);
        	   return valor;
            }
           
           $scope.printDiv = function () {
        	   $("#idPrint").print({
        		   globalStyles : true,
        		   mediaPrint : true,
        		   stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
        		   iframe : true,
        		   noPrintSelector : ".avoid-this",
        		   append : "<br/>",
        		   prepend : "<br/>",
        		   manuallyCopyFormValues: true,
        		   deferred: $.Deferred(),
        		   timeout: 250,
        		   title: "Resultado",
        		   doctype: '<!doctype html>'
        		   });
        	   }
           $scope.printDiv2 = function () {
        	   $("#idPrint2").print({
        		   globalStyles : true,
        		   mediaPrint : true,
        		   stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
        		   iframe : true,
        		   noPrintSelector : ".avoid-this",
        		   append : "<br/>",
        		   prepend : "<br/>",
        		   manuallyCopyFormValues: true,
        		   deferred: $.Deferred(),
        		   timeout: 250,
        		   title: "Resultado",
        		   doctype: '<!doctype html>'
        		   });
        	   }
           
           $scope.solicitarAutorizacionCotizacion = function(data){
        	   $http.post(CONFIG.APIURL + "/cotizador/solicitarAutorizacionCotizacion.json", data.idCotizacion).then(function(response){
        		   $log.debug('ok');
					pinesNotifications.notify({
				        title: 'Mensaje',
				        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
				        type: 'success'
				      });
        		   $location.path('/cotizador/cotizaciones');
        	   },function(response){
        		   $log.debug('Ocurrio un error al solicitar la autorizacion en la cotizacion metodo: solicitarAutorizacionCotizacion');
        		   pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al solicitar la autorización, favor de intentarlo nuevamente.',
				        type: 'error'
				      });
    		});
        	   
           }
           
           
           
           
           
           
           ///Metodos para autorizacion
           
           $scope.cerrarModalAutorizador = function () {
               $('#noAutorizar').modal('hide');
           }
           
           
           $scope.guardarObservacionAutorizador = function (cotizacion){
           	$scope.observacionAutorizador.observacionAutorizador = cotizacion.observacionAutorizador;
           	$scope.observacionAutorizador.idCotizacion = $scope.idCotizacion;
               $http.post(CONFIG.APIURL + "/cliente/guardarObservacionAutorizador.json", $scope.observacionAutorizador).then(
                       function (response) {
//                       	location.href = '/sic-web#/cotizador/solicitudAutorizaciones';
                    	   pinesNotifications.notify({
       				        title: 'Mensaje',
       				        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
       				        type: 'success'
       				      });
                       	$location.path('/cotizador/solicitudAutorizaciones');
                       },
                       function (data) {
                           alert("Ocurrio un error al guardar la observacion, favor de verificar. ")
                           pinesNotifications.notify({
       				        title: 'Error',
       				        text: 'Ocurrio un error al guardar la observación, favor de intentarlo nuevamente.',
       				        type: 'error'
       				      });
                       }
               );
           }
           
           
           $scope.autorizarCotizacion = function (){
           	bootbox
       		.confirm({
       			title : "Confirmar acci&oacute;n",
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
//                       	location.href = '/sic-web#/cotizador/solicitudAutorizaciones';
                    	   $log.debug('ok');
							pinesNotifications.notify({
						        title: 'Mensaje',
						        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
						        type: 'success'
						      });
                       	$location.path('/cotizador/solicitudAutorizaciones');
                       },
                       function (data) {
                           alert("Ocurrio un error al solicitar la cotizaci&oacute;n, favor de verificar. ")
                           pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al autorizar, favor de intentarlo nuevamente.',
						        type: 'error'
						      });
                       }
               );
       				}	
           }
       		});
           }
           
           $scope.descargar = function (){
        	   $http.post(CONFIG.APIURL + "/cotizador/generarReporteCotizacion.json").then(
                       function (response) {
                    	   if(response.data.documento != undefined){
                    		   var link = document.createElement("a");
                    		   link.href = 'data:application/pdf;base64,' + encodeURIComponent(response.data.documento);
                    		   link.style = "visibility:hidden";
                    		   link.download = "Reporte.pdf";
                    		   document.body.appendChild(link);
                    		   link.click();
                    		   document.body.removeChild(link);
                    	   }else{
                    		   pinesNotifications.notify({
                			        title: 'Error',
                			        text: 'Error al Realizar el reporte',
                			        type: 'error'
                			      });
                    	   }
            },function(data) {
                console.log("oops");
            });
           }


           $scope.limpiarObservacion = function(){
        	   $scope.cotizacion.observacionAutorizador = "";
           }
           
           $scope.gridColaboradores = {
        		    data: 'dataExcel.contentRows',
        		    enableSorting: true,
        		    enableRowSelection: true,
        		    multiSelect: false
   	         };
           $scope.gridColaboradores.columnDefs  = [
        	   {
           	   	field : 'salarioDiario | number',
  	            	displayName : 'Salario diario',
  	            	width : '20%',
  	            	cellClass: 'numberTables'
              },{
           	   	field : 'gravados | number',
 	            	displayName : 'Gravados',
 	            	width : '10%',
 	            	cellClass: 'numberTables'
           	},{
           		field : 'exentos | number',
 	            	displayName : 'Exentos',
 	            	width : '10%',
 	            	cellClass: 'numberTables'
           	},{
           		field : 'fechaAntiguedad | date:"dd/MM/yyyy" ',
 	            	displayName : 'Fecha de antiguedad',
 	            	width : '10%'
           	},{
           		field : 'netoNomina | number',
 	            	displayName : 'Neto nomina',
 	            	width : '12%',
 	            	cellClass: 'numberTables'
           	},{
           		field : 'asimilados | number',
 	            	displayName : 'Asimilados',
 	            	width : '12%',
 	            	cellClass: 'numberTables'
           	},{
           		field : 'aOtros | number',
 	            	displayName : 'Otros',
 	            	width : '12%',
 	            	cellClass: 'numberTables'
           	},{
           		field : 'dgPrimaDeRiesgo',
 	            	displayName : 'Prima de riesgo',
 	            	width : '17%',
 	            	cellClass: 'centerTxtTables'
           	}
           ]; 
           
        });
