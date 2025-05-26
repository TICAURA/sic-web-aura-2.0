package mx.com.consolida.dto; 

import java.io.Serializable;

import mx.com.consolida.comunes.dto.CatEstatusDto;

public class ClienteTempEstatusDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idClienteTempEstatus;
	private CatEstatusDto idEstatus;
	
	
	public Long getIdClienteTempEstatus() {
		return idClienteTempEstatus;
	}
	public void setIdClienteTempEstatus(Long idClienteTempEstatus) {
		this.idClienteTempEstatus = idClienteTempEstatus;
	}
	public CatEstatusDto getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(CatEstatusDto idEstatus) {
		this.idEstatus = idEstatus;
	}
		
	
}
