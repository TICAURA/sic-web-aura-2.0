package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cat_estatus")
@NamedQuery(name="CatEstatus.findAll", query="SELECT c FROM CatEstatus c")
public class CatEstatus  implements Serializable{
			
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id_estatus")
		private Long idEstatus;
		
		@Basic(optional = false)
		@Column(name = "cve_estatus")
	    private String cveEstatus;
		
		@Basic(optional = false)
		@Column(name = "descripcion_estatus")
	    private String descripcionEstatus;
		
		@Basic(optional = false)
	    @Column(name = "fecha_alta")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaAlta;
		
	    @Column(name = "fecha_modificacion")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaModificacion;
	    
	    @Basic(optional = false)
	    @Column(name = "usuario_alta")
	    private Long usuarioAlta;
	    
	    @Column(name = "usuario_modificacion")
	    private Long usuarioModificacion;
	    
	    @Column(name = "ind_estatus")
	    private Long indEstatus;

		public CatEstatus() {
			
		}
				
		public CatEstatus(Long idEstatus) {
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

		public Date getFechaAlta() {
			return fechaAlta;
		}

		public void setFechaAlta(Date fechaAlta) {
			this.fechaAlta = fechaAlta;
		}

		public Date getFechaModificacion() {
			return fechaModificacion;
		}

		public void setFechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
		}

		public Long getUsuarioAlta() {
			return usuarioAlta;
		}

		public void setUsuarioAlta(Long usuarioAlta) {
			this.usuarioAlta = usuarioAlta;
		}

		public Long getUsuarioModificacion() {
			return usuarioModificacion;
		}

		public void setUsuarioModificacion(Long usuarioModificacion) {
			this.usuarioModificacion = usuarioModificacion;
		}

		public Long getIndEstatus() {
			return indEstatus;
		}

		public void setIndEstatus(Long indEstatus) {
			this.indEstatus = indEstatus;
		}
	    
	    
	    

}
