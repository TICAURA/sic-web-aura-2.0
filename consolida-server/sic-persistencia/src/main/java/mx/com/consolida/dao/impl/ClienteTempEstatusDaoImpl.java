package mx.com.consolida.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.ClienteTempEstatusDao;
import mx.com.consolida.entity.ClienteTempEstatus;


@Repository
public class ClienteTempEstatusDaoImpl extends GenericDAO<ClienteTempEstatus, Long> implements ClienteTempEstatusDao{

//	private static Logger LOGGER = LoggerFactory.getLogger(ClienteEstatusDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	public void apagarEstatusLogicoByIdCliente(Long idClienteTemp){
		String slq = "update cliente_temp_estatus set ind_estatus = 0 where id_cliente_temp = "+idClienteTemp;
		jdbcTemplate.execute(slq.toString());
	}
	public Long agregarClienteTempEstatus(ClienteTempEstatus cl) {
		apagarEstatusLogicoByIdCliente(cl.getClienteTemp().getIdClienteTemp());
		return create(cl); 
	}
	
}

