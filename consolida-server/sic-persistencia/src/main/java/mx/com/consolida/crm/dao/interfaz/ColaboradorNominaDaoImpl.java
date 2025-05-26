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

import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.crm.dto.ColaboradorNominaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ColaboradorNomina;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class ColaboradorNominaDaoImpl extends GenericDAO<ColaboradorNomina, Long> implements ColaboradorNominaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ColaboradorNominaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional(readOnly=true)
	public Long guardarColaboradorNomina(ColaboradorNomina entity, UsuarioAplicativo us) {
		return create(entity);
	}
	
	@Transactional(readOnly=true)
	public ColaboradorNomina editarColaboradorNomina(ColaboradorNomina entity, UsuarioAplicativo us) {
		createOrUpdate(entity);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ColaboradorNominaDto obtenerColaboradorNomina(Long idNomina, Long idColaborador) {
		StringBuilder sql = new StringBuilder();
		sql.append("select cn.id_colaborador_nomina as idcolaborador,\r\n" + 
				"cn.id_nomina as idNomina,\r\n" + 
				"cn.id_colaborador as idColaborador,\r\n" + 
				"cn.monto_ppp as montoPpp,\r\n" + 
				"cn.correo_colaborador as correoElectronico,\r\n" + 
				"cn.numero_cuenta as numeroCuenta \r\n" + 
				" from colaborador_nomina cn where ").append(" cn.id_colaborador = ").append(idColaborador).append(" and cn.ind_estatus=1 and cn.id_nomina = ")
		.append(idNomina);
		List<ColaboradorNominaDto> list = new ArrayList<ColaboradorNominaDto>();
		list = jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ColaboradorNominaDto dto = new ColaboradorNominaDto();
					dto.setIdColaboradorNomina(rs.getLong("idcolaborador"));
					dto.setCorreoColaborador(rs.getString("correoElectronico"));
					dto.setIdColaborador(new ColaboradorDto(rs.getLong("idColaborador")));
					dto.setIdNomina(new NominaClienteDto(rs.getLong("idNomina")));
					dto.setMontoPpp(rs.getDouble("montoPpp"));
					dto.setNumeroCuenta(rs.getString("numeroCuenta"));
				return dto;	
			}
		});
		if (list!=null && !list.isEmpty())
			return list.get(0);
		else
			return null;
		
		
	}
}
