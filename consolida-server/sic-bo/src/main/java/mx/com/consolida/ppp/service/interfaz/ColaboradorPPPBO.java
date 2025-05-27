package mx.com.consolida.ppp.service.interfaz;


import java.util.List;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.ppp.dto.PppColaboradorEstatusDto;

public interface ColaboradorPPPBO {

	void guardarColaboradores(List<EmpleadoDTO> colaboradores, Long idUsuario);

	List<EmpleadoDTO> cargaInicialColaboradores(Long idNominaCliente);

	List<PppColaboradorEstatusDto> cargaEstatusColaborador(EmpleadoDTO colaborador);

	void bloquearColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo);

	void desbloquearColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo);

	void guardarColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo);

	void eliminarColaboradores(EmpleadoDTO colaborador, Long idUsuarioAplicativo);
	
	Boolean cambiarEstatusNomina(Long idPppNomina, Long idUsuarioAplicativo, Long idEstatusNomina);
	
	Boolean existenUsuariosSinTimbrar(Long idPppNomina);
	
	Boolean existenColaboradodaresTimbrados(Long idPppNomina);
	
	Boolean existenColaboradodaresPorTimbrar(Long idPppNomina);
	
	void guardarColaboradorDirecto(EmpleadoDTO colaborador, Long idUsuarioAplicativo);
	
	void eliminarColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo);
    
	List<EmpleadoDTO> cargaInicialColaboradoresNominasPadre(Long idnominaPadre);
    List<EmpleadoDTO> obtenerColaboradoresComplementarias(Long idnominaPadre);
    
    List<EmpleadoDTO> cargaColaboradoresRespuestaStp(Long idNomina);
	
}
