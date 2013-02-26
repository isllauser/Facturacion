package com.islla.factelect.repository;

import java.io.Serializable;
import java.util.Set;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.Lote;

public interface LoteDao  {

	
	public boolean tieneFolios(String rfc);
		
	public int obtenerFolioSiguiente(String rfc);
	
	public boolean insertarFolios(Lote pLote);
	
	public Set <Lote> consultarFolios(String pRFC);
	
	public boolean eliminarLoteFolios(Lote plote);
	
}
