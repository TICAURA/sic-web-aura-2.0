package mx.com.consolida.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */

public class CostoAdicionalDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCostoAdicional;
  
    private CatValorDefaultDto idPorcentajePromotor;
    
    private CatValorDefaultDto idPorcentajePromotorImss;
  
    private CatValorDefaultDto idPorcentajeCostosIndirectos;
    
    private CatValorDefaultDto idValorTimbre;
   
    private CatValorDefaultDto idValoSpei;
    
    private CatValorDefaultDto idPorcentajeCostoEstrategia;
    
    private BigDecimal montoSgmm;
    
    private BigDecimal porcentajeClienteSgmm;
    
    private BigDecimal porcentajePromotorSgmm;
    
    private BigDecimal porcentajeCorporativoSgmm;
    
    private BigDecimal montoImplant;
    
    private BigDecimal porcentajeClienteImplant;
    
    private BigDecimal porcentajePromotorImplant;
    
    private BigDecimal porcentajeCorporativoImplant;
    
    private ClienteTempDto idClienteTemp;
    
    private CotizacionDto idCotizacion;
    
    private UsuarioDTO usuarioAlta;

    private UsuarioDTO usuarioModificacion;
    
    private Date fechaAlta;
    
    private Date fechaModificaicon;
    
    private Long indEstatus;
    
	public Long getIdCostoAdicional() {
		return idCostoAdicional;
	}


	public void setIdCostoAdicional(Long idCostoAdicional) {
		this.idCostoAdicional = idCostoAdicional;
	}


	public CatValorDefaultDto getIdPorcentajePromotor() {
		return idPorcentajePromotor;
	}


	public void setIdPorcentajePromotor(CatValorDefaultDto idPorcentajePromotor) {
		this.idPorcentajePromotor = idPorcentajePromotor;
	}


	public CatValorDefaultDto getIdPorcentajeCostosIndirectos() {
		return idPorcentajeCostosIndirectos;
	}


	public void setIdPorcentajeCostosIndirectos(CatValorDefaultDto idPorcentajeCostosIndirectos) {
		this.idPorcentajeCostosIndirectos = idPorcentajeCostosIndirectos;
	}


	public CatValorDefaultDto getIdPorcentajePromotorImss() {
		return idPorcentajePromotorImss;
	}


	public void setIdPorcentajePromotorImss(CatValorDefaultDto idPorcentajePromotorImss) {
		this.idPorcentajePromotorImss = idPorcentajePromotorImss;
	}


	public CatValorDefaultDto getIdValorTimbre() {
		return idValorTimbre;
	}


	public void setIdValorTimbre(CatValorDefaultDto idValorTimbre) {
		this.idValorTimbre = idValorTimbre;
	}


	public CatValorDefaultDto getIdValoSpei() {
		return idValoSpei;
	}


	public void setIdValoSpei(CatValorDefaultDto idValoSpei) {
		this.idValoSpei = idValoSpei;
	}


	public CatValorDefaultDto getIdPorcentajeCostoEstrategia() {
		return idPorcentajeCostoEstrategia;
	}


	public void setIdPorcentajeCostoEstrategia(CatValorDefaultDto idPorcentajeCostoEstrategia) {
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


	public ClienteTempDto getIdClienteTemp() {
		return idClienteTemp;
	}


	public void setIdClienteTemp(ClienteTempDto idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}


	public CotizacionDto getIdCotizacion() {
		return idCotizacion;
	}


	public void setIdCotizacion(CotizacionDto idCotizacion) {
		this.idCotizacion = idCotizacion;
	}


	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}


	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}


	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}


	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
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


	@Override
    public String toString() {
        return "mx.com.consolida.entity.CostoAdicional[ idCostoAdicional=" + idCostoAdicional + " ]";
    }
    
}
