package com.islla.factelect.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;
import com.islla.factelect.service.ServiceEmisor;
import com.islla.factelect.service.facade.FacadeEmisor;

@Service("emisorService")
public class ServiceEmisorImpl implements ServiceEmisor {
	@Resource(name="facadeEmisor")
	FacadeEmisor facadeEmisor;
	
	@Override
	public int registrarEmisor(Emisor emisor,InformacionFiscal informacionFiscal) throws SQLException{
		
		if(facadeEmisor.existeCorreo(informacionFiscal.getCorreo())){
			return 3;
		}
		else{
			if(facadeEmisor.existeRfc(emisor.getRfcEmisor())){
				return 1;
			}
			else{
				if(facadeEmisor.registrarEmisor(emisor, informacionFiscal)){
					return 0;
				}
				else{
					return 2;
				}
			}
		}
		
	}
	
} 
