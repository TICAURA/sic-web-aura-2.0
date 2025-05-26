package mx.com.consolida.dao.conciliacion;

import java.util.List;

import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.conciliacion.CargaOrdenPago;


public interface CargaOrdenPagoDao extends DAO<CargaOrdenPago,Long> {
	
	public List<OrdenPagoDto> getOrdenPagoByIdCarga(Long idCarga);

}
