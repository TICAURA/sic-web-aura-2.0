//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminDiscoCtrl
// * @description
// * # AdminDiscoCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidor')
//  .controller('AdminDiscoCtrl', function ($scope,$http,CONFIG,$location,pinesNotifications) {
//    $scope.servidorPrincipal = {};
//    $scope.servidorSecundario = {};
//    $scope.discoPrincipal = {};
//    $scope.discoSecundario ={}
//
//
//    $scope.initDisco = function(){
//      $http.get(CONFIG.APIURL + "/disco/consultaServidores.json").then(function(data){
//
//          $scope.servidorPrincipal = data.data.principal;
//          $scope.servidorSecundario = data.data.secundario;
//        },function(data){
//          console.log("error --> " + data);
//      });
//
//      $http.get(CONFIG.APIURL +  "/disco/consultarDiscos.json").then(function(data){
//
//          $scope.discoPrincipal = data.data.discoPrincipal;
//          $scope.discoSecundario = data.data.discoSecundario;
//
//        },function(data){
//          console.log("error --> sin discos --> " +  data);
//      });
//
//    }
//
//    $scope.initDisco();
//
//    $scope.cancelar = function(){
//       $location.path('/admin/discos');
//    }
//
//
//    $scope.stickySuccess = function() {
//      pinesNotifications.notify({
//        title: 'Operacion exitosa',
//        text: 'Se ha guardado correctamente ',
//        type: 'success'
//      });
//    };
//
//
//    $scope.guardar = function(){
//      
//      $scope.discosConjunto ={};
//      // completa discos
//      $scope.discoPrincipal.servidorDTO = $scope.servidorPrincipal;
//      $scope.discoSecundario.servidorDTO = $scope.servidorSecundario;
//      // completa discos conjunto
//      $scope.discosConjunto.discoPrincipal =$scope.discoPrincipal;
//      $scope.discosConjunto.discoSecundario = $scope.discoSecundario;
//
//      $http.post(CONFIG.APIURL + "/disco/guardarDisco.json" , $scope.discosConjunto).then(function(data){
//          $scope.stickySuccess();
//          $location.path('/admin/discos');
//        },function(data){
//          console.log("error --> " + data);
//      });
//    }
//
//    $scope.cambiarMontado = function(tipoDisco){
//      
//       if(tipoDisco === 'principal'){
//
//         if( $scope.discoPrincipal.estadoMontado === 'montado'){
//          $scope.discoSecundario.estadoMontado = 'nomontado';
//         }else if( $scope.discoPrincipal.estadoMontado === 'nomontado'){
//          $scope.discoSecundario.estadoMontado = 'montado';
//         }
//        
//       }else{
//
//         if($scope.discoSecundario.estadoMontado === 'montado'){
//          $scope.discoPrincipal.estadoMontado = 'nomontado';
//         }else if($scope.discoSecundario.estadoMontado === 'nomontado'){
//          $scope.discoPrincipal.estadoMontado = 'montado';
//         }
//
//       }
//    }
//
//  });