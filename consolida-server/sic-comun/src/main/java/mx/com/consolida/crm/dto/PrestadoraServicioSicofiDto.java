package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;



public class PrestadoraServicioSicofiDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicio;
	private String nombreCentroCostos;
	private String nombreCorto;
	private String descCentroCostos;
	private String razonSocial;
	private String clabeOrdenate;
	private String rfc;
	private Boolean esFondo = false;
	private Date fechaAlta;
	private Long indEstatus;
	private String clave;
	private Double saldo;
	
	public PrestadoraServicioSicofiDto () {
	}

	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}

	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}

	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}

	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}
	public String getDescCentroCostos() {
		return descCentroCostos;
	}

	public void setDescCentroCostos(String descCentroCostos) {
		this.descCentroCostos = descCentroCostos;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	


	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getClabeOrdenate() {
		return clabeOrdenate;
	}

	public void setClabeOrdenate(String clabeOrdenate) {
		this.clabeOrdenate = clabeOrdenate;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Boolean getEsFondo() {
		return esFondo;
	}

	public void setEsFondo(Boolean esFondo) {
		this.esFondo = esFondo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	
	
	
	
	

}
