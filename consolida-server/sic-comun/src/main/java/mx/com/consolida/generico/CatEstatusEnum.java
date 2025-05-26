package mx.com.consolida.generico;

public enum CatEstatusEnum {
	
	INACTIVO( 0L,"I","Inactivo"),
	ACTIVO( 1L,"A","Activo"),                     
	EN_PROCESO( 2L,"EP","En Proceso"),                                
	COTIZACION_SOLICITADA( 3L,"CO_SOL","Cotizacion solicitada"),               
	ENVIADA_AUTORIZADOR( 4L,"ENVI_AUT","Enviada autorizador"),                                
	COTIZACION_RECHAZADA( 5L,"COTI_RECHAZO","Cotizacion rechazada"),                      
	COTIZACION_AUTORIZADA( 6L,"COTI_AUT","Cotización autorizada"),   
	CLIENTE_ELIMINADO( 7L,"CLI_BAJA","Cliente eliminado"),  
	PROSPECTO_AUTORIZADO( 8L,"PROS_AUTORIZADO","Prospecto autorizado"),      
	PROSPECTO_DECLINADO( 9L,"PROS_DECLINADO","Prospecto declinado"),
	PROSPECTO_DECLINADO_MSA_CTRL( 10L,"PROSP_DEC_MSA_CTRL","Prospecto declinado en mesa de control"),
	COTIZACION_EN_PROCESO(11L, "COTI_EN_PROCESO", "Cotización en proceso"),
	PROSPECTO_AUTORIZADO_MESA_CONTROL(12L, "PROSP_DEC_MSA_CTRL", "Prospecto autorizado por mesa de control"),
	PROSPECTO_RECHAZADO_MESA_CONTROL(13L, "PROSP_RECH_MESA_CONTR", "Prospecto rechazado por mesa de control"),
	PROSPECTO_CREADO_POR_MESA_CONTROL(14L, "PROSP_CREA_MES_CONTR", "Prospecto creado por mesa de control"),
	PROSPECTO_ACTUALIZADO_POR_MESA_CONTROL(15L, "PROSP_ACT_MES_CONTR", "Prospecto actualizado por mesa de control"),
	PROSPECTO_RECHAZADO(16L, "PROSP_RECHAZADO", "Prospecto rechazado"),
	CLIENTE_ACTIVO(17L, "CLIEN_ACTIVO", "ACTIVO"),
	CLIENTE_DESACTIVADO(18L, "CLIEN_DESA", "DESACTIVADO"),
	PRESTADORA_SERVICIO_CREADA(19L, "PREST_SERV_CRE", "Prestadoda servicio creada"),
	PRESTADORA_SERVICIO_ELIMINADA(20L, "PREST_SERV_ELIM", "Prestadoda servicio eliminada")
	;
	
	private Long idEstatus;
	private String cveEstatus;
	private String descripcionEstatus;
	
	private CatEstatusEnum(Long idEstatus, String cveEstatus, String descripcionEstatus) {
		this.idEstatus = idEstatus;
		this.cveEstatus = cveEstatus;
		this.descripcionEstatus = descripcionEstatus;
	}

	public final Long getIdEstatus() {
		return idEstatus;
	}

	public final String getCveEstatus() {
		return cveEstatus;
	}

	public final String getDescripcionEstatus() {
		return descripcionEstatus;
	}
	
}
