package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cat_valor_default")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatValorDefault.findAll", query = "SELECT c FROM CatValorDefault c")
})
public class CatValorDefault implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cat_valor_default")
    private Long idCatValorDefault;
    
    @JoinColumn(name = "id_tipo_default", referencedColumnName = "id_cat_general")
    @ManyToOne
    private CatGeneral idTipoDefault;
    
    @Column(name = "valor")
    private BigDecimal valor;
    
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificaicon;
    
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioAlta;
    
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;
    
    @Column(name = "ind_estatus")
    private Long indEstatus;
    
    @Column(name = "predeterminado")
    private Long predeterminado;
    

    public CatValorDefault(Long idCatValorDefault) {
		super();
		this.idCatValorDefault = idCatValorDefault;
	}
     


	public CatValorDefault(CatValorDefaultDto dto) {
		super();
		this.idCatValorDefault = dto.getIdCatValorDefault();
		this.idTipoDefault = dto.getIdTipoDefault()!=null ? new CatGeneral(dto.getIdTipoDefault().getIdCatGeneral()) : null;
		this.valor = dto.getValor();
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificaicon = dto.getFechaModificaicon();
		this.usuarioAlta = dto.getUsuarioAlta()!=null ? new Usuario(dto.getUsuarioAlta().getIdUsuario()) : null;
		this.indEstatus = dto.getIndEstatus();
		this.predeterminado = dto.getPredeterminado();
	}



	public Long getIdCatValorDefault() {
		return idCatValorDefault;
	}


	public void setIdCatValorDefault(Long idCatValorDefault) {
		this.idCatValorDefault = idCatValorDefault;
	}


	public CatGeneral getIdTipoDefault() {
		return idTipoDefault;
	}


	public void setIdTipoDefault(CatGeneral idTipoDefault) {
		this.idTipoDefault = idTipoDefault;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaModificaicon() {
		return fechaModificaicon;
	}


	public void setFechaModificaicon(Date fechaModificaicon) {
		this.fechaModificaicon = fechaModificaicon;
	}


	public Usuario getUsuarioAlta() {
		return usuarioAlta;
	}


	public void setUsuarioAlta(Usuario usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}


	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}


	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}


	public Long getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}


	public Long getPredeterminado() {
		return predeterminado;
	}


	public void setPredeterminado(Long predeterminado) {
		this.predeterminado = predeterminado;
	}


	@Override
    public String toString() {
        return "mx.com.consolida.entity.CatValorDefault[ idCatValorDefault=" + idCatValorDefault + " ]";
    }
    
}
