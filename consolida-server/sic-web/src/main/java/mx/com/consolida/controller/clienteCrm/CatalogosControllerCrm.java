package mx.com.consolida.controller.clienteCrm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.interfaz.CatalogoBO;

@Controller
@RequestMapping("catalogoCrm")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CATS_COTIZADOR })
public class CatalogosControllerCrm {


	@Autowired
	private CatalogoBO catBo;
	
	
	@RequestMapping(value="/obtenerCatalogosColaboradores")
	@ResponseBody
	public Map<String, Object> obtenerCatalogosColaboradores(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("nacionalidad", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.NACIONALIDAD.getCve()));
		params.put("estadoCivil", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.ESTADO_CIVIL.getCve()));
		params.put("genero", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GENERO.getCve()));
		params.put("entidadFederativa", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		params.put("nivelEstudio", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.NIVEL_ESTUDIO.getCve()));
		params.put("formaPago", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_FORMA_PAGO.getCve()));
		params.put("bancoOperador", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_BANCOS.getCve()));
		params.put("tipoJornada", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_JORNADA.getCve()));
		params.put("turno", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TURNO.getCve()));
		params.put("tipoContrato", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_CONTRATO.getCve()));
		params.put("tipoIngreso", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_INGRSOS.getCve()));
		params.put("beneficioAdicional", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.BENEFICIOS_ADICIONALES.getCve()));
		params.put("entidadFederativa", catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		return params;
	}
	@RequestMapping(value="/obtenerCatalogosBeneficiosAdicionales")
	@ResponseBody
	public Map<String, Object> obtenerCatalogosBeneficiosAdicionales(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beneficioAdicional", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.BENEFICIOS_ADICIONALES.getCve()));
		return params;
	}
	
	@RequestMapping(value="/obtenerCatalogoTipoPago")
	@ResponseBody
	public Map<String, Object> obtenerCatalogoTipoPago(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tipoPago", catBo.otenerTipoPagoActivo());
		return params;
	}
	
	
}