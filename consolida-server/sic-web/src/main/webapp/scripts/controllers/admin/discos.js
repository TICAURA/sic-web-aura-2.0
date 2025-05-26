//Monterito

//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminDiscosCtrl
// * @description
// * # AdminDiscosCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.discos',[])
//  .controller('AdminDiscosCtrl', function ($scope,$http,CONFIG,$location,$bootbox) {
//    $scope.principales = [];
//    $scope.secundarios = [];
//    $scope.servidorPrincipal = {};
//    $scope.servidorSecundario = {};
//
//    $scope.initDiscos = function(){
//        $http.get(CONFIG.APIURL + "/disco/consultaByServidor.json").then(function(data){
//        $scope.principales = data.data.principales;
//        $scope.secundarios = data.data.secundarios;
//
//        $scope.servidorPrincipal = data.data.servidorPrincipal;
//        $scope.servidorSecundario = data.data.servidorSecundario;
//      },function(data){
//        console.log("error --> " + data);
//      });
//    }
//
//    $scope.initDiscos();
//
//    $scope.data = {
//      headings: ['#', 'File System','disco drbd','Capacidad','disco replica','disco metadata', 'Estatus' ,'Acciones']
//    };
//
//
//    $scope.nuevoDisco = function () {
//      $http.get(CONFIG.APIURL + "/disco/nuevoDisco.json").then(function(data){
//        console.log("Selecciono nuevo disco");
//        $location.path('/admin/disco');
//      },function(data){
//        console.log("error al seleccionar nuevo disco");
//      });
//    };
//
//    $scope.editarDisco = function(disco){
//
//      var numeroDisco = disco.numeroDisco;
//      var discosConjuntoDTO ={};
//
//      if(disco.tipoDisco.clave === 'PRI'){
//        discosConjuntoDTO.discoPrincipal = disco;
//
//        angular.forEach($scope.secundarios, function(value, key){
//          if(value.numeroDisco == numeroDisco){
//            discosConjuntoDTO.discoSecundario = value;
//          }
//          
//        });
//      }else if(disco.tipoDisco.clave === 'SEC'){
//        discosConjuntoDTO.discoSecundario = disco;
//
//        angular.forEach($scope.principales, function(value, key){
//          if(value.numeroDisco == numeroDisco){
//            discosConjuntoDTO.discoPrincipal = value;
//          }
//            
//        });
//      }
//      
//
//      $http.post(CONFIG.APIURL + "/disco/editarDiscos.json", discosConjuntoDTO).then(function(data){
//        console.log("Selecciono nuevo disco");
//        $location.path('/admin/disco');
//      },function(data){
//        console.log("error al seleccionar nuevo disco");
//      });
//      
//    };
//
//    $scope.eliminarDiscos = function(disco){
//      
//      $bootbox.confirm('¿Queres borrar el disco '+ disco.fileSystem +'?', function(result) {
//          if(result === true){
//              var numeroDisco = disco.numeroDisco;
//              var discosConjuntoDTO ={};
//        
//              if(disco.tipoDisco.clave === 'PRI'){
//                discosConjuntoDTO.discoPrincipal = disco;
//        
//                angular.forEach($scope.secundarios, function(value, key){
//                  if(value.numeroDisco == numeroDisco){
//                    discosConjuntoDTO.discoSecundario = value;
//                  }
//                  
//                });
//              }else if(disco.tipoDisco.clave === 'SEC'){
//                discosConjuntoDTO.discoSecundario = disco;
//        
//                angular.forEach($scope.principales, function(value, key){
//                  if(value.numeroDisco == numeroDisco){
//                    discosConjuntoDTO.discoPrincipal = value;
//                  }
//                    
//                });
//              }
//              
//              $http.post(CONFIG.APIURL + "/disco/borrarDiscos.json", discosConjuntoDTO).then(function(data){
//                console.log("Selecciono nuevo disco");
//                $scope.initDiscos();
//              },function(data){
//                console.log("error al seleccionar nuevo disco");
//              });
//
//            }
//          })     
//        };
//
//
//  });
