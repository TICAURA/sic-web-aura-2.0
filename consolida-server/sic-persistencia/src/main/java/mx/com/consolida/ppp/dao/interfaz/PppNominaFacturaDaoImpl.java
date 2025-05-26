package mx.com.consolida.ppp.dao.interfaz;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNominaFactura;

@Repository
public class PppNominaFacturaDaoImpl extends GenericDAO<PppNominaFactura, Long> implements PppNominaFacturaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppNominaFacturaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true)
	public PppNominaFactura getPppNominaFactByIdPppNomina(Long idPppNomina) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select ppp from PppNominaFactura ppp where ppp.nominaCliente.idPppNomina = :idPppNomina and ppp.indEstatus = 1");
			query.setParameter("idPppNomina", idPppNomina);
			
			if((PppNominaFactura) query.uniqueResult()!=null) {				
				return (PppNominaFactura) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPppNominaFactByIdPppNomina ", e);
			return null;
		}
	}

}
