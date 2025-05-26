package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
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

import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteNominista;

@Repository
public class ClienteNoministaDaoImpl  extends GenericDAO<ClienteNominista, Long> implements ClienteNoministaDao{

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteNoministaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true )
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<NoministaDto> listaNoministasByCliente(Long idCliente) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select cli.id_cliente, clinom.id_nominista, clinom.ind_estatus as ind_estatus_cli_nom , usu.id_usuario, ");
			sb.append(" per.nombre, per.apellido_paterno, per.apellido_materno, clinom.id_cliente_nominista ");
			sb.append(" from sin.cliente cli, sin.cliente_nominista clinom, sin.usuario usu, sin.usuario_rol usuRol, sin.persona per ");
			sb.append(" where clinom.id_cliente = cli.id_cliente ");
			sb.append(" and usu.id_usuario = clinom.id_nominista ");
			sb.append(" and cli.id_cliente = clinom.id_cliente ");
			sb.append(" and usuRol.id_usuario = usu.id_usuario ");
			sb.append(" and per.id_persona = usu.id_persona ");
			sb.append(" and cli.id_cliente = ? ");
			sb.append(" and clinom.ind_estatus = 1 ");
			sb.append(" and per.ind_estatus = 1 ");
			sb.append(" order by  clinom.id_cliente_nominista desc ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NoministaDto nominista =  new NoministaDto();
					nominista.setIdNominista(rs.getLong("id_nominista"));
					nominista.setNombre(rs.getString("nombre"));
					nominista.setApellidoPaterno(rs.getString("apellido_paterno"));
					nominista.setApellidoMaterno(rs.getString("apellido_materno"));
					nominista.setIdCliente(rs.getLong("id_cliente"));
					nominista.setIdUsuario(rs.getLong("id_usuario"));
					nominista.setAsignado(rs.getLong("ind_estatus_cli_nom") == 1L ? true : false );
					return nominista;
				}
 
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNoministasByCliente ", e);
			return Collections.emptyList();
		}
		
		
	}

	@Override
	@Transactional(readOnly = true )
	public ClienteNominista existeNomistaEnCliente(Long idCliente, Long idNominista) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = null;
			
			if(idNominista!=null) {
				query = session.createQuery("select clien from ClienteNominista clien where clien.cliente.idCliente = :idCliente and clien.usuarioNominista.idUsuario = :idNominista and clien.indEstatus = 1");
			}else {
				query = session.createQuery("select clien from ClienteNominista clien where clien.cliente.idCliente = :idCliente and clien.indEstatus = 1");
			}
			
			query.setParameter("idCliente", idCliente);
			
			if(idNominista!=null) {
				query.setParameter("idNominista", idNominista);
			}
			
			
			return (ClienteNominista) query.uniqueResult();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeNomistaEnCliente", e);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true )
	public List<ClienteNominista> getListNomistaClienteByCliente(Long idCliente) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select clien from ClienteNominista clien where clien.cliente.idCliente = :idCliente order by clien.idClienteNominista desc");
			query.setParameter("idCliente", idCliente);

			
			
			return query.list();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeNomistaEnCliente", e);
			return null;
		}
	}
	
}
