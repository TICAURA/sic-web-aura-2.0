'use strict';
angular.module('theme.crm').service('registroCelulaService',function($http) {
	 
    var APIURL  = '/sic-web'
    	
    	return {
    	initCargaInicialActualizaCelula : function (success, error){
	  			$http.post(APIURL+'/celula/initCargaInicialActualizaCelula.json').then(success, error);
	  		},
  	  		guardarCelula : function(data,success, error){
  	  			$http.post(APIURL+'/celula/guardarCelula.json', data).then(success, error);
  	  		}
  	  		
    };
  });