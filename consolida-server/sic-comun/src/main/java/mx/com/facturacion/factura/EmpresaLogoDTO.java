package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EmpresaLogoDTO implements Serializable {
	
	private Integer idEmpresaLogo;
	private String logoBase64;
	private Integer idEmpresa;
	
	public Integer getIdEmpresaLogo() {
		return idEmpresaLogo;
	}
	public void setIdEmpresaLogo(Integer idEmpresaLogo) {
		this.idEmpresaLogo = idEmpresaLogo;
	}
	public String getLogoBase64() {
		return logoBase64;
	}
	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}
