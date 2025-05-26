package mx.com.consolida.generico;

public enum CatEstatusColaboradorEnum {

	CARGADO(1L,"CARG","CARGADO","CARGADO"),
	DISPERSADO( 2L,"DISP","DISPERSADO","CARGADO"),
	TIMBRADO( 3L,"TIMBR","TIMBRADO","CARGADO"),
	RECHAZADO_DISPERSION(4L,"RECH_DISP","RECHAZADO DISPERSION","CARGADO"),
	BLOQUEADO( 5L,"BLOQ","BLOQUEADO","CARGADO"),
	DESBLOQUEADO( 6L,"DESBLOQ","DESBLOQUEADO","CARGADO"),
	ORDEN_PAGO_CREADA( 7L,"ORDEN_PAGO_CREADA","ORDEN DE PAGO CREADA","CARGADO"),
	ORDEN_PAGO_CREADA_STP( 8L,"ORDEN_PAGO_CREADA_STP","ORDEN DE PAGO CREADA STP","CARGADO"),
	ORDEN_PAGO_RECHAZADA_STP( 9L,"ORDEN_PAGO_RECHAZADA_STP","ORDEN DE PAGO RECHAZADA STP","CARGADO"),
	LIQUIDADA_STP(12L,"LIQ_STP","LIQUIDADA STP","LQ"),
	CANCELACION(13L,"CANC_STP","CANCELADA STP","CD"),
	DEVOLUCION(14L,"DEV_STP","DEVUELTA STP","D"),
	ORDEN_PAGO_ERRO_STP( 15L,"ERROR_STP","ERROR_STP","CARGADO"),
	ERROR_TIMBR(10L,"ERROR_TIMBR",	"ERROR PAC TIMBRAR","CARGADO"),
	EDITAR(11L,"EDITAR", "COLABORADOR EDITADO","CARGADO");

	private Long id;
	private String cve;
	private String desc;
	private String cve_stp;

	private CatEstatusColaboradorEnum(Long id, String cve, String desc, String cve_stp) {
		this.id = id;
		this.cve = cve;
		this.desc = desc;
		this.cve_stp =cve_stp;

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

	public final String getCve_stp() {
		return cve;
	}

	public final String getCve_stp_stp() {
		return cve_stp;
	}




}
