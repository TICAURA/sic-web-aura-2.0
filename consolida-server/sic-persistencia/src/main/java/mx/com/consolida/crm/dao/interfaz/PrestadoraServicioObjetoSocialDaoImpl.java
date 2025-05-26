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

import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioObjetoSocialDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioObjetoSocial;

@Repository
public class PrestadoraServicioObjetoSocialDaoImpl extends GenericDAO<PrestadoraServicioObjetoSocial, Long> implements PrestadoraServicioObjetoSocialDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioObjetoSocialDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public List<PrestadoraServicioObjetoSocialDto> getListObjetoSocialByIdPrestadora(Long idPrestadora) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT psos.id_prestadora_servicio_objeto_social, psos.id_prestadora_servicio, psos.descripcion_objeto_social "); 
					sb.append(" FROM sin.prestadora_servicio_objeto_social psos, sin.prestadora_servicio ps ");
					sb.append(" where ps.id_prestadora_servicio = psos.id_prestadora_servicio ");
					sb.append(" and psos.id_prestadora_servicio = ? ");
					sb.append(" and psos.ind_estatus = 1  ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioObjetoSocialDto patronal = new PrestadoraServicioObjetoSocialDto();
					patronal.setIdPrestadoraServicioObjetoSocial((rs.getLong("id_prestadora_servicio_objeto_social")));
					patronal.setDescripcion(rs.getString("descripcion_objeto_social"));
					patronal.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
					return patronal;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListObjetoSocialByIdPrestadora ", e);
			return Collections.emptyList();
		}
	}

}
