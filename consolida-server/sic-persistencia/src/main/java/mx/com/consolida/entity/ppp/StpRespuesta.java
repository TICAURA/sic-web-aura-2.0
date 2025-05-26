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


@Entity
@Table(name = "stp_respuesta")
@NamedQueries({ @NamedQuery(name = "StpRespuesta.findAll", query = "SELECT c FROM StpRespuesta c") })
public class StpRespuesta  implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_stp_respuesta")
	private Long idStpRespuesta;

	@Column(name = "id_stp")
	private Long idStp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_colaborador")
	private PppColaborador pppColaborador;

	@Column(name = "nombre_empresa")
	private  String nombreEmpresa;

	@Column(name = "folio_origen")
	private  String folioOrigen;

	@Column(name = "estado")
	private  String estado;

	@Column(name = "causa_devolucion")
	private  String causaDevolucion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Column(name = "ind_estatus", nullable = false)
	private String indEstatus;

	public Long getIdStpRespuesta() {
		return idStpRespuesta;
	}

	public void setIdStpRespuesta(Long idStpRespuesta) {
		this.idStpRespuesta = idStpRespuesta;
	}

	public Long getIdStp() {
		return idStp;
	}

	public void setIdStp(Long idStp) {
		this.idStp = idStp;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCausaDevolucion() {
		return causaDevolucion;
	}

	public void setCausaDevolucion(String causaDevolucion) {
		this.causaDevolucion = causaDevolucion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public PppColaborador getPppColaborador() {
		return pppColaborador;
	}

	public void setPppColaborador(PppColaborador pppColaborador) {
		this.pppColaborador = pppColaborador;
	}



}
