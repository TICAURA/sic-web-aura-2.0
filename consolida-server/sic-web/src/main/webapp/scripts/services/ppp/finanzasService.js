'use strict';
angular.module('theme.crm').service('finanzasService',function($http,CONFIG) {
	
	return {

		cargaInicial : function(success, error) {
			$http.get(CONFIG.APIURL+ '/ppp/finanzas/cargaInicialFinanzas.json').then(success, error);
		},
		guardarColaboradores : function(data, success, error) {
			$http.post(CONFIG.APIURL+ '/ppp/finanzas/guardarCveOrdenPagoColaborador.json',data).then(success, error);
		},
		dispersionStpColaborador : function(data, success, error) {
			$http.post(CONFIG.APIURL+ '/ppp/finanzas/dispersionStpColaborador.json',data).then(success, error);
		},
		cambiaEstatusNominaRechazarDispersion : function(data, success, error) {
			$http.post(CONFIG.APIURL+ '/ppp/finanzas/cambiaEstatusNominaRechazarDispersion.json',data).then(success, error);
		}
	};
	
}); 