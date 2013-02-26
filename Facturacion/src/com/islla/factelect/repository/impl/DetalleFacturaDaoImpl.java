package com.islla.factelect.repository.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.islla.factelect.infra.model.dao.sql.GenericoDao;
import com.islla.factelect.repository.DetalleFacturaDao;
import com.islla.factelect.repository.FacturaDao;
import com.islla.factelect.domain.ConceptoEmisor;
import com.islla.factelect.domain.DetalleFactura;
import com.islla.factelect.domain.Iva;
import com.islla.factelect.domain.UnidadMedida;



@Repository("detalleFacturaDaoSQL")
public class DetalleFacturaDaoImpl extends GenericoDao<DetalleFactura, Serializable> implements DetalleFacturaDao{

	private FacturaDao datosFactura;
	/**
	 * Recuperar los detalles de de factura, asociados a una factura, dado un objeto Factura, adicionalmente enviamos como
	 * parametr el idFactura. 
	 * @param id
	 * @param objFactura
	 * @return
	 */
	public Set <DetalleFactura> consultarDetallesFactura(Integer pId){

				Set <DetalleFactura> detFactArray= new HashSet<DetalleFactura>();
				PreparedStatement buscarFacturaPS=null;
				
				String consultaSQL = 
						"SELECT " +
						"CONCEPTOS.DESCRIPCION       AS  DESCRIPCION, "+
						"CONCEPTOS.PRECIO_UNITARIO   AS  PRECIOUNITARIO, "+
						"CONCEPTOS.CODIGO            AS  CODIGOCONCEPTO, "+
						"DETALLE_FACTURA.CANTIDAD    AS  CANTIDAD, "+
						"UNIDAD_MEDIDA.DESCRIPCION   AS  DESCUNIDADMEDIDA, "+
						"IVA.VALOR                   AS  IVAVALOR, "+
						"IVA.FRONTERIZO              AS  IVAFRONTERIZO "+	
						
						" FROM DETALLE_FACTURA "+
						" INNER JOIN CONCEPTO_EMISOR CONCEPTOS "+
						" ON DETALLE_FACTURA.CONCEPTO_EMISOR_ID_CONCEPTO_EMISR = CONCEPTOS.ID_CONCEPTO_EMISR AND CONCEPTOS.ESTATUS='A' "+
						" INNER JOIN UNIDAD_MEDIDA ON CONCEPTOS.UNIDAD_MEDIDA_ID= UNIDAD_MEDIDA.ID "+
						" INNER JOIN IVA ON DETALLE_FACTURA.IVA_ID = IVA.ID_IVA and IVA.ESTATUS='A'"+ 
						" WHERE DETALLE_FACTURA.FACTURA_ID_FACTURA=?";
				
				try{
					
					buscarFacturaPS=super.getDBConnection().prepareStatement(consultaSQL);
					buscarFacturaPS.setInt(1,pId);	// buscamos la factura por su ID
				
					// execute select SQL statement
					ResultSet resultado= super.getResultados(buscarFacturaPS);
					while (resultado.next()){
						
						DetalleFactura detalleFact = new DetalleFactura();		// creamos un detalle de factura				
						// llenamos los datos  de detalle factura
						detalleFact.setConceptoEmisor(new ConceptoEmisor());
						detalleFact.setIva(new Iva());
						detalleFact.setCantidad(resultado.getBigDecimal("CANTIDAD"));
						
						// llenamos los datos del concepto
						detalleFact.getConceptoEmisor().setDescripcion(resultado.getString("DESCRIPCION"));
						detalleFact.getConceptoEmisor().setPrecioUnitario(resultado.getBigDecimal("PRECIOUNITARIO"));
						detalleFact.getConceptoEmisor().setCodigo(resultado.getString("CODIGOCONCEPTO"));
						// llenamos los datos de iva
						detalleFact.getIva().setValor(resultado.getBigDecimal("IVAVALOR"));
						detalleFact.getIva().setFronterizo(resultado.getString("IVAFRONTERIZO").charAt(0));
						// llenamos los datos de la unidad de medida
						detalleFact.getConceptoEmisor().setUnidadMedida(new UnidadMedida());
						detalleFact.getConceptoEmisor().getUnidadMedida().setDescripcion(resultado.getString("DESCUNIDADMEDIDA"));
						// agregamos el detalle al arreglo
						detFactArray.add(detalleFact);
						
					}
				}catch(SQLException e){
					System.out.println("Error ->"+e.getMessage());
					return null;
				}
		
	return 	detFactArray;
	}
	
	
	/**
	 * Metodo que permite Modificar el detalle de una factura, utilizando como parametros el id del concepto, el id de la factura y el objeto modificarDetalle
	 * donde se guardan los datos que se van a modificar
	 * 
	 * */
	public boolean insertarDetalleFactura(int idConcepto, int idFactura, DetalleFactura modificarDetalle){
		try{
			
			Connection conexionDB =super.getDBConnection();
			
			PreparedStatement insertar=null;
			String sql="INSERT INTO DETALLE_FACTURA " +
					"(ID_DETALLE_FACTURA, " +
					"FACTURA_ID_FACTURA, " +
					"CONCEPTO_EMISOR_ID_CONCEPTO_EMISR, " +
					"CANTIDAD, " +
					"IVA_ID) VALUES(null,?,?,?,?) ";
			insertar=conexionDB.prepareStatement(sql);
			insertar.setInt(1, idFactura);
			insertar.setInt(2, idConcepto);
			insertar.setBigDecimal(3,  modificarDetalle.getCantidad());
			insertar.setInt(4,modificarDetalle.getIva().getIdIva());
			super.ejectuarInsercion(insertar);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	
}
