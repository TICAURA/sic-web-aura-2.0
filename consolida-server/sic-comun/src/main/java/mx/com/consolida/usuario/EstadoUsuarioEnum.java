package mx.com.consolida.usuario;

public enum EstadoUsuarioEnum {
	ACTIVO(1,"1","ACTIVO"),
	INACTIVO(2,"2","INACTIVO");
	
	
	private int id;
	private String cve;
	private String desc;
	
	private EstadoUsuarioEnum(int id, String cve, String desc) {
		this.id = id;
		this.cve = cve;
		this.desc = desc;
	}

	public final int getId() {
		return id;
	}

	public final String getCve() {
		return cve;
	}

	public final String getDesc() {
		return desc;
	}
}