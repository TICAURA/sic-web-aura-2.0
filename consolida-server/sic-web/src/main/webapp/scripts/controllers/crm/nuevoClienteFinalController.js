//'use strict';
//angular.module('theme.core.templates')
//  .controller('clienteController',  function($scope, $location, $timeout,$http, CONFIG, $bootbox,$log, clienteFinalService) {
//
//	  var data = {};
//	  $scope.clienteTempDto = {};
//	  
//    var APIURL  = '/sic-web'
//    	
//    	
//    	$scope.guardar = function() {
//
//    	bootbox
//		.confirm({
//			title : "Confirmar acción",
//			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
//			buttons : {
//				confirm : {
//					label : 'S\u00ed',
//					className : 'btn-primary'
//				},
//				cancel : {
//					label : 'No',
//					className : 'btn-danger'
//				}
//			},
//			callback : function(result) {
//				if (result) {
//
//					 var data = {
//                    'clienteTempDto' : $scope.clienteTempDto
//                };
//					clienteFinalService
//							.guardar(data.clienteTempDto,function() {
//								
//										$log.debug('ok');
//										bootbox
//												.alert({
//													title : "Mensaje",
//													message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
//													buttons : {
//														ok : {
//															label : 'Aceptar',
//															className : 'center-block btn-primary'
//														}
//													},
//													callback : function() {
//														
//													}
//												});
//										location.href=APIURL+"#/crm/clientes";
//									},
//									function(response) {
//										$log.error(response.status+ ' - '+ response.statusText);
//										bootbox
//										.alert({
//											title : "Error",
//											message : "Faltan campos por ingresar, favor de verificar",
//											buttons : {
//												ok : {
//													label : 'Aceptar',
//													className : 'center-block btn-primary'
//												}
//											},
//											callback : function() {
//												
//											}
//										});
//
//									});
//
//				}
//			}
//		});
//    }
//
//
//    var passTOJson = function(data) {
//
//        var json = JSON.stringify(data, function(key, value) {
//            if (key === ",") {
//                return undefined;
//            }
//
//            return value;
//        });
//
//        return json;
//    };
//   
//    $scope.limpiarSeleccion = function(seleccion) {
//    	if(seleccion ===0){
//    		$scope.clienteTempDto.nombre = null;
//    		$scope.clienteTempDto.apellidoPaterno = null;
//    		$scope.clienteTempDto.apellidoMaterno = null;
//    	}else{
//    		$scope.clienteTempDto.razonSocial = null;
//    		$scope.clienteTempDto.contacto = null;
//    	}
//    }
//
//    $scope.cancelar = function() {
//    	location.href=APIURL+"#/crm/clientes";
//    }
// 
//  });
