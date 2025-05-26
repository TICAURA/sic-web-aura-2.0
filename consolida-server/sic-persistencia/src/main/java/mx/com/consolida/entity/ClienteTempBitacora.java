package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.dto.ClienteTempBitacoraDto;

@Entity
@Table(name = "Cliente_Temp_Bitacora")
@NamedQuery(name = "ClienteTempBitacora.findAll", query = "SELECT c FROM ClienteTempBitacora c")
public class ClienteTempBitacora implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Cliente_Temp_Bitacora")
	private Long idClienteTempBitacora;

	@JoinColumn(name = "id_cliente_temp", referencedColumnName = "id_cliente_temp")
	@ManyToOne(targetEntity = ClienteTemp.class, cascade = CascadeType.ALL)
	private ClienteTemp clienteTemp;

	@JoinColumn(name = "id_cat_tipo_evento", referencedColumnName = "id_cat_tipo_evento")
	@ManyToOne(targetEntity = CatTipoEvento.class, cascade = CascadeType.ALL)
	private CatTipoEvento catTipoEvento;

	@Column(name = "observacion")
	private String observacion;

	@Basic(optional = false)
	@Column(name = "fecha_evento")
	private Date fechaEvento;

	@Basic(optional = false)
	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@Basic(optional = false)
	@Column(name = "usuario_alta")
	private Long usuarioAlta;

	@Column(name = "usuario_modificacion")
	private Long usuarioModificaicon;

	@Basic(optional = false)
	@Column(name = "ind_estatus")
	private Long indEstatus;



	public ClienteTempBitacora() {
		super();
	}

	public ClienteTempBitacora(ClienteTempBitacoraDto dto) {
		super();
		this.idClienteTempBitacora = dto.getIdClienteTempBitacora();
		this.clienteTemp = new ClienteTemp(dto.getIdClienteTemp());
		this.catTipoEvento = dto.getTipoEvento()!=null ? new CatTipoEvento(dto.getTipoEvento().getIdCatTipoEvento()):null;
		this.observacion = dto.getObservacion();
		this.fechaEvento = dto.getFechaEvento();
		this.fechaAlta = dto.getFechaAlta();
	}

	public Long getIdClienteTempBitacora() {
		return idClienteTempBitacora;
	}

	public void setIdClienteTempBitacora(Long idClienteTempBitacora) {
		this.idClienteTempBitacora = idClienteTempBitacora;
	}

	public CatTipoEvento getCatTipoEvento() {
		return catTipoEvento;
	}

	public void setCatTipoEvento(CatTipoEvento catTipoEvento) {
		this.catTipoEvento = catTipoEvento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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

	public ClienteTemp getClienteTemp() {
		return clienteTemp;
	}

	public void setClienteTemp(ClienteTemp clienteTemp) {
		this.clienteTemp = clienteTemp;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioModificaicon() {
		return usuarioModificaicon;
	}

	public void setUsuarioModificaicon(Long usuarioModificaicon) {
		this.usuarioModificaicon = usuarioModificaicon;
	}



}
