package com.islla.factelect.repository.impl;
/* Fernando Morales Serrano 
 * 23/11/2012
 * 
 */
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.stereotype.Repository;
import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.IvaDao;
import com.islla.factelect.domain.Iva;

/**
 * Clase que implementa la interface IvaDaoI, que contendra las consultas hechas en SQL Standar
 * para la consulta, insercion, actualizaci—n y eliminaci—n de registros en la tabla IVA 
 * 
 * */
@Repository("ivaDaoSQL")
public class IvaDaoImpl extends GenericoDao<Iva, Serializable> implements IvaDao{

	
	/**
	 *Este metodo obtiene el iva vigente en zonas no fronterizas
     **/
	public Iva obtenerIvaVigente() {
		Iva iva = new Iva();
		try {

			String query = "SELECT * FROM IVA WHERE IVA.ESTATUS = 'A' " +
						   " AND IVA.FRONTERIZO = 'N'";

			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);
			
			
			ResultSet resultado = super.getResultados(consulta);

			
			if (resultado.next()) {
				iva.setIdIva(resultado.getInt(1));
				iva.setDescripcion(resultado.getString(2));
				iva.setValor(resultado.getBigDecimal(3));
				iva.setEstatus(resultado.getString(4).charAt(0));
				iva.setFronterizo(resultado.getString(4).charAt(0));
			}
		
			return iva;
		} catch (Exception e) {
			e.printStackTrace();
			return iva;
		}
	}

	/**
	 *Este metodo obtiene el iva vigente en zonas  fronterizas
     **/
	public Iva obtenerIvaVigenteFronterizo() {
		Iva iva = new Iva();
		try {

			String query = "SELECT * FROM IVA WHERE IVA.ESTATUS = 'A' " +
						   " AND IVA.FRONTERIZO = 'S'";

			PreparedStatement consulta = super.getDBConnection().prepareStatement(query);
			
			
			ResultSet resultado = super.getResultados(consulta);

			
			if (resultado.next()) {
				iva.setIdIva(resultado.getInt(1));
				iva.setDescripcion(resultado.getString(2));
				iva.setValor(resultado.getBigDecimal(3));
				iva.setEstatus(resultado.getString(4).charAt(0));
				iva.setFronterizo(resultado.getString(4).charAt(0));
			}
		
			return iva;
		} catch (Exception e) {
			e.printStackTrace();
			return iva;
		}
	}

	

	
	
	

}
