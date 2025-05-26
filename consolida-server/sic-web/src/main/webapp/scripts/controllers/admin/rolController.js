'use strict';
angular.module('theme.core.templates')
  .controller('rolController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications, NgTableParams) {
	  
	  $scope.agregarEditarTitulo = "Agregar rol";
	  $scope.rol  = {};
	  
	  $scope.initPantalla = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/rol.json").then(function(data){
	  	        $scope.roles = data.data;
	  	      $scope.tableParams = new NgTableParams({page: 1, count: 5}, {data: $scope.roles});
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  
	  $scope.initPantalla();
	  
	  
	  $scope.editarRol = function(row){
		  $scope.rol = angular.copy(row);	
		  
		  if($scope.rol.indEstatus === '1'){
			  $scope.rol.indEstatus = true;
		  }else{
			  $scope.rol.indEstatus = false;
		  }
		  
		  $scope.agregarEditarTitulo ="Editar rol"
	  }
	  
	  $scope.guardar = function (form){
		  
		  if(form.$valid){
			  var rol = angular.copy($scope.rol);
			  
			  if($scope.rol.indEstatus === true){
				  rol.indEstatus = '1';
			  }else{
				  rol.indEstatus = '0';
			  }
			  
			  
			  $http.post(CONFIG.APIURL + "/admin/rol.json",rol).then(function(data){
		  	        $scope.roles = data.data;
		  	        
		  	      
		  	        
		  	      pinesNotifications.notify({
				        title: 'rol',
				        text: 'Se ha guardado el rol exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	       
		  	      $scope.rol  = {};
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
	  
	  $scope.nuevorol = function(){
		  $scope.agregarEditarTitulo = "Agregar rol";
		  $scope.rol  = {};
	  }
	  
  });