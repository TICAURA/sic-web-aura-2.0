package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;

@SuppressWarnings("serial")
public class ConceptoDTO implements Serializable {
	
	private Long idConcepto;
	private String concepto;
	private CatGeneralDto unidad;
	private String claveProdServ;
	private BigDecimal cantidad;
	private BigDecimal precioUnitario;
	private String descripcion;
	private BigDecimal importe;
	private BigDecimal sumaImpuestos;
	private BigDecimal subtotal;
	private BigDecimal sumaRetenciones;
	private BigDecimal sumaDescuentos;
	private BigDecimal total;
	
	private String ivaTrasladado16;
	private BigDecimal ivaTrasladado16Monto;
	
	private String ivaRetenido6;
	private BigDecimal ivaRetenido6Monto;
	
	private String codigoSat;
	private String descripcionSat;
	private String descripcionConcepto;
	private String descripcionConceptoAdicional;

	
	private List<ImpuestoDTO> impuestos;
	private List<RetencionDTO> retenciones;
	private List<DescuentoDTO> descuentos;
	
	private String impuestosDescripcion;
	private String retencionDescripcion;
	
	
	
	
	public ConceptoDTO(){
		
	}
	
	public ConceptoDTO(String demo){
		this.idConcepto = 1L;
		
		this.concepto = "80111701 ";
//		this.claveUnidad="E48";
//		this.unidad = "Unidad de servicio";
		this.claveProdServ="50111515";
		
		this.cantidad = new BigDecimal(1);
		this.precioUnitario = new BigDecimal("10000");
		this.descripcion = "Servicios de contratación de personal SUMINISTRO DE PERSONAL";
		
		this.importe = new BigDecimal("10000");
		this.sumaImpuestos = new BigDecimal("1000");
		this.subtotal = new BigDecimal("10000");
		this.sumaRetenciones = new BigDecimal("600");
		
		this.total = new BigDecimal("11000");
		
		this.impuestos = new ArrayList<ImpuestoDTO>();
		this.impuestos.add(new ImpuestoDTO(demo));
		
		this.unidad = new CatGeneralDto(1L,"E48","Unidad de servicio");
		
//		this.tipo = new CatImpuestoDTO();
//		tipo.setClave("002");
//		tipo.setDescripcion("IVA Trasladado 16%");
//		
//		CatPorcentajeDTO porcentaje = new CatPorcentajeDTO();
//		porcentaje.setPorcentaje(new BigDecimal("0.160000"));
//		tipo.setPorcentajeImpuesto(porcentaje);
//		
//		this.totalImpuesto = new BigDecimal(1600);
		int i=1;
		for (ImpuestoDTO impuestoDTO : impuestos) {
			
			if(i==1) {
				this.impuestosDescripcion = 
						impuestoDTO.getTipo().getDescripcion() + " " 
				+ DecimalFormat.getInstance().format(impuestoDTO.getTotalImpuesto()).replaceFirst(",", ",");
				
			}else {
				this.impuestosDescripcion = this.impuestosDescripcion +  " <br/>" +
						impuestoDTO.getTipo().getDescripcion() + " " 
						+ DecimalFormat.getInstance().format(impuestoDTO.getTotalImpuesto()).replaceFirst(",", ",");
			}
			
			i+=1;
		}
		
		this.retenciones = new ArrayList<RetencionDTO>();
		this.retenciones.add(new RetencionDTO(demo));
		
		int j=1;
		for (RetencionDTO retencionDto : retenciones) {
			
			if(j==1 && i==1) {
				this.impuestosDescripcion = 
						retencionDto.getTipoRetencion().getDescripcion() + " " 
				+ DecimalFormat.getInstance().format(retencionDto.getTotalRetencion()).replaceFirst(",", ",");
				
			}else {
				this.impuestosDescripcion = this.impuestosDescripcion +  " <br/>" +
						retencionDto.getTipoRetencion().getDescripcion() + " " 
						+ DecimalFormat.getInstance().format(retencionDto.getTotalRetencion()).replaceFirst(",", ",");
			}
			
			j+=1;
		}
	}
	
	
	public Long getIdConcepto() {
		return idConcepto;
	}
	public void setIdConcepto(Long idConcepto) {
		this.idConcepto = idConcepto;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public CatGeneralDto getUnidad() {
		return unidad;
	}
	public void setUnidad(CatGeneralDto unidad) {
		this.unidad = unidad;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public BigDecimal getSumaImpuestos() {
		return sumaImpuestos;
	}
	public void setSumaImpuestos(BigDecimal sumaImpuestos) {
		this.sumaImpuestos = sumaImpuestos;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getSumaRetenciones() {
		return sumaRetenciones;
	}
	public void setSumaRetenciones(BigDecimal sumaRetenciones) {
		this.sumaRetenciones = sumaRetenciones;
	}
	public BigDecimal getSumaDescuentos() {
		return sumaDescuentos;
	}
	public void setSumaDescuentos(BigDecimal sumaDescuentos) {
		this.sumaDescuentos = sumaDescuentos;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public List<ImpuestoDTO> getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(List<ImpuestoDTO> impuestos) {
		this.impuestos = impuestos;
	}
	public List<RetencionDTO> getRetenciones() {
		return retenciones;
	}
	public void setRetenciones(List<RetencionDTO> retenciones) {
		this.retenciones = retenciones;
	}
	public List<DescuentoDTO> getDescuentos() {
		return descuentos;
	}
	public void setDescuentos(List<DescuentoDTO> descuentos) {
		this.descuentos = descuentos;
	}

//	public String getClaveUnidad() {
//		return claveUnidad;
//	}
//
//	public void setClaveUnidad(String claveUnidad) {
//		this.claveUnidad = claveUnidad;
//	}

	public String getClaveProdServ() {
		return claveProdServ;
	}

	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}

	public String getImpuestosDescripcion() {
		return impuestosDescripcion;
	}

	public void setImpuestosDescripcion(String impuestosDescripcion) {
		this.impuestosDescripcion = impuestosDescripcion;
	}

	public String getRetencionDescripcion() {
		return retencionDescripcion;
	}

	public void setRetencionDescripcion(String retencionDescripcion) {
		this.retencionDescripcion = retencionDescripcion;
	}

	public String getIvaTrasladado16() {
		return ivaTrasladado16;
	}

	public void setIvaTrasladado16(String ivaTrasladado16) {
		this.ivaTrasladado16 = ivaTrasladado16;
	}

	public BigDecimal getIvaTrasladado16Monto() {
		return ivaTrasladado16Monto;
	}

	public void setIvaTrasladado16Monto(BigDecimal ivaTrasladado16Monto) {
		this.ivaTrasladado16Monto = ivaTrasladado16Monto;
	}

	public String getIvaRetenido6() {
		return ivaRetenido6;
	}

	public void setIvaRetenido6(String ivaRetenido6) {
		this.ivaRetenido6 = ivaRetenido6;
	}

	public BigDecimal getIvaRetenido6Monto() {
		return ivaRetenido6Monto;
	}

	public void setIvaRetenido6Monto(BigDecimal ivaRetenido6Monto) {
		this.ivaRetenido6Monto = ivaRetenido6Monto;
	}

	public String getCodigoSat() {
		return codigoSat;
	}

	public void setCodigoSat(String codigoSat) {
		this.codigoSat = codigoSat;
	}

	public String getDescripcionSat() {
		return descripcionSat;
	}

	public void setDescripcionSat(String descripcionSat) {
		this.descripcionSat = descripcionSat;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public String getDescripcionConceptoAdicional() {
		return descripcionConceptoAdicional;
	}

	public void setDescripcionConceptoAdicional(String descripcionConceptoAdicional) {
		this.descripcionConceptoAdicional = descripcionConceptoAdicional;
	}


}
