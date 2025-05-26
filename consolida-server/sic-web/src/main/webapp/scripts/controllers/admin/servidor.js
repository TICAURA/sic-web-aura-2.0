//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminServidorCtrl
// * @description
// * # AdminServidorCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidor',[])
//  .controller('AdminServidorCtrl', function ($scope, $location , $http, CONFIG,pinesNotifications) {
//      $scope.principal = {};
//      $scope.secundario = {};
//      $scope.principal.catTipoServidor ={};
//      $scope.secundario.catTipoServidor = {};
//
//    $scope.initServidor = function(){ 
//      $http.get(CONFIG.APIURL + "/disco/consultaServidores.json").then(function(data){
//
//          $scope.principal = data.data.principal;
//          $scope.secundario = data.data.secundario;
//        },function(data){
//          console.log("error --> " + data);
//      });      
//    }
//
//    $scope.initServidor();
//
//    $scope.cancelar = function(){
//       $location.path('/admin/servidores');
//     }
//
//     $scope.stickySuccess = function() {
//      pinesNotifications.notify({
//        title: 'Operacion exitosa',
//        text: 'Se ha guardado correctamente ',
//        type: 'success'
//      });
//    };
//
//    $scope.guardar = function(){
//      
//      $scope.servidoresConjuntoDTO = {};
//      $scope.servidoresConjuntoDTO.principal= $scope.principal;
//      $scope.servidoresConjuntoDTO.secundario= $scope.secundario;
//      
//      $http.post(CONFIG.APIURL +"/servidor/guardarServidor.json",$scope.servidoresConjuntoDTO)
//      .then(function(data){
//        $scope.stickySuccess();
//        $location.path('/admin/servidores');
//      },function(data){
//        console.log("error --> " + data);
//      });
//
//    }
//
//
//    $scope.cambioTipoServidor = function(tipo){
//
//
//      if(tipo === 'servidor1'){
//        if($scope.principal.catTipoServidor.id === '1'){
//          $scope.secundario.catTipoServidor.id = 2;
//        }else if($scope.principal.catTipoServidor.id === '2'){
//          $scope.secundario.catTipoServidor.id = 1;
//        }
//      }else{
//        if($scope.secundario.catTipoServidor.id === '1'){
//          $scope.principal.catTipoServidor.id = 2;
//        }else if($scope.secundario.catTipoServidor.id === '2'){
//          $scope.principal.catTipoServidor.id = 1;
//        }
//        
//      }
//      
//    }
//
//  });