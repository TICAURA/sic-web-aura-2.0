
package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;


/**
 *
 * @author Abel
 */
public class CanalVentaCuentaBancariaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idCanalVentaCuentaBancaria;
    
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

    public CanalVentaCuentaBancariaDto() {
    }

    public CanalVentaCuentaBancariaDto(Long idCanalVentaCuentaBancaria) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
    }

    public CanalVentaCuentaBancariaDto(Long idCanalVentaCuentaBancaria, String numeroCuenta, String clabeInterbancaria, Date fechaAlta, Long indEstatus) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
        this.numeroCuenta = numeroCuenta;
        this.clabeInterbancaria = clabeInterbancaria;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdCanalVentaCuentaBancaria() {
        return idCanalVentaCuentaBancaria;
    }

    public void setIdCanalVentaCuentaBancaria(Long idCanalVentaCuentaBancaria) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
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

	@Override
	public String toString() {
		return "CanalVentaCuentaBancaria [idCanalVentaCuentaBancaria=" + idCanalVentaCuentaBancaria + "]";
	}

}
