package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClientePersonaContactoDto;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.crm.Colaborador;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class ColaboradorDaoImpl extends GenericDAO<Colaborador, Long> implements ColaboradorDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ColaboradorDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional(readOnly=true)
	public ColaboradorDto guardarColaborador(ColaboradorDto dto, UsuarioAplicativo us) {
		dto.setFechaAlta(new Date());
		dto.setIndEstatus("1");
		dto.setUsuarioAlta(us.getIdUsuario());
		Colaborador entity = new Colaborador(dto);
		dto.setIdColaborador(create(entity));
		return dto;
	}
	
	@Transactional(readOnly=true)
	public ColaboradorDto editarColaborador(ColaboradorDto dto, UsuarioAplicativo us) {
		dto.setFechaModificacion(new Date());
		dto.setIndEstatus("1");
		dto.setUsuarioModificacion(us.getIdUsuario());
		Colaborador entity = new Colaborador(dto);
		createOrUpdate(entity);
		return dto;
	}
	
	@Transactional(readOnly=true)
	public ColaboradorDto obtenerColaboradorById(Long idColaborador) {
		Colaborador entity = read(idColaborador);
		ColaboradorDto dto = obtenerColaboradorDto(entity);
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ColaboradorDto> obtenercolaboradoresByidNomina(Long idNomina) {
		StringBuilder sql = new StringBuilder();
		sql.append("select cn.id_colaborador_nomina as idcolaborador,\r\n" + 
				"cn.id_nomina as idNomina,\r\n" + 
				"cn.id_colaborador as idColaborador,\r\n" + 
				"cn.monto_ppp as montoPpp,\r\n" + 
				"cn.correo_colaborador as correoElectronico,\r\n" + 
				"cn.numero_cuenta as numeroCuenta,\r\n" + 
				"c.* from colaborador c join colaborador_nomina cn on cn.id_colaborador = c.id_colaborador and c.ind_estatus=1 and cn.ind_estatus=1 and cn.id_nomina =")
		.append(idNomina);
		List<ColaboradorDto> list = jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ColaboradorDto dto = new ColaboradorDto();
				dto.setIdColaborador(rs.getLong("id_colaborador"));
				dto.setClaveTrabajador(rs.getString("clave_trabajador"));
				dto.setNombre(rs.getString("nombre"));
				dto.setApellidoPaterno(rs.getString("nombre"));
				dto.setApellidoMaterno(rs.getString("nombre"));
				dto.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
				dto.setRfc(rs.getString("rfc"));
				dto.setCurp(rs.getString("curp"));
				dto.setNumeroTelefono(rs.getString("numero_telefono"));
				dto.setNumeroCelular(rs.getString("numero_celular"));
				dto.setCorreoElectronico(rs.getString("correo_electronico"));
				dto.setFechaFirmaContrato(rs.getDate("fecha_firma_contrato"));
				dto.setAntiguedadImss(rs.getDate("antiguedad_imss"));
				dto.setNumeroSeguroSocialImss(rs.getString("numero_seguro_social_imss"));
				dto.setSalarioDiarioIntegrado(rs.getBigDecimal("salario_diario_integrado"));
				dto.setSalarioDiario(rs.getBigDecimal("salario_diario"));
				dto.setIdEntidadFederativa(rs.getLong("id_entidad_federativa"));
				dto.setIdMunicipio(rs.getLong("id_municipio"));
				dto.setCalle(rs.getString("calle"));
				dto.setColonia(rs.getString("colonia"));
				dto.setCodigoPostal(rs.getString("codigo_postal"));
				dto.setNumeroInterior(rs.getString("numero_interior"));
				dto.setNumeroExterior(rs.getString("numero_exterior"));
				dto.setIdNivelEstudios(rs.getLong("id_nivel_estudios"));
				dto.setProfesion(rs.getString("profesion"));
				dto.setIdEstadoCivil(rs.getLong("id_estado_civil"));
				dto.setIdGenero(rs.getLong("id_genero"));
				dto.setTipoSangre(rs.getString("tipo_sangre"));
				dto.setIdFormaPago(rs.getLong("id_forma_pago"));
				dto.setIdBancoOperador(rs.getLong("id_banco_operador"));
				dto.setLocalidadSucursal(rs.getString("localidad_sucursal"));
				dto.setLlaveBancoOperador(rs.getString("llave_banco_operador"));
				dto.setCuentaClabe1(rs.getString("cuenta_clabe_1"));
				dto.setIdTurno(rs.getLong("id_turno"));
				dto.setEsClabe1Principal(rs.getLong("es_clabe1_principal"));
				dto.setCuentaClabe2(rs.getString("cuenta_clabe_2"));
				dto.setEsClabe2Principal(rs.getLong("es_clabe2_principal"));
				dto.setNumTarjetaDeposito(rs.getString("num_tarjeta_deposito"));
				dto.setNumCuentaDeposito(rs.getString("num_cuenta_deposito"));
				dto.setTipoEmpleado(rs.getString("tipo_empleado"));
				dto.setIdTipoJornada(rs.getLong("id_tipo_jornada"));
				dto.setUnidadMedicaFamiliar(rs.getString("unidad_medica_familiar"));
				dto.setEsPensionAlimenticia(rs.getLong("es_pension_alimenticia"));
				dto.setFactorOMontoDescuento(rs.getString("factor_o_monto_descuento"));
				dto.setRegistroPatronal(rs.getString("registro_patronal"));
				dto.setRiesgo(rs.getString("riesgo"));
				dto.setDepartamento(rs.getString("departamento"));
				dto.setPuesto(rs.getString("puesto"));
				dto.setIdTipoContrato(rs.getLong("id_tipo_contrato"));
				dto.setIdTipoIngreso(rs.getLong("id_tipo_ingreso"));
				dto.setPais(rs.getString("pais"));
				dto.setClaveRegimenContratacion(rs.getString("clave_regimen_contratacion"));
				dto.setBaseCotizacion(rs.getString("base_cotizacion"));
				dto.setClaveTipoContrato(rs.getString("clave_tipo_contrato"));
				dto.setClaveTipoJornada(rs.getString("clave_tipo_jornada"));
				dto.setClaveEntidadFederativa(rs.getString("clave_entidad_federativa"));
				dto.setClaveRegimenContrato(rs.getString("clave_regimen_contrato"));
				dto.setDiasDuracion(rs.getLong("dias_duracion"));
				dto.setIdNacionalidad(rs.getLong("id_nacionalidad"));
				dto.setIdBeneficioAdicional(rs.getLong("id_beneficio_adicional"));
				dto.setUsuarioModificacion(rs.getLong("usuario_modificacion"));
				dto.setUsuarioAlta(rs.getLong("usuario_alta"));
				dto.setFechaAlta(rs.getDate("fecha_alta"));
				dto.setFechaModificacion(rs.getDate("fecha_modificacion"));
				dto.setIndEstatus(rs.getString("ind_estatus"));
				return dto;	
			}
		});
		return list;
	}
	
	public ColaboradorDto obtenerColaboradorDto(Colaborador entity) {
		ColaboradorDto dto = new ColaboradorDto();
		dto.setIdColaborador(entity.getIdColaborador());
		dto.setClaveTrabajador(entity.getClaveTrabajador());
		dto.setNombre(entity.getNombre());
		dto.setApellidoPaterno(entity.getApellidoPaterno());
		dto.setApellidoMaterno(entity.getApellidoMaterno());
		dto.setFechaNacimiento(entity.getFechaNacimiento());
		dto.setRfc(entity.getRfc());
		dto.setCurp(entity.getCurp());
		dto.setNumeroTelefono(entity.getNumeroTelefono());
		dto.setNumeroCelular(entity.getNumeroCelular());
		dto.setCorreoElectronico(entity.getCorreoElectronico());
		dto.setFechaFirmaContrato(entity.getFechaFirmaContrato());
		dto.setAntiguedadImss(entity.getAntiguedadImss());
		dto.setNumeroSeguroSocialImss(entity.getNumeroSeguroSocialImss());
		dto.setSalarioDiarioIntegrado(entity.getSalarioDiarioIntegrado());
		dto.setSalarioDiario(entity.getSalarioDiario());
		dto.setIdEntidadFederativa(entity.getIdEntidadFederativa());
		dto.setIdMunicipio(entity.getIdMunicipio());
		dto.setCalle(entity.getCalle());
		dto.setColonia(entity.getColonia());
		dto.setCodigoPostal(entity.getCodigoPostal());
		dto.setNumeroInterior(entity.getNumeroInterior());
		dto.setNumeroExterior(entity.getNumeroExterior());
		dto.setIdNivelEstudios(entity.getIdNivelEstudios());
		dto.setProfesion(entity.getProfesion());
		dto.setIdEstadoCivil(entity.getIdEstadoCivil());
		dto.setIdGenero(entity.getIdGenero());
		dto.setTipoSangre(entity.getTipoSangre());
		dto.setIdFormaPago(entity.getIdFormaPago());
		dto.setIdBancoOperador(entity.getIdBancoOperador());
		dto.setLocalidadSucursal(entity.getLocalidadSucursal());
		dto.setLlaveBancoOperador(entity.getLlaveBancoOperador());
		dto.setCuentaClabe1(entity.getCuentaClabe1());
		dto.setIdTurno(entity.getIdTurno());
		dto.setEsClabe1Principal(entity.getEsClabe1Principal());
		dto.setCuentaClabe2(entity.getCuentaClabe2());
		dto.setEsClabe2Principal(entity.getEsClabe2Principal());
		dto.setNumTarjetaDeposito(entity.getNumTarjetaDeposito());
		dto.setNumCuentaDeposito(entity.getNumCuentaDeposito());
		dto.setTipoEmpleado(entity.getTipoEmpleado());
		dto.setIdTipoJornada(entity.getIdTipoJornada());
		dto.setUnidadMedicaFamiliar(entity.getUnidadMedicaFamiliar());
		dto.setEsPensionAlimenticia(entity.getEsPensionAlimenticia());
		dto.setFactorOMontoDescuento(entity.getFactorOMontoDescuento());
		dto.setRegistroPatronal(entity.getRegistroPatronal());
		dto.setRiesgo(entity.getRiesgo());
		dto.setDepartamento(entity.getDepartamento());
		dto.setPuesto(entity.getPuesto());
		dto.setIdTipoContrato(entity.getIdTipoContrato());
		dto.setIdTipoIngreso(entity.getIdTipoIngreso());
		dto.setPais(entity.getPais());
		dto.setClaveRegimenContratacion(entity.getClaveRegimenContratacion());
		dto.setBaseCotizacion(entity.getBaseCotizacion());
		dto.setClaveTipoContrato(entity.getClaveTipoContrato());
		dto.setClaveTipoJornada(entity.getClaveTipoJornada());
		dto.setClaveEntidadFederativa(entity.getClaveEntidadFederativa());
		dto.setClaveRegimenContrato(entity.getClaveRegimenContrato());
		dto.setDiasDuracion(entity.getDiasDuracion());
		dto.setIdNacionalidad(entity.getIdNacionalidad());
		dto.setIdBeneficioAdicional(entity.getIdBeneficioAdicional());
		dto.setUsuarioModificacion(entity.getUsuarioModificacion());
		dto.setUsuarioAlta(entity.getUsuarioAlta());
		dto.setFechaAlta(entity.getFechaAlta());
		dto.setFechaModificacion(entity.getFechaModificacion());
		dto.setIndEstatus(entity.getIndEstatus());
		return dto;
	}

}
