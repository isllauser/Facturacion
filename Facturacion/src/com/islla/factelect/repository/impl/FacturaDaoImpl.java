package com.islla.factelect.repository.impl;
/* Fernando Morales Serrano 
 * 23/11/2012
 * 
 */
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.DetalleFacturaDao;
import com.islla.factelect.repository.FacturaDao;
import com.islla.factelect.repository.LoteDao;
import com.islla.factelect.domain.Cliente;
import com.islla.factelect.domain.ConceptoEmisor;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Estados;
import com.islla.factelect.domain.Factura;
import com.islla.factelect.domain.InformacionFiscal;
import com.islla.factelect.domain.Lote;
import com.islla.factelect.domain.Municipios;


/**
 * Clase que implementa la interface FacturaDaoI, que contendra las consultas hechas en SQL Standar
 * para la consulta, insercion, actualizaci�n y eliminaci�n de registros en la tabla FACTURA y LOTE cuando se requiera
 * 
 * */
@Repository("facturaDaoSQL")
public class FacturaDaoImpl extends GenericoDao<Factura, Serializable> implements FacturaDao{

	
	@Autowired
	@Qualifier("loteDaoSQL")
	private LoteDao loteDao;
	
	@Autowired
	@Qualifier("detalleFacturaDaoSQL")
	private DetalleFacturaDao detalleDao;
	
	/**
	 * Este metodo inserta los datos de una factura, primero obtiene el siguiente folio a utilizarse despues inicia una transaccion  
     * actualiza el siguiente folio a utilizarse y luego inserta los datos de la factura
     **/
	public Factura insertarFactura(Factura pfactura) throws SQLException {
		ConceptoEmisor conceptoInsertado=null;
		Connection conexionDB = super.getDBConnection();
		PreparedStatement actualizacion =null;
		PreparedStatement insercion = null;
		Factura factura = null;
		try {
			
			int folio = loteDao.obtenerFolioSiguiente(pfactura.getInformacionFiscal().getEmisor().getRfcEmisor());
			
			conexionDB.setAutoCommit(false);
			
			String queryActualizacion = " UPDATE LOTE   FOLIO_SIGUIENTE = FOLIO_SIGUIENTE + 1 WHERE LOTE.ID_LOTE= ?";
			
		
			actualizacion = conexionDB.prepareStatement(queryActualizacion);		
			actualizacion.setInt(1,pfactura.getLote().getIdLote());
			super.ejectuarActualizacion(actualizacion);
			
			
			String queryInsercion = " INSERT INTO FACTURA (MUNICIPIOS_ESTADOS_ID_ESTADO, " +
									" MUNICIPIOS_ID_MUNICIPIO, CLIENTE_ID_CLIENTE,  	" +
									" INFORMACION_FISCAL_ID_INFORMACION_FISCAL, " +
									" LOTE_ID_LOTE, " +
									" FOLIO,SERIE, " +
									" FECHA_EXPEDICION, " +
									" IVA,TOTAL," +
									" PLANTILLA,ESTATUS)" +
						   " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
						   
			insercion = conexionDB.prepareStatement(queryInsercion);		
			
			
			insercion.setInt(1,new Integer(pfactura.getMunicipios().getId().getEstadosIdEstado()));
			insercion.setInt(2,new Integer(pfactura.getMunicipios().getId().getIdMunicipio()));
			insercion.setInt(3, pfactura.getCliente().getIdCliente());
			insercion.setInt(4,pfactura.getInformacionFiscal().getIdInformacionFiscal() );
			insercion.setInt(5, pfactura.getLote().getIdLote());
			insercion.setInt(6,folio);			
			insercion.setString(7, pfactura.getSerie());
			
			Timestamp fechaExpedicion = new Timestamp(pfactura.getFechaExpedicion().getTime());
			insercion.setTimestamp(8, fechaExpedicion);
			insercion.setBigDecimal(9, pfactura.getIva());
			insercion.setBigDecimal(10, pfactura.getTotal());
			insercion.setInt(11, pfactura.getPlantilla());
			insercion.setString(12, "A");
			
			
						
			
			ResultSet resultado = super.ejectuarInsercion(insercion);
			if (resultado.next()) {
				factura = pfactura;
				factura.setIdFactura(resultado.getInt(1));                                              
            } 
			
			
			conexionDB.commit();
			
			return factura;

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
	 * Metodo que permite modificar una factura que ya ha sido emitida, utilizando como parametro el id la factura Actual y un objeto Emisor 
	 * donde contiene los registros que va a modificar el emisor.
	 * */
	public Factura actualizarFactura(int id, Factura pDatosModificados)  throws SQLException{
		Factura facturaActual =new Factura();
		PreparedStatement insertar=null;
		PreparedStatement desactivarFactura=null;
		Connection conexionDB =super.getDBConnection();
		ResultSet resultado=null;
		Factura factura= new Factura();
		try{
			conexionDB.setAutoCommit(false);
			//Obtener Datos actuales de la Factura
			factura=obtenerDatosFactura(id);
			//conocerIva(pDatosModificados);
			String modificarFactura="INSERT INTO FACTURA(ID_FACTURA, " +
					"MUNICIPIOS_ID_MUNICIPIO, " +
					"MUNICIPIOS_ESTADOS_ID_ESTADO, " +
					"CLIENTE_ID_CLIENTE, " +
					"INFORMACION_FISCAL_ID_INFORMACION_FISCAL, " +
					"LOTE_ID_LOTE, " +
					"FOLIO, " +
					"SERIE, " +
					"FECHA_EXPEDICION, " +
					"IVA, " +
					"TOTAL, " +
					"PLANTILLA, " +
					"ESTATUS ) " +
					"VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			String sql="UPDATE FACTURA SET ESTATUS='D' " +
					"WHERE FACTURA.ID_FACTURA=? ";
			desactivarFactura=conexionDB.prepareStatement(sql);
			desactivarFactura.setInt(1,id);
			desactivarFactura.executeUpdate();
			//Insertar datos de la tabla factura
			insertar=conexionDB.prepareStatement(modificarFactura);
			insertar.setInt(1, new Integer( pDatosModificados.getMunicipios().getId().getIdMunicipio()));
			insertar.setInt(2, new Integer(pDatosModificados.getMunicipios().getId().getEstadosIdEstado()));
			insertar.setInt(3, pDatosModificados.getCliente().getIdCliente());
			insertar.setInt(4, factura.getInformacionFiscal().getIdInformacionFiscal());
			insertar.setInt(5, factura.getLote().getIdLote());
			insertar.setInt(6, factura.getFolio());
			insertar.setString(7, factura.getSerie());
			java.util.Date hoy = new java.util.Date();
			hoy.getTime();
			Timestamp fechaActualizacion = new Timestamp(hoy.getTime());
			insertar.setTimestamp(8,fechaActualizacion);
			insertar.setBigDecimal(9, pDatosModificados.getIva());
			insertar.setBigDecimal(10, pDatosModificados.getTotal());
			insertar.setInt(11, factura.getPlantilla());
			insertar.setString(12, "A");
			resultado= super.ejectuarInsercion(insertar);
			if (resultado.next()) {
				facturaActual = pDatosModificados;
				facturaActual.setIdFactura(resultado.getInt(1));
            } 
			conexionDB.commit();
			return facturaActual;
		}catch(Exception ex){
			conexionDB.rollback();
			ex.printStackTrace();
			return facturaActual;
		}
		finally {
			 
			if (desactivarFactura != null) {
				desactivarFactura.close();
			}
 
			if (insertar != null) {
				insertar.close();
			}
 
			if (conexionDB != null) {
				conexionDB.close();
			}
		}
	}
	
	
	/**
	 * datosFactura, recuperamos los datos de emision y del cliente de la factura, dado un IdFactura
	 * @param id
	 * @return
	 */
	public Factura consultarDatosDetalleFactura(Integer id){
		
		Factura datosFactura = new Factura();
		String query = 
				"SELECT ESTFAC.NOMBRE  AS ESTADOFACTURA, " +
				" MUNFAC.NOMBRE               AS MUNICIPIOFACTURA, " +
				" FACTURA.FECHA_EXPEDICION    AS FECHAEXPEDICION, " +
				" FACTURA.FOLIO               AS FOLIO, " +
				" FACTURA.ESTATUS             AS ESTATUS, " +
				" CLIENTE.RFC_CLIENTE         AS RFCCLIENTE, " +
				" CLIENTE.NOMBRE_RAZON_SOCIAL AS NOMBRERAZONSOCIAL, " +
				" ESTCLIENTE.NOMBRE           AS ESATDOCLIENTE, " +
				" MUNCLIENTE.NOMBRE           AS MUNICIPIOCLIENTE, " +
				" CLIENTE.DIRECCION           AS DIRECCION, " +
				" CLIENTE.CP                  AS CP, " +
				" CLIENTE.TELEFONO            AS TELEFONO, " +
				" CLIENTE.CORREO              AS CORREO " +
				" FROM FACTURA "+ 
				" INNER JOIN MUNICIPIOS MUNFAC     ON FACTURA.MUNICIPIOS_ID_MUNICIPIO = MUNFAC.ID_MUNICIPIO " +
				" INNER JOIN ESTADOS ESTFAC        ON MUNFAC.ESTADOS_ID_ESTADO = ESTFAC.ID_ESTADO " +
				" INNER JOIN CLIENTE               ON FACTURA.CLIENTE_ID_CLIENTE = CLIENTE.ID_CLIENTE" +
				" INNER JOIN MUNICIPIOS MUNCLIENTE ON CLIENTE.MUNICIPIOS_ID_MUNICIPIO = MUNCLIENTE.ID_MUNICIPIO " +
				" INNER JOIN ESTADOS ESTCLIENTE    ON MUNCLIENTE.ESTADOS_ID_ESTADO = ESTCLIENTE.ID_ESTADO " +
				" WHERE FACTURA.ID_FACTURA  = ? ";
		
		try {
			PreparedStatement consultarDatosFactura = super.getDBConnection().prepareStatement(query);
			consultarDatosFactura.setInt(1, id);
			
			ResultSet resultado = super.getResultados(consultarDatosFactura);
			
			
			datosFactura.setMunicipios(new Municipios());
			datosFactura.getMunicipios().setEstados(new Estados());
			datosFactura.setCliente(new Cliente());
			datosFactura.getCliente().setMunicipios(new Municipios());
			datosFactura.getCliente().getMunicipios().setEstados(new Estados());
			
			while(resultado.next()){
				// Recuparar datos emision de la factura y establecer como valor de los atributos de la entidad Factura
				
				datosFactura.getMunicipios().getEstados().setNombre(resultado.getString("ESTADOFACTURA"));
				datosFactura.getMunicipios().setNombre(resultado.getString("MUNICIPIOFACTURA"));
				datosFactura.setFechaExpedicion(resultado.getDate("FECHAEXPEDICION"));
				datosFactura.setFolio(resultado.getInt("FOLIO"));
				char estatus = (char)resultado.getString("ESTATUS").charAt(0);
				datosFactura.setEstatus(estatus);
				// Recupera datos Cliente y establecer como valor de los atributos de la entidad Factura
				datosFactura.getCliente().setRfcCliente(resultado.getString("RFCCLIENTE"));
				datosFactura.getCliente().setNombreRazonSocial(resultado.getString("NOMBRERAZONSOCIAL"));
				datosFactura.getCliente().getMunicipios().getEstados().setNombre(resultado.getString("ESATDOCLIENTE"));
				datosFactura.getCliente().getMunicipios().setNombre(resultado.getString("MUNICIPIOCLIENTE"));
				datosFactura.getCliente().setDireccion(resultado.getString("DIRECCION"));
				datosFactura.getCliente().setCp(resultado.getString("CP"));
				datosFactura.getCliente().setTelefono(resultado.getString("TELEFONO"));
				datosFactura.getCliente().setCorreo(resultado.getString("CORREO"));
				
			}
			
			datosFactura.setDetalleFacturas(detalleDao.consultarDetallesFactura(id));
			
		}catch (Exception e){
			e.printStackTrace();
			return datosFactura;
		}return datosFactura;
	}
	
	
	/**
	 *Este metodo se encarga de obtener la informacion fiscal del emisor, 
	 *la informacion fiscal del cliente y datos de la factura, dado un ID de factura
	 **/
	
	public Factura obtenerDatosFactura(Integer idFactura){
		
		Factura factura= new Factura();

		String consultaSQL = 
		" SELECT FACTURA.FOLIO                AS FOLIO,"+
		" FACTURA.LOTE_ID_LOTE 				  AS IDLOTE," +
		" FACTURA.IVA						  AS IVAFACTURA, "+
		" FACTURA.SERIE                       AS SERIEFACTURA,  " +
		" FACTURA.PLANTILLA 				  AS PLANTILLAFACTURA, " +
		" FACTURA.ESTATUS					  AS ESTATUSFACTURA, " +
		
		" FACTURA.FECHA_EXPEDICION            AS FECHAEXP," +
		
		" INFORMACION_FISCAL.ID_INFORMACION_FISCAL    AS IDINFOFISCAL,"+
		" INFORMACION_FISCAL.NOMBRE           AS NOMBREEMISOR,"+
		" INFORMACION_FISCAL.APELLIDO_PATERNO AS APPATEMISOR,"+
        " INFORMACION_FISCAL.APELLIDO_MATERNO AS APPMATEMISOR,"+
        " EMISOR.RFC_EMISOR                   AS RFCEMISOR,"+
        " INFORMACION_FISCAL.DIRECCION         	AS DIREMISOR,"+
        " ESTEMISOR.NOMBRE                     	AS ESTADOEMISOR,"+
        " MUNIEMISOR.NOMBRE           			AS MUNICIPIOEMISOR,"+
	    " INFORMACION_FISCAL.CP       			AS CPEMISOR,"+
	    " INFORMACION_FISCAL.TELEFONO 			AS TELEMISOR,"+
	    " INFORMACION_FISCAL.LOGO                AS LOGOEMISOR,"+ // TOMAMOS EL LOGO DEL EMISOR
	    
       " CLIENTE.NOMBRE_RAZON_SOCIAL AS NOMRAZONCLIENTE, "+
       " CLIENTE.RFC_CLIENTE         AS RFCCLIENTE,"+
       " ESTCLIENTE.NOMBRE           AS ESTADOCLIENTE,"+
       " MUNICLIENTE.NOMBRE 	   	 AS MUNICIPIOCLIENTE," +
       
       " CLIENTE.ID_CLIENTE	  		 AS IDCLIENTE,"+
       " CLIENTE.DIRECCION	  		 AS DIRECCIONCLIENTE,"+
       " CLIENTE.CP                  AS CPCLIENTE,"+
       " CLIENTE.TELEFONO            AS TELCLIENTE,"+
       " CLIENTE.correo 			 AS CORREOCLIENTE,"+		
          
       " MUNFACTURA.NOMBRE           AS MUNICIPIOFAC,"+
       " ESTFACTURA.NOMBRE           AS EDOFACTURA, "+
       " LOTE.NUMERO_APROBACION      AS NUMEROAPROBACION "+
     
	   " FROM FACTURA"+
	   " INNER JOIN INFORMACION_FISCAL"+
	   " ON FACTURA.INFORMACION_FISCAL_ID_INFORMACION_FISCAL= INFORMACION_FISCAL.ID_INFORMACION_FISCAL"+
	   " INNER JOIN EMISOR"+
	   " ON INFORMACION_FISCAL.EMISOR_RFC_EMISOR =EMISOR.RFC_EMISOR"+
	//  MUNICIPIO Y ESTADO DEL EMISOR 
		" INNER JOIN MUNICIPIOS MUNIEMISOR "+
		" ON INFORMACION_FISCAL.MUNICIPIOS_ID_MUNICIPIO = MUNIEMISOR.ID_MUNICIPIO "+
		" INNER JOIN ESTADOS ESTEMISOR "+
		" ON MUNIEMISOR.ESTADOS_ID_ESTADO = ESTEMISOR.ID_ESTADO "+
		" INNER JOIN CLIENTE"+ 
		" ON FACTURA.CLIENTE_ID_CLIENTE = CLIENTE.ID_CLIENTE"+
	//	MUNICIPIO Y ESTADO DEL CLIENTE
		
		" INNER JOIN MUNICIPIOS MUNICLIENTE "+
		" ON CLIENTE.MUNICIPIOS_ID_MUNICIPIO = MUNICLIENTE.ID_MUNICIPIO "+ 
		" INNER JOIN ESTADOS ESTCLIENTE "+
		" ON  ESTCLIENTE.ID_ESTADO=MUNICLIENTE.ESTADOS_ID_ESTADO "+
	
	//	MUNICIPIO Y ESTADO DE LA FACTURA
		" INNER JOIN MUNICIPIOS MUNFACTURA "+
		" ON FACTURA.MUNICIPIOS_ID_MUNICIPIO = MUNFACTURA.ID_MUNICIPIO "+  
		" INNER JOIN ESTADOS ESTFACTURA "+
		" ON ESTFACTURA.ID_ESTADO=MUNFACTURA.ESTADOS_ID_ESTADO " +
		
		" INNER JOIN LOTE "+
		" ON FACTURA.LOTE_ID_LOTE=LOTE.ID_LOTE "+
	
		" WHERE FACTURA.ESTATUS ='a' AND FACTURA.ID_FACTURA= ? ";
		
		try{
	
			PreparedStatement buscarFactura=super.getDBConnection().prepareStatement(consultaSQL);
			buscarFactura.setInt(1,idFactura);	// buscamos la factura por su ID
		
			// execute select SQL statement
			ResultSet resultado=super.getResultados(buscarFactura); 
					
			factura.setCliente(new Cliente());
			factura.setMunicipios(new Municipios());
			factura.getCliente().setMunicipios(new Municipios());
			factura.getCliente().getMunicipios().setEstados(new Estados());
			factura.setInformacionFiscal(new InformacionFiscal());
			factura.getInformacionFiscal().setMunicipios(new Municipios());
			factura.getInformacionFiscal().getMunicipios().setEstados(new Estados());
			factura.getInformacionFiscal().setEmisor(new Emisor());
			factura.setLote(new Lote());
			factura.setMunicipios(new Municipios());
			factura.getMunicipios().setEstados(new Estados());
			
			while (resultado.next()){
				factura.setPlantilla(resultado.getInt("PLANTILLAFACTURA"));
				factura.getCliente().setIdCliente(resultado.getInt("IDCLIENTE"));
				factura.getCliente().setRfcCliente(resultado.getString("RFCCLIENTE"));
				factura.getCliente().setNombreRazonSocial(resultado.getString("NOMRAZONCLIENTE"));// nombre Cliente 
				factura.getCliente().setDireccion(resultado.getString ("DIRECCIONCLIENTE"));// direccion Cliente
				factura.getCliente().setCp(resultado.getString("CPCLIENTE"));
				factura.getCliente().setTelefono(resultado.getString("TELCLIENTE"));
				factura.getCliente().setCorreo(resultado.getString("CORREOCLIENTE"));
				factura.getCliente().getMunicipios().setNombre(resultado.getString("MUNICIPIOCLIENTE"));
				factura.getCliente().getMunicipios().getEstados().setNombre(resultado.getString("ESTADOCLIENTE"));
				factura.getInformacionFiscal().setIdInformacionFiscal(resultado.getInt("IDINFOFISCAL"));
				factura.getInformacionFiscal().setNombre(resultado.getString("NOMBREEMISOR"));
				factura.getInformacionFiscal().setApellidoPaterno(resultado.getString("APPATEMISOR"));		
				factura.getInformacionFiscal().setApellidoMaterno(resultado.getString("APPMATEMISOR"));
				factura.getInformacionFiscal().setDireccion(resultado.getString("DIREMISOR"));		
				factura.getInformacionFiscal().setCp(resultado.getString("CPEMISOR"));
				factura.getInformacionFiscal().setTelefono(resultado.getString("TELEMISOR"));
				//obtenemos el logo y lo convertimos a un arreglo de bytes 
				Blob logoEmisor=resultado.getBlob("LOGOEMISOR");
				int blobLenght= (int)logoEmisor.length();
				byte []bytesArray=logoEmisor.getBytes(1,blobLenght);
				factura.getInformacionFiscal().setLogo(bytesArray);
				factura.getInformacionFiscal().getMunicipios().setNombre(resultado.getString("MUNICIPIOEMISOR"));
				factura.getInformacionFiscal().getMunicipios().getEstados().setNombre(resultado.getString("ESTADOEMISOR"));
				factura.getInformacionFiscal().getEmisor().setRfcEmisor(resultado.getString("RFCEMISOR"));
				factura.setFolio(resultado.getInt("FOLIO"));
				factura.setFechaExpedicion(resultado.getDate("FECHAEXP"));
				factura.setIva(resultado.getBigDecimal("IVAFACTURA"));
				factura.setSerie(resultado.getString("SERIEFACTURA"));
				factura.getLote().setIdLote(resultado.getInt("IDLOTE"));
				factura.getLote().setNumeroAprobacion(resultado.getString("NUMEROAPROBACION"));
				factura.getMunicipios().setNombre(resultado.getString("MUNICIPIOFAC"));
				factura.getMunicipios().getEstados().setNombre(resultado.getString("EDOFACTURA"));
			}
			
			factura.setDetalleFacturas(detalleDao.consultarDetallesFactura(idFactura));
			
		}catch (Exception e){
			e.printStackTrace();
			return factura;
		}return factura;
	}

	/**
	 * Este metodo busca solamente el id de la factura por el RFC del Cliente
	 * el folio de la factura y el RFC del Emisor.
	 * */
	public int buscarFactura(String pRFCCliente, int pFolioFactura, String pRFCEmisor) {
		
		int idFactura=0;
		int idCliente=0;
		String query = "SELECT id_cliente FROM CLIENTE WHERE RFC_CLIENTE=? and EMISOR_RFC_EMISOR=? and estatus='A'";
		try {
			
			PreparedStatement consultar=super.getDBConnection().prepareStatement(query);
			
			consultar.setString(1, pRFCCliente);
			consultar.setString(2, pRFCEmisor);
			consultar.execute();
			ResultSet resultado = consultar.executeQuery();
			while (resultado.next()){
				idCliente=resultado.getInt(1); 
			}
		
		 query="";
		 query = "SELECT ID_FACTURA FROM FACTURA WHERE FOLIO=? and CLIENTE_ID_CLIENTE=? and estatus='A'";
		
			PreparedStatement consultarFactura=super.getDBConnection().prepareStatement(query);

			consultarFactura.setInt(1, pFolioFactura);
			consultarFactura.setInt(2, idCliente);
			consultarFactura.execute();
			 resultado = consultarFactura.executeQuery();
			while (resultado.next()){
				
				idFactura=resultado.getInt(1); 
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return idFactura;

	}

	
	public boolean actualizarEstatusFactura(int pIdFactura) {
		String query =" UPDATE FACTURA SET ESTATUS = 'C' WHERE ID_FACTURA = ?";
		try {
			
			PreparedStatement consultarFactura=super.getDBConnection().prepareStatement(query);

			consultarFactura.setInt(1, pIdFactura);
			consultarFactura.execute();
			ResultSet resultado = consultarFactura.executeQuery();
			return true;

		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	 /**
     * Busca una factura mediante filtros de busqueda requiere os siguientes datos completos o alguna parte:
     * Folio,nombreo razon social del cliente, rfc del cliente, estatus de la factura.
     * Y un rango de fechas depende como ingresa el emisor dichas fechas en el combo a buscar
     * Regresa una lista de facturas con el filtro 
     */ 
    
    public List buscarFactura(Factura pFactura, Date fecInicio, Date fecFinal, int min, int max) {
    	List<Factura> facturas = new ArrayList();
    	PreparedStatement consulta; 
    	ResultSet resultado;
    	String query;
    	Factura factura;
    	char estatus;
    	
    	try {
    		 
    		query = "SELECT FACTURA.ID_FACTURA, FACTURA.ESTATUS, FACTURA.FECHA_EXPEDICION, FACTURA.TOTAL, FACTURA.FOLIO, " +
    				"CLIENTE.ID_CLIENTE, CLIENTE.NOMBRE_RAZON_SOCIAL, CLIENTE.RFC_CLIENTE " +
    				"FROM FACTURA " +
    				"INNER JOIN CLIENTE on FACTURA.CLIENTE_ID_CLIENTE = CLIENTE.ID_CLIENTE " +
    				"WHERE FACTURA.FOLIO LIKE ? " +
    				"AND CLIENTE.NOMBRE_RAZON_SOCIAL LIKE ? " +
    				"AND CLIENTE.RFC_CLIENTE LIKE ? "+
    				"AND FACTURA.ESTATUS LIKE ? " +
    				"AND FACTURA.FECHA_EXPEDICION BETWEEN ? AND ? " +
    				"ORDER BY FACTURA.ID_FACTURA ASC LIMIT ?,?"; 
    		
    		consulta = super.getDBConnection().prepareStatement(query);
    		
    		consulta.setString(1,pFactura.getFolio() + "%");
    		consulta.setString(2,pFactura.getCliente().getNombreRazonSocial() + "%");
    		consulta.setString(3,pFactura.getCliente().getRfcCliente() + "%");
    		consulta.setString(4,pFactura.getEstatus() + "%");
    		consulta.setDate(5,fecInicio);
    		consulta.setDate(6,fecFinal);
    		consulta.setInt(7,min);
    		consulta.setInt(8,max);
  	
    		resultado = super.getResultados(consulta);   		
    		
    		while (resultado.next()) {
				factura = new Factura();	
				factura.setIdFactura( resultado.getInt(1) );				
				estatus = (char)resultado.getString(2).charAt(0);
				factura.setEstatus(estatus);
				factura.setFechaExpedicion( resultado.getDate(3) );
				factura.setTotal( resultado.getBigDecimal(4) );
				factura.setFolio( resultado.getInt(5) );
				factura.setCliente( new Cliente() );			
				factura.getCliente().setIdCliente( resultado.getInt(6) );
				factura.getCliente().setNombreRazonSocial( resultado.getString(7) );
				factura.getCliente().setRfcCliente( resultado.getNString(8) );		
				facturas.add(factura);
			}
    	
    		 return facturas;   		 
        } catch (SQLException e) {
            e.printStackTrace();
            return facturas;  
        }
    	
    }	
	
    
    /**
     * Metodo que regresa el total de las facturas a buscar la manejar consulta de Buscar Factura
     * El metodo requiere como parametos fecha inicial y fecha final,
     * Regresa el total de las factura con los criterios buscados
     */
    
    public int totalBuscarFactura(Factura pFactura, Date fecInicio, Date fecFinal) {
    	PreparedStatement consulta; 
    	ResultSet resultado;
    	String query;
    	int totalFacturas = 0; 
    	
    	try {
    			query = "SELECT COUNT(FACTURA.ID_FACTURA) "+
        				"FROM FACTURA " +
        				"INNER JOIN CLIENTE ON FACTURA.CLIENTE_ID_CLIENTE = CLIENTE.ID_CLIENTE " +
        				"WHERE FACTURA.FOLIO LIKE ? " +
        				"AND CLIENTE.NOMBRE_RAZON_SOCIAL LIKE ? " +
        				"AND CLIENTE.RFC_CLIENTE LIKE ? "+
        				"AND FACTURA.ESTATUS LIKE ? " +
        				"AND FACTURA.FECHA_EXPEDICION BETWEEN ? AND ? ";
        consulta = super.getDBConnection().prepareStatement(query);
		
		consulta.setString(1,pFactura.getFolio() + "%");
		consulta.setString(2,pFactura.getCliente().getNombreRazonSocial() + "%");
		consulta.setString(3,pFactura.getCliente().getRfcCliente() + "%");
		consulta.setString(4,pFactura.getEstatus() + "%");
		consulta.setDate(5,fecInicio);
		consulta.setDate(6,fecFinal);
		
		resultado = super.getResultados(consulta);  

    	 while (resultado.next()) {
    		 totalFacturas =  resultado.getInt(1);
    		  
    	 }
    	return totalFacturas;
        
  		 
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return totalFacturas;
    	}
    }    
	
}
