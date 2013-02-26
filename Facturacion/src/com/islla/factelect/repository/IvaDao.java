package com.islla.factelect.repository;

import java.io.Serializable;
import java.util.List;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.Cliente;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Iva;

public interface IvaDao {

	
	
	public Iva obtenerIvaVigente();
	
	public Iva obtenerIvaVigenteFronterizo();
	
}
