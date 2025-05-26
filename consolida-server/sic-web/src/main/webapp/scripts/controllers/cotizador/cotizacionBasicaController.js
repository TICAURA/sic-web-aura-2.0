'use strict';
angular.module('theme.core.templates')
        .controller('cotizacionBasicaController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {

        	
            $scope.objAux = [];
            //var data = {};
            $scope.cotizacionRealiada = false;
            $scope.cotizacionColaboradores = false;
            $scope.catalogosCotizadorDTO = {};
            $scope.empleadoDTO = {};
            $scope.idCotizacion;
            $scope.soloLectura = false;
            $scope.clienteTempBitacoraSolicitud = {};
            $scope.data = {};
            
            $scope.ejecutarCotizacion = function() {
            	if($scope.objAux.tipoDeMonto == 1){
            		$scope.empleadoDTO.netoNomina = $scope.objAux.valorTipoDeMonto;
            		$scope.empleadoDTO.salarioDiario = 0;
            	}else{
            		$scope.empleadoDTO.salarioDiario = ($scope.objAux.valorTipoDeMonto/30);
            		$scope.empleadoDTO.netoNomina = 0;
            	}
            	$scope.guardarValoresCotizacion();
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
                    $scope.actualizaNomFis();
            	}
            	
            }
            
            $scope.headerTable = {};
            var APIURL = '/sic-web'

            $scope.cotizacion = [];
            $scope.cargaInicial = function () {
                $http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosPreCotizador.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.catalogosCotizadorDTO = response.data;
                    $scope.catalogosCotizadorDTO.catTipoMonto =[
                        {
                        	idCatGeneral: 1,
                        	descripcion: 'Neto Nómina'
                        },
                        {
                        	idCatGeneral: 2,
                        	descripcion: 'Bruto Nómina'
                         }
                    ]
                }, function (response) {
                	pinesNotifications.notify({
     			        title: 'Error',
     			        text: 'Errror al obtener los catalogos',
     			        type: 'error'
     			      });
                    console.log("error --> " + response);
                });
                $http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosRealizarCotizador.json").then(function (response) {
                    console.log("response --> " + response);
                }, function (response) {

                    console.log("error --> " + response);
                });
            }
            $scope.actualizaNomFis = function () {
                $scope.cotizacion.porcentajeNomFis = 100 - $scope.cotizacion.porcentajePpp;
                $scope.cotizacion.dgporcCotizacionDeseado = $scope.cotizacion.porcentajePpp;
            }
            $scope.cargaCotizacion = function () {
                $http.post(CONFIG.APIURL + "/cotizador/cargaInicial.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.cotizacion = response.data;
                    $scope.idCotizacion = response.data.idCotizacion;
                    if($scope.idCotizacion!=null){
                    	$scope.soloLectura = true;
                    	//$scope.actualizaTipoCot();
                    	$scope.cotizacionColaboradores = true;
                    	$http.post(CONFIG.APIURL + "/cotizador/cargarColaboradores.json").then(
                        	function (response1) {
                        		if(response1.data === undefined || response1.data.contentRows.length === 0){
                        			$scope.cotizacionRealiada = false;
                        		}else{
                        			$scope.cotizacionRealiada = true;
                              		$scope.dataExcel = response1.data;
                                    $http.post(CONFIG.APIURL + "/cotizador/cargartotales.json").then(
                                        	function (response2) {
                                            	$scope.varCot=response2.data.resultado;
                                        	},function (error2) {
                                        		pinesNotifications.notify({
                                 			        title: 'Error',
                                 			        text: 'Errror al obtener la pre cotizaci&oacute;n',
                                 			        type: 'error'
                                 			      });
                                        	}
                                        );
                        		}
                        		$scope.objAux.tipoDeMonto = 1;
                        		$scope.objAux.valorTipoDeMonto = $scope.dataExcel.contentRows[0].netoNomina;
                             }, function (error1) {
                                 $scope.empleadoDTO = null;
                                 $scope.dataExcel = undefined;
                                 pinesNotifications.notify({
                  			        title: 'Error',
                  			        text: 'Error al obtener la pre cotizaci&oacute;n',
                  			        type: 'error'
                  			      });
                             }
                        );
                    }else{
                    	$scope.soloLectura = false;
                    	$scope.cotizacion.comisionImss = 10;
                        $scope.cotizacion.comisionPpp = 10;
                    }
                }, function (response) {
                	pinesNotifications.notify({
    			        title: 'Error',
    			        text: 'Errror al obtener la configuraci&oacute;n',
    			        type: 'error'
    			      });
                	
                });
            }

            $scope.cargaInicial();
            $scope.cargaCotizacion();

            
            
            $scope.cancelar = function () {
//                location.href = APIURL + "#/cotizador/cotizador";
                $location.path('/cotizador/cotizacionesCliente');   
            }
            $scope.inicializaVariablescotizacion = function(){
            	$scope.cotizacion.idVacaciones = {};
            	$scope.cotizacion.idTipo = {};
            	$scope.cotizacion.idZona = {};
            	$scope.cotizacion.idPeriodicidad = {};
            	$scope.cotizacion.idDias = {};
            	$scope.cotizacion.idImss = {};
            	$scope.cotizacion.idPrestaciones = {};
            }
            $scope.valoresDefaultPrecotizacion = function(){
            	$scope.cotizacion.aguinaldo = 15;
            	$scope.cotizacion.idVacaciones.idCatGeneral=1;
            	$scope.cotizacion.primaVacacional=25;
            	$scope.cotizacion.tieneProvedor=0;
            	$scope.cotizacion.feeActual=0;
            	$scope.cotizacion.idTipo.idCatGeneral=5;
            	$scope.cotizacion.idZona.idCatGeneral=4;
            	$scope.cotizacion.idPeriodicidad.idTipoPago=4;
            	$scope.cotizacion.idDias.idCatGeneral=8;
            	$scope.cotizacion.idImss.idCatGeneral=10;
            	$scope.cotizacion.idPrestaciones.idCatGeneral=11;
            }
            $scope.guardarValoresCotizacion = function () {
            	$scope.inicializaVariablescotizacion();
            	$scope.valoresDefaultPrecotizacion();
                $http.post(CONFIG.APIURL + "/cotizador/guardarValoresPreCotizacion.json", $scope.cotizacion).then(
                        function (response) {
                        	$scope.total = null;
                        	$scope.varCot = null;
                        	$scope.registrarColaborador();
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
            	$scope.dataExcel = null;
                $http.post(CONFIG.APIURL + "/cotizador/nuevaCotizacion/agregarColaborador.json",$scope.empleadoDTO).then(
	                function (response) {
	                	$scope.dataExcel = response.data;
	                	$http.post(CONFIG.APIURL + "/cotizadorFormulas/ejecutarCotizacion.json",$scope.dataExcel.contentRows).then(function(response){
	                		$http.post(CONFIG.APIURL + "/cotizador/obtenerTotalesCotizacion.json").then(function(response) {

		                        $scope.total = response.data;
		                        $scope.varCot=response.data.resultado;
		                        
		            			$scope.cotizacionRealiada = true;
		            			pinesNotifications.notify({
		    				        title: 'Pre cotizaci&oacute;n',
		    				        text: 'La pre cotizaci&oacute;n se genero exitosamente',
		    				        type: 'success'
		    				      });
	                            	$('html,body').animate({
	                            	    scrollTop: $("#idPanelCotizacion").offset().top
	                            }, 2000);
                			},function(response){
                				$log.debug("Error al realizar la sumatoria de los calculos "+ response);
                    			pinesNotifications.notify({
                 			        title: 'Error',
                 			        text: 'Error al realizar la pre cotizaci&oacute;n',
                 			        type: 'error'
                 			      });
                    		});
	            		},function(response){
	            			pinesNotifications.notify({
	        			        title: 'Error',
	        			        text: 'Errror al realizar la pre cotizaci&oacute;n',
	        			        type: 'error'
	        			      });
	            		});
	                }, function (data) {
	                    $scope.empleadoDTO = null;
	                    console.log("error --> " + data);
	                }
	            );
            };
            
            
            $scope.totales=[];
            $scope.varCot=[];
            
            
           $scope.valonesPositivos = function(valor) {
        	   return valor<0? valor * -1 : valor;
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
           
           $scope.solicitarCotizacion = function (){
           	if($scope.data != undefined){
           		$scope.clienteTempBitacoraSolicitud.archivo = $scope.data;
           	}
           	
               $http.post(CONFIG.APIURL + "/cotizador/guardarSolicitudCotizacion.json", $scope.clienteTempBitacoraSolicitud).then(
                       function (response) {
                    	   $location.path('/cotizador/cotizacionesCliente');   
                       },
                       function (data) {
                       	pinesNotifications.notify({
       				        title: 'Error',
       				        text: 'Ocurrio un error al solicitar la cotización, favor de verificar.',
       				        type: 'error'
       				      });
                       	$scope.init();
                       }
               );
           }
           $scope.fileChanged = function (documento) {
               var lstArchivos = documento.files;

               var val = lstArchivos[0].name.toLowerCase();
               var regex = new RegExp("(.*?)\.(xlsx)$");

               if (lstArchivos[0].size > 2097152) {
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
                   }
                   reader.readAsDataURL(lstArchivos[0]);
               }

           };
           
        });
