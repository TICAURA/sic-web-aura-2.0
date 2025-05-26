package mx.com.consolida.ppp.dao.interfaz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.CatEstatusNomina;

@Repository
public class CatEstatusNominaDaoImpl extends GenericDAO<CatEstatusNomina, Long> implements CatEstatusNominaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatEstatusNominaDaoImpl.class);

}
