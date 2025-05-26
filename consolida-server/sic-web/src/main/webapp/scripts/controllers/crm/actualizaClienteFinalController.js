'use strict';
angular.module('theme.core.templates')
  .controller('actualizaClienteFinalController', function( $route,$window,$scope, $rootScope, $templateCache,$location, $timeout,$http, CONFIG, $bootbox,$log, clienteFinalService, pinesNotifications, agregarClienteCrmService) {

	  $scope.escrituraDatosGenerales=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraDomicilioMedioContacto=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraCelula=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraConceptoFacturacionServicio=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraNominas=['ADMINISTRADOR_CRM','DIRECTOR_OPERACIONES'];

	  $scope.escrituraComisiones=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES','DIRECTOR_FINANZAS'];

	  $scope.escrituraHonorarios=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraCuentasBancarias=['ADMINISTRADOR_CRM','DIRECTOR_OPERACIONES','DIRECTOR_FINANZAS','TESORERIA'];

	  $scope.escrituraDatosSTP=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES','TESORERIA'];

	  $scope.escrituraRepresentanteYApoderado=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.escrituraDocumentos=['ADMINISTRADOR_CRM' ,'DIRECTOR_COMERCIAL' ,'GERENTE_COMERCIAL' ,'DIRECTOR_OPERACIONES'];

	  $scope.noVenComisiones = ['GERENTE_CELULA','SUPERVISOR_CELULA','NOMINISTA'];



	  $scope.tipoDoc = "";
	  $scope.tabSeleccionado = "generales";
	  $scope.ejemploPPP = {};
	  $scope.rolSeleccionado = null;
	  $scope.inHabilitaEscritura = false;

	  $scope.muestaComisiones = true;

	  $scope.cargaInicialRol = function(){

		  $scope.inHabilitaEscritura = true;
		  $scope.muestaComisiones = true;

		  $http.get(CONFIG.APIURL +"/usuarioAplicativo.json").then(function(data){
	            $scope.rolSeleccionado = data.data.usuarioRols[0].rol.nombre.toUpperCase();

				  if($scope.escrituraDatosGenerales.includes($scope.rolSeleccionado)){
					  $scope.inHabilitaEscritura = false;
				  }

				  if($scope.noVenComisiones.includes($scope.rolSeleccionado)){
					  $scope.muestaComisiones = false;
				  }


		  },function(data){
	            console.log("error --> " + data);
	          });
	  }

	  $scope.cargaInicialRol();

	  // este if se ocuapara cuando se este atacando la pantalla de la nomina del cliente, ya que debe de haber un redirect
	  if($location.url().includes('nominaCliente')){
		  $scope.tabSeleccionado = "generales";
	  }


	  $scope.seleccionarTab = function(tab){
		  $scope.tabSeleccionado = tab;
		  $scope.inHabilitaEscritura = true;

		  if($scope.tabSeleccionado == "generales"){
			  $scope.cargaInicialDatosGenerales();
			  $scope.cargaInicialActividad();

			  if($scope.escrituraDatosGenerales.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "celula"){
			  $scope.cargaInicialCelula();

			  if($scope.escrituraCelula.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "domicilio"){
			  $scope.cargaInicialDomicilio();

			  if($scope.escrituraDomicilioMedioContacto.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "productos"){
			  $scope.cargaInicialProductos();
			  $scope.cargaInicialConceptoFacturacion();

			  if($scope.escrituraConceptoFacturacionServicio.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "nominas"){
			  $scope.cargaInicialNominaCliente();
			  $scope.cargaInicialCelula();
			  $scope.getNoministas();

			  if($scope.escrituraNominas.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "cuentasBancarias"){
			  $scope.cargaInicialCuentasBancarias();

			  if($scope.escrituraCuentasBancarias.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "stpCliente"){
			  $scope.cargaInicialDatosStp();

			  if($scope.escrituraDatosSTP.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado == "actividades"){
		  $scope.cargaInicialActividad();

		  }else if($scope.tabSeleccionado == "comisiones"){
			  $scope.cargaInicialClienteComisiones();
			  $scope.cargaInicialCelula();

			  if($scope.escrituraComisiones.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
//			  $scope.getNoministas();

		  }else if($scope.tabSeleccionado == "formulaHono"){
			  $scope.mostrarPanelHonorarios = false;
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.cargaInicialClienteHonorarios();
			  $scope.cargaInicialCelula();
			  $scope.limpiarHonorario();

			  if($scope.escrituraHonorarios.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

//			  $scope.getNoministas();

		  }else if($scope.tabSeleccionado === "apoderadoLegal"){
			  $scope.cargaInicialRepresentanteLegal();
			  $scope.cargaInicialApoderadoLegal();

			  if($scope.escrituraRepresentanteYApoderado.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }

		  }else if($scope.tabSeleccionado === "docCliente"){
			  $scope.cargaInicialDocumentosCliente();

			  if($scope.escrituraDocumentos.includes($scope.rolSeleccionado)){
				  $scope.inHabilitaEscritura = false;
			  }
		  }
	  }




	  var paramValue = $route.current.params.idCliente;
	  var data = {};
	  $scope.clienteTempDto = {};
	  $scope.clienteDomicilioDto = {};
	  $scope.clienteDomicilioDto.municipios = {};
	  $scope.clienteDomicilioDto.municipiosDomicilioComercial = {};
	  $scope.clienteDomicilioDto.domicilio = {};
	  $scope.clienteDomicilioDto.domicilio.catMunicipios = {};
	  $scope.clienteDomicilioDto.domicilioComercial = {};
	  $scope.clienteDomicilioDto.domicilioComercial.catMunicipios = {};
	  $scope.clienteMedioContactoDto = {};


	 // CUENTAS BANCARIAS
	  $scope.clienteCuentasBancarias ={};
	  $scope.clienteCuentaBancaria = {};
	  $scope.clienteCuentaBancaria.catBanco = {};
	  $scope.clienteCuentaBancaria.catTipoCuenta = {};
	  $scope.mostrarCuenta = false;

	//// COMISIONES
	  $scope.mostrarPanelComisiones = false;
	  $scope.mostrarPanelAgregarComision = false;
	  $scope.comision={};
	  $scope.comision.canalVenta={};

	  $scope.comision.esquema={};
	  $scope.comision.formulaPricing={};
	  $scope.comision.formulaComision ={};
	  $scope.comision.usuario = {};
	  $scope.tipoCanalVenta = {};

	  $scope.comision.esquema.idCatGeneral = '';

	  //HONORARIOS
	  $scope.mostrarPanelHonorarios = false;
	  $scope.mostrarPanelAgregarHonorarios = false;
	  $scope.nominaClienteHonorariosDto ={};
	  $scope.catFormulaHonorario={};
	  $scope.honorario = {};
	  $scope.honorario.formulaFactura = {};
	  $scope.honorario.formulaPPP ={};
	  $scope.honorario.formulaTipoIva ={};
	  $scope.honorario.ivaComercial = 16;
	  $scope.mostrarPanelAgregarHonorariosSS = false;
	  $scope.mostrarPanelAgregarHonorariosMixto = false;
	  $scope.mostrarPanelAgregarHonorariosMaquila = false;
	  $scope.honorario.formulaPPPMaquila ={};
	  $scope.honorario.formulaPPPMixto ={};
	  $scope.honorario.formulaPPPSs ={};


	  // DATOS GENERALES Y CONCEPTOS FACTURACION CLIENTE
	  $scope.clienteDto = {};
	  $scope.clienteDto.prefijoSTP;
	  $scope.clienteConceptoFacturacion = {}
	  $scope.IsVisibleBotonGuardarConcepto = true;
	  $scope.IsVisibleBotonActualizarConcepto = false;

	  // NOMINA CLIENTE
	  $scope.nominaClienteDto = {};
	  $scope.nominaClienteDto.prestadoraServicio;
	  $scope.IsVisibleComboComisionSS = true;
	  $scope.IsVisibleBotoAgregar = true;
	  $scope.IsVisibleCampoSueldosSalarios = false;
	  $scope.tituloNominaCliente;
	  $scope.agregarNomina = function(){
		  $scope.nominaClienteDto.idNominaCliente = null;
		  $scope.nominaClienteDto.nombreNomina = null;
		  $scope.nominaClienteDto.claveNomina = null;
		  $scope.nominaClienteDto.comisionPpp = null;
		  $scope.nominaClienteDto.comisionSs = null;
		  $scope.nominaClienteDto.catProductoNomina = null;
		  $scope.nominaClienteDto.prestadoraServicioFondo = null;
		  $scope.nominaClienteDto.prestadoraServicio = null;
		  $scope.IsVisibleAgregarNomina = true;
		  $scope.IsVisibleCargarNomina = false;
		  $scope.IsVisibleComision = false;
		  $scope.tituloNominaCliente = "AGREGAR NÓMINA"
		  $scope.validarAgregarNomina();
	  }
	  $scope.campoSueldosSalarios = function(){
		  if($scope.nominaClienteDto.catProductoNomina.idCatGeneral == 306
				  || $scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9949
				  || $scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9950){
			  $scope.IsVisibleCampoSueldosSalarios = true;
		  }else{
			  $scope.IsVisibleCampoSueldosSalarios = false;
			  $scope.nominaClienteDto.comisionSs=null;
		  }
	  }

	  // CELULA
	  $scope.isVisibleComboPrestadora = false;

	  // REPRESENTANTE Y APODERADOS LEGALES
	  $scope.representanteLegalDto = {};
	  $scope.IsVisibleFormularioRepresentante = false;
	  $scope.IsVisibleDocumentosRepresentante = false;
	  $scope.IsVisibleRepresentanteArchivos = false;
	  $scope.tituloDinamicoLegal = '';

	  $scope.apoderadoLegalDto = {};
	  $scope.IsVisibleFormularioApoderado = false;
	  $scope.IsVisibleDocumentosApoderado = false;



//////////////////////////////////////////////////// DATOS DUMMIES - BORRARLO DESPUES ////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	  //////////////////////// DUMMIES ///////////////////////////////////
	  $scope.IsVisibleColaboradorNomina = false;
	  $scope.IsVisibleRegistroColaborador = false;
	  $scope.IsVisibleDocColaborador = false;
	  $scope.IsVisibleAgregarNomina = false;
	  $scope.IsVisibleCargarNomina = false;
	  $scope.IsVisibleCargarNomina = false;
	  $scope.isVisibleFactorDescuento = false;

	  $scope.IsVisibleComision = false;


		  $scope.actualizaCliente = function() {
			  bootbox
				.confirm({
					title : "Confirmar acción",
					message : "¿Est\u00e1s seguro que deseas actualizar la informaci\u00f3n?",
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

							 var data = {
									 'clienteTempDto' : $scope.clienteTempDto
							 };
							clienteFinalService.update(data.clienteTempDto,function() {

								$log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });
												location.href=APIURL+"#/crm/clientes";
											},
											function(response) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de verificar.',
											        type: 'error'
											      });
											});

						}
					}
				});
		  }

		  $scope.nuevoApoderado = function(){
			  $scope.tituloDinamicoLegal = "Agregar apoderado legal";
			  $scope.editar=false;
			  $scope.apoderadoDto  = {};
		  }
		  $scope.editaApoderado = function(){
			  $scope.tituloDinamicoLegal = "Editar apoderado legal";
			  $scope.editar=true;
			  $scope.apoderadoDto  = {nombre:"ALBERTO",
					  apellidoPaterno:"MANZO", apellidoMaterno:"CHIRINO",
					  rfc:"AMANZO840839YT5", curp:"AMANZO840839YT5HDFRNR02" };
		  }
		  $scope.nuevaCuenta = function(){
			  $scope.tituloDinamicoCuenta = "Agregar cuenta bancaria";
			  $scope.editar=false;
			  $scope.cuentaBancaria  = {};
		  }
		  $scope.editaCuenta = function(){
			  alert();
			  $scope.tituloDinamicoCuenta = "Editar cuenta bancaria";
			  $scope.editar=false;
			  $scope.cuentaBancaria  = {numeroCuenta:"000000000000", clabe:"1231231231231231"};
		  }


		// COLABORADORES
		  $scope.showColaboradorNomima = function(){
			  $scope.IsVisibleColaboradorNomina = true;
			  $scope.IsVisibleRegistroColaborador = false;
			  $scope.IsVisibleDocColaborador=false;
		  }

		  $scope.showRegistroColaborador = function(){
			  $scope.IsVisibleRegistroColaborador = true;
			  $scope.IsVisibleColaboradorNomina = false;
			  $scope.IsVisibleDocColaborador=false;
		  }

		  $scope.showEditarColaborador = function(){
			  $scope.IsVisibleRegistroColaborador = true;
			  $scope.IsVisibleColaboradorNomina = false;
			  $scope.IsVisibleDocColaborador=false;
			  $scope.nombreColaborador = "SERGIO ANDRES HERNANDEZ GONZALEZ";
			  $scope.colaborador = {nombre:"SERGIO ANDRES",
					  apellidoPaterno:"HERNANDEZ", apellidoMaterno:"GONZALEZ",
					  rfc:"AMANZO840839YT5", curp:"AMANZO840839YT5HDFRNR02"

			  };
		  }

		  $scope.showDocColaborador = function(){
			  $scope.IsVisibleDocColaborador=true
			  $scope.IsVisibleRegistroColaborador = false;
			  $scope.IsVisibleColaboradorNomina = false;
		  }

		  $scope.showFactorDescuento = function(){

			  $scope.isVisibleFactorDescuento = true;
		  }


	      $scope.valor = {
	    	        radio: '1'
	    	      };




		  $scope.agregarComision = function(){
			  $scope.IsVisibleAgregarNomina = false;
			  $scope.IsVisibleCargarNomina = false;
			  $scope.IsVisibleComision = true;
		  }

		  $scope.cargarArchivoNomina = function(){
			  $scope.IsVisibleAgregarNomina = false;
			  $scope.IsVisibleCargarNomina = true;
			  $scope.IsVisibleComision = false;
		  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




////////////////DATOS GENERALES  ///////////////////////////////////////////

		  $scope.cargaInicialDatosGenerales = function() {
			  clienteFinalService.cargaInicialDatosGenCliente(function(response) {

				  $scope.clienteDto = response.data.clienteDto;
				  $scope.listaGrupos = response.data.listaGruposDto;
				  $scope.listaCatTipoPago = response.data.listaCatTipoPago;
				  $scope.listaCatGiroComercial = response.data.listaCatGiroComercial;
				  $scope.listaSubGrupo = response.data.listaSubGrupo;
				  $scope.listaCategorias = response.data.listaCategorias;
				  $scope.listaRegimenes = response.data.listaRegimenes;
				  $scope.getPrestadoras($scope.clienteDto.celula.idCelula);
				  agregarClienteCrmService.listaCelulas();
				  $scope.cargaInicialActividad();
				  if(response.data.clienteDto.fechaConstitucionEmpresa!=undefined && response.data.clienteDto.fechaConstitucionEmpresa!=null){
					  $scope.clienteDto.fechaConstitucionEmpresa =  new Date(response.data.clienteDto.fechaConstitucionEmpresa);
				  }
				  if($location.url().includes('nominaCliente')){
					  //$('#nominas').click();
					  $scope.seleccionarTab('nominas');
				  }
			  },function(response) {
						$log.error("error --> " + reponse);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error, favor de verificar.',
					        type: 'error'
					      });

					});

			  clienteFinalService.cargaInicialConceptoFacturacion(function(response) {
				  $scope.gridConcepFacturacion = response.data;
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});

			  clienteFinalService.detalles(paramValue,function(response){

				  $scope.tituloDinamicoLegal = "Agregar apoderado legal";
				  $scope.tituloDinamicoCuenta = "Agregar cuenta bancaria";
				  if(response.data){
					  $scope.clienteTempDto = response.data;
					  $scope.clienteTempDto.idClienteTemp = paramValue;
				  }


			  },function(response){
					$log.error(response.status+ ' - '+ response.statusText);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Error al cargar el detalle del usuario seleccionado' + response.statusText,
				        type: 'error'
				      });
			  });


			  agregarClienteCrmService.listaCelulas(function(response) {
		    		$scope.listaCelulas = response.data;
		    	},function(response){});

		  }


		  $scope.cargaInicialDatosGenerales();



		  	$scope.guardarDatosGenerales = function() {

		    	bootbox.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
							clienteFinalService.guardarGeneralesCliente($scope.clienteDto, function(response) {

								if(response.data.mensajeError != undefined){
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: response.data.mensajeError,
								        type: 'error'
								      });
								}else{
									$log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });

											$scope.cargaInicialDatosGenerales();
								}
							},
							function(response) {
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
							        type: 'error'
							      });

							});
						}
					}
				});
		    };

			  // combo de subgrupo comercial
			  $scope.getSubgrupo = function () {

				  if($scope.clienteDto){
					  $http.get(CONFIG.APIURL + "/clienteCrm/listaCatSubGiroComercial/"+$scope.clienteDto.catGiroComercialDto.idCatGeneral+".json").then(function(response){
						  $scope.listaSubGrupo = response.data;
//						  $scope.clienteDto.noministaDto = null;
				  	    },function(response){
				  	        console.log("error --> " + response);
				  	  });
				  }
			  }


			  // combo de prestadoras fondo
			  $scope.getPrestadoras = function (idCelula) {
				  $http.post(CONFIG.APIURL + "/clienteCrm/getPrestadorasByIdCelula.json", idCelula).then(function(response){
					  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
					  $scope.clienteDto.prestadoraServicioFondo = response.data.listaPrestadorasFondo[0];
			  	    },function(data){
			  	        console.log("error --> " + data);
			  	  });
			  }

////////////////////   CELULA   /////////////////////////////////////////////////////////
	  $scope.cargaInicialCelula = function() {
		  clienteFinalService.cargaInicialCelula(function(response) {
			  $scope.clienteDto = response.data.clienteDto;
			  $scope.clienteDto.prestadoraServicio = null;
			  $scope.listaCelulas = response.data.listaCelulasDto;
//			  $scope.listaNoministas = response.data.listaNoministasDto;
			  $scope.listaClientesPrestadoras = response.data.listaClientesPrestadoras;
			  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
			  $scope.listaPrestadoras = response.data.listaPrestadoras;

			  if($scope.clienteDto.celula!=null && $scope.clienteDto.celula.idCelula !=null){
				  $scope.clienteDto.prestadoraServicioFondo.idPrestadoraServicio = response.data.listaPrestadorasFondo[0].idPrestadoraServicio;
			  }

			  if($scope.listaPrestadoras!=undefined &&  $scope.listaPrestadoras!=null && $scope.listaPrestadoras.length > 0){
				  $scope.isVisibleComboPrestadora = true;
			  }else{
				  $scope.isVisibleComboPrestadora = false;
			  }

		  },function(response) {
				$log.error("error --> " + response);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
			        type: 'error'
			      });

			});
		};

	  	$scope.guardarDatosCelula = function() {

	    	bootbox.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
						clienteFinalService.guardaCelula($scope.clienteDto, function(response) {

							if(response.data.mensajeError != undefined){
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: response.data.mensajeError,
							        type: 'error'
							      });
							}else{
								$log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });

										$scope.cargaInicialCelula();
							}
						},
						function(response) {
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
						        type: 'error'
						      });

						});
					}
				}
			});
	    };

		  // combo de noministas, se mostraran noministas vinculados a la celula seleccionada
		  $scope.getNoministas = function () {

			  if($scope.clienteDto !=undefined && $scope.clienteDto != null){
				  if(($scope.clienteDto.celula !=undefined && $scope.clienteDto.celula != null)
						  && ($scope.clienteDto.celula.idCelula !=undefined && $scope.clienteDto.celula.idCelula != null)){
					  $http.get(CONFIG.APIURL + "/clienteCrm/listaNoministas/"+$scope.clienteDto.celula.idCelula+".json").then(function(data){
						  $scope.listaNoministas = data.data.listaNoministas;
						  $scope.clienteDto.noministaDto = null;
				  	    },function(data){
				  	        console.log("error --> " + data);
				  	  });
				  }
			  }
		  }

		  // combo de noministas, se mostraran noministas vinculados a la celula seleccionada
		  $scope.getPrestadoras = function (idCelula) {
			  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/celula/getPrestadorasByIdCelula.json", idCelula).then(function(response){
				  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
				  $scope.clienteDto.prestadoraServicioFondo.idPrestadoraServicio = response.data.listaPrestadorasFondo[0].idPrestadoraServicio;
				  $scope.listaPrestadoras = response.data.listaPrestadoras;

				  if($scope.listaPrestadoras!=undefined &&  $scope.listaPrestadoras!=null && $scope.listaPrestadoras.length > 0){
					  $scope.isVisibleComboPrestadora = true;
				  }else{
					  $scope.isVisibleComboPrestadora = false;
				  }

//					  $scope.clienteDto.noministaDto = null;
		  	    },function(data){
		  	        console.log("error --> " + data);
		  	  });
		  }

		  $scope.eliminarClientePrestadora = function(idClientePrestadoraServicio) {
			  bootbox.confirm({
				  title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas eliminar la prestadora?",
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
							clienteFinalService.eliminarClientePrestadora(idClientePrestadoraServicio, function(response) {

								if(response.data.mensajeError != undefined){
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: response.data.mensajeError,
								        type: 'error'
								      });
								}else{
									$log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });


											$scope.cargaInicialCelula();

								}
							},
							function(response) {
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
							        type: 'error'
							      });
							});
						}
					}
			  });
			};

//////////////CONCEPTO FACTURACION CLIENTE  ///////////////////////////////////////////


		  $scope.cargaInicialConceptoFacturacion = function() {
			  clienteFinalService.cargaInicialConceptoFacturacion(function(response) {
				  $scope.gridConcepFacturacion = response.data;
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
			};

		  $scope.guardaConceptoFacturacion = function() {

			  clienteFinalService.guardaConceptoFacturacion($scope.clienteConceptoFacturacion,function(response) {
				  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
					  $log.error(response.status+ ' - '+ response.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la el resguardo del concepto. Favor de intentarlo mas tarde',
					        type: 'error'
					      });
								$scope.clienteConceptoFacturacion.descConceptoFacturacion = null;
								$scope.cargaInicialConceptoFacturacion();
								$scope.IsVisibleBotonGuardarConcepto = true;
								$scope.IsVisibleBotonActualizarConcepto = false;
				  }else{
					  $scope.clienteConceptoFacturacion.descConceptoFacturacion = null;
					  $scope.IsVisibleBotonGuardarConcepto = true;
					  $scope.IsVisibleBotonActualizarConcepto = false;
					  $scope.cargaInicialConceptoFacturacion();
				  }
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
			};


		  $scope.eliminarConceptoFacturacion = function(conceptoFact) {
			  bootbox.confirm({
				  title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas eliminar el concepto de facturaci\u00f3n?",
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
							clienteFinalService.eliminarConceptoFacturacion(conceptoFact, function(response) {

								if(response.data.mensajeError != undefined){
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: response.data.mensajeError,
								        type: 'error'
								      });
								}else{
									$log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });

											$scope.clienteConceptoFacturacion.descConceptoFacturacion = null;
											$scope.IsVisibleBotonGuardarConcepto = true;
											$scope.IsVisibleBotonActualizarConcepto = false;
											$scope.cargaInicialConceptoFacturacion();

								}
							},
							function(response) {
								$log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
							        type: 'error'
							      });
							});
						}
					}
			  });
			};


			// opcion del grid
			$scope.actualizarConceptoFacturacion = function(conceptoFact) {
				$scope.IsVisibleBotonGuardarConcepto = false;
				$scope.IsVisibleBotonActualizarConcepto = true;

				$scope.clienteConceptoFacturacion = angular.copy(conceptoFact);
			}


			// servicio que realiza la accion de actualizar
			$scope.actualizarConcepto = function() {
			  clienteFinalService.guardaConceptoFacturacion($scope.clienteConceptoFacturacion,function(response) {
				  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
					  bootbox.alert({
							title : "Error",
							message : "Ocurrio un error al realizar la el resguardo del concepto. Favor de intentarlo mas tarde",
							buttons : {
								ok : {
									label : 'Aceptar',
									className : 'center-block btn-primary'
								}
							},
							callback : function() {
								$scope.clienteConceptoFacturacion.descConceptoFacturacion = null;
								$scope.IsVisibleBotonGuardarConcepto = true;
								$scope.IsVisibleBotonActualizarConcepto = false;
								$scope.clienteConceptoFacturacion = {};
								$scope.cargaInicialConceptoFacturacion();
							}
						});
				  }else{
					  $scope.clienteConceptoFacturacion.descConceptoFacturacion = null;
					  $scope.IsVisibleBotonGuardarConcepto = true;
					  $scope.IsVisibleBotonActualizarConcepto = false;
					  $scope.clienteConceptoFacturacion = {};
					  $scope.cargaInicialConceptoFacturacion();
				  }
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});

			}

			$scope.cancelarActualizacion = function() {
				$scope.IsVisibleBotonGuardarConcepto = true;
				$scope.IsVisibleBotonActualizarConcepto = false;
				$scope.clienteConceptoFacturacion = {}
				 $scope.cargaInicialConceptoFacturacion();
			}

			function handleInput(e) {
				   var ss = e.target.selectionStart;
				   var se = e.target.selectionEnd;
				   e.target.value = e.target.value.toUpperCase();
				   e.target.selectionStart = ss;
				   e.target.selectionEnd = se;
				}

////////////////////    NOMINA CLIENTE   /////////////////////////////////////////////////////////
			  $scope.cargaInicialNominaCliente = function() {
				  clienteFinalService.cargaInicialNominaCliente(function(response) {
					  $scope.IsVisibleAgregarNomina = false;
					  $scope.gridNominaCliente = response.data.gridNominaCliente;
					  $scope.nominaClienteDto = response.data.nominaCliente;
					  $scope.listaCelulasDto = response.data.listaCelulasDto;
					  $scope.listaProductosNomina = response.data.listaProductosNomina;
					  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
					  $scope.listaPrestadoras = response.data.listaPrestadoras;
					  $scope.catPrestadorasServicio = response.data.catPrestadorasServicio;
					  $scope.catPrestadorasServicioFondo = response.data.catPrestadorasServicioFondo;
				  },function(response) {
						$log.error("error --> " + response);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
					        type: 'error'
					      });

					});
				};

				  $scope.validarAgregarNomina = function() {
					  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/existeFondo.json", $scope.clienteDto.idCliente).then(function(response) {

						  if(!response.data){
							  bootbox.alert({
									title : "Error",
									message : "Para agregar una nómina, es necesario tener un fondo. Favor de registrarlo en la sección 'FONDO'",
									buttons : {
										ok : {
											label : 'ACEPTAR',
											className : 'center-block btn-primary'
										}
									},
									callback : function() {

									}
								});

							  $scope.IsVisibleAgregarNomina = false;

						  }else{
							  $scope.cargaNominaCliente();
						  }

						}, function(data) {
							console.log("error cargaInicialDomicilio--> " + data);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error en el sistema. Favor de intentarlo nuevamente.',
						        type: 'error'
						      });
						});

				  };

			  $scope.cargaNominaCliente = function() {
				  clienteFinalService.cargaInicialNominaCliente(function(response) {
					  $scope.gridNominaCliente = response.data.gridNominaCliente;
					  $scope.nominaClienteDto = response.data.nominaCliente;
					  $scope.listaCelulasDto = response.data.listaCelulasDto;
					  $scope.listaProductosNomina = response.data.listaProductosNomina;
					  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
					  $scope.listaPrestadoras = response.data.listaPrestadoras;
					  $scope.catPrestadorasServicio = response.data.catPrestadorasServicio;
					  $scope.catPrestadorasServicioFondo = response.data.catPrestadorasServicioFondo;
				  },function(response) {
						$log.error("error --> " + response);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
					        type: 'error'
					      });

					});
				};

				$scope.selectPPP = function(valor){
					if(valor) {
	            		$scope.nominaClienteDto.requiereFactura = 1;
	            	} else {
	            		$scope.nominaClienteDto.requiereFactura = 0;
	            	}
				}
			  	$scope.guardarDatosNominaCliente = function(nominaClienteForm) {

			    	bootbox.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
								clienteFinalService.guardaNominaCliente($scope.nominaClienteDto, function(response) {

									if(response.data.mensajeError != undefined){
										$log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: response.data.mensajeError,
									        type: 'error'
									      });
									}else{

										$log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });


										if(nominaClienteForm){
											nominaClienteForm.$setPristine();
											nominaClienteForm.$setUntouched();
										}

										$location.path("/crm/actualiza-nomina");
//												 $scope.cargaInicialNominaCliente();
//												 $scope.nominaClienteDto = {};
//												 $scope.IsVisibleAgregarNomina = false;

									}
								},
								function(response) {
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
								        type: 'error'
								      });

								});
							}
						}
					});
			    };


			  $scope.editarNominaCliente = function(nomCliente) {

				  clienteFinalService.getNominaClienteById(nomCliente, function(response) {

					  if(response.data!=null){

						  $scope.nominaClienteDto = response.data.nominaCliente;

						  if($scope.nominaClienteDto.catProductoNomina.idCatGeneral == 306
								  || $scope.nominaClienteDto.catProductoNomina.idCatGeneral == 9949
								  || $scope.nominaClienteDto.catProductoNomina.idCatGeneral == 9950){
							  $scope.IsVisibleCampoSueldosSalarios = true;

							  $scope.nominaClienteDto.prestadoraServicioFondo = null;

						  }else{
							  $scope.IsVisibleCampoSueldosSalarios = false;
							  $scope.nominaClienteDto.comisionSs=null;

							  $scope.nominaClienteDto.prestadoraServicioFondo =  response.data.nominaCliente.prestadoraServicio;
							  $scope.nominaClienteDto.prestadoraServicio =  null;
						  }

						  $scope.tituloNominaCliente = "ACTUALIZAR NÓMINA"
						  $scope.IsVisibleBotoAgregar = false;
						  $scope.gridNominaCliente = response.data.gridNominaCliente;

						  $scope.listaCelulasDto = response.data.listaCelulasDto;
						  $scope.listaProductosNomina = response.data.listaProductosNomina;
						  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
						  $scope.listaPrestadoras = response.data.listaPrestadoras;
						  $scope.catPrestadorasServicio = response.data.catPrestadorasServicio;
						  $scope.IsVisibleAgregarNomina = true;
					  }else{
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde',
						        type: 'error'
						      });
					  }
					});
				}

			  $scope.eliminarNominaCliente = function(idNominaCliente) {

				  bootbox.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas eliminar la n\u00f3mina?",
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
								  clienteFinalService.eliminarNominaCliente(idNominaCliente, function(response) {
								  if(response.data.mensajeError != undefined && response.data.mensajeError != null){

									  $log.error("error --> " + response);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
									        type: 'error'
									      });
								  }else{
									  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
												 $scope.cargaInicialNominaCliente();
								  }

							  },function(response) {
									$log.error("error --> " + response);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
								        type: 'error'
								      });

								});
							}
						}
					});
				};


				$scope.getFondoDefault = function(esquemaNomina, nomCliente) {

					// 304 es PPP
					if(esquemaNomina.idCatGeneral == 304){
						clienteFinalService.getNominaClienteById(nomCliente,function(response) {
							$scope.nominaClienteDto = response.data.nominaCliente;
							$scope.nominaClienteDto.prestadoraServicioFondo = response.data.catPrestadorasServicioFondo[0];
						  },function(response) {
								$log.error("error --> " + response);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
							        type: 'error'
							      });

							});
					}

				}


///////////////////// Domicilio  ///////////////////////////////////////////

		  $scope.cargaInicialDomicilio = function() {
				$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/domicilio/cargaInicialDomicilio.json").then(
						function(response) {
							$scope.clienteDomicilioDto = response.data;
							$scope.clienteMedioContactoDto = response.data.clienteMedioContactoDto;
							$scope.clienteMedioContactoCEODto = response.data.clienteMedioContactoCEODto;
							if(response.data.clienteMedioContactoCEODto != undefined){
								$scope.clienteMedioContactoCEODto.fechaNacimiento = new Date(response.data.clienteMedioContactoCEODto.fechaNacimiento);
							}
						}, function(data) {
							console.log("error cargaInicialDomicilio--> " + data);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la carga inicial de domicilio, favor de intentarlo nuevamente.',
						        type: 'error'
						      });
						});
			};



		  $scope.obtenerEntidadFederativaXCP = function(codigoPostal) {
			  clienteFinalService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
		    		$scope.clienteDomicilioDto.municipios = response.data.municipios;
		    		if(response.data.domicilio != null){
		    		$scope.clienteDomicilioDto.domicilio.catMunicipios = response.data.domicilio.catMunicipios;
		    		$scope.clienteDomicilioDto.domicilio.idEntidadFederativa = response.data.domicilio.idEntidadFederativa;
		    		}else{
		    			$scope.clienteDomicilioDto.domicilio.catMunicipios = {};
			    		$scope.clienteDomicilioDto.domicilio.idEntidadFederativa = null;
		    		}

		    	},function(response){

				});
		    }

		  $scope.obtenerEntidadFederativaXCPComercial = function(codigoPostal) {
			  clienteFinalService.obtenerEntidadFederativaXCP(codigoPostal, function(response) {
		    		$scope.clienteDomicilioDto.municipiosDomicilioComercial = response.data.municipios;
		    		if(response.data.domicilio != null){
		    		$scope.clienteDomicilioDto.domicilioComercial.catMunicipios = response.data.domicilio.catMunicipios;
		    		$scope.clienteDomicilioDto.domicilioComercial.idEntidadFederativa = response.data.domicilio.idEntidadFederativa;
		    		}else{
		    			$scope.clienteDomicilioDto.domicilioComercial.catMunicipios = {};
			    		$scope.clienteDomicilioDto.domicilioComercial.idEntidadFederativa = null;
		    		}

		    	},function(response){

				});
		    }


		  $scope.guardarDomicilioCliente = function(domicilio) {

		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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

//						var data = {
//		                    'prestadoraServicioDto' : prestadora
//		                };
						clienteFinalService
									.guardarDomicilioCliente(domicilio ,function(response) {
										if(response.data.mensajeError != undefined){
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: response.data.mensajeError,
										        type: 'error'
										      });
										}else{
												$log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'Domicilio fiscal se ha guardado con \u00e9xito.',
											        type: 'success'
											      });

												 $scope.cargaInicialDomicilio();
										}
											},
											function(response) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de intentarlo nuevamente.',
											        type: 'error'
											      });

											});

						}
					}
				});
		    }

		  $scope.guardarDomicilioComercial = function(domicilio) {

		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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

//						var data = {
//		                    'prestadoraServicioDto' : prestadora
//		                };
						clienteFinalService
									.guardarDomicilioComercial(domicilio ,function(response) {
										if(response.data.mensajeError != undefined){
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: response.data.mensajeError,
										        type: 'error'
										      });
										}else{
												$log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'Domicilio comercial se ha guardado con \u00e9xito.',
											        type: 'success'
											      });

												 $scope.cargaInicialDomicilio();
										}
											},
											function(response) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de intentarlo nuevamente.',
											        type: 'error'
											      });

											});

						}
					}
				});
		    }

		  $scope.guardarClienteMedioContacto = function(medioContacto) {

		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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

//						var data = {
//		                    'prestadoraServicioDto' : prestadora
//		                };
							medioContacto.cliente = $scope.clienteDto;
						clienteFinalService
									.guardarClienteMedioContacto(medioContacto,function(response) {
										if(response.data.mensajeError != undefined){
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: response.data.mensajeError,
										        type: 'error'
										      });
										}else{
												$log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
											        type: 'success'
											      });

												$scope.cargaInicialDomicilio();
										}
											},
											function(response) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
											        type: 'error'
											      });

											});

						}
					}
				});
		    }



		  $scope.guardarClienteMedioContactoCEO = function(medioContacto) {

		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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

//						var data = {
//		                    'prestadoraServicioDto' : prestadora
//		                };
							medioContacto.cliente = $scope.clienteDto;
						clienteFinalService
									.guardarClienteMedioContactoCEO(medioContacto,function(response) {
										if(response.data.mensajeError != undefined){
											$log.error(response.status+ ' - '+ response.statusText);
											pinesNotifications.notify({
										        title: 'Error',
										        text: response.data.mensajeError,
										        type: 'error'
										      });
										}else{
												$log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
											        type: 'success'
											      });

												$scope.cargaInicialDomicilio();
										}
											},
											function(response) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
											        type: 'error'
											      });

											});

						}
					}
				});
		    }
		  ///////////////////////////////////////////////////////////////////////////




  /////////////////////////////////// Cuentas Bancarias //////////////////////////////

		  $scope.cargaInicialCuentasBancarias = function() {
				$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/cuentaBancaria/cargaInicialCuentasBancarias.json").then(
						function(response) {
							$scope.clienteCuentasBancarias = response.data;
							$scope.clienteCuentaBancaria.catBanco.idCatGeneral =-1;
							$scope.clienteCuentaBancaria.catTipoCuenta.idCatGeneral = -1;
							$scope.mostrarCuenta = false;
						}, function(data) {
							console.log("error cargaInicialDomicilio--> " + data);
							pinesNotifications.notify({
						        title: 'Error',
						        text: 'Ocurrio un error al realizar la carga inicial de domicilio, favor de intentarlo nuevamente.',
						        type: 'error'
						      });
						});
			};


			 $scope.guardarCuentaBancaria = function(cuenta) {

			    	bootbox
					.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
							cuenta.idCliente = $scope.clienteDto.idCliente;
							clienteFinalService
										.guardarCuentaBancaria(cuenta ,function(response) {
											if(response.data.mensajeError != undefined){
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: response.data.mensajeError,
											        type: 'error'
											      });
											}else{
													$log.debug('ok');
													pinesNotifications.notify({
												        title: 'Mensaje',
												        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
												        type: 'success'
												      });
													 $scope.clienteCuentaBancaria = {};
													 $scope.clienteCuentaBancaria.catBanco ={};
													 $scope.clienteCuentaBancaria.catTipoCuenta = {};
													 $scope.mostrarCuenta =false;
													$scope.cargaInicialCuentasBancarias();
											}
												},
												function(response) {
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
												        type: 'error'
												      });

												});

							}
						}
					});
			    }


			 $scope.editarCuentaBancaria = function(cuenta) {
				  $scope.clienteCuentaBancaria.numeroCuenta = cuenta.numeroCuenta;
				  $scope.clienteCuentaBancaria.esPrincipal = cuenta.esPrincipal;
				  $scope.clienteCuentaBancaria.clabeInterbancaria = cuenta.clabeInterbancaria;
				  $scope.clienteCuentaBancaria.numeroReferencia = cuenta.numeroReferencia;
				  $scope.clienteCuentaBancaria.catBanco.idCatGeneral = cuenta.catBanco.idCatGeneral;
				  $scope.clienteCuentaBancaria.catTipoCuenta.idCatGeneral = cuenta.catTipoCuenta.idCatGeneral
				  $scope.clienteCuentaBancaria.idClienteCuentaBancaria = cuenta.idClienteCuentaBancaria;
				  $scope.mostrarCuenta = true;

			  }

			 $scope.nuevaCuenta = function() {

				 $scope.clienteCuentaBancaria = {};
				 $scope.clienteCuentaBancaria.catBanco = {};
				 $scope.clienteCuentaBancaria.catTipoCuenta = {};
				 $scope.clienteCuentaBancaria.catBanco.idCatGeneral =-1;
				 $scope.clienteCuentaBancaria.catTipoCuenta.idCatGeneral = -1;
				 $scope.mostrarCuenta =true;

				};


			 $scope.eliminarCuentaBancaria = function(cuenta) {

			    	bootbox
					.confirm({
						title : "Confirmar acci&oacute;n",
						message : "¿Est\u00e1s seguro que deseas eliminar la cuenta bancaria?",
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
								clienteFinalService
										.eliminarCuentaBancaria(cuenta ,function(response) {
											if(response.data.mensajeError != undefined){
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: response.data.mensajeError,
											        type: 'error'
											      });
											}else{
													$log.debug('ok');
													pinesNotifications.notify({
												        title: 'Mensaje',
												        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
												        type: 'success'
												      });
													$scope.clienteCuentaBancaria = {};
													 $scope.clienteCuentaBancaria.catBanco ={};
													 $scope.clienteCuentaBancaria.catTipoCuenta = {};
													 $scope.mostrarCuenta =false;
													$scope.cargaInicialCuentasBancarias();
											}
												},
												function(response) {
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
												        type: 'error'
												      });

												});

							}
						}
					});
			    }

/////////////////////////////////// DATOS STP ////////////////////////////////////
  $scope.cargaInicialDatosStp = function() {
		$http.get(CONFIG.APIURL + "/clienteSeccionesCrm/datosStp/cargaInicialDatosStp.json").then(function(response) {
					$scope.clienteDto.prefijoSTP = response.data.prefijoStp;

				}, function(data) {
					console.log("error cargaInicialDatosStp--> " + data);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la carga inicial de Datos STP, favor de intentarlo nuevamente.',
				        type: 'error'
				      });
				});
	};

	$scope.guardarPrefijoStp = function(datosStpForm) {
		$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/datosStp/guardarPrefijoStp.json", $scope.clienteDto).then(function(response) {


			if(response.data.mensajeError != undefined){
				$log.error(response.status+ ' - '+ response.statusText);
				pinesNotifications.notify({
			        title: 'Error',
			        text: response.data.mensajeError,
			        type: 'error'
			      });
			}else{
              	$log.debug('ok');
					pinesNotifications.notify({
				        title: 'Mensaje',
				        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
				        type: 'success'
				      });


					if(datosStpForm){
						datosStpForm.$setPristine();
						datosStpForm.$setUntouched();
					}
			}

			$scope.cargaInicialDatosStp();

		}, function(data) {
			console.log("error guardarPrefijoStp--> " + data);
			pinesNotifications.notify({
		        title: 'Error',
		        text: 'Ocurrio un error al realizar el resguardo de datos STP, favor de intentarlo nuevamente.',
		        type: 'error'
		      });
		});
	}


/////////////////////////////////// Giro  //////////////////////////////////////////

			 $scope.cargaInicialActividad = function() {
					$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/actividad/cargaInicialActividad.json").then(
							function(response) {
								$scope.clienteActividad = response.data;
								$scope.clienteActividad.idGiroComercial = -1;
								$scope.clienteActividad.idSubGiroComercial = -1;
							}, function(data) {
								console.log("error cargaInicialActividad--> " + data);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al realizar la carga inicial de actividad, favor de intentarlo nuevamente.',
							        type: 'error'
							      });
							});
				};


				 $scope.obtenerSubgiro = function(idGiro) {
					 clienteFinalService.obtenerSubgiro(idGiro, function(response) {
				    		$scope.clienteActividad.catSubGiroComercial = response.data.catSubGiroComercial;
				    		$scope.clienteActividad.idSubGiroComercial = null;
				    	},function(response){

						});
				    }


				 $scope.guardarActividad = function(actividad) {

				    	bootbox
						.confirm({
							title : "Confirmar acci&oacute;n",
							message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
								actividad.cliente = $scope.clienteDto;
								clienteFinalService
											.guardarActividad(actividad ,function(response) {
												if(response.data.mensajeError != undefined){
													$log.error(response.status+ ' - '+ response.statusText);
													pinesNotifications.notify({
												        title: 'Error',
												        text: response.data.mensajeError,
												        type: 'error'
												      });
												}else{
														$log.debug('ok');
														pinesNotifications.notify({
													        title: 'Mensaje',
													        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
													        type: 'success'
													      });

														 $scope.reset();
														$scope.cargaInicialActividad();
												}
													},
													function(response) {
														$log.error(response.status+ ' - '+ response.statusText);
														pinesNotifications.notify({
													        title: 'Error',
													        text: 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
													        type: 'error'
													      });

													});

								}
							}
						});
				    }


				 $scope.eliminarActividad = function(actividad) {

				    	bootbox
						.confirm({
							title : "Confirmar acci&oacute;n",
							message : "¿Est\u00e1s seguro que deseas eliminar la informaci\u00f3n?",
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
								actividad.cliente = $scope.clienteDto;
								 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/actividad/eliminarActividad.json", actividad).then(
						                  function (response) {
						                  	$log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
											        type: 'success'
											      });

												$scope.cargaInicialActividad();
						                  },
						                  function (response) {
						                	  $log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: 'Ocurrio un error al guardar, favor de intentar nuevamente.',
											        type: 'error'
											      });
						                	  console.log("error --> " + data);
						                  });

								}
							}
						});
				    }

/////////////////////////////////////////////////////////////////////////////////////////

////////////// Productos ///////////////////////////////////////////////////////////////
				 $scope.cargaInicialProductos = function() {
						$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/productos/cargaInicialProductos.json").then(
								function(data) {
									$scope.clienteProductoDto = data.data;
								}, function(data) {
									console.log("error --> " + data);
								});
					};


					 $scope.guardarProducto = function (producto){
//						   producto.cliente = $scope.clienteDto;
						   var data = {
				                    'prestadoraServicioProducto' : producto,
				                    'cliente' : $scope.clienteDto
				                };
						  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/productos/guardarProducto.json", data).then(
				                  function (response) {
				                  	$log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										 $scope.cargaInicialProductos();

				                  },
				                  function (response) {
				                	  $log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentar nuevamente.',
									        type: 'error'
									      });
				                	  console.log("error --> " + data);
				                	  $scope.cargaInicialProductos();
				                  });
					  };


////////////////////////////////////////////////////////////////////////////////////////

					  ////////////////// Comisiones
	 $scope.seleccionarNominaComision = function(seleccion) {
						  $scope.nominaClienteComisionesDto = seleccion;
						  $scope.mostrarPanelComisiones = true;
						  $scope.mostrarPanelAgregarComision = false;
						  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridComisiones.json", seleccion).then(
									function(response) {
								$scope.gridNominaComisiones = response.data;
							  },function(response) {
									$log.error("error --> " + response);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
								        type: 'error'
								      });

								});
	}

	$scope.agregarComision = function() {
	  $scope.mostrarPanelAgregarComision = true;

//	  $scope.comision={};
	  if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 304){
		  $scope.comision.esquema.idCatGeneral = 2878;
	  }else if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 9949){
		  $scope.comision.esquema.idCatGeneral = 9949;
	  }else if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 9950){
		  $scope.comision.esquema.idCatGeneral = 9950;
	  }else{
		  $scope.comision.esquema = '';
	  }
	  $scope.comision.canalVenta ='';
	  $scope.comision.usuario = '';
	  $scope.comision.comisionPricing = null;
	  $scope.comision.comision = null;
	  $scope.comision.idNominaClienteComision = null;
	}

	$scope.cancelarAgregarComision = function() {
		  $scope.mostrarPanelAgregarComision = false;
	}

	$scope.editarComision = function(comision) {
		 if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 304){
			  $scope.comision.esquema.idCatGeneral = 2878;
		  }else if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 9949){
			  $scope.comision.esquema.idCatGeneral = 9949;
		  }else if($scope.nominaClienteComisionesDto.catProductoNomina.idCatGeneral == 9950){
			  $scope.comision.esquema.idCatGeneral = 9950;
		  }
		  $scope.mostrarPanelAgregarComision = true;
		  $scope.comision.esquema = comision.esquema;
		  $scope.comision.canalVenta = comision.canalVenta;
		  $scope.comision.usuario = comision.usuario;
		  $scope.comision.comisionPricing = comision.comisionPricing;
		  $scope.comision.comision = comision.comision;
		  if(comision.fechaInicioPago != null || comision.fechaInicioPago != undefined){
		  $scope.comision.fechaInicioPago = new Date(comision.fechaInicioPago);
		  $scope.comision.fechaFinPago = new Date(comision.fechaFinPago);
		  }
		  $scope.comision.idNominaClienteComision = comision.idNominaClienteComision;

		  if(comision.canalVenta.idCatGeneral == 64 || comision.canalVenta.idCatGeneral == 61){
				$scope.comision.formulaPricing = $scope.catFormulaPrincing[0];
				$scope.comision.formulaComision = '';
				$scope.comision.porcentajeComision = '';
			}

			if(comision.canalVenta.idCatGeneral == 60 || comision.canalVenta.idCatGeneral == 62 || comision.canalVenta.idCatGeneral == 63){
				$scope.comision.formulaComision = $scope.catFormulaComision[0];
				$scope.comision.formulaPricing = '';
				$scope.comision.porcentajeComision = '';
			}

			if(comision.canalVenta.idCatGeneral == 65){
				$scope.comision.formulaComision = $scope.catFormulaComision[0];
				$scope.comision.formulaPricing = '';
				$scope.comision.porcentajeComision = comision.porcentajeComision;
			}

		  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/obtenerUsuarioCanalVenta.json", comision.canalVenta.idCatGeneral).then(
					function(response) {
						if(response.data.length == 0){
							$scope.tipoCanalVentaUsuario = '';
							$scope.comision.usuario.idUsuario = '';
						}else{
						$scope.tipoCanalVentaUsuario = response.data;
						}

			  },function(response) {
					$log.error("error --> " + response);

				});
		}

	$scope.eliminarComision = function(comision) {

		bootbox
		.confirm({
			title : "Confirmar acci&oacute;n",
			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
					clienteFinalService.eliminarComision(comision.idNominaClienteComision ,function(response) {
		                  	$log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });

								$scope.mostrarPanelAgregarComision = false;
								 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridComisiones.json", $scope.nominaClienteComisionesDto.idNominaCliente).then(
											function(response) {
										$scope.gridNominaComisiones = response.data;
									  },function(response) {
											$log.error("error --> " + response);
										});
		                  },
		                  function (response) {
		                	  $log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al eliminar, favor de intentar nuevamente.',
							        type: 'error'
							      });
		                	  console.log("error --> " + data);
		                  });

				}
			}
		});
		}

	$scope.guardarComision = function(comision, comisionForm) {
	comision.nominaCliente = $scope.nominaClienteComisionesDto;
    	bootbox
		.confirm({
			title : "Confirmar acci&oacute;n",
			message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
					clienteFinalService.guardarComision(comision ,function(response) {
						if(response.data.mensajeError != undefined){
							$log.error(response.status+ ' - '+ response.statusText);
							pinesNotifications.notify({
						        title: 'Error',
						        text: response.data.mensajeError,
						        type: 'error'
						      });
						}else{
		                  	$log.debug('ok');
								pinesNotifications.notify({
							        title: 'Mensaje',
							        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
							        type: 'success'
							      });


								if(comisionForm){
									comisionForm.$setPristine();
									comisionForm.$setUntouched();
								}

								 $scope.mostrarPanelAgregarComision = false;
								 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridComisiones.json", $scope.nominaClienteComisionesDto.idNominaCliente).then(
											function(response) {
										$scope.gridNominaComisiones = response.data;
									  },function(response) {
											$log.error("error --> " + response);
										});
						}
		                  },
		                  function (response) {
		                	  $log.error(response.status+ ' - '+ response.statusText);
								pinesNotifications.notify({
							        title: 'Error',
							        text: 'Ocurrio un error al guardar, favor de intentar nuevamente.',
							        type: 'error'
							      });
		                	  console.log("error --> " + data);
		                  });

				}
			}
		});
		}


	$scope.cargaInicialClienteComisiones = function() {
		$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialComisiones.json").then(
				function(response) {
			  $scope.gridNominaCliente = response.data.gridNominaCliente;
			  $scope.nominaClienteDto = response.data.nominaCliente;
			  $scope.listaCelulasDto = response.data.listaCelulasDto;
			  $scope.listaProductosNomina = response.data.listaProductosNomina;
			  $scope.listaPrestadorasFondo = response.data.listaPrestadorasFondo;
			  $scope.listaPrestadoras = response.data.listaPrestadoras;
			  $scope.catEsquema = response.data.catEsquema;
			  $scope.catFormulaPrincing = response.data.catFormulaPrincing;
			  $scope.catFormulaComision = response.data.catFormulaComision;
			  $scope.tipoCanalVenta = response.data.tipoCanalVenta;
			  $scope.catPorcentajeComision = response.data.catProcentajeComision;
			  $scope.mostrarPanelComisiones = false;
			  $scope.mostrarPanelAgregarComision = false;
			  $scope.catEsquemaMaquila = response.data.catEsquemaMaquila;
			  $scope.catEsquemaMixto = response.data.catEsquemaMixto;
		  },function(response) {
				$log.error("error --> " + response);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
			        type: 'error'
			      });

			});
		}


		$scope.obtenerUsuarioCanalVenta = function(idCanalVenta){
					$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/obtenerUsuarioCanalVenta.json", idCanalVenta).then(
							function(response) {
								if(response.data.length == 0){
									$scope.tipoCanalVentaUsuario = '';
									$scope.comision.usuario = '';
								}else{
								$scope.tipoCanalVentaUsuario = response.data;
								}

								if(idCanalVenta == 64 || idCanalVenta == 61){
									$scope.comision.formulaPricing = $scope.catFormulaPrincing[0];
									$scope.comision.formulaComision = '';
									$scope.comision.porcentajeComision = '';
								}

								if(idCanalVenta == 60 || idCanalVenta == 62 || idCanalVenta == 63){
									$scope.comision.formulaComision = $scope.catFormulaComision[0];
									$scope.comision.formulaPricing = '';
									$scope.comision.porcentajeComision = '';
								}

								if(idCanalVenta == 65){
									$scope.comision.formulaComision = $scope.catFormulaComision[0];
									$scope.comision.formulaPricing = '';
								}
								$scope.comision.comision = null;
								$scope.comision.comisionPricing = null;
					  },function(response) {
							$log.error("error --> " + response);

						});

		}


		///////////////////////////////////


	  ////////////////// Honorarios
	 $scope.seleccionarNominaHonorarios = function(seleccion) {
		$scope.nominaClienteHonorariosDto = seleccion;
		$scope.mostrarPanelHonorarios = true;
		$scope.mostrarPanelAgregarHonorarios = false;
		$scope.mostrarPanelAgregarHonorariosSS = false;
		$scope.mostrarPanelAgregarHonorariosMixto = false;
		$scope.mostrarPanelAgregarHonorariosMaquila = false;
		$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridHonorarios.json", seleccion.idNominaCliente).then(
				function(response) {
			$scope.gridNominaHonorarios = response.data;
		  },function(response) {
				$log.error("error --> " + response);
				pinesNotifications.notify({
			        title: 'Error',
			        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
			        type: 'error'
			      });

			});
	}

	 $scope.agregarHonorario = function() {
		  $scope.mostrarPanelAgregarHonorarios = true;
		  $scope.limpiarHonorario();
		  $scope.honorario.formulaPPP =  $scope.catFormulaHonorario[0];
		  $scope.catFormulaFacturaAux = angular.copy($scope.catFormulaFactura);
		  if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 306){//ss
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = true;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;
		  }else if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9949){//maquila
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = true;
		  }else if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9950){//mixto
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = true;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;
		  }
		}

	 $scope.cancelarActualizarHonorario = function() {
		  $scope.mostrarPanelAgregarHonorarios = false;
		}

	 $scope.cargaInicialClienteHonorarios = function() {
			$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialHonorarios.json").then(
					function(response) {
				  $scope.mostrarPanelHonorarios = false;
				  $scope.gridNominaCliente = response.data.gridNominaCliente;
				  $scope.nominaClienteDto = response.data.nominaCliente;
				  $scope.listaCelulasDto = response.data.listaCelulasDto;
				  $scope.listaProductosNomina = response.data.listaProductosNomina;
				  $scope.catEsquema = response.data.catEsquema;
				  $scope.catFormulaHonorario = response.data.catFormulaHonorario;
				  $scope.catFormulaFactura = response.data.catFormulaFactura;
				  $scope.catTipoIVA = response.data.catTipoIVA;

				  $scope.catFormulaHonorarioMaquila = response.data.catFormulaHonorarioMaquila;
				  $scope.catFormulaHonorarioMixto = response.data.catFormulaHonorarioMixto;
				  $scope.catFormulaHonorarioSS = response.data.catFormulaHonorarioSS;

				  $scope.catTipoIVASs = response.data.catTipoIVASs;
				  $scope.catTipoIVAMixto = response.data.catTipoIVAMixto;
				  $scope.catFormulaFacturaSs = response.data.catFormulaFacturaSs;
				  $scope.catFormulaFacturaMixto = response.data.catFormulaFacturaMixto;
			  },function(response) {
					$log.error("error --> " + response);
					pinesNotifications.notify({
				        title: 'Error',
				        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
				        type: 'error'
				      });

				});
			}

	 $scope.guardarHonorario = function(honorario) {
			honorario.nominaCliente = $scope.nominaClienteHonorariosDto;
		    	bootbox
				.confirm({
					title : "Confirmar acci&oacute;n",
					message : "¿Est\u00e1s seguro que deseas guardar la informaci\u00f3n?",
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
							clienteFinalService.guardarHonorario(honorario ,function(response) {
								if(response.data.mensajeError != undefined){
									$log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: response.data.mensajeError,
								        type: 'error'
								      });
								}else{
				                  	$log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });
										 $scope.limpiarHonorario();
										 $scope.mostrarPanelAgregarHonorarios = false;
										  $scope.mostrarPanelAgregarHonorariosSS = false;
										  $scope.mostrarPanelAgregarHonorariosMixto = false;
										  $scope.mostrarPanelAgregarHonorariosMaquila = false;
										 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridHonorarios.json", $scope.nominaClienteHonorariosDto.idNominaCliente).then(
													function(response) {
												$scope.gridNominaHonorarios = response.data;
											  },function(response) {
													$log.error("error --> " + response);
												});
								}
				                  },
				                  function (response) {
				                	  $log.error(response.status+ ' - '+ response.statusText);
										pinesNotifications.notify({
									        title: 'Error',
									        text: 'Ocurrio un error al guardar, favor de intentar nuevamente.',
									        type: 'error'
									      });
				                	  console.log("error --> " + data);
				                  });

						}
					}
				});
				}

	 $scope.limpiarHonorario = function(){
		 $scope.honorario.honorarioPPP = '';
		  $scope.honorario.formulaPPP = '';
		  $scope.honorario.formulaFactura = '';
		  $scope.honorario.formulaTipoIva = '';

		  $scope.honorario.formulaPPPSs = '';
		  $scope.honorario.formulaPPPMixto = '';
		  $scope.honorario.formulaPPPMaquila = '';
		  $scope.honorario.honorarioPPPMixto ='';
		  $scope.honorario.honorarioPPPSs ='';
		  $scope.honorario.idNominaClienteHonorario = null;
//		  $scope.honorario.catTipoIVASs = undefined
//		  $scope.honorario.catTipoIVAMixto  = undefined
//		  $scope.honorario.catFormulaFacturaSs  = undefined;
//		  $scope.honorario.catFormulaFacturaMixto  = undefined;
	 }

	 $scope.editarHonorario = function(honorario) {
		 $scope.limpiarHonorario();
		 if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 306){//ss
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = true;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;
			  $scope.honorario.formulaPPPSs = honorario.formulaPPPSs;
			  $scope.honorario.honorarioPPPSs = honorario.honorarioPPPSs;
			  $scope.honorario.idNominaClienteHonorario = honorario.idNominaClienteHonorario;
			  $scope.honorario.formulaFactura = honorario.formulaFactura;
			  $scope.honorario.formulaTipoIva = honorario.formulaTipoIva;
		  }else if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9949){//maquila
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = true;
			  $scope.honorario.formulaPPPMaquila = honorario.formulaPPPMaquila;
			  $scope.honorario.idNominaClienteHonorario = honorario.idNominaClienteHonorario;
		  }else if($scope.nominaClienteHonorariosDto.catProductoNomina.idCatGeneral == 9950){//mixto
			  $scope.mostrarPanelAgregarHonorarios = false;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = true;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;
			  $scope.honorario.honorarioPPPMixto = honorario.honorarioPPPMixto;
			  $scope.honorario.formulaPPPMixto = honorario.formulaPPPMixto;
			  $scope.honorario.idNominaClienteHonorario = honorario.idNominaClienteHonorario;
			  $scope.honorario.formulaFactura = honorario.formulaFactura;
			  $scope.honorario.formulaTipoIva = honorario.formulaTipoIva;
		  }else{
			  $scope.mostrarPanelAgregarHonorarios = true;
			  $scope.mostrarPanelAgregarHonorariosSS = false;
			  $scope.mostrarPanelAgregarHonorariosMixto = false;
			  $scope.mostrarPanelAgregarHonorariosMaquila = false;

			  $scope.honorario.honorarioPPP = honorario.honorarioPPP;
			  if(honorario.ivaComercial !=null && honorario.ivaComercial != undefined){
				  $scope.honorario.ivaComercial = honorario.ivaComercial;
			  }
			  $scope.honorario.formulaPPP = honorario.formulaPPP;
			  $scope.honorario.formulaFactura = honorario.formulaFactura;
			  $scope.honorario.formulaTipoIva = honorario.formulaTipoIva;
			  $scope.honorario.idNominaClienteHonorario = honorario.idNominaClienteHonorario;

			  $scope.catFormulaFacturaAux = angular.copy($scope.catFormulaFactura);
//			  $scope.obtenerCatTipoIVAHonorario(honorario.formulaTipoIva.idCatGeneral);
		  }

		}

	 $scope.obtenerCatTipoIVAHonorario = function(idTipoIVA){
		 if(idTipoIVA == 5){
			 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/modificarCatalogoTipoIVA.json").then(
						function(response) {
					  $scope.catFormulaFactura = response.data.catFormulaFactura;
					  $scope.honorario.formulaFactura = $scope.catFormulaFactura[0];
				  },function(response) {
						$log.error("error --> " + response);
					});
		 }else{
			 $scope.honorario.formulaFactura = '';
			 $scope.catFormulaFactura = angular.copy($scope.catFormulaFacturaAux);
		 }
	 }

	 $scope.eliminarHonorario = function(honorario) {

			bootbox
			.confirm({
				title : "Confirmar acci&oacute;n",
				message : "¿Est\u00e1s seguro que deseas eliminar la informaci\u00f3n?",
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
						clienteFinalService.eliminarHonorario(honorario.idNominaClienteHonorario ,function(response) {
			                  	$log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });

									$scope.mostrarPanelAgregarHonorarios = false;
									  $scope.mostrarPanelAgregarHonorariosSS = false;
									  $scope.mostrarPanelAgregarHonorariosMixto = false;
									  $scope.mostrarPanelAgregarHonorariosMaquila = false;
									$scope.limpiarHonorario();
									$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/nominaCliente/cargaInicialGridHonorarios.json", $scope.nominaClienteHonorariosDto.idNominaCliente).then(
											function(response) {
										$scope.gridNominaHonorarios = response.data;
									  },function(response) {
											$log.error("error --> " + response);
										});
			                  },
			                  function (response) {
			                	  $log.error(response.status+ ' - '+ response.statusText);
									pinesNotifications.notify({
								        title: 'Error',
								        text: 'Ocurrio un error al eliminar, favor de intentar nuevamente.',
								        type: 'error'
								      });
			                	  console.log("error --> " + data);
			                  });

					}
				}
			});
			}

	 //////////////////////////




	 $scope.verDetalleNominaCliente = function(idNominaCliente){
		 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/detalleNominaCliente.json", idNominaCliente).then(
				 function(response){
					 $location.path('/crm/actualiza-nomina');
				 },function(error){
					 $log.error(response.status+ ' - '+ response.statusText);
						pinesNotifications.notify({
					        title: 'Error',
					        text: 'Ocurrio un error al ver el detalle de la nomina.',
					        type: 'error'
					      });
						console.log("error --> " + data);
				 }
		 );

	 }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////REPRESENTANTES LEGALES //////////////////////////////////////////

					$scope.cargaInicialRepresentanteLegal = function() {

						clienteFinalService.cargaInicialRepresentanteLegal(
										function(response) {
											$scope.gridRepresentantesLegales = response.data.gridRepresentantesLegales;
											$scope.IsVisibleFormularioRepresentante = false;
											$scope.IsVisibleDocumentosRepresentante = false;
											 $scope.IsVisibleRepresentanteArchivos = false;
											$scope.representanteLegalDto = {};
										},
										function(response) {
											$log.error("error --> " + response);
												pinesNotifications.notify({
														title : 'Error',
														text : 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
														type : 'error'
													});

										});
					};

					$scope.guardarRepresentanteLegal = function(representanteLegalForm) {
						clienteFinalService.guardarRepresentanteLegal(
										$scope.representanteLegalDto,
										function(response) {
											if (response.data.mensajeError != undefined
													|| response.data.mensajeError != null) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
															title : 'Error',
															text : response.data.mensajeError,
															type : 'error'
														});

											} else {
												$log.debug('ok');
												pinesNotifications.notify({
															title : 'Mensaje',
															text : 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
															type : 'success'
														});

												$scope.IsVisibleFormularioRepresentante = false;
												$scope.IsVisibleDocumentosRepresentante = false;
												if(representanteLegalForm){
													representanteLegalForm.$setPristine();
													representanteLegalForm.$setUntouched();
												}

												$scope.cargaInicialRepresentanteLegal();

											}
										},
										function(response) {
											$log.error("error --> " + response);
												pinesNotifications.notify({
														title : 'Error',
														text : 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
														type : 'error'
													});

											$scope.IsVisibleFormularioRepresentante = false;
											$scope.IsVisibleDocumentosRepresentante = false;
											$scope.cargaInicialRepresentanteLegal();

										});
					};

					$scope.eliminarRepresentanteLegal = function(representante) {
							bootbox.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro de eliminar al  representante legal?",
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
											clienteFinalService.eliminarRepresentanteLegal(
															representante,
															function(response) {

																if (response.data.mensajeError != undefined) {
																	$log.error(response.status+ ' - '+ response.statusText);
																	pinesNotifications.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});

																} else {
																	$log.debug('ok');
																	pinesNotifications.notify({
																				title : 'Mensaje',
																				text : 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
																				type : 'success'
																			});

																	$scope.IsVisibleFormularioRepresentante = false;
																	$scope.IsVisibleDocumentosRepresentante = false;
																	$scope.cargaInicialRepresentanteLegal();
																}
															},
															function(response) {
																$log.error(response.status+ ' - '+ response.statusText);
																	pinesNotifications.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

																$scope.IsVisibleFormularioRepresentante = false;
																$scope.IsVisibleDocumentosRepresentante = false;
																$scope.cargaInicialRepresentanteLegal();
															});
										}
									}
								});
					};

					// accion que carga los campos para su edicion
					$scope.actualizarRepresentanteLegal = function(representante) {

						$scope.tituloDinamicoLegal = 'ACTUALIZAR INFORMACIÓN REPRESENTANTE LEGAL';

						$scope.IsVisibleDocumentosRepresentante = false;

						$scope.representanteLegalDto = {};
						$scope.IsVisibleFormularioRepresentante = true;
						$scope.representanteLegalDto = angular.copy(representante);

						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;
						$scope.IsVisibleRepresentanteArchivos = false;

					}

					$scope.nuevoRepresentanteLegal = function(representanteLegalForm) {

						$scope.tituloDinamicoLegal = 'REGISTRO DE REPRESENTANTE LEGAL';

						$scope.representanteLegalDto = {};
						$scope.IsVisibleFormularioRepresentante = true;
						$scope.IsVisibleDocumentosRepresentante = false;

						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;
						$scope.IsVisibleRepresentanteArchivos = false;


						$scope.limpiarConcepto = function(formularioConcepto){
					    	  $scope.concepto = {};
					    	  if(formularioConcepto){
					    		  formularioConcepto.$setPristine();
					        	  formularioConcepto.$setUntouched();
//					        	  formularioConcepto.$submitted = false;
//					        	  $scope.$apply();
					    	  }

					      }

					}

					$scope.showDocumentosRepresentante = function(representante) {

						$scope.representanteLegalDto = angular.copy(representante);

						$scope.cargaInicialDocumentosClienteRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);

						$scope.IsVisibleFormularioRepresentante = false;
						$scope.IsVisibleDocumentosRepresentante = true;
						$scope.IsVisibleRepresentanteArchivos = false;

						// se cierran formularios de apoderados
						$scope.IsVisibleFormularioApoderado = false;
						$scope.IsVisibleDocumentosApoderado = false;


					}

					  $scope.cargaInicialDocumentosClienteRepresentanteLegal = function (id){
						  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/representanteLegal/obtenerDocumentosRepresentante.json", id).then(
									function(data) {
										$scope.documentosPrestadoraRepresentante = data.data;
									}, function(data) {
										console.log("error --> " + data);
									});
					  }

					  $scope.mostrarModalDocumentoRepresentante = function(itemDefinicionDocumento) {

						  	$scope.archivoPrestadora = {};
						  	itemDefinicionDocumento.idClienteRepresentanteLegal = $scope.representanteLegalDto.idGenericoRepresentanteLegal;

						  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);

					        $('#agregarDocumentoRepresentante').modal('show');

					        var fileElement = angular.element('#file_representante');
					        angular.element(fileElement).val(null);

					    };


				      	$scope.guardarDocumentoRepresentante = function(){

					    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined ||
					    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){

					    		  pinesNotifications.notify({
								        title: 'Error',
								        text: 'Es necesario adjuntar el documento',
								        type: 'error'
								      });

					    		  return;
					    	  }

					    	  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/representanteLegal/guardarDocumentosClienteRepresentante.json", $scope.itemDefinicionDocumento).then(
					                  function (response) {
					                	  $log.debug('ok');
											pinesNotifications.notify({
										        title: 'Mensaje',
										        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
										        type: 'success'
										      });

											$scope.cargaInicialDocumentosClienteRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);
											$('#agregarDocumentoRepresentante').modal('hide');
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

				      	$scope.eliminarDocumentoRepresentante = function(itemDefinicionDocumento){

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

											 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/representanteLegal/eliminarDocumentosRepresentante.json",itemDefinicionDocumento).then(
									                  function (response) {
									                	  $log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });

															$scope.cargaInicialDocumentosClienteRepresentanteLegal($scope.representanteLegalDto.idGenericoRepresentanteLegal);

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


				      	$scope.cargaInicialDocumentosCerKeyRepresentante = function (id){
							$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/representanteLegal/obtenerDoctosClienteDocumentoRepresentanteCerKey.json", id).then(
							         function(data) {
							   $scope.documentosPrestadoraRepCerKey = data.data;
								   }, function(data) {
							   console.log("error --> " + data);
							});
							}


				      	$scope.guardarDocumentoRepresentanteCerKey = function(){
							  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined ||
									$scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){

								  pinesNotifications.notify({
								  title: 'Error',
								  text: 'Es necesario adjuntar el documento',
								  type: 'error'
								  });
								 return;
								 }

					$http.post(CONFIG.APIURL + "/clienteSeccionesCrm/representanteLegal/guardarDocumentosClienteRepresentanteCerKey.json", $scope.itemDefinicionDocumento).then(
					  function (response) {
					    $log.debug('ok');
						pinesNotifications.notify({
						title: 'Mensaje',
						text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
						type: 'success'
						});

						$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
						var fileElement = angular.element('#fileRepresentanteCerKey');
						angular.element(fileElement).val(null);
						$('#agregarDocumentoRepresentanteCerKey').modal('hide');
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

				      	$scope.showDocumentosCerKeyRepresentante = function(archivo, representanteLegalForm){
							$scope.representanteLegalDto = angular.copy(archivo);
							$scope.IsVisibleRepresentanteArchivos = true;
							$scope.IsVisibleFormularioApoderado = false;
							$scope.IsVisibleDocumentosApoderado = false;
							$scope.IsVisibleDocumentosRepresentante = false;
							$scope.IsVisibleFormularioRepresentante = false;

							if(representanteLegalForm){
								representanteLegalForm.$setPristine();
								representanteLegalForm.$setUntouched();
							}

							$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
						}

				      	$scope.mostrarModalDocumentoRepresentanteCerKey = function(itemDefinicionDocumento) {
				      		$scope.tipoDoc = angular.lowercase(itemDefinicionDocumento.definicion.nombreDocumento);
							$scope.archivoPrestadora = {};

						  	itemDefinicionDocumento.idClienteRepresentanteLegal = $scope.representanteLegalDto.idGenericoRepresentanteLegal;
  							$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);

								$('#agregarDocumentoRepresentanteCerKey').modal('show');
							 };


								$scope.eliminarDocumentoRepresentanteCerKey = function(documento) {
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
													clienteFinalService.eliminarDocumentoRepresentanteCerKey(documento, function(response) {

														if(response.data.mensajeError != undefined){
															$log.error(response.status+ ' - '+ response.statusText);
															pinesNotifications.notify({
														        title: 'Error',
														        text: response.data.mensajeError,
														        type: 'error'
														      });

														}else{
															$log.debug('ok');
															pinesNotifications.notify({
														        title: 'Mensaje',
														        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
														        type: 'success'
														      });

															$scope.cargaInicialDocumentosCerKeyRepresentante($scope.representanteLegalDto.idGenericoRepresentanteLegal);
															var fileElement = angular.element('#fileRepresentanteCerKey');
													        angular.element(fileElement).val(null);
														}
													},
													function(response) {
														$log.error(response.status+ ' - '+ response.statusText);
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

								$scope.guardarRepresentanteLegalContrasenia = function() {
									clienteFinalService.guardarContraseniaCerKeyRepresentanteLegal($scope.representanteLegalDto,function(response) {
										  if(response.data.mensajeError != undefined || response.data.mensajeError!=null){
											  $log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
											        title: 'Error',
											        text: response.data.mensajeError,
											        type: 'error'
											      });

										  }else{
											  $log.debug('ok');
												pinesNotifications.notify({
											        title: 'Mensaje',
											        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
											        type: 'success'
											      });

												clienteFinalService.cargaInicialRepresentanteLegal(
														function(response) {
															$scope.gridRepresentantesLegales = response.data.gridRepresentantesLegales;
															$scope.IsVisibleFormularioRepresentante = false;
															$scope.IsVisibleDocumentosRepresentante = false;
														},
														function(response) {
															$log.error("error --> " + response);
																pinesNotifications.notify({
																		title : 'Error',
																		text : 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
																		type : 'error'
																	});

														});
										  }
									  },function(response) {
											$log.error("error --> " + response);
											pinesNotifications.notify({
										        title: 'Error',
										        text: 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
										        type: 'error'
										      });
										});
									}



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////APODERADOS LEGALES //////////////////////////////////////////

					$scope.cargaInicialApoderadoLegal = function() {
						clienteFinalService.cargaInicialApoderadoLegal(
										function(response) {
											$scope.gridApoderadosLegales = response.data.gridApoderadosLegales;
											$scope.catFacultades = response.data.catFacultades;
											$scope.IsVisibleFormularioApoderado = false;
											$scope.IsVisibleDocumentosApoderado = false;
											$scope.apoderadoLegalDto = {};
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications.notify({
														title : 'Error',
														text : 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
														type : 'error'
													});

										});
					};


					$scope.guardarApoderadoLegal = function(apoderadoLegalForm) {
						clienteFinalService.guardarApoderadoLegal($scope.apoderadoLegalDto,function(response) {
											if (response.data.mensajeError != undefined|| response.data.mensajeError != null) {
												$log.error(response.status+ ' - '+ response.statusText);
												pinesNotifications.notify({
															title : 'Error',
															text : response.data.mensajeError,
															type : 'error'
														});

											} else {
												$log.debug('ok');
												pinesNotifications.notify({
															title : 'Mensaje',
															text : 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
															type : 'success'
														});

												$scope.IsVisibleFormularioApoderado = false;
												$scope.IsVisibleDocumentosApoderado = false;

												if(apoderadoLegalForm){
													apoderadoLegalForm.$setPristine();
													apoderadoLegalForm.$setUntouched();
												}

												$scope.cargaInicialApoderadoLegal();

											}
										},
										function(response) {
											$log.error("error --> " + response);
											pinesNotifications.notify({
														title : 'Error',
														text : 'Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde.',
														type : 'error'
													});

											$scope.IsVisibleFormularioApoderado = false;
											$scope.IsVisibleDocumentosApoderado = false;
											$scope.cargaInicialApoderadoLegal();

										});
					};

					$scope.eliminarApoderadoLegal = function(apoderado) {
						bootbox.confirm({
									title : "Confirmar acci&oacute;n",
									message : "¿Est\u00e1s seguro de eliminar al  apoderado legal ?",
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
											clienteFinalService.eliminarApoderadoLegal(apoderado,function(response) {

																if (response.data.mensajeError != undefined) {
																	$log.error(response.status+ ' - '+ response.statusText);
																	pinesNotifications.notify({
																				title : 'Error',
																				text : response.data.mensajeError,
																				type : 'error'
																			});

																} else {
																	$log.debug('ok');
																	pinesNotifications.notify({
																				title : 'Mensaje',
																				text : 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
																				type : 'success'
																			});

																	$scope.IsVisibleFormularioApoderado = false;
																	$scope.IsVisibleDocumentosApoderado = false;
																	$scope.cargaInicialApoderadoLegal();
																}
															},
															function(response) {
																$log.error(response.status+ ' - '+ response.statusText);
																pinesNotifications.notify({
																			title : 'Error',
																			text : 'Ocurrio un error al guardar, favor de intentarlo más tarde.',
																			type : 'error'
																		});

																$scope.IsVisibleFormularioApoderado = false;
																$scope.IsVisibleDocumentosApoderado = false;
																$scope.cargaInicialApoderadoLegal();
															});
										}
									}
								});
};

				// accion que carga los campos para su edicion
				$scope.actualizarApoderadoLegal = function(apoderado) {
					$scope.tituloDinamicoLegal = 'ACTUALIZAR INFORMACIÓN APODERADO LEGAL';

					$scope.IsVisibleDocumentosApoderado = false;

					$scope.apoderadoLegalDto = {};
					$scope.IsVisibleFormularioApoderado = true;
					$scope.apoderadoLegalDto = angular.copy(apoderado);

					// se cierran formularios de representante
					$scope.IsVisibleFormularioRepresentante = false;
					$scope.IsVisibleDocumentosRepresentante = false;

				}

				$scope.nuevoApoderadoLegal = function() {
					$scope.tituloDinamicoLegal = 'REGISTRO DE APODERADO LEGAL';

					$scope.apoderadoLegalDto = {};
					$scope.IsVisibleFormularioApoderado = true;
					$scope.IsVisibleDocumentosApoderado = false;

					// se cierran formularios de representante
					$scope.IsVisibleFormularioRepresentante = false;
					$scope.IsVisibleDocumentosRepresentante = false;

				}

				$scope.showDocumentoApoderado = function(apoderado) {

					$scope.apoderadoLegalDto = angular.copy(apoderado);

					$scope.cargaInicialDocumentosClienteApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);

					$scope.IsVisibleDocumentosApoderado = true;
					$scope.IsVisibleFormularioApoderado = false;

					// se cierran formularios de representante
					$scope.IsVisibleFormularioRepresentante = false;
					$scope.IsVisibleDocumentosRepresentante = false;

				}

				  $scope.cargaInicialDocumentosClienteApoderadoLegal = function (id){
					  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/apoderadoLegal/obtenerDocumentosApoderado.json", id).then(
								function(data) {
									$scope.documentosPrestadoraApoderado = data.data;
								}, function(data) {
									console.log("error --> " + data);
								});
				  }

				  $scope.mostrarModalDocumentoApoderado = function(itemDefinicionDocumento) {

					  	$scope.archivoPrestadora = {};
					  	itemDefinicionDocumento.idClienteApoderadoLegal = $scope.apoderadoLegalDto.idGenericoApoderadoLegal;
					  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);

				        $('#agregarDocumentoApoderado').modal('show');

				        var fileElement = angular.element('#file_apoderado');
				        angular.element(fileElement).val(null);

				    };

				      $scope.guardarDocumentoApoderado = function(){

				    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined ||
				    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){

				    		  pinesNotifications.notify({
							        title: 'Error',
							        text: 'Es necesario adjuntar el documento',
							        type: 'error'
							      });

				    		  return;
				    	  }

				    	  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/apoderadoLegal/guardarDocumentosClienteApoderado.json", $scope.itemDefinicionDocumento).then(
				                  function (response) {
				                	  $log.debug('ok');
										pinesNotifications.notify({
									        title: 'Mensaje',
									        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
									        type: 'success'
									      });

										$scope.cargaInicialDocumentosClienteApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);
										$('#agregarDocumentoApoderado').modal('hide');
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

				    $scope.eliminarDocumentoApoderado = function(itemDefinicionDocumento){

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

										 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/apoderadoLegal/eliminarDocumentosApoderado.json",itemDefinicionDocumento).then(
								                  function (response) {
								                	  $log.debug('ok');
														pinesNotifications.notify({
													        title: 'Mensaje',
													        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
													        type: 'success'
													      });

														$scope.cargaInicialDocumentosClienteApoderadoLegal($scope.apoderadoLegalDto.idGenericoApoderadoLegal);

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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////    DOCUMENTOS CLIENTE //////////////////////////////////////////////////////////////

				  $scope.cargaInicialDocumentosCliente = function (){
					  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/documentos/obtenerDocumentosCliente.json").then(
								function(data) {
									$scope.documentosCliente = data.data;
								}, function(data) {
									console.log("error --> " + data);
								});
				  }


				  $scope.guardarDocumentoCliente = function(){

			    	  if($scope.itemDefinicionDocumento == undefined || $scope.itemDefinicionDocumento.documentoNuevo === undefined ||
			    			  $scope.itemDefinicionDocumento.documentoNuevo.nombreArchivo === undefined){

			    		  pinesNotifications.notify({
						        title: 'Error',
						        text: 'Es necesario adjuntar el documento',
						        type: 'error'
						      });

			    		  return;
			    	  }

			    	  $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/documentos/guardarDocumentosCliente.json", $scope.itemDefinicionDocumento).then(
			                  function (response) {
			                	  $log.debug('ok');
									pinesNotifications.notify({
								        title: 'Mensaje',
								        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
								        type: 'success'
								      });

									$scope.cargaInicialDocumentosCliente();

									$('#agregarDocumentoCliente').modal('hide');
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

				  $scope.eliminarDocumentoCliente = function(itemDefinicionDocumento){

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

										 $http.post(CONFIG.APIURL + "/clienteSeccionesCrm/documentos/eliminarDocumentosCliente.json",itemDefinicionDocumento).then(
								                  function (response) {
								                	  $log.debug('ok');
														pinesNotifications.notify({
													        title: 'Mensaje',
													        text: 'La operaci\u00f3n se complet\u00f3 con \u00e9xito.',
													        type: 'success'
													      });

														$scope.cargaInicialDocumentosCliente();

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

				  $scope.mostrarModalDocumentoCliente = function(itemDefinicionDocumento) {
					  	$scope.archivoPrestadora = {};

					  	$scope.itemDefinicionDocumento = angular.copy(itemDefinicionDocumento);
				        $('#agregarDocumentoCliente').modal('show');

				        var fileElement = angular.element('#file_prestadora');
				        angular.element(fileElement).val(null);


				    };


//////////////////////////////////////////////////////////////////////////////
///////////////////////// DESCARGA DE  TODO LOS DOCUMENTOS, METEODO GENERICO /////////////////////////////

		      	$scope.descargarDocumentoByIdCMS = function(idCMS){


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

				  $scope.fileChangedDocPrestadora = function (documento) {

			          var lstArchivos = documento.files;
			          var val = lstArchivos[0].name.toLowerCase();



			          var regex = new RegExp("(.*?)\.(pdf|docx|png|jpg)$");

			          if (!(regex.test(val))) {
			              $(this).val('');
			              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.pdf, .docx, .png, y/o .jpg </b>";
			              pinesNotifications.notify({
						        title: 'Error',
						        text: $scope.mensaje,
						        type: 'error'
						      });
			          }else if (lstArchivos[0].size > 26214400
) {
			              $scope.mensaje = "El archivo excede el límite  de " + (26214400 / 1024 / 1024) + "MB";
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

			                  $scope.itemDefinicionDocumento.documentoNuevo = documento;
			              }

			              reader.readAsDataURL(lstArchivos[0]);

			          }

			      };

			      $scope.fileChangedFielCsd = function (documento) {
			          var lstArchivos = documento.files;

			          var val = lstArchivos[0].name.toLowerCase();
			          var regex = new RegExp("(.*?)\.(key|cer)$");

			          if (!(regex.test(val))) {
			              $(this).val('');
			              $scope.mensaje = "La extensión del archivo no es correcta, solo se permiten archivos con extensión <b>.key y/o .cer</b>";
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
			            	  var documento = {};

			            	  documento.mimeType = reader.result.substr(0,reader.result.indexOf(',')+1);
			                  documento.archivo = reader.result.substr(reader.result.indexOf(',') + 1);
			                  documento.nombreArchivo = lstArchivos[0].name;
			                  documento.tamanioArchivo = lstArchivos[0].size;

			                  $scope.itemDefinicionDocumento.documentoNuevo = documento;
			              }
			              reader.readAsDataURL(lstArchivos[0]);
			          }
			      }

					  ///////////////////////////////////

			    $scope.limpiarSeleccion = function(seleccion) {
			    	if(seleccion ==="22"){
			    		$scope.clienteDto.nombre = null;
			    		$scope.clienteDto.apellidoPaterno = null;
			    		$scope.clienteDto.apellidoMaterno = null;
			    		$scope.clienteDto.rfc = null;
			    	}else{
			    		$scope.clienteDto.razonSocial = null;
			    		$scope.clienteDto.rfc = null;
			    	}
			    };

			    $scope.cancelar = function() {
			    	location.href=CONFIG.APIURL+"#/crm/clientes";
			    }

			    function limpiarNumero(obj) {
			    	  /* El evento "change" sólo saltará si son diferentes */
			    	  obj.value = obj.value.replace(/\D/g, '');
			    }


			    $scope.reset = function() {
			    	$scope.clienteActividad = {};
			      }


			    $scope.calculaConceptoYFactura  = function(){

			    	$scope.concepto ={};
			    	$scope.montosFactura = {};
			    	$scope.concepto.cantidad=1;

					//calculo de la factura
					//Honorario DISPERSIÓN PPP * HONORARIO PACTADO
					 $scope.montosFactura.honorario =  parseFloat($scope.ejemploPPP.montoPPP) * parseFloat($scope.honorario.honorarioPPP) / 100;

					 //Formula IVA Agregamos el iva comercial
					 if($scope.honorario.formulaTipoIva.clave === 'H2'){ //IVA * MONTO DISPERSIÓN + HONORARIO PACTADO
						 $scope.montosFactura.iva = (parseFloat($scope.ejemploPPP.montoPPP) + $scope.montosFactura.honorario) * parseFloat((parseFloat($scope.honorario.ivaComercial) / 100).toFixed(2));
					 }else if($scope.honorario.formulaTipoIva.clave === 'H3'){//IVA * HONORARIO PACTADO
						 $scope.montosFactura.iva = $scope.montosFactura.honorario * parseFloat((parseFloat($scope.honorario.ivaComercial) / 100).toFixed(2));
					 }else if($scope.honorario.formulaTipoIva.clave === 'H4'){// NO APLICA
						 $scope.montosFactura.iva = parseFloat(0);
					 }

					 //Formula Factura
					 if($scope.honorario.formulaFactura.clave === 'H5'){ //DISPERSIÓN PPP + HONORARIO PACTADO + IVA
						 $scope.montosFactura.montoFactura = (parseFloat($scope.ejemploPPP.montoPPP)+ $scope.montosFactura.honorario  + $scope.montosFactura.iva );
					 }else if($scope.honorario.formulaFactura.clave === 'H6'){//HONORARIO PPP + IVA
						 $scope.montosFactura.montoFactura = ( $scope.montosFactura.honorario +   $scope.montosFactura.iva );
					 }

					 $scope.montosFactura.subtotal= parseFloat(parseFloat($scope.montosFactura.montoFactura / parseFloat(1.16)).toFixed(2));
					 $scope.concepto.precioUnitario = parseFloat(parseFloat($scope.montosFactura.subtotal).toFixed(2));
					 $scope.concepto.importe =  $scope.concepto.cantidad *  $scope.concepto.precioUnitario;


					 $scope.concepto.ivaTrasladado16Monto = parseFloat(parseFloat($scope.concepto.precioUnitario * parseFloat(0.16)).toFixed(2));

			    }


  });
