package mx.com.facturacion.factura;

public enum EstatusFacturaEnum {
	PENDIENTE_PAGO(1, "CVE_PENDIENTE_PAGO", "PENDIENTE_PAGO"),
	PAGADO(2, "CVE_PAGADO", "PAGADO"),
	CANCELADO( 3, "CVE_CANCELADO", "CANCELADO"),
	POR_PAGAR(4, "CVE_POR_PAGAR", "POR PAGAR"),
	REGISTRA_FACTURA(5, "CVE_REG_FACTURA", "REGISTRA FACTURA");
	
	private int idEstatusFactura;
	private String clave;
	private String descripcion;
	
	private EstatusFacturaEnum(int idEstatusFactura) {
		this.idEstatusFactura = idEstatusFactura;
	}
	
	private EstatusFacturaEnum(int idEstatusFactura, String clave, String descripcion) {
		this.idEstatusFactura = idEstatusFactura;
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public int getIdEstatusFactura() {
		return idEstatusFactura;
	}

	public void setIdEstatusFactura(short idEstatusFactura) {
		this.idEstatusFactura = idEstatusFactura;
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

	public static EstatusFacturaEnum parse(int value) {
		EstatusFacturaEnum c = null;
		for (EstatusFacturaEnum e : values()) {
			if (e.getIdEstatusFactura() == value) {
				c = e;
			}
		}

		return c;
	}
	
	public static String parseDescripcion(int value) {
		EstatusFacturaEnum c = null;
		for (EstatusFacturaEnum e : values()) {
			if (e.getIdEstatusFactura() == value) {
				c = e;
			}
		}

		return c.descripcion;
	}

}
