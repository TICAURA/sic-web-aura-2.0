'use strict';
angular.module('theme.core.templates')
  .controller('conciliacionController2', 
function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications,NgTableParams) {
	   
	$scope.data = {};
	 $scope.data.months = ["ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"];
	 $scope.data.month="";
	 $scope.data.year ="";
     $scope.data.anio ="";
	 $scope.data.day ="";
     $scope.data.days= [];
	 $scope.data.years =['2022'];

	$scope.data.activaSelectMes=true;
	$scope.data.activaSelectDia=true;
	$scope.data.detalleCliente={};
	
	
  

	  $scope.init = function(){
		  $scope.data.catCelula={};
			$http.get(CONFIG.APIURL+ "/conciliacion/pagos/cargaInicialDispersion.json")
			.then(function(response) {
				$scope.catCelula = response.data.catCelula;

			}, function(data) {
				console.log("error --> " + data);
			});
		  
      	  };
      	  
      	  
      	 $scope.getSaldoDisponible=function(){
      		data.listaCentroCostos
      		  if($scope.data!=null && $scope.data.catCelula !=null && $scope.data.catCelula.idCelula !=null){
    			  var centroCostos =  new Array($scope.data.catCelula); 
    			  $scope.data.listaCentroCostos = centroCostos;
    		  }else if($scope.data!=null && $scope.data.catCelula !=null ){
    			  var centroCostos =  new Array($scope.data.catCelula); 
    			  $scope.data.listaCentroCostos = centroCostos;
    		  }
      		$scope.saldoDisponible = 24500635;
      	 }

      	 $scope.calcularTotales=function(){  
      		  $scope.orden= {};
				$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
				$scope.orden.fechaFin = new Date($scope.data.finDate);
				if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
					pinesNotifications.notify({
					        title: 'Error C&aacute;lculo ',
					        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
					        type: 'error'
					      });
				
		}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
			pinesNotifications.notify({
		        title: 'Error C&aacute;lculo ',
		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
		        type: 'error'
		      });
			}else{
              $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleTotales.json",$scope.orden ).then(function (response) {
                  console.log("response --> " + response);
                  $scope.tituloDetalle="Total Dispersiones";
					$scope.totalIngresos = response.data.totalIngresos;
					$scope.totalEgresos= response.data.totalEgresos;
					$scope.totalClientesIngresos = response.data.totalClientesIngresos;
					$scope.totalClientesEgresos = response.data.totalClientesEgresos;
					/* $scope.detalle = response.data.detalleIngresos;
					$scope.tableDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});*/
					$scope.data.orden = {};
					$scope.clientesDetalle=false;
					$scope.egresosDetalle= false;
					$scope.ordenesDetalle= false;
    			        
							
                //  $scope.nombreContadorSelecionado = 'Total de prospectos';
              }, function (response) {
                  console.log("error --> " + response);
              });
          }


      	 };
			
		
      	 $scope.recalcularFechas= function(){ 
      		$scope.clientesDetalle=false;
			$scope.egresosDetalle= false;
			$scope.ordenesDetalle= false; 
			$scope.totalIngresos = 0;
			$scope.totalEgresos= 0;
			$scope.totalClientesIngresos = 0;
			$scope.totalClientesEgresos = 0;
      	 }
      	 
		 $scope.cargaAnioMes = function(){  
		  	$scope.years = [];
//	        $scope.valueYear = new Date().getFullYear();
	        $scope.months = ["ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"]
//	        $scope.month = $scope.months[new Date().getMonth()];
	        
	        for (var i = 0; i < new Date().getFullYear(); i++) {
	        	var anio = new Date().getFullYear()-i;
	        	
	        	if(2020 <= anio){
	        		$scope.years.push(new Date().getFullYear()-i);
	        	}
	        } 
	  };
  
	$scope.calcularRecursos= function(){
			if ($scope.year!="") {
			$scope.activaSelectMes =false;
		}else {
			$scope.activaSelectMes =false;
		}
	}
	$scope.activaMes = function(){
		if ($scope.data.year===undefined  || $scope.data.year==="") {
			$scope.data.activaSelectMes =true;
		}else {
			$scope.data.activaSelectMes =false;
		}
	}
	
	$scope.activaDia = function() {
	
	$scope.data.days=[];
	
	var fecha = $scope.data.year+'/'+$scope.getMes($scope.data.month)+'/01';
	var date = new Date(fecha);
	var primerDia = new Date(date.getFullYear(), date.getMonth(), 1);
	var ultimoDia = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	 var  udm = ultimoDia.getDate();
	for (var i=1; i<=udm; i++){
		 if(i<10){	
			$scope.data.days.push('0'+i);
		}else{
			$scope.data.days.push(''+i);
		}
	}
	
	if ($scope.data.month===undefined  || $scope.data.month==="") {
			$scope.data.activaSelectDia =true;
		}else {
			$scope.data.activaSelectDia =false;
		}
	
	}

	  
	  $scope.getMes = function(mes) {

		 var mesNumerico;
		  
			switch (mes) {
			case "ENERO":
				mesNumerico = '01';
				break;
			case "FEBRERO":
				mesNumerico = '02';
				break;
			case "MARZO":
				mesNumerico = '03';
				break;
			case "ABRIL":
				mesNumerico = '04';
				break;
			case "MAYO":
				mesNumerico = '05';
				break;
			case "JUNIO":
				mesNumerico = '06';
				break;
			case "JULIO":
				mesNumerico = '07';
				break;
			case "AGOSTO":
				mesNumerico = '08';
				break;
			case "SEPTIEMBRE":
				mesNumerico = '09';
				break;
			case "OCTUBRE":
				mesNumerico = 10;
				break;
			case "NOVIEMBRE":
				mesNumerico = 11;
				break;
			case "DICIEMBRE":
				mesNumerico = 12;
				break;

			default:
				mesNumerico ="";
				break;
			}
			
			return mesNumerico;
		}
		
	


		  $scope.getDetalleIngresos = function () {
				//var mesAnio=$scope.getAnioMesDia();
			  $scope.orden= {};
				
				$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
				$scope.orden.fechaFin = new Date($scope.data.finDate);
				if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
					pinesNotifications.notify({
					        title: 'Error C&aacute;lculo ',
					        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
					        type: 'error'
					      });
				
		}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
			pinesNotifications.notify({
		        title: 'Error C&aacute;lculo ',
		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
		        type: 'error'
		      });
			}else{
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleIngresos.json",$scope.orden ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.tituloDetalle="Total depósito";
					/*$scope.totalIngresos = response.data.totalIngresos;
					$scope.totalEgresos= response.data.totalEgresos;
					$scope.totalClientesIngresos = response.data.totalClientesIngresos;
					$scope.totalClientesEgresos = response.data.totalClientesEgresos;*/
                    $scope.detalle = response.data.detalleIngresos;
					$scope.tableDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
					$scope.data.orden = response.data.orden;
					$scope.clientesDetalle=false;
					$scope.ordenesDetalle= true;
					$scope.egresosDetalle= false;
      			        
							
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }

		  }

           $scope.getDetalleEgresos = function () {
        	   $scope.orden= {};
				
				$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
				$scope.orden.fechaFin = new Date($scope.data.finDate);
				if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
					pinesNotifications.notify({
					        title: 'Error C&aacute;lculo ',
					        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
					        type: 'error'
					      });
				
		}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
			pinesNotifications.notify({
		        title: 'Error C&aacute;lculo ',
		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
		        type: 'error'
		      });
			}else{
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleEgresos.json",$scope.orden ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.tituloDetalle="Total depósitos clientes";
					/*$scope.totalIngresos = response.data.totalIngresos;
					$scope.totalEgresos= response.data.totalEgresos;
					$scope.totalClientesIngresos = response.data.totalClientesIngresos;
					$scope.totalClientesEgresos = response.data.totalClientesEgresos;*/
                    $scope.detalle = response.data.detalleEgresos;
					$scope.tableDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
					$scope.data.orden = response.data.orden;    
					$scope.clientesDetalle=false;
      			    $scope.ordenesDetalle= true;
      			    $scope.egresosDetalle= true;
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }
				}
            
             $scope.getDetalleClientesIngresos = function () {
            	  $scope.orden= {};			
  				$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
  				$scope.orden.fechaFin = new Date($scope.data.finDate);
  				if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
  					pinesNotifications.notify({
  					        title: 'Error C&aacute;lculo ',
  					        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
  					        type: 'error'
  					      });
  				
  		}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
  			pinesNotifications.notify({
  		        title: 'Error C&aacute;lculo ',
  		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
  		        type: 'error'
  		      });
  			}else{
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleClientesIngresos.json",$scope.orden ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.tituloDetalle="Total depósitos intercompañias";
                  /*  $scope.totalIngresos = response.data.totalIngresos;
					$scope.totalEgresos= response.data.totalEgresos;
					$scope.totalClientesIngresos = response.data.totalClientesIngresos;
					$scope.totalClientesEgresos = response.data.totalClientesEgresos;*/
                    $scope.detalle = response.data.detalleIngresos;
					$scope.tableClientesDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
      			    $scope.clientesDetalle=true;
      			    $scope.ordenesDetalle= false;
      			    $scope.egresosDetalle= false;
      			  $scope.data.orden = response.data.orden;		
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }}
            
            
             $scope.getDetalleClientesEgresos = function () {
            	  $scope.orden= {};		
  				$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
  				$scope.orden.fechaFin = new Date($scope.data.finDate);
  				if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
  					pinesNotifications.notify({
  					        title: 'Error C&aacute;lculo ',
  					        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin.',
  					        type: 'error'
  					      });
  				
  		}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
  			pinesNotifications.notify({
  		        title: 'Error C&aacute;lculo ',
  		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
  		        type: 'error'
  		      });
  			}else{
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleClientesEgresos.json",$scope.orden ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.tituloDetalle="Devoluciones o rechazos";
                   /* $scope.totalIngresos = response.data.totalIngresos;
					$scope.totalEgresos= response.data.totalEgresos;
					$scope.totalClientesIngresos = response.data.totalClientesIngresos;
					$scope.totalClientesEgresos = response.data.totalClientesEgresos;*/
                    $scope.detalle = response.data.detalleEgresos;
					$scope.tableClientesDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
      			    $scope.clientesDetalle=true;
      			    $scope.ordenesDetalle= false
      			    $scope.egresosDetalle= false;
      			  $scope.data.orden = response.data.orden;		
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }
             }
            
            
            
            
            

	 $scope.mostrarDialogoDisponible = function (orden) {
			$scope.ordenSelect= angular.copy(orden);
			$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
			$scope.orden.fechaFin = new Date($scope.data.finDate);	
			$scope.calcularRecursos();


      }
	 
	 $scope.mostrarDialogoRechazo = function (orden) {
		  $('#mostrarDialogoRechazo').modal('show');	


   }
	  $scope.cerrarDialogoRechazo = function () {
          $('#mostrarDialogoRechazo').modal('hide');
      }
	 

      $scope.cerrarDialogoDisponible = function () {
          $('#mostrarDialogoDisponible').modal('hide');
      }
		
		
		
		$scope.getAnioMesDia = function(){
		var anioMesDia="";
			if ($scope.data.year!="" && $scope.data.year !=undefined){
				 anioMesDia=$scope.data.year;
			}
			
			if(($scope.data.year!="" && $scope.data.year !=undefined) && ($scope.data.month!="" && $scope.data.month !=undefined)){
					anioMesDia=$scope.data.year+$scope.getMes($scope.data.month)
			}
			
			if(($scope.data.year!="" && $scope.data.year !=undefined) 
			&& ($scope.data.month!="" && $scope.data.month !=undefined)
			 &&  ($scope.data.day!="" && $scope.data.day !=undefined)){
				anioMesDia=$scope.data.year+$scope.getMes($scope.data.month)+$scope.data.day;
			}
			
			return anioMesDia;
		}
		
		$scope.reCalcularRecursos= function(){		
		  $scope.orden= {};
			
			$scope.orden.fechaInicio = new Date($scope.data.inicioDate);
			$scope.orden.fechaFin = new Date($scope.data.finDate);
		
			var porcentaje = parseInt($scope.data.porcentajeComision)+parseInt($scope.data.porcentajeGasto) +parseInt($scope.data.porcentajeUtilidad)+parseInt($scope.data.porcentajeSocios);
			
			if ($scope.data.inicioDate ==undefined || $scope.data.finDate ==undefined){
					pinesNotifications.notify({
						  			        title: 'Error C&aacute;lculo ',
						  			        text: 'Al menos debes seleccionar una fecha inicio y una fecha fin, favor de verificar.',
						  			        type: 'error'
						  			      });
				}else if	 ($scope.data.inicioDate > $scope.data.finDate  ){
	  			pinesNotifications.notify({
	  		        title: 'Error C&aacute;lculo ',
	  		        text: 'La fecha inicio, no puede ser mayor a la fecha fin',
	  		        type: 'error'
	  		      });
	  			}
			else if (porcentaje!=100){			
						pinesNotifications.notify({
						  			        title: 'Error Calculo ',
						  			        text: 'La suma del porcentaje de Utilidad, Gasto y Comisiones debe ser 100%, favor de verificar.',
						  			        type: 'error'
						  			      });
			}else {
		//	var anioMesDia=$scope.getAnioMesDia();
		//	 $scope.ordenSelect.anioMesDia = anioMesDia;
			 $scope.ordenSelect.fechaInicio=$scope.orden.fechaInicio;
			 $scope.ordenSelect.fechaFin=$scope.orden.fechaFin;	 
						     $http.post(CONFIG.APIURL + "/conciliacion/pagos/getCalculoCliente.json",$scope.ordenSelect ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.detalleCliente.depositos = response.data.depositos;
					$scope.detalleCliente.disponible = response.data.disponible;
					$scope.detalleCliente.ingresoBruto = response.data.ingresoBruto;
					$scope.detalleCliente.utilidad = response.data.ingresoBruto* $scope.data.porcentajeUtilidad/100;	
					$scope.detalleCliente.gasto = response.data.ingresoBruto * $scope.data.porcentajeGasto/100  ;	
					$scope.detalleCliente.comisiones = response.data.ingresoBruto* $scope.data.porcentajeComision /100;	
					$scope.detalleCliente.socios = response.data.ingresoBruto* $scope.data.porcentajeSocios /100;	
					
					 $scope.disponiblePorcentaje = parseFloat(($scope.detalleCliente.disponible*100)/  $scope.detalleCliente.depositos).toFixed(2);
					 $scope.ingresoPorcentaje =  parseFloat(($scope.detalleCliente.ingresoBruto*100)/ $scope.detalleCliente.depositos).toFixed(2);
					
					
				  $('#mostrarDialogoDisponible').modal('show');
      			        
							
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
			}

		}
	
		
		$scope.calcularRecursos= function(){
		
		//	var anioMesDia=$scope.getAnioMesDia();
		//	$scope.ordenSelect.anioMesDia = anioMesDia;
			$scope.ordenSelect.fechaInicio = new Date($scope.data.inicioDate);
				$scope.ordenSelect.fechaFin = new Date($scope.data.finDate);						
			
			     $http.post(CONFIG.APIURL + "/conciliacion/pagos/getCalculoCliente.json",$scope.ordenSelect ).then(function (response) {
                     $scope.detalleCliente={};
                    $scope.detalleCliente.depositos = response.data.depositos;
					$scope.detalleCliente.disponible = response.data.disponible;
					$scope.detalleCliente.ingresoBruto = response.data.ingresoBruto;
					$scope.detalleCliente.utilidad = response.data.utilidad ;	
					$scope.detalleCliente.gasto = response.data.gasto ;	
					$scope.detalleCliente.comisiones = response.data.comisiones ;	
					$scope.detalleCliente.socios = response.data.socios ;	
					
					 $scope.disponiblePorcentaje = parseFloat(($scope.detalleCliente.disponible*100)/  $scope.detalleCliente.depositos).toFixed(2);
					 $scope.ingresoPorcentaje =  parseFloat(($scope.detalleCliente.ingresoBruto*100)/ $scope.detalleCliente.depositos).toFixed(2);
						$scope.data.porcentajeComision=60;
						$scope.data.porcentajeGasto=18;
						$scope.data.porcentajeUtilidad=18;
						$scope.data.porcentajeSocios=4;
						
				  $('#mostrarDialogoDisponible').modal('show');
      			        
							
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
		}
	
 $scope.init();	
					
  });







