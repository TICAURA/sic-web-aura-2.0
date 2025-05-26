'use strict';
angular.module('theme.core.templates')
  .controller('nuevoClienteController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, nuevoClienteService,pinesNotifications) {

	  var data = {};
	  $scope.clienteTempDto = {};
	  $scope.tipoPersona = {};
      $scope.clienteTempDto.grupo = {};
      $scope.grupo = {};
      $scope.seleccionEntidadFederativa = '';
      $scope.aux = {};
      $scope.aux.canalVentaAux={};
	  
    var APIURL  = '/sic-web';
    $scope.promotores = {};
    $scope.usuarioIsPromotor = true;
    $scope.validaRol = function(){
		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
	            $scope.usuarioAplicativo = data.data;
	            if($scope.usuarioAplicativo.canalVentaDto!=null){
	            	$scope.usuarioIsPromotor = false;
	            }
        },function(data){
          console.log("error --> " + data);
        });
	  };
	  $scope.validaRol();
	  
	  $scope.obtenerPromotores = function(){
		  $http.get(CONFIG.APIURL +"/cliente/obtenerPromotores.json").then(function(data){
	            $scope.promotores = data.data;
      },function(data){
        console.log("error --> " + data);
      });
		  
	  }
	  $scope.obtenerPromotores();

    	$scope.cargaInicial = function(){
    	nuevoClienteService.tipoPersona(function(data) {
    		$scope.tipoPersona = data.data;
    	},function(data){
			
		});
    	
    	nuevoClienteService.verificarEditar(function(response) {
    		$scope.clienteTempDto = response.data;
    		$scope.aux.canalVentaAux = {};
    		$scope.aux.canalVentaAux.idCanalVenta = $scope.clienteTempDto.idCanalVenta;
    	},function(response){
			
		});
    	
    };
    $scope.cargaInicial();
    	
    	$scope.guardar = function() {

    	bootbox
		.confirm({
			title : "Confirmar acci&oacute;n",
			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
			buttons : {
				confirm : {
					label : 'S\u00ed',
					className : 'btn-primary'
				},
				cancel : {
					label : 'No',
					className : 'btn-danger'
				}
			},
			callback : function(result) {
				if (result) {
				var data = {
                    'clienteTempDto' : angular.copy($scope.clienteTempDto)
                };
				data.clienteTempDto.idCanalVenta = $scope.aux.canalVentaAux.idCanalVenta;
					nuevoClienteService
							.guardar(data.clienteTempDto,function(response) {
								if(response.data.mensajeError != undefined){
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: response.data.mensajeError,
								        type: 'error'
								      });
								}else{
										$log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										
//										location.href=APIURL+"#/cotizador/cotizador";
										$location.path('/cotizador/cotizador');
								}  
									},
									function(response) {
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
									        type: 'error'
									      });

									});

				}
			}
		});
    };


    var passTOJson = function(data) {

        var json = JSON.stringify(data, function(key, value) {
            if (key === ",") {
                return undefined;
            }

            return value;
        });

        return json;
    };
   
    $scope.limpiarSeleccion = function(seleccion) {
    	if(seleccion ==="22"){
    		$scope.clienteTempDto.nombre = null;
    		$scope.clienteTempDto.apellidoPaterno = null;
    		$scope.clienteTempDto.apellidoMaterno = null;
    	}else{
    		$scope.clienteTempDto.razonSocial = null;
    	}
    };

    $scope.cancelar = function() {
//    	location.href=APIURL+"#/cotizador/cotizador";
    	$location.path('/cotizador/cotizador');
    };
    $scope.nuevoGrupo = function() {
        $('#agregarGrupo').modal('show');
    };
    
    $scope.guardarNuevoGrupo = function() {
    	nuevoClienteService.guardarGrupo($scope.grupo, function() {
    		nuevoClienteService.cargaCatalogoGrupo(function(response) {
        		$scope.clienteTempDto.grupos = response.data.grupos;
        	},function(data){
    			
    		});
    	},function(data){
			
		});
        $('#agregarGrupo').modal('hide');
    };
    
    
    $scope.obtenerCatMunicipiosByEntidad = function(seleccion) {
    	nuevoClienteService.cargaCatMunicipiosXEntidadFed(seleccion, function(response) {
    		$scope.clienteTempDto.municipios = response.data;
    	},function(response){
			
		});
    }
    
    
    $scope.obtenerEntidadFederativaXCP = function(codigoPostal) {
    	nuevoClienteService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
    		$scope.clienteTempDto.municipios = response.data.municipios;
    		if(response.data.idMedioContacto == null){
    			$scope.clienteTempDto.idMedioContacto = {};
    		}else{
    		$scope.clienteTempDto.idMedioContacto.alcaldia = response.data.idMedioContacto.alcaldia;
    		}
    		
    		$scope.clienteTempDto.idMedioContacto.estado = response.data.idMedioContacto.estado;
    		
    		
    	},function(response){
			
		});
    }
    
    $scope.obtenerSubgiro = function(idGiro) {
    	nuevoClienteService.obtenerSubgiro(idGiro, function(response) {
    		$scope.clienteTempDto.catSubGiroComercial = response.data.catSubGiroComercial;
    		$scope.clienteTempDto.idSubGiroComercial = null;
    	},function(response){
			
		});
    }
    
   
	$scope.eliminarProspecto = function() {

	bootbox
	.confirm({
		title : "Confirmar acci&oacute;n",
		message : "¿Est\u00e1s seguro que deseas eliminar la informaci\u00f3n del prospecto?",
		buttons : {
			confirm : {
				label : 'S\u00ed',
				className : 'btn-primary'
			},
			cancel : {
				label : 'No',
				className : 'btn-danger'
			}
		},
		callback : function(result) {
			if (result) {
				
			var data = {
                'clienteTempDto' : $scope.clienteTempDto
            };
				nuevoClienteService
						.eliminarProspecto(data.clienteTempDto,function(response) {
							if(response.data.mensajeError != undefined){
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: response.data.mensajeError,
							        type: 'error'
							      });
							}else{
									$log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });
									
//									location.href=APIURL+"#/cotizador/cotizador";
									$location.path('/cotizador/cotizador');
							}
								},
								function(response) {
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al eliminar la informaci\u00f3n del prospecto, favor de verificar.',
								        type: 'error'
								      });

								});

			}
		}
	});
};
 
  });
