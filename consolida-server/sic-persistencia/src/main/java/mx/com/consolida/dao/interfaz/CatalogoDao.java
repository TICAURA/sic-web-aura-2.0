package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatValorDefaultDto;

public interface CatalogoDao {
	public List<CatGeneralDto> obtenerCatGeneralByClvMaestro(String clv);
	public CatGeneralDto obtenerCatGeneralByClv(String clv);
	public List<CatTipoPagoDto> otenerTipoPagoActivo();
	List<CatGeneralDto> obtenerCatEntidadFederativaByClvMaestro(String clv);
	List<CatGeneralDto> obtenerCatMunicipioByClvMaestro(String clv);
	List<CatGeneralDto> obtenerCatMunicipioByClvMaestroByEntidadFed(String clv, String entidad);
	List<CatGeneralDto> obtenerCatMunicipioByEntidadFedByCveMun(String entidad, String municipio);
	public List<CatGeneralDto> obtenerCatEstatusInicialCotizaciones();
	List<CatSubGiroComercialDto> obtenerCatSubgiroComercialXIdGiro(Long idGiro);
	public List<CatProductoDto> obtenerCatProducto();
	List<CatGeneralDto> obtenerCatGeneralNominaByClvMaestro(String clv);
	List<CatGeneralDto> obtenerCatGeneralNominaByClvMaestroAndClaveGeneral(String clv , String clvGeneral);
	List<CatGeneralDto> listaConceptoFacturaCrmByIdCliente(Long idCliente);
	CatGeneralDto obtenerCatGeneralByClvMaestroClv(String maestro, String clv);
	List<CatGeneralDto> catFormulasByIdTipoFormula(Long idTipoFormula);
	List<CatGeneralDto> obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(String clv);
	List<CatGeneralDto> obtenerCatCelula();
	List<CatGeneralDto> obtenerListaProductos();
}
