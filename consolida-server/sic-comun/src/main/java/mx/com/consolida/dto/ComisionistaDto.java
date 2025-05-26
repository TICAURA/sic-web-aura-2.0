package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.usuario.PersonaDto;


/**
 *
 * @author Aurora
 */

public class ComisionistaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idComisionista;
    private CatGeneralDto tipoPersona;
    private CatGeneralDto tipoCanalVenta;
    private CatGeneralDto concepto_fdi;
    private CatGeneralDto tipo_pago;
    private CatGeneralDto subtipo_pago;
    private String razonSocial;
    private String nombreCompleto;
    private DomicilioDto domicilio;
    private PersonaDto persona;
    private CuentaBancariaDto cuentaBancaria;

    private Date fechaAlta;
    private Date fechaModificaicon;
    private String indEstatus;
    
 
    public ComisionistaDto() {
    }


	public Long getIdComisionista() {
		return idComisionista;
	}


	public void setIdComisionista(Long idComisionista) {
		this.idComisionista = idComisionista;
	}


	public CatGeneralDto getTipoPersona() {
		return tipoPersona;
	}


	public void setTipoPersona(CatGeneralDto tipoPersona) {
		this.tipoPersona = tipoPersona;
	}


	public CatGeneralDto getTipoCanalVenta() {
		return tipoCanalVenta;
	}


	public void setTipoCanalVenta(CatGeneralDto tipoCanalVenta) {
		this.tipoCanalVenta = tipoCanalVenta;
	}


	public CatGeneralDto getConcepto_fdi() {
		return concepto_fdi;
	}


	public void setConcepto_fdi(CatGeneralDto concepto_fdi) {
		this.concepto_fdi = concepto_fdi;
	}


	public CatGeneralDto getTipo_pago() {
		return tipo_pago;
	}


	public void setTipo_pago(CatGeneralDto tipo_pago) {
		this.tipo_pago = tipo_pago;
	}


	public CatGeneralDto getSubtipo_pago() {
		return subtipo_pago;
	}


	public void setSubtipo_pago(CatGeneralDto subtipo_pago) {
		this.subtipo_pago = subtipo_pago;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public DomicilioDto getDomicilio() {
		return domicilio;
	}


	public void setDomicilio(DomicilioDto domicilio) {
		this.domicilio = domicilio;
	}


	public PersonaDto getPersona() {
		return persona;
	}


	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}


	public CuentaBancariaDto getCuentaBancaria() {
		return cuentaBancaria;
	}


	public void setCuentaBancaria(CuentaBancariaDto cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaModificaicon() {
		return fechaModificaicon;
	}


	public void setFechaModificaicon(Date fechaModificaicon) {
		this.fechaModificaicon = fechaModificaicon;
	}


	public String getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}


    
    

	
}
