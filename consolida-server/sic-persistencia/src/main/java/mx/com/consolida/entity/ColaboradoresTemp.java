package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.dto.ColaboradoresTempDto;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "colaboradores_temp")
@NamedQueries({
    @NamedQuery(name = "ColaboradoresTemp.findAll", query = "SELECT c FROM ColaboradoresTemp c")
   })
public class ColaboradoresTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_colaborador_temp")
    private Long idColaboradorTemp;
    
    @Column(name = "salario_diario")
    private BigDecimal salarioDiario;
    
    @Column(name = "salario_diario_integral")
    private BigDecimal salarioDiarioIntegral;
    
    @Column(name = "sueldo")
    private BigDecimal sueldo;
    
    @Column(name = "gravados")
    private BigDecimal gravados;
    
    @Column(name = "exentos")
    private BigDecimal exentos;
    
    @Column(name = "subsidio")
    private BigDecimal subsidio;
    
    @Column(name = "isr")
    private BigDecimal isr;
    
    @Column(name = "co_imss")
    private BigDecimal coImss;
    
    @Column(name = "neto_nomina")
    private BigDecimal netoNomina;
    
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
    
    @JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
    @ManyToOne(targetEntity = Cotizacion.class)
    private Cotizacion idCotizacion;
    
    @Column(name = "fecha_antiguedad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAntiguedad;
    
    @OneToMany(mappedBy = "idColaboradorTemp")
    private List<CotizacionColaborador> cotizacionColaboradorList;
    
    public ColaboradoresTemp() {
    }

    public ColaboradoresTemp(Long idColaboradorTemp) {
        this.idColaboradorTemp = idColaboradorTemp;
    }

    public ColaboradoresTemp(Long idColaboradorTemp, BigDecimal salarioDiario, BigDecimal gravados, BigDecimal exentos, Date fechaAlta, Long usuarioAlta) {
        this.idColaboradorTemp = idColaboradorTemp;
        this.salarioDiario = salarioDiario;
        this.gravados = gravados;
        this.exentos = exentos;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }
    
    

    public ColaboradoresTemp(ColaboradoresTempDto dto) {
		super();
		this.idColaboradorTemp = dto.getIdColaboradorTemp();
		this.salarioDiario = dto.getSalarioDiario();
		this.salarioDiarioIntegral = dto.getSalarioDiarioIntegral();
		this.sueldo = dto.getSueldo();
		this.gravados = dto.getGravados();
		this.exentos = dto.getExentos();
		this.subsidio = dto.getSubsidio();
		this.isr = dto.getIsr();
		this.coImss = dto.getCoImss();
		this.netoNomina = dto.getNetoNomina();
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificacion = dto.getFechaModificacion();
		this.usuarioAlta = dto.getUsuarioAlta();
		this.usuarioModificacion = dto.getUsuarioModificacion();
		this.indEstatus = dto.getIndEstatus();
		this.idCotizacion = dto.getIdCotizacion()!=null ? new Cotizacion(dto.getIdCotizacion().getIdCotizacion()):null;
		this.fechaAntiguedad = dto.getFechaAntiguedad();
	}

	public Long getIdColaboradorTemp() {
        return idColaboradorTemp;
    }

    public void setIdColaboradorTemp(Long idColaboradorTemp) {
        this.idColaboradorTemp = idColaboradorTemp;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public BigDecimal getSalarioDiarioIntegral() {
        return salarioDiarioIntegral;
    }

    public void setSalarioDiarioIntegral(BigDecimal salarioDiarioIntegral) {
        this.salarioDiarioIntegral = salarioDiarioIntegral;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public BigDecimal getGravados() {
        return gravados;
    }

    public void setGravados(BigDecimal gravados) {
        this.gravados = gravados;
    }

    public BigDecimal getExentos() {
        return exentos;
    }

    public void setExentos(BigDecimal exentos) {
        this.exentos = exentos;
    }

    public BigDecimal getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(BigDecimal subsidio) {
        this.subsidio = subsidio;
    }

    public BigDecimal getIsr() {
        return isr;
    }

    public void setIsr(BigDecimal isr) {
        this.isr = isr;
    }

    public BigDecimal getCoImss() {
        return coImss;
    }

    public void setCoImss(BigDecimal coImss) {
        this.coImss = coImss;
    }

    public BigDecimal getNetoNomina() {
        return netoNomina;
    }

    public void setNetoNomina(BigDecimal netoNomina) {
        this.netoNomina = netoNomina;
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

    public Long getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Long indEstatus) {
        this.indEstatus = indEstatus;
    }

    public Cotizacion getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Cotizacion idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

	public List<CotizacionColaborador> getCotizacionColaboradorList() {
		return cotizacionColaboradorList;
	}

	public void setCotizacionColaboradorList(List<CotizacionColaborador> cotizacionColaboradorList) {
		this.cotizacionColaboradorList = cotizacionColaboradorList;
	}

	public Date getFechaAntiguedad() {
		return fechaAntiguedad;
	}

	public void setFechaAntiguedad(Date fechaAntiguedad) {
		this.fechaAntiguedad = fechaAntiguedad;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	
	
    
}
