package mx.com.consolida.service.conciliacion;


import java.util.List;


import mx.com.consolida.conciliaciones.OrdenPagoDto;



public interface OrdenPagoBO {

	public List<OrdenPagoDto> getOrdenesPagoByIdCarga(long idCarga);
	
	public Boolean existeOrdenPagoById(Long idOrdenOrigen);
}