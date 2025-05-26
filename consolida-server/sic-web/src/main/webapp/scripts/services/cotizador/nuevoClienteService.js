'use strict';
angular.module('theme.crm')
  .service('nuevoClienteService',function($http) {
	 
    var APIURL  = '/sic-web'
    	
    	return {
    	guardar : function(data, success, error){
        	  $http.post(APIURL+'/cliente/guardar.json', data).then(success,error);
          },
          tipoPersona : function(success, error){
          	  $http.post(APIURL+'/catalogoCotizador/tipoPersona.json').then(success,error);
            },
            cargaCatalogosNuevoCliente : function(success, error){
            	  $http.post(APIURL+'/catalogoCotizador/cargaCatalogosNuevoCliente.json').then(success,error);
              },
              guardarGrupo :function(data, success, error){
            	  $http.post(APIURL+'/cliente/guardarGrupo.json', data).then(success,error);
              },
              cargaCatMunicipiosXEntidadFed : function(data, success, error){
            	  $http.post(APIURL+'/catalogoCotizador/cargaCatMunicipiosXEntidadFed.json', data).then(success,error);
              },
              cargaCatalogoGrupo : function(success, error){
            	  $http.post(APIURL+'/catalogoCotizador/cargaCatalogoGrupo.json').then(success,error);
              },
              verificarEditar : function(success, error){
            	  $http.post(APIURL+'/cliente/verificarEditar.json').then(success,error);
              },
              eliminarProspecto :function(data, success, error){
            	  $http.post(APIURL+'/cliente/eliminarProspecto.json', data).then(success,error);
              },
              obtenerEntidadFederativaXCP :function(data, success, error){
            	  $http.post(APIURL+'/cliente/obtenerEntidadFederativaXCP.json', data).then(success,error);
              },
              autorizarProspecto :function(data, success, error){
            	  $http.post(APIURL+'/cliente/autorizarProspecto.json', data).then(success,error);
              },
              rechazarProspecto :function(data, success, error){
            	  $http.post(APIURL+'/cliente/rechazarProspecto.json', data).then(success,error);
              },
              obtenerSubgiro :function(data, success, error){
            	  $http.post(APIURL+'/cliente/obtenerSubgiro.json', data).then(success,error);
              },
              declinarProspecto:function(data, success, error){
            	  $http.post(APIURL+'/cliente/declinarProspecto.json', data).then(success,error);
              }
    };
  });  
