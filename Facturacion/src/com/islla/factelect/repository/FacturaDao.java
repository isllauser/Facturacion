package com.islla.factelect.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.Factura;

public interface FacturaDao extends GenericoDaoI<Factura,Serializable>{

	
	
	public Factura insertarFactura(Factura factura)throws SQLException;
	
	public Factura consultarDatosDetalleFactura(Integer id);
	
	public int buscarFactura(String pRFCCliente, int pFolioFactura, String pRFCEmisor);	
	
	public boolean actualizarEstatusFactura(int pIdFactura);
	
	public List buscarFactura(Factura pFactura, Date fecInicio, Date fecFinal, int min, int max);
	
	public int totalBuscarFactura(Factura pFactura, Date fecInicio, Date fecFinal);
	
	public Factura actualizarFactura(int id, Factura pDatosModificados)  throws SQLException;
	
	public Factura obtenerDatosFactura(Integer idFactura);
	
}
