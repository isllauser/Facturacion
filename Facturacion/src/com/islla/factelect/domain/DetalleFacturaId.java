package com.islla.factelect.domain;
// Generated Nov 14, 2012 5:48:46 PM by Hibernate Tools 3.2.1.GA



/**
 * DetalleFacturaId generated by hbm2java
 */
public class DetalleFacturaId  implements java.io.Serializable {


     private int idDetalleFactura;
     private int facturaIdFactura;

    public DetalleFacturaId() {
    }

    public DetalleFacturaId(int idDetalleFactura, int facturaIdFactura) {
       this.idDetalleFactura = idDetalleFactura;
       this.facturaIdFactura = facturaIdFactura;
    }
   
    public int getIdDetalleFactura() {
        return this.idDetalleFactura;
    }
    
    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }
    public int getFacturaIdFactura() {
        return this.facturaIdFactura;
    }
    
    public void setFacturaIdFactura(int facturaIdFactura) {
        this.facturaIdFactura = facturaIdFactura;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DetalleFacturaId) ) return false;
		 DetalleFacturaId castOther = ( DetalleFacturaId ) other; 
         
		 return (this.getIdDetalleFactura()==castOther.getIdDetalleFactura())
 && (this.getFacturaIdFactura()==castOther.getFacturaIdFactura());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdDetalleFactura();
         result = 37 * result + this.getFacturaIdFactura();
         return result;
   }   


}


