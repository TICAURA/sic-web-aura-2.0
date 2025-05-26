package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.entity.ppp.PppNominaFacturaDocumento;

@Repository
public class PppNominaFacturaDocumentoDaoImpl extends GenericDAO<PppNominaFacturaDocumento, Long> implements PppNominaFacturaDocumentoDao{
	
	private static Logger LOGGER = LoggerFactory.getLogger(PppNominaFacturaDocumentoDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public PppNominaFacturaDocumento getUltimoDocComprobantePagoByIdCms(Long idCms) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select documento from PppNominaFacturaDocumento documento where documento.indEstatus = 1 and documento.definicionDocumento.idDefinicionDocumento = 65 and documento.idCMS = :idCms");
			query.setParameter("idCms", idCms);

			PppNominaFacturaDocumento documento = (PppNominaFacturaDocumento) query.uniqueResult();
			
			return documento;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getUltimoDocByIdCms ", e);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Long> getidPppNominaFacturaDocumento(Long idPppNominaFactura) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select documento.idPppNominaFacturaDocumento from PppNominaFacturaDocumento documento where documento.indEstatus = 1 and documento.pppNominaFactura.idPppNominaFactura = :idPppNominaFactura");
			query.setParameter("idPppNominaFactura", idPppNominaFactura);

			List<Long>  lista = query.list();
			
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getidPppNominaFacturaDocumento ", e);
			return null;
		}
	}

	
	@Transactional
	public Boolean deleteDocFinanciamientoByIdPppNomina(Long IdPppNomia) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select documento from PppNominaFacturaDocumento documento where documento.indEstatus = 1 and documento.definicionDocumento.idDefinicionDocumento = 65 "
					+ " and documento.pppNominaFactura.nominaCliente.idPppNomina = :IdPppNomia");
			query.setParameter("IdPppNomia", IdPppNomia);

			PppNominaFacturaDocumento documento = (PppNominaFacturaDocumento) query.uniqueResult();
			
			if(documento !=null && documento.getIdCMS() != null) {
				Query query2 = session.createQuery("delete from PppNominaFacturaDocumento doc where doc.idCMS = :idCMS and doc.pppNominaFactura.idPppNominaFactura = :idPppNominaFactura and doc.indEstatus = 1");
				query2.setParameter("idCMS", documento.getIdCMS());
				query2.setParameter("idPppNominaFactura", documento.getPppNominaFactura().getIdPppNominaFactura());
				query2.executeUpdate();
			}

			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en deleteDocFinanciamientoByIdPppNomina ", e);
			return Boolean.FALSE;
		}
	}

}
