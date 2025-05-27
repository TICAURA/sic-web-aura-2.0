package mx.com.consolida.entity.prestadoraservicio;

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
@Table(name = "prestadora_servicio_stp")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioStp.findAll", query = "SELECT c FROM PrestadoraServicioStp c") })
public class PrestadoraServicioStp implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_prestadora_servicio_stp")
	private Long idPrestadoraServicioStp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio")
	private PrestadoraServicio prestadoraServicio;
	
	@JoinColumn(name = "id_tipo_dispersor", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
	private CatGeneral idTipoDispersor;
	
	@Column(name = "nombre_centro_costos")
	private  String nombreCentroCostos;
	
	@Column(name = "clabe_cuenta_ordenante")
	private  String clabeCuentaOrdenante;
	
	@Column(name ="user_name")
	private  String userName;
	
	@Column(name ="password")
	private  String password;
	
	@Column(name ="client_id")
	private  String clientId;
	
	@Column(name ="client_secret")
	private  String clientSecret;
	
	@Column(name ="api_key")
	private  String apiKey;
	
	@Column(name ="id_client")
	private String idCliente; 
	
	@Column(name ="id_cuenta_ahorro")
	private String idCuentaAhorro;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	public Long getIdPrestadoraServicioStp() {
		return idPrestadoraServicioStp;
	}

	public void setIdPrestadoraServicioStp(Long idPrestadoraServicioStp) {
		this.idPrestadoraServicioStp = idPrestadoraServicioStp;
	}

	public PrestadoraServicio getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public CatGeneral getIdTipoDispersor() {
		return idTipoDispersor;
	}

	public void setIdTipoDispersor(CatGeneral idTipoDispersor) {
		this.idTipoDispersor = idTipoDispersor;
	}

	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}

	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}

	public String getClabeCuentaOrdenante() {
		return clabeCuentaOrdenante;
	}

	public void setClabeCuentaOrdenante(String clabeCuentaOrdenante) {
		this.clabeCuentaOrdenante = clabeCuentaOrdenante;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdCuentaAhorro() {
		return idCuentaAhorro;
	}

	public void setIdCuentaAhorro(String idCuentaAhorro) {
		this.idCuentaAhorro = idCuentaAhorro;
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

	

}
