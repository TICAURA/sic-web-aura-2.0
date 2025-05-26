package mx.com.consolida.crm.dao.interfaz;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDomicilio;

@Repository
public class PrestadoraServicioDomicilioDaoImpl extends GenericDAO<PrestadoraServicioDomicilio, Long> implements PrestadoraServicioDomicilioDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioDomicilioDaoImpl.class);

	@Override
	public DomicilioComunDto convertirPrestadoraServicioDomicilioADto(
			PrestadoraServicioDomicilio prestadoraServicioDomicilio) {
		DomicilioComunDto dto = new DomicilioComunDto();
		DomicilioDto domicilioDto = new DomicilioDto();
		
		domicilioDto.setCatEntidadFederativa(new CatGeneralDto());
		domicilioDto.getCatEntidadFederativa().setIdCatGeneral(prestadoraServicioDomicilio.getDomicilio().getCatEntidadFederativa().getIdEntidadFederativa());
		domicilioDto.getCatEntidadFederativa().setDescripcion(prestadoraServicioDomicilio.getDomicilio().getCatEntidadFederativa().getDescripcionEntidadFederativa());
		domicilioDto.getCatEntidadFederativa().setClave(prestadoraServicioDomicilio.getDomicilio().getCatEntidadFederativa().getCveCatGeneral());
		domicilioDto.setIdEntidadFederativa(prestadoraServicioDomicilio.getDomicilio().getCatEntidadFederativa().getIdEntidadFederativa());
		
		domicilioDto.setCatMunicipios(new CatGeneralDto());
		domicilioDto.getCatMunicipios().setIdCatGeneral(prestadoraServicioDomicilio.getDomicilio().getCatMunicipios().getIdCatMunicipios());
		domicilioDto.getCatMunicipios().setClave(prestadoraServicioDomicilio.getDomicilio().getCatMunicipios().getCveMunicipio());
		domicilioDto.getCatMunicipios().setDescripcion(prestadoraServicioDomicilio.getDomicilio().getCatMunicipios().getDescripcion());
		
		domicilioDto.setCatTipoDomicilio(new CatGeneralDto());
		domicilioDto.getCatTipoDomicilio().setIdCatGeneral(prestadoraServicioDomicilio.getDomicilio().getCatTipoDomicilio().getIdCatGeneral());
		domicilioDto.getCatTipoDomicilio().setClave(prestadoraServicioDomicilio.getDomicilio().getCatTipoDomicilio().getClave());
		domicilioDto.getCatTipoDomicilio().setDescripcion(prestadoraServicioDomicilio.getDomicilio().getCatTipoDomicilio().getDescripcion());
		
		domicilioDto.setCalle(prestadoraServicioDomicilio.getDomicilio().getCalle());
		domicilioDto.setCodigoPostal(prestadoraServicioDomicilio.getDomicilio().getCodigoPostal());
		domicilioDto.setColonia(prestadoraServicioDomicilio.getDomicilio().getColonia());
		domicilioDto.setNumeroExterior(prestadoraServicioDomicilio.getDomicilio().getNumeroExterior());
		domicilioDto.setNumeroInterior(prestadoraServicioDomicilio.getDomicilio().getNumeroInterior());
		domicilioDto.setIdDomicilio(prestadoraServicioDomicilio.getDomicilio().getIdDomicilio());
		
		dto.setDomicilio(domicilioDto);
		dto.setIndEstatus(prestadoraServicioDomicilio.getIndEstatus());
		dto.setFechaAlta(prestadoraServicioDomicilio.getFechaAlta());
		dto.setIdPrestadoraServicioDomicilio(prestadoraServicioDomicilio.getIdPrestadoraServicioDomicilio());
		return dto;
	}
	
}
