package mx.com.consolida;

public enum RolUsuarioENUM {
	ADMINISTRADOR(1L,"Administrador","0"),
	ADMINISTRADO_CRM(2L,"Administrador_CRM","0"),
	VENTAS(3L,"ventas","0"),
	AUTORIZADOR(4L,"Autorizador","0"),
	ADMINISTRADOR_PPP(5L,"Administrador_PPP","0"),
//	NOMINISTA(6L,"Nominista","Nominista"),
	IMPLEMENTADOR(7L,"Implementador","0"),
	FINANZAS(8L,"Finanzas","0"),
	ADMINISTRADOR_CELULA(9L, "Administrador_celula","1"),
	GERENTE_CELULA(10L,"gerente_celula","1"),
	GERENTE_NOMINA(11L,"gerente_nomina","1"),
	NOMINISTA(12L,"NOMINISTA","1"),
	IMPLEMENTADOR_CELULA(13L,"implementador_celula","1"),
	AUXILIAR_TESORERIA(14L,"auxiliar_tesoreria","1"),
	COORDINADOR_TESORERIA(15L,"coordinador_tesoreria","1"),
	AUXILIAR_CONTABILIDAD(16L,"auxiliar_contabilidad","1"),
	COORDINADOR_CONTABILIDAD(17L,"cordinador_contabilidad","1"),
	PROMOTOR_VENTAS(18L,"promotor_ventas","0"),
	ANALISTA_COMERCIAL(19L,"analista_comercial","0"),
	TESORERIA(28L,"TESORERIA","1"),
	DIRECTOR_OPERACIONES(29L,"DIRECTOR_OPERACIONES","1");
	

	
	private Long id;
	private String clave;
	private String rolCelula;
	
	private RolUsuarioENUM(Long id,String clave, String rolCelula) {
		this.id= id;
		this.clave = clave;
		this.rolCelula = rolCelula;
	}

	public Long getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}
	
	
	public String getRolCelula() {
		return rolCelula;
	}
	
	
}
