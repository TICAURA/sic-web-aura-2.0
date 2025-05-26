package mx.com.consolida.dao.impl;

import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatEntidadFederativaDao;
import mx.com.consolida.entity.crm.CatEntidadFederativa;

@Repository
public class CatEntidadFederativaDaoImpl  extends GenericDAO<CatEntidadFederativa, Long> implements CatEntidadFederativaDao{

}
