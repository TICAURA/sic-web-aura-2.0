'use strict';
angular.module('theme.core.templates')
  .controller('consolidadoController', function ($scope, $location, $timeout, $http, CONFIG, $bootbox, $log, $window, pinesNotifications,NgTableParams) {
	  
			$scope.data = {};
	 $scope.data.months = ["ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"];
	 $scope.data.month="";
	 $scope.data.year ="";
     $scope.data.anio ="";
	 $scope.data.day ="";
     $scope.data.days= [];
	 $scope.data.years =['2022'];


	  $scope.init = function(){
		var aniomes=$scope.getAnioMesDia();
		$scope.mostrarDetalleCC= false;

      		
               $http.get(CONFIG.APIURL + "/conciliacion/pagos/cargaInicialConsolidado.json", aniomes).then(function(response){
            	   $scope.data.orden="";
            	 //$scope.data.porcentajeUtilidad=parseFloat(18.00);
            		    $scope.data.porcentajeSocios=22.00;
            			$scope.data.porcentajeGasto=18.00;
            			$scope.data.porcentajeUtilidad=0.00;//inversiones
						$scope.data.porcentajeComision=60.00;
						$scope.data.porcentajeSociosD=22.00;
	            		$scope.data.porcentajeGastoD=18.00;
	            		$scope.data.porcentajeUtilidadD=0.00;//inversiones
						$scope.data.porcentajeComisionD=60.00;	
						$scope.data.porcentajeSociosInt=22;
            			$scope.data.porcentajeGastoInt=18;
            			$scope.data.porcentajeUtilidadInt=0;//inversiones
						$scope.data.porcentajeComisionInt=60;
						  },function(data){
      			            console.log("error --> " + data);
      			        });
      	  };
      	  


			$scope.activaMes = function(){
		if ($scope.data.year===undefined  || $scope.data.year==="") {
			$scope.data.activaSelectMes =true;
		}else {
			$scope.data.activaSelectMes =false;
		}
	}
	
	
	$scope.calcular = function(){
	//	var porcentaje = parseInt($scope.data.porcentajeComision)+parseInt($scope.data.porcentajeGasto) +parseInt($scope.data.porcentajeUtilidad)+parseInt($scope.data.porcentajeSocios);
		var porcentaje = Math.round(parseFloat($scope.data.porcentajeComisionD)+parseFloat($scope.data.porcentajeGastoD) +parseFloat($scope.data.porcentajeUtilidadD)+parseFloat($scope.data.porcentajeSociosD));
		
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
	}
		else if (porcentaje!=100){			
			pinesNotifications.notify({
			  			        title: 'Error Calculo ',
			  			        text: 'La suma del porcentaje de Utilidad, Gasto, Socios y Comisiones debe ser 100%, favor de verificar.',
			  			        type: 'error'
			  			      });
}else {


var anioMesDia= '' + $scope.getAnioMesDia();
		var anioMesDia= null;

      		  $http.post(CONFIG.APIURL + "/conciliacion/pagos/calcularCosolidado.json", $scope.orden).then(function(response){
      			$scope.mostrarDetalleCC= true;		
      			            $scope.depositos = response.data.depositos;
					$scope.disponible = response.data.disponible;
					$scope.ingresoBruto = response.data.ingresoBruto;
					$scope.data.utilidad = response.data.ingresoBruto * $scope.data.porcentajeUtilidadD/100;	//inversiones
					$scope.data.gasto = response.data.ingresoBruto *$scope.data.porcentajeGastoD/100 ;	
					$scope.data.comisiones = response.data.ingresoBruto*$scope.data.porcentajeComisionD/100 ;
					$scope.data.socios = response.data.ingresoBruto*$scope.data.porcentajeSociosD/100 ;
					$scope.gridlistCC = response.data.listCC;
			
					for(var i=0; i<$scope.gridlistCC.length; i++){
				$scope.gridlistCC[i].socios = response.data.listCC[i].ingresoBruto*($scope.data.porcentajeSociosD/100);					
				$scope.gridlistCC[i].gasto = response.data.listCC[i].ingresoBruto*($scope.data.porcentajeGastoD/100);
				$scope.gridlistCC[i].utilidad = response.data.listCC[i].ingresoBruto* ($scope.data.porcentajeUtilidadD/100);			
				$scope.gridlistCC[i].comisiones = response.data.listCC[i].ingresoBruto*($scope.data.porcentajeComisionD/100);				
					}

					$scope.data.orden = response.data.orden;
					$scope.disponiblePorcentaje = parseFloat((response.data.disponible*100)/ $scope.depositos).toFixed(2);
					$scope.ingresoPorcentaje =  parseFloat((response.data.ingresoBruto*100)/$scope.depositos).toFixed(2);
					
					$scope.tableDetalleCCParams = new NgTableParams({page: 1, count: 10}, {data: $scope.gridlistCC});
					
					  },function(data){
      			            console.log("error --> " + data);
      			        });
				}
      	  };
	
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
				mesNumerico = '10';
				break;
			case "NOVIEMBRE":
				mesNumerico = '11';
				break;
			case "DICIEMBRE":
				mesNumerico = '12';
				break;

			default:
				mesNumerico ="";
				break;
			}
			
			return mesNumerico;
		}


					
			 $scope.init();		
		
	


		  $scope.getDetalleIngresos = function () {
				var mesAnio='092022';
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleIngresos.json",mesAnio ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.detalle = response.data.detalleIngresos;
					$scope.tableDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
      			        
							
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }

           $scope.getDetalleEgresos = function () {
				var mesAnio='092022';
                $http.post(CONFIG.APIURL + "/conciliacion/pagos/getDetalleEgresos.json",mesAnio ).then(function (response) {
                    console.log("response --> " + response);
                    $scope.detalle = response.data.detalleEgresos;
					$scope.tableDetalleParams = new NgTableParams({page: 1, count: 10}, {data: $scope.detalle});
      			        
							
                  //  $scope.nombreContadorSelecionado = 'Total de prospectos';
                }, function (response) {
                    console.log("error --> " + response);
                });
            }
           
           
       	
  		 $scope.mostrarDialogo = function (dato, sol) {		 			
  			$scope.data.tipo=sol;
  			$scope.data.variablePorcentaje=dato;		 					 
            $('#modalAgregarPorcentaje').modal('show');
        }
  	   $scope.cerrar = function () {
  		 $scope.data.tipo=0;
  		$scope.data.variablePorcentaje=0;
           $('#modalAgregarPorcentaje').modal('hide');
       }
  	   
  	 $scope.guardarPorcentaje =function (dato) {
  		 if( $scope.data.tipo==1){
  			$scope.data.porcentajeSociosD =parseFloat(dato).toFixed(8);
  			$scope.data.porcentajeSocios =parseFloat(dato).toFixed(2);
  			$scope.data.porcentajeSociosInt =parseInt(dato);
  		 }
  		 else if( $scope.data.tipo==2){
  			$scope.data.porcentajeGastoD =parseFloat(dato).toFixed(8);
  			$scope.data.porcentajeGasto =parseFloat(dato).toFixed(2);
  			$scope.data.porcentajeGastoInt=parseInt(dato);
  		 }
  		 else if( $scope.data.tipo==3){ //inversiones 
  			$scope.data.porcentajeUtilidadD =parseFloat(dato).toFixed(8);
  			$scope.data.porcentajeUtilidad =parseFloat(dato).toFixed(2);
  			$scope.data.porcentajeUtilidadInt=parseInt(dato);
  		 }
  		 else if( $scope.data.tipo==4){
  			$scope.data.porcentajeComisionD =parseFloat(dato).toFixed(8);
  			$scope.data.porcentajeComision =parseFloat(dato).toFixed(2);
  			$scope.data.porcentajeComisionInt=parseInt(dato);
  		 }
  		 $scope.data.tipo=0;
  		$scope.data.variablePorcentaje=0;
  	 }
  	
					
  });