package mx.com.consolida.dao.conciliacion;




import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.conciliacion.ordenPagoEstatus;


@Repository
public class OrdenPagoEstatusDaoImpl extends  GenericDAO<ordenPagoEstatus, Long> implements OrdenPagoEstatusDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(OrdenPagoEstatusDaoImpl.class);
	
	

	
}
