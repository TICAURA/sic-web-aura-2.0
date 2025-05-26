package mx.com.consolida.crm.service.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteBO {
	
	List<ClienteDto> listarProspectosAutorizar();
	ClienteDto cambiarEstatusProspecto(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo);
	Long registrarActualizarCliente(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo);
	List<ClienteDto> listaProspectosAutorizadosByMesaControl(Long idCelula);
	Long getIdCLienteByRfc(String rfc);
	Boolean declinarProspecto(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo);
	ClienteDto getDatosGeneralesClienteBiIdCliente(Long idCliente);
	Boolean eliminarCliente(Long idCliente, Long idUsuarioAplicativo);
	Boolean existeClienteEnCelula(Long idCelula, String rfc);
}
