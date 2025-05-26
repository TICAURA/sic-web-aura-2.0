package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the modulo_pantalla database table.
 *
 */
@Entity
@Table(name="modulo_pantalla")
@NamedQuery(name="ModuloPantalla.findAll", query="SELECT m FROM ModuloPantalla m")
public class ModuloPantalla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_modulo_pantalla")
	private Long idModuloPantalla;

	@Column(name="ind_estatus")
	private String indEstatus;

	//bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name="id_modulo")
	private Modulo modulo;

	//bi-directional many-to-one association to Pantalla
	@ManyToOne
	@JoinColumn(name="id_pantalla")
	private Pantalla pantalla;

	public ModuloPantalla() {
	}

	public Long getIdModuloPantalla() {
		return this.idModuloPantalla;
	}

	public void setIdModuloPantalla(Long idModuloPantalla) {
		this.idModuloPantalla = idModuloPantalla;
	}

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Pantalla getPantalla() {
		return this.pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}

}
