package mx.com.consolida.controller.cotizador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dto.CatatologosCotizadorDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.generico.CatCotizadorEnum;
import mx.com.consolida.generico.CatGeneralEnum;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.CatalogosCotizadorDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.interfaz.CatalogoCotizadorBO;

@Controller
@RequestMapping("catalogoCotizador")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CATS_COTIZADOR })
public class CatalogosController {


	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private CatalogoCotizadorBO catalogoCotizadorBO;
	
	@RequestMapping(value="/tipoPersona")
	@ResponseBody
	public List<CatGeneralDto> tipoPersona() {
		return catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_PERSONA.getCve());
	}
	
	@RequestMapping(value="/cargaCatalogosNuevoCliente")
	@ResponseBody
	public ClienteTempDto cargaInicialNuevoCliente() {
		ClienteTempDto cliente = new ClienteTempDto();
		
		cliente.setGrupos(catBo.obtenerCatalogoGrupo());
		cliente.setEntidadFederativa(cargaCatalogoEntidadFederativa());
		cliente.setGirosComerciales(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve()));
		
		return cliente;
	}
	
	@RequestMapping(value="/cargaCatalogoGrupo")
	@ResponseBody
	public ClienteTempDto cargaCatalogoGrupo() {
		ClienteTempDto cliente = new ClienteTempDto();
		cliente.setGrupos(catBo.obtenerCatalogoGrupo());		
		return cliente;
	}
	
	@RequestMapping(value="/cargaCatalogoEntidadFederativa")
	@ResponseBody
	public List<CatGeneralDto> cargaCatalogoEntidadFederativa() {
		return catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve());
	}
	
	@RequestMapping(value="/cargaCatMunicipiosXEntidadFed")
	@ResponseBody
	public List<CatGeneralDto> cargaCatMunicipiosXEntidadFed(@RequestBody String cveEntidadFederativa) {
		return catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), cveEntidadFederativa);
	}
	

	@RequestMapping(value="/obtenerCatalogosPreCotizador")
	@ResponseBody
	public CatalogosCotizadorDTO obtenerCatalogosPreCotizador(Model model) {
		
		CatalogosCotizadorDTO catCotizador = new CatalogosCotizadorDTO();
		
		catCotizador.setCatTipoNomina(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_NOMINA.getCve()));
		catCotizador.setCatTipoCotizacion(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_COTIZACION.getCve()));
		catCotizador.setCatEstatus(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.ESTATUS.getCve()));
		
		return catCotizador;
	}
	
	
	
	@RequestMapping(value="/obtenerCatalogosRealizarCotizador")
	@ResponseBody
	public boolean obtenerCatalogosRealizarCotizador(Model model) {
		CatatologosCotizadorDto cat = new CatatologosCotizadorDto();
        cat.setSmg(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.SMG.getCve()));
        cat.setSmgzf(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.SMGZF.getCve()));
        cat.setUma(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.UMA.getCve()));
        cat.setIsn(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.ISN.getCve()));
        cat.setCfp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CFP.getCve()));
        cat.setEp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.EP.getCve()));
        cat.setEt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.ET.getCve()));
        cat.setGmp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.GMP.getCve()));
        cat.setGmt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.GMT.getCve()));
        cat.setPdp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDP.getCve()));
        cat.setPdt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDT.getCve()));
        cat.setIvp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.IVP.getCve()));
        cat.setIvt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.IVT.getCve()));
        cat.setPdgp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDGP.getCve()));
        cat.setRp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.RP.getCve()));
        cat.setCvp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CVP.getCve()));
        cat.setCvt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CVT.getCve()));
        cat.setAip(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.AIP.getCve()));
        model.addAttribute(ReferenciaSeguridad.CATS_COTIZADOR, cat);
		return true;
	}
	

	@RequestMapping(value="/obtenerCatalogosCotizador")
	@ResponseBody
	public CatalogosCotizadorDTO obtenerCatalogosCotizador(Model model) {
		CatalogosCotizadorDTO catCotizador = new CatalogosCotizadorDTO();
		catCotizador.setCatVacaciones(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.VACACIONES.getCve()));
		catCotizador.setCatZona(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.ZONA.getCve()));
		catCotizador.setCatTipoNomina(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_NOMINA.getCve()));
		catCotizador.setCatDias(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.DIAS.getCve()));
		catCotizador.setCatTipoImss(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_IMMS.getCve()));
		catCotizador.setCatTipoPrestaciones(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_PRESTACIONES.getCve()));
		catCotizador.setCatCosteoAsimilable(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.COSTEO_ASIMILABLE.getCve()));
		catCotizador.setCatTipoCotizacion(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_COTIZACION.getCve()));
		catCotizador.setCatEstatus(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.ESTATUS.getCve()));
		catCotizador.setCatTipoPeriodicidad(catBo.otenerTipoPagoActivo());
		catCotizador.setCatTipoEsquema(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_ESQUEMA.getCve()));
		
		catCotizador.setCatPorcPromotor(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_PORCENTAJE_PROMOTOR.getId()));
		catCotizador.setListCatPorcPromotor(catBo.obtenerListCatValorDefaultById(CatGeneralEnum.DEFAULT_PORCENTAJE_PROMOTOR.getId(),1L));
		
		catCotizador.setCatPorcEstrategia(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_ESTRATEGIA.getId()));
		catCotizador.setCatPorcIndirectos(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_COSTOS_INDIRECTOS.getId()));
		catCotizador.setCatValorSpei(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_SPEI.getId()));
		catCotizador.setCatValorTimbrado(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_TIMBRADO.getId()));
		catCotizador.setCatPorcPromotorImss(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_PORCENTAJE_PROMOTOR_IMSS.getId()));
		
		
		
		List<CatGeneralDto> proveedor = new ArrayList<CatGeneralDto>();
		proveedor.add(new CatGeneralDto(1L, null, "Sí", 1L));
		proveedor.add(new CatGeneralDto(0L, null, "No", 1L));
		catCotizador.setCatProveedor(proveedor);
		return catCotizador;
	}
	
	@RequestMapping(value="/obtenerCatalogosCostosAdicionales")
	@ResponseBody
	public CatalogosCotizadorDTO obtenerCatalogosCostosAdicionales(Model model) {
		CatalogosCotizadorDTO catCotizador = new CatalogosCotizadorDTO();
		
		catCotizador.setCatPorcEstrategia(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_ESTRATEGIA.getId()));
		catCotizador.setCatPorcIndirectos(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_COSTOS_INDIRECTOS.getId()));
		catCotizador.setCatPorcPromotorImss(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_PORCENTAJE_PROMOTOR_IMSS.getId()));
		catCotizador.setCatValorSpei(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_SPEI.getId()));
		catCotizador.setCatValorTimbrado(catBo.obtenerCatValorDefaultById(CatGeneralEnum.DEFAULT_TIMBRADO.getId()));
		
		return catCotizador;
	}
	
	@RequestMapping(value="/modificarCostosAdicionales", method = RequestMethod.POST)
	@ResponseBody
	public CatalogosCotizadorDTO modificarCostosAdicionales(@RequestBody CatalogosCotizadorDTO costosAdicionales, Model model) {
		UsuarioAplicativo usuarioAplicativo;
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
			
			if(costosAdicionales.getCatPorcEstrategia().getEditar()!=null && costosAdicionales.getCatPorcEstrategia().getEditar()==1) {
				catBo.guardarCatValorDefault(costosAdicionales.getCatPorcEstrategia(), usuarioAplicativo);
			}
			if(costosAdicionales.getCatPorcIndirectos().getEditar()!=null && costosAdicionales.getCatPorcIndirectos().getEditar()==1) {
				catBo.guardarCatValorDefault(costosAdicionales.getCatPorcIndirectos(), usuarioAplicativo);
			}
			if(costosAdicionales.getCatValorSpei().getEditar()!=null && costosAdicionales.getCatValorSpei().getEditar()==1) {
				catBo.guardarCatValorDefault(costosAdicionales.getCatValorSpei(), usuarioAplicativo);
			}
			if(costosAdicionales.getCatValorTimbrado().getEditar()!=null && costosAdicionales.getCatValorTimbrado().getEditar()==1) {
				catBo.guardarCatValorDefault(costosAdicionales.getCatValorTimbrado(), usuarioAplicativo);
			}
			if(costosAdicionales.getCatPorcPromotorImss().getEditar()!=null && costosAdicionales.getCatPorcPromotorImss().getEditar()==1) {
				catBo.guardarCatValorDefault(costosAdicionales.getCatPorcPromotorImss(), usuarioAplicativo);
			}
		}
		
		return costosAdicionales;
	}
	
}