'use strict';
angular.module('theme.core.templates')
        .controller('oficinaCanalVentaController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications, agregarPrestadoraServicioService) {
       
      $scope.oficina = {};
      $scope.usuario = {};
      $scope.domicilio = {};
      $scope.persona = {};
      $scope.cuentaBancaria = {};
      
	  $scope.tabSeleccionado = "generales";
	  $scope.municipios = [];
	  
	  
	  $scope.seleccionarTab = function(tab){
		  $scope.tabSeleccionado = tab;	  
	  }
        	
	  $scope.cargaInicial = function(){
		  
		  $http.get(CONFIG.APIURL + "/cotizador/oficinaCanalVenta.json").then(
					function(data) {
						
						
						$scope.entidadFederativa = data.data.entidadFederativa;
						$scope.bancos = data.data.bancos;

						if(data.data.oficinaEditar !== undefined){
							$scope.oficina = data.data.oficinaEditar;
							$scope.domicilio =  data.data.oficinaEditar.domicilio;
							$scope.cuentaBancaria =   data.data.oficinaEditar.cuentaBancaria;
							
							$scope.obtenerCatMunicipiosByEntidad($scope.domicilio.catEntidadFederativa.idCatGeneral);						
						}
						
					}, function(data) {
						console.log("error --> " + data);
					});
	  }
	  
	  $scope.cargaInicial();
	  
	  
	  $scope.obtenerCatMunicipiosByEntidad = function(seleccion) {
		  agregarPrestadoraServicioService.cargaCatMunicipiosXEntidadFed(seleccion, function(response) {
	    		$scope.municipios = response.data;
	    	},function(response){
				
			});
	    }
	  
	  
	  
	  $scope.obtenerEntidadFederativaXCP = function(codigoPostal) {
		  agregarPrestadoraServicioService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
	    		$scope.municipios = response.data.municipios;
	    		$scope.domicilio.catEntidadFederativa = {};
	    		$scope.domicilio.catEntidadFederativa.idCatGeneral = response.data.prestadoraServicioDomicilioDto.domicilio.idEntidadFederativa;
	    		$scope.domicilio.catMunicipios = response.data.prestadoraServicioDomicilioDto.domicilio.catMunicipios;
	    		
	    	},function(response){
				
			});
	    }

	  
 $scope.guardarOficinaCanalVenta = function(form){
	 
	  
	  if(form.$valid){
		  
		  var oficinaCanalVentaSend = angular.copy($scope.oficina);
		 
		  oficinaCanalVentaSend.domicilio = $scope.domicilio;
		  oficinaCanalVentaSend.cuentaBancaria = $scope.cuentaBancaria;
		     	  
		 $http.post(CONFIG.APIURL + "/cotizador/oficinaCanalVenta.json", oficinaCanalVentaSend).then(
						function(data) {
							
							  pinesNotifications.notify({
							        title: 'OFICINA CANAL DE VENTA',
							        text: 'Se ha guardado la oficina del canal de venta exitosamente!! ',
							        type: 'success'
							      });
							  
							  $location.path('/cotizador/canalVenta/asignacionOficina');
							
						}, function(data) {
							console.log("error --> " + data);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'El formulario contiene un error',
						        type: 'error'
						      });
							
						});
		  
		  
	  	 
	  } else {
		  pinesNotifications.notify({
		        title: 'Error',
		        text: 'El formulario contiene un error',
		        type: 'error'
		      });
	  }
	  
	
	  }
 
 	$scope.regresar = function(){
 		$location.path('/cotizador/canalVenta/asignacionOficina');
 	}
});