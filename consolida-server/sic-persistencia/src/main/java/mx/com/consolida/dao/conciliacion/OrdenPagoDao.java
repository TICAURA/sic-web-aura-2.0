package mx.com.consolida.dao.conciliacion;

import java.util.List;

import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.conciliacion.OrdenPago;

public interface OrdenPagoDao extends DAO<OrdenPago,Long> {
	
	public List<OrdenPagoDto> getOrdenPagoByIdCarga(Long idCarga);

}
