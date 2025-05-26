package mx.com.consolida.ppp.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppFacturas;


public interface PppFacturasDao extends DAO<PppFacturas,Long>{
	
	PppFacturas getPppFactByIdPppNomina (Long idPppNomina);

}
