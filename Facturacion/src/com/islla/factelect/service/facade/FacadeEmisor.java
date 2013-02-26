package com.islla.factelect.service.facade;

import java.sql.SQLException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;
import com.islla.factelect.helper.Encriptador;
import com.islla.factelect.repository.EmisorDao;
import com.islla.factelect.repository.InformacionFiscalDao;

@Service("facadeEmisor")
public class FacadeEmisor {
	
	
	@Resource(name="emisorDaoSQL")
	EmisorDao emisorDao;
	
	
	@Resource(name="informacionFiscalDaoSQL")
	InformacionFiscalDao informacionDao;
	
	public boolean existeRfc(String rfcEmisor){
		Emisor emisor= emisorDao.recuperarDatosEmisor(rfcEmisor);
		if(emisor.getRfcEmisor()==null){
			return false;
		}
		return true;
	}
	
	public boolean existeCorreo(String pCorreoEmisor){
		InformacionFiscal informacion=informacionDao.existeCorreo(pCorreoEmisor);
		if(informacion.getCorreo()==null){
			return false;
		}
		return true;
	}
	
	
	public boolean registrarEmisor(Emisor emisor, InformacionFiscal informacionFiscal) throws SQLException{
		
		Encriptador encriptador = new Encriptador();
			
			emisor.setPasswd(encriptador.encriptarPassword(emisor.getPasswd()));
			emisor.setPlantilla(1);
			emisor.setEstatus("1");  //Por ahora se activa el usuario pero tiene que validar la cuenta desde su correo electronico
			emisor.setRol("ROLE_ADMIN");
		
			informacionFiscal.setEstatus('S');
			
			return emisorDao.registrarEmisor(emisor, informacionFiscal);
	}
}

