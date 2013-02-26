package com.islla.factelect.repository;

import java.io.Serializable;
import java.sql.SQLException;

import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Factura;
import com.islla.factelect.domain.InformacionFiscal;

public interface EmisorDao {

	public boolean actualizarPlantilla(Emisor pEmisor);
	
	public Emisor recuperarDatosEmisor(String rfcEmisor);
	
	public boolean actualizarContrasenia(String password, String rfcEmisor);
	
	public Emisor obtenerCredenciales(String pRFC, String pContrasenia);
	
	public boolean registrarEmisor(Emisor pEmisor, InformacionFiscal pInformacionFiscal) throws SQLException;
	
	public boolean modificarContrasenia(String pRfc, String pContraseniaActual, String pContraseniaNueva, String pConfirmarContraseniaNueva);

	public boolean modificaContrasenia(String pContraseniaNueva, String pRfc);
}
