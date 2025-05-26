package mx.com.consolida.generico;

public enum IndEstatusEnum {
	
	ACTIVO("1"),INACTIVO("0");
	
	private String estatus;
	
	private IndEstatusEnum(String estatus){
		this.estatus = estatus;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
}
