package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FolioDTO implements Serializable{
	
	private Integer idFolio;
	private Integer folio;
	private Integer idSerie;
	private String indEstatus;
	
	public Integer getFolio() {
		return folio;
	}
	public void setFolio(Integer folio) {
		this.folio = folio;
	}
	public Integer getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}
	public Integer getIdFolio() {
		return idFolio;
	}
	public void setIdFolio(Integer idFolio) {
		this.idFolio = idFolio;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
}
