'use strict';
angular.module('theme.core.templates')
  .controller('codificadorController', function($scope,$http, $bootbox,$log, $window,CONFIG,pinesNotifications) {
	  
	  
	  $scope.textoDecodificar = "";
      $scope.textoDecodificado ="";
	
	  
	 $scope.initDecodificador = function() {
			 $scope.textoDecodificar = "";
		      $scope.textoDecodificado ="";
		      
		      
		}
	 
	 $scope.initDecodificador();
		
		$scope.decodificar = function(textoDecodificar){
			
			$http.post(CONFIG.APIURL + "/ppp/decrypt.json", textoDecodificar).then(function(response) {
										
				if (Array.isArray(response.data)){
					const datos = response.data;
				datos.forEach(function(datos, index) {
					if(`${datos}`===":"){
						$scope.textoDecodificado+="|";
					}else{
						$scope.textoDecodificado+=`${datos}`;
					}
				});
				}else {
					$scope.textoDecodificado =JSON.stringify(response.data);
				}
		
	  			}, function(data) {
	  				console.log("error texto a decodificar--> " + data);
	  				pinesNotifications.notify({
	  			        title: 'Error',
	  			        text: 'La cadea no tiene el formato correcto',
	  			        type: 'error'
	  			      });
	  				
	  				$scope.cargaInicialColaboradores($scope.idNomina);	
	  			}); 

			
		}
  });