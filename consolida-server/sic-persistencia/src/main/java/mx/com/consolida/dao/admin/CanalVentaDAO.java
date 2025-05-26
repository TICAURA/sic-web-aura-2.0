package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.entity.CanalVenta;

public interface CanalVentaDAO   extends DAO<CanalVenta,Long> {
	
	public List<CanalVentaDto> listarTodasCanalesVenta() ;
	
	public CanalVentaDto obtenerCanalVentaByIdUsuario(Long idUsuario);
	
	public CanalVentaDto obtenerCanalVentaByIdCanalVenta(Long idCanalVenta);
	
	public List<OficinaDto> listarTodasOficinas();
	
	public OficinaDto oficinaByIdOficina(Long idOficina);
	
	public List<OficinaDto> obtenerOficinas();
	
	public List<CanalVentaDto> obtenerPromotores();

}
