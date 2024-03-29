package com.islla.factelect.domain;
// Generated Nov 14, 2012 5:48:46 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Municipios generated by hbm2java
 */
public class Municipios  implements java.io.Serializable {


     private MunicipiosId id;
     private Estados estados;
     private String nombre;
     private String zonaFronteriza;
     private Set<InformacionFiscal> informacionFiscals = new HashSet<InformacionFiscal>(0);
     private Set<Cliente> clientes = new HashSet<Cliente>(0);
     private Set<Factura> facturas = new HashSet<Factura>(0);

    public Municipios() {
    }

	
    public Municipios(MunicipiosId id, Estados estados, String nombre, String zonaFronteriza) {
        this.id = id;
        this.estados = estados;
        this.nombre = nombre;
        this.zonaFronteriza = zonaFronteriza;
    }
    public Municipios(MunicipiosId id, Estados estados, String nombre, String zonaFronteriza, Set<InformacionFiscal> informacionFiscals, Set<Cliente> clientes, Set<Factura> facturas) {
       this.id = id;
       this.estados = estados;
       this.nombre = nombre;
       this.zonaFronteriza = zonaFronteriza;
       this.informacionFiscals = informacionFiscals;
       this.clientes = clientes;
       this.facturas = facturas;
    }
   
    public MunicipiosId getId() {
        return this.id;
    }
    
    public void setId(MunicipiosId id) {
        this.id = id;
    }
    public Estados getEstados() {
        return this.estados;
    }
    
    public void setEstados(Estados estados) {
        this.estados = estados;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getZonaFronteriza() {
        return this.zonaFronteriza;
    }
    
    public void setZonaFronteriza(String zonaFronteriza) {
        this.zonaFronteriza = zonaFronteriza;
    }
    public Set<InformacionFiscal> getInformacionFiscals() {
        return this.informacionFiscals;
    }
    
    public void setInformacionFiscals(Set<InformacionFiscal> informacionFiscals) {
        this.informacionFiscals = informacionFiscals;
    }
    public Set<Cliente> getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    public Set<Factura> getFacturas() {
        return this.facturas;
    }
    
    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }




}


