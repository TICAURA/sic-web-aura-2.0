'use strict';
angular.module('theme.core.templates')
  .controller('moduloController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications,NgTableParams) {
	  $scope.agregarEditarTitulo = "Agregar modulo";
	  $scope.editar=false;
	  $scope.modulo  = {};
	  
	  $scope.initPantalla = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/modulo.json").then(function(data){
	  	        $scope.modulos = data.data;
	  	        $scope.tableParams = new NgTableParams({page: 1, count: 5}, {data: $scope.modulos});
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  
	  $scope.initPantalla();
	  
	  
	  $scope.editarModulo = function(row){
		  $scope.modulo = angular.copy(row);	
		  
		  if($scope.modulo.indEstatus === '1'){
			  $scope.modulo.indEstatus = true;
			  
			  
			  $http.get(CONFIG.APIURL + "/admin/moduloPantalla/"+ $scope.modulo.idModulo+".json").then(function(data){
		  	        $scope.pantallasModulo = data.data;
		  	        
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	  });
			  
			  
		  }else{
			  $scope.modulo.indEstatus = false;
		  }
		  
		  $scope.agregarEditarTitulo ="Editar modulo"
		  $scope.editar=true;
	  }
	  
	  $scope.guardar = function (form){
		  
		  if(form.$valid){
			  var modulo = angular.copy($scope.modulo);
			  
			  if($scope.modulo.indEstatus === true){
				  modulo.indEstatus = '1';
			  }else{
				  modulo.indEstatus = '0';
			  }
			  
			  
			  $http.post(CONFIG.APIURL + "/admin/modulo.json",modulo).then(function(data){
		  	        $scope.modulos = data.data;
		  	        
		  	      pinesNotifications.notify({
				        title: 'Modulo',
				        text: 'Se ha guardado el modulo exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	       
		  	      $scope.modulo  = {};
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
	  
	  
	  $scope.guardarModuloPantalla = function (moduloPantallaItem){
		  
		  var moduloPantalla = angular.copy(moduloPantallaItem);
		  moduloPantalla.idModulo = $scope.modulo.idModulo;
		  
		  $http.post(CONFIG.APIURL + "/admin/moduloPantalla.json",moduloPantalla).then(function(data){
			  $scope.pantallasModulo = data.data;
	  	        
	  	      pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Se ha guardado el modulo pantalla exitosamente!! ',
			        type: 'success'
			      });
	  	        
	  	    },function(data){
	  	    	pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Ocurrio un error al guardar la asignación de la pantalla!! ',
			        type: 'Error'
			      });
	  	  });
			    
	  }
	  
	  $scope.nuevoModulo = function(){
		  $scope.agregarEditarTitulo = "Agregar modulo";
		  $scope.editar=false;
		  $scope.modulo  = {};
	  }
	  
  });