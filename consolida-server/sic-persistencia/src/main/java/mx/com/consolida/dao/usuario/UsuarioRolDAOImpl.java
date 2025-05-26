package mx.com.consolida.dao.usuario;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.seguridad.UsuarioRol;


@Repository
public class UsuarioRolDAOImpl extends GenericDAO<UsuarioRol, Long> implements UsuarioRolDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRolDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	public UsuarioRol findByidUsuario(final Long idUsuario) {

		final Session session = sessionFactory.getCurrentSession();

		final Criteria criteria = session.createCriteria(entityType);
		criteria.add(Restrictions.eq("usuario.idUsuario", idUsuario));

		List<UsuarioRol > usuarioRols=  criteria.list();
		
		if(usuarioRols != null && !usuarioRols.isEmpty()){
			return usuarioRols.get(0);
		}
		
		return null;
	}


	
}
