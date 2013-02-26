package com.islla.factelect.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.InformacionFiscal;
import com.islla.factelect.domain.Municipios;
import com.islla.factelect.domain.MunicipiosId;
import com.islla.factelect.domain.City;
import com.islla.factelect.domain.State;

import com.islla.factelect.repository.EmisorDao;
import com.islla.factelect.service.ServiceEmisor;
import com.islla.factelect.service.GeoService;


@Controller
@RequestMapping("/*")
public class EmisorController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(EmisorController.class);
	
	private GeoService geoService;
	
	RegistroForm registro = new RegistroForm(new Emisor(), new InformacionFiscal());
	
	@Resource(name="emisorService")
	ServiceEmisor emisorService;

	
	@RequestMapping(value = "/registro.html" /*, method = RequestMethod.GET*/)
	public ModelAndView registro(ModelMap model)
	{
		
		return new ModelAndView("viewEmisor/registro", "registroEmisorForm1", new RegistroForm());
	}
	
	
	@RequestMapping(value = "/addRegistro", method = RequestMethod.POST)
	public String registrarEmisor(HttpServletRequest request,@Valid @ModelAttribute("registroEmisorForm1") RegistroForm emisor, BindingResult resultado) throws SQLException, IOException
	{
		if(!resultado.hasErrors()){
			 //Se obtienen los datos del Emisor
			  registro.setEmisor(emisor.getEmisor());
			  
			  //Se obtienen los datos Fiscales del Emisor
			  registro.setInformacionFiscal(emisor.getInformacionFiscal());
			  
			  registro.setInformacionFiscal(emisor.getInformacionFiscal());
			  registro.getInformacionFiscal().setMunicipios(new Municipios());
			  registro.getInformacionFiscal().getMunicipios().setId(new MunicipiosId());
			  registro.getInformacionFiscal().getMunicipios().getId().setEstadosIdEstado("1");
			  registro.getInformacionFiscal().getMunicipios().getId().setIdMunicipio("1");
			  
			//Insertar y validar una imagen usando Spring
			  MultipartFile multipartFile=emisor.getFile();
			  String fileName="";
			  fileName=multipartFile.getOriginalFilename();
			  if(multipartFile!=null){
				  if(multipartFile.getSize()>512000){
					  resultado.rejectValue("file", "file.required");
					  return "viewEmisor/registro";
				  }
				  if(!(fileName.endsWith(".jpg")||fileName.endsWith(".gif")||fileName.endsWith(".jpeg")||fileName.endsWith(".png")||fileName.endsWith(""))){
					  resultado.rejectValue("file", "extencion.required");
					  return "viewEmisor/registro";
				  }
				  fileName = multipartFile.getOriginalFilename();
				  registro.getInformacionFiscal().setLogo(multipartFile.getBytes());
				}
			  
			  int respuesta= emisorService.registrarEmisor(registro.getEmisor(), registro.getInformacionFiscal());
			  if(respuesta==0){
				  System.out.println("Se inserto exitosamente");
				  return "index";
			  }
			  else if(respuesta==1){
				  System.out.println("No se inserto el registro: "+respuesta+" Por motivo de duplica de RFC.");
				  resultado.rejectValue("emisor.rfcEmisor", "rfc.required");
				  return "viewEmisor/registro";
			  }
			  else if(respuesta==2){
				  System.out.println("Nada se inserto: "+respuesta);
				  return "viewEmisor/registro";
			  }
			  else if(respuesta==3){
				  System.out.println("No se inserto el registro:"+respuesta+" Por motivo de que el correo ya se habia agregado en la base de datos.");
				  resultado.rejectValue("informacionFiscal.correo", "correo.requiered");
				  return "viewEmisor/registro";
			  }
		}
		else
			return "viewEmisor/registro";
		return "index";
		
		 
	}
	
	@RequestMapping(value = "/index.html")
	public String inicio(){
	   return "index";
	}
	
	@RequestMapping(value = "/facturas/index.html", method = RequestMethod.GET)
	public String menu(ModelMap model){
		System.out.print("menu");
	   return "menu";
	}
	
	@RequestMapping(value = "/delegmunicipio", method = RequestMethod.GET)
	public @ResponseBody Set<City> citiesForState(@RequestParam(value = "stateName", required = true) String state) {
		logger.debug("finding cities for state " + state);
		System.out.println("Hola estoy en cities");
		return this.geoService.findCitiesForState(state);
	}

	@RequestMapping(value = "/estados", method = RequestMethod.GET)
	public @ResponseBody Set<State> findAllStates() {
		logger.debug("finding all states");
		System.out.println("Hola estoy en states");
		return this.geoService.findAllStates();
	}

	
}        
