package mx.com.consolida.ppp.service.interfaz;

import java.util.List;

import mx.com.consolida.ppp.dto.CatFolioDto;
import mx.com.consolida.ppp.dto.CatSerieDto;

public interface SerieFolioBO {
	
	Boolean guardarActualizarSerie(CatSerieDto catSerieDto, Long idUsuarioAplicativo);
	
	List<CatSerieDto> listaCatSerie();
	
	Boolean eliminarSerie(Long idCatSerie, Long idUsuarioAplicativo);
	
	Boolean guardarActualizarFolio(CatFolioDto catFolioDto, Long idUsuarioAplicativo);
	
	List<CatFolioDto> listaCatFolio();
	
	Boolean eliminarFolio(Long idCatFolio, Long idUsuarioAplicativo);

}
