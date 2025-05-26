package mx.com.consolida.alquimia;

public class RastreoTransferenciaResponse {
	
	private String id_transaccion;
	private String estatus;
	
	private detalleProveedor detalle_proveedor;
	

	public RastreoTransferenciaResponse() {
		// TODO Auto-generated constructor stub
	}


	public String getId_transaccion() {
		return id_transaccion;
	}


	public void setId_transaccion(String id_transaccion) {
		this.id_transaccion = id_transaccion;
	}


	public String getEstatus() {
		return estatus;
	}


	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	public detalleProveedor getDetalle_proveedor() {
		return detalle_proveedor;
	}


	public void setDetalle_proveedor(detalleProveedor detalle_proveedor) {
		this.detalle_proveedor = detalle_proveedor;
	}
	
	

}
