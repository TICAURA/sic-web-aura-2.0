package mx.com.consolida.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatIsrDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatalogoCotizadorDao;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatImss;
import mx.com.consolida.entity.CatIsr;
import mx.com.consolida.entity.CatPrimaRiesgo;
import mx.com.consolida.entity.CatSalariosGenerales;
import mx.com.consolida.entity.CatSubsidio;
import mx.com.consolida.entity.CatVacaciones;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

@Repository
public class CatalogoCotizadorDaoImpl extends GenericDAO<CatGeneral, Integer> implements CatalogoCotizadorDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CatalogoCotizadorDaoImpl.class);
	
	@Transactional(propagation = Propagation.MANDATORY )
	public CatSalariosGenerales obtenerSalarioGeneralByClave(String clv){
		Session session = sessionFactory.openSession();
		CatSalariosGenerales valor = new CatSalariosGenerales();
		try {
			Query qry = session.createQuery("from CatSalariosGenerales where ind_estatus = 1 and clave ='"+clv+"'");
			valor = (CatSalariosGenerales) qry.uniqueResult();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return valor;
	}
	
	@Transactional(propagation = Propagation.MANDATORY )
	public CatImss otenerImssByClave(String clv){
		Session session = sessionFactory.openSession();
		CatImss valor = new CatImss();
		try {
			Query qry = session.createQuery("from CatImss where ind_estatus = 1 and clave ='"+clv+"'");
			valor = (CatImss) qry.uniqueResult();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return valor;
	}
	
	@Transactional(propagation = Propagation.MANDATORY )
	public CatVacaciones obtenerVacacionesByAntiguedad(BigDecimal valor) {
		Session session = sessionFactory.openSession();
		CatVacaciones result = new CatVacaciones();
		try {
			Integer id = obtenerVacacionesMayor(valor);
			Query qry = session.createQuery("from CatVacaciones where ind_estatus = 1 and id_cat_vacaciones = "+id);
			result = (CatVacaciones) qry.uniqueResult();
			if((new BigDecimal(result.getAniosInicio())).compareTo(valor)==1) {
				qry = session.createQuery("from CatVacaciones where ind_estatus = 1 and anios_inicio <= "+valor+" and anios_fin >="+valor);
				result = (CatVacaciones) qry.uniqueResult();
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return result;
	}
	@Transactional(propagation = Propagation.MANDATORY )
	public Integer obtenerVacacionesMayor(BigDecimal valor) {
		Session session = sessionFactory.openSession();
		Object result = 0;
		try {
			Query qry = session.createSQLQuery("select id_cat_vacaciones from Cat_Vacaciones where anios_fin =(select max(anios_fin) from Cat_Vacaciones where ind_estatus = 1)");
			result = (Object) qry.uniqueResult();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return Integer.parseInt(result.toString());
	}
	
	@Transactional(propagation = Propagation.MANDATORY )
	public CatIsr otenerIsrByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor) {
		Session session = sessionFactory.openSession();
		CatIsr result = new CatIsr();
		try {
			Query qry = session.createQuery("from CatIsr where ind_estatus = 1 and id_tipo_pago = "+idPeriodicidad+"  and limite_inferior <="+valor+" and limite_superior >="+valor);
			List<CatIsr> lista = (List<CatIsr>) qry.list();
			if(lista!=null && !lista.isEmpty()) {
				if(lista.size() > 1) {
					System.out.println(" mayor a 1    where  where ind_estatus = 1 and id_tipo_pago = "+idPeriodicidad+"  and limite_inferior <="+valor+" and limite_superior >="+valor);
				} else {
					result = lista.get(0);
				}
			}else {
				result = new CatIsr();
				result.setCuotaFija(new BigDecimal(0));
				result.setLimiteInferior(new BigDecimal(0));
				result.setLimiteSuperior(new BigDecimal(0));
				result.setPorcentajecatTipoPago(new BigDecimal(0));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.MANDATORY )
	public CatSubsidio otenerSubsidioByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor) {
		Session session = sessionFactory.openSession();
		CatSubsidio result = new CatSubsidio();
		try {
			Query qry = session.createQuery("from CatSubsidio where ind_estatus = 1 and id_tipo_pago = "+idPeriodicidad+"  and para_ingresos_de <="+valor+" and hasta_ingresos_de >="+valor);
			List<CatSubsidio> lista = (List<CatSubsidio>) qry.list();
			if(lista!=null && !lista.isEmpty()) {
				if(lista.size() > 1) {
						System.out.println(" mayor a 1   where ind_estatus = 1 and id_tipo_pago = "+idPeriodicidad+"  and para_ingresos_de <="+valor+" and hasta_ingresos_de >="+valor);	
				}else {
					result = lista.get(0);
				}
			}else {
				result = new CatSubsidio();
				result.setParaIngresosDe(new BigDecimal(0));
				result.setHastaIngresosDe(new BigDecimal(0));
				result.setSubsidio(new BigDecimal(0));
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return result;
	}
	@Transactional(propagation = Propagation.MANDATORY )
	public CatPrimaRiesgo otenerIsrByPrimaRiegoVar(String claveRiesgo) {
		Session session = sessionFactory.openSession();
		CatPrimaRiesgo result = new CatPrimaRiesgo();
		try {
			Query qry = session.createQuery("from CatPrimaRiesgo where ind_estatus = 1  and clave_riesgo = '"+claveRiesgo+"'");
			result = (CatPrimaRiesgo) qry.uniqueResult();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			session.clear();
			session.flush();
			session.close();
		}
		return result;
	}
}

