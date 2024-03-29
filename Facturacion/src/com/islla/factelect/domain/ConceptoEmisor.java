package com.islla.factelect.domain;
// Generated Nov 14, 2012 5:48:46 PM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * ConceptoEmisor generated by hbm2java
 */
public class ConceptoEmisor  implements java.io.Serializable {


     private Integer idConceptoEmisr;
     private Emisor emisor;
     private UnidadMedida unidadMedida;
     private String descripcion;
     private BigDecimal precioUnitario;
     private char aplicaIva;
     private char estatus;
     private String codigo;
     private Set<DetalleFactura> detalleFacturas = new HashSet<DetalleFactura>(0);

    public ConceptoEmisor() {
    }

	
    public ConceptoEmisor(Emisor emisor, UnidadMedida unidadMedida, String descripcion, BigDecimal precioUnitario, char aplicaIva, char estatus) {
        this.emisor = emisor;
        this.unidadMedida = unidadMedida;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.aplicaIva = aplicaIva;
        this.estatus = estatus;
    }
    public ConceptoEmisor(Emisor emisor, UnidadMedida unidadMedida, String descripcion, BigDecimal precioUnitario, char aplicaIva, char estatus, String codigo, Set<DetalleFactura> detalleFacturas) {
       this.emisor = emisor;
       this.unidadMedida = unidadMedida;
       this.descripcion = descripcion;
       this.precioUnitario = precioUnitario;
       this.aplicaIva = aplicaIva;
       this.estatus = estatus;
       this.codigo = codigo;
       this.detalleFacturas = detalleFacturas;
    }
   
    public Integer getIdConceptoEmisr() {
        return this.idConceptoEmisr;
    }
    
    public void setIdConceptoEmisr(Integer idConceptoEmisr) {
        this.idConceptoEmisr = idConceptoEmisr;
    }
    public Emisor getEmisor() {
        return this.emisor;
    }
    
    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }
    public UnidadMedida getUnidadMedida() {
        return this.unidadMedida;
    }
    
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecioUnitario() {
        return this.precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public char getAplicaIva() {
        return this.aplicaIva;
    }
    
    public void setAplicaIva(char aplicaIva) {
        this.aplicaIva = aplicaIva;
    }
    public char getEstatus() {
        return this.estatus;
    }
    
    public void setEstatus(char estatus) {
        this.estatus = estatus;
    }
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Set<DetalleFactura> getDetalleFacturas() {
        return this.detalleFacturas;
    }
    
    public void setDetalleFacturas(Set<DetalleFactura> detalleFacturas) {
        this.detalleFacturas = detalleFacturas;
    }




}


