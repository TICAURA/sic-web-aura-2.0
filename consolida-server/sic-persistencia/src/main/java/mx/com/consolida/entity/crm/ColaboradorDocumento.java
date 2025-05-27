package mx.com.consolida.entity.crm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "colaborador_documento")
public class ColaboradorDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_colaborador_documento")
    private Long idColaboradorDocumento;
    @Column(name = "id_colaborador")
    private Long idColaborador;
    @Column(name = "id_definicion_documento")
    private Long idDefinicionDocumento;
    
    @Column(name = "id_CMS")
    private Long idCms;
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
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

    public ColaboradorDocumento() {
    }

    public ColaboradorDocumento(Long idColaboradorDocumento) {
        this.idColaboradorDocumento = idColaboradorDocumento;
    }

    public ColaboradorDocumento(Long idColaboradorDocumento, Date fechaAlta, Long indEstatus) {
        this.idColaboradorDocumento = idColaboradorDocumento;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdColaboradorDocumento() {
		return idColaboradorDocumento;
	}

	public void setIdColaboradorDocumento(Long idColaboradorDocumento) {
		this.idColaboradorDocumento = idColaboradorDocumento;
	}

	public Long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public Long getIdCms() {
		return idCms;
	}

	public void setIdCms(Long idCms) {
		this.idCms = idCms;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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

	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}

	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idColaboradorDocumento != null ? idColaboradorDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaboradorDocumento)) {
            return false;
        }
        ColaboradorDocumento other = (ColaboradorDocumento) object;
        if ((this.idColaboradorDocumento == null && other.idColaboradorDocumento != null) || (this.idColaboradorDocumento != null && !this.idColaboradorDocumento.equals(other.idColaboradorDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.ColaboradorDocumento[ idColaboradorDocumento=" + idColaboradorDocumento + " ]";
    }
}