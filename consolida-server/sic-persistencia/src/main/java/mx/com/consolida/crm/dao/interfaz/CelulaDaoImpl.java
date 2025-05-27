package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioSicofiDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class CelulaDaoImpl extends GenericDAO<Celula, Long> implements CelulaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CelulaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CelulaDto> listarTodasCelulasCliente() {
		
		try {
			//Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			
			sb.append("  select c.id_celula ,c.clave, c.nombre, c.fecha_alta\r\n" + 
					" , c.usuario_alta, c.ind_estatus ,ca.* \r\n" + 
					"from  celula c \r\n" + 
					"left join (\r\n" + 
					"	select\r\n" + 
					"		c.id_celula as idCelula,\r\n" + 
					"		uca.id_usuario_celula ,\r\n" + 
					"		ua.id_usuario ,\r\n" + 
					"		ua.usuario,\r\n" + 
					"		ua.fecha_alta as fecha_alta_usuario,\r\n" + 
					"		p.id_persona ,\r\n" + 
					"		p.nombre as nombreRep ,\r\n" + 
					"		p.apellido_paterno ,\r\n" + 
					"		p.apellido_materno ,\r\n" + 
					"		ra.id_rol ,\r\n" + 
					"		ra.nombre as nombreRol ,\r\n" + 
					"		ra.descripcion ,\r\n" + 
					"		ura.id_usuario_rol\r\n" + 
					"	from 		celula c\r\n" + 
					"	inner join usuario_celula uca on\r\n" + 
					"		uca.id_celula = c.id_celula\r\n" + 
					"	inner join usuario ua on\r\n" + 
					"		ua.id_usuario = uca.id_usuario\r\n" + 
					"	inner join persona p on\r\n" + 
					"		p.id_persona = ua.id_persona\r\n" + 
					"	inner join usuario_rol ura on\r\n" + 
					"		ura.id_usuario = ua.id_usuario\r\n" + 
					"	inner join rol ra on\r\n" + 
					"		ra.id_rol = ura.id_rol\r\n" + 
					"		and ra.id_rol =?) ca on\r\n" + 
					"	c.id_celula = ca.idCelula ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {RolUsuarioENUM.DIRECTOR_OPERACIONES.getId()}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CelulaDto celula  = new CelulaDto();
					
					celula.setIdCelula(rs.getLong("id_celula"));
					celula.setClave(rs.getString("clave"));
					celula.setNombre(rs.getString("nombre"));
					celula.setFechaAlta(rs.getDate("fecha_alta"));
					celula.setUsuarioAlta(rs.getLong("usuario_alta"));
					celula.setIndEstatus(rs.getString("ind_estatus"));
					
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					usuarioDTO.setIdUsuarioCelula(rs.getLong("id_usuario_celula"));
					
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
										rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					celula.setAdministrador(usuarioDTO);
					
					return celula;
					
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}
		
	}
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CelulaDto> listarTodasCelularRegistradas() {
		
		try {
			//Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select ps.id_prestadora_servicio, c.id_celula, c.clave,  ps.nombre_corto \r\n" + 
					", ps.fecha_alta, ps.usuario_alta, ps.ind_estatus \r\n" + 
					",ca.*\r\n" + 
					"from prestadora_servicio ps join \r\n" + 
					"celula_prestadora_servicio cps  \r\n" + 
					"on ps.id_prestadora_servicio = cps.id_prestadora_servicio \r\n" + 
					"join celula c on c.id_celula = cps.id_celula \r\n" + 
					"left join (\r\n" + 
					"	select\r\n" + 
					"		c.id_celula as idCelulaJoin,\r\n" + 
					"		uca.id_usuario_celula ,\r\n" + 
					"		ua.id_usuario ,\r\n" + 
					"		ua.usuario,\r\n" + 
					"		ua.fecha_alta as fecha_alta_usuario,\r\n" + 
					"		p.id_persona ,\r\n" + 
					"		p.nombre as nombreRep ,\r\n" + 
					"		p.apellido_paterno ,\r\n" + 
					"		p.apellido_materno ,\r\n" + 
					"		ra.id_rol ,\r\n" + 
					"		ra.nombre as nombreRol ,\r\n" + 
					"		ra.descripcion ,\r\n" + 
					"		ura.id_usuario_rol\r\n" + 
					"	from\r\n" + 
					"		celula c\r\n" + 
					"	inner join usuario_celula uca on\r\n" + 
					"		uca.id_celula = c.id_celula\r\n" + 
					"	inner join usuario ua on\r\n" + 
					"		ua.id_usuario = uca.id_usuario\r\n" + 
					"	inner join persona p on\r\n" + 
					"		p.id_persona = ua.id_persona\r\n" + 
					"	inner join usuario_rol ura on\r\n" + 
					"		ura.id_usuario = ua.id_usuario\r\n" + 
					"	inner join rol ra on\r\n" + 
					"		ra.id_rol = ura.id_rol\r\n" + 
					"		and ra.id_rol =?) ca on\r\n" + 
					"	c.id_celula = ca.idCelulaJoin\r\n" + 
					"where ps.es_fondo =1");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {RolUsuarioENUM.DIRECTOR_OPERACIONES.getId()}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CelulaDto celula  = new CelulaDto();
					
					celula.setIdCelula(rs.getLong("id_celula"));
					celula.setClave(rs.getString("clave"));
					celula.setNombre(rs.getString("nombre_corto"));
					celula.setFechaAlta(rs.getDate("fecha_alta"));
					celula.setUsuarioAlta(rs.getLong("usuario_alta"));
					celula.setIndEstatus(rs.getString("ind_estatus"));
					
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					usuarioDTO.setIdUsuarioCelula(rs.getLong("id_usuario_celula"));
					
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
										rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					celula.setAdministrador(usuarioDTO);
					
					return celula;
					
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}
		
	}


	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CelulaDto consultarDatosCelula(String claveCelula) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select c.id_celula ,c.clave ,c.nombre , c.fecha_alta  , c.usuario_alta ,c.ind_estatus ," + 
					" ua.id_usuario ,ua.usuario,ua.fecha_alta as fecha_alta_usuario, p.id_persona ," + 
					" p.nombre as nombreRep  ,p.apellido_paterno , p.apellido_materno , ra.id_rol ,ra.nombre  , ra.descripcion , ura.id_usuario_rol " + 
					" from celula c " + 
					" left join usuario_celula uca on uca.id_celula = c.id_celula " + 
					" left join usuario ua on ua.id_usuario  = uca.id_usuario" + 
					" left join persona p on p.id_persona = ua.id_persona " + 
					" left join usuario_rol ura on ura.id_usuario  =ua.id_usuario " + 
					" left join rol ra on ra.id_rol = ura.id_rol and ra.id_rol =9 and c.clave = ? ");
			
			return jdbcTemplate.queryForObject(sb.toString(), new Object[] { claveCelula },
					new RowMapper<CelulaDto>() {
						public CelulaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							
					CelulaDto celula  = new CelulaDto();
					
					celula.setIdCelula(rs.getLong("id_celula"));
					celula.setClave(rs.getString("clave"));
					celula.setNombre(rs.getString("nombre_corto"));
					celula.setFechaAlta(rs.getDate("fecha_alta"));
					celula.setUsuarioAlta(rs.getLong("usuario_alta"));
					celula.setIndEstatus(rs.getString("ind_estatus"));
					
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setNombre(rs.getString("nombre"));
					rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					celula.setAdministrador(usuarioDTO);
					
					
					return celula;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en consultarDatosCelula ", e);
			return null;
		}
	}
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CelulaDto> consultarCelulaPorUsuario(Long idUsuario) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("  select ps.id_prestadora_servicio,c.clave,  ps.nombre_corto \r\n" );
			sb.append( " , ps.fecha_alta, ps.usuario_alta\r\n ");
			sb.append( " from prestadora_servicio ps \r\n ");
			sb.append( "join celula_prestadora_servicio cps  \r\n");
			sb.append( "	on ps.id_prestadora_servicio = cps.id_celula_prestadora_servicio \r\n");
			sb.append( "join celula c on c.id_celula = cps.id_celula \r\n");
			sb.append( "join usuario_celula uca on	uca.id_celula = c.id_celula\r\n");
			sb.append( "where	uca.id_usuario =  ?");
			
			return jdbcTemplate.query(sb.toString(), new Object[] { idUsuario }
					, new RowMapper() {
						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {			
							
					CelulaDto celula  = new CelulaDto();
					
					celula.setIdCelula(rs.getLong("id_prestadora_servicio"));
					celula.setClave(rs.getString("clave"));
					celula.setNombre(rs.getString("nombre_corto"));
					celula.setFechaAlta(rs.getDate("fecha_alta"));			
					return celula;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en consultarCelulaPorUsuario ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CelulaDto consultarDatosCelula(Long idRol, Long  idUsuario) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select ra.id_rol  , ua.id_usuario,c.id_celula ,c.clave ,c.nombre , c.fecha_alta  , c.usuario_alta ,c.ind_estatus , " + 
					" ua.id_usuario ,ua.usuario,ua.fecha_alta as fecha_alta_usuario, p.id_persona , " + 
					" p.nombre as nombreRep  ,p.apellido_paterno , p.apellido_materno , ra.id_rol ,ra.nombre  , ra.descripcion , ura.id_usuario_rol " + 
					" from celula c " + 
					" inner join usuario_celula uca on uca.id_celula = c.id_celula " + 
					" inner join usuario ua on ua.id_usuario  = uca.id_usuario " + 
					" inner join persona p on p.id_persona = ua.id_persona  " + 
					" inner join usuario_rol ura on ura.id_usuario  =ua.id_usuario " + 
					" inner join rol ra on ra.id_rol = ura.id_rol and ra.id_rol = ? and ua.id_usuario = ? ");
			
			return jdbcTemplate.queryForObject(sb.toString(), new Object[] { idRol , idUsuario},
					new RowMapper<CelulaDto>() {
						public CelulaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							
					CelulaDto celula  = new CelulaDto();
					
					celula.setIdCelula(rs.getLong("id_celula"));
					celula.setClave(rs.getString("clave"));
					celula.setNombre(rs.getString("nombre"));
					celula.setFechaAlta(rs.getDate("fecha_alta"));
					celula.setUsuarioAlta(rs.getLong("usuario_alta"));
					celula.setIndEstatus(rs.getString("ind_estatus"));
					
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setNombre(rs.getString("nombre"));
					rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					celula.setAdministrador(usuarioDTO);
					
					
					return celula;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en consultarDatosCelula ", e);
			return null;
		}
	}
	
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UsuarioDTO> consultarUsuariosCelula(Long  idCelula) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select c.id_celula ,c.clave ,c.nombre , c.fecha_alta  , c.usuario_alta ,c.ind_estatus , ");
			sb.append(" ua.id_usuario ,ua.usuario,ua.fecha_alta as fecha_alta_usuario, p.id_persona , uca.id_usuario_celula , ");
			sb.append(" p.nombre as nombreRep  ,p.apellido_paterno , p.apellido_materno , ra.id_rol ,ra.nombre  , ra.descripcion , ura.id_usuario_rol  ");
			sb.append(" from celula c  ");
			sb.append(" inner join usuario_celula uca on uca.id_celula = c.id_celula  ");
			sb.append(" inner join usuario ua on ua.id_usuario  = uca.id_usuario ");
			sb.append(" inner join persona p on p.id_persona = ua.id_persona  ");
			sb.append(" inner join usuario_rol ura on ura.id_usuario  =ua.id_usuario  ");
			sb.append(" inner join rol ra on ra.id_rol = ura.id_rol and c.id_celula = ? ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] { idCelula},
					new RowMapper<UsuarioDTO>() {
						public UsuarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					usuarioDTO.setIdUsuarioCelula(rs.getLong("id_usuario_celula"));
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setNombre(rs.getString("nombre"));
					rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					return usuarioDTO;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en consultarDatosCelula ", e);
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UsuarioDTO> consultarUsuariosByCelulaRol(Long  idCelula, Long idRol) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select c.id_celula ,c.clave ,c.nombre , c.fecha_alta  , c.usuario_alta ,c.ind_estatus , ");
			sb.append(" ua.id_usuario ,ua.usuario,ua.fecha_alta as fecha_alta_usuario, p.id_persona , uca.id_usuario_celula , ");
			sb.append(" p.nombre as nombreRep  ,p.apellido_paterno , p.apellido_materno , ra.id_rol ,ra.nombre  , ra.descripcion , ura.id_usuario_rol  ");
			sb.append(" from celula c  ");
			sb.append(" inner join usuario_celula uca on uca.id_celula = c.id_celula  ");
			sb.append(" inner join usuario ua on ua.id_usuario  = uca.id_usuario ");
			sb.append(" inner join persona p on p.id_persona = ua.id_persona  ");
			sb.append(" inner join usuario_rol ura on ura.id_usuario  =ua.id_usuario  ");
			sb.append(" inner join rol ra on ra.id_rol = ura.id_rol and c.id_celula = ? and ra.id_rol = ? and ua.ind_estatus = 1 and p.ind_estatus = 1");
			
			return jdbcTemplate.query(sb.toString(), new Object[] { idCelula, idRol},
					new RowMapper<UsuarioDTO>() {
						public UsuarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));
					
					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombreRep"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					usuarioDTO.setIdUsuarioCelula(rs.getLong("id_usuario_celula"));
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setNombre(rs.getString("nombre"));
					rol.setDescripcion(rs.getString("descripcion"));
					usuarioDTO.setRol(rol);
					
					return usuarioDTO;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en consultarDatosCelula ", e);
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PrestadoraServicioSicofiDto> listaPrestadorasSicofi() {
		
		try {
			//Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select ps.razon_social,  ps.nombre_corto, pss.nombre_centro_costos,pss.desc_centro_costos, pss.clabe_cuenta_ordenante  from prestadora_servicio ps \r\n" + 
					"join prestadora_servicio_stp pss on ps.id_prestadora_servicio = pss.id_prestadora_servicio \r\n" + 
					" where pss.ind_estatus=1 and pss.ind_sicofi =1 "
					+ "order by ps.razon_social");
			
			return jdbcTemplate.query(sb.toString(), new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioSicofiDto prestadora  = new PrestadoraServicioSicofiDto();
					
					
					prestadora.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadora.setDescCentroCostos(rs.getString("desc_centro_costos"));
					prestadora.setNombreCorto(rs.getString("nombre_corto"));
					prestadora.setClabeOrdenate(rs.getString("clabe_cuenta_ordenante"));
					prestadora.setRazonSocial(rs.getString("razon_social"));
					prestadora.setSaldo(0.00);
					
					return prestadora;
					
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}
		
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PrestadoraServicioSicofiDto> listaPrestadorasSicofiProvision(String claveProvision, String fondo) {
		
		try {
			//Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select ps.razon_social,  ps.nombre_corto, pss.nombre_centro_costos,pss.desc_centro_costos, pss.clabe_cuenta_ordenante  from prestadora_servicio ps \r\n" + 
					"join prestadora_servicio_stp pss on ps.id_prestadora_servicio = pss.id_prestadora_servicio \r\n" + 
					" where pss.ind_estatus =1 and pss.ind_sicofi =" + claveProvision );
					if (fondo !=null) {
						sb.append(" and pss.es_fondo=" + fondo);
					}
					
					sb.append( " order by pss.nombre_centro_costos ");
			
			return jdbcTemplate.query(sb.toString(), new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioSicofiDto prestadora  = new PrestadoraServicioSicofiDto();
					
					
					prestadora.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadora.setDescCentroCostos(rs.getString("desc_centro_costos"));
					prestadora.setNombreCorto(rs.getString("nombre_corto"));
					prestadora.setClabeOrdenate(rs.getString("clabe_cuenta_ordenante"));
					prestadora.setRazonSocial(rs.getString("razon_social"));
					prestadora.setSaldo(0.00);
					
					return prestadora;
					
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}
		
	}

}
