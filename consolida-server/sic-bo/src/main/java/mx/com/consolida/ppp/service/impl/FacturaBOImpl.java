package mx.com.consolida.ppp.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.crm.service.impl.DocumentoServiceBO;
import mx.com.consolida.dto.DefinicionDocumentoENUM;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.ppp.CatSerie;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.consolida.entity.ppp.PppNominaFacturaConcepto;
import mx.com.consolida.entity.ppp.PppNominaFacturaDocumento;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.IndEstatusSeriesEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.ppp.dao.interfaz.CatSerieDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaConceptoDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaDocumentoDao;
import mx.com.consolida.ppp.service.interfaz.FacturaBO;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.documento.DefinicionDocumentoDto;
import mx.com.facturacion.factura.CatImpuestoDTO;
import mx.com.facturacion.factura.CatPorcentajeDTO;
import mx.com.facturacion.factura.ConceptoDTO;
import mx.com.facturacion.factura.ConceptoFacturaDTO;
import mx.com.facturacion.factura.FacturaDTO;
import mx.com.facturacion.factura.ImpuestoDTO;
import mx.com.facturacion.factura.TotalDTO;

@Service
public class FacturaBOImpl implements FacturaBO{

	private static final Logger LOGGER = LoggerFactory.getLogger(FacturaBOImpl.class);


	@Autowired
	private PppNominaFacturaDao pppNominaFacturaDao;

	@Autowired
	private PppNominaFacturaConceptoDao pppNominaFacturaConceptoDao;

	@Autowired
	private PppNominaFacturaDocumentoDao pppNominaFacturaDocumentoDao;

	@Autowired
	private DocumentoServiceBO documentoServiceBO;

	@Autowired
	private NominaBO nominaBO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CatSerieDao catSerieDao;



	@Transactional
	public void guardarNominaFactura(FacturaDTO factura , Long idUsuario) {
		PppNominaFactura pppNominaFactura = new PppNominaFactura();
		boolean esGuardarDocComplPago = false;

		if(factura.getIdPPPNominaFactura() != null) {
			pppNominaFactura = pppNominaFacturaDao.read(factura.getIdPPPNominaFactura());
		}
		//Regimen fiscal
		CatGeneral catRegimenFiscal = new CatGeneral();
		catRegimenFiscal.setIdCatGeneral(factura.getRegimenFiscal().getIdCatGeneral());
		pppNominaFactura.setCatRegimenFiscal(catRegimenFiscal);


		//Tipo comprobante
		CatGeneral catTipoComprobante = new CatGeneral();
		catTipoComprobante.setIdCatGeneral(factura.getTipoComprobante().getIdCatGeneral());
		pppNominaFactura.setCatTipoCfdi(catTipoComprobante);


		//Moneda
		CatGeneral catMoneda = new CatGeneral();
		catMoneda.setIdCatGeneral(factura.getMoneda().getIdCatGeneral());
		pppNominaFactura.setCatMoneda(catMoneda);

		if(factura.getSerie()==null || factura.getSerie().getClave()==null) {
			CatSerie serie = new CatSerie();
			Long idSerie = validaSerie(factura.getNomina().getClienteDto().getIdCliente(), factura.getTipoComprobante().getIdCatGeneral() );
			serie.setIdCatSerie(idSerie);

			pppNominaFactura.setCatSerie(serie);

			pppNominaFactura.setFolio(validarFolio(idSerie));

		}


		if((factura.getMetodoPago()!=null && factura.getMetodoPago().getIdCatGeneral()!=null && factura.getMetodoPago().getIdCatGeneral() >= 1)
				&& (factura.getFormaPago()!=null && factura.getFormaPago().getIdCatGeneral()!=null && factura.getFormaPago().getIdCatGeneral() >= 1)) {
			//Metodo de pago
			CatGeneral catMetodoPago = new CatGeneral();
			catMetodoPago.setIdCatGeneral(factura.getMetodoPago().getIdCatGeneral());
			pppNominaFactura.setCatMetodoPago(catMetodoPago);

			//Forma de pago
			CatGeneral catFormaPago = new CatGeneral();
			catFormaPago.setIdCatGeneral(factura.getFormaPago().getIdCatGeneral());
			pppNominaFactura.setCatFormaPago(catFormaPago);

		}

		if(factura.getMontoComprobantePago() !=null &&
				(factura.getDocumentoNuevo()!=null && factura.getDocumentoNuevo().getArchivo()!=null && factura.getDocumentoNuevo().getMimeType()!=null)) {

			pppNominaFactura.setMontoComprobantePago(factura.getMontoComprobantePago());
			esGuardarDocComplPago = true;

		}else if(factura.getMontoComprobantePago() !=null) {

			pppNominaFactura.setMontoComprobantePago(factura.getMontoComprobantePago());

		}

		//CatUso de CFDI
		CatGeneral catUsoCFDI = new CatGeneral();
		catUsoCFDI.setIdCatGeneral(factura.getUsoCFDI().getIdCatGeneral());
		pppNominaFactura.setCatUsoCfdi(catUsoCFDI);

		pppNominaFactura.setSubTotal(factura.getTotales().getSubtotal());
		pppNominaFactura.setTotal(factura.getTotales().getTotal());
		pppNominaFactura.setTotalIvaTrasladado16(factura.getTotales().getSumaImpuestos());

		// Nuevos totales para la generacion de reportes
		pppNominaFactura.setIvaComercial(factura.getTotales().getIvaComercial());
		pppNominaFactura.setHonorario(factura.getTotales().getHonorario());
		pppNominaFactura.setMontoTotalHonorario(factura.getTotales().getMontoTotalHonorario());
		pppNominaFactura.setMontoTotalColaboradoresPPP(factura.getTotales().getMontoTotalColaboradoresPPP());

		PppNomina pppNomina = new PppNomina();
		if(factura.getNomina()!=null && factura.getNomina().getIdNomina()!=null) {
			pppNomina.setIdPppNomina(factura.getNomina().getIdNomina());
		}else {
			pppNomina.setIdPppNomina(factura.getIdPPPNomina());
		}
		pppNominaFactura.setNominaCliente(pppNomina);

		// guarda documento de pago proveniente del paso 5
		if(esGuardarDocComplPago) {
			guardarDocComplementoPagoNominaFactura(factura.getIdPPPNomina(), factura.getIdPPPNominaFactura(), factura.getDocumentoNuevo(), idUsuario, factura.getIdCMS());
		}


		if(factura.getIdPPPNominaFactura() != null) {
			pppNominaFactura.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
			pppNominaFactura.setIdPppNominaFactura(factura.getIdPPPNominaFactura());

			pppNominaFactura.setUsuarioModificacion(new Usuario(idUsuario));
			pppNominaFactura.setFechaModificacion(new Date());

			pppNominaFacturaDao.update(pppNominaFactura);

		}else {

			pppNominaFactura.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
			pppNominaFactura.setUsuarioAlta(new Usuario(idUsuario));
			pppNominaFactura.setFechaAlta(new Date());

			pppNominaFacturaDao.create(pppNominaFactura);

		}

		factura.setIdFactura(pppNominaFactura.getIdPppNominaFactura());

		if(factura.getConcepto() != null) {
			guardarConceptNomina(factura,pppNominaFactura,idUsuario);
		}


	     //cambia el estua de la nomina a FACTURA GENERADA
		if (pppNominaFactura!=null
				&& (pppNominaFactura.getCatMetodoPago()!=null && pppNominaFactura.getCatMetodoPago().getIdCatGeneral()!=null && pppNominaFactura.getCatMetodoPago().getIdCatGeneral() >= 1)
				&& (pppNominaFactura.getCatFormaPago()!=null && pppNominaFactura.getCatFormaPago().getIdCatGeneral()!=null && pppNominaFactura.getCatFormaPago().getIdCatGeneral() >= 1)
				&& pppNominaFactura.getMontoComprobantePago() !=null) {

			List<Long> lista = pppNominaFacturaDocumentoDao.getidPppNominaFacturaDocumento(factura.getIdPPPNominaFactura());

			if(lista!=null && !lista.isEmpty() && lista.size() == 3) {

				if (!nominaBO.cambiaEstatusNomina(factura.getIdPPPNomina(), null, NominaEstatusEnum.FACTURA_GENERADA, idUsuario)) {
					LOGGER.error("Ocurrio un error en guardarNominaFactura - actualizar estatus de la nomina factura con id {} ", factura.getIdPPPNominaFactura());
				}
			}
		}
	}

	@Transactional
	public void eliminarConceptoFactura(ConceptoFacturaDTO conceptoFactura) {
		pppNominaFacturaConceptoDao.delete(conceptoFactura.getIdConceptoFactura());

		if(conceptoFactura.getIdPPPNominaFactura() != null) {
			PppNominaFactura pppNominaFactura = pppNominaFacturaDao.read(conceptoFactura.getIdPPPNominaFactura());

			pppNominaFactura.setSubTotal(new BigDecimal(0));
			pppNominaFactura.setTotal(new BigDecimal(0));
			pppNominaFactura.setTotalIvaTrasladado16(new BigDecimal(0));

			pppNominaFactura.setIvaComercial(new BigDecimal(0));
			pppNominaFactura.setHonorario(new BigDecimal(0));
			pppNominaFactura.setMontoTotalHonorario(new BigDecimal(0));
			pppNominaFactura.setMontoTotalColaboradoresPPP(new BigDecimal(0));

			pppNominaFacturaDao.update(pppNominaFactura);
		}
	}


	@Transactional
	public void guardarConceptNomina(FacturaDTO factura,PppNominaFactura pppNominaFactura, Long idUsuario) {
		ConceptoDTO conceptoDTO = factura.getConcepto();

		PppNominaFacturaConcepto concepto = new PppNominaFacturaConcepto();

		if(conceptoDTO.getIdConcepto() != null) {
			concepto = pppNominaFacturaConceptoDao.read(conceptoDTO.getIdConcepto());
		}

		factura.getConceptoFacturacion();
		concepto.setCantidad(conceptoDTO.getCantidad() == null ? new BigDecimal(1) : conceptoDTO.getCantidad());

		CatGeneral unidadMedida = new CatGeneral();
		unidadMedida.setIdCatGeneral(conceptoDTO.getUnidad().getIdCatGeneral());
		concepto.setUnidadMedida(unidadMedida);

		concepto.setPrecioUnitario(conceptoDTO.getPrecioUnitario());
		concepto.setImporte(conceptoDTO.getImporte());

		//Comentado temporalmente

		if(conceptoDTO.getDescripcionConceptoAdicional() == null || "".equals(conceptoDTO.getDescripcionConceptoAdicional().trim())) {

			conceptoDTO.setDescripcionConceptoAdicional(null);

    	}else {

    		concepto.setDescripcionConceptoAdicional(conceptoDTO.getDescripcionConceptoAdicional());

    	}

		concepto.setDescripcionConcepto(conceptoDTO.getDescripcionConcepto());
		concepto.setCodigoSat(conceptoDTO.getCodigoSat());
		concepto.setDescripcionSat(conceptoDTO.getDescripcionSat());

		concepto.setPppNominaFactura(pppNominaFactura);

		concepto.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());

		concepto.setIvaTrasladado16(conceptoDTO.getIvaTrasladado16());
		concepto.setIvaTrasladado16Monto(conceptoDTO.getIvaTrasladado16Monto());

		if(conceptoDTO.getIdConcepto() != null) {

			concepto.setUsuarioModificacion(new Usuario(idUsuario));
			concepto.setFechaModificacion(new Date());
			concepto.setIdPppNominaFacturaConcepto(conceptoDTO.getIdConcepto());

			pppNominaFacturaConceptoDao.update(concepto);

		}else {

			concepto.setUsuarioAlta(new Usuario(idUsuario));
			concepto.setFechaAlta(new Date());
			pppNominaFacturaConceptoDao.create(concepto);
		}


	}


	@Transactional
	public FacturaDTO obtenerFacturaByIdNomina(Long idNominaPPP) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT ");
			sb.append(" 	pnf.id_ppp_nomina_factura, ");
			sb.append(" 	pnf.id_ppp_nomina, ");
			sb.append(" 	pnf.sub_total, ");
			sb.append(" 	pnf.total_iva_trasladado_16, ");
			sb.append(" 	pnf.total, ");
			sb.append(" 	pnf.uuid, ");
			sb.append(" 	pnf.certificado_emisor, ");
			sb.append(" 	pnf.fecha_hora_emision, ");
			sb.append(" 	pnf.fecha_hora_certificacion, ");
			sb.append(" 	pnf.id_prestadora_servicio,	 ");
			sb.append(" 	pnf.id_cat_serie, ");
			sb.append(" 	pnf.folio, ");
			sb.append(" 	pnf.id_cat_metodo_pago, ");
			sb.append(" 	pnf.monto_comprobante_pago, ");
			sb.append(" 	mp.clave as clave_mp, ");
			sb.append(" 	mp.descripcion as desc_mp, ");
			sb.append(" 	pnf.id_cat_forma_pago, ");
			sb.append(" 	fp.clave  as clave_fp, ");
			sb.append(" 	fp.descripcion  as desc_fp, ");
			sb.append(" 	pnf.id_cat_tipo_cfdi, ");
			sb.append(" 	tcfdi.clave  as clave_tcfdi, ");
			sb.append(" 	tcfdi.descripcion  as desc_tcfdi, ");
			sb.append(" 	pnf.id_cat_regimen_fiscal, ");
			sb.append(" 	rf.clave   as clave_rf , ");
			sb.append(" 	rf.descripcion as desc_rf, ");
			sb.append(" 	pnf.id_cat_moneda, ");
			sb.append(" 	m.clave  as clave_mon, ");
			sb.append(" 	m.descripcion  as desc_mon, ");
			sb.append(" 	pnf.id_cat_uso_cfdi, ");
			sb.append(" 	uc.clave  as clave_ucfdi, ");
			sb.append(" 	uc.descripcion  as desc_ucfdi, ");
			sb.append(" 	pnf.ind_estatus , ");
			sb.append(" 	ps.id_prestadora_servicio , ");
			sb.append(" 	c2.id_cliente , ");
			sb.append(" 	c2.rfc , ");
			sb.append(" 	c2.apellido_paterno , ");
			sb.append(" 	c2.apellido_materno , ");
			sb.append(" 	c2.nombre , ");
			sb.append(" 	c2.razon_social ");

			sb.append(" 	,cs.id_cat_serie  ");
			sb.append(" 	,cs.id_celula ");
			sb.append(" 	,cs.id_tipo_comprobante ");
			sb.append(" 	,cs.nombre_serie  ");
			sb.append(" 	,cs.folio_inicial  ");

			sb.append(" 	,(select count(*) from sin.ppp_nomina_factura_documento where id_ppp_nomina_factura = pnf.id_ppp_nomina_factura and id_definicion_documento in (63, 64, 65) and ind_estatus = 1) as total_documentos ");
			sb.append(" 	,( select id_CMS from sin.ppp_nomina_factura_documento ");
			sb.append(" 		where id_ppp_nomina_factura = pnf.id_ppp_nomina_factura ");
			sb.append(" 		and id_definicion_documento = 65 and ind_estatus = 1) as id_cms_comprobante_pago_nominista ");
			sb.append(" 	,( select nombre_archivo from sin.ppp_nomina_factura_documento ");
			sb.append(" 		where id_ppp_nomina_factura = pnf.id_ppp_nomina_factura ");
			sb.append(" 		and id_definicion_documento = 65 and ind_estatus = 1) as nombre_archivo_comprobante_pago_nominista ");
			sb.append(" 	,( select id_CMS from sin.ppp_nomina_factura_documento ");
			sb.append(" 		where id_ppp_nomina_factura = pnf.id_ppp_nomina_factura ");
			sb.append(" 		and id_definicion_documento = 64 and ind_estatus = 1) as id_cms_xml_nomina_factura ");
			sb.append(" 	,( select id_CMS from sin.ppp_nomina_factura_documento ");
			sb.append(" 		where id_ppp_nomina_factura = pnf.id_ppp_nomina_factura ");
			sb.append(" 		and id_definicion_documento = 63 and ind_estatus = 1) as id_cms_pdf_nomina_factura, ");
			sb.append(" ps.logo ");
			sb.append(" FROM ");
			sb.append(" 	ppp_nomima_factura pnf ");
			sb.append(" left join cat_general mp on ");
			sb.append(" 	mp.id_cat_general = pnf.id_cat_metodo_pago ");
			sb.append(" left join cat_general fp on ");
			sb.append(" 	fp.id_cat_general = pnf.id_cat_forma_pago ");
			sb.append(" inner join cat_general tcfdi on ");
			sb.append(" 	tcfdi.id_cat_general = pnf.id_cat_tipo_cfdi ");
			sb.append(" LEFT join cat_general rf on ");
			sb.append(" 	rf.id_cat_general = pnf.id_cat_regimen_fiscal ");
			sb.append(" inner join cat_general m on ");
			sb.append(" 	m.id_cat_general = pnf.id_cat_moneda ");
			sb.append(" LEFT join cat_general uc on ");
			sb.append(" 	uc.id_cat_general = pnf.id_cat_uso_cfdi ");
			sb.append(" inner join ppp_nomina pn on ");
			sb.append(" 	pn.id_ppp_nomina = pnf.id_ppp_nomina ");
			sb.append(" inner join nomina_cliente nc on ");
			sb.append(" 	nc.id_nomina_cliente = pn.id_nomina_cliente ");
			sb.append(" inner join cliente c2 on ");
			sb.append(" 	c2.id_cliente = nc.id_cliente ");
			sb.append(" left join prestadora_servicio ps on ");
			sb.append(" 	nc.id_prestadora_servicio = ps.id_prestadora_servicio ");
			sb.append(" left join cat_serie cs on pnf.id_cat_Serie = cs.id_cat_serie ");
			sb.append(" WHERE ");
			sb.append(" 	pnf.id_ppp_nomina =  " + idNominaPPP);

			@SuppressWarnings("unchecked")
			List<FacturaDTO> facturasList = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	FacturaDTO facturaDTO = new FacturaDTO();
		        	facturaDTO.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
		        	facturaDTO.setIdCliente(rs.getLong("id_cliente"));
		        	facturaDTO.setIdCMS(rs.getLong("id_cms_comprobante_pago_nominista"));
		        	facturaDTO.setIdCmsPdfFactura(rs.getLong("id_cms_pdf_nomina_factura"));
		        	facturaDTO.setIdCmsXmlFactura(rs.getLong("id_cms_xml_nomina_factura"));
		        	facturaDTO.setIdPPPNominaFactura(rs.getLong("id_ppp_nomina_factura"));
		      		facturaDTO.setIdPPPNomina(rs.getLong("id_ppp_nomina"));
		      		facturaDTO.setTotalDocumentosRegistrados(rs.getInt("total_documentos"));
		      		facturaDTO.setMontoComprobantePago(rs.getBigDecimal("monto_comprobante_pago"));

		        	CatGeneralDto catRegimenFiscal = new CatGeneralDto();
		      		catRegimenFiscal.setIdCatGeneral(rs.getLong("id_cat_regimen_fiscal"));
		      		catRegimenFiscal.setClave(rs.getString("clave_rf"));
		      		catRegimenFiscal.setDescripcion(rs.getString("desc_rf"));
		      		facturaDTO.setRegimenFiscal(catRegimenFiscal);

		      		//Tipo comprobante
		      		CatGeneralDto tipoComprobante = new CatGeneralDto();
		      		tipoComprobante.setIdCatGeneral(rs.getLong("id_cat_tipo_cfdi"));
		      		tipoComprobante.setClave(rs.getString("clave_tcfdi"));
		      		tipoComprobante.setDescripcion(rs.getString("desc_tcfdi"));
		      		facturaDTO.setTipoComprobante(tipoComprobante);


		      		//Moneda
		      		CatGeneralDto catMoneda = new CatGeneralDto();
		      		catMoneda.setIdCatGeneral(rs.getLong("id_cat_moneda"));
		      		catMoneda.setClave(rs.getString("clave_mon"));
		      		catMoneda.setDescripcion(rs.getString("desc_mon"));
		      		facturaDTO.setMoneda(catMoneda);


		      		//Metodo de pago
		      		CatGeneralDto catMetodoPago = new CatGeneralDto();
		      		catMetodoPago.setIdCatGeneral(rs.getLong("id_cat_metodo_pago"));
		      		catMetodoPago.setClave(rs.getString("clave_mp"));
		      		catMetodoPago.setDescripcion(rs.getString("desc_mp"));
		      		facturaDTO.setMetodoPago(catMetodoPago);


		      		//Forma de pago
		      		CatGeneralDto catFormaPago = new CatGeneralDto();
		      		catFormaPago.setIdCatGeneral(rs.getLong("id_cat_forma_pago"));
		      		catFormaPago.setClave(rs.getString("clave_fp"));
		      		catFormaPago.setDescripcion(rs.getString("desc_fp"));
		      		facturaDTO.setFormaPago(catFormaPago);


		      		//Uso del cfdi
		      		CatGeneralDto catUsoCfdi = new CatGeneralDto();
		      		catUsoCfdi.setIdCatGeneral(rs.getLong("id_cat_uso_cfdi"));
		      		catUsoCfdi.setClave(rs.getString("clave_ucfdi"));
		      		catUsoCfdi.setDescripcion(rs.getString("desc_ucfdi"));
		      		facturaDTO.setUsoCFDI(catUsoCfdi);

		    		// documento comprobante de pago
		      		DocumentoDTO nuevoDoc = new DocumentoDTO();
		      		nuevoDoc.setNombreArchivo(rs.getString("nombre_archivo_comprobante_pago_nominista"));
		      		facturaDTO.setDocumentoNuevo(nuevoDoc);

		      		TotalDTO total = new TotalDTO();
		      		total.setSubtotal(rs.getBigDecimal("sub_total")!= null?rs.getBigDecimal("sub_total").setScale(2): new BigDecimal(0));
		      		total.setSumaImpuestos(rs.getBigDecimal("total_iva_trasladado_16") != null? rs.getBigDecimal("total_iva_trasladado_16"): new BigDecimal(0));
		      		total.setTotal(rs.getBigDecimal("total")!= null?rs.getBigDecimal("total"): new BigDecimal(0));

		      		facturaDTO.setTotales(total);
		      		facturaDTO.setLogoPrestadoraServicioString(rs.getString("logo"));

		      		if(facturaDTO.getLogoPrestadoraServicioString() != null) {
		      		String logo = facturaDTO.getLogoPrestadoraServicioString().replace("data:image/jpeg;base64,", "");
		      		facturaDTO.setLogoPrestadoraServicioString(logo);
		      		}

		      		CatGeneralDto serie = new CatGeneralDto();
		      		serie.setIdCatGeneral(rs.getLong("id_cat_serie"));
		      		serie.setClave(rs.getString("nombre_serie"));
		      		serie.setDescripcion(String.valueOf(rs.getLong("folio_inicial")));
		      	    facturaDTO.setSerie(serie);
		      		facturaDTO.setFolio(rs.getLong("folio"));

		          return facturaDTO;
			   }
			   });

			FacturaDTO facturaDTO = null;

			if(facturasList != null && !facturasList.isEmpty()) {
				facturaDTO = new FacturaDTO();
				facturaDTO = facturasList.get(0);
			}

			return facturaDTO;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerFacturaByIdNomina ", e);
			return null;
		}


	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<ConceptoDTO> obtenerConceptosFacturaByIdPPPNominaFactura(Long IdPPPNominaFactura) {

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT pnf.id_ppp_nomina_factura_concepto, ");
		sb.append(" pnf.id_ppp_nomina_factura, ");
		sb.append(" pnf.cantidad, ");
		sb.append(" pnf.id_unidad_medida,  ");
		sb.append(" uni.clave as clave_unidad, ");
		sb.append(" uni.descripcion  as descripcion_unidad, ");
		sb.append(" pnf.precio_unitario, ");
		sb.append(" pnf.importe, ");
		sb.append(" pnf.codigo_sat, ");
		sb.append(" pnf.descripcion_sat, ");
		sb.append(" pnf.descripcion_concepto,  ");
		sb.append(" pnf.descripcion_concepto_adicional,  ");
		sb.append(" pnf.iva_trasladado_16, ");
		sb.append(" pnf.iva_trasladado_16_monto, ");
		sb.append(" pnf.iva_retenido_6,  ");
		sb.append(" pnf.iva_retenido_6_monto, ");
		sb.append(" pnf.ind_estatus  ");
		sb.append(" FROM ppp_nomina_factura_concepto pnf ");
		sb.append(" inner join cat_general uni on ");
		sb.append(" 	uni.id_cat_general = pnf.id_unidad_medida ");
		sb.append(" WHERE id_ppp_nomina_factura = " + IdPPPNominaFactura);

		return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {


	      		ConceptoDTO conceptoDTO = new ConceptoDTO();
	      		conceptoDTO.setIdConcepto(rs.getLong("id_ppp_nomina_factura_concepto"));
	      		conceptoDTO.setCantidad(rs.getBigDecimal("cantidad"));

	      		CatGeneralDto unidad = new CatGeneralDto();
	      		unidad.setIdCatGeneral(rs.getLong("id_unidad_medida"));
	      		unidad.setClave(rs.getString("clave_unidad"));
	      		unidad.setDescripcion(rs.getString("descripcion_unidad"));
	      		conceptoDTO.setUnidad(unidad);

	      		conceptoDTO.setPrecioUnitario(rs.getBigDecimal("precio_unitario") != null ?rs.getBigDecimal("precio_unitario").setScale(2): new BigDecimal(0));
	      		conceptoDTO.setImporte(rs.getBigDecimal("importe") != null ?rs.getBigDecimal("importe").setScale(2): new BigDecimal(0));

	      		conceptoDTO.setCodigoSat(rs.getString("codigo_sat"));
	      		conceptoDTO.setDescripcionSat(rs.getString("descripcion_sat"));
	      		conceptoDTO.setDescripcionConcepto(rs.getString("descripcion_concepto"));
	      		conceptoDTO.setDescripcionConceptoAdicional(rs.getString("descripcion_concepto_adicional"));

	      		conceptoDTO.setIvaTrasladado16(rs.getString("iva_trasladado_16"));
	      		conceptoDTO.setIvaTrasladado16Monto(rs.getBigDecimal("iva_trasladado_16_monto")!= null?rs.getBigDecimal("iva_trasladado_16_monto").setScale(2):new BigDecimal(0));

	      		conceptoDTO.setIvaRetenido6(rs.getString("iva_retenido_6"));
	      		conceptoDTO.setIvaRetenido6Monto(rs.getBigDecimal("iva_retenido_6_monto")!= null?rs.getBigDecimal("iva_retenido_6_monto").setScale(2):new BigDecimal(0));

	      		//Se agrega el impuesto del iva transladado que es el que generamos con las facturas de AURA
	      		List<ImpuestoDTO> impuestosDtos = new  ArrayList<ImpuestoDTO>();
	      		ImpuestoDTO impuestoDTO = new ImpuestoDTO();
	      		impuestoDTO.setIdImpuesto(1);
	      		CatImpuestoDTO tipo = new CatImpuestoDTO();
	    		tipo.setClave("002");
	    		tipo.setDescripcion("IVA Trasladado 16%");

	    		CatPorcentajeDTO porcentajeImpuesto = new CatPorcentajeDTO();
	    		porcentajeImpuesto.setPorcentaje(new BigDecimal("0.160000"));
	    		tipo.setPorcentajeImpuesto(porcentajeImpuesto);


	      		impuestoDTO.setTipo(tipo);
	      		impuestoDTO.setTotalImpuesto(conceptoDTO.getIvaTrasladado16Monto()!=null?conceptoDTO.getIvaTrasladado16Monto().setScale(2): new BigDecimal(0));

	      		impuestosDtos.add(impuestoDTO);

	      		conceptoDTO.setImpuestos(impuestosDtos);

	          return conceptoDTO;
		   }
		   });
	}

	private void guardarDocComplementoPagoNominaFactura(Long idPppNomina, Long idPppNominaFactura,DocumentoDTO documentoNuevo, Long idUsuarioAplicativo, Long idCms) {

		Map<String,String> contextos = new HashMap<String,String>();
		DocumentoCSMDto documento = new DocumentoCSMDto();
		Long idCMS = null;

		try {

			if(idCms !=null && idCms >= 1) {
				PppNominaFacturaDocumento comprobante = pppNominaFacturaDocumentoDao.getUltimoDocComprobantePagoByIdCms(idCms);

				comprobante.setIndEstatus(IndEstatusEnum.INACTIVO.getEstatus());
				comprobante.setFechaModificacion(new Date());
				comprobante.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));

			    pppNominaFacturaDocumentoDao.update(comprobante);
			}

			contextos.put("1","nomina");
			contextos.put("2",String.valueOf(idPppNomina));
			contextos.put("3","nominaFactura");
			contextos.put("4",String.valueOf(idPppNominaFactura));
			contextos.put("5", String.valueOf(DefinicionDocumentoENUM.COMPROBANTE_PAGO_NOMINISTA.getIdDefinicionDocumento()));

			documento.setDocumentoNuevo(documentoNuevo);
			documento.setDefinicion(new DefinicionDocumentoDto(DefinicionDocumentoENUM.COMPROBANTE_PAGO_NOMINISTA.getIdDefinicionDocumento()));
		    idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);

		    PppNominaFacturaDocumento pppNominaDocumento = new PppNominaFacturaDocumento();
		    pppNominaDocumento.setPppNominaFactura(new PppNominaFactura(idPppNominaFactura));
		    pppNominaDocumento.setDefinicionDocumento(new DefinicionDocumento(DefinicionDocumentoENUM.COMPROBANTE_PAGO_NOMINISTA.getIdDefinicionDocumento()));
		    pppNominaDocumento.setIdCMS(idCMS);
		    pppNominaDocumento.setNombreArchivo(documentoNuevo.getNombreArchivo());
		    pppNominaDocumento.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
		    pppNominaDocumento.setFechaAlta(new Date());
		    pppNominaDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());

		    pppNominaFacturaDocumentoDao.create(pppNominaDocumento);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocComplementoPagoNominaFactura");
		}
	}

	@Transactional
	public Boolean guardarNominaFacturaFlujoAlterno(FacturaDTO factura , Long idUsuario) {

		try {

			PppNominaFactura pppNominaFactura = new PppNominaFactura();

			if(factura.getIdPPPNominaFactura() != null) {
				pppNominaFactura = pppNominaFacturaDao.read(factura.getIdPPPNominaFactura());
			}

			if(factura.getTotales() != null) {
				pppNominaFactura.setSubTotal(factura.getTotales().getSubtotal());
				pppNominaFactura.setTotal(factura.getTotales().getTotal());
				pppNominaFactura.setTotalIvaTrasladado16(factura.getTotales().getSumaImpuestos() );
			}

			pppNominaFactura.setCatTipoCfdi(new CatGeneral(329L)); // TIPO: INGRESO
			pppNominaFactura.setCatMoneda(new CatGeneral(2804L)); // MONEDA: PESO MEXICANO




			PppNomina pppNomina = new PppNomina();
			if(factura.getNomina()!=null && factura.getNomina().getIdNomina()!=null) {
				pppNomina.setIdPppNomina(factura.getNomina().getIdNomina());
			}else {
				pppNomina.setIdPppNomina(factura.getIdPPPNomina());
			}
			pppNominaFactura.setNominaCliente(pppNomina);

			if(factura.getIdPPPNominaFactura() != null) {
				pppNominaFactura.setMontoComprobantePago(factura.getMontoComprobantePago());
				pppNominaFactura.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
				pppNominaFactura.setIdPppNominaFactura(pppNominaFactura.getIdPppNominaFactura());
				pppNominaFactura.setUsuarioModificacion(new Usuario(idUsuario));
				pppNominaFactura.setFechaModificacion(new Date());

				pppNominaFacturaDao.update(pppNominaFactura);

			}else {

				pppNominaFactura.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
				pppNominaFactura.setUsuarioAlta(new Usuario(idUsuario));
				pppNominaFactura.setFechaAlta(new Date());

				pppNominaFacturaDao.create(pppNominaFactura);

			}

			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarNominaFacturaFromComplemento ", e);
			return Boolean.FALSE;
		}
	}

	@Transactional(readOnly = true)
	public Boolean existeInfoPasoCinco(Long idPppNomina) {
		try {

			PppNominaFactura pppNomina = pppNominaFacturaDao.getPppNominaFactByIdPppNomina(idPppNomina);

			if(pppNomina!= null
					&& (pppNomina.getCatMetodoPago() != null && pppNomina.getCatMetodoPago().getIdCatGeneral()!=null)
					&& (pppNomina.getCatFormaPago()!= null && pppNomina.getCatFormaPago().getIdCatGeneral()!=null)) {

				return Boolean.TRUE;

			}else {
				LOGGER.info("No existe info para el idPppNomina {}", idPppNomina);
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", e);
			return Boolean.FALSE;
		}
	}

	@Transactional(readOnly = true)
	public Boolean updateCoMpelmentoPasoCinco(Long idPppNomina) {
		try {

			PppNominaFactura pppNomina = pppNominaFacturaDao.read(idPppNomina);

			if(pppNomina!= null
					&& (pppNomina.getCatMetodoPago() != null && pppNomina.getCatMetodoPago().getIdCatGeneral()!=null)
					&& (pppNomina.getCatFormaPago()!= null && pppNomina.getCatFormaPago().getIdCatGeneral()!=null)) {

				return Boolean.TRUE;

			}else {
				LOGGER.info("No existe info para el idPppNomina {}", idPppNomina);
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeInfoPasoCinco ", e);
			return Boolean.FALSE;
		}
	}

	@Transactional
	public Boolean updateDatosPasoCinco(Long idPppNomina, Long idUsuarioAplicativo) {

		try {

			PppNominaFactura pppNominaFactura = pppNominaFacturaDao.getPppNominaFactByIdPppNomina(idPppNomina);

			if(pppNominaFactura!= null && pppNominaFactura.getIdPppNominaFactura()!=null) {

				pppNominaFactura.setCatMetodoPago(null);
				pppNominaFactura.setCatFormaPago(null);
				pppNominaFactura.setMontoComprobantePago(null);
				pppNominaFactura.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				pppNominaFactura.setFechaModificacion(new Date());
				pppNominaFacturaDao.update(pppNominaFactura);

				if(!pppNominaFacturaDocumentoDao.deleteDocFinanciamientoByIdPppNomina(idPppNomina)) {
					LOGGER.error("Ocurrio un error en  updateDatosPasoCinco para idPppNomina{}", idPppNomina);
					return Boolean.FALSE;
				}

				return Boolean.TRUE;

			}else {
				LOGGER.info("No existe info para el idPppNomina {}", idPppNomina);
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en updateDatosPasoCinco ", e);
			return Boolean.FALSE;
		}
	}



private Long validaSerie(Long idCliente, Long idTipoFactura)  {
	Long idSerieNoVigente = 0L;
	Long idSeriePorIniciar = 0L;

	Long idSerie = obtenerIdSerie(idCliente, idTipoFactura, Boolean.TRUE, IndEstatusSeriesEnum.ACTIVA.getEstatus());

	if (idSerie != null) {
		return idSerie;
	} else {
		idSeriePorIniciar = obtenerIdSerie(idCliente, idTipoFactura, Boolean.FALSE,
				IndEstatusSeriesEnum.POR_INICIAR.getEstatus());

		if (idSeriePorIniciar != null) {
			// Serie por iniciar vigente
			idSerieNoVigente = obtenerIdSerie(idCliente, idTipoFactura, Boolean.FALSE,
					IndEstatusSeriesEnum.ACTIVA.getEstatus());

			if (idSeriePorIniciar != null) {
				if (idSerieNoVigente != null) {
					caducarSerie(idSerieNoVigente);
				}
				activarSerie(idSeriePorIniciar);
				return idSeriePorIniciar;

			} else if (idSerieNoVigente != null) {
				return idSerieNoVigente;
			}
		}
	}
	return null;

}

private void activarSerie(Long idSerie) {
	CatSerie catserie = new CatSerie();
	catserie  = catSerieDao.read(idSerie);
	catserie.setIdCatSerie(idSerie);
	catserie.setIdEstatusSerie(1L);
	catSerieDao.update(catserie);

}

private void caducarSerie(Long idSerie) {
	CatSerie catserie = new CatSerie();
	catserie  = catSerieDao.read(idSerie);
	catserie.setIdEstatusSerie(2L);// 2 Serie caducada


	catSerieDao.update(catserie);

}

private Long obtenerIdSerie(Long idCliente, Long idTipoFactura,Boolean vigente, Long estatus) {

	try {

		StringBuilder sb = new StringBuilder();
		sb.append(" select cs.id_cat_serie from sin.celula c ");
		sb.append(" 	join sin.cat_serie cs on c.id_celula = cs.id_celula ");
		sb.append(" 	join sin.celula_cliente cc on c.id_celula = cc.id_celula ");
		sb.append(" 	join sin.cliente cl on cl.id_cliente = cc.id_cliente ");
		sb.append(" 	where id_tipo_comprobante =   ");
		sb.append(idTipoFactura);
		sb.append(" 	and cl.id_cliente= ");
		sb.append(idCliente);
		sb.append(" 	and cs.id_estatus=  ");
		sb.append( estatus);
		sb.append( " and cs.ind_estatus='1'  ");

		if (vigente) {
			sb.append(" and DATE(cs.fecha_fin_vigencia)>= (SELECT CURDATE())");


		}
		if (estatus == IndEstatusSeriesEnum.POR_INICIAR.getEstatus()) {
			sb.append(" and DATE(cs.fecha_inicio_vigencia)<= (SELECT CURDATE())");
			sb.append(" and DATE(cs.fecha_fin_vigencia)>= (SELECT CURDATE())");
		}


		return jdbcTemplate.queryForObject(sb.toString(),Long.class);

	} catch (Exception e) {
		LOGGER.error("Ocurrio un error en obtenerCatSerie ", e);
		return null;
	}

}

private Long obtenerIdSeriePorIniciar(Long idCliente, Long idTipoFactura) {

	try {

		StringBuilder sb = new StringBuilder();
		sb.append(" select cs.id_cat_serie from sin.celula c ");
		sb.append(" 	join sin.cat_serie cs on c.id_celula = cs.id_celula ");
		sb.append(" 	join sin.celula_cliente cc on c.id_celula = cc.id_celula ");
		sb.append(" 	join sin.cliente cl on cl.id_cliente = cc.id_cliente ");
		sb.append(" 	where id_tipo_comprobante =   ");
		sb.append(idTipoFactura);
		sb.append(" 	and cl.id_cliente= ");
		sb.append(idCliente);
		sb.append(" 	and cs.id_estatus= 0 ");
		sb.append(" and DATE(cs.fecha_inicio_vigencia)<= (SELECT CURDATE())");

		return jdbcTemplate.queryForObject(sb.toString(),Long.class);




	} catch (Exception e) {
		LOGGER.error("Ocurrio un error en obtenerCatSerie ", e);
		return null;
	}

}


private Long inicioSerie(Long idSerie) {

	try {

		StringBuilder sb = new StringBuilder();
		sb.append(" select ifnull(folio_inicial,0) folio  ");
		sb.append(" 	 from sin.cat_serie cs ");
		sb.append(" 	where id_cat_serie =");
		sb.append(idSerie);

		return jdbcTemplate.queryForObject(sb.toString(),Long.class);

	} catch (Exception e) {
		LOGGER.error("Ocurrio un error en obtenerCatSerie ", e);
		return null;
	}

}

private Long validarFolio(Long idSerie) {
	Long folioInicialSerie = 0L;
	Long folio = obtnerFolioSerie(idSerie);
	if (folio !=null && folio !=1L) {
		return folio;
	}else {
		folioInicialSerie=obtnerFolioSerie(idSerie);
		if (folioInicialSerie>1) {
			return folioInicialSerie;
		}else {
			return folio;
		}
	}


}

private  Long obtnerFolioSerie(Long id_serie) {
	try {

		StringBuilder sb = new StringBuilder();
		sb.append("   Select  ifnull( max(folio),0)+1 folio ");
		sb.append("from sin.ppp_nomima_factura  ");
		sb.append(" where id_cat_serie = ");
		sb.append(id_serie);

		return jdbcTemplate.queryForObject(sb.toString(),Long.class);



	}catch (Exception e) {
		LOGGER.error("Ocurrio un error en obtenerFacturaByIdNomina ", e);
		return null;
	}

}
}
