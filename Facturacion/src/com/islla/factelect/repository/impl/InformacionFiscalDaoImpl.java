package com.islla.factelect.repository.impl;

/* Fernando Morales Serrano 
 * 23/11/2012
 * Maricruz Reyes Gonzalez
 * 27/11/2012
 * Comentarios: Se realizan cambios en la consulta del metodo obtenerDatosFiscales()
 * Integracion del metodo obtenerDatosFiscales(), 
 * 28/11/2012 
 * Comentarios: Integracion de los metodos
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.InformacionFiscalDao;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Estados;
import com.islla.factelect.domain.InformacionFiscal;
import com.islla.factelect.domain.Municipios;

/**
 * Clase que implementa la interface InformacionFiscalDaoI, que contendra las
 * consultas hechas en SQL Standar para la consulta, insercion, actualizacion y
 * eliminacion de registros de la tabla INFORMACION_FISCAL principalmente
 * 
 * */
@Repository("informacionFiscalDaoSQL")
public class InformacionFiscalDaoImpl extends
		GenericoDao<InformacionFiscal, Serializable> implements
		InformacionFiscalDao {

	/**
	 * Metodo que regresa la informacion fiscal activa dado un rfc del emisor
	 * 
	 * */
	public InformacionFiscal obtenerDatosFiscales(String pRfc) {
		InformacionFiscal datosFiscales = new InformacionFiscal();
		try {
			PreparedStatement consulta = null;
			String query = "(SELECT INFORMACION_FISCAL.RAZON_SOCIAL AS RAZONSOCIAL,"
					+ "ESTADOS.NOMBRE 							    AS ESTADO, "
					+ "MUNICIPIOS.NOMBRE 							AS MUNICIPIO, "
					+ "INFORMACION_FISCAL.DIRECCION 				AS DIRECCION, "
					+ "INFORMACION_FISCAL.CP 						AS CP, "
					+ "INFORMACION_FISCAL.TELEFONO 				    AS TELEFONO, "
					+ "INFORMACION_FISCAL.LOGO 					    AS LOGO "
					+ "FROM EMISOR "
					+ "INNER JOIN INFORMACION_FISCAL   ON EMISOR.RFC_EMISOR=INFORMACION_FISCAL.EMISOR_RFC_EMISOR "
					+ "INNER JOIN MUNICIPIOS 		   ON INFORMACION_FISCAL.MUNICIPIOS_ID_MUNICIPIO=MUNICIPIOS.ID_MUNICIPIO "
					+ "INNER JOIN ESTADOS 		       ON MUNICIPIOS.ESTADOS_ID_ESTADO=ESTADOS.ID_ESTADO "
					+ "WHERE EMISOR.RFC_EMISOR=? 	   AND INFORMACION_FISCAL.ESTATUS='A' )";

			consulta = super.getDBConnection().prepareStatement(query);
			consulta.setString(1, pRfc);

			ResultSet resultado = super.getResultados(consulta);

			datosFiscales.setMunicipios(new Municipios());
			datosFiscales.getMunicipios().setEstados(new Estados());
			datosFiscales.setEmisor(new Emisor());

			while (resultado.next()) {

				datosFiscales.setRazonSocial(resultado.getString("RAZONSOCIAL"));
				datosFiscales.getMunicipios().getEstados().setNombre(resultado.getString("ESTADO"));
				datosFiscales.getMunicipios().setNombre(resultado.getString("MUNICIPIO"));
				datosFiscales.setDireccion(resultado.getString("DIRECCION"));
				datosFiscales.setCp(resultado.getString("CP"));
				datosFiscales.setTelefono(resultado.getString("TELEFONO"));
				datosFiscales.setLogo(resultado.getBytes("LOGO"));

			}

			return datosFiscales;
		} catch (Exception ex) {
			ex.printStackTrace();
			return datosFiscales;
		}
	}
	
	/**
	 * 
	 *  obtenerDatosFiscalesAct, recuperar la informacion fiscal del emisor activa, 
	 *  dado el rfc del emisor que requiere actualizar su informacion fiscal (caso de uso, Actualizar informacion fiscal.
	 * @param idInformacion
	 * @return
	 */
	public InformacionFiscal obtenerDatosFiscalesAct(String pRfc){
		
		InformacionFiscal informacionF = new InformacionFiscal();
		
		try {
			PreparedStatement consulta = null;
			String query = "(SELECT INFORMACION_FISCAL.RAZON_SOCIAL AS RAZONSOCIAL,"
					+ " INFORMACION_FISCAL.NOMBRE 					AS NOMBRE,"
					+ " INFORMACION_FISCAL.APELLIDO_PATERNO 		AS APELLIDOP, "
					+ " INFORMACION_FISCAL.APELLIDO_MATERNO 		AS APELLIDOM,"
					+ " ESTADOS.NOMBRE 							  	AS ESTADO, "
					+ " MUNICIPIOS.NOMBRE 							AS MUNICIPIO, "
					+ " INFORMACION_FISCAL.DIRECCION 				AS DIRECCION, "
					+ " INFORMACION_FISCAL.CP 						AS CP, "
					+ " INFORMACION_FISCAL.TELEFONO 				AS TELEFONO, "
					+ " INFORMACION_FISCAL.LOGO 					AS LOGO, "
					+ " INFORMACION_FISCAL.ID_INFORMACION_FISCAL 	AS IDSOLICITUD,"
					+ " INFORMACION_FISCAL.FECHA_MODIFICACION 		AS FECHAMODIFICACION,"
					+ " EMISOR.TIPPO_PERSONA AS TIPOPERSONA," 
					+ " EMISOR.RFC_EMISOR AS RFCEMISOR"
					+ " FROM EMISOR "
					+ " INNER JOIN INFORMACION_FISCAL  ON EMISOR.RFC_EMISOR=INFORMACION_FISCAL.EMISOR_RFC_EMISOR "
					+ " INNER JOIN MUNICIPIOS 		   ON INFORMACION_FISCAL.MUNICIPIOS_ID_MUNICIPIO=MUNICIPIOS.ID_MUNICIPIO "
					+ " INNER JOIN ESTADOS 		       ON MUNICIPIOS.ESTADOS_ID_ESTADO=ESTADOS.ID_ESTADO "
					+ " WHERE EMISOR.RFC_EMISOR=? 	   AND INFORMACION_FISCAL.ESTATUS='A' )";

			consulta = super.getDBConnection().prepareStatement(query);
			consulta.setString(1, pRfc);
			consulta.execute();

			ResultSet resultado = super.getResultados(consulta);

			informacionF.setMunicipios(new Municipios());
			informacionF.getMunicipios().setEstados(new Estados());
			informacionF.setEmisor(new Emisor());

			while (resultado.next()) {

				informacionF.setRazonSocial(resultado.getString("RAZONSOCIAL"));
				informacionF.setNombre(resultado.getString("NOMBRE"));
				informacionF.setApellidoPaterno(resultado.getString("APELLIDOP"));
				informacionF.setApellidoMaterno(resultado.getString("APELLIDOM"));
				informacionF.getMunicipios().getEstados().setNombre(resultado.getString("ESTADO"));
				informacionF.getMunicipios().setNombre(resultado.getString("MUNICIPIO"));
				informacionF.setDireccion(resultado.getString("DIRECCION"));
				informacionF.setCp(resultado.getString("CP"));
				informacionF.setTelefono(resultado.getString("TELEFONO"));
				informacionF.setLogo(resultado.getBytes("LOGO"));
				informacionF.setIdInformacionFiscal(resultado.getInt("IDSOLICITUD"));
				informacionF.getEmisor().setTippoPersona(resultado.getString("TIPOPERSONA").charAt(0));
				informacionF.getEmisor().setRfcEmisor(resultado.getString("RFCEMISOR"));
				informacionF.setFechaModificacion(resultado.getDate("FECHAMODIFICACION"));
			}
			
			return informacionF;
		}catch (SQLException e){
			e.printStackTrace();
			return informacionF;
		}
	}
	
	/**
	 * Metodod para actualizar el estatus del registro actual de informacionFiscal, establecer como desactivado = 'D',dado el IdInformacion
	 * e insertar un nuevo registro.
	 * @param idInformacion
	 * @throws SQLException 
	 */
	public InformacionFiscal actualizarDatosFiscales(InformacionFiscal pInformacion, int pIdInformacion) throws SQLException {

		Connection conexionDB = super.getDBConnection();
		PreparedStatement actualizar =null;
		PreparedStatement insertarNuevoRegistro = null;
		InformacionFiscal informacionFiscal = null;
		
		try {
			
			conexionDB.setAutoCommit(false);  //Se configura la conexion en false para la transaccion
			
			// Actualizar el estatus del registro actual (actual 'A', cambio 'D')
			String query = " UPDATE INFORMACION_FISCAL SET ESTATUS = 'D' WHERE ID_INFORMACION_FISCAL = ? ";
			
			actualizar = conexionDB.prepareStatement(query);
			actualizar.setInt(1, pIdInformacion);
		    super.ejectuarActualizacion(actualizar);
		    
			// Insertar nuevo registro
			String queryInsercion = "insert into informacion_fiscal(ID_INFORMACION_FISCAL, "
					+ " EMISOR_RFC_EMISOR, "
					+ " MUNICIPIOS_ESTADOS_ID_ESTADO, "
					+ " MUNICIPIOS_ID_MUNICIPIO, "
					+ " DIRECCION, "
					+ " CP, "
					+ " TELEFONO, "
					+ " CORREO, "
					+ " LOGO, "
					+ " ESTATUS, "
					+ " FECHA_MODIFICACION) "
					+ " values (null,?,?,?,?,?,?,?,?,?,now()) ";
			
				insertarNuevoRegistro = conexionDB.prepareStatement(queryInsercion);

				insertarNuevoRegistro.setString(1, pInformacion.getEmisor().getRfcEmisor());
				insertarNuevoRegistro.setInt(2, new Integer(pInformacion.getMunicipios().getId().getEstadosIdEstado()));
				insertarNuevoRegistro.setInt(3, new Integer(pInformacion.getMunicipios().getId().getIdMunicipio()));
				insertarNuevoRegistro.setString(4, pInformacion.getDireccion());
				insertarNuevoRegistro.setString(5, pInformacion.getCp());
				insertarNuevoRegistro.setString(6, pInformacion.getTelefono());
				insertarNuevoRegistro.setString(7, pInformacion.getCorreo());
				insertarNuevoRegistro.setBytes(8, pInformacion.getLogo());
				insertarNuevoRegistro.setString(9, pInformacion.getEstatus() + "");
				super.ejectuarInsercion(insertarNuevoRegistro);
			
				conexionDB.commit();
				
				return pInformacion;
				
		} catch (SQLException e) {
			conexionDB.rollback();
			e.printStackTrace();
			return null;
		}
		finally {
			 
			if (actualizar != null) {
				actualizar.close();
			}
 
			if (insertarNuevoRegistro != null) {
				insertarNuevoRegistro.close();
			}
 
			if (conexionDB != null) {
				conexionDB.close();
			}
		}
	}
	
	/**
	 * Actualizar el nuevo registro que fue insertado, haciendo la discriminacion de persona fisica = 'F' o persona moral ='M'
	 */
	public boolean actualizarDatosFiscalesTipoPersona(InformacionFiscal pInformacion, int pId, char pTipoPersona) throws SQLException{
		
		PreparedStatement actualizarRegistro = null;
		Connection conexionDB = super.getDBConnection();
		// Insertar datos para persona fisica
		
			try {
				conexionDB.setAutoCommit(false);  //Se configura la conexion en false para la transaccion
				
				if (pTipoPersona == 'F') {
					String queryPersonaF = "UPDATE INFORMACION_FISCAL SET NOMBRE = ?, APELLIDO_PATERNO = ?, APELLIDO_MATERNO = ? WHERE ID_INFORMACION_FISCAL = ? ";
					
				actualizarRegistro = conexionDB.prepareStatement(queryPersonaF);
				actualizarRegistro.setString(1, pInformacion.getNombre());
				actualizarRegistro.setString(2, pInformacion.getApellidoPaterno());
				actualizarRegistro.setString(3, pInformacion.getApellidoMaterno());
				actualizarRegistro.setInt(4, pId);
				super.ejectuarActualizacion(actualizarRegistro);
				
				}
		//Insertar datos para persona Moral
		else if (pTipoPersona == 'M') {
			String queryPersonaM = "UPDATE INFORMACION_FISCAL SET 	RAZON_SOCIAL = ? WHERE ID_INFORMACION_FISCAL = ? ";
		
				actualizarRegistro = conexionDB.prepareStatement(queryPersonaM);
				actualizarRegistro.setString(1, pInformacion.getRazonSocial());
				actualizarRegistro.setInt(2, pInformacion.getIdInformacionFiscal());
				super.ejectuarActualizacion(actualizarRegistro);
				
			} 
				conexionDB.commit();
				
				return true;
				
		}catch (Exception e) {
			return false;
		}finally {
 
			if (actualizarRegistro != null) {
				actualizarRegistro.close();
			}
		}
	}
	
	/**
	 * Metodo que pemite buscar un correo electronico 
	 */
	public InformacionFiscal existeCorreo (String pCorreo){
		InformacionFiscal datosFiscales = new InformacionFiscal();
		try{
			PreparedStatement consulta=null;
			String query ="(SELECT CORREO AS CORREO " +
					"FROM INFORMACION_FISCAL " +
					"WHERE CORREO=? )";
			consulta = super.getDBConnection().prepareStatement(query);
			consulta.setString(1, pCorreo);
			ResultSet resultado = super.getResultados(consulta);
			while (resultado.next()) {
				datosFiscales.setCorreo("CORREO");
			}
			return datosFiscales;
		}catch(Exception ex){
			ex.printStackTrace();
			return datosFiscales;
		}
	}
	
	
}
