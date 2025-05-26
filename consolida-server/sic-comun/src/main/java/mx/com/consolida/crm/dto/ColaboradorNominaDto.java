package mx.com.consolida.crm.dto;

import java.io.Serializable;


/**
 *
 * @author Abel
 */

public class ColaboradorNominaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idColaboradorNomina;

    private Double montoPpp;

    private String correoColaborador;

    private String numeroCuenta;

    private ColaboradorDto idColaborador;

    private NominaClienteDto idNomina;

    public ColaboradorNominaDto() {
    }

    public ColaboradorNominaDto(Long idColaboradorNomina) {
        this.idColaboradorNomina = idColaboradorNomina;
    }

    public ColaboradorNominaDto(Long idColaboradorNomina, String correoColaborador, String numeroCuenta) {
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

    public ColaboradorDto getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(ColaboradorDto idColaborador) {
        this.idColaborador = idColaborador;
    }

    public NominaClienteDto getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(NominaClienteDto idNomina) {
        this.idNomina = idNomina;
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
        if (!(object instanceof ColaboradorNominaDto)) {
            return false;
        }
        ColaboradorNominaDto other = (ColaboradorNominaDto) object;
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
