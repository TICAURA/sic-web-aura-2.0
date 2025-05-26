package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNominaDocumento;

public interface PppNominaDocumentoDao extends DAO<PppNominaDocumento,Long>{
	
	List<DocumentoCSMDto> listDocumentosPppNomina(Long idPppNominaFactura);

}
