package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppFacturaDocumento;


public interface PppFacturaDocumentoDao extends DAO<PppFacturaDocumento,Long>{
	
	PppFacturaDocumento getUltimoDocComprobantePagoByIdCms(Long idCms);

	List<Long> getidPppFacturaDocumento(Long idPppFactura);
	
	List<DocumentoCSMDto> listDocumentosPppFactura(Long idPppFactura);
}
