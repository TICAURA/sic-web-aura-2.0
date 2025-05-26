package mx.com.consolida.dto; 

import java.io.Serializable;

public class CatTipoEventoDto implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
	    private Long idCatTipoEvento;
	    private String cveTipoEvento;
	    private String descripcionTipoEvento;
	    
	    
		public Long getIdCatTipoEvento() {
			return idCatTipoEvento;
		}
		public void setIdCatTipoEvento(Long idCatTipoEvento) {
			this.idCatTipoEvento = idCatTipoEvento;
		}
		public String getCveTipoEvento() {
			return cveTipoEvento;
		}
		public void setCveTipoEvento(String cveTipoEvento) {
			this.cveTipoEvento = cveTipoEvento;
		}
		public String getDescripcionTipoEvento() {
			return descripcionTipoEvento;
		}
		public void setDescripcionTipoEvento(String descripcionTipoEvento) {
			this.descripcionTipoEvento = descripcionTipoEvento;
		}
	    
	    
		
}
