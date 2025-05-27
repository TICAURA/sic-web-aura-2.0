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

import mx.com.consolida.crm.dto.NominaPeriodicidadFechasDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.NominaPeriodicidadFechas;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class NominaPeriodicidadFechasDaoImpl extends GenericDAO<NominaPeriodicidadFechas, Long> implements NominaPeriodicidadFechasDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaPeriodicidadFechasDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional
	public NominaPeriodicidadFechas guardarNominaPeriodicidad(NominaPeriodicidadFechas entity, UsuarioAplicativo us) {
		create(entity);
		return entity;
	}
	@Transactional
	public void eliminarNominaPeriodicidad(Long idNominaPeriodos) {
		String sql = "update nomina_periodicidad_fechas set ind_estatus = 0 where id_nomina_periodicidad = "+idNominaPeriodos;
		jdbcTemplate.execute(sql);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<NominaPeriodicidadFechasDto> obtenerPeriodosFechas(Long idNomina) {
		StringBuilder sql = new StringBuilder();
		sql.append("select \r\n" + 
				"npf.id_nomina_periodicidad_fechas, \r\n" + 
				"npf.id_nomina_periodicidad, \r\n" + 
				"npf.fecha_inicio_periodo, \r\n" + 
				"npf.fecha_fin_periodo, \r\n" + 
				"npf.fecha_tentativa_pago, \r\n" + 
				"npf.usuario_alta, \r\n" + 
				"npf.usuario_modificacion, \r\n" + 
				"npf.fecha_alta, \r\n" + 
				"npf.fecha_modificacion,\r\n" + 
				"npf.ind_estatus from nomina_periodicidad_fechas npf join nomina_periodicidad np on np.id_nomina_periodicidad = npf.id_nomina_periodicidad and npf.ind_estatus = 1 and  np.id_nomina = ")
		.append(idNomina)
		.append(" order by fecha_tentativa_pago asc" );
		
		List<NominaPeriodicidadFechasDto> list = new ArrayList<NominaPeriodicidadFechasDto>();
		list = jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				NominaPeriodicidadFechasDto dto = new NominaPeriodicidadFechasDto();
				dto.setIdNominaPeriodicidadFechas(rs.getLong("id_nomina_periodicidad_fechas"));
				dto.setIdNominaPeriodicidad(rs.getLong("id_nomina_periodicidad"));
				dto.setFechaInicioPeriodo(rs.getDate("fecha_inicio_periodo"));
				dto.setFechaFinPeriodo(rs.getDate("fecha_fin_periodo"));
				dto.setFechaTentativaPago(rs.getDate("fecha_tentativa_pago"));
				dto.setUsuarioAlta(rs.getLong("usuario_alta"));
				dto.setUsuarioModificacion(rs.getLong("usuario_modificacion"));
				dto.setFechaAlta(rs.getDate("fecha_alta"));
				dto.setFechaModificacion(rs.getDate("fecha_modificacion"));
				dto.setIndEstatus(rs.getString("ind_estatus"));
				return dto;	
			}
		});
		return list;
	}
}
