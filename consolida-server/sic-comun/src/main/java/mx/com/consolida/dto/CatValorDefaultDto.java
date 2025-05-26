package mx.com.consolida.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */

public class CatValorDefaultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCatValorDefault;

    private CatGeneralDto idTipoDefault;
    
    private BigDecimal valor;

    private Date fechaAlta;

    private Date fechaModificaicon;

    private UsuarioDTO usuarioAlta;

    private UsuarioDTO usuarioModificacion;

    private Long indEstatus;
    
    private Long predeterminado;
    
    private Long editar;
    

    public CatValorDefaultDto(Long idCatValorDefault) {
		super();
		this.idCatValorDefault = idCatValorDefault;
	}
    
    public CatValorDefaultDto(Long idCatValorDefault, BigDecimal valor) {
		super();
		this.idCatValorDefault = idCatValorDefault;
		this.valor = valor;
	}


	public CatValorDefaultDto() {
		super();
	}


	public Long getIdCatValorDefault() {
		return idCatValorDefault;
	}


	public void setIdCatValorDefault(Long idCatValorDefault) {
		this.idCatValorDefault = idCatValorDefault;
	}


	public CatGeneralDto getIdTipoDefault() {
		return idTipoDefault;
	}


	public void setIdTipoDefault(CatGeneralDto idTipoDefault) {
		this.idTipoDefault = idTipoDefault;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
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


	public Long getPredeterminado() {
		return predeterminado;
	}

	public void setPredeterminado(Long predeterminado) {
		this.predeterminado = predeterminado;
	}

	public Long getEditar() {
		return editar;
	}

	public void setEditar(Long editar) {
		this.editar = editar;
	}

	@Override
    public String toString() {
        return "mx.com.consolida.entity.CatValorDefault[ idCatValorDefault=" + idCatValorDefault + " ]";
    }
    
}
