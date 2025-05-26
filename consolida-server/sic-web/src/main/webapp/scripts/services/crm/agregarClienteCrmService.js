'use strict';
angular.module('theme.crm').service('agregarClienteCrmService', function($http, CONFIG ) {

			var APIURL = '/sic-web'

			return {
				cargaCatGrupo : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/cargaCatGrupo.json').then(success, error);
				},
				cargaCatCategoria : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/cargaCatCategoria.json').then(success, error);
				},
				listaCatTipoPago : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/listaCatTipoPago.json').then(success, error);
				},
				listaCelulas : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/listaCelulas.json').then(success, error);
				},
				listaNoministas : function(data,success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/listaNoministas.json', data).then(success, error);
				},
				listaCatGiroComercial : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/cargaCatGiroComercial.json').then(success, error);
				},
				listaCatRegimenFiscal : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/cargaCatRegimenFiscal.json').then(success, error);
				},
				cargaInicialPospectosAutorizar : function(success, error) {
					$http.get(CONFIG.APIURL + '/clienteCrm/cargaInicialPospectosAutorizar.json').then(success, error);
				},
				autorizarProspecto : function(data,success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/autorizarProspecto.json', data).then(success, error);
				},
				declinarProspecto : function(data,success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/declinarProspecto.json', data).then(success, error);
				},
				guardarGeneralesCliente : function(data, success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/guardarGeneralesCliente.json', data).then(success, error);
				},
				editarProspectoAutorizado : function(success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/editarProspectoAutorizado.json').then(success, error);
				},
				obtenerValorAgregarCliente: function(success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/obtenerValorAgregarCliente.json').then(success, error);
				},
				esAgregarCliente: function(data,success, error) {
					$http.post(CONFIG.APIURL + '/clienteCrm/esAgregarCliente.json', data).then(success, error);
				}
			};
		});