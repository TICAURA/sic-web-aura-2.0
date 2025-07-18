angular
  .module('theme.demos.ng_grid', [
    'ngGrid'
  ])
  .controller('TablesAdvancedController', ['$scope', '$filter', '$http', function($scope, $filter, $http) {
    'use strict';
    $scope.dataGrid = [
    	  {
    		    "Engine": "Trident",
    		    "Browser": "Internet Explorer 4.0",
    		    "Platform": "Win 95+",
    		    "Version": "4",
    		    "Grade": "X"
    		  },
    		  {
    		    "Engine": "Trident",
    		    "Browser": "Internet Explorer 5.0",
    		    "Platform": "Win 95+",
    		    "Version": "5",
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Trident",
    		    "Browser": "Internet Explorer 5.5",
    		    "Platform": "Win 95+",
    		    "Version": "5.5",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Trident",
    		    "Browser": "Internet Explorer 6",
    		    "Platform": "Win 98+",
    		    "Version": "6",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Trident",
    		    "Browser": "Internet Explorer 7",
    		    "Platform": "Win XP SP2+",
    		    "Version": "7",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Trident",
    		    "Browser": "AOL browser (AOL desktop)",
    		    "Platform": "Win XP",
    		    "Version": "6",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Firefox 1.0",
    		    "Platform": "Win 98+ / OSX.2+",
    		    "Version": "1.7",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Firefox 1.5",
    		    "Platform": "Win 98+ / OSX.2+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Firefox 2.0",
    		    "Platform": "Win 98+ / OSX.2+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Firefox 3.0",
    		    "Platform": "Win 2k+ / OSX.3+",
    		    "Version": "1.9",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Camino 1.0",
    		    "Platform": "OSX.2+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Camino 1.5",
    		    "Platform": "OSX.3+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Netscape 7.2",
    		    "Platform": "Win 95+ / Mac OS 8.6-9.2",
    		    "Version": "1.7",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Netscape Browser 8",
    		    "Platform": "Win 98SE+",
    		    "Version": "1.7",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Netscape Navigator 9",
    		    "Platform": "Win 98+ / OSX.2+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.0",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.1",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.1",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.2",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.2",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.3",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.3",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.4",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.4",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.5",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.5",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.6",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": "1.6",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.7",
    		    "Platform": "Win 98+ / OSX.1+",
    		    "Version": "1.7",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Mozilla 1.8",
    		    "Platform": "Win 98+ / OSX.1+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Seamonkey 1.1",
    		    "Platform": "Win 98+ / OSX.2+",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Gecko",
    		    "Browser": "Epiphany 2.20",
    		    "Platform": "Gnome",
    		    "Version": "1.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "Safari 1.2",
    		    "Platform": "OSX.3",
    		    "Version": "125.5",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "Safari 1.3",
    		    "Platform": "OSX.3",
    		    "Version": "312.8",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "Safari 2.0",
    		    "Platform": "OSX.4+",
    		    "Version": "419.3",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "Safari 3.0",
    		    "Platform": "OSX.4+",
    		    "Version": "522.1",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "OmniWeb 5.5",
    		    "Platform": "OSX.4+",
    		    "Version": "420",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "iPod Touch / iPhone",
    		    "Platform": "iPod",
    		    "Version": "420.1",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Webkit",
    		    "Browser": "S60",
    		    "Platform": "S60",
    		    "Version": "413",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 7.0",
    		    "Platform": "Win 95+ / OSX.1+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 7.5",
    		    "Platform": "Win 95+ / OSX.2+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 8.0",
    		    "Platform": "Win 95+ / OSX.2+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 8.5",
    		    "Platform": "Win 95+ / OSX.2+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 9.0",
    		    "Platform": "Win 95+ / OSX.3+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 9.2",
    		    "Platform": "Win 88+ / OSX.3+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera 9.5",
    		    "Platform": "Win 88+ / OSX.3+",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Opera for Wii",
    		    "Platform": "Wii",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Nokia N800",
    		    "Platform": "N800",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Presto",
    		    "Browser": "Nintendo DS browser",
    		    "Platform": "Nintendo DS",
    		    "Version": "8.5",
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "KHTML",
    		    "Browser": "Konqureror 3.1",
    		    "Platform": "KDE 3.1",
    		    "Version": "3.1",
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "KHTML",
    		    "Browser": "Konqureror 3.3",
    		    "Platform": "KDE 3.3",
    		    "Version": "3.3",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "KHTML",
    		    "Browser": "Konqureror 3.5",
    		    "Platform": "KDE 3.5",
    		    "Version": "3.5",
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Tasman",
    		    "Browser": "Internet Explorer 4.5",
    		    "Platform": "Mac OS 8-9",
    		    "Version": null,
    		    "Grade": "X"
    		  },
    		  {
    		    "Engine": "Tasman",
    		    "Browser": "Internet Explorer 5.1",
    		    "Platform": "Mac OS 7.6-9",
    		    "Version": "1",
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Tasman",
    		    "Browser": "Internet Explorer 5.2",
    		    "Platform": "Mac OS 8-X",
    		    "Version": "1",
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "NetFront 3.1",
    		    "Platform": "Embedded devices",
    		    "Version": null,
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "NetFront 3.4",
    		    "Platform": "Embedded devices",
    		    "Version": null,
    		    "Grade": "A"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "Dillo 0.8",
    		    "Platform": "Embedded devices",
    		    "Version": null,
    		    "Grade": "X"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "Links",
    		    "Platform": "Text only",
    		    "Version": null,
    		    "Grade": "X"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "Lynx",
    		    "Platform": "Text only",
    		    "Version": null,
    		    "Grade": "X"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "IE Mobile",
    		    "Platform": "Windows Mobile 6",
    		    "Version": null,
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Misc",
    		    "Browser": "PSP browser",
    		    "Platform": "PSP",
    		    "Version": null,
    		    "Grade": "C"
    		  },
    		  {
    		    "Engine": "Other browsers",
    		    "Browser": "All others",
    		    "Platform": "",
    		    "Version": null,
    		    "Grade": "U"
    		  }
    		];
    $scope.filterOptions = {
      filterText: '',
      useExternalFilter: true
    };
    $scope.totalServerItems = 0;
    $scope.pagingOptions = {
      pageSizes: [25, 50, 100],
      pageSize: 25,
      currentPage: 1
    };
    $scope.setPagingData = function(data, page, pageSize) {
      var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
      $scope.myData = pagedData;
      $scope.totalServerItems = data.length;
      if (!$scope.$$phase) {
        $scope.$apply();
      }
    };
    $scope.getPagedDataAsync = function(pageSize, page, searchText) {
      setTimeout(function() {
        var data;
        if (searchText) {
          var ft = searchText.toLowerCase();
//          $http.get('assets/demo/ng-data.json').success(function(largeLoad) {
//            data = largeLoad.filter(function(item) {
//              return JSON.stringify(item).toLowerCase().indexOf(ft) !== -1;
//            });
//            $scope.setPagingData(data, page, pageSize);
//          });
//        } else {
//          $http.get('assets/demo/ng-data.json').success(function(largeLoad) {
//            $scope.setPagingData(largeLoad, page, pageSize);
//          });
          
          $scope.setPagingData($scope.dataGrid, page, pageSize);
        }
      }, 100);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    $scope.$watch('pagingOptions', function(newVal, oldVal) {
      if (newVal !== oldVal) {
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
      }
    }, true);
    $scope.$watch('filterOptions', function(newVal, oldVal) {
      if (newVal !== oldVal) {
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
      }
    }, true);

    $scope.gridOptions = {
      data: 'myData',
      enablePaging: true,
      showFooter: true,
      totalServerItems: 'totalServerItems',
      pagingOptions: $scope.pagingOptions,
      filterOptions: $scope.filterOptions
    };
  }]);