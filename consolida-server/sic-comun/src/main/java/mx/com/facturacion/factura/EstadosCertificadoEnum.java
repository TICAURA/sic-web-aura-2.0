package mx.com.facturacion.factura;

public enum EstadosCertificadoEnum {
	SUCCESS(0,"Exitoso"),
	ERROR_PASSWORD(1, "Error en password"),
	ERROR_PRIVATE_KEY(2, "Error en llave privada"),
	ERROR_PUBLIC_KEY(3,"Error llava publica"),
	ERROR_PUBLIC_PRIVATE_KEY(4,"Error llave publica y privada");
	
	private int id;
	private String descripcion;

	private EstadosCertificadoEnum(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

}