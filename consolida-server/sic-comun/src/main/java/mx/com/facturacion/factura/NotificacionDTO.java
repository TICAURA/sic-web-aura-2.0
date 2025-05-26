package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

public class NotificacionDTO implements Serializable {
	
	private String cuerpoCorreo;
	private List<ReceptorDTO> receptores;
	
	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}
	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}
	public List<ReceptorDTO> getReceptores() {
		return receptores;
	}
	public void setReceptores(List<ReceptorDTO> receptores) {
		this.receptores = receptores;
	}
}
