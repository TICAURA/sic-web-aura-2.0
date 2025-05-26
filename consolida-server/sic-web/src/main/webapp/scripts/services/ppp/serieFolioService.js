'use strict';
angular.module('theme.crm').service('serieFolioService',function($http,CONFIG) {
    return {
    	cargaInicialSerieFolio : function(success, error){
    		$http.get(CONFIG.APIURL + '/ppp/serieFolio/cargaInicialSerie.json').then(success,error);
        },
        guardarSerie : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/serieFolio/guardarSerie.json', data).then(success,error);
        },
        eliminarSerie : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/serieFolio/eliminarSerie.json', data).then(success,error);
        },
        guardarFolio : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/serieFolio/guardarFolio.json', data).then(success,error);
        },
        eliminarFolio : function(data,success, error){
    		$http.post(CONFIG.APIURL + '/ppp/serieFolio/eliminarFolio.json', data).then(success,error);
        },  
    };
  });  