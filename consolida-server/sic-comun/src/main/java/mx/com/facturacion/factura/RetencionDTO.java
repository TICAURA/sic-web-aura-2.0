package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class RetencionDTO implements Serializable {

	private Integer idRetencion;
	private CatRetencionDTO tipoRetencion;
	private BigDecimal totalRetencion;
	
	public RetencionDTO(){
		
	}
	
	public RetencionDTO(String demo){
		
		this.idRetencion = new Integer(1);
		
		CatRetencionDTO catRetencionDTO = new CatRetencionDTO();
		catRetencionDTO.setClave("002");
		catRetencionDTO.setDescripcion("IVA Retenido");
		
		CatPorcentajeDTO catPorcentajeDTO = new CatPorcentajeDTO();
		catPorcentajeDTO.setPorcentaje(new BigDecimal("0.06000"));
		catRetencionDTO.setPorcentajeRetencion(catPorcentajeDTO);
		this.tipoRetencion =catRetencionDTO;
		this.tipoRetencion.setPorcentajeRetencion(catPorcentajeDTO);
		this.totalRetencion = new BigDecimal(600);
	}
	
	public Integer getIdRetencion() {
		return idRetencion;
	}
	public void setIdRetencion(Integer idRetencion) {
		this.idRetencion = idRetencion;
	}
	public CatRetencionDTO getTipoRetencion() {
		return tipoRetencion;
	}
	public void setTipoRetencion(CatRetencionDTO tipoRetencion) {
		this.tipoRetencion = tipoRetencion;
	}
	public BigDecimal getTotalRetencion() {
		return totalRetencion;
	}
	public void setTotalRetencion(BigDecimal totalRetencion) {
		this.totalRetencion = totalRetencion;
	}
	
}
