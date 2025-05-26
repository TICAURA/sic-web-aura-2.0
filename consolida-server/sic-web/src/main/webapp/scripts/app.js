angular
  .module('themesApp', [
    'theme',
    'theme.demos',
    'constantes.app',
    'ngTable',
    'ngJsonExportExcel'
  ])
  .config(['$provide', '$routeProvider','$httpProvider' ,function($provide, $routeProvider,$httpProvider) {
    'use strict';
    
    $httpProvider.interceptors.push('httpInterceptor');
    
    $routeProvider
      .when('/', {
        templateUrl: 'views/index.html',
        resolve: {
          loadCalendar: ['$ocLazyLoad', function($ocLazyLoad) {
            return $ocLazyLoad.load([
              'bower_components/fullcalendar/fullcalendar.js',
            ]);
          }]
        }
      })
      .when('/:templateFile', {
        templateUrl: function(param) {
          return 'views/' + param.templateFile + '.html';
        }
      })
      .when('#', {
        templateUrl: 'views/index.html'
      })
      .when('/crm/:templateFile', {
        templateUrl: function(param) {
          return 'views/crm/' + param.templateFile + '.html';
        }
      })
      .when('/crm/tableroControl', {
        templateUrl: 'views/crm/tableroControl.html',
      })
      .when('/crm/reportes', {
        templateUrl: 'views/crm/reportes.html',
        controller: 'SvgChartsController'
      })
      .when('/admin/pantalla', {
        templateUrl: 'views/admin/pantalla.html',
      })
      .when('/crm/clientes', {
        templateUrl: 'views/crm/clientes.html',
      })
      .when('/crm/cliente', {
        templateUrl: 'views/crm/cliente.html',
      })
      .when('/crm/cliente-implementador', {
        templateUrl: 'views/crm/cliente-implementador.html',
      })
      .when('/crm/nominas', {
        templateUrl: 'views/crm/nominas.html',
      })
      .when('/crm/editarCliente', {
        templateUrl: 'views/crm/editarCliente.html',
      })
      .when('/crm/registroNominas', {
        templateUrl: 'views/crm/registroNominas.html',
      })
      .when('/crm/detalle-cliente/:idCliente', {
        templateUrl: 'views/crm/detalle-cliente.html',
      })
      .when('/crm/actualiza-cliente/:idCliente', {
        templateUrl: 'views/crm/actualiza-cliente.html',
      })
      
      // FONDO
      .when('/crm/fondo/consultaFondo', {
        templateUrl: 'views/crm/fondo/consultaFondo.html',
      })
      .when('/crm/fondo/registroFondo', {
        templateUrl: 'views/crm/fondo/registroFondo.html',
      })
      .when('/crm/fondo/actualizaDatosFondo', {
        templateUrl: 'views/crm/fondo/actualizaDatosFondo.html',
      })
      .when('/crm/fondo/consultaPersonalFondo', {
        templateUrl: 'views/crm/fondo/consultaPersonalFondo.html',
      })
      .when('/crm/fondo/consultaNominasAsignadas', {
        templateUrl: 'views/crm/fondo/consultaNominasAsignadas.html',
      })
      .when('/crm/fondo/asignaNoministaCliente', {
        templateUrl: 'views/crm/fondo/asignaNoministaCliente.html',
      })
      .when('/crm/fondo/registroNominista', {
        templateUrl: 'views/crm/fondo/registroNominista.html',
      })
      .when('/crm/fondo/consultaPatrona', {
        templateUrl: 'views/crm/fondo/consultaPatrona.html',
      })
      .when('/crm/fondo/registroPatrona', {
        templateUrl: 'views/crm/fondo/registroPatrona.html',
      })
      .when('/crm/fondo/actualizaPatrona', {
        templateUrl: 'views/crm/fondo/actualizaPatrona.html',
      })
      .when('/crm/fondo/registroPersonalFondo', {
        templateUrl: 'views/crm/fondo/registroPersonalFondo.html',
      })
            .when('/crm/fondo/asignaPatronaCliente', {
        templateUrl: 'views/crm/fondo/asignaPatronaCliente.html',
      })
      
      // PRESTADORA DE SERVICIO
      .when('/crm/prestadoraServicio/prestadoraServicio', {
        templateUrl: 'views/crm/prestadoraServicio/prestadoraServicio.html',
      })
      .when('/crm/prestadoraServicio/agregarPrestadoraServicio', {
        templateUrl: 'views/crm/prestadoraServicio/agregarPrestadoraServicio.html',
      })
      
      // PERSONAL
      .when('/crm/grupoTrabajo/consultaPersonal', {
        templateUrl: 'views/crm/grupoTrabajo/consultaPersonal.html',
      })
      .when('/crm/grupoTrabajo/registroPersonal', {
        templateUrl: 'views/crm/grupoTrabajo/registroPersonal.html',
      })
      .when('/crm/grupoTrabajo/actualizaPersonal', {
        templateUrl: 'views/crm/grupoTrabajo/actualizaPersonal.html',
      })
      .when('/crm/grupoTrabajo/asignaPersonalFondo', {
        templateUrl: 'views/crm/grupoTrabajo/asignaPersonalFondo.html',
      })

      
      // COTIZADOR
      .when('/cotizador/cotizador', {
        templateUrl: 'views/cotizador/buscarClienteCotizador.html',
      })
      .when('/cotizador/nominas', {
        templateUrl: 'views/cotizador/nominasCotizador.html',
      })
      .when('/cotizador/solicitudAutorizaciones', {
        templateUrl: 'views/cotizador/autorizador/solicitudAutorizaciones.html',
      })
      .when('/cotizador/autorizador/detalleCotizacionAutorizador', {
        templateUrl: 'views/cotizador/autorizador/detalleCotizacionAutorizador.html',
      })
      .when('/cotizador/autorizador/cotizacionAutorizador', {
        templateUrl: 'views/cotizador/autorizador/cotizacionAutorizador.html',
      })
      .when('/cotizador/seguimientoProspectos/', {
        templateUrl: 'views/cotizador/seguimientoProspectos/seguimientoProspectos.html',
      })
      .when('/cotizador/autorizador/:templateFile', {
    	  templateUrl: function(param) {
    		  return 'views/cotizador/autorizador/' + param.templateFile + '.html';
    	  }
      })
      .when('/cotizador/:templateFile', {
        templateUrl: function(param) {
          return 'views/cotizador/' + param.templateFile + '.html';
        }
      })
      .when('/cotizador/canalVenta/:templateFile', {
        templateUrl: function(param) {
          return 'views/cotizador/canalVenta/' + param.templateFile + '.html';
        }
      })
      .when('/ppp/:templateFile', {
        templateUrl: function(param) {
          return 'views/ppp/' + param.templateFile + '.html';
        }
      })
      .when('/admin/:templateFile', {
        templateUrl: function(param) {
          return 'views/admin/' + param.templateFile + '.html';
        }
      })
      .when('/ppp/buzonRol', {
        templateUrl: 'views/ppp/buzonRol.html',
      })
      .when('/ppp/consultaClientes', {
        templateUrl: 'views/ppp/consultaClientes.html',
      })
      .when('/reportes/:templateFile', {
        templateUrl: function(param) {
          return 'views/reportes/' + param.templateFile + '.html';
        }
      })
      .otherwise({
        redirectTo: '/'
      });
  }]).factory('httpInterceptor', function ($q, $rootScope, $log, $window) {
    
	var numLoadings = 0;
    return {
      request: function (config) {
        numLoadings++;
        $('#loaderDiv').show();
        return config || $q.when(config);
      },
      response: function (response) {
        if((--numLoadings)===0){
           angular.element(document.getElementById('userPanelApp')).scope().$broadcast('timer');
           $('#loaderDiv').hide();
          $.ajax({ beforeSend: function(){
      	       	  	$("input").each(function(i){//.inputMayuscula
      	   	        	   var type = $(this).attr("type");
      	   	     	      if(type === 'text'){
      	   	     	    $(this).attr('onkeyup','var start = this.selectionStart; var end = this.selectionEnd; this.value = this.value.toUpperCase(); this.setSelectionRange(start, end);');
      	   	     	    $(this).attr('onblur','var start = this.selectionStart; var end = this.selectionEnd; this.value = this.value.toUpperCase(); this.setSelectionRange(start, end);');
      	   	     	    	  
      	   	     	      }
      	   	     	});
      	       	$("input").keydown(function (e){
      	       	  // Capturamos qué telca ha sido
      	       	  var keyCode= e.which;
      	       	  // Si la tecla es el Intro/Enter
      	       	  if (keyCode == 13){
      	       	    // Evitamos que se ejecute eventos
      	       	    event.preventDefault();
      	       	    // Devolvemos falso
      	       	    return false;
      	       	  }
      	       	});
        	  } 
          }).done(function(){
        	  $("input").each(function(i){//.inputMayuscula
      	   	        	   var type = $(this).attr("type");
      	   	     	      if(type === 'text'){
      	   	     	    	  $(this).attr('onkeyup','var start = this.selectionStart; var end = this.selectionEnd; this.value = this.value.toUpperCase(); this.setSelectionRange(start, end);');
      	   	     	    	  $(this).attr('onblur','var start = this.selectionStart; var end = this.selectionEnd; this.value = this.value.toUpperCase(); this.setSelectionRange(start, end);');
      	   	     	      }
      	   	     	});
        	  $("input").keydown(function (e){
      	       	  // Capturamos qué telca ha sido
      	       	  var keyCode= e.which;
      	       	  // Si la tecla es el Intro/Enter
      	       	  if (keyCode == 13){
      	       	    // Evitamos que se ejecute eventos
      	       	    event.preventDefault();
      	       	    // Devolvemos falso
      	       	    return false;
      	       	  }
      	       	});
          })
        }
        return response || $q.when(response);
      },
      responseError: function(response) {
        if(!(--numLoadings)) {
           angular.element(document.getElementById('userPanelApp')).scope().$broadcast('timer');
           $('#loaderDiv').hide();
        }
        return $q.reject(response);
      }
    };
});
