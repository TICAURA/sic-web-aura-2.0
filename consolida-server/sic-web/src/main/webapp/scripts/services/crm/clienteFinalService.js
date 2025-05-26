/**
 * 
 */

'use strict';
angular.module('theme.crm')
  .service('clienteFinalService',function($http, CONFIG) {
    	
    	return {
    	
   		detalles : function(data, success, error){
//      	  $http.get(APIURL+'/clientes/consulta-detalle.json?idCliente='+data).then(success,error);
      	},
      	cargaInicialDatosGenCliente : function(success, error) {
      		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/datosGenerales/cargaInicialDatosGenCliente.json').then(success, error);
      	},
      	obtenerEntidadFederativaXCP :function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/domicilio/obtenerEntidadFederativaXCP.json', data).then(success,error);
        },
        guardarDomicilioCliente : function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/domicilio/guardarDomicilioCliente.json', data).then(success,error);
        },
        guardarDomicilioComercial : function(data, success, error){
        	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/domicilio/guardarDomicilioComercial.json', data).then(success,error);
          },
        guardarClienteMedioContacto : function(data, success, error){
        	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/domicilio/guardarClienteMedioContacto.json', data).then(success,error);
        },
        guardarClienteMedioContactoCEO : function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/domicilio/guardarClienteMedioContactoCEO.json', data).then(success,error);
        },
      	cargaInicialConceptoFacturacion : function(success, error) {
      		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/conceptoFacturacionCliente/cargaInicialFacturacion.json').then(success, error);
      	},
        guardaConceptoFacturacion : function(data, success, error){
      	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/conceptoFacturacionCliente/guardaConceptoFacturacion.json', data).then(success,error);
        },
        eliminarConceptoFacturacion : function(data, success, error){
        	  $http.post(CONFIG.APIURL + '/clienteSeccionesCrm/conceptoFacturacionCliente/eliminarConceptoFacturacion.json', data).then(success,error);
        },
		guardarGeneralesCliente: function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/datosGenerales/guardarGeneralesCliente.json', data).then(success, error);
		},
		cargaInicialCelula : function(success, error) {
      		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/celula/cargaInicialCelula.json').then(success, error);
      	},
      	guardaCelula: function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/celula/guardaCelula.json', data).then(success, error);
		},
		eliminarClientePrestadora: function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/celula/eliminarClientePrestadora.json', data).then(success, error);
		},
		cargaInicialNominaCliente : function(success, error) {
      		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/cargaInicialNominaCliente.json').then(success, error);
      	},
      	guardaNominaCliente: function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/guardaNominaCliente.json', data).then(success, error);
		},
		getNominaClienteById : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/getNominaClienteById.json', data).then(success, error);
		},
		eliminarNominaCliente : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/eliminarNominaClienteById.json', data).then(success, error);
		},
		guardarCuentaBancaria : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/cuentaBancaria/guardarCuentaBancaria.json', data).then(success, error);
		},
		eliminarCuentaBancaria : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/cuentaBancaria/eliminarCuentaBancaria.json', data).then(success, error);
		},
		obtenerSubgiro : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/actividad/obtenerSubgiro.json', data).then(success, error);
		},
		guardarActividad : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/actividad/guardarActividad.json', data).then(success, error);
		},
		guardarComision : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/guardarComision.json', data).then(success, error);
		},
		eliminarComision : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/eliminarComision.json', data).then(success, error);
		},
		guardarHonorario : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/guardarHonorario.json', data).then(success, error);
		},
		eliminarHonorario : function(data,success, error) {
			$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/nominaCliente/eliminarHonorario.json', data).then(success, error);
		},
  		cargaInicialRepresentanteLegal : function(success, error) {
    		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/representanteLegal/cargaInicialRepresentanteLegal.json').then(success, error);
  		},
  		guardarRepresentanteLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/representanteLegal/guardarRepresentanteLegal.json', data).then(success, error);
  		},
  		eliminarRepresentanteLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/representanteLegal/eliminarRepresentanteLegal.json', data).then(success, error);
  		},
  		cargaInicialApoderadoLegal : function(success, error) {
    		$http.get(CONFIG.APIURL + '/clienteSeccionesCrm/apoderadoLegal/cargaInicialApoderadoLegal.json').then(success, error);
  		},
  		guardarApoderadoLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/apoderadoLegal/guardarApoderadoLegal.json', data).then(success, error);
  		},
  		eliminarApoderadoLegal : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/apoderadoLegal/eliminarApoderadoLegal.json', data).then(success, error);
  		},
  		eliminarDocumentoRepresentanteCerKey : function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/representanteLegal/eliminarDocumentosRepresentante.json', data).then(success, error);
  		},
  		guardarContraseniaCerKeyRepresentanteLegal: function(data, success, error) {
    		$http.post(CONFIG.APIURL + '/clienteSeccionesCrm/representanteLegal/guardarContraseniaCerKeyRepresentanteLegal.json', data).then(success, error);
  		},
		
//  	  		guardar : function(data, success, error){
//      	  $http.post(APIURL+'/clientes/guardar.json', data).then(success,error);
//        },   	

//    	update : function(data, success, error){
//        	  $http.post(APIURL+'/clientes/actualiza.json', data).then(success,error);
//          },   	
    };
  });