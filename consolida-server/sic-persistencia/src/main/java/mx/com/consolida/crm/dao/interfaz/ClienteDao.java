package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.crm.Cliente;

public interface ClienteDao extends DAO<Cliente,Long>{
	
	List<ClienteDto> listarProspectosAutorizar();
	boolean existeEnCliente(Long idClienteTemp);
	List<ClienteDto> listaProspectosAutorizadosByMesaControl(Long idCelula);
	Long getIdClienteByRfc(String rfc);
	DomicilioComunDto obtenerEntidadFederativaXCP(String codigoPostal);
	ClienteDto getDatosGeneralesClienteBiIdCliente(Long idCliente);
	CelulaCliente getCelulaByIdCelulaRfc(Long idCelula, String rfc);
}
