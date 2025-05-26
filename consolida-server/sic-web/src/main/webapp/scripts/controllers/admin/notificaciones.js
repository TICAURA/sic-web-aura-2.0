//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminNotificacionesCtrl
// * @description
// * # AdminNotificacionesCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidor')
//  .controller('AdminNotificacionesCtrl', function ($scope,$http,CONFIG,$location,$bootbox) {
//
//    $scope.busqueda = 'SIN_ATENDER';
//    $scope.notificacion = {};
//    $scope.cambiarEstatus = function(estado){
//      $scope.estatusNotificion = estado;
//    }
//
//    $scope.notificaciones = [];
// 
//
//    $scope.initNotificaciones = function(){
//      $http.get(CONFIG.APIURL + "/notificacion/obtenernotificaciones.json").then(function(data){
//        $scope.notificaciones = data.data;
//
//    },function(data){
//      console.log("error --> " + data);
//    });
//    
//  }
//
//  $scope.initNotificaciones();
//
//    $scope.data = {
//      headings: ['#', 'Servidor','Notificacion' ,'Fecha notificación','estatus','Acciones']
//    };
//
//    $scope.marcarNotificacion = function(noti){
//      $scope.notificacion = noti;
//
//      if($scope.notificacion.estatusNotificacion.clave === 'INIT'){
//        
//        $bootbox.confirm('¿Quieres marcar como leida?', function(result) {
//          if(result === true){
//              $http.post(CONFIG.APIURL + "/notificacion/marcarLeida.json",$scope.notificacion)
//              .then(function(data){
//                $scope.notificaciones = data.data;
//        
//            },function(data){
//              console.log("error --> " + data);
//            });
//          }
//        })
//
//      }else if($scope.notificacion.estatusNotificacion.clave === 'LEIDO'){
//        $('#myModal').modal('show');
//      }else if($scope.notificacion.estatusNotificacion.clave === 'ATENDIDO'){
//        $('#myModal').modal('show');
//      }
//
//    }
//
//    $scope.notificaciones = function(){
//        alert("Notificaciones");
//    }
//
//    $scope.actializarBusqueda = function(status){
//      $scope.busqueda = status;
//
//      if(status === 'SIN_ATENDER'){
//        $http.get(CONFIG.APIURL + "/notificacion/obtenernotificaciones.json").then(function(data){
//          $scope.notificaciones = data.data;
//    
//        },function(data){
//          console.log("error --> " + data);
//        });
//
//      }else if(status === 'ATENDIDO'){
//        $http.get(CONFIG.APIURL + "/notificacion/obtenernotificacionesAtendidas.json").then(function(data){
//          $scope.notificaciones = data.data;
//  
//        },function(data){
//          console.log("error --> " + data);
//        });
//
//      }
//    }
//
//    $scope.guardarNotificacion = function(){
//      $http.post(CONFIG.APIURL + "/notificacion/marcarAtendida.json",$scope.notificacion)
//        .then(function(data){
//          $scope.initNotificaciones();
//          $('#myModal').modal('hide');
//      },function(data){
//        console.log("error --> " + data);
//      });
//    }
//
//  });
