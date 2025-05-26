package mx.com.consolida.comunes.dto; 

import java.io.Serializable;

public class CatGrupoDto implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
	    private Long idCatGrupo;
	    private String cveGrupo;
	    private String descripcionRazonSocial;
	    private String rfc;
	    
	    
		public Long getIdCatGrupo() {
			return idCatGrupo;
		}
		public void setIdCatGrupo(Long idCatGrupo) {
			this.idCatGrupo = idCatGrupo;
		}
		public String getCveGrupo() {
			return cveGrupo;
		}
		public void setCveGrupo(String cveGrupo) {
			this.cveGrupo = cveGrupo;
		}
		public String getDescripcionRazonSocial() {
			return descripcionRazonSocial;
		}
		public void setDescripcionRazonSocial(String descripcionRazonSocial) {
			this.descripcionRazonSocial = descripcionRazonSocial;
		}
		public String getRfc() {
			return rfc;
		}
		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

	    
	    
}
