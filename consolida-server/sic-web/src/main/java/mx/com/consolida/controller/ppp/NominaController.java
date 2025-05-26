package mx.com.consolida.controller.ppp;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.TimbradoColaboradoresDto;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.FormulaFacturaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.service.interfaz.ClienteBO;
import mx.com.consolida.crm.service.interfaz.ClienteSeccionesBO;
import mx.com.consolida.crm.service.interfaz.NominaClienteBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatGenericoDTO;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dto.FacturaMsDTO;
import mx.com.consolida.ppp.dto.HistoricoNominaDto;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.dto.PppColaboradorEstatusDto;
import mx.com.consolida.ppp.dto.ValidaCreacionNominaDto;
import mx.com.consolida.ppp.service.interfaz.ColaboradorPPPBO;
import mx.com.consolida.ppp.service.interfaz.FacturaBO;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.ppp.service.interfaz.NominaComplementoBO;
import mx.com.consolida.ppp.service.interfaz.SerieFolioBO;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.documento.DocumentoMSDto;
import mx.com.facturacion.factura.CatMunicipioDTO;
import mx.com.facturacion.factura.ClienteDTO;
import mx.com.facturacion.factura.ComplementoNominaDto;
import mx.com.facturacion.factura.ConceptoDTO;
import mx.com.facturacion.factura.ConceptoFacturaDTO;
import mx.com.facturacion.factura.DomicilioDTO;
import mx.com.facturacion.factura.EmpresaDTO;
import mx.com.facturacion.factura.FacturaDTO;
import mx.com.facturacion.factura.ImpuestoDTO;
import mx.com.facturacion.factura.ImpuestoFacturaDTO;
import mx.com.facturacion.factura.Numero_Letras;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class NominaController  extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(NominaController.class);

	@Autowired
	private SerieFolioBO serieBO;

	@Autowired
	private CatalogoDao catalogoDao;

	@Autowired
	private CatalogoBO catBo;

	@Autowired
	private NominaBO nominaBO;

	@Autowired
	private NominaClienteBO nominaClienteBO;

	@Autowired
	private NominaComplementoBO nominaComplementoBO;

	@Autowired
	private ColaboradorPPPBO colaboradorPPPBO;

	@Autowired
	private FacturaBO facturaBO;

	@Autowired
	private PrestadoraServicioBO prestadoraServicioBO;

	@Autowired
	private ClienteBO clienteBO;

	@Autowired
	private ClienteSeccionesBO clienteSeccionesBO;

//	@Value("${urlZuul}")
//	private String urlZuulServer;
	@Value("${url.ms.base}")
	private String urlBase;
	@Value("${url.factura.service}")
	protected String facturaService;
	@Value("${url.nomina.service}")
	protected String nominaService;
	@Autowired
	private UsuarioBO usuarioBO;

	@RequestMapping(value = "/nominas/cargaInicialNomina")
	@ResponseBody
	public Map<String, Object> cargaInicialNomina(Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {
				Long idCelula = usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario());
				String nombreRol = getUser().getRol().getNombre();

				if(nombreRol != null && RolUsuarioENUM.NOMINISTA.getClave().equalsIgnoreCase(nombreRol)) {
					dataReturn.put("gridNominaCliente", nominaBO.listaClientesNomina(RolUsuarioENUM.NOMINISTA.getId(), getUser().getIdUsuario()));
				}else {

					//La celula puede venir null en caso de los usuarios generales
					dataReturn.put("gridNominaCliente", nominaBO.listaClientesNominaByCelula(idCelula));
				}


				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/nominas/cargaInicialNominaCatalogos")
	@ResponseBody
	public Map<String, Object> cargaInicialNominaCatalogos(Model model) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();

		try {

			dataReturn.put("comboTipoComprobante", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_TIPO_COMPROBANTE.getCve()));
			dataReturn.put("comboMetodoPago", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.METODO_PAGO.getCve()));
			dataReturn.put("comboFormaPago", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_FORMA_PAGO.getCve()));
			dataReturn.put("comboMoneda", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_MONEDA.getCve()));
			dataReturn.put("comboRegimenFiscal", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_REGIMEN_FISCAL.getCve()));
			dataReturn.put("comboUnidadFactura", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_UNI_FACTURA.getCve()));
			dataReturn.put("comboUsoCFDI", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_USO_CFDI.getCve()));
			dataReturn.put("comboSerie", serieBO.listaCatSerie());

			return dataReturn;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}


	@RequestMapping(value = "/nominas/detalleNominasClienteByIdCliente")
	@ResponseBody
	public List<NominaClienteDto> detalleNominasClienteByIdCliente(@RequestBody Long idCliente) throws BusinessException {

		try {
			    List<NominaClienteDto> nominas = null;

				String nombreRol = getUser().getRol().getNombre();

				if(nombreRol != null && RolUsuarioENUM.NOMINISTA.getClave().equalsIgnoreCase(nombreRol)) {
					//Si es nominista se manda el usuario nominista
					nominas = nominaClienteBO.listaNominaCliente(idCliente, getUser().getIdUsuario());
				}else {
					//Si es nominista se manda el usuario nominista
					nominas = nominaClienteBO.listaNominaCliente(idCliente, null);
				}


			    if(nominas!=null && !nominas.isEmpty()) {
			    	return nominas;
			    }else {
			    	return Collections.emptyList();
			    }


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasClienteByIdCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/guardarNominaPpp")
	@ResponseBody
	public MensajeDTO guardaNominaPpp(@RequestBody NominaDto nominaDto) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();
		Long fechaInicio = null;
		Long fechaFin = null;

		try {

			if(nominaDto == null) {
				LOGGER.error("Ocurrio un error en guardaNominaPpp,  nominaDto viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar. Favor de intentarlo mas tarde");
				return mensajeDTO;
			}

			if (nominaDto.getNominaClienteDto()== null
					|| (nominaDto.getNominaClienteDto()!= null && nominaDto.getNominaClienteDto().getIdNominaCliente() == null)
					|| nominaDto.getFechaInicioNomina() == null
					|| nominaDto.getFechaFinNomina() == null) {
				throw new BusinessException("", "");
			}


			if(nominaDto.getFechaInicioNomina() != null && nominaDto.getFechaFinNomina() != null) {
				Format formatter = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				fechaInicio = df.parse(formatter.format(nominaDto.getFechaInicioNomina() )).getTime();
				fechaFin = df.parse(formatter.format(nominaDto.getFechaFinNomina())).getTime();

				if (fechaFin < fechaInicio) {
					throw new BusinessException("", "");
				} else if (fechaInicio > fechaFin) {
					throw new BusinessException("", "");
				}
			}

			if(!nominaBO.guardarNomina(nominaDto, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en guardaNominaPpp - guardarNomina");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
				return mensajeDTO;
			}



		}catch (BusinessException be){

			if (nominaDto == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar y seleccionar alguna opcion");
				return mensajeDTO;
			}

			if (nominaDto.getNominaClienteDto()== null
					|| (nominaDto.getNominaClienteDto()!= null && nominaDto.getNominaClienteDto().getIdNominaCliente() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar 'Grupo'");
				return mensajeDTO;
			}

			if (nominaDto.getFechaInicioNomina() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha inicio n\u00f3mina'");
				return mensajeDTO;
			}

			if (nominaDto.getFechaFinNomina() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha inicio n\u00f3mina'");
				return mensajeDTO;
			}

			if(fechaInicio > fechaFin) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("'Fecha inicio de n\u00f3mina' no puede ser mayor a 'Fecha fin de n\u00f3mina'.");
				return mensajeDTO;
			}

			if(fechaFin < fechaInicio) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("'Fecha fin de n\u00f3mina' no puede ser menor a 'Fecha inicio de n\u00f3mina'.");
				return mensajeDTO;
			}


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaPpp ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}

		return mensajeDTO;
	}

	@RequestMapping(value = "/nominas/getNominasByIdCliente")
	@ResponseBody
	public Map<String, Object> getNominasByIdCliente(@RequestBody Long idCliente, Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {

				dataReturn.put("catNominaCliente", nominaClienteBO.listaNominaCliente(idCliente));
				return dataReturn;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasByIdCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/nominas/listaNominaEnProceso")
	@ResponseBody
	public Map<String, Object> listaNominaEnProceso(@RequestBody Long idCliente, Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {

			String nombreRol = getUser().getRol().getNombre();

			if(nombreRol != null && RolUsuarioENUM.NOMINISTA.getClave().equalsIgnoreCase(nombreRol)) {
				//Si es nominista se manda el usuario nominista
				dataReturn.put("gridNominasProceso", nominaBO.listaNominaEnProcesoByIdCliente(idCliente , getUser().getIdUsuario()));
			}else{
				//Si no es nominista se manda null el usuario
				dataReturn.put("gridNominasProceso", nominaBO.listaNominaEnProcesoByIdCliente(idCliente , null));
			}

				return dataReturn;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaEnProcesoByIdCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	/**Nominas que se pueden complementar*/

	@RequestMapping(value = "/nominas/listaNominaAcomplementar")
	@ResponseBody
	public Map<String, Object> listaNominaAcomplementar(@RequestBody Long idCliente, Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {

			String nombreRol = getUser().getRol().getNombre();

			if(nombreRol != null && RolUsuarioENUM.NOMINISTA.getClave().equalsIgnoreCase(nombreRol)) {
				//Si es nominista se manda el usuario nominista
				dataReturn.put("gridNominasProceso", nominaBO.listaNominaAcomplementar(idCliente , getUser().getIdUsuario()));
			}else{
				//Si no es nominista se manda null el usuario
				dataReturn.put("gridNominasProceso", nominaBO.listaNominaAcomplementar(idCliente , null));
			}

				return dataReturn;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaEnProcesoByIdCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/nominas/getDatosNominaByIdNomina")
	@ResponseBody
	public Map<String, Object> getDatosNominaByIdNomina(@RequestBody Long idNomina) throws BusinessException {

		try {
			NominaDto nomina = nominaBO.getDatosNominaByIdNomina(idNomina);
			Map<String, Object> dataReturn = new HashMap<>();
			dataReturn.put("nominaDto", nominaBO.getDatosNominaByIdNomina(idNomina));
			dataReturn.put("comboConceptoFacturaCrm", catalogoDao.listaConceptoFacturaCrmByIdCliente(nomina.getClienteDto().getIdCliente()));
			dataReturn.put("comboPac", catBo.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(CatMaestroEnum.PROVEEDOR_TIMBRADO.getCve()));

			return dataReturn;

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
	}


	@RequestMapping(value = "/nominas/getDatosNominaByIdNominaComplementaria")
	@ResponseBody
	public Map<String, Object> getDatosNominaByIdNominaComplementaria(@RequestBody NominaDto nominaDto) throws BusinessException {
		try {
			//hace referencia a la nomina origen
			NominaDto nomina = nominaBO.getDatosNominaByIdNomina(nominaDto.getIdNomina());
			Map<String, Object> dataReturn = new HashMap<>();
			dataReturn.put("nominaDto", nominaBO.getDatosNominaByIdNominaComplementaria(nominaDto, getUser().getIdUsuario()));
			dataReturn.put("ListColaboradoresPadre", colaboradorPPPBO.cargaInicialColaboradoresNominasPadre(nominaDto.getIdNomina())) ;

			return dataReturn;

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value = "/nominas/guardaNominaByIdNominaComplementaria")
	@ResponseBody
	public Map<String, Object> guardaNominaByIdNominaComplementaria(@RequestBody NominaDto nominaDto) throws BusinessException {
		try {

			Map<String, Object> dataReturn = new HashMap<>();
			dataReturn.put("nominaDto", nominaBO.guardaNominaByIdNominaComplementaria(nominaDto, getUser().getIdUsuario()));

			return dataReturn;

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en guardaNominaByIdNominaComplementaria ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaByIdNominaComplementaria ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
	}



	@RequestMapping(value = "/nominas/guardaNominaComplemento", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardaNominaComplemento(@RequestBody NominaComplementoDto nominaComplementoDto, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			if(nominaComplementoDto == null) {
				LOGGER.error("Ocurrio un error en guardaNominaComplemento,  nominaComplementoDto viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar. Favor de intentarlo mas tarde");
				return mensajeDTO;
			}

			if (nominaComplementoDto.getFechaFacturacion() == null
					|| nominaComplementoDto.getFechaDispersion() == null) {
				throw new BusinessException("", "");
			}

			if(nominaComplementoDto.getRequiereFianciamiento()) {

				if(nominaComplementoDto.getIdCMS() == null) {

					if(nominaComplementoDto.getMontoFinanciamiento() == null
						|| nominaComplementoDto.getObservaciones() == null
						|| "".equals(nominaComplementoDto.getObservaciones().trim())
							) {
						throw new BusinessException("", "");

					}else if(nominaComplementoDto.getDocumentoNuevo() == null) {
						LOGGER.error("Ocurrio un error en guardaNominaComplemento objeto DocumentoNuevo es null ");
						throw new BusinessException("", "");
					}else if(nominaComplementoDto.getDocumentoNuevo() != null &&
							nominaComplementoDto.getDocumentoNuevo().getArchivo() == null || nominaComplementoDto.getDocumentoNuevo().getMimeType() == null
							|| nominaComplementoDto.getDocumentoNuevo().getNombreArchivo() == null || nominaComplementoDto.getDocumentoNuevo().getTamanioArchivo() == null) {

						LOGGER.error("Ocurrio un error en guardaNominaComplemento variables del obketo DocumentoNuevo son null, revisar ");
						throw new BusinessException("", "");

					}else if(nominaComplementoDto.getMontoFinanciamiento() !=null ) {
						BigDecimal mf = nominaComplementoDto.getMontoFinanciamiento();
						BigDecimal mtppp = nominaComplementoDto.getMontoTotalPpp();

						if(mtppp.compareTo(mf) < 0) {
							throw new BusinessException("", "");
						}
					}

				} else {

					if(nominaComplementoDto.getMontoFinanciamiento() == null
							|| nominaComplementoDto.getObservaciones() == null
							|| "".equals(nominaComplementoDto.getObservaciones().trim())
								) {
							throw new BusinessException("", "");

					}else if(nominaComplementoDto.getMontoFinanciamiento() !=null ) {
						BigDecimal mf = nominaComplementoDto.getMontoFinanciamiento();
						BigDecimal mtppp = nominaComplementoDto.getMontoTotalPpp();

						if(mtppp.compareTo(mf) < 0) {
							throw new BusinessException("", "");
						}
					}

				}


			}else {
				nominaComplementoDto.setDocumentoNuevo(null);
				nominaComplementoDto.setMontoFinanciamiento(null);
				nominaComplementoDto.setObservaciones(null);
			}
		
			nominaComplementoDto.setFechaTimbrado(nominaComplementoDto.getFechaFacturacion());
			if(!nominaComplementoBO.guargarNominaComplemento(nominaComplementoDto, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en guardaNominaComplemento - guargarNominaComplemento");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
				return mensajeDTO;
			}



		}catch (BusinessException be){

			if (nominaComplementoDto == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar y seleccionar alguna opci\u00f3n");
				return mensajeDTO;

			}else if (nominaComplementoDto.getFechaFacturacion() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha de facturaci\u00f3n'");
				return mensajeDTO;

			}else if (nominaComplementoDto.getFechaDispersion() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha de dispersi\u00f3n'");
				return mensajeDTO;

			}else if (nominaComplementoDto.getFechaTimbrado() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha timbrado'");
				return mensajeDTO;

			}else if(nominaComplementoDto.getRequiereFianciamiento()) {

				if(nominaComplementoDto.getIdCMS() == null) {
					if(nominaComplementoDto.getObservaciones() == null|| "".equals(nominaComplementoDto.getObservaciones().trim())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Motivo de financiamiento'");
						return mensajeDTO;

					}else if(nominaComplementoDto.getMontoFinanciamiento() == null) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Monto financiamiento'");
						return mensajeDTO;
					}else if(nominaComplementoDto.getDocumentoNuevo() == null) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Documento sustento (financiamiento)'");
						return mensajeDTO;

					}else if(nominaComplementoDto.getDocumentoNuevo() != null &&
							nominaComplementoDto.getDocumentoNuevo().getArchivo() == null || nominaComplementoDto.getDocumentoNuevo().getMimeType() == null
							|| nominaComplementoDto.getDocumentoNuevo().getNombreArchivo() == null || nominaComplementoDto.getDocumentoNuevo().getTamanioArchivo() == null) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Documento sustento (financiamiento)'");
						return mensajeDTO;

					}else if(nominaComplementoDto.getMontoFinanciamiento() !=null ) {
						BigDecimal mf = nominaComplementoDto.getMontoFinanciamiento();
						BigDecimal mtppp = nominaComplementoDto.getMontoTotalPpp();

						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("El monto de financiamiento es mayor al monto total ppp.");
						return mensajeDTO;
					}

				}else if(nominaComplementoDto.getMontoFinanciamiento() !=null ) {
						BigDecimal mf = nominaComplementoDto.getMontoFinanciamiento();
						BigDecimal mtppp = nominaComplementoDto.getMontoTotalPpp();

						if(mtppp.compareTo(mf) < 0) {
							mensajeDTO.setCorrecto(false);
							mensajeDTO.setMensajeError("El monto de financiamiento es mayor al monto total ppp.");
							return mensajeDTO;
						}

					}
				}



		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaComplemento ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}

		return mensajeDTO;
	}

	@RequestMapping(value = "/nominas/getDatosNominaComplemento")
	@ResponseBody
	public NominaComplementoDto getDatosNominaComplemento(@RequestBody String claveNomina) throws BusinessException {
		try {


			return nominaComplementoBO.getDatosNomComplByIdNomCompl(claveNomina);

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en getdatosNominaComplemento ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en getdatosNominaComplemento ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/cargarDatosDespuesDeGuardarComplemento")
	@ResponseBody
	public NominaDto cargarDatosDespuesDeGuardarComplemento(@RequestBody String claveNomina) throws BusinessException {
		try {


			return nominaComplementoBO.getNominaDtoByClave(claveNomina);

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en cargarDatosDespuesDeGuardarComplemento ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargarDatosDespuesDeGuardarComplemento ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/cargaInicialColaboradores")
	@ResponseBody
	public List<EmpleadoDTO> cargaInicialColaboradores(@RequestBody Long idNominaCliente) throws BusinessException {
		try {
			List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
			//se obtiene el id de la nomina complementaria


			lista = colaboradorPPPBO.cargaInicialColaboradores(idNominaCliente);
		return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	/*Para nomina complementaria*/
	@RequestMapping(value = "/nominas/cargaInicialColaboradoresComplemento")
	@ResponseBody
	public List<EmpleadoDTO> cargaInicialColaboradoresComplemento(@RequestBody Long idNominaCliente) throws BusinessException {
		try {
			List<EmpleadoDTO> lista = new ArrayList<EmpleadoDTO>();
			//se obtiene el id de la nomina complementaria
			NominaDto nomina= new NominaDto();
			nomina=nominaBO.getIdNominaByComplementaria(idNominaCliente);
			lista = colaboradorPPPBO.cargaInicialColaboradores(nomina.getIdNominaPPPComplementaria());
		//	lista = colaboradorPPPBO.cargaInicialColaboradoresComplementaria(idNominaCliente, nomina.getIdNominaPPPComplementaria());
		return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}


	@RequestMapping(value = "/nominas/guardarColaboradores")
	@ResponseBody
	public void guardarColaboradores(@RequestBody List<EmpleadoDTO> colaboradores) throws BusinessException {
		try {

			if(!colaboradores.isEmpty()) {
				colaboradorPPPBO.guardarColaboradores(colaboradores, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}


	@RequestMapping(value = "/nominas/guardarColaborador")
	@ResponseBody
	public void guardarColaborador(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {

			if(colaborador.getIdPppColaborador() != null) {
				colaboradorPPPBO.guardarColaborador(colaborador, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}


	@RequestMapping(value = "/nominas/guardarColaboradorDirecto")
	@ResponseBody
	public void guardarColaboradorDirecto(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {

				colaboradorPPPBO.guardarColaboradorDirecto(colaborador, getUser().getIdUsuario());


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	@RequestMapping(value = "/nominas/eliminarColaboradores")
	@ResponseBody
	public void eliminarColaboradores(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {

			if(colaborador.getIdNomina() != null) {
				colaboradorPPPBO.eliminarColaboradores(colaborador, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	@RequestMapping(value = "/nominas/eliminarColaborador")
	@ResponseBody
	public void eliminarColaborador(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {

			if(colaborador.getIdPppColaborador() != null) {
				colaboradorPPPBO.eliminarColaborador(colaborador, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	@RequestMapping(value = "/nominas/bloquearColaborador")
	@ResponseBody
	public void bloquearColaborador(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {
			if(colaborador != null) {
				colaboradorPPPBO.bloquearColaborador(colaborador, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	@RequestMapping(value = "/nominas/desbloquearColaborador")
	@ResponseBody
	public void desbloquearColaborador(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {
			if(colaborador != null) {
				colaboradorPPPBO.desbloquearColaborador(colaborador, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}

	@RequestMapping(value = "/nominas/cargaEstatusColaborador")
	@ResponseBody
	public List<PppColaboradorEstatusDto> cargaEstatusColaborador(@RequestBody EmpleadoDTO colaborador) throws BusinessException {
		try {
			List<PppColaboradorEstatusDto> pppColaboradorEstatusDto= new ArrayList<PppColaboradorEstatusDto>();
			if(colaborador.getIdPppColaborador() != null) {
				pppColaboradorEstatusDto = colaboradorPPPBO.cargaEstatusColaborador(colaborador);
			}else {
				PppColaboradorEstatusDto  estatus = new PppColaboradorEstatusDto();
				UsuarioDTO usuario = new UsuarioDTO();

				estatus.setCatEstatusColaborador(new CatEstatusDto());
				estatus.setPppColaboradorDto(new EmpleadoDTO());

				estatus.getPppColaboradorDto().setNombre(colaborador.getNombre());
				estatus.getPppColaboradorDto().setRfc(colaborador.getRfc());
				estatus.getPppColaboradorDto().setMontoPPP(colaborador.getMontoPPP());
				estatus.getPppColaboradorDto().setClabe(colaborador.getClabe());

				estatus.getCatEstatusColaborador().setDescripcionEstatus("Cargado layout");
				estatus.setFechaAlta(new Date());

				usuario.setNombre(getUser().getNombre());
				usuario.setPrimerApellido(getUser().getPrimerApellido());
				usuario.setSegundoApellido(getUser().getSegundoApellido());

				estatus.setUsuarioAlta(usuario);
				pppColaboradorEstatusDto.add(estatus);
			}
		return pppColaboradorEstatusDto;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaEstatusColaborador ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}


	@RequestMapping(value="/nominas/guardarConcepto")
	@ResponseBody
	public ResponseEntity<String> guardarConcepto(@RequestBody FacturaDTO facturaDTO) {

		facturaBO.guardarNominaFactura(facturaDTO, getUser().getIdUsuario());

		return new ResponseEntity<String>(HttpStatus.OK);
	}


	@RequestMapping(value="/nominas/eliminarConcepto")
	@ResponseBody
	public ResponseEntity<String> eliminarConcepto(@RequestBody ConceptoFacturaDTO  conceptoFactura) {

		facturaBO.eliminarConceptoFactura(conceptoFactura);

		return new ResponseEntity<String>(HttpStatus.OK);
	}


	@RequestMapping(value = "/nominas/getFacturacionByNomina")
	@ResponseBody
	public FacturaDTO cargaInicialFacturacion(@RequestBody Long idNominaPPP) throws BusinessException {
		try {
			//Obtener el id de la nomina padre
			FacturaDTO facturaDTO=null;
			NominaDto	nomPadre = nominaBO.getNominaPadre(idNominaPPP);
			if(nomPadre != null && nomPadre.getIdNominaPPPPadre() !=null ) {
				 facturaDTO = facturaBO.obtenerFacturaByIdNomina(nomPadre.getIdNominaPPPPadre());
			}else {
			 facturaDTO = facturaBO.obtenerFacturaByIdNomina(idNominaPPP);
			}
			if(facturaDTO != null && facturaDTO.getIdPPPNominaFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdPPPNominaFactura()));
			}

			return facturaDTO;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/getFormulaFactura")
	@ResponseBody
	public FormulaFacturaDto formulaFactura(@RequestBody Long idNominaCliente) throws BusinessException {
		try {


			return nominaClienteBO.formulaFactura(idNominaCliente);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/getHistoricoByIdPppNomina")
	@ResponseBody
	public List<HistoricoNominaDto> getHistoricoByIdPppNomina(@RequestBody Long idPppNomina) throws BusinessException {
		try {

			if (idPppNomina != null) {


				return nominaBO.getHistoricoByIdPppNomina(idPppNomina);

			}else {
				LOGGER.error("Ocurrio un error en getHistoricoByIdPppNomina idPppNomina viene null");
				throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
			}


		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en getHistoricoByIdPppNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/obtenerDocumentosPppNomina", method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentoCSMDto> obtenerDocumentosPppNomina(@RequestBody Long idPppNominaFactura) throws BusinessException{

		try {


			if(idPppNominaFactura == null) {
				LOGGER.error("Ocurrio un error en obtenerDocumentosPppNomina, idPppNomina viene null ");
				return new ArrayList<DocumentoCSMDto>();
			}

			return nominaBO.listDocumentosPppNomina(idPppNominaFactura);


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerDocumentosPppNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/guardarDocumentosPppNomina")
    @ResponseBody
    public ResponseEntity<String> guardarDocumentosPppNomina(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {

		try {
			nominaBO.guardarDocumentosPppNominaFactura(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPppNomina ", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPppNomina ", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
    }

	@RequestMapping(value = "/nominas/eliminarDocumentosPppNomina", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> eliminarDocumentosPppNomina(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {

		try {
			nominaBO.eliminarDocumentosPppNomina(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosPppNomina ", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosPppNomina ", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(HttpStatus.OK);

    }


	@RequestMapping(value="/nominas/cambiaEstatusNomina", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNomina(@RequestBody Long idPppNomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {


			if(idPppNomina!=null) {
				if(!nominaBO.cambiaEstatusNomina(idPppNomina, null, NominaEstatusEnum.FACTURA_CARGADA, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en cambiaEstatusNomina, el cambio de estatus de nomina fallo ");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusNomina, idPppNomina viene null ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNomina ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNomina ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/cambiaEstatusNominaGenerada", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNominaGenerada(@RequestBody Long idPppNomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {


			if(idPppNomina!=null) {
				if(!nominaBO.cambiaEstatusNomina(idPppNomina, null, NominaEstatusEnum.FACTURA_GENERADA, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en cambiaEstatusNominaGenerada, el cambio de estatus de nomina fallo ");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusNominaGenerada, idPppNomina viene null ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNominaGenerada ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNominaGenerada ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}



	@RequestMapping(value="/nominas/cambiaEstatusNominaCuentaConciliada", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNominaCuentaConciliada(@RequestBody Long idPppNomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {


			if(idPppNomina!=null) {
				if(!nominaBO.cambiaEstatusNomina(idPppNomina, null, NominaEstatusEnum.CTA_CONCILIADA, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en cambiaEstatusNominaCuentaConciliada, el cambio de estatus de nomina fallo ");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusNominaCuentaConciliada, idPppNomina viene null ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNominaCuentaConciliada ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNominaCuentaConciliada ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/guardarNominaFacturaFlujoAlterno", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardarNominaFacturaFlujoAlterno(@RequestBody FacturaDTO factura, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {


//			if(factura.getIdPPPNominaFactura()!=null) {
				if(!facturaBO.guardarNominaFacturaFlujoAlterno(factura, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en al guardar guardarNominaFacturaFlujoAlterno ");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}


//			}else {
//				LOGGER.error("Ocurrio un error en guardarComplementoPppNominaFactura, getIdPPPNominaFactura viene null ");
//				mensajeDTO.setCorrecto(false);
//				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
//			}

			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en guardarNominaFacturaFlujoAlterno ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarNominaFacturaFlujoAlterno ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}



	@RequestMapping(value = "/nominas/preFactura")
	@ResponseBody
	public DocumentoMSDto preFactura(@RequestBody NominaDto nominaDto) throws BusinessException {
		DocumentoMSDto documento = new DocumentoMSDto();

		try {
			RestTemplate restTemplate = new  RestTemplate();

			FacturaDTO facturaDTO = facturaBO.obtenerFacturaByIdNomina(nominaDto.getIdNomina());

			facturaDTO.setTipoCambio("1");
			if(facturaDTO != null && facturaDTO.getIdPPPNominaFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdPPPNominaFactura()));

				List<ImpuestoFacturaDTO> impuestosFactura = new ArrayList<>();

				for (ConceptoDTO conceptoDTO : facturaDTO.getConceptos()) {
					if(conceptoDTO.getImpuestos() != null && !conceptoDTO.getImpuestos().isEmpty()){
						List<ImpuestoDTO> impuestoDTOs = conceptoDTO.getImpuestos();
						for (ImpuestoDTO impuestoDTO : impuestoDTOs) {

							ImpuestoFacturaDTO impuesto = new ImpuestoFacturaDTO(1,impuestoDTO.getTipo().getDescripcion(),impuestoDTO.getTotalImpuesto());
							impuestosFactura.add(impuesto);

							String impuestosDescripcion = impuestoDTO.getTipo().getDescripcion() + " " + impuestoDTO.getTotalImpuesto().setScale(2, BigDecimal.ROUND_FLOOR);

							conceptoDTO.setImpuestosDescripcion(impuestosDescripcion);
						}
					}
				}

				facturaDTO.setImpuestosFactura(impuestosFactura);
			}

			// PRESTADORA (EMPRESA) DATOS GENERALES Y DOMICILIO
			PrestadoraServicioDto prestadoraServicioDto = null;

			if(facturaDTO.getIdPrestadoraServicio()!=null && facturaDTO.getIdPrestadoraServicio() > 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(new PrestadoraServicioDto(), facturaDTO.getIdPrestadoraServicio());
			}else {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(new PrestadoraServicioDto(), nominaDto.getPrestadoraServicioDto().getIdPrestadoraServicio());
			}

			//Emisor
			facturaDTO.setEmpresa(datosEmisor(prestadoraServicioDto));
			facturaDTO.setDomicilioEmpresa(domicilioEmisor(prestadoraServicioDto));


			// CLIENTE DATOS GENERALES Y DIRECCION
			ClienteDto cliente = null;
			if(facturaDTO.getIdCliente()!=null && facturaDTO.getIdCliente() > 0) {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(facturaDTO.getIdCliente());
			}else {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(nominaDto.getClienteDto().getIdCliente());
			}


			DomicilioComunDto domicilio = null;

			if(facturaDTO.getIdCliente()!=null && facturaDTO.getIdCliente() > 0) {
				domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(facturaDTO.getIdCliente()));
			}else {
				domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(nominaDto.getClienteDto().getIdCliente()));
			}

			//Cliente
			facturaDTO.setCliente(datosCliente(cliente));
			facturaDTO.setDomicilioCliente(domicilioCliente(domicilio));

			//Monto en pesos
			facturaDTO.setMontoLetra(Numero_Letras.cantidadConLetra(facturaDTO.getTotales().getTotal().toString()));

			String base64= restTemplate.postForObject(urlBase + facturaService + "/vistaPreviafactura", facturaDTO, String.class);
			documento.setDocumentoBase64(base64);
			documento.setMimeType("data:application/pdf;base64,");

			return documento;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}




	@RequestMapping(value = "/nominas/generarFactura")
	@ResponseBody
	public FacturaMsDTO generarFactura(@RequestBody NominaDto nominaDto) throws BusinessException {


		try {
			RestTemplate restTemplate = new  RestTemplate();

			FacturaDTO facturaDTO = facturaBO.obtenerFacturaByIdNomina(nominaDto.getIdNomina());
			facturaDTO.setIdUsuarioAplicativo(getUser().getIdUsuario());
			facturaDTO.setIdPrestadoraServicio(nominaDto.getPrestadoraServicioDto().getIdPrestadoraServicio());
			facturaDTO.setTipoCambio("1");
			if(facturaDTO != null && facturaDTO.getIdPPPNominaFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdPPPNominaFactura()));

				List<ImpuestoFacturaDTO> impuestosFactura = new ArrayList<>();

				for (ConceptoDTO conceptoDTO : facturaDTO.getConceptos()) {
					if(conceptoDTO.getImpuestos() != null && !conceptoDTO.getImpuestos().isEmpty()){
						List<ImpuestoDTO> impuestoDTOs = conceptoDTO.getImpuestos();
						for (ImpuestoDTO impuestoDTO : impuestoDTOs) {

							ImpuestoFacturaDTO impuesto = new ImpuestoFacturaDTO(1,impuestoDTO.getTipo().getDescripcion(),impuestoDTO.getTotalImpuesto());
							impuestosFactura.add(impuesto);

							String impuestosDescripcion = impuestoDTO.getTipo().getDescripcion() + " " + impuestoDTO.getTotalImpuesto().setScale(2, BigDecimal.ROUND_FLOOR);

							conceptoDTO.setImpuestosDescripcion(impuestosDescripcion);
						}
					}
				}

				facturaDTO.setImpuestosFactura(impuestosFactura);
			}

			// PRESTADORA (EMPRESA) DATOS GENERALES Y DOMICILIO
			PrestadoraServicioDto prestadoraServicioDto = null;

			if(facturaDTO.getIdPrestadoraServicio()!=null && facturaDTO.getIdPrestadoraServicio() > 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(new PrestadoraServicioDto(), facturaDTO.getIdPrestadoraServicio());
			}else {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(new PrestadoraServicioDto(), nominaDto.getPrestadoraServicioDto().getIdPrestadoraServicio());
			}

			//Emisor
			facturaDTO.setEmpresa(datosEmisor(prestadoraServicioDto));
			facturaDTO.setDomicilioEmpresa(domicilioEmisor(prestadoraServicioDto));


			// CLIENTE DATOS GENERALES Y DIRECCION
			ClienteDto cliente = null;
			if(facturaDTO.getIdCliente()!=null && facturaDTO.getIdCliente() > 0) {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(facturaDTO.getIdCliente());
			}else {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(nominaDto.getClienteDto().getIdCliente());
			}


			DomicilioComunDto domicilio = null;

			if(facturaDTO.getIdCliente()!=null && facturaDTO.getIdCliente() > 0) {
				domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(facturaDTO.getIdCliente()));
			}else {
				domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(nominaDto.getClienteDto().getIdCliente()));
			}

			//Cliente
			facturaDTO.setCliente(datosCliente(cliente));
			facturaDTO.setDomicilioCliente(domicilioCliente(domicilio));
			facturaDTO.getCliente().setUsoCFDI(facturaDTO.getUsoCFDI());

			//Monto en pesos
			facturaDTO.setMontoLetra(Numero_Letras.cantidadConLetra(facturaDTO.getTotales().getTotal().toString()));


			//PAC timbrado seleccionado
			facturaDTO.setPacTimbrado(nominaDto.getCatPacTimbrado());

			FacturaMsDTO facturaMsDTO= restTemplate.postForObject(urlBase +facturaService+"/generarFactura", facturaDTO, FacturaMsDTO.class);
//			FacturaMsDTO facturaMsDTO= restTemplate.postForObject("http://localhost:9080/generarFactura", facturaDTO, FacturaMsDTO.class);


			return facturaMsDTO;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			new FacturaMsDTO("500", "Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");

		}
	}





	private DomicilioDTO domicilioCliente(DomicilioComunDto domicilio) {
		DomicilioDTO domicilioCliente = new DomicilioDTO();
		domicilioCliente.setIdDomicilio(domicilio.getDomicilio().getIdDomicilio().intValue());
		domicilioCliente.setCodigoPostal(domicilio.getDomicilio().getCodigoPostal());
		domicilioCliente.setCalle(domicilio.getDomicilio().getCalle());
		domicilioCliente.setNumExterior(domicilio.getDomicilio().getNumeroExterior());
		domicilioCliente.setNumInterior(domicilio.getDomicilio().getNumeroInterior());
		domicilioCliente.setColonia(domicilio.getDomicilio().getColonia());

		CatMunicipioDTO catMunicipioCliente = new CatMunicipioDTO();
		catMunicipioCliente.setIdCatMunicipio(domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral() != null ? domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral().intValue() : null);
		catMunicipioCliente.setClave(domicilio.getDomicilio().getCatMunicipios().getClave()  != null ? domicilio.getDomicilio().getCatMunicipios().getClave() : null);
		catMunicipioCliente.setMunicipio(domicilio.getDomicilio().getCatMunicipios().getDescripcion()  != null ? domicilio.getDomicilio().getCatMunicipios().getDescripcion().toUpperCase() : null);
		domicilioCliente.setCatMunicipio(catMunicipioCliente);

		CatGeneralDto catEntidadFed = domicilio.getDomicilio().getCatEntidadFederativa();

		CatGenericoDTO catEstadoPaisCliente = new CatGenericoDTO();
		catEstadoPaisCliente.setId(catEntidadFed.getIdCatGeneral() !=null ? catEntidadFed.getIdCatGeneral().intValue() : null);
		catEstadoPaisCliente.setClave(catEntidadFed.getClave()!=null ? catEntidadFed.getClave() : null);
		catEstadoPaisCliente.setDescripcion(catEntidadFed.getDescripcion()!=null ? catEntidadFed.getDescripcion().toUpperCase() : null);
		domicilioCliente.setCatEstadoPais(catEstadoPaisCliente);

		CatGenericoDTO catPaisCliente = new CatGenericoDTO();
		catPaisCliente.setDescripcion("M\u00c9XICO".toUpperCase());
		domicilioCliente.setCatPais(catPaisCliente);
		return domicilioCliente;
	}

	private ClienteDTO datosCliente(ClienteDto cliente) {
		ClienteDTO clienteFactura = new ClienteDTO();
		if(cliente.getRazonSocial()!=null) {
			clienteFactura.setRazonSocial(cliente.getRazonSocial());
		}else {
			clienteFactura.setRazonSocial(cliente.getApellidoMaterno()!=null ? cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno()
													: cliente.getNombre()+" "+cliente.getApellidoPaterno());
		}

		clienteFactura.setRfc(cliente.getRfc());
		return clienteFactura;
	}

	private EmpresaDTO datosEmisor(PrestadoraServicioDto prestadoraServicioDto) {
		EmpresaDTO empresaDto = new EmpresaDTO();
		empresaDto.setIdEmpresa(prestadoraServicioDto.getIdPrestadoraServicio().intValue());
		empresaDto.setNombre(prestadoraServicioDto.getNombreCorto());
		empresaDto.setRazonSocial(prestadoraServicioDto.getRazonSocial());
		empresaDto.setRfc(prestadoraServicioDto.getRfc());

		return empresaDto;
	}

	public DomicilioDTO domicilioEmisor(PrestadoraServicioDto prestadoraServicioDto) {

		DomicilioDTO domicilioEmpresa = new DomicilioDTO();
		domicilioEmpresa.setCalle(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCalle());
		domicilioEmpresa.setNumExterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroExterior());
		domicilioEmpresa.setNumInterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroInterior());
		domicilioEmpresa.setColonia(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getColonia());
		domicilioEmpresa.setCodigoPostal(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal());

		CatGeneralDto catEntidadFed = prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCatEntidadFederativa();
		CatGenericoDTO catEstadoPais = new CatGenericoDTO();
		catEstadoPais.setId(catEntidadFed.getIdCatGeneral() !=null ? catEntidadFed.getIdCatGeneral().intValue() : null);
		catEstadoPais.setClave(catEntidadFed.getClave()!=null ? catEntidadFed.getClave() : null);
		catEstadoPais.setDescripcion(catEntidadFed.getDescripcion()!=null ? catEntidadFed.getDescripcion().toUpperCase() : null);
		domicilioEmpresa.setCatEstadoPais(catEstadoPais);

		CatGeneralDto catMunicipios = prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCatMunicipios();
		CatMunicipioDTO catMunicipio = new CatMunicipioDTO();
		catMunicipio.setIdCatMunicipio(catMunicipios.getIdCatGeneral() != null ? catMunicipios.getIdCatGeneral().intValue() : null);
		catMunicipio.setClave(catMunicipios.getClave()  != null ? catMunicipios.getClave() : null);
		catMunicipio.setMunicipio(catMunicipios.getDescripcion()  != null ? catMunicipios.getDescripcion().toUpperCase() : null);
		domicilioEmpresa.setCatMunicipio(catMunicipio);

		CatGenericoDTO catPais = new CatGenericoDTO();
		catPais.setDescripcion("M\u00c9XICO".toUpperCase());
		domicilioEmpresa.setCatPais(catPais);

		return domicilioEmpresa;
	}


	@RequestMapping(value="/nomina/timbrado")
	@ResponseBody
	public FacturaMsDTO timbrarColaboradores(@RequestBody TimbradoColaboradoresDto timbrado) {

		LOGGER.info(timbrado.getNominaPPP().getClaveNomina());
		FacturaMsDTO facturaMsDTO = new FacturaMsDTO();
	    SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MMM/yyyy");

		PrestadoraServicioDto prestadora =  timbrado.getNominaPPP().getPrestadoraServicioDto();
		NominaComplementoDto nominaComplementoDto = timbrado.getNominaComplemento();

		PrestadoraServicioDto prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(new PrestadoraServicioDto(), prestadora.getIdPrestadoraServicio());

		//Valores Default de la generacion de conceptos
		ConceptoDTO conceptoDTO = new ConceptoDTO();
		conceptoDTO.setCodigoSat("84111505");
		conceptoDTO.setDescripcionConcepto("Pago de nómina");
		conceptoDTO.setDescripcion("Pago nomina");

		List<ConceptoDTO> conceptoDTOs = new ArrayList<>();
		conceptoDTOs.add(conceptoDTO);

		CatGeneralDto moneda = new CatGeneralDto();
		moneda.setClave("MXN");
		moneda.setDescripcion("Peso");

		FacturaDTO facturaDTO =  new FacturaDTO();

		facturaDTO.setConceptos(conceptoDTOs);
		facturaDTO.setIdUsuarioAplicativo(getUser().getIdUsuario());

		//Emisor
		facturaDTO.setEmpresa(datosEmisor(prestadoraServicioDto));
		facturaDTO.setMoneda(moneda);
		facturaDTO.setTipoCambio("1");
		facturaDTO.setIdConsar(prestadora.getIdConsar());

		facturaDTO.setIdPPPNominaFactura(timbrado.getIdPPPNominaFactura());
		facturaDTO.setIdPrestadoraServicio(prestadora.getIdPrestadoraServicio());
		facturaDTO.setPacTimbrado(timbrado.getCatPacTimbrado());

		CatGeneralDto regimenFiscal = new CatGeneralDto();
		regimenFiscal.setClave("601");
		facturaDTO.setRegimenFiscal(regimenFiscal);

		for (EmpleadoDTO empleado : timbrado.getColaboradores()) {

			RestTemplate restTemplate = new RestTemplate();

			// PRESTADORA (EMPRESA) DATOS GENERALES Y DOMICILIO
			//TODO cambiar el rfc de la empresa
//			facturaDTO.getEmpresa().setRfc("EKU9003173C9");
			facturaDTO.setDomicilioEmpresa(domicilioEmisor(prestadoraServicioDto));

			// CLIENTE DATOS GENERALES Y DIRECCION
			ClienteDTO cliente = new ClienteDTO();
			cliente.setNombre(empleado.getNombre());
			cliente.setPrimerApellido(empleado.getApellidoPaterno());
			cliente.setSegundoApellido(empleado.getApellidoMaterno());
			cliente.setNombreCompleto(cliente.getNombre() + " " + (cliente.getPrimerApellido()!= null ? cliente.getPrimerApellido():"") + " " + (cliente.getSegundoApellido()!=null?cliente.getSegundoApellido():"") );
			cliente.setRfc(empleado.getRfc());
			cliente.setCurp(empleado.getCurp());
			cliente.setIdEmpleadoSTP(empleado.getCveOrdenPago());
			cliente.setIdCliente(empleado.getIdPppColaborador());
			cliente.setCorreoElectronico(empleado.getCorreoElectronico());
			cliente.setNss(empleado.getNss());

			facturaDTO.setCliente(cliente);


			ComplementoNominaDto complementoNominaDto = new ComplementoNominaDto();
			complementoNominaDto.setFechaInicio(timbrado.getNominaPPP().getFechaInicioNomina());
			complementoNominaDto.setFechaInicioFormato(formatFecha.format(timbrado.getNominaPPP().getFechaInicioNomina()).toUpperCase());

			complementoNominaDto.setFechaFin(timbrado.getNominaPPP().getFechaFinNomina());
			complementoNominaDto.setFechaFinFormato(formatFecha.format(timbrado.getNominaPPP().getFechaFinNomina()).toUpperCase());

			complementoNominaDto.setFechaPago(nominaComplementoDto.getFechaDispersion());
			complementoNominaDto.setFechaPagoFormato(formatFecha.format(nominaComplementoDto.getFechaDispersion()).toUpperCase());

			complementoNominaDto.setMontoPPP(empleado.getMontoPPP());
			complementoNominaDto.setPeriodicidad(timbrado.getNominaPPP().getPeriodicidadNomina());
			facturaDTO.setComplementoNominaDto(complementoNominaDto);


			facturaMsDTO= restTemplate.postForObject(urlBase + nominaService+ "/generarFacturaNomina", facturaDTO, FacturaMsDTO.class);

//			facturaMsDTO= restTemplate.postForObject("http://localhost:8096/" + "generarFacturaNomina", facturaDTO, FacturaMsDTO.class);
			facturaMsDTO.getPdf();
			facturaMsDTO.getXml();
		}

		return facturaMsDTO;
	}

	@RequestMapping(value="/nominas/cambiaEstatusNominaBorrador", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNominaBorrador(@RequestBody Long idPppNomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {


			if(idPppNomina!=null) {
				if(!nominaBO.cambiaEstatusNomina(idPppNomina, null, NominaEstatusEnum.BORRADOR, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en cambiaEstatusNominaBorrador, el cambio de estatus de nomina fallo ");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusNominaBorrador, idPppNomina viene null ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNominaBorrador ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNominaBorrador ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/cambiaEstatusNominaCancelada", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNominaCancelada(@RequestBody NominaDto nomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en cambiaEstatusNominaCancelada, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en cambiaEstatusNominaCancelada, no cuenta con motivo de rechazo para el idClienteTmp "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }


			if(!nominaBO.cambiaEstatusNomina(nomina.getIdNominaPPP() , nomina.getMotivoRechazo(), NominaEstatusEnum.NOMINA_CANCELADA, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en cambiaEstatusNominaCancelada, el cambio de estatus de nomina fallo ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}


			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNominaCancelada ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNominaCancelada ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/existeFondoAsignadoAlCliente", method = RequestMethod.POST)
	@ResponseBody
	public Boolean existeFondoAsignadoAlCliente(@RequestBody NominaClienteDto nominaCliente, Model model) throws BusinessException {

		ValidaCreacionNominaDto valida = new ValidaCreacionNominaDto();

		try {

			valida = nominaClienteBO.validaSecciones(nominaCliente.getClienteDto().getIdCliente(), nominaCliente.getIdNominaCliente());

			if(valida.esCliente) {
				return false;
			}

			return false;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en validaCreacionNomina ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en validaCreacionNomina ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/validaCreacionNomina", method = RequestMethod.POST)
	@ResponseBody
	public ValidaCreacionNominaDto validaCreacionNomina(@RequestBody NominaClienteDto nominaCliente, Model model) throws BusinessException {

		ValidaCreacionNominaDto valida = new ValidaCreacionNominaDto();

		try {

			valida = nominaClienteBO.validaSecciones(nominaCliente.getClienteDto().getIdCliente(), nominaCliente.getIdNominaCliente());

			if(valida.esCliente) {
				return valida;
			}

			return valida;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en validaCreacionNomina ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en validaCreacionNomina ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/existeInfoPasoCinco", method = RequestMethod.POST)
	@ResponseBody
	public Boolean existeInfoPasoCinco(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return facturaBO.existeInfoPasoCinco(idNominaPPP);

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/updateDatosPasoCinco", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateDatosPasoCinco(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return facturaBO.updateDatosPasoCinco(idNominaPPP, getUser().getIdUsuario());

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/cambiarEstatusNominaCierre", method = RequestMethod.POST)
	@ResponseBody
	public Boolean cambiarEstatusNominaCierre(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return colaboradorPPPBO.cambiarEstatusNomina(idNominaPPP, getUser().getIdUsuario(), new Long(NominaEstatusEnum.CIERRE_NOMINA.getId()));

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error al cambiar a Cierre la nomina - cambiarEstatusNominaCierre ", be);
			throw new BusinessException ("Ocurrio un error al cambiar a Cierre la nomina. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiarEstatusNominaCierre ", e);
			throw new BusinessException ("Ocurrio un error al cambiar a Cierre la nomina. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/cambiarEstatusNominaTimbrado", method = RequestMethod.POST)
	@ResponseBody
	public Boolean cambiarEstatusNominaTimbrado(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return colaboradorPPPBO.cambiarEstatusNomina(idNominaPPP, getUser().getIdUsuario(), new Long(NominaEstatusEnum.CIERRE_NOMINA.getId()));

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error al cambiar a Timbrado la nomina - cambiarEstatusNominaTimbrado ", be);
			throw new BusinessException ("Ocurrio un error al cambiar a Timbrado la nomina. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiarEstatusNominaTimbrado ", e);
			throw new BusinessException ("Ocurrio un error al cambiar a Timbrado la nomina. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/existenColaboradodaresTimbrados", method = RequestMethod.POST)
	@ResponseBody
	public Boolean existenColaboradodaresTimbrados(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return colaboradorPPPBO.existenColaboradodaresTimbrados(idNominaPPP);

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en existenUsuariosSinTimbrar ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existenUsuariosSinTimbrar ", e);
			throw new BusinessException ("Ocurrio un error  en el sistema. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value="/nominas/existenColaboradoresSinTimbrar", method = RequestMethod.POST)
	@ResponseBody
	public Boolean existenColaboradoresSinTimbrar(@RequestBody Long idNominaPPP, Model model) throws BusinessException {

		try {

				return colaboradorPPPBO.existenUsuariosSinTimbrar(idNominaPPP);

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en  existenColaboradodaresTimbrados ", be);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existenColaboradodaresTimbrados ", e);
			throw new BusinessException ("Ocurrio un error  en el sistema. Favor de intentarlo mas tarde");
		}
	}

	@RequestMapping(value = "/nominas/guardarNominaPppComplementaria")
	@ResponseBody
	public long guardaNominaPppComplementaria(@RequestBody NominaDto nominaDto) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();
		Long fechaInicio = null;
		Long fechaFin = null;
		Long idComplementaria = null;

		try {
			if(nominaDto == null) {
				LOGGER.error("Ocurrio un error en guardaNominaPppComplementaria,  nominaDto viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar. Favor de intentarlo mas tarde");
			//	return mensajeDTO;
			}

			if (nominaDto.getNominaClienteDto()== null
					|| (nominaDto.getNominaClienteDto()!= null && nominaDto.getNominaClienteDto().getIdNominaCliente() == null)
					|| nominaDto.getFechaInicioNomina() == null
					|| nominaDto.getFechaFinNomina() == null) {
				throw new BusinessException("", "");
			}


			if(nominaDto.getFechaInicioNomina() != null && nominaDto.getFechaFinNomina() != null) {
				Format formatter = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				fechaInicio = df.parse(formatter.format(nominaDto.getFechaInicioNomina() )).getTime();
				fechaFin = df.parse(formatter.format(nominaDto.getFechaFinNomina())).getTime();

				if (fechaFin < fechaInicio) {
					throw new BusinessException("", "");
				} else if (fechaInicio > fechaFin) {
					throw new BusinessException("", "");
				}
			}
			idComplementaria=nominaBO.guardarNominaComplementaria(nominaDto, getUser().getIdUsuario());
			if(idComplementaria!=0l) {
				LOGGER.error("Ocurrio un error en guardaNominaPpp - guardarNomina");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");

				return idComplementaria;
			}



		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaComplementaria ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}

		return idComplementaria;
	}


}
