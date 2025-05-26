'use strict';
angular.module('theme.core.templates')
  .controller('consultaPersonalController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, pinesNotifications) {

	  var data = {};
	  $scope.celula = {};
	  $scope.tituloPersonal="Registro personal célula";
	  $scope.usuario = {};
	  $scope.nuevoUsuario = true;
	  
        $scope.initCargaInicial = function() {
    	
        	 $http.get(CONFIG.APIURL + "/admin/celulaAdministrador.json").then(function(data){
   			  
 	  	        $scope.celula = data.data.celula;
 	  	        $scope.usuariosCelula = data.data.usuariosCelula;
 	  	        
 	  	    },function(data){
 	  	        console.log("error --> " + data);
 	  	  });
 		  
 		  
 		  $http.get(CONFIG.APIURL + "/admin/rol/celula.json").then(function(data){
 	  	        $scope.roles = data.data;
 	  	        
 	  	    },function(data){
 	  	        console.log("error --> " + data);
 	  	  });

        };
    	
    	
    	$scope.initCargaInicial();
    
    	$scope.registrarPersonal =function(){
    		$scope.tituloPersonal="Registro personal célula";
    		  $scope.usuario = {};
    		  $scope.nuevoUsuario = true;
    	}
    	
    	$scope.actualizarPersonal = function(row){
    		$scope.tituloPersonal="Editar personal célula";
    		$scope.usuario = angular.copy(row);
    		$scope.nuevoUsuario = false;
    	}
    	
    	
    	$scope.guardarPersonal = function(form) {

    	  if(form.$invalid){
  			  pinesNotifications.notify({
  			        title: 'Error',
  			        text: 'El formulario contiene un error',
  			        type: 'error'
  			      });
  			  
  			  return;
  		  }
    		
	
		  if($scope.usuario.confirmPassword != $scope.usuario.password){
			  
			  pinesNotifications.notify({
			        title: 'Error',
			        text: 'Los password no coinciden',
			        type: 'error'
			      });
			  
			  return;
		  }
			  
			  
    	bootbox
		.confirm({
			title : "Confirmar acción",
			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
			buttons : {
				confirm : {
					label : 'ACEPTAR',
					className : 'btn-primary'
				},
				cancel : {
					label : 'CANCELAR',
					className : 'btn-danger'
				}
			},
			callback : function(result) {
				if (result) {
					
					 var celula = angular.copy($scope.celula);
					 celula.usuario = $scope.usuario;
	 
					  $http.post(CONFIG.APIURL + "/admin/celula/integranteCelula.json",celula).then(function(data){
				  	        $scope.usuariosCelula = data.data;
				  	        
				  	      pinesNotifications.notify({
						        title: 'celula',
						        text: 'Se ha guardado el integrante de la célula exitosamente!! ',
						        type: 'success'
						      });
				  	        
				  	       
				  	      $scope.usuario  = {};
				  	      
				  	    },function(data){
				  	        console.log("error --> " + data);
				  	        
				  	      pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error',
						        type: 'error'
						      });
				  	  });
					  

				}
			}
		});
    }



 
  });
