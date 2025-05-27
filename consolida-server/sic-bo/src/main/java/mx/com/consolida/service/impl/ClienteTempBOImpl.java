package mx.com.consolida.service.impl; 

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.dao.admin.CanalVentaDAO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.interfaz.CatGrupoDao;
import mx.com.consolida.dao.interfaz.CatTipoEventoDao;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraSolicitudesDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dao.interfaz.ClienteTempEstatusDao;
import mx.com.consolida.dao.interfaz.CotizadorDao;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatTipoEventoDto;
import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.ClienteTempEstatusDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.ObservacionAutorizadorDto;
import mx.com.consolida.dto.TotalesClienteTempDto;
import mx.com.consolida.entity.CatEstatus;
import mx.com.consolida.entity.CatGrupo;
import mx.com.consolida.entity.CatTipoEvento;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.ClienteTempBitacora;
import mx.com.consolida.entity.ClienteTempBitacoraSolicitudes;
import mx.com.consolida.entity.ClienteTempEstatus;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.interfaz.ClienteTempBO;

@Service
public class ClienteTempBOImpl implements ClienteTempBO {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteTempBOImpl.class);

	@Autowired
	private ClienteTempDao clienteDao;
	
	@Autowired
	private ClienteTempEstatusDao clienteEstatusDao;
	
	@Autowired
	private CatEstatusDao catEstatusDao;
	
	@Autowired
	private CatTipoEventoDao catTipoEventoDao;
	
	@Autowired
	private CatGrupoDao catGrupoDao;
	
	@Autowired
	private CotizadorDao cotizadorDao;
	
	@Autowired
	private CanalVentaDAO canalVentaDao;
	
	@Autowired
	private ClienteTempBitacoraSolicitudesDao clienteTempBitacoraSolicitudesDao;
	
	@Autowired
	private CatalogoBO catBo;
	
	@Transactional
	public void guardar(ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo) {
		try {
			ClienteTemp entity = new ClienteTemp();
			//ModelMapper mapper = new ModelMapper();
			if(cliente.getIdCanalVenta()==null ) {
				boolean banderaFilter = false;
				banderaFilter = usuarioAplicativo.getUsuarioRols().stream().anyMatch(rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave()));
				if(banderaFilter) {
					cliente.setIdCanalVenta(canalVentaDao.obtenerCanalVentaByIdUsuario(usuarioAplicativo.getIdUsuario()).getIdCanalVenta());
				}
			}

			if(cliente.getIdClienteTemp() != null) {
				cliente.setEstatusCliente(new ArrayList<ClienteTempEstatusDto>());
			}
			//entity = mapper.map(cliente, ClienteTemp.class);
			
			if (cliente.getGrupo() != null) {
				CatGrupo catGrupo = catGrupoDao.read(cliente.getGrupo().getIdCatGrupo());
				entity.setCatGrupo(catGrupo);
			}
			
			if (cliente.getIdClienteTemp() != null) {
				entity.setUsuarioModificacion(usuarioAplicativo.getIdUsuario());
				entity.setFechaModificacion(new Date());
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				
				entity.getIdMedioContactoTemp().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.getIdMedioContactoTemp().setFechaModificacion(new Date());
				entity.getIdMedioContactoTemp().setUsuarioModificacion(usuarioAplicativo.getIdUsuario());
				
				entity.getIdPersonaContactoTemp().setFechaModificacion(new Date());
				entity.getIdPersonaContactoTemp().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.getIdPersonaContactoTemp().setUsuarioModificacion(usuarioAplicativo.getIdUsuario());
				
			} else {
				
				if (entity.getIdMedioContactoTemp().getIdMedioContactoTemp() == null) {
					entity.getIdMedioContactoTemp().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					entity.getIdMedioContactoTemp().setFechaAlta(new Date());
					entity.getIdMedioContactoTemp().setUsuarioAlta(usuarioAplicativo.getIdUsuario());
				}

				if (entity.getIdPersonaContactoTemp().getIdPersonaContactoTemp() == null) {
					entity.getIdPersonaContactoTemp().setFechaAlta(new Date());
					entity.getIdPersonaContactoTemp().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					entity.getIdPersonaContactoTemp().setUsuarioAlta(usuarioAplicativo.getIdUsuario());
				}
				
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
				entity.setFechaAlta(new Date());
			}
			
			if (entity.getIdClienteTemp() != null && entity.getIdClienteTemp() > 0) {
				clienteDao.update(entity);
			} else {
				clienteDao.create(entity);
			}

			if (cliente.getIdClienteTemp() == null) {
				
				CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.EN_PROCESO.getIdEstatus());

				ClienteTempEstatus entityEstatus = new ClienteTempEstatus();
				entityEstatus.setClienteTemp(entity);
				entityEstatus.setCatEstatus(catEstatus);
				entityEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entityEstatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
				entityEstatus.setFechaAlta(new Date());
//				clienteEstatusDao.apagarEstatusLogicoByIdCliente(entity.getIdClienteTemp());
//				clienteEstatusDao.create(entityEstatus);
				clienteEstatusDao.agregarClienteTempEstatus(entityEstatus);
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardar ", e);
		}


	}
	
	
	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerClientes(UsuarioAplicativo usuarioAplicativo) {
		return clienteDao.obtenerClientes(usuarioAplicativo);
	}
	
	@Transactional(readOnly = true)
	public ClienteTempDto obtenerClienteById(Long idClienteTemp) {
		try {
			ClienteTempDto cliente = new ClienteTempDto();
			ClienteTemp clienteEntity = new ClienteTemp();
			cliente = clienteDao.obtenerClienteById(idClienteTemp);
			clienteEntity = clienteDao.read(idClienteTemp);

			cliente.setBitacora(obtenerBitacoras(clienteEntity.getClienteTempBitacora()));
			cliente.setEstatusCliente(obtenerEstatusCliente(clienteEntity.getClienteTempEstatus()));
			cliente.setPreCotizaciones(clienteDao.obtenerCotizaciones(idClienteTemp,44L));
			cliente.setCotizaciones(clienteDao.obtenerCotizaciones(idClienteTemp,45L));
			
			cliente.setListCatTipoEventoDto(obtenerCatTipoEventoDto(catTipoEventoDao.findAll()));

			if (clienteEntity.getIdMedioContactoTemp() != null
					&& clienteEntity.getIdMedioContactoTemp().getEstado() != null
					&& !clienteEntity.getIdMedioContactoTemp().getEstado().equals("0")) {
				cliente.getIdMedioContacto().setNombreEstado(clienteDao.obtenerNombreEstadoXCveEstado(clienteEntity.getIdMedioContactoTemp().getEstado()));
			}
			if (clienteEntity.getIdMedioContactoTemp() != null
					&& clienteEntity.getIdMedioContactoTemp().getAlcaldia() != null
					&& clienteEntity.getIdMedioContactoTemp().getAlcaldia() != 0) {
				cliente.getIdMedioContacto().setNombreAlcaldia(clienteDao.obtenerNombreAlcaldiaXIdMunicipio(clienteEntity.getIdMedioContactoTemp().getAlcaldia()));
				cliente.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), clienteEntity.getIdMedioContactoTemp().getEstado()));
			}

			if (clienteEntity.getIdGiroComercial() != null) {
				cliente.setNombreGiroComercial(clienteDao.obtenerNombreGiroComercialXId(clienteEntity.getIdGiroComercial()));
			}
			return cliente;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerClienteById ", e);
			return new ClienteTempDto();
		}
	}


	@Transactional(readOnly = true)
	private List<CatTipoEventoDto> obtenerCatTipoEventoDto(List<CatTipoEvento> listCatTipoEvento1) {
		List<CatTipoEventoDto> catTipoEvento =  new ArrayList<CatTipoEventoDto>();
		List<CatTipoEvento> listCatTipoEvento = listCatTipoEvento1.stream().sorted((o1, o2)->o1.getDescripcionTipoEvento().compareTo(o2.getDescripcionTipoEvento())).collect(Collectors.toList());
		for(CatTipoEvento tipo : listCatTipoEvento) {
			CatTipoEventoDto evento =  new CatTipoEventoDto();
			evento.setIdCatTipoEvento(tipo.getIdCatTipoEvento());
			evento.setCveTipoEvento(tipo.getCveTipoEvento());
			evento.setDescripcionTipoEvento(tipo.getDescripcionTipoEvento());
			
			catTipoEvento.add(evento);
		}
		return catTipoEvento;
	}


	@Transactional(readOnly = true)
	private List<ClienteTempEstatusDto> obtenerEstatusCliente(List<ClienteTempEstatus> clienteTempEstatus) {
		List<ClienteTempEstatusDto> listClienteTempEstatusDto = new ArrayList<ClienteTempEstatusDto>();
		
		for(ClienteTempEstatus estatus: clienteTempEstatus) {
			ClienteTempEstatusDto clienteTempEstatusDto= new ClienteTempEstatusDto();
			
			clienteTempEstatusDto.setIdClienteTempEstatus(estatus.getIdClienteTempEstatus());
			clienteTempEstatusDto.setIdEstatus(new CatEstatusDto());
			clienteTempEstatusDto.getIdEstatus().setIdEstatus(estatus.getCatEstatus().getIdEstatus());
			clienteTempEstatusDto.getIdEstatus().setCveEstatus(estatus.getCatEstatus().getCveEstatus());
			clienteTempEstatusDto.getIdEstatus().setDescripcionEstatus(estatus.getCatEstatus().getDescripcionEstatus());
						
			listClienteTempEstatusDto.add(clienteTempEstatusDto);
		}
		return listClienteTempEstatusDto;
	}


	@Transactional(readOnly = true)
	private List<ClienteTempBitacoraDto> obtenerBitacoras(List<ClienteTempBitacora> clienteTempBitacora) {
		List<ClienteTempBitacoraDto> listClienteTempBitacoraDto = new ArrayList<ClienteTempBitacoraDto>();
		
		for(ClienteTempBitacora bitacora: clienteTempBitacora) {
			ClienteTempBitacoraDto clienteTempBitacoraDto = new ClienteTempBitacoraDto();
			clienteTempBitacoraDto.setIdClienteTempBitacora(bitacora.getIdClienteTempBitacora());
			clienteTempBitacoraDto.setTipoEvento(new CatTipoEventoDto());
			clienteTempBitacoraDto.getTipoEvento().setIdCatTipoEvento(bitacora.getCatTipoEvento().getIdCatTipoEvento());
			clienteTempBitacoraDto.getTipoEvento().setCveTipoEvento(bitacora.getCatTipoEvento().getCveTipoEvento());
			clienteTempBitacoraDto.getTipoEvento().setDescripcionTipoEvento(bitacora.getCatTipoEvento().getDescripcionTipoEvento());
			clienteTempBitacoraDto.setFechaEvento(bitacora.getFechaEvento());
			clienteTempBitacoraDto.setObservacion(bitacora.getObservacion());
			clienteTempBitacoraDto.setFechaAlta(bitacora.getFechaAlta());
			
			listClienteTempBitacoraDto.add(clienteTempBitacoraDto);
		}
		return listClienteTempBitacoraDto;
	}
	
	@Transactional
	public void guardarNuevoGrupo(CatGrupoDto grupo) {
		String alta="";
		CatGrupo catGrupo = new CatGrupo();
		
		if(grupo.getDescripcionRazonSocial().contains("Grupo")) {
			alta= grupo.getDescripcionRazonSocial().substring(0, 9);
		}else {
			alta= grupo.getDescripcionRazonSocial().substring(0, 4);
		}
		
		catGrupo.setCveGrupo("ALTA_" + alta);
		catGrupo.setFechaAlta(new Date());
		catGrupo.setIndEstatus(1L);
		if(grupo.getRfc() != null) {
		catGrupo.setRfc(grupo.getRfc());
		}
		catGrupo.setDescripcionRazonSocial(grupo.getDescripcionRazonSocial());
		catGrupo.setUsuarioAlta(1L);
		catGrupoDao.create(catGrupo);
	}


	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerBitacoraSolicitudesCotizacion() {
		return clienteDao.obtenerBitacoraSolicitudesCotizacion();
	}
	
	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerBitacoraCotizacion(Long idTipoSolCotizacion) {
		return clienteDao.obtenerBitacoraCotizacion(idTipoSolCotizacion);
	}
	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerCotizacionesPorEstatus(Long idEstatus) {
		return clienteDao.obtenerBitacoraCotizacionByIdEstatus(idEstatus);
	}
	@Transactional(readOnly = true)
	public List<ClienteTempBitacoraSolicitudesDto> obtenerArchivoBitacoraSolicitudes(Long idClienteTempBitacoraSolicitudes) {
		return clienteDao.obtenerArchivoBitacoraSolicitudes(idClienteTempBitacoraSolicitudes);
	}
	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerBitacoraSolicitudesAutorizador() {
		return clienteDao.obtenerBitacoraSolicitudesAutorizador();
	}

	@Transactional
	public void guardarObservacionAutorizador(ObservacionAutorizadorDto observacionAutorizadorDto, UsuarioAplicativo usuarioAplicativo) {
		CotizacionDto cotizacion = cotizadorDao.obtenerCotizacionById(observacionAutorizadorDto.getIdCotizacion());
		CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.COTIZACION_RECHAZADA.getIdEstatus());
				
		cotizadorDao.guardarObservacionAutorizador(cotizacion, usuarioAplicativo);
		
		
		//Falta Agregar estatus de cotizacion
		ClienteTemp clienteTemp = clienteDao.read(cotizacion.getIdCliente().getIdClienteTemp());
		
		ClienteTempBitacoraSolicitudes solicitud = clienteTempBitacoraSolicitudesDao.obtenerClienteXIdCotizacion(cotizacion.getIdCotizacion());
		solicitud.setClienteTemp(clienteTemp);
		solicitud.setCatEstatus(catEstatus);
		solicitud.setFechaAlta(new Date());
		solicitud.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
		solicitud.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		clienteTempBitacoraSolicitudesDao.create(solicitud);
		
		//Se cambia estatus a cliente_temp
		ClienteTempEstatus estatus= new ClienteTempEstatus();
		estatus.setCatEstatus(catEstatus);
		estatus.setClienteTemp(clienteTemp);
		estatus.setFechaAlta(new Date());
		estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		estatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
//		clienteEstatusDao.apagarEstatusLogicoByIdCliente(clienteTemp.getIdClienteTemp());
//		clienteEstatusDao.create(estatus);
		clienteEstatusDao.agregarClienteTempEstatus(estatus);
	}


	@Transactional
	public void autorizarCotizacion(Long idCotizacion) {
		CotizacionDto cotizacion = cotizadorDao.obtenerCotizacionById(idCotizacion);
		CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.COTIZACION_AUTORIZADA.getIdEstatus());
		Cotizacion cotizacionEntity = new Cotizacion();
		cotizacionEntity = cotizadorDao.obtenerCotizacionXId(idCotizacion);
		
		ClienteTemp clienteTemp = clienteDao.read(cotizacion.getIdCliente().getIdClienteTemp());
		
		cotizadorDao.actualizarCotizacion(cotizacion);
		
		ClienteTempBitacoraSolicitudes solicitud = clienteTempBitacoraSolicitudesDao.obtenerClienteXIdCotizacion(cotizacion.getIdCotizacion());
		solicitud.setClienteTemp(clienteTemp);
		solicitud.setCotizacion(cotizacionEntity);
		solicitud.setCatEstatus(catEstatus);
		solicitud.setFechaAlta(new Date());
		solicitud.setUsuarioAlta(1L);
		solicitud.setIndEstatus(1L);
		clienteTempBitacoraSolicitudesDao.create(solicitud); 
		
		//Se cambia estatus a cliente_temp
		ClienteTempEstatus estatus= new ClienteTempEstatus();
		estatus.setCatEstatus(catEstatus);
		estatus.setClienteTemp(clienteTemp);
		estatus.setFechaAlta(new Date());
		estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		estatus.setUsuarioAlta(1L);
//		clienteEstatusDao.apagarEstatusLogicoByIdCliente(clienteTemp.getIdClienteTemp());
//		clienteEstatusDao.create(estatus);
		clienteEstatusDao.agregarClienteTempEstatus(estatus);
	}
	
	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerContadorSeguimientoXEstatus(Long idEstatus, UsuarioAplicativo usuarioAplicativo) {
		return clienteDao.obtenerContadorSeguimientoXEstatus(idEstatus,usuarioAplicativo);
	}
	
	@Transactional(readOnly = true)
	public TotalesClienteTempDto obtenerContadoresTotales(UsuarioAplicativo usuarioAplicativo) {
		TotalesClienteTempDto total = clienteDao.obtenerContadoresTotales(usuarioAplicativo);
		total.setTotalProspectosConCotizacion(total.getTotalProspectosEnCotizacion() + total.getTotalProspectosEnAutorizacion() + total.getTotalProspectosAutorizados() + total.getTotalProspectosRechazados());
		return total;
	}

	@Transactional(readOnly = true)
	public List<ClienteTempDto> obtenerRegistrosContadorPrincipal(UsuarioAplicativo usuarioAplicativo) {
		return clienteDao.obtenerRegistrosContadorPrincipal(usuarioAplicativo);
	}
	
	@Transactional(readOnly = true)
	public ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdClienteTemp(Long idClienteTemp) {
		ClienteTempBitacoraSolicitudesDto cliente = new ClienteTempBitacoraSolicitudesDto();
		cliente = clienteDao.obtenerBitacoraSolicitudesXIdClienteTemp(idClienteTemp);
		
		return cliente;
	}

	@Transactional(readOnly = true)
	public ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdCotizacion(Long idCotizacion) {
		return clienteDao.obtenerBitacoraSolicitudesXIdCotizacion(idCotizacion);
	}



	@Transactional
	public void eliminarProspecto(ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo) {
		
		try {

			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.CLIENTE_ELIMINADO.getIdEstatus());
			ClienteTemp entity = new ClienteTemp();
			ModelMapper mapper = new ModelMapper();

			if (cliente.getFechaAlta() == null) {
				entity = clienteDao.read(cliente.getIdClienteTemp());
			} else {
				if (cliente.getIdClienteTemp() != null) {
					cliente.setEstatusCliente(new ArrayList<ClienteTempEstatusDto>());
				}

				entity = mapper.map(cliente, ClienteTemp.class);
			}

			if (cliente.getGrupo() != null) {
				CatGrupo catGrupo = catGrupoDao.read(cliente.getGrupo().getIdCatGrupo());
				entity.setCatGrupo(catGrupo);
			}

			entity.setFechaModificacion(new Date());
			entity.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
			entity.setUsuarioModificacion(usuarioAplicativo.getIdUsuario());

			clienteDao.createOrUpdate(entity);

			// Se cambia estatus a cliente_temp
			ClienteTempEstatus estatus = new ClienteTempEstatus();
			estatus.setCatEstatus(catEstatus);
			estatus.setClienteTemp(entity);
			estatus.setFechaAlta(new Date());
			estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			estatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
//			clienteEstatusDao.apagarEstatusLogicoByIdCliente(entity.getIdClienteTemp());
//			clienteEstatusDao.create(estatus);
			clienteEstatusDao.agregarClienteTempEstatus(estatus);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarProspecto ", e);
		}
	}



	@Transactional(readOnly = true)
	public ClienteTempDto obtenerEntidadFederativaXCP(String codigoPostal) {
		ClienteTempDto cliente = new ClienteTempDto();
		cliente = clienteDao.obtenerEntidadFederativaXCP(codigoPostal);
		return cliente;
	}


	@Transactional(readOnly = true)
	public List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro) {
		return clienteDao.obtenerSubgiroXIdGiro(idGiro);
	}
}
