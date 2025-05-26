//'use strict';
//
///**
// * @ngdoc function
// * @name maverickApp.controller:AdminServidoresCtrl
// * @description
// * # AdminServidoresCtrl
// * Controller of the maverickApp
// */
//angular.module('theme.admin.servidores',['ngGrid'])
//  .controller('AdminServidoresCtrl', function ($scope,$http,$location,CONFIG,$bootbox) {
//    $scope.principales = [];
//    $scope.secundarios = [];
//    $scope.initServidores = function(){
//      
//      $http.get(CONFIG.APIURL +"/servidor/servidores.json").then(function(data){
//        $scope.principales = data.data.principales;
//        $scope.secundarios = data.data.secundarios;
//      },function(data){
//        console.log("error --> " + data);
//      });
//    }
//
//    $scope.initServidores();
//     
//
//    $scope.data = {
//      headings: ['#', 'Ip Replica', 'Host Name', 'Puerto agente' ,'Discos','Acciones']
//    };
//
//    $scope.nuevoServidor = function () {
//     
//
//      $http.post(CONFIG.APIURL +"/servidor/nuevosServidores.json").then(function(data){
//        $location.path('/admin/servidor');
//     },function(data){
//       console.log("error --> " + data);
//    
//     });
//    };
//
//    $scope.editarServidor = function(servidor){
//      
//      $http.post(CONFIG.APIURL +"/servidor/seleccionaServer.json" ,servidor).then(function(data){
//        $location.path('/admin/servidor');
//     },function(data){
//       console.log("error --> " + data);
//    
//     });
//     
//    };
//
//    $scope.editarDiscos = function(servidor){
//       
//       $http.post(CONFIG.APIURL +"/servidor/seleccionaServer.json" ,servidor).then(function(data){
//         $location.path('/admin/discos');
//      },function(data){
//        console.log("error --> " + data);
//      });
//      
//    }
//
//
//    $scope.eliminarServidor = function(servidor){
//
//        $bootbox.confirm('¿Queres borrar el servidor '+ servidor.hostname +'?', function(result) {
//          if(result === true){
//
//            var numeroServidor = servidor.numeroServidor;
//            var servidoresConjunto ={};
//
//            if(servidor.catTipoServidor.clave === 'PRI'){
//              servidoresConjunto.principal = servidor;
//
//              angular.forEach($scope.secundarios, function(value, key){
//                if(value.numeroServidor == numeroServidor){
//                  servidoresConjunto.secundario = value;
//
//                }
//                
//              });
//            }else if(servidor.catTipoServidor.clave === 'SEC'){
//              servidoresConjunto.secundario = servidor;
//
//              angular.forEach($scope.principales, function(value, key){
//                if(value.numeroServidor == numeroServidor){
//                  servidoresConjunto.principal = value;
//                
//                }
//                  
//              });
//            }
//
//          $http.post(CONFIG.APIURL +"/servidor/borrarServidores.json" ,servidoresConjunto).then(function(data){
//              $scope.initServidores();
//          },function(data){
//            console.log("error --> " + data);
//          });
//    
//        }
//      })
//
//    }
//
//
//  });
