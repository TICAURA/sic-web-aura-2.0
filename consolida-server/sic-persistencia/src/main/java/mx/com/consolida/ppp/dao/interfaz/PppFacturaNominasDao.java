package mx.com.consolida.ppp.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppFacturaNomina;



public interface PppFacturaNominasDao extends DAO<PppFacturaNomina,Long>{
	
	PppFacturaNomina getPppFactByIdPppNomina (Long idPppNomina);

}
