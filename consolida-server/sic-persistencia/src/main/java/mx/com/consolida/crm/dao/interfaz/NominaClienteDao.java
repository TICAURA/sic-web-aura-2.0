package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.FormulaFacturaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.ppp.dto.ValidaCreacionNominaDto;

public interface NominaClienteDao extends DAO<NominaCliente,Long>{
	
	List<NominaClienteDto> listaNominaCliente(Long idCliente);
	List<NominaClienteDto> listaNominaCliente(Long idCliente , Long idNominista) ;
	List<NominaClienteDto> listaNominaClient(Long idCliente , Long idNominista) ;
	
	List<NominaClienteDto> listaDetalleNominaByIdCliente(Long idCliente);
	NominaClienteDto nominaClientebyId(Long idNominaCliente);
	
	FormulaFacturaDto formulaFactura( Long idNominaCliente); 
	
	ValidaCreacionNominaDto validaSecciones(Long idCliente , Long idNominaCliente);
	
}
