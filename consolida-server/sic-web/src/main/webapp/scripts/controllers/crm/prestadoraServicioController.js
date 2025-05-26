'use strict';
angular.module('theme.core.templates')
  .controller('prestadoraServicioController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, NgTableParams) {
	  
	  $scope.tabSeleccionado = "generales";
	  $scope.inHabilitaEscritura = true;
	  $scope.inHabilitaAgregar = true;
	  $scope.gridPrestadoraServicio = {};
	  $scope.buscarPrestadoraServicioDto = {};
	  $scope.seleccionEditarBorrar=['DIRECTOR_OPERACIONES', 'GERENTE_CELULA'];
	  $scope.seleccionAgregarPrestadora=['TESORERIA'];

	  
	  $scope.cargaInicialRol = function(){
		  
		  $scope.inHabilitaEscritura = true;
		  $scope.inHabilitaAgregar = true;
		  
		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
	            $scope.rolSeleccionado = data.data.usuarioRols[0].rol.nombre.toUpperCase();
	            
				  if($scope.seleccionEditarBorrar.includes($scope.rolSeleccionado)){
					  $scope.inHabilitaEscritura = false;
				  }
				  
				  if($scope.seleccionAgregarPrestadora.includes($scope.rolSeleccionado)){
					  $scope.inHabilitaAgregar = false;
				  }
	            
		  },function(data){
	            console.log("error --> " + data);
	          });
	  }
	  
	  $scope.cargaInicialRol();
	  
	  $scope.init = function() {
			$http.post(CONFIG.APIURL + "/prestadoraServicio/cargaInicial.json").then(
					function(data) {
						$scope.gridPrestadoraServicio = data.data;
						$scope.tableParams = new NgTableParams({page: 1, count: 25}, {data: $scope.gridPrestadoraServicio});
						
					}, function(data) {
						console.log("error --> " + data);
					});
		};
		$scope.init();
  
		
		 $scope.buscar = function(){
			  $http.post(CONFIG.APIURL + "/prestadoraServicio/buscar.json",  $scope.buscarPrestadoraServicioDto).then(
	                  function (data) {
	                	  $scope.gridPrestadoraServicio = data.data;
	                  },
	                  function (data) {
	                      console.log("error buscar--> " + data);
	                  }
	          );
		  };
	  
		
		 $scope.limpiarSession = function(){
			 $http.post(CONFIG.APIURL + "/prestadoraServicio/nuevaPrestadoraServicio.json").then(
	                  function (data) {
	                	  $location.path("/crm/prestadoraServicio/agregarPrestadoraServicio");
	                  },
	                  function (data) {
	                      console.log("error buscar--> " + data);
	                  }
	          );
			 
		 }
		 $scope.fichaTecnicaDto = {} ;
		 $scope.fichaTecnica = function(idPrestadoraServicio){
			 $http.post(CONFIG.APIURL + "/prestadoraServicio/fichaTecnica.json",idPrestadoraServicio).then(
	                  function (data) {
	                	  $scope.fichaTecnicaDto = data.data.fichaTecnica;
	                	  $('#fichaTecnica').modal('show');
	                  },
	                  function (data) {
	                      console.log("error buscar--> " + data);
	                  }
	          ); 
		 }
		 $scope.editarPrestadoraServicio = function(idPrestadoraServicio){
			 
			 $http.post(CONFIG.APIURL + "/prestadoraServicio/editarPrestadoraServicio.json",idPrestadoraServicio).then(
	                  function (data) {
	                	  $location.path("/crm/prestadoraServicio/agregarPrestadoraServicio");
	                  },
	                  function (data) {
	                      console.log("error buscar--> " + data);
	                  }
	          ); 
		 }
		 
		  $scope.eliminarPrestadoraServicio = function(idPrestadoraServicio) {
	          
			  bootbox.confirm({
	              title : "Mensaje confirmaci&oacute;n",
	              message : "\u00bfEst&aacute; seguro que desea eliminar a la prestadora o fondo?",
	              buttons : {
	                 cancel : {
	                    label : '<i class="fa fa-times"></i> CANCELAR',
	                    className : 'btn-danger'
	                 },
	                 confirm : {
	                    label : '<i class="fa fa-check"></i> ACEPTAR',
	                    className : 'btn-primary'
	                 }
	              },
				callback : function(result) {
					
				  if(result){
						 $http.post(CONFIG.APIURL + "/prestadoraServicio/eliminarPrestadoraServicio.json",idPrestadoraServicio).then(function (response) {
							 
							 if(response.data){
								  bootbox.dialog({
		                              message : "La operaci\u00f3n se completo con \u00e9xito",
		                              title : "Mensaje",
		                              buttons : {
		                                  success : {
		                                      label : "ACEPTAR",
		                                      className : "btn-primary",
		                                  }
		                              }
		                          });
								  
								  $scope.init(); 
								  
							  }else{
									bootbox.alert({
										title : "Error",
										message : "Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde",
										buttons : {
											ok : {
												label : 'ACEPTAR',
												className : 'center-block btn-primary'
											}
										},
										callback : function() {
											$scope.init();
										}
									});	
							  }
							  
		                  },function (response) {
		                      console.log("error al eliminar prestadora--> " + response);
		                  }); 

					  
//					  consultaClienteFinalService.eliminarCliente(idCliente, function(response) {
//						  
//						  if(response.data){
//							  bootbox.dialog({
//	                              message : "La operaci\u00f3n se completo con \u00e9xito",
//	                              title : "Mensaje",
//	                              buttons : {
//	                                  success : {
//	                                      label : "Aceptar",
//	                                      className : "btn-primary",
//	                                  }
//	                              }
//	                          });
//							  
//							  $scope.cargaInicial(); 
//							  
//						  }else{
//								bootbox.alert({
//									title : "Error",
//									message : "Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde",
//									buttons : {
//										ok : {
//											label : 'Aceptar',
//											className : 'center-block btn-primary'
//										}
//									},
//									callback : function() {
//										$scope.cargaInicial();
//									}
//								});	
//						  }
//						});
					} 
				  }	
	      	});
		  };
		  
		  
		  $scope.descargarDocumentoByIdCMS = function(idCMS){
	    	  
				
    			$http.get(CONFIG.APIURL + "/documento/documentoByIdCMS/"+ idCMS +".json").then(function (response) {
    				
    				var link = document.createElement("a");
    				   link.href =  response.data.mimeType + response.data.documentoBase64;
                	   link.style = "visibility:hidden";
                	   link.download = response.data.archivo;
                	   document.body.appendChild(link);
                	   link.click();
                	   document.body.removeChild(link);
						
                },
                function (data) {
              	  $log.error(data.status+ ' - '+ data.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
					        type: 'error'
					      });
                });

	      	}
		  
		 
  });