package mx.com.consolida.controller.clienteCotizador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.controller.clienteCrm.ClienteController;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.ObservacionAutorizadorDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.admin.CanalVentaBO;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.interfaz.ClienteTempBO;
import mx.com.consolida.service.interfaz.CotizadorBO;

@Controller
@RequestMapping("cliente")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CLIENTE_TEMP, ReferenciaSeguridad.COTIZADOR, ReferenciaSeguridad.CLIENTE_TEMP_EDITAR,
		ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES })
public class ClienteTempController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteTempBO nuevoClienteBO;
	
	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private CotizadorBO cotizadorBo;
	@Autowired
	private CanalVentaBO canalVentaBo;
	
	@RequestMapping(value = "/cargaInicial")
	@ResponseBody
	public List<ClienteTempDto> cargaInicial(Model model) {
		List<ClienteTempDto> clienteTempDto = new ArrayList<ClienteTempDto>();
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		clienteTempDto = nuevoClienteBO.obtenerClientes(usuarioAplicativo);
		model.addAttribute(ReferenciaSeguridad.COTIZADOR, 0L);
		return clienteTempDto;
	}
	
	@RequestMapping(value = "/obtenerPromotores")
	@ResponseBody
	public List<CanalVentaDto> obtenerPromotores(Model model) {
		return canalVentaBo.obtenerPromotores();
	}
	
	@RequestMapping(value = "/buscar")
	@ResponseBody
	public List<ClienteTempDto> buscar(@RequestBody String rfc, Model model) {
		List<ClienteTempDto> clienteTempDto = new ArrayList<ClienteTempDto>();
		List<ClienteTempDto> regresaListaConEncontrado = new ArrayList<ClienteTempDto>();
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		clienteTempDto = nuevoClienteBO.obtenerClientes(usuarioAplicativo);
		
		if(!rfc.isEmpty()) {
		
			for (ClienteTempDto cliente : clienteTempDto) {
				if (cliente.getRfc() != null) {
					if (cliente.getRfc().contains(rfc)) {
						regresaListaConEncontrado.add(cliente);
					}
				}
				if (cliente.getNombre() != null) {
					if (cliente.getNombre().contains(rfc)) {
						regresaListaConEncontrado.add(cliente);
					}
				}
				if (cliente.getApellidoPaterno() != null) {
					if (cliente.getApellidoPaterno().contains(rfc)) {
						regresaListaConEncontrado.add(cliente);
					}
				}
				if (cliente.getApellidoMaterno() != null) {
					if (cliente.getApellidoMaterno().contains(rfc)) {
						regresaListaConEncontrado.add(cliente);
					}
				}
				if (cliente.getRazonSocial() != null) {
					if (cliente.getRazonSocial().contains(rfc)) {
						regresaListaConEncontrado.add(cliente);
					}
				}
			}
			
				return regresaListaConEncontrado;
		}else {
			return clienteTempDto;
		}
	}
	
	@RequestMapping(value = "/verCotizaciones")
	public void verCotizaciones(@RequestBody Long idCliente, Model model) {
		model.addAttribute(ReferenciaSeguridad.CLIENTE_TEMP, idCliente);
	}
	
	@RequestMapping(value = "/nuevoIdClienteTempBitacoraSolicitudes")
	public void nuevoIdClienteTempBitacoraSolicitudes(@RequestBody Long idClienteTempBitacoraSolicitudes, Model model) {
		model.addAttribute(ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES, idClienteTempBitacoraSolicitudes);
	}
	
	@RequestMapping(value = "/verEditarProspecto")
	public void verEditarProspecto(@RequestBody Long idCliente, Model model) {
		model.addAttribute(ReferenciaSeguridad.CLIENTE_TEMP_EDITAR, idCliente);
	}
	
	@RequestMapping(value = "/nuevoProspecto")
	public void nuevoProspecto(Model model) {
		model.addAttribute(ReferenciaSeguridad.CLIENTE_TEMP_EDITAR, 0L);
	}
	
	@RequestMapping(value = "/cargaInicialCliente")
	@ResponseBody
	public ClienteTempDto cargaInicialCliente(Model model) {
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTE_TEMP)) {
			Long idCliente = (Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP);
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto = nuevoClienteBO.obtenerClienteById(idCliente);
			model.addAttribute(ReferenciaSeguridad.COTIZADOR, 0L);
			return clienteTempDto;
		}else {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
//			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerBitacoraSolicitudesCotizacion());
			
			return clienteTempDto;
		}
	}
	
	@RequestMapping(value = "/cargaInicialSolicitudes")
	@ResponseBody
	public ClienteTempDto cargaInicialSolicitudes(Model model) {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerBitacoraSolicitudesCotizacion());
			model.addAttribute(ReferenciaSeguridad.COTIZADOR, 0L);
			return clienteTempDto;
	}
	
	@RequestMapping(value = "/cargaInicialCotizaciones")
	@ResponseBody
	public ClienteTempDto cargaInicialCotizaciones(Model model) {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerBitacoraCotizacion(45L));
			clienteTempDto.setCatEstatus(catBo.obtenerCatEstatusInicialCotizaciones());
			model.addAttribute(ReferenciaSeguridad.COTIZADOR, 0L);
			return clienteTempDto;
	}
	
	@RequestMapping(value = "/cargaInicialPreCotizaciones")
	@ResponseBody
	public ClienteTempDto cargaInicialPreCotizaciones(Model model) {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerBitacoraCotizacion(44L));
			clienteTempDto.setCatEstatus(catBo.obtenerCatEstatusInicialCotizaciones());
			model.addAttribute(ReferenciaSeguridad.COTIZADOR, 0L);
			return clienteTempDto;
	}
	
	@RequestMapping(value="/guardar")
	@ResponseBody
	public MensajeDTO guardarClienteNuevo(@RequestBody ClienteTempDto cliente, Model model) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();
			try {
				UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
				
			if(cliente.getIdTipoPersona() == null || cliente.getIdPersonaContacto() == null || cliente.getIdPersonaContacto().getNombrePersona() == null
					|| cliente.getIdPersonaContacto().getApellidoPaterno() == null 
					|| cliente.getIdPersonaContacto().getCorreoElectronico() == null || cliente.getIdPersonaContacto().getTelefono() == null  || cliente.getIdMedioContacto() == null
					||  cliente.getIdMedioContacto().getCodigoPostal() == null) {
				throw new BusinessException("","");
			}
			
			if(cliente.getIdTipoPersona().getIdCatGeneral() == 21) {
				if(cliente.getNombre() == null) {
					throw new BusinessException("");
				}
				
				if(cliente.getApellidoPaterno() == null) {
					throw new BusinessException("");
				}
			}
			
			
			if(cliente.getIdTipoPersona().getIdCatGeneral() == 22) {
				if(cliente.getRazonSocial() == null) {
					throw new BusinessException("");
				}
				}
			
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}
				nuevoClienteBO.guardar(cliente, usuarioAplicativo);
				
				
			}catch(BusinessException be){
				
				if(cliente.getIdTipoPersona() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe seleccionar el campo 'TIPO DE PERSONA', de la sección DATOS GENERALES");
			         return mensajeDTO;
				}
				
				if(cliente.getIdTipoPersona().getIdCatGeneral() == 21) {
				if(cliente.getNombre() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'NOMBRE(S)', de la sección DATOS GENERALES");
			         return mensajeDTO;
				}
				
				if(cliente.getApellidoPaterno() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'APELLIDO PATERNO', de la sección DATOS GENERALES");
			         return mensajeDTO;
				}
				}
				
				
				if(cliente.getIdTipoPersona().getIdCatGeneral() == 22) {
					if(cliente.getRazonSocial() == null) {
						mensajeDTO.setCorrecto(false);
				        mensajeDTO.setMensajeError("Debe agregar el campo 'RAZÓN SOCIAL', de la sección DATOS GENERALES");
				         return mensajeDTO;
					}
					}
				
				if(cliente.getIdPersonaContacto() == null || cliente.getIdPersonaContacto().getNombrePersona() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'NOMBRE', de la sección DATOS DE CONTACTO");
			         return mensajeDTO;
				}
				
				if(cliente.getIdPersonaContacto() == null || cliente.getIdPersonaContacto().getApellidoPaterno() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'APELLIDO PATERNO', de la sección DATOS DE CONTACTO");
			         return mensajeDTO;
				}
				
				if(cliente.getIdPersonaContacto() == null || cliente.getIdPersonaContacto().getCorreoElectronico() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'CORREO ELECTRÓNICO', de la sección DATOS DE CONTACTO");
			         return mensajeDTO;
				}
				
				if(cliente.getIdPersonaContacto() == null || cliente.getIdPersonaContacto().getTelefono() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'NÚMERO DE TELÉFONO', de la sección DATOS DE CONTACTO");
			         return mensajeDTO;
				}
				
				if(cliente.getIdMedioContacto() == null || cliente.getIdMedioContacto().getCodigoPostal() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'CÓDIGO POSTAL', de la sección DOMICILIO DE PROSPECTO");
			         return mensajeDTO;
				}
	  }
			return mensajeDTO;
	}
	
	@RequestMapping(value="/guardarGrupo")
	@ResponseBody
	public void guardarNuevoGrupo(@RequestBody CatGrupoDto grupo) throws BusinessException {
			try {
				
			
			if(grupo.getDescripcionRazonSocial() == null) {
				throw new BusinessException("","");
			}
				nuevoClienteBO.guardarNuevoGrupo(grupo);
			}catch(BusinessException be){
				throw new BusinessException(be.getCodigo(), be.getMessage());
	  }	
	}
	
	@RequestMapping(value = "/cargaInicialAutorizador")
	@ResponseBody
	public ClienteTempDto cargaInicialAutorizador() {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerBitacoraSolicitudesAutorizador());
			return clienteTempDto;
		
	}
	
	@RequestMapping(value="/guardarObservacionAutorizador")
	@ResponseBody
	public void guardarObservacionAutorizador(@RequestBody ObservacionAutorizadorDto observacion, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
			try {
				
			if(observacion.getIdCotizacion() == null) {
				throw new BusinessException("","");
			}
			
			if(observacion.getObservacionAutorizador() == null) {
				throw new BusinessException("","");
			}
			nuevoClienteBO.guardarObservacionAutorizador(observacion, usuarioAplicativo);
			}catch(BusinessException be){
				throw new BusinessException(be.getCodigo(), be.getMessage());
	  }	
	}
	
	@RequestMapping(value="/autorizarCotizacion")
	@ResponseBody
	public void autorizarCotizacion(@RequestBody Long idCotizacion) throws BusinessException {
			try {
				
			if(idCotizacion == null) {
				throw new BusinessException("","");
			}
			nuevoClienteBO.autorizarCotizacion(idCotizacion);
			}catch(BusinessException be){
				throw new BusinessException(be.getCodigo(), be.getMessage());
	  }	
	}
	
	@RequestMapping (value="/descargarArchivo", method=RequestMethod.POST )
	@ResponseBody
    public HashMap<String, Object> descargarArchivo(@RequestBody ClienteTempDto cliente) throws IOException {
    	HashMap<String, Object> documento = new HashMap<String, Object>();
    		String encodedFile = "";
    		try {
    			List<ClienteTempBitacoraSolicitudesDto> clienteTempBitacoraSolicitudesDto = nuevoClienteBO.obtenerArchivoBitacoraSolicitudes(cliente.getClienteTempBitacoraSolicitudesDto().getIdClienteTempBitacoraSolicitudes());
    	    	String filePath= clienteTempBitacoraSolicitudesDto.get(0).getArchivoRecuperado();
    			encodedFile = filePath;
    		} catch (Exception e) {
    			// Manejar Error
    		}
    	    
    	    documento.put("documento", encodedFile);
    		return documento;
    }
	
	@RequestMapping(value = "/verificarEditar")
	@ResponseBody
	public ClienteTempDto verificarEditar(Model model) {
		ClienteTempDto clienteTempDto = new ClienteTempDto();
		Long idClienteTemp = (Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP_EDITAR);
		if(idClienteTemp != null && idClienteTemp != 0) {
		clienteTempDto = nuevoClienteBO.obtenerClienteById(idClienteTemp);
		}
		clienteTempDto.setGrupos(catBo.obtenerCatalogoGrupo());
		clienteTempDto.setEntidadFederativa(catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		clienteTempDto.setGirosComerciales(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve()));
		if(clienteTempDto.getIdSubGiroComercial() != null ) {
			clienteTempDto.setCatSubGiroComercial(nuevoClienteBO.obtenerSubgiroXIdGiro(clienteTempDto.getIdGiroComercial().toString()));
		}
		return clienteTempDto;
		}
	
	@RequestMapping(value="/eliminarProspecto")
	@ResponseBody
	public MensajeDTO eliminarProspecto(@RequestBody ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo, Model model) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();
		 
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}
		 
		 ClienteTempBitacoraSolicitudesDto clienteSolicitudes=  new ClienteTempBitacoraSolicitudesDto();
			try {
				 clienteSolicitudes = nuevoClienteBO.obtenerBitacoraSolicitudesXIdClienteTemp(cliente.getIdClienteTemp());
				if(clienteSolicitudes.getIdClienteTemp() != null) {
					throw new BusinessException("","");
				}
				nuevoClienteBO.eliminarProspecto(cliente, usuarioAplicativo);
			
			}catch(BusinessException be){
				if(clienteSolicitudes.getIdClienteTemp() != null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("No se puede eliminar el prospecto, ya que tiene una solicitud en proceso, favor de verificar");
			         return mensajeDTO;
				}
			}
			return mensajeDTO;
			}
	
	
	@RequestMapping(value="/obtenerEntidadFederativaXCP")
	@ResponseBody
	public ClienteTempDto obtenerEntidadFederativaXCP(@RequestBody String codigoPostal) {
		ClienteTempDto cliente = new ClienteTempDto();
		 cliente = nuevoClienteBO.obtenerEntidadFederativaXCP(codigoPostal);
		 	 
		 if(cliente.getIdMedioContacto() != null && cliente.getIdMedioContacto().getEstado() != null) {
		 List<CatGeneralDto> catGeneral = catBo.obtenerCatMunicipioByEntidadFedByCveMun(cliente.getIdMedioContacto().getEstado(), cliente.getIdMedioContacto().getNombreAlcaldia());
		 if(!catGeneral.isEmpty()) {	
		 cliente.getIdMedioContacto().setAlcaldia(catGeneral.get(0).getIdCatGeneral().intValue());
		 }
		 cliente.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), cliente.getIdMedioContacto().getEstado()));
		 }
		return cliente;
	}
	
	@RequestMapping(value = "/obtenerCotizacionesPorEstatus")
	@ResponseBody
	public ClienteTempDto cargaInicialCotizaciones(@RequestBody Long idEstatus) {
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto.setListClienteTempDto(nuevoClienteBO.obtenerCotizacionesPorEstatus(idEstatus));
			clienteTempDto.setCatEstatus(catBo.obtenerCatEstatusInicialCotizaciones());
			return clienteTempDto;
	}
	
	
	@RequestMapping(value="/declinarProspecto")
	@ResponseBody
//	public MensajeDTO declinarProspecto(@RequestBody Long idClienteTemp, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	public MensajeDTO declinarProspecto(@RequestBody ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();
		 List<CotizacionDto> listCotizaciones = new ArrayList<CotizacionDto>();
				listCotizaciones = cotizadorBo.obtenerCotizacionesXIdClienteTemp(cliente.getIdClienteTemp());
				
				 if(!listCotizaciones.isEmpty()) {
					 cotizadorBo.actualizarEstatusCotizaciones(listCotizaciones, usuarioAplicativo, cliente.getMotivoRechazo()!=null ? cliente.getMotivoRechazo() : null);
				}
	return mensajeDTO;
	}
	
	@RequestMapping(value="/rechazarProspecto")
	@ResponseBody
	public MensajeDTO rechazarProspecto(@RequestBody ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(cliente.getIdClienteTemp() == null) {
				 LOGGER.error("Ocurrio un error en rechazarProspecto, no tiene idClienteTmp");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(cliente.getMotivoRechazo() == null || "".equals(cliente.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en rechazarProspecto, no cuenta con motivo de rechazo para el idClienteTmp "+ cliente.getIdClienteTemp());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }
			 
			 if(!cotizadorBo.rechazarProspecto(cliente.getMotivoRechazo(), usuarioAplicativo, cliente.getIdClienteTemp())) {
				 LOGGER.error("Ocurrio un error en rechazarProspecto ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en rechazarProspecto ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
		 
		
				
	return mensajeDTO;
	}
	
	@RequestMapping(value="/autorizarProspecto")
	@ResponseBody
	public MensajeDTO autorizarProspecto(@RequestBody Long idClienteTemp, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(!cotizadorBo.autorizarProspecto(usuarioAplicativo, idClienteTemp)) {
				 LOGGER.error("Ocurrio un error en autorizarProspecto ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en autorizarProspecto ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
		 
		
				
	return mensajeDTO;
	}
	
	
	@RequestMapping(value="/obtenerSubgiro")
	@ResponseBody
	public ClienteTempDto obtenerSubgiro(@RequestBody String idGiro) {
		ClienteTempDto cliente = new ClienteTempDto();
		 cliente.setCatSubGiroComercial(nuevoClienteBO.obtenerSubgiroXIdGiro(idGiro));
		
		return cliente;
	}
	
}