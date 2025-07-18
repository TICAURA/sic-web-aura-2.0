package mx.com.consolida.crm.service.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioSicofiDto;
import mx.com.consolida.usuario.UsuarioDTO;

public interface CelulaBO {
	
	void guardarCelula(CelulaDto celulaDto , Long idUsuarioAutenticado);
	
	void guardarAdministrador(CelulaDto celulaDto,  Long idUsuarioAutenticado);
	
	void guardarIntegranteCelula(CelulaDto celulaDto , Long idUsuarioAutenticado) ;
	
	void eliminarCelula(Long idCelula);

	List<CelulaDto> listarTodasLasCelulas();
	
	List<CelulaDto> listarTodasLasCelulasCliente();
	
	CelulaDto consultarDatosCelula(String rfcCelula);
	
	CelulaDto consultarDatosCelula(Long idRol, Long  idUsuario) ;
	
	List<UsuarioDTO> consultarUsuariosCelula(Long  idCelula);
	
	List<UsuarioDTO> consultarUsuariosByCelulaRol(Long  idCelula, Long idRol);
	
	CelulaDto consultarDatosCelulaByIdCliente(Long  idCliente);
	
	List<CelulaDto> consultarCelulaPorUsuario(Long  idUsuario);

	List<PrestadoraServicioSicofiDto> listaPrestadorasSicofi();
	
	List<PrestadoraServicioSicofiDto> listaPrestadorasSicofiProvision(String claveProvision, String fondo);
	

}
