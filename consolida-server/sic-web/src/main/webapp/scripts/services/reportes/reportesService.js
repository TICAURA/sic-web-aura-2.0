'use strict';
angular.module('theme.crm').service('reportesService',function($http,CONFIG) {
	
	return {

		cargaInicial : function(success, error) {
			$http.get(CONFIG.APIURL+ '/reportes/cargaInicial.json').then(success, error);
		}
	};
	
}); 