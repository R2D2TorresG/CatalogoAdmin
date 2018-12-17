package com.liverpool.cc.interfas;

import java.util.List;

import com.liverpool.cc.util.Conexion;
import com.liverpool.cc.util.ConexionBD;

public interface InterfasDAO {
	
	public boolean Borrar(String string);

	public boolean Actualizar(Object object);
	
	public boolean Insertar(Object object);

	public void DaoIntf(Conexion newConexion);

	public void Close();

	public List<Object> Lista();

	public Object Busqueda(String id);
	
	public Object Download(String id,String Campo);
	
	public Object UpLoad(String id,String Campo,byte[] blob);

}
