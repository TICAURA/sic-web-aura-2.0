package mx.com.consolida.crm.dto;

import mx.com.consolida.catalogos.CatGeneralDto;

/**
 *
 * @author Abel
 */

public class BeneficioAdicionalColaboradorDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


    private Long idBeneficioAdicionalColaborador;
    private Long idBeneficioAdicional;
    private Long idColaborador;
    private Long indEstatus;
    private CatGeneralDto general;


    public Long getIdBeneficioAdicionalColaborador() {
		return idBeneficioAdicionalColaborador;
	}


	public void setIdBeneficioAdicionalColaborador(Long idBeneficioAdicionalColaborador) {
		this.idBeneficioAdicionalColaborador = idBeneficioAdicionalColaborador;
	}


	public Long getIdBeneficioAdicional() {
		return idBeneficioAdicional;
	}


	public void setIdBeneficioAdicional(Long idBeneficioAdicional) {
		this.idBeneficioAdicional = idBeneficioAdicional;
	}


	public Long getIdColaborador() {
		return idColaborador;
	}


	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}


	public Long getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}


	public CatGeneralDto getGeneral() {
		return general;
	}


	public void setGeneral(CatGeneralDto general) {
		this.general = general;
	}


	@Override
    public String toString() {
        return "mx.com.consolida.entity.BeneficioAdicionalColaborador[ idBeneficioAdicionalColaborador =" + idBeneficioAdicionalColaborador + " ]";
    }
    
}
