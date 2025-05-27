package mx.com.consolida.ppp.service.interfaz;

import java.math.BigDecimal;
import java.util.List;

import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.consolida.ppp.dto.DepositoDTO;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.facturacion.factura.ClienteDTO;
import mx.com.facturacion.factura.ConceptoDTO;
import mx.com.facturacion.factura.ConceptoFacturaDTO;
import mx.com.facturacion.factura.FacturaDTO;

public interface FacturaBO {

	public void guardarNominaFactura(FacturaDTO factura , Long idUsuario) ;
	public void guardarDateNominaFactura(FacturaDTO factura , Long idUsuario);
	public void guardarNominaFacturaRegistro(NominaDto nomina , Long idUsuario) ;
	public void guardarConceptNomina(FacturaDTO factura,PppNominaFactura pppNominaFactura, Long idUsuario) ;
	
	public FacturaDTO obtenerFacturaByIdNomina(Long idNominaPPP);
	
	//public FacturaDTO obtenerFacturaTotalesById(Long idNominaPPP);
	
	public List<ConceptoDTO> obtenerConceptosFacturaByIdPPPNominaFactura(Long IdPPPNominaFactura);
	
	public void eliminarConceptoFactura(ConceptoFacturaDTO conceptoFactura);
	
	Boolean guardarNominaFacturaFlujoAlterno(FacturaDTO factura , Long idUsuario);
	
	Boolean existeInfoPasoCinco(Long idPppNomina);
	
	Boolean updateDatosPasoCinco(Long idPppNomina, Long idUsuarioAplicativo);
	
	/*multifactura*/
	public List<FacturaDTO> obtenerFacturasDisponibles(String lista,Long idClientePPP) ;
	public List<FacturaDTO> obtenerFacturasGeneradas(Long idClientePPP) ;
	public FacturaDTO obtenerFacturaByIdNominaCliente(Long idNominaClientePPP);
	public FacturaDTO guardarFactura(FacturaDTO factura , Long idUsuario) ;
	public void vincularFacturaNomina(FacturaDTO factura , Long idUsuario) ;
	public void guardarConceptoFacturaPlus(FacturaDTO factura, Long idUsuario) ;
	public FacturaDTO obtenerFacturaByIdFactura(Long idFacturaPPP);
	public List<ConceptoDTO> obtenerConceptosFacturaByIdPPPFactura(Long IdPPPFactura) ;
	public List<ConceptoDTO> obtenerConceptosPlusFacturaByIdPPPFactura (Long IdPPPFactura) ;
	public FacturaDTO obtenerFactura(Long idPPPFactura);
	
	public DepositoDTO obtenerDeposito(String rfc,BigDecimal monto);
	public List<DepositoDTO> obtenerListDeposito(String rfc,BigDecimal monto ) ;
	public String obtenerClientesPatino(Long id_Cliente, String rfcPrestadora);
}
