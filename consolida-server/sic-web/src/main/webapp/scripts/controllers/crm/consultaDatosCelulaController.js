'use strict';
angular.module('theme.core.templates')
  .controller('consultaDatosCelulaController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, registroCelulaService) {

	  var data = {};
	  $scope.celulaDto = {};
    	
        $scope.initCargaInicial = function() {
    	
    	registroCelulaService.initCargaInicialActualizaCelula( function(response) {

    		if(response.data){
    			
    			 $scope.celulaDto = response.data;
    		}
    		
    	}, function(response) {
    		
    		$bootbox.customDialog({
                message : 'La operaci\u00f3n no fue exitosa.',
                title : 'Error',
                buttons : {
                    main : {
                        label : 'Aceptar',
                        className : 'center-block btn-primary'
                    }
                }
            });
        });
    };
    	
    	
    	$scope.initCargaInicial();
    
    
    	
    	
    	$scope.guardarCelula = function() {

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

					 var data = {
							 celulaDto : $scope.celulaDto
					 };
	 
					 registroCelulaService.guardarCelula(data.celulaDto,function(response) {

										$log.debug('ok');
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
														$scope.limpiaCampos();
														location.href=CONFIG.APIURL+"#/crm/fondo/actualizaDatosFondo";
													}
												});
//										location.href=APIURL+"#/crm/clientes";
									},
									function(response) {									
										bootbox
										.alert({
											title : "Error",
											message : "Ocurrio un error en el sistema.",
											buttons : {
												ok : {
													label : 'Aceptar',
													className : 'center-block btn-primary'
												}
											},
											callback : function() {
												
											}
										});

									});

				}
			}
		});
    }


    var passTOJson = function(data) {

        var json = JSON.stringify(data, function(key, value) {
            if (key === ",") {
                return undefined;
            }

            return value;
        });

        return json;
    };
   
    $scope.limpiaCampos = function() {
    		$scope.celulaDto.nombreCelula = null;
    		$scope.celulaDto.rfcCelula = null;
    		$scope.celulaDto.nombreResponsable = null;
    		$scope.celulaDto.apellidoPaternoResponsable = null;
    		$scope.celulaDto.apellidoMaternoResponsable = null;
    		$scope.celulaDto.rfcResponsable = null;
    		$scope.celulaDto.correoResponsable = null;
    }

    $scope.cancelar = function() {
    	location.href=CONFIG.APIURL+"#/crm/fondo/consultaFondo";
    }
 
  });
