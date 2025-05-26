
'use strict';
angular.module('theme.core.templates')
  .controller('pantallaController',  function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications,NgTableParams) {
	  $scope.agregarEditarTitulo = "Agregar pantalla";
	  $scope.pantalla  = {};
	  
	  $scope.initPantalla = function(){
		  
		  $http.get(CONFIG.APIURL + "/admin/pantalla.json").then(function(data){
	  	        $scope.pantallas = data.data;
	  	        $scope.tableParams = new NgTableParams({page: 1, count: 5}, {data: $scope.pantallas});
	  	      
	  	    },function(data){
	  	        console.log("error --> " + data);
	  	  });
		  
	  }
	  
	  
	  $scope.initPantalla();
	  
	  
	  $scope.editarPantalla = function(row){
		  $scope.pantalla = angular.copy(row);	
		  
		  if($scope.pantalla.indEstatus === '1'){
			  $scope.pantalla.indEstatus = true;
		  }else{
			  $scope.pantalla.indEstatus = false;
		  }
		  
		  $scope.agregarEditarTitulo ="Editar pantalla"
	  }
	  
	  $scope.guardar = function (form){
		  
		  if(form.$valid){
			  var pantalla = angular.copy($scope.pantalla);
			  
			  if($scope.pantalla.indEstatus === true){
				  pantalla.indEstatus = '1';
			  }else{
				  pantalla.indEstatus = '0';
			  }
			  
			  
			  $http.post(CONFIG.APIURL + "/admin/pantalla.json",pantalla).then(function(data){
		  	        $scope.pantallas = data.data;
		  	        
		  	      pinesNotifications.notify({
				        title: 'Pantalla',
				        text: 'Se ha guardado la pantalla exitosamente!! ',
				        type: 'success'
				      });
		  	        
		  	    $scope.pantalla  = {};
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
	  
	  
	  $scope.nuevaPantalla = function(){
		  $scope.agregarEditarTitulo = "Agregar pantalla";
		  $scope.pantalla  = {};
	  }
	  
	  
    
  });