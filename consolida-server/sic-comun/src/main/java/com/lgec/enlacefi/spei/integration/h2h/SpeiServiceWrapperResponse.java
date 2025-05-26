package com.lgec.enlacefi.spei.integration.h2h;

import java.io.Serializable;

public class SpeiServiceWrapperResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SpeiServiceResponse resultado;

	public SpeiServiceResponse getResultado() {
		return resultado;
	}

	public void setResultado(SpeiServiceResponse resultado) {
		this.resultado = resultado;
	}
	
	
}
