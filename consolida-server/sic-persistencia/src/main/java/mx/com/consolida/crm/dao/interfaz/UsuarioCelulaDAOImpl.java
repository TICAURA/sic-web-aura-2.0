package mx.com.consolida.crm.dao.interfaz;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.usuario.UsuarioDAOImpl;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.seguridad.UsuarioCelula;

@Repository
public class UsuarioCelulaDAOImpl extends GenericDAO<UsuarioCelula, Long> implements UsuarioCelulaDAO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioCelulaDAOImpl.class);
	   
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public UsuarioCelula getUsuarioCelulaByIdUsuario(Long idUsuario){
		
		try {
			
			 Session session = sessionFactory.getCurrentSession();
			 Query query = session.createQuery("select usuario from UsuarioCelula usuario where usuario.usuario.idUsuario = :idUsuario and usuario.usuario.indEstatus = 1 and usuario.indEstatus = 1 ");
			 query.setParameter("idUsuario", idUsuario);
			
			 return (UsuarioCelula) query.uniqueResult();
			 
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getUsuarioCelulaByIdUsuario ", e);
			return new UsuarioCelula();
		}
		
	}
	
}
