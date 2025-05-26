'use strict';
angular.module('theme.core.templates')// , ['ngTable']NgTableParams
  .controller('usuarioController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications, NgTableParams) {
	  $scope.agregarEditarTitulo = "Agregar usuario";
	  $scope.nuevoUsuario = true;
	  
	  $scope.usuario  = {};
	  
	  
	  $scope.initUsuario = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/usuarios.json").then(function(data){
	  	        $scope.usuarios = data.data;
	  	        
	  	      $scope.tableParams = new NgTableParams({page: 1, count: 5}, {data: $scope.usuarios});
	            
          
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
		  
		  $http.get(CONFIG.APIURL + "/admin/rol.json").then(function(data){
	  	        $scope.roles = data.data;
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  $scope.initUsuario();
	  
	  $scope.editarUsuario = function(row){
		  $scope.usuario = angular.copy(row);	
		  
		  if($scope.usuario.indEstatus === '1'){
			  $scope.usuario.indEstatus = true;
		  }else{
			  $scope.usuario.indEstatus = false;
		  }
		  
		  $scope.agregarEditarTitulo ="Editar Usuario";
		  $scope.nuevoUsuario = false;
	  }
	  
	  
	  
	  $scope.iniciarFormularioUsuario = function(){
		  
		  $scope.usuario = {};	
		  $scope.usuario.indEstatus = true;  
		    
		  $scope.agregarEditarTitulo ="Crear Nuevo Usuario";
		  $scope.nuevoUsuario = true;
	  }
	  
	  $scope.guardarUsuario = function (form){
		  
		  if(form.$valid){
			  
			  var usuario = angular.copy($scope.usuario);
			  
			  if($scope.usuario.confirmPassword != $scope.usuario.password){
				  
				  pinesNotifications.notify({
				        title: 'Error',
				        text: 'Los password no coinciden',
				        type: 'error'
				      });
				  
				  return;
			  }
			  
			  
			  if($scope.usuario.indEstatus === true){
				  usuario.indEstatus = '1';
			  }else{
				  usuario.indEstatus = '0';
			  }
			  
			  
			  $http.post(CONFIG.APIURL + "/admin/usuario.json",usuario).then(function(data){
		  	        $scope.usuarios = data.data;
		  	      $scope.tableParams = new NgTableParams({page: 1, count: 5}, {data: $scope.usuarios});
		  	        
		  	      pinesNotifications.notify({
				        title: 'usuario',
				        text: 'Se ha guardado el usuario exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	    $scope.usuario  = {};
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	      pinesNotifications.notify({
				        title: 'Error',
				        text: 'El formulario contiene un error',
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
	  
	  
	  $scope.nuevaUsuario = function(){
		  $scope.agregarEditarTitulo = "Agregar usuario";
		  $scope.usuario  = {};
		  $scope.nuevoUsuario = true;
	  }
	  
	  
    
  });