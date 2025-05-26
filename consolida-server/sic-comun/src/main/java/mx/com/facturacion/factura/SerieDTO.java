package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SerieDTO implements Serializable {
	private Integer idSerie;
	private String serie;
	private Integer idEmpresa;
	private List<FolioDTO> listaFolios;
	private EmpresaDTO empresaDTO;

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	public List<FolioDTO> getListaFolios() {
		return listaFolios;
	}

	public void setListaFolios(List<FolioDTO> listaFolios) {
		this.listaFolios = listaFolios;
	}

	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}

	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}

}
