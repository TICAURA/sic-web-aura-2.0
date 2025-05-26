package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.PrestadoraServicioClaseFraccionPrimaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioImssDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioImss;

@Repository
public class PrestadoraServicioImssDaoImpl extends GenericDAO<PrestadoraServicioImss, Long> implements PrestadoraServicioImssDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioImssDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioImssDto getPresatdorServicioImssByIdPrestServicio(Long idPrestadora) {
		try {
			final List<PrestadoraServicioImssDto> prestadoras = new ArrayList<PrestadoraServicioImssDto>();
			StringBuilder sb = new StringBuilder();

			sb.append(" select * from prestadora_servicio_imss where id_prestadora_servicio = ? and ind_estatus = 1");
			
			
			@SuppressWarnings("unchecked")
			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  PrestadoraServicioImssDto ps = new PrestadoraServicioImssDto();
		        	  ps.setIdPrestadoraServicioImss(rs.getLong("id_prestadora_servicio_imss"));
		        	  ps.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
		        	  ps.setPassword(rs.getString("password"));
		        	  ps.setUsuario(rs.getString("usuario"));
		        	 
		        	  prestadoras.add(ps); 
		          LOGGER.debug("Datos : --> ps");
		          return prestadoras;
			   }
			   });
			   
			   
			   return prestadoras.get(0);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPresatdorServicioImssByIdPrestServicio Dao ", e);
			return null;
		}
	}

}
