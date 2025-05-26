package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.NominaPeriodicidadDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.NominaPeriodicidad;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class NominaPeriodicidadDaoImpl extends GenericDAO<NominaPeriodicidad, Long> implements NominaPeriodicidadDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaPeriodicidadDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Transactional
	public NominaPeriodicidad guardarNominaPeriodicidad(NominaPeriodicidad entity, UsuarioAplicativo us) {
		final Session session = sessionFactory.getCurrentSession();

	      final Long pk = (Long) session.save(entity);
	      session.flush();
	      session.clear();
		entity.setIdCatPeriodicidad(pk);
		return entity;
	}
	
	@Transactional
	public NominaPeriodicidad editarNominaPeriodicidad(NominaPeriodicidad entity, UsuarioAplicativo us) {
		createOrUpdate(entity);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public NominaPeriodicidadDto obtenerNominaPeriodicidad(Long idNomina) {
		StringBuilder sql = new StringBuilder();
		sql.append("select id_nomina_periodicidad, id_nomina, id_cat_periodicidad,fecha_inicio,usuario_alta,usuario_modificacion, fecha_alta,fecha_modificacion from nomina_periodicidad where id_nomina = ")
		.append(idNomina);
		List<NominaPeriodicidadDto> list = new ArrayList<NominaPeriodicidadDto>();
		list = jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				NominaPeriodicidadDto dto = new NominaPeriodicidadDto();
				dto.setIdNominaPeriodicidad(rs.getLong("id_nomina_periodicidad"));
				dto.setIdNomina(rs.getLong("id_nomina"));
				dto.setIdCatPeriodicidad(rs.getLong("id_cat_periodicidad"));
				dto.setFechaInicio(rs.getDate("fecha_inicio"));
				dto.setUsuarioAlta(rs.getLong("usuario_alta"));
				dto.setUsuarioModificacion(rs.getLong("usuario_modificacion"));
				dto.setFechaAlta(rs.getDate("fecha_alta"));
				dto.setFechaModificacion(rs.getDate("fecha_modificacion"));
				return dto;	
			}
		});
		if (list!=null && !list.isEmpty())
			return list.get(0);
		else
			return null;
		
		
	}
}
