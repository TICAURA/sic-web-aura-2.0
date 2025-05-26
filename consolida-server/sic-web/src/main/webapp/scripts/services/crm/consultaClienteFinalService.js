'use strict';
angular.module('theme.crm').service('consultaClienteFinalService', function($http) {
	var APIURL = '/sic-web'
		
	return {
		initProspectosAutMesaControl : function(success, error) {
		$http.get(APIURL + '/clienteCrm/cargaInicialProspectosAutorizadosByMesaControl.json').then(success, error);
	},
	obtieneClienteDtoParaEditar : function(data,success, error) {
		$http.post(APIURL + '/clienteCrm/obtieneClienteDtoParaEditar.json',data).then(success, error);
	},
	esAgregarCliente: function(data,success, error) {
		$http.post(APIURL + '/clienteCrm/esAgregarCliente.json',data).then(success, error);
	},
	enviarClienteSeccciones: function(data,success, error) {
		$http.post(APIURL + '/clienteSeccionesCrm/datosGenerales/datosGeneralesCliente.json',data).then(success, error);
		
	},
	eliminarCliente: function(data,success, error) {
		$http.post(APIURL + '/clienteCrm/eliminarCliente.json',data).then(success, error);
		
	}
};
	
});