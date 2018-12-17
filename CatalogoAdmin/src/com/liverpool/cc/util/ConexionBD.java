package com.liverpool.cc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.liverpool.cc.exception.ServiceException;
import com.liverpool.cc.util.Conexion;

public class ConexionBD {

	private static final Logger logger = Logger.getLogger(ConexionBD.class);

	Conexion newConexion = null;

	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private int resultInt = 0;

	public ConexionBD() {
		super();
	}

	public ConexionBD(Conexion newConexion)  throws ServiceException{
		logger.debug("ConexionBD" + ":" + newConexion);
		this.newConexion = newConexion;
		try {
			Class.forName(newConexion.getJDBC_DRIVER());
			try {
				connection = DriverManager.getConnection(newConexion.getDB_URL(),newConexion.getUSER(),newConexion.getPASS());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new ServiceException(e);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e);
		}
	}

	public ResultSet Consultas(String consultaSQL)  throws ServiceException{
		logger.debug("Consultas" + ":" + consultaSQL);
		try {
			if (connection == null)
				connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		try {
			statement = connection.prepareStatement(consultaSQL);
			resultSet = statement.executeQuery();
		} catch (Exception ex) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			} catch (Exception e) {
				throw new ServiceException(e);
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
			}
		}
		return resultSet;
	}

	public boolean Insert(String insertSQL) throws ServiceException{
		logger.debug("Insert" + ":" + insertSQL);
		try {
			if (connection == null)
				connection.close();
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		boolean newBoolean = false;
		try {
			statement = connection.prepareStatement(insertSQL);
			int interger = statement.executeUpdate();

			if (interger == 0) {
				newBoolean = true;
			}
		} catch (Exception ex) {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			} catch (Exception e) {
				throw new ServiceException(e);
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
			}
		}
		return newBoolean;
	}

	public boolean InsertBlob(String insertSQL, byte[] archivo) throws ServiceException {
		logger.debug("InsertBlob" + ":" + insertSQL + "," + archivo);
		boolean newBoolean = false;
		try {
			if (connection == null)
				connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		try {
			statement = connection.prepareStatement(insertSQL);
			statement.setBytes(1, archivo);
			int interger = statement.executeUpdate();
			if (interger == 0) {
				newBoolean = true;
			}
		} catch (Exception ex) {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			} catch (Exception e) {
				throw new ServiceException(e);
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
			}
		}
		return newBoolean;
	}

	public boolean Delete(String deleteSQL) throws ServiceException {
		logger.debug("Delete" + ":" + deleteSQL);
		try {
			if (connection == null)
				connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		boolean newBoolean = false;
		try {
			statement = connection.prepareStatement(deleteSQL);
			resultInt = statement.executeUpdate();

			if (resultInt == 0) {
				newBoolean = true;
			}
		} catch (Exception ex) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					throw new ServiceException("Error en el Cambio del Servicios", e);
				}
			}
		}
		return newBoolean;
	}

	public boolean Create(String createSQL) throws ServiceException {
		logger.debug("Create" + ":" + createSQL);
		boolean newBoolean = false;
		try {
			if (connection == null)
				connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		try {
			statement = connection.prepareStatement(createSQL);
			int interger = statement.executeUpdate();

			if (interger == 0) {
				newBoolean = true;
			}
		} catch (Exception ex) {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			} catch (Exception e) {
				throw new ServiceException(e);
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					throw new ServiceException(e);
				}
			}
		}
		return newBoolean;
	}

	public void cerrar() throws ServiceException {
		logger.debug("cerrar");
		try {
			if (connection == null)
				connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		}

		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				throw new ServiceException(e);
			}
		}
	}

}
