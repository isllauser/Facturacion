package com.islla.factelect.repository.impl;
/* Fernando Morales Serrano 
 * 23/11/2012
 * Maricruz Reyes Gonzalez
 * 23/11/2012
 * Cometnarios: Integracion del metodo recuperarDatosEmisor() y actualizarContrasenia()
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.EmisorDao;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;

@Repository("emisorDaoSQL")
public class EmisorDaoImpl extends GenericoDao<Emisor, Serializable> implements
		EmisorDao {


	/**
	 * Metodo que permite registrar un Emisor, y su cuenta para el acceso al sistema de facturacion
	 *
	 */
	public boolean registrarEmisor(Emisor pEmisor, InformacionFiscal pInformacionFiscal) throws SQLException{
		PreparedStatement registrarEmisor=null;
		PreparedStatement informacionFiscal=null;
		Connection conexionDB = super.getDBConnection();
		try{
			conexionDB.setAutoCommit(false);
			String query="INSERT INTO EMISOR(rfc_emisor, tippo_persona, passwd, plantilla, estatus, vigencia, rol) " +
					"VALUES (?,?,?,?,?,now(),?)";
			registrarEmisor= conexionDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			registrarEmisor.setString(1,pEmisor.getRfcEmisor());
			registrarEmisor.setString(2,String.valueOf(pEmisor.getTippoPersona()));
			registrarEmisor.setString(3,pEmisor.getPasswd());
			registrarEmisor.setInt(4, pEmisor.getPlantilla());
			registrarEmisor.setString(5,pEmisor.getEstatus());
			registrarEmisor.setString(6,pEmisor.getRol());
			String tipoPersona=String.valueOf(pEmisor.getTippoPersona());
			if(tipoPersona.equals("F")){
				String sqlPersonaFisica="INSERT INTO INFORMACION_FISCAL(ID_INFORMACION_FISCAL, EMISOR_RFC_EMISOR, MUNICIPIOS_ESTADOS_ID_ESTADO, MUNICIPIOS_ID_MUNICIPIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, DIRECCION, CP, TELEFONO, CORREO, LOGO, ESTATUS, FECHA_MODIFICACION) " +
						"VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,now())";
				informacionFiscal=conexionDB.prepareStatement(sqlPersonaFisica, Statement.RETURN_GENERATED_KEYS);
				informacionFiscal.setString(1, pEmisor.getRfcEmisor());				
				informacionFiscal.setInt(2,new Integer(pInformacionFiscal.getMunicipios().getId().getEstadosIdEstado()));
				informacionFiscal.setInt(3,new Integer(pInformacionFiscal.getMunicipios().getId().getIdMunicipio()));
				informacionFiscal.setString(4, pInformacionFiscal.getNombre());
				informacionFiscal.setString(5, pInformacionFiscal.getApellidoPaterno());
				informacionFiscal.setString(6, pInformacionFiscal.getApellidoMaterno());
				informacionFiscal.setString(7, pInformacionFiscal.getDireccion());
				informacionFiscal.setString(8, pInformacionFiscal.getCp());
				informacionFiscal.setString(9, pInformacionFiscal.getTelefono());
				informacionFiscal.setString(10, pInformacionFiscal.getCorreo());
				informacionFiscal.setBytes(11,(pInformacionFiscal.getLogo()));
				informacionFiscal.setString(12, String.valueOf(pInformacionFiscal.getEstatus()));
				registrarEmisor.executeUpdate();
				informacionFiscal.executeUpdate();
				conexionDB.commit();
			}
			else if(tipoPersona.equals("M")){
				String sqlPersonaMoral="INSERT INTO INFORMACION_FISCAL(ID_INFORMACION_FISCAL, EMISOR_RFC_EMISOR, MUNICIPIOS_ESTADOS_ID_ESTADO, MUNICIPIOS_ID_MUNICIPIO, RAZON_SOCIAL, DIRECCION, CP, TELEFONO, CORREO, LOGO, ESTATUS, FECHA_MODIFICACION) " +
						"VALUES (null,?,?,?,?,?,?,?,?,?,?,now())";
				informacionFiscal=conexionDB.prepareStatement(sqlPersonaMoral,Statement.RETURN_GENERATED_KEYS);
				informacionFiscal.setString(1,pEmisor.getRfcEmisor());
				informacionFiscal.setInt(2,new Integer(pInformacionFiscal.getMunicipios().getId().getEstadosIdEstado()));
				informacionFiscal.setInt(3,new Integer(pInformacionFiscal.getMunicipios().getId().getIdMunicipio()));
				informacionFiscal.setString(4, pInformacionFiscal.getRazonSocial());
				informacionFiscal.setString(5, pInformacionFiscal.getDireccion());
				informacionFiscal.setString(6, pInformacionFiscal.getCp());
				informacionFiscal.setString(7, pInformacionFiscal.getTelefono());
				informacionFiscal.setString(8, pInformacionFiscal.getCorreo());
				informacionFiscal.setBytes(9,(pInformacionFiscal.getLogo()));
				informacionFiscal.setString(10, String.valueOf(pInformacionFiscal.getEstatus()));
				registrarEmisor.executeUpdate();
				informacionFiscal.executeUpdate();
				conexionDB.commit();
			}
			
			return true;
			
		}catch(Exception ex){
			conexionDB.rollback();			
			ex.printStackTrace();
			return false;
		}
		finally{
			
			try{
				
				if(registrarEmisor!=null){
					registrarEmisor.close();
				}
				
				if(informacionFiscal!=null){
					informacionFiscal.close();
				}
				
				if(conexionDB!=null){
					conexionDB.close();
				}
			
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Actualiza la plantilla seleccionada del emisor requiere el RFC del emisor
	 * y el id de la nueva plantilla a seleccionar
	 */
	public boolean actualizarPlantilla(Emisor pEmisor) {
		String query = "";
		try {
			query = "update EMISOR set PLANTILLA=? where RFC_EMISOR=? AND EMISOR.ESTATUS='A'";
			PreparedStatement insercion = super.getDBConnection()
					.prepareStatement(query);
			insercion.setInt(1, pEmisor.getPlantilla());
			insercion.setString(2, pEmisor.getRfcEmisor());
			insercion.executeUpdate();

			super.ejectuarInsercion(insercion);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo recuperarContrasenia, recupera el rfc, estatus y correo del emisor,
	 * para validar los datos, que correspondan con los datos que el usuario
	 * introduce.
	 * 
	 * @param rfcEmisor
	 * @return
	 */
	public Emisor recuperarDatosEmisor(String rfcEmisor) {

		Emisor emisor = null;
		InformacionFiscal informacion = null;
		Set<InformacionFiscal> listaInformacion = null;

		String query = " SELECT EMISOR.RFC_EMISOR AS RFCEMISOR, "
				+ " INFO.CORREO                      AS CORREO "
				+ " FROM EMISOR "
				+ " INNER JOIN INFORMACION_FISCAL  INFO  ON  EMISOR.RFC_EMISOR = INFO.EMISOR_RFC_EMISOR "
				+ " WHERE EMISOR.RFC_EMISOR = ?         AND EMISOR.ESTATUS = '1'";
		try {
			PreparedStatement consultar = super.getDBConnection().prepareStatement(query);
			consultar.setString(1, rfcEmisor);
			consultar.execute();

			ResultSet resultado = super.getResultados(consultar);

			emisor = new Emisor();
			informacion = new InformacionFiscal();
			listaInformacion = new HashSet<InformacionFiscal>(0);

			while (resultado.next()) {
				emisor.setRfcEmisor(resultado.getString("RFCEMISOR"));
				informacion.setCorreo(resultado.getString("CORREO"));
				listaInformacion.add(informacion);
			}
			
			emisor.setInformacionFiscals(listaInformacion);
			return emisor;
		} catch (SQLException e) {
			e.printStackTrace();
			return emisor;
		}
	}

	/**
	 * Metodo actualizarContrasenia, actualiza la tabla 'Emisor' con la nueva
	 * contrasenia que se le envia al emisor.
	 * 
	 * @param password
	 * @param rfcEmisor
	 */
	public boolean actualizarContrasenia(String password, String rfcEmisor) {

		String query = " UPDATE EMISOR SET PASSWD = ? WHERE EMISOR.RFC_EMISOR = ?  AND EMISOR.ESTATUS = 'S'";
		try {
			PreparedStatement actualizar = super.getDBConnection().prepareStatement(query);
			actualizar.setString(1, password);
			actualizar.setString(2, rfcEmisor);
			actualizar.executeUpdate();

			super.ejectuarInsercion(actualizar);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	 /** 
	  * Este metodo obtiene las credenciales de un emisor a partir de su RFC y su contraseï¿½a
	  * en caso de que no encuentre el emisor con estos datos entonces el metodo regresarï¿½ null
	  * */
	public Emisor obtenerCredenciales(String pRFC, String pContrasenia) {
		Emisor emisor = new Emisor();

		String consulta = "SELECT RFC_EMISOR,TIPPO_PERSONA,PASSWD,ESTATUS,VIGENCIA FROM EMISOR WHERE RFC_EMISOR = ? AND PASSWD = ?";
		try {


			PreparedStatement query =  super.getDBConnection().prepareStatement(consulta);
			query.setString(1, pRFC);
			query.setString(2, pContrasenia);

			ResultSet rs = query.executeQuery();

			// Reorrido de los elementos del contenidos en la base de datos
			while (rs.next()) {

				//Se asignan a Emisor los valores obtenidos de la consulta 
				emisor.setRfcEmisor(rs.getString(1));
				emisor.setTippoPersona((char) rs.getString(2).charAt(0));
				emisor.setPasswd(rs.getString(3));
				emisor.setEstatus(rs.getString(4));
				emisor.setVigencia(rs.getDate(5));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return emisor;

	}
	
	
	/** 
	  * Este metodo realiza la validacion de la contrasenia actual de la base de datos
	  * con la contrase–a que introduce el emisor al momento del cambio de contrasenia.
	  * Despues de la validacion se ejecuta el metodo Modifica contrasenia
	  * */
	public boolean modificarContrasenia(String pRfc, String pContraseniaActual, String pContraseniaNueva, String pConfirmarContraseniaNueva){
		try{
			Connection conexionDB = super.getDBConnection();
			String contraseniaActualBd=null;
			PreparedStatement preStatement=null;
			ResultSet resultSet=null;
			
			if(pContraseniaNueva.equals(pConfirmarContraseniaNueva)){
				String sql="(SELECT RFC_EMISOR, PASSWD FROM EMISOR WHERE RFC_EMISOR=?)";
				preStatement=conexionDB.prepareStatement(sql);
				preStatement.setString(1, pRfc);
				resultSet=preStatement.executeQuery();
				while(resultSet.next()){
					contraseniaActualBd=resultSet.getString(2);
				}
				if(contraseniaActualBd.equals(pContraseniaActual)){
					if(contraseniaActualBd.equals(pContraseniaNueva)){		
					}
					else{
						modificaContrasenia(pContraseniaNueva, pRfc);
					}
				}
				else{
				}
			}
			else{
			}
			return true;
		}catch(SQLException ex){
			ex.printStackTrace();
			return false;
			
		}
	}
	/** 
	  * Este metodo permite modificar la contrasenia que tiene la BD con la contrasenia que ingresa el Emisor
	  *  
	  * */
	public boolean modificaContrasenia(String pContraseniaNueva, String pRfc){
		try{
			PreparedStatement preStatement=null;
			String SQL="UPDATE EMISOR SET PASSWD=? WHERE RFC_EMISOR=?";
			preStatement= super.getDBConnection().prepareStatement(SQL);
			preStatement.setString(1,pContraseniaNueva) ;
			preStatement.setString(2,pRfc);
			preStatement.executeUpdate();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	
	

}
