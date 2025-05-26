'use strict';
angular.module('theme.crm')
  .service('subirVariablesService',function($http,CONFIG) {
    	return {
    	seleccionCotizacion : function(data, success, error){
    		$http.post(CONFIG.APIURL + "/cotizador/seleccionCotizacion.json", data).then(success,error);
          }  
    };
  });  
