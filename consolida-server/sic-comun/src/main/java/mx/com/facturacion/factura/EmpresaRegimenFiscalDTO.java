package mx.com.facturacion.factura;

import java.io.Serializable;

import mx.com.consolida.generico.CatGenericoDTO;

@SuppressWarnings("serial")
public class EmpresaRegimenFiscalDTO implements Serializable {
	
	private Integer IdEmpresaRegimen;
	private CatGenericoDTO regimen;
	private EmpresaDTO empresa;
	private String giro;
	
	public EmpresaRegimenFiscalDTO(){
		
	}
	
	public EmpresaRegimenFiscalDTO(String demo){
		this.IdEmpresaRegimen = 1;
		this.regimen = new CatGenericoDTO(1, "Clave regimen empresa regimen fiscal dto", "Clave regimen empresa regimen fiscal dto");
		this.giro = "Giro de empresa regimen fiscal dto";
	}
	
	public Integer getIdEmpresaRegimen() {
		return IdEmpresaRegimen;
	}
	public void setIdEmpresaRegimen(Integer idEmpresaRegimen) {
		IdEmpresaRegimen = idEmpresaRegimen;
	}
	public CatGenericoDTO getRegimen() {
		return regimen;
	}
	public void setRegimen(CatGenericoDTO regimen) {
		this.regimen = regimen;
	}
	public EmpresaDTO getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
}
