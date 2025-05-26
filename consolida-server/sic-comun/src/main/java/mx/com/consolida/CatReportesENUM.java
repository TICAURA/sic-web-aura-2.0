package mx.com.consolida;

public enum CatReportesENUM {
	
	REPORTE_VARIACIONES(9953L,"REPOR_VARI", " REPORTE VARIACIONES"),
	REPORTE_OPERACIONES(9952L,"REPOR_OPER", "REPORTE OPERACIONES"),
	REPORTE_ESTIMADOS(9951L,"REPOR_EST", "REPORTE ESTIMADOS"),
	COLABORADORES_FALTANTES_EN_CRM(9956L,"COLAB_FALT_CRM", "COLABORADORES FALTANTES EN CRM"),
	FACTURACION_MES(9957L,"REPOR_FACMES","FACTURACIÓN MENSUAL");

	
	private Long id;
	private String clave;
	private String descripcion;
	
	private CatReportesENUM(Long id,String clave, String descripcion) {
		this.id= id;
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	

}
