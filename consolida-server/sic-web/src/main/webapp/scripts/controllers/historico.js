//angular
//  .module('theme.historico', [
//    'theme.core.services',
//    'theme.chart.morris',
//    'theme.chart.flot'
//  ]
//  )
//  .controller('HistoricoCtrl', ['$scope', '$theme', '$timeout','$http','CONFIG', function($scope, $theme, $timeout,$http,CONFIG) {
//    'use strict';
//    $scope.servidores =[];
//    $scope.busqueda = {};
//    $scope.historico  = [];
//   /* $scope.otro = [[[0,1],[1,2]]];
//    
//    var dxta = [],
//    totalPoints = 12;
//    var updateInterval = 100;
//
//   
//    function getRandomData() {
//      if (dxta.length > 0) {
//        dxta = dxta.slice(1);
//      }
//
//      while (dxta.length < totalPoints) {
//        var prev = dxta.length > 0 ? dxta[dxta.length - 1] : 50,
//          y = prev + Math.random() * 10 - 5;
//
//        if (y < 0) {
//          y = 0;
//        } else if (y > 100) {
//          y = 100;
//        }
//
//        dxta.push(y);
//      }
//      var res = [];
//      for (i = 0; i < dxta.length; ++i) {
//        res.push([i, dxta[i]]);
//      }
//      return res;
//    }
//    var promise;
//    var updateRealtimeData = function() {
//      $scope.realtimeData = [getRandomData()];
//     // $timeout.cancel(promise);
//      //promise = $timeout(updateRealtimeData, updateInterval);
//    };
//
//    updateRealtimeData(); */
//
//    $scope.realtimeOptions = {
//      series: {
//        lines: {
//          show: true,
//          lineWidth: 0.75,
//          fill: 0.15,
//          fillColor: {
//            colors: [{
//              opacity: 0.01
//            }, {
//              opacity: 0.3
//            }]
//          }
//        },
//        shadowSize: 0 // Drawing is faster without shadows
//      },
//      grid: {
//        labelMargin: 10,
//        hoverable: true,
//        clickable: true,
//        borderWidth: 1,
//        borderColor: 'rgba(0, 0, 0, 0.06)'
//      },
//      yaxis: {
//        min: 0,
//        max: 110,
//        tickColor: 'rgba(0, 0, 0, 0.06)',
//        font: {
//          color: 'rgba(0, 0, 0, 0.4)'
//        }
//      },
//      xaxis: {
//        show: true
//      },
//      colors: [$theme.getBrandColor('indigo')],
//      tooltip: true,
//      tooltipOpts: {
//        content: 'Migracion: %y% - %x '
//      }
//
//    };
//
//    
///*
//    $scope.$on('$destroy', function() {
//      $timeout.cancel(promise);
//    });
//  */  
//
//    $scope.initHistorico = function(){
//      $http.get(CONFIG.APIURL+"/historico/servidores.json").then(function(data){
//        
//        $scope.servidores = data.data;
//      },function(data){
//        console.log("error --> " + data);        
//      });
//
//    }
//
//    $scope.initHistorico();
//
//    $scope.busquedaHistorico = function(){
//      $http.post(CONFIG.APIURL+"/historico/historicoServidor.json",$scope.busqueda).then(function(data){
//        
//        $scope.historico = data.data;
//      },function(data){
//        console.log("error --> " + data);        
//      });
//    }
//
//  }]);
