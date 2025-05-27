package mx.com.consolida.generico;

public enum IndEstatusSeriesEnum {
	
	ACTIVA(1L),POR_INICIAR(0L),CADUCADA(2L);
	
	private Long estatus;
	
	private IndEstatusSeriesEnum(Long estatus){
		this.estatus = estatus;
	}

	public Long getEstatus() {
		return estatus;
	}

	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}
	
}
