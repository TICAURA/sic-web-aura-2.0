package mx.com.consolida.dto;

import java.io.Serializable;

import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */
public class OficinaCanalVentaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idOficinaCanalVenta;
    private String fechaAlta;
    private String fechaModificacion;
    private Long indEstatus;
    private UsuarioDTO usuarioModificacion;
    private CanalVentaDto idCanalVenta;
    private OficinaDto idOficina;
    private UsuarioDTO usuarioAlta;

    public OficinaCanalVentaDto() {
    }

    public OficinaCanalVentaDto(Long idOficinaCanalVenta) {
        this.idOficinaCanalVenta = idOficinaCanalVenta;
    }

    public OficinaCanalVentaDto(Long idOficinaCanalVenta, String fechaAlta, Long indEstatus) {
        this.idOficinaCanalVenta = idOficinaCanalVenta;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

	public Long getIdOficinaCanalVenta() {
		return idOficinaCanalVenta;
	}

	public void setIdOficinaCanalVenta(Long idOficinaCanalVenta) {
		this.idOficinaCanalVenta = idOficinaCanalVenta;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
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

	public CanalVentaDto getIdCanalVenta() {
		return idCanalVenta;
	}

	public void setIdCanalVenta(CanalVentaDto idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}

	public OficinaDto getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(OficinaDto idOficina) {
		this.idOficina = idOficina;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	@Override
	public String toString() {
		return "OficinaCanalVenta [idOficinaCanalVenta=" + idOficinaCanalVenta + "]";
	}

}
