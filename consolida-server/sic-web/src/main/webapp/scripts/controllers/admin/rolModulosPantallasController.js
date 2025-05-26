'use strict';
angular.module('theme.core.templates')
  .controller('rolModulosPantallasController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications ,NgTableParams ) {
	  
	  $scope.initPantalla = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/rolModulosPantallas.json").then(function(data){
	  	        $scope.rolesModulos = data.data;
	  	        $scope.tableParamsRolesModulos = new NgTableParams({page: 1, count: 5}, {data: $scope.rolesModulos});
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  
	  $scope.initPantalla();
	  
	  
	  $scope.editarModulosPantalla = function(rolModuloPantalla){
		  
		  //Consulta de modulos
		  $http.get(CONFIG.APIURL + "/admin/rolModulos/"+rolModuloPantalla.idRol +".json").then(function(data){
	  	        $scope.rolModulos = data.data;
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
		  
		  //Consulta de pantallas
		  $http.get(CONFIG.APIURL + "/admin/rolPantallas/"+rolModuloPantalla.idRol +".json").then(function(data){
	  	        $scope.rolPantallas = data.data;
	  	        
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });	  
	  }
	  
	  //Metodo que actualiza los modulos asignados al rol
	  $scope.guardarRolModulo = function (moduloRolItem){
		  
		  var moduloRol = angular.copy(moduloRolItem);
		  
		  $http.post(CONFIG.APIURL + "/admin/rolModulos.json",moduloRol).then(function(data){
			  $scope.pantallasModulo = data.data;
			  $scope.initPantalla();
			  
	  	      pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Se ha guardado exitosamente!! ',
			        type: 'success'
			      });
	  	        
	  	       
	  	      $scope.modulo  = {};
	  	    },function(data){
	  	    	pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Ocurrio un error al guardar la asignación de la pantalla!! ',
			        type: 'Error'
			      });
	  	  });
			    
	  }
	  
	  
	//Metodo que actualiza las pantallas asignados al rol
	  $scope.guardarRolPantallas = function (pantallaRolItem){
		  
		  var pantallaRol = angular.copy(pantallaRolItem);
		  
		  
		  $http.post(CONFIG.APIURL + "/admin/rolPantallas.json",pantallaRol).then(function(data){
			  $scope.rolPantallas = data.data;
			  $scope.initPantalla();
			  
	  	      pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Se ha guardado exitosamente!! ',
			        type: 'success'
			      });
	  	        
	  	    },function(data){
	  	    	pinesNotifications.notify({
			        title: 'Modulo',
			        text: 'Ocurrio un error al guardar la asignación de la rol pantalla!! ',
			        type: 'Error'
			      });
	  	  });
			    
	  }
	  
  });