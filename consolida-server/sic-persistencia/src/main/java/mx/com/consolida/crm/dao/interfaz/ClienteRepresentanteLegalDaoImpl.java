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

import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteRepresentanteLegal;

@Repository
public class ClienteRepresentanteLegalDaoImpl extends GenericDAO<ClienteRepresentanteLegal, Long> implements ClienteRepresentanteLegalDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteRepresentanteLegalDaoImpl.class);


	@Override
	@Transactional(readOnly = true)
	public List<RepresentanteLegalDto> getListRepresentanteLegalByIdCliente(Long idCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select crl.id_cliente_representante_legal,  cli.id_cliente,  "); 
			sb.append(" per.id_persona, per.nombre, per.apellido_paterno,  per.apellido_materno, per.rfc, per.curp, crl.contrasena_certificado  "); 
			sb.append(" from sin.cliente_representante_legal crl, sin.cliente cli, sin.persona per  "); 
			sb.append(" where per.id_persona = crl.id_persona  "); 
			sb.append(" and cli.id_cliente = crl.id_cliente  "); 
			sb.append(" and cli.id_cliente = ? "); 
			sb.append(" and per.ind_estatus = 1  "); 
			sb.append(" and crl.ind_estatus = 1 ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					RepresentanteLegalDto representante = new RepresentanteLegalDto();
					representante.setIdGenericoRepresentanteLegal(rs.getLong("id_cliente_representante_legal"));
					representante.setIdPersona(rs.getLong("id_persona"));
					representante.setNombre(rs.getString("nombre"));
					representante.setApellidoPaterno(rs.getString("apellido_paterno"));
					representante.setApellidoMaterno(rs.getString("apellido_materno"));
					representante.setRfc(rs.getString("rfc"));
					representante.setCurp(rs.getString("curp"));
					representante.setContrasenaCertificado(rs.getString("contrasena_certificado"));
					representante.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					
					return representante;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRepresentanteLegalByIdCliente ", e);
			return Collections.emptyList();
		}
	}

}
