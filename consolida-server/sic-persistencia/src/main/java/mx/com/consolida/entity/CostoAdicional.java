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

import javassist.expr.NewArray;
import mx.com.consolida.dto.CostoAdicionalDto;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "costo_adicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostoAdicional.findAll", query = "SELECT c FROM CostoAdicional c")
})
public class CostoAdicional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_costo_adicional")
    private Long idCostoAdicional;
    
    @JoinColumn(name = "id_porcentaje_promotor", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idPorcentajePromotor;
    
    @JoinColumn(name = "id_porcentaje_promotor_imss", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idPorcentajePromotorImss;
    
    @JoinColumn(name = "id_porcentaje_costos_indirectos", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idPorcentajeCostosIndirectos;
    
    @JoinColumn(name = "id_valor_timbre", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idValorTimbre;
    
    @JoinColumn(name = "id_valo_spei", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idValoSpei;
    
    @JoinColumn(name = "id_porcentaje_costo_estrategia", referencedColumnName = "id_cat_valor_default")
    @ManyToOne
    private CatValorDefault idPorcentajeCostoEstrategia;
    
    @Column(name = "monto_sgmm")
    private BigDecimal montoSgmm;
    
    @Column(name = "porcentaje_cliente_sgmm")
    private BigDecimal porcentajeClienteSgmm;
    
    @Column(name = "porcentaje_promotor_sgmm")
    private BigDecimal porcentajePromotorSgmm;
    
    @Column(name = "porcentaje_corporativo_sgmm")
    private BigDecimal porcentajeCorporativoSgmm;
    
    @Column(name = "monto_implant")
    private BigDecimal montoImplant;
    
    @Column(name = "porcentaje_cliente_implant")
    private BigDecimal porcentajeClienteImplant;
    
    @Column(name = "porcentaje_promotor_implant")
    private BigDecimal porcentajePromotorImplant;
    
    @Column(name = "porcentaje_corporativo_implant")
    private BigDecimal porcentajeCorporativoImplant;
    
    @JoinColumn(name = "id_cliente_temp", referencedColumnName = "id_cliente_temp")
    @ManyToOne
    private ClienteTemp idClienteTemp;
    
    @JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
    @ManyToOne
    private Cotizacion idCotizacion;
    
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioAlta;
    
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;
    
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificaicon;
    
    @Column(name = "ind_estatus")
    private Long indEstatus;
    
    
    

	public CostoAdicional(CostoAdicionalDto dto) {
		super();
		this.idCostoAdicional = dto.getIdCostoAdicional();
		this.idPorcentajePromotor = dto.getIdPorcentajePromotor() != null ? new CatValorDefault(dto.getIdPorcentajePromotor().getIdCatValorDefault()) : null;
		this.idPorcentajePromotorImss = dto.getIdPorcentajePromotorImss() != null ? new CatValorDefault(dto.getIdPorcentajePromotorImss().getIdCatValorDefault()) : null;
		this.idPorcentajeCostosIndirectos = dto.getIdPorcentajeCostosIndirectos() !=null ? new CatValorDefault(dto.getIdPorcentajeCostosIndirectos().getIdCatValorDefault()) : null;
		this.idValorTimbre = dto.getIdValorTimbre() != null ?new CatValorDefault(dto.getIdValorTimbre().getIdCatValorDefault()) : null ;
		this.idValoSpei = dto.getIdValoSpei() !=null ?new CatValorDefault(dto.getIdValoSpei().getIdCatValorDefault()):null;
		this.idPorcentajeCostoEstrategia = dto.getIdPorcentajeCostoEstrategia() !=null ? new CatValorDefault(dto.getIdPorcentajeCostoEstrategia().getIdCatValorDefault()):null;
		this.montoSgmm = dto.getMontoSgmm();
		this.porcentajeClienteSgmm = dto.getPorcentajeClienteSgmm();
		this.porcentajePromotorSgmm = dto.getPorcentajePromotorSgmm();
		this.porcentajeCorporativoSgmm = dto.getPorcentajeCorporativoSgmm();
		this.montoImplant = dto.getMontoImplant();
		this.porcentajeClienteImplant = dto.getPorcentajeClienteImplant();
		this.porcentajePromotorImplant = dto.getPorcentajePromotorImplant();
		this.porcentajeCorporativoImplant = dto.getPorcentajeCorporativoImplant();
		this.idClienteTemp = dto.getIdClienteTemp()!=null ? new ClienteTemp(dto.getIdClienteTemp().getIdClienteTemp()) : null;
		this.idCotizacion = dto.getIdCotizacion() !=null ? new Cotizacion(dto.getIdCotizacion().getIdCotizacion()) : null;
		this.usuarioAlta = dto.getUsuarioAlta() != null ? new Usuario(dto.getUsuarioAlta().getIdUsuario()) : null ;
		this.usuarioModificacion = dto.getUsuarioModificacion()!=null ? new Usuario(dto.getUsuarioModificacion().getIdUsuario()):null;
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificaicon = dto.getFechaModificaicon();
		this.indEstatus = dto.getIndEstatus();
	}



	public CostoAdicional(Long idCostoAdicional) {
		super();
		this.idCostoAdicional = idCostoAdicional;
	}



	public CostoAdicional() {
		super();
	}



	public Long getIdCostoAdicional() {
		return idCostoAdicional;
	}
	


	public void setIdCostoAdicional(Long idCostoAdicional) {
		this.idCostoAdicional = idCostoAdicional;
	}


	public CatValorDefault getIdPorcentajePromotor() {
		return idPorcentajePromotor;
	}


	public void setIdPorcentajePromotor(CatValorDefault idPorcentajePromotor) {
		this.idPorcentajePromotor = idPorcentajePromotor;
	}


	public CatValorDefault getIdPorcentajeCostosIndirectos() {
		return idPorcentajeCostosIndirectos;
	}


	public void setIdPorcentajeCostosIndirectos(CatValorDefault idPorcentajeCostosIndirectos) {
		this.idPorcentajeCostosIndirectos = idPorcentajeCostosIndirectos;
	}


	public CatValorDefault getIdValorTimbre() {
		return idValorTimbre;
	}


	public void setIdValorTimbre(CatValorDefault idValorTimbre) {
		this.idValorTimbre = idValorTimbre;
	}


	public CatValorDefault getIdValoSpei() {
		return idValoSpei;
	}


	public void setIdValoSpei(CatValorDefault idValoSpei) {
		this.idValoSpei = idValoSpei;
	}


	public CatValorDefault getIdPorcentajeCostoEstrategia() {
		return idPorcentajeCostoEstrategia;
	}


	public void setIdPorcentajeCostoEstrategia(CatValorDefault idPorcentajeCostoEstrategia) {
		this.idPorcentajeCostoEstrategia = idPorcentajeCostoEstrategia;
	}


	public BigDecimal getMontoSgmm() {
		return montoSgmm;
	}


	public void setMontoSgmm(BigDecimal montoSgmm) {
		this.montoSgmm = montoSgmm;
	}


	public BigDecimal getPorcentajeClienteSgmm() {
		return porcentajeClienteSgmm;
	}


	public void setPorcentajeClienteSgmm(BigDecimal porcentajeClienteSgmm) {
		this.porcentajeClienteSgmm = porcentajeClienteSgmm;
	}


	public BigDecimal getPorcentajePromotorSgmm() {
		return porcentajePromotorSgmm;
	}


	public void setPorcentajePromotorSgmm(BigDecimal porcentajePromotorSgmm) {
		this.porcentajePromotorSgmm = porcentajePromotorSgmm;
	}


	public BigDecimal getPorcentajeCorporativoSgmm() {
		return porcentajeCorporativoSgmm;
	}


	public void setPorcentajeCorporativoSgmm(BigDecimal porcentajeCorporativoSgmm) {
		this.porcentajeCorporativoSgmm = porcentajeCorporativoSgmm;
	}


	public BigDecimal getMontoImplant() {
		return montoImplant;
	}


	public void setMontoImplant(BigDecimal montoImplant) {
		this.montoImplant = montoImplant;
	}


	public BigDecimal getPorcentajeClienteImplant() {
		return porcentajeClienteImplant;
	}


	public void setPorcentajeClienteImplant(BigDecimal porcentajeClienteImplant) {
		this.porcentajeClienteImplant = porcentajeClienteImplant;
	}


	public BigDecimal getPorcentajePromotorImplant() {
		return porcentajePromotorImplant;
	}


	public void setPorcentajePromotorImplant(BigDecimal porcentajePromotorImplant) {
		this.porcentajePromotorImplant = porcentajePromotorImplant;
	}


	public BigDecimal getPorcentajeCorporativoImplant() {
		return porcentajeCorporativoImplant;
	}


	public void setPorcentajeCorporativoImplant(BigDecimal porcentajeCorporativoImplant) {
		this.porcentajeCorporativoImplant = porcentajeCorporativoImplant;
	}


	public ClienteTemp getIdClienteTemp() {
		return idClienteTemp;
	}


	public void setIdClienteTemp(ClienteTemp idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}


	public Cotizacion getIdCotizacion() {
		return idCotizacion;
	}


	public void setIdCotizacion(Cotizacion idCotizacion) {
		this.idCotizacion = idCotizacion;
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


	public Long getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}


	public CatValorDefault getIdPorcentajePromotorImss() {
		return idPorcentajePromotorImss;
	}



	public void setIdPorcentajePromotorImss(CatValorDefault idPorcentajePromotorImss) {
		this.idPorcentajePromotorImss = idPorcentajePromotorImss;
	}



	@Override
    public String toString() {
        return "mx.com.consolida.entity.CostoAdicional[ idCostoAdicional=" + idCostoAdicional + " ]";
    }
    
}
