'use strict';

/**
 * @ngdoc function
 * @name maverickApp.controller:AdminServidoresCtrl
 * @description
 * # AdminServidoresCtrl
 * Controller of the maverickApp
 */
angular.module('theme.crm',['ngGrid'])
  .controller('clienteFileExcelController', function ($scope,$http,$location,CONFIG,$bootbox,$log) {
	  
	  	$scope.tituloPantalla = "Carga de archivos...";
	  	$scope.data = {};
	  	$scope.dataExcel = undefined;
	  	
	  	
	    $scope.fileChanged = function(documento) {
	        var lstArchivos = documento.files;
	        
	        var val = lstArchivos[0].name.toLowerCase();
	        var regex = new RegExp("(.*?)\.(xlsx)$");
	        
	        if (!(regex.test(val))) {
	            $(this).val('');
	            $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.xlsx</b>";
	            alert($scope.mensaje);
	            
	        } else if (lstArchivos[0].size > 2097152) {
	            $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
	            $scope.$apply();
	            alert($scope.mensaje);
	        } else {
	            var reader = new FileReader();
	            reader.onloadend = function() {
	                $log.debug("Archivo cargado memoria");
	                $scope.data.archivo = reader.result;
	                $scope.data.nombreArchivo = lstArchivos[0].name;
	                $scope.data.tamanioArchivo = lstArchivos[0].size;
	                $scope.registrarDocumento();
	            }
	            reader.readAsDataURL(lstArchivos[0]);
	        }
	        
		};
		
		$scope.registrarDocumento = function(){
			
			 $http.post(CONFIG.APIURL +"/archivo/xlsFile.json",$scope.data).then(function(response){
				 
				 $scope.dataExcel = response.data;
				 
				 $scope.headerTable = $scope.dataExcel[0];
				 
				 
				 
		      },function(data){
		    	$scope.dataExcel = undefined;
		        console.log("error --> " + data);
		      });
		}
		
		

  });
