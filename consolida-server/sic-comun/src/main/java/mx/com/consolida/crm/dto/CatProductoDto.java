package mx.com.consolida.crm.dto;

import java.io.Serializable;


public class CatProductoDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idCatProductoSAT;
	private String claveSAT;
	private String descripcionSAT;
	
	private Long idCatProducto;
	private String claveProducto;
	private String descripcionProducto;
	private Boolean indEstatus;
	
	
	
	public CatProductoDto() {
	}


	public Long getIdCatProductoSAT() {
		return idCatProductoSAT;
	}

	public void setIdCatProductoSAT(Long idCatProductoSAT) {
		this.idCatProductoSAT = idCatProductoSAT;
	}

	public String getClaveSAT() {
		return claveSAT;
	}

	public void setClaveSAT(String claveSAT) {
		this.claveSAT = claveSAT;
	}

	public String getDescripcionSAT() {
		return descripcionSAT;
	}

	public void setDescripcionSAT(String descripcionSAT) {
		this.descripcionSAT = descripcionSAT;
	}

	public Long getIdCatProducto() {
		return idCatProducto;
	}

	public void setIdCatProducto(Long idCatProducto) {
		this.idCatProducto = idCatProducto;
	}

	public String getClaveProducto() {
		return claveProducto;
	}

	public void setClaveProducto(String claveProducto) {
		this.claveProducto = claveProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}


	public Boolean getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Boolean indEstatus) {
		this.indEstatus = indEstatus;
	}
	
}
