angular
  .module('themesApp', [
    'theme',
    'theme.demos',
    'constantes.app'
  ])
  .config(['$provide', '$routeProvider', function($provide, $routeProvider) {
    'use strict';
    $routeProvider
      .when('/', {
        templateUrl: 'views/login.html',
      })
      .when('/:templateFile', {
        templateUrl: function(param) {
          return 'views/login.html';
        }
      })
      .when('#', {
        templateUrl: 'views/login.html',
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
  })
	  ;
