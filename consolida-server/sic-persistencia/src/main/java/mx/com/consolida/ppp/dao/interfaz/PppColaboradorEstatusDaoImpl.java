package mx.com.consolida.ppp.dao.interfaz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppColaboradorEstatus;

@Repository
public class PppColaboradorEstatusDaoImpl extends GenericDAO<PppColaboradorEstatus, Long> implements PppColaboradorEstatusDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppColaboradorEstatusDaoImpl.class);


}
