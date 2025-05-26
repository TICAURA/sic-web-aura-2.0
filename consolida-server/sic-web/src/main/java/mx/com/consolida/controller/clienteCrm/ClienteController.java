package mx.com.consolida.controller.clienteCrm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.ClienteBO;
import mx.com.consolida.crm.service.interfaz.NominaClienteBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.TipoPersonaEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.consolida.util.ConstantesComunes;

@Controller
@RequestMapping("clienteCrm")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CLIENTE_TEMP, ReferenciaSeguridad.CLIENTE
		, ReferenciaSeguridad.ES_AGREGAR_CLIENTE, ReferenciaSeguridad.ID_NOMINA_CLIENTE})
public class ClienteController extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteBO clienteBO;

	@Autowired
	private CatalogoBO catBo;

	@Autowired
	private CelulaBO celulaBO;

	@Autowired
	private UsuarioBO usuarioBO;

	@Autowired
	private NominaClienteBO nominaClienteBo;

	@Autowired
	private PrestadoraServicioBO prestadoraServicioBO;

	@RequestMapping(value = "/cargaInicialPospectosAutorizar")
	@ResponseBody
	public List<ClienteDto> cargaInicialPospectosAutorizar(Model model) {
		try {

			List<ClienteDto> listaProspectos = clienteBO.listarProspectosAutorizar();
			if(listaProspectos!=null && !listaProspectos.isEmpty()) {
				return listaProspectos;
			}else {
				return null;
			}


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial ", e);
			return Collections.emptyList();
		}
	}

	@RequestMapping(value = "/cargaInicialProspectosAutorizadosByMesaControl")
	@ResponseBody
	public List<ClienteDto> cargaInicialProspectosAutorizadosByMesaControl(Model model) {
		try {

			List<ClienteDto> listaProspectos = clienteBO.listaProspectosAutorizadosByMesaControl(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
			return listaProspectos;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialProspectosAutorizadosByMesaControl ", e);
			return Collections.emptyList();
		}
	}

	@RequestMapping(value = "/buscarCliente")
	@ResponseBody
	public List<ClienteDto> buscarCliente(@RequestBody String rfc) {
		List<ClienteDto> listaClientes = new ArrayList<ClienteDto>();
		List<ClienteDto> regresaListaConEncontrado = new ArrayList<ClienteDto>();

		listaClientes = clienteBO.listaProspectosAutorizadosByMesaControl(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
		if(listaClientes!=null && !listaClientes.isEmpty()) {
			rfc=rfc.toUpperCase();
			if(!rfc.isEmpty()) {

				for (ClienteDto cliente : listaClientes) {

//					if (cliente.getRfc() != null) {
//						if (cliente.getRfc().contains(rfc)) {
//							regresaListaConEncontrado.add(cliente);
//						}
//					}

					if (cliente.getNombre() != null) {
						if (cliente.getNombre().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}
					if (cliente.getApellidoPaterno() != null) {
						if (cliente.getApellidoPaterno().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}
					if (cliente.getApellidoMaterno() != null) {
						if (cliente.getApellidoMaterno().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}

					if (cliente.getRazonSocial() != null) {
						if (cliente.getRazonSocial().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}

					if (cliente.getNombreRazonSocial() != null) {
						if (cliente.getNombreRazonSocial().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}

					if(cliente.getCatGrupo() !=null && cliente.getCatGrupo().getDescripcionRazonSocial()!=null) {
						if (cliente.getCatGrupo().getDescripcionRazonSocial().toUpperCase().contains(rfc)) {
							regresaListaConEncontrado.add(cliente);
						}
					}
				}

				if(regresaListaConEncontrado!=null && !regresaListaConEncontrado.isEmpty()) {
					return regresaListaConEncontrado;
				}else {
					return listaClientes;
				}

			}else {
				return listaClientes;
			}

		}else {
			LOGGER.info("No hay lista para regresar en buscarCliente");
			return Collections.emptyList();
		}

	}


	@RequestMapping(value = "/obtieneClienteDtoParaEditar")
	@ResponseBody
	public void obtieneClienteDtoParaEditar(@RequestBody ClienteDto clienteDto, Model model, HttpServletResponse response) {

		if(clienteDto.getCatGiroComercialDto() != null && clienteDto.getCatGiroComercialDto().getIdCatGeneral()!=null ) {
			clienteDto.setListaSubGiroComercialDto(catBo.obtenerCatSubgiroXIdGiro(clienteDto.getCatGiroComercialDto().getIdCatGeneral()));
		}
		if(clienteDto.getCelula()!=null && clienteDto.getCelula().getIdCelula()!=null) {
			clienteDto.setListaNoministas(listaNoministasByidCelula(clienteDto.getCelula().getIdCelula(), model, response).getListaNoministas());
		}

		model.addAttribute(ReferenciaSeguridad.CLIENTE, clienteDto);
	}

	@RequestMapping(value = "/editarProspectoAutorizado")
	@ResponseBody
	public ClienteDto editarProspectoAutorizado(Model model, HttpServletResponse response) {

		ClienteDto clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);

		return clienteDto;
	}

	@RequestMapping(value = "/cargaCatGrupo")
	@ResponseBody
	public List<CatGrupoDto> cargaCatGrupo()  throws BusinessException  {
		try {

			return catBo.obtenerCatalogoGrupo();

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaCatGrupo ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/cargaCatRegimenFiscal")
	@ResponseBody
	public List<CatGeneralDto> cargaCatRegimenFiscal()  throws BusinessException  {
		try {

			return catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_REGIMEN_FISCAL.getCve());

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaCatGiroComercial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/cargaCatGiroComercial")
	@ResponseBody
	public List<CatGeneralDto> cargaCatGiroComercial()  throws BusinessException  {
		try {

			return catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve());

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaCatGiroComercial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}


	@RequestMapping(value = "/listaCatSubGiroComercial/{idGiro}", method = RequestMethod.GET)
	@ResponseBody
	public List<CatSubGiroComercialDto> listaCatSubGiroComercial(@PathVariable("idGiro") Long idGiro, HttpServletResponse response) throws BusinessException {

		try{

			List<CatSubGiroComercialDto> lista = catBo.obtenerCatSubgiroXIdGiro(idGiro);

			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCatSubGiroComercial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}


	}

	@RequestMapping(value = "/listaCelulas")
	@ResponseBody
	public List<CelulaDto> listaCelulas() throws BusinessException {

		try {

			List<CelulaDto> celula = celulaBO.listarTodasLasCelulas();

			return celula;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCelulas ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/listaCatTipoPago")
	@ResponseBody
	public List<CatGeneralDto> listaCatTipoPago() throws BusinessException {
		try {
			return catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.METODO_PAGO.getCve());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCatTipoPago ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/cargaCatCategoria")
	@ResponseBody
	public List<CatGeneralDto> cargaCatCategoria() throws BusinessException {
		try {
			return catBo.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(CatMaestroEnum.CATEGORIA.getCve());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaCatCategoria ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/listaNoministas/{idCelula}", method = RequestMethod.GET)
	@ResponseBody
	public ClienteDto listaNoministasByidCelula(@PathVariable("idCelula") Long idCelula, Model model, HttpServletResponse response) throws BusinessException {

		try {

			ClienteDto clienteDto = new ClienteDto();
			clienteDto.setCelula(new CelulaDto(idCelula));

			List<UsuarioDTO> noministas = null;
			if(clienteDto.getCelula() != null && clienteDto.getCelula().getIdCelula() != null) {
				noministas = celulaBO.consultarUsuariosByCelulaRol(clienteDto.getCelula().getIdCelula(), RolUsuarioENUM.NOMINISTA.getId());
			}else if(clienteDto.getCelula() != null && clienteDto.getCelula().getIdCelula() == null) {
				clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				if(clienteDto!=null && clienteDto.getCelula()!=null&&clienteDto.getCelula().getIdCelula()!=null) {
					noministas = celulaBO.consultarUsuariosByCelulaRol(clienteDto.getCelula().getIdCelula(), RolUsuarioENUM.NOMINISTA.getId());
				}
			}else if(clienteDto == null || clienteDto.getIdCliente() == null) {
				clienteDto = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
				if(clienteDto!=null && clienteDto.getCelula()!=null&&clienteDto.getCelula().getIdCelula()!=null) {
					noministas = celulaBO.consultarUsuariosByCelulaRol(clienteDto.getCelula().getIdCelula(), RolUsuarioENUM.NOMINISTA.getId());
				}
			}

			ClienteDto cliente = new ClienteDto();

			if(clienteDto!=null && clienteDto.getCelula()!=null&&clienteDto.getCelula().getIdCelula()!=null) {

				List<NoministaDto> listaNoministas = new ArrayList<>();


				if(noministas!=null && !noministas.isEmpty()) {
					for(UsuarioDTO usu : noministas ) {
						NoministaDto nominista = new NoministaDto();
						nominista.setIdPersona(usu.getIdPersona());
						nominista.setNombre(usu.getNombre());
						nominista.setApellidoPaterno(usu.getPrimerApellido());
						nominista.setApellidoMaterno(usu.getSegundoApellido()!=null ? usu.getSegundoApellido() : "".trim());
						nominista.setNombreCompletoNominista(usu.getNombre() + " " + usu.getPrimerApellido()+ " " + nominista.getApellidoMaterno());
						nominista.setIdNominista(usu.getIdUsuario());
						listaNoministas.add(nominista);
					}

					cliente.setListaNoministas(listaNoministas);

				}else {
					cliente.setListaNoministas(null);
				}
			}else {
				cliente.setListaNoministas(null);
			}


			return cliente;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNoministasByidCelula ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}


	@RequestMapping(value = "/autorizarProspecto", method = RequestMethod.POST)
	@ResponseBody
	public ClienteDto autorizarProspecto(@RequestBody ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo, Model model) throws BusinessException {
		try {

			if (clienteDto.getRfc() != null && !"".equals(clienteDto.getRfc().trim())) {
				if(clienteBO.getIdCLienteByRfc(clienteDto.getRfc())!=null) {
					LOGGER.error("El RFC a registrar ya existe");
					throw new BusinessException("El RFC ingresado ya existe, favor de verificar");
				}
			}

			ClienteDto cambiaEstatusProspecto = clienteBO.cambiarEstatusProspecto(clienteDto, usuarioAplicativo);

			if(cambiaEstatusProspecto!=null && cambiaEstatusProspecto.getIdCliente()!=null) {
				return cambiaEstatusProspecto;
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusProspecto ");
			    return 	new ClienteDto();
			}

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en cambiaEstatusProspecto ", be);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/declinarProspecto")
	@ResponseBody
	public Boolean declinarProspecto(@RequestBody ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo, Model model) {
		try {

			return(clienteBO.declinarProspecto(clienteDto, usuarioAplicativo));

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusProspecto ", e);
			return Boolean.FALSE;
		}
	}



	@RequestMapping(value = "/guardarGeneralesCliente", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO guardarGeneralesCliente(@RequestBody ClienteDto clienteDto, Model model)
			throws BusinessException {
		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();

			if (clienteDto.getCatTipoPersona() == null
					|| clienteDto.getCatGrupo() == null
					|| (clienteDto.getRfc() == null || (clienteDto.getRfc() != null && "".equals(clienteDto.getRfc().trim())))
				    || (clienteDto.getCveRegistroPatronal()==null || "".equals(clienteDto.getCveRegistroPatronal().trim()))
					|| clienteDto.getFechaConstitucionEmpresa() == null
					|| clienteDto.getCatCategoria() == null || (clienteDto.getCatCategoria() != null && clienteDto.getCatCategoria().getIdCatGeneral() == null)
					|| clienteDto.getCatRegimenFiscal() == null || (clienteDto.getCatRegimenFiscal() != null && clienteDto.getCatRegimenFiscal().getIdCatGeneral() == null)
					|| clienteDto.getCelula() == null || (clienteDto.getCelula() != null && clienteDto.getCelula().getIdCelula() == null)
					|| clienteDto.getPrestadoraServicioFondo() == null || (clienteDto.getPrestadoraServicioFondo() != null && clienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() == null)
					|| (clienteDto.getActividadEconomicaFinal() == null || (clienteDto.getActividadEconomicaFinal() != null && "".equals(clienteDto.getActividadEconomicaFinal().trim())))) {
				throw new BusinessException("", "");
			}


			if(clienteDto.getCatTipoPersona()!=null) {
				if(TipoPersonaEnum.FISICA.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
					if(clienteDto.getNombre() == null || "".equals(clienteDto.getNombre().trim())
						|| (clienteDto.getApellidoPaterno() == null || "".equals(clienteDto.getApellidoPaterno().trim()))
						|| ConstantesComunes.LONGITUD_RFC_FISICA != clienteDto.getRfc().length()) {
						throw new BusinessException("", "");
					}
				}else if(TipoPersonaEnum.MORAL.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
					if(clienteDto.getRazonSocial()==null || "".equals(clienteDto.getRazonSocial().trim())
							|| ConstantesComunes.LONGITUD_RFC_MORAL != clienteDto.getRfc().length()) {
						throw new BusinessException("", "");
					}
				}
			}

			if(clienteDto.getIdCliente() == null) {
				if(clienteBO.existeClienteEnCelula(clienteDto.getCelula().getIdCelula(), clienteDto.getRfc().trim())) {
					LOGGER.error("El RFC a registrar ya existe para la celula seleccionada: " + clienteDto.getRfc());
					throw new BusinessException("", "");
				}
			}


			if (model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo = (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}else {
				usuarioAplicativo.setIdUsuario(getUser().getIdUsuario());
			}

			Long idCliente = clienteBO.registrarActualizarCliente(clienteDto, usuarioAplicativo);
			if(idCliente > 0) {
				mensajeDTO.setCorrecto(true);
				mensajeDTO.setMensaje(String.valueOf(idCliente));
				return mensajeDTO;
			}else {
				LOGGER.error("Ocurrio in error en guardarGeneralesCliente");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
				return mensajeDTO;
			}

		} catch (BusinessException be) {

			if (clienteDto.getCatTipoPersona() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar el 'tipo de persona'");
				return mensajeDTO;
			}

			if (clienteDto.getCatGrupo() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar el 'Cliente (nombre comercial o grupo)'");
				return mensajeDTO;
			}

			if (clienteDto.getRfc() == null ||
					(clienteDto.getRfc() !=null && "".equals(clienteDto.getRfc().trim()))) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar el 'RFC'");
				return mensajeDTO;
			}

			 if(clienteDto.getCelula() == null || (clienteDto.getCelula() != null && clienteDto.getCelula().getIdCelula() == null)) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe seleccionar  'Celula'");
					return mensajeDTO;
			}

			if(clienteDto.getPrestadoraServicioFondo() == null || (clienteDto.getPrestadoraServicioFondo() != null && clienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Prestadora de servicio (fondo)'");
				return mensajeDTO;
			}


			if (clienteDto.getCatTipoPersona() != null) {
				if (TipoPersonaEnum.FISICA.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
					if (clienteDto.getNombre() == null || "".equals(clienteDto.getNombre().trim())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Nombre'");
						return mensajeDTO;
					}else if(clienteDto.getApellidoPaterno() == null || "".equals(clienteDto.getApellidoPaterno().trim())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Apellido paterno'");
						return mensajeDTO;
					}else if(ConstantesComunes.LONGITUD_RFC_FISICA != clienteDto.getRfc().length()) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Favor de revisar el RFC, el n\u00famero de caracteres no corresponde a la de persona f\u00edsica");
						return mensajeDTO;
					}


				} else if (TipoPersonaEnum.MORAL.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
					if (clienteDto.getRazonSocial() == null || "".equals(clienteDto.getRazonSocial().trim())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Debe ingresar 'Raz\u00f3n social'");
						return mensajeDTO;
					}else if(ConstantesComunes.LONGITUD_RFC_MORAL != clienteDto.getRfc().length()) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Favor de revisar el RFC, el n\u00famero de caracteres no corresponde a la de persona moral");
						return mensajeDTO;
					}
				}
			}

			if (clienteDto.getFechaConstitucionEmpresa() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Fecha constituci\u00f3n de la empresa'");
				return mensajeDTO;
			}

			if (clienteDto.getCveRegistroPatronal() == null
					|| (clienteDto.getCveRegistroPatronal()!=null && "".equals(clienteDto.getCveRegistroPatronal().trim()))) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Clave registro patronal'");
				return mensajeDTO;
			}

			if(clienteDto.getIdCliente() == null) {
				if(clienteBO.existeClienteEnCelula(clienteDto.getCelula().getIdCelula(), clienteDto.getRfc().trim())) {
					LOGGER.error("El RFC a registrar ya existe para la celula seleccionada: " + clienteDto.getRfc());
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("El RFC a registrar ya existe para la celula seleccionada, favor de verificar");
					return mensajeDTO;
				}
			}

			if(clienteDto.getCatCategoria() == null || (clienteDto.getCatCategoria() != null && clienteDto.getCatCategoria().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Categoria'");
				return mensajeDTO;
			}

			if(clienteDto.getCatRegimenFiscal() == null || (clienteDto.getCatRegimenFiscal() != null && clienteDto.getCatRegimenFiscal().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar  'Regimen fiscal'");
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

	@RequestMapping(value = "/esAgregarCliente")
	@ResponseBody
	public void esAgregarCliente(@RequestBody Boolean esAgregarCliente, Model model) {

		model.addAttribute(ReferenciaSeguridad.ES_AGREGAR_CLIENTE, esAgregarCliente);

	}


	@RequestMapping(value = "/obtenerValorAgregarCliente")
	@ResponseBody
	public Boolean obtenerValorAgregarCliente(Model model) {

		Boolean esAgregarCliente = (Boolean) model.asMap().get(ReferenciaSeguridad.ES_AGREGAR_CLIENTE);

		return esAgregarCliente;
	}

	@RequestMapping(value = "/eliminarCliente")
	@ResponseBody
	public Boolean eliminarCliente(@RequestBody Long idCliente) {


		try {

			if(!clienteBO.eliminarCliente(idCliente, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en eliminarCliente - eliminarCliente");
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarCliente - eliminarCliente ", e);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	@RequestMapping(value = "/actualizaNomina/cargaInicial")
	@ResponseBody
	public Map<String, Object> anCargaInicial(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		ClienteDto cliente = (ClienteDto) model.asMap().get(ReferenciaSeguridad.CLIENTE);
		params.put("cliente", cliente);
		if(model.containsAttribute(ReferenciaSeguridad.ID_NOMINA_CLIENTE)) {
			params.put("nominaCliente", nominaClienteBo.nominaClientebyId((Long) model.asMap().get(ReferenciaSeguridad.ID_NOMINA_CLIENTE)));
		}
		params.put("nominasByCliente", nominaClienteBo.listaNominaCliente(cliente.getIdCliente()));
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/guardarColaborador")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> anGuardarColaborador(@RequestBody ColaboradorDto colaborador, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
		UsuarioAplicativo us = (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		nominaClienteBo.guardarColaborador(colaborador,us);
		}
		catch (Exception e) {
			if(e.getCause().getMessage().contains("Duplicate entry")) {
				params.put("mensajeError","El RFC: "+colaborador.getRfc()+" ya se encuentra registrado");
				return new ResponseEntity<>(params,HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				params.put("mensajeError","Ocurrio un error al cargar los datos.");
				return new ResponseEntity<>(params,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(params,HttpStatus.OK);
	}

	@RequestMapping(value = "/actualizaNomina/editarColaborador")
	@ResponseBody
	public Map<String, Object> anEditarColaborador(@RequestBody ColaboradorDto colaborador, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		UsuarioAplicativo us = (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		nominaClienteBo.editarColaborador(colaborador,us);
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/obtenerColaboradores")
	@ResponseBody
	public Map<String, Object> anGuardarColaborador(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("listColaboradores",nominaClienteBo.obtenercolaboradoresByidNomina((Long) model.asMap().get(ReferenciaSeguridad.ID_NOMINA_CLIENTE)));
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/obtenerColaboradorById")
	@ResponseBody
	public Map<String, Object> anGuardarColaborador(@RequestBody Long idColaborador, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("colaborador",nominaClienteBo.obtenerColaboradorById(idColaborador));
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/obtenerPeriodos")
	@ResponseBody
	public Map<String, Object> anObtenerPeriodos(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("periodos",nominaClienteBo.obtenerPeriodo((Long) model.asMap().get(ReferenciaSeguridad.ID_NOMINA_CLIENTE)));
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/obtenerPeriodosFechas")
	@ResponseBody
	public Map<String, Object> anbtenerPeriodosFechas(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("listPeriodos",nominaClienteBo.obtenerPeriodosFechas((Long) model.asMap().get(ReferenciaSeguridad.ID_NOMINA_CLIENTE)));
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/calcularPeriodo")
	@ResponseBody
	public Map<String, Object> anCalcularPeriodo(@RequestBody NominaPeriodicidadDto dto, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		UsuarioAplicativo us = (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		nominaClienteBo.calcularPeriodo(dto, us);
		return params;
	}

	@RequestMapping(value = "/actualizaNomina/guardarDocumentoColaborador")
    @ResponseBody
    public ResponseEntity<String> guardarDocumentoColaborador(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {

		try {
			nominaClienteBo.guardarDocumentoColaborador(documento, usuarioAplicativo);
		} catch (IOException  e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosCliente ", e);
			return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosCliente ", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(HttpStatus.OK);
    }

	@RequestMapping(value = "/actualizaNomina/eliminarDocumentoColaborador")
    @ResponseBody
	public void eliminarDocumentoColaborador(@RequestBody DocumentoCSMDto documento) {
		nominaClienteBo.eliminarDocumentoColaborador(documento.getIdColaboradorDocumento());
	}

	@RequestMapping(value = "/getPrestadorasByIdCelula")
	@ResponseBody
	public Map<String, Object> getPrestadorasByIdCelula(@RequestBody Long idCelula,  Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {

			dataReturn.put("listaPrestadorasFondo", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(idCelula, ConstantesComunes.ES_FONDO));
			dataReturn.put("listaPrestadoras", prestadoraServicioBO.listaPrestdorasFondoYSinFondoByIdCelula(idCelula, ConstantesComunes.NO_ES_FONDO));

			return dataReturn;


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

}
