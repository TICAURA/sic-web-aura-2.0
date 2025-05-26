package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */

public class OficinaCuentaBancariaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idOficinaCuentaBancaria;
    private String numeroCuenta;
    private String clabeInterbancaria;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Long indEstatus;
    private UsuarioDTO usuarioModificacion;
    private CatGeneralDto idBanco;
    private OficinaDto idOficina;
    private CatGeneralDto idTipoCuenta;
    private UsuarioDTO usuarioAlta;

    public OficinaCuentaBancariaDto() {
    }

    public OficinaCuentaBancariaDto(Long idOficinaCuentaBancaria) {
        this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
    }

    public OficinaCuentaBancariaDto(Long idOficinaCuentaBancaria, String numeroCuenta, String clabeInterbancaria, Date fechaAlta, Long indEstatus) {
        this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
        this.numeroCuenta = numeroCuenta;
        this.clabeInterbancaria = clabeInterbancaria;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

	public Long getIdOficinaCuentaBancaria() {
		return idOficinaCuentaBancaria;
	}

	public void setIdOficinaCuentaBancaria(Long idOficinaCuentaBancaria) {
		this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}

	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public CatGeneralDto getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(CatGeneralDto idBanco) {
		this.idBanco = idBanco;
	}

	public OficinaDto getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(OficinaDto idOficina) {
		this.idOficina = idOficina;
	}

	public CatGeneralDto getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(CatGeneralDto idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	@Override
	public String toString() {
		return "OficinaCuentaBancaria [idOficinaCuentaBancaria=" + idOficinaCuentaBancaria + "]";
	}

}
