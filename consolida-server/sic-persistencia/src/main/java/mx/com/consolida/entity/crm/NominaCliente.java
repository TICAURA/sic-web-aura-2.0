package mx.com.consolida.entity.crm;

import java.util.Date;
import java.util.List;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "nomina_cliente")
@NamedQueries({ @NamedQuery(name = "NominaCliente.findAll", query = "SELECT c FROM NominaCliente c") })
public class NominaCliente implements java.io.Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nomina_cliente", nullable = false)
	private Long idNominaCliente;

	@Column(name = "nombre_nomina", nullable = false, length = 50)
	private String nombreNomina;

	@Column(name = "clave_nomina", nullable = false, length = 45)
	private String claveNomina;

//	@Column(name = "comision_ppp", nullable = false)
//	private Long comisionPpp;
//
//	@Column(name = "comision_ss")
//	private Long comisionSs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_producto_nomina")
	private CatGeneral catProductoNomina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio")
	private PrestadoraServicio PrestadoraServicio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nominista", nullable = false)
	private Usuario usuarioNominista;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_prestadora_servicio")
//	private PrestadoraServicio prestadoraServicio;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_prestadora_servicio_fondo")
//	private PrestadoraServicio prestadoraServicioFondo;

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

	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	@OneToMany(mappedBy = "nominaCliente")
    @OrderBy("fechaAlta DESC")
    private List<NominaClienteComision> nominaClienteComision;

	@OneToMany(mappedBy = "nominaCliente")
    @OrderBy("fechaAlta DESC")
    private List<NominaClienteHonorario> nominaClienteHonorarios;

	@Column(name = "requiere_factura", nullable = false)
	private Long requiereFactura;

	public NominaCliente() {

	}

	public NominaCliente(Long idNominaCliente) {
		this.idNominaCliente = idNominaCliente;
	}

	public Long getIdNominaCliente() {
		return idNominaCliente;
	}

	public void setIdNominaCliente(Long idNominaCliente) {
		this.idNominaCliente = idNominaCliente;
	}

	public String getNombreNomina() {
		return nombreNomina;
	}

	public void setNombreNomina(String nombreNomina) {
		this.nombreNomina = nombreNomina;
	}

	public String getClaveNomina() {
		return claveNomina;
	}

	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

//	public PrestadoraServicio getPrestadoraServicio() {
//		return prestadoraServicio;
//	}
//
//	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
//		this.prestadoraServicio = prestadoraServicio;
//	}
//
//	public PrestadoraServicio getPrestadoraServicioFondo() {
//		return prestadoraServicioFondo;
//	}
//
//	public void setPrestadoraServicioFondo(PrestadoraServicio prestadoraServicioFondo) {
//		this.prestadoraServicioFondo = prestadoraServicioFondo;
//	}

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

	public CatGeneral getCatProductoNomina() {
		return catProductoNomina;
	}

	public void setCatProductoNomina(CatGeneral catProductoNomina) {
		this.catProductoNomina = catProductoNomina;
	}

	public Usuario getUsuarioNominista() {
		return usuarioNominista;
	}

	public void setUsuarioNominista(Usuario usuarioNominista) {
		this.usuarioNominista = usuarioNominista;
	}

	public List<NominaClienteComision> getNominaClienteComision() {
		return nominaClienteComision;
	}

	public void setNominaClienteComision(List<NominaClienteComision> nominaClienteComision) {
		this.nominaClienteComision = nominaClienteComision;
	}

	public List<NominaClienteHonorario> getNominaClienteHonorarios() {
		return nominaClienteHonorarios;
	}

	public void setNominaClienteHonorarios(List<NominaClienteHonorario> nominaClienteHonorarios) {
		this.nominaClienteHonorarios = nominaClienteHonorarios;
	}

	public Long getRequiereFactura() {
		return requiereFactura;
	}

	public void setRequiereFactura(Long requiereFactura) {
		this.requiereFactura = requiereFactura;
	}

	public PrestadoraServicio getPrestadoraServicio() {
		return PrestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		PrestadoraServicio = prestadoraServicio;
	}

}
