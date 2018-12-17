package com.liverpool.cc.catalogo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.liverpool.cc.exception.ServiceException;
import com.liverpool.cc.interfas.InterfasDAO;
import com.liverpool.cc.catalogo.bean.CatalogoBean;
import com.liverpool.cc.catalogo.pojo.Catalogo;
import com.liverpool.cc.util.ConexionBD;
import com.liverpool.cc.util.Conexion;

public class CatalogoDAO implements InterfasDAO{

	private static final Logger logger = Logger.getLogger(CatalogoDAO.class);
	
	private ConexionBD newConexionBD;

	public ConexionBD getNewConexionBD() {
		return newConexionBD;
	}

	public void setNewConexionBD(ConexionBD newConexionBD) {
		this.newConexionBD = newConexionBD;
	}

	public void DaoIntf(Conexion newConexion) {
		// TODO Auto-generated method stub
		this.newConexionBD = new ConexionBD(newConexion);
	}

	public void Close() {
		// TODO Auto-generated method stub
		newConexionBD.cerrar();
	}

	public boolean Borrar(String string) {
		boolean newBoolean = false;
		String sql = "DELETE FROM catalogo WHERE id=\"" + string + "\"";
		newBoolean = newConexionBD.Delete(sql);
		logger.debug( "Borrar" + ":" + sql );
		return newBoolean;
	}

	public ArrayList<Object> Lista() {
		List<Object> newListCatalogo = new ArrayList<Object>();
		String sql = "SELECT id,nombre,etiqueta,pojo,dao FROM catalogo";
		logger.debug( "Lista" + ":" + sql );
		ResultSet rs = newConexionBD.Consultas(sql);
		try {
			while (rs.next()) {
				Catalogo newCatalogo = new Catalogo();
				newCatalogo.setId(rs.getString("id"));
				newCatalogo.setNombre(rs.getString("nombre"));
				newCatalogo.setEtiqueta(rs.getString("etiqueta"));
				newCatalogo.setPojo(rs.getString("pojo"));
				newCatalogo.setDao(rs.getString("dao"));
				newListCatalogo.add(newCatalogo);
			}
		} catch (SQLException e) {
			throw new ServiceException("Error en el ResultSet ", e);
		}
		return (ArrayList<Object>) newListCatalogo;
	}

	public String ListaString() {
		String listString = "";
		String sql = "SELECT id,nombre,etiqueta,pojo,dao  FROM catalogo";
		logger.debug( "ListaString" + ":" + sql );
		ResultSet rs = newConexionBD.Consultas(sql);
		try {
			while (rs.next()) {
				listString = listString + " " + rs.getString("id");
			}
		} catch (SQLException e) {
			throw new ServiceException("Error en el ResultSet ", e);
		}
		return listString;
	}

	public List<Object> BusquedaCatalogos(String string) {
		List<Object> newListCatalogo = new ArrayList<Object>();
		String sql = "SELECT id,nombre,etiqueta,pojo,dao FROM catalogo WHERE id=\'" + string + "\'";
		logger.debug( "BusquedaCatalogos" + ":" + sql );
		ResultSet rs = newConexionBD.Consultas(sql);
		try {
			while (rs.next()) {
				Catalogo newCatalogo = new Catalogo();
				newCatalogo.setId(rs.getString("id"));
				newCatalogo.setNombre(rs.getString("nombre"));
				newCatalogo.setEtiqueta(rs.getString("etiqueta"));
				newCatalogo.setPojo(rs.getString("pojo"));
				newCatalogo.setDao(rs.getString("dao"));
				newListCatalogo.add(newCatalogo);
			}
		} catch (SQLException e) {
			throw new ServiceException( e);
		}
		return newListCatalogo;
	}

	public boolean Actualizar(Object object) {
		Catalogo newCatalogo = (Catalogo) object;
		String sql = "";
		boolean newBoolean = false;
		sql = "UPDATE catalogo SET" + " nombre=\'"	+ newCatalogo.getNombre() + "\'" 
		+ ", etiqueta=\'"	+ newCatalogo.getEtiqueta() + "\'"
		+ ", dao=\'"	+ newCatalogo.getDao() + "\'"
		+ ", pojo=\'"	+ newCatalogo.getPojo() + "\'"
		+ " WHERE ( id=\'" + newCatalogo.getId() + "\')";
		logger.debug( "Actualizar" + ":" + sql );
		newBoolean = newConexionBD.Insert(sql);
		return newBoolean;
	}

	public boolean Insertar(Object object) {
		Catalogo newCatalogo = (Catalogo) object;
		boolean newBoolean = false;
		String sql = "INSERT INTO catalogo (id,nombre,etiqueta,pojo,dao) VALUES (\""
				+ newCatalogo.getId() + "\",\"" + newCatalogo.getNombre()
				+ "\",\"" + newCatalogo.getEtiqueta() + "\",\"" + newCatalogo.getPojo()
				+ "\",\"" + newCatalogo.getDao() + "\")";
		logger.debug( "Insertar" + ":" + sql );
		newBoolean = newConexionBD.Insert(sql);
		CatalogoBean CatalogoBean = new CatalogoBean();
		CatalogoBean.creaCampos(newCatalogo, newConexionBD);
		return newBoolean;
	}

	public Object Busqueda(String id) {
		Catalogo catalogo = null;
		String sql = "SELECT id,nombre,etiqueta,pojo,dao FROM catalogo WHERE id=\"" + id + "\"";
		logger.debug( "Busqueda" + ":" + sql );
		ResultSet rs = newConexionBD.Consultas(sql);
		try {
			while (rs.next()) {
				catalogo = new Catalogo();
				catalogo.setId(rs.getString("id"));
				catalogo.setNombre(rs.getString("nombre"));
				catalogo.setEtiqueta(rs.getString("etiqueta"));
				catalogo.setPojo(rs.getString("pojo"));
				catalogo.setDao(rs.getString("dao"));

			}
		} catch (SQLException e) {
			throw new ServiceException( e);
		}
		return catalogo;
	}

	public Object Download(String id, String Campo) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Object UpLoad(String id, String Campo, byte[] blob) {
		// TODO Auto-generated method stub
		return null;
	}

}
