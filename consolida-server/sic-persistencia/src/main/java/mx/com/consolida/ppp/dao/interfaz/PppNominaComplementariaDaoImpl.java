package mx.com.consolida.ppp.dao.interfaz;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNominaComplementaria;

@Repository
public class PppNominaComplementariaDaoImpl extends GenericDAO<PppNominaComplementaria, Long> implements PppNominaComplementariaDao{

	private static final Logger LOGGER = LoggerFactory.getLogger(PppNominaComplementariaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;



}
