package mx.com.consolida.crm.dto;

import java.util.Date;

public class ClientePrestadoraServicioDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idClientePrestadoraServicio;
	private ClienteDto clienteDto;
	private PrestadoraServicioDto prestadoraServicioFondo;
	private PrestadoraServicioDto prestadoraServicio;
	private Long idUsuarioAlta;
	private Long idUsuarioModificacion;
	private Date fechaAlta;
	private Date fechaModificacion;
	private Long indEstatus;

	public Long getIdClientePrestadoraServicio() {
		return idClientePrestadoraServicio;
	}

	public void setIdClientePrestadoraServicio(Long idClientePrestadoraServicio) {
		this.idClientePrestadoraServicio = idClientePrestadoraServicio;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public PrestadoraServicioDto getPrestadoraServicioFondo() {
		return prestadoraServicioFondo;
	}

	public void setPrestadoraServicioFondo(PrestadoraServicioDto prestadoraServicioFondo) {
		this.prestadoraServicioFondo = prestadoraServicioFondo;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
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

}
