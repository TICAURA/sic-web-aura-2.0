package mx.com.consolida.ppp.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNominaFactura;

public interface PppNominaFacturaDao extends DAO<PppNominaFactura,Long>{
	
	PppNominaFactura getPppNominaFactByIdPppNomina (Long idPppNomina);

}
