package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TimbradoEstatusDTO implements Serializable{
	
	private String estado;
    private String strError;
    private String strAdvertencia;
    
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getStrError() {
		return strError;
	}
	public void setStrError(String strError) {
		this.strError = strError;
	}
	public String getStrAdvertencia() {
		return strAdvertencia;
	}
	public void setStrAdvertencia(String strAdvertencia) {
		this.strAdvertencia = strAdvertencia;
	}
	
	@Override
	public String toString() {
		return "TimbradoEstatusDTO [estado=" + estado + ", strError=" + strError + ", strAdvertencia=" + strAdvertencia
				+ "]";
	}
    
    
}
