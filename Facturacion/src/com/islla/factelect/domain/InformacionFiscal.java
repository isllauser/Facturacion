package com.islla.factelect.domain;
// Generated Nov 14, 2012 5:48:46 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * InformacionFiscal generated by hbm2java
 */
public class InformacionFiscal  implements java.io.Serializable {


     private Integer idInformacionFiscal;
     private Emisor emisor;
     private Municipios municipios;
     
     
     private String razonSocial;
     private String nombre;
     private String apellidoPaterno;
     private String apellidoMaterno;
     
     @NotEmpty
     private String direccion;
     
     @NotEmpty
     private String cp;
     
     @NotEmpty
     private String telefono;
     
     @NotEmpty
     private String correo;
     private byte[] logo;
     private char estatus;
     private Date fechaModificacion;
     private Set<Factura> facturas = new HashSet<Factura>(0);

    public InformacionFiscal() {
    }

	
    public InformacionFiscal(Emisor emisor, Municipios municipios, String direccion, String cp, String telefono, char estatus, Date fechaModificacion) {
        this.emisor = emisor;
        this.municipios = municipios;
        this.direccion = direccion;
        this.cp = cp;
        this.telefono = telefono;
        this.estatus = estatus;
        this.fechaModificacion = fechaModificacion;
    }
    public InformacionFiscal(Emisor emisor, Municipios municipios, String razonSocial, String nombre, String apellidoPaterno, String apellidoMaterno, String direccion, String cp, String telefono, String correo, byte[] logo, char estatus, Date fechaModificacion, Set<Factura> facturas) {
       this.emisor = emisor;
       this.municipios = municipios;
       this.razonSocial = razonSocial;
       this.nombre = nombre;
       this.apellidoPaterno = apellidoPaterno;
       this.apellidoMaterno = apellidoMaterno;
       this.direccion = direccion;
       this.cp = cp;
       this.telefono = telefono;
       this.correo = correo;
       this.logo = logo;
       this.estatus = estatus;
       this.fechaModificacion = fechaModificacion;
       this.facturas = facturas;
    }
   
    public Integer getIdInformacionFiscal() {
        return this.idInformacionFiscal;
    }
    
    public void setIdInformacionFiscal(Integer idInformacionFiscal) {
        this.idInformacionFiscal = idInformacionFiscal;
    }
    public Emisor getEmisor() {
        return this.emisor;
    }
    
    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }
    public Municipios getMunicipios() {
        return this.municipios;
    }
    
    public void setMunicipios(Municipios municipios) {
        this.municipios = municipios;
    }
    public String getRazonSocial() {
        return this.razonSocial;
    }
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }
    
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCp() {
        return this.cp;
    }
    
    public void setCp(String cp) {
        this.cp = cp;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public byte[] getLogo() {
        return this.logo;
    }
    
    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    public char getEstatus() {
        return this.estatus;
    }
    
    public void setEstatus(char estatus) {
        this.estatus = estatus;
    }
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    public Set<Factura> getFacturas() {
        return this.facturas;
    }
    
    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }




}


