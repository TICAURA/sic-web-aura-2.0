package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="cat_grupo")
@NamedQuery(name="CatGrupo.findAll", query="SELECT c FROM CatGrupo c")
public class CatGrupo  implements Serializable{
			
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id_cat_grupo")
		private Long idCatGrupo;
		
		
		@Column(name = "cve_grupo")
	    private String cveGrupo;
		
		
		@Column(name = "descripcion_razon_social")
	    private String descripcionRazonSocial;
		
		
		@Column(name = "rfc")
	    private String rfc;
		
		
	    @Column(name = "fecha_alta")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaAlta;
		
	    @Column(name = "fecha_modificacion")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaModificacion;
	    
	    
	    @Column(name = "usuario_alta")
	    private Long usuarioAlta;
	    
	    @Column(name = "usuario_modificacion")
	    private Long usuarioModificacion;
	    
	    @Column(name = "ind_estatus")
	    private Long indEstatus;
	    
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
