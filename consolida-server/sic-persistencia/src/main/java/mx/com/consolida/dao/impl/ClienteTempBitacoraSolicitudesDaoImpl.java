package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraSolicitudesDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dao.interfaz.ClienteTempEstatusDao;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.entity.CatEstatus;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.ClienteTempBitacoraSolicitudes;
import mx.com.consolida.entity.ClienteTempEstatus;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;


@Repository
public class ClienteTempBitacoraSolicitudesDaoImpl extends GenericDAO<ClienteTempBitacoraSolicitudes, Long> implements ClienteTempBitacoraSolicitudesDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private CatEstatusDao catEstatusDao;
	
	@Autowired
	private ClienteTempDao clienteDao;
	
	@Autowired
	private ClienteTempEstatusDao clienteEstatusDao;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteTempBitacoraSolicitudesDaoImpl.class);

	@Override
	@Transactional
	public void guardarSolicitudCotizacion(ClienteTempBitacoraSolicitudesDto solicitarCotizacion, UsuarioAplicativo usuarioAplicativo) {
		byte[] pixel = null;
		CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.COTIZACION_SOLICITADA.getIdEstatus());
		
		ClienteTemp clienteTemp = clienteDao.read(solicitarCotizacion.getIdClienteTemp());
		
		if(solicitarCotizacion.getArchivo().get("archivo") != null) {
		String bytesArchivo = solicitarCotizacion.getArchivo().get("archivo").toString();
		pixel = bytesArchivo.getBytes();
		}
		ClienteTempBitacoraSolicitudes solicitud = new ClienteTempBitacoraSolicitudes();
		solicitud.setCatEstatus(catEstatus);
		solicitud.setClienteTemp(clienteTemp);
		solicitud.setFechaAlta(new Date());
		solicitud.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		solicitud.setObservacion(solicitarCotizacion.getObservacion());
		solicitud.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
		solicitud.setFechaModificacion(new Date());
		
		if(solicitarCotizacion.getArchivo().get("archivo") != null) {
		solicitud.setArchivo(pixel);
		solicitud.setNombreArchivo(solicitarCotizacion.getArchivo().get("nombreArchivo").toString());
		}
		
		create(solicitud);
		
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

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly=true)
	public ClienteTempBitacoraSolicitudes obtenerClienteXIdCotizacion(Long idCotizacion) {
		List<ClienteTempBitacoraSolicitudes> list = new ArrayList<ClienteTempBitacoraSolicitudes>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select id_cliente_temp_bitacora_solicitudes, id_cliente_temp, id_cotizacion, id_estatus, observacion, fecha_alta, usuario_alta, ind_estatus ");
		sb.append(" from sin.cliente_temp_bitacora_solicitudes ");
		sb.append(" where id_cotizacion= ").append(idCotizacion);
		
		List<ClienteTempBitacoraSolicitudes> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempBitacoraSolicitudes mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  ClienteTempBitacoraSolicitudes dto = new ClienteTempBitacoraSolicitudes();
	        	  
	        	  dto.setIdClienteTempBitacoraSolicitudes(rs.getLong("id_cliente_temp_bitacora_solicitudes"));
	        	  CatEstatus catEstatus = new CatEstatus();
	        	  catEstatus.setIdEstatus(rs.getLong("id_estatus"));
	        	  dto.setCatEstatus(catEstatus);
	        	  
	        	  ClienteTemp clienteTemp = new ClienteTemp();
	        	  clienteTemp.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  dto.setClienteTemp(clienteTemp);
	        	  
	        	  Cotizacion cotizacion = new Cotizacion();
	        	  cotizacion.setIdCotizacion(rs.getLong("id_cotizacion"));
	        	  dto.setCotizacion(cotizacion);
	        	  
	        	  dto.setObservacion(rs.getString("observacion"));
	        	  
	        	  dto.setFechaAlta(rs.getDate("fecha_alta"));
	        	  dto.setUsuarioAlta(rs.getLong("usuario_alta"));
	        	  dto.setIndEstatus(rs.getLong("ind_estatus"));
	        	  
	        	  list.add(dto); 
		          LOGGER.debug("obtenerClienteXIdCotizacion--> " + sb);
				return dto;
		         
			   }
			   });
		if(list.isEmpty()) {
			ClienteTempBitacoraSolicitudes dto = new ClienteTempBitacoraSolicitudes();
			list.add(dto); 
			}
		return list.get(0);
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly=true)
	public ClienteTempBitacoraSolicitudes obtenerClienteXIdCliente(Long idClienteTemp) {
		
		try {
			
			List<ClienteTempBitacoraSolicitudes> list = new ArrayList<ClienteTempBitacoraSolicitudes>();
			StringBuilder sb = new StringBuilder();
			sb.append("select id_cliente_temp_bitacora_solicitudes, id_cliente_temp, id_cotizacion, id_estatus, observacion, fecha_alta, usuario_alta, ind_estatus, nombre_archivo, archivo ");
			sb.append(" from sin.cliente_temp_bitacora_solicitudes ctbs where ctbs.id_cliente_temp=").append(idClienteTemp);
			sb.append(" and fecha_alta=(select MAX(ctbs2.fecha_alta) from cliente_temp_bitacora_solicitudes ctbs2 where ctbs2.id_cliente_temp=ctbs.id_cliente_temp)");
			
			List<ClienteTempBitacoraSolicitudes> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
		          public ClienteTempBitacoraSolicitudes mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
		        	  ClienteTempBitacoraSolicitudes dto = new ClienteTempBitacoraSolicitudes();
		        	  
		        	  dto.setIdClienteTempBitacoraSolicitudes(rs.getLong("id_cliente_temp_bitacora_solicitudes"));
		        	  CatEstatus catEstatus = new CatEstatus();
		        	  catEstatus.setIdEstatus(rs.getLong("id_estatus"));
		        	  dto.setCatEstatus(catEstatus);
		        	  
		        	  ClienteTemp clienteTemp = new ClienteTemp();
		        	  clienteTemp.setIdClienteTemp(rs.getLong("id_cliente_temp"));
		        	  dto.setClienteTemp(clienteTemp);
		        	  
		        	  Cotizacion cotizacion = new Cotizacion();
		        	  dto.setCotizacion(cotizacion);
		        	  
		        	  dto.setObservacion(rs.getString("observacion"));
		        	  dto.setArchivo(rs.getBytes("archivo"));
		        	  dto.setNombreArchivo(rs.getString("nombre_archivo"));
		        	  
		        	  dto.setFechaAlta(rs.getDate("fecha_alta"));
		        	  dto.setUsuarioAlta(rs.getLong("usuario_alta"));
		        	  dto.setIndEstatus(rs.getLong("ind_estatus"));
		        	  
		        	  list.add(dto); 
			          LOGGER.debug("obtenerClienteXIdCliente--> " + sb);
					return dto;
			         
				   }
				   });
			if(list.isEmpty()) {
			ClienteTempBitacoraSolicitudes dto = new ClienteTempBitacoraSolicitudes();
			list.add(dto); 
			}
			return list.get(0);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en ClienteTempBitacoraSolicitudes", e);
			return new ClienteTempBitacoraSolicitudes();
		}
		

	}
	
}

