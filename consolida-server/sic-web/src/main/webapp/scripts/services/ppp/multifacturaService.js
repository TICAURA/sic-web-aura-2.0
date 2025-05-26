'use strict';
angular.module('theme.crm').service('multifacturaService',function($http,CONFIG) {
    return {
    	cargaInicialMultifactura : function(success, error){
    		$http.get(CONFIG.APIURL + '/ppp/multifactura/cargaInicialMultifactura.json').then(success,error);
        },
        
        getFacturacionByNominaCliente : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/multifactura/getFacturacionByNominaCliente.json', data).then(success,error);
        },
        getFactura : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/multifactura/getFactura.json', data).then(success,error);
        },
        listaNominasFacturar : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/multifactura/listaNominasFacturar.json', data).then(success,error);
        },
        listaFacturasDisponibles : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/multifactura/listaFacturasDisponibles.json', data).then(success,error);
        },
        listaFacturasGeneradas : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/multifactura/listaFacturasGeneradas.json', data).then(success,error);
        },
 
        
        
        
    
       
    };
  }); 