package mx.com.consolida.controller.crm.prestadoraServicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.AccionistaDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.BuscarPrestadoraServicioDto;
import mx.com.consolida.crm.dto.FichaTecnicaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioActividadDto;
import mx.com.consolida.crm.dto.PrestadoraServicioClaseFraccionPrimaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioCuentaBancariaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDocumentoDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioImssDto;
import mx.com.consolida.crm.dto.PrestadoraServicioObjetoSocialDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.crm.dto.PrestadoraServicioRegistroPatronalDto;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.crm.service.interfaz.CatGeneralBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.usuario.UsuarioBO;

@Controller
@RequestMapping("prestadoraServicio")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR })
public class PrestadoraServicioController  extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioController.class);
	
	@Autowired
	private PrestadoraServicioBO prestadoraServicioBO;
	
	@Autowired
	private CatGeneralBO catGeneralBO;
	
	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	
	@RequestMapping(value = "/cargaInicial")
	@ResponseBody
	public List<PrestadoraServicioDto> cargaInicialGrid() {
		try {
			
			List<PrestadoraServicioDto> listaProspectos = prestadoraServicioBO.obtenerPrestadorasServicio(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
			return listaProspectos;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaInicial ", e);
			return Collections.emptyList();
		}
	}
	
	@RequestMapping(value = "/cargaCatalogos")
	@ResponseBody
	public PrestadoraServicioDto cargaCatalogosAgregarPrestadora(Model model) {
		try {
			PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDatosGenerales(prestadoraServicioDto, idPrestadoraServicio);
			}
			}
			
			prestadoraServicioDto = prestadoraServicioBO.obtenerCatalogosDatosGenerales(prestadoraServicioDto);
						
			return prestadoraServicioDto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
			return new PrestadoraServicioDto();
		}
	}
	
	
	@RequestMapping(value = "/cargaInicialProductos")
	@ResponseBody
	public PrestadoraServicioDto cargaInicialProductos(Model model) {
		try {
			PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdProductos(prestadoraServicioDto, idPrestadoraServicio);
			}
			}
			
			return prestadoraServicioDto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
			return new PrestadoraServicioDto();
		}
	}
	
	@RequestMapping(value = "/cargaInicialFiel")
	@ResponseBody
	public PrestadoraServicioDto cargaInicialFiel(Model model) {
		try {
			PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
					prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdFiel(prestadoraServicioDto, idPrestadoraServicio);
					
					if(prestadoraServicioDto !=null) {
						
						if(prestadoraServicioDto.getPasswordCsd()!=null) {
							byte[] decodedBytesCsd = Base64.getDecoder().decode(prestadoraServicioDto.getPasswordCsd());
							String passCsd = new String(decodedBytesCsd);
							prestadoraServicioDto.setPasswordCsd(passCsd);
							
							if(prestadoraServicioDto.getPrestadoraServicioDocumento() != null) {
								prestadoraServicioDto.getPrestadoraServicioDocumento().setPasswordCsd(passCsd);	
							}
							
						}
						
						if(prestadoraServicioDto.getPasswordFiel()!=null) {
							byte[] decodedBytesFiel = Base64.getDecoder().decode(prestadoraServicioDto.getPasswordFiel());
							String passFiel = new String(decodedBytesFiel);
							prestadoraServicioDto.setPasswordFiel(passFiel);
							
							if(prestadoraServicioDto.getPrestadoraServicioDocumento() != null) {
								prestadoraServicioDto.getPrestadoraServicioDocumento().setPasswordFiel(passFiel);
							}
						}
					}

				}
			}

			return prestadoraServicioDto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
			return new PrestadoraServicioDto();
		}
	}
	
	@RequestMapping(value = "/cargaInicialDomicilio")
	@ResponseBody
	public PrestadoraServicioDto cargaInicialDomicilio(Model model) {
		try {
			PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(prestadoraServicioDto, idPrestadoraServicio);
			}
			}
			
			prestadoraServicioDto = prestadoraServicioBO.obtenerCatalogosDomicilio(prestadoraServicioDto);
					
			return prestadoraServicioDto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
			return new PrestadoraServicioDto();
		}
	}
	
	
	@RequestMapping(value = "/cargaInicialCuentas")
	@ResponseBody
	public PrestadoraServicioDto cargaInicialCuentas(Model model) {
		try {
			PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdCuentaBancaria(prestadoraServicioDto, idPrestadoraServicio);
			}
			}
			
			prestadoraServicioDto = prestadoraServicioBO.obtenerCatalogosCuentaBancaria(prestadoraServicioDto);
			
			return prestadoraServicioDto;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
			return new PrestadoraServicioDto();
		}
	}
	
	@RequestMapping(value = "/nuevaPrestadoraServicio")
	public ResponseEntity<String> nuevaPrestadoraServicio(Model model) {
		model.addAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR, 0L);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/editarPrestadoraServicio")
	public ResponseEntity<String> editarPrestadoraServicio(@RequestBody Long idPrestadoraServicio, Model model) {
		model.addAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR, idPrestadoraServicio);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/buscar")
	@ResponseBody
	public List<PrestadoraServicioDto> buscar(@RequestBody BuscarPrestadoraServicioDto busqueda) {
		List<PrestadoraServicioDto> regresaListaConEncontrado = new ArrayList<PrestadoraServicioDto>();
		
		List<PrestadoraServicioDto> listaProspectos = prestadoraServicioBO.obtenerPrestadorasServicio(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
		
		if((busqueda.getRfc() != null && !busqueda.getRfc().isEmpty()) || (busqueda.getNombreRazonSocial() != null && !busqueda.getNombreRazonSocial().isEmpty())) {
		
		for(PrestadoraServicioDto cliente : listaProspectos) {
			if(cliente.getRfc() != null || cliente.getNombreCorto() != null || cliente.getRazonSocial() != null) {
			
			if((busqueda.getRfc() != null && !busqueda.getRfc().equals(" ") && !busqueda.getRfc().isEmpty())
					&& cliente.getRfc().contains(busqueda.getRfc())) {
				regresaListaConEncontrado.add(cliente);
			}
			
			if(busqueda.getNombreRazonSocial() != null && !busqueda.getNombreRazonSocial().isEmpty()
					&& (cliente.getNombreCorto().contains(busqueda.getNombreRazonSocial()) 
					|| cliente.getRazonSocial().contains(busqueda.getNombreRazonSocial()))) {
				regresaListaConEncontrado.add(cliente);
			}
			}
		}
		return regresaListaConEncontrado;
		}else {
		return listaProspectos;
		}
	}
	
	
	@RequestMapping(value = "/guardarPrestadoraServicio")
	@ResponseBody
	public PrestadoraServicioDto guardarPrestadoraServicio(@RequestBody PrestadoraServicioDto prestadoraServicioDto, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}

			
			if(prestadoraServicioDto!=null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto()!=null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio() !=null
					&& prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal()!=null ) {
				
				
				
				prestadoraServicioDto = prestadoraServicioBO.guardarPrestadoraServicio(prestadoraServicioDto, usuarioAplicativo);
				
			}else {
				if(prestadoraServicioDto.getRfc() == null  || "".equals(prestadoraServicioDto.getRfc().trim())
						|| prestadoraServicioDto.getNombreCorto() == null || "".equals( prestadoraServicioDto.getNombreCorto().trim())
						|| prestadoraServicioDto.getRazonSocial() == null || "".equals(prestadoraServicioDto.getRazonSocial().trim())
						|| prestadoraServicioDto.getCelulaPrestadoraServicioDto() == null
						|| (prestadoraServicioDto.getCelulaPrestadoraServicioDto() != null && prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula() == null)
						|| (prestadoraServicioDto.getCelulaPrestadoraServicioDto() != null && prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula() != null
						&& prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula() == null)) {
					throw new BusinessException("");
				}
				
//				if(prestadoraServicioDto.getEsFondo()) {
//					if(prestadoraServicioDto.getClave() == null || "".equals(prestadoraServicioDto.getClave().trim())) {
//						throw new BusinessException("");
//					}
//				}
				
				if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto() != null) {
					if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal() == null 
							|| prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa() == null) {
						throw new BusinessException("");
					}
				}
				
				if(prestadoraServicioDto.getEsFondo()) {
					if(prestadoraServicioDto.getIdConsar() == null || "".equals(prestadoraServicioDto.getIdConsar().trim())){
						throw new BusinessException("");
					}
				}
				
				if(!prestadoraServicioDto.getEsFondo()) {
					prestadoraServicioDto.setIdConsar(null);
//					prestadoraServicioDto.setClave(null);
					
				}
				
				
				prestadoraServicioDto = prestadoraServicioBO.guardarPrestadoraServicio(prestadoraServicioDto, usuarioAplicativo);
			}
			

			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/guardarPrestadoraServicio ", e.getMessage());
			
			
			if(prestadoraServicioDto!=null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto()!=null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio() !=null
					&& prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal()!=null ) {
				
				if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto() != null) {
					if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal() == null) {
						mensajeDTO.setCorrecto(false);
				        mensajeDTO.setMensajeError("Debe agregar el campo 'Código Postal', de la sección Domicilio");
				        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
				         return prestadoraServicioDto;
					}else if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa() == null) {
						mensajeDTO.setCorrecto(false);
				        mensajeDTO.setMensajeError("Debe seleccionar el campo 'Entidad federativa', de la sección Domicilio");
				        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
				         return prestadoraServicioDto;
					}else {
						mensajeDTO.setCorrecto(false);
				        mensajeDTO.setMensajeError("Ocurrio un error al guardar, favor de verificar.");
				        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
				         return prestadoraServicioDto;
					}
					
				}
								
			}else {
				if(prestadoraServicioDto.getRfc() == null || "".equals(prestadoraServicioDto.getRfc().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Rfc', de la sección Datos generales");
			        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
			         return prestadoraServicioDto;
				}else if(prestadoraServicioDto.getNombreCorto() == null || "".equals(prestadoraServicioDto.getNombreCorto().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Nombre corto', de la sección Datos generales");
			        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
			         return prestadoraServicioDto;
				}else if(prestadoraServicioDto.getRazonSocial() == null || "".equals(prestadoraServicioDto.getRazonSocial().trim())) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Razón social', de la sección Datos generales");
			        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
			         return prestadoraServicioDto;
				}else if(prestadoraServicioDto.getCelulaPrestadoraServicioDto() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe agregar el campo 'Célula', de la sección Datos generales");
			        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
			         return prestadoraServicioDto;
				}else if(prestadoraServicioDto.getEsFondo()) {
					if(prestadoraServicioDto.getIdConsar() == null || "".equals(prestadoraServicioDto.getIdConsar().trim())){
						mensajeDTO.setCorrecto(false);
				        mensajeDTO.setMensajeError("Debe agregar el campo 'ID Consar', de la sección Datos generales");
				        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
				        return prestadoraServicioDto;
					}
//					else if(prestadoraServicioDto.getClave() == null || "".equals(prestadoraServicioDto.getClave().trim())) {
//						mensajeDTO.setCorrecto(false);
//				        mensajeDTO.setMensajeError("Debe agregar el campo 'Clave'");
//				        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
//				         return prestadoraServicioDto;
//					}
				} else if(prestadoraServicioDto.getCelulaPrestadoraServicioDto() == null
						|| (prestadoraServicioDto.getCelulaPrestadoraServicioDto() != null && prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula() == null)
						|| (prestadoraServicioDto.getCelulaPrestadoraServicioDto() != null && prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula() != null
						&& prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula() == null)) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe seleccionar el campo 'Célula'.");
			        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
			         return prestadoraServicioDto;
			         
				}
			}
			
			
				
				
				
				
			
			
//			else {
//				mensajeDTO.setCorrecto(false);
//		        mensajeDTO.setMensajeError("Ocurrio un error al guardar, favor de verificar.");
//		        prestadoraServicioDto.setMensajeDTO(mensajeDTO);
//		         return prestadoraServicioDto;
//			}
		}
		return prestadoraServicioDto;
	}
	
	
	@RequestMapping(value = "/guardarCuentaPrestadoraServicio")
	@ResponseBody
	public MensajeDTO guardarCuentaPrestadoraServicio(@RequestBody PrestadoraServicioCuentaBancariaDto cuenta, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
						
			if(cuenta.getCatBanco() == null
					|| cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == null || cuenta.getCatBanco() != null && cuenta.getCatBanco().getIdCatGeneral() == -1
					|| cuenta.getCatTipoCuenta() == null
					|| cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == null || cuenta.getCatTipoCuenta() != null && cuenta.getCatTipoCuenta().getIdCatGeneral() == -1) {
					throw new BusinessException("");
			}
			
			if(cuenta.getNumeroCuenta() == null  && cuenta.getClabeInterbancaria() == null 
					|| ("".equals(cuenta.getNumeroCuenta().trim()) && "".equals(cuenta.getClabeInterbancaria().trim()))
					|| (cuenta.getNumeroCuenta() == null && "".equals(cuenta.getClabeInterbancaria().trim()))    
					|| ("".equals(cuenta.getNumeroCuenta().trim()) && cuenta.getClabeInterbancaria() == null)){
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
			
			prestadoraServicioBO.guardarCuentaPrestadoraServicio(cuenta, usuarioAplicativo);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/guardarCuentaPrestadoraServicio ", e);
			if(cuenta.getNumeroCuenta() == null  && cuenta.getClabeInterbancaria() == null 
					|| ("".equals(cuenta.getNumeroCuenta().trim()) && "".equals(cuenta.getClabeInterbancaria().trim()))
					|| (cuenta.getNumeroCuenta() == null && "".equals(cuenta.getClabeInterbancaria().trim()))    
					|| ("".equals(cuenta.getNumeroCuenta().trim()) && cuenta.getClabeInterbancaria() == null)) {
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
	
	public MensajeDTO verificarGuardado(PrestadoraServicioCuentaBancariaDto cuenta) {
		return prestadoraServicioBO.verificarGuardado(cuenta);
	}
	
	
	@RequestMapping(value="/obtenerEntidadFederativaXCP")
	@ResponseBody
	public PrestadoraServicioDto obtenerEntidadFederativaXCP(@RequestBody String codigoPostal) {
		PrestadoraServicioDto cliente = new PrestadoraServicioDto();
		 cliente = prestadoraServicioBO.obtenerEntidadFederativaXCP(codigoPostal);
		 	 
		 if(cliente.getPrestadoraServicioDomicilioDto() != null && cliente.getPrestadoraServicioDomicilioDto().getDomicilio() != null && 
				 cliente.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa() != null) {
		 List<CatGeneralDto> catGeneral = catBo.obtenerCatMunicipioByEntidadFedByCveMun(cliente.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa().toString(), cliente.getPrestadoraServicioDomicilioDto().getDomicilio().getCatMunicipios().getIdCatGeneral().toString());
		 if(!catGeneral.isEmpty()) {	
			 cliente.getPrestadoraServicioDomicilioDto().getDomicilio().setCatMunicipios(catGeneral.get(0));
		 }
		 cliente.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), cliente.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa().toString()));
		 }
		return cliente;
	}
	
	@RequestMapping(value = "/eliminarCuentaPrestadoraServicio")
	@ResponseBody
	public MensajeDTO eliminarCuentaPrestadoraServicio(@RequestBody PrestadoraServicioCuentaBancariaDto cuenta, Model model) {
		MensajeDTO mensajeDTO = new MensajeDTO();
		try {
			
			if(cuenta.getIdPrestadoraServicioCuentaBancaria() == null) {
					throw new BusinessException("");
			}
			
		
			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			
			if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
				usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			}
			
			prestadoraServicioBO.eliminarCuentaPrestadoraServicio(cuenta, usuarioAplicativo);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en prestadoraServicio/guardarCuentaPrestadoraServicio ", e);
			
			if(cuenta.getIdPrestadoraServicioCuentaBancaria() == null) {
					mensajeDTO.setCorrecto(false);
			        mensajeDTO.setMensajeError("Debe de introducir el campo Clabe interbancaria o número de cuenta, favor de verificar.");
			         return mensajeDTO;
			}else{
				mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar la cuenta, favor de verificar.");
		         return mensajeDTO;
			}
		}
		return mensajeDTO;
	}

	
	 @RequestMapping(value = "/guardarArchivosFiel")
	    @ResponseBody
	    public void guardarArchivosFiel(@RequestBody PrestadoraServicioDocumentoDto documentos, Model model) throws BusinessException {
	        try {
	        	UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
	        	if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
					usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
				}
	        	if(documentos.getPasswordCsd() != null) {
	        		String passEncode = Base64.getEncoder().encodeToString(documentos.getPasswordCsd().getBytes());
	        		documentos.setPasswordCsd(passEncode);
	        	}
	        	
	        	if(documentos.getPasswordFiel() != null) {
	        		String passEncode = Base64.getEncoder().encodeToString(documentos.getPasswordFiel().getBytes());
	        		documentos.setPasswordFiel(passEncode);
	        	}
	        	prestadoraServicioBO.guardarArchivosFiel(documentos, usuarioAplicativo);
	        } catch (BusinessException be) {
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 
	 
	 @RequestMapping(value = "/guardarProducto")
	 @ResponseBody
	public void guardarProducto(@RequestBody PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	        try {
	        	
	        	prestadoraServicioBO.guardarProducto(producto, usuarioAplicativo);
	        } catch (BusinessException be) {
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
	    }
	 

	 @RequestMapping(value = "/eliminarProducto")
	 @ResponseBody
	public void eliminarProducto(@RequestBody PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
	        try {
	        	if(producto.getIdPrestadoraServicioProducto() == null) {
	        		throw new BusinessException("");
	        	}
	        	prestadoraServicioBO.eliminarProducto(producto, usuarioAplicativo);
	        } catch (BusinessException be) {
	        	if(producto.getIdPrestadoraServicioProducto() == null) {
	        		throw new BusinessException("Ocurrio un error al obtener el IdPrestadoraServicioProducto");
	        	}
	        }
	    }
	 
	 
	 @RequestMapping(value="/producto/guardarProductoSat", method = RequestMethod.POST)
		@ResponseBody
		public MensajeDTO guardarProductoSat(@RequestBody PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {
				
				if(producto == null || (producto.getClave() == null || "".equals(producto.getClave().trim()))
								|| (producto.getDescripcion() == null || "".equals(producto.getDescripcion().trim()))) {
					throw new BusinessException("", "");
				}


				if(!catGeneralBO.guardarCatGeneral(Long.valueOf(CatMaestroEnum.PRODUCTO_SAT.getId()), producto.getClave(), producto.getDescripcion(), usuarioAplicativo)) {
					LOGGER.error("Ocurrio un error en guardarProductoSat - guardarCatGeneral, no se guardo el regristro en catGeneral");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
				
				if(producto!= null && producto.getClave() == null || "".equals(producto.getClave().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Clave producto sat'");
				} 
				
				if(producto.getDescripcion() == null || "".equals(producto.getDescripcion().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Descripci\u00f3n producto sat'");
				} 
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardaObjetoSocial ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}

	 
	 @RequestMapping(value = "/verificarFondo")
	    @ResponseBody
	    public MensajeDTO verificarFondo(@RequestBody PrestadoraServicioDto prestadoraServicioDto) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();
	        try {
	        	if(prestadoraServicioDto.getEsFondo() != null && prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula() != null) {
	        		List<PrestadoraServicioDto> fondo= prestadoraServicioBO.verificarFondo(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula());
	        		
	        		for(PrestadoraServicioDto esFondo: fondo) {
	        			if(esFondo.getEsFondo().equals(Boolean.TRUE)) {
	        				mensajeDTO.setCorrecto(false);
	        				mensajeDTO.setMensaje("La celula seleccionada ya cuenta con un fondo, con el ID Consar: "+esFondo.getIdConsar());
	        			}else {
	        				mensajeDTO.setCorrecto(true);
	        			}
		        		
		        	}
	        	}
	        	
	        } catch (BusinessException be) {
	        	mensajeDTO.setCorrecto(false);
		        mensajeDTO.setMensajeError("Ocurrio un error al guardar la cuenta, favor de verificar.");
		         return mensajeDTO;
	        }
			return mensajeDTO;
	    }
	 
	 
		@RequestMapping(value="/actividad/cargaInicialActividad")
		@ResponseBody
		public PrestadoraServicioActividadDto cargaInicialActividad(Model model) {
			try {
			PrestadoraServicioActividadDto actividad = new PrestadoraServicioActividadDto();
			
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				actividad = prestadoraServicioBO.obtenerDatosByActividad(idPrestadoraServicio);
				
				actividad = prestadoraServicioBO.obtenerCatalogosActividad(actividad);
				
				
			}
			
			return actividad;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialDatosGenCliente ", e.getMessage());
				throw new BusinessException ("Ocurrio un error en el sistema cargaInicialDomicilio");
			}
		}
		
		@RequestMapping(value="/actividad/obtenerSubgiro")
		@ResponseBody
		public PrestadoraServicioActividadDto obtenerSubgiro(@RequestBody String idGiro) {
			PrestadoraServicioActividadDto subgiro = new PrestadoraServicioActividadDto();
			subgiro.setCatSubGiroComercial(prestadoraServicioBO.obtenerSubgiroXIdGiro(idGiro));
			
			return subgiro;
		}
		
		@RequestMapping(value="/actividad/guardarActividad")
		@ResponseBody
		public MensajeDTO guardarActividad(@RequestBody PrestadoraServicioActividadDto actividad, Model model) {
			MensajeDTO mensajeDTO = new MensajeDTO();
			try {
				
				if(actividad.getIdGiroComercial() == null || actividad.getIdSubGiroComercial() == null 
						|| actividad.getIdGiroComercial() <=0) {
						throw new BusinessException("");
				}
				
			
				UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
				
				if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
					usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
				}
				
				
				actividad.setPrestadoraDto(new PrestadoraServicioDto((Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)));
				prestadoraServicioBO.guardarActividad(actividad, usuarioAplicativo);
				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardarActividad ", e);
				
				if(actividad.getIdGiroComercial() == null || actividad.getIdGiroComercial() <=0) {
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
		public void eliminarActividad(@RequestBody PrestadoraServicioActividadDto actividad) {
			try {
				prestadoraServicioBO.eliminarActividad(actividad, getUser().getIdUsuario());
			} catch (BusinessException be) {
				LOGGER.error("Ocurrio un error en eliminarActividad ", be);
	            throw new BusinessException(be.getCodigo(), be.getMessage());
	        }
		}
		
		@RequestMapping(value="/registroPatronal/cargaInicialRegistroPatronal")
		@ResponseBody
		public List<PrestadoraServicioRegistroPatronalDto> cargaInicialRegistroPatronal(Model model) {
			try {
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				if(idPrestadoraServicio == null) {
					LOGGER.error("Ocurrio un error en cargaInicialRegistroPatronal, idPrestadoraServicioes null");
					return Collections.emptyList();
				}
				
				List<PrestadoraServicioRegistroPatronalDto> listaRegistroPatronal = prestadoraServicioBO.getListRegistroPatronalByIdPrestadora(idPrestadoraServicio);
				
				return listaRegistroPatronal;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/registroPatronal/guardaRegistroPatronal")
		@ResponseBody
		public MensajeDTO guardaRegistroPatronal(@RequestBody PrestadoraServicioRegistroPatronalDto registroPatronalDto, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {
				
				if(registroPatronalDto == null || registroPatronalDto.getRegistroPatronal() == null ||  "".equals(registroPatronalDto.getRegistroPatronal().trim())) {
					throw new BusinessException("", "");
				}
				
				
				if(registroPatronalDto.getIdPrestadoraServicioRegistroPatronal()==null) {
					registroPatronalDto.setEsEliminar(false);
				}
				
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				if(idPrestadoraServicio == null ) {
					LOGGER.error("Ocurrio un error en guardaRegistroPatronal, idPrestadoraServicio es null ");
					mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo nuevamente");
				}else {
					registroPatronalDto.setPrestadoraServicioDto(new PrestadoraServicioDto(idPrestadoraServicio));
					
					if(!prestadoraServicioBO.guardarActualizarPrestadoraRegistroPatronal(registroPatronalDto, getUser().getIdUsuario())) {
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
				}
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
				if (registroPatronalDto == null || registroPatronalDto.getRegistroPatronal() == null || "".equals(registroPatronalDto.getRegistroPatronal().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar un 'Registro patronal.'");
				}
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardaRegistroPatronal ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/registroPatronal/eliminarRegistroPatronal")
		@ResponseBody
		public MensajeDTO eliminarRegistroPatronal(@RequestBody PrestadoraServicioRegistroPatronalDto registroPatronalDto, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {

				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				registroPatronalDto.setPrestadoraServicioDto(new PrestadoraServicioDto(idPrestadoraServicio));
				registroPatronalDto.setEsEliminar(true);

				if(!prestadoraServicioBO.guardarActualizarPrestadoraRegistroPatronal(registroPatronalDto, getUser().getIdUsuario())) {
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				return mensajeDTO;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en eliminarRegistroPatronal ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		
		@RequestMapping(value="/claseFraccionPrima/cargaInicialClasePrimaFraccion")
		@ResponseBody
		public  Map<String, Object>  cargaInicialClasePrimaFraccion(Model model) {
			
			
			Map<String, Object> dataReturn = new HashMap<>();
			
			try {
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				dataReturn.put("listaCatClase", catBo.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(CatMaestroEnum.CAT_CLASE.getCve()));
//				dataReturn.put("listaCatRiesgo", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_RIESGO.getCve()));
				dataReturn.put("presatdoraClase", prestadoraServicioBO.getPresatdorServicioClaseFraccionByIdPrestServicio(idPrestadoraServicio));
				
				return dataReturn;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialClasePrimaFraccion ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		
		@RequestMapping(value="/claseFraccionPrima/guardaClasePrimaFraccion", method = RequestMethod.POST)
		@ResponseBody
		public MensajeDTO guardaClasePrimaFraccion(@RequestBody PrestadoraServicioClaseFraccionPrimaDto presatdoraClase, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {
				
				if(presatdoraClase == null ||presatdoraClase.getFraccion() == null || "".equals(presatdoraClase.getFraccion().trim())
						|| presatdoraClase.getPrima() == null || "".equals(presatdoraClase.getPrima().trim())
						|| presatdoraClase.getCatClase() == null || (presatdoraClase.getCatClase() !=null && presatdoraClase.getCatClase().getIdCatGeneral()== null)
//						|| presatdoraClase.getCatRiesgo() == null || (presatdoraClase.getCatRiesgo() !=null && presatdoraClase.getCatRiesgo().getIdCatGeneral()== null)
						) {
					throw new BusinessException("", "");
				}

								
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				if(idPrestadoraServicio == null ) {
					LOGGER.error("Ocurrio un error en guardaClasePrimaFraccion, idPrestadoraServicio es null ");
					mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo nuevamente");
				}else {
					
					presatdoraClase.setPrestadoraServicio(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarPrestadoraClaseFraccionPrima(presatdoraClase, getUser().getIdUsuario())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
				}
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
				
				if(presatdoraClase.getCatClase() == null || (presatdoraClase.getCatClase() !=null && presatdoraClase.getCatClase().getIdCatGeneral()== null)) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe seleccionar  'Clase.'");
				}
//				else if(presatdoraClase.getCatRiesgo() == null || (presatdoraClase.getCatRiesgo() !=null && presatdoraClase.getCatRiesgo().getIdCatGeneral()== null)) {
//					mensajeDTO.setCorrecto(false);
//					mensajeDTO.setMensajeError("Debe seleccionar  'Riesgo.'");
//				}
				else if(presatdoraClase.getFraccion() == null || "".equals(presatdoraClase.getFraccion().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Fracción.'");
				}else if(presatdoraClase.getPrima() == null || "".equals(presatdoraClase.getPrima().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Prima.'");
				}
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardaClasePrimaFraccion ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/imss/cargaInicialPrestadoraImss")
		@ResponseBody
		public  Map<String, Object>  cargaInicialPrestadoraImss(Model model) {
			
			
			Map<String, Object> dataReturn = new HashMap<>();
			
			try {
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);

				dataReturn.put("presatdoraImms", prestadoraServicioBO.getPresatdorServicioImssByIdPrestServicio(idPrestadoraServicio));
				
				return dataReturn;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialPrestadoraImss ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/imss/guardaPrestadoraImss", method = RequestMethod.POST)
		@ResponseBody
		public MensajeDTO guardaPrestadoraImss(@RequestBody PrestadoraServicioImssDto prestadoraServicioImss, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {
				
				if(prestadoraServicioImss == null ||prestadoraServicioImss.getUsuario() == null || "".equals(prestadoraServicioImss.getUsuario().trim())
						|| prestadoraServicioImss.getPassword() == null || "".equals(prestadoraServicioImss.getPassword().trim())) {
					throw new BusinessException("", "");
				}

								
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				if(idPrestadoraServicio == null ) {
					LOGGER.error("Ocurrio un error en guardaPrestadoraImss, idPrestadoraServicio es null ");
					mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo nuevamente");
				}else {
					
					prestadoraServicioImss.setPrestadoraServicio(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarPrestadoraImss(prestadoraServicioImss, getUser().getIdUsuario())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
				}
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
				
				if(prestadoraServicioImss.getUsuario() == null || "".equals(prestadoraServicioImss.getUsuario().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'Usuario.'");
				}else if(prestadoraServicioImss.getPassword() == null || "".equals(prestadoraServicioImss.getPassword().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Contrase\u00f1a.'");
				}
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardaPrestadoraImss ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/objetoSocial/cargaInicialObjetoSocial")
		@ResponseBody
		public  Map<String, Object>  cargaInicialObjetoSocial(Model model) {
			
			
			Map<String, Object> dataReturn = new HashMap<>();
			
			try {
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				dataReturn.put("gridObjetoSocial", prestadoraServicioBO.getListObjetoSocialByIdPrestadora(idPrestadoraServicio));
				
				return dataReturn;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialClasePrimaFraccion ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
				
		@RequestMapping(value="/objetoSocial/guardaObjetoSocial", method = RequestMethod.POST)
		@ResponseBody
		public MensajeDTO guardaObjetoSocial(@RequestBody PrestadoraServicioObjetoSocialDto prestadoraServicioObjetoSocial, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {
				
				if(prestadoraServicioObjetoSocial == null ||prestadoraServicioObjetoSocial.getDescripcion()== null || "".equals(prestadoraServicioObjetoSocial.getDescripcion().trim())) {
					throw new BusinessException("", "");
				}

								
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				if(idPrestadoraServicio == null ) {
					LOGGER.error("Ocurrio un error en guardaObjetoSocial, idPrestadoraServicio es null ");
					mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo nuevamente");
				}else {
					
					if(prestadoraServicioObjetoSocial.getIdPrestadoraServicioObjetoSocial()==null) {
						prestadoraServicioObjetoSocial.setEsEliminar(false);
					}
					
					prestadoraServicioObjetoSocial.setPrestadoraServicio(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarPrestadoraObjetoSocial(prestadoraServicioObjetoSocial, getUser().getIdUsuario())) {
						mensajeDTO.setCorrecto(false);
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
				}
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
				
				if(prestadoraServicioObjetoSocial == null ||prestadoraServicioObjetoSocial.getDescripcion()== null || "".equals(prestadoraServicioObjetoSocial.getDescripcion().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar 'Descripci\u00f3n'");
				} 
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardaObjetoSocial ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/objetoSocial/eliminarObjetoSocial")
		@ResponseBody
		public MensajeDTO eliminarObjetoSocial(@RequestBody PrestadoraServicioObjetoSocialDto prestadoraServicioObjetoSocial, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {

				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				prestadoraServicioObjetoSocial.setPrestadoraServicio(new PrestadoraServicioDto(idPrestadoraServicio));
				prestadoraServicioObjetoSocial.setEsEliminar(true);

				if(!prestadoraServicioBO.guardarActualizarPrestadoraObjetoSocial(prestadoraServicioObjetoSocial, getUser().getIdUsuario())) {
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				return mensajeDTO;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en eliminarObjetoSocial ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		

		@RequestMapping(value = "/obtenerCatProductoSat", method = RequestMethod.POST)
		@ResponseBody
		public CatGeneralDto obtenerCatProductoSat(@RequestBody String cveSat) {
			try {
				if(cveSat == null || cveSat.isEmpty()) {
					throw new Exception();
				}
				
				CatGeneralDto cat = prestadoraServicioBO.obtenerCatProductoSatXCve(cveSat);
				
				return cat;
				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en prestadoraServicio/obtenerCatProductoSat ", e);
				return new CatGeneralDto();
			}
		}
		
		@RequestMapping(value = "/obtenerCatProductoSatCompleto")
		@ResponseBody
		public List<CatGeneralDto> obtenerCatProductoSatCompleto() {
			try {
				List<CatGeneralDto> cat = catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.PRODUCTO_SAT.getCve());
				return cat;
				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en prestadoraServicio/obtenerCatProductoSatCompleto ", e);
			}
			return new ArrayList<CatGeneralDto>();
		}

		@RequestMapping (value="/descargarArchivo", method=RequestMethod.POST )
		@ResponseBody
	    public HashMap<String, Object> descargarArchivo(@RequestBody PrestadoraServicioDto prestadora) throws IOException {
	    	HashMap<String, Object> documento = new HashMap<String, Object>();
	    		String encodedFile = "";
	    		try {
	    			PrestadoraServicioDto prestadoraDto = prestadoraServicioBO.obtenerPrestadoraServicioArchivo(new PrestadoraServicioDto(), prestadora.getIdPrestadoraServicio());
	    	    	String filePath= prestadoraDto.getArchivoRecuperadoLogotipo();
	    			encodedFile = filePath;
	    		} catch (Exception e) {
	    			// Manejar Error
	    		}
	    	    
	    	    documento.put("documento", encodedFile);
	    		return documento;
	    }
		

		
		@RequestMapping(value = "/obtenerDocumentosPrestadora", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDocumentosPrestadora(Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
				
			return prestadoraServicioBO.listDocumentosPrestadora(idPrestadoraServicio);
		}
		
		@RequestMapping (value="/representanteLegal/cargaInicialRepresentanteLegal")
		@ResponseBody
	    public Map<String, Object>  cargaInicialRepresentanteLegal(Model model) {
	    	HashMap<String, Object> representante = new HashMap<String, Object>();
	    		
	    		try {

	    			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
	    			
	    			representante.put("gridRepresentantesLegales", prestadoraServicioBO.getListRepresentanteLegalByIdPrestadoraServicio(idPrestadoraServicio));
	    			
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
							|| representanteLegal.getCurp() == null || "".equals(representanteLegal.getCurp().trim())) {
						throw new BusinessException("", "");
					}
					
				}else {
					LOGGER.error("Ocurrio un error en guardarRepresentanteLegal el objeto representanteLegal viene null");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					return mensajeDTO;
				} 
				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio!=null) {
					
					representanteLegal.setPrestadoraServicioDto(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarRepresentanteLegal(representanteLegal, getUser().getIdUsuario())) {
						LOGGER.error("Ocurrio un error en guardarRepresentanteLegal-guardarActualizarRepresentanteLegal");
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
					
					
				}else {
					LOGGER.error("Ocurrio un error en guardarRepresentanteLegal idPrestadoraServicio viene null");
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
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardarRepresentanteLegal ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/representanteLegal/eliminarRepresentanteLegal")
		@ResponseBody
		public MensajeDTO eliminarRepresentanteLegal(@RequestBody RepresentanteLegalDto representanteLegal, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {

				if(!prestadoraServicioBO.eliminarRepresentanteLegal(representanteLegal, getUser().getIdUsuario())) {
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
		

		@RequestMapping (value="/apoderadoLegal/cargaInicialApoderadoLegal")
		@ResponseBody
	    public Map<String, Object>  cargaInicialApoderadoLegal(Model model) {
	    	HashMap<String, Object> apoderado = new HashMap<String, Object>();
	    		
			try {
				
    			Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
    			
    			apoderado.put("gridApoderadosLegales", prestadoraServicioBO.getListApoderadoLegalByIdPrestadoraServicio(idPrestadoraServicio));
				apoderado.put("catFacultades", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_FACULTADES.getCve()));
				
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialApoderadoLegal ", e);
				throw new BusinessException(
						"Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
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

				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio!=null) {
					
					apoderadoLegal.setPrestadoraServicioDto(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarApoderadoLegal(apoderadoLegal, getUser().getIdUsuario())) {
						LOGGER.error("Ocurrio un error en guardarApoderadoLegal-guardarActualizarApoderadoLegal");
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
					
					
				}else {
					LOGGER.error("Ocurrio un error en guardarApoderadoLegal idPrestadoraServicio viene null");
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

				if(!prestadoraServicioBO.eliminarApoderadoLegal(apoderadoLegalDto, getUser().getIdUsuario())) {
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
		
		@RequestMapping (value="/accionista/cargaInicialAccionistas")
		@ResponseBody
	    public Map<String, Object>  cargaInicialAccionistas(Model model) {
	    	HashMap<String, Object> accionista = new HashMap<String, Object>();
	    		
			try {

				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				
				accionista.put("gridAccionistas", prestadoraServicioBO.getListAccionistasByIdPrestadoraServicio(idPrestadoraServicio));
				accionista.put("entidadFederativa", catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error en cargaInicialAccionistas ", e);
				throw new BusinessException(
						"Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}

		return accionista;
	    }
		
		@RequestMapping(value="/accionista/guardarAccionista")
		@ResponseBody
		public MensajeDTO guardarAccionista(@RequestBody AccionistaDto accionista, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {

				if(accionista!=null) {
					
					if(accionista.getNombre() == null || "".equals(accionista.getNombre().trim())
							|| accionista.getApellidoPaterno() == null || "".equals(accionista.getApellidoPaterno().trim())
							|| accionista.getRfc() == null || "".equals(accionista.getRfc().trim())
							|| accionista.getFechaNacimiento() == null
							|| accionista.getPorcentajeAccion() == null) {
						throw new BusinessException("", "");
					}
					
				}else {
					LOGGER.error("Ocurrio un error en guardarAccionista el objeto accionista viene null");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					return mensajeDTO;
				} 

				
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio!=null) {
					
					accionista.setPrestadoraServicio(new PrestadoraServicioDto(idPrestadoraServicio));
					if(!prestadoraServicioBO.guardarActualizarAccionistas(accionista, getUser().getIdUsuario())) {
						LOGGER.error("Ocurrio un error en guardarAccionista-guardarActualizarAccionistas");
						mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
					}
					
					
				}else {
					LOGGER.error("Ocurrio un error en guardarAccionista idPrestadoraServicio viene null");
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				return mensajeDTO;
			
			}catch (BusinessException be) {
								
				
				if(accionista.getNombre() == null || "".equals(accionista.getNombre().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'Nombre'.");
				}else if(accionista.getApellidoPaterno() == null || "".equals(accionista.getApellidoPaterno().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'Apellido paterno'.");
				}else if(accionista.getRfc() == null || "".equals(accionista.getRfc().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'RFC'.");
				}else if(accionista.getFechaNacimiento() == null) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'Fecha de nacimiento'.");
				}else if(accionista.getPorcentajeAccion() == null) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'Porcentaje de acci\u00f3n'.");
				}else if(accionista.getDomicilioDto().getCodigoPostal() == null || "".equals(accionista.getDomicilioDto().getCodigoPostal().trim())) {
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Debe ingresar  'C\u00f3digo postal'.");
				}
				
				return mensajeDTO;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en guardarAccionista ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value="/accionista/eliminarAccionista")
		@ResponseBody
		public MensajeDTO eliminarAccionista(@RequestBody AccionistaDto accionistaDto, Model model) throws BusinessException {
			
			MensajeDTO mensajeDTO = new MensajeDTO();
			
			try {

				if(!prestadoraServicioBO.eliminarAccionista(accionistaDto, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en eliminarAccionista-eliminarAccionista");
					mensajeDTO.setCorrecto(false);
					mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				}
				
				return mensajeDTO;
			
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en eliminarAccionista ", e);
				throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}
		}
		
		@RequestMapping(value = "/eliminarPrestadoraServicio")
		@ResponseBody
		public Boolean eliminarPrestadoraServicio(@RequestBody Long idPresatdora) {
			try {
				
				if(!prestadoraServicioBO.eliminarPrestadora(idPresatdora, getUser().getIdUsuario())) {
					LOGGER.error("Ocurrio un error en eliminarPrestadoraServicio - eliminarPrestadora");
					return Boolean.FALSE;
				}
				
				return Boolean.TRUE;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en eliminarPrestadoraServicio", e);
				return Boolean.FALSE;
			}


		}
		 
		
		@RequestMapping(value = "/guardarDocumentosPrestadora")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosPrestadora(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.guardarDocumentosPrestadora(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		@RequestMapping(value = "/representanteLegal/obtenerDocumentosRepresentante", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDocumentosRepresentante(@RequestBody Long idPrestadoraServRepLeg, Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
				
			return prestadoraServicioBO.listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(idPrestadoraServicio, idPrestadoraServRepLeg);
		}
		
		@RequestMapping(value = "/representanteLegal/guardarDocumentosPrestadoraRepresentante")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosPrestadoraRepresentante(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.guardarDocumentosPrestadoraRepresentante(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		@RequestMapping(value = "/representanteLegal/eliminarDocumentosRepresentante")
	    @ResponseBody
	    public ResponseEntity<String> eliminarDocumentosRepresentante(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.eliminarDocumentosPrestadoraRepresentante(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosRepresentante", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosRepresentante", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		
		@RequestMapping(value = "/apoderadoLegal/obtenerDocumentosApoderado", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDocumentosApoderado(@RequestBody Long idPrestadoraServRepLeg, Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
				
			return prestadoraServicioBO.listDocumentosPrestadoraApoderado(idPrestadoraServicio, idPrestadoraServRepLeg);
		}
		
		@RequestMapping(value = "/apoderadoLegal/guardarDocumentosApoderado")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosApoderado(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.guardarDocumentosPrestadoraApoderado(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		@RequestMapping(value = "/apoderadoLegal/eliminarDocumentosApoderado")
	    @ResponseBody
	    public ResponseEntity<String> eliminarDocumentosApoderado(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.eliminarDocumentosPrestadoraApoderado(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosApoderado", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosApoderado", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		@RequestMapping(value = "/accionista/obtenerDocumentosAccionista", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDocumentosAccionista(@RequestBody Long idPrestadoraServRepLeg, Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
				
			return prestadoraServicioBO.listDocumentosPrestadoraAccionista(idPrestadoraServicio, idPrestadoraServRepLeg);
		}
		

		@RequestMapping(value = "/accionista/guardarDocumentosAccionista")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosAccionista(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.guardarDocumentosPrestadoraAccionista(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosAccionista ", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosAccionista ", e);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		

		@RequestMapping(value = "/accionista/eliminarDocumentosAccionista")
	    @ResponseBody
	    public ResponseEntity<String> eliminarDocumentosAccionista(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.eliminarDocumentosAccionista(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosAccionista", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en eliminarDocumentosAccionista", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		
		@RequestMapping(value = "/obtenerDoctosPrestadoraXDefinicionDoctoXIdPrestadora", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDoctosPrestadoraXDefinicionDoctoXIdPrestadora(@RequestBody String listaDefinicionDocumento, Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
			
			return prestadoraServicioBO.listDocumentosPrestadoraDefinicionDocto(listaDefinicionDocumento, idPrestadoraServicio);
		}
		
		
		@RequestMapping(value = "/guardarDocumentosPrestadoraFielCsd")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosPrestadoraFielCsd(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				prestadoraServicioBO.guardarDocumentosPrestadora(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }
		

		@RequestMapping(value = "/fielCsd/eliminarDocumentoFielCsd")
	    @ResponseBody
	    public MensajeDTO eliminarDocumentoFielCsd(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
			MensajeDTO mensaje = new MensajeDTO();
			try {
				prestadoraServicioBO.eliminarDocumentos(documento, usuarioAplicativo);
			
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				mensaje.setCorrecto(false);
				mensaje.setMensajeError("Ocurrio un error al eliminar el documento, favor de intentarlo nuevamente.");
			}
			return mensaje;
	    }
		

		@RequestMapping(value = "/obtenerDoctosPrestadoraDocumentoRepresentanteCerKey", method = RequestMethod.POST)
		@ResponseBody
		public List<DocumentoCSMDto> obtenerDoctosPrestadoraDocumentoRepresentanteCerKey(@RequestBody Long idPrestadoraServRepLeg, Model model) {
			
			Long idPrestadoraServicio = null;
			if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
			}else {
				return new ArrayList<DocumentoCSMDto>();
			}
			
			return prestadoraServicioBO.listPrestadoraDocumentosRepresentanteCerKey(idPrestadoraServRepLeg, idPrestadoraServicio);
		}

		@RequestMapping(value = "/representanteLegal/guardarDocumentosPrestadoraRepresentanteCerKey")
	    @ResponseBody
	    public ResponseEntity<String> guardarDocumentosPrestadoraRepresentanteCerKey(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		 
			try {
				
				usuarioAplicativo.setIdUsuario(getUser().getIdUsuario());
				prestadoraServicioBO.guardarDocumentosPrestadoraRepresentanteCerKey(documento, usuarioAplicativo);
			} catch (IOException  e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante", e);
				return	new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante", e);
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<String>(HttpStatus.OK);
		 
	    }

		@RequestMapping(value = "/eliminarDocumentoPrestadoraServicio")
	    @ResponseBody
	    public MensajeDTO eliminarDocumentoPrestadoraServicio(@RequestBody DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
			MensajeDTO mensaje = new MensajeDTO();
			try {
				prestadoraServicioBO.eliminarDocumentos(documento, usuarioAplicativo);
			
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en documento prestadora", e);
				mensaje.setCorrecto(false);
				mensaje.setMensajeError("Ocurrio un error al eliminar el documento, favor de intentarlo nuevamente.");
			}
			return mensaje;
	    }
		
		@RequestMapping(value = "/fichaTecnica")
	    @ResponseBody
	    public HashMap<String, Object> fichaTecnica(@RequestBody Long idPrestadora) throws BusinessException {
			HashMap<String, Object> documento = new HashMap<String, Object>();
			try {
				FichaTecnicaDto fichaTecnica = prestadoraServicioBO.fichaTecnica(idPrestadora);
				documento.put("fichaTecnica", fichaTecnica);
				documento.put("codigo", "200");
			} catch(Exception e) {
				LOGGER.error("Ocurrio un error en la ficha tecnica", e);
				documento.put("codigo", "500");
				documento.put("descripcionError","Ocurrio un error al mostrar la ficha tecnica, favor de intentarlo nuevamente.");
			}
			return documento;
	    }
		
		
		
		@RequestMapping(value = "/cargaInicialDatosStp")
		@ResponseBody
		public PrestadoraServicioDto cargaInicialDatosStp(Model model) {
			try {
				PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
				
				if(model.containsAttribute(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR)) {
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
					prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByStp(prestadoraServicioDto, idPrestadoraServicio);
				}
				}
										
				return prestadoraServicioDto;
				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en prestadoraServicio/cargaCatalogos ", e);
				return new PrestadoraServicioDto();
			}
		}
		
		@RequestMapping(value = "/stp/guardarDatosStp")
		@ResponseBody
		public MensajeDTO guardarDatosStp(@RequestBody PrestadoraServicioStpDto prestadoraServicioStp, Model model) {
			
			MensajeDTO mensaje = new MensajeDTO();
			try {
				Long idPrestadoraServicio = (Long) model.asMap().get(ReferenciaSeguridad.PRESTADORA_SERVICIO_EDITAR);
				if(idPrestadoraServicio != null && idPrestadoraServicio != 0) {
					 prestadoraServicioBO.guardarDatosStp(prestadoraServicioStp, idPrestadoraServicio, getUser().getIdUsuario());
				}else {
					throw new BusinessException("");
				}
			return mensaje;	
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error al guardar datos stp prestadora", e);
				mensaje.setCorrecto(false);
				mensaje.setMensajeError("Ocurrio un error al guardar datos stp, favor de intentarlo nuevamente.");
				return mensaje;
			}
		}
}
