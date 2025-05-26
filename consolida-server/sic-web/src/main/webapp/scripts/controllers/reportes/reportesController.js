'use strict';
angular.module('theme.core.templates')
  .controller('reportesController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, finanzasService,reportesService, nominaService, pinesNotifications, NgTableParams) {
	  
	 
	  $scope.reporteDto = {};
	  $scope.celula = {};
	  $scope.usuarioDto= {};
	  
	  // documento excel
      $scope.data = {};
      $scope.dataExcel = {};
      $scope.dataExcel.contentRows = {};
      $scope.registraOrden = {};
      $scope.ordenPago ={};
      $scope.idCatEstatusSelected = null;
      $scope.isVisibleEstimados = false;
      $scope.isVisibleOperaciones = false;
      $scope.isVisibleVariaciones = false;
      $scope.isVisibleColabFaltCrm = false;
      $scope.isDiferenteNominista = false;
      $scope.isVisibleFacturacion = false;
      $scope.isVisibleCamposReporteOperaciones = false;
      $scope.isVisibleCamposReporteVariaciones_Estimados = false;
      $scope.isVisibleCamposReporteFacturacion=false;
      $scope.nombreReporte = "";
      
      
	  
	  $scope.cargaInicial = function(){

	      $scope.isVisibleCamposReporteOperaciones = false;
	      $scope.isVisibleCamposReporteVariaciones_Estimados = false;
		  $scope.deshabilitarCelula = false;
		  $scope.isDiferenteNominista = true;
		 
		  
		  reportesService.cargaInicial(function(response) {

			  $scope.catReportes = response.data.catReportes;
			  $scope.catCelula = response.data.catCelula;
			  $scope.catTipoPeriodo = response.data.catTipoPeriodo;
			  $scope.isVisibleEstimados = false;
		      $scope.isVisibleOperaciones = false;
		      $scope.isVisibleVariaciones = false;
		      $scope.isVisibleColabFaltCrm = false;
		      $scope.cargaAnioMes();
		      
			  if(response.data.usuario != undefined && response.data.usuario !=null){
				  $scope.usuarioDto = response.data.usuario;
				  
				  if($scope.usuarioDto.rol.nombre == "NOMINISTA"){
					  $scope.deshabilitarCelula = true;
					  $scope.isDiferenteNominista = false;
					  $scope.reporteDto.catCelula={};
					  $scope.reporteDto.catCelula.idCelula= $scope.usuarioDto.idUsuarioCelula;
				  }	  
			  }


			},
			function(response) {
				$log.error("error --> " + response);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error, favor de intentarlo mas tarde.',
			        type: 'error'
			      });
			});
	  };
	  
	  $scope.getReporte = function(reporteForm){	
		  
		  
		  if(!reporteForm.centroCostos.$valid){
			  return pinesNotifications.notify({
			        title: 'Reportes',
			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Centro de costos',
			        type: 'error'
			      });	
		  }

		  if(!reporteForm.catReporte.$valid){
			  return pinesNotifications.notify({
			        title: 'Reportes',
			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Tipo reporte',
			        type: 'error'
			      });	
		  }
		  
		  
		  if('REPOR_OPER' === reporteForm.catReporte.$modelValue.clave){
			  
			  if(reporteForm.fechaInicio_.$invalid || reporteForm.fechaFin_.$invalid){
    			  return pinesNotifications.notify({
    			        title: 'Reportes',
    			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Periodo inicio y Periodo fin',
    			        type: 'error'
    			      });
    		  }
			  
		  }else{

			  if('REPOR_EST' === reporteForm.catReporte.$modelValue.clave
					  || 'REPOR_VARI' === reporteForm.catReporte.$modelValue.clave 
					  	|| 'REPOR_FACMES'=== reporteForm.catReporte.$modelValue.clave){
				  
				  if($scope.reporteDto.mes ==null  || $scope.reporteDto.mes == undefined || $scope.reporteDto.mes == "" ||  JSON.stringify($scope.reporteDto.mes)=='{}'){
					  return pinesNotifications.notify({
	  			        title: 'Reportes',
	  			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Mes" ',
	  			        type: 'error'
	  			      });
				  }
				  
				  if($scope.reporteDto.anio ==null  || $scope.reporteDto.anio == undefined || $scope.reporteDto.anio == "" ||  JSON.stringify($scope.reporteDto.anio)=='{}'){
					  return pinesNotifications.notify({
		  			        title: 'Reportes',
		  			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Año" ',
		  			        type: 'error'
		  			      });
				  }
				  
				  if('REPOR_FACMES' != reporteForm.catReporte.$modelValue.clave
					 ){
				  
				  if($scope.reporteDto.catTipoPeriodo == null || $scope.reporteDto.catTipoPeriodo == undefined ||  JSON.stringify($scope.reporteDto.catTipoPeriodo)=='{}') {
	    			  return pinesNotifications.notify({
	    			        title: 'Reportes',
	    			        text: 'El formulario tiene un error. <br>Favor de seleccionar "Quincena"',
	    			        type: 'error'
	    			      });
	    		  }
				  }
			  }
		  }
		  
		  if(reporteForm.catReporte.$invalid){
			  
			  if(reporteForm.centroCostos.$invalid){
    			  return pinesNotifications.notify({
    			        title: 'Reportes',
    			        text: 'El formulario tiene un error. <br>Favor de ingresar "Centro de costos"',
    			        type: 'error'
    			      });
    		  }
			  
			  if(reporteForm.catReporte.$invalid){
    			  return pinesNotifications.notify({
    			        title: 'Reportes',
    			        text: 'El formulario tiene un error. <br>Favor de ingresar "Tipo reporte"',
    			        type: 'error'
    			      });
    		  }
		  }
		  
		  
		  var fechaInicio = null;
		  var fechaFin = null;
		  if($scope.reporteDto!= null){

			  if($scope.reporteDto.fechaInicio !=null && $scope.reporteDto.fechaFin !=null){
				  
				  fechaInicio= new Date($scope.reporteDto.fechaInicio);
				  fechaFin = new Date($scope.reporteDto.fechaFin);
				  
				  if(fechaFin < fechaInicio){
			    	  
			    	 return pinesNotifications.notify({
					        title: 'Reportes',
					        text: 'La FECHA PERIODO FIN no puede ser menor a la FECHA PERIODO INICIO',
					        type: 'error'
					      });
			      }
				  
			  }
		  }
		  	      
		  if($scope.reporteDto!=null && $scope.reporteDto.catCelula !=null && $scope.reporteDto.catCelula.idCelula !=null){
			  var centroCostos =  new Array($scope.reporteDto.catCelula); 
			  $scope.reporteDto.listaCentroCostos = centroCostos;
		  }else if($scope.reporteDto!=null && $scope.reporteDto.catCelula !=null ){
			  var centroCostos =  new Array($scope.reporteDto.catCelula); 
			  $scope.reporteDto.listaCentroCostos = centroCostos;
		  }
		  
		  
		  var mesCongelado = null;
		  if('REPOR_OPER' === reporteForm.catReporte.$modelValue.clave){
			  
			  $scope.reporteDto.mes = null;
			  $scope.reporteDto.anio = null;
			  $scope.reporteDto.catTipoPeriodo = null;
			  
		  }else if ('COLAB_FALT_CRM' === reporteForm.catReporte.$modelValue.clave){
			  $scope.reporteDto.mes = null;
			  $scope.reporteDto.anio = null;
			  $scope.reporteDto.catTipoPeriodo = null;
			  $scope.reporteDto.fechaFin = null;
			  $scope.reporteDto.fechaInicio = null;
			  $scope.reporteDto.catCelula = null;
			  
		  }else{
			  mesCongelado = angular.copy($scope.reporteDto.mes);
			  $scope.reporteDto.mes = $scope.getMes($scope.reporteDto.mes); 
		  }

		  $http.post(CONFIG.APIURL + "/reportes/getReporte.json", $scope.reporteDto).then(function(response) {
			  
			  if($scope.reporteDto.catReporte.clave == 'REPOR_EST'){
				  
			      $scope.isVisibleEstimados = true;
			      $scope.isVisibleOperaciones = false;
			      $scope.isVisibleVariaciones = false;
			      $scope.isVisibleColabFaltCrm = false;
			      $scope.isVisibleFacturacion = false;
			      $scope.nombreReporte = "REPORTE ESTIMADOS";
				  
			  }else  if($scope.reporteDto.catReporte.clave == 'REPOR_OPER'){
				  
			      $scope.isVisibleEstimados = false;
			      $scope.isVisibleOperaciones = true;
			      $scope.isVisibleVariaciones = false;
			      $scope.isVisibleColabFaltCrm = false;
			      $scope.isVisibleFacturacion = false;
			      $scope.nombreReporte = "REPORTE OPERACIONES";
				  
			  }else  if($scope.reporteDto.catReporte.clave == 'REPOR_VARI'){
				  
			      $scope.isVisibleEstimados = false;
			      $scope.isVisibleOperaciones = false;
			      $scope.isVisibleVariaciones = true;
			      $scope.isVisibleColabFaltCrm = false;
			      $scope.isVisibleFacturacion = false;
			      $scope.nombreReporte = "REPORTE VARIACIONES";
				  
			  }else  if($scope.reporteDto.catReporte.clave == 'COLAB_FALT_CRM'){
				  
			      $scope.isVisibleEstimados = false;
			      $scope.isVisibleOperaciones = false;
			      $scope.isVisibleVariaciones = false;
			      $scope.isVisibleColabFaltCrm = true;
			      $scope.isVisibleFacturacion = false;
			      $scope.isVisibleFacturacion = false;
			      $scope.nombreReporte = "COLABORADORES FALTANTES EN CRM";
				  
			  }else  if($scope.reporteDto.catReporte.clave == 'REPOR_FACMES'){
				  
			      $scope.isVisibleEstimados = false;
			      $scope.isVisibleOperaciones = false;
			      $scope.isVisibleVariaciones = false;
			      $scope.isVisibleColabFaltCrm = false;
			      $scope.isVisibleFacturacion = true;
			      $scope.nombreReporte = "FACTURACIÓN MENSUAL";
				  
			  }
			 
			  
			  if(mesCongelado!=null){
				  $scope.reporteDto.mes = mesCongelado;
			  }
			  
			  if($scope.usuarioDto.rol.nombre == "NOMINISTA"){
				  $scope.deshabilitarCelula = true;
				  $scope.isDiferenteNominista = false;
				  $scope.reporteDto.catCelula={};
				  $scope.reporteDto.catCelula.idCelula= $scope.usuarioDto.idUsuarioCelula;
			  }	

			 $scope.gridReporte = response.data.reporte;
			 $scope.tableReporte = new NgTableParams({page: 1, count: 25}, {data: response.data.reporte});
			  

			}, function(data) {
				console.log("error getReporte--> " + data);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al cargar los datos, favor de intentarlo nuevamente.',
			        type: 'error'
			      });
			}); 
	  };
	  
	  $scope.limparCampos = function(reporteForm){
		  $scope.isVisibleEstimados = false;
	      $scope.isVisibleOperaciones = false;
	      $scope.isVisibleVariaciones = false;
	      $scope.isVisibleColabFaltCrm = false;
	      
//	      $scope.reporteDto.catReporte = {};
	      $scope.reporteDto.fechaInicio = "";
	      $scope.reporteDto.fechaFin = "";
	      $scope.reporteDto.catTipoPeriodo = {};
	      $scope.reporteDto.mes ={};
	      $scope.reporteDto.anio ={};
	      $scope.gridReporte = 0;

	  };
	  
	  $scope.validaCamposReporte = function(reporte, reporteForm){
		  
		  $scope.limparCampos();
		  
		  this.reporteForm.$pristine=false;
		  
		  if('REPOR_VARI' === reporte.clave || 'REPOR_EST' === reporte.clave){
		      $scope.isVisibleCamposReporteOperaciones = false;
		      $scope.isVisibleCamposReporteVariaciones_Estimados = true;
		      $scope.isVisibleCamposReporteFacturacion = false;
		  }else if ('REPOR_OPER' === reporte.clave){
		      $scope.isVisibleCamposReporteOperaciones = true;
		      $scope.isVisibleCamposReporteVariaciones_Estimados = false;  
		      $scope.isVisibleCamposReporteFacturacion = false;
		  }else if ('COLAB_FALT_CRM' === reporte.clave){
		      $scope.isVisibleCamposReporteOperaciones = false;
		      $scope.isVisibleCamposReporteVariaciones_Estimados = false;  
		      $scope.isVisibleCamposReporteFacturacion = false;
		  }else if ('REPOR_FACMES' === reporte.clave){
		      $scope.isVisibleCamposReporteOperaciones = false;
		      $scope.isVisibleCamposReporteVariaciones_Estimados = false;  
		      $scope.isVisibleCamposReporteFacturacion = true;
		  }
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
	  
	  $scope.getMes = function(mes) {

		 var mesNumerico;
		  
			switch (mes) {
			case "ENERO":
				mesNumerico = 1;
				break;
			case "FEBRERO":
				mesNumerico = 2;
				break;
			case "MARZO":
				mesNumerico = 3;
				break;
			case "ABRIL":
				mesNumerico = 4;
				break;
			case "MAYO":
				mesNumerico = 5;
				break;
			case "JUNIO":
				mesNumerico = 6;
				break;
			case "JULIO":
				mesNumerico = 7;
				break;
			case "AGOSTO":
				mesNumerico = 8;
				break;
			case "SEPTIEMBRE":
				mesNumerico = 9;
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
				mesNumerico = 0;
				break;
			}
			
			return mesNumerico;
		}

	 	  
	  $scope.cargaInicial();

  });