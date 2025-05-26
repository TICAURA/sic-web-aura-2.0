package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.usuario.UsuarioDTO;


public class NominaFacturaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNominaFactura;
	
	private NominaClienteDto nominaCliente;
	
	private ClienteDto cliente;
	
	private PrestadoraServicioDto prestadoraServicio;
	
	private CatSerieDto catSerie;
	
	private CatFolioDto catFolio;
	
	private CatGeneralDto catMetodoPago;
	
	private CatGeneralDto catFormaPago;

	private CatGeneralDto catTipoCfdi;

	private CatGeneralDto catRegimenFiscal;

	private CatGeneralDto catMoneda;

	private BigDecimal total;

	private String uuid;

	private String medtodoPago;

	private String formaPago;

	private String tipoCfdi;

	private String certificadoEmisor;
	
	private Date fechaHoraEmision;

	private Date fechaHoraCertificacion;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Long indEstatus;

	public Long getIdNominaFactura() {
		return idNominaFactura;
	}

	public void setIdNominaFactura(Long idNominaFactura) {
		this.idNominaFactura = idNominaFactura;
	}

	public NominaClienteDto getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(NominaClienteDto nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public CatSerieDto getCatSerie() {
		return catSerie;
	}

	public void setCatSerie(CatSerieDto catSerie) {
		this.catSerie = catSerie;
	}

	public CatFolioDto getCatFolio() {
		return catFolio;
	}

	public void setCatFolio(CatFolioDto catFolio) {
		this.catFolio = catFolio;
	}

	public CatGeneralDto getCatMetodoPago() {
		return catMetodoPago;
	}

	public void setCatMetodoPago(CatGeneralDto catMetodoPago) {
		this.catMetodoPago = catMetodoPago;
	}

	public CatGeneralDto getCatFormaPago() {
		return catFormaPago;
	}

	public void setCatFormaPago(CatGeneralDto catFormaPago) {
		this.catFormaPago = catFormaPago;
	}

	public CatGeneralDto getCatTipoCfdi() {
		return catTipoCfdi;
	}

	public void setCatTipoCfdi(CatGeneralDto catTipoCfdi) {
		this.catTipoCfdi = catTipoCfdi;
	}

	public CatGeneralDto getCatRegimenFiscal() {
		return catRegimenFiscal;
	}

	public void setCatRegimenFiscal(CatGeneralDto catRegimenFiscal) {
		this.catRegimenFiscal = catRegimenFiscal;
	}

	public CatGeneralDto getCatMoneda() {
		return catMoneda;
	}

	public void setCatMoneda(CatGeneralDto catMoneda) {
		this.catMoneda = catMoneda;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMedtodoPago() {
		return medtodoPago;
	}

	public void setMedtodoPago(String medtodoPago) {
		this.medtodoPago = medtodoPago;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoCfdi() {
		return tipoCfdi;
	}

	public void setTipoCfdi(String tipoCfdi) {
		this.tipoCfdi = tipoCfdi;
	}

	public String getCertificadoEmisor() {
		return certificadoEmisor;
	}

	public void setCertificadoEmisor(String certificadoEmisor) {
		this.certificadoEmisor = certificadoEmisor;
	}

	public Date getFechaHoraEmision() {
		return fechaHoraEmision;
	}

	public void setFechaHoraEmision(Date fechaHoraEmision) {
		this.fechaHoraEmision = fechaHoraEmision;
	}

	public Date getFechaHoraCertificacion() {
		return fechaHoraCertificacion;
	}

	public void setFechaHoraCertificacion(Date fechaHoraCertificacion) {
		this.fechaHoraCertificacion = fechaHoraCertificacion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	
	
}
