//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminCorreosnotificacionCtrl
// * @description
// * # AdminCorreosnotificacionCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidor')
//  .controller('AdminCorreosnotificacionCtrl', function($scope, pinesNotifications, $window, $http,CONFIG,$location){
//    $scope.correo ={};
//
//    $scope.stickySuccess = function() {
//      pinesNotifications.notify({
//        title: 'Operacion exitosa',
//        text: 'Se ha guardado correctamente los correos',
//        type: 'success'
//      });
//    };
//
//    $scope.stickyError = function() {
//      pinesNotifications.notify({
//        title: 'Error !',
//        text: 'Ocurrio un error al guardar los contactos de notificacion',
//        type: 'error'
//      });
//    };
//
//    $scope.initCorreosNotificacion = function(){
//        
//      $http.get(CONFIG.APIURL +  "/correo/correosNotificacion.json").then(function(data){
//        if(data.data !== undefined && data.data !== ''){
//          $scope.correo = data.data;
//        }
//          
//        },function(data){
//          console.log("error --> sin discos --> " +  data);
//      });
//    }
//
//    $scope.initCorreosNotificacion();
//
//    $scope.guardarCorreo = function(){
//      $http.post(CONFIG.APIURL + "/correo/guardarCorreos.json",$scope.correo).then(function(data){
//        $scope.stickySuccess();
//        $location.path('/');
//      },function(data){
//        
//        console.log("error --> sin discos --> " +  data);
//        $scope.stickyError();
//    });
//    }
//
//    $scope.cancelar = function(){
//      $location.path('/');
//    }
//
//  });
