package mx.com.consolida.service.admin;

import java.util.List;

import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.usuario.UsuarioDTO;

public interface CanalVentaBO {
	
	public void guardarCanalVenta(CanalVentaDto canalVenta , Long idUsuarioAutenticado) ;
	
	public List<CanalVentaDto> listarTodasCanalesVenta() ;
	
	
	CanalVentaDto obtenerCanalVentaByIdUsuario(Long idUsuario);
	
	public CanalVentaDto obtenerCanalVentaByIdCanalVenta(Long idCanalVenta);
	
	public void guardarOficinaCanalVenta(OficinaDto oficinalDto , Long idUsuarioAutenticado);
	
	
	public List<OficinaDto> listarTodasOficinas();
	
	public OficinaDto oficinaByIdOficina(Long idOficina);
	
	List<CanalVentaDto> obtenerPromotores();
	
	public List<OficinaDto> obtenerOficinas();
	
	public Long asignacionOficinaCanalVenta(CanalVentaDto canalVentaDto  , Long idUsuarioAutenticado);

	public List<UsuarioDTO> obtenerUsuarioXTipoCanalVenta(String idTipoCanalVenta);
	
	
	
}
