package mx.com.consolida.controller.clienteCrm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteActividadDto;
import mx.com.consolida.crm.dto.ClienteComisionHonorarioDto;
import mx.com.consolida.crm.dto.ClienteConceptoFacaturacionDto;
import mx.com.consolida.crm.dto.ClienteCuentaBancariaDto;
import mx.com.consolida.crm.dto.ClienteCuentasBancariasDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClienteMedioContactoDto;
import mx.com.consolida.crm.dto.ClientePrestadoraServicioDto;
import mx.com.consolida.crm.dto.ClienteProductoDto;
import mx.com.consolida.crm.dto.ClienteServicioDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.ClienteBO;
import mx.com.consolida.crm.service.interfaz.ClientePrestadoraServicioBO;
import mx.com.consolida.crm.service.interfaz.ClienteProductoBo;
import mx.com.consolida.crm.service.interfaz.ClienteSeccionesBO;
import mx.com.consolida.crm.service.interfaz.NominaClienteBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.CatTipoFormulaEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.TipoPersonaEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.admin.CanalVentaBO;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.consolida.util.ConstantesComunes;



@Controller
@RequestMapping("clienteSeccionesCrm")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CLIENTE_TEMP, ReferenciaSeguridad.CLIENTE 
		, ReferenciaSeguridad.ES_AGREGAR_CLIENTE, ReferenciaSeguridad.ID_NOMINA_CLIENTE})
public class ClienteSeccionesController  extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteSeccionesController.class);
	

	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private ClienteProductoBo productoBo;
	
	@Autowired
	private CelulaBO celulaBO;
	
	@Autowired
	private ClienteSeccionesBO clienteSeccionesBO;
	
	@Autowired
	private PrestadoraServicioBO prestadoraServicioBO;
	
	@Autowired
	private NominaClienteBO nominaClienteBO;
	
	@Autowired
	private ClientePrestadoraServicioBO clientePrestadoraServicioBO;

	@Autowired
	private ClienteBO clienteBO;
	
	@Autowired
	private CanalVentaBO canalVentaBO;
	
	@RequestMapping(value = "/datosGenerales/datosGeneralesCliente")
	@ResponseBody
	public void datosGenCliente(@RequestBody ClienteDto clienteDto, Model model) throws BusinessException {
		try {
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				model.addAttribute(ReferenciaSeguridad.CLIENTE, clienteDto);
			}else {
				LOGGER.error("Ocurrio un error en datosGenCliente, el objeto clienteDto es vacio ");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en datosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/datosGenerales/creaDatosGenCliente")
	@ResponseBody
	public void creaDatosGenCliente(@RequestBody Long idCliente, Model model) throws BusinessException {
		try {
			
			if(idCliente!=null) {
				model.addAttribute(ReferenciaSeguridad.CLIENTE, clienteBO.getDatosGeneralesClienteBiIdCliente(idCliente));
			}else {
				LOGGER.error("Ocurrio un error en creaDatosGenCliente, el objeto idCLiente es vacio ");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en creaDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/datosGenerales/cargaInicialDatosGenCliente")
	@ResponseBody
	public Map<String, Object> cargaInicialDatosGenCliente(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				
				List<CatGrupoDto> listaGrupos = catBo.obtenerCatalogoGrupo();
				dataReturn.put("clienteDto", clienteDto);
				dataReturn.put("listaGruposDto", listaGrupos);
				dataReturn.put("listaCatTipoPago", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.METODO_PAGO.getCve()));
				dataReturn.put("listaCatGiroComercial", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve()));
				dataReturn.put("listaCategorias", catBo.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(CatMaestroEnum.CATEGORIA.getCve()));
				dataReturn.put("listaRegimenes", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_REGIMEN_FISCAL.getCve()));
				if(clienteDto.getCatGiroComercialDto()!=null && clienteDto.getCatGiroComercialDto().getIdCatGeneral()!=null) {
					dataReturn.put("listaSubGrupo", catBo.obtenerCatSubgiroXIdGiro(clienteDto.getCatGiroComercialDto().getIdCatGeneral()));	
				}else {
					dataReturn.put("listaSubGrupo", Collections.emptyList());
				}

				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/datosGenerales/guardarGeneralesCliente", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardarGeneralesCliente(@RequestBody ClienteDto clienteDto, Model model)
			throws BusinessException {
		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			if (clienteDto.getCatTipoPersona() == null 
					|| clienteDto.getCatGrupo() == null 
					|| (clienteDto.getRfc() == null || (clienteDto.getRfc() != null && "".equals(clienteDto.getRfc().trim())))
				    || (clienteDto.getCveRegistroPatronal()==null || "".equals(clienteDto.getCveRegistroPatronal().trim()))
					|| clienteDto.getFechaConstitucionEmpresa() == null 
					|| clienteDto.getCveRegistroPatronal()== null
					|| clienteDto.getCatCategoria() == null || (clienteDto.getCatCategoria() != null && clienteDto.getCatCategoria().getIdCatGeneral() == null)
					|| clienteDto.getCatRegimenFiscal() == null || (clienteDto.getCatRegimenFiscal() != null && clienteDto.getCatRegimenFiscal().getIdCatGeneral() == null)
					|| (clienteDto.getActividadEconomicaFinal()== null || (clienteDto.getActividadEconomicaFinal() != null && "".equals(clienteDto.getActividadEconomicaFinal().trim())))
					|| clienteDto.getEsActivo() == null
					) {
				throw new BusinessException("", "");
			}
			
			if(clienteDto.getCatTipoPersona().getIdCatGeneral().equals(TipoPersonaEnum.MORAL.getId_tipoPersona())) {
				if(clienteDto.getRazonSocial() == null || "".equals(clienteDto.getRazonSocial())
						|| ConstantesComunes.LONGITUD_RFC_MORAL != clienteDto.getRfc().length()) {
					throw new BusinessException("", "");
				}
			}
			
			if(clienteDto.getCatTipoPersona().getIdCatGeneral().equals(TipoPersonaEnum.FISICA.getId_tipoPersona())) {
				if(clienteDto.getNombre() == null || "".equals(clienteDto.getNombre().trim()) 
			|| (clienteDto.getApellidoPaterno() == null || "".equals(clienteDto.getApellidoPaterno().trim()))
			|| ConstantesComunes.LONGITUD_RFC_FISICA != clienteDto.getRfc().length()){
					throw new BusinessException("", "");
				}
			}
			

		    clienteSeccionesBO.guardaActualizaDatosgenerales(clienteDto, getUser().getIdUsuario());
			
			model.addAttribute(ReferenciaSeguridad.CLIENTE, clienteDto);

		} catch (BusinessException be) {

			if (clienteDto.getCatTipoPersona() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar el 'tipo de persona'");
				return mensajeDTO;
			}

			if (clienteDto.getCatGrupo() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar el 'cliente'");
				return mensajeDTO;
			}

			if (clienteDto.getRfc() == null || 
					(clienteDto.getRfc() !=null && "".equals(clienteDto.getRfc().trim()))) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar el 'RFC'");
				return mensajeDTO;
			}
			
			if(clienteDto.getCatTipoPersona().getIdCatGeneral().equals(TipoPersonaEnum.FISICA.getId_tipoPersona())) {
				if (clienteDto.getNombre() == null 
						|| (clienteDto.getNombre()!=null && "".equals(clienteDto.getNombre().trim()))) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Nombre'");
					return mensajeDTO;
				}
				
				if (clienteDto.getApellidoPaterno() == null 
						|| (clienteDto.getApellidoPaterno()!=null && "".equals(clienteDto.getApellidoPaterno().trim()))) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Apellido Paterno'");
					return mensajeDTO;
				}
				
				if (ConstantesComunes.LONGITUD_RFC_FISICA != clienteDto.getRfc().length()) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Favor de revisar el RFC, el n\u00famero de caracteres no corresponde a la de persona f\u00edsica");
					return mensajeDTO;
				}
			}
			
			if(clienteDto.getCatTipoPersona().getIdCatGeneral().equals(TipoPersonaEnum.MORAL.getId_tipoPersona())) {
				if (clienteDto.getRazonSocial() == null 
						|| (clienteDto.getRazonSocial()!=null && "".equals(clienteDto.getRazonSocial().trim()))) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Raz\u00f3n social'");
					return mensajeDTO;
				}else if(ConstantesComunes.LONGITUD_RFC_MORAL != clienteDto.getRfc().length()) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Favor de revisar el RFC, el n\u00famero de caracteres no corresponde a la de persona moral");
					return mensajeDTO;
				}
			}
			
			if (clienteDto.getFechaConstitucionEmpresa() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha constituci\u00f3n de la empresa'");
				return mensajeDTO;
			}
			
			if ((clienteDto.getCveRegistroPatronal() == null 
					|| (clienteDto.getCveRegistroPatronal()!=null && "".equals(clienteDto.getCveRegistroPatronal().trim())))  &&  clienteDto.getCatTipoPersona().getIdCatGeneral()!=21)  {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Clave registro patronal'");
				return mensajeDTO;
			}
			

			if(clienteDto.getCatCategoria() == null || (clienteDto.getCatCategoria() != null && clienteDto.getCatCategoria().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Categoria'");
				return mensajeDTO;
			}
			
			if(clienteDto.getCatRegimenFiscal() == null || (clienteDto.getCatRegimenFiscal() != null && clienteDto.getCatRegimenFiscal().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'R\u00e9gimen fiscal'");
				return mensajeDTO;
			}

			if(clienteDto.getEsActivo() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Es activo'");
				return mensajeDTO;
			}
			
			if (clienteDto.getActividadEconomicaFinal() == null || 
					(clienteDto.getActividadEconomicaFinal() !=null && "".equals(clienteDto.getActividadEconomicaFinal().trim()))) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Actividad econ\u00f3mica final'");
				return mensajeDTO;
			}
		
		}
		return mensajeDTO;
	}
	
	@RequestMapping(value="/datosStp/cargaInicialDatosStp")
	@ResponseBody
	public Map<String, Object> cargaInicialDatosStp(Model model) {
		
		Map<String, Object> dataReturn = new HashMap<>();

		try {

			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				
				dataReturn.put("prefijoStp", clienteSeccionesBO.getPrefijoStp(clienteDto.getIdCliente()));
				return dataReturn;

			}else {
				LOGGER.error("Ocurrio un error en cargaInicialDatosStp , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosStp ", e.getMessage());
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/datosStp/guardarPrefijoStp", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardarPrefijoStp(@RequestBody ClienteDto cliente, Model model)
			throws BusinessException {
		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			
			if ((cliente.getPrefijoSTP() == null || (cliente.getPrefijoSTP() != null && "".equals(cliente.getPrefijoSTP().trim())))) {
				throw new BusinessException("", "");
			}

			usuarioAplicativo.setIdUsuario(getUser().getIdUsuario());
			clienteSeccionesBO.guardarPrefijoStp(cliente, usuarioAplicativo);

		} catch (BusinessException be) {
			
			if (cliente.getPrefijoSTP() == null || 
					(cliente.getPrefijoSTP() !=null && "".equals(cliente.getPrefijoSTP().trim()))) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar el 'Prefijo STP'");
				return mensajeDTO;
			}
			
		}
		return mensajeDTO;
	}

	private List<NoministaDto> listaNoministasByidCelula(ClienteDto clienteDto) throws BusinessException {
		
		try {
			
			List<UsuarioDTO> noministas = celulaBO.consultarUsuariosByCelulaRol(clienteDto.getCelula().getIdCelula(), RolUsuarioENUM.NOMINISTA.getId());
							
			List<NoministaDto> listaNoministas = new ArrayList<>();

			if(clienteDto!=null && clienteDto.getCelula()!=null&&clienteDto.getCelula().getIdCelula()!=null) {
				
				if(noministas!=null && !noministas.isEmpty()) {
					for(UsuarioDTO usu : noministas ) {
						NoministaDto nominista = new NoministaDto();
						nominista.setIdPersona(usu.getIdPersona());
						nominista.setNombre(usu.getNombre());
						nominista.setApellidoPaterno(usu.getPrimerApellido());
						nominista.setApellidoMaterno(usu.getSegundoApellido()!=null ? usu.getSegundoApellido() : "".trim());
						nominista.setNombreCompletoNominista(usu.getNombre()+ " "+usu.getPrimerApellido()+ " "+nominista.getApellidoMaterno());
						nominista.setIdNominista(usu.getIdUsuario());
						listaNoministas.add(nominista);
					}

					return listaNoministas;
					
				}else {
					return listaNoministas;
				}
			}else {
				return listaNoministas;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNoministasByidCelula ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value="/conceptoFacturacionCliente/cargaInicialFacturacion")
	@ResponseBody
	public List<ClienteConceptoFacaturacionDto> cargaInicialFacturacion(Model model) {
		try {
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			List<ClienteConceptoFacaturacionDto> factuDto = clienteSeccionesBO.getListtConceptosFacturacionByCliente(clienteDto.getIdCliente());
			
			return factuDto;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value="/conceptoFacturacionCliente/guardaConceptoFacturacion")
	@ResponseBody
	public MensajeDTO guardaConceptoFacturacion(@RequestBody ClienteConceptoFacaturacionDto factuDto, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(factuDto == null || (factuDto!=null &&factuDto.getDescConceptoFacturacion() == null)) {
				throw new BusinessException("", "");
			}
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(factuDto.getIdConceptoFacturacionCliente()==null) {
				factuDto.setEsEliminar(false);
			}
			
			if(clienteDto == null || (clienteDto!=null & clienteDto.getIdCliente() == null)) {
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo nuevamente");
			}else {
				factuDto.setClienteDto(clienteDto);
				
				if(!clienteSeccionesBO.guardarActualizarClienteConceptoFacturacion(factuDto, getUser().getIdUsuario())) {
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}
			
			return mensajeDTO;
		
		}catch (BusinessException be) {
			if (factuDto == null || (factuDto!=null && factuDto.getDescConceptoFacturacion() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar un 'concepto de facturacio\u00f3n.'");
			}
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaConceptoFacturacion ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value="/conceptoFacturacionCliente/eliminarConceptoFacturacion")
	@ResponseBody
	public MensajeDTO eliminarConceptoFacturacion(@RequestBody ClienteConceptoFacaturacionDto factuDto, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {

			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			factuDto.setClienteDto(clienteDto);
			factuDto.setEsEliminar(true);
			if(!clienteSeccionesBO.guardarActualizarClienteConceptoFacturacion(factuDto, getUser().getIdUsuario())) {
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			
			return mensajeDTO;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaConceptoFacturacion ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/celula/cargaInicialCelula")
	@ResponseBody
	public Map<String, Object> cargaInicialCelula(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				
//				clienteDto.setNoministaDto(clienteSeccionesBO.getClienteNoministaByidClienteIdNominista(clienteDto.getIdCliente()));
				CelulaDto celula = clienteSeccionesBO.getCelulaBy(clienteDto.getIdCliente());
				clienteDto.setCelula(celula);
				clienteDto.setPrestadoraServicioFondo(new PrestadoraServicioDto(clientePrestadoraServicioBO.getidFondoByIdCliente(clienteDto.getIdCliente())));
				dataReturn.put("clienteDto", clienteDto);
				dataReturn.put("listaPrestadorasFondo", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(celula.getIdCelula(), ConstantesComunes.ES_FONDO));
				dataReturn.put("listaPrestadoras", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(celula.getIdCelula(), ConstantesComunes.NO_ES_FONDO));
				dataReturn.put("listaCelulasDto", celulaBO.listarTodasLasCelulasCliente());
				dataReturn.put("listaClientesPrestadoras", clientePrestadoraServicioBO.listaClientesPrestadoras(clienteDto.getIdCliente()));
				
//				dataReturn.put("listaNoministasDto", listaNoministasByidCelula(clienteDto));

				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/celula/getPrestadorasByIdCelula")
	@ResponseBody
	public Map<String, Object> getPrestadorasByIdCelula(@RequestBody Long idCelula,  Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {

				dataReturn.put("listaPrestadorasFondo", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(idCelula, ConstantesComunes.ES_FONDO));
				dataReturn.put("listaPrestadoras", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(idCelula, ConstantesComunes.NO_ES_FONDO));

				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value="/celula/guardaCelula")
	@ResponseBody
	public MensajeDTO guardaCelula(@RequestBody ClienteDto cliente, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(cliente == null || cliente!=null && cliente.getIdCliente()==null) {
				LOGGER.error("Ocurrio un error en guardaCelula , el objeto clienteDto es vacio o no cuneta con idCLiente");
				throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
			}
			
			if(cliente == null || (cliente!=null &&cliente.getCelula() == null)
					|| (cliente!=null && cliente.getPrestadoraServicioFondo() == null)
					|| (cliente!=null && cliente.getPrestadoraServicio() == null)) {
				throw new BusinessException("", "");
			}
			
			if(cliente.getIdCliente()!=null) {
				if(clienteSeccionesBO.existeCelulaEnCliente(cliente.getIdCliente(), cliente.getCelula().getIdCelula())) {
					throw new BusinessException("", "");
				}
			}
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);

			
			if(clienteDto == null || (clienteDto!=null & clienteDto.getIdCliente() == null)) {
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
			}else {

				if(clienteSeccionesBO.existeCelulaCliente(cliente.getIdCliente())) {
					clienteDto.setCelula(null);
				}else {
					clienteDto.setCelula(cliente.getCelula());
				}
				
//				clienteDto.setNoministaDto(cliente.getNoministaDto());
				clienteDto.setPrestadoraServicio(cliente.getPrestadoraServicio());
				clienteDto.setPrestadoraServicioFondo(cliente.getPrestadoraServicioFondo());
				if(!clienteSeccionesBO.guardaDatosCelula(clienteDto, getUser().getIdUsuario())) {
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
			}
			
			model.addAttribute(ReferenciaSeguridad.CLIENTE, clienteDto);
			
			return mensajeDTO;
		
		}catch (BusinessException be) {
			if (cliente!=null &&cliente.getCelula() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe selecionar una 'Celula.'");
				
			}
			
			
			if ((cliente!=null &&cliente.getPrestadoraServicioFondo() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe selecionar 'Prestadora de servicio (fondo).'");
			}
			
			if ((cliente!=null &&cliente.getPrestadoraServicio() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe selecionar 'Prestadora de servicio.'");
			}
			
			if(cliente.getIdCliente()!=null) {
				if(clienteSeccionesBO.existeCelulaEnCliente(cliente.getIdCliente(), cliente.getCelula().getIdCelula())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("No puede registrar mas de un Centro de costos al cliente.'");
				}
			}
			
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaConceptoFacturacion ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/celula/eliminarClientePrestadora")
	@ResponseBody
	public MensajeDTO eliminarClientePrestadora(@RequestBody Long idClientePrestadoraServicio, Model model) {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(idClientePrestadoraServicio!=null) {

				if(!clienteSeccionesBO.eliminarClientePrestadora(idClientePrestadoraServicio, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en eliminarClientePrestadora, no se pudieron eliminar los datos en eliminarClientePrestadora");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}

			}else {
				LOGGER.error("Ocurrio un error en eliminarClientePrestadora , el objeto idClientePrestadoraServicio es null");
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema, favor de intentarlo mas tarde.");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarClientePrestadora , el objeto idNominaCliente es null ", e);
			mensajeDTO.setMensajeError("Ocurrio un error en el sistema, favor de intentarlo mas tarde.");
			return mensajeDTO;
		}
		
		return mensajeDTO;
		
	}
	
	@RequestMapping(value = "/nominaCliente/cargaInicialNominaCliente")
	@ResponseBody
	public Map<String, Object> cargaInicialNominas(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

			NominaClienteDto nominaClienteDto = new NominaClienteDto();
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				
				nominaClienteDto.setClienteDto(clienteDto);

				dataReturn.put("nominaCliente", nominaClienteDto);
				dataReturn.put("listaCelulasDto", celulaBO.listarTodasLasCelulas());
				dataReturn.put("listaProductosNomina", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PRODUCTO_NOMINA.getCve()));
//				dataReturn.put("listaPrestadorasFondo", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(clienteDto.getCelula().getIdCelula(), ES_FONDO));
//				dataReturn.put("listaPrestadoras", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(clienteDto.getCelula().getIdCelula(), NO_ES_FONDO));
				dataReturn.put("catPrestadorasServicioFondo", prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.ES_FONDO));
				dataReturn.put("catPrestadorasServicio", prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.NO_ES_FONDO));
				dataReturn.put("gridNominaCliente", nominaClienteBO.listaNominaCliente(clienteDto.getIdCliente()));

				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/nominaCliente/existeFondo")
	@ResponseBody
	public Boolean existeFondo(@RequestBody Long idCliente, Model model) {
		
		try {
			
			List<ClientePrestadoraServicioDto> lista= clientePrestadoraServicioBO.listaClientesPrestadoras(idCliente);
			
			if(lista != null && !lista.isEmpty()) {
				
				return Boolean.TRUE;
				
			}else {
				return Boolean.FALSE;
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeFondo ", e);
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = "/nominaCliente/getNominaClienteById")
	@ResponseBody
	public Map<String, Object> getNominaClienteById(@RequestBody NominaClienteDto nominaClienteDto, Model model) throws BusinessException {
		
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
			
			if(nominaClienteDto.getClienteDto()!=null && nominaClienteDto.getClienteDto().getIdCliente()!=null) {

				dataReturn.put("nominaCliente", nominaClienteDto);
				dataReturn.put("listaCelulasDto", celulaBO.listarTodasLasCelulas());
				dataReturn.put("listaProductosNomina", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PRODUCTO_NOMINA.getCve()));
//				dataReturn.put("listaPrestadorasFondo", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), ConstantesComunes.ES_FONDO));
//				dataReturn.put("listaPrestadoras", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), NO_ES_FONDO));
				dataReturn.put("catPrestadorasServicioFondo", prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.ES_FONDO));
				dataReturn.put("catPrestadorasServicio", prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(nominaClienteDto.getClienteDto().getCelula().getIdCelula(), nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.NO_ES_FONDO));
				dataReturn.put("gridNominaCliente", nominaClienteBO.listaNominaCliente(nominaClienteDto.getClienteDto().getIdCliente()));

				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en getNominaClienteById , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominaClienteById ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value="/nominaCliente/guardaNominaCliente", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardaNominaCliente(@RequestBody NominaClienteDto nominaClienteDto, Model model) throws Exception {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(nominaClienteDto == null) {
				LOGGER.error("Ocurrio un error en guardaNominaCliente , el objeto nominaClienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
			}
						
			if(nominaClienteDto.getNombreNomina() == null || "".equals(nominaClienteDto.getNombreNomina().trim())
					|| nominaClienteDto.getClaveNomina()== null || "".equals(nominaClienteDto.getClaveNomina().trim())
					|| (nominaClienteDto.getCatProductoNomina() == null
					|| (nominaClienteDto.getCatProductoNomina() != null && nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == null))
					|| nominaClienteDto.getUsuarioNominista() == null
					|| nominaClienteDto.getUsuarioNominista().getIdNominista() == null) {
				throw new BusinessException("", "");
			}
			
			
			if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950
					){
				
				List<PrestadoraServicioDto> listPrestadora = prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(
						nominaClienteDto.getClienteDto().getCelula().getIdCelula(),
						nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.NO_ES_FONDO);
				
				if(listPrestadora!=null && !listPrestadora.isEmpty()) {

					 if((nominaClienteDto.getPrestadoraServicio() == null
								|| (nominaClienteDto.getPrestadoraServicio() != null && nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio() == null))) {
							throw new BusinessException("", "");
					 }
				}

			}if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304 || nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9958) {// 304 es PPP
				
				if(nominaClienteDto.getRequiereFactura() == null) {
					throw new BusinessException("", "");
				}else if((nominaClienteDto.getPrestadoraServicioFondo() == null
						|| (nominaClienteDto.getPrestadoraServicioFondo() != null && nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() == null))) {
					throw new BusinessException("", "");
				}
				
			}
			
			// 304 es PPP
			if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304 || nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9958) {
				
				nominaClienteDto.setPrestadoraServicio(null);
				
			}else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950
					){
				
				nominaClienteDto.setPrestadoraServicioFondo(null);
			}
		
			Long idNominaCliente = clienteSeccionesBO.guardaActualizaNominaCliente(nominaClienteDto, getUser().getIdUsuario());
			if( idNominaCliente == null ) {
				LOGGER.error("Ocurrio un error en guardaNominaCliente, no se pudieron guardar los datos de nomina cliente");
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			model.addAttribute(ReferenciaSeguridad.ID_NOMINA_CLIENTE, idNominaCliente);
			return mensajeDTO;
		
		}catch (BusinessException be) {
			if (nominaClienteDto.getNombreNomina() == null || "".equals(nominaClienteDto.getNombreNomina().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Nombre n\u00f3mina.'");
				return mensajeDTO;
				
			}else if (nominaClienteDto.getUsuarioNominista() == null
				||	nominaClienteDto.getUsuarioNominista().getIdNominista() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Nominista'");
				return mensajeDTO;
				
			}else if (nominaClienteDto.getClaveNomina()== null || "".equals(nominaClienteDto.getClaveNomina().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Clave n\u00f3mina.'");
				return mensajeDTO;
				
			}else if (nominaClienteDto.getCatProductoNomina() == null
					|| (nominaClienteDto.getCatProductoNomina() != null && nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar un  'Esquema n\u00f3mina'");
				return mensajeDTO;
				
			}else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950
					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9958){
				
				List<PrestadoraServicioDto> listPrestadora = prestadoraServicioBO.listPrestadoraServicioByIdCelulaAndIdCliente(
						nominaClienteDto.getClienteDto().getCelula().getIdCelula(),
						nominaClienteDto.getClienteDto().getIdCliente(), ConstantesComunes.NO_ES_FONDO);
				
				if(listPrestadora!=null && !listPrestadora.isEmpty()) {

					 if((nominaClienteDto.getPrestadoraServicio() == null
								|| (nominaClienteDto.getPrestadoraServicio() != null && nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio() == null))) {
						 mensajeDTO.setCorrecto(false);
						 mensajeDTO.setMensajeError("Debe seleccionar 'Prestadora de servicio'");
						 return mensajeDTO;
					 }
				}

			}if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304 || nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9958) {// 304 es PPP
				
				if(nominaClienteDto.getRequiereFactura() == null) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe seleccionar una opci\u00f3n para  '¿Generar factura?'");
					return mensajeDTO;
				}else if(nominaClienteDto.getRequiereTimbre() == null) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe seleccionar una opci\u00f3n para  '¿Generar Timbre?'");
					return mensajeDTO;
				}
				else if((nominaClienteDto.getPrestadoraServicioFondo() == null
						|| (nominaClienteDto.getPrestadoraServicioFondo() != null && nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() == null))) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe seleccionar 'Prestadora de servicio'");
					return mensajeDTO;
				}
				
			}
			
			
			
//			else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304) {
//				if(nominaClienteDto.getRequiereFactura() == null) {
//					mensajeDTO.setCorrecto(false);
//					mensajeDTO.setMensajeError("Debe seleccionar una opci\u00f3n para  '¿Generar factura?'");
//					return mensajeDTO;
//				}else if((nominaClienteDto.getPrestadoraServicioFondo() == null
//						|| (nominaClienteDto.getPrestadoraServicioFondo() != null && nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() == null))) {
//					mensajeDTO.setCorrecto(false);
//					mensajeDTO.setMensajeError("Debe seleccionar 'Prestadora de servicio'");
//					return mensajeDTO;
//				}
//				
//			}else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
//					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
//					|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950){
//				 if((nominaClienteDto.getPrestadoraServicio() == null
//							|| (nominaClienteDto.getPrestadoraServicio() != null && nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio() == null))) {
//						 mensajeDTO.setCorrecto(false);
//						 mensajeDTO.setMensajeError("Debe seleccionar 'Prestadora de servicio'");
//						 return mensajeDTO;
//					}
//			}
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaCliente ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/nominaCliente/eliminarNominaClienteById")
	@ResponseBody
	public MensajeDTO eliminarNominaClienteById(@RequestBody Long idNominaCliente, Model model) {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(idNominaCliente!=null) {

				if(!clienteSeccionesBO.eliminarNominaCliente(idNominaCliente, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en eliminarNominaClienteById, no se pudieron guardar los datos de nomina cliente");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}

			}else {
				LOGGER.error("Ocurrio un error en eliminarNominaClienteById , el objeto idNominaCliente es null");
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema, favor de intentarlo mas tare.");
			}
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarNominaClienteById , el objeto idNominaCliente es null ", e);
			mensajeDTO.setMensajeError("Ocurrio un error en el sistema, favor de intentarlo mas tare.");
			return mensajeDTO;
		}
		
		return mensajeDTO;
		
	}
	
	@RequestMapping(value="/domicilio/obtenerEntidadFederativaXCP")
	@ResponseBody
	public DomicilioComunDto obtenerEntidadFederativaXCP(@RequestBody String codigoPostal) {
		DomicilioComunDto clienteDomicilioDto = new DomicilioComunDto();
		clienteDomicilioDto = clienteSeccionesBO.obtenerEntidadFederativaXCP(codigoPostal);
		 	 
		 if(clienteDomicilioDto.getDomicilio() != null && clienteDomicilioDto.getDomicilio().getIdEntidadFederativa() != null) {
		 List<CatGeneralDto> catGeneral = catBo.obtenerCatMunicipioByEntidadFedByCveMun(clienteDomicilioDto.getDomicilio().getIdEntidadFederativa().toString(), clienteDomicilioDto.getDomicilio().getCatMunicipios().getIdCatGeneral().toString());
		 if(!catGeneral.isEmpty()) {	
			 clienteDomicilioDto.getDomicilio().setCatMunicipios(catGeneral.get(0));
		 }
		 clienteDomicilioDto.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), clienteDomicilioDto.getDomicilio().getIdEntidadFederativa().toString()));
		 }
		return clienteDomicilioDto;
	}
	
	@RequestMapping(value="/domicilio/cargaInicialDomicilio")
	@ResponseBody
	public DomicilioComunDto cargaInicialDomicilio(Model model) {
		try {
		DomicilioComunDto clienteDomicilioDto = new DomicilioComunDto();
		
		ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		
		clienteDomicilioDto = clienteSeccionesBO.obtenerDatosDomicilioByCliente(clienteDto);
		
		clienteDomicilioDto = clienteSeccionesBO.obtenerCatalogosDomicilio(clienteDomicilioDto);
		
		return clienteDomicilioDto;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDomicilio ", e.getMessage());
			throw new BusinessException ("Ocurrio un error en el sistema cargaInicialDomicilio");
		}
	}
	
	@RequestMapping(value = "/domicilio/guardarDomicilioCliente")
	@ResponseBody
	public MensajeDTO guardarDomicilioCliente(@RequestBody DomicilioComunDto domicilio, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			if(domicilio.getDomicilio() == null && domicilio.getDomicilioComercial() == null) {
				throw new BusinessException("");
			}
			
			if(domicilio.getDomicilio() != null) {
				if(domicilio.getDomicilio().getCodigoPostal() == null 
						|| domicilio.getDomicilio().getCalle() == null
						|| "".equals(domicilio.getDomicilio().getCalle().trim())
						|| domicilio.getDomicilio().getColonia() == null
						|| "".equals(domicilio.getDomicilio().getColonia().trim())
						|| (domicilio.getDomicilio().getNumeroExterior() == null || "".equals(domicilio.getDomicilio().getNumeroExterior().trim()))
						|| domicilio.getDomicilio().getIdEntidadFederativa()== null 
						|| domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral() == null) {
					throw new BusinessException("");
				}
			}

			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			domicilio.setCliente(clienteDto);
			clienteSeccionesBO.guardarDomicilioCliente(domicilio, getUser().getIdUsuario());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en domicilio/guardarDomicilioCliente ", e.getMessage());
			if(domicilio.getDomicilio() == null && domicilio.getDomicilioComercial() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar, favor de verificar.");
		         return mensajeDTO;
			}
			if(domicilio.getDomicilio() != null) {
				if(domicilio.getDomicilio().getCodigoPostal() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Código Postal', de la sección Domicilio Fiscal");
			         return mensajeDTO;
				}else if(domicilio.getDomicilio().getIdEntidadFederativa() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Entidad federativa', de la sección Domicilio Fiscal");
			         return mensajeDTO;
				}else if(domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Alcaldía', de la sección Domicilio Fiscal");
			         return mensajeDTO;
				}else if(domicilio.getDomicilio().getCalle() == null || "".equals(domicilio.getDomicilio().getCalle().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Calle'");
			         return mensajeDTO;
				}else if(domicilio.getDomicilio().getNumeroExterior() == null || "".equals(domicilio.getDomicilio().getNumeroExterior().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'N\u00famero exterior'");
			        return mensajeDTO;
				}else if(domicilio.getDomicilio().getColonia() == null || "".equals(domicilio.getDomicilio().getColonia().trim()))  {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'colonia'");
			         return mensajeDTO;
				}
			} 
		}
		return mensajeDTO;
	}
	
	@RequestMapping(value = "/domicilio/guardarDomicilioComercial")
	@ResponseBody
	public MensajeDTO guardarDomicilioComercial(@RequestBody DomicilioComunDto domicilio, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			if(domicilio.getDomicilio() == null && domicilio.getDomicilioComercial() == null) {
				throw new BusinessException("");
			}
						
			if(domicilio.getDomicilioComercial() != null) {
				if(domicilio.getDomicilioComercial().getCodigoPostal() == null 
						|| domicilio.getDomicilioComercial().getCalle() == null
						|| "".equals(domicilio.getDomicilioComercial().getCalle().trim())
						|| domicilio.getDomicilioComercial().getColonia() == null
					    || "".equals(domicilio.getDomicilioComercial().getColonia().trim())
						|| (domicilio.getDomicilioComercial().getNumeroExterior() == null || "".equals(domicilio.getDomicilioComercial().getNumeroExterior().trim()))
						|| domicilio.getDomicilioComercial().getIdEntidadFederativa() == null 
						|| domicilio.getDomicilioComercial().getCatMunicipios().getIdCatGeneral() == null) {
					throw new BusinessException("");
				}
			}
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			domicilio.setCliente(clienteDto);
			clienteSeccionesBO.guardarDomicilioComercial(domicilio, getUser().getIdUsuario());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en domicilio/guardarDomicilioComercial ", e.getMessage());
			if(domicilio.getDomicilio() == null && domicilio.getDomicilioComercial() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar, favor de verificar.");
		         return mensajeDTO;
			}
			
			if(domicilio.getDomicilioComercial() != null) {
				if(domicilio.getDomicilioComercial().getCodigoPostal() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Código Postal', de la sección Domicilio Comercial");
			         return mensajeDTO;
				}else if(domicilio.getDomicilioComercial().getIdEntidadFederativa() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Entidad federativa', de la sección Domicilio Comercial");
			         return mensajeDTO;
				}else if(domicilio.getDomicilioComercial().getCatMunicipios().getIdCatGeneral() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Alcaldía', de la sección Domicilio Comercial");
			         return mensajeDTO;
				}else if(domicilio.getDomicilioComercial().getCalle() == null || "".equals(domicilio.getDomicilioComercial().getCalle().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Calle'");
			         return mensajeDTO;
				}else if(domicilio.getDomicilioComercial().getNumeroExterior() == null || "".equals(domicilio.getDomicilioComercial().getNumeroExterior().trim())){
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'N\u00famero exterior'");
			        return mensajeDTO;
				}else if(domicilio.getDomicilioComercial().getColonia() == null || "".equals(domicilio.getDomicilioComercial().getColonia().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'colonia'");
			         return mensajeDTO;
				}
			}
		}
		return mensajeDTO;
	}
	
	@RequestMapping(value = "/domicilio/guardarClienteMedioContacto")
	@ResponseBody
	public MensajeDTO guardarClienteMedioContacto(@RequestBody ClienteMedioContactoDto cliente, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			
			if(cliente.getNombre() == null || cliente.getApellidoPaterno() == null 
					|| cliente.getCorreo() == null || cliente.getTelefono() == null) {
				throw new BusinessException("");
			}
			
			if(cliente.getCliente() != null && cliente.getCliente().getIdCliente() == null) {
				ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				cliente.setCliente(clienteDto);
			}
			
			clienteSeccionesBO.guardarClienteMedioContacto(cliente, getUser().getIdUsuario());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarClienteMedioContacto ", e.getMessage());
			if(cliente.getNombre() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Nombre', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getApellidoPaterno() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Apellido paterno', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getCorreo() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Correo', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getTelefono() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Teléfono', de la sección Medio de contacto");
		         return mensajeDTO;
			}else {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar el medio de contacto, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}	
	
	
	@RequestMapping(value = "/domicilio/guardarClienteMedioContactoCEO")
	@ResponseBody
	public MensajeDTO guardarClienteMedioContactoCEO(@RequestBody ClienteMedioContactoDto cliente, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			
			if(cliente.getNombre() == null || cliente.getApellidoPaterno() == null 
					|| cliente.getCorreo() == null || cliente.getTelefono() == null) {
				throw new BusinessException("");
			}
			
			if(cliente.getCliente() != null && cliente.getCliente().getIdCliente() == null) {
				ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				cliente.setCliente(clienteDto);
			}
			
			clienteSeccionesBO.guardarClienteMedioContacto(cliente, getUser().getIdUsuario());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarClienteMedioContactoCEO ", e.getMessage());
			if(cliente.getNombre() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Nombre', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getApellidoPaterno() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Apellido paterno', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getCorreo() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Correo', de la sección Medio de contacto");
		         return mensajeDTO;
			}else if(cliente.getTelefono() == null) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe agregar el campo 'Teléfono', de la sección Medio de contacto");
		         return mensajeDTO;
			}else {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar el medio de contacto, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}	
	
	
	///// Cuentas bancarias
	
	
	@RequestMapping(value="/cuentaBancaria/cargaInicialCuentasBancarias")
	@ResponseBody
	public ClienteCuentasBancariasDto cargaInicialCuentasBancarias(Model model) {
		try {
		ClienteCuentasBancariasDto clienteCuentasBancariasDto = new ClienteCuentasBancariasDto();
		
		ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		
		clienteCuentasBancariasDto = clienteSeccionesBO.obtenerDatosCuentaBancariaByCliente(clienteDto);
		
		clienteCuentasBancariasDto = clienteSeccionesBO.obtenerCatalogosCuentaBancaria(clienteCuentasBancariasDto);
		
		return clienteCuentasBancariasDto;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialCuentasBancarias ", e.getMessage());
			throw new BusinessException ("Ocurrio un error en el sistema cargaInicialDomicilio");
		}
	}
	
	@RequestMapping(value = "/cuentaBancaria/guardarCuentaBancaria")
	@ResponseBody
	public MensajeDTO guardarCuentaBancaria(@RequestBody ClienteCuentaBancariaDto cuenta, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
						
			if(cuenta.getCatBanco() == null
					|| cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == null || cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == -1
					|| cuenta.getCatTipoCuenta() == null
					|| cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == null || cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == -1) {
					throw new BusinessException("");
			}
			
			if(cuenta.getNumeroCuenta() == null  && cuenta.getClabeInterbancaria() == null ) {
				throw new BusinessException("");
			}
			
			if (cuenta.getEsPrincipal() != null) {
				if (cuenta.getEsPrincipal() == true) {
					mensajeDTO = verificarGuardado(cuenta);
					if (mensajeDTO.getCorrecto() == false) {
						return mensajeDTO;
					}
				}
			}
			
			// el 55 es para realizar depositos
			if(cuenta.getCatTipoCuenta().getIdCatGeneral() == 55) {
				if(cuenta.getNumeroReferencia() == null || "".equals(cuenta.getNumeroReferencia().trim())) {
					throw new BusinessException("");
				}
			}else {
				cuenta.setNumeroReferencia(null);
			}
		
			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}
			
			clienteSeccionesBO.guardarCuentaBancaria(cuenta, usuarioAplicativo);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/guardarCuentaBancaria ", e);
			
			if(cuenta.getNumeroCuenta() == null  && cuenta.getClabeInterbancaria() == null ) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe de introducir el campo Clabe interbancaria o número de cuenta, favor de verificar.");
		         return mensajeDTO;
			}else if(cuenta.getCatBanco() == null
					|| cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == null || cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == -1) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe de seleccionar el campo Banco, favor de verificar.");
		         return mensajeDTO;
			}else if(cuenta.getCatTipoCuenta() == null
					|| cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == null || cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == -1) {
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Debe de seleccionar el campo Función de la cuenta, favor de verificar.");
		         return mensajeDTO;
			}else if(cuenta.getCatTipoCuenta().getIdCatGeneral() == 55) {
				if(cuenta.getNumeroReferencia() == null  || "".equals(cuenta.getNumeroReferencia().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe ingresar 'Número de referencia de depósito', favor de verificar.");
			         return mensajeDTO;
				}
				
			}else{
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar la cuenta, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}
	
	
	public MensajeDTO verificarGuardado(ClienteCuentaBancariaDto cuenta) {
		return clienteSeccionesBO.verificarGuardado(cuenta);
	}
	
	
	@RequestMapping(value = "/cuentaBancaria/eliminarCuentaBancaria")
	@ResponseBody
	public MensajeDTO eliminarCuentaPrestadoraServicio(@RequestBody ClienteCuentaBancariaDto cuenta, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			if(cuenta.getIdClienteCuentaBancaria() == null) {
					throw new BusinessException("");
			}
			
			clienteSeccionesBO.eliminarCuentaBancaria(cuenta, getUser().getIdUsuario());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/eliminarCuentaPrestadoraServicio ", e);
			
			if(cuenta.getIdClienteCuentaBancaria() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Ocurrio un error al eliminar la cuenta, favor de verificar.");
			         return mensajeDTO;
			}else{
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar la cuenta, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}
	
	@RequestMapping(value="/actividad/cargaInicialActividad")
	@ResponseBody
	public ClienteActividadDto cargaInicialActividad(Model model) {
		try {
		ClienteActividadDto actividad = new ClienteActividadDto();
		
		ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		
		actividad = clienteSeccionesBO.obtenerDatosByActividad(clienteDto);
		
		actividad = clienteSeccionesBO.obtenerCatalogosActividad(actividad);
		
		return actividad;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialActividad ", e.getMessage());
			throw new BusinessException ("Ocurrio un error en el sistema cargaInicialDomicilio");
		}
	}
	
	@RequestMapping(value="/actividad/obtenerSubgiro")
	@ResponseBody
	public ClienteActividadDto obtenerSubgiro(@RequestBody String idGiro) {
		ClienteActividadDto cliente = new ClienteActividadDto();
		 cliente.setCatSubGiroComercial(clienteSeccionesBO.obtenerSubgiroXIdGiro(idGiro));
		
		return cliente;
	}
	
	@RequestMapping(value="/actividad/guardarActividad")
	@ResponseBody
	public MensajeDTO guardarActividad(@RequestBody ClienteActividadDto actividad, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			if(actividad.getIdGiroComercial() == null || actividad.getIdSubGiroComercial() == null ) {
					throw new BusinessException("");
			}
			
		
			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}
			
			clienteSeccionesBO.guardarActividad(actividad, usuarioAplicativo);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActividad ", e);
			
			if(actividad.getIdGiroComercial() == null ) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe seleccionar el campo 'Grupo', favor de verificar.");
			         return mensajeDTO;
			}else if(actividad.getIdSubGiroComercial() == null ) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe seleccionar el campo 'Subgrupo', favor de verificar.");
			         return mensajeDTO;
			}else{
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar la actividad, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}
	
	
	@RequestMapping(value="/actividad/eliminarActividad")
	@ResponseBody
	public void eliminarActividad(@RequestBody ClienteActividadDto actividad) {
		try {
		clienteSeccionesBO.eliminarActividad(actividad);
		} catch (BusinessException be) {
            throw new BusinessException(be.getCodigo(), be.getMessage());
        }
	}
	
	
	@RequestMapping(value = "/productos/cargaInicialProductos")
	@ResponseBody
	public ClienteServicioDto cargaInicialProductos(Model model) {
		try {
			ClienteServicioDto producto = new ClienteServicioDto();
						
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			CelulaDto celula =celulaBO.consultarDatosCelulaByIdCliente(clienteDto.getIdCliente());

			List<PrestadoraServicioProductoDto> listaPrestadoraProductos = clienteSeccionesBO.listaPrestadoraServicioProductosByIdCelula(clienteDto.getIdCliente());
			
			listaPrestadoraProductos = (clienteSeccionesBO.obtenerEstatusPrestServProductos(listaPrestadoraProductos, clienteDto.getIdCliente()));
			
			producto.setListaPrestadoraProductos(listaPrestadoraProductos);
			
			return producto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialProductos ", e.getMessage());
			throw new BusinessException ("Ocurrio un error en el sistema cargaInicialProductos");
		}
	}
	
	 @RequestMapping(value = "/productos/guardarProducto")
	    @ResponseBody
	    public void guardarProducto(@RequestBody ClienteServicioDto producto) throws BusinessException {
	        try {

	        	clienteSeccionesBO.guardarProducto(producto, getUser().getIdUsuario());
	        	
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en guardarProducto ", be);
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 
	@RequestMapping(value = "/detalleNominaCliente")
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public void detalleNominaCliente(@RequestBody Long idNominaCliente, Model model) {
		model.addAttribute(ReferenciaSeguridad.ID_NOMINA_CLIENTE, idNominaCliente);
	}

	
	@RequestMapping (value="/representanteLegal/cargaInicialRepresentanteLegal")
	@ResponseBody
    public Map<String, Object>  cargaInicialRepresentanteLegal(Model model) {
    	HashMap<String, Object> representante = new HashMap<String, Object>();
    		
    		try {

    			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
    			
    			representante.put("gridRepresentantesLegales", clienteSeccionesBO.getListRepresentanteLegalByIdCliente(clienteDto.getIdCliente()));
    			
    		} catch (Exception e) {
    			LOGGER.error("Ocurrio un error en cargaInicialRepresentanteLegal ", e);
    			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
    		}
    	    
    	   
    		return representante;
    }
	
	@RequestMapping(value="/representanteLegal/guardarRepresentanteLegal")
	@ResponseBody
	public MensajeDTO guardarRepresentanteLegal(@RequestBody RepresentanteLegalDto representanteLegal, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {


			if(representanteLegal!=null) {
				
				if(representanteLegal.getNombre() == null || "".equals(representanteLegal.getNombre().trim())
						|| representanteLegal.getApellidoPaterno() == null || "".equals(representanteLegal.getApellidoPaterno().trim())
						|| representanteLegal.getRfc() == null || "".equals(representanteLegal.getRfc().trim())
						|| representanteLegal.getCurp() == null || "".equals(representanteLegal.getCurp().trim())
//						|| representanteLegal.getContrasenaCertificado() == null || "".equals(representanteLegal.getContrasenaCertificado().trim())
						) {
					throw new BusinessException("", "");
				}
				
			}else {
				LOGGER.error("Ocurrio un error en guardarRepresentanteLegal el objeto representanteLegal viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				return mensajeDTO;
			} 
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null) {
				
				representanteLegal.setClienteDto(clienteDto);
				if(!clienteSeccionesBO.guardarActualizarRepresentanteLegal(representanteLegal, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en guardarRepresentanteLegal-guardarRepresentanteLegal");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				
			}else {
				LOGGER.error("Ocurrio un error en guardarRepresentanteLegal clienteDto viene null");
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			

			return mensajeDTO;
		}catch (BusinessException be) {
			
			
			if(representanteLegal.getNombre() == null || "".equals(representanteLegal.getNombre().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Nombre' para representante legal.");
				
			}else if(representanteLegal.getApellidoPaterno() == null || "".equals(representanteLegal.getApellidoPaterno().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Apellido paterno'  para representante legal.");
				
			}else if(representanteLegal.getRfc() == null || "".equals(representanteLegal.getRfc().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'RFC ' para representante legal.");
				
			}else if(representanteLegal.getCurp() == null || "".equals(representanteLegal.getCurp().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'CURP'  para representante legal.'");
			}
//			else if(representanteLegal.getContrasenaCertificado() == null || "".equals(representanteLegal.getContrasenaCertificado().trim())) {
//				mensajeDTO.setCorrecto(false);
//				mensajeDTO.setMensajeError("Debe ingresar  'Contrase\u00f1a certificado'  para representante legal.");
//			}
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarRepresentanteLegal ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value="/representanteLegal/guardarContraseniaCerKeyRepresentanteLegal")
	@ResponseBody
	public MensajeDTO guardarContraseniaCerKeyRepresentanteLegal(@RequestBody RepresentanteLegalDto representanteLegal, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {


			if(representanteLegal!=null) {
				
				if(representanteLegal.getContrasenaCertificado() == null || "".equals(representanteLegal.getContrasenaCertificado().trim())) {
					throw new BusinessException("", "");
				}
				
			}else {
				LOGGER.error("Ocurrio un error en guardarContraseniaCerKeyRepresentanteLegal el objeto representanteLegal viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				return mensajeDTO;
			} 
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null) {
				
				representanteLegal.setClienteDto(clienteDto);
				if(!clienteSeccionesBO.guardarActualizarRepresentanteLegal(representanteLegal, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en guardarContraseniaCerKeyRepresentanteLegal-guardarActualizarRepresentanteLegal");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				
			}else {
				LOGGER.error("Ocurrio un error en guardarContraseniaCerKeyRepresentanteLegal clienteDto viene null");
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			

			return mensajeDTO;
		}catch (BusinessException be) {
			
			
			if(representanteLegal.getContrasenaCertificado() == null || "".equals(representanteLegal.getContrasenaCertificado().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Contrase\u00f1a certificado'  para representante legal.");
			}
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarContraseniaCerKeyRepresentanteLegal ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value="/representanteLegal/eliminarRepresentanteLegal")
	@ResponseBody
	public MensajeDTO eliminarRepresentanteLegal(@RequestBody RepresentanteLegalDto representanteLegal, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {

			if(!clienteSeccionesBO.eliminarRepresentanteLegal(representanteLegal, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en eliminarRepresentanteLegal-eliminarRepresentanteLegal");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			
			return mensajeDTO;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarRepresentanteLegal ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/representanteLegal/obtenerDocumentosRepresentante", method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentoCSMDto> obtenerDocumentosRepresentante(@RequestBody Long idClienteRepLeg, Model model) {
		
		ClienteDto clienteDto = null;
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTE)) {
			clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		}else {
			return new ArrayList<DocumentoCSMDto>();
		}
			
		return clienteSeccionesBO.listDocumentosClienteRepresentante(clienteDto.getIdCliente(), idClienteRepLeg);
	}
	
	@RequestMapping(value = "/representanteLegal/guardarDocumentosClienteRepresentante")
    @ResponseBody
    public ResponseEntity<String> guardarDocumentosClienteRepresentante(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	 
		try {
			clienteSeccionesBO.guardarDocumentosClienteRepresentante(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentante", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentante", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	@RequestMapping(value = "/representanteLegal/obtenerDoctosClienteDocumentoRepresentanteCerKey", method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentoCSMDto> obtenerDoctosClienteDocumentoRepresentanteCerKey(@RequestBody Long idPrestadoraServRepLeg, Model model) {
		
		ClienteDto clienteDto = null;
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTE)) {
			clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		}else {
			return new ArrayList<DocumentoCSMDto>();
		}
		
		return clienteSeccionesBO.listDocumentosCerKeyRepresentanteCliente(clienteDto.getIdCliente(), idPrestadoraServRepLeg);
	}
	
	@RequestMapping(value = "/representanteLegal/guardarDocumentosClienteRepresentanteCerKey")
    @ResponseBody
    public ResponseEntity<String> guardarDocumentosClienteRepresentanteCerKey(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	 
		try {
			clienteSeccionesBO.guardarDocumentosClienteRepresentanteCerKey(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentanteCerKey", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentanteCerKey", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	 
    }
	
	@RequestMapping(value = "/representanteLegal/eliminarDocumentosRepresentante")
    @ResponseBody
    public ResponseEntity<String> eliminarDocumentosRepresentante(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	 
		try {
			clienteSeccionesBO.eliminarDocumentosRepresentante(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosRepresentante", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosRepresentante", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	 
    }
	
	@RequestMapping (value="/apoderadoLegal/cargaInicialApoderadoLegal")
	@ResponseBody
    public Map<String, Object>  cargaInicialApoderadoLegal(Model model) {
    	HashMap<String, Object> apoderado = new HashMap<String, Object>();
    		
		try {
			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			
			if(clienteDto!=null) {
				
				apoderado.put("gridApoderadosLegales", clienteSeccionesBO.getListApoderadoLegalByIdCliente(clienteDto.getIdCliente()));
				apoderado.put("catFacultades", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_FACULTADES.getCve()));
				
				
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialApoderadoLegal clienteDto viene null");
				throw new BusinessException("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialApoderadoLegal ", e);
			throw new BusinessException("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}

	return apoderado;
    }
	
	@RequestMapping(value="/apoderadoLegal/guardarApoderadoLegal")
	@ResponseBody
	public MensajeDTO guardarApoderadoLegal(@RequestBody ApoderadoLegalDto apoderadoLegal, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {


			if(apoderadoLegal!=null) {
				
				if(apoderadoLegal.getNombre() == null || "".equals(apoderadoLegal.getNombre().trim())
						|| apoderadoLegal.getApellidoPaterno() == null || "".equals(apoderadoLegal.getApellidoPaterno().trim())
						|| apoderadoLegal.getRfc() == null || "".equals(apoderadoLegal.getRfc().trim())
						|| apoderadoLegal.getCurp() == null || "".equals(apoderadoLegal.getCurp().trim())
						|| apoderadoLegal.getPoderNotarial() == null || "".equals(apoderadoLegal.getPoderNotarial().trim())
						|| apoderadoLegal.getCatFacultadDto() == null || (apoderadoLegal.getCatFacultadDto() != null && apoderadoLegal.getCatFacultadDto().getIdCatGeneral() == null)) {
					throw new BusinessException("", "");
				}
				
			}else {
				LOGGER.error("Ocurrio un error en guardarApoderadoLegal el objeto apoderadoLegal viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				return mensajeDTO;
			} 

			
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			if(clienteDto!=null) {
				
				apoderadoLegal.setClienteDto(clienteDto);
				if(!clienteSeccionesBO.guardarActualizarApoderadoLegal(apoderadoLegal, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en guardarApoderadoLegal-guardarActualizarApoderadoLegal");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				
			}else {
				LOGGER.error("Ocurrio un error en guardarApoderadoLegal clienteDto viene null");
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			
			return mensajeDTO;
		
		}catch (BusinessException be) {
			
			
			if(apoderadoLegal.getNombre() == null || "".equals(apoderadoLegal.getNombre().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Nombre' para apoderado legal.");
				
			}else if(apoderadoLegal.getApellidoPaterno() == null || "".equals(apoderadoLegal.getApellidoPaterno().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Apellido paterno'  para apoderado legal.");
				
			}else if(apoderadoLegal.getRfc() == null || "".equals(apoderadoLegal.getRfc().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'RFC'  para apoderado legal.");
				
			}else if(apoderadoLegal.getCurp() == null || "".equals(apoderadoLegal.getCurp().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'CURP'  para apoderado legal.");
				
			}else if(apoderadoLegal.getPoderNotarial() == null || "".equals(apoderadoLegal.getPoderNotarial().trim())) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar  'Poder notarial' para representante legal.");
				
			}else if(apoderadoLegal.getCatFacultadDto() == null || (apoderadoLegal.getCatFacultadDto() != null && apoderadoLegal.getCatFacultadDto().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Facultad'  para apoderado legal.");
			}
			
			return mensajeDTO;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarApoderadoLegal ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value="/apoderadoLegal/eliminarApoderadoLegal")
	@ResponseBody
	public MensajeDTO eliminarApoderadoLegal(@RequestBody ApoderadoLegalDto apoderadoLegalDto, Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {

			if(!clienteSeccionesBO.eliminarApoderadoLegal(apoderadoLegalDto, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en eliminarApoderadoLegal-eliminarApoderadoLegal");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
			
			return mensajeDTO;
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarApoderadoLegal ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
	
	@RequestMapping(value = "/apoderadoLegal/obtenerDocumentosApoderado", method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentoCSMDto> obtenerDocumentosApoderado(@RequestBody Long idClienteApodLeg, Model model) {
		
		ClienteDto clienteDto = null;
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTE)) {
			clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		}else {
			return new ArrayList<DocumentoCSMDto>();
		}
			
		return clienteSeccionesBO.listDocumentosClienteApoderado(clienteDto.getIdCliente(), idClienteApodLeg);
	}
	
	@RequestMapping(value = "/apoderadoLegal/guardarDocumentosClienteApoderado")
    @ResponseBody
    public ResponseEntity<String> guardarDocumentosClienteApoderado(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	 
		try {
			clienteSeccionesBO.guardarDocumentosClienteApoderado(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteApoderado", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteApoderado", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	 
    }
	
	@RequestMapping(value = "/apoderadoLegal/eliminarDocumentosApoderado")
    @ResponseBody
    public ResponseEntity<String> eliminarDocumentosApoderado(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	 
		try {
			clienteSeccionesBO.eliminarDocumentosApoderado(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosApoderado", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en eliminarDocumentosApoderado", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	 
    }
	
	@RequestMapping(value = "/nominaCliente/cargaInicialComisiones")
	@ResponseBody
	public Map<String, Object> cargaInicialComisiones(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

			NominaClienteDto nominaClienteDto = new NominaClienteDto();
			ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			List<CatGeneralDto>  listMaquila = new ArrayList<CatGeneralDto>();
			List<CatGeneralDto>  listMixto = new ArrayList<CatGeneralDto>();
			
			if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
				
				nominaClienteDto.setClienteDto(clienteDto);

				dataReturn.put("nominaCliente", nominaClienteDto);
				dataReturn.put("listaCelulasDto", celulaBO.listarTodasLasCelulas());
				dataReturn.put("listaProductosNomina", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PRODUCTO_NOMINA.getCve()));
				dataReturn.put("gridNominaCliente", nominaClienteBO.listaNominaCliente(clienteDto.getIdCliente()));
				dataReturn.put("catEsquema", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.PPP_SS.getCve()));
				dataReturn.put("tipoCanalVenta", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CANAL_VENTA.getCve()));
				dataReturn.put("catProcentajeComision", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.PORCENTAJE_COMISION.getCve()));
				dataReturn.put("catFormulaComision", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.COMISIONES_PPP.getId()));
				dataReturn.put("catFormulaPrincing", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.PRICING.getId()));
				
				List<CatGeneralDto>  esquemaMixtoMaquila = catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PRODUCTO_NOMINA.getCve());
				for(CatGeneralDto esquemaMaquila: esquemaMixtoMaquila) {
					if(esquemaMaquila.getIdCatGeneral() == 9949) {
						listMaquila.add(esquemaMaquila);
					}
					if(esquemaMaquila.getIdCatGeneral() == 9950) {
						listMixto.add(esquemaMaquila);
					}
				}
				dataReturn.put("catEsquemaMaquila", listMaquila);
				dataReturn.put("catEsquemaMixto", listMixto);
				return dataReturn;
			}else {
				LOGGER.error("Ocurrio un error en cargaInicialComisiones , el objeto clienteDto es vacio");
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
	}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialComisiones ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	 @RequestMapping(value = "/nominaCliente/guardarComision")
	    @ResponseBody
	    public MensajeDTO guardarComision(@RequestBody ClienteComisionHonorarioDto comision) throws BusinessException {
		 
		 MensajeDTO mensajeDTO = new MensajeDTO();
		 
	        try {
	        	
	        	if(comision != null) {
	        		if(comision.getEsquema() == null || comision.getEsquema()!=null && comision.getEsquema().getIdCatGeneral()==null
		        			|| comision.getUsuario() == null || comision.getUsuario()!=null && comision.getUsuario().getIdUsuario()==null) {
		        		throw new BusinessException("");
		        	}
	        		
	        		if(comision.getCanalVenta() == null || comision.getCanalVenta().getIdCatGeneral() == null) {
	        			throw new BusinessException("");
	        		}else {
	        			if(comision.getCanalVenta().getIdCatGeneral() == 61 || comision.getCanalVenta().getIdCatGeneral() == 64) {
	        				if(comision.getComisionPricing() == null || "".equals(comision.getComisionPricing().trim()) 
	        						|| comision.getFormulaPricing() == null || comision.getFormulaPricing().getIdCatGeneral() == null) {
	        					throw new BusinessException("");
	        				}
	        			}else {
	        				if(comision.getCanalVenta().getIdCatGeneral() == 60 || comision.getCanalVenta().getIdCatGeneral() == 62 || comision.getCanalVenta().getIdCatGeneral() == 63) {
	        					if(comision.getComision() == null || "".equals(comision.getComision().trim()) 
	        						|| comision.getFormulaComision() == null || comision.getFormulaComision().getIdCatGeneral() == null) {
	        					throw new BusinessException("");
	        				}else if(comision.getCanalVenta().getIdCatGeneral() == 65){
	        					if(comision.getPorcentajeComision() == null || comision.getFormulaComision() == null || comision.getFormulaComision().getIdCatGeneral() == null) {
	        						throw new BusinessException("");
	        					}
	        				}
	        				}
	        			}
	        		}

	        		
	        		clienteSeccionesBO.guardarComision(comision, getUser().getIdUsuario());
	        	}else {
	        		LOGGER.error("Ocurrio un error en guardarComision el objeto comision es null");
	        		throw new BusinessException("");
	        	}

	        	
	        	
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en guardarComision ", be);
	        	if(comision == null) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Ocurrio un error al intentar guardar la comisi&oacute;n.");
    				return mensajeDTO;
	        	}else if(comision.getEsquema() == null || comision.getEsquema()!=null && comision.getEsquema().getIdCatGeneral()==null) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Debe seleccionar 'Esquema de comisión'.");
    				return mensajeDTO;
	        	
	        	}else if(comision.getUsuario() == null || comision.getUsuario()!=null && comision.getUsuario().getIdUsuario()==null) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Debe seleccionar 'Comisionista'.");
    				return mensajeDTO;
	        	
	        	}else if(comision.getCanalVenta() == null || comision.getCanalVenta().getIdCatGeneral() == null) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Debe seleccionar 'Canal de venta'.");
    				return mensajeDTO;
        		}else {
        			if(comision.getCanalVenta().getIdCatGeneral() == 61 || comision.getCanalVenta().getIdCatGeneral() == 64) {
        				if(comision.getComisionPricing() == null || "".equals(comision.getComisionPricing().trim())) {
        					mensajeDTO.setCorrecto(false);
            				mensajeDTO.setMensajeError("Debe ingresar '% Pricing'.");
            				return mensajeDTO;
        				}
        				if(comision.getFormulaPricing() == null || comision.getFormulaPricing().getIdCatGeneral() == null) {
        					mensajeDTO.setCorrecto(false);
            				mensajeDTO.setMensajeError("Debe seleccionar 'Fórmula de pricing'.");
            				return mensajeDTO;
        				}
        			}else {
        				if(comision.getCanalVenta().getIdCatGeneral() == 60 || comision.getCanalVenta().getIdCatGeneral() == 62 || comision.getCanalVenta().getIdCatGeneral() == 63) {
        				if(comision.getComision() == null || "".equals(comision.getComision().trim())) {
        					mensajeDTO.setCorrecto(false);
            				mensajeDTO.setMensajeError("Debe ingresar '% Comisión'.");
            				return mensajeDTO;
        				}
        				}
        				if(comision.getFormulaComision() == null || comision.getFormulaComision().getIdCatGeneral() == null) {
        					mensajeDTO.setCorrecto(false);
            				mensajeDTO.setMensajeError("Debe seleccionar 'Fórmula de comisión'.");
            				return mensajeDTO;
        				}
        				if(comision.getCanalVenta().getIdCatGeneral() == 65) {
        					if(comision.getPorcentajeComision() == null) {
        						mensajeDTO.setCorrecto(false);
                				mensajeDTO.setMensajeError("Debe seleccionar '% Comisión'.");
                				return mensajeDTO;
        					}
        					if(comision.getFormulaComision() == null || comision.getFormulaComision().getIdCatGeneral() == null) {
            					mensajeDTO.setCorrecto(false);
                				mensajeDTO.setMensajeError("Debe seleccionar 'Fórmula de comisión'.");
                				return mensajeDTO;
            				}
        					
        				}
        			}
        		}
	        }
	        
			return mensajeDTO;

	    }
	 
	 
	 @RequestMapping(value = "/nominaCliente/obtenerUsuarioCanalVenta")
	    @ResponseBody
	    public List<UsuarioDTO> obtenerUsuarioCanalVenta(@RequestBody String idCanalVenta) throws BusinessException {
	        try {
	        	List<UsuarioDTO> list = canalVentaBO.obtenerUsuarioXTipoCanalVenta(idCanalVenta);
	        return 	list;
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en obtenerUsuarioCanalVenta ", be);
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 
	 @RequestMapping(value = "/nominaCliente/cargaInicialGridComisiones")
		@ResponseBody
		public Map<String, Object> cargaInicialGridComisiones(@RequestBody NominaClienteDto nomina) throws BusinessException {
			Map<String, Object> dataReturn = new HashMap<>();
			try {
					dataReturn.put("gridNominaComisiones", clienteSeccionesBO.obtenerNominaComisionesXIdNomina(nomina));
					return dataReturn;
		}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialGridComisiones ", e);
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
		}
	 
	 @RequestMapping(value = "/nominaCliente/eliminarComision")
	    @ResponseBody
	    public void eliminarComision(@RequestBody Long idNominaClienteComision) throws BusinessException {
	        try {
	        	clienteSeccionesBO.eliminarComision(idNominaClienteComision, getUser().getIdUsuario());
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en eliminarComision ", be);
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 
	 @RequestMapping(value = "/nominaCliente/cargaInicialHonorarios")
		@ResponseBody
		public Map<String, Object> cargaInicialHonorarios(Model model) throws BusinessException {
			
			Map<String, Object> dataReturn = new HashMap<>();
			
			try {

				NominaClienteDto nominaClienteDto = new NominaClienteDto();
				ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				
				if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
					
					nominaClienteDto.setClienteDto(clienteDto);

					dataReturn.put("nominaCliente", nominaClienteDto);
					dataReturn.put("listaCelulasDto", celulaBO.listarTodasLasCelulas());
					dataReturn.put("listaProductosNomina", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PRODUCTO_NOMINA.getCve()));
					dataReturn.put("gridNominaCliente", nominaClienteBO.listaNominaCliente(clienteDto.getIdCliente()));
					
					dataReturn.put("catEsquema", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.PPP_SS.getCve()));
					dataReturn.put("catFormulaHonorario", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP.getId()));
					dataReturn.put("catFormulaFactura", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_FACTURA.getId()));
					dataReturn.put("catTipoIVA", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_IVA.getId()));
					
					dataReturn.put("catFormulaHonorarioSS", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_FACTURA_SS.getId()));
					dataReturn.put("catFormulaHonorarioMixto", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_FACTURA_MIXTO.getId()));
					dataReturn.put("catFormulaHonorarioMaquila", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_FACTURA_MAQUILA.getId()));
					
					dataReturn.put("catTipoIVASs", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_SS_IVA.getId()));
					dataReturn.put("catFormulaFacturaSs", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_SS_FACTURA.getId()));
					dataReturn.put("catTipoIVAMixto", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_MIXTO_IVA.getId()));
					dataReturn.put("catFormulaFacturaMixto", catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_MIXTO_FACTURA.getId()));
					return dataReturn;
				}else {
					LOGGER.error("Ocurrio un error en cargaInicialHonorarios , el objeto clienteDto es vacio");
					throw new BusinessException ("Ocurrio un error en el sistema");
				}
		}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialHonorarios ", e);
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
		}
	 
	 @RequestMapping(value = "/nominaCliente/modificarCatalogoTipoIVA")
		@ResponseBody
		public Map<String, Object> modificarCatalogoTipoIVA(Model model) throws BusinessException {
			
			Map<String, Object> dataReturn = new HashMap<>();
			
			try {

				NominaClienteDto nominaClienteDto = new NominaClienteDto();
				ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				
				if(clienteDto!=null && clienteDto.getIdCliente()!=null) {
					List<CatGeneralDto> formulaFacturaList = catBo.catFormulasByIdTipoFormula(CatTipoFormulaEnum.HONORARIOS_PPP_FACTURA.getId());
					List<CatGeneralDto> catFormulaFactura = new ArrayList<CatGeneralDto>();
					for(CatGeneralDto factura: formulaFacturaList) {
						if(factura.getIdCatGeneral() == 8) {
							catFormulaFactura.add(factura);
						}
					}
					dataReturn.put("catFormulaFactura", catFormulaFactura);
					return dataReturn;
				}else {
					LOGGER.error("Ocurrio un error en modificarCatalogoTipoIVA , el objeto clienteDto es vacio");
					throw new BusinessException ("Ocurrio un error en el sistema");
				}
		}catch (Exception e) {
				LOGGER.error("Ocurrio un error en modificarCatalogoTipoIVA ", e);
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
		}
	 
	 
	 @RequestMapping(value = "/nominaCliente/guardarHonorario")
	    @ResponseBody
	    public MensajeDTO guardarHonorario(@RequestBody ClienteComisionHonorarioDto honorario) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();
	        try {
	        	if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 306) {
	        		if(honorario.getHonorarioPPPSs() == null || "".equals(honorario.getHonorarioPPPSs().trim())
	        				|| (honorario.getFormulaPPPSs() == null 
		        			|| (honorario.getFormulaPPPSs() != null && honorario.getFormulaPPPSs().getIdCatGeneral() == null))) {
		        		throw new BusinessException("");
		        	}
	        		honorario.setIvaComercial(null);
	        		honorario.setFormulaPPP(null);
	        	}else if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 9949){
	        		if(honorario.getFormulaPPPMaquila().getIdCatGeneral() == null) {
	        			throw new BusinessException("");
	        		}
	        		honorario.setIvaComercial(null);
	        		honorario.setFormulaPPP(null);
	        	}else if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 9950){
	        		if(honorario.getHonorarioPPPMixto() == null || honorario.getFormulaPPPMixto().getIdCatGeneral() == null) {
	        			throw new BusinessException("");
	        		}
	        		honorario.setIvaComercial(null);
	        		honorario.setFormulaPPP(null);
	        	}else {
	        	if(honorario.getHonorarioPPP() == null || "".equals(honorario.getHonorarioPPP().trim())
	        			|| honorario.getIvaComercial() == null || "".equals(honorario.getIvaComercial().trim())
	        			|| honorario.getFormulaPPP()== null 
	        			|| (honorario.getFormulaPPP() != null && honorario.getFormulaPPP().getIdCatGeneral() == null)) {
	        		throw new BusinessException("");
	        	}
	        	
	        	if(honorario.getFormulaFactura()== null 
	        			|| (honorario.getFormulaFactura() != null && honorario.getFormulaFactura().getIdCatGeneral() == null)
	        			|| honorario.getFormulaTipoIva()== null 
	        			|| (honorario.getFormulaTipoIva() != null && honorario.getFormulaTipoIva().getIdCatGeneral() == null)) {
	        		throw new BusinessException("");
	        	}
	        	}

	        	clienteSeccionesBO.guardarHonorario(honorario, getUser().getIdUsuario());
	        	
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en guardarHonorario ", be);
	        	if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 306) {
	        		if(honorario.getHonorarioPPPSs() == null || "".equals(honorario.getHonorarioPPPSs().trim())) {
	        			mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo '% sueldos y salarios'.");
	    				return mensajeDTO;
		        	}
	        		if((honorario.getFormulaPPPSs() == null 
		        			|| (honorario.getFormulaPPPSs() != null && honorario.getFormulaPPPSs().getIdCatGeneral() == null))){
	        			mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Formula sueldos y salarios'.");
	    				return mensajeDTO;
	        		}
	        	}else if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 9949){
	        		if(honorario.getFormulaPPPMaquila().getIdCatGeneral() == null) {
	        			mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Formula Maquila'.");
	    				return mensajeDTO;
	        		}
	        	}else if(honorario.getNominaCliente().getCatProductoNomina().getIdCatGeneral() == 9950){
	        		if(honorario.getHonorarioPPPMixto() == null) {
	        			mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo '% Honorario Mixto(PPP+IMSS)'.");
	    				return mensajeDTO;
	        		}
	        		if(honorario.getFormulaPPPMixto().getIdCatGeneral() == null) {
	        			mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Formula Honorario Mixto(PPP+IMSS)'.");
	    				return mensajeDTO;
	        		}
	        	}else {
	        	if(honorario.getFormulaPPP().getIdCatGeneral() == null
		        			|| (honorario.getFormulaPPP() != null && honorario.getFormulaPPP().getIdCatGeneral() == null)) {
		        		mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Formula Honorario ppp'.");
	    				return mensajeDTO;
	    				
		        }else if(honorario.getHonorarioPPP() == null || "".equals(honorario.getHonorarioPPP().trim())) {
		        		mensajeDTO.setCorrecto(false);
	    				mensajeDTO.setMensajeError("Debe ingresar '% Honorario PPP'.");
	    				return mensajeDTO;
	    				
		        } else if(honorario.getIvaComercial() == null || "".equals(honorario.getIvaComercial().trim())) {
        		mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar '% IVA comercial'.");
				return mensajeDTO;
				
		        }else if(honorario.getFormulaFactura()== null 
	        			|| (honorario.getFormulaFactura() != null && honorario.getFormulaFactura().getIdCatGeneral() == null)) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Formula factura'.");
    				return mensajeDTO;
    				
	        	}else if(honorario.getFormulaTipoIva()== null 
	        			|| (honorario.getFormulaTipoIva() != null && honorario.getFormulaTipoIva().getIdCatGeneral() == null)) {
	        		mensajeDTO.setCorrecto(false);
    				mensajeDTO.setMensajeError("Debe seleccionar el campo 'Tipo de IVA'.");
    				return mensajeDTO;
	        	}
	        	}
	        }
			return mensajeDTO;
	    }
	 
	 @RequestMapping(value = "/nominaCliente/eliminarHonorario")
	    @ResponseBody
	    public void eliminarHonorario(@RequestBody Long idNominaClienteHonorario) throws BusinessException {
	        try {
	        	clienteSeccionesBO.eliminarHonorario(idNominaClienteHonorario, getUser().getIdUsuario());
	        } catch (BusinessException be) {
	        	LOGGER.error("Ocurrio un error en eliminarHonorario ", be);
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 
	 @RequestMapping(value = "/nominaCliente/cargaInicialGridHonorarios")
		@ResponseBody
		public Map<String, Object> cargaInicialGridHonorarios(@RequestBody NominaClienteDto nomina) throws BusinessException {
			
			Map<String, Object> dataReturn = new HashMap<>();
			try {
					dataReturn.put("gridNominaHonorarios", clienteSeccionesBO.obtenerNominaHonorariosXIdNomina(nomina));
					return dataReturn;
		}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialGridHonorarios ", e);
				throw new BusinessException ("Ocurrio un error en el sistema");
			}
		}
	 
	 
		@RequestMapping(value = "/documentos/obtenerDocumentosCliente", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDocumentosCliente(Model model) {
						
			ClienteDto clienteDto = null;
			if(model.containsAttribute(ReferenciaSeguridad.CLIENTE)) {
				clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
				
			return clienteSeccionesBO.listDocumentosCliente(clienteDto.getIdCliente());
		}
	 
		@RequestMapping(value = "/documentos/guardarDocumentosCliente")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosCliente(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				clienteSeccionesBO.guardarDocumentosCliente(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosCliente ", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosCliente ", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
	    }
		
		@RequestMapping(value = "/documentos/eliminarDocumentosCliente")
	    @ResponseBody
	    public ResponseEntity<String> eliminarDocumentosCliente(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				clienteSeccionesBO.eliminarDocumentosCliente(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosCliente ", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosCliente ", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
	@RequestMapping(value = "/productos/listaClienteProductos.json")
	@ResponseBody
	public Map<String, Object> getProductos(@RequestBody Long idcliente) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();
		try {
			List<ClienteProductoDto> listProductos = new ArrayList<ClienteProductoDto>();
			listProductos = productoBo.getProductosByIdCliente(idcliente);
			dataReturn.put("productos", listProductos);
			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaProductos ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/productos/guardarProductoGral.json")
	@ResponseBody
	public void guardarProducto(@RequestBody ClienteProductoDto producto, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();
		try {
			
			productoBo.guardarProducto(producto, usuarioAplicativo);
			
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarProductos ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}

}
