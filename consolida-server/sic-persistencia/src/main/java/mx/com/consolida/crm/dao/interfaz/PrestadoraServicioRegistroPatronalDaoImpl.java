package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioRegistroPatronalDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRegistroPatronal;

@Repository
public class PrestadoraServicioRegistroPatronalDaoImpl extends GenericDAO<PrestadoraServicioRegistroPatronal, Long> implements PrestadoraServicioRegistroPatronalDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioRegistroPatronalDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioRegistroPatronalDto> getListRegistroPatronalByIdPrestadora(Long idPrestadora) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT psrp.id_prestadora_servicio_registro_patronal, psrp.id_prestadora_servicio, psrp.registro_patronal  ");
			sb.append(" FROM sin.prestadora_servicio_registro_patronal psrp, sin.prestadora_servicio ps  ");
			sb.append(" where ps.id_prestadora_servicio = psrp.id_prestadora_servicio  ");
			sb.append(" and psrp.id_prestadora_servicio = ? ");
			sb.append(" and psrp.ind_estatus = 1 ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioRegistroPatronalDto patronal = new PrestadoraServicioRegistroPatronalDto();
					patronal.setIdPrestadoraServicioRegistroPatronal(rs.getLong("id_prestadora_servicio_registro_patronal"));
					patronal.setRegistroPatronal(rs.getString("registro_patronal"));
					patronal.setPrestadoraServicioDto(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
					return patronal;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRegistroPatronalByIdPrestadora ", e);
			return Collections.emptyList();
		}
	}

}
