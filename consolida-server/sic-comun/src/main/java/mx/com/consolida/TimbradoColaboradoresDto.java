package mx.com.consolida;

import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;

public class TimbradoColaboradoresDto {
	private List<EmpleadoDTO> colaboradores;
	private NominaComplementoDto nominaComplemento;
	private NominaDto nominaPPP;
	private Long idPPPNominaFactura;
	private CatGeneralDto catPacTimbrado;
	
	
	public List<EmpleadoDTO> getColaboradores() {
		return colaboradores;
	}
	public void setColaboradores(List<EmpleadoDTO> colaboradores) {
		this.colaboradores = colaboradores;
	}
	
	public NominaComplementoDto getNominaComplemento() {
		return nominaComplemento;
	}
	public void setNominaComplemento(NominaComplementoDto nominaComplemento) {
		this.nominaComplemento = nominaComplemento;
	}
	public NominaDto getNominaPPP() {
		return nominaPPP;
	}
	public void setNominaPPP(NominaDto nominaPPP) {
		this.nominaPPP = nominaPPP;
	}
	public Long getIdPPPNominaFactura() {
		return idPPPNominaFactura;
	}
	public void setIdPPPNominaFactura(Long idPPPNominaFactura) {
		this.idPPPNominaFactura = idPPPNominaFactura;
	}
	public CatGeneralDto getCatPacTimbrado() {
		return catPacTimbrado;
	}
	public void setCatPacTimbrado(CatGeneralDto catPacTimbrado) {
		this.catPacTimbrado = catPacTimbrado;
	}
	
	
}
