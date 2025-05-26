package mx.com.consolida.generico;

public enum EstatusDiscoEnum {
	
	INCONSISTENTE("Inconsistent/UpToDate"),
	CONSISTENTE("UpToDate/UpToDate"),
	SIN_DEFINIR("NoDefinido");
	
	private String clave;
	
	private EstatusDiscoEnum(String clave){
		this.clave=clave;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}