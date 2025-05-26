package mx.com.consolida.ppp.dao.interfaz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppColaboradorClabeInterbancaria;

@Repository
public class PppColaboradorClabeDaoImpl extends GenericDAO<PppColaboradorClabeInterbancaria, Long> implements PppColaboradorClabeDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppColaboradorClabeDaoImpl.class);


}
