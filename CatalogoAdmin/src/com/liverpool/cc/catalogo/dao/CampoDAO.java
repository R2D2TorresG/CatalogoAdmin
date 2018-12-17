package com.liverpool.cc.catalogo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.liverpool.cc.exception.ServiceException;
import com.liverpool.cc.interfas.InterfasDAO;
import com.liverpool.cc.catalogo.pojo.Campo;
import com.liverpool.cc.util.ConexionBD;
import com.liverpool.cc.util.Conexion;

public class CampoDAO implements InterfasDAO {

	private static final Logger logger = Logger.getLogger(CampoDAO.class) ; 
	
	private ConexionBD newConexion;

	public ConexionBD getnewConexion() {
		return newConexion;
	}

	public void setnewConexion(ConexionBD newConexion) {
		this.newConexion = newConexion;
	}

	public void DaoIntf(Conexion newConexion) {
		// TODO Auto-generated method stub
		this.newConexion = new ConexionBD(newConexion);
	}

	public void Close() {
		// TODO Auto-generated method stub
		newConexion.cerrar();
	}

	public List<Object> Lista() {
		List<Object> newListCampo = new ArrayList<Object>();
		String sql = "SELECT id,idCatalogo,etiqueta,longitud,nombre,tipo,valor,validar,llave,formato,modificar FROM campo";
		logger.debug( "Lista" + ":" + sql );
		ResultSet rs = newConexion.Consultas(sql);
		try {
			while (rs.next()) {
				Campo newCampo = new Campo();
				newCampo.setId(rs.getString("id"));
				newCampo.setIdCatalogo(rs.getString("idCatalogo"));
				newCampo.setEtiqueta(rs.getString("etiqueta"));
				newCampo.setLongitud(rs.getString("longitud"));
				newCampo.setNombre(rs.getString("nombre"));
				newCampo.setTipo(rs.getString("tipo"));
				newCampo.setValor(rs.getString("valor"));
				newCampo.setValidar(rs.getString("validar"));
				newCampo.setLlave(rs.getString("llave"));
				newCampo.setFormato(rs.getString("formato"));
				newCampo.setModificar(rs.getString("modificar"));
				newListCampo.add(newCampo);
			}
		} catch (SQLException e) {
			throw new ServiceException("Error", e);
		}
		return (List<Object>) newListCampo;
	}

	public List<Campo> BusquedaCampos(String string) {
		String sql = "SELECT id,idCatalogo,etiqueta,longitud,nombre,tipo,valor,validar,llave,formato,modificar FROM campo WHERE idCatalogo=\'"
				+ string + "\'";
		logger.debug( "BusquedaCampos" + ":" + sql );
		List<Campo> newListCampo = new ArrayList<Campo>();
		ResultSet rs = newConexion.Consultas(sql);
		try {
			while (rs.next()) {
				Campo newCampo = new Campo();
				newCampo.setId(rs.getString("id"));
				newCampo.setIdCatalogo(rs.getString("idCatalogo"));
				newCampo.setEtiqueta(rs.getString("etiqueta"));
				newCampo.setLongitud(rs.getString("longitud"));
				newCampo.setNombre(rs.getString("nombre"));
				newCampo.setTipo(rs.getString("tipo"));
				newCampo.setValor(rs.getString("valor"));
				newCampo.setValidar(rs.getString("validar"));
				newCampo.setLlave(rs.getString("llave"));
				newCampo.setFormato(rs.getString("formato"));
				newCampo.setModificar(rs.getString("modificar"));
				newListCampo.add(newCampo);
			}
		} catch (SQLException e) {
			throw new ServiceException( e);
		}
		return newListCampo;
	}

	public Object Busqueda(String id) {
		String sql = "SELECT id,idCatalogo,etiqueta,longitud,nombre,tipo,valor,validar,llave,formato,modificar FROM campo WHERE id=\'"
				+ id + "\'";
		Campo newCampo = null;
		logger.debug( "Busqueda" + ":" + sql );
		ResultSet rs = newConexion.Consultas(sql);
		try {
			while (rs.next()) {
				newCampo = new Campo();
				newCampo.setId(rs.getString("id"));
				newCampo.setIdCatalogo(rs.getString("idCatalogo"));
				newCampo.setEtiqueta(rs.getString("etiqueta"));
				newCampo.setLongitud(rs.getString("longitud"));
				newCampo.setNombre(rs.getString("nombre"));
				newCampo.setTipo(rs.getString("tipo"));
				newCampo.setValor(rs.getString("valor"));
				newCampo.setValidar(rs.getString("validar"));
				newCampo.setLlave(rs.getString("llave"));
				newCampo.setFormato(rs.getString("formato"));
				newCampo.setModificar(rs.getString("modificar"));
			}
		} catch (SQLException e) {
			throw new ServiceException( e);
		}
		return newCampo;
	}

	public boolean Borrar(String string) {
		boolean newBoolean = false;
		String sql = "DELETE FROM campo WHERE id=\'" + string + "\'";
		logger.debug( "Borrar" + ":" + sql );
		newBoolean = newConexion.Delete(sql);
		return newBoolean;
	}

	public boolean Actualizar(Object object) {
		
		Campo newCampo = (Campo) object;
		String sql = "";
		boolean newBoolean = false;
		sql = "UPDATE campo SET" 
				+ "  nombre=\'"	+ newCampo.getNombre() + "\'" 
				+ ", formato=\'" + newCampo.getFormato() + "\'"
				+ ", etiqueta=\'" + newCampo.getEtiqueta() + "\'"
				+ ", llave=\'" + newCampo.getLlave() + "\'" 
				+ ", longitud=\'" + newCampo.getLongitud() + "\'" 
				+ ", tipo=\'"+ newCampo.getTipo() + "\'" 
				+ ", validar=\'"+ newCampo.getValidar() + "\'" 
				+ ", valor=\'"+ newCampo.getValor() + "\'" 
				+ ", modificar=\'"+ newCampo.getModificar() + "\'" 
				+ " WHERE ( id=\'"+ newCampo.getId() + "\')";
		logger.debug( "Actualizar" + ":" + sql );
		newBoolean = newConexion.Insert(sql);
		return newBoolean;
	}

	public boolean Insertar(Object object) {
		Campo newCampo = (Campo) object;
		boolean newBoolean = false;
		String sql = "INSERT INTO campo (id,idCatalogo,etiqueta,longitud,nombre,tipo,valor,validar,llave,formato,modificar) VALUES (\'"
				+ newCampo.getIdCatalogo()
				+ "."
				+ newCampo.getNombre()
				+ "\',\'"
				+ newCampo.getIdCatalogo()
				+ "\',\'"
				+ newCampo.getEtiqueta()
				+ "\',\'"
				+ newCampo.getLongitud()
				+ "\',\'"
				+ newCampo.getNombre()
				+ "\',\'"
				+ newCampo.getTipo()
				+ "\',\'"
				+ newCampo.getValor()
				+ "\',\'"
				+ newCampo.getValidar()
				+ "\',\'"
				+ newCampo.getLlave()
				+ "\',\'"
				+ newCampo.getFormato()
				+ "\',\'"
				+ newCampo.getModificar() + "\')";
		logger.debug( "Insertar" + ":" + sql );
		newBoolean = newConexion.Insert(sql);
		return newBoolean;
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
