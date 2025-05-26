package mx.com.consolida.service.conciliacion;


import java.math.BigDecimal;
import java.util.List;


import mx.com.consolida.conciliaciones.CargaOrdenesPagoDto;
import mx.com.consolida.conciliaciones.ClientesDetalleDto;
import mx.com.consolida.conciliaciones.DetalleConciliacionesCCDto;
import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.conciliaciones.TotalesConciliacionesDto;


public interface CargaOrdenPagoBO {

	public List<OrdenPagoDto> getOrdenesPagoByIdCarga(long idCarga);
	
	public List<CargaOrdenesPagoDto> getCargaOrdenesPago();
	
	public void guardaOrdenesPagos(CargaOrdenesPagoDto  listOrdenesPago);
		
	public List<OrdenPagoDto> getOrdenesPago();
	
	public BigDecimal getTotalIngresos(OrdenPagoDto  orden);
	
	public Double getTotalEgresos(OrdenPagoDto  orden);
	
	public BigDecimal getTotalIngresosCliente(OrdenPagoDto  orden);
	
	public Double getTotalEgresosCliente(OrdenPagoDto  orden);
	
	public Long getClienteIngresos(OrdenPagoDto orden);
	
	public Long getClienteEgresos(OrdenPagoDto orden);

	List<OrdenPagoDto> getDetalleIngresos(OrdenPagoDto orden);
	
	List<ClientesDetalleDto> getDetalleClienteIngresos(OrdenPagoDto orden);
	
	List<ClientesDetalleDto> getDetalleClienteEgresos(OrdenPagoDto orden);
	
	List<OrdenPagoDto> getDetalleEgresos(OrdenPagoDto orden);
	
	public BigDecimal getCostoPromedio();
	
	public TotalesConciliacionesDto getTotales(OrdenPagoDto orden);
	
	public TotalesConciliacionesDto getTotalesDate(OrdenPagoDto orden);
	
    List<DetalleConciliacionesCCDto> getlistDetalleCC (OrdenPagoDto orden);
	
	
    List<DetalleConciliacionesCCDto> getlistDispersionIB (OrdenPagoDto orden);
	
	
    public Boolean crearEstatusDeposito(long idDeposito, Long idUsuario, Long idEstatusColaborador);
    
	public Boolean vinculaFacturaDeposito(long idDeposito, Long idUsuario, Long idFactura) ;
	
	
}