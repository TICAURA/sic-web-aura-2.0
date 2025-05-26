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
import mx.com.consolida.entity.ppp.PppFacturaNomina;
import mx.com.consolida.entity.ppp.PppFacturas;
import mx.com.consolida.entity.ppp.PppNominaFactura;

@Repository
public class PppFacturaNominasDaoImpl extends GenericDAO<PppFacturaNomina, Long> implements PppFacturaNominasDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppFacturaNominasDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true)
	public PppFacturaNomina getPppFactByIdPppNomina(Long idPppNomina) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select ppp from PppFacturaNomina ppp where ppp.nominaCliente.idPppNomina = :idPppNomina and ppp.indEstatus = 1");
			query.setParameter("idPppNomina", idPppNomina);
			
			if((PppFacturas) query.uniqueResult()!=null) {				
				return (PppFacturaNomina) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPppNominaFactByIdPppNomina ", e);
			return null;
		}
	}

}
