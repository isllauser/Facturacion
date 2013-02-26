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
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.ConceptoEmisorDao;
import com.islla.factelect.domain.ConceptoEmisor;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.UnidadMedida;

/**
 * Clase que implementa la interface ConceptoEmisorDaoI, que contendra las consultas hechas en SQL Standar
 * para la consulta, insercion, actualizacion y eliminacion de registros en la tabla CONCEPTO_EMISOR
 * 
 * */
@Repository("conceptoEmisorDaoSQL")
public class ConceptoEmisorDaoImpl extends GenericoDao<ConceptoEmisor, Serializable> implements ConceptoEmisorDao{

	/**
	 * Este metodo busca los todas las incidencias de los conceptos que pertenecen a un emisor y que esten activos 
	 * el filtro que se usa para obtener los conceptos es el campo de DESCRCIPCION  
     * 
     **/
	public List buscarConcepto(String pDescripcion, String pRfc) {

		List<ConceptoEmisor> conceptos = new ArrayList();
		try {

			String query = " SELECT ID_CONCEPTO_EMISR, " +
									"C.DESCRIPCION, " +
									"C.PRECIO_UNITARIO, " +
									"C.APLICA_IVA, " +
									"C.UNIDAD_MEDIDA_ID, " +
									"EMISOR_RFC_EMISOR " +
									"FROM CONCEPTO_EMISOR C WHERE C.EMISOR_RFC_EMISOR = ? AND C.ESTATUS='A'  AND C.DESCRIPCION LIKE ?";
						   
			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);			
			consulta.setString(1,pRfc);
			consulta.setString(2,pDescripcion + "%");
			
			ResultSet resultado = super.getResultados(consulta);

			ConceptoEmisor concepto;
			while (resultado.next()) {
				
				concepto = new ConceptoEmisor();
				concepto.setEmisor(new Emisor());
				
				concepto.setIdConceptoEmisr(resultado.getInt(1));
				concepto.setDescripcion(resultado.getString(2));
				concepto.setPrecioUnitario(resultado.getBigDecimal(3));
				concepto.setAplicaIva(resultado.getString(4).charAt(0));
				concepto.setUnidadMedida(new  UnidadMedida());
				concepto.getUnidadMedida().setId(resultado.getShort(5));
				concepto.getEmisor().setRfcEmisor(resultado.getString(6));
				
				
				conceptos.add(concepto);
			}
				
			return conceptos;

		} catch (Exception e) {
			e.printStackTrace();
			return conceptos;
		}	
	}

	/**
     * Inserta los datos de un concepto dado un objeto del tipo ConceptoEmisor, 
     * este se inserta con estatus "Activo"
     **/
	public ConceptoEmisor insertarConcepto(ConceptoEmisor pConcepto) {
		ConceptoEmisor concepto=null;
		try {

			String query = " INSERT INTO CONCEPTO_EMISOR (" +
										"EMISOR_RFC_EMISOR, " +
										"DESCRIPCION, " +
										"PRECIO_UNITARIO, " +
										"APLICA_IVA, " +
										"ESTATUS,CODIGO, " +
										"UNIDAD_MEDIDA_ID)" +
										" VALUES(?,?,?,?,?,?,?)";
						   
			PreparedStatement insercion = super.getDBConnection().prepareStatement(query);		
			
			insercion.setString(1,pConcepto.getEmisor().getRfcEmisor());
			insercion.setString(2,pConcepto.getDescripcion());
			insercion.setBigDecimal(3, pConcepto.getPrecioUnitario());
			insercion.setString(4, pConcepto.getAplicaIva()+"");
			insercion.setString(5, "A");
			insercion.setString(6, pConcepto.getCodigo());
			insercion.setShort(7, pConcepto.getUnidadMedida().getId());
		
			
			ResultSet resultado = super.ejectuarInsercion(insercion);
			if (resultado.next()) {
				concepto = pConcepto;
				concepto.setIdConceptoEmisr(resultado.getInt(1));                                              
            } 
			
				
			return concepto;

		} catch (Exception e) {
			e.printStackTrace();
			return concepto;
		}
	}

	/**
     * El proceso que se sigue para modificar los datos de un concepto  es poner en estatus Desactivado "D" el registro actual de la informaci�n del concepto
     * y luego  se inserta un nuevo registro con los datos modificados con estatus Activo "A" 
     **/
	public ConceptoEmisor actualizarConcepto(ConceptoEmisor pConcepto) throws SQLException {
		ConceptoEmisor concepto=null;
		Connection conexionDB = super.getDBConnection();
		PreparedStatement actualizacion =null;
		PreparedStatement insercion = null;
		try {
			
			conexionDB.setAutoCommit(false);  //Se configura la conexi�n en false para la transacci�n
			
			String queryActualizacion = " UPDATE CONCEPTO_EMISOR CONCEPTO SET ESTATUS = 'D' " +
										" WHERE CONCEPTO.ID_CONCEPTO_EMISR= ?";
				
		
			actualizacion = conexionDB.prepareStatement(queryActualizacion);		
			actualizacion.setInt(1,pConcepto.getIdConceptoEmisr());
			super.ejectuarActualizacion(actualizacion);
			
			
			String queryInsercion = " INSERT INTO CONCEPTO_EMISOR " +
												"(EMISOR_RFC_EMISOR, " +
												"DESCRIPCION, " +
												"PRECIO_UNITARIO, " +
												"APLICA_IVA, " +
												"ESTATUS, " +
												"CODIGO, " +
												"UNIDAD_MEDIDA_ID) " +
												"VALUES(?,?,?,?,?,?,?)";
						   
			insercion = conexionDB.prepareStatement(queryInsercion);		
			
			
			insercion.setString(1,pConcepto.getEmisor().getRfcEmisor());
			insercion.setString(2,pConcepto.getDescripcion());
			insercion.setBigDecimal(3, pConcepto.getPrecioUnitario());
			insercion.setString(4, pConcepto.getAplicaIva()+"");
			insercion.setString(5, "A");
			insercion.setString(6, pConcepto.getCodigo());			
			insercion.setShort(7, pConcepto.getUnidadMedida().getId());
						
			
			ResultSet resultado = super.ejectuarInsercion(insercion);
			if (resultado.next()) {
				concepto = pConcepto;
				concepto.setIdConceptoEmisr(resultado.getInt(1));                                              
            } 
			
			conexionDB.commit();
			
			return concepto;

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
	 * Metodo que permite buscar un concepto utilicando como parametro la descripcion del concepto y el rfc del emisor
	 * */
	public ConceptoEmisor buscarUnConcepto(String pDescripcion, String pRfc) {
		ConceptoEmisor concepto=null;
		Connection conexionDB = super.getDBConnection();
		try {

			String query = " SELECT ID_CONCEPTO_EMISR, " +
									"C.DESCRIPCION, " +
									"C.PRECIO_UNITARIO, " +
									"C.APLICA_IVA, " +
									"C.UNIDAD_MEDIDA_ID, " +
									"EMISOR_RFC_EMISOR " +
									"FROM CONCEPTO_EMISOR C WHERE C.EMISOR_RFC_EMISOR = ? AND C.ESTATUS='A'  AND C.DESCRIPCION LIKE ?";
						   
			PreparedStatement consulta = conexionDB.prepareStatement(query);			
			consulta.setString(1,pRfc);
			consulta.setString(2,pDescripcion + "%");
			
			ResultSet resultado =consulta.executeQuery();

			
			while (resultado.next()) {
				
				concepto = new ConceptoEmisor();
				concepto.setEmisor(new Emisor());
				
				concepto.setIdConceptoEmisr(resultado.getInt(1));
				concepto.setDescripcion(resultado.getString(2));
				concepto.setPrecioUnitario(resultado.getBigDecimal(3));
				concepto.setAplicaIva(resultado.getString(4).charAt(0));
				concepto.setUnidadMedida(new  UnidadMedida());
				concepto.getUnidadMedida().setId(resultado.getShort(5));
				concepto.getEmisor().setRfcEmisor(resultado.getString(6));	
			}
			return concepto;

		} catch (Exception e) {
			e.printStackTrace();
			return concepto;
		}	
	}
}
