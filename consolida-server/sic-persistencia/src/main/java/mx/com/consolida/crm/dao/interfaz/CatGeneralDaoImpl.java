package mx.com.consolida.crm.dao.interfaz;

import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.CatGeneral;

@Repository
public class CatGeneralDaoImpl extends GenericDAO<CatGeneral, Long> implements CatGeneralDao{

}
