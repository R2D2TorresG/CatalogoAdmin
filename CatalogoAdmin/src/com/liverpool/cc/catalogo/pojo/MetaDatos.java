package com.liverpool.cc.catalogo.pojo;

import java.util.ArrayList;
import java.util.List;

public class MetaDatos {

	List<Campo> newListCampo = new ArrayList<Campo>();
	Object object = new Object();

	public List<Campo> getNewListCampo() {
		return newListCampo;
	}

	public void setNewListCampo(List<Campo> newListCampo) {
		this.newListCampo = newListCampo;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
