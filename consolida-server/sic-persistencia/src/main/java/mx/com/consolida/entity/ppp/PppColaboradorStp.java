package mx.com.consolida.entity.ppp;

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

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_colaborador_stp")
@NamedQueries({ @NamedQuery(name = "PppColaboradorStp.findAll", query = "SELECT c FROM PppColaboradorStp c") })
public class PppColaboradorStp  implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_colaborador_stp")
	private Long idPppColaboradorStp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_colaborador")
	private PppColaborador pppColaborador;
	
	@Column(name = "cve_orden_pago")
	private  String cveOrdenPago;
	
	@Column(name = "id_stp")
	private Integer idStp;
	
	@Column(name = "descripcion_error_stp")
	private  String descripcionErrorStp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	public Long getIdPppColaboradorStp() {
		return idPppColaboradorStp;
	}

	public void setIdPppColaboradorStp(Long idPppColaboradorStp) {
		this.idPppColaboradorStp = idPppColaboradorStp;
	}

	public PppColaborador getPppColaborador() {
		return pppColaborador;
	}

	public void setPppColaborador(PppColaborador pppColaborador) {
		this.pppColaborador = pppColaborador;
	}

	public String getCveOrdenPago() {
		return cveOrdenPago;
	}

	public void setCveOrdenPago(String cveOrdenPago) {
		this.cveOrdenPago = cveOrdenPago;
	}

	public Integer getIdStp() {
		return idStp;
	}

	public void setIdStp(Integer idStp) {
		this.idStp = idStp;
	}

	public String getDescripcionErrorStp() {
		return descripcionErrorStp;
	}

	public void setDescripcionErrorStp(String descripcionErrorStp) {
		this.descripcionErrorStp = descripcionErrorStp;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Usuario getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Usuario usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	

}
