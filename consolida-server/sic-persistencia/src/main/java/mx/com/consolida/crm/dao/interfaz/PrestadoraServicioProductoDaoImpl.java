package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.CelulaPrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.CatProducto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;
import mx.com.consolida.generico.CatEstatusEnum;

@Repository
public class PrestadoraServicioProductoDaoImpl extends GenericDAO<PrestadoraServicioProducto, Long> implements PrestadoraServicioProductoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioProductoDaoImpl.class);


	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioProducto obtenerPrestadoraProductoXIdCatProducto(Long idCatProducto, Long idPrestadoraServicio) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select prod from PrestadoraServicioProducto prod where prod.catProducto.idCatProducto = :idCatProducto "
					+ " and prod.prestadoraServicio.idPrestadoraServicio = :idPrestadoraServicio");
			query.setParameter("idCatProducto", idCatProducto);
			query.setParameter("idPrestadoraServicio", idPrestadoraServicio);

			
			
			return (PrestadoraServicioProducto) query.uniqueResult();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeNomistaEnCliente", e);
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<CatProductoDto> obtenerProductosXIdPrestadora(Long idPrestadoraServicio) {
		List<CatProductoDto> listCatProducto =  new ArrayList<CatProductoDto>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select id_producto ");
			sb.append(" from prestadora_servicio_producto");
			sb.append(" where id_prestadora_servicio = ?");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServicio}, new RowMapper() {
				public CatProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatProductoDto producto = new CatProductoDto();
					producto.setIdCatProducto(rs.getLong("id_producto"));
					
					listCatProducto.add(producto);
					return producto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerProductosXIdPrestadora ", e);
			return Collections.emptyList();
		}
	}


	@Override
	public List<PrestadoraServicioProductoDto> convertirPrestadoraServicioProductoADto(
			List<PrestadoraServicioProducto> prestadoraServicioProducto) {
		List<PrestadoraServicioProductoDto> listProducto = new ArrayList<PrestadoraServicioProductoDto>();
		
		for(PrestadoraServicioProducto psp : prestadoraServicioProducto ) {
			if(psp.getIndEstatus().equals(CatEstatusEnum.ACTIVO.getIdEstatus())) {
			PrestadoraServicioProductoDto producto = new PrestadoraServicioProductoDto();
			producto.setIdPrestadoraServicioProducto(psp.getIdPrestadoraServicioProducto());
			producto.setIdCatGeneral(psp.getCatProductoSat().getIdCatGeneral());
			producto.setClave(psp.getCatProductoSat().getClave());
			producto.setDescripcion(psp.getCatProductoSat().getDescripcion());
			producto.setDescripcionProductoConsolida(psp.getDescripcionProductoConsolida());
			
			listProducto.add(producto);
			}
		}
		
		return listProducto;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioProductoDto> listaPrestadoraServicioProductosByIdCelula(Long idCliente) {
		List<PrestadoraServicioProductoDto> listCatProducto =  new ArrayList<PrestadoraServicioProductoDto>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select ps.id_prestadora_servicio, ps.rfc as rfc_prest_servicio, ps.razon_social, "); 
			sb.append(" psp.id_prestadora_servicio_producto, "); 
			sb.append(" cg.id_cat_general as id_cat_producto_sat, cg.clave, cg.descripcion as desc_producto_sat, psp.descripcion_producto_consolida, "); 
			sb.append(" cel.id_cliente, cel.nombre as nombre_celula "); 
			sb.append(" from sin.prestadora_servicio_producto psp, sin.prestadora_servicio ps, sin.cat_general cg, "); 
			sb.append(" sin.cliente_prestadora_servicio cps, sin.cliente cel  "); 
			sb.append(" where ps.id_prestadora_servicio = psp.id_prestadora_servicio "); 
			sb.append(" and cg.id_cat_general = psp.id_cat_producto_sat "); 
			sb.append(" and cps.id_prestadora_servicio = ps.id_prestadora_servicio "); 
			sb.append(" and cel.id_cliente = cps.id_cliente  "); 
			sb.append(" and psp.ind_estatus = 1 ");
			sb.append(" and cps.ind_estatus = 1 ");
			sb.append(" and cel.id_cliente = ? "); 
			sb.append(" union "); 
			sb.append(" select ps.id_prestadora_servicio, ps.rfc as rfc_prest_servicio, ps.razon_social, "); 
			sb.append(" psp.id_prestadora_servicio_producto, "); 
			sb.append(" cg.id_cat_general as id_cat_producto_sat, cg.clave, cg.descripcion as desc_producto_sat, psp.descripcion_producto_consolida, "); 
			sb.append(" cel.id_cliente, cel.nombre as nombre_celula "); 
			sb.append(" from sin.prestadora_servicio_producto psp, sin.prestadora_servicio ps, sin.cat_general cg, "); 
			sb.append(" sin.cliente_prestadora_servicio cps, sin.cliente cel  "); 
			sb.append(" where ps.id_prestadora_servicio = psp.id_prestadora_servicio "); 
			sb.append(" and cg.id_cat_general = psp.id_cat_producto_sat "); 
			sb.append(" and cps.id_prestadora_servicio_fondo = ps.id_prestadora_servicio "); 
			sb.append(" and cel.id_cliente = cps.id_cliente  "); 
			sb.append(" and psp.ind_estatus = 1 "); 
			sb.append(" and cps.ind_estatus = 1 ");
			sb.append(" and cel.id_cliente =  ? ");
			sb.append(" order by razon_social ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente,idCliente}, new RowMapper() {
				public PrestadoraServicioProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioProductoDto producto = new PrestadoraServicioProductoDto();
					producto.setIdPrestadoraServicioProducto(rs.getLong("id_prestadora_servicio_producto"));
					producto.setDescripcionProductoConsolida(rs.getString("descripcion_producto_consolida"));
					producto.setIdCatGeneral(rs.getLong("id_cat_producto_sat"));
					producto.setClave(rs.getString("clave"));
					producto.setDescripcion(rs.getString("desc_producto_sat"));
					
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setRfc(rs.getString("rfc_prest_servicio"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social"));
					
//					CelulaDto celula = new CelulaDto();
//					celula.setIdCelula(rs.getLong("id_celula"));
//					celula.setNombre(rs.getString("nombre_celula"));
//					CelulaPrestadoraServicioDto celuaCelulaPrestadoraServicioDto = new CelulaPrestadoraServicioDto();
//					celuaCelulaPrestadoraServicioDto.setCelula(celula);
//					prestadoraServicioDto.setCelulaPrestadoraServicioDto(celuaCelulaPrestadoraServicioDto);
					
					producto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					
					listCatProducto.add(producto);
					return producto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaPrestadoraServicioProductosByIdCelula ", e);
			return Collections.emptyList();
		}
	}


//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public List<CatProductoDto> convertirProductoADto(Long idPrestadoraServicio) {
//		List<CatProductoDto> listCatProducto =  new ArrayList<CatProductoDto>();
//		try {
//			
//			StringBuilder sb = new StringBuilder();
//			sb.append(" select cp.id_cat_producto, cp.id_cat_producto_sat, cp.clave as claveProducto, cp.descripcion as descripcionProducto, "); 
//			sb.append(" cps.clave, cps.descripcion, cps.codigo_sat, cps.descripcion_sat ");
//			sb.append(" from sin.prestadora_servicio_producto psp, sin.cat_producto cp, sin.cat_producto_sat cps ");
//			sb.append(" where psp.id_producto = cp.id_cat_producto ");
//			sb.append(" and cps.id_cat_producto_sat = cp.id_cat_producto_sat ");
//			sb.append(" and psp.id_prestadora_servicio = ?");
//			
//			
//			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServicio}, new RowMapper() {
//				public CatProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//					CatProductoDto producto = new CatProductoDto();
//					producto.setIdCatProducto(rs.getLong("id_cat_producto"));
//					producto.setClaveProducto(rs.getString("claveProducto"));
//					producto.setDescripcionProducto(rs.getString("descripcionProducto"));
//					
//					producto.setClaveSAT(rs.getString("clave"));
//					producto.setDescripcionSAT(rs.getString("descripcion"));
//					producto.setIdCatProductoSAT(rs.getLong("id_cat_producto_sat"));
//					
//					producto.setIndEstatus(Boolean.TRUE);
//					
//					listCatProducto.add(producto);
//					return producto;	
//				}
//			});
//			
//		}catch (Exception e) {
//			LOGGER.error("Ocurrio un error en obtenerProductosXIdPrestadora ", e);
//			return Collections.emptyList();
//		}
//	}

}
