package mx.com.consolida.entity.crm;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import mx.com.consolida.crm.dto.ColaboradorDto;


/**
 *
 * @author Abel
 */
@Entity
@Table(name = "colaborador", uniqueConstraints = @UniqueConstraint(columnNames = "rfc"))
public class Colaborador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador")
    private Long idColaborador;

    @Column(name = "clave_trabajador")
    private String claveTrabajador;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "curp")
    private String curp;
    @Column(name = "numero_telefono")
    private String numeroTelefono;
    @Column(name = "numero_celular")
    private String numeroCelular;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Column(name = "fecha_firma_contrato")
    @Temporal(TemporalType.DATE)
    private Date fechaFirmaContrato;
    @Column(name = "antiguedad_imss")
    @Temporal(TemporalType.DATE)
    private Date antiguedadImss;
    @Column(name = "numero_seguro_social_imss")
    private String numeroSeguroSocialImss;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario_diario_integrado")
    private BigDecimal salarioDiarioIntegrado;
    @Column(name = "salario_diario")
    private BigDecimal salarioDiario;
    @Column(name = "id_entidad_federativa")
    private Long idEntidadFederativa;
    @Column(name = "id_municipio")
    private Long idMunicipio;
    @Column(name = "calle")
    private String calle;
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "codigo_postal")
    private String codigoPostal;
    @Column(name = "numero_interior")
    private String numeroInterior;
    @Column(name = "numero_exterior")
    private String numeroExterior;
    @Column(name = "id_nivel_estudios")
    private Long idNivelEstudios;
    @Column(name = "profesion")
    private String profesion;
    @Column(name = "id_estado_civil")
    private Long idEstadoCivil;
    @Column(name = "id_genero")
    private Long idGenero;
    @Column(name = "tipo_sangre")
    private String tipoSangre;
    @Column(name = "id_forma_pago")
    private Long idFormaPago;
    @Column(name = "id_banco_operador")
    private Long idBancoOperador;
    @Column(name = "localidad_sucursal")
    private String localidadSucursal;
    @Column(name = "llave_banco_operador")
    private String llaveBancoOperador;
    @Column(name = "cuenta_clabe_1")
    private String cuentaClabe1;
    @Column(name = "id_turno")
    private Long idTurno;
    @Column(name = "es_clabe1_principal")
    private Long esClabe1Principal;
    @Column(name = "cuenta_clabe_2")
    private String cuentaClabe2;
    @Column(name = "es_clabe2_principal")
    private Long esClabe2Principal;
    @Column(name = "num_tarjeta_deposito")
    private String numTarjetaDeposito;
    @Column(name = "num_cuenta_deposito")
    private String numCuentaDeposito;
    @Column(name = "tipo_empleado")
    private String tipoEmpleado;
    @Column(name = "id_tipo_jornada")
    private Long idTipoJornada;
    @Column(name = "unidad_medica_familiar")
    private String unidadMedicaFamiliar;
    @Column(name = "es_pension_alimenticia")
    private Long esPensionAlimenticia;
    @Column(name = "factor_o_monto_descuento")
    private String factorOMontoDescuento;
    @Column(name = "registro_patronal")
    private String registroPatronal;
    @Column(name = "riesgo")
    private String riesgo;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "puesto")
    private String puesto;
    @Column(name = "id_tipo_contrato")
    private Long idTipoContrato;
    @Column(name = "id_tipo_ingreso")
    private Long idTipoIngreso;
    @Column(name = "pais")
    private String pais;
    @Column(name = "clave_regimen_contratacion")
    private String claveRegimenContratacion;
    @Column(name = "base_cotizacion")
    private String baseCotizacion;
    @Column(name = "clave_tipo_contrato")
    private String claveTipoContrato;
    @Column(name = "clave_tipo_jornada")
    private String claveTipoJornada;
    @Column(name = "clave_entidad_federativa")
    private String claveEntidadFederativa;
    @Column(name = "clave_regimen_contrato")
    private String claveRegimenContrato;
    @Column(name = "dias_duracion")
    private Long diasDuracion;
    @Column(name = "id_nacionalidad")
    private Long idNacionalidad;
    @Column(name = "id_beneficio_adicional")
    private Long idBeneficioAdicional;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;

    @Column(name = "usuario_alta")
    private Long usuarioAlta;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "ind_estatus")
    private String indEstatus;

    public Colaborador() {
    }

    public Colaborador(Long idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Colaborador(Long idColaborador, String claveTrabajador, String nombre, String apellidoPaterno, Date fechaNacimiento, String rfc, String curp, Long usuarioAlta, Date fechaAlta, String indEstatus) {
        this.idColaborador = idColaborador;
        this.claveTrabajador = claveTrabajador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.rfc = rfc;
        this.curp = curp;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Colaborador(ColaboradorDto dto) {
		super();
		this.idColaborador = dto.getIdColaborador();
		this.claveTrabajador = dto.getClaveTrabajador();
		this.nombre = dto.getNombre();
		this.apellidoPaterno = dto.getApellidoPaterno();
		this.apellidoMaterno = dto.getApellidoMaterno();
		this.fechaNacimiento = dto.getFechaNacimiento();
		this.rfc = dto.getRfc();
		this.curp = dto.getCurp();
		this.numeroTelefono = dto.getNumeroTelefono();
		this.numeroCelular = dto.getNumeroCelular();
		this.correoElectronico = dto.getCorreoElectronico();
		this.fechaFirmaContrato = dto.getFechaFirmaContrato();
		this.antiguedadImss = dto.getAntiguedadImss();
		this.numeroSeguroSocialImss = dto.getNumeroSeguroSocialImss();
		this.salarioDiarioIntegrado = dto.getSalarioDiarioIntegrado();
		this.salarioDiario = dto.getSalarioDiario();
		this.idEntidadFederativa = dto.getIdEntidadFederativa();
		this.idMunicipio = dto.getIdMunicipio();
		this.calle = dto.getCalle();
		this.colonia = dto.getColonia();
		this.codigoPostal = dto.getCodigoPostal();
		this.numeroInterior = dto.getNumeroInterior();
		this.numeroExterior = dto.getNumeroExterior();
		this.idNivelEstudios = dto.getIdNivelEstudios();
		this.profesion = dto.getProfesion();
		this.idEstadoCivil = dto.getIdEstadoCivil();
		this.idGenero = dto.getIdGenero();
		this.tipoSangre = dto.getTipoSangre();
		this.idFormaPago = dto.getIdFormaPago();
		this.idBancoOperador = dto.getIdBancoOperador();
		this.localidadSucursal = dto.getLocalidadSucursal();
		this.llaveBancoOperador = dto.getLlaveBancoOperador();
		this.cuentaClabe1 = dto.getCuentaClabe1();
		this.idTurno = dto.getIdTurno();
		this.esClabe1Principal = dto.getEsClabe1Principal();
		this.cuentaClabe2 = dto.getCuentaClabe2();
		this.esClabe2Principal = dto.getEsClabe2Principal();
		this.numTarjetaDeposito = dto.getNumTarjetaDeposito();
		this.numCuentaDeposito = dto.getNumCuentaDeposito();
		this.tipoEmpleado = dto.getTipoEmpleado();
		this.idTipoJornada = dto.getIdTipoJornada();
		this.unidadMedicaFamiliar = dto.getUnidadMedicaFamiliar();
		this.esPensionAlimenticia = dto.getEsPensionAlimenticia();
		this.factorOMontoDescuento = dto.getFactorOMontoDescuento();
		this.registroPatronal = dto.getRegistroPatronal();
		this.riesgo = dto.getRiesgo();
		this.departamento = dto.getDepartamento();
		this.puesto = dto.getPuesto();
		this.idTipoContrato = dto.getIdTipoContrato();
		this.idTipoIngreso = dto.getIdTipoIngreso();
		this.pais = dto.getPais();
		this.claveRegimenContratacion = dto.getClaveRegimenContratacion();
		this.baseCotizacion = dto.getBaseCotizacion();
		this.claveTipoContrato = dto.getClaveTipoContrato();
		this.claveTipoJornada = dto.getClaveTipoJornada();
		this.claveEntidadFederativa = dto.getClaveEntidadFederativa();
		this.claveRegimenContrato = dto.getClaveRegimenContrato();
		this.diasDuracion = dto.getDiasDuracion();
		this.idNacionalidad = dto.getIdNacionalidad();
		this.idBeneficioAdicional = dto.getIdBeneficioAdicional();
		this.usuarioModificacion = dto.getUsuarioModificacion();
		this.usuarioAlta = dto.getUsuarioAlta();
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificacion = dto.getFechaModificacion();
		this.indEstatus = dto.getIndEstatus();
	}

	public Long getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Long idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getClaveTrabajador() {
        return claveTrabajador;
    }

    public void setClaveTrabajador(String claveTrabajador) {
        this.claveTrabajador = claveTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Date getFechaFirmaContrato() {
        return fechaFirmaContrato;
    }

    public void setFechaFirmaContrato(Date fechaFirmaContrato) {
        this.fechaFirmaContrato = fechaFirmaContrato;
    }

    public Date getAntiguedadImss() {
        return antiguedadImss;
    }

    public void setAntiguedadImss(Date antiguedadImss) {
        this.antiguedadImss = antiguedadImss;
    }

    public String getNumeroSeguroSocialImss() {
        return numeroSeguroSocialImss;
    }

    public void setNumeroSeguroSocialImss(String numeroSeguroSocialImss) {
        this.numeroSeguroSocialImss = numeroSeguroSocialImss;
    }

    public BigDecimal getSalarioDiarioIntegrado() {
        return salarioDiarioIntegrado;
    }

    public void setSalarioDiarioIntegrado(BigDecimal salarioDiarioIntegrado) {
        this.salarioDiarioIntegrado = salarioDiarioIntegrado;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public Long getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setIdEntidadFederativa(Long idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public Long getIdNivelEstudios() {
        return idNivelEstudios;
    }

    public void setIdNivelEstudios(Long idNivelEstudios) {
        this.idNivelEstudios = idNivelEstudios;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Long getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Long idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Long getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(Long idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public Long getIdBancoOperador() {
        return idBancoOperador;
    }

    public void setIdBancoOperador(Long idBancoOperador) {
        this.idBancoOperador = idBancoOperador;
    }

    public String getLocalidadSucursal() {
        return localidadSucursal;
    }

    public void setLocalidadSucursal(String localidadSucursal) {
        this.localidadSucursal = localidadSucursal;
    }

    public String getLlaveBancoOperador() {
        return llaveBancoOperador;
    }

    public void setLlaveBancoOperador(String llaveBancoOperador) {
        this.llaveBancoOperador = llaveBancoOperador;
    }

    public String getCuentaClabe1() {
        return cuentaClabe1;
    }

    public void setCuentaClabe1(String cuentaClabe1) {
        this.cuentaClabe1 = cuentaClabe1;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    public Long getEsClabe1Principal() {
        return esClabe1Principal;
    }

    public void setEsClabe1Principal(Long esClabe1Principal) {
        this.esClabe1Principal = esClabe1Principal;
    }

    public String getCuentaClabe2() {
        return cuentaClabe2;
    }

    public void setCuentaClabe2(String cuentaClabe2) {
        this.cuentaClabe2 = cuentaClabe2;
    }

    public Long getEsClabe2Principal() {
        return esClabe2Principal;
    }

    public void setEsClabe2Principal(Long esClabe2Principal) {
        this.esClabe2Principal = esClabe2Principal;
    }

    public String getNumTarjetaDeposito() {
        return numTarjetaDeposito;
    }

    public void setNumTarjetaDeposito(String numTarjetaDeposito) {
        this.numTarjetaDeposito = numTarjetaDeposito;
    }

    public String getNumCuentaDeposito() {
        return numCuentaDeposito;
    }

    public void setNumCuentaDeposito(String numCuentaDeposito) {
        this.numCuentaDeposito = numCuentaDeposito;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public Long getIdTipoJornada() {
        return idTipoJornada;
    }

    public void setIdTipoJornada(Long idTipoJornada) {
        this.idTipoJornada = idTipoJornada;
    }

    public String getUnidadMedicaFamiliar() {
        return unidadMedicaFamiliar;
    }

    public void setUnidadMedicaFamiliar(String unidadMedicaFamiliar) {
        this.unidadMedicaFamiliar = unidadMedicaFamiliar;
    }

    public Long getEsPensionAlimenticia() {
        return esPensionAlimenticia;
    }

    public void setEsPensionAlimenticia(Long esPensionAlimenticia) {
        this.esPensionAlimenticia = esPensionAlimenticia;
    }

    public String getFactorOMontoDescuento() {
        return factorOMontoDescuento;
    }

    public void setFactorOMontoDescuento(String factorOMontoDescuento) {
        this.factorOMontoDescuento = factorOMontoDescuento;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Long idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public Long getIdTipoIngreso() {
        return idTipoIngreso;
    }

    public void setIdTipoIngreso(Long idTipoIngreso) {
        this.idTipoIngreso = idTipoIngreso;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getClaveRegimenContratacion() {
        return claveRegimenContratacion;
    }

    public void setClaveRegimenContratacion(String claveRegimenContratacion) {
        this.claveRegimenContratacion = claveRegimenContratacion;
    }

    public String getBaseCotizacion() {
        return baseCotizacion;
    }

    public void setBaseCotizacion(String baseCotizacion) {
        this.baseCotizacion = baseCotizacion;
    }

    public String getClaveTipoContrato() {
        return claveTipoContrato;
    }

    public void setClaveTipoContrato(String claveTipoContrato) {
        this.claveTipoContrato = claveTipoContrato;
    }

    public String getClaveTipoJornada() {
        return claveTipoJornada;
    }

    public void setClaveTipoJornada(String claveTipoJornada) {
        this.claveTipoJornada = claveTipoJornada;
    }

    public String getClaveEntidadFederativa() {
        return claveEntidadFederativa;
    }

    public void setClaveEntidadFederativa(String claveEntidadFederativa) {
        this.claveEntidadFederativa = claveEntidadFederativa;
    }

    public String getClaveRegimenContrato() {
        return claveRegimenContrato;
    }

    public void setClaveRegimenContrato(String claveRegimenContrato) {
        this.claveRegimenContrato = claveRegimenContrato;
    }

    public Long getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(Long diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

    public Long getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Long idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public Long getIdBeneficioAdicional() {
        return idBeneficioAdicional;
    }

    public void setIdBeneficioAdicional(Long idBeneficioAdicional) {
        this.idBeneficioAdicional = idBeneficioAdicional;
    }

    public Long getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Long usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Long getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Long usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
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

    public String getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(String indEstatus) {
        this.indEstatus = indEstatus;
    }



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((antiguedadImss == null) ? 0 : antiguedadImss.hashCode());
		result = prime * result + ((apellidoMaterno == null) ? 0 : apellidoMaterno.hashCode());
		result = prime * result + ((apellidoPaterno == null) ? 0 : apellidoPaterno.hashCode());
		result = prime * result + ((baseCotizacion == null) ? 0 : baseCotizacion.hashCode());
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((claveEntidadFederativa == null) ? 0 : claveEntidadFederativa.hashCode());
		result = prime * result + ((claveRegimenContratacion == null) ? 0 : claveRegimenContratacion.hashCode());
		result = prime * result + ((claveRegimenContrato == null) ? 0 : claveRegimenContrato.hashCode());
		result = prime * result + ((claveTipoContrato == null) ? 0 : claveTipoContrato.hashCode());
		result = prime * result + ((claveTipoJornada == null) ? 0 : claveTipoJornada.hashCode());
		result = prime * result + ((claveTrabajador == null) ? 0 : claveTrabajador.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((colonia == null) ? 0 : colonia.hashCode());
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		result = prime * result + ((cuentaClabe1 == null) ? 0 : cuentaClabe1.hashCode());
		result = prime * result + ((cuentaClabe2 == null) ? 0 : cuentaClabe2.hashCode());
		result = prime * result + ((curp == null) ? 0 : curp.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((diasDuracion == null) ? 0 : diasDuracion.hashCode());
		result = prime * result + ((esClabe1Principal == null) ? 0 : esClabe1Principal.hashCode());
		result = prime * result + ((esClabe2Principal == null) ? 0 : esClabe2Principal.hashCode());
		result = prime * result + ((esPensionAlimenticia == null) ? 0 : esPensionAlimenticia.hashCode());
		result = prime * result + ((factorOMontoDescuento == null) ? 0 : factorOMontoDescuento.hashCode());
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaFirmaContrato == null) ? 0 : fechaFirmaContrato.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((idBancoOperador == null) ? 0 : idBancoOperador.hashCode());
		result = prime * result + ((idBeneficioAdicional == null) ? 0 : idBeneficioAdicional.hashCode());
		result = prime * result + ((idColaborador == null) ? 0 : idColaborador.hashCode());
		result = prime * result + ((idEntidadFederativa == null) ? 0 : idEntidadFederativa.hashCode());
		result = prime * result + ((idEstadoCivil == null) ? 0 : idEstadoCivil.hashCode());
		result = prime * result + ((idFormaPago == null) ? 0 : idFormaPago.hashCode());
		result = prime * result + ((idGenero == null) ? 0 : idGenero.hashCode());
		result = prime * result + ((idMunicipio == null) ? 0 : idMunicipio.hashCode());
		result = prime * result + ((idNacionalidad == null) ? 0 : idNacionalidad.hashCode());
		result = prime * result + ((idNivelEstudios == null) ? 0 : idNivelEstudios.hashCode());
		result = prime * result + ((idTipoContrato == null) ? 0 : idTipoContrato.hashCode());
		result = prime * result + ((idTipoIngreso == null) ? 0 : idTipoIngreso.hashCode());
		result = prime * result + ((idTipoJornada == null) ? 0 : idTipoJornada.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + ((indEstatus == null) ? 0 : indEstatus.hashCode());
		result = prime * result + ((llaveBancoOperador == null) ? 0 : llaveBancoOperador.hashCode());
		result = prime * result + ((localidadSucursal == null) ? 0 : localidadSucursal.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numCuentaDeposito == null) ? 0 : numCuentaDeposito.hashCode());
		result = prime * result + ((numTarjetaDeposito == null) ? 0 : numTarjetaDeposito.hashCode());
		result = prime * result + ((numeroCelular == null) ? 0 : numeroCelular.hashCode());
		result = prime * result + ((numeroExterior == null) ? 0 : numeroExterior.hashCode());
		result = prime * result + ((numeroInterior == null) ? 0 : numeroInterior.hashCode());
		result = prime * result + ((numeroSeguroSocialImss == null) ? 0 : numeroSeguroSocialImss.hashCode());
		result = prime * result + ((numeroTelefono == null) ? 0 : numeroTelefono.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((profesion == null) ? 0 : profesion.hashCode());
		result = prime * result + ((puesto == null) ? 0 : puesto.hashCode());
		result = prime * result + ((registroPatronal == null) ? 0 : registroPatronal.hashCode());
		result = prime * result + ((rfc == null) ? 0 : rfc.hashCode());
		result = prime * result + ((riesgo == null) ? 0 : riesgo.hashCode());
		result = prime * result + ((salarioDiario == null) ? 0 : salarioDiario.hashCode());
		result = prime * result + ((salarioDiarioIntegrado == null) ? 0 : salarioDiarioIntegrado.hashCode());
		result = prime * result + ((tipoEmpleado == null) ? 0 : tipoEmpleado.hashCode());
		result = prime * result + ((tipoSangre == null) ? 0 : tipoSangre.hashCode());
		result = prime * result + ((unidadMedicaFamiliar == null) ? 0 : unidadMedicaFamiliar.hashCode());
		result = prime * result + ((usuarioAlta == null) ? 0 : usuarioAlta.hashCode());
		result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaborador other = (Colaborador) obj;
		if (antiguedadImss == null) {
			if (other.antiguedadImss != null)
				return false;
		} else if (!antiguedadImss.equals(other.antiguedadImss))
			return false;
		if (apellidoMaterno == null) {
			if (other.apellidoMaterno != null)
				return false;
		} else if (!apellidoMaterno.equals(other.apellidoMaterno))
			return false;
		if (apellidoPaterno == null) {
			if (other.apellidoPaterno != null)
				return false;
		} else if (!apellidoPaterno.equals(other.apellidoPaterno))
			return false;
		if (baseCotizacion == null) {
			if (other.baseCotizacion != null)
				return false;
		} else if (!baseCotizacion.equals(other.baseCotizacion))
			return false;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (claveEntidadFederativa == null) {
			if (other.claveEntidadFederativa != null)
				return false;
		} else if (!claveEntidadFederativa.equals(other.claveEntidadFederativa))
			return false;
		if (claveRegimenContratacion == null) {
			if (other.claveRegimenContratacion != null)
				return false;
		} else if (!claveRegimenContratacion.equals(other.claveRegimenContratacion))
			return false;
		if (claveRegimenContrato == null) {
			if (other.claveRegimenContrato != null)
				return false;
		} else if (!claveRegimenContrato.equals(other.claveRegimenContrato))
			return false;
		if (claveTipoContrato == null) {
			if (other.claveTipoContrato != null)
				return false;
		} else if (!claveTipoContrato.equals(other.claveTipoContrato))
			return false;
		if (claveTipoJornada == null) {
			if (other.claveTipoJornada != null)
				return false;
		} else if (!claveTipoJornada.equals(other.claveTipoJornada))
			return false;
		if (claveTrabajador == null) {
			if (other.claveTrabajador != null)
				return false;
		} else if (!claveTrabajador.equals(other.claveTrabajador))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (colonia == null) {
			if (other.colonia != null)
				return false;
		} else if (!colonia.equals(other.colonia))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (cuentaClabe1 == null) {
			if (other.cuentaClabe1 != null)
				return false;
		} else if (!cuentaClabe1.equals(other.cuentaClabe1))
			return false;
		if (cuentaClabe2 == null) {
			if (other.cuentaClabe2 != null)
				return false;
		} else if (!cuentaClabe2.equals(other.cuentaClabe2))
			return false;
		if (curp == null) {
			if (other.curp != null)
				return false;
		} else if (!curp.equals(other.curp))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (diasDuracion == null) {
			if (other.diasDuracion != null)
				return false;
		} else if (!diasDuracion.equals(other.diasDuracion))
			return false;
		if (esClabe1Principal == null) {
			if (other.esClabe1Principal != null)
				return false;
		} else if (!esClabe1Principal.equals(other.esClabe1Principal))
			return false;
		if (esClabe2Principal == null) {
			if (other.esClabe2Principal != null)
				return false;
		} else if (!esClabe2Principal.equals(other.esClabe2Principal))
			return false;
		if (esPensionAlimenticia == null) {
			if (other.esPensionAlimenticia != null)
				return false;
		} else if (!esPensionAlimenticia.equals(other.esPensionAlimenticia))
			return false;
		if (factorOMontoDescuento == null) {
			if (other.factorOMontoDescuento != null)
				return false;
		} else if (!factorOMontoDescuento.equals(other.factorOMontoDescuento))
			return false;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaFirmaContrato == null) {
			if (other.fechaFirmaContrato != null)
				return false;
		} else if (!fechaFirmaContrato.equals(other.fechaFirmaContrato))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (idBancoOperador == null) {
			if (other.idBancoOperador != null)
				return false;
		} else if (!idBancoOperador.equals(other.idBancoOperador))
			return false;
		if (idBeneficioAdicional == null) {
			if (other.idBeneficioAdicional != null)
				return false;
		} else if (!idBeneficioAdicional.equals(other.idBeneficioAdicional))
			return false;
		if (idColaborador == null) {
			if (other.idColaborador != null)
				return false;
		} else if (!idColaborador.equals(other.idColaborador))
			return false;
		if (idEntidadFederativa == null) {
			if (other.idEntidadFederativa != null)
				return false;
		} else if (!idEntidadFederativa.equals(other.idEntidadFederativa))
			return false;
		if (idEstadoCivil == null) {
			if (other.idEstadoCivil != null)
				return false;
		} else if (!idEstadoCivil.equals(other.idEstadoCivil))
			return false;
		if (idFormaPago == null) {
			if (other.idFormaPago != null)
				return false;
		} else if (!idFormaPago.equals(other.idFormaPago))
			return false;
		if (idGenero == null) {
			if (other.idGenero != null)
				return false;
		} else if (!idGenero.equals(other.idGenero))
			return false;
		if (idMunicipio == null) {
			if (other.idMunicipio != null)
				return false;
		} else if (!idMunicipio.equals(other.idMunicipio))
			return false;
		if (idNacionalidad == null) {
			if (other.idNacionalidad != null)
				return false;
		} else if (!idNacionalidad.equals(other.idNacionalidad))
			return false;
		if (idNivelEstudios == null) {
			if (other.idNivelEstudios != null)
				return false;
		} else if (!idNivelEstudios.equals(other.idNivelEstudios))
			return false;
		if (idTipoContrato == null) {
			if (other.idTipoContrato != null)
				return false;
		} else if (!idTipoContrato.equals(other.idTipoContrato))
			return false;
		if (idTipoIngreso == null) {
			if (other.idTipoIngreso != null)
				return false;
		} else if (!idTipoIngreso.equals(other.idTipoIngreso))
			return false;
		if (idTipoJornada == null) {
			if (other.idTipoJornada != null)
				return false;
		} else if (!idTipoJornada.equals(other.idTipoJornada))
			return false;
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (indEstatus == null) {
			if (other.indEstatus != null)
				return false;
		} else if (!indEstatus.equals(other.indEstatus))
			return false;
		if (llaveBancoOperador == null) {
			if (other.llaveBancoOperador != null)
				return false;
		} else if (!llaveBancoOperador.equals(other.llaveBancoOperador))
			return false;
		if (localidadSucursal == null) {
			if (other.localidadSucursal != null)
				return false;
		} else if (!localidadSucursal.equals(other.localidadSucursal))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numCuentaDeposito == null) {
			if (other.numCuentaDeposito != null)
				return false;
		} else if (!numCuentaDeposito.equals(other.numCuentaDeposito))
			return false;
		if (numTarjetaDeposito == null) {
			if (other.numTarjetaDeposito != null)
				return false;
		} else if (!numTarjetaDeposito.equals(other.numTarjetaDeposito))
			return false;
		if (numeroCelular == null) {
			if (other.numeroCelular != null)
				return false;
		} else if (!numeroCelular.equals(other.numeroCelular))
			return false;
		if (numeroExterior == null) {
			if (other.numeroExterior != null)
				return false;
		} else if (!numeroExterior.equals(other.numeroExterior))
			return false;
		if (numeroInterior == null) {
			if (other.numeroInterior != null)
				return false;
		} else if (!numeroInterior.equals(other.numeroInterior))
			return false;
		if (numeroSeguroSocialImss == null) {
			if (other.numeroSeguroSocialImss != null)
				return false;
		} else if (!numeroSeguroSocialImss.equals(other.numeroSeguroSocialImss))
			return false;
		if (numeroTelefono == null) {
			if (other.numeroTelefono != null)
				return false;
		} else if (!numeroTelefono.equals(other.numeroTelefono))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (profesion == null) {
			if (other.profesion != null)
				return false;
		} else if (!profesion.equals(other.profesion))
			return false;
		if (puesto == null) {
			if (other.puesto != null)
				return false;
		} else if (!puesto.equals(other.puesto))
			return false;
		if (registroPatronal == null) {
			if (other.registroPatronal != null)
				return false;
		} else if (!registroPatronal.equals(other.registroPatronal))
			return false;
		if (rfc == null) {
			if (other.rfc != null)
				return false;
		} else if (!rfc.equals(other.rfc))
			return false;
		if (riesgo == null) {
			if (other.riesgo != null)
				return false;
		} else if (!riesgo.equals(other.riesgo))
			return false;
		if (salarioDiario == null) {
			if (other.salarioDiario != null)
				return false;
		} else if (!salarioDiario.equals(other.salarioDiario))
			return false;
		if (salarioDiarioIntegrado == null) {
			if (other.salarioDiarioIntegrado != null)
				return false;
		} else if (!salarioDiarioIntegrado.equals(other.salarioDiarioIntegrado))
			return false;
		if (tipoEmpleado == null) {
			if (other.tipoEmpleado != null)
				return false;
		} else if (!tipoEmpleado.equals(other.tipoEmpleado))
			return false;
		if (tipoSangre == null) {
			if (other.tipoSangre != null)
				return false;
		} else if (!tipoSangre.equals(other.tipoSangre))
			return false;
		if (unidadMedicaFamiliar == null) {
			if (other.unidadMedicaFamiliar != null)
				return false;
		} else if (!unidadMedicaFamiliar.equals(other.unidadMedicaFamiliar))
			return false;
		if (usuarioAlta == null) {
			if (other.usuarioAlta != null)
				return false;
		} else if (!usuarioAlta.equals(other.usuarioAlta))
			return false;
		if (usuarioModificacion == null) {
			if (other.usuarioModificacion != null)
				return false;
		} else if (!usuarioModificacion.equals(other.usuarioModificacion))
			return false;
		return true;
	}

    @Override
    public String toString() {
        return "mx.com.consolida.entity.Colaborador[ idColaborador=" + idColaborador + " ]";
    }

}
