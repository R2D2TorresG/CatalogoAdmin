package com.liverpool.cc.catalogo.pojo;

public class Itext {
	
	String Tipo;
	Object Informacion;
	
	
	public Itext(String tipo, Object informacion) {
		super();
		Tipo = tipo;
		Informacion = informacion;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public Object getInformacion() {
		return Informacion;
	}
	public void setInformacion(Object informacion) {
		Informacion = informacion;
	}
	
	

}
