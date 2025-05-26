package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface CatValorDefaultDao {
	public CatValorDefaultDto obtenerCatValorDefaultById(Long id);
	public CatValorDefaultDto obtenerCatValorDefaultByIdPrincipal(Long id);
	public List<CatValorDefaultDto> obtenerListCatValorDefaultById(Long id,Long indEstatus);
	public CatValorDefaultDto guardarCatValorDefault(CatValorDefaultDto dto, UsuarioAplicativo us);
}
