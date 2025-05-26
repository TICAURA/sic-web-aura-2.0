'use strict';
angular.module('theme.crm').service('seguimientoNominasService',function($http,CONFIG) {
	
	return {

		cargaInicial : function(success, error) {
			$http.get(CONFIG.APIURL+ '/ppp/seguimientoNomina/cargaInicial.json').then(success, error);
		}
	};
	
}); 