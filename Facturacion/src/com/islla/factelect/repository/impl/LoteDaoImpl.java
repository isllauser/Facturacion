package com.islla.factelect.repository.impl;
/* Fernando Morales Serrano 
 * 23/11/2012
 * Maricruz Reyes Gonzalez
 * 28/11/2012
 * Comentarios: Se integra el metodo insertarFolios  y consultarFolios
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;

import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.LoteDao;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Lote;

/**
 * Clase que implementa la interface LoteDaoI, que contendra las consultas hechas en SQL Standar
 * para la consulta, insercion, actualizaci�n y eliminaci�n de registros principalmente de la tabla LOTE
 * 
 * */
@Repository("loteDaoSQL")
public class LoteDaoImpl extends GenericoDao<Lote, Serializable> implements LoteDao {
	
	private PreparedStatement preparedStatement = null;
	private Connection connect = null;

	/**
     * Metodo que regresa true o false dado un rfc del Emisor para saber si tiene o no folios disponibles
     **/
	public boolean tieneFolios(String pRfc) {
		try {

			String query = "SELECT LOTE.FOLIO_SIGUIENTE FROM LOTE WHERE LOTE.ESTATUS = 'A' " +
						   " AND LOTE.FECHA_VIGENCIA > NOW() AND LOTE.EMISOR_RFC_EMISOR= ? AND LOTE.FOLIO_SIGUIENTE <> 0";

			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);
			
			consulta.setString(1, pRfc);
			ResultSet resultado = super.getResultados(consulta);

			if (resultado.next())
				return true;
			else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo que regresa el siguiente folio a utilizar dado el rfc del emisor, en caso de no haber regresa un cero "0"
     **/
	public int obtenerFolioSiguiente(String pRfc) {
		try {

			String query = "SELECT LOTE.FOLIO_SIGUIENTE FROM LOTE WHERE LOTE.ESTATUS = 'A' " +
						   " AND LOTE.FECHA_VIGENCIA > NOW() AND LOTE.EMISOR_RFC_EMISOR= ? AND LOTE.FOLIO_SIGUIENTE<>0 ORDER BY LOTE.FECHA_APROBACION ASC LIMIT 1";

			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);
			
			consulta.setString(1, pRfc);
			ResultSet resultado = super.getResultados(consulta);

			if (resultado.next())
				return resultado.getInt(1);
			else
				return 0;
			

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * insertar un lote de folios
	 * 
	 */
	public boolean insertarFolios(Lote pLote){
		
		Lote lote = null;
		Timestamp fechaAprobacion = new Timestamp(pLote.getFechaAprobacion().getTime());
		Timestamp fechaVigencia = new Timestamp(pLote.getFechaVigencia().getTime());
		
		String query = "insert into lote(ID_LOTE, " +
				" EMISOR_RFC_EMISOR, " +
				" FOLIO_INICIAL, " +
				" FOLIO_FINAL, " +
				" FOLIO_SIGUIENTE, " +
				" FECHA_APROBACION, " +
				" FECHA_VIGENCIA, " +
				" SERIE_LOTE, " +
				" ESTATUS, " +
				" NUMERO_APROBACION) " +
				" values (null,?,?,?,?,?,?,?,?,?)";
		try{
			PreparedStatement insertar = super.getDBConnection().prepareStatement(query);
			insertar.setString(1, pLote.getEmisor().getRfcEmisor());
			insertar.setInt(2, pLote.getFolioFinal());
			insertar.setInt(3, pLote.getFolioInicial());
			insertar.setInt(4, pLote.getFolioSiguiente());
			insertar.setTimestamp(5, fechaAprobacion);
			insertar.setTimestamp(6, fechaVigencia);
			insertar.setString(7, pLote.getSerieLote());
			String estatus = pLote.getEstatus()+"";
			insertar.setString(8, estatus);
			insertar.setString(9, pLote.getNumeroAprobacion());
			super.ejectuarInsercion(insertar);
			return true;
		}catch(SQLException e){ 
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Consultar lotes de folios, dado el rfc de un emisor.
	 * 
	 * 
	 */
	public Set <Lote> consultarFolios(String pRFC){
		
		Lote lote = null;
		Set<Lote> seLotes = new HashSet<Lote>(0);
		
		String query = "SELECT LOTE.NUMERO_APROBACION AS NUMERO_APROBACION, " +
				" LOTE.FOLIO_INICIAL                  AS FOLIOINICIAL, " +
				" LOTE.FOLIO_FINAL 					  AS FOLIOFINAL, " +
				" LOTE.FOLIO_SIGUIENTE 				  AS FOLIOSIGUIENTE, " +
				" LOTE.FECHA_APROBACION 			  AS FECHAAPROBACION, " +
				" LOTE.FECHA_VIGENCIA 				  AS FECHAVIGENCIA, " +
				" LOTE.SERIE_LOTE 					  AS SERIE, " +
				" LOTE.EMISOR_RFC_EMISOR 			  AS RFCEMISOR, " +
				" LOTE.IDLOTE 						  AS ID" + 
				" FROM  EMISOR " +
				" INNER JOIN LOTE ON EMISOR.RFC_EMISOR = LOTE.EMISOR_RFC_EMISOR" + 
				" WHERE LOTE.EMISOR_RFC_EMISOR = ? ";
		try{
			PreparedStatement consultar = super.getDBConnection().prepareStatement(query);
			consultar.setString(1, pRFC);
			consultar.execute();
			
			ResultSet resultado = super.getResultados(consultar);
			
			while (resultado.next()) {
				
				lote = new Lote();
				lote.setNumeroAprobacion(resultado.getString("NUMERO_APROBACION"));
				lote.setFolioInicial(resultado.getInt("FOLIOINICIAL"));
				lote.setFolioFinal(resultado.getInt("FOLIOFINAL"));
				lote.setFolioSiguiente(resultado.getInt("FOLIOSIGUIENTE"));
				lote.setFechaAprobacion(resultado.getDate("FECHAAPROBACION"));
				lote.setFechaVigencia(resultado.getDate("FECHAVIGENCIA"));
				lote.setSerieLote(resultado.getString("SERIE"));
				lote.setEmisor(new Emisor());
				lote.getEmisor().setRfcEmisor(resultado.getString("RFCEMISOR"));
				lote.setIdLote(resultado.getInt("ID"));
				
				seLotes.add(lote);
			}
			
			return seLotes;
		}catch(SQLException e){
			e.printStackTrace();
			return seLotes;
		}
	}
	
	
	 /**
     * Elimina un lote de folios del emisor previemente consultado por el emisor
     * Para eliminar el lote no debe existir alguna factura activa.
     * requiere el RFC del emisor y el id del lote a eliminar 
     */
    
    public boolean eliminarLoteFolios(Lote plote) {
    	boolean banderaEliminar;
    	String query;
    	PreparedStatement consulta;
    	ResultSet resultado;
    	
    	try {
    		banderaEliminar = true; // por defecto es verdadera para que elimine el lote de folio 
    		query = "select FACTURA.ID_FACTURA from FACTURA " +
    				"left join LOTE on FACTURA.LOTE_ID_LOTE = LOTE.ID_LOTE " +
    				"where LOTE_ID_LOTE =? and FACTURA.ESTATUS in ('A','a') ";
    		consulta = super.getDBConnection().prepareStatement(query);   		
    		consulta.setInt(1,plote.getIdLote());
    		
    		resultado = super.getResultados(consulta);
    		
    		 while (resultado.next()) {
                 //System.out.println("Lote ocupado, no esposible eliminarlo con ID_FACTURA = " + resultado.getInt(1) );
                 banderaEliminar = false; // si entra al ciclo es por que existe alguna factura emitida y no puede eliminar el lote de folios
             }
    		   		 
    		if(banderaEliminar) { // entra al ciclo donde cambia el el estatus del lote de folio 
    			query = "update LOTE set ESTATUS='D' where EMISOR_RFC_EMISOR=? and ID_LOTE=?";
    			consulta = super.getDBConnection().prepareStatement(query);
    			consulta.setString(1, plote.getEmisor().getRfcEmisor());
    			consulta.setInt(2, plote.getIdLote());
    			super.ejectuarInsercion(consulta);
    			//System.out.println("******** eliminado ********");
    			
            }// fin del if
    		
    		return banderaEliminar;
    		
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    }
	
	
}
