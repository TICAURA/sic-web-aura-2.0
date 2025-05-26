//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminConfignotificacionCtrl
// * @description
// * # AdminConfignotificacionCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidor')
//  .controller('AdminConfignotificacionCtrl', function ($scope,$http,CONFIG,$location,$bootbox) {
//    
//    $scope.initConfNotificaciones = function(){
//
//    $http.get(CONFIG.APIURL + "/confNotificacion/configuraciones.json").then(function(data){
//        $scope.confNotificaciones = data.data;
//    },function(data){
//      console.log("error --> " + data);
//    });
//    
//  }
//
//  $scope.initConfNotificaciones();
//
//    $scope.data = {
//      headings: ['#', 'Tipo Notificacion','Total notificaciones' ,'Intervalo de tiempo','Acciones']
//    };
//
//    $scope.editConfig = function(confNotificacion){
//      $scope.confNotificacion = angular.copy(confNotificacion);
//      $('#myModal').modal('show');
//    }
//
//    $scope.guardarConfNotificacion = function(){
//      $http.post(CONFIG.APIURL + "/confNotificacion/guardarConfig.json" , $scope.confNotificacion).then(function(data){
//        $scope.initConfNotificaciones();
//        $('#myModal').modal('hide');
//      },function(data){
//        console.log("error --> " + data);
//      });
//    }
//
//  });
