
'use strict';
angular.module('theme.core.templates')
  .controller('celulaController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications) {
	  
	  $scope.agregarEditarTitulo = "Agregar centro de costos";
	  $scope.celula  = {};
	  $scope.usuario = {};
	  
	  $scope.editarAdministrador = "Editar administrador";
	  
	  $scope.initPantalla = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/celula.json").then(function(data){
	  	        $scope.celulas = data.data;
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  
	  $scope.initPantalla();
	  
	  
	  $scope.editarcelula = function(row){
		  $scope.celula = angular.copy(row);	
		  $scope.usuario = angular.copy($scope.celula.administrador);
		  
		  if($scope.celula.indEstatus === '1'){
			  $scope.celula.indEstatus = true;
		  }else{
			  $scope.celula.indEstatus = false;
		  }
		  
		  $scope.agregarEditarTitulo ="Editar centro de costos"
	  }
	  
	  
	  $scope.nuevaCelula = function(){
		  $scope.celula = {};	
		  $scope.celula.indEstatus = true;
		  $scope.usuario = {};
		  
		  $scope.agregarEditarTitulo ="Nuevo centro de costos"
	  }
	  
	  $scope.guardar = function (form){
		  
		  if(form.$valid){
			  var celula = angular.copy($scope.celula);
			  
			  if($scope.celula.indEstatus === true){
				  celula.indEstatus = '1';
			  }else{
				  celula.indEstatus = '0';
			  }
			  
			 
			  
			  $http.post(CONFIG.APIURL + "/admin/celula.json",celula).then(function(data){
		  	        $scope.celulas = data.data;
		  	        
		  	      pinesNotifications.notify({
				        title: 'celula',
				        text: 'Se ha guardado el celula exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	       
		  	      $scope.celula  = {};
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	  });
			  
		  }else {
			  pinesNotifications.notify({
			        title: 'Error',
			        text: 'El formulario contiene un error',
			        type: 'error'
			      });
		  }	  
	  }
	  
	  
	  $scope.guardarAdministrador = function (form){
		  if(form.$valid){
			  var celula = angular.copy($scope.celula);
			  
			  if($scope.celula.indEstatus === true){
				  celula.indEstatus = '1';
			  }else{
				  celula.indEstatus = '0';
			  }
			  
			  if($scope.usuario.confirmPassword != $scope.usuario.password){
				  
				  pinesNotifications.notify({
				        title: 'Error',
				        text: 'Los password no coinciden',
				        type: 'error'
				      });
				  
				  return;
			  }
			  
			  celula.administrador = $scope.usuario;
			  
			  $http.post(CONFIG.APIURL + "/admin/celula/administrador.json",celula).then(function(data){
		  	        $scope.celulas = data.data;
		  	        
		  	      pinesNotifications.notify({
				        title: 'celula',
				        text: 'Se ha guardado el administrador  de la célula exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	       
		  	      $scope.celula  = {};
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	        
		  	      pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error',
				        type: 'error'
				      });
		  	  });
			  
		  }else {
			  pinesNotifications.notify({
			        title: 'Error',
			        text: 'El formulario contiene un error',
			        type: 'error'
			      });
		  }	  
	  }
	  
	  $scope.nuevocelula = function(){
		  $scope.agregarEditarTitulo = "Agregar celula";
		  $scope.celula  = {};
	  }
	  
  });