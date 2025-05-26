package mx.com.consolida.reporte;

import java.io.Serializable;
import java.util.List;

public class CotizadorReporteDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String totalColaboradores;
	private String totalNomina;
	
	private List<DemoColaboradorReporteDTO> colaboradores;

	public String getTotalColaboradores() {
		return totalColaboradores;
	}

	public void setTotalColaboradores(String totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}

	public String getTotalNomina() {
		return totalNomina;
	}

	public void setTotalNomina(String totalNomina) {
		this.totalNomina = totalNomina;
	}

	public List<DemoColaboradorReporteDTO> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<DemoColaboradorReporteDTO> colaboradores) {
		this.colaboradores = colaboradores;
	}

}