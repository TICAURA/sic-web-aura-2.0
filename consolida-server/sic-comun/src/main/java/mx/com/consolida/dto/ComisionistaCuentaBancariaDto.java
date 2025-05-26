
package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;


/**
 *
 * @author Aurora
 */
public class ComisionistaCuentaBancariaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idcomisionistaCuentaBancaria;
    
    private String numeroCuenta;
    
    private String clabeInterbancaria;
    
    private Date fechaAlta;
    
    private Date fechaModificacion;
    
    private Long indEstatus;
    
    private CatGeneralDto idTipoCuenta;
    private CanalVentaDto idCanalVenta;
    private CatGeneralDto idBanco;
    private UsuarioDTO usuarioAlta;
    private UsuarioDTO usuarioModificacion;

    public ComisionistaCuentaBancariaDto() {
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

    public CatGeneralDto getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(CatGeneralDto idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public CanalVentaDto getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(CanalVentaDto idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public CatGeneralDto getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(CatGeneralDto idBanco) {
        this.idBanco = idBanco;
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




	public Long getIdcomisionistaCuentaBancaria() {
		return idcomisionistaCuentaBancaria;
	}




	public void setIdcomisionistaCuentaBancaria(Long idcomisionistaCuentaBancaria) {
		this.idcomisionistaCuentaBancaria = idcomisionistaCuentaBancaria;
	}



}
