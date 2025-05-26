package mx.com.consolida.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the definicion_documento database table.
 * 
 */
@Entity
@Table(name="definicion_documento")
@NamedQuery(name="DefinicionDocumento.findAll", query="SELECT d FROM DefinicionDocumento d")
public class DefinicionDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_definicion_documento")
	private Long idDefinicionDocumento;

	@Column(name="clave_documento")
	private String claveDocumento;

	@Column(name="ind_estatus")
	private String indEstatus;

	@Column(name="nombre_documento")
	private String nombreDocumento;

	@Column(name="url_base")
	private String urlBase;

	//bi-directional many-to-one association to DocumentoM
//	@OneToMany(mappedBy="definicionDocumento")
//	private List<DocumentoMS> documentoMs;

	public DefinicionDocumento() {
	}

	public DefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}

	public Long getIdDefinicionDocumento() {
		return this.idDefinicionDocumento;
	}

	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}

	public String getClaveDocumento() {
		return this.claveDocumento;
	}

	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getNombreDocumento() {
		return this.nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getUrlBase() {
		return this.urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

}