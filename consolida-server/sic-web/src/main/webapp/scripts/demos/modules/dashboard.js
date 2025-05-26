angular.module('theme.demos.dashboard', [
    'angular-skycons',
    'theme.demos.forms',
    'theme.demos.tasks'
  ])
  .controller('DashboardController', ['$scope', '$timeout', '$window','$http','CONFIG', function($scope, $timeout, $window,$http,CONFIG) {
    'use strict';
   
    
    
    $scope.tileDef = { title: 'Page Views', href: '#/ui-tiles', titleBarInfo: '-7.6%', text: '1.6K', color: 'info', classes: 'fa fa-eye' };

    $scope.comentarios = {
        title: 'Notificaciones',
        titleBarInfo: '+Pendientes',
        text: '32',
        color: 'green',
        classes: 'fa fa-comments'
      }; 

    $scope.descargas = { 
        title: 'Actualizaciones',
        titleBarInfo: '+Pendientes',
        text: '6',
        color: 'danger',
        classes: 'fa fa-download'
      };
    
    $scope.tareas = {
        title: 'Perfil',
        titleBarInfo: '+Usuario',
        text: '1',
        color: 'magenta',
        classes: 'fa fa-tasks'
      };


  }]);
