package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.celula.CelulaCliente;

public interface CelulaClienteDao extends DAO<CelulaCliente,Long>{
	
	CelulaCliente getCelulaByIdCliente(Long idCliente);
	
	CelulaCliente getAllCelulaByIdCliente(Long idPrestadora);
	
	CelulaCliente getCelulaByIdClienteAndIdCelula(Long idCliente, Long idCelula);

}
