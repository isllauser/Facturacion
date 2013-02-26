package com.islla.factelect.repository;

import java.io.Serializable;
import java.sql.SQLException;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.InformacionFiscal;

public interface InformacionFiscalDao {
	
	
	public InformacionFiscal obtenerDatosFiscales(String rfc);
	
	public InformacionFiscal obtenerDatosFiscalesAct(String pRfc);
	
	public InformacionFiscal actualizarDatosFiscales(InformacionFiscal pInformacion, int pIdInformacion) throws SQLException;
	
	public boolean actualizarDatosFiscalesTipoPersona(InformacionFiscal pInformacion, int pId, char pTipoPersona) throws SQLException;
	
	public InformacionFiscal existeCorreo (String pCorreo);

}
