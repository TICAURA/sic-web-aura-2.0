package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.generico.CatGenericoDTO;

@SuppressWarnings("serial")
public class SerieResponse  implements Serializable {
	
	private List<CatGenericoDTO> series;

	public List<CatGenericoDTO> getSeries() {
		return series;
	}

	public void setSeries(List<CatGenericoDTO> series) {
		this.series = series;
	}
}