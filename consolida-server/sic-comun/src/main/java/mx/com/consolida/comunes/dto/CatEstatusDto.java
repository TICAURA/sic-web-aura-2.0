package mx.com.consolida.comunes.dto; 

import java.io.Serializable;

public class CatEstatusDto implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
	    private Long idEstatus;
	    private String cveEstatus;
	    private String descripcionEstatus;
	    
		public CatEstatusDto() {
			super();
		}
		public CatEstatusDto(Long idEstatus) {
			super();
			this.idEstatus = idEstatus;
		}
		public Long getIdEstatus() {
			return idEstatus;
		}
		public void setIdEstatus(Long idEstatus) {
			this.idEstatus = idEstatus;
		}
		public String getCveEstatus() {
			return cveEstatus;
		}
		public void setCveEstatus(String cveEstatus) {
			this.cveEstatus = cveEstatus;
		}
		public String getDescripcionEstatus() {
			return descripcionEstatus;
		}
		public void setDescripcionEstatus(String descripcionEstatus) {
			this.descripcionEstatus = descripcionEstatus;
		}
	    
	    
}