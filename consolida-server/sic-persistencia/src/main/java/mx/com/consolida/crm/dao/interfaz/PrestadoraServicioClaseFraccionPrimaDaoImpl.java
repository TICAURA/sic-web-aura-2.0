package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.PrestadoraServicioClaseFraccionPrimaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioClaseFraccionPrima;

@Repository
public class PrestadoraServicioClaseFraccionPrimaDaoImpl extends GenericDAO<PrestadoraServicioClaseFraccionPrima, Long> implements PrestadoraServicioClaseFraccionPrimaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioClaseFraccionPrimaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioClaseFraccionPrimaDto getPresatdorServicioClaseFraccionByIdPrestServicio(Long idPrestadora) {
		
		try {
			final List<PrestadoraServicioClaseFraccionPrimaDto> prestadoras = new ArrayList<PrestadoraServicioClaseFraccionPrimaDto>();
			StringBuilder sb = new StringBuilder();

			sb.append(" select * from prestadora_servicio_clase_fraccion_prima where id_prestadora_servicio = ? and ind_estatus = 1");
			
			
			@SuppressWarnings("unchecked")
			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  PrestadoraServicioClaseFraccionPrimaDto ps = new PrestadoraServicioClaseFraccionPrimaDto();
		        	  ps.setIdPrestadoraServicioClaseFraccionPrima(rs.getLong("id_prestadora_servicio_clase_fraccion_prima"));
		        	  ps.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
		        	  ps.setCatClase(new CatGeneralDto(rs.getLong("id_cat_clase")));
//		        	  ps.setCatRiesgo(new CatGeneralDto(rs.getLong("id_cat_riesgo")));
		        	  ps.setPrima(rs.getString("prima"));
		        	  ps.setFraccion(rs.getString("fraccion"));
		        	 
		        	  prestadoras.add(ps); 
		          LOGGER.debug("Datos : --> ps");
		          return prestadoras;
			   }
			   });
			   
			   
			   return prestadoras.get(0);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPresatdorServicioClaseFraccionByIdPrestServicio Dao ", e);
			return null;
		}
	}

}
