'use strict';
angular.module('theme.core.templates')
        .controller('canalVentaController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications, agregarPrestadoraServicioService) {
       
      $scope.canalVenta = {};
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
		  
		  $http.get(CONFIG.APIURL + "/cotizador/canalVenta.json").then(
					function(data) {
						
						$scope.canalesVenta = data.data.canalesVenta;
						$scope.entidadFederativa = data.data.entidadFederativa;
						$scope.bancos = data.data.bancos;

						if(data.data.canalVentaEditar !== undefined){
							$scope.canalVenta = data.data.canalVentaEditar;
							$scope.usuario = data.data.canalVentaEditar.usuario;
							$scope.domicilio =  data.data.canalVentaEditar.domicilio;
							$scope.cuentaBancaria =   data.data.canalVentaEditar.cuentaBancaria;
							
							$scope.obtenerCatMunicipiosByEntidad($scope.domicilio.catEntidadFederativa.idCatGeneral);
							
							var usuario = angular.copy($scope.usuario);
							
							  if($scope.usuario.indEstatus === '1'){
								  $scope.usuario.indEstatus = true;
							  }else{
								  $scope.usuario.indEstatus = false;
							  }
							  
							  if($scope.canalVenta.generaCotizacion === '1'){
								  $scope.canalVenta.generaCotizacion = true;
							  }else{
								  $scope.canalVenta.generaCotizacion = false;
							  }
							  
							  if($scope.canalVenta.pagoOficina === '1'){
								  $scope.canalVenta.pagoOficina = true;
							  }else{
								  $scope.canalVenta.pagoOficina = false;
							  }
							  
							  
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

	  
 $scope.guardarCanalVenta = function(form){
	 
	  
	  if(form.$valid){
		  
		  var canalVentaSend = angular.copy($scope.canalVenta);
		 
			 
		  if($scope.usuario.confirmPassword != $scope.usuario.password){
			  
			  pinesNotifications.notify({
			        title: 'Error',
			        text: 'Los password no coinciden',
			        type: 'error'
			      });
			  
			  return;
		  }
		  var usuario = angular.copy($scope.usuario);
		  if($scope.usuario.indEstatus === true){
			  usuario.indEstatus = '1';
		  }else{
			  usuario.indEstatus = '0';
		  }
		  
		  
		  if($scope.canalVenta.generaCotizacion === true){
			  canalVentaSend.generaCotizacion = '1';
		  }else{
			  canalVentaSend.generaCotizacion = '0';
		  }
		  
		  if($scope.canalVenta.pagoOficina === true){
			  canalVentaSend.pagoOficina = '1';
		  }else{
			  canalVentaSend.pagoOficina = '0';
		  }
		  
		  if($scope.usuario.indEstatus === '1'){
			  $scope.usuario.indEstatus = true;
		  }else{
			  $scope.usuario.indEstatus = false;
		  }
		  
		  
		  canalVentaSend.usuario = usuario ; 
		  canalVentaSend.domicilio = $scope.domicilio;
		  canalVentaSend.cuentaBancaria = $scope.cuentaBancaria;
		     	  
		 $http.post(CONFIG.APIURL + "/cotizador/canalVenta.json", canalVentaSend).then(
						function(data) {
							
							  pinesNotifications.notify({
							        title: 'CANAL DE VENTA',
							        text: 'Se ha guardado el canal de venta exitosamente!! ',
							        type: 'success'
							      });
							  
							  $location.path('/cotizador/canalVenta/consultaCanalVenta');
							
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
 		$location.path('/cotizador/canalVenta/consultaCanalVenta');
 	}
});