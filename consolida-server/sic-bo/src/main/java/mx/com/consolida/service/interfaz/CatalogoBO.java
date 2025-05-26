package mx.com.consolida.service.interfaz;


import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.generico.UsuarioAplicativo;


public interface CatalogoBO {

	public List<CatGeneralDto> obtenerCatGeneralByClvMaestro(String idMaestro);
	public CatGeneralDto obtenerCatGeneralByClv(String clv);
	public List<CatTipoPagoDto> otenerTipoPagoActivo();
	List<CatGeneralDto> obtenerCatEntidadFederativaByClvMaestro(String clv);
	List<CatGeneralDto> obtenerCatMunicipioByClvMaestroByEntidadFed(String maestro, String cveEntidad);
	List<CatGeneralDto> obtenerCatMunicipioByClvMaestro(String clv);
	List<CatGeneralDto> obtenerCatMunicipioByEntidadFedByCveMun(String cveEntidad, String cveMunicipio);
	public List<CatGeneralDto> obtenerCatEstatusInicialCotizaciones();
	public List<CatProductoDto> obtenerCatProducto();
	List<CatSubGiroComercialDto> obtenerCatSubgiroXIdGiro(Long idGiro);
	public List<CatGrupoDto> obtenerCatalogoGrupo();
	
	public CatValorDefaultDto obtenerCatValorDefaultById(Long id);
	public CatValorDefaultDto obtenerCatValorDefaultByIdPrincipal(Long id);
	public List<CatValorDefaultDto> obtenerListCatValorDefaultById(Long id,Long indEstatus);
	public CatValorDefaultDto guardarCatValorDefault(CatValorDefaultDto dto, UsuarioAplicativo us);
	public CatGeneralDto obtenerCatGeneralByClvMaestroClv(String cve, String cveSat);
	public List<CatGeneralDto> catFormulasByIdTipoFormula(int id);
	List<CatGeneralDto> obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(String clv);

}