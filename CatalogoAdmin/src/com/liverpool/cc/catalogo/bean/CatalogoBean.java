package com.liverpool.cc.catalogo.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.liverpool.cc.catalogo.dao.CampoDAO;
import com.liverpool.cc.catalogo.pojo.Campo;
import com.liverpool.cc.catalogo.pojo.Catalogo;
import com.liverpool.cc.util.ConexionBD;
import com.liverpool.cc.util.GeneraObjectBean;

public class CatalogoBean {
	
	
	public void creaCampos(Catalogo newCatalogo, ConexionBD newConexion){
		GeneraObjectBean GeneraObjectBean = new GeneraObjectBean(newCatalogo.getPojo());
		Field[] userFields = GeneraObjectBean.getClassName().getDeclaredFields();
		List<Object> newListCampo =  new ArrayList<Object>();
		for (Field field : userFields) {
			Campo newCampo = new Campo();
			newCampo.setId(newCatalogo.getId()+"."+field.getName());
			newCampo.setIdCatalogo(newCatalogo.getId());
			newCampo.setNombre(field.getName());
			newListCampo.add(newCampo);
		}
		CampoDAO newCampoDAO = new CampoDAO();
		newCampoDAO.setnewConexion(newConexion);
		for (Object object : newListCampo) {
			newCampoDAO.Insertar(object);
		}
	}

}
