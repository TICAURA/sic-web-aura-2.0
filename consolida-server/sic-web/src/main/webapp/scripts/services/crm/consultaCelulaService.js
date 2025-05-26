'use strict';
angular.module('theme.crm').service('consultaCelulaService',function($http) {
	 
    var APIURL  = '/sic-web'
    	
    	return {
  	  		celulaCargaInicial : function(success, error){
  	  			$http.get(APIURL+'/celula/cargaInicial.json').then(success, error);
  	  		},
  	  		consultarDatosCelula: function(data, success, error){
  	  			$http.get(APIURL+'/celula/consultarDatosCelula.json', data).then(success, error);
  	  		},
  	  		getRfcCelula: function(data, success, error){
  	  			$http.post(APIURL+'/celula/getRfcCelula.json', data).then(success, error);
  	  		},
  	  		eliminarCelula: function(data, success, error){
  	  			$http.post(APIURL+'/celula/eliminarCelula.json', data).then(success, error);
  	  		}
    };
  });