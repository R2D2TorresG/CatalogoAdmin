package com.liverpool.cc.catalogo.bean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.liverpool.cc.catalogo.dao.CampoDAO;
import com.liverpool.cc.catalogo.dao.CatalogoDAO;
import com.liverpool.cc.exception.ServiceException;
import com.liverpool.cc.interfas.InterfasDAO;
import com.liverpool.cc.catalogo.pojo.Campo;
import com.liverpool.cc.catalogo.pojo.Catalogo;
import com.liverpool.cc.util.Conexion;
import com.liverpool.cc.util.GeneraObjectBean;

public class CrudBean {

	private static final Logger logger = Logger.getLogger(CrudBean.class);

	public Object Descarga(String id, String campo, String daoClass,
			Conexion newConexion) throws ServiceException {
		logger.debug("Descarga" + ":" + id + "," + campo + "," + daoClass);
		GeneraObjectBean newClass = new GeneraObjectBean(daoClass);
		((InterfasDAO) newClass.getObjectClase()).DaoIntf(newConexion);
		Object newObject = ((InterfasDAO) newClass.getObjectClase()).Download(
				id, campo);
		((InterfasDAO) newClass.getObjectClase()).Close();
		return newObject;
	}

	public List<Campo> listaCampo(String id, Conexion newConexion)
			throws ServiceException {
		logger.debug("listaCampo" + ":" + id);
		CampoDAO newCampoDAO = new CampoDAO();
		newCampoDAO.DaoIntf(newConexion);
		List<Campo> newListCatalogo = newCampoDAO.BusquedaCampos(id);
		newCampoDAO.Close();
		return newListCatalogo;
	}

	public List<Object> listaObjeto(String id, String daoClass,
			Conexion newConexion) throws ServiceException {
		logger.debug("listaObjeto" + ":" + id + "," + daoClass);
		GeneraObjectBean newClass = new GeneraObjectBean(daoClass);
		((InterfasDAO) newClass.getObjectClase()).DaoIntf(newConexion);
		List<Object> newlistObjects = ((InterfasDAO) newClass.getObjectClase())
				.Lista();
		((InterfasDAO) newClass.getObjectClase()).Close();
		return newlistObjects;
	}

	public void Eliminar(String id, String idResgistro, String daoClass,
			Conexion newConexion) throws ServiceException {
		logger.debug("Eliminar" + ":" + id + "," + idResgistro + "," + daoClass);
		GeneraObjectBean newClass = new GeneraObjectBean(daoClass);
		((InterfasDAO) newClass.getObjectClase()).DaoIntf(newConexion);
		((InterfasDAO) newClass.getObjectClase()).Borrar(idResgistro);
		((InterfasDAO) newClass.getObjectClase()).Close();
	}

	public void Guardar(String id, HttpServletRequest request,
			String pojoClass, String daoClass, Conexion newConexion,
			Properties propiedades) throws ServiceException {
		logger.debug("Guardar" + ":" + id + "," + request + "," + pojoClass
				+ "," + daoClass + "," + propiedades);
		GeneraObjectBean newClassPojo = new GeneraObjectBean(pojoClass);
		GeneraObjectBean newClassDao = new GeneraObjectBean(daoClass);
		InformacionForm(id, newConexion, newClassPojo, newClassDao, request,
				propiedades);
		((InterfasDAO) newClassDao.getObjectClase()).DaoIntf(newConexion);
		((InterfasDAO) newClassDao.getObjectClase()).Insertar(newClassPojo
				.getObjectClase());
		((InterfasDAO) newClassDao.getObjectClase()).Close();
	}

	public void GuardarMond(String id, HttpServletRequest request,
			String pojoClass, String daoClass, Conexion newConexion,
			Properties propiedades) throws ServiceException {
		logger.debug("GuardarMond" + ":" + id + "," + request + "," + pojoClass
				+ "," + daoClass + "," + propiedades);
		GeneraObjectBean newClassPojo = new GeneraObjectBean(pojoClass);
		GeneraObjectBean newClassDao = new GeneraObjectBean(daoClass);
		InformacionForm(id, newConexion, newClassPojo, newClassDao, request,
				propiedades);
		((InterfasDAO) newClassDao.getObjectClase()).DaoIntf(newConexion);
		((InterfasDAO) newClassDao.getObjectClase()).Actualizar(newClassPojo
				.getObjectClase());
		((InterfasDAO) newClassDao.getObjectClase()).Close();
	}

	public void InformacionForm(String id, Conexion newConexion,
			GeneraObjectBean newClassPojo, GeneraObjectBean newClassDao,
			HttpServletRequest request, Properties propiedades)
			throws ServiceException {
		logger.debug("InformacionForm" + ":" + id + "," + newClassPojo + ","
				+ newClassDao + "," + request + "," + propiedades);
		List<Campo> listCampos = listaCampo(id, newConexion);
		for (Campo campo : listCampos) {
			Class<?> theClass = null;
			Method metodo = null;
			String tipoClase = propiedades.getProperty(campo.getTipo());
			if (tipoClase == null) {
				throw new ServiceException(new Throwable(
						" En el archivo de Properties no tiene la clase Definida "
								+ campo.getTipo()));
			}
			theClass = parseType(tipoClase);

			switch (campo.getTipo()) {
			case "Integer":
				if (!request.getParameter(campo.getNombre()).equals("")) {
					try {
						metodo = newClassPojo.getClassName().getMethod(
								"set"
										+ StringUtils.capitalize(campo
												.getNombre()), theClass);
						metodo.invoke(newClassPojo.getObjectClase(), Integer
								.parseInt(request.getParameter(campo
										.getNombre())));
						continue;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "randomUUID":
				String claveUnica = request.getParameter(campo.getNombre());
				if (claveUnica.equals("") || claveUnica == null) {
					try {
						metodo = newClassPojo.getClassName().getMethod(
								"set"
										+ StringUtils.capitalize(campo
												.getNombre()), theClass);
						metodo.invoke(newClassPojo.getObjectClase(),
								java.util.UUID.randomUUID().toString());
						continue;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						metodo = newClassPojo.getClassName().getMethod(
								"set"
										+ StringUtils.capitalize(campo
												.getNombre()), theClass);
						metodo.invoke(newClassPojo.getObjectClase(), theClass
								.cast(request.getParameter(campo.getNombre())));
						continue;
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "InputStream":
				MultipartFile multiFile = ((MultipartRequest) request)
						.getFile(campo.getNombre());
				String href = request.getParameter("href_" + campo.getNombre());
				InputStream is = null;
				if (multiFile != null) {
					try {
						is = multiFile.getInputStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					}
					try {
						metodo = newClassPojo.getClassName().getMethod(
								"set"
										+ StringUtils.capitalize(campo
												.getNombre()), theClass);
						metodo.invoke(newClassPojo.getObjectClase(), is);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					}
				}
				if (href.equals("0") && multiFile == null) {
					try {
						byte[] buffer = "".getBytes();
						InputStream b = new java.io.ByteArrayInputStream(buffer);
						metodo = newClassPojo.getClassName().getMethod(
								"set"
										+ StringUtils.capitalize(campo
												.getNombre()), theClass);
						metodo.invoke(newClassPojo.getObjectClase(), b);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						throw new ServiceException(e);
					}
				}
				break;
			case "Boolean":
				try {
					metodo = newClassPojo.getClassName().getMethod(
							"set" + StringUtils.capitalize(campo.getNombre()),
							theClass);
					boolean b = Boolean.valueOf(request.getParameter(campo
							.getNombre()));
					metodo.invoke(newClassPojo.getObjectClase(), b);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "String":
				try {
					metodo = newClassPojo.getClassName().getMethod(
							"set" + StringUtils.capitalize(campo.getNombre()),
							theClass);
					metodo.invoke(newClassPojo.getObjectClase(), theClass
							.cast(request.getParameter(campo.getNombre())));
					continue;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				throw new ServiceException(new Throwable(
						" En el archivo de Properties no tiene la clase Definida "
								+ campo.getTipo()));
			}
		}

	}

	public Catalogo BusquedaCatalogo(String id, Conexion newConexion)
			throws ServiceException {
		logger.debug("BusquedaCatalogo" + ":" + id);
		CatalogoDAO newCatalogoDAO = new CatalogoDAO();
		newCatalogoDAO.DaoIntf(newConexion);
		List<Object> newListCatalogos = newCatalogoDAO.BusquedaCatalogos(id);
		newCatalogoDAO.Close();
		return (Catalogo) newListCatalogos.get(0);
	}

	public Object ObjetoId(String id, String idResgistro, String daoClass,
			Conexion newConexion) throws ServiceException {
		logger.debug("ObjetoId" + ":" + id + "," + idResgistro + "," + daoClass);
		GeneraObjectBean newClass = new GeneraObjectBean(daoClass);
		((InterfasDAO) newClass.getObjectClase()).DaoIntf(newConexion);
		Object Objects = ((InterfasDAO) newClass.getObjectClase())
				.Busqueda(idResgistro);
		((InterfasDAO) newClass.getObjectClase()).Close();
		return Objects;
	}

	public static Class<?> parseType(final String className) {
		switch (className) {
		case "boolean":
			return boolean.class;
		case "byte":
			return byte.class;
		case "short":
			return short.class;
		case "int":
			return int.class;
		case "long":
			return long.class;
		case "float":
			return float.class;
		case "double":
			return double.class;
		case "char":
			return char.class;
		case "void":
			return void.class;
		default:
			String fqn = className.contains(".") ? className : "java.lang."
					.concat(className);
			try {
				return Class.forName(fqn);
			} catch (ClassNotFoundException ex) {
				throw new IllegalArgumentException("Class not found: " + fqn);
			}
		}
	}

	public static void verificarCamposObjetos(String pojo, List<Campo> newCampos) {
		List<String> listaNombreCampo = new ArrayList<String>();
		List<String> listaNombreClase = new ArrayList<String>();
		String idCatalogo = "";
		for (Campo campo : newCampos) {
			listaNombreCampo.add(campo.getId().toUpperCase());
			idCatalogo = campo.getIdCatalogo();
		}
		GeneraObjectBean newClass = new GeneraObjectBean(pojo);
		Field[] userFields = newClass.getClassName().getDeclaredFields();
		for (Field field : userFields) {
			listaNombreClase.add((idCatalogo + "." + field.getName())
					.toUpperCase());
		}
		Collections.sort(listaNombreCampo);
		Collections.sort(listaNombreClase);
		if (!listaNombreCampo.equals(listaNombreClase)) {
			throw new ServiceException("Error", new Throwable(
					"El OBJETO NO ESTA DECLARADO EN LA CONFIGURACION CORRECTAMENTE"
							+ "\n Objeto:" + listaNombreClase
							+ "\n Configuracion:" + listaNombreCampo));
		}
	}

}