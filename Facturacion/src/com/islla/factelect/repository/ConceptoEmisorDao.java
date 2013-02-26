package com.islla.factelect.repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.ConceptoEmisor;

public interface ConceptoEmisorDao {

	
	
	public List buscarConcepto(String descripcion, String rfc);
	
	public ConceptoEmisor insertarConcepto(ConceptoEmisor concepto);
	
	public ConceptoEmisor actualizarConcepto(ConceptoEmisor concepto)throws SQLException;
	
	public ConceptoEmisor buscarUnConcepto(String pDescripcion, String pRfc);
}
