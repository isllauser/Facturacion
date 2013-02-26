package com.islla.factelect.repository;

import java.io.Serializable;
import java.util.Set;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.DetalleFactura;
import com.islla.factelect.domain.Factura;
import com.islla.factelect.domain.InformacionFiscal;

public interface DetalleFacturaDao {

	public Set <DetalleFactura> consultarDetallesFactura(Integer pId);
	public boolean insertarDetalleFactura(int idConcepto, int idFactura, DetalleFactura modificarDetalle);
}
