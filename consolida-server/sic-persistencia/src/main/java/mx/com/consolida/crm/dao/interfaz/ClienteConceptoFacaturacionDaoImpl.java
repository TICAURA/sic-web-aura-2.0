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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.crm.dto.ClienteConceptoFacaturacionDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClientePersonaContactoDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteConceptoFacturacion;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class ClienteConceptoFacaturacionDaoImpl extends GenericDAO<ClienteConceptoFacturacion, Long> implements ClienteConceptoFacaturacionDao{

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteConceptoFacaturacionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteConceptoFacaturacionDto> getListConceptoFactruByCliente(Long idCliente) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT ccf.id_concepto_facturacion_cliente, ccf.id_cliente, ccf.concepto_facturacion ");
			sb.append(" FROM sin.cliente_concepto_facturacion ccf, sin.cliente cli ");
			sb.append(" where cli.id_cliente = ccf.id_cliente ");
			sb.append(" and ccf.id_cliente = ? ");
			sb.append(" and ccf.ind_estatus = 1 ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClienteConceptoFacaturacionDto clienteConcepFact = new ClienteConceptoFacaturacionDto();
					clienteConcepFact.setIdConceptoFacturacionCliente(rs.getLong("id_concepto_facturacion_cliente"));
					clienteConcepFact.setDescConceptoFacturacion(rs.getString("concepto_facturacion"));
					clienteConcepFact.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					return clienteConcepFact;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListConceptoFactruByCliente ", e);
			return Collections.emptyList();
		}
	}
}
