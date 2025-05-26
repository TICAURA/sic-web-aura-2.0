'use strict';
angular.module('theme.crm').service('autorizaTesoreriaService',function($http,CONFIG) {
	
	return {
		cargaInicialTesoreriaNominaParaAutorizarFinanciamiento : function(success, error) {
			$http.get(CONFIG.APIURL+ '/pppTesoreria/nominas/cargaInicialTesoreriaNominaParaAutorizarFinanciamiento.json').then(success, error);
		},
		autorizarFinanciamientoTesoreria : function(data,success, error) {
			$http.post(CONFIG.APIURL+ '/pppTesoreria/nominas/autorizarFinanciamientoTesoreria.json',data).then(success, error);
		},
		rechazarFinanciamientoTesoreria : function(data,
				success, error) {$http.post(CONFIG.APIURL+ '/pppTesoreria/nominas/rechazarFinanciamientoTesoreria.json',data).then(success, error);
		},
		
	};
	
}); 
