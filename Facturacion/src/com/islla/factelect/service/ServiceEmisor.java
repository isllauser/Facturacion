package com.islla.factelect.service;

import java.sql.SQLException;

import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;

public interface ServiceEmisor {
	public int registrarEmisor(Emisor emisor, InformacionFiscal informacionFiscal) throws SQLException;
}