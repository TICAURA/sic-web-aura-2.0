package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.generico.CatGenericoDTO;

@SuppressWarnings("serial")
public class LugarExpedicionResponse implements Serializable{
	
	private List<CatGenericoDTO> lugares;

	public List<CatGenericoDTO> getLugares() {
		return lugares;
	}

	public void setLugares(List<CatGenericoDTO> lugares) {
		this.lugares = lugares;
	}
}
