'use strict';
angular
		.module('theme.core.templates')
		.controller(
				'serieFolioController',
				function($route, $window, $scope, $rootScope, $templateCache,
						$location, $timeout, $http, CONFIG, $bootbox, $log,
						serieFolioService, pinesNotifications) {

					$scope.catSerieDto = {};
					$scope.catFolioDto = {
						fechaInicioVigencia : null,
						fechaFinVigencia : null,
						FechaInicioVigenciaFormato: "",
						FechaFinVigenciaFormato: "",
						indEstatus: ""
					};
					$scope.IsVisibleBotonGuardarSerie = true;
					$scope.IsVisibleBotonActualizarSerie = false;
					$scope.IsVisibleBotonGuardarFolio = true;
					$scope.IsVisibleBotonActualizarFolio = false;
					$scope.fechaInicioVigencia = {};
					$scope.fechaFinVigencia = {};

					$scope.cargaInicialSerieFolio = function() {

						serieFolioService
								.cargaInicialSerieFolio(
										function(response) {
											$scope.gridSeries = response.data.gridSeries;
											$scope.comboTipoComprobante = response.data.comboTipoComprobante;
											$scope.comboCelula = response.data.comboCelula;
											// $scope.gridFolios =
											// response.data.gridFolios;
											// $scope.listaCatSerie =
											// response.data.listaCatSerie;
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications
													.notify({
														title : 'Error',
														text : 'Ocurrio un error, favor de intentarlo mas tarde.',
														type : 'error'
													});
										});
					};

					$scope.cargaInicialSerieFolio();

					$scope.validarSerie = function(serieForm) {

						$scope.catSerieDto.fechaInicioVigencia = $scope.fechaInicioVigencia.value;
						$scope.catSerieDto.fechaFinVigencia = $scope.fechaFinVigencia.value;
						$scope.catSerieDto.fechaInicioVigenciaFormato="";
						$scope.catSerieDto.fechaFinVigenciaFormato="";
						$scope.catSerieDto.indEstatus="";
						$scope.catSerieDto.usuarioAlta=null;
						
						if($scope.fechaFinVigencia.value.getTime() <=$scope.fechaInicioVigencia.value.getTime()){
							pinesNotifications.notify({
									title : 'Error',
									text : 'La Fecha Vigencia Fin no puede ser igual o menor a la fecha de inicio. ',
									type : 'error'
								});
							return;
							}

						let serie = Object.keys($scope.gridSeries);
						for (let i = 0; i < $scope.gridSeries.length; i++) {
							let serie = $scope.gridSeries[i];

							if (serie.catCelula.descripcion == $scope.catSerieDto.catCelula.descripcion) {
								if (serie.catTipoComprobante.descripcion == $scope.catSerieDto.catTipoComprobante.descripcion) {
									if (serie.idEstatusSerie == 1) {
										var fechaLong = $scope.catSerieDto.fechaInicioVigencia * 1;
										var fechaSerie = {	value : new Date(serie.fechaFinVigencia)};
										if(fechaSerie.value.getTime() >$scope.fechaInicioVigencia.value.getTime()){
										pinesNotifications.notify({
												title : 'Error',
												text : 'Las fechas de una serie no deben traslaparse con la serie Activa. ',
												type : 'error'
											});
										return;
										}

									}else if(serie.idEstatusSerie == 0){
										pinesNotifications.notify({
											title : 'Error',
											text : 'No se puede tener dos seccuencias para una Celula y mismo tipo, con Estatus Por iniciar. ',
											type : 'error'
										});
									return;
									}
								}
							}

						
						}
					 $scope.guardarSerie(serieForm);
					}
					

					$scope.guardarSerie = function(serieForm) {

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.guardarSerie(
															$scope.catSerieDto,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catSerieDto = {};
																					$scope
																							.cargaInicialSerieFolio();
																					if (serieForm) {
																						serieForm
																								.$setPristine();
																						serieForm
																								.$setUntouched();
																					}

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					$scope.actualizarDatosSerie = function(serieForm) {
						
						$scope.catSerieDto.fechaInicioVigencia = $scope.fechaInicioVigencia.value;
						$scope.catSerieDto.fechaFinVigencia = $scope.fechaFinVigencia.value;

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas actualizar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.guardarSerie(
															$scope.catSerieDto,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catSerieDto = {};
																					$scope.IsVisibleBotonGuardarSerie = true;
																					$scope.IsVisibleBotonActualizarSerie = false;
																					$scope
																							.cargaInicialSerieFolio();
																					if (serieForm) {
																						serieForm
																								.$setPristine();
																						serieForm
																								.$setUntouched();
																					}

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					$scope.eliminarSerie = function(idCatSerie) {

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas borrar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.eliminarSerie(
															idCatSerie,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catSerieDto = {};
																					$scope.IsVisibleBotonGuardarSerie = true;
																					$scope.IsVisibleBotonActualizarSerie = false;
																					$scope
																							.cargaInicialSerieFolio();

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					$scope.guardarFolio = function() {

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.guardarFolio(
															$scope.catFolioDto,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catFolioDto = {};
																					$scope
																							.cargaInicialSerieFolio();

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					$scope.actualizarDatosFolio = function() {

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas actualizar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.guardarFolio(
															$scope.catFolioDto,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catFolioDto = {};
																					$scope.IsVisibleBotonGuardarFolio = true;
																					$scope.IsVisibleBotonActualizarFolio = false;
																					$scope
																							.cargaInicialSerieFolio();

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					$scope.eliminarFolio = function(idCatFolio) {

						bootbox
								.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro que deseas borrar la informaci\u00f3n?",
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
										if (result) {

											serieFolioService
													.eliminarFolio(
															idCatFolio,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log
																			.error(response.status
																					+ ' - '
																					+ response.statusText);
																	pinesNotifications
																			.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});
																} else {

																	bootbox
																			.alert({
																				title : "Mensaje",
																				message : "La operaci\u00f3n se complet\u00f3 con \u00e9xito.",
																				buttons : {
																					ok : {
																						label : 'Aceptar',
																						className : 'center-block btn-primary'
																					}
																				},
																				callback : function() {

																					$scope.catFolioDto = {};
																					$scope.IsVisibleBotonGuardarFolio = true;
																					$scope.IsVisibleBotonActualizarFolio = false;
																					$scope
																							.cargaInicialSerieFolio();

																				}
																			});
																}
															},
															function(response) {
																$log
																		.error(response.status
																				+ ' - '
																				+ response.statusText);
																pinesNotifications
																		.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

															});
										}
									}
								});
					};

					// opcion del grid
					$scope.actualizarSerie = function(serie) {

						$scope.IsVisibleBotonGuardarSerie = false;
						$scope.IsVisibleBotonActualizarSerie = true;

						var fechaInicio = new Date(serie.fechaInicioVigencia);
						var fechaFin = new Date(serie.fechaFinVigencia);

						var fstring = fechaInicio.toLocaleDateString();

						$scope.catSerieDto = angular.copy(serie);

						$scope.fechaInicioVigencia = {
							value : fechaInicio
						};
						$scope.fechaFinVigencia = {
							value : fechaFin
						};

						$scope.data = [];

					}

					$scope.cancelarActualizacionSerie = function(serieForm) {
						$scope.IsVisibleBotonGuardarSerie = true;
						$scope.IsVisibleBotonActualizarSerie = false;
						$scope.catSerieDto = {};
						if (serieForm) {
							serieForm.$setPristine();
							serieForm.$setUntouched();
						}
						$scope.cargaInicialSerieFolio();

					}

					$scope.actualizarFolio = function(folio) {

						$scope.IsVisibleBotonGuardarFolio = false;
						$scope.IsVisibleBotonActualizarFolio = true;

						$scope.catFolioDto = angular.copy(folio);
					}

					$scope.cancelarActualizacionFolio = function() {
						$scope.IsVisibleBotonGuardarFolio = true;
						$scope.IsVisibleBotonActualizarFolio = false;
						$scope.catFolioDto = {};
						$scope.cargaInicialSerieFolio();
					}
				});