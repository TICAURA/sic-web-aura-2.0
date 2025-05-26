package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.crm.dto.FichaTecnicaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;

public interface PrestadoraServicioDao extends mx.com.consolida.dao.DAO<PrestadoraServicio, Long>{

	List<PrestadoraServicioDto> obtenerPrestadorasServicio(Long idCelula);

	PrestadoraServicioDto obtenerEntidadFederativaXCP(String codigoPostal);
	
	List<PrestadoraServicioDto> listaPrestdorasFondoYSinFondoByIdCelula(Long idCelula, boolean esFondo);

	List<PrestadoraServicioDto> verificarFondo(Long idCelula);

	PrestadoraServicioDto obtenerPrestadoraServicioArchivoLogotipo(Long idPrestadoraServicio);
	
	List<DocumentoCSMDto> listDocumentosPrestadora(Long idPrestadora) ;

	List<DocumentoCSMDto> listDocumentosPrestadoraDefinicionDocto(String listaDefinicionDocumento, Long idPrestadora);

	public FichaTecnicaDto fichaTecnica(Long idPrestadora) throws Exception;
	
	List<PrestadoraServicioDto> listPrestadoraServicioByIdCelulaAndIdCliente(Long idCelula, Long idCliente, boolean esFondo);
}
