package com.islla.factelect.web;

import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;
//import com.islla.factelect.domain.Municipios;
//import com.islla.factelect.domain.MunicipiosId;



public class RegistroForm {
	
	 @Valid
	 private Emisor emisor;
	 @Valid
	 private InformacionFiscal informacionFiscal;
	 
	/* private Municipios municipio;
	 private MunicipiosId municipiosid;*/
	 
	 private String confirmarRFC;
	 private String confirmarPassword;
	 
	 
	 private MultipartFile file;
	 
	 
	 
	 public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public RegistroForm(){}
	 
	 public RegistroForm(Emisor emisor, InformacionFiscal informacionFiscal) 
	 {
		super();
		this.emisor = emisor;
		this.informacionFiscal = informacionFiscal;
	 }
	 
	/*public Municipios getMunicipio() {
		return municipio;
	}

    public void setMunicipio(Municipios municipio) {
		this.municipio = municipio;
	}*/



	/*public MunicipiosId getMunicipiosid() {
		return municipiosid;
	}*/



	/*public void setMunicipiosid(MunicipiosId municipiosid) {
		this.municipiosid = municipiosid;
	}*/



	public String getConfirmarRFC() 
	{
	    return confirmarRFC;
	}

    public void setConfirmarRFC(String confirmarRFC)
	{
		this.confirmarRFC = confirmarRFC;
	}

	public String getConfirmarPassword() {
		return confirmarPassword;
	}

	public void setConfirmarPassword(String confirmarPassword) {
		this.confirmarPassword = confirmarPassword;
	}

		
    public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	public InformacionFiscal getInformacionFiscal() {
		return informacionFiscal;
	}

	public void setInformacionFiscal(InformacionFiscal informacionFiscal) {
		this.informacionFiscal = informacionFiscal;
	}
	 
}
