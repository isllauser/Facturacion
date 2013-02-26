package com.islla.factelect.domain;
// Generated Nov 14, 2012 5:48:46 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


/**
 * Emisor generated by hbm2java
 */
public class Emisor  implements java.io.Serializable {
	 
	
	
	 @NotEmpty
     private String rfcEmisor;
	 
     private char tippoPersona;
     
	 @NotEmpty
     private String passwd;
	 	 
     private int plantilla;
     private String estatus;
     private Date vigencia;
     private String rol;
     private Set<Cliente> clientes = new HashSet<Cliente>(0);
     private Set<Lote> lotes = new HashSet<Lote>(0);
     private Set<InformacionFiscal> informacionFiscals = new HashSet<InformacionFiscal>(0);
     private Set<ConceptoEmisor> conceptoEmisors = new HashSet<ConceptoEmisor>(0);

    public Emisor() {
    }

	
    public Emisor(String rfcEmisor, char tippoPersona, String passwd, int plantilla, String estatus, Date vigencia, String rol) {
        this.rfcEmisor = rfcEmisor;
        this.tippoPersona = tippoPersona;
        this.passwd = passwd;
        this.plantilla = plantilla;
        this.estatus = estatus;
        this.vigencia = vigencia;
        this.rol = rol;
    }
    public Emisor(String rfcEmisor, char tippoPersona, String passwd, int plantilla, String estatus, Date vigencia, String rol, Set<Cliente> clientes, Set<Lote> lotes, Set<InformacionFiscal> informacionFiscals, Set<ConceptoEmisor> conceptoEmisors) {
       this.rfcEmisor = rfcEmisor;
       this.tippoPersona = tippoPersona;
       this.passwd = passwd;
       this.plantilla = plantilla;
       this.estatus = estatus;
       this.vigencia = vigencia;
       this.rol = rol;
       this.clientes = clientes;
       this.lotes = lotes;
       this.informacionFiscals = informacionFiscals;
       this.conceptoEmisors = conceptoEmisors;
    }
   
    public String getRfcEmisor() {
        return this.rfcEmisor;
    }
    
    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }
    public char getTippoPersona() {
        return this.tippoPersona;
    }
    
    public void setTippoPersona(char tippoPersona) {
        this.tippoPersona = tippoPersona;
    }
    public String getPasswd() {
        return this.passwd;
    }
    
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public int getPlantilla() {
        return this.plantilla;
    }
    
    public void setPlantilla(int plantilla) {
        this.plantilla = plantilla;
    }
    public String getEstatus() {
        return this.estatus;
    }
    
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    public Date getVigencia() {
        return this.vigencia;
    }
    
    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }
    public String getRol() {
        return this.rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Set<Cliente> getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    public Set<Lote> getLotes() {
        return this.lotes;
    }
    
    public void setLotes(Set<Lote> lotes) {
        this.lotes = lotes;
    }
    public Set<InformacionFiscal> getInformacionFiscals() {
        return this.informacionFiscals;
    }
    
    public void setInformacionFiscals(Set<InformacionFiscal> informacionFiscals) {
        this.informacionFiscals = informacionFiscals;
    }
    public Set<ConceptoEmisor> getConceptoEmisors() {
        return this.conceptoEmisors;
    }
    
    public void setConceptoEmisors(Set<ConceptoEmisor> conceptoEmisors) {
        this.conceptoEmisors = conceptoEmisors;
    }




}

