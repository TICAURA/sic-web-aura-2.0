package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraSolicitudesDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dao.interfaz.ClienteTempEstatusDao;
import mx.com.consolida.dao.interfaz.CostoAdicionalDao;
import mx.com.consolida.dao.interfaz.CotizadorDao;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CostoAdicionalDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.CatEstatus;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatTipoPago;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.ClienteTempBitacoraSolicitudes;
import mx.com.consolida.entity.ClienteTempEstatus;
import mx.com.consolida.entity.CostoAdicional;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.CatGeneralEnum;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class CotizadorDaoImpl extends GenericDAO<Cotizacion, Long> implements CotizadorDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private CatEstatusDao catEstatusDao;
	
	@Autowired
	private ClienteTempBitacoraSolicitudesDao clienteTempBitacoraSolicitudesDao;
	
	@Autowired
	private ClienteTempDao clienteDao;
	
	@Autowired
	private ClienteTempEstatusDao clienteEstatusDao;
	
	@Autowired
	private CatalogoDao catDao;
	
	@Autowired
	private CostoAdicionalDao costAdicDao;
	
//	@Autowired
//	private ClienteDao clienteDao;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CotizadorDaoImpl.class);
	
	public CotizacionDto convertirCotizacionDto(Cotizacion entity) {
		CotizacionDto dto = new CotizacionDto();
		dto.setIdCotizacion(entity.getIdCotizacion());
		dto.setPorcentajeNomFis(entity.getPorcentajeNomFis());
		dto.setPorcentajePpp(entity.getPorcentajePpp());
		dto.setAguinaldo(entity.getAguinaldo());
		dto.setDiasVacaciones(entity.getDiasVacaciones());
		dto.setPrimaVacacional(entity.getPrimaVacacional());
		dto.setTieneProvedor(entity.getTieneProvedor());
		dto.setFeeActual(entity.getFeeActual());
		dto.setFechaAlta(entity.getFechaAlta());
		dto.setUsuarioAlta(entity.getUsuarioAlta());
		dto.setIndEstatus(entity.getIndEstatus());
		dto.setIdVacaciones(entity.getIdVacaciones() !=null ? new CatGeneralDto(entity.getIdVacaciones().getIdCatGeneral(),entity.getIdVacaciones().getClave(),entity.getIdVacaciones().getDescripcion()):null);
		dto.setIdCosteoAsimilable(entity.getIdCosteoAsimilable() !=null ? new CatGeneralDto(entity.getIdCosteoAsimilable().getIdCatGeneral(), entity.getIdCosteoAsimilable().getClave(),entity.getIdCosteoAsimilable().getDescripcion()):null);
		dto.setIdDias(entity.getIdDias() !=null ?new CatGeneralDto(entity.getIdDias().getIdCatGeneral(), entity.getIdDias().getClave(), entity.getIdDias().getDescripcion()):null);
		dto.setIdImss(entity.getIdImss() !=null ?new CatGeneralDto(entity.getIdImss().getIdCatGeneral(), entity.getIdImss().getClave(), entity.getIdImss().getDescripcion()):null);
		
		dto.setIdPeriodicidad(new CatTipoPagoDto(entity.getIdPeriodicidad().getIdTipoPago(), entity.getIdPeriodicidad().getCveTipoPago(), entity.getIdPeriodicidad().getDescripcionTipoPago(), entity.getIdPeriodicidad().getDiasNaturales(), entity.getIdPeriodicidad().getDiasPeriodo(), entity.getIdPeriodicidad().getPorAnio(), entity.getIdPeriodicidad().getFechaAlta()));
		dto.setIdPrestaciones(entity.getIdPrestaciones() !=null ?new CatGeneralDto(entity.getIdPrestaciones().getIdCatGeneral(),entity.getIdPrestaciones().getClave(), entity.getIdPrestaciones().getDescripcion()):null);
		dto.setIdTipo(entity.getIdTipo() !=null ?new CatGeneralDto(entity.getIdTipo().getIdCatGeneral(),entity.getIdTipo().getClave(), entity.getIdTipo().getDescripcion()):null);
		dto.setIdTipoCotizacion(entity.getIdTipoCotizacion() !=null ?new CatGeneralDto(entity.getIdTipoCotizacion().getIdCatGeneral(),entity.getIdTipoCotizacion().getClave(), entity.getIdTipoCotizacion().getDescripcion()):null);
		dto.setIdTipoSolCotizacion(entity.getIdTipoSolCotizacion() !=null ?new CatGeneralDto(entity.getIdTipoSolCotizacion().getIdCatGeneral(),entity.getIdTipoSolCotizacion().getClave(), entity.getIdTipoSolCotizacion().getDescripcion()):null);
		
		dto.setIdZona(entity.getIdZona() !=null ?new CatGeneralDto(entity.getIdZona().getIdCatGeneral(),entity.getIdZona().getClave(), entity.getIdZona().getDescripcion()):null);
		dto.setIdCliente(entity.getIdCliente() !=null ?new ClienteTempDto(entity.getIdCliente().getIdClienteTemp()):null);
		dto.setIdEstatus(entity.getIdEstatus() !=null ?new CatGeneralDto(entity.getIdEstatus().getIdCatGeneral(),entity.getIdEstatus().getClave(), entity.getIdEstatus().getDescripcion()):null);
		dto.setObservacionAutorizador(entity.getObservacionAutorizador());
		dto.setComisionImss(entity.getComisionImss());
		dto.setComisionPpp(entity.getComisionPpp());
		dto.setDgMontoBrutoMensual(entity.getDgMontoBrutoMensual());
		dto.setDgVSM(entity.getDgVSM());
		dto.setDgporcCotizacionDeseado(entity.getDgporcCotizacionDeseado());
		dto.setCveCotizacion(entity.getCveCotizacion());
		dto.setIdTipoEsquema(entity.getIdTipoEsquema() != null ? new CatGeneralDto(entity.getIdTipoEsquema().getIdCatGeneral(), entity.getIdTipoEsquema().getClave(), entity.getIdTipoEsquema().getDescripcion()) : null);
		dto.setTieneCostosAdicionales(entity.getTieneCostosAdicionales());
		

		dto.setCostoAdicional(costAdicDao.obtenerCostoAdicionalByIdCotizacion(dto.getIdCotizacion()));
		dto.setNumColaboradores(numColaboradores(dto.getIdCotizacion()));
		return dto;
	}
	
	
	@Transactional
	public CotizacionDto obtenerCotizacionById(Long id){
		Cotizacion entity = new Cotizacion();
		entity = read(id);
		return convertirCotizacionDto(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Long numColaboradores(Long idCotizacion) {
		try {
			String sql = "select count(*) as numColaboradores from cotizacion_colaborador where ind_estatus=1 and id_cotizacion = "+ idCotizacion;
			List<Long> listCot = jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("numColaboradores");
				}
			});
			return listCot.get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public CotizacionDto obtenercotizacionAutorizar(Long idClienteTemp) {
		try {
		String sql = "SELECT  f.id_cotizacion as idCotizacion, f.id_cliente_temp_bitacora_solicitudes \r\n" + 
				"FROM    (SELECT  MAX(UNIX_TIMESTAMP(fecha_alta)) AS mts \r\n" + 
				"           FROM  cliente_temp_bitacora_solicitudes  where id_cliente_temp = "+idClienteTemp+" and id_estatus = 4 \r\n" + 
				"           GROUP BY HOUR(fecha_alta)) s JOIN    cliente_temp_bitacora_solicitudes  f \r\n" + 
				"ON      UNIX_TIMESTAMP(fecha_alta) = s.mts where f.id_cliente_temp = "+idClienteTemp+" and f.id_estatus = 4";
		
		List<CotizacionDto> listCot = jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
	          public CotizacionDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  CotizacionDto cot = new CotizacionDto();
	        	  cot.setIdCotizacion(rs.getLong("idCotizacion"));
	        	  Long idClienteTemp_bit_sol = rs.getLong("id_cliente_temp_bitacora_solicitudes");
				return cot; 
			   }
			   });
		
			return listCot.get(0);
		}catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public CotizacionDto guardarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo, Long idClienteTempBitacoraSolicitudes) {
		
		try {
			
			String cveCotizacion = null;
			Model model = null;
			
			if (cotizacion.getIdTipoSolCotizacion().getClave().equals("TIP_COT_GEN")) {
				String numeroConsecutivoString;
				int numeroConsecutivo;
				String apellidoMaterno = null;

				List<String> consecutivo = obtenerConsecutivoCotizacion(cotizacion);
				List<CatGeneralDto> listCatalogo = catDao.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_IMMS.getCve());
				ClienteTemp clienteTemp = clienteDao.read(cotizacion.getIdCliente().getIdClienteTemp());

				if (consecutivo.isEmpty() || consecutivo.get(0) == null) {
					
					if(clienteTemp.getNombre() != null) {
						if(clienteTemp.getApellidoMaterno() != null) {
							 apellidoMaterno = clienteTemp.getApellidoMaterno().replace(" ", "");
						}else {
							apellidoMaterno="";
						}
						
						cveCotizacion = "01" + "-" + clienteTemp.getNombre().replace(" ", "") + clienteTemp.getApellidoPaterno().replace(" ", "") + apellidoMaterno + "-";
						
					}else {
						cveCotizacion = "01" + "-" + clienteTemp.getRazonSocial().replace(" ", "") + "-";
					}

				} else {
					numeroConsecutivoString = consecutivo.get(0);
					numeroConsecutivo = Integer.parseInt(numeroConsecutivoString.substring(0, 2));
					numeroConsecutivo = numeroConsecutivo + 1;
					numeroConsecutivoString = String.format("%02d", numeroConsecutivo);

					if(clienteTemp.getNombre() != null) {
						if(clienteTemp.getApellidoMaterno() != null) {
							 apellidoMaterno = clienteTemp.getApellidoMaterno().replace(" ", "");
						}else {
							apellidoMaterno="";
						}
							cveCotizacion = numeroConsecutivoString + "-" + clienteTemp.getNombre().replace(" ", "") + clienteTemp.getApellidoPaterno().replace(" ", "") + apellidoMaterno + "-";
							 
						}else {
							cveCotizacion = numeroConsecutivoString + "-" + clienteTemp.getRazonSocial().replace(" ", "")+ "-";
						}
					
				}

				for (CatGeneralDto catalogo : listCatalogo) {
					if (catalogo.getIdCatGeneral().equals(cotizacion.getIdImss().getIdCatGeneral())) {
						cveCotizacion = cveCotizacion + catalogo.getDescripcion().replace(" ", "");
					}
				}
			}
			
			
			cotizacion.setCveCotizacion(cveCotizacion);

			Cotizacion entity = new Cotizacion();
			ClienteTemp clienteTemp = new ClienteTemp();
			CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.COTIZACION_SOLICITADA.getIdEstatus());
			
			entity = /*conv().map(cotizacion,Cotizacion.class);*/new Cotizacion(cotizacion);
			
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setFechaAlta(new Date());
			entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
			entity.setTieneCostosAdicionales(cotizacion.getTieneCostosAdicionales());
			entity.setIdCotizacion(create(entity));
			
			if(cotizacion.getIdTipoSolCotizacion().getClave().equals("TIP_COT_GEN")) {
				ClienteTempBitacoraSolicitudes solicitud = new ClienteTempBitacoraSolicitudes();
				ClienteTempBitacoraSolicitudes clienteRecuperado = new ClienteTempBitacoraSolicitudes();
				if(idClienteTempBitacoraSolicitudes != null) {
				 clienteRecuperado = clienteTempBitacoraSolicitudesDao.read(idClienteTempBitacoraSolicitudes);
					if(clienteRecuperado.getObservacion() != null) {
						solicitud.setObservacion(clienteRecuperado.getObservacion());
					}
					if(clienteRecuperado.getArchivo() != null) {
						solicitud.setArchivo(clienteRecuperado.getArchivo());
					}
					if(clienteRecuperado.getNombreArchivo() != null) {
						solicitud.setNombreArchivo(clienteRecuperado.getNombreArchivo());
					}
				}else {
					solicitud.setObservacion("CREADA SIN SOLICITUD");
				}
				clienteTemp = clienteDao.read(entity.getIdCliente().getIdClienteTemp());
				solicitud.setCotizacion(entity);
				solicitud.setCatEstatus(catEstatus);
				solicitud.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
				solicitud.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				solicitud.setClienteTemp(clienteTemp);
				solicitud.setFechaAlta(new Date());
				clienteTempBitacoraSolicitudesDao.createOrUpdate(solicitud);


				CostoAdicionalDto costAdicDto = cotizacion.getCostoAdicional();
				CostoAdicional costAdic = new CostoAdicional(costAdicDto);//conv().map(costAdicDto, CostoAdicional.class);
				costAdic.setFechaAlta(new Date());
				costAdic.setUsuarioAlta(new Usuario(usuarioAplicativo.getIdUsuario()));
				costAdic.setIndEstatus(1L);
				costAdic.setIdCotizacion(new Cotizacion(entity.getIdCotizacion()));
				costAdic.setIdClienteTemp(new ClienteTemp(entity.getIdCliente().getIdClienteTemp()));
				costAdicDao.guardarCostoAdicional(costAdic);
			}
			return conv().map(entity, CotizacionDto.class);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarCotizacion ", e);
			return new CotizacionDto();
		}
		

	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	private List<String> obtenerConsecutivoCotizacion(CotizacionDto cotizacion) {
		
		try {
			final List<String> list = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select c.cve_cotizacion ");
			sb.append(" from sin.cotizacion c ");
			sb.append(" where c.id_cliente_temp =").append(cotizacion.getIdCliente().getIdClienteTemp()).append(" and id_tipo_sol_cotizacion=").append(cotizacion.getIdTipoSolCotizacion().getIdCatGeneral());
			sb.append(" and fecha_alta = (select MAX(fecha_alta) from cotizacion where id_cliente_temp=").append(cotizacion.getIdCliente().getIdClienteTemp()).append(")");
			
			List<String> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
		          public String mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
		        	  String cve = new String();
		        	  
		        	  cve = rs.getString("cve_cotizacion");
		        	  
		        	  list.add(cve);
					return cve; 
				   }
				   });
			
				return list;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerConsecutivoCotizacion ", e);
			return Collections.emptyList();
		}
		

		
	}

	@Transactional
	public CotizacionDto editarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo) {
		try {
			Cotizacion entity = new Cotizacion();
			Long idClienteTemp = cotizacion.getIdCliente().getIdClienteTemp();
			cotizacion.setIdCliente(null);
			entity = new Cotizacion(cotizacion);//conv().map(cotizacion, Cotizacion.class);
			entity.setIdCliente(new ClienteTemp(idClienteTemp));
			update(entity);
//			costAdicDao.borradoLogicoCostoAdicional(entity.getIdCotizacion(),usuarioAplicativo.getIdUsuario());
			if(cotizacion.getIdTipoSolCotizacion().getClave().equals("TIP_COT_GEN")) {
				CostoAdicionalDto costAdicDto = cotizacion.getCostoAdicional();
				CostoAdicional costAdic = new CostoAdicional(costAdicDto);//.map(costAdicDto, CostoAdicional.class);
				costAdic.setFechaAlta(new Date());
				costAdic.setFechaModificaicon(new Date());
				costAdic.setUsuarioAlta(new Usuario(usuarioAplicativo.getIdUsuario()));
				costAdic.setUsuarioModificacion(new Usuario(usuarioAplicativo.getIdUsuario()));
				costAdic.setIndEstatus(1L);
				costAdic.setIdCotizacion(new Cotizacion(entity.getIdCotizacion()));
				costAdic.setIdClienteTemp(new ClienteTemp(entity.getIdCliente().getIdClienteTemp()));
				costAdicDao.guardarCostoAdicional(costAdic);
			}
			return convertirCotizacionDto(entity);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en editarCotizacion ", e);
			return new CotizacionDto();
		}
	}
	
	@Override
	@Transactional
	public CotizacionDto actualizarCotizacion(CotizacionDto cotizacion) {
		try {
			Cotizacion entity = new Cotizacion();
			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.COTIZACION_AUTORIZADA.getIdEstatus());

			entity = read(cotizacion.getIdCotizacion());

			CatGeneral catgeneral = new CatGeneral();
			catgeneral.setIdCatGeneral(catEstatus.getIdEstatus());
			catgeneral.setClave(catEstatus.getCveEstatus());
			catgeneral.setDescripcion(catEstatus.getDescripcionEstatus());
			entity.setIdEstatus(catgeneral);

			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioAlta(1L);
			update(entity);
			return conv().map(entity, CotizacionDto.class);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en actualizarCotizacion ", e);
			return new CotizacionDto();
		}
	}
	
	@Override
	@Transactional
	public void guardarObservacionAutorizador(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo) {
		try {
			Cotizacion entity = new Cotizacion();
			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.COTIZACION_RECHAZADA.getIdEstatus());

			entity = read(cotizacion.getIdCotizacion());

			CatGeneral catgeneral = new CatGeneral();
			catgeneral.setIdCatGeneral(catEstatus.getIdEstatus());
			catgeneral.setClave(catEstatus.getCveEstatus());
			catgeneral.setDescripcion(catEstatus.getDescripcionEstatus());
			entity.setIdEstatus(catgeneral);

			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
			entity.setObservacionAutorizador(cotizacion.getObservacionAutorizador());

			update(entity);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarObservacionAutorizador ", e);
		}
		
		
	}

	@Override
	@Transactional
	public void solicitarAutorizacionCotizacion(CotizacionDto cotizacion) {
		
		try {
			Cotizacion entity = new Cotizacion();
			CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.ENVIADA_AUTORIZADOR.getIdEstatus());
			
			entity = read(cotizacion.getIdCotizacion());
			
			CatGeneral catgeneral = new CatGeneral();
			catgeneral.setIdCatGeneral(catEstatus.getIdEstatus());
			catgeneral.setClave(catEstatus.getCveEstatus());
			catgeneral.setDescripcion(catEstatus.getDescripcionEstatus());
			entity.setIdEstatus(catgeneral);
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioAlta(1L);
			
			update(entity);
			
			ClienteTempBitacoraSolicitudes solicitud = new ClienteTempBitacoraSolicitudes();
			solicitud = clienteTempBitacoraSolicitudesDao.obtenerClienteXIdCotizacion(cotizacion.getIdCotizacion());
			
			solicitud.setCotizacion(entity);
			solicitud.setCatEstatus(catEstatus);
			solicitud.setClienteTemp(entity.getIdCliente());
			clienteTempBitacoraSolicitudesDao.update(solicitud);
			
			
			//Se cambia estatus a cliente_temp
			ClienteTempEstatus estatus= new ClienteTempEstatus();
			estatus.setCatEstatus(catEstatus);
			estatus.setClienteTemp(entity.getIdCliente());
			estatus.setFechaAlta(new Date());
			estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			estatus.setUsuarioAlta(1L);
//			clienteEstatusDao.apagarEstatusLogicoByIdCliente(entity.getIdCliente().getIdClienteTemp());
//			clienteEstatusDao.create(estatus);
			clienteEstatusDao.agregarClienteTempEstatus(estatus);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en solicitarAutorizacionCotizacion ", e);
		}

	}
	
	@Override
	@Transactional
	public Cotizacion obtenerCotizacionXId(Long idCotizacion) {
		Cotizacion entity = new Cotizacion();
		return entity = read(idCotizacion);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<CotizacionDto> obtenerCotizacionesXIdClienteTemp(Long idClienteTemp) {
		try {
			final List<CotizacionDto> list = new ArrayList<CotizacionDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select c.id_cotizacion, c.id_cliente_temp ");
			sb.append(" from sin.cotizacion c ");
			sb.append(" where c.id_cliente_temp =").append(idClienteTemp);

			List<CotizacionDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CotizacionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CotizacionDto cotizacion = new CotizacionDto();

					cotizacion.setIdCotizacion(rs.getLong("id_cotizacion"));
					cotizacion.setIdCliente(new ClienteTempDto());
					cotizacion.getIdCliente().setIdClienteTemp(rs.getLong("id_cliente_temp"));

					list.add(cotizacion);
					return cotizacion;
				}
			});
			return list;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCotizacionesXIdClienteTemp ", e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void actualizarEstatusCotizaciones(List<CotizacionDto> listCotizaciones, UsuarioAplicativo usuarioAplicativo, String motivo) {
		try {
			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.PROSPECTO_DECLINADO.getIdEstatus());
			Cotizacion entityCotizacion = new Cotizacion();

			entityCotizacion = read(listCotizaciones.get(0).getIdCotizacion());

			for (CotizacionDto cotizacion : listCotizaciones) {
				Cotizacion entity = new Cotizacion();

				entity = read(cotizacion.getIdCotizacion());

				CatGeneral catgeneral = new CatGeneral();
				catgeneral.setIdCatGeneral(catEstatus.getIdEstatus());
				catgeneral.setClave(catEstatus.getCveEstatus());
				catgeneral.setDescripcion(catEstatus.getDescripcionEstatus());
			
				entity.setIdEstatus(catgeneral);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());

				update(entity);

				ClienteTempBitacoraSolicitudes solicitud = new ClienteTempBitacoraSolicitudes();
				solicitud = clienteTempBitacoraSolicitudesDao.obtenerClienteXIdCotizacion(cotizacion.getIdCotizacion());
				
				if (solicitud.getIdClienteTempBitacoraSolicitudes() != null) {
					if(solicitud.getCotizacion() != null  &&  solicitud.getCotizacion().getIdCotizacion() != null) {
						solicitud.setCotizacion(entity);
						solicitud.setCatEstatus(catEstatus);
						solicitud.setClienteTemp(entity.getIdCliente());
						clienteTempBitacoraSolicitudesDao.update(solicitud);
					}
				}

			}

			// Se cambia estatus a cliente_temp
			ClienteTempEstatus estatus = new ClienteTempEstatus();
			estatus.setCatEstatus(catEstatus);
			estatus.setClienteTemp(entityCotizacion.getIdCliente());
			estatus.setFechaAlta(new Date());
			estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			estatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
			if(motivo!=null) {
				estatus.setMotivo(motivo);	
			}
//			clienteEstatusDao.apagarEstatusLogicoByIdCliente(entityCotizacion.getIdCliente().getIdClienteTemp());
//			clienteEstatusDao.create(estatus);
			clienteEstatusDao.agregarClienteTempEstatus(estatus);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en actualizarEstatusCotizaciones ", e);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean rechazarProspecto(String motivo, UsuarioAplicativo usuarioAplicativo, Long idClienteTemp) {
		try {
			
			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.PROSPECTO_RECHAZADO.getIdEstatus());
			
			ClienteTempEstatus clienteTempEstatus = new ClienteTempEstatus();
			clienteTempEstatus.setMotivo(motivo);
			clienteTempEstatus.setCatEstatus(catEstatus);
			clienteTempEstatus.setClienteTemp(clienteDao.read(idClienteTemp));
			clienteTempEstatus.setFechaAlta(new Date());
			clienteTempEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			clienteTempEstatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
//			clienteEstatusDao.apagarEstatusLogicoByIdCliente(idClienteTemp);
//			clienteEstatusDao.create(clienteTempEstatus);
			clienteEstatusDao.agregarClienteTempEstatus(clienteTempEstatus);
			
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en rechazarPropecto ", e);
			return Boolean.FALSE;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean autorizarProspecto(UsuarioAplicativo usuarioAplicativo, Long idClienteTemp) {
		try {
			
			CatEstatus catEstatus = catEstatusDao.read(CatEstatusEnum.PROSPECTO_AUTORIZADO.getIdEstatus());
			
			ClienteTempEstatus clienteTempEstatus = new ClienteTempEstatus();
			clienteTempEstatus.setMotivo(null);
			clienteTempEstatus.setCatEstatus(catEstatus);
			
			clienteTempEstatus.setClienteTemp(clienteDao.read(idClienteTemp));
			clienteTempEstatus.setFechaAlta(new Date());
			clienteTempEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			clienteTempEstatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
//			clienteEstatusDao.apagarEstatusLogicoByIdCliente(idClienteTemp);
//			clienteEstatusDao.create(clienteTempEstatus);
			clienteEstatusDao.agregarClienteTempEstatus(clienteTempEstatus);
			
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en autorizarPropecto ", e);
			return Boolean.FALSE;
		
		}
	}
	
}

