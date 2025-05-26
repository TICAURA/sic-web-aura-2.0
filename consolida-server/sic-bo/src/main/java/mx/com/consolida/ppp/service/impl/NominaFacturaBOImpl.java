package mx.com.consolida.ppp.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaDao;
import mx.com.consolida.ppp.service.interfaz.NominaFacturaBO;
import mx.com.facturacion.factura.FacturaDTO;

@Service
public class NominaFacturaBOImpl implements NominaFacturaBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaFacturaBOImpl.class);

}
