package mx.com.consolida.crm.dto;

public class ClienteProductoDto {
	
	private Long idProducto;	
	private Long idCliente;
	private Long idClienteProducto;
	private String clave;
	private String descripcion;
	private Boolean estatus;

	public ClienteProductoDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdClienteProducto() {
		return idClienteProducto;
	}

	public void setIdClienteProducto(Long idClienteProducto) {
		this.idClienteProducto = idClienteProducto;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	

}
