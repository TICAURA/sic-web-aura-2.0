package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;

public interface PrestadoraServicioProductoDao extends mx.com.consolida.dao.DAO<PrestadoraServicioProducto, Long>{

	PrestadoraServicioProducto obtenerPrestadoraProductoXIdCatProducto(Long idCatProducto, Long idPrestadoraServicio);

	List<CatProductoDto> obtenerProductosXIdPrestadora(Long idPrestadoraServicio);

	List<PrestadoraServicioProductoDto> convertirPrestadoraServicioProductoADto(
			List<PrestadoraServicioProducto> prestadoraServicioProducto);
	
	List<PrestadoraServicioProductoDto> listaPrestadoraServicioProductosByIdCelula(Long idCelula);


}
