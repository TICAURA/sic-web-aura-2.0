
package mx.com.consolida.entity;

import java.io.Serializable;
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

import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "comisionista_cuenta_bancaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComisionistaCuentaBancaria.findAll", query = "SELECT c FROM ComisionistaCuentaBancaria c")
})
public class ComisionistaCuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comisionista_cuenta_bancaria")
    private Long idComisionistaCuentaBancaria;
    
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    
    @Column(name = "clabe_interbancaria")
    private String clabeInterbancaria;
    
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @Column(name = "ind_estatus")
    private Long indEstatus;
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral tipoCuenta;
    @JoinColumn(name = "id_comisionista", referencedColumnName = "id_comisionista")
    @ManyToOne(optional = false)
    private Comisionista comisionista;
    @JoinColumn(name = "id_banco", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral banco;
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;

    public ComisionistaCuentaBancaria() {
    }

	public Long getIdComisionistaCuentaBancaria() {
		return idComisionistaCuentaBancaria;
	}

	public void setIdComisionistaCuentaBancaria(Long idComisionistaCuentaBancaria) {
		this.idComisionistaCuentaBancaria = idComisionistaCuentaBancaria;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}

	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public CatGeneral getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(CatGeneral tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Comisionista getComisionista() {
		return comisionista;
	}

	public void setComisionista(Comisionista comisionista) {
		this.comisionista = comisionista;
	}

	public CatGeneral getBanco() {
		return banco;
	}

	public void setBanco(CatGeneral banco) {
		this.banco = banco;
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
    
    
    
}

 

