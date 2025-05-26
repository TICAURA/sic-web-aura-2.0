package mx.com.consolida.entity.crm;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "colaborador_nomina")
public class ColaboradorNomina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador_nomina")
    private Long idColaboradorNomina;
    @Column(name = "monto_ppp")
    private Double montoPpp;
    @Column(name = "correo_colaborador")
    private String correoColaborador;
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    @ManyToOne(optional = false)
    private Colaborador idColaborador;
    @JoinColumn(name = "id_nomina", referencedColumnName = "id_nomina_cliente")
    @ManyToOne(optional = false)
    private NominaCliente idNomina;
    @Column(name = "ind_estatus")
    private Long indEstatus;

    public ColaboradorNomina() {
    }

    public ColaboradorNomina(Long idColaboradorNomina) {
        this.idColaboradorNomina = idColaboradorNomina;
    }

    public ColaboradorNomina(Long idColaboradorNomina, String correoColaborador, String numeroCuenta) {
        this.idColaboradorNomina = idColaboradorNomina;
        this.correoColaborador = correoColaborador;
        this.numeroCuenta = numeroCuenta;
    }

    public Long getIdColaboradorNomina() {
        return idColaboradorNomina;
    }

    public void setIdColaboradorNomina(Long idColaboradorNomina) {
        this.idColaboradorNomina = idColaboradorNomina;
    }

    public Double getMontoPpp() {
        return montoPpp;
    }

    public void setMontoPpp(Double montoPpp) {
        this.montoPpp = montoPpp;
    }

    public String getCorreoColaborador() {
        return correoColaborador;
    }

    public void setCorreoColaborador(String correoColaborador) {
        this.correoColaborador = correoColaborador;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Colaborador getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Colaborador idColaborador) {
        this.idColaborador = idColaborador;
    }

    public NominaCliente getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(NominaCliente idNomina) {
        this.idNomina = idNomina;
    }


    public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idColaboradorNomina != null ? idColaboradorNomina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaboradorNomina)) {
            return false;
        }
        ColaboradorNomina other = (ColaboradorNomina) object;
        if ((this.idColaboradorNomina == null && other.idColaboradorNomina != null) || (this.idColaboradorNomina != null && !this.idColaboradorNomina.equals(other.idColaboradorNomina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.ColaboradorNomina[ idColaboradorNomina=" + idColaboradorNomina + " ]";
    }

}
