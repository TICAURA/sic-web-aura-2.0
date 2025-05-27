package mx.com.consolida.entity.ppp;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "cat_serie")
@NamedQueries({ @NamedQuery(name = "CatSerie.findAll", query = "SELECT c FROM CatSerie c") })
public class CatSerie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cat_serie")
	private Long idCatSerie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_comprobante", nullable = false)
	private CatGeneral catTipoComprobante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_celula", nullable = false)
	private CatGeneral catCelula;
	
	@Column(name = "nombre_serie")
	private String nombreSerie;
	
	@Column(name = "folio_inicial")
	private Long folioInicial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;
	
	@Column(name ="fecha_inicio_vigencia")
	private Date fechaIncioVigencia;
	
	@Column(name="fecha_fin_vigencia" )
	private Date fechaFinVigencia;

	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;
	
	@Column(name = "id_estatus")
	private Long idEstatusSerie;
	
	public CatSerie () {
		
	}
	
	public CatSerie (Long idCatSerie) {
		this.idCatSerie = idCatSerie;
	}

	public Long getIdCatSerie() {
		return idCatSerie;
	}

	public void setIdCatSerie(Long idCatSerie) {
		this.idCatSerie = idCatSerie;
	}

	public CatGeneral getCatTipoComprobante() {
		return catTipoComprobante;
	}

	public void setCatTipoComprobante(CatGeneral catTipoComprobante) {
		this.catTipoComprobante = catTipoComprobante;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public Long getFolioInicial() {
		return folioInicial;
	}

	public void setFolioInicial(Long folioInicial) {
		this.folioInicial = folioInicial;
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

	public CatGeneral getCatCelula() {
		return catCelula;
	}

	public void setCatCelula(CatGeneral catCelula) {
		this.catCelula = catCelula;
	}

	public Date getFechaIncioVigencia() {
		return fechaIncioVigencia;
	}

	public void setFechaIncioVigencia(Date fechaIncioVigencia) {
		this.fechaIncioVigencia = fechaIncioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Long getIdEstatusSerie() {
		return idEstatusSerie;
	}

	public void setIdEstatusSerie(Long idEstatusSerie) {
		this.idEstatusSerie = idEstatusSerie;
	}
	
	

}
