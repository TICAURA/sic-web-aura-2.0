package mx.com.consolida.generico;

public enum TipoPersonaEnum {

	FISICA(21L), MORAL(22L);
	
	private Long id_tipoPersona;
	
	private TipoPersonaEnum() {
		
	}
	
	private TipoPersonaEnum(Long id_tipoPersona) {
		this.id_tipoPersona = id_tipoPersona;
	}

	public Long getId_tipoPersona() {
		return id_tipoPersona;
	}

	public void setId_tipoPersona(Long id_tipoPersona) {
		this.id_tipoPersona = id_tipoPersona;
	}
	
	
	
}
