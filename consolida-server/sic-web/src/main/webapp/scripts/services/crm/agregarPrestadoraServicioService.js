'use strict';
angular.module('theme.crm')
  .service('agregarPrestadoraServicioService',function($http, CONFIG) {
	 
    var APIURL  = '/sic-web'
    	
    	return {
    	guardarPrestadoraServicio : function(data, success, error){
        	  $http.post(APIURL+'/prestadoraServicio/guardarPrestadoraServicio.json', data).then(success,error);
          },
          cargaCatMunicipiosXEntidadFed : function(data, success, error){
        	  $http.post(APIURL+'/catalogoCotizador/cargaCatMunicipiosXEntidadFed.json', data).then(success,error);
          },
          obtenerEntidadFederativaXCP :function(data, success, error){
        	  $http.post(APIURL+'/prestadoraServicio/obtenerEntidadFederativaXCP.json', data).then(success,error);
          },
          guardarCuentaPrestadoraServicio : function(data, success, error){
        	  $http.post(APIURL+'/prestadoraServicio/guardarCuentaPrestadoraServicio.json', data).then(success,error);
          },
          eliminarCuentaPrestadoraServicio : function(data, success, error){
        	  $http.post(APIURL+'/prestadoraServicio/eliminarCuentaPrestadoraServicio.json', data).then(success,error);
          },
  		obtenerSubgiro : function(data,success, error) {
			$http.post(APIURL + '/prestadoraServicio/actividad/obtenerSubgiro.json', data).then(success, error);
		},
		guardarActividad : function(data,success, error) {
			$http.post(APIURL + '/prestadoraServicio/actividad/guardarActividad.json', data).then(success, error);
		},
		guardaRegistroPatronal : function(data, success, error){
        	  $http.post(CONFIG.APIURL + '/prestadoraServicio/registroPatronal/guardaRegistroPatronal.json', data).then(success,error);
        },
        cargaInicialRegistroPatronal : function(success, error) {
      		$http.get(CONFIG.APIURL + '/prestadoraServicio/registroPatronal/cargaInicialRegistroPatronal.json').then(success, error);
      	},
      	eliminarRegistroPatronal : function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/prestadoraServicio/registroPatronal/eliminarRegistroPatronal.json', data).then(success,error);
      	},
      	cargaInicialClasePrimaFraccion : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio/claseFraccionPrima/cargaInicialClasePrimaFraccion.json').then(success, error);
    	},
    	guardaClasePrimaFraccion : function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/prestadoraServicio/claseFraccionPrima/guardaClasePrimaFraccion.json', data).then(success,error);
    	},
    	cargaInicialPrestadoraImss : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio/imss/cargaInicialPrestadoraImss.json').then(success, error);
  		},
  		guardaPrestadoraImss : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/imss/guardaPrestadoraImss.json', data).then(success, error);
  		},
  		cargaInicialObjetoSocial : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio/objetoSocial/cargaInicialObjetoSocial.json').then(success, error);
  		},
  		guardaObjetoSocial : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/objetoSocial/guardaObjetoSocial.json', data).then(success, error);
  		},
  		eliminarObjetoSocial : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/objetoSocial/eliminarObjetoSocial.json', data).then(success, error);
  		},
  		guardarProductoSat : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/producto/guardarProductoSat.json', data).then(success, error);
  		},
  		guardarProducto : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/guardarProducto.json', data).then(success, error);
  		},
  		cargaInicialRepresentanteLegal : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio/representanteLegal/cargaInicialRepresentanteLegal.json').then(success, error);
  		},
  		guardarRepresentanteLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/representanteLegal/guardarRepresentanteLegal.json', data).then(success, error);
  		},
  		eliminarRepresentanteLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/representanteLegal/eliminarRepresentanteLegal.json', data).then(success, error);
  		},
  		cargaInicialApoderadoLegal : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio/apoderadoLegal/cargaInicialApoderadoLegal.json').then(success, error);
  		},
  		guardarApoderadoLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/apoderadoLegal/guardarApoderadoLegal.json', data).then(success, error);
  		},
  		eliminarApoderadoLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/apoderadoLegal/eliminarApoderadoLegal.json', data).then(success, error);
  		},
  		cargaInicialAccionista : function(success, error) {
    		$http.get(CONFIG.APIURL + '/prestadoraServicio//accionista/cargaInicialAccionistas.json').then(success, error);
  		},
  		guardarAccionista : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/accionista/guardarAccionista.json', data).then(success, error);
  		},
  		eliminarAccionista : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/accionista/eliminarAccionista.json', data).then(success, error);
  		},
  		eliminarDocumentoFielCsd : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/fielCsd/eliminarDocumentoFielCsd.json', data).then(success, error);
  		},
  		eliminarDocumentoRepresentanteCerKey : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/representanteLegal/eliminarDocumentosRepresentante.json', data).then(success, error);
  		},
  		eliminarDocumentoPrestadoraServicio : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/eliminarDocumentoPrestadoraServicio.json', data).then(success, error);
  		},
  		guardarDatosStp : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/prestadoraServicio/stp/guardarDatosStp.json', data).then(success, error);
  		}
    };
  });  
