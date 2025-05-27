'use strict';
angular.module('theme.crm').service('nominaService',function($http,CONFIG) {
    return {
    	cargaInicialNomina : function(success, error){
    		$http.get(CONFIG.APIURL + '/ppp/nominas/cargaInicialNomina.json').then(success,error);
        },
        detalleNominasClienteByIdCliente : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getNominasClienteByIdCliente.json', data).then(success,error);
        },
        guardarNominaPpp : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardarNominaPpp.json', data).then(success,error);
        },
        guardarNominaPppComplementaria : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardarNominaPppComplementaria.json', data).then(success,error);
        },
         listaNominaEnProceso : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/listaNominaEnProceso.json', data).then(success,error);
        },
             listaNominaAcomplementar : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/listaNominaAcomplementar.json', data).then(success,error);
        },
        getDatosNominaByIdNomina : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getDatosNominaByIdNomina.json', data).then(success,error);
        },
        getDatosFacturaByIdNomina : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getFacturacionByNomina.json', data).then(success,error);
        },
        cargaInicialNominaCatalogos : function(success, error){
    		$http.get(CONFIG.APIURL + '/ppp/nominas/cargaInicialNominaCatalogos.json').then(success,error);
        },
        guardaNominaComplemento : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardaNominaComplemento.json',data).then(success,error);
        },
         guardaNominaByIdNominaComplementaria : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardaNominaByIdNominaComplementaria.json',data).then(success,error);
        },
        getDatosNominaComplemento : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getDatosNominaComplemento.json',data).then(success,error);
        },
        guardarColaboradores : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardarColaboradores.json',data).then(success,error);
        },
        getHistoricoByIdPppNomina : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getHistoricoByIdPppNomina.json',data).then(success,error);
        },
        cargarDatosDespuesDeGuardarComplemento : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/cargarDatosDespuesDeGuardarComplemento.json',data).then(success,error);
        },
        bloquearColaborador : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/bloquearColaborador.json',data).then(success,error);
        },
        desbloquearColaborador : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/desbloquearColaborador.json',data).then(success,error);
        },
        getFormulaFactura : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getFormulaFactura.json', data).then(success,error);
        },
        guardarNominaFacturaFlujoAlterno : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/guardarNominaFacturaFlujoAlterno.json', data).then(success,error);
        },
        cancelarNomina : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/cambiaEstatusNominaCancelada.json', data).then(success,error);
        },
          getDatosNominaByIdNominaComplementaria : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/nominas/getDatosNominaByIdNominaComplementaria.json', data).then(success,error);
        },
    };
  }); 