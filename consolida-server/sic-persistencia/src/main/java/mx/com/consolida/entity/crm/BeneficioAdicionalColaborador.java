package mx.com.consolida.entity.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "beneficio_adicional_colaborador")
public class BeneficioAdicionalColaborador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficio_adicional_colaborador")
    private Long idBeneficioAdicionalColaborador;

    @Column(name = "id_beneficio_adicional")
    private Long idBeneficioAdicional;

    @Column(name = "id_colaborador")
    private Long idColaborador;

    @Column(name = "ind_estatus")
    private Long indEstatus;


    public Long getIdBeneficioAdicionalColaborador() {
		return idBeneficioAdicionalColaborador;
	}


	public void setIdBeneficioAdicionalColaborador(Long idBeneficioAdicionalColaborador) {
		this.idBeneficioAdicionalColaborador = idBeneficioAdicionalColaborador;
	}


	public Long getIdBeneficioAdicional() {
		return idBeneficioAdicional;
	}


	public void setIdBeneficioAdicional(Long idBeneficioAdicional) {
		this.idBeneficioAdicional = idBeneficioAdicional;
	}


	public Long getIdColaborador() {
		return idColaborador;
	}


	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}


	public Long getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}


	@Override
    public String toString() {
        return "mx.com.consolida.entity.BeneficioAdicionalColaborador[ idBeneficioAdicionalColaborador =" + idBeneficioAdicionalColaborador + " ]";
    }

}
