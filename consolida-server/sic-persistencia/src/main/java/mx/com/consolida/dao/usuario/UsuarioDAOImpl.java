package mx.com.consolida.dao.usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.usuario.EstadoUsuarioEnum;
import mx.com.consolida.usuario.PersonaDto;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class UsuarioDAOImpl extends GenericDAO<Usuario, Long>  implements UsuarioDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDAOImpl.class);
	   
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional
	public Usuario getById(int id){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Usuario.class);
		 
		Usuario usuario =  (Usuario)  criteria.uniqueResult();
		
		return usuario;
	}
	
	
	public Usuario getUsuarioAutenticacion(UsuarioDTO usuario){
		Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("from Usuario where usuario=:usuario and password = :password");
		 query.setParameter("usuario", usuario.getUsuario());
		 query.setParameter("password", usuario.getPassword());
		 
		List<Usuario> usuarios =  query.list();
		
		if(usuarios !=  null && !usuarios.isEmpty()){
			return usuarios.get(0);
		}
		 
		
		return null;
	}
	
	
	public Usuario getExisteUsuario(UsuarioDTO usuario){
		Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery("from Usuario where usuario=:usuario");
		 query.setParameter("usuario", usuario.getUsuario());
		 
		List<Usuario> usuarios =  query.list();
		
		if(usuarios !=  null && !usuarios.isEmpty()){
			return usuarios.get(0);
		}
		 
		return null;
	}
	
	public Usuario getExisteUsuario(String usuario){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Usuario where usuario=:usuario and indEstatus = :indEstatus ");
		 
		query.setParameter("usuario", usuario);
		query.setParameter("indEstatus",EstadoUsuarioEnum.ACTIVO.getCve());
		 
		List<Usuario> usuarios =  query.list();
		
		if(usuarios !=  null && !usuarios.isEmpty()){
			return usuarios.get(0);
		}
		 
		return null;
	}
	
	@Override
	public List<UsuarioDTO> getUsuarios(){
			
		StringBuilder sb = new StringBuilder();

		sb.append(" select	u.id_usuario,	du.id_persona ,u.usuario,	u.fecha_alta,u.ind_estatus ");
		sb.append(" as estatusUsuario,du.nombre,	du.apellido_paterno ,du.apellido_materno ,ur.id_usuario_rol, ur.id_rol , r.nombre as nombreRol ,r.descripcion as descripcionRol ");
		sb.append(" from usuario u  ");
		sb.append(" left join persona du on du.id_persona = u.id_persona ");
		sb.append(" left join usuario_rol ur on ur.id_usuario  = u.id_usuario  ");
		sb.append(" left join  rol r on r.id_rol = ur.id_rol  ");
		sb.append(" order by u.id_usuario, du.nombre,du.apellido_paterno ");
		
		return jdbcTemplate.query(
				 sb.toString(),
	                (rs, rowNum) ->
	                        new UsuarioDTO(
	                                rs.getLong("id_usuario"),
	                                rs.getLong("id_persona"),
	                                rs.getString("usuario"),
	                                rs.getDate("fecha_alta"),
	                                rs.getString("nombre"),
	                                rs.getString("apellido_paterno"),
	                                rs.getString("apellido_materno"),
	                                rs.getString("estatusUsuario"),
	                                rs.getLong("id_usuario_rol"),
	                                rs.getLong("id_rol"),
	                                rs.getString("nombreRol"),
	                                rs.getString("descripcionRol")
	                        )
	        );
		
	}

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UsuarioDTO> getListUsuariosByRol(Long idRol) {
		
		try {
			
			StringBuilder sb = new StringBuilder();			
			sb.append(" select per.id_persona, per.nombre, per.apellido_paterno, per.apellido_paterno, per.rfc,per.curp, ");
			sb.append(" usu.id_usuario, usu.usuario, rol.id_rol, rol.descripcion  ");
			sb.append(" from sin.persona per, sin.usuario usu, usuario_rol usuRol, sin.rol rol ");
			sb.append(" where usu.id_persona = per.id_persona ");
			sb.append(" and usuRol.id_usuario = usu.id_usuario ");
			sb.append(" and rol.id_rol = usuRol.id_rol ");
			sb.append(" and rol.id_rol = ? ");
			sb.append(" and usu.ind_estatus = 1 ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idRol}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					UsuarioDTO usuario = new UsuarioDTO();
					usuario.setIdUsuario(rs.getLong("id_usuario"));
					usuario.setUsuario(rs.getString("usuario"));
					
					PersonaDto persona = new PersonaDto();
					persona.setIdPersona(rs.getLong("id_persona"));
					persona.setNombre(rs.getString("nombre"));
					persona.setApellidoPaterno(rs.getString("apellido_paterno"));
					persona.setApellidoMaterno(rs.getString("apellido_paterno"));
					persona.setRfc(rs.getString("rfc"));
					persona.setCurp(rs.getString("curp"));
					usuario.setPersona(persona);
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setDescripcion(rs.getString("descripcion"));
					usuario.setRol(rol);
					
					
					return usuario;
					
					
				}
			});
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getUsuariosByRol ", e);
			return Collections.emptyList();
		}

	}
	
}
