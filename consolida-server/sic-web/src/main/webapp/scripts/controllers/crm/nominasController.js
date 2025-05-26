angular.module('theme')
        .controller('nominasCtrl',function ($route, $window, $scope, $rootScope, $templateCache, $location, $timeout, $http, CONFIG, $bootbox, $log, clienteFinalService, pinesNotifications) {
            'use strict';

            $scope.tabSeleccionado = "generales";
            $scope.cliente = {};
            $scope.nominaCliente = {};
            $scope.colaborador = {};
            $scope.listColaboradores = [];
            $scope.listPeriodos = [];
            
            $scope.nacionalidad = {};
            $scope.estadoCivil = {};
            $scope.genero = {};
            $scope.entidadFederativa = {};
            $scope.nivelEstudio = {};
            $scope.formaPago = {};
            $scope.bancoOperador = {};
            $scope.tipoJornada = {};
            $scope.turno = {};
            $scope.tipoContrato = {};
            $scope.tipoIngreso = {};
            $scope.beneficioAdicional = {};
            $scope.variables = {};
            $scope.variables.isPencionAlimenticia=false;
            $scope.nominaPeriodicidad = {};
            $scope.tipoPago = {};
            $scope.inHabilitaEscritura = true;
            
            $scope.escrituraNominas=['ADMINISTRADOR_CRM','DIRECTOR_OPERACIONES'];
            
      	    $scope.cargaInicialRol = function(){
    		  
    		  $scope.inHabilitaEscritura = true;
    		  
    		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
    	            $scope.rolSeleccionado = data.data.usuarioRols[0].rol.nombre.toUpperCase();
    	            
    				  if($scope.escrituraNominas.includes($scope.rolSeleccionado)){
    					  $scope.inHabilitaEscritura = false;
    				  }
    	            
    		  },function(data){
    	            console.log("error --> " + data);
    	          });
      	    }
    	  
    	  $scope.cargaInicialRol();
    	  

            $scope.cargaInicial = function () {
                $http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/cargaInicial.json").then(
                    function (response) {
                        $scope.cliente = response.data.cliente;
                        $scope.nominaCliente = response.data.nominaCliente;
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al cargar los datos.',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            
            $scope.cargarCatalogosColaboradores = function(){
            	$http.post(CONFIG.APIURL + "/catalogoCrm/obtenerCatalogosColaboradores.json").then(
            			function (response) {
            				 	$scope.nacionalidad = response.data.nacionalidad;
            		            $scope.estadoCivil = response.data.estadoCivil;
            		            $scope.genero = response.data.genero;
            		            $scope.entidadFederativa = response.data.entidadFederativa;
            		            $scope.nivelEstudio = response.data.nivelEstudio;
            		            $scope.formaPago = response.data.formaPago;
            		            $scope.bancoOperador = response.data.bancoOperador;
            		            $scope.tipoJornada = response.data.tipoJornada;
            		            $scope.turno = response.data.turno;
            		            $scope.tipoContrato = response.data.tipoContrato;
            		            $scope.tipoIngreso = response.data.tipoIngreso;
            		            $scope.beneficioAdicional = response.data.beneficioAdicional;
            		            $scope.entidadFederativa = response.data.entidadFederativa;
                        },
                        function (response) {
                            $log.error(response.status + ' - ' + response.statusText);
                            pinesNotifications.notify({
                                title: 'Error',
                                text: 'Ocurrio un error al cargar los datos.',
                                type: 'error'
                            });
                            console.log("error --> " + response);
                        }
                    );
            };
            
            $scope.obtenerCatalogoTipoPago = function(){
            	$http.post(CONFIG.APIURL + "/catalogoCrm/obtenerCatalogoTipoPago.json").then(
            			function (response) {
            				 	$scope.tipoPago = response.data.tipoPago;
                        },
                        function (response) {
                            $log.error(response.status + ' - ' + response.statusText);
                            pinesNotifications.notify({
                                title: 'Error',
                                text: 'Ocurrio un error al cargar los datos.',
                                type: 'error'
                            });
                            console.log("error --> " + response);
                        }
                    );
            };

            $scope.seleccionarTab = function (tab) {
            	if(tab == 'colaboradores'){
            		$scope.obtenerColaboradores();
            	}else if(tab == 'periodicidad'){
            		$scope.obtenerPeriodos();
            		$scope.obtenerPeriodosFechas();
            		$scope.obtenerCatalogoTipoPago();
            	}
            	
                $scope.tabSeleccionado = tab;
            };
            $scope.IsVisibleRegistroColaborador = false;
            $scope.IsVisibleCargarColaboradores = false;
            $scope.IsVisibleDocColaborador = false;

            $scope.IsVisible = false;
            $scope.IsVisible2 = false;
            $scope.regColaboradores = false;

            $scope.ShowHide = function () {
                $scope.IsVisible = true;
                $scope.IsVisible2 = false;
                $scope.regColaboradores = false;
            };

            $scope.ShowHide2 = function () {
                $scope.IsVisible2 = true;
                $scope.IsVisible = false;
                $scope.regColaboradores = false;
            };

            $scope.ShowColaboradores = function () {
                $scope.regColaboradores = true;
            };
            
            $scope.selectPensionAlimenticia = function(){
            	$scope.colaborador.factorOMontoDescuento="";
            	if($scope.variables.isPencionAlimenticia){
            		$scope.colaborador.esPensionAlimenticia=1;
            	}else{
            		$scope.colaborador.esPensionAlimenticia=0;
            	}
            }
            
            $scope.selectClavePrincipal = function(valor){
            	if(valor){
            		$scope.colaborador.esClabe1Principal = 1;
            		$scope.colaborador.esClabe2Principal = 0;
            	} else {
            		$scope.colaborador.esClabe1Principal = 0;
            		$scope.colaborador.esClabe2Principal = 1;
            	}
            }
            
            $scope.showEditarColaborador = function (id) {
            	$scope.IsVisibleRegistroColaborador = true;
                $scope.IsVisibleCargarColaboradores = false;
            	$scope.obtenerColaboradorById(id);
            	
            	if($scope.colaborador.esPensionAlimenticia == 1){
            		$scope.variables.isPencionAlimenticia = true;
            	}
            	setTimeout("$('html, body').animate({scrollTop: ($('#idPanelColaborador').offset().top)-65}, 1500);",1000);
            }

            $scope.showVisibleRegistroColaborador = function () {
            	$scope.IsVisibleRegistroColaborador = true;
                $scope.IsVisibleCargarColaboradores = false;
                $scope.colaborador = {};
                
            	$scope.variables.isPencionAlimenticia=false;
                $scope.colaborador.esPensionAlimenticia=0;
                $scope.colaborador.esClabe1Principal=1;
                $scope.colaborador.esClabe2Principal=0;
                $scope.cargarCatalogosColaboradores();
                
                setTimeout("$('html, body').animate({scrollTop: ($('#idPanelColaborador').offset().top)-65}, 1500);",1000);
            };

            $scope.showIsVisibleCargarColaboradores = function () {
                $scope.IsVisibleCargarColaboradores = true;
                $scope.IsVisibleRegistroColaborador = false;
            };

            $scope.showDocColaborador = function (idColaborador) {
            	$scope.obtenerColaboradorById(idColaborador);
            	
                $scope.IsVisibleDocColaborador = true;
                $scope.IsVisibleRegistroColaborador = false;
                $scope.IsVisibleColaboradorNomina = false;
            };

            // combo de tipo de pago
            $scope.tipoPago = {
                selected: 'Semanal'
            };
            $scope.regresar = function () {
                location.href = CONFIG.APIURL + "#/crm/actualiza-cliente/1";
            };
            $scope.$watch('tipoPago.selected', function (newVal, oldVal) {});
            $scope.getTiposDePago = function (search) {
                var newSupes = $scope.tipoPago.slice();
                if (search && newSupes.indexOf(search) === -1) {
                    newSupes.unshift(search);
                }
                return newSupes;
            };

            $scope.tipoPago = ['Semanal', 'Catorcenal', 'Quincenal', 'Mensual', 'Anual'].sort();
            
            $scope.obtenerEntidadFederativaXCP = function(codigoPostal) {
            	clienteFinalService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
					  $scope.colaborador.municipios = response.data.municipios;
					  if(response.data.domicilio != null || response.data.domicilio != undefined){
					  $scope.colaborador.idEntidadFederativa = response.data.domicilio.idEntidadFederativa;
					  $scope.colaborador.catMunicipios = response.data.domicilio.catMunicipios;
					  }else{
						  $scope.colaborador.idEntidadFederativa = '';
						  $scope.colaborador.catMunicipios = '';
					  }
			    	},function(response){
						
					});
			    }	
            
            
            $scope.editarColaborador = function() {
            	$scope.colaborador.idNomina = $scope.nominaCliente.idNominaCliente;
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/editarColaborador.json", $scope.colaborador).then(
                    function (response) {
                        $scope.obtenerColaboradores();
                    	$scope.IsVisibleRegistroColaborador = false;
                        $scope.colaborador = {};
                        pinesNotifications.notify({
                            title: 'Colaborador',
                            text: 'El colaborador se edito exitosamente.',
                            type: 'success'
                        });
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al cargar los datos.',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            $scope.guardarColaborador = function(formColaborador) {
            	$scope.colaborador.idNomina = $scope.nominaCliente.idNominaCliente;
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/guardarColaborador.json", $scope.colaborador).then(
                    function (response) {
                    	$scope.obtenerColaboradores();
                    	$scope.IsVisibleRegistroColaborador = false;
                        $scope.colaborador = {};
                        pinesNotifications.notify({
                            title: 'Colaborador',
                            text: 'El colaborador se agrego exitosamente.',
                            type: 'success'
                        });
                        
                        if(formColaborador){
                        	formColaborador.$setPristine();
                        	formColaborador.$setUntouched();
						}
                        
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: response.data.mensajeError,
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            $scope.obtenerColaboradorById = function(id) {
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/obtenerColaboradorById.json", id).then(
                    function (response) {
                        $scope.colaborador = response.data.colaborador;
                        $scope.colaborador.fechaNacimiento = $scope.formatearFecha($scope.colaborador.fechaNacimiento);
                        $scope.colaborador.fechaFirmaContrato = $scope.formatearFecha($scope.colaborador.fechaFirmaContrato);
                        $scope.colaborador.antiguedadImss = $scope.formatearFecha($scope.colaborador.antiguedadImss);
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al obtener al colaborador',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            	$scope.cargarCatalogosColaboradores();
            };
            
            $scope.obtenerColaboradores = function() {
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/obtenerColaboradores.json").then(
                    function (response) {
                    	$scope.listColaboradores = response.data.listColaboradores;
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al cargar la lista de colaboradores.',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            $scope.obtenerPeriodos = function() {
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/obtenerPeriodos.json").then(
                    function (response) {
                    	$scope.nominaPeriodicidad = response.data.periodos;
                    	if($scope.nominaPeriodicidad==null){
                    		$scope.nominaPeriodicidad = {};
                    	}else{
                    		$scope.nominaPeriodicidad.fechaInicio = $scope.formatearFecha($scope.nominaPeriodicidad.fechaInicio);
                    	}
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al cargar los periodos.',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            $scope.obtenerPeriodosFechas = function() {
            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/obtenerPeriodosFechas.json").then(
                    function (response) {
                    	$scope.listPeriodos = response.data.listPeriodos;
                    },
                    function (response) {
                        $log.error(response.status + ' - ' + response.statusText);
                        pinesNotifications.notify({
                            title: 'Error',
                            text: 'Ocurrio un error al cargar los periodos.',
                            type: 'error'
                        });
                        console.log("error --> " + response);
                    }
                );
            };
            
            $scope.calcularPeriodo = function(){
            	
            	bootbox
    			.confirm({
    				title : "Confirmar acci&oacute;n",
    				message : "Esta seguro de guardar los periodos.",
    				buttons : {
    					confirm : {
    						label : 'S\u00ed',
    						className : 'btn-primary'
    					},
    					cancel : {
    						label : 'No',
    						className : 'btn-danger'
    					}
    				},
    				callback : function(result) {
    					if(result){
    					$scope.nominaPeriodicidad.idNomina = $scope.nominaCliente.idNominaCliente;
    	            	if($scope.nominaPeriodicidad != null){
    	            		$scope.nominaPeriodicidad.idNominaPeriodicidad = $scope.nominaPeriodicidad.idNominaPeriodicidad;
    	            	}
    	            	
    	            	$http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/calcularPeriodo.json", $scope.nominaPeriodicidad).then(
    	            			function(response){
    	            				$scope.obtenerPeriodos();
    	            				$scope.obtenerPeriodosFechas();
    	            				pinesNotifications.notify({
    	                                title: 'Periodos',
    	                                text: 'Los periodos se calcular&oacute;n exitosamente.',
    	                                type: 'success'
    	                            });
    	            			},function(response){
    	            				$log.error(response.status + ' - ' + response.statusText);
    	                            pinesNotifications.notify({
    	                                title: 'Error',
    	                                text: 'Ocurrio un error al calcular el periodo.',
    	                                type: 'error'
    	                            });
    	                            console.log("error --> " + response);
    	            			});
    					}
    				}
    			});
            	
            }
            $scope.archivo ={};
            $scope.documentoColaborador = {};
            $scope.subirDocumento = function(documento){
            	$scope.archivo = {};
			  	
			  	$scope.documentoColaborador = angular.copy(documento);
		        $('#agregarDocumento').modal('show');
		        
		        var fileElement = angular.element('#file');
		        angular.element(fileElement).val(null);
            }

			$scope.descargaDocumento = function(idCMS){
	      			$http.get(CONFIG.APIURL + "/documento/documentoByIdCMS/"+ idCMS +".json").then(function (response) {
	      				var link = document.createElement("a");
	      				   link.href =  response.data.mimeType + response.data.documentoBase64;
	                  	   link.style = "visibility:hidden";
	                  	   link.download = response.data.archivo;
	                  	   document.body.appendChild(link);
	                  	   link.click();
	                  	   document.body.removeChild(link);
	                  },
	                  function (data) {
	                	  $log.error(data.status+ ' - '+ data.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
						        type: 'error'
						      });
	                  });
		      }
			
			
			$scope.eliminarDocumento = function(itemDefinicionDocumento){
			    	  
			      		bootbox.confirm({
							  title : "Confirmar acci&oacute;n",
								message : "¿Est\u00e1s seguro de eliminar el documento ?",
								buttons : {
									confirm : {
										label : 'ACEPTAR',
										className : 'btn-primary'
									},
									cancel : {
										label : 'CANCELAR',
										className : 'btn-danger'
									}
								},
								callback : function(result) {
									if (result) {
										
										 $http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/eliminarDocumentoColaborador.json",itemDefinicionDocumento).then(
								                  function (response) {
								                	  $log.debug('ok');
														pinesNotifications.notify({
													        title: 'Mensaje',
													        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
													        type: 'success'
													      });
														
														$scope.obtenerColaboradorById(itemDefinicionDocumento.idColaborador);
														
								                  },
								                  function (data) {
								                	  $log.error(data.status+ ' - '+ data.statusText);
														pinesNotifications.notify({
													        title: 'Error',
													        text: 'Ocurrio un error al eliminar, favor de intentarlo más tarde.',
													        type: 'error'
													      });
								                  });
										
									}
								}				  
						  });
			      
				
			}
			
			$scope.fileChangedDoc = function (documento) {
				  
		          var lstArchivos = documento.files;
		          var val = lstArchivos[0].name.toLowerCase();
		          
		          
		        
		          var regex = new RegExp("(.*?)\.(pdf|docx|png|jpg)$");

		          if (!(regex.test(val))) {
		              $(this).val('');
		              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.pdf, .docx, .png, y/o .jpg</b>";
		              pinesNotifications.notify({
					        title: 'Error',
					        text: $scope.mensaje,
					        type: 'error'
					      });
		          }else if (lstArchivos[0].size > 2097152) {
		              $scope.mensaje = "El archivo excede el límite  de " + (2097152 / 1024 / 1024) + "MB";
		              $scope.$apply();
		              alert($scope.mensaje);
		          } else {
		              var reader = new FileReader();
		              
		              reader.onloadend = function () {
		                  $log.debug("Archivo cargado memoria");
		                  
		                  var documento = {};
		                  documento.mimeType = reader.result.substr(0,reader.result.indexOf(',')+1);
		                  documento.archivo = reader.result.substr(reader.result.indexOf(',') + 1);
		                  documento.nombreArchivo = lstArchivos[0].name;
		                  documento.tamanioArchivo = lstArchivos[0].size;
		                  
		                  $scope.documentoColaborador.documentoNuevo = documento;
		              }
		              
		              reader.readAsDataURL(lstArchivos[0]);
		              
		          }

		      };
		      $scope.guardarDocumento = function(){
		    	  $scope.documentoColaborador.idColaborador = $scope.colaborador.idColaborador ;
		    	  if($scope.documentoColaborador == undefined || $scope.documentoColaborador.documentoNuevo === undefined || 
		    			  $scope.documentoColaborador.documentoNuevo.nombreArchivo === undefined){
		    		  pinesNotifications.notify({
					        title: 'Error',
					        text: 'Es necesario adjuntar el documento',
					        type: 'error'
					      });
		    		  
		    		  return;
		    	  }
		      $http.post(CONFIG.APIURL + "/clienteCrm/actualizaNomina/guardarDocumentoColaborador.json", $scope.documentoColaborador).then(
		                  function (response) {
		                	  $log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });
								
								$scope.obtenerColaboradorById($scope.documentoColaborador.idColaborador);
	
								$('#agregarDocumento').modal('hide');
		                  }, function (data) {
		                	  $log.error(data.status+ ' - '+ data.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
							        type: 'error'
							      });
		                  });
		    	  
		    	  
		      }
            
            $scope.cargaInicial();
            
            $scope.formatearFecha = function(fecha){
            	if(fecha != undefined && fecha != null){
        			return  new Date(fecha);  
				}else{
					return null;
				}
            }
            $scope.obtenerFechaMes = function(mes) {
            	if(mes === 'Jan')
            		return "Ene";
            	else if (mes === 'Feb')
            		return "Feb";
            	else if (mes === 'Mar')
            		return "Mar";
            	else if (mes === 'Apr')
            		return "Abr";
            	else if (mes === 'May')
            		return "May";
            	else if (mes === 'Jun')
            		return "Jun";
            	else if (mes === 'Jul')
            		return "Jul";
            	else if (mes === 'Aug')
            		return "Ago";
            	else if (mes === 'Sep')
            		return "Sep";
            	else if (mes === 'Oct')
            		return "Oct";
            	else if (mes === 'Nov')
            		return "Nov";
            	else if (mes === 'Dec')
            		return "Dic";
            	else
            		return "";
            }
            $scope.cargaNacionaidad = function(idNacionalidad){
            	if($scope.colaborador.idNacionalidad != 2894){
            		$scope.colaborador.pais = '';
            	}else{
            		$scope.colaborador.pais = 'México';
            	}
            }
            $scope.agregarBeneficio = function(){
            	if($scope.colaborador.listBeneficioAdicColab == undefined || $scope.colaborador.listBeneficioAdicColab == null){
            		$scope.colaborador.listBeneficioAdicColab = [];
            	}
            	for (var i = 0; i < $scope.colaborador.listBeneficioAdicColab.length; i++) {
            		if($scope.colaborador.listBeneficioAdicColab[i].general.idCatGeneral==$scope.colaborador.idBeneficioAdicional){
            			pinesNotifications.notify({
					        title: 'Error',
					        text: 'El beneficio adicional <span style="font-weight: bold;">'+ $scope.colaborador.listBeneficioAdicColab[i].general.descripcion +',</span>  ya se encuentra seleccionado.',
					        type: 'error'
					      });
            		 return;
            		}
            	}
            	
            	
            	for (var i = 0; i < $scope.beneficioAdicional.length; i++) {
            		if($scope.beneficioAdicional[i].idCatGeneral==$scope.colaborador.idBeneficioAdicional){
            			var beneficio = {};
            			beneficio.general = $scope.beneficioAdicional[i];
            			$scope.colaborador.listBeneficioAdicColab.push(beneficio);
            			break;
            		}
            	}
            	$scope.removerVatBenfList();
            	$scope.colaborador.idBeneficioAdicional = null;
            }
            
            $scope.removerBeneficio = function(row) {
            	var i = $scope.colaborador.listBeneficioAdicColab.indexOf( row );
            	$scope.colaborador.listBeneficioAdicColab.splice( i, 1 );
            	$scope.removerVatBenfList();
            }
            $scope.removerVatBenfList = function() {
            	$scope.obtenerCatalogosBeneficiosAdicionales();
            	for (var i = 0; i < $scope.colaborador.listBeneficioAdicColab.length; i++) {
            		var x = $scope.beneficioAdicional.indexOf($scope.colaborador.listBeneficioAdicColab[i].general);
            		$scope.beneficioAdicional.splice(x, 1);
            	}
            }
            
            $scope.obtenerCatalogosBeneficiosAdicionales = function(){
            	$http.post(CONFIG.APIURL + "/catalogoCrm/obtenerCatalogosBeneficiosAdicionales.json").then(
            			function (response) {
            		            $scope.beneficioAdicional = response.data.beneficioAdicional;
                        },
                        function (response) {
                            $log.error(response.status + ' - ' + response.statusText);
                            pinesNotifications.notify({
                                title: 'Error',
                                text: 'Ocurrio un error al cargar los datos.',
                                type: 'error'
                            });
                            console.log("error --> " + response);
                        }
                    );
            };
            
            
            
        });
