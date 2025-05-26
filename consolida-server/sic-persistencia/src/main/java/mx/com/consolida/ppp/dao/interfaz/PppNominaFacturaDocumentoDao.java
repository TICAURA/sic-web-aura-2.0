package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNominaFacturaDocumento;

public interface PppNominaFacturaDocumentoDao extends DAO<PppNominaFacturaDocumento,Long>{
	
	PppNominaFacturaDocumento getUltimoDocComprobantePagoByIdCms(Long idCms);

	List<Long> getidPppNominaFacturaDocumento(Long idPppNominaFactura);
	
	Boolean deleteDocFinanciamientoByIdPppNomina(Long IdPppNomia);
	
}
