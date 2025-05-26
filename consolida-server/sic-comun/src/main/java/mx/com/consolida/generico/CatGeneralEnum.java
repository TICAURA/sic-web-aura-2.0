package mx.com.consolida.generico;

public enum CatGeneralEnum {
	
	DEFAULT_SPEI( 2803L,"Val-Def-Porcentaje-Promotor","Default SPEI"),                     
	DEFAULT_TIMBRADO( 2802L,"Val-Def-Costos-Indirectos","Default Timbrado"),                                
	DEFAULT_ESTRATEGIA( 2801L,"Val-Def-Estrategia","Default Estrategia"),               
	DEFAULT_COSTOS_INDIRECTOS( 2800L,"Val-Def-Timbrado","Default Costos Indirectos"),                                
	DEFAULT_PORCENTAJE_PROMOTOR( 2799L,"Val-Def-SPEI","Default Porcentaje Promotor"),
	DEFAULT_PORCENTAJE_PROMOTOR_IMSS( 9948L,"Val-Def-Porcentaje-Promotor_Imss","Default Porcentaje Promotor Imss"),
	TIP_COT_GEN (45L,"COTIZACION GENERAL","TIP_COT_GEN")
	;
			
	private Long id;
	private String cve;
	private String desc;
	
	private CatGeneralEnum(Long id, String cve, String desc) {
		this.id = id;
		this.cve = cve;
		this.desc = desc;
	}

	public final Long getId() {
		return id;
	}

	public final String getCve() {
		return cve;
	}

	public final String getDesc() {
		return desc;
	}
	
}
