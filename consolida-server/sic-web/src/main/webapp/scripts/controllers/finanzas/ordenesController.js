'use strict';

angular.module('theme.core.templates')
  .controller('ordenesController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications,NgTableParams) {
	  
	 

	
		$scope.data={};		 
		$scope.dataPagos = {};
		$scope.tablePagosParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataPagos});
		$scope.dataCargas = {};
		$scope.tableCargasParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataCargas});
		$scope.cargaSelect ={};
		$scope.cargaSelect.descCarga="";
		
	
		

	     $scope.init = function(){
      		  $http.get(CONFIG.APIURL + "/conciliacion/pagos/cargaInicialOrdenes.json").then(function(data){
							$scope.dataCargas = data.data;
							$scope.tableCargasParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataCargas.listaCargasOrdenes});
      			        							  $scope.mostrarBotonGuardarPagos =false;

      			          },function(data){
      			            console.log("error --> " + data);
      			        });
      	  };
					
			 $scope.init();		
		
		 $scope.mostrarDialogo = function () {
          $('#cargarDocumento').modal('show');
      }

      $scope.cerrar = function () {
          $('#cargarDocumento').modal('hide');
      }


		 $scope.fileChanged = function (documento) {
          var lstArchivos = documento.files;

          var val = lstArchivos[0].name.toLowerCase();
          var regex = new RegExp(".(xls|xlsx)$");

          if (!(regex.test(val))) {
              $(this).val('');
              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.xls y/o .xlsx</b>";
              alert($scope.mensaje);

          } else if (lstArchivos[0].size > 2097152) {
              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
              $scope.$apply();
              alert($scope.mensaje);
              return;
          } else {
              var reader = new FileReader();
              reader.onloadend = function () {
                  $log.debug("Archivo cargado memoria");
                  $scope.data.archivo = reader.result.substr(reader.result.indexOf(',') + 1);
                  $scope.data.nombreArchivo = lstArchivos[0].name;
                  $scope.data.tamanioArchivo = lstArchivos[0].size;
                  $scope.registrarDocumento();
                 
              }
              reader.readAsDataURL(lstArchivos[0]);
			
          }
	
      };


		   $scope.registrarDocumento = function () {
      
    
          $http.post(CONFIG.APIURL + "/conciliacion/pagos/createFile.json", $scope.data).then(function (response) {
			
        	  $scope.dataPagos={};
        	  $scope.tablePagosParams={};
          	     
          	   if (response.data.errorCargaLayout !='OK'){
          		   
          		   
          		 pinesNotifications.notify({
	   			        title: 'Carga ordenes de dep&oacute;sito',
	   			        text: 'Error: ' + response.data.errorCargaLayout ,
	   			        type: 'error'
	   			      });
					}else{
						
						$scope.dataPagos=response.data;
						 $scope.tablePagosParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataPagos.contentRows});
						
						
		          	     $scope.mostrarBotonGuardarPagos =response.data.errorLayout;
		          	     
		          	   pinesNotifications.notify({
		   			        title: 'Carga ordenes de dep&oacute;sito',
		   			        text: ' Un layout de ordenes de dep&oacute;sito ha sido cargado, por favor verificar.',
		   			        type: 'success'
		   			      });
						
					}


					

          }, function (data) {
              $scope.dataPagos = undefined;
              console.log("error --> " + data);
			

          });
			$scope.cerrar();
      }
		
		
		 $scope.guardarColaboradores = function(){
  	 
  	    $scope.priue="";
		} 
		
		$scope.getCargaOrden  = function(carga){
		
      		  			$http.post(CONFIG.APIURL + "/conciliacion/pagos/getCarga.json", carga).then(function(response){
							
							$scope.dataPagos=response.data;
							$scope.dataPagos.reg= response.data.contentRows.length;
							$scope.dataPagos.regCorrectos=response.data.contentRows.length;
							$scope.dataPagos.regDevueltos=0;
							$scope.dataPagos.regIncorrecto=0;
							$scope.dataPagos.totalMonto=0;
							$scope.dataPagos.totalMontoDevuelto=0;
							$scope.dataPagos.totalMontoIncorrecto=0;
							var total =0;
							response.data.contentRows.forEach(dato =>total += dato.monto);
							$scope.dataPagos.totalMontoCorrecto=total;
							$scope.dataPagos.totalMonto=total;
				 $scope.tablePagosParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataPagos.contentRows});
							$scope.error ="";
      			          },function(data){
      			            console.log("error --> " + data);
      			        });
      	 		 };



		          
		
		 $scope.confirmaGuardarOrdenes = function (nomina){
		    	   
					  bootbox.confirm({
						  title : "Confirmar Guardado de dep&oacute;sitos",
							message : "¿Esta seguro que desea guardar las ordenes de dep&oacute;sito?",
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
									$scope.cargaSelect.descCarga=""; 
									
									$('#modalDescripcionGuardaPagos').modal('show');
								}
							}
					  	});
					}
				
				$scope.guardarOrdenes = function(carga) {
						
				
						carga.ordenesPago = $scope.dataPagos.contentRows;
				
						$http.post(CONFIG.APIURL + "/conciliacion/pagos/guardarOrdenes.json", carga).then(function (response) {
							
						  //  $scope.dataPagos=response.data;
						//	$scope.tablePagosParams = new NgTableParams({page: 1, count: 25}, {data: $scope.dataPagos.contentRows});
							$scope.error ="";
							$scope.init();	
							
							 pinesNotifications.notify({
				   			        title: 'Carga ordenes de dep&oacute;sito',
				   			        text: ' Las ordenes de dep&oacute;sito se guardaron correctamente.',
				   			        type: 'success'
				   			      });
							
						
          					}, function (data) {
             					 $scope.dataPagos = undefined;
             						 console.log("error --> " + data);
             						 pinesNotifications.notify({
             				        title: 'Carga ordenes de dep&oacute;sito',
				   			        text: ' Ocurrio un error al guardar las ordenes de dep&oacute;sito.',
				   			        type: 'error'
				   			      });
	        				  });
							}
							
							
							
							
					
  });