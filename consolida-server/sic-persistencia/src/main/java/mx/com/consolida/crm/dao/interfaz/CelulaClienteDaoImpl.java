package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.celula.CelulaPrestadoraServicio;
import mx.com.consolida.entity.crm.ClienteServicio;

@Repository
public class CelulaClienteDaoImpl extends GenericDAO<CelulaCliente, Long> implements CelulaClienteDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CelulaClienteDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;


	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public CelulaCliente getCelulaByIdCliente(Long idCliente) {
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.*, celcli.id_celula_cliente  ");
			sb.append(" from sin.celula cel, sin.celula_cliente celcli ");
			sb.append(" where celcli.id_celula = cel.id_celula ");
			sb.append(" and celcli.ind_estatus = 1 ");
			sb.append(" and celcli.id_cliente = ? ");
			
			List<CelulaCliente> result = jdbcTemplate.query(sb.toString(), new Object[] { idCliente},
					new RowMapper<CelulaCliente>() {
				
				public CelulaCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
					CelulaCliente celCli = new CelulaCliente();
					celCli.setIdCelulaCliente(rs.getLong("id_celula_cliente"));
					celCli.setCelula(new Celula(rs.getLong("id_celula"), rs.getString("clave"), rs.getString("nombre")));
					return celCli;
				}
			
			});
			
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			} else {
				return new CelulaCliente();
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCelulaByIdCliente", e);
			return null;
		}		
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public 	CelulaCliente getCelulaByIdClienteAndIdCelula(Long idCliente, Long idCelula) {
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.*, celcli.id_celula_cliente  ");
			sb.append(" from sin.celula cel, sin.celula_cliente celcli ");
			sb.append(" where celcli.id_celula = cel.id_celula ");
			sb.append(" and celcli.ind_estatus = 1 ");
			sb.append(" and celcli.id_cliente = ? ");
			sb.append(" and celcli.id_celula= ? ");
			
			List<CelulaCliente> result = jdbcTemplate.query(sb.toString(), new Object[] { idCliente, idCelula},
					new RowMapper<CelulaCliente>() {
				
				public CelulaCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
					CelulaCliente celCli = new CelulaCliente();
					celCli.setIdCelulaCliente(rs.getLong("id_celula_cliente"));
					celCli.setCelula(new Celula(rs.getLong("id_celula"), rs.getString("clave"), rs.getString("nombre")));
					return celCli;
				}
			
			});
			
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			} else {
				return new CelulaCliente();
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCelulaByIdCliente", e);
			return null;
		}		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public CelulaCliente getAllCelulaByIdCliente(Long idCliente) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select cp from CelulaCliente cp where cp.cliente.idCliente = :idCliente and cp.indEstatus = 1");
			query.setParameter("idCliente", idCliente);
			
			if((CelulaCliente) query.uniqueResult()!=null) {				
				return (CelulaCliente) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getAllCelulaByIdCliente ", e);
			return null;
		}		
	}
}
