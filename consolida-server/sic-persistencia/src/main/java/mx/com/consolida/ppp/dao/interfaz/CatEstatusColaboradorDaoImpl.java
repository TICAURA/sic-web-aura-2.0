package mx.com.consolida.ppp.dao.interfaz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.CatEstatusColaborador;

@Repository
public class CatEstatusColaboradorDaoImpl extends GenericDAO<CatEstatusColaborador, Long> implements CatEstatusColaboradorDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatEstatusColaboradorDaoImpl.class);


}
