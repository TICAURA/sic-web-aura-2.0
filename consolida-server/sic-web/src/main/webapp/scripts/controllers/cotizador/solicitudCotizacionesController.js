'use strict';
angular.module('theme.core.templates')
        .controller('solicitudCotizacionesController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window,pinesNotifications) {
            $scope.cliente;
            $scope.clienteCot;
            $scope.clientePreCot;
            $scope.clienteTempBitacoraSolicitud={};
            $scope.idClienteTemp;
            $scope.modalBitNuevo = false;
            
            $scope.idCotizacion;
            $scope.cotizacion;
            $scope.modalBitNuevo = false;
            $scope.cotizacionRealiada = false;
            $scope.catalogosCotizadorDTO = {};
            $scope.observacionAutorizador = {};
            $scope.idCatEstatusSelected;
            
            $scope.init = function () {
                $http.post(CONFIG.APIURL + "/cliente/cargaInicialSolicitudes.json").then(
                        function (data) {
                        	if(data.data.listClienteTempDto != undefined){
                        		$scope.cliente = data.data.listClienteTempDto;
                        	}else{
                            $scope.cliente = data.data;
                            $scope.idClienteTemp = $scope.cliente.idClienteTemp;
                            $scope.cliente.bitacora = data.data.bitacora;
                        	}
                    },
                        function (data) {
                            console.log("error --> " + data);
                        }
                );
            }
            $scope.init2 = function () {
            	$http.post(CONFIG.APIURL + "/cliente/cargaInicialCotizaciones.json").then(
                        function (data) {
                        	$scope.clienteCot = data.data.listClienteTempDto;
                        	$scope.clienteCot.catEstatus = data.data.catEstatus;
                        	$scope.idCatEstatusSelected = 11;
                    },
                        function (data) {
                            console.log("error --> " + data);
                        }
                );
            	$http.post(CONFIG.APIURL + "/cliente/cargaInicialPreCotizaciones.json").then(
                        function (data) {
                        	$scope.clientePreCot = data.data.listClienteTempDto;
                    },
                        function (data) {
                            console.log("error --> " + data);
                        }
                );
            }
            $scope.verCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", row.cotizacionDto.idCotizacion).then(
                        function (data) {
                            $location.path('/cotizador/verCotizacion');
                        },  
                        function (data) {
                            alert("errar " + data)
                        }
                );
            };
            $scope.verPreCotizacion = function (row) {
                $http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", row.cotizacionDto.idCotizacion).then(
                        function (data) {
                            $location.path('/cotizador/preCotizacion');
                        },  
                        function (data) {
                            alert("errar " + data)
                        }
                );
            };
           
            $scope.nuevaCotizacion = function(cliente) {
      		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",cliente.idClienteTemp);
      		$http.post(CONFIG.APIURL + "/cliente/nuevoIdClienteTempBitacoraSolicitudes.json", cliente.clienteTempBitacoraSolicitudesDto.idClienteTempBitacoraSolicitudes);
      		$location.path('/cotizador/cotizacion');
      	  };
      	  
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
                if($scope.bitacora.tipoEvento.idCatTipoEvento===1){
                    $scope.bitacora.tipoEvento.descripcionTipoEvento = 'Telefonica';
                    $scope.bitacora.tipoEvento.cveTipoEvento = 'Llamada';
                }else if($scope.bitacora.tipoEvento.idCatTipoEvento===2){
                    $scope.bitacora.tipoEvento.descripcionTipoEvento = 'Correo';
                    $scope.bitacora.tipoEvento.cveTipoEvento = 'Correo';
                }else{
                    $scope.bitacora.tipoEvento.descripcionTipoEvento = 'Visita';
                    $scope.bitacora.tipoEvento.cveTipoEvento = 'Visita';
                }
                $scope.cliente.bitacora.push($scope.bitacora);
                $http.post(CONFIG.APIURL + "/cotizador/guardarBitacora.json", $scope.bitacora).then(
                        function (response) {
                        	$scope.init();
                        },
                        function (data) {
                            alert("Ocurrio un error al guardar la bitacora, favor de verificar. ")
                        }
                );
            }
            
            
            $scope.solicitarCotizacion = function (){
            	$scope.clienteTempBitacoraSolicitud.idClienteTemp = $scope.idClienteTemp;
                $http.post(CONFIG.APIURL + "/cotizador/guardarSolicitudCotizacion.json", $scope.clienteTempBitacoraSolicitud).then(
                        function (response) {
                        	$scope.init();
                        },
                        function (data) {
                            alert("Ocurrio un error al solicitar la cotización, favor de verificar. ")
                        }
                );
            }
            
            $scope.descargarArchivo = function (cliente){
          	   $http.post(CONFIG.APIURL + "/cliente/descargarArchivo.json", cliente).then(
                         function (response) {
                      	   var link = document.createElement("a");
                      	   link.href = response.data.documento;
                      	   link.style = "visibility:hidden";
                      	   link.download = cliente.clienteTempBitacoraSolicitudesDto.nombreArchivo;
                      	   document.body.appendChild(link);
                      	   link.click();
                      	   document.body.removeChild(link);
              },function(data) {
                  console.log("fallo al descargar el archivo en el metodo descargarArchivo");
              });
             }
            
            $scope.obtenerCotizacionesPorEstatus = function(idEstatus) {
            	$http.post(CONFIG.APIURL + "/cliente/obtenerCotizacionesPorEstatus.json", idEstatus).then(
                        function (data) {
                        	$scope.clienteCot = data.data.listClienteTempDto;
                        	$scope.clienteCot.catEstatus = data.data.catEstatus;
                        	$scope.idCatEstatusSelected = parseInt(idEstatus,10);
                    },
                        function (data) {
                            console.log("error --> " + data);
                        }
                );
            }
             
            
            
            $scope.init();
            $scope.init2();
        });
