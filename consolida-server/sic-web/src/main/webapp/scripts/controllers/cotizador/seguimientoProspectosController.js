'use strict';
angular.module('theme.core.templates')
        .controller('seguimientoProspectosController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications) {
            $scope.cliente;
            $scope.idClienteTemp;
            $scope.idCotizacion;
            $scope.cotizacion;
            $scope.modalBitNuevo = false;
            $scope.cotizacionRealiada = false;
            $scope.catalogosCotizadorDTO = {};
            $scope.observacionAutorizador = {};
            $scope.idCotizacion;
            $scope.gridClientes = {};
            $scope.totales = {};
            $scope.nombreContadorSelecionado;
            $scope.idEstatus == null;
                 
            $scope.init = function(){
      		  $http.get(CONFIG.APIURL + "/seguimiento/obtenerContadoresTotales.json").then(function(data){
      			            $scope.totales = data.data;
      			          },function(data){
      			            console.log("error --> " + data);
      			        });
      	  };
            
            $scope.busquedaContadorXEstatus = function (idEstatus) {
                $http.post(CONFIG.APIURL + "/seguimiento/busquedaContadorXEstatus.json", idEstatus).then(function (response) {
                    console.log("response --> " + response);
                    $scope.gridClientes = response.data;
                    $scope.idEstatus = idEstatus;
                    if(idEstatus === 2){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos sin cotización';
                    }
                    if(idEstatus === 3){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos en proceso de cotización';
                    }
                    if(idEstatus === 4){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos con cotizaciones en autorización';
                    }
                    if(idEstatus === 6){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos con cotizaciones autorizadas';
                    }
                    if(idEstatus === 5){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos con cotizaciones rechazadas';
                    }
                    if(idEstatus === 8){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos autorizados';
                    }
                    if(idEstatus === 9){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos que declinaron';
                    }
                    if(idEstatus === 16){
                    	$scope.nombreContadorSelecionado = 'Detalle de Prospectos rechazados';
                    }
                    if(idEstatus ===  23456){
                    	$scope.nombreContadorSelecionado = "Prospectos con cotizaciones en proceso";
                    }
                }, function (response) {
                    console.log("error --> " + response);
                });
            }
            
            $scope.obtenerRegistrosContadorPrincipal = function () {
                $http.post(CONFIG.APIURL + "/seguimiento/obtenerRegistrosContadorPrincipal.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.gridClientes = response.data;
                    $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }
            $scope.verCotizaciones = function(idCliente) {
      		  $http.post(CONFIG.APIURL + "/cliente/verCotizaciones.json",idCliente);
//      		  location.href=CONFIG.APIURL+"#/cotizador/cotizacionesCliente";
      		  $location.path('/cotizador/cotizacionesCliente');
      	  };
            
            $scope.init();
        });
