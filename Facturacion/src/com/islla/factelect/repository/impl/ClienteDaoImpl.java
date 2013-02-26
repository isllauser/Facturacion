package com.islla.factelect.repository.impl;
/* Fernando Morales Serrano 
 * 23/11/2012
 * 
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.ClienteDao;
import com.islla.factelect.domain.Cliente;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Municipios;
import com.islla.factelect.domain.MunicipiosId;


/**
 * Clase que implementa la interface ClienteDaoI, que contendra las consultas hechas en SQL Standar
 * para la consulta, insercion, actualizaci—n y eliminaci—n de registros en la tabla CLIENTE
 * 
 * */
@Repository("clienteDaoSQL")
public class ClienteDaoImpl extends GenericoDao<Cliente, Serializable> implements ClienteDao{

	/**
     * Inserta los datos de un cliente dado un objeto del tipo cliente, 
     * este se inserta con estatus "Activo"
     **/
	public Cliente insertarCliente(Cliente pCliente) {
		
		Cliente cliente=null;
		try {

			String query = " INSERT INTO CLIENTE " +
										"(MUNICIPIOS_ESTADOS_ID_ESTADO, " +
										"MUNICIPIOS_ID_MUNICIPIO, " +
										"EMISOR_RFC_EMISOR, " +
										"RFC_CLIENTE, " +
										"NOMBRE_RAZON_SOCIAL, " +
										"DIRECCION, " +
										"CP, " +
										"TELEFONO, " +
										"CORREO, " +
										"ESTATUS,  " +
										"FECHA_MODIFICACION)" +
										" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
						   
			PreparedStatement insercion = super.getDBConnection().prepareStatement(query);		
			
			insercion.setInt(1,new Integer(pCliente.getMunicipios().getId().getEstadosIdEstado()));
			insercion.setInt(2,new Integer(pCliente.getMunicipios().getId().getIdMunicipio()));
			insercion.setString(3, pCliente.getEmisor().getRfcEmisor());
			insercion.setString(4, pCliente.getRfcCliente());
			insercion.setString(5, pCliente.getNombreRazonSocial());
			insercion.setString(6, pCliente.getDireccion());
			insercion.setString(7, pCliente.getCp());
			insercion.setString(8, pCliente.getTelefono());
			insercion.setString(9, pCliente.getCorreo());
			insercion.setString(10, "A");
			
			Timestamp fechaActualizacion = new Timestamp(pCliente.getFechaModificacion().getTime());
			
			insercion.setTimestamp(11, fechaActualizacion);
			
			
			ResultSet resultado = super.ejectuarInsercion(insercion);
			if (resultado.next()) {
				cliente = pCliente;
				cliente.setIdCliente(resultado.getInt(1));                                              
            } 
			
				
			return cliente;

		} catch (Exception e) {
			e.printStackTrace();
			return cliente;
		}
	}

	
	/**
     * El proceso que se sigue para modificar los datos de un cliente  es poner en estatus Desactivado "D" el registro actual de la informaci—n del cliente
     * y luego  se inserta un nuevo registro con los datos modificados con estatus Activo "A" 
     **/
	public Cliente actualizarCliente(Cliente pCliente) throws SQLException {
		Cliente cliente=null;
		Connection conexionDB = super.getDBConnection();
		PreparedStatement actualizacion =null;
		PreparedStatement insercion = null;
		try {
			
			conexionDB.setAutoCommit(false);
			
			String queryActualizacion = " UPDATE CLIENTE SET ESTATUS = 'D' WHERE CLIENTE.ID_CLIENTE = ?";
			
		
			actualizacion = conexionDB.prepareStatement(queryActualizacion);		
			actualizacion.setInt(1,pCliente.getIdCliente());
			super.ejectuarActualizacion(actualizacion);
			
			
			String queryInsercion = " INSERT INTO CLIENTE " +
												"(MUNICIPIOS_ESTADOS_ID_ESTADO, " +
												"MUNICIPIOS_ID_MUNICIPIO, " +
												"EMISOR_RFC_EMISOR, " +
												"RFC_CLIENTE, " +
												"NOMBRE_RAZON_SOCIAL, " +
												"DIRECCION, " +
												"CP, " +
												"TELEFONO, " +
												"CORREO, " +
												"ESTATUS,  " +
												"FECHA_MODIFICACION)" +
												" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
						   
			insercion = conexionDB.prepareStatement(queryInsercion);		
			
			insercion.setInt(1,new Integer(pCliente.getMunicipios().getId().getEstadosIdEstado()));
			insercion.setInt(2,new Integer(pCliente.getMunicipios().getId().getIdMunicipio()));
			insercion.setString(3, pCliente.getEmisor().getRfcEmisor());
			insercion.setString(4, pCliente.getRfcCliente());
			insercion.setString(5, pCliente.getNombreRazonSocial());
			insercion.setString(6, pCliente.getDireccion());
			insercion.setString(7, pCliente.getCp());
			insercion.setString(8, pCliente.getTelefono());
			insercion.setString(9, pCliente.getCorreo());
			insercion.setString(10, "A");
			
			Timestamp fechaActualizacion = new Timestamp(pCliente.getFechaModificacion().getTime());
			
			insercion.setTimestamp(11, fechaActualizacion);
			
			
			ResultSet resultado = super.ejectuarInsercion(insercion);
			if (resultado.next()) {
				cliente = pCliente;
				cliente.setIdCliente(resultado.getInt(1));                                              
            } 
			
			conexionDB.commit();
			
			return cliente;

		} catch (Exception e) {
			conexionDB.rollback();
			e.printStackTrace();
			return null;
		}finally {
 
			if (actualizacion != null) {
				actualizacion.close();
			}
 
			if (insercion != null) {
				insercion.close();
			}
 
			if (conexionDB != null) {
				conexionDB.close();
			}
 
		}
	}
	
	
	/**
     * Este metodo busca los todas las incidencias de clientes dado ya sea por el RFC o la raz—n social, 
     * regresa una lista de Cliente que coincidan con los filtros especificados.
     **/
	public List buscarCliente(Cliente pCliente) {
		List<Cliente> clientes = new ArrayList();
		try {

			String query = " SELECT C.ID_CLIENTE, " +
								   "C.MUNICIPIOS_ESTADOS_ID_ESTADO, " +
								   "C.MUNICIPIOS_ID_MUNICIPIO, " +
								   "C.EMISOR_RFC_EMISOR, " +
								   "C.RFC_CLIENTE, " +
								   "C.NOMBRE_RAZON_SOCIAL, " +
								   "C.DIRECCION, " +
								   "C.CP, " +
								   "C.TELEFONO, " +
								   "C.CORREO " +
								   "FROM CLIENTE C WHERE C.EMISOR_RFC_EMISOR = ? AND ";
			
			String nomRazonSocial ="";
			
			String rfc ="";
			
			if(pCliente.getNombreRazonSocial()!=null && !pCliente.getNombreRazonSocial().isEmpty()) {
				nomRazonSocial = "  C.NOMBRE_RAZON_SOCIAL like ? ";
				query = query + nomRazonSocial;
			}
		
			if(pCliente.getRfcCliente()!=null && !pCliente.getRfcCliente().isEmpty()) {
				
				if(nomRazonSocial!=null && !nomRazonSocial.isEmpty()) {
					query = query + " AND ";
				}
					rfc = " C.RFC_CLIENTE like ? ";
					
					query = query + rfc;
				
			}
						   
			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);			
			consulta.setString(1,pCliente.getEmisor().getRfcEmisor());
			
			if(pCliente.getNombreRazonSocial()!=null && !pCliente.getNombreRazonSocial().isEmpty()) {
				consulta.setString(2, pCliente.getNombreRazonSocial() + "%");
			}
			
			if(pCliente.getRfcCliente()!=null && !pCliente.getRfcCliente().isEmpty()) {
				if(nomRazonSocial!=null && !nomRazonSocial.isEmpty()) {
					consulta.setString(3, pCliente.getRfcCliente() +"%");
				}
					consulta.setString(2, pCliente.getRfcCliente()+"%");
			}			
						
			ResultSet resultado = super.getResultados(consulta);

			Cliente cliente;
			while (resultado.next()) {
				
				cliente = new Cliente();
				cliente.setMunicipios(new Municipios());
				cliente.getMunicipios().setId(new MunicipiosId());
				cliente.setIdCliente(resultado.getInt(1));
				cliente.getMunicipios().getId().setEstadosIdEstado(new Integer(resultado.getInt(2)).toString());
				cliente.getMunicipios().getId().setIdMunicipio(new Integer(resultado.getInt(3)).toString());
				cliente.setEmisor(new Emisor());
				cliente.getEmisor().setRfcEmisor(resultado.getString(4));
				cliente.setRfcCliente(resultado.getString(5));
				cliente.setNombreRazonSocial(resultado.getString(6));
				cliente.setDireccion(resultado.getString(7));
				cliente.setCp(resultado.getString(8));
				cliente.setTelefono(resultado.getString(9));
				cliente.setCorreo(resultado.getString(10));
				
				clientes.add(cliente);
			}
				
			return clientes;

		} catch (Exception e) {
			e.printStackTrace();
			return clientes;
		}
	}

	/**
	 * Metodo que recupera el correo del cliente dado un ID del cliente.
	 */	
	public String buscarCorreo(int idCliente){
		
		String consultaSQL ="SELECT CLIENTE.CORREO "+
		"FROM CLIENTE "+
		"WHERE CLIENTE.ID_CLIENTE = ? AND CLIENTE.ESTATUS = 'A' ";
		String correo="";
		
		try{
			PreparedStatement buscarCorreo=super.getDBConnection().prepareStatement(consultaSQL);
			buscarCorreo.setInt(1,idCliente);	// buscamos la factura por su ID
			ResultSet resultado=super.getResultados(buscarCorreo); 
			//extraccion de datos desde el ResultSet
			while (resultado.next()){	
				correo= resultado.getString("CORREO");
			}	
		}catch(Exception e){
			e.printStackTrace();
			return correo;
		}
		return correo;
	}


	

}
