package mx.com.consolida.ppp.service.interfaz;

import java.util.List;

import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.facturacion.factura.ConceptoDTO;
import mx.com.facturacion.factura.ConceptoFacturaDTO;
import mx.com.facturacion.factura.FacturaDTO;

public interface FacturaBO {

	public void guardarNominaFactura(FacturaDTO factura , Long idUsuario) ;
	public void guardarConceptNomina(FacturaDTO factura,PppNominaFactura pppNominaFactura, Long idUsuario) ;

	public FacturaDTO obtenerFacturaByIdNomina(Long idNominaPPP);

	//public FacturaDTO obtenerFacturaTotalesById(Long idNominaPPP);

	public List<ConceptoDTO> obtenerConceptosFacturaByIdPPPNominaFactura(Long IdPPPNominaFactura);

	public void eliminarConceptoFactura(ConceptoFacturaDTO conceptoFactura);

	Boolean guardarNominaFacturaFlujoAlterno(FacturaDTO factura , Long idUsuario);

	Boolean existeInfoPasoCinco(Long idPppNomina);

	Boolean updateDatosPasoCinco(Long idPppNomina, Long idUsuarioAplicativo);

}
