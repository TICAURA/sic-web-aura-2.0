'use strict';
angular.module('theme')
        .controller('costosAdicionalesController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, pinesNotifications) {

            
        	$scope.catalogosCostosAdicionalesDTO = {};
        	$scope.catalogosCostosAdicionalesAnterioresDTO = {};
            $scope.cargaInicial = function () {
            	$http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosCostosAdicionales.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.catalogosCostosAdicionalesDTO = response.data;
                }, function (response) {

                    console.log("error --> " + response);
                });
            	$http.post(CONFIG.APIURL + "/catalogoCotizador/obtenerCatalogosCostosAdicionales.json").then(function (response) {
                    console.log("response --> " + response);
                    $scope.catalogosCostosAdicionalesAnterioresDTO = response.data;
                }, function (response) {

                    console.log("error --> " + response);
                });
            	
            	$http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
      	            $scope.usuarioAplicativo = data.data;
      	            if($scope.usuarioAplicativo.canalVentaDto!=null){
      	            	$scope.isPromotor = true;
      	            }
                },function(data){
                  console.log("error --> " + data);
                });
            	
            }
            
            $scope.guardarCostosAdicionales = function () {
	            if($scope.catalogosCostosAdicionalesDTO.catPorcIndirectos.valor == $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcIndirectos.valor
	            		&& $scope.catalogosCostosAdicionalesDTO.catPorcEstrategia.valor == $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcEstrategia.valor
	            		&& $scope.catalogosCostosAdicionalesDTO.catValorTimbrado.valor == $scope.catalogosCostosAdicionalesAnterioresDTO.catValorTimbrado.valor
	            		&& $scope.catalogosCostosAdicionalesDTO.catValorSpei.valor == $scope.catalogosCostosAdicionalesAnterioresDTO.catValorSpei.valor
	            		&& $scope.catalogosCostosAdicionalesDTO.catPorcPromotorImss.valor == $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcPromotorImss.valor
	            		){
	            	pinesNotifications.notify({
				        title: 'Error',
				        text: 'No se modific&oacute; ning&uacute;n valor de costos adicionales.',
				        type: 'error'
				      });
	            	
	            	return;
	            	
	            }
            	var mensaje = "¿Est\u00e1s seguro que desea modificar los valores de los costos adicionales? <br/><br/> Los valores a modificar son los siguientes: ";
            	mensaje = mensaje + " <br/>  <ol>";
            	if($scope.catalogosCostosAdicionalesDTO.catPorcIndirectos.valor != $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcIndirectos.valor){
            		$scope.catalogosCostosAdicionalesDTO.catPorcIndirectos.editar = 1;
            		mensaje = mensaje + " <li> Porcentaje indirecto <span style='font-weight: bold;'> valor anterior = "+$scope.catalogosCostosAdicionalesAnterioresDTO.catPorcIndirectos.valor+", nuevo valor = "+$scope.catalogosCostosAdicionalesDTO.catPorcIndirectos.valor+"</span></li>";
            	}
            	if($scope.catalogosCostosAdicionalesDTO.catPorcEstrategia.valor != $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcEstrategia.valor){
            		$scope.catalogosCostosAdicionalesDTO.catPorcEstrategia.editar = 1;
            		mensaje = mensaje + " <li> Porcentaje estrategia <span style='font-weight: bold;'> valor anterior = "+$scope.catalogosCostosAdicionalesAnterioresDTO.catPorcEstrategia.valor+", nuevo valor = "+$scope.catalogosCostosAdicionalesDTO.catPorcEstrategia.valor+"</span></li>";
            	}
            	if($scope.catalogosCostosAdicionalesDTO.catPorcPromotorImss.valor != $scope.catalogosCostosAdicionalesAnterioresDTO.catPorcPromotorImss.valor){
            		$scope.catalogosCostosAdicionalesDTO.catPorcPromotorImss.editar = 1;
            		mensaje = mensaje + " <li> Porcentaje Promotor IMSS <span style='font-weight: bold;'> valor anterior = "+$scope.catalogosCostosAdicionalesAnterioresDTO.catPorcPromotorImss.valor+", nuevo valor = "+$scope.catalogosCostosAdicionalesDTO.catPorcPromotorImss.valor+"</span></li>";
            	}
            	if($scope.catalogosCostosAdicionalesDTO.catValorTimbrado.valor != $scope.catalogosCostosAdicionalesAnterioresDTO.catValorTimbrado.valor){
            		$scope.catalogosCostosAdicionalesDTO.catValorTimbrado.editar = 1;
            		mensaje = mensaje + " <li> Valor timbre <span style='font-weight: bold;'> valor anterior = "+$scope.catalogosCostosAdicionalesAnterioresDTO.catValorTimbrado.valor+", nuevo valor = "+$scope.catalogosCostosAdicionalesDTO.catValorTimbrado.valor+"</span></li>";
            	}
            	if($scope.catalogosCostosAdicionalesDTO.catValorSpei.valor != $scope.catalogosCostosAdicionalesAnterioresDTO.catValorSpei.valor){
            		$scope.catalogosCostosAdicionalesDTO.catValorSpei.editar = 1;
            		mensaje = mensaje + " <li> Valor SPEI <span style='font-weight: bold;'> valor anterior = "+$scope.catalogosCostosAdicionalesAnterioresDTO.catValorSpei.valor+", nuevo valor = "+$scope.catalogosCostosAdicionalesDTO.catValorSpei.valor+"</span></li>";
            	}
            	mensaje = mensaje + " </ol>";
            	bootbox
    			.confirm({
    				title : "Confirmar acci&oacute;n",
    				message : mensaje,
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
    					if(result){
	    					$http.post(CONFIG.APIURL + "/catalogoCotizador/modificarCostosAdicionales.json",$scope.catalogosCostosAdicionalesDTO).then(function (response) {
	    	                    console.log("response --> " + response);
	    	                    $scope.cargaInicial();
	    	                }, function (response) {
	
	    	                    console.log("error --> " + response);
	    	                });
    					} else {
    						$scope.cargaInicial();
    					}
    				}
    			});
            }
            
            
            $scope.cargaInicial();
        });
