package mx.com.consolida.catalogos;

public enum TipoProductoEnum {
	
	PPP( 304L,"PPP",	"PLAN PRIVADO DE PENSIONES (PPP)"), 
	IPRL( 9058L ,"IRLAB",	"INDEMNIZACIÓN POR RIESGO LABORAL"), 
	IMMS(306L,"IMSS","PROD_MIX	SUELDOS Y SALARIOS (IMSS)"),
	MAQUILA(9949L,"MAQUILA","MAQUILA"),
	MIXTO(9950L,"IMSS","MIXTO (PPP + IMSS)");
	
	private Long id;
	private String cve;
	private String desc;
	
	private TipoProductoEnum(Long id, String cve, String desc) {
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
