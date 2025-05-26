'use strict';
angular.module('theme.core.templates')
        .controller('cotizacionesClienteController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications) {
            $scope.cliente;
            $scope.clienteTempBitacoraSolicitud={};
            $scope.idClienteTemp;
            $scope.modalBitNuevo = false;
            $scope.data = {};
            $scope.mostrarBtnCotizacion=false;
            $scope.mostrarBtnPreCotizacion=false;
            
      	  $scope.validaRol = function(){
      		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
      	            $scope.usuarioAplicativo = data.data;
      	            if($scope.usuarioAplicativo.canalVentaDto!=null){
      	            	$scope.mostrarBtnCotizacion = false;
      	            	$scope.mostrarBtnPreCotizacion = $scope.usuarioAplicativo.canalVentaDto.generaCotizacion == 1 ? true : false;
      	            }else{
      	            	$scope.mostrarBtnCotizacion=true;
      	            }
                },function(data){
                  console.log("error --> " + data);
                });
      	  };
      	  $scope.validaRol();
            
            
            $scope.init = function () {
                $http.post(CONFIG.APIURL + "/cliente/cargaInicialCliente.json").then(
                        function (data) {
                        	if(data.data.listClienteTempDto != undefined){
                        		$scope.cliente = data.data.listClienteTempDto;
                        	}else{
                            $scope.cliente = data.data;
                            $scope.idClienteTemp = $scope.cliente.idClienteTemp;
                            $scope.cliente.bitacora = data.data.bitacora;
                        	}
                    }, function (data) {
                            console.log("error --> " + data);
                        }
                );
            }
            $scope.verCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", row.idCotizacion).then(
                        function (data) {
//                            location.href = CONFIG.APIURL + "#/cotizador/cotizacion";
                            $location.path('/cotizador/verCotizacion');
                        },
                        function (data) {
                            alert("errar " + data)
                        }
                );
            }
            $scope.verPreCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", row.idCotizacion).then(
                        function (data) {
//                            location.href = CONFIG.APIURL + "#/cotizador/cotizacion";
                            $location.path('/cotizador/preCotizacion');
                        },
                        function (data) {
                            alert("errar " + data)
                        }
                );
            }
            $scope.verBitacora = function (row) {
                $scope.bitacora =  row ;
                $scope.modalBitNuevo = false;
                $('#modalBitacora').modal('show');
            }
            $scope.nuevaBitacora = function(){
                $scope.bitacora = {} ;
                $scope.modalBitNuevo = true;
                $('#modalBitacora').modal('show');
            }
            $scope.guardarBitacora = function (){
            	$scope.bitacora.idClienteTemp = $scope.idClienteTemp;
            	if($scope.bitacora.tipoEvento== undefined || $scope.bitacora.tipoEvento == null){
            		pinesNotifications.notify({
				        title: 'Error',
				        text: 'DEBE DE SELECCIONAR EL CAMPO, TIPO DE EVENTO',
				        type: 'error'
				      });
            		return;
            	}
            	
            	if($scope.bitacora.fechaEvento == undefined ){
            		pinesNotifications.notify({
				        title: 'Error',
				        text: 'DEBE DE SELECCIONAR EL CAMPO, FECHA DE EVENTO',
				        type: 'error'
				      });
            		return;
            	}
            	
            	if($scope.bitacora.observacion == undefined ){
            		pinesNotifications.notify({
				        title: 'Error',
				        text: 'DEBE AGREGAR EL CAMPO, OBSERVACIÓN.',
				        type: 'error'
				      });
            		return;
            	}
                $scope.cliente.bitacora.push($scope.bitacora);
                $http.post(CONFIG.APIURL + "/cotizador/guardarBitacora.json", $scope.bitacora).then(
                        function (response) {
                        	$scope.init();
                        },
                        function (data) {
                        	$scope.init();
                        	pinesNotifications.notify({
        				        title: 'Error',
        				        text: 'Ocurrio un error al guardar la bitacora, favor de verificar. .',
        				        type: 'error'
        				      });
                        }
                );
            }
            
            $scope.realizarPreCotizacion = function() {
//                 location.href = CONFIG.APIURL + "#/cotizador/preCotizacion";
                 $location.path('/cotizador/preCotizacion');
            }
            $scope.nuevaCotizacion = function() {
      		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",$scope.idClienteTemp);
//                location.href = CONFIG.APIURL + "#/cotizador/cotizacion";
                $location.path('/cotizador/cotizacion');
      	  };
            
            $scope.solicitarCotizacion = function (){
            	$scope.clienteTempBitacoraSolicitud.idClienteTemp = $scope.idClienteTemp;
            	if($scope.data != undefined){
            		$scope.clienteTempBitacoraSolicitud.archivo = $scope.data;
            	}
            	
                $http.post(CONFIG.APIURL + "/cotizador/guardarSolicitudCotizacion.json", $scope.clienteTempBitacoraSolicitud).then(
                        function (response) {
                        	$scope.init();
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
            
            
            $scope.init();
            
            $scope.regresarCotizacionesCliente = function() {
      		  $location.path('/cotizador/cotizador');
      	  };
            
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
