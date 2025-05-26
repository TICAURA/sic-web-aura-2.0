//'use strict';
//angular.module('theme.core.templates')
//  .controller('detalleClienteFinalController', function( $route,$window,$scope, $rootScope, $location, $timeout,$http, CONFIG, $bootbox,$log, clienteFinalService) {
//  
//	  var paramValue = $route.current.params.idCliente;
//      console.log(paramValue); 
//	  $scope.init = function() {
//		  clienteFinalService.detalles(paramValue,function(response){
//
//			  if(response.data){
//				  $scope.clienteTempDto = response.data;
//			  }
//			  
//			 
//		  },
//		  function(response){
//			  $log.error(response.status+ ' - '+ response.statusText);
//				bootbox
//				.alert({
//					title : "Error",
//					message : "Error al cargar el detalle del usuario seleccionado"+response.statusText,
//					buttons : {
//						ok : {
//							label : 'Aceptar',
//							className : 'center-block btn-primary'
//						}
//					},
//					callback : function() {
//						
//					}
//				});
//		  });	}
//		  $scope.init();
//  
//  });