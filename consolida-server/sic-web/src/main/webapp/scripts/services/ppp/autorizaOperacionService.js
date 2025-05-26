'use strict';
angular.module('theme.crm').service('autorizaOperacionService',function($http, CONFIG) {

	return {
		cargaInicialNominaParaAutorizarFinanciamiento : function(success, error) {
			$http.get(CONFIG.APIURL+ '/pppOperaciones/nominas/cargaInicialNominaParaAutorizarFinanciamiento.json').then(success, error);
		},
		autorizarFinanciamientoOperaciones : function(data,success, error) {
			$http.post(CONFIG.APIURL+ '/pppOperaciones/nominas/autorizarFinanciamientoOperaciones.json',data).then(success, error);
		},
		rechazarFinanciamientoOperaciones : function(data,
				success, error) {$http.post(CONFIG.APIURL+ '/pppOperaciones/nominas/rechazarFinanciamientoOperaciones.json',data).then(success, error);
		},
		
	};

});
