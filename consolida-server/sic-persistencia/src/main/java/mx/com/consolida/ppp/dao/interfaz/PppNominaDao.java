package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.dto.NominasDTO;
import mx.com.facturacion.factura.SeguimientoNominaDto;

public interface PppNominaDao extends DAO<PppNomina,Long>{
	
	List<NominaDto> listaClientesNomina(Long idRol, Long idUsuarioAplicativo);
	
	List<NominaDto> listaClientesNominaByCelula(Long idCelula);
	
	boolean existeNomina(String clave);
	
	Integer maxConsecutivoByIdNomina(Long idNominaCliente);
	
	List<NominaDto> listaNominaEnProcesoByIdCliente(Long idCliente, Long idNominista);
	
	List<NominasDTO> listaNominaAFacturar (String ListClientes, Long idCliente);
	
	
	List<NominaDto> listaCierreNomina(Long idCliente, Long idNominista);
	
	NominaDto getDatosNominaByIdNomina(Long idNomina);
	
	List<NominaDto> getNominasParaAutorizarFinanciamiento(int idCatEstatusNomina);
	
	NominaDto getNominaDtoByClave(String claveNomina);
	
	List<NominaDto> listaNominasFinanzasByCelula(Long idCelula, Long idCatEstatusNomina);
	
	List<NominaDto> listaNominasSeguimiento(SeguimientoNominaDto seguimientoNomina);
	
	NominaDto getNominaComplemenaria(Long idNomina);
	
	boolean esNominaComplementaria(Long idNomina);
	
	NominaDto getNominaPadre(Long idNomina) ;
	
	Long getIdNominaFacturaPadre(Long idNominaHija);
}
