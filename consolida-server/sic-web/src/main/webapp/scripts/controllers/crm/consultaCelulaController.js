'use strict';
angular.module('theme.core.templates')
  .controller('consultaCelulaController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, $window, consultaCelulaService) {
	  
	  $scope.gridCelula = {};
	  $scope.celula;
	  var rfcCelula;
	            
          $scope.celulaCargaInicial = function() {
              consultaCelulaService.celulaCargaInicial( function(response) {
              	
          		if(response.data){
          			
          			 $scope.gridCelula = response.data;
          		}
          		
          	}, function(response) {
          		          		
//				bootbox.alert({
//					title : "Error",
//					message : "La operaci\u00f3n no fue exitosa.",
//					buttons : {
//						ok : {
//							label : 'Aceptar',
//							className : 'center-block btn-primary'
//						}
//					},
//					callback : function() {
//						$scope.celulaCargaInicial();
//						location.href=APIURL+"#/crm/celula/consultaCelula";
//					}
//				});
          		
              });
          };
          
		  
	  $scope.celulaCargaInicial();
	  
	  $scope.actualizarCelula = function(rfcCelula) {
		  $http.post(CONFIG.APIURL + "/celula/getRfcCelula.json",rfcCelula);
		  location.href=CONFIG.APIURL+"#/crm/fondo/actualizaDatosFondo"		  
	  };
	  
      $scope.borrarCelula = function(idCelula) {
    	  
          bootbox.confirm({
              title : "Mensaje confirmaci&oacute;n",
              message : "\u00bfEst&aacute;s seguro que deseas borrar la celula?",
              buttons : {
                 cancel : {
                    label : '<i class="fa fa-times"></i> Cancelar',
                    className : 'btn-danger'
                 },
                 confirm : {
                    label : '<i class="fa fa-check"></i> Aceptar',
                    className : 'btn-primary'
                 }
              },
			callback : function(result) {
				
			  if(result){
		          consultaCelulaService.eliminarCelula(idCelula, function(response) {
						bootbox.alert({
							title : "Mensaje",
							message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
							buttons : {
								ok : {
									label : 'Aceptar',
									className : 'center-block btn-primary'
								}
							},
							callback : function() {
								$scope.celulaCargaInicial();
//								location.href=CONFIG.APIURL+"#/crm/consultaCelula";
							}
						});	 
					});
				} 
			  }	
      	});
      };
      
      $scope.nuevoClienteCel = function(){
    	  location.href=CONFIG.APIURL+"#/crm/celula/cliente";
      }
  });