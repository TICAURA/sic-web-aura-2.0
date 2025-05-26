package mx.com.consolida.ppp.service.interfaz;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dto.HistoricoNominaDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.facturacion.factura.SeguimientoNominaDto;

public interface NominaBO {
	
	List<NominaDto> listaClientesNomina(Long idRol, Long idUsuarioAplicativo);
	
	List<NominaDto> listaClientesNominaByCelula(Long idCelula);
	
	Boolean guardarNomina(NominaDto nominaDto, Long idUsuarioAplicativo);
	

	Long guardarNominaComplementaria(NominaDto nominaDto, Long idUsuarioAplicativo);
	
	List<NominaDto> listaNominaEnProcesoByIdCliente(Long idCliente , Long idNominista);
	
	//nominas que se pueden complementar
	List<NominaDto>  listaNominaAcomplementar (Long idCliente , Long idNominista);
	
	NominaDto getDatosNominaByIdNomina(@RequestBody Long idNomina);
	
	NominaDto getIdNominaByComplementaria(@RequestBody Long idNomina);
	
	NominaDto getDatosNominaByIdNominaComplementaria(NominaDto idNomina, long idUsuario);
	
	NominaDto guardaNominaByIdNominaComplementaria(NominaDto idNomina, long idUsuario);

	List<NominaDto> getNominasParaAutorizarFinanciamiento(int idCatEstatusNomina);
	
	Boolean cambioEstatusOtorgarFinanciamientoGenerico(Long idNominaPPP, String observacion , NominaEstatusEnum estatus , Long idUsuarioAplicativo);
	
	List<HistoricoNominaDto> getHistoricoByIdPppNomina(Long idPppNomina);
	
	List<NominaDto> listaNominasFinanzasByCelula(Long idCelula, Long idCatEstatusNomina);

	void guardarCveOrdenPagoColaborador(List<EmpleadoDTO> colaboradores, Long idUsuario);
	
	List<DocumentoCSMDto> listDocumentosPppNomina(Long idPppNominaFactura);
	
	void guardarDocumentosPppNominaFactura(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	
	void eliminarDocumentosPppNomina(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException;
	
	Boolean cambiaEstatusNomina(Long idNomina, String observacion, NominaEstatusEnum nomEstaEnum, Long idUsuarioAplicativo);
	
	Boolean dispersionStpColaborador(Long idPppNomina, Long idUsuarioAplicativo);

	void crearEstatusColaboradorSTP(EmpleadoDTO colaborador, Long idUsuario, Long idEstatus);

	List<CatGeneralDto> obtenerCatEstatusNomina();
	
	List<NominaDto> listaNominasSeguimiento(SeguimientoNominaDto seguimientoNomina);

	Double getTotalNoDispersadoXNomina(Long idNomina);
	
	Double getTotalFacturadoXNomina(Long idNomina) ;

	Long getNominasComplementariasEnBorrador(Long idNomina) ;
	
	NominaDto getNominaPadre(@RequestBody Long idNomina);

}
