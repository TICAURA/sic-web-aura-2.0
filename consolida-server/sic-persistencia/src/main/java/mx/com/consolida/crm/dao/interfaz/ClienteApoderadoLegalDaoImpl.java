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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteApoderadoLegal;

@Repository
public class ClienteApoderadoLegalDaoImpl extends GenericDAO<ClienteApoderadoLegal, Long> implements ClienteApoderadoLegalDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteApoderadoLegalDaoImpl.class);


	@Override
	public List<ApoderadoLegalDto> getListApoderadoLegalByIdCliente(Long idCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select psal.id_cliente_apoderado_legal,  ps.id_cliente,  ");
			sb.append(" per.id_persona, per.nombre, per.apellido_paterno,  per.apellido_materno, per.rfc, per.curp, psal.id_cat_facultad, psal.poder_notarial,  ");
			sb.append(" (select descripcion from sin.cat_general where id_cat_general = psal.id_cat_facultad ) as desc_cat_facultad  ");
			sb.append(" from sin.cliente_apoderado_legal psal,sin.cliente ps, sin.persona per  ");
			sb.append(" where per.id_persona = psal.id_persona  ");
			sb.append(" and ps.id_cliente = psal.id_cliente  ");
			sb.append(" and ps.id_cliente = ?  ");
			sb.append(" and per.ind_estatus = 1  ");
			sb.append(" and psal.ind_estatus = 1 ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ApoderadoLegalDto apoderado = new ApoderadoLegalDto();
					apoderado.setIdGenericoApoderadoLegal(rs.getLong("id_cliente_apoderado_legal"));
					apoderado.setIdPersona(rs.getLong("id_persona"));
					apoderado.setNombre(rs.getString("nombre"));
					apoderado.setApellidoPaterno(rs.getString("apellido_paterno"));
					apoderado.setApellidoMaterno(rs.getString("apellido_materno"));
					apoderado.setRfc(rs.getString("rfc"));
					apoderado.setCurp(rs.getString("curp"));
					apoderado.setPoderNotarial(rs.getString("poder_notarial"));
					apoderado.setCatFacultadDto(new CatGeneralDto(rs.getLong("id_cat_facultad"), rs.getString("desc_cat_facultad")));
					apoderado.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					
					return apoderado;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListApoderadoLegalByIdPrestadoraServicio ", e);
			return Collections.emptyList();
		}
	}

}
