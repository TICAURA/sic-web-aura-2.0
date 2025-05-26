package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.usuario.UsuarioDTO;


public class ClienteServicioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idClienteServicio;

	private PrestadoraServicioProductoDto prestadoraServicioProducto;

	private List<PrestadoraServicioProductoDto> listaPrestadoraProductos;

	private ClienteDto cliente;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;
	
	public ClienteServicioDto() {
		
	}
	
	public ClienteServicioDto(Long idClienteServicio) {
		this.idClienteServicio = idClienteServicio;
	}

	public Long getIdClienteServicio() {
		return idClienteServicio;
	}

	public void setIdClienteServicio(Long idClienteServicio) {
		this.idClienteServicio = idClienteServicio;
	}

	public PrestadoraServicioProductoDto getPrestadoraServicioProducto() {
		return prestadoraServicioProducto;
	}

	public void setPrestadoraServicioProducto(PrestadoraServicioProductoDto prestadoraServicioProducto) {
		this.prestadoraServicioProducto = prestadoraServicioProducto;
	}

	public List<PrestadoraServicioProductoDto> getListaPrestadoraProductos() {
		return listaPrestadoraProductos;
	}

	public void setListaPrestadoraProductos(List<PrestadoraServicioProductoDto> listaPrestadoraProductos) {
		this.listaPrestadoraProductos = listaPrestadoraProductos;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
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

}
