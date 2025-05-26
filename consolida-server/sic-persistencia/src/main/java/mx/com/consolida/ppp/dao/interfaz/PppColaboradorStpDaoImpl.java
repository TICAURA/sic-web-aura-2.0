package mx.com.consolida.ppp.dao.interfaz;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppColaboradorStp;

@Repository
public class PppColaboradorStpDaoImpl extends GenericDAO<PppColaboradorStp, Long> implements PppColaboradorStpDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppColaboradorStpDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

}
